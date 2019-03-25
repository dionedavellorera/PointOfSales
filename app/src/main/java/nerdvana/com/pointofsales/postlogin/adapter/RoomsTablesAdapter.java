package nerdvana.com.pointofsales.postlogin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private Animation animBlink;
    private Context context;
    public RoomsTablesAdapter(List<RoomTableModel> roomTableModelList, SelectionContract selectionContract,
                              Context context) {
        this.roomTableModelList = roomTableModelList;
        this.selectionContract = selectionContract;
        this.context = context;
        animBlink = AnimationUtils.loadAnimation(context,
                R.anim.blink);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomsTablesAdapter.ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_roomtables, viewGroup, false));
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView timer;
        private TextView price;
        private ImageView imageUrl;
        private CardView rootView;
        private RelativeLayout rel;
        private TextView orderCount;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timer = itemView.findViewById(R.id.timer);
            price = itemView.findViewById(R.id.roomPrice);
            imageUrl = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.rootView);
            rel = itemView.findViewById(R.id.rel);
            orderCount = itemView.findViewById(R.id.orderCount);
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

        if (roomTableModelList.get(i).getUnpostedOrdersCount() > 0) {
            ((ProductsViewHolder) holder).orderCount.setVisibility(View.VISIBLE);
            ((ProductsViewHolder) holder).orderCount.setText(String.valueOf(roomTableModelList.get(i).getUnpostedOrdersCount()));
        } else {
            ((ProductsViewHolder) holder).orderCount.setVisibility(View.GONE);
        }

        if (productsModel.getOtHours().equalsIgnoreCase("0.0") || TextUtils.isEmpty(productsModel.getOtHours())) {
            ((RoomsTablesAdapter.ProductsViewHolder)holder).name.setText(productsModel.getName());
        } else {
            ((RoomsTablesAdapter.ProductsViewHolder)holder).name.setText(productsModel.getName() + "\n(OT:" + productsModel.getOtHours()+")");
        }

        ImageLoader.loadImage(productsModel.getImageUrl(), ((RoomsTablesAdapter.ProductsViewHolder)holder).imageUrl);

        if (!productsModel.isTakeOut()) {
            ((ProductsViewHolder)holder).name.setBackgroundColor(Color.parseColor(productsModel.getHexColor()));
        }

        if (productsModel.isBlink()) {
            ((ProductsViewHolder)holder).name.startAnimation(animBlink);
        } else {
            ((ProductsViewHolder)holder).name.clearAnimation();
        }


        ((ProductsViewHolder)holder).timer.setText(productsModel.getExpectedCheckout());
        ((ProductsViewHolder)holder).price.setText(String.valueOf(productsModel.getAmountSelected()));



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
