package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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
    private int specialCount;
    private boolean isEdit;
    public AddGuestDialog(@NonNull final Context context, String previousPersonCount,
                          String roomId, int specialCount,
                          boolean isEdit) {
        super(context);
        this.context = context;
        this.previousPersonCount = previousPersonCount;
        this.roomId = roomId;
        this.specialCount = specialCount;
        this.isEdit = isEdit;
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

        if (isEdit) {
            value.setText(String.valueOf(Integer.valueOf(previousPersonCount) - 2));
        } else {
            value.setText("1");
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {

                    if ((Integer.valueOf(value.getText().toString()) + 1) > ((Integer.valueOf(previousPersonCount)) - 2)) {
                        Utils.showDialogMessage(context, "Cannot add more guest, please go to add guest menu to add", "Information");
                    } else {
                        value.setText(String.valueOf(Integer.valueOf(value.getText().toString()) + 1));
                    }
                } else {
                    value.setText(String.valueOf(Integer.valueOf(value.getText().toString()) + 1));
                }

            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((Integer.valueOf(value.getText().toString()) - 1) < specialCount) {
                    Utils.showDialogMessage(context, "Cannot proceed, special discount will exceed the number of person, void discount first", "Information");
                } else {
                    if (isEdit) {
                        if ((Integer.valueOf(value.getText().toString()) - 1) < 0) {
                            Utils.showDialogMessage(context, "Cannot remove negative count of guest", "Information");
                        } else {
                            value.setText(String.valueOf(Integer.valueOf(value.getText().toString()) - 1));
                        }
                    } else {
                        if ((Integer.valueOf(value.getText().toString()) - 1) < 1) {
                            Utils.showDialogMessage(context, "Cannot add zero(0) guest", "Information");
                        } else {
                            value.setText(String.valueOf(Integer.valueOf(value.getText().toString()) - 1));
                        }
                    }
                }

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(context) {
                    @Override
                    public void save(final String remarks) {
                        PasswordDialog passwordDialog = new PasswordDialog(context, "99") {
                            @Override
                            public void passwordSuccess(String employeeId, String employeeName) {
                                int personCount = 0;
                                if (isEdit) {
                                    if (Integer.valueOf(value.getText().toString()) == 0) {
                                        personCount = 2;
                                    } else if(Integer.valueOf(value.getText().toString()) == (Integer.valueOf(previousPersonCount) - Integer.valueOf(value.getText().toString()))) {
                                        Utils.showDialogMessage(context, "No changes to guest number, cannot update", "Information");
                                    } else {
                                        personCount = Integer.valueOf(previousPersonCount) - Integer.valueOf(value.getText().toString());
                                    }

                                } else {
                                    personCount = Integer.valueOf(previousPersonCount) + Integer.valueOf(value.getText().toString());
                                }

                                EditGuestCountRequest editGuestCountRequest = new EditGuestCountRequest(
                                        previousPersonCount,
                                        String.valueOf(personCount),
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
        });
    }

    public abstract void editGuestSucess();

}
