package nerdvana.com.pointofsales;

import java.util.Map;

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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUsers {
    @POST("test")
    @FormUrlEncoded
    Call<TestConnectionResponse> sendTestRequest(@FieldMap Map<String, String> params);

    @POST("fetchRoom")
    @FormUrlEncoded
    Call<FetchRoomResponse> sendRoomListRequest(@FieldMap Map<String, String> params);

    @POST("fetchRoomStatus")
    @FormUrlEncoded
    Call<FetchRoomStatusResponse> sendRoomStatusListRequest(@FieldMap Map<String, String> params);

    @POST("verifyMachine")
    @FormUrlEncoded
    Call<VerifyMachineResponse> sendVerifyMachineRequest(@FieldMap Map<String, String> params);

    @POST("fetchCar")
    @FormUrlEncoded
    Call<FetchCarResponse> sendFetchCarRequest(@FieldMap Map<String, String> params);

    @POST("fetchVehicle")
    @FormUrlEncoded
    Call<FetchVehicleResponse> sendFetchVehicleRequest(@FieldMap Map<String, String> params);

    @POST("fetchGuestType")
    @FormUrlEncoded
    Call<FetchGuestTypeResponse> sendFetchGuestTypeRequest(@FieldMap Map<String, String> params);

    @POST("booked")
    @FormUrlEncoded
    Call<WelcomeGuestResponse> sendWelcomeRequest(@FieldMap Map<String, String> params);


    @POST("fetchRoomPending")
    @FormUrlEncoded
    Call<FetchRoomPendingResponse> sendFetchRoomPendingRequest(@FieldMap Map<String, String> params);

    @POST("checkIn")
    @FormUrlEncoded
    Call<CheckInResponse> sendCheckInRequest(@FieldMap Map<String, String> params);

    @POST("offGoingNego")
    @FormUrlEncoded
    Call<ResponseBody> sendOffGoingNegoRequest(@FieldMap Map<String, String> params);

    @POST("fetchPaymentType")
    @FormUrlEncoded
    Call<FetchPaymentResponse> sendFetchPaymentRequest(@FieldMap Map<String, String> params);

    @POST("addProduct")
    @FormUrlEncoded
    Call<AddRoomPriceResponse> sendAddRoomPriceRequest(@FieldMap Map<String, String> params);

    @POST("fetchProducts")
    @FormUrlEncoded
    Call<FetchProductsResponse> sendFetchProductsRequest(@FieldMap Map<String, String> params);

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> sendLoginRequest(@FieldMap Map<String, String> params);

    @POST("sendPayment")
    @FormUrlEncoded
    Call<AddPaymentResponse> sendAddPayment(@FieldMap Map<String, String> params);

    @POST("soa")
    @FormUrlEncoded
    Call<PrintSoaResponse> printSoa(@FieldMap Map<String, String> params);

    //fetchRoomArea
    @POST("fetchRoomArea")
    @FormUrlEncoded
    Call<FetchRoomAreaResponse> fetchRoomArea(@FieldMap Map<String, String> params);

    @POST("fetchOrderPending")
    @FormUrlEncoded
    Call<FetchOrderPendingResponse> fetchOrderPending(@FieldMap Map<String, String> params);

    @POST("fetchOrderPendingViaControlNo")
    @FormUrlEncoded
    Call<FetchOrderPendingViaControlNoResponse> fetchOrderPendingViaControlNo(@FieldMap Map<String, String> params);

    @POST("getOrder")
    @FormUrlEncoded
    Call<GetOrderResponse> getOrder(@FieldMap Map<String, String> params);

    @POST("fetchUser")
    @FormUrlEncoded
    Call<FetchUserResponse> fetchUser(@FieldMap Map<String, String> params);

    @POST("addProductTo")
    @FormUrlEncoded
    Call<AddProductToResponse> addProductTo(@FieldMap Map<String, String> params);

    @POST("checkOut")
    @FormUrlEncoded
    Call<CheckOutResponse> checkOut(@FieldMap Map<String, String> params);

    //fetchDefaultCurrency
    @POST("fetchDefaultCurrency")
    @FormUrlEncoded
    Call<FetchDefaultCurrenyResponse> fetchDefaultCurrency(@FieldMap Map<String, String> params);

    //fetchCurrencyExceptDefault
    @POST("fetchCurrencyExceptDefault")
    @FormUrlEncoded
    Call<FetchCurrencyExceptDefaultResponse> fetchCurrencyExceptDefault(@FieldMap Map<String, String> params);

    //fetchAROnline
    @POST("fetchAROnline")
    @FormUrlEncoded
    Call<FetchArOnlineResponse> fetchArOnline(@FieldMap Map<String, String> params);

    //fetchCreditCard
    @POST("fetchCreditCard")
    @FormUrlEncoded
    Call<FetchCreditCardResponse> fetchCreditCard(@FieldMap Map<String, String> params);

    @POST("foc")
    @FormUrlEncoded
    Call<FocResponse> focTransaction(@FieldMap Map<String, String> params);

    @POST("checkGc")
    @FormUrlEncoded
    Call<CheckGcResponse> checkGc(@FieldMap Map<String, String> params);

    @POST("switchRoom")
    @FormUrlEncoded
    Call<SwitchRoomResponse> switchRoom(@FieldMap Map<String, String> params);

    @POST("fetchRoomViaId")
    @FormUrlEncoded
    Call<FetchRoomViaIdResponse> fetchRoomViaId(@FieldMap Map<String, String> params);
}
