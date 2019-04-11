package nerdvana.com.pointofsales.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.background.CountUpTimer;
import nerdvana.com.pointofsales.model.TimerModel;

public class TimerService extends Service {
    long secsOfDate = 0;
    String startDate = "";
    CountDownTimer countUpTimer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TIMER_SERVICe", "TIMER STARTED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDate = intent.getStringExtra("start_time");
        secsOfDate = Utils.getDurationInSecs(startDate);

        countUpTimer = new CountUpTimer(999999999) {
                @Override
                public void onTick(int second) {
                    secsOfDate+= 1;
                    BusProvider.getInstance().post(new TimerModel(Utils.convertSecondsToReadableDate(secsOfDate)));
                }
            }.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countUpTimer != null) {
            countUpTimer.cancel();
            countUpTimer = null;
        }
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
