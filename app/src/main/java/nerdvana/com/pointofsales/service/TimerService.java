package nerdvana.com.pointofsales.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.CheckShiftRequest;
import nerdvana.com.pointofsales.api_requests.RepatchDataRequest;
import nerdvana.com.pointofsales.api_responses.CheckShiftResponse;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import nerdvana.com.pointofsales.background.CountUpTimer;
import nerdvana.com.pointofsales.model.InfoModel;
import nerdvana.com.pointofsales.model.TimerModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimerService extends Service {
    long secsOfDate = 0;
    String startDate = "";
    String shiftInfoStringArray = "";
    private String shiftDisplay = "";
    private static String currentDate = "";

    CountDownTimer countUpTimer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TIMER_SERVICe", "TIMER STARTED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getStringExtra("start_time") != null) {
                startDate = intent.getStringExtra("start_time");
                shiftInfoStringArray = intent.getStringExtra("shift_info_array");

                TypeToken<List<FetchBranchInfoResponse.Shift>> branchInfo = new TypeToken<List<FetchBranchInfoResponse.Shift>>() {};
                final List<FetchBranchInfoResponse.Shift> userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.SHIFT_INFO_ARRAY), branchInfo.getType());
;


                secsOfDate = Utils.getDurationInSecs(startDate);

                countUpTimer = new CountUpTimer(999999999) {
                    @Override
                    public void onTick(int second) {
                        secsOfDate+= 1;


                        BusProvider.getInstance().post(new TimerModel(Utils.convertSecondsToReadableDate(secsOfDate), shiftDisplay));
                        currentDate = Utils.convertSecondsToReadableDate(secsOfDate);

                        if (secsOfDate % 10 == 0) {
//                            RepatchDataRequest repatchDataRequest = new RepatchDataRequest();
                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
//                            Call<ResponseBody> repatchData = iUsers.repatchData(
//                                    repatchDataRequest.getMapValue());
//                            repatchData.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                }
//                            });


                            CheckShiftRequest checkShiftRequest = new CheckShiftRequest();
//                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                            Call<CheckShiftResponse> request = iUsers.checkShift(checkShiftRequest.getMapValue());
                            request.enqueue(new Callback<CheckShiftResponse>() {
                                @Override
                                public void onResponse(Call<CheckShiftResponse> call, Response<CheckShiftResponse> response) {
                                    if (response.body().getStatus() == 1) {
                                        if (response.body().getResult().size() > 0) {
                                            DateTimeFormatter fff = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                            DateTime endShiftTime = fff.parseDateTime(response.body().getResult().get(0).getLastTransDate() + " " + response.body().getResult().get(0).getETime());
                                            shiftDisplay = String.valueOf(response.body().getResult().get(0).getShiftNo());
                                            if ((secsOfDate >= (endShiftTime.getMillis() / 1000))) {
                                                BusProvider.getInstance().post(new InfoModel("Please execute cutoff"));
                                            } else {
                                                BusProvider.getInstance().post(new InfoModel("ALLOW"));
                                            }
                                        } else {
                                            shiftDisplay = "0";
                                            BusProvider.getInstance().post(new InfoModel("ALLOW"));
                                        }
                                    } else {
                                        BusProvider.getInstance().post(new InfoModel("Please execute end of day"));
                                    }

                                }

                                @Override
                                public void onFailure(Call<CheckShiftResponse> call, Throwable t) {
                                    BusProvider.getInstance().post(new InfoModel("Please execute end of day"));
                                }
                            });

                        }
                    }
                }.start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private String shiftNumber(List<FetchBranchInfoResponse.Shift> userList, long secsOfDate) {
        String shiftNumber = "0";
        if (userList.size() > 0) {
            for (FetchBranchInfoResponse.Shift res : userList) {
                DateTimeFormatter fff = DateTimeFormat.forPattern("HH:mm:ss");
                DateTime startSec = fff.parseDateTime(res.getSTime());
//                Log.d("CCCCCC-START", String.valueOf(startSec.getSecondOfDay()));

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

                DateTime midTime = fff.parseDateTime(formatter.format(new Date(secsOfDate * 1000L)));
//                Log.d("CCCCCC-MID", String.valueOf(midTime.getSecondOfDay()));


                DateTimeFormatter ddd = DateTimeFormat.forPattern("HH:mm:ss");
                DateTime endSec = ddd.parseDateTime(res.getETime());

//                Log.d("CCCCCC-END", String.valueOf(endSec.getSecondOfDay()));
                if (midTime.getSecondOfDay() >= startSec.getSecondOfDay() && midTime.getSecondOfDay() <= endSec.getSecondOfDay()) {
                    shiftNumber = String.valueOf(res.getShiftNo());
                    break;
                }
            }
        } else {
            shiftNumber = "0";
        }

        return shiftNumber;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countUpTimer != null) {
            countUpTimer.cancel();
            countUpTimer = null;
        }
    }

    public static String getCurrentDate() {
        return currentDate;
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
