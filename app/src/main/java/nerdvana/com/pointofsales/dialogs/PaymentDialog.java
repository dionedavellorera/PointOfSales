package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.entities.CartEntity;

public abstract class PaymentDialog extends Dialog implements View.OnClickListener {

    private String transactionNumber;

    TextInputLayout tilCash;
    EditText cash;
    TextInputLayout tilCreditCard;
    EditText creditCard;
    TextInputLayout tilCharge;
    EditText charge;
    TextInputLayout tilAr;
    EditText ar;

    TextView balanceValue;

    TextView totalChange;
    TextView totalPayment;
    Button checkout;

    double balance = 0;
    double payment = 0;

    double _cash = 0;
    double _credit = 0;
    double _charge = 0;
    double _ar = 0;

    public PaymentDialog(@NonNull Context context, String transactionNumber, double balance) {
        super(context);
        this.transactionNumber = transactionNumber;
        this.balance = balance;

    }

    public PaymentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PaymentDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_payment);
        tilCash = findViewById(R.id.tilCash);
        cash = findViewById(R.id.inputCash);
        tilCreditCard = findViewById(R.id.tilCreditCard);
        creditCard = findViewById(R.id.inputCreditCard);
        tilCharge = findViewById(R.id.tilCharge);
        charge = findViewById(R.id.inputCharge);
        tilAr = findViewById(R.id.tilAr);
        ar = findViewById(R.id.inputAr);
        totalChange = findViewById(R.id.totalChange);
        totalPayment = findViewById(R.id.totalPayment);
        balanceValue = findViewById(R.id.balanceValue);
        checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(this);
        addTextWatchers();

        balanceValue.setText(String.valueOf(balance));
    }

    private void addTextWatchers() {
        ar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ar.getText().toString().trim().equalsIgnoreCase("")) {
                    _ar = 0;
                    ar.setText("0");
                } else {
                    _ar = Double.valueOf(ar.getText().toString());
                }

                computeTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        charge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charge.getText().toString().trim().equalsIgnoreCase("")) {
                    _charge = 0;
                    charge.setText("0");
                } else {
                    _charge = Double.valueOf(charge.getText().toString());
                }

                computeTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        creditCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (creditCard.getText().toString().trim().equalsIgnoreCase("")) {
                    _credit = 0;
                    creditCard.setText("0");
                } else {
                    _credit = Double.valueOf(creditCard.getText().toString());
                }

                computeTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cash.getText().toString().trim().equalsIgnoreCase("")) {
                    _cash = 0;
                    cash.setText("0");
                } else {
                    _cash = Double.valueOf(cash.getText().toString());
                }

                computeTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void computeTotal() {
        payment = _ar + _cash + _charge + _credit;
        totalPayment.setText(String.valueOf(payment));
        totalChange.setText(String.valueOf(payment - balance));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkout:
                if (payment >= balance) {
                    Toast.makeText(getContext(), "Payment accepted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Payment not accepted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public abstract void paymentSuccess();
    public abstract void paymentFailed();
}