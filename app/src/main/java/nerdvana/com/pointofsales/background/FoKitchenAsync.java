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

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class FoKitchenAsync extends AsyncTask<Void, Void, Void> {

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private Printer printer;
    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;

    private String kitchPath;
    private String printerPath;
    public FoKitchenAsync(PrintModel printModel, Context context,
                   UserModel userModel, String currentDateTime,
                   MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                   String kitchPath, String printerPath,
                  PrinterPresenter printerPresenter, SunmiPrinterService mSunmiPrintService) {
        this.printerPresenter = printerPresenter;
        this.mSunmiPrintService = mSunmiPrintService;
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.asyncFinishCallBack = asyncFinishCallBack;
        this.kitchPath = kitchPath;
        this.printerPath = printerPath;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY).equalsIgnoreCase("sunmi")) {
            if (printerPresenter == null) {
                printerPresenter = new PrinterPresenter(context, mSunmiPrintService);
            }
            String finalString = "";

            finalString += PrinterUtils.returnHeader(printModel, printer);

            Double totalAmount = 0.00;
            addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            String qtyDescString;
            if (Integer.valueOf(SharedPreferenceManager.getString(null, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN))>32) {
                String filler = "";
                for (int i = 0;i <Integer.valueOf(SharedPreferenceManager.getString(null, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN)) - 32; i++) {
                    filler += " ";
                }
                qtyDescString = "QTY   DESCRIPTION    "+filler+"     AMOUNT";
            } else {
                qtyDescString = "QTY   DESCRIPTION         AMOUNT";
            }

            finalString += MainActivity.receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
            finalString += MainActivity.receiptString(qtyDescString, "", context, true);
            finalString += MainActivity.receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);


            TypeToken<List<AddRateProductModel>> token = new TypeToken<List<AddRateProductModel>>() {};
            List<AddRateProductModel> aprm = GsonHelper.getGson().fromJson(printModel.getData(), token.getType());
            for (AddRateProductModel r : aprm) {
                String qty = "";

                qty += r.getQty();
                if (r.getQty().length() < 4) {
                    for (int i = 0; i < 4 - r.getQty().length(); i++) {
                        qty += " ";
                    }
                }

                totalAmount += Double.valueOf(r.getPrice());

                finalString += MainActivity.receiptString(qty+ r.getProduct_initial(), PrinterUtils.returnWithTwoDecimal(r.getPrice()), context, false);


                if (!TextUtils.isEmpty(r.getRemarks())) {

                    finalString += MainActivity.receiptString("  " + r.getRemarks(), "", context, false);


                }
                if (r.getAlaCarteList().size() > 0) {
                    for (AddRateProductModel.AlaCarte palac : r.getAlaCarteList()) {

                        finalString += MainActivity.receiptString("   "+palac.getQty()+ " "+palac.getProduct_initial(), "", context, false);



                    }
                }

                if (r.getGroupList().size() > 0) {
                    for (AddRateProductModel.Group postGroup : r.getGroupList()) {
                        for (AddRateProductModel arpm : postGroup.getGroupCompoList().getItem()) {

                            finalString += MainActivity.receiptString("   "+arpm.getQty()+ " "+ arpm.getProduct_initial(), "", context, false);

                        }
                    }
                }
            }



            finalString += MainActivity.receiptString("------------", "", context, true);
            finalString += MainActivity.receiptString("PRINTED DATE", "", context, true);
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
            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)) &&
                    !TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE))) {


                boolean hasConnected = true;
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
//                                asyncFinishCallBack.doneProcessing();
                                    }
                                }
                            }).start();
                        }
                    });


                } catch (Epos2Exception e) {
                    e.printStackTrace();
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
//            asyncFinishCallBack.doneProcessing();
                }

//            try {
//                printer.connect("TCP:" + printerPath, Printer.PARAM_DEFAULT);
//            } catch (Epos2Exception e) {
//                e.printStackTrace();
//                try {
//                    printer.disconnect();
//                } catch (Epos2Exception e1) {
//                    e1.printStackTrace();
//                }
//                hasConnected = false;
////            asyncFinishCallBack.doneProcessing();
//            }


                try {
                    printer.connect("TCP:" + kitchPath, Printer.PARAM_DEFAULT);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                    hasConnected = false;
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    asyncFinishCallBack.doneProcessing();
                }


//            if (!hasConnected) {
//                PrinterUtils.connect(context, printer);
//            }

                PrinterUtils.addHeader(printModel, printer);
                Double totalAmount = 0.00;
                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                String qtyDescString;
                if (Integer.valueOf(SharedPreferenceManager.getString(null, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN))>32) {
                    String filler = "";
                    for (int i = 0;i <Integer.valueOf(SharedPreferenceManager.getString(null, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN)) - 32; i++) {
                        filler += " ";
                    }
                    qtyDescString = "QTY   DESCRIPTION    "+filler+"     AMOUNT";
                } else {
                    qtyDescString = "QTY   DESCRIPTION         AMOUNT";
                }
                addTextToPrinter(printer, qtyDescString, Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                TypeToken<List<AddRateProductModel>> token = new TypeToken<List<AddRateProductModel>>() {};
                List<AddRateProductModel> aprm = GsonHelper.getGson().fromJson(printModel.getData(), token.getType());
                for (AddRateProductModel r : aprm) {
                    String qty = "";

                    qty += r.getQty();
                    if (r.getQty().length() < 4) {
                        for (int i = 0; i < 4 - r.getQty().length(); i++) {
                            qty += " ";
                        }
                    }

                    totalAmount += Double.valueOf(r.getPrice());
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(qty+ r.getProduct_initial(), PrinterUtils.returnWithTwoDecimal(r.getPrice()), 40, 2, context, true), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    if (!TextUtils.isEmpty(r.getRemarks())) {
                        addTextToPrinter(printer,
                                "  " + r.getRemarks(),
                                Printer.FALSE, Printer.FALSE,
                                Printer.ALIGN_LEFT,
                                1,1,1);
                    }
                    if (r.getAlaCarteList().size() > 0) {
                        for (AddRateProductModel.AlaCarte palac : r.getAlaCarteList()) {
                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    "   "+palac.getQty()+ " "+palac.getProduct_initial(),
                                    ""
                                    ,
                                    40,
                                    2,
                                    context,true),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                    }

                    if (r.getGroupList().size() > 0) {
                        for (AddRateProductModel.Group postGroup : r.getGroupList()) {
                            for (AddRateProductModel arpm : postGroup.getGroupCompoList().getItem()) {
                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "   "+arpm.getQty()+ " "+ arpm.getProduct_initial(),
                                        ""
                                        ,
                                        40,
                                        2,
                                        context,true),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }
                    }
                }


                addTextToPrinter(printer, "TOTAL: " + PrinterUtils.returnWithTwoDecimal(String.valueOf(totalAmount)), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);
                addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                try {
                    printer.addCut(Printer.CUT_FEED);
                    if (printer.getStatus().getConnection() == 1) {
                        printer.sendData(Printer.PARAM_DEFAULT);
                    }
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
//            asyncFinishCallBack.doneProcessing();
                }


            }
        }

//        else {
//            Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
//        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    }

}
