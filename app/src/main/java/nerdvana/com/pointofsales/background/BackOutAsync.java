package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.sunmi.devicemanager.cons.Cons;
import com.sunmi.devicemanager.device.Device;
import com.sunmi.devicesdk.core.PrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
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

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    public BackOutAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime,
                        MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                        PrinterPresenter printerPresenter, SunmiPrinterService mSunmiPrintService) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.asyncFinishCallBack = asyncFinishCallBack;
        this.printerPresenter = printerPresenter;
        this.mSunmiPrintService = mSunmiPrintService;
    }



    @Override
    protected Void doInBackground(Void... voids) {
        if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY).equalsIgnoreCase("sunmi")) {
            if (printerPresenter == null) {
                printerPresenter = new PrinterPresenter(context, mSunmiPrintService);
            }
            String finalString = "";

            finalString += PrinterUtils.returnHeader(printModel, printer);


            RoomTableModel selectedRoom = GsonHelper.getGson().fromJson(printModel.getData(), RoomTableModel.class);

            if (selectedRoom.getRoomId() != 0) {

                finalString += MainActivity.receiptString(
                        "ROOM NO",
                        selectedRoom.getName() != null ? selectedRoom.getName() : "NA",
                        context, false);

                finalString += MainActivity.receiptString(
                        "CHECK IN TIME",
                        selectedRoom.getCheckInTime() != null ? selectedRoom.getCheckInTime() : "NA",
                        context, false);

                finalString += MainActivity.receiptString(
                        "EXPECTED CHECKOUT",
                        selectedRoom.getExpectedCheckout() != null ? selectedRoom.getExpectedCheckout() : "NA",
                        context, false);

                finalString += MainActivity.receiptString(
                        "BACKOUT TIME",
                        currentDateTime,
                        context, false);


                finalString += MainActivity.receiptString(
                        "REMARKS",
                        printModel.getRemarks(),
                        context, false);


            } else {

                finalString += MainActivity.receiptString(
                        "CONTROL NO",
                        selectedRoom.getControlNo(),
                        context, false);

                finalString += MainActivity.receiptString(
                        "BACKOUT TIME",
                        currentDateTime,
                        context, false);

                finalString += MainActivity.receiptString(
                        "REMARKS",
                        printModel.getRemarks(),
                        context, false);

            }

            finalString += MainActivity.receiptString(
                    !TextUtils.isEmpty(selectedRoom.getDepositInfo()) ? ("CANCELLED DEPOSIT") : "DEPOSIT",
                    selectedRoom.getDepositInfo(),
                    context, false);

            finalString += MainActivity.receiptString(
                    "",
                    "",
                    context, false);
            finalString += MainActivity.receiptString("------------", "", context, true);
            finalString += MainActivity.receiptString("PRINTED DATE" , "", context, true);
            finalString += MainActivity.receiptString(currentDateTime , "", context, true);
            finalString += MainActivity.receiptString("PRINTED BY: " + userModel.getUsername(), "", context, true);

            printerPresenter.printNormal(finalString);
            String finalString1 = finalString;
            ThreadPoolManager.getsInstance().execute(() -> {
                List<Device> deviceList = PrinterManager.getInstance().getPrinterDevice();
                if (deviceList == null || deviceList.isEmpty()) return;
                for (Device device : deviceList) {
                    if (device.type == Cons.Type.PRINT && device.connectType == Cons.ConT.INNER) {
                        continue;
                    }
                    printerPresenter.printByDeviceManager(device, finalString1);
                }
            });

            asyncFinishCallBack.doneProcessing();

        } else {
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

                    RoomTableModel selectedRoom = GsonHelper.getGson().fromJson(printModel.getData(), RoomTableModel.class);

//                addTextToPrinter(printer, "BACK OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                    if (selectedRoom.getRoomId() != 0) {
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "ROOM NO",
                                selectedRoom.getName() != null ? selectedRoom.getName() : "NA"
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "CHECK IN TIME",
                                selectedRoom.getCheckInTime() != null ? selectedRoom.getCheckInTime() : "NA"
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "EXPECTED CHECKOUT",
                                selectedRoom.getExpectedCheckout() != null ? selectedRoom.getExpectedCheckout() : "NA"
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "BACKOUT TIME",
                                currentDateTime
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "REMARKS",
                                printModel.getRemarks()
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    } else {

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "CONTROL NO",
                                selectedRoom.getControlNo()
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "BACKOUT TIME",
                                currentDateTime
                                ,
                                40,
                                2,
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "REMARKS",
                                printModel.getRemarks()
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            !TextUtils.isEmpty(selectedRoom.getDepositInfo()) ? ("CANCELLED DEPOSIT") : "DEPOSIT",
                            selectedRoom.getDepositInfo()
                            ,
                            40,
                            2,context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);
                    addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                    addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "APPROVED BY " + printModel.getDutyManager(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


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

        }







        return null;
    }
}
