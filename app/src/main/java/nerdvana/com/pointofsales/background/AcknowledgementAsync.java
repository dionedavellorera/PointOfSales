package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class AcknowledgementAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public AcknowledgementAsync(PrintModel printModel, Context context,
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
            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)) &&
                    !TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE))) {


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

                PrinterUtils.addHeader(printModel, printer);

                addPrinterSpace(1);

                RoomTableModel selectedRoom = GsonHelper.getGson().fromJson(printModel.getData(), RoomTableModel.class);

                addTextToPrinter(printer, "", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(printer, "ACKNOWLEDGEMENT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);




                addTextToPrinter(printer, printModel.getMessage(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(printer, "", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//                addTextToPrinter(printer, "APPROVED BY " + printModel.getDutyManager(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


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


            }
//            else {
//                Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
//            }

        } catch (Epos2Exception e) {
            try {
                printer.disconnect();
            } catch (Epos2Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }






        return null;
    }


}
