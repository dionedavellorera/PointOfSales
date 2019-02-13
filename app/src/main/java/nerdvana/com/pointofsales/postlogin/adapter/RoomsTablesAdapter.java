package nerdvana.com.pointofsales.postlogin.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class RoomsTablesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RoomTableModel> roomTableModelList;
    private SelectionContract selectionContract;
    public RoomsTablesAdapter(List<RoomTableModel> roomTableModelList, SelectionContract selectionContract) {
        this.roomTableModelList = roomTableModelList;
        this.selectionContract = selectionContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomsTablesAdapter.ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_roomtables, viewGroup, false));
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private ImageView imageUrl;
        private CardView rootView;
        private RelativeLayout rel;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imageUrl = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.rootView);
            rel = itemView.findViewById(R.id.rel);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        final RoomTableModel productsModel = roomTableModelList.get(i);
        ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionContract.listClicked(productsModel);
                ((ProductsViewHolder)holder).rel.setSelected(true);
            }
        });
        ((RoomsTablesAdapter.ProductsViewHolder)holder).name.setText(productsModel.getName());
        ImageLoader.loadImage(productsModel.getImageUrl(), ((RoomsTablesAdapter.ProductsViewHolder)holder).imageUrl);


        ((ProductsViewHolder)holder).name.setBackgroundColor(Color.parseColor(productsModel.getHexColor()));

//        if (productsModel.getStatus().equalsIgnoreCase(RoomConstants.CLEAN)) {
//
//        } else if (productsModel.getStatus().equalsIgnoreCase(RoomConstants.DIRTY)) {
//
//        } else if (productsModel.getStatus().equalsIgnoreCase(RoomConstants.OCCUPIED)) {
//
//        } else if (productsModel.getStatus().equalsIgnoreCase(RoomConstants.DIRTY_WITH_LINEN)) {
//
//        } else if (productsModel.getStatus().equalsIgnoreCase(RoomConstants.SOA)) {
//
//        } else {
//            //return default color
//            ((RoomsTablesAdapter.ProductsViewHolder)holder).rel.setBackgroundResource(R.color.colorWhite);
//        }

    }


    public void addItems(List<RoomTableModel> roomTableModelList) {
        this.roomTableModelList = roomTableModelList;
        notifyDataSetChanged();
    }

    public List<RoomTableModel> getRoomTableModelList() {
        return roomTableModelList;
    }

    public void setRoomTableModelList(List<RoomTableModel> roomTableModelList) {
        this.roomTableModelList = roomTableModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return roomTableModelList.size();
    }
}
