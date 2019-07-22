package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.dialogs.DialogWakeUpCall;
import nerdvana.com.pointofsales.interfaces.VoidItemContract;
import nerdvana.com.pointofsales.model.ForVoidDiscountModel;
import nerdvana.com.pointofsales.model.WakeUpCallModel;

public class WakeUpCallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WakeUpCallModel> wakeUpList;
    private DialogWakeUpCall.AddOkWakeUpCall addOkWakeUpCall;
    private List<String> roomNoList = new ArrayList<>();

    public WakeUpCallAdapter(List<WakeUpCallModel> wakeUpCallModels,
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


            TypeToken<List<String>> roomToken = new TypeToken<List<String>>() {};
            List<String> wul =
                    GsonHelper
                            .getGson()
                            .fromJson(
                                    SharedPreferenceManager.getString(null, "room_no_list"),
                                    roomToken.getType());

            if (wul != null) {
                if (wul.size() > 0) {
                    if (!wul.contains(wakeUpList.get(i).getRoomNumber())) {
                        ((ViewHolder)holder)
                                .name
                                .setText("ROOM#" + wakeUpList.get(i).getRoomNumber());

                        ((ViewHolder)holder)
                                .subHeader
                                .setText("WAKE UP CALL TIME: " + wakeUpList.get(i).getWakeUpCallTime());

                        ((ViewHolder)holder)
                                .done
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        addOkWakeUpCall.click(holder.getAdapterPosition());
                                    }
                                });

                        ((ViewHolder)holder)
                                .rootView
                                .setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    } else {
                        ((ViewHolder)holder)
                                .rootView
                                .setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    }
                } else {
                    ((ViewHolder)holder)
                            .name
                            .setText("ROOM#" + wakeUpList.get(i).getRoomNumber());

                    ((ViewHolder)holder)
                            .subHeader
                            .setText("WAKE UP CALL TIME: " + wakeUpList.get(i).getWakeUpCallTime());

                    ((ViewHolder)holder)
                            .done
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addOkWakeUpCall.click(holder.getAdapterPosition());
                                }
                            });
                }
            } else {
                ((ViewHolder)holder)
                        .name
                        .setText("ROOM#" + wakeUpList.get(i).getRoomNumber());

                ((ViewHolder)holder)
                        .subHeader
                        .setText("WAKE UP CALL TIME: " + wakeUpList.get(i).getWakeUpCallTime());

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
    }

    @Override
    public int getItemCount() {
        return wakeUpList.size();
    }
}
