package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Reprint;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
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
                                roomNo = response.body().getResult().getGuestInfo().getRoomNo() == null ? "NA" : response.body().getResult().getGuestInfo().getRoomNo();
                                roomType = response.body().getResult().getGuestInfo().getRoomType();
                            } else {
                                roomNo = "TAKEOUT";
                                roomType = "NA";
                            }
                            BusProvider.getInstance().post(new PrintModel(
                                    "", roomNo,
                                    "REPRINT_RECEIPT",
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
                    "ABC COMPANY",
//                                SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ADDRESS),
                    "1 ABC ST. DE AVE\nPASIG CITY 1600",
                    "TEL NO: 8123-4567",
//                                "SERIAL NO:" + SharedPreferenceManager.getString(null, ApplicationConstants.SERIAL_NUMBER),
                    "SERIAL NO:" + "*******",
                    "VAT REG TIN NO:" + "009-772-500-000",
//                                "VAT REG TIN NO:" + SharedPreferenceManager.getString(null, ApplicationConstants.TIN_NUMBER),
//                                "PERMIT NO:" + SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_PERMIT),
                    "PERMIT NO:" + "*******-***-******-*****",
                    "MIN NO: " + "***-***-***",
                    r.getGuestInfo() == null ? "OFFICIAL RECEIPT(REPRINT)\n\n"+ "TAKEOUT" :  r.getGuestInfo().getRoom().getRoomNo() != null ? "OFFICIAL RECEIPT(REPRINT)\n\n"+"ROOM #" + r.getGuestInfo().getRoom().getRoomNo().toString() : "EMPTY",
                    r.getCashierOut().getName(),
                    r.getRoomBoy() == null ? "" : r.getGuestInfo().getRoomBoyIn().getName(),
                    r.getGuestInfo() == null ? "" : Utils.birDateTimeFormat(r.getGuestInfo().getCheckIn()),
                    r.getGuestInfo() == null ? "" : Utils.birDateTimeFormat(r.getGuestInfo().getCheckOut()),
                    r.getReceiptNo(),
                    String.valueOf(r.getPosId()),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getVatExempt() > 0 ? r.getVatExempt() * -1 : 0.00)),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getDiscount() > 0 ? r.getDiscount() * -1 : 0.00)),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getAdvance())),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf((r.getTotal() + r.getOtAmount() + r.getxPersonAmount())
                            - (r.getAdvance() + r.getDiscount() + r.getVatExempt())
                    )),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getTendered())),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getChange() < 1 ? -1 * r.getChange() : r.getChange())),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getVatable())),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getVatExemptSales())),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getVat())),
                    String.valueOf(r.getPersonCount()),
                    String.valueOf(r.getTotalItem()),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf((r.getTotal() + r.getOtAmount() + r.getxPersonAmount()))),
                    r.getPost(),
                    String.valueOf(r.getOtHours()),
                    PrinterUtils.returnWithTwoDecimal(String.valueOf(r.getOtAmount())),
                    customerName,
                    customerAddress,
                    customerTin,
                    customerBusinessStyle,
                    r.getControlNo(),
                    r.getControlNo().split("-")[2],
                    r.getDiscounts(),
                    r.getCheckedOutAt()));

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

                Log.d("ROOMNO", roomNo);

                BusProvider.getInstance().post(new PrintModel(
                        "", roomNo,
                        "REPRINT_RECEIPT",
                        GsonHelper.getGson().toJson(response.body().getResult()),
                        roomType));
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });
    }



}
