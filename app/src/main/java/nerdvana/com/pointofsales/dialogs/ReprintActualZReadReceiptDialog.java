package nerdvana.com.pointofsales.dialogs;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_requests.FetchZReadListRequest;
import nerdvana.com.pointofsales.api_requests.FetchZReadListRequestViaDate;
import nerdvana.com.pointofsales.api_responses.FetchZReadListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReprintActualZReadReceiptDialog extends BaseDialog {
    public ReprintActualZReadReceiptDialog(@NonNull Context context) {
        super(context);
    }

    private TextView startDate;
    private TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_reprint_z_read, "REPRINT Z READ");

        startDate = findViewById(R.id.startDate);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("from");
            }
        });
        endDate = findViewById(R.id.endDate);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog("to");
            }
        });
        Button btnSearch = findViewById(R.id.search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ZXActualDialog zxActualDialog = new ZXActualDialog(getContext(), "z", startDate.getText().toString(), endDate.getText().toString());
                zxActualDialog.show();



            }
        });
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
}
