package nerdvana.com.pointofsales;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;
import com.squareup.otto.Bus;

public class PosApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();


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
    }
}
