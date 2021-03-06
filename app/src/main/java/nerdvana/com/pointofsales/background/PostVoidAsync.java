package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
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

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.custom.PrinterPresenter;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SeniorReceiptCheckoutModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.MainActivity.receiptString;
import static nerdvana.com.pointofsales.PrinterUtils.addFooterToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.convertDateToReadableDate;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class PostVoidAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    private PrinterPresenter printerPresenter;
    private SunmiPrinterService mSunmiPrintService;

    public PostVoidAsync(PrintModel printModel, Context context,
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


            String receiptNo = "NA";
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), "", context, true);
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), "", context, true);
            finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), "", context, true);
            finalString += receiptString("SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), "", context, true);
            finalString += receiptString("VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER), "", context, true);
            finalString += receiptString("PERMIT NO:" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO), "", context, true);

            finalString += receiptString("OFFICIAL RECEIPT", "", context, true);
            finalString += receiptString("", "", context, true);

            if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
                finalString += receiptString("ROOM #" + printModel.getRoomNumber(), "", context, true);
            } else {
                finalString += receiptString("TAKEOUT", "", context, true);
            }

            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class)
                    ;

            if (toList1 != null) {


                if (toList1.get_void() == 1) {

                    finalString += receiptString("V O I D", "", context, true);
                    finalString += receiptString("VOID NUMBER", toList1.getVoidCount(), context, false);

                }

                //region create receipt data

                if (toList1.getGuestInfo() != null) {
                    if (toList1.getGuestInfo().getCashierOut() != null) {
                        finalString += receiptString("CASHIER", toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCashierOut().getName() : userModel.getUsername(), context, false);
                    } else {
                        finalString += receiptString("CASHIER", "", context, false);
                    }
                } else {
                    finalString += receiptString("CASHIER", "", context, false);
                }

                finalString += receiptString("ROOM BOY", String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA"), context, false);
                finalString += receiptString("CHECK IN", toList1.getGuestInfo() != null ? Utils.birDateTimeFormat(toList1.getGuestInfo().getCheckIn()) : "NA", context, false);
                finalString += receiptString("CHECK OUT", toList1.getGuestInfo() != null ? Utils.birDateTimeFormat(toList1.getGuestInfo().getCheckOut()) : "NA", context, false);

                if (toList1.get_void() == 1) {
                    finalString += receiptString("REF OR NO", toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(), context, false);

                } else {
                    finalString += receiptString("OR NO", toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(), context, false);
                }



                if (toList1.getVoid() == 0) {
                    if (Integer.valueOf(toList1.getSoaCount()) > 1) {
                        finalString += receiptString("SOA REF NO.", toList1.getControlNo().split("-")[2] + "-" + (Integer.valueOf(Utils.removeStartingZero(toList1.getSoaCount())) - 1), context, false);

                    } else {
                        finalString += receiptString("SOA REF NO.", toList1.getControlNo().split("-")[2], context, false);

                    }


                    int count = 0;
                    if (Integer.valueOf(toList1.getSoaCount()) > 1) {

                        List<List<String>> allData = new ArrayList<>();
                        List<String> str = new ArrayList<>();
                        for (int i = Integer.valueOf(toList1.getSoaCount()) - 1; i > 0; i--) {
                            if (i == Integer.valueOf(toList1.getSoaCount()) - 1) {
                                str.add(toList1.getControlNo().split("-")[2]);
                            } else {
                                str.add(toList1.getControlNo().split("-")[2] + "-" +count);
                            }


//                        if (str.size() % 3 == 0) {
//                            allData.add(str);
//                            str = new ArrayList<>();
//                        }else {
//                            if (i == 1) {
//                                allData.add(str);
//                            }
//                        }
                            count++;
                        }


                        int displayCount = 0;
                        Collections.reverse(allData);
                        for (String my : str) {
                            if (displayCount == 0) {
                                finalString += receiptString("CANCELLED SOA", my, context, false);

                            } else {
                                finalString += receiptString("", my, context, false);

                            }
                            displayCount++;
                        }



                    }

                }

                finalString += receiptString("TERMINAL NO", SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID), context, false);
                finalString += receiptString("", new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]), context, false);
                finalString += receiptString("QTY   DESCRIPTION         AMOUNT", "", context, false);
                finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]), "", context, false);

                for (FetchOrderPendingViaControlNoResponse.Post soaTrans : toList1.getPost()) {
                    if (soaTrans.getVoid() == 0) {
                        String qty = "";

                        String qtyFiller = "";

                        qty += soaTrans.getQty();

                        for (int i = 0; i < soaTrans.getQty(); i++) {
                            qtyFiller += " ";
                        }


                        if (String.valueOf(soaTrans.getQty()).length() < 4) {
                            for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                                qty += " ";
                                qtyFiller += " ";
                            }
                        }else {
                            qtyFiller = "    ";
                        }
                        String item = "";

                        if (soaTrans.getProductId() == 0) {
                            item =soaTrans.getRoomRate().toString();
                        } else {
                            item =soaTrans.getProduct().getProductInitial();
                        }


                        finalString += receiptString(qty+ " "+item, returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty())), context, false);



                        if (soaTrans.getFreebie() != null) {
                            if (soaTrans.getFreebie().getPostAlaCart().size() > 0) {
                                for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getFreebie().getPostAlaCart()) {


                                    finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", context, false);

                                }
                            }

                            if (soaTrans.getFreebie().getPostGroup().size() > 0) {
                                for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getFreebie().getPostGroup()) {
                                    for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {



                                        finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", context, false);
                                    }
                                }
                            }


                        }



                        if (soaTrans.getPostAlaCartList().size() > 0) {
                            for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getPostAlaCartList()) {

                                finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", context, false);

                            }
                        }

                        if (soaTrans.getPostGroupList().size() > 0) {
                            for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getPostGroupList()) {
                                for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                    finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", context, false);

                                }

                            }
                        }

                    }
                }




                if (toList1.getOtHours() > 0) {


                    finalString += receiptString(String.valueOf(toList1.getOtHours()) + " " + "OT HOURS",
                            returnWithTwoDecimal(String.valueOf(toList1.getOtAmount())), context, false);

                }

                if (Integer.valueOf(toList1.getPersonCount()) > 2) {

                    finalString += receiptString(String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                            returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount())), context, false);

                }


                finalString += receiptString("",
                        "", context, false);


                finalString += receiptString("",
                        "", context, false);


                if (toList1.getVatExempt() > 0 && toList1.getDiscountsList().size() > 0) {
                    finalString += receiptString("LESS",
                            "", context, false);
                }

                if (toList1.getVatExempt() > 0) {
                    finalString += receiptString("VAT DISCOUNT",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())), context, false);
                }





                for (FetchOrderPendingViaControlNoResponse.Discounts dc : toList1.getDiscountsList()) {
                    finalString += receiptString(dc.getDiscountType() + " " + dc.getAve_discount_percentage(),
                            returnWithTwoDecimal(String.valueOf(dc.getDiscountAmount())), context, false);
                }

                finalString += receiptString("",
                        "", context, false);


                List<Integer> tmpArr = new ArrayList<>();
                String pymType = "";
                List<String> ccardArray = new ArrayList<>();

                for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                    if (!tmpArr.contains(pym.getPaymentTypeId())) {
                        tmpArr.add(pym.getPaymentTypeId());
                        pymType = pym.getPaymentDescription();
                    }
                }





                if (toList1.get_void() == 1) {

                    finalString += receiptString("VATABLE SALES",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatable() * -1)), context, false);

                    finalString += receiptString("VAT AMOUNT",
                            returnWithTwoDecimal(String.valueOf(toList1.getVat() * -1)), context, false);
                    finalString += receiptString("VAT-EXEMPT SALES",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales() * -1)), context, false);

                    finalString += receiptString("ZERO-RATED SALES",
                            "0.00", context, false);


                } else {

                    finalString += receiptString("VATABLE SALES",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatable())), context, false);

                    finalString += receiptString("VAT AMOUNT",
                            returnWithTwoDecimal(String.valueOf(toList1.getVat())), context, false);

                    finalString += receiptString("VAT-EXEMPT SALES",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())), context, false);

                    finalString += receiptString("ZERO-RATED SALES",
                            "0.00", context, false);

                }

                finalString += receiptString("",
                        "", context, false);


                for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                    if (pym.getIsAdvance() == 1) {

                        finalString += receiptString(pym.getPaymentDescription(),
                                returnWithTwoDecimal(String.valueOf(pym.getAmount())), context, false);

                    }
                }

                boolean hasSpecial = false;
                List<SeniorReceiptCheckoutModel> seniorReceiptList = new ArrayList<>();
                if (toList1.getDiscountsList().size() > 0) {

                    finalString += receiptString("DISCOUNT LIST",
                            "", context, false);


                    for (FetchOrderPendingViaControlNoResponse.Discounts d : toList1.getDiscountsList()) {
//                        addTextToPrinter(printer, d.getDiscountType(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        if (TextUtils.isEmpty(d.getVoid_by())) {
                            if (d.getId().equalsIgnoreCase("0")) { //MANUAL
//                        addTextToPrinter(printer, "    " + d.getDiscountReason(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            } else {

                                if (d.getInfo() != null) {

                                    if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {


                                        finalString += receiptString(d.getDiscountType() + " ID",
                                                "NA", context, false);

                                    } else {

                                        if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {


                                            finalString += receiptString(d.getDiscountType() + " ID",
                                                    "NA", context, false);

                                        } else {
                                            if (d.getInfo().getCardNo() != null) {

                                                finalString += receiptString(d.getDiscountType() + " ID",
                                                        d.getInfo().getCardNo().toUpperCase(), context, false);

                                            }

                                            if (d.getInfo().getName() != null) {


                                                finalString += receiptString("NAME",
                                                        d.getInfo().getName().toUpperCase(), context, false);
                                            }
                                        }


                                    }

                                }

                                finalString += receiptString("ADDRESS",
                                        d.getInfo().getAddress() != null ? d.getInfo().getAddress() : "", context, false);
                                finalString += receiptString("SIGNATURE",
                                        "", context, false);
                                finalString += receiptString("",
                                        "", context, false);



                            }
                        }
                    }

                }



                if (toList1.get_void() == 1) {

                    finalString += receiptString("SUB TOTAL",
                            "0.00", context, false);

                    finalString += receiptString("AMOUNT DUE",
                            "0.00", context, false);

                    finalString += receiptString("TENDERED",
                            "0.00", context, false);

                    finalString += receiptString("CHANGE",
                            "0.00", context, false);


                } else {

                    finalString += receiptString("SUB TOTAL",
                            returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))), context, false);

                    finalString += receiptString("AMOUNT DUE",
                            returnWithTwoDecimal(String.valueOf(
                                    (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                                            - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))), context, false);

                    finalString += receiptString("TENDERED",
                            returnWithTwoDecimal(String.valueOf(toList1.getTendered())), context, false);

                    finalString += receiptString("CHANGE",
                            returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))), context, false);

                }


                finalString += receiptString("",
                        "", context, false);

                finalString += receiptString("PAYMENT TYPE",
                        tmpArr.size() > 1 ? "MULTIPLE" : pymType, context, false);

                finalString += receiptString("",
                        "", context, false);

                for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                    if (!tmpArr.contains(pym.getPaymentTypeId())) {
                        tmpArr.add(pym.getPaymentTypeId());
                        pymType = pym.getPaymentDescription();
                    }

                    if (pym.getPaymentTypeId() == 2) {
                        if (pym.getCardDetail() != null) {
                            if (!pym.getCardDetail().getCardNumber().trim().isEmpty()) {
                                int starCount = 0;
                                String finalData = "";
                                if (pym.getCardDetail().getCardNumber().length() < 3) {
                                    finalData += pym.getCardDetail().getCardNumber();
                                } else {
                                    starCount = pym.getCardDetail().getCardNumber().length() - 3;
                                    finalData += new String(new char[starCount]).replace("\0", "*");
                                    finalData += pym.getCardDetail().getCardNumber().substring(starCount);
                                }

                                if (pym.getCardDetail().getCreditCardId().equalsIgnoreCase("1")) {


                                    finalString += receiptString("MASTERCARD",
                                            "", context, false);

                                } else {

                                    finalString += receiptString("VISA",
                                            "", context, false);
                                }


                                finalString += receiptString(pym.getPaymentDescription(),
                                        finalData, context, false);

                            }
                        }
                    }
                }


                finalString += receiptString("",
                        "", context, false);

                finalString += receiptString("NO OF PERSON/S",
                        returnWithTwoDecimal(String.valueOf(toList1.getPersonCount())), context, false);

                finalString += receiptString("NO OF FOOD ITEMS",
                        returnWithTwoDecimal(String.valueOf(toList1.getTotalQty())), context, false);

                finalString += receiptString("",
                        "", context, false);

                finalString += receiptString("",
                        "", context, false);



                if (toList1.getCustomer() != null) {
                    if (!toList1.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList1.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {


                        finalString += receiptString("",
                                "", context, false);



                        finalString += receiptString("SOLD TO",
                                "", context, true);

                        finalString += receiptString("NAME:"+toList1.getCustomer().getCustomer(),
                                "", context, true);

                        if (toList1.getCustomer().getAddress() != null) {

                            finalString += receiptString("ADDRESS:"+
                                            toList1.getCustomer().getAddress() != null ? toList1.getCustomer().getAddress() : "",
                                    "", context, true);


                        } else {

                            finalString += receiptString("ADDRESS:________________________",
                                    "", context, true);

                        }

                        if (toList1.getCustomer().getTin() != null) {

                            finalString += receiptString("TIN#:"+toList1.getCustomer().getTin(),
                                    "", context, true);

                        } else {

                            finalString += receiptString("TIN#:___________________________",
                                    "", context, true);


                        }

                        if (toList1.getCustomer().getBusinessStyle() != null) {

                            finalString += receiptString("BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(),
                                    "", context, true);


                        } else {
                            finalString += receiptString("BUSINESS STYLE:_________________",
                                    "", context, true);

                        }

                        finalString += receiptString("",
                                "", context, true);


                    } else {

                        finalString += receiptString("SOLD TO",
                                "", context, true);
                        finalString += receiptString("NAME:___________________________",
                                "", context, true);
                        finalString += receiptString("ADDRESS:________________________",
                                "", context, true);
                        finalString += receiptString("TIN#:___________________________",
                                "", context, true);
                        finalString += receiptString("BUSINESS STYLE:_________________",
                                "", context, true);

                    }
                } else {

                    finalString += receiptString("SOLD TO",
                            "", context, true);
                    finalString += receiptString("NAME:___________________________",
                            "", context, true);
                    finalString += receiptString("ADDRESS:________________________",
                            "", context, true);
                    finalString += receiptString("TIN#:___________________________",
                            "", context, true);
                    finalString += receiptString("BUSINESS STYLE:_________________",
                            "", context, true);
                }


                finalString += receiptString("",
                        "", context, true);


                finalString += receiptString("THIS SERVES AS YOUR",
                        "", context, true);
                finalString += receiptString("OFFICIAL RECEIPT",
                        "", context, true);
                finalString += receiptString("Thank you come again",
                        "", context, true);
                finalString += receiptString("POS Provider : NERDVANA CORP.",
                        "", context, true);
                finalString += receiptString("Address : 1 CANLEY ROAD BRGY",
                        "", context, true);
                finalString += receiptString("BAGONG ILOG PASIG CITY",
                        "", context, true);
                finalString += receiptString("VAT REG TIN: 009-772-500-00000",
                        "", context, true);
                finalString += receiptString("",
                        "", context, true);
                finalString += receiptString("ACCRED NO:0430097725002019061099",
                        "", context, true);
                finalString += receiptString("Date Issued : " + Utils.birDateTimeFormat(toList1.getCreatedAt()),
                        "", context, true);
                finalString += receiptString("Valid Until " + Utils.birDateTimeFormat(PrinterUtils.yearPlusFive(toList1.getCreatedAt())),
                        "", context, true);


                finalString += receiptString("PERMIT NO:"+SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO), "", context, true);
                finalString += receiptString("Date Issued :" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_ISSUED_DATE),
                        "", context, true);
                finalString += receiptString("Valid Until " + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_END_DATE),
                        "", context, true);


                finalString += receiptString("",
                        "", context, true);

                finalString += receiptString("THIS RECEIPT SHALL BE VALID FOR",
                        "", context, true);
                finalString += receiptString("FIVE(5) YEARS FROM THE DATE OF",
                        "", context, true);
                finalString += receiptString("THE PERMIT TO USE",
                        "", context, true);

//                printerPresenter.printNormal(finalString);
//                String finalString1 = finalString;
//                PrinterPresenter finalPrinterPresenter = printerPresenter;
//                ThreadPoolManager.getsInstance().execute(() -> {
//                    List<Device> deviceList = PrinterManager.getInstance().getPrinterDevice();
//                    if (deviceList == null || deviceList.isEmpty()) return;
//                    for (Device device : deviceList) {
//                        if (device.type == Cons.Type.PRINT && device.connectType == Cons.ConT.INNER) {
//                            continue;
//                        }
//                        finalPrinterPresenter.printByDeviceManager(device, finalString1);
//                    }
//                });

                TypeToken<List<PrintingListModel>> collectionToken = new TypeToken<List<PrintingListModel>>() {};
                List<PrintingListModel> pOutList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(context, ApplicationConstants.PRINTER_PREFS), collectionToken.getType());
                PrintingListModel tmpLstModel = null;
                for (PrintingListModel list : pOutList) {
                    if (list.getType().equalsIgnoreCase(printModel.getType())) {
                        String finalString1 = finalString;
                        PrinterPresenter finalPrinterPresenter = printerPresenter;
                        ThreadPoolManager.getsInstance().execute(() -> {
                            for (PrintingListModel.SelectedPrinterData data : list.getSelectedPrinterList()) {
                                if (data.getId().equalsIgnoreCase(SunmiPrinterModel.PRINTER_BUILT_IN)) {
                                    finalPrinterPresenter.printNormal(finalString1);
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
                                            finalPrinterPresenter.printByDeviceManager(device, finalString1);
                                        }
                                    }

                                }

                            }
                        });
                    }
                }

                asyncFinishCallBack.doneProcessing();

                //endregion
            }
//            else {
//
//            }
        } else {
            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)) &&
                    !TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE))) {

                //region connect printer
                try {
                    printer = new Printer(
                            Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER)),
                            Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_LANGUAGE)),
                            context);

                    try {
                        printer.addPulse(Printer.DRAWER_HIGH, Printer.PULSE_100);
                    } catch (Epos2Exception e) {
                        try {
                            printer.disconnect();
                        } catch (Epos2Exception e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
//                asyncFinishCallBack.doneProcessing();
                    }

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
                //endregion



                PrinterUtils.addHeader(printModel, printer);


                FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class)
                        ;
                if (toList1 != null) {

                    //region create receipt data


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "VOID NUMBER",
                            toList1.getVoidCount()
                            ,
                            40,
                            2,
                            context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "CASHIER",
                            toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCashierOut().getName() : userModel.getUsername()
                            ,
                            40,
                            2,
                            context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "ROOM BOY",
                            String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA")
                            ,
                            40,
                            2,
                            context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "CHECK IN",
                            convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA")
                            ,
                            40,
                            2,
                            context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "CHECK OUT",
                            convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA")
                            ,
                            40,
                            2,
                            context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "REF. OR NO",
                            toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(),
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "TERMINAL NO",
                            SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "SOA REF NO.",
//                        toList1.getControlNo().split("-")[2],
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, "QTY   DESCRIPTION         AMOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    for (FetchOrderPendingViaControlNoResponse.Post soaTrans : toList1.getPost()) {
                        if (soaTrans.getVoid() == 0) {
                            String qty = "";
                            String qtyFiller = "";

                            for (int i = 0; i < soaTrans.getQty(); i++) {
                                qtyFiller += " ";
                            }
                            qty += soaTrans.getQty();



                            if (String.valueOf(soaTrans.getQty()).length() < 4) {
                                for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                                    qty += " ";
                                    qtyFiller += " ";
                                }
                            } else {
                                qtyFiller = "    ";
                            }
                            String item = "";

                            if (soaTrans.getProductId() == 0) {
                                item =soaTrans.getRoomRate().toString();
                            } else {
                                item =soaTrans.getProduct().getProductInitial();
                            }


                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    qty+ " "+item,
                                    returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty()))
                                    ,
                                    40,
                                    2,context),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                            if (soaTrans.getFreebie() != null) {
                                if (soaTrans.getFreebie().getPostAlaCart().size() > 0) {
                                    for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getFreebie().getPostAlaCart()) {


                                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                "   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(),
                                                ""
                                                ,
                                                40,
                                                2,
                                                context),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }
                                }

                                if (soaTrans.getFreebie().getPostGroup().size() > 0) {
                                    for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getFreebie().getPostGroup()) {
                                        for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {




                                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                    "   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(),
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



                            if (soaTrans.getPostAlaCartList().size() > 0) {
                                for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getPostAlaCartList()) {

                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            "   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(),
                                            ""
                                            ,
                                            40,
                                            2,
                                            context),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }
                            }

//                        if (soaTrans.getDiscounts().size() > 0) {
//                            for (FetchOrderPendingViaControlNoResponse.PostObjectDiscount d : soaTrans.getDiscounts()) {
//                                if (TextUtils.isEmpty(d.getDeleted_at())) {
//                                    String itemDiscount = "";
//                                    if (d.getDiscountPercentage().equalsIgnoreCase("0")) {
//                                        itemDiscount = "LESS ";
//                                    } else {
//                                        itemDiscount = "LESS "+d.getDiscountPercentage() + "%";
//                                    }
//
//                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                                            qtyFiller+ " "+itemDiscount,
//                                            "-" + returnWithTwoDecimal(String.valueOf(d.getDiscountAmount()))
//                                            ,
//                                            40,
//                                            2,context),
//                                            Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                                }
//                            }
//                        }
                        }
                    }




                    if (toList1.getOtHours() > 0) {


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                String.valueOf(toList1.getOtHours()) + " " + "OT HOURS",
                                returnWithTwoDecimal(String.valueOf(toList1.getOtAmount()))
                                ,
                                40,
                                2,
                                context
                                ),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }

                    if (Integer.valueOf(toList1.getPersonCount()) > 2) {





                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                                returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount()))
                                ,
                                40,
                                2,context),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }


                    addPrinterSpace(1);



//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "NO OF PERSON/S",
//                        returnWithTwoDecimal(String.valueOf(toList1.getPersonCount()))
//                        ,
//                        40,
//                        2,context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "NO OF ITEMS",
//                        returnWithTwoDecimal(String.valueOf(toList1.getTotalQty()))
//                        ,
//                        40,
//                        2,context),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);





//                addTextToPrinter(printer, "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "VAT 12%",
//                        returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())),
////                        toList1.getVatExempt() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getVatExempt()))) : returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    if (toList1.getVatExempt() > 0 && toList1.getDiscountsList().size() > 0) {
                        addPrinterSpace(1);
                        addTextToPrinter(printer, "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }

                    if (toList1.getVatExempt() > 0) {
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                "VAT DISCOUNT",
                                returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())),
//                        toList1.getVatExempt() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getVatExempt()))) : returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())),
                                40,
                                2,
                                context)
                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }



//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "DISCOUNT",
//                        returnWithTwoDecimal(String.valueOf(toList1.getDiscount())),
////                        toList1.getDiscount() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getDiscount())))  : returnWithTwoDecimal(String.valueOf(toList1.getDiscount())),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    for (FetchOrderPendingViaControlNoResponse.Discounts dc : toList1.getDiscountsList()) {
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                dc.getDiscountType() + " " + dc.getAve_discount_percentage() + "%",
                                returnWithTwoDecimal(String.valueOf(dc.getDiscountAmount())),
                                40,
                                2,
                                context)
                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }

                    addPrinterSpace(1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "ADVANCED DEPOSIT",
                            toList1.getAdvance() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getAdvance()))) : returnWithTwoDecimal(String.valueOf(toList1.getAdvance())),
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addPrinterSpace(1);

//            bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount()






//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "SUB TOTAL",
//                    returnWithTwoDecimal(String.valueOf(toList1.getTotal())),
//                    40,
//                    2,
//                    context)
//                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "SUB TOTAL",
//                        returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "AMOUNT DUE",
//                        returnWithTwoDecimal(String.valueOf(
//                                (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
//                                        - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))),
//                        40,
//                        2,
//                        context)
//                        ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "TENDERED",
//                        returnWithTwoDecimal(String.valueOf(toList1.getTendered())),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//
//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "CHANGE",
//                        returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addPrinterSpace(1);
                    List<Integer> tmpArr = new ArrayList<>();
                    String pymType = "";
                    List<String> ccardArray = new ArrayList<>();

                    for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                        if (!tmpArr.contains(pym.getPaymentTypeId())) {
                            tmpArr.add(pym.getPaymentTypeId());
                            pymType = pym.getPaymentDescription();
                        }
                    }


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "PAYMENT TYPE",
                            tmpArr.size() > 1 ? "MULTIPLE" : pymType
                            ,
                            40,
                            2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);





                    addPrinterSpace(1);

                    for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                        if (!tmpArr.contains(pym.getPaymentTypeId())) {
                            tmpArr.add(pym.getPaymentTypeId());
                            pymType = pym.getPaymentDescription();
                        }

                        if (pym.getPaymentTypeId() == 2) {
                            if (pym.getCardDetail() != null) {
                                if (!pym.getCardDetail().getCardNumber().trim().isEmpty()) {
                                    int starCount = 0;
                                    String finalData = "";
                                    if (pym.getCardDetail().getCardNumber().length() < 3) {
                                        finalData += pym.getCardDetail().getCardNumber();
                                    } else {
                                        starCount = pym.getCardDetail().getCardNumber().length() - 3;
                                        finalData += new String(new char[starCount]).replace("\0", "*");
                                        finalData += pym.getCardDetail().getCardNumber().substring(starCount);
                                    }

                                    if (pym.getCardDetail().getCreditCardId().equalsIgnoreCase("1")) {


                                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                "MASTERCARD",
                                                ""
                                                ,
                                                40,
                                                2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    } else {

                                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                "VISA",
                                                ""
                                                ,
                                                40,
                                                2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }


                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            pym.getPaymentDescription(),
                                            finalData
                                            ,
                                            40,
                                            2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }
                            }
                        }
                    }



                    addPrinterSpace(1);

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "NO OF PERSON/S",
                            returnWithTwoDecimal(String.valueOf(toList1.getPersonCount()))
                            ,
                            40,
                            2,context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "NO OF ITEMS",
                            returnWithTwoDecimal(String.valueOf(toList1.getTotalQty()))
                            ,
                            40,
                            2,context),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addPrinterSpace(1);



                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "VATABLE SALES",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatable() * -1)),
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "VAT AMOUNT",
                            returnWithTwoDecimal(String.valueOf(toList1.getVat() * -1)),
                            40,
                            2,context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "VAT-EXEMPT SALES",
                            returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales() * -1)),
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "ZERO-RATED SALES",
                            "0.00",
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addPrinterSpace(1);

                    for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                        if (pym.getIsAdvance() == 1) {


                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    pym.getPaymentDescription(),
                                    returnWithTwoDecimal(String.valueOf(pym.getAmount())),
                                    40,
                                    2,context)
                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                    }

                    boolean hasSpecial = false;
                    List<SeniorReceiptCheckoutModel> seniorReceiptList = new ArrayList<>();
                    if (toList1.getDiscountsList().size() > 0) {

                        addTextToPrinter(printer, "DISCOUNT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        for (FetchOrderPendingViaControlNoResponse.Discounts d : toList1.getDiscountsList()) {
//                        addTextToPrinter(printer, d.getDiscountType(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                            if (TextUtils.isEmpty(d.getVoid_by())) {
                                if (d.getId().equalsIgnoreCase("0")) { //MANUAL
//                        addTextToPrinter(printer, "    " + d.getDiscountReason(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {

                                    if (d.getInfo() != null) {

//                                if (d.getDiscountTypes().getIsSpecial() == 1) {
//                                    hasSpecial = true;
//                                    seniorReceiptList.add(
//                                            new SeniorReceiptCheckoutModel(
//                                                    d.getInfo().getName() == null ? "" : d.getInfo().getName(),
//                                                    d.getInfo().getCardNo() == null ? "" : d.getInfo().getCardNo(),
//                                                    d.getInfo().getAddress() == null ? "" : d.getInfo().getAddress(),
//                                                    d.getInfo().getTin() == null ? "" : d.getInfo().getTin(),
//                                                    d.getInfo().getBusinessStyle() == null ? "" : d.getInfo().getBusinessStyle()
//                                            )
//                                    );
//                                }

                                        if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {



                                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                    d.getDiscountType() + " ID",
                                                    "NA",
                                                    40,
                                                    2,
                                                    context)
                                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                        } else {

                                            if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {



                                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                        d.getDiscountType() + " ID",
                                                        "NA",
                                                        40,
                                                        2,
                                                        context)
                                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                            } else {
                                                if (d.getInfo().getCardNo() != null) {




                                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                            d.getDiscountType() + " ID",
                                                            d.getInfo().getCardNo().toUpperCase(),
                                                            40,
                                                            2,
                                                            context)
                                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                                }

                                                if (d.getInfo().getName() != null) {



                                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                            "NAME",
                                                            d.getInfo().getName().toUpperCase(),
                                                            40,
                                                            2,
                                                            context)
                                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                                }
                                            }


                                        }

                                    }

                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            "ADDRESS",
                                            d.getInfo().getAddress() != null ? d.getInfo().getAddress().toUpperCase() : "",
                                            40,
                                            2,
                                            context)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            "SIGNATURE",
                                            "",
                                            40,
                                            2,
                                            context)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                                    addPrinterSpace(1);


                                }
                            }
                        }

                    }

//            if (hasSpecial) {
//                for (SeniorReceiptCheckoutModel sr : seniorReceiptList) {
//                    addPrinterSpace(1);
//                    if (!TextUtils.isEmpty(sr.getName())) {
//                        addTextToPrinter(printer, "NAME:"+toList1.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "NAME:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    if (!TextUtils.isEmpty(sr.getScPwdId())) {
//                        addTextToPrinter(printer, "SC/PWD ID:"+sr.getScPwdId(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "SC/PWD ID:______________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//
//                    if (toList1.getCustomer().getAddress() != null) {
//                        addTextToPrinter(printer, "ADDRESS:"+sr.getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    if (toList1.getCustomer().getTin() != null) {
//                        addTextToPrinter(printer, "TIN#:" +sr.getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    if (toList1.getCustomer().getBusinessStyle() != null) {
//                        addTextToPrinter(printer, "BUSINESS STYLE:"+ sr.getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                        addTextToPrinter(printer, toList1.getCustomer().getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    } else {
//                        addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
//                    }
//
//                    addPrinterSpace(1);
//
//                }
//            }


                    addPrinterSpace(1);

                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "SUB TOTAL",
//                        returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))),
                            "0.00",
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "AMOUNT DUE",
                            "0.00",
//                        returnWithTwoDecimal(String.valueOf(
//                                (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
//                                        - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))),
                            40,
                            2,
                            context)
                            ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "TENDERED",
                            "0.00",
//                        "0.00",
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "CHANGE",
                            "0.00",
                            40,
                            2,
                            context)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addPrinterSpace(1);

                    if (toList1.getCustomer() != null) {
                        if (!toList1.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList1.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {


                            addPrinterSpace(1);



                            addTextToPrinter(printer, "SOLD TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


                            addTextToPrinter(printer, "NAME:"+toList1.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            if (toList1.getCustomer().getAddress() != null) {


                                addTextToPrinter(printer, "ADDRESS:"+toList1.getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            } else {

                                addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            }

                            if (toList1.getCustomer().getTin() != null) {

                                addTextToPrinter(printer, "TIN#:"+toList1.getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            } else {


                                addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            }

                            if (toList1.getCustomer().getBusinessStyle() != null) {

                                addTextToPrinter(printer, "BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);


                                addTextToPrinter(printer, toList1.getCustomer().getBusinessStyle(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            } else {

                                addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            }


//                    addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);


                            addPrinterSpace(1);

                        } else {

                            addTextToPrinter(printer, "SOLD TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                            addTextToPrinter(printer, "NAME:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                            addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                        }
                    } else {

                        addTextToPrinter(printer, "SOLD TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(printer, "NAME:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                        addTextToPrinter(printer, "ADDRESS:________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                        addTextToPrinter(printer, "TIN#:___________________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                        addTextToPrinter(printer, "BUSINESS STYLE:_________________", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                    }


                    addPrinterSpace(1);

                    addTextToPrinter(printer, "THIS SERVES AS YOUR", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(printer, "OFFICIAL RECEIPT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

//                addPrinterSpace(1);

                    addFooterToPrinter(toList1.getCreatedAt(), PrinterUtils.yearPlusFive(toList1.getCreatedAt()));



                    //endregion



                } else {

                }






            }
//        else {
//            Toast.makeText(context, "Printer not set up", Toast.LENGTH_LONG).show();
//        }


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





        return null;
    }

    private void addFooterToPrinter(String currentDate, String currentDatePlus5) {

        if (printer != null) {
//            addTextToPrinter(printer, "THIS IS NOT AN OFFICIAL RECEIPT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Thank you come again", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//            addTextToPrinter(printer, "----- SYSTEM PROVIDER DETAILS -----", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "POS Provider : NERDVANA CORP.", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Address : 1 CANLEY ROAD BRGY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "BAGONG ILOG PASIG CITY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "VAT REG TIN: 009-772-500-00000", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "ACCRED NO:0430097725002019061099", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Date Issued : " + Utils.birDateTimeFormat(currentDate), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "Valid Until : " + Utils.birDateTimeFormat(currentDatePlus5), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

            PrinterUtils.addPtuFooter(printer, context);


//            addTextToPrinter(printer, "PTU No. : FPU 42434242424242423", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
            addTextToPrinter(printer, "THIS RECEIPT SHALL BE VALID FOR", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "FIVE(5) YEARS FROM THE DATE OF", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "THE PERMIT TO USE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);

            addTextToPrinter(printer, "THIS DOCUMENT IS NOT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "VALID FOR CLAIM", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "OF INPUT TAX", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);

            addTextToPrinter(printer, "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(printer, "PRINTED DATE" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(printer, "PRINTED BY: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        }
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
//            asyncFinishCallBack.doneProcessing();
        }
    }
}
