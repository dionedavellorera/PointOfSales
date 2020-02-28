package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.PosMachinesResponse;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.interfaces.PosListContract;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public class PosSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PosMachinesResponse.Result> posList;
    private PosListContract posListContract;

    public PosSelectionAdapter(List<PosMachinesResponse.Result> posList, PosListContract posListContract) {
        this.posList = posList;
        this.posListContract = posListContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PosSelectionAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pos_machines, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView machineNumber;
        private LinearLayout row;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            machineNumber = itemView.findViewById(R.id.machineNumber);
            row = itemView.findViewById(R.id.row);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        PosMachinesResponse.Result r = posList.get(holder.getAdapterPosition());
        if(holder instanceof PosSelectionAdapter.ViewHolder){

            ((ViewHolder) holder).machineNumber.setText(String.format("MACHINE NO: %s\nPERMIT NO:%s\nPRODUCT KEY: %s\nSERIAL: %s\nMODEL: %s\nANDROID ID:%s\nManufacturer:%s\nBOARD:%s",
                    r.getId(), r.getPermit_nos(), r.getProduct_key(), r.getDevice().getSerial(), r.getDevice().getModel(), r.getDevice().getAndroid_id(), r.getDevice().getManufacturer(), r.getDevice().getBoard()));
            ((ViewHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    posListContract.clicked(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return posList.size();
    }


}
