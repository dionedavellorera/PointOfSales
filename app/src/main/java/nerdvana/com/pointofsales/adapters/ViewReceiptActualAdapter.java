package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.ViewReceiptViaDateResponse;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;

public class ViewReceiptActualAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewReceiptActualModel> viewReceiptList;
    private Context context;
    public ViewReceiptActualAdapter(List<ViewReceiptActualModel> viewReceiptList, Context context) {
        this.viewReceiptList = viewReceiptList;
        this.context = context;
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
        private TextView minNumber;
        private TextView roomNumber;
        private TextView cashierValue;
        private TextView roomboyValue;
        private TextView checkinValue;
        private TextView checkoutValue;
        private TextView machineNumberValue;
        private TextView receiptNumberValue;
        private LinearLayout layoutItemsInner;
        private TextView vatExemptValue;
        private TextView discountValue;
        private TextView advanceDepoValue;
        private TextView amountdueValue;
        private TextView tenderedValue;
        private TextView changeValue;
        private TextView tweleVatValue;
        private TextView vatableSalesValue;
        private TextView vatExemptSaleValue;
        private TextView itemCountValue;
        private TextView personCountValue;
        private TextView subtotalValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyName);
            address = itemView.findViewById(R.id.companyAddress);
            number = itemView.findViewById(R.id.telNumber);
            serial = itemView.findViewById(R.id.serial);
            vatReg = itemView.findViewById(R.id.vatReg);
            permit = itemView.findViewById(R.id.permitNumber);
            minNumber = itemView.findViewById(R.id.minNumber);
            roomNumber = itemView.findViewById(R.id.roomNumber);
            cashierValue = itemView.findViewById(R.id.cashierValue);
            roomboyValue = itemView.findViewById(R.id.roomBoyValue);
            checkinValue = itemView.findViewById(R.id.checkinValue);
            checkoutValue = itemView.findViewById(R.id.checkoutValue);
            machineNumberValue = itemView.findViewById(R.id.machineNumberValue);
            receiptNumberValue = itemView.findViewById(R.id.receiptNumberValue);
            layoutItemsInner = itemView.findViewById(R.id.layoutItemsInner);
            vatExemptValue = itemView.findViewById(R.id.vatExemptValue);
            discountValue = itemView.findViewById(R.id.discountValue);
            advanceDepoValue = itemView.findViewById(R.id.advanceDepoValue);
            amountdueValue = itemView.findViewById(R.id.amountDueValue);
            tenderedValue = itemView.findViewById(R.id.tenderedValue);
            changeValue = itemView.findViewById(R.id.changeValue);
            changeValue = itemView.findViewById(R.id.changeValue);
            changeValue = itemView.findViewById(R.id.changeValue);
            tweleVatValue = itemView.findViewById(R.id.tweleVatValue);
            vatExemptSaleValue = itemView.findViewById(R.id.vatExemptSalesValue);
            vatableSalesValue = itemView.findViewById(R.id.vatableSalesValue);
            itemCountValue = itemView.findViewById(R.id.itemCountValue);
            personCountValue = itemView.findViewById(R.id.personCountValue);
            subtotalValue = itemView.findViewById(R.id.subtotalValue);
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

            ((ViewHolder) holder)
                    .minNumber
                    .setText(model.getMinNumber());

            ((ViewHolder) holder)
                    .roomNumber
                    .setText(model.getRoomNumber());

            ((ViewHolder) holder)
                    .cashierValue
                    .setText(model.getCashier());

            ((ViewHolder) holder)
                    .roomboyValue
                    .setText(model.getRoomBoy());

            ((ViewHolder) holder)
                    .checkinValue
                    .setText(model.getCheckInTime());

            ((ViewHolder) holder)
                    .checkoutValue
                    .setText(model.getExpectedCheckOut());

            ((ViewHolder) holder)
                    .receiptNumberValue
                    .setText(model.getReceiptNumber());

            ((ViewHolder) holder)
                    .machineNumberValue
                    .setText(model.getMachineNumber());


            ((ViewHolder) holder)
                    .layoutItemsInner.removeAllViews();

            for (ViewReceiptViaDateResponse.Post_ data : model.getPostList()) {

                LinearLayout childLayout = new LinearLayout(
                        context);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                childLayout.setLayoutParams(linearParams);

                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f);
                LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f);
                LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f);
                TextView tvQty = new TextView(context);
                tvQty.setText(String.valueOf(data.getQty()));
                tvQty.setLayoutParams(llp);
                TextView tvItem = new TextView(context);
                tvItem.setText(data.getProduct() == null ? data.getRoomRate() : data.getProduct().getProduct_initial());
                tvItem.setLayoutParams(llp1);
                TextView tvAmount = new TextView(context);
                tvAmount.setText(String.valueOf(data.getTotal()));
                tvAmount.setLayoutParams(llp2);


                childLayout.addView(tvQty);
                childLayout.addView(tvItem);
                childLayout.addView(tvAmount);
                ((ViewHolder) holder)
                        .layoutItemsInner
                        .addView(childLayout);

            }


            LinearLayout childLayout = new LinearLayout(
                    context);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayout.setLayoutParams(linearParams);

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f);
            LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f);
            LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f);
            TextView tvQty = new TextView(context);
            tvQty.setText(String.valueOf(model.getOtHours()));
            tvQty.setLayoutParams(llp);
            TextView tvItem = new TextView(context);
            tvItem.setText("OT HOURS");
            tvItem.setLayoutParams(llp1);
            TextView tvAmount = new TextView(context);
            tvAmount.setText(String.valueOf(model.getOtAmount()));
            tvAmount.setLayoutParams(llp2);


            childLayout.addView(tvQty);
            childLayout.addView(tvItem);
            childLayout.addView(tvAmount);
            ((ViewHolder) holder)
                    .layoutItemsInner
                    .addView(childLayout);






            ((ViewHolder) holder)
                    .vatExemptValue
                    .setText(model.getVatExempt());

            ((ViewHolder) holder)
                    .discountValue
                    .setText(model.getDiscount());

            ((ViewHolder) holder)
                    .advanceDepoValue
                    .setText(model.getAdvanceDepo());

            ((ViewHolder) holder)
                    .amountdueValue
                    .setText(model.getAmountDue());

            ((ViewHolder) holder)
                    .tenderedValue
                    .setText(model.getTendered());

            ((ViewHolder) holder)
                    .changeValue
                    .setText(model.getChange());

            ((ViewHolder) holder)
                    .vatableSalesValue
                    .setText(model.getVatableSales());

            ((ViewHolder) holder)
                    .vatExemptSaleValue
                    .setText(model.getVatExemptsales());
            ((ViewHolder) holder)
                    .tweleVatValue
                    .setText(model.getTwelveVat());

            ((ViewHolder) holder)
                    .personCountValue
                    .setText(model.getPersonCountValue());

            ((ViewHolder) holder)
                    .itemCountValue
                    .setText(model.getItemCountValue());

            ((ViewHolder) holder)
                    .subtotalValue
                    .setText(model.getSubTotalValue());
        }
    }

    @Override
    public int getItemCount() {
        return viewReceiptList.size();
    }





}
