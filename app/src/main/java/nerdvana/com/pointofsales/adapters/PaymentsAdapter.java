package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;

public class PaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchPaymentResponse.Result> paymentList;

    public PaymentsAdapter(List<FetchPaymentResponse.Result> paymentList) {
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PaymentsAdapter.PaymentTypeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_payment_types, viewGroup, false));
    }



    static class PaymentTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public PaymentTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof PaymentsAdapter.PaymentTypeViewHolder){

            ((PaymentTypeViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected()) {
                        v.setSelected(true);
                    } else {
                        v.setSelected(false);
                    }

                }
            });

            ((PaymentTypeViewHolder) holder).name.setText(paymentList.get(i).getPaymentType());
        }
    }




    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
