package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;

public abstract class TakeOutCreateCustomerDialog extends BaseDialog {
    private EditText name;
    private Button create;
    private SearchableSpinner roomAreaSpinner;
    private List<FetchRoomAreaResponse.Result> roomAreaList;
    private Context context;
    private CheckBox isEmployeeChkBox;
    private Spinner employeeSpinner;
    private String userId = "";
    private int areaId = 0;
    public TakeOutCreateCustomerDialog(@NonNull Context context, List<FetchRoomAreaResponse.Result> roomAreaList) {
        super(context);
        this.context = context;
        this.roomAreaList = roomAreaList;

    }

    public TakeOutCreateCustomerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TakeOutCreateCustomerDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_take_out_create_customer);
        setDialogLayout(R.layout.dialog_take_out_create_customer, "CUSTOMER INFO");
        roomAreaSpinner = findViewById(R.id.roomArea);
        isEmployeeChkBox = findViewById(R.id.isEmployeeChkBox);
        employeeSpinner = findViewById(R.id.employeeSpinner);
        name = findViewById(R.id.name);
        create = findViewById(R.id.create);


        setEmployeeSpinner();


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmployeeChkBox.isChecked()) {
                    if (TextUtils.isEmpty(userId)) {
                        Toast.makeText(getContext(), "user id cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "request create", Toast.LENGTH_SHORT).show();
                        createSuccess(employeeSpinner.getSelectedItem().toString(), areaId, userId);
                    }
                } else {
                    createSuccess(name.getText().toString(), areaId, "");
                }

            }
        });

        setRoomArea();


        isEmployeeChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleView(isChecked);

            }
        });

        toggleView(true);
    }

    private void toggleView(boolean isEmployee) {
        if (isEmployee) {
            name.setVisibility(View.GONE);
            employeeSpinner.setVisibility(View.VISIBLE);
        } else {
            name.setVisibility(View.VISIBLE);
            employeeSpinner.setVisibility(View.GONE);
        }
    }

    public abstract void createSuccess(String customerName, int areaId, String userId);

    private void setRoomArea() {
        List<String> areaArray = new ArrayList<>();
        for (FetchRoomAreaResponse.Result area : roomAreaList) {
            areaArray.add(area.getRoomArea());
        }
        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                areaArray);
        roomAreaSpinner.setAdapter(rateSpinnerAdapter);

        roomAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaId = roomAreaList.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                userId = companyUser.get(position).getUsername();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = 600;
            int height = 300;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
