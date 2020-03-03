package nerdvana.com.pointofsales.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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

    private SeekBar seekbarProgressRb;
    private TextView seekbarValueRb;

    private SeekBar seekbarProgressKitchen;
    private TextView seekbarValueKitchen;
    public ReceiptSetupFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt_setup, container, false);
        seekbarProgress = view.findViewById(R.id.seekbarProgress);
        seekbarValue = view.findViewById(R.id.seekbarValue);

        seekbarProgressRb = view.findViewById(R.id.seekbarProgressRb);
        seekbarValueRb = view.findViewById(R.id.seekbarValueRb);

        seekbarProgressKitchen = view.findViewById(R.id.seekbarProgressKitchen);
        seekbarValueKitchen = view.findViewById(R.id.seekbarValueKitchen);


        seekbarValue.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT));
        seekbarValueRb.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_RB));
        seekbarValueKitchen.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN));
        if (TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT))) {
            SharedPreferenceManager.saveString(getContext(), "32", ApplicationConstants.MAX_COLUMN_COUNT);
        }

        if (TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN))) {
            SharedPreferenceManager.saveString(getContext(), "32", ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN);
        }

        if (TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_RB))) {
            SharedPreferenceManager.saveString(getContext(), "32", ApplicationConstants.MAX_COLUMN_COUNT_RB);
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

        Log.d("WEKWEK", String.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_RB)));
        Log.d("WEKWEK", String.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN)));
        seekbarProgressRb.setProgress(Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_RB)));
        seekbarProgressRb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarValueRb.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferenceManager.saveString(getContext(), String.valueOf(seekBar.getProgress()), ApplicationConstants.MAX_COLUMN_COUNT_RB);
            }
        });

        seekbarProgressKitchen.setProgress(Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN)));
        seekbarProgressKitchen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarValueKitchen.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SharedPreferenceManager.saveString(getContext(), String.valueOf(seekBar.getProgress()), ApplicationConstants.MAX_COLUMN_COUNT_KITCHEN);
            }
        });
        return view;
    }
}
