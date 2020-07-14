package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.content.IntentFilter;
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
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SafeKeepDataModel;
import nerdvana.com.pointofsales.model.SpotAuditModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class SpotAuditAsync extends AsyncTask<Void, Void, Void> {
    private PrintModel printModel;
    private Context context;
    private UserModel userModel;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;
    private String currentDateTime;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    public SpotAuditAsync(PrintModel printModel, Context context,
                          UserModel userModel, MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                          String currentDateTime,
                          PrinterPresenter printerPresenter, SunmiPrinterService mSunmiPrintService) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.asyncFinishCallBack = asyncFinishCallBack;
        this.currentDateTime = currentDateTime;
        this.printerPresenter = printerPresenter;
        this.mSunmiPrintService = mSunmiPrintService;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY).equalsIgnoreCase("sunmi")) {
            if (printerPresenter == null) {
                printerPresenter = new PrinterPresenter(context, mSunmiPrintService);
            }
            String finalString = "";


            finalString += PrinterUtils.returnHeader(printModel, printer);

            finalString += MainActivity.receiptString("SPOT AUDIT", "", context, true);


            SpotAuditModel collectionDetails = GsonHelper.getGson().fromJson(printModel.getData(), SpotAuditModel.class);

            finalString += MainActivity.receiptString("BILLS", "", context, false);
            finalString += MainActivity.receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);

            finalString += fixDenoPrintString(collectionDetails.getCollectionFinalPostModels(),
                    collectionDetails.getShortOver(),
                    collectionDetails.getCashSales());

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

                addTextToPrinter(printer, "SPOT AUDIT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//        TypeToken<List<CollectionFinalPostModel>> collectionToken = new TypeToken<List<CollectionFinalPostModel>>() {};
                SpotAuditModel collectionDetails = GsonHelper.getGson().fromJson(printModel.getData(), SpotAuditModel.class);

//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "SK COUNT",
//                    String.valueOf(collectionDetails.ge(0).getSkCount())
//                    ,
//                    40,
//                    2,
//                    context),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, "BILLS", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                fixDenoPrint(collectionDetails.getCollectionFinalPostModels(),
                        collectionDetails.getShortOver(),
                        collectionDetails.getCashSales());



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
                try {
                    printer.disconnect();
                } catch (Epos2Exception e1) {
                    e1.printStackTrace();
                }
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
            try {
                printer.disconnect();
            } catch (Epos2Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private String fixDenoPrintString(List<CollectionFinalPostModel> myList, String shortOver,
                                      String cashSales) {
        String tmpString = "";
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.CASH_DENO_JSON))) {
            TypeToken<List<FetchDenominationResponse.Result>> collectionToken = new TypeToken<List<FetchDenominationResponse.Result>>() {};
            List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.CASH_DENO_JSON), collectionToken.getType());
            Double finalAmount = 0.00;
            for (FetchDenominationResponse.Result cfm : denoDetails) {
                String valueCount = "0";
                String valueAmount = "0.00";
                for (CollectionFinalPostModel c : myList) {
                    if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                        valueCount = c.getAmount();
                        valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                        break;
                    }
                }

                tmpString += MainActivity.receiptString(String.format("%s  x %s", valueCount, cfm.getAmount()), valueAmount, context, false);

                finalAmount += Double.valueOf(valueAmount);
            }

            tmpString += MainActivity.receiptString("", "", context, false);
            tmpString += MainActivity.receiptString("------------", "", context, true);
            tmpString += MainActivity.receiptString("CASH SALES", cashSales, context, false);
            tmpString += MainActivity.receiptString("CASH COUNT", String.valueOf(finalAmount), context, false);
            tmpString += MainActivity.receiptString("CASH OUT", "0.00", context, false);
            tmpString += MainActivity.receiptString("", "", context, false);
            tmpString += MainActivity.receiptString("SHORT/OVER", shortOver, context, false);
            tmpString += MainActivity.receiptString("", "", context, false);

            tmpString += MainActivity.receiptString("------------", "", context, true);
            tmpString += MainActivity.receiptString("PRINTED DATE" , "", context, true);
            tmpString += MainActivity.receiptString(currentDateTime , "", context, true);
            tmpString += MainActivity.receiptString("PRINTED BY: " + userModel.getUsername(), "", context, true);

        }
        return tmpString;
    }
    private void fixDenoPrint(List<CollectionFinalPostModel> myList, String shortOver,
                              String cashSales) {

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.CASH_DENO_JSON))) {
            TypeToken<List<FetchDenominationResponse.Result>> collectionToken = new TypeToken<List<FetchDenominationResponse.Result>>() {};
            List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.CASH_DENO_JSON), collectionToken.getType());
            Double finalAmount = 0.00;
            for (FetchDenominationResponse.Result cfm : denoDetails) {
                String valueCount = "0";
                String valueAmount = "0.00";
                for (CollectionFinalPostModel c : myList) {
                    if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                        valueCount = c.getAmount();
                        valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                        break;
                    }
                }

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        String.format("%s  x %s", valueCount, cfm.getAmount()),
                        valueAmount
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                finalAmount += Double.valueOf(valueAmount);
            }

            addPrinterSpace(1);

            addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASH SALES",
                    cashSales
                    ,
                    40,
                    2,
                    context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASH COUNT",
                    String.valueOf(finalAmount)
                    ,
                    40,
                    2,
                    context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "CASH OUT",
                    "0.00"
                    ,
                    40,
                    2,
                    context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



            addPrinterSpace(1);

            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                    "SHORT/OVER",
                    shortOver
                    ,
                    40,
                    2,
                    context),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


            addPrinterSpace(1);

            addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        }

    }


}
