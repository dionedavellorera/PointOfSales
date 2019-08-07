package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.ZXReceiptAdapter;

public class ZXActualDialog extends Dialog {
    private String title;
    private String from;
    private RecyclerView rvList;
    public ZXActualDialog(@NonNull Context context, String from) {
        super(context);
        this.from = from;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zx_actual_receipt);
        rvList = findViewById(R.id.rvList);



        if (from.equalsIgnoreCase("x")) {
            title = "X READ";
        } else { //z
            title = "Z READ";
        }

        List<ZXReadModel> zxReadModelList = new ArrayList<>();

        ZXReadModel zxReadModel = new ZXReadModel("NERDVANA CORD",
                "BAGONG ILOG PASIG CITY",
                "671 97 82",
                "SERIAL NO: *******",
                "VAT REG TIN NO:009-772-500-000",
                "PERMIT NO:*****-*****-****-**",
                "MIN NO: ************",
                title,
                "2019-08-02",
                from.equalsIgnoreCase("x") ? "SHIFT 3" : "",
                "DIONE DAVE LLORERA",
                "ANGELO DEVIL DEL MUNDO",
                "2",
                "39999",
                "12321",
                "1232131",
                "12432",
                "4324",
                "54353",
                "",
                "43543",
                "4534",
                "543543",
                "54353",
                "543543",
                "543534",
                "12313",
                "432423",
                "543534",
                "45543",
                "543543543",
                "543543",
                "432423",
                "453543",
                "543543543",
                "543543543");

        zxReadModelList.add(zxReadModel);

        ZXReceiptAdapter zxReceiptAdapter = new ZXReceiptAdapter(zxReadModelList, getContext(), from);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(zxReceiptAdapter);
        zxReceiptAdapter.notifyDataSetChanged();

    }
}
