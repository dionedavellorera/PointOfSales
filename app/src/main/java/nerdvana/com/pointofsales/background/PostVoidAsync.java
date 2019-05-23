package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addFooterToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.convertDateToReadableDate;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class PostVoidAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public PostVoidAsync(PrintModel printModel, Context context,
                         UserModel userModel, String currentDateTime,
                         MainActivity.AsyncFinishCallBack asyncFinishCallBack) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.asyncFinishCallBack = asyncFinishCallBack;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            printer = new Printer(
                    Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE)),
                    context);
            printer.setReceiveEventListener(new ReceiveListener() {
                @Override
                public void onPtrReceive(final Printer printer, int i, PrinterStatusInfo printerStatusInfo, String s) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                printer.disconnect();
                                asyncFinishCallBack.doneProcessing();
                            } catch (Epos2Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
            PrinterUtils.connect(context, printer);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }


        PrinterUtils.addHeader(printModel, printer);


        ViewReceiptResponse.Result toListPV = GsonHelper.getGson().fromJson(printModel.getData(), ViewReceiptResponse.Result.class)
                ;

        if (toListPV != null) {
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASHIER IN",
                    toListPV.getCashier() != null ? String.valueOf(toListPV.getCashier().getName()) : "NA "
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASHIER OUT",
                    toListPV.getCashierOut() != null ? String.valueOf(toListPV.getCashierOut().getName()) : "NA "
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "ROOM BOY IN",
                    String.valueOf(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomBoyIn().getName() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "ROOM BOY OUT",
                    String.valueOf(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomBoyOut().getName() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHECK IN",
                    convertDateToReadableDate(toListPV.getGuestInfo() != null ?toListPV.getGuestInfo().getCheckIn() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHECK OUT",
                    convertDateToReadableDate(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getCheckOut() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "RECEIPT NO",
                    toListPV.getReceiptNo() == null ? "NOT YET CHECKOUT" : toListPV.getReceiptNo().toString(),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "MACHINE NO",
                    SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, "QTY   DESCRIPTION         AMOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            for (ViewReceiptResponse.Post_ soaTrans : toListPV.getPost()) {

                String qty = "";

                qty += soaTrans.getQty();
                if (String.valueOf(soaTrans.getQty()).length() < 4) {
                    for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                        qty += " ";
                    }
                }
                String item = "";

                if (soaTrans.getProductId() == 0) {
                    item =soaTrans.getRoomRate().toString();
                } else {
                    item =soaTrans.getProduct().getProductInitial();
                }


                if (soaTrans.getVoid() == 0) {
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            qty+ " "+item,
                            returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty()))
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }
            }

            if (toListPV.getOtHours() > 0) {
                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        String.valueOf(toListPV.getOtHours()) + " " + "OT HOURS",
                        returnWithTwoDecimal(String.valueOf(toListPV.getOtAmount()))
                        ,
                        40,
                        2,context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }
//
            addTextToPrinter(printer, "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "   VAT EXEMPT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVatExempt())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "   DISCOUNT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getDiscount())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "   ADVANCED DEPOSIT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getAdvance())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "SUB TOTAL",
                    returnWithTwoDecimal(String.valueOf(toListPV.getTotal())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "AMOUNT DUE",
                    returnWithTwoDecimal(String.valueOf((toListPV.getTotal() + toListPV.getOtAmount() + toListPV.getxPersonAmount()) - (toListPV.getAdvance() + toListPV.getDiscount() + toListPV.getVatExempt()))),
                    40,
                    2,
                    context)
                    ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "TENDERED",
                    returnWithTwoDecimal(String.valueOf(toListPV.getTendered())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHANGE",
                    returnWithTwoDecimal(String.valueOf((toListPV.getChange() * -1))),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "VATable sales",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVatable())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "VAT-EXEMPT SALES",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVatExemptSales())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "12% VAT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVat())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);
//                    addTextToPrinter(printer, "ADVANCE DEPOSIT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            for (ViewReceiptResponse.Payment pym : toListPV.getPayments()) {
                if (pym.getIsAdvance() == 1) {
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            pym.getPaymentDescription(),
                            returnWithTwoDecimal(String.valueOf(pym.getAmount())),
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }
            }

            addPrinterSpace(1);

            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, "VOID", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,2,2);
            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);

            addFooterToPrinter();

            try {

                printer.addCut(Printer.CUT_FEED);

                if (printer.getStatus().getConnection() == 1) {
                    printer.sendData(Printer.PARAM_DEFAULT);
                    printer.clearCommandBuffer();
                }

            } catch (Epos2Exception e) {
                e.printStackTrace();
            }



        } else {
            Log.d("DATANUL"," DATAI SNULL");
        }


        return null;
    }
}
