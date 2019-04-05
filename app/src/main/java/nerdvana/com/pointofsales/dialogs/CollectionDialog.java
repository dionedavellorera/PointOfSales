package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_requests.CollectionRequest;
import nerdvana.com.pointofsales.api_requests.FetchDenominationRequest;
import nerdvana.com.pointofsales.api_responses.CollectionResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.model.SafeKeepDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionDialog extends BaseDialog {
    private String type;
    private LinearLayout relContainer;
    private LinearLayout linear;
    private Button save;

    private List<SafeKeepDataModel> safeKeepDataModelList;

    public CollectionDialog(@NonNull Context context, String type) {
        super(context);
        this.type = type;
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
                List<CollectionFinalPostModel> collectionFinalPostModels = new ArrayList<>();
                for (SafeKeepDataModel skdm : safeKeepDataModelList) {

                    if (!TextUtils.isEmpty(skdm.getEditText().getText().toString().trim()) && !skdm.getEditText().getText().toString().trim().equalsIgnoreCase("0")) {
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
                CollectionRequest collectionRequest = new CollectionRequest(collectionFinalPostModels);

                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<CollectionResponse> request = iUsers.collectionRequest(collectionRequest.getMapValue());

                request.enqueue(new Callback<CollectionResponse>() {
                    @Override
                    public void onResponse(Call<CollectionResponse> call, Response<CollectionResponse> response) {
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<CollectionResponse> call, Throwable t) {

                    }
                });
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
        linearLayout.addView(editText);

        safeKeepDataModelList.add(new SafeKeepDataModel(editText, actualAmount));

        relContainer.addView(linearLayout);
    }
}
