package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public abstract class FocDialog extends BaseDialog {
    private List<PostedPaymentsModel> postedPaymentsModelList;
    private EditText customerName;
    private TextView totalValue;
    private Double total = 0.00;
    private Button submit;
    public FocDialog(@NonNull Context context, List<PostedPaymentsModel> postedPaymentsModelList) {
        super(context);
        this.postedPaymentsModelList = postedPaymentsModelList;
    }

    public FocDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected FocDialog(@NonNull Context context, boolean cancelable,
                        @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_foc);
        setDialogLayout(R.layout.dialog_foc, "FREE OF CHARGE TRANSACTION");
        customerName = findViewById(R.id.customerName);
        totalValue = findViewById(R.id.totalValue);
        submit = findViewById(R.id.submit);

        for (PostedPaymentsModel ppm : postedPaymentsModelList) {
            total += Double.valueOf(ppm.getAmount());
        }

        totalValue.setText(String.valueOf(total));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focSuccess();
            }
        });
    }

    public abstract void focSuccess();


}
