package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.ViewReceiptActualAdapter;
import nerdvana.com.pointofsales.api_responses.ViewReceiptViaDateResponse;
import nerdvana.com.pointofsales.model.ViewReceiptActualModel;

public class ViewReceiptActualDialog extends Dialog {

    private List<ViewReceiptViaDateResponse.Result> result;

    private RecyclerView rvReceiptList;

    public ViewReceiptActualDialog(@NonNull Context context, List<ViewReceiptViaDateResponse.Result> result) {
        super(context);
        this.result = result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_receipt_actual);

        rvReceiptList = findViewById(R.id.rvReceiptList);


        List<ViewReceiptActualModel> list = new ArrayList<>();

        ViewReceiptActualAdapter viewReceiptActualAdapter =
                new ViewReceiptActualAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReceiptList.setLayoutManager(linearLayoutManager);
        rvReceiptList.setAdapter(viewReceiptActualAdapter);

        Log.d("WATEKTEK", String.valueOf(result.size()));
        for (ViewReceiptViaDateResponse.Result r : result) {
            Log.d("WATEKTEK", r.getReceiptNo());
            list.add(new ViewReceiptActualModel(
                    "PANORAMA ENTERPRISE INC.",
                    "BAGONG ILOG PASIG CITY",
                    "671-9782",
                    "PC001LTR",
                    "000001010101",
                    "FDSFPDFS-FDSFDSFDS-FDS04234DF",
                    "15080516005415409",
                    r.getGuestInfo() == null ? "TAKEOUT" : r.getGuestInfo().getRoomNo().toString(),
                    r.getCashier().getName(),
                    r.getRoomBoy() == null ? "" : r.getRoomBoy().getName(),
                    r.getGuestInfo() == null ? "" : r.getGuestInfo().getCheckIn(),
                    r.getGuestInfo() == null ? "" : r.getGuestInfo().getCheckOut(),
                    r.getReceiptNo(),
                    String.valueOf(r.getPosId()),
                    String.valueOf(r.getVatExempt()),
                    String.valueOf(r.getDiscount()),
                    String.valueOf(r.getAdvance()),
                    String.valueOf(r.getTotal()),
                    String.valueOf(r.getTendered()),
                    String.valueOf(r.getChange()),
                    String.valueOf(r.getVatable()),
                    String.valueOf(r.getVatExemptSales()),
                    String.valueOf(r.getVat()),
                    String.valueOf(r.getPersonCount()),
                    String.valueOf(r.getTotalItem()),
                    "--",
                    r.getPost(),
                    String.valueOf(r.getOtHours()),
                    String.valueOf(r.getOtAmount())));




        }

        viewReceiptActualAdapter.notifyDataSetChanged();
    }


}
