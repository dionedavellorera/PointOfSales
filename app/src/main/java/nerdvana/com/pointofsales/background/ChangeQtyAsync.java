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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class ChangeQtyAsync extends AsyncTask<Void, Void, Void> {
    private PrintModel printModel;
    private Context context;
    private UserModel userModel;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public ChangeQtyAsync(PrintModel printModel, Context context, UserModel userModel, MainActivity.AsyncFinishCallBack asyncFinishCallBack) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.asyncFinishCallBack = asyncFinishCallBack;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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

            if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
                addTextToPrinter(printer,"ROOM #" + printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            } else {
                addTextToPrinter(printer,"TAKEOUT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }

            addPrinterSpace(1);
            addTextToPrinter(printer, "VOID QUANTITY", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);



            CartItemsModel cim = GsonHelper.getGson().fromJson(printModel.getData(), CartItemsModel.class);


            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    cim.getName(),
                    String.valueOf(cim.getQuantity() - printModel.getNewQty()),
                    40,
                    2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            try {
                printer.addCut(Printer.CUT_FEED);
                if (printer.getStatus().getConnection() == 1) {
                    printer.sendData(Printer.PARAM_DEFAULT);
                    printer.clearCommandBuffer();
                }
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }



        } else {
            Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
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
}
