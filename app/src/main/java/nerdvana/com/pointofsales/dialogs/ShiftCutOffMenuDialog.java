package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.ButtonsModel;

public class ShiftCutOffMenuDialog extends BaseDialog implements View.OnClickListener {
    private Button xRead;
    private Button zRead;
    private Button reprintXRead;
    private Button reprintZRead;

    private Button reprintXReadV2;
    private Button reprintZReadV2;
    public ShiftCutOffMenuDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_shift_cut_off_menu, "SHIFT CUTOFF");
        xRead = findViewById(R.id.btnXread);
        xRead.setOnClickListener(this);
        zRead = findViewById(R.id.btnZread);
        zRead.setOnClickListener(this);
        reprintXRead = findViewById(R.id.btnReprintXRead);
        reprintXRead.setOnClickListener(this);
        reprintZRead = findViewById(R.id.btnReprintZread);
        reprintZRead.setOnClickListener(this);

        reprintXReadV2 = findViewById(R.id.btnReprintXReadV2);
        reprintXReadV2.setOnClickListener(this);

        reprintZReadV2 = findViewById(R.id.btnReprintZReadV2);
        reprintZReadV2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnXread:
                BusProvider.getInstance().post(new ButtonsModel(121,"XREAD", "",19));
                break;
            case R.id.btnZread:
                BusProvider.getInstance().post(new ButtonsModel(120,"ZREAD", "",21));
                break;
            case R.id.btnReprintXRead:
                BusProvider.getInstance().post(new ButtonsModel(123,"REPRINT X READING", "",22));
                break;
            case R.id.btnReprintZread:
                BusProvider.getInstance().post(new ButtonsModel(127,"REPRINT Z READING", "",22));
                break;
            case R.id.btnReprintXReadV2:
                BusProvider.getInstance().post(new ButtonsModel(500,"REPRINT X READING V2", "",22));
                break;
            case R.id.btnReprintZReadV2:
                BusProvider.getInstance().post(new ButtonsModel(501,"REPRINT Z READING V2", "",22));
                break;
        }
    }
}
