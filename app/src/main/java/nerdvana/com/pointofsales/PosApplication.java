package nerdvana.com.pointofsales;

import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mazenrashed.printooth.Printooth;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

import net.danlew.android.joda.JodaTimeAndroid;

public class PosApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();

        Printooth.INSTANCE.init(this);
        FirebaseAnalytics.getInstance(this);
        Bus mBus = BusProvider.getInstance();
        PosManager autopilotManager = new PosManager(this, mBus);
        mBus.register(autopilotManager);
        new UserServices(this);
        new GsonHelper();

        new DbInit().execute();

        new SharedPreferenceManager(this);
        Stetho.initializeWithDefaults(this);

        JodaTimeAndroid.init(this);
    }
}
