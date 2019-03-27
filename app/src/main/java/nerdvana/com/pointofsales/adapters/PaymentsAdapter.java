package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;

public class PaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchPaymentResponse.Result> paymentList;
    private PaymentDialog.PaymentMethod paymentMethod;
    public PaymentsAdapter(List<FetchPaymentResponse.Result> paymentList, PaymentDialog.PaymentMethod paymentMethod) {
        this.paymentList = paymentList;
        this.paymentMethod = paymentMethod;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PaymentsAdapter.PaymentTypeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_payment_types, viewGroup, false));
    }



    static class PaymentTypeViewHolder extends RecyclerView.ViewHolder {
        private ImageView name;
        private TextView description;
        private LinearLayout rootView;
        public PaymentTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof PaymentsAdapter.PaymentTypeViewHolder){

            ((PaymentTypeViewHolder) holder).name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentMethod.clicked(i);
                }
            });

            ((PaymentTypeViewHolder) holder).description.setText(paymentList.get(i).getPaymentType());
//            holder.description.sette
            ImageLoader.loadImage("http://192.168.1.90/pos/uploads/icon/" +paymentList.get(i).getImage(), ((PaymentTypeViewHolder) holder).name);
        }
    }




    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
