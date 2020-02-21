package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
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
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.CashNReconcileRequest;
import nerdvana.com.pointofsales.api_requests.CheckSafeKeepingRequest;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_requests.CollectionRequest;
import nerdvana.com.pointofsales.api_requests.FetchDenominationRequest;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.CollectionResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.SafeKeepDataModel;
import nerdvana.com.pointofsales.model.SpotAuditModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public abstract class CollectionDialog extends BaseDialog {
    private String type;
    private LinearLayout relContainer;
    private LinearLayout linear;
    private Button save;

    private PasswordDialog passwordDialog;

    private List<SafeKeepDataModel> safeKeepDataModelList;
//    private List<SafeKeepDataModel> safeKeepDataModelListDisplay;
    private Activity act;
    private boolean willCashReco;
    List<CollectionFinalPostModel> collectionFinalPostModels;

    private Double totalSafeKeepAmount = 0.00;
    private Double totalSafeKeepAmountDisp = 0.00;

    private TextView totalSafeKeep;
    List<Double> totalAmountList = new ArrayList<>();


    public CollectionDialog(@NonNull Activity context, String type, boolean continueCashReco) {
        super(context);
        this.type = type;
        this.act = context;
        this.willCashReco = continueCashReco;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_collection, type);
        safeKeepDataModelList = new ArrayList<>();
        relContainer = findViewById(R.id.relContainer);
        save = findViewById(R.id.save);
        totalSafeKeep = findViewById(R.id.totalSafeKeep);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalSafeKeepAmount = 0.00;
                collectionFinalPostModels = new ArrayList<>();
                for (SafeKeepDataModel skdm : safeKeepDataModelList) {
                    if (!TextUtils.isEmpty(skdm.getEditText().getText().toString().trim())) {
                        if (Double.valueOf(skdm.getEditText().getText().toString()) > 0) {
                            if (skdm.getValue().equalsIgnoreCase("CHECK")) { //CHECK, SPECIAL CASE

                            } else {
                                totalSafeKeepAmount += (Double.valueOf(skdm.getValue()) * Double.valueOf(skdm.getEditText().getText().toString()));
                                collectionFinalPostModels.add(
                                        new CollectionFinalPostModel(String.valueOf(skdm.getEditText().getId()),
                                                skdm.getEditText().getText().toString(),
                                                skdm.getValue(),
                                                SharedPreferenceManager.getString(null, ApplicationConstants.COUNTRY_CODE),
                                                SharedPreferenceManager.getString(null, ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                                                SharedPreferenceManager.getString(null, ApplicationConstants.MACHINE_ID),
                                                SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID),
                                                ""
                                        ));
                            }
                        }
                    }
                }

                if (willCashReco) {
                    if (Utils.isPasswordProtected(getContext(), "72")) {
                        if (passwordDialog == null) {
                            passwordDialog = new PasswordDialog(act, "X READING PROCESS", "") {

                                @Override
                                public void passwordSuccess(String employeeId, String employeeName) {
                                    doCashAndRecoFunction(employeeId);
                                }

                                @Override
                                public void passwordFailed() {

                                }
                            };

                            passwordDialog.setOnCancelListener(new OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    passwordDialog = null;
                                }
                            });

                            passwordDialog.setOnDismissListener(new OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    passwordDialog = null;
                                }
                            });
                            passwordDialog.show();
                        }

                    } else {
                        doCashAndRecoFunction(SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID));
                    }


                } else {
                    if (collectionFinalPostModels.size() > 0) {
                        CheckSafeKeepingRequest checkSafeKeepingRequest = new CheckSafeKeepingRequest();
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<CheckSafeKeepingResponse> checkSafeKeepRequest = iUsers.checkSafeKeeping(checkSafeKeepingRequest.getMapValue());
                        checkSafeKeepRequest.enqueue(new Callback<CheckSafeKeepingResponse>() {
                            @Override
                            public void onResponse(Call<CheckSafeKeepingResponse> call, Response<CheckSafeKeepingResponse> response) {

                                if (type.equalsIgnoreCase("SPOT AUDIT")) {
                                    SpotAuditModel spotAuditModel = new SpotAuditModel(
                                            String.valueOf(totalSafeKeepAmount - response.body().getResult().getUnCollected()),
                                            collectionFinalPostModels,
                                            String.valueOf(response.body().getResult().getUnCollected()));

                                    BusProvider.getInstance()
                                            .post(
                                                    new PrintModel("",
                                                            "",
                                                            "SPOT_AUDIT_PRINT",
                                                            GsonHelper.getGson().toJson(spotAuditModel)));

                                    dismiss();
                                } else {
                                    if (totalSafeKeepAmount <= response.body().getResult().getUnCollected()) {
                                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                        CollectionRequest collectionRequest = new CollectionRequest(collectionFinalPostModels);


                                        Call<CollectionResponse> request = iUsers.collectionRequest(collectionRequest.getMapValue());

                                        request.enqueue(new Callback<CollectionResponse>() {
                                            @Override
                                            public void onResponse(Call<CollectionResponse> call, Response<CollectionResponse> response) {
                                                if (response.body().getStatus() == 0) {
                                                    Utils.showDialogMessage(act, response.body().getMessage(), "Information");
                                                } else {



                                                    for (CollectionFinalPostModel cfpm : collectionFinalPostModels) {
                                                        Log.d("SAFEKEEPVALUE", cfpm.getAmount() +" - " + cfpm.getCash_valued());
                                                    }

                                                    TypeToken<List<FetchDenominationResponse.Result>> collectionToken = new TypeToken<List<FetchDenominationResponse.Result>>() {};
                                                    List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.CASH_DENO_JSON), collectionToken.getType());
                                                    Double finalAmount = 0.00;
                                                    for (FetchDenominationResponse.Result cfm : denoDetails) {
                                                        String valueCount = "0";
                                                        String valueAmount = "0.00";
                                                        for (CollectionFinalPostModel c : collectionFinalPostModels) {

                                                            Log.d("QWEQWE", String.valueOf(cfm.getCoreId()) + " -- " +c.getCash_denomination_id());

                                                            if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                                                                valueCount = c.getAmount();
                                                                valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                                                                break;
                                                            }
                                                        }

                                                        Log.d("SAFEKEEPVALUE", String.valueOf(valueAmount) + " - " + String.valueOf(valueCount));


                                                    }


                                                    BusProvider.getInstance().post(new PrintModel("",
                                                            "",
                                                            "SAFEKEEPING",
                                                            GsonHelper.getGson().toJson(collectionFinalPostModels),
                                                            "", "", ""));


                                                    dismiss();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<CollectionResponse> call, Throwable t) {

                                            }
                                        });
                                    } else {
                                        Utils.showDialogMessage(act, "The amount you entered is more than the actual sales", "Error");
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<CheckSafeKeepingResponse> call, Throwable t) {

                            }
                        });
                    } else {
                        Utils.showDialogMessage(act, "Please put amount for safekeep", "Information");
                    }
                }

            }
        });
        requestDenomination();
    }

    private void doCashAndRecoFunction(String employeeId) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        CashNReconcileRequest collectionRequest = new CashNReconcileRequest(collectionFinalPostModels, employeeId);
        Call<Object> request = iUsers.cashNReconcile(collectionRequest.getMapValue());
        request.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject jsonObject = new JSONObject(GsonHelper.getGson().toJson(response.body()));
                    if (jsonObject.getString("status").equalsIgnoreCase("1.0") || jsonObject.getString("status").equalsIgnoreCase("1")) {
                        printCashRecoData(GsonHelper.getGson().toJson(jsonObject.getJSONArray("result").get(0)));

                        BusProvider.getInstance().post(new PrintModel("",
                                "",
                                "CASHRECONCILE",
                                GsonHelper.getGson().toJson(collectionFinalPostModels),
                                "", "", ""));

                        Utils.showDialogMessage(act, "X READ SUCCESS", "Information");
                    } else {
                        Utils.showDialogMessage(act, jsonObject.getString("message"), "Information");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    private void requestDenomination() {
        FetchDenominationRequest fetchDenominationRequest = new FetchDenominationRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDenominationResponse> request = iUsers.fetchDenomination(fetchDenominationRequest.getMapValue());
        request.enqueue(new Callback<FetchDenominationResponse>() {
            @Override
            public void onResponse(Call<FetchDenominationResponse> call, Response<FetchDenominationResponse> response) {
                //do logic here
                for (FetchDenominationResponse.Result r : response.body().getResult()) {
                    addView(r.getDenomination(), String.valueOf(r.getAmount()), r.getCoreId());
                }

//                addView("CHECK", "CHECK", 9999);
            }

            @Override
            public void onFailure(Call<FetchDenominationResponse> call, Throwable t) {

            }
        });
    }

    private  void addView(String amountToDisplay, final String actualAmount, int id) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        linearLayout.setLayoutParams(parentParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(1);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);

        TextView textView = new TextView(getContext());
        textView.setHeight(50);
        textView.setGravity(Gravity.CENTER);
        textView.setText(amountToDisplay);
        textView.setLayoutParams(params1);
        linearLayout.addView(textView);

        final EditText editText = new EditText(getContext());
        editText.setHeight(50);
        editText.setHint(actualAmount);
        editText.setId(id);
        editText.setLayoutParams(params1);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTextIsSelectable(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                totalSafeKeepAmountDisp = 0.00;
                for (SafeKeepDataModel skdm : safeKeepDataModelList) {
                    if (!TextUtils.isEmpty(skdm.getEditText().getText().toString())) {
                        totalSafeKeepAmountDisp += Double.valueOf(skdm.getValue()) * Double.valueOf(skdm.getEditText().getText().toString());
                    }

                }

                totalSafeKeep.setText("PHP " + totalSafeKeepAmountDisp);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        linearLayout.addView(editText);

        safeKeepDataModelList.add(new SafeKeepDataModel(editText, actualAmount));
//        safeKeepDataModelListDisplay.add(new SafeKeepDataModel(editText, actualAmount));

        relContainer.addView(linearLayout);
    }

    public abstract void printCashRecoData(String cashNRecoData);
}
