package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.google.gson.reflect.TypeToken;

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
import nerdvana.com.pointofsales.api_responses.FetchDiscountSpecialResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

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


    public XReadAsync(PrintModel printModel, Context context,
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

            JSONObject jsonObject = new JSONObject(printModel.getData());
            JSONObject dataJsonObject = jsonObject.getJSONObject("data");
            JSONObject cashierDataObject = jsonObject.getJSONObject("data").getJSONObject("cashier");
            JSONObject dutyManager = jsonObject.getJSONObject("data").getJSONObject("duty_manager");
            if (dataJsonObject != null) {
//                        Log.d("TESXXXXTDATA", jsonObject.toString());
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
                        "MACHINE NO.",
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
                        returnWithTwoDecimal(dataJsonObject.getString("net_sales"))
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
                        "12% VAT",
                        returnWithTwoDecimal(dataJsonObject.getString("vat"))
                        ,
                        40,
                        2,context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "NON VAT",
                        returnWithTwoDecimal(dataJsonObject.getString("vat_exempt"))
                        ,
                        40,
                        2,context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "SERVICE CHARGE",
                        "0.00"
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            }


            JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");

            addPrinterSpace(1);


            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                };
                List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());
//                        Log.d("TESZZ", String.valueOf(paymentTypeList.size()));


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

//                                Log.d("TEKTEK", payment.getPaymentType() + " - " + String.valueOf(isAdvance));
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


                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    payment.getPaymentType() + " Sales",
                                    String.valueOf(value)
                                    ,
                                    40,
                                    2,
                                    context),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                            if (payment.getPaymentType().equalsIgnoreCase("card")) {
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "DEPOSIT SALES",
                                        String.valueOf(totalAdvancePayment)
                                        ,
                                        40,
                                        2,
                                        context),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASH OUT",
                    "0.00"
                    ,
                    40,
                    2,
                    context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "REFUND",
                    "0.00"
                    ,
                    40,
                    2,
                    context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

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

                Log.d("TETETE", "XREADASYNC");
                Log.d("TETETE", String.valueOf(discountDetails.size()));


                double otherDiscAmount = 0.00;


                if (discountDetails != null) {

                    Log.d("TURY", discountDetails.get(0).getDiscountCard());


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


                                } else {
                                    otherDiscAmount += Double.valueOf(temp.getString("discount_amount"));
                                }
                            }
                        }

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                d.getDiscountCard(),
                                String.valueOf(amount)
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



                int otherDiscCount = 0;
//                double otherDiscAmount = 0.00;

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "OTHERS",
                        String.valueOf(otherDiscAmount)
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "OTHERS" + "(COUNT)",
//                        String.valueOf(otherDiscCount)
//                        ,
//                        40,
//                        2),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

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
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "OTHERS" + "(COUNT)",
//                        "0"
//                        ,
//                        40,
//                        2),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            }

            addPrinterSpace(1);

            addTextToPrinter(printer, "------ END OF REPORT ------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);





            //short over
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



        } catch (JSONException e) {
            Log.d("ERROR", e.getMessage());
        } catch (Epos2Exception e) {
            e.printStackTrace();
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
}
