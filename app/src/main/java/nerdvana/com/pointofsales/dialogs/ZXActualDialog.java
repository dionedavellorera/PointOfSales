package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.ZXReceiptAdapter;
import nerdvana.com.pointofsales.api_requests.FetchXReadListRequestViaDate;
import nerdvana.com.pointofsales.api_requests.FetchZReadListRequestViaDate;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadListViaDateResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListViaDateResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZXActualDialog extends Dialog {
    private String title;
    private String from;
    private RecyclerView rvList;

    private String startDate;
    private String endDate;

    private ProgressBar progress;
    public ZXActualDialog(@NonNull Context context, String from,
                          String startDate, String endDate) {
        super(context);
        this.from = from;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zx_actual_receipt);
        rvList = findViewById(R.id.rvList);
        progress = findViewById(R.id.progress);



        if (from.equalsIgnoreCase("x")) {
            title = "X READ";

            FetchXReadListRequestViaDate fetchXReadListRequest = new FetchXReadListRequestViaDate(startDate, endDate);
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
            Call<FetchXReadListViaDateResponse> request = iUsers.fetchXReadListRequestViaDate(fetchXReadListRequest.getMapValue());
            request.enqueue(new Callback<FetchXReadListViaDateResponse>() {
                @Override
                public void onResponse(Call<FetchXReadListViaDateResponse> call, Response<FetchXReadListViaDateResponse> response) {
                    progress.setVisibility(View.GONE);
                    Log.d("TESTDATA_XREAD", "SUCCESS");

                    List<ZXReadModel> zxReadModelList = new ArrayList<>();

                    for (FetchXReadListViaDateResponse.Result res : response.body().getResult()) {
                        ZXReadModel zxReadModel = new ZXReadModel("NERDVANA CORP",
                                "BAGONG ILOG PASIG CITY",
                                "671 97 82",
                                "SERIAL NO: *******",
                                "VAT REG TIN NO:009-772-500-000",
                                "PERMIT NO:*****-*****-****-**",
                                "MIN NO: ************",
                                title,
                                res.getCutOffDate(),
                                from.equalsIgnoreCase("x") ? "SHIFT " + String.valueOf(res.getShiftNo()) : "",
                                res.getCashier().getName(),
                                res.getDutyManager().getName(),
                                String.valueOf(res.getPosId()),
                                String.valueOf(res.getGrossSales()),
                                String.valueOf(res.getNetSales()),
                                String.valueOf(res.getVatable()),
                                String.valueOf(res.getVatExemptSales()),
                                String.valueOf(res.getVat()),
                                String.valueOf(res.getVatExempt()),
                                "0.00",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                String.valueOf(res.getVoidAmount()),
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--");
                        zxReadModelList.add(zxReadModel);
                    }

                    ZXReceiptAdapter zxReceiptAdapter = new ZXReceiptAdapter(zxReadModelList, getContext(), from);
                    rvList.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvList.setAdapter(zxReceiptAdapter);
                    zxReceiptAdapter.notifyDataSetChanged();




                }

                @Override
                public void onFailure(Call<FetchXReadListViaDateResponse> call, Throwable t) {
                    Log.d("TESTDATA_FAIL", t.getLocalizedMessage());
                    progress.setVisibility(View.GONE);
                }
            });

        } else { //z
            title = "Z READ";
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
            FetchZReadListRequestViaDate fetchZReadListRequest = new FetchZReadListRequestViaDate(startDate, endDate);
            Call<FetchZReadListViaDateResponse> request = iUsers.fetchZReadListRequestViaDate(fetchZReadListRequest.getMapValue());

            request.enqueue(new Callback<FetchZReadListViaDateResponse>() {
                @Override
                public void onResponse(Call<FetchZReadListViaDateResponse> call, Response<FetchZReadListViaDateResponse> response) {
                    progress.setVisibility(View.GONE);
                    List<ZXReadModel> zxReadModelList = new ArrayList<>();

                    for (FetchZReadListViaDateResponse.Result res : response.body().getResult()) {
                        ZXReadModel zxReadModel = new ZXReadModel("NERDVANA CORP",
                                "BAGONG ILOG PASIG CITY",
                                "671 97 82",
                                "SERIAL NO: *******",
                                "VAT REG TIN NO:009-772-500-000",
                                "PERMIT NO:*****-*****-****-**",
                                "MIN NO: ************",
                                title,
                                res.getGeneratedAt(),
                                from.equalsIgnoreCase("x") ? "SHIFT 3" : "",
                                res.getCashier().getName(),
                                res.getDutyManager().getName(),
                                String.valueOf(res.getPosId()),
                                String.valueOf(res.getGrossSales()),
                                String.valueOf(res.getNetSales()),
                                String.valueOf(res.getVatable()),
                                String.valueOf(res.getVatExemptSales()),
                                String.valueOf(res.getVat()),
                                String.valueOf(res.getVatExempt()),
                                "0.00",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                String.valueOf(res.getVoidAmount()),
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                "--");
                        zxReadModelList.add(zxReadModel);
                    }

                    ZXReceiptAdapter zxReceiptAdapter = new ZXReceiptAdapter(zxReadModelList, getContext(), from);
                    rvList.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvList.setAdapter(zxReceiptAdapter);
                    zxReceiptAdapter.notifyDataSetChanged();

                    Log.d("TESTDATA_ZREAD", "SUCCESS");
                }



                @Override
                public void onFailure(Call<FetchZReadListViaDateResponse> call, Throwable t) {
                    Log.d("TESTDATA_FAIL", t.getLocalizedMessage());
                    progress.setVisibility(View.GONE);
                }
            });


        }







    }
}
