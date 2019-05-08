package nerdvana.com.pointofsales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.dialogs.RateDialog;
import nerdvana.com.pointofsales.interfaces.VoidItemContract;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class PostedPaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PostedPaymentsModel> postedPaymentList;
    private VoidItemContract voidItemContract;
    public PostedPaymentsAdapter(List<PostedPaymentsModel> postedPaymentList, VoidItemContract voidItemContract) {
        this.postedPaymentList = postedPaymentList;
        this.voidItemContract = voidItemContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostedPaymentsAdapter.PostedPaymentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_posted_payments, viewGroup, false));
    }



    static class PostedPaymentsViewHolder extends RecyclerView.ViewHolder {
        private TextView label;
        private TextView paymentType;
        private TextView paymentAmount;
        private LinearLayout rootView;
        private ImageView iconStatus;
        private Button voidPayment;
        public PostedPaymentsViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            paymentType = itemView.findViewById(R.id.paymentType);
            paymentAmount = itemView.findViewById(R.id.paymentAmount);
            rootView = itemView.findViewById(R.id.rootView);
            iconStatus = itemView.findViewById(R.id.iconStatus);
            voidPayment = itemView.findViewById(R.id.voidPayment);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if (postedPaymentList.get(i).isIs_posted()) {
            ((PostedPaymentsViewHolder)holder).iconStatus.setVisibility(View.VISIBLE);
            ((PostedPaymentsViewHolder)holder).voidPayment.setVisibility(View.VISIBLE);
        } else {
            ((PostedPaymentsViewHolder)holder).iconStatus.setVisibility(View.GONE);
            ((PostedPaymentsViewHolder)holder).voidPayment.setVisibility(View.GONE);
        }

        ((PostedPaymentsViewHolder)holder).voidPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voidItemContract.remove(postedPaymentList.get(i).getPaymentId(), postedPaymentList.get(i).getPayment_description(), postedPaymentList.get(i).getAmount(), i);
            }
        });

        if (postedPaymentList.get(i).isAdvance()) {
            ((PostedPaymentsViewHolder)holder).label.setVisibility(View.VISIBLE);
        } else {
            ((PostedPaymentsViewHolder)holder).label.setVisibility(View.GONE);
        }



        ((PostedPaymentsViewHolder)holder).paymentType.setText(postedPaymentList.get(i).getPayment_description());

        if (Double.valueOf(postedPaymentList.get(i).getCurrency_value()) != 1) {
            ((PostedPaymentsViewHolder)holder).paymentType.setText(postedPaymentList.get(i).getCurrency_id());
        }


        ((PostedPaymentsViewHolder)holder).paymentAmount.setText(postedPaymentList.get(i).getAmount());


    }


    @Override
    public int getItemCount() {
        return postedPaymentList.size();
    }
}
