package nerdvana.com.pointofsales.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;

public class MachineSetupFragment extends Fragment {

    private View view;

    private Switch toSwitch;
    private Switch allowedToCheckInSwitch;
    public MachineSetupFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_machine_setup, container, false);

        toSwitch = view.findViewById(R.id.machineSwitchTo);
        allowedToCheckInSwitch = view.findViewById(R.id.allowedToCheckInSwitch);

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_SETUP).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {
                toSwitch.setChecked(true);
            }
        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                allowedToCheckInSwitch.setChecked(true);
            }
        }

        allowedToCheckInSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE MACHINE TYPE", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {

                        SharedPreferenceManager.saveString(getContext(), toSwitch.isChecked() ? "n" : "y", ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN);

                    }

                    @Override
                    public void passwordFailed() {
                        allowedToCheckInSwitch.toggle();
                    }
                };


                passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        allowedToCheckInSwitch.toggle();
                    }
                });
                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        allowedToCheckInSwitch.toggle();
                    }
                });
                passwordDialog.show();
            }
        });
        toSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE MACHINE TYPE", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        ApplicationConstants.IS_MACHINE_CHANGED = "T";
                        SharedPreferenceManager.saveString(getContext(), toSwitch.isChecked() ? "to" : "cashier", ApplicationConstants.MACHINE_SETUP);
                    }

                    @Override
                    public void passwordFailed() {
                        toSwitch.toggle();
                    }
                };


                passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        toSwitch.toggle();
                    }
                });
                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        toSwitch.toggle();
                    }
                });
                passwordDialog.show();
            }
        });
//        toSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

//
//            }
//        });
        return view;
    }
}
