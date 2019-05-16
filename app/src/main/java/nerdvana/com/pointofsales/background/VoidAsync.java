package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class VoidAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    public VoidAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        TypeToken<List<VoidProductModel>> voidToken = new TypeToken<List<VoidProductModel>>() {};
        List<VoidProductModel> voidList = GsonHelper.getGson().fromJson(printModel.getData(), voidToken.getType());

        addTextToPrinter(SPrinter.getPrinter(), "VOID SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "QTY   Description              Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        Double voidTotalAmount = 0.00;
        for (VoidProductModel vpm : voidList) {

            String qty = "";

            qty += vpm.getQuantity();
            if (vpm.getQuantity().length() < 4) {
                for (int i = 0; i < 4 - vpm.getQuantity().length(); i++) {
                    qty += " ";

                }
            }

            voidTotalAmount += Double.valueOf(vpm.getPrice());
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(qty+ vpm.getName(), vpm.getPrice(), 40, 2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        }

        addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(voidTotalAmount), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);

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
