package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.AutoDiscountRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountRequest;
import nerdvana.com.pointofsales.api_responses.AutoDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.DiscountModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SelectionDiscountDialog extends BaseDialog {
    private SearchableSpinner spinnerDiscountType;

    private List<DiscountModel> discountModelList;

    private RelativeLayout formCard;
    private RelativeLayout formSpecial;
    private RelativeLayout formEmployee;

    private Spinner spinnerEmployee;

    private String discountId = "";
    private String discountType = "";
    private String discountedBy = "";
    private String selectionType = "";
    private String controlNumber = "";
    private String roomId = "";

    private EditText etCardNumber;
    private EditText etSeniorPwdName;
    private EditText etSeniorPwdAddress;
    private EditText etSeniorPwdCardNumber;

    private Context context;
    private Button submit;
    private String specialDiscountId;

    public SelectionDiscountDialog(@NonNull Context context, String controlNumber,
                                   String roomId, String specialDiscountId) {
        super(context);
        this.context = context;
        this.controlNumber = controlNumber;
        this.roomId = roomId;
        this.specialDiscountId = specialDiscountId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_selection_discount, "DISCOUNT SELECTION");
        spinnerDiscountType = findViewById(R.id.spinnerDiscountType);
        spinnerDiscountType.setTitle("Select Item");
        spinnerDiscountType.setPositiveButton("OK");

        discountModelList = new ArrayList<>();

        etCardNumber = findViewById(R.id.cardNumber);
        etSeniorPwdName = findViewById(R.id.seniorPwdName);
        etSeniorPwdAddress = findViewById(R.id.seniorPwdAddress);
        etSeniorPwdCardNumber = findViewById(R.id.seniorPwdCardNumber);

        formCard = findViewById(R.id.formCard);
        formSpecial = findViewById(R.id.formSpecial);
        formEmployee = findViewById(R.id.formEmployee);
        spinnerEmployee = findViewById(R.id.spinnerEmployee);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDiscount();
            }
        });

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
        final List<FetchCompanyUserResponse.Result> companyUser = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.COMPANY_USER), token.getType());
        ArrayList<String> stringArray = new ArrayList<>();

        for (FetchCompanyUserResponse.Result r :companyUser) {
            stringArray.add(r.getName() + "(" + String.valueOf(r.getUserId()) +")");
        }
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                stringArray);
        spinnerEmployee.setAdapter(customSpinnerAdapter);
        spinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                discountedBy = companyUser.get(position).getUsername();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                        if (!TextUtils.isEmpty(specialDiscountId)) {
                            if (specialDiscountId.equalsIgnoreCase(String.valueOf(r.getId()))) {
                                stringArray.add(r.getDiscountCard());
                                discountId = String.valueOf(r.getId());
                                discountType = r.getDiscountCard();
                                break;
                            }
                        } else {
                            if (r.getIsSpecial() != 1) {

                                discountModelList.add(new DiscountModel(String.valueOf(r.getId()), r.getDiscountCard()));
                                stringArray.add(r.getDiscountCard());
                            }
                        }
                    }
                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                            stringArray);
                    spinnerDiscountType.setAdapter(customSpinnerAdapter);

                    spinnerDiscountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (discountModelList.size() > 0) {
                                discountType = discountModelList.get(position).getName();
                                discountId = discountModelList.get(position).getPost_id();
                            }


                            if (response.body().getResult().get(position).getIsCard() == 1) {
                                selectionType = "card";
                                showForm("card");
                            } else if (response.body().getResult().get(position).getIsEmployee() == 1) {
                                selectionType = "employee";
                                showForm("employee");
                            } else if (response.body().getResult().get(position).getIsSpecial() == 1) {
                                selectionType = "special";
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

    private void sendDiscount() {

        PasswordDialog passwordDialog = new PasswordDialog(context, "64") {

            @Override
            public void passwordSuccess(String employeeId, String employeeName) {

                AutoDiscountRequest autoDiscountRequest = null;
                switch (selectionType) {
                    case "card":
                        discountedBy = "";
                        autoDiscountRequest =
                                new AutoDiscountRequest(
                                        discountType,
                                        discountId,
                                        discountedBy,
                                        employeeId,
                                        etCardNumber.getText().toString(),
                                        "",
                                        "",
                                        controlNumber,
                                        roomId);
                        break;
                    case "employee":
                        autoDiscountRequest =
                                new AutoDiscountRequest(
                                        discountType,
                                        discountId,
                                        discountedBy,
                                        employeeId,
                                        "",
                                        "",
                                        "",
                                        controlNumber,
                                        roomId);

                        Log.d("ERQREQREQ", autoDiscountRequest.toString());

                        break;
                    case "special":
                        autoDiscountRequest =
                                new AutoDiscountRequest(
                                        discountType,
                                        discountId,
                                        discountedBy,
                                        employeeId,
                                        etSeniorPwdCardNumber.getText().toString(),
                                        etSeniorPwdName.getText().toString(),
                                        etSeniorPwdAddress.getText().toString(),
                                        controlNumber,
                                        roomId);
                        break;
                }

                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<AutoDiscountResponse> request = iUsers.sendAutoDiscount(autoDiscountRequest.getMapValue());
                request.enqueue(new Callback<AutoDiscountResponse>() {
                    @Override
                    public void onResponse(Call<AutoDiscountResponse> call, Response<AutoDiscountResponse> response) {
                        if (response.body().getStatus() == 0) {
                            Utils.showDialogMessage(context, response.body().getMessage() ,"Information");
                        } else {
                            SelectionDiscountDialog.this.dismiss();
                            Utils.showDialogMessage(context, "Discounting success" ,"Information");
                            discountSuccess();
                        }
                    }

                    @Override
                    public void onFailure(Call<AutoDiscountResponse> call, Throwable t) {

                    }
                });


            }

            @Override
            public void passwordFailed() {

            }
        };

        if (!passwordDialog.isShowing()) passwordDialog.show();

    }


    public abstract void discountSuccess();

}
