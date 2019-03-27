package nerdvana.com.pointofsales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.DiscountListModel;

public class DiscountProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DiscountListModel.DiscountProduct> productsList;

    public DiscountProductsAdapter(List<DiscountListModel.DiscountProduct> productsList) {
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DiscProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dc_product, viewGroup, false));
    }



    static class DiscProductsViewHolder extends RecyclerView.ViewHolder {
        public CheckBox header;
        public DiscProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        ((DiscProductsViewHolder)holder).header.setText(productsList.get(i).getName());

        if (productsList.get(i).isChecked()) {
            ((DiscProductsViewHolder)holder).header.setChecked(true);
        } else {
            ((DiscProductsViewHolder)holder).header.setChecked(false);
        }
//        ((RoomRatesAdapter.DiscProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roomRateImpl.clicked(rateList.get(i));
//            }
//        });
//        ((RoomRatesAdapter.DiscProductsViewHolder)holder).name.setText(rateList.get(i).getRatePrice().getRoomRate().getRoomRate());
//        ((RoomRatesAdapter.DiscProductsViewHolder)holder).amount.setText(String.valueOf(rateList.get(i).getRatePrice().getAmount()));

    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
