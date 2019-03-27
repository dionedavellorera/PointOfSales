package nerdvana.com.pointofsales.postlogin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.model.DepartmentsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class DepartmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DepartmentsModel> roomTableModelList;
    public DepartmentsAdapter(List<DepartmentsModel> roomTableModelList) {
        this.roomTableModelList = roomTableModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DepartmentsAdapter.DepartmentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_departments, viewGroup, false));
    }



    static class DepartmentsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView imageUrl;
        public DepartmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageUrl = itemView.findViewById(R.id.image);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        final DepartmentsModel productsModel = roomTableModelList.get(i);

        ((DepartmentsAdapter.DepartmentsViewHolder)holder).name.setText(productsModel.getName());
//        ImageLoader.loadImage(productsModel.getImageUrl(), ((DepartmentsAdapter.DiscProductsViewHolder)holder).imageUrl);
    }


    public void addItems(List<DepartmentsModel> roomTableModelList) {
        this.roomTableModelList = roomTableModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return roomTableModelList.size();
    }
}
