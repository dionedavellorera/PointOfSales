package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.ReprintSkAdapter;
import nerdvana.com.pointofsales.api_requests.CheckSafeKeepingRequest;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.interfaces.SafeKeepingContract;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.SkListModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReprintSkDialog extends BaseDialog implements SafeKeepingContract {

    private RecyclerView rvSkList;
    private TextView tvNoData;
    private SwipeRefreshLayout refreshSkList;

    public ReprintSkDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_reprint_sk, "REPRINT SK");
        initViews();
        initRefreshListener();
        checkSafeKeep();
    }

    private void initRefreshListener() {
        refreshSkList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkSafeKeep();
            }
        });
    }

    private void initViews() {
        rvSkList = findViewById(R.id.rvSkList);
        tvNoData = findViewById(R.id.tvNoData);
        refreshSkList = findViewById(R.id.refreshSkList);
    }

    private List<SkListModel> denominationData(List<CheckSafeKeepingResponse.Liiiist> list){
        List<SkListModel> mySkList = new ArrayList<>();
        TypeToken<List<FetchDenominationResponse.Result>> collectionToken = new TypeToken<List<FetchDenominationResponse.Result>>() {};
        List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.CASH_DENO_JSON), collectionToken.getType());
        Double finalAmount = 0.00;
        int counter = 1;
        for (CheckSafeKeepingResponse.Liiiist outerSk : list) {

            List<CollectionFinalPostModel> tmpCfpm = new ArrayList<>();
            SkListModel tmpSk = new SkListModel(Double.valueOf(outerSk.getAmount()), String.valueOf(counter));

            for (CheckSafeKeepingResponse.Denomination innerList : outerSk.getDenominationList()) {

                CollectionFinalPostModel cfpm = new CollectionFinalPostModel(innerList.getCashDenominationId(),
                        innerList.getAmount(),
                        innerList.getCashDenominationValue(),
                        SharedPreferenceManager.getString(null, ApplicationConstants.COUNTRY_CODE),
                        SharedPreferenceManager.getString(null, ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                        SharedPreferenceManager.getString(null, ApplicationConstants.MACHINE_ID),
                        SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID),
                        ""
                );
                cfpm.setSkCount(counter);

                tmpCfpm.add(cfpm);
            }
            tmpSk.setCfpmList(tmpCfpm);
            mySkList.add(tmpSk);
            counter++;
        }

        return mySkList;
    }

    private void setSkAdapter(List<SkListModel> list) {
        ReprintSkAdapter reprintSkAdapter = new ReprintSkAdapter(list, getContext(), this);
        rvSkList.setAdapter(reprintSkAdapter);
        rvSkList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void checkSafeKeep() {
        CheckSafeKeepingRequest checkSafeKeepingRequest = new CheckSafeKeepingRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckSafeKeepingResponse> checkSafeKeepRequest = iUsers.checkSafeKeeping(checkSafeKeepingRequest.getMapValue());
        checkSafeKeepRequest.enqueue(new Callback<CheckSafeKeepingResponse>() {
            @Override
            public void onResponse(Call<CheckSafeKeepingResponse> call, Response<CheckSafeKeepingResponse> response) {
                refreshSkList.setRefreshing(false);
                if (response.body().getResult() != null) {
                    if (response.body().getResult().getList().size() > 0) {
                        setSkAdapter(denominationData(response.body().getResult().getList()));
                        tvNoData.setVisibility(View.GONE);
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckSafeKeepingResponse> call, Throwable t) {
                tvNoData.setVisibility(View.VISIBLE);
                refreshSkList.setRefreshing(false);
            }
        });
    }

    @Override
    public void reprintSk(List<CollectionFinalPostModel> collectionFinalPostModelList) {
        BusProvider.getInstance().post(new PrintModel("",
                "",
                "SAFEKEEPING",
                GsonHelper.getGson().toJson(collectionFinalPostModelList),
                "", "", ""));
    }
}
