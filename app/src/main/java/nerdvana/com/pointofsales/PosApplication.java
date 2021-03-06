package nerdvana.com.pointofsales;

import android.content.pm.PackageManager;

import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mazenrashed.printooth.Printooth;
import com.orm.SugarApp;
import com.squareup.otto.Bus;
import com.sunmi.devicesdk.core.DeviceManager;

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

        new SocketManager(getApplicationContext());
//        DeviceManager.getInstance().initialization(this);

        if (isPackageInstalled("com.sunmi.devicemanager", this.getPackageManager())) {
            DeviceManager.getInstance().initialization(this);
        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
