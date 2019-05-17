package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.model.PrintModel;
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
    public IntransitAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
    }


    @Override
    protected Void doInBackground(Void... voids) {


        PrinterUtils.addHeader(printModel);


        addTextToPrinter(SPrinter.getPrinter(), "IN TRANSIT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        TypeToken<List<FetchRoomResponse.Result>> intransitToken = new TypeToken<List<FetchRoomResponse.Result>>() {};
        List<FetchRoomResponse.Result> intransitDetails = GsonHelper.getGson().fromJson(printModel.getData(), intransitToken.getType());

        List<String> t = new ArrayList<>();
        t.add("I");
        t.add("II");
        t.add("III");
        t.add("IV");
        t.add("V");
        addTextToPrinter(SPrinter.getPrinter(), intransitReceipt(t), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 2, 1);

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
//                intransitCount += 1;
                List<String> temp = new ArrayList<>();
                temp.add(r.getRoomNo());

                DateTime jodatime = dtf.parseDateTime(r.getTransaction().getCheckIn());

                temp.add(dateIn.print(jodatime));
                temp.add(timeIn.print(jodatime));
                temp.add(returnWithTwoDecimal(String.valueOf(r.getTransaction().getTransaction().getAdvance())));

                Double totalFnb = 0.00;
                for (FetchRoomResponse.PostFood pf : r.getTransaction().getTransaction().getPostFood()) {
                    totalFnb += pf.getTotal() * pf.getQty();
                }
                temp.add(returnWithTwoDecimal(String.valueOf(totalFnb)));
                addTextToPrinter(SPrinter.getPrinter(), intransitReceipt(temp), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 2, 1);
            }

        }


        addPrinterSpace(1);
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr("OCCUPIED ROOMS", String.valueOf(occupiedCount), 4, 5), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr("SOA ROOMS", String.valueOf(soaCount), 4, 5), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr("DIRTY ROOMS", String.valueOf(dirtyCount), 4, 5), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr("DIRTY WITH LINEN ROOMS", String.valueOf(withLinenCount), 4, 5), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr("CLEAN ROOMS", String.valueOf(cleanCount), 4, 5), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addPrinterSpace(1);
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

    private String intransitReceipt(List<String> details) {
        String finalString = "";
        float maxColumn = 40;
        float perColumn = maxColumn / details.size();
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
