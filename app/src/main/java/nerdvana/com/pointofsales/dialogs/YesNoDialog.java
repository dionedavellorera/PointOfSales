package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import nerdvana.com.pointofsales.R;

public class YesNoDialog extends BaseDialog {

    private String header;
    private String body;

    private TextView bodyText;

    public YesNoDialog(@NonNull Context context, String header, String body) {
        super(context);
        this.header = header;
        this.body = body;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_yes_no, header);
        bodyText = findViewById(R.id.body);

        bodyText.setText(body);
    }
}
