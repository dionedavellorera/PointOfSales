package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.SaveGuestInfoRequest;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.SaveGuestInfoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class EmployeeSelectionDialog extends BaseDialog {

    private Spinner employeeSpinner;

    private Button saveEmployee;

    private String userId;
    private String wholeName;
    private String controlNumber;
    public EmployeeSelectionDialog(@NonNull Context context, String controlNumber) {
        super(context);
        this.controlNumber = controlNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_employee_selection, "SELECT EMPLOYEE");

        employeeSpinner = findViewById(R.id.employeeSpinner);
        saveEmployee = findViewById(R.id.saveEmployee);

        saveEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userId)) {
                    SaveGuestInfoRequest saveGuestInfoRequest = new SaveGuestInfoRequest(userId, wholeName,
                            "", "",
                            controlNumber, "");
                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                    Call<SaveGuestInfoResponse> request = iUsers.saveGuestInfo(saveGuestInfoRequest.getMapValue());

                    request.enqueue(new Callback<SaveGuestInfoResponse>() {
                        @Override
                        public void onResponse(Call<SaveGuestInfoResponse> call, Response<SaveGuestInfoResponse> response) {
                            dismiss();
                            saveEmployeeToTransaction(userId, wholeName);
                        }

                        @Override
                        public void onFailure(Call<SaveGuestInfoResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "No employee selected", Toast.LENGTH_LONG).show();
                }
            }
        });
        setEmployeeSpinner();
    }

    private void setEmployeeSpinner() {
        TypeToken<List<FetchCompanyUserResponse.Result>> token = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        final List<FetchCompanyUserResponse.Result> companyUser = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.COMPANY_USER), token.getType());
        ArrayList<String> stringArray = new ArrayList<>();

        for (FetchCompanyUserResponse.Result r :companyUser) {
            stringArray.add(r.getName() + "(" + String.valueOf(r.getUserId()) +")");
        }
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                stringArray);
        employeeSpinner.setAdapter(customSpinnerAdapter);
        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userId = String.valueOf(companyUser.get(position).getUserId());
                wholeName = companyUser.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public abstract void saveEmployeeToTransaction(String username, String name);
}
