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
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
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

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;
    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;
    public CheckInAsync(PrintModel printModel, Context context,
                        UserModel userModel, String currentDateTime,
                        RoomTableModel selected, MainActivity.AsyncFinishCallBack asyncFinishCallBack,
                        PrinterPresenter printerPresenter, SunmiPrinterService mSunmiPrintService) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.currentDateTime = currentDateTime;
        this.selected = selected;
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

            TypeToken<List<CheckInResponse.Booked>> checkInToken = new TypeToken<List<CheckInResponse.Booked>>() {};
            List<CheckInResponse.Booked> checkinDetails = GsonHelper.getGson().fromJson(printModel.getData(), checkInToken.getType());

            finalString += PrinterUtils.returnHeader(printModel, printer);

            finalString += MainActivity.receiptString("ROOM TYPE", checkinDetails.get(0).getRoomType().toUpperCase(), context, false);

            finalString += MainActivity.receiptString("RATE DESC", checkinDetails.get(0).getRoomRate().toUpperCase(), context, false);

            finalString += MainActivity.receiptString("STARTING RATE", roomRatePrice(String.valueOf(checkinDetails.get(0).getRoomRatePriceId())), context, false);

            finalString += MainActivity.receiptString("DATE / TIME", convertDateToReadableDate(checkinDetails.get(0).getCheckIn() != null ? checkinDetails.get(0).getCheckIn() : "NA"), context, false);

            finalString += MainActivity.receiptString("VEHICLE TYPE", fetchVehicleFromId(String.valueOf(checkinDetails.get(0).getVehicleId() != null ? checkinDetails.get(0).getVehicleId() : "NA")).toUpperCase(), context, false);

            finalString += MainActivity.receiptString("CAR MAKE", checkinDetails.get(0).getCar().getCarMake() != null ? checkinDetails.get(0).getCar().getCarMake().toUpperCase() : "NA", context, false);

            finalString += MainActivity.receiptString("PLATE NUMBER", checkinDetails.get(0).getPlateNo() != null ? checkinDetails.get(0).getPlateNo().toUpperCase() : "NA", context, false);


            if (checkinDetails.size() > 0) {
                if (checkinDetails.get(0).getRoomBoyIn() != null) {
                    finalString += MainActivity.receiptString("ROOM BOY",
                            checkinDetails.get(0).getRoomBoyIn().getName(), context, false);

                } else {
                    finalString += MainActivity.receiptString("ROOM BOY",
                            "--", context, false);
                }
            } else {
                finalString += MainActivity.receiptString("ROOM BOY",
                        "NA", context, false);

            }

            finalString += MainActivity.receiptString("------------",
                    "", context, true);
            finalString += MainActivity.receiptString("PRINTED DATE",
                    "", context, true);
            finalString += MainActivity.receiptString(currentDateTime,
                    "", context, true);
            finalString += MainActivity.receiptString("PRINTED BY: " + userModel.getUsername(),
                    "", context, true);

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
                    PrinterUtils.connect(context, printer);
                } catch (Epos2Exception e) {
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
//            asyncFinishCallBack.doneProcessing();
                }

                PrinterUtils.addHeader(printModel, printer);

                TypeToken<List<CheckInResponse.Booked>> checkInToken = new TypeToken<List<CheckInResponse.Booked>>() {};
                List<CheckInResponse.Booked> checkinDetails = GsonHelper.getGson().fromJson(printModel.getData(), checkInToken.getType());

//            addTextToPrinter(printer, "CHECK IN SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "ROOM TYPE",
                        checkinDetails.get(0).getRoomType().toUpperCase(),
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "RATE DESC.",
                        checkinDetails.get(0).getRoomRate().toUpperCase(),
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "STARTING RATE",
                        roomRatePrice(String.valueOf(checkinDetails.get(0).getRoomRatePriceId())),
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "DATE / TIME",
                        convertDateToReadableDate(checkinDetails.get(0).getCheckIn() != null ? checkinDetails.get(0).getCheckIn() : "NA"),
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VEHICLE TYPE",
                        fetchVehicleFromId(String.valueOf(checkinDetails.get(0).getVehicleId() != null ? checkinDetails.get(0).getVehicleId() : "NA")).toUpperCase(),
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "CAR MAKE",
                        checkinDetails.get(0).getCar().getCarMake() != null ? checkinDetails.get(0).getCar().getCarMake().toUpperCase() : "NA",
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "PLATE NUMBER",
                        checkinDetails.get(0).getPlateNo() != null ? checkinDetails.get(0).getPlateNo().toUpperCase() : "NA",
                        40,
                        2,
                        context)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                if (checkinDetails.size() > 0) {
                    if (checkinDetails.get(0).getRoomBoyIn() != null) {
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "ROOM BOY",
                                checkinDetails.get(0).getRoomBoyIn().getName(),
                                40,
                                2,
                                context)
                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    } else {
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "ROOM BOY",
                                "--",
                                40,
                                2,
                                context)
                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }
                } else {
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "ROOM BOY",
                            "NA",
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }



//        addTextToPrinter(printer,
//                "ROOM BOY:  " + getUserInfo(String.valueOf(checkinDetails.get(0).getUserId())),Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//            addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
//            addTextToPrinter(printer, "REMARKS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//            addTextToPrinter(printer, "PENDING TO DO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

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


//            printer.endTransaction();
                } catch (Epos2Exception e) {
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
//            asyncFinishCallBack.doneProcessing();
                }




            }
//        else {
//            Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
//        }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


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
