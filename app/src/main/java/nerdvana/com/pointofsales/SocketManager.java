package nerdvana.com.pointofsales;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketManager {
    static Socket mSocket;
    private static Context context;
    private static Handler handler;
    private static boolean isConnected;
    public SocketManager(Context context) {
        SocketManager.context = context;
        initializeSocket();
        handler = new Handler(Looper.getMainLooper());
    }
    public static Socket getInstance() {
        if (mSocket == null) {
            initializeSocket();
        }
        return mSocket;
    }

    public void disconnectSocket() {
        mSocket.disconnect();
    }

    private static void initializeSocket() {
        IO.Options opts = new IO.Options();
        try {
            mSocket = IO.socket("http://192.168.1.23:6965", opts);

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("SOCKET_CONNECTION", "CONNECTED TO SOCKET ONE");
                            isConnected = true;
//                            BusProvider.getInstance().post(new SocketConnectionModel("T"));
                        }
                    });
                }

            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    isConnected = false;
//                    BusProvider.getInstance().post(new SocketConnectionModel("T"));
                }

            }).on("reminder", new Emitter.Listener() {
                @Override
                public void call(Object... args) { //to be used in future

//                    SocketManager.showNotification("New Priority Inspection");

                }
            }).on("online_users", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
//                    SocketManager.showNotification("New Priority Inspection");
                    JSONObject data = (JSONObject) args[0];
                    try {
                        if (data.getString("locale_id").equalsIgnoreCase("8")) {
                            BusProvider.getInstance().post(new UpdateDataModel("y"));
                        }
                        Log.d("DATATEST", data.getString("locale_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (!mSocket.connected()) {
                mSocket.connect();
            }

        } catch (URISyntaxException e) {
        }
    }

    public static boolean isConnected() {
        return isConnected;
    }

//    private static void showNotification(String content) {
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SocketManager.context, "101")
//                .setSmallIcon(R.mipmap.baseline_nfc_black_24)
//                .setContentTitle("Autopilot")
//                .setContentText(content)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(content))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "channelname";
//            String description = "channel description";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel("101", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = SocketManager.context.getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            notificationManager.notify(101, mBuilder.build());
//        }
//    }
}
