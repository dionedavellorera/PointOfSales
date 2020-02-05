package nerdvana.com.pointofsales.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_requests.ViewReceiptViaDateRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptViaDateResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewReceiptDialog extends BaseDialog {

    private Button btnSearch;

    private TextView startDate;
    private TextView endDate;

    private CheckBox chkTakeOut;

    private Spinner spinrRoomNumber;

    boolean isRoom = true;
    public ViewReceiptDialog(@NonNull Context context) {
        super(context);
    }

    private String roomId;

    private EditText inputReceiptNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_view_receipt, "View Receipt");
        btnSearch = findViewById(R.id.btnSearch);
        inputReceiptNumber = findViewById(R.id.inputReceiptNumber);
        chkTakeOut = findViewById(R.id.chkTakeOut);
        spinrRoomNumber = findViewById(R.id.spinrRoomNumber);

        chkTakeOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isRoom = false;
                    spinrRoomNumber.setVisibility(View.GONE);
                } else {
                    isRoom = true;
                    spinrRoomNumber.setVisibility(View.VISIBLE);
                }
            }
        });

        startDate = findViewById(R.id.inputDateStart);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog("from");

            }
        });
        endDate = findViewById(R.id.inputDateEnd);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("to");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SEARCHME", "Y");
                ViewReceiptViaDateRequest viewReceiptViaDateRequest =
                        new ViewReceiptViaDateRequest(
                        isRoom ? "" : "",
                                inputReceiptNumber.getText().toString(),
                        startDate.getText().toString(), endDate.getText().toString());
                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<ViewReceiptViaDateResponse> request = iUsers.viewReceiptViaDate(
                        viewReceiptViaDateRequest.getMapValue());
                request.enqueue(new Callback<ViewReceiptViaDateResponse>() {
                    @Override
                    public void onResponse(Call<ViewReceiptViaDateResponse> call, Response<ViewReceiptViaDateResponse> response) {

                        if (response.body().getResult().size() > 0) {
                            ViewReceiptActualDialog viewReceiptActualDialog =
                                    new ViewReceiptActualDialog(getContext(),
                                            response.body().getResult());
                            viewReceiptActualDialog.show();
                        } else {
                            Utils.showDialogMessage(getContext(), "No data", "Information");
                        }


                    }

                    @Override
                    public void onFailure(Call<ViewReceiptViaDateResponse> call, Throwable t) {

                    }
                });

            }
        });

        setRoomSpinner();

        startDate.setText(Utils.getCurrentDate());
        endDate.setText(Utils.getCurrentDate());
    }

    private void showDatePickerDialog(final String type) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        switch (type) {
                            case "from":
                                startDate.setText(String.format("%s-%s-%s", String.valueOf(year),
                                        monthOfYear < 9 ? "0" + String.valueOf(monthOfYear + 1) : String.valueOf(monthOfYear + 1),
                                        dayOfMonth < 10 ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth)));
                                break;
                            case "to":
                                endDate.setText(String.format("%s-%s-%s", String.valueOf(year),
                                        monthOfYear < 9 ? "0" + String.valueOf(monthOfYear + 1) : String.valueOf(monthOfYear + 1),
                                        dayOfMonth < 10 ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth)));
                                break;
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void setRoomSpinner() {
        FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        final List<String> roomNumbers = new ArrayList<>();
        Call<FetchRoomResponse> roomlistRequest = iUsers.sendRoomListRequest(
                fetchRoomRequest.getMapValue());
        roomlistRequest.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, final Response<FetchRoomResponse> response) {
                for (FetchRoomResponse.Result frr : response.body().getResult()) {
                    roomNumbers.add(frr.getRoomNo());
                }

                CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                        roomNumbers);
                spinrRoomNumber.setAdapter(rateSpinnerAdapter);

                spinrRoomNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        roomId = String.valueOf(response.body().getResult().get(position).getId());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });

    }

}
