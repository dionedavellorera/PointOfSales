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

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.WakeUpCallAdapter;
import nerdvana.com.pointofsales.entities.RoomEntity;
import nerdvana.com.pointofsales.model.WakeUpCallModel;

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
                wakeUpCallModels.get(position).setIs_done(1);
                wakeUpCallModels.get(position).save();
                wakeUpCallModels.remove(position);
                if (wakeUpCallAdapter != null) {
                    wakeUpCallAdapter.notifyItemRemoved(position);
                }

                if (wakeUpCallModels.size() == 0) dismiss();

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
