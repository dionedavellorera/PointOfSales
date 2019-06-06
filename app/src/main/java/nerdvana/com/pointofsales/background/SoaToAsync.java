package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
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

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    public SoaToAsync(PrintModel printModel, Context context,
                      UserModel userModel, String currentDateTime,
                      MainActivity.AsyncFinishCallBack asyncFinishCallBack) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.asyncFinishCallBack = asyncFinishCallBack;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

        PrintSoaResponse.Result toList = GsonHelper.getGson().fromJson(printModel.getData(), PrintSoaResponse.Result.class)
                ;
//
//
//        addTextToPrinter(printer, toList.getCreatedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
        addPrinterSpace(1);
        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "MACHINE NO",
                SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


        addTextToPrinter(printer, "SOA-TAKE OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(printer, "QTY   DESCRIPTION         AMOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        qty+ " "+item,
                        String.valueOf(Double.valueOf(soaTrans.getPrice()) * Integer.valueOf(soaTrans.getQty()))
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                if (soaTrans.getPostAlaCartList().size() > 0) {
                    for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getPostAlaCartList()) {
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
//
        addTextToPrinter(printer, "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "   VAT EXEMPT",
                returnWithTwoDecimal(String.valueOf(toList.getVatExempt())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "   DISCOUNT",
                returnWithTwoDecimal(String.valueOf(toList.getDiscount())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "   ADVANCED DEPOSIT",
                returnWithTwoDecimal(String.valueOf(toList.getAdvance())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "SUB TOTAL",
                returnWithTwoDecimal(String.valueOf(toList.getTotal())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "AMOUNT DUE",
                returnWithTwoDecimal(String.valueOf((Double.valueOf(toList.getTotal())) - (Double.valueOf(toList.getDiscount()) + Double.valueOf(toList.getAdvance()) + Double.valueOf(toList.getVatExempt())))),
                40,
                2,
                context)
                ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
        addPrinterSpace(2);
//
        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "SOA NO:",
                toList.getSoaCount(),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
        addPrinterSpace(1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "VATABLE SALES",
                returnWithTwoDecimal(String.valueOf(toList.getVatable())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "VAT-EXEMPT SALES",
                returnWithTwoDecimal(String.valueOf(toList.getVatExemptSales())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "12% VAT",
                returnWithTwoDecimal(String.valueOf(toList.getVat())),
                40,
                2,context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addPrinterSpace(1);

        if (toList.getCustomer() != null) {
            if (!toList.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {
                addTextToPrinter(printer, "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, toList.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                if (toList.getCustomer().getAddress() != null) {
                    addTextToPrinter(printer, toList.getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                }

                if (toList.getCustomer().getTin() != null) {
                    addTextToPrinter(printer, toList.getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                }

            }
        }

        addPrinterSpace(1);
        addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


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



}
