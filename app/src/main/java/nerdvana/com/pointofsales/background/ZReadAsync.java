package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
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

public class ZReadAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    public ZReadAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        ZReadResponse.Result zReadResponse = GsonHelper.getGson().fromJson(printModel.getData(), ZReadResponse.Result.class);

        if (zReadResponse != null) {

            addTextToPrinter(SPrinter.getPrinter(), "Z READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
            addTextToPrinter(SPrinter.getPrinter(), "POSTING DATE: " + zReadResponse.getData().getGeneratedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "USER : " + userModel.getUsername(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "MANAGER : " + zReadResponse.getData().getDutyManager().getName(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "DESCRIPTION                      VALUE", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "MACHINE NO.",
                    String.valueOf(zReadResponse.getData().getPosId())
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addPrinterSpace(1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "Gross Sales",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getGrossSales()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "Net Sales",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getNetSales()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "VATable SALES",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatable()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "VAT EXEMPT SALES",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExemptSales()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "12% VAT",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVat()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "NON VAT",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "SERVICE CHARGE",
                    "0.00"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



//                    zReadResponse.getResult().getPayment()

//                    JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");

            addPrinterSpace(1);


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
                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    payment.getPaymentType(),
                                    "0.00"
                                    ,
                                    40,
                                    2),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        } else {
                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    payment.getPaymentType() + " Sales",
                                    String.valueOf(value)
                                    ,
                                    40,
                                    2),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                            if (payment.getPaymentType().equalsIgnoreCase("card")) {
                                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                        "Deposit Sales",
                                        String.valueOf(totalAdvancePayment)
                                        ,
                                        40,
                                        2),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }

                    } else {

                        if (value > 0) {
                            if (isAdvance.equalsIgnoreCase("1")) {

                                paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                            } else {
                                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                        payment.getPaymentType(),
                                        String.valueOf(value)
                                        ,
                                        40,
                                        2),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }
                    }
                }
            }

            addPrinterSpace(1);
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CASH OUT",
                    "0.00"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "REFUND",
                    "0.00"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "Void",
                    returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVoidAmount()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);
            if (zReadResponse.getDiscount().size() > 0) {
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "DISCOUNT LIST",
                        ""
                        ,
                        40,
                        2),
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
                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    d.getDiscountCard(),
                                    returnWithTwoDecimal(String.valueOf(amount))
                                    ,
                                    40,
                                    2),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    d.getDiscountCard() + "(COUNT)",
                                    String.valueOf(count)
                                    ,
                                    40,
                                    2),
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

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "OTHERS(AMOUNT)",
                        String.valueOf(otherDiscAmount)
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "OTHERS(COUNT)",
                        String.valueOf(otherDiscCount)
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);




            } else {

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "SENIOR CITIZEN",
                        "0.00"
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "SENIOR CITIZEN" + "(COUNT)",
                        "0"
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "PWD",
                        "0.00"
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "PWD" + "(COUNT)",
                        "0"
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "OTHERS",
                        "0.00"
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "OTHERS" + "(COUNT)",
                        "0"
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }

            addPrinterSpace(1);

            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "BEGINNING TRANS",
                    zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : "NA"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "ENDING TRANS",
                    zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : "NA"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "NEW GRAND TOTAL",
                    String.valueOf(zReadResponse.getNewGrandTotal())
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "OLD GRAND TOTAL",
                    String.valueOf(zReadResponse.getOldGrandTotal())
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);


            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "Z-READ NO",
                    String.valueOf(zReadResponse.getCount())
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "------ END OF REPORT ------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);




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
            Toast.makeText(context, "ZREAD IS NULL", Toast.LENGTH_SHORT).show();
        }



        return null;
    }
}
