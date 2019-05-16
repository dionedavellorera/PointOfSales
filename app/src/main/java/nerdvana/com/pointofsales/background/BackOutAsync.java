package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class BackOutAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    public BackOutAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        RoomTableModel selectedRoom = GsonHelper.getGson().fromJson(printModel.getData(), RoomTableModel.class);

        addTextToPrinter(SPrinter.getPrinter(), "BACK OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

        if (selectedRoom.getRoomId() != 0) {
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "ROOM NO",
                    selectedRoom.getName() != null ? selectedRoom.getName() : "NA"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CHECK IN TIME",
                    selectedRoom.getCheckInTime() != null ? selectedRoom.getCheckInTime() : "NA"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "EXPECTED CHECKOUT",
                    selectedRoom.getExpectedCheckout() != null ? selectedRoom.getExpectedCheckout() : "NA"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "BACKOUT TIME",
                    currentDateTime
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "REMARKS",
                    printModel.getRemarks()
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        } else {

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "CONTROL NO",
                    selectedRoom.getControlNo()
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "BACKOUT TIME",
                    currentDateTime
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    "REMARKS",
                    printModel.getRemarks()
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        }

        addPrinterSpace(1);
        addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "Approved by: " + printModel.getDutyManager(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


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
