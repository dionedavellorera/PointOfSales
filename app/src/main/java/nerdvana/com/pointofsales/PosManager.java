package nerdvana.com.pointofsales;

import android.content.Context;

import com.squareup.otto.Bus;

public class PosManager {
    private Context mContext;
    private Bus mBus;
    private PosClient posClient;
    private PosClientCompany posClientCompany;
    public PosManager(Context context, Bus bus) {
        this.mContext = context;
        this.mBus = bus;
        posClient = PosClient.getClient();
        posClientCompany = PosClientCompany.getClient();
    }


}
