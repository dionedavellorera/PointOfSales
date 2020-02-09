package nerdvana.com.pointofsales;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AlertDialog;

import android.text.TextUtils;
import android.util.Log;
import android.view.Display;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nerdvana.com.pointofsales.api_requests.TestConnectionRequest;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {

    static boolean canConnect;

    public static boolean isPasswordProtected(Context context, String moduleId) {
        boolean isProtected = true;
        List<String> accessList = new ArrayList<>();
        accessList = Arrays.asList(SharedPreferenceManager.getString(context, ApplicationConstants.ACCESS_RIGHTS).split(","));

        if (moduleId.equalsIgnoreCase("0")) {
            isProtected = false;
        } else {
            if (accessList.size() > 0) {
                if (accessList.contains(moduleId)) {
                    isProtected = false;
                }
            }
        }

        return isProtected;
    }



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
                finalValue = tempArray[0] + "." + (tempArray[1].length() == 1 ? tempArray[1] + "0" : tempArray[1]);
            }
        } else {
            finalValue = amount;
        }

        return finalValue;

    }

    public static String convertDateOnlyToReadableDate(String createdAt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(createdAt);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d");


            res = dtfOut.print(jodatime);
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
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d hh:mm a");

            res = dtfOut.print(jodatime);
        } catch (Exception e) {
            res  = "NA";
        }

        return res.toUpperCase();

    }

    public static long getDurationInSecs(String dateStart) {

        DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime startDate = f.parseDateTime(dateStart);
        return startDate.getMillis() / 1000;

    }

    public static String convertSecondsToReadableDate(long seconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy hh:mm:ss a");
        return formatter.format(new Date(seconds * 1000L));
    }

    public static String getDuration(String dateStart, String dateEnd) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime endDate = formatter.parseDateTime(dateEnd);
        DateTime startDate = formatter.parseDateTime(dateStart);
        Duration duration = new Duration(startDate, endDate);

        return formatSeconds(duration.getStandardSeconds());
    }

    private static String formatSeconds(long timeInSeconds)
    {
        long hours = timeInSeconds / 3600;
        long secondsLeft = timeInSeconds - hours * 3600;
        long minutes = secondsLeft / 60;
        long seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds ;

        return formattedTime;
    }

    public static String getSystemType(Context context) {
        if (SharedPreferenceManager.getString(context, ApplicationConstants.IS_SYSTEM_TABLE).equalsIgnoreCase("0") &&
                SharedPreferenceManager.getString(context, ApplicationConstants.IS_SYSTEM_ROOM).equalsIgnoreCase("0")) {
            return "franchise";
        } else if (SharedPreferenceManager.getString(context, ApplicationConstants.IS_SYSTEM_TABLE).equalsIgnoreCase("1")) {
            return "table";
        } else if (SharedPreferenceManager.getString(context, ApplicationConstants.IS_SYSTEM_ROOM).equalsIgnoreCase("1")) {
            return "room";
        } else {
            return "not_supported";
        }
    }
    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(c);
    }

    public static boolean canConnectToServer() {

        TestConnectionRequest testConnectionRequest = new TestConnectionRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<TestConnectionResponse> request = iUsers.sendTestRequest(
                testConnectionRequest.getMapValue());

        request.enqueue(new Callback<TestConnectionResponse>() {
            @Override
            public void onResponse(Call<TestConnectionResponse> call, Response<TestConnectionResponse> response) {
                Utils.canConnect = true;
            }

            @Override
            public void onFailure(Call<TestConnectionResponse> call, Throwable t) {




                Utils.canConnect = false;
            }
        });



        return Utils.canConnect;
    }

    public static String digitsWithComma(Double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }

    public static boolean isColorDark(String color){
        int mColor = Color.parseColor(color);
        double darkness = 1-(0.299* Color.red(mColor) + 0.587*Color.green(mColor) + 0.114*Color.blue(mColor))/255;
        if(darkness<0.5){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    public static String birDateTimeFormat(String currentString) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime jodatime = dtf.parseDateTime(currentString);
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        return dtfOut.print(jodatime);
    }

    public static String removeStartingZero(String myString) {
        return myString.replaceAll("^0*", "");
    }
}
