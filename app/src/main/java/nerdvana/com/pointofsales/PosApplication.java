package nerdvana.com.pointofsales;

import android.app.Application;

import nerdvana.com.pointofsales.custom.BusProvider;

public class PosApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();


        new BusProvider();
        new GsonHelper();
        new SharedPreferenceManager(this);
    }
}
