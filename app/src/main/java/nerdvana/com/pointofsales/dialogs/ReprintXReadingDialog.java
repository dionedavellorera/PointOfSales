package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.squareup.otto.Subscribe;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.ReprintXReadingAdapter;
import nerdvana.com.pointofsales.api_requests.FetchXReadListRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReprintXReadingDialog extends BaseDialog {
    private RecyclerView listXReading;
    private ReprintXReadingAdapter reprintXReadingAdapter;
    public ReprintXReadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_reprint_xreading, "REPRINT X READING");
        listXReading = findViewById(R.id.listXReading);

        FetchXReadListRequest fetchXReadListRequest = new FetchXReadListRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchXReadListResponse> request = iUsers.fetchXReadListRequest(fetchXReadListRequest.getMapValue());

        request.enqueue(new Callback<FetchXReadListResponse>() {
            @Override
            public void onResponse(Call<FetchXReadListResponse> call, Response<FetchXReadListResponse> response) {
                Item item = new Item() {
                    @Override
                    public void clicked(String xReadingId) {
                        FetchXReadingViaIdRequest fetchXReadingViaIdRequest = new FetchXReadingViaIdRequest(xReadingId);
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<FetchXReadingViaIdResponse> request = iUsers.fetchXReadingViaId(fetchXReadingViaIdRequest.getMapValue());
                        request.enqueue(new Callback<FetchXReadingViaIdResponse>() {
                            @Override
                            public void onResponse(Call<FetchXReadingViaIdResponse> call, Response<FetchXReadingViaIdResponse> response) {
                                BusProvider.getInstance().post(new PrintModel("", "X READING", "REXREADING", GsonHelper.getGson().toJson(response.body().getResult())));
                                BusProvider.getInstance().post(new PrintModel("", "SHORT/OVER", "SHORTOVER", GsonHelper.getGson().toJson(response.body().getResult())));
                            }

                            @Override
                            public void onFailure(Call<FetchXReadingViaIdResponse> call, Throwable t) {

                            }
                        });

                    }
                };
                reprintXReadingAdapter = new ReprintXReadingAdapter(response.body().getResult(), item);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                listXReading.setLayoutManager(linearLayoutManager);
                listXReading.setAdapter(reprintXReadingAdapter);
                reprintXReadingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FetchXReadListResponse> call, Throwable t) {

            }
        });
    }

    public interface Item {
        void clicked(String xReadingId);
    }

}
