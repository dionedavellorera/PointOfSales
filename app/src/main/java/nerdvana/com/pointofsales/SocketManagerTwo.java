package nerdvana.com.pointofsales;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketManagerTwo {

    static Socket mSocket;
    private static Context context;
    private static Handler handler;
    private static boolean isConnected;
    public SocketManagerTwo(Context context) {
        SocketManagerTwo.context = context;
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
            mSocket = IO.socket("http://ws.buytheminute.biz", opts);

            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("SOCKET_CONNECTION", "CONNECTED TO SOCKET TWO");
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

}
