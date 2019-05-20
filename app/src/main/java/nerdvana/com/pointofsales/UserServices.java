package nerdvana.com.pointofsales;

import com.squareup.otto.Subscribe;

import nerdvana.com.pointofsales.api_requests.AddPaymentRequest;
import nerdvana.com.pointofsales.api_requests.AddProductToRequest;
import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_requests.AutoDiscountRequest;
import nerdvana.com.pointofsales.api_requests.BackupDatabaseRequest;
import nerdvana.com.pointofsales.api_requests.CancelOverTimeRequest;
import nerdvana.com.pointofsales.api_requests.CheckGcRequest;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.CheckOutRequest;
import nerdvana.com.pointofsales.api_requests.CheckSafeKeepingRequest;
import nerdvana.com.pointofsales.api_requests.CheckShiftRequest;
import nerdvana.com.pointofsales.api_requests.CollectionRequest;
import nerdvana.com.pointofsales.api_requests.FetchArOnlineRequest;
import nerdvana.com.pointofsales.api_requests.FetchBranchInfoRequest;
import nerdvana.com.pointofsales.api_requests.FetchCarRequest;
import nerdvana.com.pointofsales.api_requests.FetchCompanyUserRequest;
import nerdvana.com.pointofsales.api_requests.FetchCreditCardRequest;
import nerdvana.com.pointofsales.api_requests.FetchCurrencyExceptDefaultRequest;
import nerdvana.com.pointofsales.api_requests.FetchDefaultCurrencyRequest;
import nerdvana.com.pointofsales.api_requests.FetchDenominationRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountSpecialRequest;
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
import nerdvana.com.pointofsales.api_requests.FetchTimeRequest;
import nerdvana.com.pointofsales.api_requests.FetchUserRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FocTransactionRequest;
import nerdvana.com.pointofsales.api_requests.GetOrderRequest;
import nerdvana.com.pointofsales.api_requests.LoginRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_requests.PostVoidRequest;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
import nerdvana.com.pointofsales.api_requests.SwitchRoomRequest;
import nerdvana.com.pointofsales.api_requests.VerifyMachineRequest;
import nerdvana.com.pointofsales.api_requests.ViewReceiptRequest;
import nerdvana.com.pointofsales.api_requests.WakeUpCallUpdateRequest;
import nerdvana.com.pointofsales.api_requests.WelcomeGuestRequest;
import nerdvana.com.pointofsales.api_responses.AddPaymentResponse;
import nerdvana.com.pointofsales.api_responses.AddProductToResponse;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.AutoDiscountResponse;
import nerdvana.com.pointofsales.api_responses.CancelOverTimeResponse;
import nerdvana.com.pointofsales.api_responses.CheckGcResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckOutResponse;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.CheckShiftResponse;
import nerdvana.com.pointofsales.api_responses.CollectionResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchDefaultCurrenyResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountReasonResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountSpecialResponse;
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
import nerdvana.com.pointofsales.api_responses.FetchTimeResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FocTransactionResponse;
import nerdvana.com.pointofsales.api_responses.GetOrderResponse;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
import nerdvana.com.pointofsales.api_responses.PostVoidResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.SwitchRoomResponse;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
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

    @Subscribe
    public void fetchDenomination(FetchDenominationRequest fetchDenominationRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDenominationResponse> request = iUsers.fetchDenomination(fetchDenominationRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchDenomination(FetchDiscountRequest fetchDiscountRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDiscountResponse> request = iUsers.fetchDiscount(fetchDiscountRequest.getMapValue());
        asyncRequest(request);
    }


    @Subscribe
    public void fetchCompanyUser(FetchCompanyUserRequest fetchCompanyUserRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchCompanyUserResponse> request = iUsers.fetchCompanyUser(fetchCompanyUserRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void autoDiscount(AutoDiscountRequest autoDiscountRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<AutoDiscountResponse> request = iUsers.sendAutoDiscount(autoDiscountRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchDiscountReason(FetchDiscountRequest fetchDiscountRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDiscountReasonResponse> request = iUsers.fetchDiscountReason(fetchDiscountRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchDiscountReason(ViewReceiptRequest viewReceiptRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ViewReceiptResponse> request = iUsers.viewReceipt(viewReceiptRequest.getMapValue());
        asyncRequest(request);
    }


    @Subscribe
    public void voidReceipt(PostVoidRequest postVoidRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<PostVoidResponse> request = iUsers.voidReceipt(postVoidRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void updateGuestInfo(WakeUpCallUpdateRequest wakeUpCallUpdateRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<PostVoidResponse> request = iUsers.voidReceipt(wakeUpCallUpdateRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void collectionSafeKeepCashReco(CollectionRequest collectionRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CollectionResponse> request = iUsers.collectionRequest(collectionRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void checkSafeKeeping(CheckSafeKeepingRequest checkSafeKeepingRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckSafeKeepingResponse> request = iUsers.checkSafeKeeping(checkSafeKeepingRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void cancelOverTime(CancelOverTimeRequest cancelOverTimeRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CancelOverTimeResponse> request = iUsers.cancelOverTime(cancelOverTimeRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchServerTime(FetchTimeRequest fetchTimeRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchTimeResponse> request = iUsers.fetchTime(fetchTimeRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void checkShift(CheckShiftRequest checkShiftRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckShiftResponse> request = iUsers.checkShift(checkShiftRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchXReadViaId(FetchXReadingViaIdRequest fetchXReadingViaIdRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchXReadingViaIdResponse> request = iUsers.fetchXReadingViaId(fetchXReadingViaIdRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchDiscountSpecial(FetchDiscountSpecialRequest fetchDiscountSpecialRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDiscountSpecialResponse> request = iUsers.fetchDiscountSpecial(fetchDiscountSpecialRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void fetchBranchInfo(FetchBranchInfoRequest fetchBranchInfoRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchBranchInfoResponse> request = iUsers.fetchBranchInfo(fetchBranchInfoRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void focRequest(FocTransactionRequest focRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FocTransactionResponse> request = iUsers.focTransactionRequest(focRequest.getMapValue());
        asyncRequest(request);
    }

    @Subscribe
    public void backupDatabase(BackupDatabaseRequest backupDatabaseRequest) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ResponseBody> request = iUsers.backupDb(backupDatabaseRequest.getMapValue());
        asyncRequest(request);
    }

}
