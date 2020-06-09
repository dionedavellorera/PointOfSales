package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sunmi.devicemanager.cons.Cons;
import com.sunmi.devicemanager.device.Device;
import com.sunmi.devicesdk.core.PrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class XReadAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    public XReadAsync(PrintModel printModel, Context context,
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

//            finalString += receiptString("X READING", "", context, true);

            try {


                JSONObject jsonObject = new JSONObject(printModel.getData());
                JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                JSONArray dataCashAndRecoJsonObject = jsonObject.getJSONObject("data").getJSONArray("cash_and_reco");
                JSONObject cashierDataObject = jsonObject.getJSONObject("data").getJSONObject("cashier");
                JSONObject dutyManager = jsonObject.getJSONObject("data").getJSONObject("duty_manager");
                if (dataJsonObject != null) {

                    finalString += receiptString("X READING", "", context, true);
                    finalString += receiptString("POSTING DATE: " + dataJsonObject.getString("cut_off_date"), "", context, true);
                    finalString += receiptString("SHIFT : " + (dataJsonObject.getString("shift_no") != null ? dataJsonObject.getString("shift_no") : " NA"), "", context, true);
                    finalString += receiptString("USER : " + userModel.getUsername(), "", context, true);
                    finalString += receiptString("MANAGER : " + dutyManager.getString("name"), "", context, true);
                    finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
                    finalString += receiptString("DESCRIPTION                VALUE", "", context, true);
                    finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);

                    finalString += receiptString("TERMINAL NO", dataJsonObject.getString("pos_id"), context, false);


                    finalString += receiptString("", "", context, false);
                    finalString += receiptString("Gross Sales", returnWithTwoDecimal(dataJsonObject.getString("gross_sales")), context, false);
                    finalString += receiptString("Net Sales", returnWithTwoDecimal(String.valueOf(Double.valueOf(dataJsonObject.getString("net_sales")))), context, false);
                    finalString += receiptString("", "", context, false);
                    finalString += receiptString("VATable Sales", returnWithTwoDecimal(dataJsonObject.getString("vatable")), context, false);
                    finalString += receiptString("VAT EXEMPT SALES", returnWithTwoDecimal(dataJsonObject.getString("vat_exempt_sales")), context, false);
                    finalString += receiptString("VAT DISCOUNT", returnWithTwoDecimal(dataJsonObject.getString("vat_exempt")), context, false);
                    finalString += receiptString("VAT AMOUNT", returnWithTwoDecimal(dataJsonObject.getString("vat")), context, false);



                }


                JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");


                Double change = jsonObject.getDouble("change");

                addPrinterSpace(1);


                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                    TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                    };
                    List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());

                    Double totalAdvancePayment = 0.00;
                    for (int i = 0; i < paymentJsonArray.length(); i++) {
                        JSONObject temp = paymentJsonArray.getJSONObject(i);
                        if (temp.getString("is_advance").equalsIgnoreCase("1") || temp.getString("is_advance").equalsIgnoreCase("1.0")) {
                            totalAdvancePayment += Double.valueOf(temp.getString("amount"));
                        }
                    }




                    List<PaymentPrintModel> paymentPrintModels = new ArrayList<>();
                    for (FetchPaymentResponse.Result payment : paymentTypeList) {

                        Double value = 0.00;
                        String isAdvance = "0";
                        for (int i = 0; i < paymentJsonArray.length(); i++) {
                            JSONObject temp = paymentJsonArray.getJSONObject(i);
                            if (temp.getString("payment_description").equalsIgnoreCase(payment.getPaymentType())) {
                                value = Double.valueOf(temp.getString("amount"));
                                isAdvance = temp.getString("is_advance");
                                break;
                            }
                        }




                        if (payment.getPaymentType().equalsIgnoreCase("cash") || payment.getPaymentType().equalsIgnoreCase("card")) {

                            if (isAdvance.equalsIgnoreCase("1")) {

                                paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));

                                finalString += receiptString(payment.getPaymentType(), "0.00", context, false);

                            } else {

                                if (payment.getPaymentType().equalsIgnoreCase("cash")) {

                                    finalString += receiptString(payment.getPaymentType() + " Sales", PrinterUtils.returnWithTwoDecimal(String.valueOf(value + change)), context, false);

                                } else {

                                    finalString += receiptString(payment.getPaymentType() + " Sales", PrinterUtils.returnWithTwoDecimal(String.valueOf(value)), context, false);

                                }


//                                if (payment.getPaymentType().equalsIgnoreCase("card")) {
//
//                                    finalString += receiptString("DEPOSIT SALES", PrinterUtils.returnWithTwoDecimal(String.valueOf(totalAdvancePayment)), context, false);
//
//                                }
                            }

                        } else {

                            if (value > 0) {
                                if (isAdvance.equalsIgnoreCase("1")) {

                                    paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                } else {

                                    finalString += receiptString(payment.getPaymentType(), String.valueOf(value), context, false);

                                }
                            }
                        }
                    }

                }

                finalString += receiptString("", "", context, false);



                JSONObject cashRecoObj = dataCashAndRecoJsonObject.getJSONObject(0);


                finalString += receiptString("VOID", returnWithTwoDecimal(String.valueOf(dataJsonObject.get("void_amount"))), context, false);


                JSONArray discountJsonArray = jsonObject.getJSONArray("discount");
                addPrinterSpace(1);
                if (discountJsonArray.length() > 0) {

                    finalString += receiptString("DISCOUNT LIST", "", context, false);




                    TypeToken<List<FetchDiscountSpecialResponse.Result>> discToken = new TypeToken<List<FetchDiscountSpecialResponse.Result>>() {};
                    List<FetchDiscountSpecialResponse.Result> discountDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.DISCOUNT_SPECIAL_JSON), discToken.getType());




                    double otherDiscAmount = 0.00;

                    if (discountDetails != null) {
                        for (FetchDiscountSpecialResponse.Result d : discountDetails) {
                            Integer count = 0;
                            Double amount = 0.00;



                            if (discountJsonArray.length() > 0) {
                                for (int i = 0; i < discountJsonArray.length(); i++) {
                                    JSONObject temp = discountJsonArray.getJSONObject(i);
                                    if (temp.getString("is_special").equalsIgnoreCase("1") || temp.getString("is_special").equalsIgnoreCase("1.0")) {

                                        if (temp.getString("discount_type_id").equalsIgnoreCase(String.valueOf(d.getId()))) {
                                            amount = Double.valueOf(temp.getString("discount_amount"));
                                            count = Integer.valueOf(temp.getString("count"));

                                        }


                                    }
//                                    else {
//                                        otherDiscAmount += Double.valueOf(temp.getString("discount_amount"));
//                                    }
                                }
                            }

                            finalString += receiptString(d.getDiscountCard(), amount > 0 ? "-" + returnWithTwoDecimal(String.valueOf(amount)) : returnWithTwoDecimal(String.valueOf(amount)), context, false);
                            finalString += receiptString(d.getDiscountCard() + "(COUNT)", String.valueOf(count), context, false);





                        }



                    }

                    for (int i = 0; i < discountJsonArray.length(); i++) {
                        JSONObject temp = discountJsonArray.getJSONObject(i);
                        if (!temp.getString("is_special").equalsIgnoreCase("1") && !temp.getString("is_special").equalsIgnoreCase("1.0")) {

                            otherDiscAmount += Double.valueOf(temp.getString("discount_amount"));


                        }
//                        else {
//
//                        }
                    }


                    finalString += receiptString("OTHERS", otherDiscAmount > 0 ? "-" + returnWithTwoDecimal(String.valueOf(otherDiscAmount)) : returnWithTwoDecimal(String.valueOf(otherDiscAmount)), context, false);


                    int otherDiscCount = 0;
                } else {

                    finalString += receiptString("SENIOR CITIZEN", "0.00", context, false);
                    finalString += receiptString("SENIOR CITIZEN" + "(COUNT)", "0", context, false);
                    finalString += receiptString("PWD", "0.00", context, false);
                    finalString += receiptString("PWD(COUNT)", "0", context, false);
                    finalString += receiptString("OTHERS", "0.00", context, false);

                }

                finalString += receiptString("", "", context, false);

                finalString += receiptString("------ END OF REPORT ------", "", context, true);
                finalString += receiptString("---------------", "", context, true);
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


                finalString += receiptString("---------------", "", context, true);
                finalString += receiptString("PRINTED DATE", "", context, true);
                finalString += receiptString(currentDateTime, "", context, true);
                finalString += receiptString("PRINTED BY:", userModel.getUsername(), context, true);

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

            } catch (JSONException e) {

            }



        } else {
            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)) &&
                    !TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE))) {

                try {
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

                    JSONObject jsonObject = new JSONObject(printModel.getData());


                    JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                    JSONArray dataCashAndRecoJsonObject = jsonObject.getJSONObject("data").getJSONArray("cash_and_reco");
                    JSONObject cashierDataObject = jsonObject.getJSONObject("data").getJSONObject("cashier");
                    JSONObject dutyManager = jsonObject.getJSONObject("data").getJSONObject("duty_manager");
                    if (dataJsonObject != null) {
                        addTextToPrinter(printer, "X READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, "POSTING DATE: " + dataJsonObject.getString("cut_off_date"), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, "SHIFT : " + (dataJsonObject.getString("shift_no") != null ? dataJsonObject.getString("shift_no") : " NA"), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//                        addTextToPrinter(printer, "USER : " + cashierDataObject.getString("name"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, "USER : " + userModel.getUsername(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, "MANAGER : " + dutyManager.getString("name"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(printer, "DESCRIPTION                VALUE", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "TERMINAL NO",
                                dataJsonObject.getString("pos_id")
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addPrinterSpace(1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "Gross Sales",
                                returnWithTwoDecimal(dataJsonObject.getString("gross_sales"))
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "Net Sales",
                                returnWithTwoDecimal(String.valueOf(Double.valueOf(dataJsonObject.getString("net_sales"))))
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addPrinterSpace(1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "VATable Sales",
                                returnWithTwoDecimal(dataJsonObject.getString("vatable"))
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "VAT EXEMPT SALES",
                                returnWithTwoDecimal(dataJsonObject.getString("vat_exempt_sales"))
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "VAT DISCOUNT",
                                returnWithTwoDecimal(dataJsonObject.getString("vat_exempt"))
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "VAT AMOUNT",
                                returnWithTwoDecimal(dataJsonObject.getString("vat"))
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "ZERO-RATED SALES",
                                "0.00",
                                40,
                                2,
                                context)
                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                            "SERVICE CHARGE",
//                            "0.00"
//                            ,
//                            40,
//                            2,
//                            context),
//                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    }


                    JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");



                    Double change = jsonObject.getDouble("change");

                    addPrinterSpace(1);


                    if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                        TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                        };
                        List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());

                        Double totalAdvancePayment = 0.00;
                        for (int i = 0; i < paymentJsonArray.length(); i++) {
                            JSONObject temp = paymentJsonArray.getJSONObject(i);
                            if (temp.getString("is_advance").equalsIgnoreCase("1") || temp.getString("is_advance").equalsIgnoreCase("1.0")) {
                                totalAdvancePayment += Double.valueOf(temp.getString("amount"));
                            }
                        }




                        List<PaymentPrintModel> paymentPrintModels = new ArrayList<>();
                        for (FetchPaymentResponse.Result payment : paymentTypeList) {

                            Double value = 0.00;
                            String isAdvance = "0";
                            for (int i = 0; i < paymentJsonArray.length(); i++) {
                                JSONObject temp = paymentJsonArray.getJSONObject(i);
                                if (temp.getString("payment_description").equalsIgnoreCase(payment.getPaymentType())) {
                                    value += Double.valueOf(temp.getString("amount"));
                                    isAdvance = temp.getString("is_advance");
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
                                            2,
                                            context),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {

                                    if (payment.getPaymentType().equalsIgnoreCase("cash")) {


                                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                payment.getPaymentType() + " Sales",
                                                PrinterUtils.returnWithTwoDecimal(String.valueOf(value + change))
                                                ,
                                                40,
                                                2,
                                                context),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    } else {
                                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                payment.getPaymentType() + " Sales",
                                                PrinterUtils.returnWithTwoDecimal(String.valueOf(value))
                                                ,
                                                40,
                                                2,
                                                context),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }


                                    if (payment.getPaymentType().equalsIgnoreCase("card")) {
//                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                                            "DEPOSIT SALES",
//                                            PrinterUtils.returnWithTwoDecimal(String.valueOf(totalAdvancePayment))
//                                            ,
//                                            40,
//                                            2,
//                                            context),
//                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }
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

                    addPrinterSpace(1);


                    JSONObject cashRecoObj = dataCashAndRecoJsonObject.getJSONObject(0);

//                if (Double.valueOf(cashRecoObj.getString("adjustment_deposit")) > 0) {
//                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                            "DEPOSIT ADJ.",
//                            String.format("-%s",
//                                    String.valueOf(Double.valueOf(cashRecoObj.getString("adjustment_deposit")))),
//                            40,
//                            2,
//                            context),
//                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                } else {
//                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                            "DEPOSIT ADJ.",
//                            String.format("%s",
//                                    String.valueOf(Double.valueOf(cashRecoObj.getString("adjustment_deposit")))),
//                            40,
//                            2,
//                            context),
//                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                }



//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "CASH OUT",
//                    "0.00",
//                    40,
//                    2,
//                    context),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "REFUND",
//                    "0.00"
//                    ,
//                    40,
//                    2,
//                    context),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "VOID",
                            returnWithTwoDecimal(String.valueOf(dataJsonObject.get("void_amount")))
                            ,
                            40,
                            2,
                            context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);




                    JSONArray discountJsonArray = jsonObject.getJSONArray("discount");
                    addPrinterSpace(1);
                    if (discountJsonArray.length() > 0) {
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




                        double otherDiscAmount = 0.00;

                        if (discountDetails != null) {
                            for (FetchDiscountSpecialResponse.Result d : discountDetails) {
                                Integer count = 0;
                                Double amount = 0.00;



                                if (discountJsonArray.length() > 0) {
                                    for (int i = 0; i < discountJsonArray.length(); i++) {
                                        JSONObject temp = discountJsonArray.getJSONObject(i);
                                        if (temp.getString("is_special").equalsIgnoreCase("1") || temp.getString("is_special").equalsIgnoreCase("1.0")) {

                                            if (temp.getString("discount_type_id").equalsIgnoreCase(String.valueOf(d.getId()))) {
                                                amount = Double.valueOf(temp.getString("discount_amount"));
                                                count = Integer.valueOf(temp.getString("count"));

                                            }
                                        }
//                                    else {
//                                        otherDiscAmount += Double.valueOf(temp.getString("discount_amount"));
//                                    }
                                    }
                                }

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


                        for (int i = 0; i < discountJsonArray.length(); i++) {
                            JSONObject temp = discountJsonArray.getJSONObject(i);
                            if (!temp.getString("is_special").equalsIgnoreCase("1") && !temp.getString("is_special").equalsIgnoreCase("1.0")) {
                                otherDiscAmount += Double.valueOf(temp.getString("discount_amount"));
                            }

                        }


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "OTHERS",
                                returnWithTwoDecimal(String.valueOf(otherDiscAmount))
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                        int otherDiscCount = 0;
//                double otherDiscAmount = 0.00;


//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "OTHERS" + "(COUNT)",
//                        String.valueOf(otherDiscCount)
//                        ,
//                        40,
//                        2),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    } else {

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "DISCOUNT LIST",
                                ""
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "SENIOR ",
                                "0.00"
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "SENIOR " + "(COUNT)",
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
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "OTHERS" + "(COUNT)",
//                        "0"
//                        ,
//                        40,
//                        2),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

//                    String shortOver = "0.00";
//                    try {
//                        JSONObject jsonObject = new JSONObject(printModel.getData());
//
//                        shortOver = jsonObject.getString("short_over");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }




                    }

                    addPrinterSpace(1);




//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "SHORT / OVER",
//                        String.valueOf(jsonObject.getString("short_over"))
//                        ,
//                        40,
//                        2,
//                        context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                addPrinterSpace(1);

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
                    addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);





                    //short over
                    try {
                        printer.addCut(Printer.CUT_FEED);

                        addTextToPrinter(printer, "SHORT OVER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addPrinterSpace(1);
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "SHORT / OVER",
                                String.valueOf(jsonObject.getString("short_over"))
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addPrinterSpace(1);
                        addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                        addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    } catch (Epos2Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {

                }
//            catch (Epos2Exception e) {
//                e.printStackTrace();
//            }

                try {

                    printer.addCut(Printer.CUT_FEED);

                    if (printer.getStatus().getConnection() == 1) {
                        printer.sendData(Printer.PARAM_DEFAULT);
                        printer.clearCommandBuffer();
                    }


//            printer.endTransaction();
                } catch (Epos2Exception e) {
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }



            }
        }


        return null;
    }
}
