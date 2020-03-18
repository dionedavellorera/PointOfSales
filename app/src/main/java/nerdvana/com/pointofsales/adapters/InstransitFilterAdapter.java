package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.interfaces.IntransitFilterContract;
import nerdvana.com.pointofsales.model.AvailableGcModel;
import nerdvana.com.pointofsales.model.IntransitFilterModel;

public class InstransitFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IntransitFilterModel> intransitFilterModelList;
    private IntransitFilterContract intransitFilterContract;
    public InstransitFilterAdapter(List<IntransitFilterModel> intransitFilterModelList,
                                   IntransitFilterContract intransitFilterContract) {
        this.intransitFilterModelList = intransitFilterModelList;
        this.intransitFilterContract = intransitFilterContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new InstransitFilterAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_intransit_filter, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnAction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnAction = itemView.findViewById(R.id.btnAction);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof InstransitFilterAdapter.ViewHolder){
            IntransitFilterModel model = intransitFilterModelList.get(holder.getAdapterPosition());
            ((InstransitFilterAdapter.ViewHolder) holder).btnAction.setText(model.getText().toUpperCase());
            ((InstransitFilterAdapter.ViewHolder) holder).btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intransitFilterContract.clicked(model);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return intransitFilterModelList.size();
    }


}
