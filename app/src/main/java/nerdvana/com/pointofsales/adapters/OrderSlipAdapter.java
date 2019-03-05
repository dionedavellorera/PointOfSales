package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.dialogs.OrderSlipDialog;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.model.OrderSlipModel;

public class OrderSlipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderSlipModel> orderSlipList;
    OrderSlipDialog.OrderSlip orderSlip;
    public OrderSlipAdapter(List<OrderSlipModel> orderSlipList, OrderSlipDialog.OrderSlip orderSlip) {
        this.orderSlipList = orderSlipList;
        this.orderSlip = orderSlip;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderSlipViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_order_list, viewGroup, false));
    }



    static class OrderSlipViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout rootView;
        public OrderSlipViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof OrderSlipAdapter.OrderSlipViewHolder){

            ((OrderSlipAdapter.OrderSlipViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                orderSlip.clicked(orderSlipList.get(i).getOrderSlipInfoList());
//                    paymentMethod.clicked(i);
                }
            });
            ((OrderSlipAdapter.OrderSlipViewHolder) holder).name.setText(orderSlipList.get(i).getOrderSlipId());
        }

    }

    @Override
    public int getItemCount() {
        return orderSlipList.size();
    }
}
