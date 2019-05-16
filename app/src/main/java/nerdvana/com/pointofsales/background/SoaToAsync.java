package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.MainActivity.formatSeconds;
import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterLr;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class SoaToAsync extends AsyncTask<Void, Void, Void> {


    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    public SoaToAsync(PrintModel printModel, Context context,
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

        PrintSoaResponse.Result toList = GsonHelper.getGson().fromJson(printModel.getData(), PrintSoaResponse.Result.class)
                ;
//
//
        addTextToPrinter(SPrinter.getPrinter(), toList.getCreatedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
        addPrinterSpace(1);
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "MACHINE NO",
                SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//
        addTextToPrinter(SPrinter.getPrinter(), "SOA-TAKE OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "QTY   Description             Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        for (PrintSoaResponse.SoaToPost soaTrans : toList.getToPostList()) {

            String qty = "";

            qty += soaTrans.getQty();
            if (String.valueOf(soaTrans.getQty()).length() < 4) {
                for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                    qty += " ";
                }
            }
            String item = "";
            item =soaTrans.getToProduct().getProductInitial();

            if (soaTrans.getVoidd().equalsIgnoreCase("0")) {
                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        qty+ " "+item,
                        String.valueOf(Double.valueOf(soaTrans.getPrice()) * Integer.valueOf(soaTrans.getQty()))
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            }
        }
//
        addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "   VAT EXEMPT",
                returnWithTwoDecimal(String.valueOf(toList.getVatExempt())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "   DISCOUNT",
                returnWithTwoDecimal(String.valueOf(toList.getDiscount())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "   ADVANCED DEPOSIT",
                returnWithTwoDecimal(String.valueOf(toList.getAdvance())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "SUB TOTAL",
                returnWithTwoDecimal(String.valueOf(toList.getTotal())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "AMOUNT DUE",
                returnWithTwoDecimal(String.valueOf((Double.valueOf(toList.getTotal())) - (Double.valueOf(toList.getDiscount()) + Double.valueOf(toList.getAdvance()) + Double.valueOf(toList.getVatExempt())))),
                40,
                2)
                ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);
//
        addPrinterSpace(1);
//
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "SOA NO:",
                toList.getSoaCount(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
        addPrinterSpace(1);

        if (toList.getCustomer() != null) {
            if (!toList.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {
                addTextToPrinter(SPrinter.getPrinter(), "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), toList.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), toList.getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), toList.getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
            }
        }



        addPrinterSpace(1);
//                addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

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




    }



}
