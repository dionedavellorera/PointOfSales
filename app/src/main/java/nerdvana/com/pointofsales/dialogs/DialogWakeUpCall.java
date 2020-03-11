package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.WakeUpCallAdapter;
import nerdvana.com.pointofsales.api_requests.SaveWakeUpCallRequest;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.entities.RoomEntity;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.WakeUpCallModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogWakeUpCall extends BaseDialog {
    private AddOkWakeUpCall addOkWakeUpCall;
    private List<RoomEntity> wakeUpCallModels = new ArrayList<>();
    private WakeUpCallAdapter wakeUpCallAdapter;
    private RecyclerView listWakeUps;
    private Button clearAll;

    public DialogWakeUpCall(@NonNull Context context, List<RoomEntity> wakeUpCallModelList) {
        super(context);
        this.wakeUpCallModels = wakeUpCallModelList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_wake_up_call, "Wake Up Call");
        setCancelable(false);
        clearAll = findViewById(R.id.clearAll);
        listWakeUps = findViewById(R.id.listWakeUp);

        addOkWakeUpCall = new AddOkWakeUpCall() {
            @Override
            public void click(int position) {

                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                SaveWakeUpCallRequest saveWakeUpCallRequest = new SaveWakeUpCallRequest(wakeUpCallModels.get(position).getControl_number());
                Call<ResponseBody> request = iUsers.saveWakeUpCall(saveWakeUpCallRequest.getMapValue());
                request.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        BusProvider.getInstance().post(new PrintModel(
                                "", wakeUpCallModels.get(position).getRoom_number(),
                                "PRINT_WAKEUP_CALL",
                                GsonHelper.getGson().toJson(wakeUpCallModels.get(position)),
                                wakeUpCallModels.get(position).getRoom_type(), "", ""));


                        wakeUpCallModels.get(position).setIs_done(1);
                        wakeUpCallModels.get(position).save();
                        wakeUpCallModels.remove(position);





                        if (wakeUpCallAdapter != null) {
                            wakeUpCallAdapter.notifyItemRemoved(position);
                        }

                        if (wakeUpCallModels.size() == 0) dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });



            }
        };

        if (wakeUpCallModels != null) {
            if (wakeUpCallModels.size() > 0) {
                wakeUpCallAdapter = new WakeUpCallAdapter(wakeUpCallModels, addOkWakeUpCall);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                listWakeUps.setLayoutManager(llm);
                listWakeUps.setAdapter(wakeUpCallAdapter);
            }
        }

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                TypeToken<List<String>> roomToken = new TypeToken<List<String>>() {};
//                List<String> wul =
//                        GsonHelper
//                                .getGson()
//                                .fromJson(
//                                        SharedPreferenceManager.getString(null, "room_no_list"),
//                                        roomToken.getType());
//
//                if (wul != null) {
//                    roomNoList = wul;
//                }
//
//
//                for (WakeUpCallModel wum : wakeUpCallModels) {
//                    if (!roomNoList.contains(wum.getRoomNumber())) {
//                        roomNoList.add(wum.getRoomNumber());
//                    }
//
//                }
//
//                SharedPreferenceManager.saveString(
//                        getContext(),
//                        GsonHelper.getGson().toJson(roomNoList),
//                        "room_no_list");
//
//                wakeUpCallModels.clear();
//
//                if (wakeUpCallAdapter != null) {
//                    wakeUpCallAdapter.notifyDataSetChanged();
//                }

                dismiss();
            }
        });
    }

    public interface AddOkWakeUpCall {
        void click(int position);
    }
}
