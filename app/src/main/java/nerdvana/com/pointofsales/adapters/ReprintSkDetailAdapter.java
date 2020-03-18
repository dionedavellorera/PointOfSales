package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.model.SkListModel;

public class ReprintSkDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CollectionFinalPostModel> cfpm;
    public ReprintSkDetailAdapter(List<CollectionFinalPostModel> cfpm) {
        this.cfpm = cfpm;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReprintSkDetailAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_safekeepings_detail, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;
        private TextView tvValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvValue = itemView.findViewById(R.id.tvValue);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ReprintSkDetailAdapter.ViewHolder){
            CollectionFinalPostModel data = cfpm.get(holder.getAdapterPosition());

            ((ViewHolder) holder).tvDescription.setText(data.getCash_valued());
            ((ViewHolder) holder).tvValue.setText(data.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return cfpm.size();
    }

}
