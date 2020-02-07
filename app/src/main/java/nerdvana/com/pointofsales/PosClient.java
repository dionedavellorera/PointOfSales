package nerdvana.com.pointofsales;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PosClient {
    private static String BASE_URL = "http://192.168.1.5:3000/api/";
    private static PosClient mPosClient;
    public static Retrofit mRestAdapter;

    public static PosClient getClient() {
        if (mPosClient == null)
            mPosClient = new PosClient();
        return mPosClient;
    }

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        BASE_URL = newApiBaseUrl;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                addInterceptor(httpLoggingInterceptor)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        mRestAdapter = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private PosClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        mRestAdapter = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
