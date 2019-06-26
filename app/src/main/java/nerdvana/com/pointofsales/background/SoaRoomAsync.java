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
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

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
import static nerdvana.com.pointofsales.PrinterUtils.getDuration;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class SoaRoomAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public SoaRoomAsync(PrintModel printModel, Context context,
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





        TypeToken<List<PrintSoaResponse.Booked>> bookedToken = new TypeToken<List<PrintSoaResponse.Booked>>() {};
        List<PrintSoaResponse.Booked> bookedList = GsonHelper.getGson().fromJson(printModel.getData(), bookedToken.getType());


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

            if (bookedList.get(0).getRoom().getArea().getPrinterPath().isEmpty()) {
                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY))) {
                    try {
                        if (printer != null) {

                            printer.connect(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY), Printer.PARAM_DEFAULT);
                        }
                    } catch (Epos2Exception e) {

                        e.printStackTrace();
                    }
                } else {
                    if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT).isEmpty()) {
                        Toast.makeText(context, "No Printer", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            if (printer != null) {
                                printer.connect(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT), Printer.PARAM_DEFAULT);
                            }
                        } catch (Epos2Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {

                Log.d("TTTTT", bookedList.get(0).getRoom().getArea().getPrinterPath());

                printer.connect("TCP:" + bookedList.get(0).getRoom().getArea().getPrinterPath(), Printer.PARAM_DEFAULT);
            }


        } catch (Epos2Exception e) {
            e.printStackTrace();
        }

        PrinterUtils.addHeader(printModel, printer);


        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "CASHIER",
//                        getUserInfo(String.valueOf(bookedList.get(0).getUserId())),
                userModel.getUsername(),
                40,
                2,
                context)
                , Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "MACHINE NO",
                SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "CHECKED-IN",
                bookedList.get(0).getCheckIn(),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "EXP. CHECK-OUT",
                bookedList.get(0).getExpectedCheckOut(),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "DURATION",
                getDuration(bookedList.get(0).getCheckIn()),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                Double soaTotal = 0.00;
        addPrinterSpace(1);
        addTextToPrinter(printer, "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addPrinterSpace(1);
        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(printer, "QTY   Description         Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);




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
                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        qty+ " "+item,
                        String.valueOf(soaTrans.getPrice() * soaTrans.getQty())
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

        if (bookedList.get(0).getTransaction().getOtHours() > 0) {
            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    String.valueOf(bookedList.get(0).getTransaction().getOtHours()) + " " + "OT HOURS",
                    returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getOtAmount()))
                    ,
                    40,
                    2,context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        }




        addTextToPrinter(printer, "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "   VAT EXEMPT",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getVatExempt())),
                40,
                2,context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "   DISCOUNT",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getDiscount())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "   ADVANCED DEPOSIT",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getAdvance())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "SUB TOTAL",
                returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount())),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "AMOUNT DUE",
                returnWithTwoDecimal(String.valueOf((
                        bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount())
                        - (bookedList.get(0).getTransaction().getDiscount() + bookedList.get(0).getTransaction().getAdvance() + bookedList.get(0).getTransaction().getVatExempt()))),
                40,
                2,
                context)
                ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        if (bookedList.get(0).getTransaction().getDiscounts().size() > 0) {

            addPrinterSpace(2);

            addTextToPrinter(printer, "DISCOUNT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            for (PrintSoaResponse.Discounts d : bookedList.get(0).getTransaction().getDiscounts()) {
//                        addTextToPrinter(printer, d.getDiscountType(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                if (d.getDiscountTypeId().equalsIgnoreCase("0")) { //MANUAL
                    addTextToPrinter(printer, "    " + d.getDiscountReason().getDiscountReason(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                } else {
                    if (d.getInfo() != null) {
                        if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {
                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    d.getDiscountType(),
                                    "NA",
                                    40,
                                    2,
                                    context)
                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        } else {

                            if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        d.getDiscountType(),
                                        "NA",
                                        40,
                                        2,
                                        context)
                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            } else {

                                if (d.getInfo().getCardNo() != null) {
                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            d.getDiscountType(),
                                            d.getInfo().getCardNo().toUpperCase(),
                                            40,
                                            2,
                                            context)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }


                                if (d.getInfo().getName() != null) {
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


        addPrinterSpace(1);


        if (bookedList.get(0).getTransaction() != null) {
            if (bookedList.get(0).getTransaction().getCustomerTrans() != null) {

                if (!bookedList.get(0).getTransaction().getCustomerTrans().getCustomer().equalsIgnoreCase("EMPTY") && !bookedList.get(0).getTransaction().getCustomerTrans().getCustomer().equalsIgnoreCase("to be filled")) {

                    addTextToPrinter(printer, "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


                    addTextToPrinter(printer, bookedList.get(0).getTransaction().getCustomerTrans().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                    if (bookedList.get(0).getTransaction().getCustomerTrans().getTin() != null) {
                        addTextToPrinter(printer, bookedList.get(0).getTransaction().getCustomerTrans().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    }

                    if (bookedList.get(0).getTransaction().getCustomerTrans().getAddress() != null) {
                        addTextToPrinter(printer, bookedList.get(0).getTransaction().getCustomerTrans().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    }


                }



            }
        }



        addPrinterSpace(1);

        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                "SOA NO:",
                bookedList.get(0).getTransaction().getSoaCount(),
                40,
                2,
                context)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

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


        if (!SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY).isEmpty()) {
            new SPrinter(
                    Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE)),
                    context);
            try {
                printer.connect(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY), Printer.PARAM_DEFAULT);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        } else {
            if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT).isEmpty()) {
                Toast.makeText(context, "No Printer", Toast.LENGTH_SHORT).show();
            } else {
                new SPrinter(
                        Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)),
                        Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE)),
                        context);

                try {
                    printer.connect(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT), Printer.PARAM_DEFAULT);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }
            }
        }

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

}
