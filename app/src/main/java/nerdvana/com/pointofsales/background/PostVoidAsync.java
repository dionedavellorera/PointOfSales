package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
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
    public PostVoidAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        PrinterUtils.addHeader(printModel);


        ViewReceiptResponse.Result toListPV = GsonHelper.getGson().fromJson(printModel.getData(), ViewReceiptResponse.Result.class)
                ;

        if (toListPV != null) {
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CASHIER IN",
                    toListPV.getCashier() != null ? String.valueOf(toListPV.getCashier().getName()) : "NA "
                    ,
                    40,
                    2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CASHIER OUT",
                    toListPV.getCashierOut() != null ? String.valueOf(toListPV.getCashierOut().getName()) : "NA "
                    ,
                    40,
                    2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "ROOM BOY IN",
                    String.valueOf(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomBoyIn().getName() : "NA")
                    ,
                    40,
                    2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "ROOM BOY OUT",
                    String.valueOf(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomBoyOut().getName() : "NA")
                    ,
                    40,
                    2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CHECK IN",
                    convertDateToReadableDate(toListPV.getGuestInfo() != null ?toListPV.getGuestInfo().getCheckIn() : "NA")
                    ,
                    40,
                    2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CHECK OUT",
                    convertDateToReadableDate(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getCheckOut() : "NA")
                    ,
                    40,
                    2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "RECEIPT NO",
                    toListPV.getReceiptNo() == null ? "NOT YET CHECKOUT" : toListPV.getReceiptNo().toString(),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "MACHINE NO",
                    SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "QTY   Description             Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            qty+ " "+item,
                            returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }
            }

            if (toListPV.getOtHours() > 0) {
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        String.valueOf(toListPV.getOtHours()) + " " + "OT HOURS",
                        returnWithTwoDecimal(String.valueOf(toListPV.getOtAmount()))
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }
//
            addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "   VAT EXEMPT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVatExempt())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "   DISCOUNT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getDiscount())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "   ADVANCED DEPOSIT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getAdvance())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "SUB TOTAL",
                    returnWithTwoDecimal(String.valueOf(toListPV.getTotal())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "AMOUNT DUE",
                    returnWithTwoDecimal(String.valueOf((toListPV.getTotal() + toListPV.getOtAmount() + toListPV.getxPersonAmount()) - (toListPV.getAdvance() + toListPV.getDiscount() + toListPV.getVatExempt()))),
                    40,
                    2)
                    ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "TENDERED",
                    returnWithTwoDecimal(String.valueOf(toListPV.getTendered())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CHANGE",
                    returnWithTwoDecimal(String.valueOf((toListPV.getChange() * -1))),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "VATable sales",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVatable())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "VAT-EXEMPT SALES",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVatExemptSales())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "12% VAT",
                    returnWithTwoDecimal(String.valueOf(toListPV.getVat())),
                    40,
                    2)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);
//                    addTextToPrinter(SPrinter.getPrinter(), "ADVANCE DEPOSIT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            for (ViewReceiptResponse.Payment pym : toListPV.getPayments()) {
                if (pym.getIsAdvance() == 1) {
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            pym.getPaymentDescription(),
                            returnWithTwoDecimal(String.valueOf(pym.getAmount())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }
            }

            addPrinterSpace(1);

            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "VOID", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,2,2);
            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);

            addFooterToPrinter();

//                    try {
//                        SPrinter.getPrinter().addCut(Printer.CUT_FEED);
//                    } catch (Epos2Exception e) {
//                        e.printStackTrace();
//                    }

//                    addTextToPrinter(SPrinter.getPrinter(), "PAYMENT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "ROOM NO",
//                            String.valueOf(printModel.getRoomNumber())
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "ROOM TYPE",
//                            printModel.getRoomType()
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "CASHIER",
//                            toList1.getCashier() != null ? String.valueOf(toList1.getCashier().getName()) : "NA"
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "ROOM BOY",
//                            String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA")
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "CHECK IN",
//                            convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckIn() : "NA" )
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "CHECK OUT",
//                            convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA")
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                    List<Integer> tempArray = new ArrayList<>();
//                    String paymentType = "";
//                    for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
//                        if (!tempArray.contains(pym.getPaymentTypeId())) {
//                            tempArray.add(pym.getPaymentTypeId());
//                            paymentType = pym.getPaymentDescription();
//                        }
//                    }
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "PAYMENT TYPE",
//                            tempArray.size() > 1 ? "MULTIPLE" : paymentType
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "AMOUNT DUE",
//                            returnWithTwoDecimal(String.valueOf(toList1.getTotal() - (toList1.getAdvance() + toList1.getDiscount()))),
//                            40,
//                            2), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "TENDERED",
//                            returnWithTwoDecimal(String.valueOf(toList1.getTendered()))
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "CHANGE",
//                            returnWithTwoDecimal(String.valueOf((toList1.getChange() * -1)))
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            try {

                SPrinter.getPrinter().addCut(Printer.CUT_FEED);

                if (SPrinter.getPrinter().getStatus().getConnection() == 1) {
                    SPrinter.getPrinter().sendData(Printer.PARAM_DEFAULT);
                    SPrinter.getPrinter().clearCommandBuffer();
                }


//            SPrinter.getPrinter().endTransaction();
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }



        } else {
            Log.d("DATANUL"," DATAI SNULL");
        }


        return null;
    }
}
