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
import com.sunmi.devicemanager.cons.Cons;
import com.sunmi.devicemanager.device.Device;
import com.sunmi.devicesdk.core.PrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class IntransitAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    public IntransitAsync(PrintModel printModel, Context context,
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
            finalString += MainActivity.receiptString("IN TRANSIT SLIP", "", context, true);




            TypeToken<List<FetchRoomResponse.Result>> intransitToken = new TypeToken<List<FetchRoomResponse.Result>>() {};
            List<FetchRoomResponse.Result> intransitDetails = GsonHelper.getGson().fromJson(printModel.getData(), intransitToken.getType());

            List<String> t = new ArrayList<>();
            t.add("I");
            t.add("II");
            t.add("III");
            t.add("IV");
            t.add("V");
            finalString += MainActivity.receiptString(intransitReceipt(t), "", context, true);


            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter dateIn = DateTimeFormat.forPattern("MM/dd");
            DateTimeFormatter timeIn = DateTimeFormat.forPattern("HH:mm");

            int dirtyCount = 0;
            int cleanCount = 0;
            int soaCount = 0;
            int withLinenCount = 0;
            int occupiedCount = 0;
            for (FetchRoomResponse.Result r : intransitDetails) {

                if (r.getStatus().getCoreId() == 1) {
                    cleanCount += 1;
                }

                if (r.getStatus().getCoreId() == 31) {
                    withLinenCount += 1;
                }

                if (r.getStatus().getCoreId() == 17) {
                    soaCount += 1;
                }

                if (r.getStatus().getCoreId() == 3) {
                    dirtyCount += 1;
                }

                if (r.getStatus().getCoreId() == 2) {
                    occupiedCount += 1;
                }

                if (r.getStatus().getCoreId() == 17 || r.getStatus().getCoreId() == 2) {

                    if (r.getTransaction() != null) {
                        if (r.getTransaction().getCheckIn() != null) {
                            List<String> temp = new ArrayList<>();
                            temp.add(r.getRoomNo()); //ROOM NUMBER

                            DateTime jodatime = dtf.parseDateTime(r.getTransaction().getCheckIn());

                            temp.add(dateIn.print(jodatime)); //DATE IN
                            temp.add(timeIn.print(jodatime)); //TIME IN
                            temp.add(returnWithTwoDecimal(String.valueOf(r.getTransaction().getTransaction().getAdvance()))); //ADVANCE PAYMENT

                            Double totalFnb = 0.00;
                            for (FetchRoomResponse.PostFood pf : r.getTransaction().getTransaction().getPostFood()) {
                                totalFnb += pf.getTotal() * pf.getQty();
                            }
                            temp.add(returnWithTwoDecimal(String.valueOf(totalFnb))); //FNB
                            finalString += MainActivity.receiptString(intransitReceipt(temp), "", context, true);

                        }

                    }

                }
            }


            finalString += MainActivity.receiptString("", "", context, true);
            finalString += MainActivity.receiptString("OCCUPIED ROOMS", String.valueOf(occupiedCount), context, false);
            finalString += MainActivity.receiptString("SOA ROOMS", String.valueOf(soaCount), context, false);
            finalString += MainActivity.receiptString("DIRTY ROOMS", String.valueOf(dirtyCount), context, false);
            finalString += MainActivity.receiptString("DIRTY WITH LINEN ROOMS", String.valueOf(withLinenCount), context, false);
            finalString += MainActivity.receiptString("CLEAN ROOMS", String.valueOf(cleanCount), context, false);


            finalString += MainActivity.receiptString("", "", context, true);
            finalString += MainActivity.receiptString("------------", "", context, true);
            finalString += MainActivity.receiptString("PRINTED DATE" , "", context, true);
            finalString += MainActivity.receiptString(currentDateTime , "", context, true);
            finalString += MainActivity.receiptString("PRINTED BY: " + userModel.getUsername(), "", context, true);


            TypeToken<List<PrintingListModel>> collectionToken = new TypeToken<List<PrintingListModel>>() {};
            List<PrintingListModel> pOutList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PRINTER_PREFS), collectionToken.getType());
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
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }


                PrinterUtils.addHeader(printModel, printer);


                addTextToPrinter(printer, "IN TRANSIT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                TypeToken<List<FetchRoomResponse.Result>> intransitToken = new TypeToken<List<FetchRoomResponse.Result>>() {};
                List<FetchRoomResponse.Result> intransitDetails = GsonHelper.getGson().fromJson(printModel.getData(), intransitToken.getType());

                List<String> t = new ArrayList<>();
                t.add("I");
                t.add("II");
                t.add("III");
                t.add("IV");
                t.add("V");
                addTextToPrinter(printer, intransitReceipt(t), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);

                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter dateIn = DateTimeFormat.forPattern("MM/dd");
                DateTimeFormatter timeIn = DateTimeFormat.forPattern("HH:mm");

                int dirtyCount = 0;
                int cleanCount = 0;
                int soaCount = 0;
                int withLinenCount = 0;
                int occupiedCount = 0;
                for (FetchRoomResponse.Result r : intransitDetails) {

                    if (r.getStatus().getCoreId() == 1) {
                        cleanCount += 1;
                    }

                    if (r.getStatus().getCoreId() == 31) {
                        withLinenCount += 1;
                    }

                    if (r.getStatus().getCoreId() == 17) {
                        soaCount += 1;
                    }

                    if (r.getStatus().getCoreId() == 3) {
                        dirtyCount += 1;
                    }

                    if (r.getStatus().getCoreId() == 2) {
                        occupiedCount += 1;
                    }

                    if (r.getStatus().getCoreId() == 17 || r.getStatus().getCoreId() == 2) {

                        if (r.getTransaction() != null) {
                            if (r.getTransaction().getCheckIn() != null) {
                                List<String> temp = new ArrayList<>();
                                temp.add(r.getRoomNo()); //ROOM NUMBER

                                DateTime jodatime = dtf.parseDateTime(r.getTransaction().getCheckIn());

                                temp.add(dateIn.print(jodatime)); //DATE IN
                                temp.add(timeIn.print(jodatime)); //TIME IN
                                temp.add(returnWithTwoDecimal(String.valueOf(r.getTransaction().getTransaction().getAdvance()))); //ADVANCE PAYMENT

                                Double totalFnb = 0.00;
                                for (FetchRoomResponse.PostFood pf : r.getTransaction().getTransaction().getPostFood()) {
                                    totalFnb += pf.getTotal() * pf.getQty();
                                }
                                temp.add(returnWithTwoDecimal(String.valueOf(totalFnb))); //FNB
                                addTextToPrinter(printer, intransitReceipt(temp), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            }

                        }

                    }
                }


                addPrinterSpace(1);
                addTextToPrinter(printer, twoColumnsRightGreaterTr("OCCUPIED ROOMS", String.valueOf(occupiedCount), 4, 5, context), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, twoColumnsRightGreaterTr("SOA ROOMS", String.valueOf(soaCount), 4, 5,context), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, twoColumnsRightGreaterTr("DIRTY ROOMS", String.valueOf(dirtyCount), 4, 5,context), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, twoColumnsRightGreaterTr("DIRTY WITH LINEN ROOMS", String.valueOf(withLinenCount), 4, 5,context), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, twoColumnsRightGreaterTr("CLEAN ROOMS", String.valueOf(cleanCount), 4, 5, context), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
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

//        else {
//            Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
//        }








        return null;
    }

    private String intransitReceipt(List<String> details) {
        String finalString = "";
        float maxColumn = Float.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT));
        int perColumn = (int)maxColumn / details.size();

        for (int i = 0; i < details.size(); i++) {
            if (details.size() >= perColumn) {
                finalString += details.get(i);
            } else {
                finalString += details.get(i);
                float temp = perColumn - details.get(i).length();
                for (int j = 0; j < temp; j++) {
                    finalString += " ";
                }
            }
        }
        return finalString;
    }


}
