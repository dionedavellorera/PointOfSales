package nerdvana.com.pointofsales.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.interfaces.RoomFilterContract;
import nerdvana.com.pointofsales.model.FilterOptionModel;

public class RoomFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FilterOptionModel> filterOptionsList;
    private RoomFilterContract roomFilterContract;
    public RoomFilterAdapter(List<FilterOptionModel> filterOptionsList, RoomFilterContract roomFilterContract) {
        this.filterOptionsList = filterOptionsList;
        this.roomFilterContract = roomFilterContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomFilterAdapter.RoomFilterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_room_filter, viewGroup, false));
    }



    static class RoomFilterViewHolder extends RecyclerView.ViewHolder {
        private TextView filter;
        private RelativeLayout row;
        public RoomFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filter = itemView.findViewById(R.id.filter);
            row = itemView.findViewById(R.id.row);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        FilterOptionModel fom = filterOptionsList.get(i);
        if (filterOptionsList.get(i).isSelected()) {
            ((RoomFilterViewHolder)holder).filter.setSelected(true);
        } else {
            ((RoomFilterViewHolder)holder).filter.setSelected(false);
        }

        ((RoomFilterViewHolder)holder).filter.setText(String.format("%s(%s)", fom.getName(), String.valueOf(fom.getStatusCount())));
        ((RoomFilterViewHolder)holder).filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (FilterOptionModel f : filterOptionsList) {
                    f.setSelected(false);
                }

                filterOptionsList.get(i).setSelected(true);
                roomFilterContract.filterSelected(fom.getStatusId());

                notifyDataSetChanged();
            }
        });

        if (fom.getHexColor() != null) {
            ((RoomFilterViewHolder)holder).filter.setTextColor(Color.parseColor(fom.getHexColor()));
        }

    }


    @Override
    public int getItemCount() {
        return filterOptionsList.size();
    }
}
