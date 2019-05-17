package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.MainActivity.formatSeconds;
import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.getDuration;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class SoaRoomAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    public SoaRoomAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {


        PrinterUtils.addHeader(printModel);


        TypeToken<List<PrintSoaResponse.Booked>> bookedToken = new TypeToken<List<PrintSoaResponse.Booked>>() {};
        List<PrintSoaResponse.Booked> bookedList = GsonHelper.getGson().fromJson(printModel.getData(), bookedToken.getType());

        /*Log.d("PEPEDATA", twoColumnsRightGreaterTr(
                "CASHIER",
                userModel.getUsername(), 40, 2));*/

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "CASHIER",
//                        getUserInfo(String.valueOf(bookedList.get(0).getUserId())),
                userModel.getUsername(),
                40,
                2)
                , Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "MACHINE NO",
                SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "CHECKED-IN",
                bookedList.get(0).getCheckIn(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "EXP. CHECK-OUT",
                bookedList.get(0).getExpectedCheckOut(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "DURATION",
                getDuration(bookedList.get(0).getCheckIn()),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                Double soaTotal = 0.00;
        addPrinterSpace(1);
        addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        addPrinterSpace(1);
        addTextToPrinter(SPrinter.getPrinter(), "--------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "QTY   Description             Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "--------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        for (PrintSoaResponse.Post soaTrans : bookedList.get(0).getTransaction().getPost()) {

            String qty = "";

            qty += soaTrans.getQty();
            if (String.valueOf(soaTrans.getQty()).length() < 4) {
                for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                    qty += " ";
                }
            }
            String item = "";
            if (soaTrans.getProduct() != null) {
                item =soaTrans.getProduct().getProductInitial();
            } else {
                item = soaTrans.getRoomRate().toString();
            }

            if (soaTrans.getVoid() == 0) {
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        qty+ " "+item,
                        String.valueOf(soaTrans.getPrice() * soaTrans.getQty())
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }
        }

        if (bookedList.get(0).getTransaction().getOtHours() > 0) {
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    String.valueOf(bookedList.get(0).getTransaction().getOtHours()) + " " + "OT HOURS",
                    returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getOtAmount()))
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        }




        addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "   VAT EXEMPT",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getVatExempt())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "   DISCOUNT",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getDiscount())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "   ADVANCED DEPOSIT",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getAdvance())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "SUB TOTAL",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getTotal())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "AMOUNT DUE",
                returnWithTwoDecimal(String.valueOf((
                        bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount())
                        - (bookedList.get(0).getTransaction().getDiscount() + bookedList.get(0).getTransaction().getAdvance() + bookedList.get(0).getTransaction().getVatExempt()))),
                40,
                2)
                ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);

        if (bookedList.get(0).getTransaction().getDiscounts().size() > 0) {

            addPrinterSpace(2);

            addTextToPrinter(SPrinter.getPrinter(), "DISCOUNT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            for (PrintSoaResponse.Discounts d : bookedList.get(0).getTransaction().getDiscounts()) {
//                        addTextToPrinter(SPrinter.getPrinter(), d.getDiscountType(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                if (d.getDiscountTypeId().equalsIgnoreCase("0")) { //MANUAL
                    addTextToPrinter(SPrinter.getPrinter(), "    " + d.getDiscountReason().getDiscountReason(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                } else {
                    if (d.getInfo() != null) {
                        if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {
                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    d.getDiscountType(),
                                    "NA",
                                    40,
                                    2)
                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        } else {

                            if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {
                                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                        d.getDiscountType(),
                                        "NA",
                                        40,
                                        2)
                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            } else {
                                if (d.getInfo().getCardNo() == null) {

                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            d.getDiscountType(),
                                            d.getInfo().getName().toUpperCase(),
                                            40,
                                            2)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                                    addTextToPrinter(SPrinter.getPrinter(), "    " +d.getInfo().getName().toUpperCase(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {

                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            d.getDiscountType(),
                                            d.getInfo().getCardNo().toUpperCase(),
                                            40,
                                            2)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                                    addTextToPrinter(SPrinter.getPrinter(), "    " +d.getInfo().getCardNo().toUpperCase(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }
                            }


                        }

                    }
                }

            }

        }


        addPrinterSpace(1);

        if (bookedList.get(0).getCustomer() != null) {
            if (!bookedList.get(0).getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !bookedList.get(0).getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {
                addTextToPrinter(SPrinter.getPrinter(), "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

                if (bookedList.get(0).getCustomer().getAddress() != null) {
                    addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                }

                if (bookedList.get(0).getCustomer().getTin() != null) {
                    addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                }


//                addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
//                addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
            }
        }



        addPrinterSpace(1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "SOA NO:",
                bookedList.get(0).getTransaction().getSoaCount(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addPrinterSpace(1);

        addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
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
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

//        try {
//
//            SPrinter.getPrinter().addCut(Printer.CUT_FEED);
//
//            if (SPrinter.getPrinter().getStatus().getConnection() == 1) {
//                SPrinter.getPrinter().sendData(Printer.PARAM_DEFAULT);
//                SPrinter.getPrinter().clearCommandBuffer();
//            }
//
//
////            SPrinter.getPrinter().endTransaction();
//        } catch (Epos2Exception e) {
//            e.printStackTrace();
//        }



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

//    private String twoColumnsRightGreaterTr(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
//        String finalString = "";
//        int filler = 0;
//        if (partOne.length() < 20) {
//            filler += (20 - partOne.length());
//            finalString += partOne;
//        } else {
//            finalString += partOne.substring(0, 20);
//        }
//
//        if (partTwo.length() < 20) {
//            filler += (20 - partTwo.length());
//        }
//
//        for (int i=0;i<filler;i++) {
//            finalString += " ";
//        }
//
//
//        finalString += (partTwo.length() >= 20 ? partTwo.substring(0, 20) : partTwo);
//
//
//
////        float column1 = 20;
////        float column2 = 20;
////        if (partOne.length() >= 20) {
////            finalString += partOne.substring(0, 20);
////        } else {
////            finalString += partOne;
////
////            for (int i = 0; i < column1 - partOne.length(); i++) {
////                finalString += " ";
////            }
////        }
////
////        if (partTwo.length() >= 20) {
////            finalString += partTwo.substring(0, 20);
////        } else {
////
////            for (int i = 0; i < column2 - partTwo.length(); i++) {
////                finalString += " ";
////            }
////
////            finalString += partTwo;
////        }
//
//
//        return finalString;
//    }


}
