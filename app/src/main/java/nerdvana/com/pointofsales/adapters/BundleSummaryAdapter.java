package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;

public class BundleSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SelectedProductsInBundleModel> sipm;
    private Context context;
    public BundleSummaryAdapter(List<SelectedProductsInBundleModel> sipm, Context context) {
        this.sipm = sipm;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BundleSummaryAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_summary, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private LinearLayout linearContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            linearContainer = itemView.findViewById(R.id.linearContainer);
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof BundleSummaryAdapter.ViewHolder){
            SelectedProductsInBundleModel model = sipm.get(i);
            ((BundleSummaryAdapter.ViewHolder) holder).name.setText(model.getGroupName());
            ((ViewHolder) holder).linearContainer.removeAllViews();
            for (SelectedProductsInBundleModel.BundleProductModel bpm : model.getBundleProductModelList()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 5, 5, 5);
                TextView tv = new TextView(context);
                tv.setText(String.format("%d - %s", bpm.getQty(), bpm.getName()));
                tv.setLayoutParams(params);
                ((ViewHolder) holder).linearContainer.addView(tv);
            }
        }
    }

    @Override
    public int getItemCount() {
        return sipm.size();
    }
}
