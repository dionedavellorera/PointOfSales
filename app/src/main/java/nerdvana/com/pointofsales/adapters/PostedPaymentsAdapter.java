package nerdvana.com.pointofsales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.dialogs.RateDialog;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class PostedPaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PostedPaymentsModel> postedPaymentList;
    public PostedPaymentsAdapter(List<PostedPaymentsModel> postedPaymentList) {
        this.postedPaymentList = postedPaymentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostedPaymentsAdapter.PostedPaymentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_posted_payments, viewGroup, false));
    }



    static class PostedPaymentsViewHolder extends RecyclerView.ViewHolder {
        private TextView paymentType;
        private TextView paymentAmount;
        private LinearLayout rootView;
        public PostedPaymentsViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentType = itemView.findViewById(R.id.paymentType);
            paymentAmount = itemView.findViewById(R.id.paymentAmount);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        ((PostedPaymentsViewHolder)holder).paymentType.setText(postedPaymentList.get(i).getPayment_description());
        ((PostedPaymentsViewHolder)holder).paymentAmount.setText(postedPaymentList.get(i).getAmount());

    }


    @Override
    public int getItemCount() {
        return postedPaymentList.size();
    }
}
