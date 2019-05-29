package nerdvana.com.pointofsales;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

import net.danlew.android.joda.JodaTimeAndroid;

public class PosApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();

        new SocketManager(this);
        new SocketManagerTwo(this);
        Bus mBus = BusProvider.getInstance();
        PosManager autopilotManager = new PosManager(this, mBus);
        mBus.register(autopilotManager);
//        mBus.register(BusProvider.getInstance());
        new UserServices(this);

//        new BusProvider();
        new GsonHelper();

        new DbInit().execute();

        new SharedPreferenceManager(this);
        Stetho.initializeWithDefaults(this);

        JodaTimeAndroid.init(this);
    }
}
