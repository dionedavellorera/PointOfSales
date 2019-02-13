package nerdvana.com.pointofsales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.interfaces.RoomFilterContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.FilterOptionModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;

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
        public RoomFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filter = itemView.findViewById(R.id.filter);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {

        if (filterOptionsList.get(i).isSelected()) {
            ((RoomFilterViewHolder)holder).filter.setSelected(true);
        } else {
            ((RoomFilterViewHolder)holder).filter.setSelected(false);
        }

        ((RoomFilterViewHolder)holder).filter.setText(filterOptionsList.get(i).getName());
        ((RoomFilterViewHolder)holder).filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (FilterOptionModel f : filterOptionsList) {

                    f.setSelected(false);

                }

                filterOptionsList.get(i).setSelected(true);
                roomFilterContract.filterSelected(filterOptionsList.get(i).getStatusId());
//                ((RoomFilterViewHolder)holder).filter.setSelected(true);

                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return filterOptionsList.size();
    }
}
