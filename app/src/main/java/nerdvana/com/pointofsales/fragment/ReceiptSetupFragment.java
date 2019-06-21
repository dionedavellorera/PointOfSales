package nerdvana.com.pointofsales.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;

public class ReceiptSetupFragment extends Fragment {
    private View view;
    private SeekBar seekbarProgress;
    private TextView seekbarValue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt_setup, container, false);
        seekbarProgress = view.findViewById(R.id.seekbarProgress);
        seekbarValue = view.findViewById(R.id.seekbarValue);


        seekbarValue.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT));
        if (TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT))) {
            SharedPreferenceManager.saveString(getContext(), "32", ApplicationConstants.MAX_COLUMN_COUNT);
        }
        seekbarProgress.setProgress(Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT)));
        seekbarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarValue.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferenceManager.saveString(getContext(), String.valueOf(seekBar.getProgress()), ApplicationConstants.MAX_COLUMN_COUNT);
            }
        });
        return view;
    }
}
