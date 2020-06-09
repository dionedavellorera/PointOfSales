package nerdvana.com.pointofsales;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.model.PrintModel;

import static nerdvana.com.pointofsales.MainActivity.formatSeconds;
import static nerdvana.com.pointofsales.MainActivity.receiptString;

public class PrinterUtils {

    public static void addTextToPrinter(Printer printer, String text,
                                  int isBold, int isUnderlined,
                                  int alignment, int feedLine,
                                  int textSizeWidth, int textSizeHeight) {

        if (printer != null) {
            StringBuilder textData = new StringBuilder();
            try {
//                printer.addFeedLine(feedLine);
                printer.addTextSize(textSizeWidth, textSizeHeight);
                printer.addTextAlign(alignment);
                printer.addTextStyle(Printer.PARAM_DEFAULT, isUnderlined, isBold, Printer.PARAM_DEFAULT);
//                printer.addTextSmooth(Printer.TRUE);
                printer.addText(textData.toString());
                textData.append(text);
                printer.addText(textData.toString());
                textData.delete(0, textData.length());
                printer.addFeedLine(feedLine);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String returnWithTwoDecimal(String amount) {
        String finalValue = "";
        if (amount.contains(".")) {
            String[] tempArray = amount.split("\\.");
            if (tempArray[1].length() > 2) {
                finalValue = tempArray[0] + "." + tempArray[1].substring(0,2);
            } else {
//                finalValue = tempArray[0] + "." + tempArray[1];
                finalValue = tempArray[0] + "." + (tempArray[1].length() == 1 ? tempArray[1] +  "0" : tempArray[1]);
            }
        } else {
            finalValue = amount;
        }

        return finalValue;

    }


    public static String twoColumnsRightGreaterTrGSales(String partOne, String partTwo, int maxTextCountPerLine, int columns, Context context) {
        String finalString = "";
        int filler = 0;
        int maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) / 2);
        if (partOne.length() < maxColumnDivideTwo) {
            filler += (maxColumnDivideTwo - partOne.length());
        }
        if (partTwo.length() < maxColumnDivideTwo) {
            filler += (maxColumnDivideTwo - partTwo.length());
        }
        finalString = (partOne.length() >= maxColumnDivideTwo ? partOne.substring(0, maxColumnDivideTwo + 1) : partOne) + repeat(" ", filler) + (partTwo.length() >= maxColumnDivideTwo ? partTwo.substring(0, maxColumnDivideTwo) : partTwo);

        return finalString;
    }

    public static String twoColumnsRightGreaterTr(String partOne, String partTwo, int maxTextCountPerLine, int columns, Context context, boolean isKitchen) {
        String finalString = "";
        int filler = 0;
        int maxColumnDivideTwo = 0;
        if (isKitchen) {
            maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN)) / 2);
        } else {
            maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT_RB)) / 2);
        }
        if (partOne != null) {
            if (partOne.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partOne.length());
            }
        }
        if (partTwo != null) {
            if (partTwo.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partTwo.length());
            }
        }

        finalString = (partOne.length() >= maxColumnDivideTwo ? partOne.substring(0, maxColumnDivideTwo) : partOne) + repeat(" ", filler) + (partTwo.length() >= maxColumnDivideTwo ? partTwo.substring(0, maxColumnDivideTwo) : partTwo);


        return finalString;
    }

    public static String twoColumnsRightGreaterTr(String partOne, String partTwo, int maxTextCountPerLine, int columns, Context context) {
        String finalString = "";
        int filler = 0;
        int maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) / 2);
        if (partOne != null) {
            if (partOne.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partOne.length());
            }
        }
        if (partTwo != null) {
            if (partTwo.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partTwo.length());
            }
        }

        finalString = (partOne.length() >= maxColumnDivideTwo ? partOne.substring(0, maxColumnDivideTwo) : partOne) + repeat(" ", filler) + (partTwo.length() >= maxColumnDivideTwo ? partTwo.substring(0, maxColumnDivideTwo) : partTwo);

        return finalString;
    }


    private static String repeat(String str, int i){
        return new String(new char[i]).replace("\0", str);
    }



    public static void addPrinterSpace(int count) {
        try {
            if (SPrinter.getPrinter() != null) {
                SPrinter.getPrinter().addFeedLine(count);
            } else {
//                Toast.makeText(getApplicationContext(), "Printer not setup", Toast.LENGTH_SHORT).show();
            }
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }


    }

    public static String returnPtuFooter(Printer printer, Context context) {
        String finalString = "";

        finalString += MainActivity.receiptString(
                "Permit No" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO),
                "",
                context, true);
        finalString += MainActivity.receiptString(
                "Date Issued :" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_ISSUED_DATE),
                "",
                context, true);
        finalString += MainActivity.receiptString(
                "Valid Until : " + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_END_DATE),
                "",
                context, true);
        return finalString;
    }
    public static void addPtuFooter(Printer printer, Context context) {
        addTextToPrinter(printer, "Permit No" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_NO) , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );
        addTextToPrinter(printer, "Date Issued :" + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_ISSUED_DATE), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "Valid Until : " + SharedPreferenceManager.getString(context, ApplicationConstants.PERMIT_END_DATE), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
    }

    public static void addFooterToPrinter() {

        if (SPrinter.getPrinter() != null) {
            addTextToPrinter(SPrinter.getPrinter(), "OFFICIAL RECEIPT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Thank you come again", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "----- SYSTEM PROVIDER DETAILS -----", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Provider : Nerdvana", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Address : test address", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "TIN: 432540435435", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "ACCRE No. : 1231231231231231231234324234", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Date issued : 01/01/2001", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Valid until : 01/01/2090", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "PTU No. : FPU 42434242424242423", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
            addTextToPrinter(SPrinter.getPrinter(), "THIS INVOICE RECEIPT SHALL BE VALID FOR FIVE(5) YEARS FROM THE DATE OF THE PERMIT TO USE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
        }
    }

    public static String yearPlusFive(String date) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(date);
//            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            res = dtfOut.print(jodatime.plusYears(5));
        } catch (Exception e) {
            res  = "NA";
        }
        return res.toUpperCase();
    }

    public static String convertDateToReadableDate(String createdAt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(createdAt);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");
            res = dtfOut.print(jodatime);
        } catch (Exception e) {
            res  = "NA";
        }
        return res.toUpperCase();
    }

    public static String twoColumnsRightGreaterLr(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
        String finalString = "";
        float column1 = 14;
        float column2 = 26;
        if (partOne.length() >= column1) {
            finalString += partOne.substring(0, 14);
        } else {
            finalString += partOne;

            for (int i = 0; i < column1 - partOne.length(); i++) {
                finalString += " ";
            }
        }

        if (partTwo.length() >= column2) {
            finalString += partTwo.substring(0, 26);
        } else {

            for (int i = 0; i < column2 - partTwo.length(); i++) {
                finalString += " ";
            }

            finalString += partTwo;
        }


        return finalString;
    }

    public static String getDuration(String dateTime) {
        long secs = 0;
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime today = new DateTime();
            DateTime yesterday = formatter.parseDateTime(dateTime);
            Duration duration = new Duration(yesterday, today);
            secs = duration.getStandardSeconds();
        }

        return formatSeconds(secs);
    }



    public static void connect(Context context, Printer printer) {
        boolean isConnected = false;
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY))) {
            try {
                if (printer != null) {


                    printer.connect(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY), Printer.PARAM_DEFAULT);
//                    printer.beginTransaction();
                    isConnected = true;
                }
            } catch (Epos2Exception e) {
                try {
                    printer.disconnect();
                } catch (Epos2Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        } else {
            if (SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT).isEmpty()) {
                Toast.makeText(context, "No Printer", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (printer != null) {
                        printer.connect(SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT), Printer.PARAM_DEFAULT);
//                        printer.beginTransaction();
                        isConnected = true;
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

    }

    public static String returnHeader(PrintModel printModel, Printer printer) {
        String finalString = "";
        finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), "", null, true);
        finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), "", null, true);

        finalString += receiptString(SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), "", null, true);
        finalString += receiptString("SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), "", null, true);
        finalString += receiptString("VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER), "", null, true);
        finalString += receiptString("PERMIT NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.PERMIT_NO), "", null, true);


        if (printModel.getType().equalsIgnoreCase("FO")) {
            finalString += receiptString("FOOD ORDER SLIP", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("BACKOUT")) {
            finalString += receiptString("BACK OUT SLIP", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("PRINT_RECEIPT")) {
            finalString += receiptString("OFFICIAL RECEIPT", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("DEPOSIT")) {
            finalString += receiptString("DEPOSIT SLIP", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("CHECKIN")) {
            finalString += receiptString("CHECK IN SLIP", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("VOID")) {
            finalString += receiptString("VOID SLIP", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("SOA-ROOM")) {
            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class);
            if (toList1.getIsSoa() > 1) {
                finalString += receiptString("STATEMENT OF ACCOUNT(REPRINT)", "", null, true);
            } else {
                finalString += receiptString("STATEMENT OF ACCOUNT", "", null, true);

            }
        }
        if (printModel.getType().equalsIgnoreCase("SOA-TO")) {
            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class);
            if (toList1.getIsSoa() > 1) {
                finalString += receiptString("STATEMENT OF ACCOUNT(REPRINT)", "", null, true);
            } else {
                finalString += receiptString("STATEMENT OF ACCOUNT", "", null, true);
            }
        }
        if (printModel.getType().equalsIgnoreCase("POST_VOID")) {
            finalString += receiptString("V O I D", "", null, true);
        }
        if (printModel.getType().equalsIgnoreCase("ZREAD")) {
            finalString += receiptString("Z-READING", "", null, true);
        }
        //REPRINTZREAD
        if (printModel.getType().equalsIgnoreCase("REPRINTZREAD")) {
            finalString += receiptString("Z-READING(REPRINT)", "", null, true);
        }


        if (printModel.getType().equalsIgnoreCase("REPRINTXREADING")){
            finalString += receiptString("X-READING(REPRINT)", "", null, true);

        }
        if (printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT")) {
            finalString += receiptString("OFFICIAL RECEIPT(REPRINT)", "", null, true);

        }

        if (printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT_SPEC")) {
            finalString += receiptString("OFFICIAL RECEIPT(CUSTOMERS COPY)", "", null, true);

        }


        if (printModel.getType().equalsIgnoreCase("FO") || printModel.getType().equalsIgnoreCase("BACKOUT") ||
                printModel.getType().equalsIgnoreCase("PRINT_RECEIPT") || printModel.getType().equalsIgnoreCase("DEPOSIT") ||
                printModel.getType().equalsIgnoreCase("CHECKIN") || printModel.getType().equalsIgnoreCase("VOID") ||
                printModel.getType().equalsIgnoreCase("SOA-ROOM") || printModel.getType().equalsIgnoreCase("POST_VOID") ||
                printModel.getType().equalsIgnoreCase("SOA-TO") || printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT")) { //
            if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
                finalString += receiptString("ROOM #" + printModel.getRoomNumber(), "", null, true);

            } else {
                finalString += receiptString("TAKEOUT" + printModel.getRoomNumber(), "", null, true);

            }
        }

        if (printModel.getType().equalsIgnoreCase("CHANGE_WAKE_UP_CALL")  ||
                printModel.getType().equalsIgnoreCase("SWITCH_ROOM")) {
            finalString += receiptString("ROOM #" + printModel.getRoomNumber(), "", null, true);

        }


        if (printModel.getType().equalsIgnoreCase("SOA-TO")) {
            finalString += receiptString("TAKEOUT", "", null, true);
        }

        return  finalString +"\n";
    }



    public static void addHeader(PrintModel printModel, Printer printer) {
        addTextToPrinter(printer, SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer,SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1,1);
        addTextToPrinter(printer, "VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER) , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "PERMIT NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.PERMIT_NO) , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );


//        addTextToPrinter(printer, "ABC COMPANY", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//        addTextToPrinter(printer, "1 ABC ST. DE AVE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//        addTextToPrinter(printer, "PASIG CITY 1600", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//        addTextToPrinter(printer," TEL NO: 8123-4567", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//        addTextToPrinter(printer, "VAT REG TIN NO: 009-772-500-000" , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//        addTextToPrinter(printer, "MIN NO: *****************", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );
//        addTextToPrinter(printer, "SERIAL NO: ********", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1,1);
//        addTextToPrinter(printer, "PERMIT NO: ********-***-*******-*****" , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );










        if (printModel.getType().equalsIgnoreCase("FO"))  addTextToPrinter(printer,"FOOD ORDER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("BACKOUT"))  addTextToPrinter(printer,"BACK OUT SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("PRINT_RECEIPT"))  addTextToPrinter(printer,"OFFICIAL RECEIPT" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("DEPOSIT"))  addTextToPrinter(printer,"DEPOSIT SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("CHECKIN"))  addTextToPrinter(printer,"CHECK IN SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("VOID"))  addTextToPrinter(printer,"VOID SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("SOA-ROOM")) {
            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class);
            if (toList1.getIsSoa() > 1) {
                addTextToPrinter(printer, "STATEMENT OF ACCOUNT(REPRINT)", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            } else {
                addTextToPrinter(printer,"STATEMENT OF ACCOUNT" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }
        }
        if (printModel.getType().equalsIgnoreCase("SOA-TO")) {
            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class);
            if (toList1.getIsSoa() > 1) {
                addTextToPrinter(printer, "STATEMENT OF ACCOUNT(REPRINT)", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            } else {
                addTextToPrinter(printer,"STATEMENT OF ACCOUNT" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }
        }
        if (printModel.getType().equalsIgnoreCase("POST_VOID"))  addTextToPrinter(printer,"V O I D" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("ZREAD"))  addTextToPrinter(printer,"Z-READING" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        //REPRINTZREAD
        if (printModel.getType().equalsIgnoreCase("REPRINTZREAD")) {
            addTextToPrinter(printer,"Z-READING(REPRINT)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }


        if (printModel.getType().equalsIgnoreCase("REPRINTXREADING")){
            addTextToPrinter(printer,"X-READING(REPRINT)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }
        if (printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT")) {
            addTextToPrinter(printer,"OFFICIAL RECEIPT(REPRINT)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }

        if (printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT_SPEC")) {
            addTextToPrinter(printer,"OFFICIAL RECEIPT(CUSTOMERS COPY)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }


        if (printModel.getType().equalsIgnoreCase("FO") || printModel.getType().equalsIgnoreCase("BACKOUT") ||
                printModel.getType().equalsIgnoreCase("PRINT_RECEIPT") || printModel.getType().equalsIgnoreCase("DEPOSIT") ||
                printModel.getType().equalsIgnoreCase("CHECKIN") || printModel.getType().equalsIgnoreCase("VOID") ||
                printModel.getType().equalsIgnoreCase("SOA-ROOM") || printModel.getType().equalsIgnoreCase("POST_VOID") ||
                printModel.getType().equalsIgnoreCase("SOA-TO") || printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT")) { //
            if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
                addTextToPrinter(printer,"ROOM #" + printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            } else {
                addTextToPrinter(printer,"TAKEOUT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }
        }

        if (printModel.getType().equalsIgnoreCase("CHANGE_WAKE_UP_CALL") || printModel.getType().equalsIgnoreCase("SOA-ROOM") ||
                printModel.getType().equalsIgnoreCase("SWITCH_ROOM")) {
            addTextToPrinter(SPrinter.getPrinter(),"ROOM #" + printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,2,2);
        }


        if (printModel.getType().equalsIgnoreCase("SOA-TO")) {
            addTextToPrinter(SPrinter.getPrinter(),"TAKEOUT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,2,2);
        }
    }


    public static void addHeader(PrintModel printModel, Printer printer, int i) {
        addTextToPrinter(printer, SharedPreferenceManager.getString(null, ApplicationConstants.RECEIPT_HEADER), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer,SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "SERIAL NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1,1);
        addTextToPrinter(printer, "VAT REG TIN NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER) , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(printer, "PERMIT NO:"+SharedPreferenceManager.getString(null, ApplicationConstants.PERMIT_NO) , Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );


        if (printModel.getType().equalsIgnoreCase("FO"))  addTextToPrinter(printer,"FOOD ORDER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("BACKOUT"))  addTextToPrinter(printer,"BACK OUT SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("PRINT_RECEIPT"))  addTextToPrinter(printer,"OFFICIAL RECEIPT(STORE COPY)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("DEPOSIT"))  addTextToPrinter(printer,"DEPOSIT SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("CHECKIN"))  addTextToPrinter(printer,"CHECK IN SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("VOID"))  addTextToPrinter(printer,"VOID SLIP" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("SOA-ROOM")) {
            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class);
            if (toList1.getIsSoa() > 1) {
                addTextToPrinter(printer, "STATEMENT OF ACCOUNT(REPRINT)", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            } else {
                addTextToPrinter(printer,"STATEMENT OF ACCOUNT" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }
        }
        if (printModel.getType().equalsIgnoreCase("SOA-TO")) {
            FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class);
            if (toList1.getIsSoa() > 1) {
                addTextToPrinter(printer, "STATEMENT OF ACCOUNT(REPRINT)", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            } else {
                addTextToPrinter(printer,"STATEMENT OF ACCOUNT" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }
        }
        if (printModel.getType().equalsIgnoreCase("POST_VOID"))  addTextToPrinter(printer,"V O I D" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        if (printModel.getType().equalsIgnoreCase("ZREAD"))  addTextToPrinter(printer,"Z-READING" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        //REPRINTZREAD
        if (printModel.getType().equalsIgnoreCase("REPRINTZREAD")) {
            addTextToPrinter(printer,"Z-READING(REPRINT)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }


        if (printModel.getType().equalsIgnoreCase("REPRINTXREADING")){
            addTextToPrinter(printer,"X-READING(REPRINT)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }
        if (printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT")) {
            addTextToPrinter(printer,"OFFICIAL RECEIPT(REPRINT)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }

        if (printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT_SPEC")) {
            addTextToPrinter(printer,"OFFICIAL RECEIPT(CUSTOMERS COPY)" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
        }


        if (printModel.getType().equalsIgnoreCase("FO") || printModel.getType().equalsIgnoreCase("BACKOUT") ||
                printModel.getType().equalsIgnoreCase("PRINT_RECEIPT") || printModel.getType().equalsIgnoreCase("DEPOSIT") ||
                printModel.getType().equalsIgnoreCase("CHECKIN") || printModel.getType().equalsIgnoreCase("VOID") ||
                printModel.getType().equalsIgnoreCase("SOA-ROOM") || printModel.getType().equalsIgnoreCase("POST_VOID") ||
                printModel.getType().equalsIgnoreCase("SOA-TO") || printModel.getType().equalsIgnoreCase("REPRINT_RECEIPT")) { //
            if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
                addTextToPrinter(printer,"ROOM #" + printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            } else {
                addTextToPrinter(printer,"TAKEOUT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1,2);
            }
        }

        if (printModel.getType().equalsIgnoreCase("CHANGE_WAKE_UP_CALL") || printModel.getType().equalsIgnoreCase("SOA-ROOM") ||
                printModel.getType().equalsIgnoreCase("SWITCH_ROOM")) {
            addTextToPrinter(SPrinter.getPrinter(),"ROOM #" + printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,2,2);
        }


        if (printModel.getType().equalsIgnoreCase("SOA-TO")) {
            addTextToPrinter(SPrinter.getPrinter(),"TAKEOUT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,2,2);
        }
    }

}
