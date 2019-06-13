package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.custom.CircularTextView;
import nerdvana.com.pointofsales.dialogs.DialogBundleComposition;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;

public class SelectedProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchProductsResponse.BranchGroup> branchGroupList;
    private DialogBundleComposition.Category category;
    private List<SelectedProductsInBundleModel.BundleProductModel> selectedProductsInBundleModels;
    private DialogBundleComposition.Minus minus;
    public SelectedProductsAdapter(
            List<SelectedProductsInBundleModel.BundleProductModel> selectedProductsInBundleModels,
            DialogBundleComposition.Minus minus) {
        this.selectedProductsInBundleModels = selectedProductsInBundleModels;
        this.minus = minus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SelectedProductsAdapter.ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_selected_product_from_bundle, viewGroup, false));
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView count;
        private CoordinatorLayout row;
        private ImageView minusQty;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            row = itemView.findViewById(R.id.row);
            count = itemView.findViewById(R.id.count);
            minusQty = itemView.findViewById(R.id.minusQty);

        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof SelectedProductsAdapter.ListViewHolder) {
            ((SelectedProductsAdapter.ListViewHolder) holder).name.setText(selectedProductsInBundleModels.get(i).getName());
            ((SelectedProductsAdapter.ListViewHolder) holder).count.setText(String.valueOf(selectedProductsInBundleModels.get(i).getQty()));
            ((ListViewHolder) holder).minusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minus.clicked(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return selectedProductsInBundleModels.size();
    }
}
