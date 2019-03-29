package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.gson.reflect.TypeToken;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.FetchDiscountRequest;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectionDiscountDialog extends BaseDialog {
    private SearchableSpinner spinnerDiscountType;

    private RelativeLayout formCard;
    private RelativeLayout formSpecial;
    private RelativeLayout formEmployee;

    private Spinner spinnerEmployee;

    private String discountId = "";

    public SelectionDiscountDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_selection_discount, "DISCOUNT SELECTION");
        spinnerDiscountType = findViewById(R.id.spinnerDiscountType);
        spinnerDiscountType.setTitle("Select Item");
        spinnerDiscountType.setPositiveButton("OK");

        formCard = findViewById(R.id.formCard);
        formSpecial = findViewById(R.id.formSpecial);
        formEmployee = findViewById(R.id.formEmployee);
        spinnerEmployee = findViewById(R.id.spinnerEmployee);

        requestDiscountSelection();
        populateCompanyUserList();

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

    private void populateCompanyUserList() {
        TypeToken<List<FetchCompanyUserResponse.Result>> token = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        List<FetchCompanyUserResponse.Result> companyUser = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.COMPANY_USER), token.getType());
        ArrayList<String> stringArray = new ArrayList<>();

        for (FetchCompanyUserResponse.Result r :companyUser) {
            stringArray.add(r.getName() + "(" + String.valueOf(r.getUserId()) +")");
        }
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                stringArray);
        spinnerEmployee.setAdapter(customSpinnerAdapter);

//        Log.d("COMPANYUSER", String.valueOf(companyUser.size()));
    }

    private void requestDiscountSelection() {
        FetchDiscountRequest fetchDiscountRequest = new FetchDiscountRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDiscountResponse> request = iUsers.fetchDiscount(fetchDiscountRequest.getMapValue());
        request.enqueue(new Callback<FetchDiscountResponse>() {
            @Override
            public void onResponse(Call<FetchDiscountResponse> call, final Response<FetchDiscountResponse> response) {
                if (response.body().getResult().size() > 0) {
                    ArrayList<String> stringArray = new ArrayList<>();
                    for (FetchDiscountResponse.Result r :response.body().getResult()) {
                        stringArray.add(r.getDiscountCard());
                    }
                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                            stringArray);
                    spinnerDiscountType.setAdapter(customSpinnerAdapter);

                    spinnerDiscountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            discountId = String.valueOf(response.body().getResult().get(position).getId());
                            if (response.body().getResult().get(position).getIsCard() == 1) {
                                showForm("card");
                            } else if (response.body().getResult().get(position).getIsEmployee() == 1) {
                                showForm("employee");
                            } else if (response.body().getResult().get(position).getIsSpecial() == 1) {
                                showForm("special");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<FetchDiscountResponse> call, Throwable t) {

            }
        });
    }

    private void showForm(String form) {
        switch (form) {
            case "card":
                formCard.setVisibility(View.VISIBLE);
                formSpecial.setVisibility(View.GONE);
                formEmployee.setVisibility(View.GONE);
                break;
            case "employee":
                formCard.setVisibility(View.GONE);
                formSpecial.setVisibility(View.GONE);
                formEmployee.setVisibility(View.VISIBLE);
                break;
            case "special":
                formCard.setVisibility(View.GONE);
                formSpecial.setVisibility(View.VISIBLE);
                formEmployee.setVisibility(View.GONE);
                break;
        }
    }

}
