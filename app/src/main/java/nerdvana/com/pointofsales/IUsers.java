package nerdvana.com.pointofsales;

import java.util.Map;

import nerdvana.com.pointofsales.api_responses.AddPaymentResponse;
import nerdvana.com.pointofsales.api_responses.AddProductToResponse;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.AutoDiscountResponse;
import nerdvana.com.pointofsales.api_responses.BackOutGuestResponse;
import nerdvana.com.pointofsales.api_responses.CancelOverTimeResponse;
import nerdvana.com.pointofsales.api_responses.CheckEndOfDayResponse;
import nerdvana.com.pointofsales.api_responses.CheckGcResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckOutResponse;
import nerdvana.com.pointofsales.api_responses.CheckPermissionResponse;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.CheckShiftResponse;
import nerdvana.com.pointofsales.api_responses.CheckXReadResponse;
import nerdvana.com.pointofsales.api_responses.CollectionResponse;
import nerdvana.com.pointofsales.api_responses.DiscountResponse;
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
import nerdvana.com.pointofsales.api_responses.FetchRoomRatePriceIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchTimeResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListResponse;
import nerdvana.com.pointofsales.api_responses.FocTransactionResponse;
import nerdvana.com.pointofsales.api_responses.GetOrderResponse;
import nerdvana.com.pointofsales.api_responses.LoginResponse;
import nerdvana.com.pointofsales.api_responses.PostVoidResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.SaveGuestInfoResponse;
import nerdvana.com.pointofsales.api_responses.SwitchRoomResponse;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import nerdvana.com.pointofsales.api_responses.UpdateCheckInTimeResponse;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.api_responses.VoidDiscountResponse;
import nerdvana.com.pointofsales.api_responses.VoidPaymentResponse;
import nerdvana.com.pointofsales.api_responses.WakeUpCallUpdateResponse;
import nerdvana.com.pointofsales.api_responses.WelcomeGuestResponse;
import nerdvana.com.pointofsales.api_responses.XReadResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
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


    @POST("checkGc")
    @FormUrlEncoded
    Call<CheckGcResponse> checkGc(@FieldMap Map<String, String> params);

    @POST("switchRoom")
    @FormUrlEncoded
    Call<SwitchRoomResponse> switchRoom(@FieldMap Map<String, String> params);

    @POST("fetchRoomViaId")
    @FormUrlEncoded
    Call<FetchRoomViaIdResponse> fetchRoomViaId(@FieldMap Map<String, String> params);

    @POST("fetchNationality")
    @FormUrlEncoded
    Call<FetchNationalityResponse> fetchNationality(@FieldMap Map<String, String> params);

    @POST("sendDiscount")
    @FormUrlEncoded
    Call<DiscountResponse> sendDiscount(@FieldMap Map<String, String> params);

    @POST("sendDiscount")
    @FormUrlEncoded
    Call<AutoDiscountResponse> sendAutoDiscount(@FieldMap Map<String, String> params);

    @POST("fetchCashDenomination")
    @FormUrlEncoded
    Call<FetchDenominationResponse> fetchDenomination(@FieldMap Map<String, String> params);

    @POST("fetchDiscount")
    @FormUrlEncoded
    Call<FetchDiscountResponse> fetchDiscount(@FieldMap Map<String, String> params);

    @POST("fetchCompanyUser")
    @FormUrlEncoded
    Call<FetchCompanyUserResponse> fetchCompanyUser(@FieldMap Map<String, String> params);

    @POST("fetchDiscountReason")
    @FormUrlEncoded
    Call<FetchDiscountReasonResponse> fetchDiscountReason(@FieldMap Map<String, String> params);

    @POST("viewReceipt")
    @FormUrlEncoded
    Call<ViewReceiptResponse> viewReceipt(@FieldMap Map<String, String> params);

    @POST("voidReceipt")
    @FormUrlEncoded
    Call<PostVoidResponse> voidReceipt(@FieldMap Map<String, String> params);

    @POST("wakeUpCallUpdate")
    @FormUrlEncoded
    Call<WakeUpCallUpdateResponse> updateWakeUpCall(@FieldMap Map<String, String> params);

    @POST("safeKeeping")
    @FormUrlEncoded
    Call<CollectionResponse> collectionRequest(@FieldMap Map<String, String> params);

    @POST("xReading")
    @FormUrlEncoded
    Call<XReadResponse> xReading(@FieldMap Map<String, String> params);

    @POST("zReading")
    @FormUrlEncoded
    Call<ResponseBody> zReading(@FieldMap Map<String, String> params);

    @POST("checkEndOfDay")
    @FormUrlEncoded
    Call<CheckEndOfDayResponse> checkEndOfDay(@FieldMap Map<String, String> params);

    @POST("cashAndReconcile")
    @FormUrlEncoded
    Call<Object> cashNReconcile(@FieldMap Map<String, String> params);

    @POST("checkSafeKeeping")
    @FormUrlEncoded
    Call<CheckSafeKeepingResponse> checkSafeKeeping(@FieldMap Map<String, String> params);

    @POST("cancelOverTime")
    @FormUrlEncoded
    Call<CancelOverTimeResponse> cancelOverTime(@FieldMap Map<String, String> params);

    //CheckXReadRequest()
    @POST("checkXReading")
    @FormUrlEncoded
    Call<CheckXReadResponse> checkXREad(@FieldMap Map<String, String> params);

    //fetchTime
    @POST("fetchTime")
    @FormUrlEncoded
    Call<FetchTimeResponse> fetchTime(@FieldMap Map<String, String> params);


    //checkShift
    @POST("checkShift")
    @FormUrlEncoded
    Call<CheckShiftResponse> checkShift(@FieldMap Map<String, String> params);

    @POST("fetchXReadingViaId")
    @FormUrlEncoded
    Call<FetchXReadingViaIdResponse> fetchXReadingViaId(@FieldMap Map<String, String> params);

    @POST("fetchZReadingViaId")
    @FormUrlEncoded
    Call<ZReadResponse> fetchZReadViaId(@FieldMap Map<String, String> params);



    @POST("fetchDiscountSpecial")
    @FormUrlEncoded
    Call<FetchDiscountSpecialResponse> fetchDiscountSpecial(@FieldMap Map<String, String> params);


    @POST("saveGuestInfo") //SAVE USER DETAILS FOR RECEIPT
    @FormUrlEncoded
    Call<SaveGuestInfoResponse> saveGuestInfo(@FieldMap Map<String, String> params);
    @POST("backOutGuest")
    @FormUrlEncoded
    Call<BackOutGuestResponse> backOutGuest(@FieldMap Map<String, String> params);
    @POST("updateCheckIn")
    @FormUrlEncoded
    Call<UpdateCheckInTimeResponse> updateCheckInTime(@FieldMap Map<String, String> params);


    @POST("fetchBranchInfo")
    @FormUrlEncoded
    Call<FetchBranchInfoResponse> fetchBranchInfo(@FieldMap Map<String, String> params);

    @POST("voidPayment")
    @FormUrlEncoded
    Call<VoidPaymentResponse> voidPayment(@FieldMap Map<String, String> params);

    @POST("voidDiscount")
    @FormUrlEncoded
    Call<VoidDiscountResponse> voidDiscount(@FieldMap Map<String, String> params);

    @POST("getFoc")
    @FormUrlEncoded
    Call<FocTransactionResponse> focTransactionRequest(@FieldMap Map<String, String> params);

    @POST("fetchXReading")
    @FormUrlEncoded
    Call<FetchXReadListResponse> fetchXReadListRequest(@FieldMap Map<String, String> params);

    @POST("fetchZReading")
    @FormUrlEncoded
    Call<FetchZReadListResponse> fetchZReadListRequest(@FieldMap Map<String, String> params);
    //region pending
    @POST("mysqlDump")
    @FormUrlEncoded
    Call<ResponseBody> backupDb(@FieldMap Map<String, String> params);
    @POST("rePatch")
    @FormUrlEncoded
    Call<ResponseBody> repatchData(@FieldMap Map<String, String> params);
    //endregion

    @POST("testSend")
    @FormUrlEncoded
    Call<ResponseBody> testSend(@FieldMap Map<String, String> params);

    @POST("checkPermission")
    @FormUrlEncoded
    Call<CheckPermissionResponse> checkPermission(@FieldMap Map<String, String> params);


    @POST("fetchRoomRatePriceId")
    @FormUrlEncoded
    Call<FetchRoomRatePriceIdResponse> fetchRoomRatePriceId(@FieldMap Map<String, String> params);
}
