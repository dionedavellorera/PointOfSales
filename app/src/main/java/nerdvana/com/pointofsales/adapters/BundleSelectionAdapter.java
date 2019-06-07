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
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomRatePriceIdResponse;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.dialogs.RoomBundleSelectionDialog;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public class BundleSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchRoomRatePriceIdResponse.ProductBundle> result;
    private RoomBundleSelectionDialog.BundleProductSelection bundleProductSelection;
    public BundleSelectionAdapter(List<FetchRoomRatePriceIdResponse.ProductBundle> result, RoomBundleSelectionDialog.BundleProductSelection bundleProductSelection) {
        this.result = result;
        this.bundleProductSelection = bundleProductSelection;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BundleSelectionAdapter.ListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_bundle_selection, viewGroup, false));
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
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
        if(holder instanceof BundleSelectionAdapter.ListViewHolder){
            ((BundleSelectionAdapter.ListViewHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundleProductSelection.selected(i);
                }
            });
            ((BundleSelectionAdapter.ListViewHolder) holder).name.setText(result.get(i).getProduct().getProduct());
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

}
