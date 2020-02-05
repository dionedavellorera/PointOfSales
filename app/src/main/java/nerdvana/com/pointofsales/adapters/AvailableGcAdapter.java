package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public class AvailableGcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AvailableGcModel> approveList;
    private AvailableGcDialog.Item item;
    public AvailableGcAdapter(List<AvailableGcModel> approveList, AvailableGcDialog.Item item) {
        this.approveList = approveList;
        this.item = item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AvailableGcAdapter.ApproveListGcViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_available_gc, viewGroup, false));
    }

    static class ApproveListGcViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView remove;
        public ApproveListGcViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            remove = itemView.findViewById(R.id.remove);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof AvailableGcAdapter.ApproveListGcViewHolder){
            ((ApproveListGcViewHolder) holder).remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.remove(i);
                }
            });
            ((AvailableGcAdapter.ApproveListGcViewHolder) holder).name.setText("GC# "+ approveList.get(i).getSeriesNumber() + " - " + "Amount:" + String.valueOf(approveList.get(i).getAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return approveList.size();
    }
}
