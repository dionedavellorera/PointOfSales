package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.ReprintXReadingAdapter;
import nerdvana.com.pointofsales.adapters.ReprintZReadingAdapter;
import nerdvana.com.pointofsales.api_requests.FetchXReadListRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchZReadListRequest;
import nerdvana.com.pointofsales.api_requests.FetchZReadViaIdRequest;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReprintZReadingDialog extends BaseDialog {
    private RecyclerView listXReading;
    private ReprintZReadingAdapter reprintXReadingAdapter;
    public ReprintZReadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_reprint_xreading, "REPRINT Z READING");
        listXReading = findViewById(R.id.listXReading);

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        FetchZReadListRequest fetchZReadListRequest = new FetchZReadListRequest();
        Call<FetchZReadListResponse> request = iUsers.fetchZReadListRequest(fetchZReadListRequest.getMapValue());
        request.enqueue(new Callback<FetchZReadListResponse>() {
            @Override
            public void onResponse(Call<FetchZReadListResponse> call, Response<FetchZReadListResponse> response) {
                Item item = new Item() {
                    @Override
                    public void clicked(String zReadingId) {
                        FetchZReadViaIdRequest fetchZReadViaIdRequest = new FetchZReadViaIdRequest(zReadingId);
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<ZReadResponse> request = iUsers.fetchZReadViaId(fetchZReadViaIdRequest.getMapValue());
                        request.enqueue(new Callback<ZReadResponse>() {
                            @Override
                            public void onResponse(Call<ZReadResponse> call, Response<ZReadResponse> response) {
                                BusProvider.getInstance().post(new PrintModel("", "ZREAD", "ZREAD", GsonHelper.getGson().toJson(response.body().getResult())));
                            }

                            @Override
                            public void onFailure(Call<ZReadResponse> call, Throwable t) {

                            }
                        });
                    }
                };
                reprintXReadingAdapter = new ReprintZReadingAdapter(response.body().getResult(), item);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                listXReading.setLayoutManager(linearLayoutManager);
                listXReading.setAdapter(reprintXReadingAdapter);
                reprintXReadingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FetchZReadListResponse> call, Throwable t) {

            }
        });
    }

    public interface Item {
        void clicked(String xReadingId);
    }

}
