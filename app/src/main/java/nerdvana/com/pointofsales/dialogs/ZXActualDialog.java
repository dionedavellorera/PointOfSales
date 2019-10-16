package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.ZXReceiptAdapter;
import nerdvana.com.pointofsales.api_requests.FetchXReadListRequestViaDate;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchZReadListRequestViaDate;
import nerdvana.com.pointofsales.api_requests.FetchZReadViaIdRequest;
import nerdvana.com.pointofsales.api_responses.FetchDiscountSpecialResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadListViaDateResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListViaDateResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PrintModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class ZXActualDialog extends Dialog {
    private String title;
    private String from;
    private RecyclerView rvList;

    private String startDate;
    private String endDate;

    private Button btnPrintAll;

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
        btnPrintAll = findViewById(R.id.btnPrintAll);

        if (from.equalsIgnoreCase("x")) {
            title = "X READ";

            FetchXReadListRequestViaDate fetchXReadListRequest = new FetchXReadListRequestViaDate(startDate, endDate);
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
            Call<FetchXReadListViaDateResponse> request = iUsers.fetchXReadListRequestViaDate(fetchXReadListRequest.getMapValue());
            request.enqueue(new Callback<FetchXReadListViaDateResponse>() {
                @Override
                public void onResponse(Call<FetchXReadListViaDateResponse> call, final Response<FetchXReadListViaDateResponse> response) {

                    Log.d("DSADASDSA", "DASDASDSADSA");

                    progress.setVisibility(View.GONE);
                    List<ZXReadModel> zxReadModelList = new ArrayList<>();
                    for (FetchXReadListViaDateResponse.Result res : response.body().getResult()) {

                        double otherDiscAmount = 0.00;
                        double pwdCount = 0;
                        double pwdAmount = 0;
                        double seniorCount = 0;
                        double seniorAmount = 0;
                        double othersCount = 0;
                        double othersAmount = 0;
                        for (FetchXReadListViaDateResponse.Discount disc : res.getDiscount()) {
                            if (disc.getDiscountTypeId() == 3) { //PWD
                                pwdCount += 1;
                                pwdAmount += disc.getDiscountAmount();
                            } else if (disc.getDiscountTypeId() == 2) { //SENIOR
                                seniorCount += 1;
                                seniorAmount += disc.getDiscountAmount();
                            } else {
                                othersCount += 1;
                                othersAmount += disc.getDiscountAmount();
                            }
                        }



                        Double totalAdvancePayment = 0.00;
                        Double cashSales = 0.00;
                        Double cardSales = 0.00;
                        Double onlineSales = 0.00;
                        for (int i = 0; i < res.getPayment().size(); i++) {
                            FetchXReadListViaDateResponse.Payment_ temp = res.getPayment().get(i);
                            if (String.valueOf(temp.getIsAdvance()).equalsIgnoreCase("1") || String.valueOf(temp.getIsAdvance()).equalsIgnoreCase("1.0")) {
                                totalAdvancePayment += Double.valueOf(temp.getAmount());
                            } else {
                                if (temp.getPaymentDescription().equalsIgnoreCase("cash")) {
                                    cashSales+= temp.getAmount();
                                } else if (temp.getPaymentDescription().equalsIgnoreCase("card")) {
                                    cardSales+= temp.getAmount();
                                } else if (temp.getPaymentDescription().equalsIgnoreCase("online")) {
                                    onlineSales+= temp.getAmount();
                                }
                            }
                        }






                        ZXReadModel zxReadModel = new ZXReadModel(
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
                                title,
                                res.getData().getCutOffDate(),
                                from.equalsIgnoreCase("x") ? "SHIFT " + String.valueOf(res.getData().getShiftNo()) : "",
                                res.getData().getCashier().getName(),
                                res.getData().getDutyManager().getName(),
                                String.valueOf(res.getData().getPosId()),
                                String.valueOf(res.getData().getGrossSales()),
                                String.valueOf(res.getData().getNetSales()),
                                String.valueOf(res.getData().getVatable()),
                                String.valueOf(res.getData().getVatExemptSales()),
                                String.valueOf(res.getData().getVat()),
                                String.valueOf(res.getData().getVatExempt()),
                                "0.00",
                                String.valueOf(cashSales),
                                String.valueOf(cardSales),
                                String.valueOf(totalAdvancePayment),
                                String.valueOf(onlineSales),
                                res.getData().getCashAndReco().get(0).getAdjustmentDeposit(),
                                String.valueOf(res.getData().getVoidAmount()),
                                String.valueOf(pwdCount),
                                String.valueOf(pwdAmount),
                                String.valueOf(seniorCount),
                                String.valueOf(seniorAmount),
                                String.valueOf(othersAmount),
                                "--",
                                "--",
                                "--",
                                "--",
                                "--",
                                String.valueOf(res.getData().getId()));
                        zxReadModelList.add(zxReadModel);
                    }

                    ZXRead zxRead = new ZXRead() {
                        @Override
                        public void reprint(String data) {
                            Log.d("MYDATAXREAD", "REQ");
                            FetchXReadingViaIdRequest fetchXReadingViaIdRequest = new FetchXReadingViaIdRequest(data);
                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                            Call<FetchXReadingViaIdResponse> request = iUsers.fetchXReadingViaId(fetchXReadingViaIdRequest.getMapValue());
                            request.enqueue(new Callback<FetchXReadingViaIdResponse>() {
                                @Override
                                public void onResponse(Call<FetchXReadingViaIdResponse> call, Response<FetchXReadingViaIdResponse> response) {

                                    Log.d("MYDATAXREAD", "XREDVIAID");

                                    BusProvider.getInstance().post(new PrintModel("", "X READING", "REPRINTXREADING", GsonHelper.getGson().toJson(response.body().getResult())));
//                                    BusProvider.getInstance().post(new PrintModel("", "SHORT/OVER", "SHORTOVER", GsonHelper.getGson().toJson(response.body().getResult())));
                                }

                                @Override
                                public void onFailure(Call<FetchXReadingViaIdResponse> call, Throwable t) {

                                }
                            });
                        }
                    };

                    ZXReceiptAdapter zxReceiptAdapter = new ZXReceiptAdapter(zxReadModelList, getContext(), from, zxRead);
                    rvList.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvList.setAdapter(zxReceiptAdapter);
                    zxReceiptAdapter.notifyDataSetChanged();


                    btnPrintAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (from.equalsIgnoreCase("X")) {
                                //print all x read
                                for (FetchXReadListViaDateResponse.Result r : response.body().getResult()) {
                                    FetchXReadingViaIdRequest fetchXReadingViaIdRequest = new FetchXReadingViaIdRequest(String.valueOf(r.getData().getId()));
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

                            } else {
                                //print all z read
                            }
                        }
                    });

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
                public void onResponse(Call<FetchZReadListViaDateResponse> call, final Response<FetchZReadListViaDateResponse> response) {
                    progress.setVisibility(View.GONE);
                    List<ZXReadModel> zxReadModelList = new ArrayList<>();

                    for (FetchZReadListViaDateResponse.Result res : response.body().getResult()) {

                        double otherDiscAmount = 0.00;
                        double pwdCount = 0;
                        double pwdAmount = 0;
                        double seniorCount = 0;
                        double seniorAmount = 0;
                        double othersCount = 0;
                        double othersAmount = 0;
                        for (FetchZReadListViaDateResponse.Discount disc : res.getDiscount()) {
                            if (disc.getDiscountTypeId() == 3) { //PWD
                                pwdCount += 1;
                                pwdAmount += disc.getDiscountAmount();
                            } else if (disc.getDiscountTypeId() == 2) { //SENIOR
                                seniorCount += 1;
                                seniorAmount += disc.getDiscountAmount();
                            } else {
                                othersCount += 1;
                                othersAmount += disc.getDiscountAmount();
                            }
                        }



                        Double totalAdvancePayment = 0.00;
                        Double cashSales = 0.00;
                        Double cardSales = 0.00;
                        Double onlineSales = 0.00;
                        Double depositAdjustment = 0.00;
                        for (int i = 0; i < res.getPayment().size(); i++) {
                            FetchZReadListViaDateResponse.Payment temp = res.getPayment().get(i);
                            if (String.valueOf(temp.getIsAdvance()).equalsIgnoreCase("1") || String.valueOf(temp.getIsAdvance()).equalsIgnoreCase("1.0")) {
                                totalAdvancePayment += Double.valueOf(temp.getAmount());
                            } else {
                                if (temp.getPaymentDescription().equalsIgnoreCase("cash")) {
                                    cashSales+= temp.getAmount();
                                } else if (temp.getPaymentDescription().equalsIgnoreCase("card")) {
                                    cardSales+= temp.getAmount();
                                } else if (temp.getPaymentDescription().equalsIgnoreCase("online")) {
                                    onlineSales+= temp.getAmount();
                                }
                            }
                        }

                        for (FetchZReadListViaDateResponse.CutOff cutOff : res.getData().getCutOff()) {
                            for (FetchZReadListViaDateResponse.CashAndReco cashAndReco : cutOff.getCashAndReco()) {
                                depositAdjustment += Double.valueOf(cashAndReco.getAdjustmentDeposit());
                            }
                        }



                        ZXReadModel zxReadModel = new ZXReadModel(
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
                                title,
                                res.getData().getGeneratedAt(),
                                from.equalsIgnoreCase("x") ? "xxxx" : "",
                                res.getData().getCashier().getName(),
                                res.getData().getDutyManager().getName(),
                                String.valueOf(res.getData().getPosId()),
                                String.valueOf(res.getData().getGrossSales()),
                                String.valueOf(res.getData().getNetSales()),
                                String.valueOf(res.getData().getVatable()),
                                String.valueOf(res.getData().getVatExemptSales()),
                                String.valueOf(res.getData().getVat()),
                                String.valueOf(res.getData().getVatExempt()),
                                "0.00",
                                String.valueOf(cashSales),
                                String.valueOf(cardSales),
                                String.valueOf(totalAdvancePayment),
                                String.valueOf(onlineSales),
                                String.valueOf(depositAdjustment),
                                String.valueOf(res.getData().getVoidAmount()),
                                String.valueOf(pwdCount),
                                String.valueOf(pwdAmount),
                                String.valueOf(seniorCount),
                                String.valueOf(seniorAmount),
                                String.valueOf(othersAmount),
                                res.getControlNo().size() > 0 ? res.getControlNo().get(0) : "NA",
                                res.getControlNo().size() > 0 ? res.getControlNo().get(res.getControlNo().size() - 1) : "NA",
                                returnWithTwoDecimal(String.valueOf(res.getOldGrandTotal())),
                                returnWithTwoDecimal(String.valueOf(res.getNewGrandTotal())),
                                String.valueOf(res.getCount()),
                                String.valueOf(res.getData().getId()));
                        zxReadModelList.add(zxReadModel);


                        btnPrintAll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (from.equalsIgnoreCase("X")) {
                                    //print all x read
                                } else {
                                    //print all z read
                                    for (FetchZReadListViaDateResponse.Result r : response.body().getResult()) {
                                        FetchZReadViaIdRequest fetchZReadViaIdRequest = new FetchZReadViaIdRequest(String.valueOf(r.getData().getId()));
                                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                        Call<ZReadResponse> request = iUsers.fetchZReadViaId(fetchZReadViaIdRequest.getMapValue());
                                        request.enqueue(new Callback<ZReadResponse>() {
                                            @Override
                                            public void onResponse(Call<ZReadResponse> call, Response<ZReadResponse> response) {
                                                BusProvider.getInstance().post(new PrintModel("", "ZREAD", "REPRINTZREAD", GsonHelper.getGson().toJson(response.body().getResult())));
                                            }
                                            @Override
                                            public void onFailure(Call<ZReadResponse> call, Throwable t) {

                                            }
                                        });
                                    }
                                }
                            }
                        });



                    }
                    ZXRead zxRead = new ZXRead() {
                        @Override
                        public void reprint(String data) {
                            FetchZReadViaIdRequest fetchZReadViaIdRequest = new FetchZReadViaIdRequest(data);
                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                            Call<ZReadResponse> request = iUsers.fetchZReadViaId(fetchZReadViaIdRequest.getMapValue());
                            request.enqueue(new Callback<ZReadResponse>() {
                                @Override
                                public void onResponse(Call<ZReadResponse> call, Response<ZReadResponse> response) {
                                    BusProvider.getInstance().post(new PrintModel("", "ZREAD", "REPRINTZREAD", GsonHelper.getGson().toJson(response.body().getResult())));
                                }
                                @Override
                                public void onFailure(Call<ZReadResponse> call, Throwable t) {

                                }
                            });


                        }
                    };

                    ZXReceiptAdapter zxReceiptAdapter = new ZXReceiptAdapter(zxReadModelList, getContext(), from, zxRead);
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

    public interface ZXRead {
        void reprint(String data);
    }
}
