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

            ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkoutItemsContract.itemSelected(cartItem, holder.getAdapterPosition());
                    notifyItemChanged(i);
                }
            });


//            if (productsModel.getProductStatus() != ProductConstants.PENDING &&
//                    productsModel.getProductStatus() != ProductConstants.VOID) {

//            }

//            ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    checkoutItemsContract.itemSelected(productsModel, holder.getAdapterPosition());
////                    notifyItemChanged(i);
//
//                }
//            });



            ((ProductsViewHolder)holder).rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    checkoutItemsContract.itemLongClicked(cartItem, holder.getAdapterPosition(), v);
                    return false;
                }
            });

            ((ProductsViewHolder)holder).name.setText(cartItem.getName());
            ((ProductsViewHolder)holder).quantity.setText(String.valueOf(cartItem.getQuantity())); //oki
//            if (!cartItem.isPosted()) {
//                ((ProductsViewHolder)holder).price.setText(String.valueOf(((cartItem.getAmount() * (cartItem.getMarkUp() + 1))) * cartItem.getQuantity()));
//
//            } else {
//            }

            ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice()));
            ((ProductsViewHolder)holder).totalPrice.setText(String.valueOf(cartItem.getUnitPrice() * cartItem.getQuantity()));


//            ((ProductsViewHolder)holder).name.setTextColor(Color.parseColor("#d3d3d3"));
//            ((ProductsViewHolder)holder).quantity.setTextColor(Color.parseColor("#d3d3d3"));
//            ((ProductsViewHolder)holder).price.setTextColor(Color.parseColor("#d3d3d3"));
            if (cartItem.isSelected()) {
                ((ProductsViewHolder) holder).rootView.setSelected(true);
//                ((ProductsViewHolder)holder).rootView.setBackgroundResource(R.color.highlight);
            } else {
                ((ProductsViewHolder) holder).rootView.setSelected(false);
//                ((ProductsViewHolder)holder).rootView.setBackgroundResource(R.color.navyblue);
            }
//
//            switch (productsModelList.get(holder.getAdapterPosition()).getProductStatus()) {
//                case ProductConstants.DISABLED: //will only be used on product listing
//                    break;
//                case ProductConstants.PAID: //will only be used for viewing purposess
//                    break;
//                case ProductConstants.PENDING: //on initial order, can remove without having to void
//                    break;
//                case ProductConstants.SAVED: //saved items that requires special permission to remove
//                    break;
//                case ProductConstants.VOID: //voided items for viewing only
//                    break;
//            }
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
