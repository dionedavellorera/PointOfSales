package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.dialogs.DialogWakeUpCall;
import nerdvana.com.pointofsales.entities.RoomEntity;
import nerdvana.com.pointofsales.model.WakeUpCallModel;

public class WakeUpCallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoomEntity> wakeUpList;
    private DialogWakeUpCall.AddOkWakeUpCall addOkWakeUpCall;
    private List<String> roomNoList = new ArrayList<>();
    DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public WakeUpCallAdapter(List<RoomEntity> wakeUpCallModels,
                             DialogWakeUpCall.AddOkWakeUpCall addOkWakeUpCall) {
        this.wakeUpList = wakeUpCallModels;
        this.addOkWakeUpCall = addOkWakeUpCall;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WakeUpCallAdapter
                .ViewHolder(
                        LayoutInflater
                                .from(viewGroup.getContext())
                                .inflate(R.layout.list_item_wake_up_call,
                                        viewGroup,
                                        false));
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button done;
        private TextView name;
        private TextView subHeader;
        private RelativeLayout rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            subHeader = itemView.findViewById(R.id.subHeader);
            done = itemView.findViewById(R.id.done);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {


        if(holder instanceof WakeUpCallAdapter.ViewHolder){

            ((ViewHolder)holder)
                    .name
                    .setText("ROOM#" + wakeUpList.get(i).getRoom_number());



            ((ViewHolder)holder)
                    .subHeader
                    .setText("TIME : " + new DateTime(df.parseDateTime(wakeUpList.get(i).getWake_up_call())).toString("yyyy-MM-dd hh:mm a"));

            ((ViewHolder)holder)
                    .done
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addOkWakeUpCall.click(holder.getAdapterPosition());
                        }
                    });

        }
    }

    @Override
    public int getItemCount() {
        return wakeUpList.size();
    }
}
