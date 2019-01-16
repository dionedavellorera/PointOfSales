package nerdvana.com.pointofsales.postlogin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.interfaces.ProductsContract;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductsModel> productsModelList;
    private ProductsContract productsContract;
    public ProductsAdapter(List<ProductsModel> productsModelList, ProductsContract productsContract) {
        this.productsModelList = productsModelList;
        this.productsContract = productsContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_products, viewGroup, false));
    }



    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private ImageView imageUrl;
        private CardView rootView;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imageUrl = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final ProductsModel productsModel = productsModelList.get(i);
        ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsContract.productClicked(i);
            }
        });
        ((ProductsViewHolder)holder).name.setText(productsModel.getName());
        ImageLoader.loadImage(productsModel.getImageUrls()[0], ((ProductsViewHolder)holder).imageUrl);
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
