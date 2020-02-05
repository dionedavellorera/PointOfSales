package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.dialogs.ChangeRoomStatusDialog;

public class ChangeRoomStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchRoomStatusResponse.Result> statusList;
    private ChangeRoomStatusDialog.NewRoomStatus newRoomStatus;
    public ChangeRoomStatusAdapter(List<FetchRoomStatusResponse.Result> statusList, ChangeRoomStatusDialog.NewRoomStatus newRoomStatus) {
        this.statusList = statusList;
        this.newRoomStatus = newRoomStatus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChangeRoomStatusAdapter.ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_new_status, viewGroup, false));
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout rootView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ChangeRoomStatusAdapter.ListViewHolder){

            //CLEAN, DIRTY, PREVENTIVE MAINTENANCE only for change status
            if (statusList.get(i).getCoreId() != 1 && statusList.get(i).getCoreId() != 3 &&
                    statusList.get(i).getCoreId() != 35) {

                ((ListViewHolder) holder).rootView.setVisibility(View.GONE);
                ((ListViewHolder) holder).rootView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            } else {
                ((ListViewHolder) holder).rootView.setVisibility(View.VISIBLE);
                ((ListViewHolder) holder).rootView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                ((ChangeRoomStatusAdapter.ListViewHolder) holder).name.setText(statusList.get(i).getRoomStatus());

                ((ChangeRoomStatusAdapter.ListViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newRoomStatus.clicked(statusList.get(i).getCoreId(), statusList.get(i).getRoomStatus());
                    }
                });
            }




        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
