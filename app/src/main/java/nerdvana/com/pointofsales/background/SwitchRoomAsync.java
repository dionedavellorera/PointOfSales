package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.SwitchRoomPrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.convertDateToReadableDate;

public class SwitchRoomAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;


    public SwitchRoomAsync(PrintModel printModel, Context context,
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


        SwitchRoomPrintModel switchRoomPrintModel = GsonHelper.getGson().fromJson(printModel.getData(), SwitchRoomPrintModel.class);

        addTextToPrinter(printer, "SWITCH ROOM SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        addPrinterSpace(1);
        addTextToPrinter(printer, "FROM : "+switchRoomPrintModel.getFromRoomNumber() +"(" + switchRoomPrintModel.getFromRoomType() + ")", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        addTextToPrinter(printer, "SWITCHED TO : " +switchRoomPrintModel.getToRoomNumber() + "(" + switchRoomPrintModel.getToRoomType() + ")", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

//                addTextToPrinter(printer, "CASHIER : " + getUserInfo(switchRoomPrintModel.getUserId()), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "CASHIER : " + userModel.getUsername(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        addTextToPrinter(printer, "CHECK IN TIME : " + convertDateToReadableDate(switchRoomPrintModel.getCheckInTime()), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        addTextToPrinter(printer, "REMARKS", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, printModel.getRemarks(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        addPrinterSpace(1);

        addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(printer, "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

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
