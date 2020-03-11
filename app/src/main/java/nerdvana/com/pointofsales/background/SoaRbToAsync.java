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

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.SeniorReceiptCheckoutModel;
import nerdvana.com.pointofsales.model.UserModel;

import static nerdvana.com.pointofsales.MainActivity.formatSeconds;
import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterLr;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class SoaRbToAsync extends AsyncTask<Void, Void, Void> {


    private PrintModel printModel;
    private Context context;
    private UserModel userModel;
    private String currentDateTime;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private Printer printer;

    private String kitchPath;
    private String printerPath;

    public SoaRbToAsync(PrintModel printModel, Context context,
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
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

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
//                PrinterUtils.connect(context, printer);

                try {
                    printer.connect("TCP:" + printerPath, Printer.PARAM_DEFAULT);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                    try {
                        printer.disconnect();
                    } catch (Epos2Exception e1) {
                        e1.printStackTrace();
                    }
                    asyncFinishCallBack.doneProcessing();
                }


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

//                if (toList1.getIsSoa() > 1) {
//                    addTextToPrinter(printer, "REPRINT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//                }

                //region create receipt data


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "CASHIER",
                        userModel.getUsername()
                        ,
                        40,
                        2,
                        context,
                        false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "ROOM BOY",
                        String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA")
                        ,
                        40,
                        2,
                        context,false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "CHECK IN",
                        convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA")
                        ,
                        40,
                        2,
                        context,
                        false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "CHECK OUT",
                        convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA")
                        ,
                        40,
                        2,
                        context,
                        false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                int count = 0;
                if (Integer.valueOf(toList1.getSoaCount()) > 1) {


                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "SOA NO",
                            toList1.getControlNo().split("-")[2] + "-" + (Integer.valueOf(Utils.removeStartingZero(toList1.getSoaCount())) - 1),
                            40,
                            2,
                            context,
                            false)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                    if ((Integer.valueOf(Utils.removeStartingZero(toList1.getSoaCount())) - 1)  == 1) {
//                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                                "CANCELLED SOA",
//                                Utils.removeStartingZero(toList1.getControlNo().split("-")[2]) ,
//                                40,
//                                2,
//                                context)
//                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                    } else {
//                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                                "CANCELLED SOA",
//                                Utils.removeStartingZero(toList1.getControlNo().split("-")[2]) + "-" +String.valueOf((Integer.valueOf(Utils.removeStartingZero(toList1.getSoaCount())) - 1) - 1) ,
//                                40,
//                                2,
//                                context)
//                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                    }



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
                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    "CANCELLED SOA",
                                    my,
                                    40,
                                    2,
                                    context,
                                    false)
                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        } else {
                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                    "",
                                    my,
                                    40,
                                    2,
                                    context,false)
                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                        displayCount++;
                    }



                } else {
                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            "SOA NO",
                            toList1.getControlNo().split("-")[2],
                            40,
                            2,
                            context,false)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }



                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "TERMINAL NO",
                        SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID),
                        40,
                        2,
                        context,
                        false)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                String qtyDescString = "";
                if (Integer.valueOf(SharedPreferenceManager.getString(null, ApplicationConstants.MAX_COLUMN_COUNT_RB))>32) {
                    String filler = "";
                    for (int i = 0;i <Integer.valueOf(SharedPreferenceManager.getString(null, ApplicationConstants.MAX_COLUMN_COUNT_RB)) - 32; i++) {
                        filler += " ";
                    }
                    qtyDescString = "QTY   DESCRIPTION    "+filler+"     AMOUNT";
                } else {
                    qtyDescString = "QTY   DESCRIPTION         AMOUNT";
                }


                addTextToPrinter(printer, qtyDescString, Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
                                2,context,false),
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
                                            context,false),
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
                                                context,false),
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
                                        context,false),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }

                        if (soaTrans.getPostGroupList().size() > 0) {
                            for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getPostGroupList()) {
                                for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {


                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            "   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(),
                                            ""
                                            ,
                                            40,
                                            2,
                                            context,false),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }

                            }
                        }

//                        if (soaTrans.getDiscounts().size() > 0) {
//                            for (FetchOrderPendingViaControlNoResponse.PostObjectDiscount d : soaTrans.getDiscounts()) {
//
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
//
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
                            context,
                            false
                            ),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }

                if (Integer.valueOf(toList1.getPersonCount()) > 2) {





                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                            String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                            returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount()))
                            ,
                            40,
                            2,context,false),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }


//                addPrinterSpace(1);



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
//                        "NO OF FOOD ITEMS",
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
                            context,false)
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
                    if (TextUtils.isEmpty(dc.getVoid_by())) {
                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                            dc.getDiscountType() + " " + dc.getAve_discount_percentage() + "%",
                                dc.getDiscountType(),
                                returnWithTwoDecimal(String.valueOf(dc.getDiscountAmount())),
                                40,
                                2,
                                context,false)
                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }

                }

                addPrinterSpace(1);


//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "   ADVANCED DEPOSIT",
//                        toList1.getAdvance() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getAdvance()))) : returnWithTwoDecimal(String.valueOf(toList1.getAdvance())),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addPrinterSpace(1);

//            bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount()



//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "SUB TOTAL",
//                        returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))),
//                        40,
//                        2,
//                        context)
//                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//            addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                    "SUB TOTAL",
//                    returnWithTwoDecimal(String.valueOf(toList1.getTotal())),
//                    40,
//                    2,
//                    context)
//                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "AMOUNT DUE",
//                        returnWithTwoDecimal(String.valueOf(
//                                (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
//                                        - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))),
//                        40,
//                        2,
//                        context)
//                        ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



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


                List<Integer> tmpArr = new ArrayList<>();
                String pymType = "";
                List<String> ccardArray = new ArrayList<>();
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
                                            "MASTER",
                                            ""
                                            ,
                                            40,
                                            2,context,false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {

                                    addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                            "VISA",
                                            ""
                                            ,
                                            40,
                                            2,context,false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }


                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        pym.getPaymentDescription(),
                                        finalData
                                        ,
                                        40,
                                        2,context,false), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }
                    }
                }



//                addTextToPrinter(printer, twoColumnsRightGreaterTr(
//                        "PAYMENT TYPE",
//                        tmpArr.size() > 1 ? "MULTIPLE" : pymType
//                        ,
//                        40,
//                        2,context), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//



                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VATABLE SALES",
                        returnWithTwoDecimal(String.valueOf(toList1.getVatable())),
                        40,
                        2,
                        context,false)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VAT AMOUNT",
                        returnWithTwoDecimal(String.valueOf(toList1.getVat())),
                        40,
                        2,context,false)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "VAT-EXEMPT SALES",
                        returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())),
                        40,
                        2,
                        context,false)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);




                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "Zero-Rated Sales",
                        "0.00",
                        40,
                        2,
                        context,false)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addPrinterSpace(1);

                for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                    if (pym.getIsAdvance() == 1) {


                        addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                pym.getPaymentDescription(),
                                returnWithTwoDecimal(String.valueOf(pym.getAmount())),
                                40,
                                2,context,false)
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
                                                context,false)
                                                ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    } else {

                                        if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {



                                            addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                    d.getDiscountType() + " ID",
                                                    "NA",
                                                    40,
                                                    2,
                                                    context,false)
                                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                        } else {
                                            if (d.getInfo().getCardNo() != null) {




                                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                        d.getDiscountType() + " ID",
                                                        d.getInfo().getCardNo().toUpperCase(),
                                                        40,
                                                        2,
                                                        context,false)
                                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                            }

                                            if (d.getInfo().getName() != null) {



                                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                                        "NAME",
                                                        d.getInfo().getName().toUpperCase(),
                                                        40,
                                                        2,
                                                        context,false)
                                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                            }
                                        }


                                    }

                                }

                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "ADDRESS",
                                        "",
                                        40,
                                        2,
                                        context,false)
                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                                        "SIGNATURE",
                                        "",
                                        40,
                                        2,
                                        context,false)
                                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


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

                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "SUB TOTAL",
                        returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))),
                        40,
                        2,
                        context,false)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "AMOUNT DUE",
                        returnWithTwoDecimal(String.valueOf(
                                (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                                        - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))),
                        40,
                        2,
                        context,false)
                        ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                addPrinterSpace(1);
                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "NO OF PERSON/S",
                        returnWithTwoDecimal(String.valueOf(toList1.getPersonCount()))
                        ,
                        40,
                        2,context,false),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(printer, twoColumnsRightGreaterTr(
                        "NO OF FOOD ITEMS",
                        returnWithTwoDecimal(String.valueOf(toList1.getTotalQty()))
                        ,
                        40,
                        2,context,false),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);



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
            addTextToPrinter(printer, "THIS DOCUMENT SHALL BE VALID FOR", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
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

    private String convertDateToReadableDate(String createdAt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(createdAt);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");


            res = dtfOut.print(jodatime);
        } catch (Exception e) {
            res  = "NA";
//            asyncFinishCallBack.doneProcessing();
        }


        return res.toUpperCase();

    }


}
