package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
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
    public SwitchRoomAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        PrinterUtils.addHeader(printModel);


        SwitchRoomPrintModel switchRoomPrintModel = GsonHelper.getGson().fromJson(printModel.getData(), SwitchRoomPrintModel.class);

        addTextToPrinter(SPrinter.getPrinter(), "SWITCH ROOM SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        addPrinterSpace(1);
        addTextToPrinter(SPrinter.getPrinter(), "FROM : "+switchRoomPrintModel.getFromRoomNumber() +"(" + switchRoomPrintModel.getFromRoomType() + ")", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        addTextToPrinter(SPrinter.getPrinter(), "SWITCHED TO : " +switchRoomPrintModel.getToRoomNumber() + "(" + switchRoomPrintModel.getToRoomType() + ")", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

//                addTextToPrinter(SPrinter.getPrinter(), "CASHIER : " + getUserInfo(switchRoomPrintModel.getUserId()), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "CASHIER : " + userModel.getUsername(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        addTextToPrinter(SPrinter.getPrinter(), "CHECK IN TIME : " + convertDateToReadableDate(switchRoomPrintModel.getCheckInTime()), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

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
}
