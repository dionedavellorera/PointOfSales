package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;

public class ViewReceiptActualAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewReceiptActualModel> viewReceiptList;

    public ViewReceiptActualAdapter(List<ViewReceiptActualModel> viewReceiptList) {
        this.viewReceiptList = viewReceiptList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewReceiptActualAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_view_receipt, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView companyName;
        private TextView address;
        private TextView number;
        private TextView serial;
        private TextView vatReg;
        private TextView permit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyName);
            address = itemView.findViewById(R.id.companyAddress);
            number = itemView.findViewById(R.id.telNumber);
            serial = itemView.findViewById(R.id.serial);
            vatReg = itemView.findViewById(R.id.vatReg);
            permit = itemView.findViewById(R.id.permitNumber);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        ViewReceiptActualModel model = viewReceiptList.get(holder.getAdapterPosition());
        if(holder instanceof ViewReceiptActualAdapter.ViewHolder){
            ((ViewReceiptActualAdapter.ViewHolder) holder)
                    .companyName
                    .setText(model.getCompany());

            ((ViewHolder) holder)
                    .address
                    .setText(model.getAddress());

            ((ViewHolder) holder)
                    .number
                    .setText(model.getTelNumber());

            ((ViewHolder) holder)
                    .serial
                    .setText(model.getSerialNumber());

            ((ViewHolder) holder)
                    .vatReg
                    .setText(model.getRegTin());

            ((ViewHolder) holder)
                    .permit
                    .setText(model.getPermitNumber());
        }
    }

    @Override
    public int getItemCount() {
        return viewReceiptList.size();
    }
}
