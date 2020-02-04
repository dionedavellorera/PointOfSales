package nerdvana.com.pointofsales;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaCodec;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PatternMatcher;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

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
//            mSocket = IO.socket("http://192.168.1.23:6965", opts);
            Log.d("SUCKET", SharedPreferenceManager.getString(SocketManager.context, ApplicationConstants.NODE_URL));
            if (android.util.Patterns.WEB_URL.matcher(SharedPreferenceManager.getString(SocketManager.context, ApplicationConstants.NODE_URL)).matches()) {
                mSocket = IO.socket(SharedPreferenceManager.getString(SocketManager.context, ApplicationConstants.NODE_URL), opts);

                mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        Log.d("REFRESHROOM", "Y-0");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                isConnected = true;
//                            BusProvider.getInstance().post(new SocketConnectionModel("T"));
                            }
                        });
                    }

                }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        isConnected = false;
                        Log.d("REFRESHROOM", "Y-ERR");
//                    BusProvider.getInstance().post(new SocketConnectionModel("T"));
                    }

                }).on("online_users", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                        BusProvider.getInstance().post(new UpdateDataModel("y"));

                    }
                }).on("loadroom", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d("REFRESHROOM", "Y-1");
                        BusProvider.getInstance().post(new UpdateDataModel("y"));

                    }
                });
                if (!mSocket.connected()) {
                    mSocket.connect();
                }
            } else {
                Toast.makeText(context, "Invalid node url", Toast.LENGTH_LONG).show();
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
