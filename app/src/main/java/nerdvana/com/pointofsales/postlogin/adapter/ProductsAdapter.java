package nerdvana.com.pointofsales.postlogin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.interfaces.ProductsContract;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<ProductsModel> productsModelList;
    private List<ProductsModel> productsFilteredList;
    private ProductsContract productsContract;
    public ProductsAdapter(List<ProductsModel> productsModelList, ProductsContract productsContract) {
        this.productsModelList = new ArrayList<>(productsModelList);

        this.productsContract = productsContract;
        productsFilteredList = new ArrayList<>(productsModelList);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_products, viewGroup, false));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSting = constraint.toString();
                productsFilteredList = new ArrayList<>();
                if (charSting.isEmpty()) {
                    productsFilteredList = productsModelList;
                } else {
                    List<ProductsModel> filteredList = new ArrayList<>();
                    for (ProductsModel pm : productsModelList) {
                        if (pm.getName().toLowerCase().contains(charSting.toLowerCase()) ||
                            String.valueOf(pm.getPrice()).contains(charSting.toLowerCase()) ||
                            pm.getDepartment().toLowerCase().contains(charSting.toLowerCase())) {
                            filteredList.add(pm);
                        }
                    }
                    productsFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productsFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productsFilteredList = (ArrayList<ProductsModel>) results.values;
                notifyDataSetChanged();
            }
        };
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
        final ProductsModel productsModel = productsFilteredList.get(i);
        ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsContract.productClicked(productsModel);
            }
        });
        ((ProductsViewHolder)holder).name.setText(productsModel.getName());
        ((ProductsViewHolder)holder).price.setText(String.valueOf(productsModel.getPrice()));
        ImageLoader.loadImage(productsModel.getImageUrls()[0], ((ProductsViewHolder)holder).imageUrl);
    }


    public void addItems(List<ProductsModel> productsModelList) {
        this.productsModelList = new ArrayList<>(productsModelList);
        this.productsFilteredList = productsModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productsFilteredList.size();
    }


}
