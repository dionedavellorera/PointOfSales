package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.interfaces.SafeKeepingContract;
import nerdvana.com.pointofsales.model.AvailableGcModel;
import nerdvana.com.pointofsales.model.SkListModel;

public class ReprintSkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SkListModel> skList;
    private Context context;
    private SafeKeepingContract safeKeepingContract;
    public ReprintSkAdapter(List<SkListModel> skList, Context context,
                            SafeKeepingContract safeKeepingContract) {
        this.skList = skList;
        this.context = context;
        this.safeKeepingContract = safeKeepingContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReprintSkAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_safekeepings, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSkCount;
        private TextView tvSkTotal;
        private RecyclerView rvSkDetailedData;
        private Button btnReprintSk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSkCount = itemView.findViewById(R.id.tvSkCount);
            tvSkTotal = itemView.findViewById(R.id.tvSkTotal);
            rvSkDetailedData = itemView.findViewById(R.id.rvSkDetailedData);
            btnReprintSk = itemView.findViewById(R.id.btnReprintSk);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ReprintSkAdapter.ViewHolder){

            SkListModel model = skList.get(holder.getAdapterPosition());

            ReprintSkDetailAdapter reprintSkDetailAdapter = new ReprintSkDetailAdapter(skList.get(holder.getAdapterPosition()).getCfpmList());
            ((ViewHolder) holder).rvSkDetailedData.setAdapter(reprintSkDetailAdapter);
            ((ViewHolder) holder).rvSkDetailedData.setLayoutManager(new LinearLayoutManager(context));
            reprintSkDetailAdapter.notifyDataSetChanged();
            ((ViewHolder) holder).tvSkCount.setText(model.getSkCount());
            ((ViewHolder) holder).tvSkTotal.setText(String.valueOf(model.getTotalSk()));

            ((ViewHolder) holder).btnReprintSk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    safeKeepingContract.reprintSk(model.getCfpmList());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return skList.size();
    }

}
