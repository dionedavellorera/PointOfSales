package nerdvana.com.pointofsales.postlogin.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CartItemsModel> cartItemsList;
    private CheckoutItemsContract checkoutItemsContract;
    public CheckoutAdapter(List<CartItemsModel> cartItemsList, CheckoutItemsContract checkoutItemsContract) {
        this.cartItemsList = cartItemsList;
        this.checkoutItemsContract = checkoutItemsContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CheckoutAdapter.ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_checkout_items, viewGroup, false));
    }



    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView totalPrice;
        private ImageView iconStatus;
        private ConstraintLayout rootView;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            iconStatus = itemView.findViewById(R.id.iconStatus);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        final CartItemsModel cartItem = cartItemsList.get(holder.getAdapterPosition());

        if(holder instanceof CheckoutAdapter.ProductsViewHolder){
            if (!cartItem.isPosted()) {
                ((ProductsViewHolder) holder).iconStatus.setVisibility(View.GONE);
            } else {
                ((ProductsViewHolder) holder).iconStatus.setVisibility(View.VISIBLE);
            }





            ((ProductsViewHolder)holder).rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    checkoutItemsContract.itemLongClicked(cartItem, holder.getAdapterPosition(), v);
                    return false;
                }
            });

            ((ProductsViewHolder)holder).name.setText(cartItem.getName());
            ((ProductsViewHolder)holder).quantity.setText(String.valueOf(cartItem.getQuantity())); //oki

            ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice()));

            if (cartItem.getType().equalsIgnoreCase("ot")) {
                ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice() / cartItem.getQuantity()));
                ((ProductsViewHolder)holder).totalPrice.setText(String.valueOf(cartItem.getUnitPrice()));

            } else {

                ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkoutItemsContract.itemSelected(cartItem, holder.getAdapterPosition());
                        notifyItemChanged(i);
                    }
                });


                ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice()));
                ((ProductsViewHolder)holder).totalPrice.setText(String.valueOf(cartItem.getUnitPrice() * cartItem.getQuantity()));
            }



            if (cartItem.isSelected()) {
                ((ProductsViewHolder) holder).rootView.setSelected(true);
            } else {
                ((ProductsViewHolder) holder).rootView.setSelected(false);
            }
        }
    }


    public void addItems(List<CartItemsModel> cartItemsList) {
        this.cartItemsList = cartItemsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }


}
