package nerdvana.com.pointofsales.postlogin.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.ProductsModel;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductsModel> productsModelList;
    private CheckoutItemsContract checkoutItemsContract;
    public CheckoutAdapter(List<ProductsModel> productsModelList, CheckoutItemsContract checkoutItemsContract) {
        this.productsModelList = productsModelList;
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
        private ConstraintLayout rootView;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final ProductsModel productsModel = productsModelList.get(i);

        if(holder instanceof CheckoutAdapter.ProductsViewHolder){
            ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkoutItemsContract.itemSelected(productsModel, i);
                }
            });

            ((ProductsViewHolder)holder).rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    checkoutItemsContract.itemLongClicked(productsModel, i, v);
                    return false;
                }
            });

            ((ProductsViewHolder)holder).name.setText(productsModel.getShortName());
            ((ProductsViewHolder)holder).quantity.setText(String.valueOf(productsModel.getPrice()));
            ((ProductsViewHolder)holder).price.setText(productsModel.getPrice() + (productsModel.isVattable() ?" V" : " N"));

            if (productsModel.isSelected()) {
                ((ProductsViewHolder)holder).rootView.setBackgroundResource(R.color.colorAccent);
            } else {
                ((ProductsViewHolder)holder).rootView.setBackgroundResource(R.color.colorWhite);
            }
        }
    }


    public void addItems(List<ProductsModel> productsModelList) {
        this.productsModelList = productsModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productsModelList.size();
    }
}
