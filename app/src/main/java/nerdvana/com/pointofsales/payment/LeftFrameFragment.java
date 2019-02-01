package nerdvana.com.pointofsales.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import nerdvana.com.pointofsales.R;

public class LeftFrameFragment extends Fragment {
    private View view;

    Spinner paymentMethodSpinner;
    LinearLayout formCash;
    LinearLayout formCreditCard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.payment_left_frame, container, false);

        paymentMethodSpinner = view.findViewById(R.id.paymentMethodSpinner);
        formCash = view.findViewById(R.id.formCash);
        formCreditCard = view.findViewById(R.id.formCreditCard);
        paymentMethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (paymentMethodSpinner.getItemAtPosition(position).toString().equalsIgnoreCase("cash")) {
                    formCash.setVisibility(View.VISIBLE);
                    formCreditCard.setVisibility(View.GONE);
                } else if (paymentMethodSpinner.getItemAtPosition(position).toString().equalsIgnoreCase("credit card")) {
                    formCash.setVisibility(View.GONE);
                    formCreditCard.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}
