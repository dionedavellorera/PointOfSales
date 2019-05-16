package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.convertDateToReadableDate;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterLr;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class CheckInAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;
    private RoomTableModel selected;
    public CheckInAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime,
                        RoomTableModel selected) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.selected = selected;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        TypeToken<List<CheckInResponse.Booked>> checkInToken = new TypeToken<List<CheckInResponse.Booked>>() {};
        List<CheckInResponse.Booked> checkinDetails = GsonHelper.getGson().fromJson(printModel.getData(), checkInToken.getType());
        addTextToPrinter(SPrinter.getPrinter(), "CHECK IN SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "ROOM TYPE",
                checkinDetails.get(0).getRoomType().toUpperCase(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "RATE DESC.",
                checkinDetails.get(0).getRoomRate().toUpperCase(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                "STARTING RATE",
                roomRatePrice(String.valueOf(checkinDetails.get(0).getRoomRatePriceId())),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "DATE / TIME",
                convertDateToReadableDate(checkinDetails.get(0).getCreatedAt() != null ? checkinDetails.get(0).getCreatedAt() : "NA"),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "VEHICLE TYPE",
                fetchVehicleFromId(String.valueOf(checkinDetails.get(0).getVehicleId() != null ? checkinDetails.get(0).getVehicleId() : "NA")).toUpperCase(),
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "CAR MAKE",
                checkinDetails.get(0).getCar().getCarMake() != null ? checkinDetails.get(0).getCar().getCarMake().toUpperCase() : "NA",
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                "PLATE NUMBER",
                checkinDetails.get(0).getPlateNo() != null ? checkinDetails.get(0).getPlateNo().toUpperCase() : "NA",
                40,
                2)
                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


        addTextToPrinter(SPrinter.getPrinter(),
                "ROOM BOY:  " + getUserInfo(String.valueOf(checkinDetails.get(0).getUserId())),Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


        addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
        addTextToPrinter(SPrinter.getPrinter(), "REMARKS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "PENDING TO DO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

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

    private String roomRatePrice(String roomRatePriceId) {
        String rateDisplay = "";

        if (selected != null) {
            for (int i = 0; i < selected.getPrice().size(); i++) {
                if (roomRatePriceId.equalsIgnoreCase(String.valueOf(selected.getPrice().get(i).getRoomRatePriceId()))) {
                    rateDisplay = String.valueOf(selected.getPrice().get(i).getRatePrice().getAmount());
                    break;
                }
            }
        }

        return rateDisplay;
    }

    private String fetchVehicleFromId(String vehicleId) {
        TypeToken<List<FetchVehicleResponse.Result>> vehicleToken = new TypeToken<List<FetchVehicleResponse.Result>>() {};
        List<FetchVehicleResponse.Result> vehicleList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.VEHICLE_JSON), vehicleToken.getType());
        String result = "N/A";

        for (FetchVehicleResponse.Result res : vehicleList) {
            if (String.valueOf(res.getId()).equals(vehicleId)) {
                result = res.getVehicle();
                break;
            }
        }

        return result;
    }


    private String getUserInfo(String userId) {

        TypeToken<List<FetchCompanyUserResponse.Result>> companyUser = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        List<FetchCompanyUserResponse.Result> vehicleList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.COMPANY_USER), companyUser.getType());
        String result = "N/A";

        for (FetchCompanyUserResponse.Result res : vehicleList) {
            if (String.valueOf(res.getUserId()).equals(userId)) {
                result = res.getName();
                break;
            }
        }

        return result;
    }



}
