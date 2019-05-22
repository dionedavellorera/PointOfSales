package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SetupActivity;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_requests.VerifyMachineRequest;
import nerdvana.com.pointofsales.api_responses.VerifyMachineResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupDialog extends BaseDialog {

    private Context context;

    EditText ipAddress;
    EditText branchName;
    EditText branchCode;
    EditText serial;
    Button proceed;


    public SetupDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_setup, "SETUP");

        ipAddress = findViewById(R.id.ipAddress);
        branchName = findViewById(R.id.branchName);
        branchCode = findViewById(R.id.branchCode);
        serial =findViewById(R.id.serialNumber);
        proceed =findViewById(R.id.proceed);


        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(context, ApplicationConstants.HOST))) {
            ipAddress.setText(SharedPreferenceManager.getString(context, ApplicationConstants.HOST));
            branchName.setText(SharedPreferenceManager.getString(context, ApplicationConstants.BRANCH));
            branchCode.setText(SharedPreferenceManager.getString(context, ApplicationConstants.CODE));
            serial.setText(SharedPreferenceManager.getString(context, ApplicationConstants.SERIAL_NUMBER));
        }

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(ipAddress.getText().toString().trim()) &&
                        !TextUtils.isEmpty(branchName.getText().toString().trim()) &&
                        !TextUtils.isEmpty(branchCode.getText().toString().trim()) &&
                        !TextUtils.isEmpty(serial.getText().toString().trim())) {

                    if (URLUtil.isValidUrl(String.format("%s/%s/%s/%s/",
                            ipAddress.getText().toString(),
                            "api",
                            branchName.getText().toString(),
                            branchCode.getText().toString()))) {

                        SharedPreferenceManager.saveString(context,
                                ipAddress.getText().toString(),ApplicationConstants.HOST);
                        SharedPreferenceManager.saveString(context,
                                branchName.getText().toString(),ApplicationConstants.BRANCH);
                        SharedPreferenceManager.saveString(context,
                                branchCode.getText().toString(),ApplicationConstants.CODE);
                        SharedPreferenceManager.saveString(context,
                                serial.getText().toString(),ApplicationConstants.SERIAL_NUMBER);

                        String apiBaseUrl = String.format("%s/%s/%s/%s/",
                                ipAddress.getText().toString(),
                                "api",
                                branchName.getText().toString(),
                                branchCode.getText().toString());
                        String apiImageUrl = String.format("%s/images/statuses/",
                                ipAddress.getText().toString());
                        SharedPreferenceManager.saveString(context, apiImageUrl, ApplicationConstants.API_IMAGE_URL);
                        SharedPreferenceManager.saveString(context, apiBaseUrl, ApplicationConstants.API_BASE_URL);
                        PosClient.changeApiBaseUrl(
                                apiBaseUrl
                        );
                        sendVerifyMachineRequest(serial.getText().toString().toUpperCase());
                    } else {
                        Toast.makeText(context, "Please enter valid url", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(context, "Please fill up all fields", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void sendVerifyMachineRequest(String productKey) {
        VerifyMachineRequest request = new VerifyMachineRequest(
                productKey,
                Build.ID,
                Build.SERIAL,
                Build.MODEL,
                Build.MANUFACTURER,
                Build.BOARD
        );
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<VerifyMachineResponse> verifyMachineRequest = iUsers.sendVerifyMachineRequest(
                request.getMapValue());

        verifyMachineRequest.enqueue(new Callback<VerifyMachineResponse>() {
            @Override
            public void onResponse(Call<VerifyMachineResponse> call, Response<VerifyMachineResponse> response) {
                if (response.body().getStatus() == 1) { //success
                    SharedPreferenceManager.saveString(context, "40", ApplicationConstants.MAX_COLUMN_COUNT);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getResult().get(0).getPrinter_path()), ApplicationConstants.SELECTED_PORT);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getResult().get(0).getId()), ApplicationConstants.MACHINE_ID);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getCompany().get(0).getCompany()), ApplicationConstants.BUSINESS_NAME);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getCompany().get(0).getOwner()), ApplicationConstants.TAXPAYERS_NAME);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getInfo().getTinNo()), ApplicationConstants.TIN_NUMBER);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getAddress()), ApplicationConstants.BRANCH_ADDRESS);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getInfo().getRemarks()), ApplicationConstants.OR_INFO_DISPLAY);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getInfo().getTax()), ApplicationConstants.TAX_RATE);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getId()), ApplicationConstants.BRANCH_ID);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getBranchCode()), ApplicationConstants.BRANCH_CODE);
                    SharedPreferenceManager.saveString(context, String.valueOf(response.body().getBranch().getInfo().getSafe_keeping_amount()), ApplicationConstants.SAFEKEEPING_AMOUNT);
                    SharedPreferenceManager.saveString(context, GsonHelper.getGson().toJson(String.valueOf(response.body().getBranch().getShift())), ApplicationConstants.SHIFT_DETAILS);
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<VerifyMachineResponse> call, Throwable t) {

            }
        });


//        BusProvider.getInstance().post(request);
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
}
