package nerdvana.com.pointofsales.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;

public class MachineSetupFragment extends Fragment {

    private View view;

    private CheckBox toSwitch;
    private CheckBox allowedToCheckInSwitch;
    public MachineSetupFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_machine_setup, container, false);

        toSwitch = view.findViewById(R.id.machineSwitchTo);
        allowedToCheckInSwitch = view.findViewById(R.id.allowedToCheckInSwitch);

//        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_SETUP).isEmpty()) {
//            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {
//                toSwitch.setChecked(true);
//            }
//        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                allowedToCheckInSwitch.setChecked(true);
            }
        }

//        toSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE MACHINE TYPE", "") {
//                    @Override
//                    public void passwordSuccess(String employeeId, String employeeName) {
//                        ApplicationConstants.IS_MACHINE_CHANGED = "T";
//                        SharedPreferenceManager.saveString(getContext(), toSwitch.isChecked() ? "y" : "n", ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN);
//                    }
//
//                    @Override
//                    public void passwordFailed() {
//                    }
//                };
//
//                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//
//                        allowedToCheckInSwitch.toggle();
//                    }
//                });
//
//                passwordDialog.show();
//            }
//        });
        allowedToCheckInSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(),"CONFIRM CHANGE MACHINE TYPE", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        SharedPreferenceManager.saveString(getContext(), allowedToCheckInSwitch.isChecked() ? "y" : "n", ApplicationConstants.MACHINE_SETUP);
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
