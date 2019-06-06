package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public class RoomListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchRoomResponse.Result> roomDataList;
    public RoomListViewAdapter(List<FetchRoomResponse.Result> roomDataList) {
        this.roomDataList = roomDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomListViewAdapter.RoomDataHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_room_list_view, viewGroup, false));
    }

    static class RoomDataHolder extends RecyclerView.ViewHolder {
        private LinearLayout row;
        private TextView room;
        private TextView status;
        private TextView rate;
        private TextView wakeUpCall;
        private TextView elapsed;
        private TextView dateTimeIn;
        private TextView dateTimeOut;
        private TextView remarks;
        private TextView deposit;
        private TextView fnb;
        public RoomDataHolder(@NonNull View itemView) {
            super(itemView);
            room = itemView.findViewById(R.id.room);
            status = itemView.findViewById(R.id.status);
            rate = itemView.findViewById(R.id.rate);
            wakeUpCall = itemView.findViewById(R.id.wakeUpCall);
            elapsed = itemView.findViewById(R.id.elapsed);
            dateTimeIn = itemView.findViewById(R.id.dateTimeIn);
            dateTimeOut = itemView.findViewById(R.id.dateTimeOut);
            remarks = itemView.findViewById(R.id.remarks);
            deposit = itemView.findViewById(R.id.deposit);
            fnb = itemView.findViewById(R.id.fnb);
            row = itemView.findViewById(R.id.row);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof RoomListViewAdapter.RoomDataHolder){
//            roomDataList.get(i).getStatus().getCoreId() == 3
//            roomDataList.get(i).getStatus().getCoreId() == 31
//            roomDataList.get(i).getStatus().getCoreId() == 17
//            roomDataList.get(i).getStatus().getCoreId() == 1
            if (roomDataList.get(i).getStatus().getCoreId() == 2 || roomDataList.get(i).getStatus().getCoreId() == 17) {
//                ((RoomListViewAdapter.RoomDataHolder) holder).row.setVisibility(View.VISIBLE);

                ((RoomListViewAdapter.RoomDataHolder)holder).row.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;


                List<String> rateList = new ArrayList<>();
                Double totalRoom = 0.00;
                if (roomDataList.get(i).getTransaction() != null) {
                    if (roomDataList.get(i).getTransaction().getTransaction() != null) {
                        for (FetchRoomResponse.PostRoom pr : roomDataList.get(i).getTransaction().getTransaction().getPostRoom()) {
                            rateList.add(pr.getRoomRate());
                        }

                        Double totalFnb = 0.00;
                        for (FetchRoomResponse.PostFood pf : roomDataList.get(i).getTransaction().getTransaction().getPostFood()) {
                            totalFnb += pf.getTotal() * pf.getQty();
                        }
                        ((RoomDataHolder) holder).fnb.setText(String.valueOf(totalFnb));
                    }
                }

                ((RoomListViewAdapter.RoomDataHolder) holder).room.setText(String.format("%s", roomDataList.get(i).getRoomNo()));
                ((RoomListViewAdapter.RoomDataHolder) holder).status.setText(roomDataList.get(i).getStatus().getRoomStatus());
                ((RoomListViewAdapter.RoomDataHolder) holder).rate.setText(TextUtils.join(",", rateList));
                if (roomDataList.get(i).getTransaction() != null) {
                    ((RoomListViewAdapter.RoomDataHolder) holder).wakeUpCall.setText(roomDataList.get(i).getTransaction().getWakeUpCall());
                    ((RoomListViewAdapter.RoomDataHolder) holder).elapsed.setText(PrinterUtils.getDuration(roomDataList.get(i).getTransaction().getCheckIn()));
                    ((RoomListViewAdapter.RoomDataHolder) holder).dateTimeIn.setText(roomDataList.get(i).getTransaction().getCheckIn());
                    ((RoomListViewAdapter.RoomDataHolder) holder).dateTimeOut.setText(roomDataList.get(i).getTransaction().getExpectedCheckOut());
                    ((RoomListViewAdapter.RoomDataHolder) holder).dateTimeOut.setText(roomDataList.get(i).getTransaction().getExpectedCheckOut());
                    ((RoomDataHolder) holder).remarks.setText("-----");
                    ((RoomDataHolder) holder).deposit.setText(String.valueOf(roomDataList.get(i).getTransaction().getTransaction().getAdvance()));
                }


            } else {

                ((RoomListViewAdapter.RoomDataHolder)holder).row.getLayoutParams().height = 0;

//                ((RoomListViewAdapter.RoomDataHolder) holder).row.setVisibility(View.GONE);
            }


        }

    }

    @Override
    public int getItemCount() {
        return roomDataList.size();
    }
}
