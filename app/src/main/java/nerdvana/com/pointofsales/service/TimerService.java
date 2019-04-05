package nerdvana.com.pointofsales.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

public class TimerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TIMER_SERVICe", "TIMER STARTED");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TIMER_SERVICe", "TIMER STOPPED");
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
