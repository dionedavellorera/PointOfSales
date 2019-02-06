package nerdvana.com.pointofsales;

import com.squareup.otto.Subscribe;

import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
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

    @Subscribe
    public void onReceiveRoomList(FetchRoomRequest fetchRoomRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomResponse> roomlistRequest = iUsers.sendRoomListRequest(
                fetchRoomRequest.getMapValue());
        asyncRequest(roomlistRequest);
    }

    @Subscribe
    public void onReceiveRoomStatusList(FetchRoomStatusRequest fetchRoomStatusRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomStatusResponse> roomStatusListRequest = iUsers.sendRoomStatusListRequest(
                fetchRoomStatusRequest.getMapValue());
        asyncRequest(roomStatusListRequest);
    }

}
