package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.dialogs.OrderSlipDialog;
import nerdvana.com.pointofsales.interfaces.VoidItemContract;
import nerdvana.com.pointofsales.model.ForVoidDiscountModel;
import nerdvana.com.pointofsales.model.OrderSlipModel;

public class VoidDiscountsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private VoidItemContract voidItemContract;
    private List<ForVoidDiscountModel> voidDiscountModelList;
    public VoidDiscountsAdapter(List<ForVoidDiscountModel> voidDiscountModelList, VoidItemContract voidItemContract) {
        this.voidDiscountModelList = voidDiscountModelList;
        this.voidItemContract = voidItemContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VoidDiscountsAdapter.VoidDiscountHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_void_discount, viewGroup, false));
    }



    static class VoidDiscountHolder extends RecyclerView.ViewHolder {
        private Button voidPayment;
        private TextView paymentType;
        private TextView paymentAmount;
        public VoidDiscountHolder(@NonNull View itemView) {
            super(itemView);
            paymentType = itemView.findViewById(R.id.paymentType);
            paymentAmount = itemView.findViewById(R.id.paymentAmount);
            voidPayment = itemView.findViewById(R.id.voidPayment);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof VoidDiscountsAdapter.VoidDiscountHolder){
            ((VoidDiscountHolder)holder).voidPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    voidItemContract.remove(voidDiscountModelList.get(i).getDiscountId(), voidDiscountModelList.get(i).getDiscountName(), voidDiscountModelList.get(i).getDiscountAmount(), i);
                }
            });

            ((VoidDiscountHolder)holder).paymentType.setText(voidDiscountModelList.get(i).getDiscountName());
            ((VoidDiscountHolder)holder).paymentAmount.setText(voidDiscountModelList.get(i).getDiscountAmount());
        }
    }

    @Override
    public int getItemCount() {
        return voidDiscountModelList.size();
    }
}
