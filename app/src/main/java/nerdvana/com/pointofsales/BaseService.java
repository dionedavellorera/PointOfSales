package nerdvana.com.pointofsales;

import android.util.Log;

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
                try {
                    if (response.code() == 200) {
                        Log.d("OBJECTVALUE", response.body().toString());
                        BusProvider.getInstance().post(response.body());
                    } else if (response.code() == 403){
//                        BusProvider.getInstance().post(new ErrorModel("There is an error"));
                    }
                } catch (Exception e) {
//                    BusProvider.getInstance().post(new ErrorModel("There is an error"));
                }

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
//                BusProvider.getInstance().post(new SendWeatherEventError(t.getLocalizedMessage()));
            }
        });

    }

}
