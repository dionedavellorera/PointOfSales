package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.google.gson.reflect.TypeToken;
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
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;
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

    private String kitchPath;
    private String printerPath;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;
    public SwitchRoomAsync(PrintModel printModel, Context context,
                           UserModel userModel, String currentDateTime,
                           MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                           String kitchPath, String printerPath,
                           PrinterPresenter printerPresenter, SunmiPrinterService mSunmiPrintService) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.asyncFinishCallBack = asyncFinishCallBack;
        this.kitchPath = kitchPath;
        this.printerPath = printerPath;

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



            SwitchRoomPrintModel switchRoomPrintModel = GsonHelper.getGson().fromJson(printModel.getData(), SwitchRoomPrintModel.class);

            finalString += MainActivity.receiptString("SWITCH ROOM SLIP", "", context, true);
            finalString += MainActivity.receiptString("", "", context, true);
            finalString += MainActivity.receiptString("FROM : "+switchRoomPrintModel.getFromRoomNumber() +"(" + switchRoomPrintModel.getFromRoomType() + ")", "", context, true);
            finalString += MainActivity.receiptString("SWITCHED TO : " +switchRoomPrintModel.getToRoomNumber() + "(" + switchRoomPrintModel.getToRoomType() + ")", "", context, true);
            finalString += MainActivity.receiptString("CASHIER : " + userModel.getUsername(), "", context, true);
            finalString += MainActivity.receiptString("CHECK IN TIME : " + convertDateToReadableDate(switchRoomPrintModel.getCheckInTime()), "", context, true);
            finalString += MainActivity.receiptString("REMARKS", "", context, true);
            finalString += MainActivity.receiptString(printModel.getRemarks(), "", context, true);
            finalString += MainActivity.receiptString("", "", context, true);
            finalString += MainActivity.receiptString("------------", "", context, true);
            finalString += MainActivity.receiptString("PRINTED DATE" , "", context, true);
            finalString += MainActivity.receiptString(currentDateTime , "", context, true);
            finalString += MainActivity.receiptString("PRINTED BY: " + userModel.getUsername(), "", context, true);


            TypeToken<List<PrintingListModel>> myToken = new TypeToken<List<PrintingListModel>>() {};
            List<PrintingListModel> pOutList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PRINTER_PREFS), myToken.getType());
            PrintingListModel tmpLstModel = null;
            for (PrintingListModel list : pOutList) {
                if (list.getType().equalsIgnoreCase(printModel.getType())) {
                    String finalString1 = finalString;
                    ThreadPoolManager.getsInstance().execute(() -> {
                        for (PrintingListModel.SelectedPrinterData data : list.getSelectedPrinterList()) {
                            if (data.getId().equalsIgnoreCase(SunmiPrinterModel.PRINTER_BUILT_IN)) {
                                printerPresenter.printNormal(finalString1);
                            }
                        }
                        List<Device> deviceList = PrinterManager.getInstance().getPrinterDevice();
                        if (deviceList == null || deviceList.isEmpty()) return;
                        for (Device device : deviceList) {
                            if (device.type == Cons.Type.PRINT && device.connectType == Cons.ConT.INNER) {
                                continue;
                            }
                            if (list.getSelectedPrinterList().size() > 0) {
                                for (PrintingListModel.SelectedPrinterData data : list.getSelectedPrinterList()) {
                                    if (data.getId().equalsIgnoreCase(device.getId())) {
                                        printerPresenter.printByDeviceManager(device, finalString1);
                                    }
                                }

                            }

                        }
                    });
                }
            }


//            printerPresenter.printNormal(finalString);
//            String finalString1 = finalString;
//            ThreadPoolManager.getsInstance().execute(() -> {
//                List<Device> deviceList = PrinterManager.getInstance().getPrinterDevice();
//                if (deviceList == null || deviceList.isEmpty()) return;
//                for (Device device : deviceList) {
//                    if (device.type == Cons.Type.PRINT && device.connectType == Cons.ConT.INNER) {
//                        continue;
//                    }
//                    printerPresenter.printByDeviceManager(device, finalString1);
//                }
//            });

            asyncFinishCallBack.doneProcessing();



        } else {

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
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
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
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }


            }


        }



        return null;
    }
}
