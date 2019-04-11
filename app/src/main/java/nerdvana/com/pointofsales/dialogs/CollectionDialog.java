package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
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
import nerdvana.com.pointofsales.api_requests.XReadRequest;
import nerdvana.com.pointofsales.api_responses.CashNReconcileResponse;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.CollectionResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.XReadResponse;
import nerdvana.com.pointofsales.model.SafeKeepDataModel;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionDialog extends BaseDialog {
    private String type;
    private LinearLayout relContainer;
    private LinearLayout linear;
    private Button save;

    private List<SafeKeepDataModel> safeKeepDataModelList;
    private Context act;
    private boolean willCashReco;
    List<CollectionFinalPostModel> collectionFinalPostModels;

    private Double totalSafeKeepAmount = 0.00;

    public CollectionDialog(@NonNull Context context, String type, boolean continueCashReco) {
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



                if (collectionFinalPostModels.size() > 0) {
                    if (willCashReco) {
                        PasswordDialog passwordDialog = new PasswordDialog(act) {
                            @Override
                            public void passwordSuccess(String employeeId) {
                                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                XReadRequest collectionRequest = new XReadRequest(collectionFinalPostModels, employeeId);
                                Call<XReadResponse> request = iUsers.xReading(collectionRequest.getMapValue());
                                request.enqueue(new Callback<XReadResponse>() {
                                    @Override
                                    public void onResponse(Call<XReadResponse> call, Response<XReadResponse> response) {
                                        if (response.body().getStatus() == 0) {
                                            Utils.showDialogMessage(act, response.body().getMessage(), "Information");
                                        } else {
                                            dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<XReadResponse> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void passwordFailed() {

                            }
                        };

                        if (!passwordDialog.isShowing()) passwordDialog.show();

                    } else {

                        CheckSafeKeepingRequest checkSafeKeepingRequest = new CheckSafeKeepingRequest();
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<CheckSafeKeepingResponse> checkSafeKeepRequest = iUsers.checkSafeKeeping(checkSafeKeepingRequest.getMapValue());
                        checkSafeKeepRequest.enqueue(new Callback<CheckSafeKeepingResponse>() {
                            @Override
                            public void onResponse(Call<CheckSafeKeepingResponse> call, Response<CheckSafeKeepingResponse> response) {
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
                                                dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CollectionResponse> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    Utils.showDialogMessage(act, "Safekeep amount is greater than the sales", "Error");
                                }
                            }

                            @Override
                            public void onFailure(Call<CheckSafeKeepingResponse> call, Throwable t) {

                            }
                        });
                    }

                } else {
                    Utils.showDialogMessage(act, "Please put amount for safekeep", "Information");
                }

            }
        });
        requestDenomination();
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

                addView("CHECK", "CHECK", 9999);
            }

            @Override
            public void onFailure(Call<FetchDenominationResponse> call, Throwable t) {

            }
        });
    }

    private  void addView(String amountToDisplay, String actualAmount, int id) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        linearLayout.setLayoutParams(parentParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(1);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);

        TextView textView = new TextView(getContext());
        textView.setText(amountToDisplay);
        textView.setLayoutParams(params1);
        linearLayout.addView(textView);

        EditText editText = new EditText(getContext());
        editText.setHint(actualAmount);
        editText.setId(id);
        editText.setLayoutParams(params1);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        linearLayout.addView(editText);

        safeKeepDataModelList.add(new SafeKeepDataModel(editText, actualAmount));

        relContainer.addView(linearLayout);
    }


}
