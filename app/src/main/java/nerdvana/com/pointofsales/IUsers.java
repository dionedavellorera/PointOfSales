package nerdvana.com.pointofsales;

import java.util.Map;

import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
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
}
