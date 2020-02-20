package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.dialogs.DialogBundleComposition;

public class ListProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchProductsResponse.BranchList> branchLists;
    private DialogBundleComposition.Product product;
    public ListProductsAdapter(List<FetchProductsResponse.BranchList> branchLists, DialogBundleComposition.Product product) {
        this.branchLists = branchLists;
        this.product = product;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListProductsAdapter.ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_product_bundle_selection, viewGroup, false));
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView row;
        private ImageView imageView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            row = itemView.findViewById(R.id.row);
            imageView = itemView.findViewById(R.id.image);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ListProductsAdapter.ListViewHolder) {

            if (branchLists.get(i).getBranchProduct() != null) {
                if (branchLists.get(i).getPrice() != 0) {
                    ((ListProductsAdapter.ListViewHolder) holder).name.setText(String.format("%s (ADD %s) ", branchLists.get(i).getBranchProduct().getProduct(), String.valueOf(branchLists.get(i).getPrice())));
                } else {
                    ((ListProductsAdapter.ListViewHolder) holder).name.setText(branchLists.get(i).getBranchProduct().getProduct());
                }
            }



            ((ListProductsAdapter.ListViewHolder) holder).name.setTextColor(R.color.lightPrimaryFont);



            if (branchLists.get(i).getBranchProduct() != null) {
                ImageLoader.loadImage(SharedPreferenceManager.getString(null, ApplicationConstants.HOST) + "/uploads/company/product/" + branchLists.get(i).getBranchProduct().getImageFile(), ((ListViewHolder) holder).imageView);
            }

            ((ListProductsAdapter.ListViewHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (branchLists.get(i).getBranchProduct() != null) {
                        product.clicked(i);
                    }

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return branchLists.size();
    }
}
