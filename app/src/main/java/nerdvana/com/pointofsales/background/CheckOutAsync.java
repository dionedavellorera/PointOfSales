package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.SeniorReceiptCheckoutModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class CheckOutAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public CheckOutAsync(PrintModel printModel, Context context, UserModel userModel, MainActivity.AsyncFinishCallBack asyncFinishCallBack) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.asyncFinishCallBack = asyncFinishCallBack;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.d("STARTEXEC", "CHECKOUT");

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("STARTEXEC", "CHECKOUT EXECING");

        String finalString = "";
        String receiptNo = "NA";
        try {
            printer = new Printer(
                    Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE)),
                    context);

            try {
                printer.addPulse(Printer.DRAWER_HIGH, Printer.PULSE_100);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }

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

        addTextToPrinter(printer, "NERDVANA CORP.", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "1 CANLEY ROAD BRGY BAGONG", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "ILOG PASIG CITY 1600", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer," 671-9782", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "SERIAL NO: ********", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1,1);
        addTextToPrinter(printer, "VAT REG TIN NO: 009-772-500-000" , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "PERMIT NO: ********-***-*******-*****" , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );


        finalString += receiptString("NERDVANA CORP.", "", context, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", context, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", context, true);
        finalString += receiptString("671-9782", "", context, true);
        finalString += receiptString("SERIAL NO: ********", "", context, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", context, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", context, true);

        FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class)
                ;
        if (toList1 != null) {

            finalString += receiptString("CASHIER", userModel.getUsername(), context, false);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASHIER",
                    userModel.getUsername()
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("ROOM BOY", String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA"), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "ROOM BOY",
                    String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            finalString += receiptString("CHECK IN", convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA"), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHECK IN",
                    convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("CHECK OUT", convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA"), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHECK OUT",
                    convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            if (toList1.getReceiptNo() == null) {
                receiptNo = "NA";
            } else {
                receiptNo = toList1.getReceiptNo().toString();
            }


            finalString += receiptString("RECEIPT NO", toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "RECEIPT NO",
                    toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("MACHINE NO", SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "MACHINE NO",
                    SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("QTY   DESCRIPTION         AMOUNT", "", context, true);
            addTextToPrinter(printer, "QTY   DESCRIPTION         AMOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            for (FetchOrderPendingViaControlNoResponse.Post soaTrans : toList1.getPost()) {
                if (soaTrans.getVoid() == 0) {
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

                    finalString += receiptString(qty + " " + item, returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty())), context, false);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            qty+ " "+item,
                            returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty()))
                            ,
                            40,
                            2,context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    if (soaTrans.getFreebie() != null) {
                        if (soaTrans.getFreebie().getPostAlaCart().size() > 0) {
                            for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getFreebie().getPostAlaCart()) {

                                finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", context, false);
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(),
                                        ""
                                        ,
                                        40,
                                        2,
                                        context),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }

                        if (soaTrans.getFreebie().getPostGroup().size() > 0) {
                            for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getFreebie().getPostGroup()) {
                                for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                    finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", context, false);


                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            "   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(),
                                            ""
                                            ,
                                            40,
                                            2,
                                            context),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }
                            }
                        }


                    }



                    if (soaTrans.getPostAlaCartList().size() > 0) {
                        for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getPostAlaCartList()) {

                            finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", context, false);
                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    "   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(),
                                    ""
                                    ,
                                    40,
                                    2,
                                    context),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                    }

                    if (soaTrans.getPostGroupList().size() > 0) {
                        for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getPostGroupList()) {
                            for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", context, false);
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(),
                                        ""
                                        ,
                                        40,
                                        2,
                                        context),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }

                        }
                    }
                }
            }




            if (toList1.getOtHours() > 0) {

                finalString += receiptString(String.valueOf(toList1.getOtHours()) + " " + "OT HOURS",
                        returnWithTwoDecimal(String.valueOf(toList1.getOtAmount())), context, false);
                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        String.valueOf(toList1.getOtHours()) + " " + "OT HOURS",
                        returnWithTwoDecimal(String.valueOf(toList1.getOtAmount()))
                        ,
                        40,
                        2,
                        context
                        ),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }

            if (Integer.valueOf(toList1.getPersonCount()) > 2) {


                finalString += receiptString(String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                        returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount())), context, false);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                        returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount()))
                        ,
                        40,
                        2,context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }

            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);


            finalString += receiptString("NO OF PERSONS", returnWithTwoDecimal(String.valueOf(toList1.getPersonCount())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "NO OF PERSONS",
                    returnWithTwoDecimal(String.valueOf(toList1.getPersonCount()))
                    ,
                    40,
                    2,context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("NO OF ITEMS", returnWithTwoDecimal(String.valueOf(toList1.getTotalQty())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "NO OF ITEMS",
                    returnWithTwoDecimal(String.valueOf(toList1.getTotalQty()))
                    ,
                    40,
                    2,context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);

            finalString += receiptString("LESS", "", context, false);
            addTextToPrinter(printer, "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("   VAT EXEMPT", returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "   VAT EXEMPT",
                    returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            finalString += receiptString("   DISCOUNT", returnWithTwoDecimal(String.valueOf(toList1.getDiscount())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "   DISCOUNT",
                    returnWithTwoDecimal(String.valueOf(toList1.getDiscount())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("   ADVANCED DEPOSIT", returnWithTwoDecimal(String.valueOf(toList1.getAdvance())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "   ADVANCED DEPOSIT",
                    returnWithTwoDecimal(String.valueOf(toList1.getAdvance())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);

//            bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount()


            finalString += receiptString("SUB TOTAL", returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "SUB TOTAL",
                    returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "SUB TOTAL",
//                    returnWithTwoDecimal(String.valueOf(toList1.getTotal())),
//                    40,
//                    2,
//                    context)
//                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



            finalString += receiptString("AMOUNT DUE", returnWithTwoDecimal(String.valueOf(
                    (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                            - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "AMOUNT DUE",
                    returnWithTwoDecimal(String.valueOf(
                            (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                                    - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))),
                    40,
                    2,
                    context)
                    ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            finalString += receiptString("TENDERED", returnWithTwoDecimal(String.valueOf(toList1.getTendered())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "TENDERED",
                    returnWithTwoDecimal(String.valueOf(toList1.getTendered())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            finalString += receiptString("CHANGE", returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHANGE",
                    returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);
            List<Integer> tmpArr = new ArrayList<>();
            String pymType = "";
            List<String> ccardArray = new ArrayList<>();
            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (!tmpArr.contains(pym.getPaymentTypeId())) {
                    tmpArr.add(pym.getPaymentTypeId());
                    pymType = pym.getPaymentDescription();
                }

                if (pym.getPaymentTypeId() == 2) {
                    if (pym.getCardDetail() != null) {
                        if (!pym.getCardDetail().getCardNumber().trim().isEmpty()) {
                            int starCount = 0;
                            String finalData = "";
                            if (pym.getCardDetail().getCardNumber().length() < 3) {
                                finalData += pym.getCardDetail().getCardNumber();
                            } else {
                                starCount = pym.getCardDetail().getCardNumber().length() - 3;
                                finalData += new String(new char[starCount]).replace("\0", "*");
                                finalData += pym.getCardDetail().getCardNumber().substring(starCount);
                            }

                            if (pym.getCardDetail().getCreditCardId().equalsIgnoreCase("1")) {

                                finalString += receiptString("MASTER", "", context, false);
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "MASTER",
                                        ""
                                        ,
                                        40,
                                        2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            } else {
                                finalString += receiptString("VISA", "", context, false);
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "VISA",
                                        ""
                                        ,
                                        40,
                                        2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }

                            finalString += receiptString(pym.getPaymentDescription(), finalData, context, false);
                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    pym.getPaymentDescription(),
                                    finalData
                                    ,
                                    40,
                                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                    }
                }
            }


            finalString += receiptString("PAYMENT TYPE", tmpArr.size() > 1 ? "MULTIPLE" : pymType, context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "PAYMENT TYPE",
                    tmpArr.size() > 1 ? "MULTIPLE" : pymType
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);


            finalString += receiptString("VATABLE SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatable())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "VATABLE SALES",
                    returnWithTwoDecimal(String.valueOf(toList1.getVatable())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            finalString += receiptString("VAT-EXEMPT SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "VAT-EXEMPT SALES",
                    returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())),
                    40,
                    2,
                    context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("12% VAT", returnWithTwoDecimal(String.valueOf(toList1.getVat())), context, false);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "12% VAT",
                    returnWithTwoDecimal(String.valueOf(toList1.getVat())),
                    40,
                    2,context)
                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);

            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (pym.getIsAdvance() == 1) {

                    finalString += receiptString(pym.getPaymentDescription(), returnWithTwoDecimal(String.valueOf(pym.getAmount())), context, false);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            pym.getPaymentDescription(),
                            returnWithTwoDecimal(String.valueOf(pym.getAmount())),
                            40,
                            2,context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }
            }

            boolean hasSpecial = false;
            List<SeniorReceiptCheckoutModel> seniorReceiptList = new ArrayList<>();
            if (toList1.getDiscountsList().size() > 0) {
                finalString += receiptString("DISCOUNT LIST", "", context, true);
                addTextToPrinter(printer, "DISCOUNT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                for (FetchOrderPendingViaControlNoResponse.Discounts d : toList1.getDiscountsList()) {
//                        addTextToPrinter(printer, d.getDiscountType(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    if (TextUtils.isEmpty(d.getVoid_by())) {
                        if (d.getId().equalsIgnoreCase("0")) { //MANUAL
//                        addTextToPrinter(printer, "    " + d.getDiscountReason(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        } else {

                            if (d.getInfo() != null) {

//                                if (d.getDiscountTypes().getIsSpecial() == 1) {
//                                    hasSpecial = true;
//                                    seniorReceiptList.add(
//                                            new SeniorReceiptCheckoutModel(
//                                                    d.getInfo().getName() == null ? "" : d.getInfo().getName(),
//                                                    d.getInfo().getCardNo() == null ? "" : d.getInfo().getCardNo(),
//                                                    d.getInfo().getAddress() == null ? "" : d.getInfo().getAddress(),
//                                                    d.getInfo().getTin() == null ? "" : d.getInfo().getTin(),
//                                                    d.getInfo().getBusinessStyle() == null ? "" : d.getInfo().getBusinessStyle()
//                                            )
//                                    );
//                                }

                                if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                    finalString += receiptString(d.getDiscountType(), "NA", context, false);

                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            d.getDiscountType(),
                                            "NA",
                                            40,
                                            2,
                                            context)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {

                                    if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                        finalString += receiptString(d.getDiscountType(), "NA", context, false);

                                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                d.getDiscountType(),
                                                "NA",
                                                40,
                                                2,
                                                context)
                                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    } else {
                                        if (d.getInfo().getCardNo() != null) {

                                            finalString += receiptString(d.getDiscountType(), d.getInfo().getCardNo().toUpperCase(), context, false);


                                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                    d.getDiscountType(),
                                                    d.getInfo().getCardNo().toUpperCase(),
                                                    40,
                                                    2,
                                                    context)
                                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                        }

                                        if (d.getInfo().getName() != null) {

                                            finalString += receiptString("NAME", d.getInfo().getName().toUpperCase(), context, false);

                                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                    "NAME",
                                                    d.getInfo().getName().toUpperCase(),
                                                    40,
                                                    2,
                                                    context)
                                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                        }
                                    }


                                }

                            }
                        }
                    }
                }

            }

//            if (hasSpecial) {
//                for (SeniorReceiptCheckoutModel sr : seniorReceiptList) {
//                    addPrinterSpace(1);
//                    if (!TextUtils.isEmpty(sr.getName())) {
//                        addTextToPrinter(printer, "NAME:"+toList1.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "NAME:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    if (!TextUtils.isEmpty(sr.getScPwdId())) {
//                        addTextToPrinter(printer, "SC/PWD ID:"+sr.getScPwdId(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "SC/PWD ID:______________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//
//                    if (toList1.getCustomer().getAddress() != null) {
//                        addTextToPrinter(printer, "ADDRESS:"+sr.getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    if (toList1.getCustomer().getTin() != null) {
//                        addTextToPrinter(printer, "TIN#:" +sr.getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    if (toList1.getCustomer().getBusinessStyle() != null) {
//                        addTextToPrinter(printer, "BUSINESS STYLE:"+ sr.getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                        addTextToPrinter(printer, toList1.getCustomer().getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    addPrinterSpace(1);
//
//                }
//            }


            addPrinterSpace(1);

            if (toList1.getCustomer() != null) {
                if (!toList1.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList1.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {

                    finalString += receiptString("", "", context, true);
                    addPrinterSpace(1);


                    finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", context, true);
                    addTextToPrinter(printer, "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);

                    finalString += receiptString("NAME:"+toList1.getCustomer().getCustomer(), "", context, true);
                    addTextToPrinter(printer, "NAME:"+toList1.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    if (toList1.getCustomer().getAddress() != null) {

                        finalString += receiptString("ADDRESS:"+toList1.getCustomer().getAddress(), "", context, true);
                        addTextToPrinter(printer, "ADDRESS:"+toList1.getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    } else {
                        finalString += receiptString("ADDRESS:________________________", "", context, true);
                        addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    }

                    if (toList1.getCustomer().getTin() != null) {
                        finalString += receiptString("TIN#:"+toList1.getCustomer().getTin(), "", context, true);
                        addTextToPrinter(printer, "TIN#:"+toList1.getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    } else {

                        finalString += receiptString("TIN#:___________________________", "", context, true);
                        addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    }

                    if (toList1.getCustomer().getBusinessStyle() != null) {
                        finalString += receiptString("BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(), "", context, true);
                        addTextToPrinter(printer, "BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);

                        finalString += receiptString(toList1.getCustomer().getBusinessStyle(), "", context, true);
                        addTextToPrinter(printer, toList1.getCustomer().getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    } else {
                        finalString += receiptString("BUSINESS STYLE:_________________", "", context, true);
                        addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    }


//                    addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);

                    finalString += receiptString("", "", context, true);
                    addPrinterSpace(1);

                } else {

                    finalString += receiptString("NAME:___________________________", "", context, true);
                    finalString += receiptString("ADDRESS:________________________", "", context, true);
                    finalString += receiptString("TIN#:___________________________", "", context, true);
                    finalString += receiptString("BUSINESS STYLE:_________________", "", context, true);
                    addTextToPrinter(printer, "NAME:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                }
            } else {

                finalString += receiptString("NAME:___________________________", "", context, true);
                finalString += receiptString("ADDRESS:________________________", "", context, true);
                finalString += receiptString("TIN#:___________________________", "", context, true);
                finalString += receiptString("BUSINESS STYLE:_________________", "", context, true);

                addTextToPrinter(printer, "NAME:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
            }


            finalString += receiptString("", "", context, true);
            addPrinterSpace(1);


            finalString += receiptString("", "", context, true);

            finalString += receiptString("Thank you come again", "", context, true);

            finalString += receiptString("----- SYSTEM PROVIDER DETAILS -----", "", context, true);

            finalString += receiptString("Provider : NERDVANA CORP.", "", context, true);

            finalString += receiptString("Address : 1 CANLEY ROAD BRGY BAGONG ILOG PASIG CITY", "", context, true);

            finalString += receiptString("TIN: 009-772-500-000", "", context, true);

            finalString += receiptString("ACCRE No. : ******", "", context, true);

            finalString += receiptString("Date issued : " + toList1.getCreatedAt(), "", context, true);

            finalString += receiptString("Date issued : " + PrinterUtils.yearPlusFive(toList1.getCreatedAt()), "", context, true);

            finalString += receiptString("", "", context, true);

            finalString += receiptString("THIS RECEIPT SHALL BE VALID FOR", "", context, true);

            finalString += receiptString("FIVE(5) YEARS FROM THE DATE OF", "", context, true);

            finalString += receiptString("THE PERMIT TO USE", "", context, true);

            finalString += receiptString("", "", context, true);




            try {
                printer.addCut(Printer.CUT_FEED);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }

            addTextToPrinter(printer, "PAYMENT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

            addPrinterSpace(1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "ROOM NO",
                    String.valueOf(printModel.getRoomNumber())
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "ROOM TYPE",
                    printModel.getRoomType() != null ? printModel.getRoomType() : ""
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASHIER",
                    toList1.getCashier() != null ? String.valueOf(toList1.getCashier().getName()) : "NA"
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "ROOM BOY",
                    String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA")
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addPrinterSpace(1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHECK IN",
                    convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckIn() : "NA" )
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHECK OUT",
                    convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA")
                    ,
                    40,
                    2,
                    context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            List<Integer> tempArray = new ArrayList<>();
            String paymentType = "";
            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (!tempArray.contains(pym.getPaymentTypeId())) {
                    tempArray.add(pym.getPaymentTypeId());
                    paymentType = pym.getPaymentDescription();
                }
            }

            addPrinterSpace(1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "PAYMENT TYPE",
                    tempArray.size() > 1 ? "MULTIPLE" : paymentType
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);



            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "TENDERED",
                    returnWithTwoDecimal(String.valueOf(toList1.getTendered()))
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "AMOUNT DUE",
                    returnWithTwoDecimal(Utils.returnWithTwoDecimal(String.valueOf((toList1.getTotal() +
                            toList1.getOtAmount() +
                            toList1.getXPersonAmount())
                            - (
                            toList1.getDiscount() +
                                    toList1.getVatExempt())))),
                    40,
                    2,
                    context), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CHANGE",
                    returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange())))
                    ,
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            try {
                Log.d("MYDATA", "START");
                Log.d("MYDATA", finalString);

                File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, receiptNo +".txt");
                FileWriter writer = null;
                writer = new FileWriter(gpxfile);

                writer.append(finalString);
//                writer.append("test data");

                writer.flush();

                writer.close();

            } catch (IOException e) {
                Log.d("ERRORMESSAGE", e.getMessage());
            }




        } else {
            Log.d("DATANUL"," DATAI SNULL");
        }



        try {

            printer.addCut(Printer.CUT_FEED);

            if (printer.getStatus().getConnection() == 1) {
                printer.sendData(Printer.PARAM_DEFAULT);
                printer.clearCommandBuffer();
            }


//            printer.endTransaction();
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }





        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }

    private void addTextToPrinter(Printer printer, String text,
                                  int isBold, int isUnderlined,
                                  int alignment, int feedLine,
                                  int textSizeWidth, int textSizeHeight) {

        if (printer != null) {
            StringBuilder textData = new StringBuilder();
            try {
                printer.addTextSize(textSizeWidth, textSizeHeight);
                printer.addTextAlign(alignment);
                printer.addTextStyle(Printer.PARAM_DEFAULT, isUnderlined, isBold, Printer.PARAM_DEFAULT);
                printer.addTextSmooth(Printer.TRUE);
                printer.addText(textData.toString());
                textData.append(text);
                printer.addText(textData.toString());
                textData.delete(0, textData.length());
                printer.addFeedLine(feedLine);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String returnWithTwoDecimal(String amount) {
        String finalValue = "";
        if (amount.contains(".")) {
            String[] tempArray = amount.split("\\.");
            if (tempArray[1].length() > 2) {
                finalValue = tempArray[0] + "." + tempArray[1].substring(0,2);
            } else {
                finalValue = tempArray[0] + "." + tempArray[1];
            }
        } else {
            finalValue = amount;
        }

        return finalValue;

    }

    private void addPrinterSpace(int count) {
        try {
            printer.addFeedLine(count);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    private void addFooterToPrinter(String currentDate, String currentDatePlus5) {

        if (printer != null) {
//            addTextToPrinter(printer, "THIS IS NOT AN OFFICIAL RECEIPT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Thank you come again", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "----- SYSTEM PROVIDER DETAILS -----", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Provider : NERDVANA CORP.", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Address : 1 CANLEY ROAD BRGY BAGONG ILOG PASIG CITY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "TIN: 009-772-500-000", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "ACCRE No. : ******", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Date issued : " + currentDate, Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Valid until : " + currentDatePlus5, Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//            addTextToPrinter(printer, "PTU No. : FPU 42434242424242423", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
            addTextToPrinter(printer, "THIS RECEIPT SHALL BE VALID FOR", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "FIVE(5) YEARS FROM THE DATE OF", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "THE PERMIT TO USE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
        }
    }

    private String convertDateToReadableDate(String createdAt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(createdAt);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");


            res = dtfOut.print(jodatime);
        } catch (Exception e) {
            res  = "NA";
        }


        return res.toUpperCase();

    }



    private String receiptString(String partOne, String partTwo, Context context, boolean isCenter) {
        String finalString = "";
        int filler = 0;
        int maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) / 2);

        if (isCenter) {
            if (partOne.length() > Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))) {
                finalString = partOne.substring(0, Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)));
            } else {
                int custFillter = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) - partOne.length()) / 2;
                finalString = repeat(" ", custFillter) + partOne + repeat(" ", custFillter);
            }

        } else {

            if (partOne.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partOne.length());
            }
            if (partTwo.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partTwo.length());
            }
            finalString = (partOne.length() >= maxColumnDivideTwo ? partOne.substring(0, maxColumnDivideTwo) : partOne)
                        + repeat(" ", filler)
                        + (partTwo.length() >= maxColumnDivideTwo ? partTwo.substring(0, maxColumnDivideTwo) : partTwo);

        }



        return finalString + "\n";
    }

    private static String repeat(String str, int i){
        return new String(new char[i]).replace("\0", str);
    }


}
