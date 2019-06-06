package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.dialogs.FreebiesDialog;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public class FreebiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchRoomPendingResponse.Freebies> freebyList;
    private AvailableGcDialog.Item item;
    private FreebiesDialog.Freeby freeby;
    public FreebiesListAdapter(List<FetchRoomPendingResponse.Freebies> freebyList, FreebiesDialog.Freeby freeby) {
        this.freebyList = freebyList;
        this.freeby = freeby;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FreebiesListAdapter.ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_freebies, viewGroup, false));
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout row;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            row = itemView.findViewById(R.id.row);
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof FreebiesListAdapter.ListViewHolder){
            ((FreebiesListAdapter.ListViewHolder) holder).name.setText(freebyList.get(i).getFreebyRoomRatePrice().getFreebyRoomRate().getRoomRate());
            ((FreebiesListAdapter.ListViewHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    freeby.clicked(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return freebyList.size();
    }
}
