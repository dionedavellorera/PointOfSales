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
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.MainActivity.receiptString;
import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class CashNReconcileAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    public CashNReconcileAsync(PrintModel printModel, Context context,
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

            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), "", context, true);
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), "", context, true);
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), "", context, true);
            finalString += receiptString("SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), "", context, true);
            finalString += receiptString("VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER), "", context, true);
            finalString += receiptString("PERMIT NO:" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO), "", context, true);

            finalString += receiptString("CASHIER RECONCILE", "", context, true);



            TypeToken<List<CollectionFinalPostModel>> collectionToken = new TypeToken<List<CollectionFinalPostModel>>() {};
            List<CollectionFinalPostModel> collectionDetails = GsonHelper.getGson().fromJson(printModel.getData(), collectionToken.getType());
            finalString += receiptString("BILLS", "", context, true);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]), "", context, true);

            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.CASH_DENO_JSON))) {
                TypeToken<List<FetchDenominationResponse.Result>> collectionToken2 = new TypeToken<List<FetchDenominationResponse.Result>>() {
                };
                List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.CASH_DENO_JSON), collectionToken2.getType());
                Double finalAmount = 0.00;
                for (FetchDenominationResponse.Result cfm : denoDetails) {
                    String valueCount = "0";
                    String valueAmount = "0.00";
                    for (CollectionFinalPostModel c : collectionDetails) {
                        if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                            valueCount = c.getAmount();
                            valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                            break;
                        }
                    }
                    finalString += receiptString(String.format("%s  x %s", valueCount, cfm.getAmount()), valueAmount, context, false);
                    finalAmount += Double.valueOf(valueAmount);
                }
                finalString += receiptString("", "", context, false);
                finalString += receiptString("CASH COUNT", String.valueOf(finalAmount), context, false);
                finalString += receiptString("CASH OUT", String.valueOf(0.00), context, false);
                finalString += receiptString("", "", context, false);
                finalString += receiptString("------------", "", context, true);
                finalString += receiptString("PRINTED DATE", "", context, true);
                finalString += receiptString(currentDateTime, "", context, true);
                finalString += receiptString("PRINTED BY:", userModel.getUsername(), context, true);

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

            }



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


                addTextToPrinter(printer, "CASHIER RECONCILE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                TypeToken<List<CollectionFinalPostModel>> cashrecotoken = new TypeToken<List<CollectionFinalPostModel>>() {};
                List<CollectionFinalPostModel> cashrecodetails = GsonHelper.getGson().fromJson(printModel.getData(), cashrecotoken.getType());

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "SK COUNT",
                        String.valueOf(cashrecodetails.get(0).getSkCount())
                        ,
                        40,
                        2,
                        context),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, "BILLS", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                fixDenoPrint(cashrecodetails);



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




        return null;
    }

    private void fixDenoPrint(List<CollectionFinalPostModel> myList) {

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
            addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        }

    }


}
