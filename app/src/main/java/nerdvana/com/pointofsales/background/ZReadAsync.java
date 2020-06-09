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
import com.sunmi.devicemanager.cons.Cons;
import com.sunmi.devicemanager.device.Device;
import com.sunmi.devicesdk.core.PrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

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
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.MainActivity.receiptString;
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

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;
    public ZReadAsync(PrintModel printModel, Context context,
                      UserModel userModel, String currentDateTime,
                      MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                      PrinterPresenter printerPresenter, SunmiPrinterService mSunmiPrintService) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.asyncFinishCallBack = asyncFinishCallBack;
        this.printerPresenter = printerPresenter;
        this.mSunmiPrintService = mSunmiPrintService;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY).equalsIgnoreCase("sunmi")) {
            if (printerPresenter == null) {
                printerPresenter = new PrinterPresenter(context, mSunmiPrintService);
            }
            String finalString = "";

            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), "", context, true);
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), "", context, true);
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), "", context, true);
            finalString += receiptString("SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), "", context, true);
            finalString += receiptString("VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER), "", context, true);
            finalString += receiptString("PERMIT NO:" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO), "", context, true);

            finalString += receiptString("Z READING", "", context, true);




            ZReadResponse.Result zReadResponse = GsonHelper.getGson().fromJson(printModel.getData(), ZReadResponse.Result.class);

            if (zReadResponse != null) {

                finalString += receiptString("POSTING DATE: " + zReadResponse.getData().getGeneratedAt(), "", context, true);
                finalString += receiptString("USER : " + userModel.getUsername(), "", context, true);
                if (zReadResponse.getData().getDutyManager() != null) {
                    finalString += receiptString("MANAGER : " + zReadResponse.getData().getDutyManager().getName(), "", context, true);
                } else {
                    finalString += receiptString("MANAGER : " + "", "", context, true);
                }

                finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
                finalString += receiptString("DESCRIPTION                VALUE", "", context, true);
                finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
                finalString += receiptString("TERMINAL NO", String.valueOf(zReadResponse.getData().getPosId()), context, false);


                finalString += receiptString(
                        "GROSS SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getGrossSales()))
                        ,
                        context,
                        false);
                finalString += receiptString(
                        "NET SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getNetSales()))
                        ,
                        context,
                        false);


                finalString += receiptString(
                        "VATABLE SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatable()))
                        ,
                        context,
                        false);

                finalString += receiptString(
                        "VAT EXEMPT SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExemptSales()))
                        ,
                        context,
                        false);

                finalString += receiptString(
                        "VAT DISCOUNT",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt()))
                        ,
                        context,
                        false);


                finalString += receiptString(
                        "VAT AMOUNT",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVat()))
                        ,
                        context,
                        false);


                finalString += receiptString(
                        "ZERO-RATED SALES",
                        "0.00"
                        ,
                        context,
                        false);


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
                                finalString += receiptString(
                                        payment.getPaymentType(),
                                        "0.00"
                                        ,
                                        context,
                                        false);

                            } else {


                                if (payment.getPaymentType().equalsIgnoreCase("cash")) {
                                    finalString += receiptString(
                                            payment.getPaymentType().toUpperCase()+ " SALES",
                                            returnWithTwoDecimal(String.valueOf(value + change))
                                            ,
                                            context,
                                            false);

                                } else {
                                    finalString += receiptString(
                                            payment.getPaymentType().toUpperCase()+ " SALES",
                                            returnWithTwoDecimal(String.valueOf(value))
                                            ,
                                            context,
                                            false);

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
                                    finalString += receiptString(
                                            payment.getPaymentType(),
                                            String.valueOf(value)
                                            ,
                                            context,
                                            false);

                                }
                            }
                        }
                    }
                }

                double depositAdjustment = 0.00;
                for (ZReadResponse.CutOff cutOff : zReadResponse.getData().getCutOff()) {
                    depositAdjustment += Double.valueOf(cutOff.getCashAndReco().get(0).getAdjustmentDeposit());
                }

                finalString += receiptString(
                        "VOID AMOUNT",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVoidAmount()))
                        ,
                        context,
                        false);




                if (zReadResponse.getDiscount().size() > 0) {
                    finalString += receiptString(
                            "DISCOUNT LIST",
                            ""
                            ,
                            context,
                            false);



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
                                finalString += receiptString(
                                        d.getDiscountCard(),
                                        amount > 0 ? "-" + returnWithTwoDecimal(String.valueOf(amount)) : returnWithTwoDecimal(String.valueOf(amount))
                                        ,
                                        context,
                                        false);
                                finalString += receiptString(
                                        d.getDiscountCard() + "(COUNT)",
                                        String.valueOf(count)
                                        ,
                                        context,
                                        false);

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

                    finalString += receiptString(
                            "OTHERS(AMOUNT)",
                            otherDiscAmount > 0 ? "-" + returnWithTwoDecimal(String.valueOf(otherDiscAmount)) : returnWithTwoDecimal(String.valueOf(otherDiscAmount))
                            ,
                            context,
                            false);

                    finalString += receiptString(
                            "OTHERS(COUNT)",
                            String.valueOf(otherDiscCount)
                            ,
                            context,
                            false);

                } else {

                    finalString += receiptString(
                            "SENIOR CITIZEN",
                            "0.00"
                            ,
                            context,
                            false);

                    finalString += receiptString(
                            "SENIOR CITIZEN(COUNT)",
                            "0"
                            ,
                            context,
                            false);

                    finalString += receiptString(
                            "PWD",
                            "0.00"
                            ,
                            context,
                            false);

                    finalString += receiptString(
                            "PWD(COUNT)",
                            "0"
                            ,
                            context,
                            false);

                    finalString += receiptString(
                            "OTHERS",
                            "0.00"
                            ,
                            context,
                            false);

                    finalString += receiptString(
                            "OTHERS(COUNT)",
                            "0"
                            ,
                            context,
                            false);

                }

                finalString += receiptString(
                        new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"),
                        ""
                        ,
                        context,
                        true);


                finalString += receiptString(
                        "BEG. OR NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : zReadResponse.getLastOrNo()
                        ,
                        context,
                        false);

                finalString += receiptString(
                        "ENDING OR NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : zReadResponse.getLastOrNo()
                        ,
                        context,
                        false);


                finalString += receiptString(
                        "BEG. SOA NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : zReadResponse.getLastOrNo()
                        ,
                        context,
                        false);

                finalString += receiptString(
                        "ENDING SOA NO",
                        zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : zReadResponse.getLastOrNo()
                        ,
                        context,
                        false);


                finalString += receiptString(
                        "BEG. VOID NO",
                        zReadResponse.getVoidNo().size() > 0 ? zReadResponse.getVoidNo().get(0) : zReadResponse.getLastVoidNo()
                        ,
                        context,
                        false);

                finalString += receiptString(
                        "ENDING VOID NO",
                        zReadResponse.getVoidNo().size() > 0 ? zReadResponse.getVoidNo().get(zReadResponse.getVoidNo().size() - 1) : zReadResponse.getLastVoidNo()
                        ,
                        context,
                        false);


                finalString += receiptString(
                        "BEG. BALANCE",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getOldGrandTotal()))
                        ,
                        context,
                        false);

                finalString += receiptString(
                        "GRAND TOTAL SALES",
                        returnWithTwoDecimal(String.valueOf(zReadResponse.getNewGrandTotal()))
                        ,
                        context,
                        false);

                finalString += receiptString(
                        new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"),
                        ""
                        ,
                        context,
                        true);

                finalString += receiptString(
                        "Z COUNTER:" + returnWithTwoDecimal(String.valueOf(zReadResponse.getCount())),
                        ""
                        ,
                        context,
                        true);


                finalString += receiptString("------ END OF REPORT ------", "", context, true);
                finalString += receiptString("-------------", "", context, true);

                finalString += receiptString("POS Provider : NERDVANA CORP.",
                        "", context, true);
                finalString += receiptString("Address : 1 CANLEY ROAD BRGY",
                        "", context, true);
                finalString += receiptString("BAGONG ILOG PASIG CITY",
                        "", context, true);
                finalString += receiptString("VAT REG TIN: 009-772-500-00000",
                        "", context, true);
                finalString += receiptString("",
                        "", context, true);


                DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
                String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd hh:mm:ss");


                finalString += receiptString("ACCRED NO:0430097725002019061099",
                        "", context, true);
                finalString += receiptString("Date Issued : " + Utils.birDateTimeFormat(folderName),
                        "", context, true);
                finalString += receiptString("Valid Until " + Utils.birDateTimeFormat(PrinterUtils.yearPlusFive(folderName)),
                        "", context, true);

                finalString += receiptString("PERMIT NO: ********-***-******", "", context, true);
                finalString += receiptString("Date Issued : " + Utils.birDateTimeFormat(folderName),
                        "", context, true);
                finalString += receiptString("Valid Until " + Utils.birDateTimeFormat(PrinterUtils.yearPlusFive(folderName)),
                        "", context, true);


                finalString += receiptString("-------------", "", context, true);
                finalString += receiptString("PRINTED DATE", "", context, true);
                finalString += receiptString(currentDateTime, "", context, true);
                finalString += receiptString("PRINTED BY", userModel.getUsername(), context, false);


                printerPresenter.printNormal(finalString);
                String finalString1 = finalString;
                ThreadPoolManager.getsInstance().execute(() -> {
                    List<Device> deviceList = PrinterManager.getInstance().getPrinterDevice();
                    if (deviceList == null || deviceList.isEmpty()) return;
                    for (Device device : deviceList) {
                        if (device.type == Cons.Type.PRINT && device.connectType == Cons.ConT.INNER) {
                            continue;
                        }
                        printerPresenter.printByDeviceManager(device, finalString1);
                    }
                });

                asyncFinishCallBack.doneProcessing();


            } else {

            }



        } else {

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
                                        try {
                                            printer.disconnect();
                                        } catch (Epos2Exception e1) {
                                            e1.printStackTrace();
                                        }
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                    PrinterUtils.connect(context, printer);
                } catch (Epos2Exception e) {
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }


                PrinterUtils.addHeader(printModel, printer);

                ZReadResponse.Result zReadResponse = GsonHelper.getGson().fromJson(printModel.getData(), ZReadResponse.Result.class);

                if (zReadResponse != null) {

//                addTextToPrinter(printer, "Z READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "POSTING DATE: " + zReadResponse.getData().getGeneratedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "USER : " + userModel.getUsername(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    if (zReadResponse.getData().getDutyManager() != null) {
                        addTextToPrinter(printer, "MANAGER : " + zReadResponse.getData().getDutyManager().getName(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    } else {
                        addTextToPrinter(printer, "MANAGER : " + "", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    }

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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
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
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }

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


                    addTextToPrinter(printer, "ACCRED NO:0430097725002019061099", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "Date Issued : " + Utils.birDateTimeFormat(folderName), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "Valid Until : " + Utils.birDateTimeFormat(folderName), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                    PrinterUtils.addPtuFooter(printer, context);

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
                    } catch (Epos2Exception e) {
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }



                } else {
                    Toast.makeText(context, "ZREAD IS NULL", Toast.LENGTH_SHORT).show();
                }



            }

        }
        return null;
    }

}
