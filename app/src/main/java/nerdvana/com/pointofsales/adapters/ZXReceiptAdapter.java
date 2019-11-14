package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.dialogs.ZXActualDialog;
import nerdvana.com.pointofsales.dialogs.ZXReadModel;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;

public class ZXReceiptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String from;
    private List<ZXReadModel> viewReceiptList;
    private Context context;
    private ZXActualDialog.ZXRead zxRead;
    public ZXReceiptAdapter(List<ZXReadModel> viewReceiptList, Context context,
                            String from, ZXActualDialog.ZXRead zxRead) {
        this.viewReceiptList = viewReceiptList;
        this.context = context;
        this.from = from;
        this.zxRead = zxRead;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ZXReceiptAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_zx_receipt, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView companyName;
        private TextView address;
        private TextView number;
        private TextView serial;
        private TextView vatReg;
        private TextView permit;
        private TextView minNumber;
        private TextView title;
        private TextView userValue;
        private TextView managerValue;

        private TextView machineNumberValue;
        private TextView grossSalesValue;
        private TextView netSalesValue;
        private TextView vatableSalesValue;
        private TextView vatExemptSalesValue;
        private TextView vatExemptValue;
        private TextView twelveVatValue;
        private TextView nonVatValue;
        private TextView serviceChargeValue;
        private TextView cashSalesValue;
        private TextView cardSalesValue;
        private TextView depositSalesValue;
        private TextView onlineValue;
        private TextView depositAdjustmentValue;
        private TextView pwdValue;
        private TextView pwdCount;
        private TextView seniorValue;
        private TextView seniorCount;
        private TextView othersValue;
        private TextView begTrans;
        private TextView endTrans;
        private TextView newGrandTotal;
        private TextView oldGrandTotal;
        private TextView zReadNo;
        private TextView voidValue;
        private TextView begSoaNo;
        private TextView endSoaNo;
        private TextView begVoidNo;
        private TextView endVoidNo;

        private LinearLayout linZReadOnly;

        private Button btnReprint;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyName);
            address = itemView.findViewById(R.id.companyAddress);
            number = itemView.findViewById(R.id.telNumber);
            serial = itemView.findViewById(R.id.serial);
            vatReg = itemView.findViewById(R.id.vatReg);
            permit = itemView.findViewById(R.id.permitNumber);
            minNumber = itemView.findViewById(R.id.minNumber);
            title = itemView.findViewById(R.id.title);
            userValue = itemView.findViewById(R.id.userValue);
            managerValue = itemView.findViewById(R.id.managerValue);
            vatExemptValue = itemView.findViewById(R.id.vatExemptValue);


            machineNumberValue = itemView.findViewById(R.id.machineNumberValue);
            grossSalesValue = itemView.findViewById(R.id.grossSalesValue);
            netSalesValue = itemView.findViewById(R.id.netSalesValue);
            vatableSalesValue = itemView.findViewById(R.id.vatableSalesValue);
            vatExemptSalesValue = itemView.findViewById(R.id.vatExemptSalesValue);
            twelveVatValue = itemView.findViewById(R.id.twelveVatValue);
            nonVatValue = itemView.findViewById(R.id.nonVatValue);
            serviceChargeValue = itemView.findViewById(R.id.serviceChargeValue);
            cashSalesValue = itemView.findViewById(R.id.cashSalesValue);
            cardSalesValue = itemView.findViewById(R.id.cardSalesValue);
            depositSalesValue = itemView.findViewById(R.id.depositSalesValue);
            onlineValue = itemView.findViewById(R.id.onlineValue);
            depositAdjustmentValue = itemView.findViewById(R.id.depositAdjustmentvalue);
            pwdValue = itemView.findViewById(R.id.pwdValue);
            pwdCount = itemView.findViewById(R.id.pwdCount);
            seniorValue = itemView.findViewById(R.id.seniorValue);
            seniorCount = itemView.findViewById(R.id.seniorCount);
            othersValue = itemView.findViewById(R.id.othersValue);
            voidValue = itemView.findViewById(R.id.voidValue);

            begTrans = itemView.findViewById(R.id.begTrans);
            endTrans = itemView.findViewById(R.id.endTrans);
            newGrandTotal = itemView.findViewById(R.id.newGrandTotal);
            oldGrandTotal = itemView.findViewById(R.id.oldGrandTotal);
            zReadNo = itemView.findViewById(R.id.zReadNo);

            linZReadOnly = itemView.findViewById(R.id.linZReadOnly);
            btnReprint = itemView.findViewById(R.id.btnReprint);

            begSoaNo = itemView.findViewById(R.id.begSoaNo);
            endSoaNo = itemView.findViewById(R.id.endSoaNo);
            begVoidNo = itemView.findViewById(R.id.begVoidNo);
            endVoidNo = itemView.findViewById(R.id.endVoidNo);


        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        final ZXReadModel model = viewReceiptList.get(holder.getAdapterPosition());
        if(holder instanceof ZXReceiptAdapter.ViewHolder){

            ((ViewHolder) holder)
                    .btnReprint
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            zxRead.reprint(model.getId());
                        }
                    });


            ((ViewHolder) holder)
                    .voidValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getVoidAmount()));
            ((ViewHolder) holder)
                    .companyName
                    .setText(model.getCompany());
            ((ViewHolder) holder)
                    .vatExemptValue
                    .setText(model.getNonVat());
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
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getRegTin()));
            ((ViewHolder) holder)
                    .permit
                    .setText(model.getPermitNumber());
            ((ViewHolder) holder)
                    .minNumber
                    .setText(model.getMinNumber());

            ((ViewHolder) holder)
                    .title
                    .setText(model.getTitle() + "\n" + model.getPostingDate() + "\n" + model.getShiftNumber());

            ((ViewHolder) holder)
                    .userValue
                    .setText(model.getUser());
            ((ViewHolder) holder)
                    .managerValue
                    .setText(model.getManager());

            ((ViewHolder) holder)
                    .machineNumberValue
                    .setText(model.getMachineNumber());
            ((ViewHolder) holder)
                    .grossSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getGrossSales()));
            ((ViewHolder) holder)
                    .netSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getNetSales()));
            ((ViewHolder) holder)
                    .vatableSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getVatableSales()));
            ((ViewHolder) holder)
                    .vatExemptSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getVatExemptSales()));
            ((ViewHolder) holder)
                    .twelveVatValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getTwelveVat()));
            ((ViewHolder) holder)
                    .nonVatValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getNonVat()));
            ((ViewHolder) holder)
                    .serviceChargeValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getServiceCharge()));
            ((ViewHolder) holder)
                    .cashSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getCashSales()));
            ((ViewHolder) holder)
                    .cardSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getCardSales()));
            ((ViewHolder) holder)
                    .depositSalesValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getDepositSales()));
            ((ViewHolder) holder)
                    .onlineValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getOnline()));
            ((ViewHolder) holder)
                    .depositAdjustmentValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getDepositAdjustment()));
            ((ViewHolder) holder)
                    .pwdValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getPwdAmount()));
            ((ViewHolder) holder)
                    .pwdCount
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getPwdCount()));
            ((ViewHolder) holder)
                    .seniorValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getSeniorAmount()));
            ((ViewHolder) holder)
                    .seniorCount
                    .setText(model.getSeniorCount());
            ((ViewHolder) holder)
                    .othersValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getOthers()));
            ((ViewHolder) holder)
                    .depositAdjustmentValue
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getDepositAdjustment()));

            ((ViewHolder) holder)
                    .begTrans
                    .setText(model.getBeginningTrans());
            ((ViewHolder) holder)
                    .endTrans
                    .setText(model.getEndTrans());
            ((ViewHolder) holder)
                    .newGrandTotal
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getNewGrandTotal()));
            ((ViewHolder) holder)
                    .oldGrandTotal
                    .setText(PrinterUtils.returnWithTwoDecimal(model.getOldGrandTotal()));
            ((ViewHolder) holder)
                    .zReadNo
                    .setText(model.getzReadNo());

            ((ViewHolder) holder)
                    .begSoaNo
                    .setText(model.getBegSoaNo());
            ((ViewHolder) holder)
                    .endSoaNo
                    .setText(model.getEndSoaNo());
            ((ViewHolder) holder)
                    .begVoidNo
                    .setText(model.getBegVoidNo());
            ((ViewHolder) holder)
                    .endVoidNo
                    .setText(model.getEndVoidNo());

            if (from.equalsIgnoreCase("z")) {
                ((ViewHolder) holder)
                        .linZReadOnly.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return viewReceiptList.size();
    }





}
