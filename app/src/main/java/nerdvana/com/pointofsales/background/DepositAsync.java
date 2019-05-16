package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class DepositAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    public DepositAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }



    @Override
    protected Void doInBackground(Void... voids) {

        TypeToken<List<PostedPaymentsModel>> depositToken = new TypeToken<List<PostedPaymentsModel>>() {};
        List<PostedPaymentsModel> depositDetails = GsonHelper.getGson().fromJson(printModel.getData(), depositToken.getType());


        addTextToPrinter(SPrinter.getPrinter(), "DEPOSIT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);


        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "MACHINE NO",
                SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "ROOM TYPE",
                printModel.getRoomType(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


        addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "TYPE                        Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        Double total = 0.00;
        for (PostedPaymentsModel ppm : depositDetails) {
            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                    ppm.getPayment_description(),
                    ppm.getAmount(),
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
            total += Double.valueOf(ppm.getAmount());
        }


        addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(total), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);


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
