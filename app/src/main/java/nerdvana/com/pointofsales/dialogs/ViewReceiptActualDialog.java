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
import nerdvana.com.pointofsales.adapters.ViewReceiptActualAdapter;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;

public class ViewReceiptActualDialog extends Dialog {

    private RecyclerView rvReceiptList;
    public ViewReceiptActualDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_receipt_actual);

        rvReceiptList = findViewById(R.id.rvReceiptList);


        List<ViewReceiptActualModel> list = new ArrayList<>();

        list.add(new ViewReceiptActualModel("PANORAMA ENTERPRISE INC.", "BAGONG ILOG PASIG CITY",
                "671-9782", "PC001LTR",
                "000001010101", "FDSFPDFS-FDSFDSFDS-FDS04234DF",
                "", "",
                "", "",
                "", "",
                "", ""));

        ViewReceiptActualAdapter viewReceiptActualAdapter = new ViewReceiptActualAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReceiptList.setLayoutManager(linearLayoutManager);
        rvReceiptList.setAdapter(viewReceiptActualAdapter);
    }


}
