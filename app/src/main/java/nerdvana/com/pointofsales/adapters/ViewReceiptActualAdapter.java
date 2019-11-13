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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Reprint;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_responses.ViewReceiptViaDateResponse;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;

public class ViewReceiptActualAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewReceiptActualModel> viewReceiptList;
    private Context context;
    private Reprint reprint;
    public ViewReceiptActualAdapter(List<ViewReceiptActualModel> viewReceiptList,
                                    Context context, Reprint reprint) {
        this.viewReceiptList = viewReceiptList;
        this.context = context;
        this.reprint = reprint;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewReceiptActualAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_view_receipt, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnReprint;
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
        private LinearLayout layoutDiscountsInner;
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
        private TextView soaRefValue;

        private TextView dateIssued;
        private TextView validUntil;


        private TextView customerName;
        private TextView customerAddress;
        private TextView customerTin;
        private TextView customerBusinessStyle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateIssued = itemView.findViewById(R.id.dateIssued);
            validUntil = itemView.findViewById(R.id.validUntil);
            btnReprint = itemView.findViewById(R.id.btnReprint);
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
            tweleVatValue = itemView.findViewById(R.id.tweleVatValue);
            vatExemptSaleValue = itemView.findViewById(R.id.vatExemptSalesValue);
            vatableSalesValue = itemView.findViewById(R.id.vatableSalesValue);
            itemCountValue = itemView.findViewById(R.id.itemCountValue);
            personCountValue = itemView.findViewById(R.id.personCountValue);
            subtotalValue = itemView.findViewById(R.id.subtotalValue);
            soaRefValue = itemView.findViewById(R.id.soaRefValue);

            customerName = itemView.findViewById(R.id.customerNameValue);
            customerAddress = itemView.findViewById(R.id.customerAddress);
            customerTin = itemView.findViewById(R.id.customerTinValue);
            customerBusinessStyle = itemView.findViewById(R.id.customerBusinesStyle);
            layoutDiscountsInner = itemView.findViewById(R.id.layoutDiscountsInner);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        final ViewReceiptActualModel model = viewReceiptList.get(holder.getAdapterPosition());
        if(holder instanceof ViewReceiptActualAdapter.ViewHolder){

            ((ViewHolder) holder)
                    .dateIssued
                    .setText("Date Issued : " + Utils.birDateTimeFormat(model.getCheckedOutAt()));

            ((ViewHolder) holder)
                    .validUntil
                    .setText("Valid Until : " + Utils.birDateTimeFormat(PrinterUtils.yearPlusFive(model.getCheckedOutAt())));



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
                    .soaRefValue
                    .setText(model.getSoaRefValue());

            ((ViewHolder) holder)
                    .btnReprint
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reprint.data(model.getControlNumber());
                        }
                    });

            ((ViewHolder) holder)
                    .layoutItemsInner.removeAllViews();

            for (ViewReceiptViaDateResponse.VRDiscounts disc : model.getVrDiscountsList()) {


                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);


                TextView discLabel = new TextView(context);
                discLabel.setText("DISCOUNT LIST");
                discLabel.setLayoutParams(llp);
                TextView discValue = new TextView(context);
                discValue.setText("");
                discValue.setLayoutParams(llp1);

                LinearLayout childLayout0 = new LinearLayout(
                        context);
                LinearLayout.LayoutParams linearParams0 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                childLayout0.setLayoutParams(linearParams0);

                childLayout0.addView(discLabel);
                childLayout0.addView(discValue);
                ((ViewHolder) holder)
                        .layoutDiscountsInner
                        .addView(childLayout0);





                TextView tvId = new TextView(context);
                tvId.setText(disc.getDiscountType() + " ID");
                tvId.setLayoutParams(llp);
                TextView tvCardNo = new TextView(context);
                tvCardNo.setGravity(Gravity.RIGHT);
                if (disc.getVrInfo() != null) {
                    if (disc.getVrInfo().getCardNo() != null) {
                        tvCardNo.setText(disc.getVrInfo().getCardNo().toUpperCase());
                    } else {
                        tvCardNo.setText("");
                    }
                } else {
                    tvCardNo.setText("");
                }
                tvCardNo.setLayoutParams(llp1);

                LinearLayout childLayout = new LinearLayout(
                        context);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                childLayout.setLayoutParams(linearParams);

                childLayout.addView(tvId);
                childLayout.addView(tvCardNo);
                ((ViewHolder) holder)
                        .layoutDiscountsInner
                        .addView(childLayout);


                TextView tvName = new TextView(context);
                tvName.setText("NAME");
                tvName.setLayoutParams(llp);
                TextView tvNameValue = new TextView(context);
                tvNameValue.setGravity(Gravity.RIGHT);
                tvNameValue.setText(disc.getVrInfo() != null ? disc.getVrInfo().getName().toUpperCase() : "");
                tvNameValue.setLayoutParams(llp1);


                LinearLayout childLayout1 = new LinearLayout(
                        context);
                LinearLayout.LayoutParams linearParams1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                childLayout1.setLayoutParams(linearParams1);

                childLayout1.addView(tvName);
                childLayout1.addView(tvNameValue);
                ((ViewHolder) holder)
                        .layoutDiscountsInner
                        .addView(childLayout1);


                TextView tvAddress = new TextView(context);
                tvAddress.setText("ADDRESS");
                tvAddress.setLayoutParams(llp);
                TextView tvAddressValue = new TextView(context);
                tvAddressValue.setGravity(Gravity.RIGHT);
                if (disc.getVrInfo() != null) {
                    if (disc.getVrInfo().getAddress() != null) {
                        tvAddressValue.setText(disc.getVrInfo().getAddress().toUpperCase());
                    } else {
                        tvAddressValue.setText("");
                    }
                }
//                tvAddressValue.setText(disc.getVrInfo() != null ? disc.getVrInfo().getAddress().toUpperCase() : "");
                tvAddressValue.setLayoutParams(llp1);


                LinearLayout childLayout2 = new LinearLayout(
                        context);
                LinearLayout.LayoutParams linearParams2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                childLayout2.setLayoutParams(linearParams2);

                childLayout2.addView(tvAddress);
                childLayout2.addView(tvAddressValue);
                ((ViewHolder) holder)
                        .layoutDiscountsInner
                        .addView(childLayout2);

                TextView tvSignature = new TextView(context);
                tvSignature.setText("SIGNATURE");
                tvSignature.setLayoutParams(llp);
                TextView tvSigValue = new TextView(context);
                tvSigValue.setText("");
                tvSigValue.setLayoutParams(llp1);


                LinearLayout childLayout3 = new LinearLayout(
                        context);
                LinearLayout.LayoutParams linearParams3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                childLayout3.setLayoutParams(linearParams3);
                childLayout3.addView(tvSignature);
                childLayout3.addView(tvSigValue);
                ((ViewHolder) holder)
                        .layoutDiscountsInner
                        .addView(childLayout3);





            }

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
                tvAmount.setGravity(Gravity.RIGHT);
                tvAmount.setText(PrinterUtils.returnWithTwoDecimal(String.valueOf(data.getPrice())));
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
            tvAmount.setGravity(Gravity.RIGHT);
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
