package nerdvana.com.pointofsales.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;

public class MachineSetupFragment extends Fragment {

    private View view;

    private Switch toSwitch;

    public MachineSetupFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_machine_setup, container, false);

        toSwitch = view.findViewById(R.id.machineSwitchTo);

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_SETUP).isEmpty()) {
            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_SETUP).equalsIgnoreCase("to")) {
                toSwitch.setChecked(true);
            }
        }
        toSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity(), "60") {
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
