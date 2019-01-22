package nerdvana.com.pointofsales;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

import nerdvana.com.pointofsales.custom.BusProvider;

public class PosApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();


        new BusProvider();
        new GsonHelper();
        new SharedPreferenceManager(this);
        Stetho.initializeWithDefaults(this);
    }
}
