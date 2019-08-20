package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Reprint;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.ViewReceiptActualAdapter;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingViaControlNoRequest;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptViaDateResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewReceiptActualDialog extends Dialog implements Reprint {

    private List<ViewReceiptViaDateResponse.Result> result;

    private RecyclerView rvReceiptList;

    private Button btnReprintAll;

    public ViewReceiptActualDialog(@NonNull Context context, List<ViewReceiptViaDateResponse.Result> result) {
        super(context);
        this.result = result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_receipt_actual);
        btnReprintAll = findViewById(R.id.btnReprintAll);
        rvReceiptList = findViewById(R.id.rvReceiptList);

        List<ViewReceiptActualModel> list = new ArrayList<>();

        ViewReceiptActualAdapter viewReceiptActualAdapter =
                new ViewReceiptActualAdapter(list, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReceiptList.setLayoutManager(linearLayoutManager);
        rvReceiptList.setAdapter(viewReceiptActualAdapter);

        Log.d("WATEKTEK", String.valueOf(result.size()));

        btnReprintAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (final ViewReceiptViaDateResponse.Result r : result) {
                    FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(r.getControlNo());
                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                    Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
                    request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
                        @Override
                        public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                            String roomNo = "";
                            String roomType = "";
                            if (response.body().getResult().getGuestInfo() != null) {
                                roomNo = response.body().getResult().getGuestInfo().getRoomNo().toString();
                                roomType = response.body().getResult().getGuestInfo().getRoomType();
                            } else {
                                roomNo = "TAKEOUT";
                                roomType = "NA";
                            }
                            BusProvider.getInstance().post(new PrintModel(
                                    "", roomNo,
                                    "PRINT_RECEIPT",
                                    GsonHelper.getGson().toJson(response.body().getResult()),
                                    roomType));
                        }

                        @Override
                        public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

                        }
                    });
                }

            }
        });





        for (ViewReceiptViaDateResponse.Result r : result) {
            Log.d("WATEKTEK", r.getReceiptNo());

            String customerName = "";
            String customerAddress = "=";
            String customerTin = "";
            String customerBusinessStyle = "";

            if (r.getGuestInfo() != null) {
                if (r.getGuestInfo().getCustomer() != null) {
                    if (r.getGuestInfo().getCustomer().getCustomer().equalsIgnoreCase("empty") &&
                            r.getGuestInfo().getCustomer().getCustomer().equalsIgnoreCase("to be filled")) {
                        customerName = r.getGuestInfo().getCustomer().getCustomer();
                        customerAddress = r.getGuestInfo().getCustomer().getAddress().toString();
                        customerTin = r.getGuestInfo().getCustomer().getTin().toString();
                        customerBusinessStyle = r.getGuestInfo().getCustomer().getBusinessStyle().toString();
                    }
                }
            }

            list.add(new ViewReceiptActualModel(
                    //                                SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH),
                    "NERDVANA CORP",
//                                SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS),
                    "1 CANLEY ROAD BRGY. BAGONG ILOG PASIG CITY 1600",
                    SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_TELEPHONE),
//                                "SERIAL NO:" + SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER),
                    "SERIAL NO:" + "***-***-***",
                    "VAT REG TIN NO:" + "009-772-500-000",
//                                "VAT REG TIN NO:" + SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER),
//                                "PERMIT NO:" + SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_PERMIT),
                    "PERMIT NO:" + "***-***-***",
                    "MIN NO: " + "***-***-***",
                    r.getGuestInfo() == null ? "TAKEOUT" : r.getGuestInfo().getRoom().getRoomNo() != null ? r.getGuestInfo().getRoom().getRoomNo().toString() : "EMPTY",
                    r.getCashier().getName(),
                    r.getRoomBoy() == null ? "" : r.getRoomBoy().getName(),
                    r.getGuestInfo() == null ? "" : r.getGuestInfo().getCheckIn(),
                    r.getGuestInfo() == null ? "" : r.getGuestInfo().getCheckOut(),
                    r.getReceiptNo(),
                    String.valueOf(r.getPosId()),
                    String.valueOf(r.getVatExempt()),
                    String.valueOf(r.getDiscount()),
                    String.valueOf(r.getAdvance()),
                    String.valueOf(r.getTotal()),
                    String.valueOf(r.getTendered()),
                    String.valueOf(r.getChange()),
                    String.valueOf(r.getVatable()),
                    String.valueOf(r.getVatExemptSales()),
                    String.valueOf(r.getVat()),
                    String.valueOf(r.getPersonCount()),
                    String.valueOf(r.getTotalItem()),
                    "--",
                    r.getPost(),
                    String.valueOf(r.getOtHours()),
                    String.valueOf(r.getOtAmount()),
                    customerName,
                    customerAddress,
                    customerTin,
                    customerBusinessStyle,
                    r.getControlNo()));

        }

        viewReceiptActualAdapter.notifyDataSetChanged();
    }

    @Override
    public void data(String controlNumber) {
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                String roomNo = "";
                String roomType = "";
                if (response.body().getResult().getGuestInfo() != null) {
                    roomNo = response.body().getResult().getGuestInfo().getRoom().getRoomNo().toString();
                    roomType = response.body().getResult().getGuestInfo().getRoomType();
                } else {
                    roomNo = "TAKEOUT";
                    roomType = "NA";
                }
                BusProvider.getInstance().post(new PrintModel(
                        "", roomNo,
                        "PRINT_RECEIPT",
                        GsonHelper.getGson().toJson(response.body().getResult()),
                        roomType));
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });
    }



}
