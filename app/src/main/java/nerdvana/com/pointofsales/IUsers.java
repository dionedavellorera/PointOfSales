package nerdvana.com.pointofsales;

import java.util.Map;

import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
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
}