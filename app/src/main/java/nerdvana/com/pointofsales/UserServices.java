package nerdvana.com.pointofsales;

import com.squareup.otto.Subscribe;

import nerdvana.com.pointofsales.requests.TestRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserServices extends BaseService {
    public UserServices(PosApplication minutesApplication) {
        super(minutesApplication);
    }

    @Subscribe
    public void onReceiveFb(TestRequest sendNotificationListRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ResponseBody> hotelCall = iUsers.sendTestRequest(
                sendNotificationListRequest.getMapValue());
        asyncRequest(hotelCall);
    }


}
