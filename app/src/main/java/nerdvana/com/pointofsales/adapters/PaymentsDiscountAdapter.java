package nerdvana.com.pointofsales.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.PaymentsDiscountsModel;

public class PaymentsDiscountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context c;
    public static final int TYPE_DISC = 0;
    public static final int TYPE_DEPOSIT = 1;
    List<PaymentsDiscountsModel> paymentsDiscountsModelList;


    public class AdvanceViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public AdvanceViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.name);
        }
    }

    public class DiscountViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public DiscountViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.name);
        }
    }

    public PaymentsDiscountAdapter(List<PaymentsDiscountsModel> paymentsDiscountsModelList, Context ctx) {
        c = ctx;
        this.paymentsDiscountsModelList = paymentsDiscountsModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        c = parent.getContext();
        if (viewType == TYPE_DISC) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_discount, parent, false);
            return new PaymentsDiscountAdapter.DiscountViewHolder(itemView);
        } else if (viewType == TYPE_DEPOSIT) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_payment, parent, false);
            return new PaymentsDiscountAdapter.AdvanceViewHolder(itemView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (paymentsDiscountsModelList.get(position).getViewType() == TYPE_DISC) {
            type = TYPE_DISC;
        } else {
            type = TYPE_DEPOSIT;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof PaymentsDiscountAdapter.DiscountViewHolder){
//            ((HeaderViewHolder) holder).header.setText(reviewsModelList.get(position).getReview());
        }else if(holder instanceof PaymentsDiscountAdapter.AdvanceViewHolder){
//            ((ContentViewHolder) holder).p1.setText(reviewsModelList.get(position).getName());
        }
    }



    @Override
    public int getItemCount() {
        return paymentsDiscountsModelList.size();
    }


}
