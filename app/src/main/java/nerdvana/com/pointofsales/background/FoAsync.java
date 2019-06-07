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

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class FoAsync extends AsyncTask<Void, Void, Void> {


    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private Printer printer;
    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;

    private String kitchPath;
    private String printerPath;
    public FoAsync(PrintModel printModel, Context context,
                   UserModel userModel, String currentDateTime,
                   MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                   String kitchPath, String printerPath) {
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

            boolean hasConnected = false;
            if (!TextUtils.isEmpty(printerPath)) {
                printer.connect("TCP:" + printerPath, Printer.PARAM_DEFAULT);
                hasConnected = true;
            }

            if (!hasConnected) {
                if (!TextUtils.isEmpty(kitchPath)) {
                    printer.connect("TCP:" + kitchPath, Printer.PARAM_DEFAULT);
                    hasConnected = true;
                }
            }

            if (!hasConnected) {
                printer.disconnect();
                Toast.makeText(context, "No printer available", Toast.LENGTH_SHORT).show();
            }

        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
        PrinterUtils.addHeader(printModel, printer);
        Double totalAmount = 0.00;
        addTextToPrinter(printer, "FO ORDER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(printer, "QTY   DESCRIPTION         AMOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

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
            addTextToPrinter(printer, twoColumnsRightGreaterTr(qty+ r.getProduct_initial(), r.getPrice(), 40, 2, context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            if (r.getAlaCarteList().size() > 0) {
                for (AddRateProductModel.AlaCarte palac : r.getAlaCarteList()) {
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "   "+palac.getQty()+ " "+palac.getProduct_initial(),
                            ""
                            ,
                            40,
                            2,
                            context),
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
                                context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }
                }
            }
        }


        addTextToPrinter(printer, "TOTAL: " + String.valueOf(totalAmount), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);
        addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(printer, "REMARKS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "PENDING TO DO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
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
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

}
