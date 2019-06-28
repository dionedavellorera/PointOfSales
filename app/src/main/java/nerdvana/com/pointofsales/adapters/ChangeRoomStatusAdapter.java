package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.dialogs.ChangeRoomStatusDialog;
import nerdvana.com.pointofsales.model.AvailableGcModel;

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
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ChangeRoomStatusAdapter.ListViewHolder){
            ((ChangeRoomStatusAdapter.ListViewHolder) holder).name.setText(statusList.get(i).getRoomStatus());

            ((ChangeRoomStatusAdapter.ListViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newRoomStatus.clicked(statusList.get(i).getCoreId(), statusList.get(i).getRoomStatus());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
