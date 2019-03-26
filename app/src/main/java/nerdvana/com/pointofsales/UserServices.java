package nerdvana.com.pointofsales;

import com.squareup.otto.Subscribe;

import nerdvana.com.pointofsales.api_requests.AddPaymentRequest;
import nerdvana.com.pointofsales.api_requests.AddProductToRequest;
import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_requests.CheckGcRequest;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.CheckOutRequest;
import nerdvana.com.pointofsales.api_requests.FetchArOnlineRequest;
import nerdvana.com.pointofsales.api_requests.FetchCarRequest;
import nerdvana.com.pointofsales.api_requests.FetchCreditCardRequest;
import nerdvana.com.pointofsales.api_requests.FetchCurrencyExceptDefaultRequest;
import nerdvana.com.pointofsales.api_requests.FetchDefaultCurrencyRequest;
import nerdvana.com.pointofsales.api_requests.FetchGuestTypeRequest;
import nerdvana.com.pointofsales.api_requests.FetchNationalityRequest;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingViaControlNoRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchProductsRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomAreaRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchUserRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.FocRequest;
import nerdvana.com.pointofsales.api_requests.GetOrderRequest;
import nerdvana.com.pointofsales.api_requests.LoginRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
import nerdvana.com.pointofsales.api_requests.SwitchRoomRequest;
import nerdvana.com.pointofsales.api_requests.VerifyMachineRequest;
import nerdvana.com.pointofsales.api_requests.WelcomeGuestRequest;
import nerdvana.com.pointofsales.api_responses.AddPaymentResponse;
import nerdvana.com.pointofsales.api_responses.AddProductToResponse;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.CheckGcResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckOutResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchDefaultCurrenyResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchNationalityResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FocResponse;
import nerdvana.com.pointofsales.api_responses.GetOrderResponse;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.SwitchRoomResponse;
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

    @Subscribe
    public void onReceiveFetchProductsRequest(FetchProductsRequest fetchProductsRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchProductsResponse> request = iUsers.sendFetchProductsRequest(fetchProductsRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveLoginRequest(LoginRequest loginRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<LoginResponse> request = iUsers.sendLoginRequest(loginRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveAddPaymentRequest(AddPaymentRequest addPaymentRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<AddPaymentResponse> request = iUsers.sendAddPayment(addPaymentRequest.getMapValue());
        asyncRequest(request);
    }

    //printSoa
    @Subscribe
    public void onReceiveAddPaymentRequest(PrintSoaRequest printSoaRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<PrintSoaResponse> request = iUsers.printSoa(printSoaRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveFetchRoomAreaRequest(FetchRoomAreaRequest fetchRoomAreaRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomAreaResponse> request = iUsers.fetchRoomArea(fetchRoomAreaRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveFetchRoomPendingRequest(FetchOrderPendingRequest fetchOrderPendingRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingResponse> request = iUsers.fetchOrderPending(fetchOrderPendingRequest.getMapValue());
        asyncRequest(request);
    }


    @Subscribe
    public void onReceiveFetchRoomPendingRequest(FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveGetOrderRequest(GetOrderRequest getOrderRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<GetOrderResponse> request = iUsers.getOrder(getOrderRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveFetchUserRequest(FetchUserRequest fetchUserRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchUserResponse> request = iUsers.fetchUser(fetchUserRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveAddProductTo(AddProductToRequest addProductToRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<AddProductToResponse> request = iUsers.addProductTo(addProductToRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveCheckoutRequest(CheckOutRequest checkOutRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckOutResponse> request = iUsers.checkOut(checkOutRequest.getMapValue());
        asyncRequest(request);
    }

    //
    @Subscribe
    public void onReceiveCheckoutRequest(FetchDefaultCurrencyRequest fetchDefaultCurrencyRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDefaultCurrenyResponse> request = iUsers.fetchDefaultCurrency(fetchDefaultCurrencyRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveCheckoutRequest(FetchCurrencyExceptDefaultRequest fetchCurrencyExceptDefaultRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchCurrencyExceptDefaultResponse> request = iUsers.fetchCurrencyExceptDefault(fetchCurrencyExceptDefaultRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveFetchArOnline(FetchArOnlineRequest fetchArOnlineRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchArOnlineResponse> request = iUsers.fetchArOnline(fetchArOnlineRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveFetchArOnline(FetchCreditCardRequest fetchCreditCardRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchCreditCardResponse> request = iUsers.fetchCreditCard(fetchCreditCardRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onReceiveFocRequest(FocRequest focRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FocResponse> request = iUsers.focTransaction(focRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onCheckGc(CheckGcRequest checkGcRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckGcResponse> request = iUsers.checkGc(checkGcRequest.getMapValue());
        asyncRequest(request);
    }

    //switchRoom
    @Subscribe
    public void onCheckGc(SwitchRoomRequest switchRoomRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<SwitchRoomResponse> request = iUsers.switchRoom(switchRoomRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onCheckGc(FetchRoomViaIdRequest fetchRoomViaIdRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchRoomViaIdResponse> request = iUsers.fetchRoomViaId(fetchRoomViaIdRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void onCheckGc(FetchNationalityRequest fetchNationalityRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchNationalityResponse> request = iUsers.fetchNationality(fetchNationalityRequest.getMapValue());
        asyncRequest(request);
    }
}
