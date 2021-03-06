package nerdvana.com.pointofsales.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.custom.HidingEditText;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;
import nerdvana.com.pointofsales.dialogs.ToMachineSelectionDialog;

public class MachineSetupFragment extends Fragment {

    private View view;

    private CheckBox toSwitch;
    private CheckBox allowedToCheckInSwitch;

    private CheckBox allowedXReadSwitch;
    private CheckBox allowedZReadSwitch;

    private CheckBox allowedWakeUpCall;

    private ToMachineSelectionDialog toMachineSelectionDialog;

    private HidingEditText wakeUpCallTimeTrigger;
    private HidingEditText skTimeTrigger;

    private Button btnSaveSafeKeeping;
    private Button btnSaveWakeUpCall;
    public MachineSetupFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_machine_setup, container, false);
        skTimeTrigger = view.findViewById(R.id.skTimeTrigger);
        wakeUpCallTimeTrigger = view.findViewById(R.id.wakeUpCallTimeTrigger);
        toSwitch = view.findViewById(R.id.machineSwitchTo);
        allowedZReadSwitch = view.findViewById(R.id.allowedZRead);
        allowedXReadSwitch = view.findViewById(R.id.allowedXRead);
        allowedToCheckInSwitch = view.findViewById(R.id.allowedToCheckInSwitch);
        allowedWakeUpCall = view.findViewById(R.id.allowedWakeUpCall);

        btnSaveSafeKeeping = view.findViewById(R.id.btnSaveSafeKeeping);
        btnSaveSafeKeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(skTimeTrigger.getText().toString())) {
                    if (Double.valueOf(skTimeTrigger.getText().toString()) > 0) {
                        SharedPreferenceManager.saveString(getContext(), skTimeTrigger.getText().toString() ,ApplicationConstants.SK_TIME_TRIGGER);
                        Toast.makeText(getContext(), "Safekeeping time trigger saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Cannot save zero(0) safekeeping call time tigger", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Cannot save empty SK time tigger time", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSaveWakeUpCall = view.findViewById(R.id.btnSaveWakeUpCall);
        btnSaveWakeUpCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(wakeUpCallTimeTrigger.getText().toString())) {
                    if (Double.valueOf(wakeUpCallTimeTrigger.getText().toString()) > 0) {
                        SharedPreferenceManager.saveString(getContext(), wakeUpCallTimeTrigger.getText().toString() ,ApplicationConstants.WAKEUP_CALL_TIME_TRIGGER);
                        Toast.makeText(getContext(), "Wake up call time trigger saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Cannot save zero(0) Wake up call time tigger", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Cannot save empty Wake up call time tigger", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.SK_TIME_TRIGGER).isEmpty()) {
            skTimeTrigger.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SK_TIME_TRIGGER));

        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.WAKEUP_CALL_TIME_TRIGGER).isEmpty()) {
            wakeUpCallTimeTrigger.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.WAKEUP_CALL_TIME_TRIGGER));
        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_WAKE_UP_CALL).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_WAKE_UP_CALL).equalsIgnoreCase("y")) {
                allowedWakeUpCall.setChecked(true);
            }
        }


        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_TELEPHONE_OPERATOR).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_TELEPHONE_OPERATOR).equalsIgnoreCase("y")) {
                toSwitch.setChecked(true);
            }
        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                allowedToCheckInSwitch.setChecked(true);
            }
        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_XREADING).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_XREADING).equalsIgnoreCase("y")) {
                allowedXReadSwitch.setChecked(true);
            }
        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_ZREADING).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_ZREADING).equalsIgnoreCase("y")) {
                allowedZReadSwitch.setChecked(true);
            }
        }

        toSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE MACHINE TYPE", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        if (toMachineSelectionDialog == null) {
                            toMachineSelectionDialog = new ToMachineSelectionDialog(getContext());
                            toMachineSelectionDialog.setOnCancelListener(new OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    toMachineSelectionDialog = null;
//                                    SharedPreferenceManager.saveString(getContext(), SharedPreferenceManager.getString(getContext(), ApplicationConstants.POS_TO_ID), ApplicationConstants.POS_TO_ID);
//                                    SharedPreferenceManager.saveString(getContext(), SharedPreferenceManager.getString(getContext(), ApplicationConstants.POS_TO_ID), ApplicationConstants.MACHINE_ID);
                                    toSwitch.toggle();

                                    if (!toSwitch.isChecked()) {

                                        allowedToCheckInSwitch.setVisibility(View.VISIBLE);
                                        allowedXReadSwitch.setVisibility(View.VISIBLE);
                                        allowedZReadSwitch.setVisibility(View.VISIBLE);
                                    } else {
                                        allowedToCheckInSwitch.setVisibility(View.GONE);
                                        allowedXReadSwitch.setVisibility(View.GONE);
                                        allowedZReadSwitch.setVisibility(View.GONE);
                                        toMachineSelectionDialog.show();
                                    }


                                }
                            });
                            toMachineSelectionDialog.setOnDismissListener(new OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    toMachineSelectionDialog = null;
                                }
                            });
                        }
                        if (!toSwitch.isChecked()) {
//                            SharedPreferenceManager.saveString(getContext(), "", ApplicationConstants.POS_TO_ID);

                            SharedPreferenceManager.saveString(getContext(), SharedPreferenceManager.getString(getContext(), ApplicationConstants.POS_TO_ID), ApplicationConstants.POS_TO_ID);
                            SharedPreferenceManager.saveString(getContext(), SharedPreferenceManager.getString(getContext(), ApplicationConstants.POS_TO_ID), ApplicationConstants.MACHINE_ID);

                            allowedToCheckInSwitch.setVisibility(View.VISIBLE);
                            allowedXReadSwitch.setVisibility(View.VISIBLE);
                            allowedZReadSwitch.setVisibility(View.VISIBLE);
                        } else {
                            allowedToCheckInSwitch.setVisibility(View.GONE);
                            allowedXReadSwitch.setVisibility(View.GONE);
                            allowedZReadSwitch.setVisibility(View.GONE);
                            toMachineSelectionDialog.show();
                        }
                        ApplicationConstants.IS_MACHINE_CHANGED = "T";
                        SharedPreferenceManager.saveString(getContext(), toSwitch.isChecked() ? "y" : "n", ApplicationConstants.IS_TELEPHONE_OPERATOR);
                    }

                    @Override
                    public void passwordFailed() {
                    }
                };

                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        toSwitch.toggle();
                    }
                });

                passwordDialog.show();
            }
        });

        allowedWakeUpCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"ALLOW WAKE UP CALL REMINDER?", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        SharedPreferenceManager.saveString(getContext(), allowedWakeUpCall.isChecked() ? "y" : "n", ApplicationConstants.IS_ALLOWED_WAKE_UP_CALL);
                    }

                    @Override
                    public void passwordFailed() {
                    }
                };

                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        allowedWakeUpCall.toggle();
                    }
                });

                passwordDialog.show();
            }
        });


        allowedZReadSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE ZREAD CAPABILITY", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        SharedPreferenceManager.saveString(getContext(), allowedZReadSwitch.isChecked() ? "y" : "n", ApplicationConstants.IS_ALLOWED_FOR_ZREADING);
                    }

                    @Override
                    public void passwordFailed() {
                    }
                };

                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        allowedZReadSwitch.toggle();
                    }
                });

                passwordDialog.show();
            }
        });


        allowedXReadSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE XREAD CAPABILITY", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        SharedPreferenceManager.saveString(getContext(), allowedXReadSwitch.isChecked() ? "y" : "n", ApplicationConstants.IS_ALLOWED_FOR_XREADING);
                    }

                    @Override
                    public void passwordFailed() {
                    }
                };

                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        allowedXReadSwitch.toggle();
                    }
                });

                passwordDialog.show();
            }
        });


        allowedToCheckInSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE CHECKIN CAPABILITY", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        SharedPreferenceManager.saveString(getContext(), allowedToCheckInSwitch.isChecked() ? "y" : "n", ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN);
                    }

                    @Override
                    public void passwordFailed() {
                    }
                };

                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        allowedToCheckInSwitch.toggle();
                    }
                });

                passwordDialog.show();
            }
        });

        return view;
    }
}
