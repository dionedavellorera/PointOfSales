package nerdvana.com.pointofsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import nerdvana.com.pointofsales.payment.LeftFrameFragment;

public class PaymentActivity extends AppCompatActivity {

    private String transactionNumber = "";

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            transactionNumber = bundle.getString("transaction_number");
        }

        if (TextUtils.isEmpty(transactionNumber)) {
            Toast.makeText(PaymentActivity.this, "Transaction number missing", Toast.LENGTH_SHORT).show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        openFragment(R.id.leftPaymentFrame, new LeftFrameFragment());
//        openFragment(R.id.rightPaymentFrame, new LeftFrameFragment());


    }

    private void successIntent() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void failedIntent() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        failedIntent();
    }

    private void openFragment(int container, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

}
