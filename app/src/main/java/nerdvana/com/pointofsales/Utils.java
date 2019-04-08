package nerdvana.com.pointofsales;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.Display;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utils {
    public static boolean checkConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static int getDeviceWidth(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static void showDialogMessage(Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        if (!alert.isShowing()) {
            alert.show();
        }
    }

    public static String returnWithTwoDecimal(String amount) {
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
}
