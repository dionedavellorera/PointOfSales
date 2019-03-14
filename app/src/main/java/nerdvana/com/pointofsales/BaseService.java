package nerdvana.com.pointofsales;

import android.util.Log;

import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseService {

    public BaseService(PosApplication posApplication) {
        BusProvider.getInstance().register(this);
    }

    protected <T> void asyncRequest(final Call<T> apiCall) {
        apiCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                BusProvider.getInstance().post(response.body());
//                try {
//                    BusProvider.getInstance().post(response.body());
//                } catch (Exception e) {
//                    BusProvider.getInstance().post(new ApiError(e.getMessage()));
//                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
//                BusProvider.getInstance().post(new SendWeatherEventError(t.getLocalizedMessage()));
            }
        });

    }

}
