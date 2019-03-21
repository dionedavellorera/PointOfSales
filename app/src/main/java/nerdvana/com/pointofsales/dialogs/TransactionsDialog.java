package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nerdvana.com.pointofsales.R;

public class TransactionsDialog extends Dialog {
    private Boolean isViewing;

    private RecyclerView listTransaction;
    private RecyclerView listTransactionDetails;
    private Button reprintOr;
    private Button postVoid;

    public TransactionsDialog(@NonNull Context context, Boolean isViewing) {
        super(context);
        this.isViewing = isViewing;
    }

    public TransactionsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TransactionsDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_transactions);
        listTransaction = findViewById(R.id.listTransactions);
        listTransactionDetails = findViewById(R.id.listTransactionDetails);

        reprintOr = findViewById(R.id.reprintOr);
        postVoid = findViewById(R.id.postVoid);

        if (isViewing) {
            reprintOr.setVisibility(View.VISIBLE);
            postVoid.setVisibility(View.GONE);
        } else {
            reprintOr.setVisibility(View.GONE);
            postVoid.setVisibility(View.VISIBLE);
        }

        reprintOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
