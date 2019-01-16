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
import nerdvana.com.pointofsales.model.ProductsModel;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductsModel> productsModelList;
    public CheckoutAdapter(List<ProductsModel> productsModelList) {
        this.productsModelList = productsModelList;
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

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ProductsModel productsModel = productsModelList.get(i);
        if(holder instanceof CheckoutAdapter.ProductsViewHolder){

            ((ProductsViewHolder)holder).name.setText(productsModel.getShortName());
            ((ProductsViewHolder)holder).quantity.setText(String.valueOf(productsModel.getPrice()));
            ((ProductsViewHolder)holder).price.setText(productsModel.getPrice() + (productsModel.isVattable() ?" V" : " N"));

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
