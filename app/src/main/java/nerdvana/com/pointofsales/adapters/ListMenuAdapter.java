package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
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
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.dialogs.DialogBundleComposition;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public class ListMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchProductsResponse.BranchGroup> branchGroupList;
    private DialogBundleComposition.Category category;
    public ListMenuAdapter(List<FetchProductsResponse.BranchGroup> branchGroupList, DialogBundleComposition.Category category) {
        this.branchGroupList = branchGroupList;
        this.category = category;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_product_bundle_menu, viewGroup, false));
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private CircularTextView name;
        private RelativeLayout row;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            row = itemView.findViewById(R.id.row);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof ListMenuAdapter.ListViewHolder) {
            ((ListViewHolder) holder).name.setText(branchGroupList.get(i).getGroupName());
            ((ListViewHolder) holder).name.setTextColor(R.color.lightPrimaryFont);
            ((ListViewHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category.clicked(i);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return branchGroupList.size();
    }
}
