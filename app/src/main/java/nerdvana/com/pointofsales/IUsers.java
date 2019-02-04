package nerdvana.com.pointofsales;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUsers {
    @POST("inspection/notificationsList")
    @FormUrlEncoded
    Call<ResponseBody> sendTestRequest(@FieldMap Map<String, String> params);
}
