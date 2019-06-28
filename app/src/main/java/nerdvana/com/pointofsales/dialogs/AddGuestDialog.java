package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.EditGuestCountRequest;
import nerdvana.com.pointofsales.api_responses.EditGuestCountResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AddGuestDialog extends BaseDialog {

    private String previousPersonCount;
    private TextView add;
    private TextView subtract;
    private TextView value;
    private Button confirm;
    private Context context;
    private String roomId;
    public AddGuestDialog(@NonNull final Context context, String previousPersonCount, String roomId) {
        super(context);
        this.context = context;
        this.previousPersonCount = previousPersonCount;
        this.roomId = roomId;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_add_guest,"EDIT GUEST");

        setCancelable(false);

        add = findViewById(R.id.add);
        subtract = findViewById(R.id.subtract);
        value = findViewById(R.id.value);
        confirm = findViewById(R.id.confirm);

        value.setText(previousPersonCount);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.setText(String.valueOf(Integer.valueOf(value.getText().toString()) + 1));
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(value.getText().toString()) - 1 == 1) {
                    Utils.showDialogMessage(context, "Guest count cannot be less than two(2)", "Information");
                } else {
                    value.setText(String.valueOf(Integer.valueOf(value.getText().toString()) - 1));
                }
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(value.getText().toString()) < 2) {
                    Utils.showDialogMessage(context, "Guest count cannot be less than two(2)", "Information");
                } else {

                    ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(context) {
                        @Override
                        public void save(final String remarks) {
                            PasswordDialog passwordDialog = new PasswordDialog(context, "99") {
                                @Override
                                public void passwordSuccess(String employeeId, String employeeName) {
                                    EditGuestCountRequest editGuestCountRequest = new EditGuestCountRequest(
                                            previousPersonCount,
                                            value.getText().toString(),
                                            roomId,
                                            employeeId,
                                            remarks);

                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    Call<EditGuestCountResponse> request = iUsers.editGuestCount(editGuestCountRequest.getMapValue());
                                    request.enqueue(new Callback<EditGuestCountResponse>() {
                                        @Override
                                        public void onResponse(Call<EditGuestCountResponse> call, Response<EditGuestCountResponse> response) {
                                            if (response.body().getStatus() == 1) {
                                                dismiss();
                                                editGuestSucess();
                                            } else {
                                                Utils.showDialogMessage(context, response.body().getMessage(), "Information");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<EditGuestCountResponse> call, Throwable t) {

                                        }
                                    });
                                }

                                @Override
                                public void passwordFailed() {

                                }
                            };

                            passwordDialog.show();
                        }
                    };

                    confirmWithRemarksDialog.show();



                }
            }
        });
    }

    public abstract void editGuestSucess();

}
