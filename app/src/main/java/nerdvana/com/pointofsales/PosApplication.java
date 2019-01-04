package nerdvana.com.pointofsales;

import android.app.Application;

public class PosApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        new GsonHelper();
        new SharedPreferenceManager(this);
    }
}
