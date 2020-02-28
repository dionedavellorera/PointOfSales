package nerdvana.com.pointofsales.background;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.epson.epos2.printer.Printer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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


public class CreateReceiptAsync extends AsyncTask<Void, Void, Void> {

    private PrintModel printModel;
    private Context context;
    private UserModel userModel;

    private MainActivity.AsyncFinishCallBack asyncFinishCallBack;
    private String currentDateTime;
    public CreateReceiptAsync(PrintModel printModel, Context context, UserModel userModel, String currentDateTime, MainActivity.AsyncFinishCallBack asyncFinishCallBack) {
        this.context = context;
        this.printModel = printModel;
        this.userModel = userModel;
        this.asyncFinishCallBack = asyncFinishCallBack;
        this.currentDateTime = currentDateTime;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected Void doInBackground(Void... voids) {


        String finalString = "";
        String receiptNo = "NA";
        finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), "", context, true);
        finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), "", context, true);
        finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), "", context, true);
        finalString += receiptString("SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), "", context, true);
        finalString += receiptString("VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER), "", context, true);
        finalString += receiptString("PERMIT NO:" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO), "", context, true);

        FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class)
                ;
        if (toList1 != null) {

            //region create receipt data
            finalString += receiptString("CASHIER", userModel.getUsername(), context, false);
            finalString += receiptString("ROOM BOY", String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA"), context, false);
            finalString += receiptString("CHECK IN", convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA"), context, false);
            finalString += receiptString("CHECK OUT", convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA"), context, false);

            if (toList1.getReceiptNo() == null) {
                receiptNo = "NA";
            } else {
                receiptNo = toList1.getReceiptNo().toString();
            }


            finalString += receiptString("OR NO", toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(), context, false);
            finalString += receiptString("TERMINAL NO", SharedPreferenceManager.getString(context, ApplicationConstants.MACHINE_ID), context, false);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
            finalString += receiptString("QTY   DESCRIPTION         AMOUNT", "", context, true);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", context, true);
            for (FetchOrderPendingViaControlNoResponse.Post soaTrans : toList1.getPost()) {
                if (soaTrans.getVoid() == 0) {
                    String qty = "";

                    qty += soaTrans.getQty();
                    if (String.valueOf(soaTrans.getQty()).length() < 4) {
                        for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                            qty += " ";
                        }
                    }
                    String item = "";

                    if (soaTrans.getProductId() == 0) {
                        item =soaTrans.getRoomRate().toString();
                    } else {
                        item =soaTrans.getProduct().getProductInitial();
                    }

                    finalString += receiptString(qty + " " + item, returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty())), context, false);


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

            finalString += receiptString("", "", context, true);



            finalString += receiptString("NO OF PERSON/S", returnWithTwoDecimal(String.valueOf(toList1.getPersonCount())), context, false);

            finalString += receiptString("NO OF ITEMS", returnWithTwoDecimal(String.valueOf(toList1.getTotalQty())), context, false);

            finalString += receiptString("", "", context, true);


//            finalString += receiptString("LESS", "", context, false);


            finalString += receiptString("   VAT DISCOUNT", returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())), context, false);


            finalString += receiptString("   DISCOUNT", returnWithTwoDecimal(String.valueOf(toList1.getDiscount())), context, false);

            finalString += receiptString("   ADVANCED DEPOSIT", returnWithTwoDecimal(String.valueOf(toList1.getAdvance())), context, false);

            finalString += receiptString("", "", context, true);


//            bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount()


            finalString += receiptString("SUB TOTAL", returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))), context, false);



            finalString += receiptString("AMOUNT DUE", returnWithTwoDecimal(String.valueOf(
                    (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                            - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))), context, false);


            finalString += receiptString("TENDERED", returnWithTwoDecimal(String.valueOf(toList1.getTendered())), context, false);


            finalString += receiptString("CHANGE", returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))), context, false);


            finalString += receiptString("", "", context, true);

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

                                finalString += receiptString("MASTER", "", context, false);

                            } else {
                                finalString += receiptString("VISA", "", context, false);

                            }

                            finalString += receiptString(pym.getPaymentDescription(), finalData, context, false);

                        }
                    }
                }
            }


            finalString += receiptString("PAYMENT TYPE", tmpArr.size() > 1 ? "MULTIPLE" : pymType, context, false);



            finalString += receiptString("", "", context, true);



            finalString += receiptString("VATABLE SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatable())), context, false);

            finalString += receiptString("VAT-EXEMPT SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())), context, false);

            finalString += receiptString("12% VAT", returnWithTwoDecimal(String.valueOf(toList1.getVat())), context, false);


            finalString += receiptString("", "", context, true);


            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (pym.getIsAdvance() == 1) {

                    finalString += receiptString(pym.getPaymentDescription(), returnWithTwoDecimal(String.valueOf(pym.getAmount())), context, false);

                }
            }

            boolean hasSpecial = false;
            List<SeniorReceiptCheckoutModel> seniorReceiptList = new ArrayList<>();
            if (toList1.getDiscountsList().size() > 0) {
                finalString += receiptString("DISCOUNT LIST", "", context, true);

                for (FetchOrderPendingViaControlNoResponse.Discounts d : toList1.getDiscountsList()) {


                    if (TextUtils.isEmpty(d.getVoid_by())) {
                        if (d.getId().equalsIgnoreCase("0")) { //MANUAL

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

                                    finalString += receiptString(d.getDiscountType(), "NA", context, false);


                                } else {

                                    if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                        finalString += receiptString(d.getDiscountType(), "NA", context, false);


                                    } else {
                                        if (d.getInfo().getCardNo() != null) {

                                            finalString += receiptString(d.getDiscountType(), d.getInfo().getCardNo().toUpperCase(), context, false);


                                        }

                                        if (d.getInfo().getName() != null) {

                                            finalString += receiptString("NAME", d.getInfo().getName().toUpperCase(), context, false);

                                        }
                                    }


                                }

                            }
                        }
                    }
                }

            }




            if (toList1.getCustomer() != null) {
                if (!toList1.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList1.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {

                    finalString += receiptString("", "", context, true);

                    finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", context, true);


                    finalString += receiptString("NAME:"+toList1.getCustomer().getCustomer(), "", context, true);

                    if (toList1.getCustomer().getAddress() != null) {

                        finalString += receiptString("ADDRESS:"+toList1.getCustomer().getAddress(), "", context, true);

                    } else {
                        finalString += receiptString("ADDRESS:________________________", "", context, true);

                    }

                    if (toList1.getCustomer().getTin() != null) {
                        finalString += receiptString("TIN#:"+toList1.getCustomer().getTin(), "", context, true);

                    } else {

                        finalString += receiptString("TIN#:___________________________", "", context, true);

                    }

                    if (toList1.getCustomer().getBusinessStyle() != null) {
                        finalString += receiptString("BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(), "", context, true);


                        finalString += receiptString(toList1.getCustomer().getBusinessStyle(), "", context, true);

                    } else {
                        finalString += receiptString("BUSINESS STYLE:_________________", "", context, true);

                    }



                    finalString += receiptString("", "", context, true);


                } else {

                    finalString += receiptString("NAME:___________________________", "", context, true);
                    finalString += receiptString("ADDRESS:________________________", "", context, true);
                    finalString += receiptString("TIN#:___________________________", "", context, true);
                    finalString += receiptString("BUSINESS STYLE:_________________", "", context, true);

                }
            } else {

                finalString += receiptString("NAME:___________________________", "", context, true);
                finalString += receiptString("ADDRESS:________________________", "", context, true);
                finalString += receiptString("TIN#:___________________________", "", context, true);
                finalString += receiptString("BUSINESS STYLE:_________________", "", context, true);

            }

            finalString += receiptString("", "", context, true);


            finalString += receiptString("", "", context, true);

            finalString += receiptString("Thank you come again", "", context, true);

            finalString += receiptString("----- SYSTEM PROVIDER DETAILS -----", "", context, true);

            finalString += receiptString("Provider : NERDVANA CORP.", "", context, true);

            finalString += receiptString("Address : 1 CANLEY ROAD BRGY BAGONG ILOG PASIG CITY", "", context, true);

            finalString += receiptString("TIN: 009-772-500-000", "", context, true);

            finalString += receiptString("ACCRE No. : ******", "", context, true);

            finalString += receiptString("Date issued : " + toList1.getCreatedAt(), "", context, true);

            finalString += receiptString("Valid until : " + PrinterUtils.yearPlusFive(toList1.getCreatedAt()), "", context, true);

            finalString += receiptString("", "", context, true);

            finalString += receiptString("THIS RECEIPT SHALL BE VALID FOR", "", context, true);

            finalString += receiptString("FIVE(5) YEARS FROM THE DATE OF", "", context, true);

            finalString += receiptString("THE PERMIT TO USE", "", context, true);

            finalString += receiptString("", "", context, true);



            //endregion



            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
            String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");

            try {
                File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, receiptNo +".txt");
                FileWriter writer = null;
                writer = new FileWriter(gpxfile);

                writer.append(finalString);
//                writer.append("test data");

                writer.flush();

                writer.close();

            } catch (IOException e) {

                asyncFinishCallBack.doneProcessing();
            }




        } else {

        }







        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        asyncFinishCallBack.doneProcessing();

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




    private String convertDateToReadableDate(String createdAt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(createdAt);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");


            res = dtfOut.print(jodatime);
        } catch (Exception e) {
            res  = "NA";
            asyncFinishCallBack.doneProcessing();
        }


        return res.toUpperCase();

    }



    private String receiptString(String partOne, String partTwo, Context context, boolean isCenter) {
        String finalString = "";
        int filler = 0;
        int maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) / 2);

        if (isCenter) {
            if (partOne.length() > Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))) {
                finalString = partOne.substring(0, Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)));
            } else {
                int custFillter = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) - partOne.length()) / 2;
                finalString = repeat(" ", custFillter) + partOne + repeat(" ", custFillter);
            }

        } else {

            if (partOne.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partOne.length());
            }
            if (partTwo.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partTwo.length());
            }
            finalString = (partOne.length() >= maxColumnDivideTwo ? partOne.substring(0, maxColumnDivideTwo) : partOne)
                    + repeat(" ", filler)
                    + (partTwo.length() >= maxColumnDivideTwo ? partTwo.substring(0, maxColumnDivideTwo) : partTwo);

        }



        return finalString + "\n";
    }

    private static String repeat(String str, int i){
        return new String(new char[i]).replace("\0", str);
    }


}

