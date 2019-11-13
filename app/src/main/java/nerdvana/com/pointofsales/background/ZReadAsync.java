package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.google.gson.reflect.TypeToken;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_responses.FetchDiscountSpecialResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTrGSales;

public class ZReadAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;


    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public ZReadAsync(PrintModel printModel, Context context,
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

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)) &&
                !TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE))) {


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

            ZReadResponse.Result zReadResponse = GsonHelper.getGson().fromJson(printModel.getData(), ZReadResponse.Result.class);

            if (zReadResponse != null) {

//                addTextToPrinter(printer, "Z READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "POSTING DATE: " + zReadResponse.getData().getGeneratedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "USER : " + userModel.getUsername(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "MANAGER : " + zReadResponse.getData().getDutyManager().getName(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, "DESCRIPTION                VALUE", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "TERMINAL NO",
                        String.valueOf(zReadResponse.getData().getPosId())
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "GROSS SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getGrossSales()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "NET SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getNetSales()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VATABLE SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatable()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VAT EXEMPT SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExemptSales()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VAT DISCOUNT",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VAT AMOUNT",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVat()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "ZERO-RATED SALES",
                        "0.00",
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "NON VAT",
//                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt()))
//                        ,
//                        40,
//                        2,
//                        context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "SERVICE CHARGE",
//                        "0.00"
//                        ,
//                        40,
//                        2,
//                        context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }


                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                    TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                    };
                    List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());

                    List<PaymentPrintModel> paymentPrintModels = new ArrayList<>();

                    Double totalAdvancePayment = 0.00;
                    for (ZReadResponse.Payment pym : zReadResponse.getPayment()) {
                        if (pym.getIsAdvance() == 1) {
                            totalAdvancePayment += pym.getAmount();
                        }

                    }


                    for (FetchPaymentResponse.Result payment : paymentTypeList) {

                        Double value = 0.00;
                        String isAdvance = "0";
                        Double change = zReadResponse.getChange();
                        for (ZReadResponse.Payment pym : zReadResponse.getPayment()) {
                            if (pym.getPaymentDescription().equalsIgnoreCase(payment.getPaymentType())) {
                                value = Double.valueOf(pym.getAmount());
                                isAdvance = String.valueOf(pym.getIsAdvance());
                                break;
                            }
                        }
                        if (payment.getPaymentType().equalsIgnoreCase("cash") || payment.getPaymentType().equalsIgnoreCase("card")) {

                            if (isAdvance.equalsIgnoreCase("1")) {
                                paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        payment.getPaymentType(),
                                        "0.00"
                                        ,
                                        40,
                                        2,context),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            } else {


                                if (payment.getPaymentType().equalsIgnoreCase("cash")) {
                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            payment.getPaymentType().toUpperCase()+ " SALES",
                                            returnWithTwoDecimal(String.valueOf(value + change))
                                            ,
                                            40,
                                            2,
                                            context),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {
                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            payment.getPaymentType().toUpperCase()+ " SALES",
                                            returnWithTwoDecimal(String.valueOf(value))
                                            ,
                                            40,
                                            2,
                                            context),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }



//                                if (payment.getPaymentType().equalsIgnoreCase("card")) {
//                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                                            "DEPOSIT SALES",
//                                            returnWithTwoDecimal(String.valueOf(totalAdvancePayment))
//                                            ,
//                                            40,
//                                            2,
//                                            context),
//                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                                }
                            }

                        } else {

                            if (value > 0) {
                                if (isAdvance.equalsIgnoreCase("1")) {

                                    paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                } else {
                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            payment.getPaymentType(),
                                            String.valueOf(value)
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

                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }

//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "CASH OUT",
//                    "0.00"
//                    ,
//                    40,
//                    2,
//                    context),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                double depositAdjustment = 0.00;
                for (ZReadResponse.CutOff cutOff : zReadResponse.getData().getCutOff()) {
                    depositAdjustment += Double.valueOf(cutOff.getCashAndReco().get(0).getAdjustmentDeposit());
                }
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "DEPO. ADJUSTMENT",
//                        returnWithTwoDecimal(String.valueOf(depositAdjustment))
//                        ,
//                        40,
//                        2,
//                        context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "REFUND",
//                    "0.00"
//                    ,
//                    40,
//                    2,
//                    context),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VOID AMOUNT",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVoidAmount()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }


                if (zReadResponse.getDiscount().size() > 0) {
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "DISCOUNT LIST",
                            ""
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    TypeToken<List<FetchDiscountSpecialResponse.Result>> discToken = new TypeToken<List<FetchDiscountSpecialResponse.Result>>() {};
                    List<FetchDiscountSpecialResponse.Result> discountDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.DISCOUNT_SPECIAL_JSON), discToken.getType());

                    if (discountDetails != null) {
                        for (FetchDiscountSpecialResponse.Result d : discountDetails) {
                            Integer count = 0;
                            Double amount = 0.00;
                            for (ZReadResponse.Discount disc : zReadResponse.getDiscount()) {
                                if (disc.getIsSpecial() == 1) {
                                    if (d.getId() == disc.getDiscountTypeId()) {
                                        amount = disc.getDiscountAmount();
                                        count = disc.getCount();
                                        break;
                                    }
                                }
                            }

                            if (d.getIsSpecial() == 1) {
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        d.getDiscountCard(),
                                        amount > 0 ? "-" + returnWithTwoDecimal(String.valueOf(amount)) : returnWithTwoDecimal(String.valueOf(amount))
                                        ,
                                        40,
                                        2,
                                        context),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        d.getDiscountCard() + "(COUNT)",
                                        String.valueOf(count)
                                        ,
                                        40,
                                        2,
                                        context),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }

                        }
                    }



                    int otherDiscCount = 0;
                    double otherDiscAmount = 0.00;


                    for (ZReadResponse.Discount disc : zReadResponse.getDiscount()) {
                        if (disc.getIsSpecial() == 0) {
                            otherDiscAmount+= disc.getDiscountAmount();
                            otherDiscCount+= disc.getCount();
                        }
                    }

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "OTHERS(AMOUNT)",
                            otherDiscAmount > 0 ? "-" + returnWithTwoDecimal(String.valueOf(otherDiscAmount)) : returnWithTwoDecimal(String.valueOf(otherDiscAmount))
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "OTHERS(COUNT)",
                            String.valueOf(otherDiscCount)
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);




                } else {

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "SENIOR CITIZEN",
                            "0.00"
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "SENIOR CITIZEN" + "(COUNT)",
                            "0"
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "PWD",
                            "0.00"
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "PWD" + "(COUNT)",
                            "0"
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "OTHERS",
                            "0.00"
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "OTHERS" + "(COUNT)",
                            "0"
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }

                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }


                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "BEG. OR NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : zReadResponse.getLastOrNo()
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "ENDING OR NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : zReadResponse.getLastOrNo()
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "BEG. SOA NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : zReadResponse.getLastOrNo()
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "ENDING SOA NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : zReadResponse.getLastOrNo()
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "BEG. VOID NO",
                        zReadResponse.getVoidNo().size() > 0 ? zReadResponse.getVoidNo().get(0) : zReadResponse.getLastVoidNo()
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "ENDING VOID NO",
                        zReadResponse.getVoidNo().size() > 0 ? zReadResponse.getVoidNo().get(zReadResponse.getVoidNo().size() - 1) : zReadResponse.getLastVoidNo()
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "BEG. BALANCE",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getOldGrandTotal()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTrGSales(
                        "GRAND TOTAL SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getNewGrandTotal()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                try {
                    printer.addFeedLine(1);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }


                addTextToPrinter(printer, "Z COUNTER:" + returnWithTwoDecimal(String.valueOf(zReadResponse.getCount())), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "Z KEEPING COUNTER",
//                        returnWithTwoDecimal(String.valueOf(zReadResponse.getCount()))
//                        ,
//                        40,
//                        2,
//                        context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, "------ END OF REPORT ------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, "---------------------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);

                addTextToPrinter(printer, "POS Provider : NERDVANA CORP.", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "Address : 1 CANLEY ROAD BRGY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "BAGONG ILOG PASIG CITY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "VAT REG TIN: 009-772-500-00000", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
                String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd hh:mm:ss");


                addTextToPrinter(printer, "ACCRED NO:**********************", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "Date Issued : " + Utils.birDateTimeFormat(folderName), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "Valid Until : " + Utils.birDateTimeFormat(folderName), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(printer, "PERMIT NO: ********-***-*******-*****" , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );
                addTextToPrinter(printer, "Date Issued : " + Utils.birDateTimeFormat(folderName), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "Valid Until : " + Utils.birDateTimeFormat(folderName), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(printer, "---------------------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "PRINTED BY " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);




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



            } else {
                Toast.makeText(context, "ZREAD IS NULL", Toast.LENGTH_SHORT).show();
            }



        }
//        else {
//            Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
//        }






        return null;
    }

//    private String returnWithTwoDecimal(String amount) {
//        String finalValue = "";
//        if (amount.contains(".")) {
//            String[] tempArray = amount.split("\\.");
//            if (tempArray[1].length() > 2) {
//                finalValue = tempArray[0] + "." + tempArray[1].substring(0,2);
//            } else {
//                finalValue = tempArray[0] + "." + tempArray[1];
//            }
//        } else {
//            finalValue = amount;
//        }
//
//        return finalValue;
//
//    }
}
