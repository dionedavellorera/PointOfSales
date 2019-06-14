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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;

public class RoomsTablesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<RoomTableModel> roomTableModelList;
    private SelectionContract selectionContract;

    private List<RoomTableModel> roomsFilteredList;


    private Animation animBlink;
    private Context context;
    private String systemType;
    public RoomsTablesAdapter(List<RoomTableModel> roomTableModelList, SelectionContract selectionContract,
                              Context context, String systemType) {
        this.roomsFilteredList = new ArrayList<>(roomTableModelList);
        this.roomTableModelList = new ArrayList<>(roomTableModelList);
        this.selectionContract = selectionContract;
        this.context = context;
        this.systemType = systemType;
        animBlink = AnimationUtils.loadAnimation(context,
                R.anim.blink);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomsTablesAdapter.ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_roomtables, viewGroup, false));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSting = constraint.toString();
                roomsFilteredList = new ArrayList<>();
                if (charSting.isEmpty()) {
                    roomsFilteredList = roomTableModelList;
                } else {
                    List<RoomTableModel> filteredList = new ArrayList<>();
                    for (RoomTableModel pm : roomTableModelList) {
                        if (pm.getName().contains(charSting)) {
                            filteredList.add(pm);
                        }
                    }
                    roomsFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = roomsFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                roomsFilteredList = (ArrayList<RoomTableModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView timer;
        private TextView price;
        private ImageView imageUrl;
        private ImageView badge;
        private CardView rootView;
        private RelativeLayout rel;
        private TextView orderCount;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            timer = itemView.findViewById(R.id.timer);
            price = itemView.findViewById(R.id.roomPrice);
            imageUrl = itemView.findViewById(R.id.image);
            badge = itemView.findViewById(R.id.badge);
            rootView = itemView.findViewById(R.id.rootView);
            rel = itemView.findViewById(R.id.rel);
            orderCount = itemView.findViewById(R.id.orderCount);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        final RoomTableModel productsModel = roomsFilteredList.get(i);
        ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionContract.listClicked(productsModel);
                ((ProductsViewHolder)holder).rel.setSelected(true);
            }
        });
        if (productsModel.getOtHours().equalsIgnoreCase("0.0") || TextUtils.isEmpty(productsModel.getOtHours())) {
            ((RoomsTablesAdapter.ProductsViewHolder)holder).name.setText(productsModel.getName());
        } else {
            ((RoomsTablesAdapter.ProductsViewHolder)holder).name.setText(productsModel.getName() + "\n(OT:" + productsModel.getOtHours()+")");
        }



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

        if (!systemType.equalsIgnoreCase("franchise")) {
            ((ProductsViewHolder)holder).badge.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(String.format("%sstatus_%s.png", SharedPreferenceManager.getString(context, ApplicationConstants.API_IMAGE_URL), productsModel.getStatus()), ((ProductsViewHolder) holder).badge);
        } else {
            ((RoomsTablesAdapter.ProductsViewHolder)holder).badge.setVisibility(View.GONE);
        }




    }


    public void addItems(List<RoomTableModel> roomTableModelList) {

        this.roomTableModelList = new ArrayList<>(roomTableModelList);
        this.roomsFilteredList = roomTableModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return roomsFilteredList.size();
    }


}
