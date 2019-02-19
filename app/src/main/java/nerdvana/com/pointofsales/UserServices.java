package nerdvana.com.pointofsales;

import com.squareup.otto.Subscribe;

import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.FetchCarRequest;
import nerdvana.com.pointofsales.api_requests.FetchGuestTypeRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_requests.VerifyMachineRequest;
import nerdvana.com.pointofsales.api_requests.WelcomeGuestRequest;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
import nerdvana.com.pointofsales.api_responses.WelcomeGuestResponse;
import nerdvana.com.pointofsales.requests.TestRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class UserServices extends BaseService {
    public UserServices(PosApplication minutesApplication) {
        super(minutesApplication);
    }

    @Subscribe
    public void onReceiveTestConnection(TestRequest sendNotificationListRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<TestConnectionResponse> hotelCall = iUsers.sendTestRequest(
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

    @Subscribe
    public void onReceiveVerifyMachine(VerifyMachineRequest verifyMachineRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<VerifyMachineResponse> roomStatusListRequest = iUsers.sendVerifyMachineRequest(
                verifyMachineRequest.getMapValue());
        asyncRequest(roomStatusListRequest);
    }

    @Subscribe
    public void onReceiveCar(FetchCarRequest fetchCarRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchCarResponse> roomStatusListRequest = iUsers.sendFetchCarRequest(
                fetchCarRequest.getMapValue());
        asyncRequest(roomStatusListRequest);
    }

    @Subscribe
    public void onReceiveVehicle(FetchVehicleRequest fetchVehicleRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchVehicleResponse> roomStatusListRequest = iUsers.sendFetchVehicleRequest(
                fetchVehicleRequest.getMapValue());
        asyncRequest(roomStatusListRequest);
    }

    @Subscribe
    public void onReceiveGuestType(FetchGuestTypeRequest fetchGuestTypeRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchGuestTypeResponse> roomStatusListRequest = iUsers.sendFetchGuestTypeRequest(
                fetchGuestTypeRequest.getMapValue());
        asyncRequest(roomStatusListRequest);
    }

    @Subscribe
    public void onReceiveGuestType(WelcomeGuestRequest welcomeGuestRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<WelcomeGuestResponse> welcomeRequest = iUsers.sendWelcomeRequest(
                welcomeGuestRequest.getMapValue());
        asyncRequest(welcomeRequest);
    }

    @Subscribe
    public void onReceiveRoomPending(FetchRoomPendingRequest fetchRoomPendingRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomPendingResponse> welcomeRequest = iUsers.sendFetchRoomPendingRequest(
                fetchRoomPendingRequest.getMapValue());
        asyncRequest(welcomeRequest);
    }

    @Subscribe
    public void onReceiveCheckInResponse(CheckInRequest checkInRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckInResponse> checkinRequest = iUsers.sendCheckInRequest(
                checkInRequest.getMapValue());
        asyncRequest(checkinRequest);
    }

    @Subscribe
    public void onReceiveOffGoingNego(OffGoingNegoRequest offGoingNegoRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ResponseBody> checkinRequest = iUsers.sendOffGoingNegoRequest(
                offGoingNegoRequest.getMapValue());
        asyncRequest(checkinRequest);
    }

    @Subscribe
    public void onReceiveFetchPayment(FetchPaymentRequest fetchPaymentRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchPaymentResponse> checkinRequest = iUsers.sendFetchPaymentRequest(
                fetchPaymentRequest.getMapValue());
        asyncRequest(checkinRequest);
    }

    @Subscribe
    public void onReceiveAddRoomPrice(AddRoomPriceRequest addRoomPriceRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<AddRoomPriceResponse> checkinRequest = iUsers.sendAddRoomPriceRequest(
                addRoomPriceRequest.getMapValue());
        asyncRequest(checkinRequest);
    }
}
