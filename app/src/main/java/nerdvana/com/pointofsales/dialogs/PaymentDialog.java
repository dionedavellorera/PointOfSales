package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.PaymentsAdapter;
import nerdvana.com.pointofsales.adapters.PostedPaymentsAdapter;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.entities.PaymentEntity;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;

public abstract class PaymentDialog extends Dialog  {

//    private String transactionNumber;
//
//    TextInputLayout tilCash;
//    EditText cash;
//    TextInputLayout tilCreditCard;
//    EditText creditCard;
//    TextInputLayout tilCharge;
//    EditText charge;
//    TextInputLayout tilAr;
//    EditText ar;
//
//    TextView balanceValue;
//
//    TextView totalChange;
//    TextView totalPayment;
//    Button checkout;
//
//    double balance = 0;
//    double payment = 0;
//
//    double _cash = 0;
//    double _credit = 0;
//    double _charge = 0;
//    double _ar = 0;
    private PaymentsAdapter paymentsAdapter;
    private List<FetchPaymentResponse.Result> paymentList;
    private RecyclerView listPayments;
    private RecyclerView listPostedPayments;
    private Button add;
    private Button pay;
    private EditText amountToPay;
    private TextView displayTotalChange;
    private FetchPaymentResponse.Result paymentMethod = null;
    private PaymentMethod paymentMethodImpl;
    List<PostedPaymentsModel> postedPaymentList = new ArrayList<>();
    PostedPaymentsAdapter postedPaymentsAdapter;
    private boolean isCheckout;
    private Double totalBalance;
    private TextView displayTotalBalance;
    private TextView displayTotalPayment;
    public PaymentDialog(@NonNull Context context, List<FetchPaymentResponse.Result> paymentList,
                         boolean isCheckout,
                         List<PostedPaymentsModel> postedPaymentList,
                         Double totalBalance) {
        super(context);
        this.paymentList = paymentList;
        this.isCheckout = isCheckout;
        this.postedPaymentList = postedPaymentList;
        this.totalBalance = totalBalance;
//        this.transactionNumber = transactionNumber;
//        this.balance = balance;
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
        displayTotalBalance = findViewById(R.id.totalBalance);
        displayTotalPayment = findViewById(R.id.totalPayment);
        listPayments = findViewById(R.id.listPayments);
        listPostedPayments = findViewById(R.id.listPostedPayments);
        add = findViewById(R.id.add);
        pay = findViewById(R.id.pay);
        displayTotalChange = findViewById(R.id.totalChange);
        amountToPay = (EditText) findViewById(R.id.amount);

        displayTotalBalance.setText(String.valueOf(totalBalance));
        paymentMethodImpl = new PaymentMethod() {
            @Override
            public void clicked(int position) {
                paymentMethod = paymentList.get(position);
            }
        };

        if (isCheckout) {
            pay.setText("CHECKOUT");
        } else {
            pay.setText("PAY");
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentMethod == null) {
                    Toast.makeText(getContext(), "Please select payment method", Toast.LENGTH_SHORT).show();
                } else {

                    postedPaymentList.add(new PostedPaymentsModel(paymentMethod.getCoreId(), amountToPay.getText().toString(), paymentMethod.getPaymentType(), false));
                    if (postedPaymentsAdapter != null) {
                        postedPaymentsAdapter.notifyDataSetChanged();
                    }
                    paymentMethod = null;
                }

                computeTotal();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentSuccess(postedPaymentList);
            }
        });

        //computeTotal()
        computeTotal();


        paymentsAdapter = new PaymentsAdapter(paymentList, paymentMethodImpl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listPayments.setLayoutManager(linearLayoutManager);
        listPayments.setAdapter(paymentsAdapter);
        paymentsAdapter.notifyDataSetChanged();


        postedPaymentsAdapter = new PostedPaymentsAdapter(postedPaymentList);
        listPostedPayments.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listPostedPayments.setAdapter(postedPaymentsAdapter);
    }

    public abstract void paymentSuccess(List<PostedPaymentsModel> postedPaymentList);
    public abstract void paymentFailed();

    public interface PaymentMethod {
        void clicked(int position);
    }

    private void computeTotal() {
        Double totalPayment = 0.00;
        Double totalChange = 0.00;
        for (PostedPaymentsModel ppm : postedPaymentList) {
            totalPayment += Double.valueOf(ppm.getAmount());

        }

        totalChange = totalPayment - totalBalance;
        displayTotalChange.setText(String.valueOf(totalChange));
        displayTotalPayment.setText(String.valueOf(totalPayment));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
