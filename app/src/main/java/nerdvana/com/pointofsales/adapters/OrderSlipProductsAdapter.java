package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.OrderSlipModel;

public class OrderSlipProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderSlipModel.OrderSlipProduct> orderSlipProduct;
    public OrderSlipProductsAdapter(List<OrderSlipModel.OrderSlipProduct> orderSlipProduct) {
        this.orderSlipProduct = orderSlipProduct;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderSlipProductViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_checkout_items, viewGroup, false));
    }



    static class OrderSlipProductViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView totalPrice;
        private ConstraintLayout rootView;

        public OrderSlipProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof OrderSlipProductsAdapter.OrderSlipProductViewHolder){
            if (TextUtils.isEmpty(orderSlipProduct.get(i).getProductName())) {
                ((OrderSlipProductsAdapter.OrderSlipProductViewHolder)holder).name.setText(orderSlipProduct.get(i).getRoomType());
            } else {
                ((OrderSlipProductsAdapter.OrderSlipProductViewHolder)holder).name.setText(orderSlipProduct.get(i).getProductName());
            }

            ((OrderSlipProductsAdapter.OrderSlipProductViewHolder)holder).quantity.setText(String.valueOf(orderSlipProduct.get(i).getQuantity())); //oki
            ((OrderSlipProductsAdapter.OrderSlipProductViewHolder)holder).price.setText(String.valueOf(orderSlipProduct.get(i).getUnitCost()));
            ((OrderSlipProductsAdapter.OrderSlipProductViewHolder)holder).totalPrice.setText(String.valueOf(Double.valueOf(orderSlipProduct.get(i).getUnitCost()) * Double.valueOf(orderSlipProduct.get(i).getQuantity())));

        }
    }

    @Override
    public int getItemCount() {
        return orderSlipProduct.size();
    }
}
