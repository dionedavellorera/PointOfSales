package nerdvana.com.pointofsales.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.CheckSafeKeepingRequest;
import nerdvana.com.pointofsales.api_requests.CheckShiftRequest;
import nerdvana.com.pointofsales.api_requests.RepatchDataRequest;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.background.CountUpTimer;
import nerdvana.com.pointofsales.background.WakeUpCallReminderAsync;
import nerdvana.com.pointofsales.model.GlobalServerTime;
import nerdvana.com.pointofsales.model.InfoModel;
import nerdvana.com.pointofsales.model.ServerConnectionModel;
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

    private Call<ResponseBody> checkShiftApiRequest;

    CountDownTimer countUpTimer;
    @Override
    public void onCreate() {
        super.onCreate();

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
                        //currentDate = Utils.convertSecondsToSqlDate(secsOfDate);

                        BusProvider.getInstance().post(new GlobalServerTime(Utils.convertSecondsToSqlDate(secsOfDate)));
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



                            if (checkShiftApiRequest == null) {
                                CheckShiftRequest checkShiftRequest = new CheckShiftRequest();

                                checkShiftApiRequest = iUsers.checkShiftRaw(checkShiftRequest.getMapValue());
                                checkShiftApiRequest.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        checkShiftApiRequest = null;
                                        try {
                                            String rawResponse = response.body().string();
                                            JSONObject responseObject = new JSONObject(rawResponse);

                                            JSONArray resArr = responseObject.optJSONArray("result");
                                            String shiftNumberToSend = "";

                                            if (responseObject.getInt("status") == 0) {

                                                if (!SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_SETUP).isEmpty()) {
                                                    if (SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {

                                                    } else {
                                                        if (responseObject.getString("message").equalsIgnoreCase("Please execute end of day")) {

                                                            if (resArr != null) {
                                                                JSONObject resultObj = resArr.getJSONObject(0);
                                                                shiftNumberToSend = resultObj.getString("shift_no");
                                                            }


                                                            if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {

                                                                BusProvider.getInstance().post(new InfoModel("Please execute end of day", shiftNumberToSend));
                                                            }
                                                        } else {
                                                            if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {

                                                                BusProvider.getInstance().post(new InfoModel("Generate end of day", shiftNumberToSend));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (responseObject.getString("message").equalsIgnoreCase("Please execute end of day")) {

                                                        if (resArr != null) {
                                                            JSONObject resultObj = resArr.getJSONObject(0);
                                                            shiftNumberToSend = resultObj.getString("shift_no");
                                                        }


                                                        if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                            BusProvider.getInstance().post(new InfoModel("Please execute end of day", shiftNumberToSend));

                                                        }
                                                    } else {
                                                        if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                            BusProvider.getInstance().post(new InfoModel("Generate end of day", shiftNumberToSend));
                                                        }
                                                    }
                                                }


                                            } else {
                                                JSONArray resultArray = responseObject.getJSONArray("result");
                                                if (resultArray.length() > 0) {
                                                    if (resultArray.getJSONObject(0).getString("eTime") == null) {
                                                        shiftDisplay = "0";
                                                        BusProvider.getInstance().post(new InfoModel("ALLOW", shiftNumberToSend));
                                                        if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                            BusProvider.getInstance().post(new CheckSafeKeepingRequest());
                                                        }

                                                    } else {
                                                        DateTimeFormatter fff = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                                        shiftDisplay = String.valueOf(resultArray.getJSONObject(0).getString("shift_no"));

                                                        if (resultArray.getJSONObject(0).getString("eTime").equalsIgnoreCase("null")){
                                                            BusProvider.getInstance().post(new InfoModel("ALLOW", shiftDisplay));
                                                        } else {
                                                            if (resultArray.getJSONObject(0).getString("eTime").equalsIgnoreCase("null")){
                                                                BusProvider.getInstance().post(new InfoModel("ALLOW", shiftDisplay));
                                                            } else {
                                                                DateTime endShiftTime = fff.parseDateTime(resultArray.getJSONObject(0).getString("date") + " " + resultArray.getJSONObject(0).getString("eTime"));
                                                                if (!SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_SETUP).isEmpty()) {
                                                                    if (SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {

                                                                    } else {
                                                                        if ((secsOfDate >= (endShiftTime.getMillis() / 1000))) {

                                                                            if (resArr != null) {
                                                                                JSONObject resultObj = resArr.getJSONObject(0);
                                                                                shiftNumberToSend = resultObj.getString("shift_no");
                                                                            }

                                                                            if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                                                BusProvider.getInstance().post(new InfoModel("Please execute cutoff", shiftNumberToSend));
                                                                            }

                                                                        } else {
                                                                            if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                                                BusProvider.getInstance().post(new CheckSafeKeepingRequest());

                                                                            }

                                                                            if (resArr != null) {
                                                                                JSONObject resultObj = resArr.getJSONObject(0);
                                                                                shiftNumberToSend = resultObj.getString("shift_no");
                                                                            }

                                                                            BusProvider.getInstance().post(new InfoModel("ALLOW", shiftNumberToSend));
                                                                        }
                                                                    }
                                                                } else {
                                                                    if ((secsOfDate >= (endShiftTime.getMillis() / 1000))) {

                                                                        if (resArr != null) {
                                                                            JSONObject resultObj = resArr.getJSONObject(0);
                                                                            shiftNumberToSend = resultObj.getString("shift_no");
                                                                        }


                                                                        if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                                            BusProvider.getInstance().post(new InfoModel("Please execute cutoff", shiftNumberToSend));
                                                                        }
                                                                    } else {
                                                                        if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                                            BusProvider.getInstance().post(new CheckSafeKeepingRequest());

                                                                        }

                                                                        if (resArr != null) {
                                                                            JSONObject resultObj = resArr.getJSONObject(0);
                                                                            shiftNumberToSend = resultObj.getString("shift_no");
                                                                        }

                                                                        BusProvider.getInstance().post(new InfoModel("ALLOW", shiftNumberToSend));
                                                                    }
                                                                }
                                                            }


                                                        }


                                                    }
                                                } else {
                                                    shiftDisplay = "0";
                                                    BusProvider.getInstance().post(new InfoModel("ALLOW", shiftNumberToSend));
                                                    if (!SharedPreferenceManager.getString(null, ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                                                        BusProvider.getInstance().post(new CheckSafeKeepingRequest());
                                                    }
                                                }

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        checkShiftApiRequest = null;

                                    }
                                });


                            }


                        }
//                        else if (secsOfDate % 15 == 0) {
//                            BusProvider.getInstance().post(new CheckSafeKeepingRequest());
//                        }
                        if (SharedPreferenceManager.getString(null, ApplicationConstants.IS_ALLOWED_WAKE_UP_CALL).equalsIgnoreCase("y")) {
                            if (secsOfDate % 35 == 0){
                                new WakeUpCallReminderAsync(secsOfDate).execute();
                            }
                        }



//                        if (secsOfDate % 5 == 0) {
////                            BusProvider.getInstance().post(new ServerConnectionModel(Utils.canConnectToServer()));
//                        }
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

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

                DateTime midTime = fff.parseDateTime(formatter.format(new Date(secsOfDate * 1000L)));


                DateTimeFormatter ddd = DateTimeFormat.forPattern("HH:mm:ss");
                DateTime endSec = ddd.parseDateTime(res.getETime());

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
