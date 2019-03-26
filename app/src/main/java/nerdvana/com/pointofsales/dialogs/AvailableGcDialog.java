package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.OrderSlipAdapter;
import nerdvana.com.pointofsales.api_responses.CheckGcResponse;
import nerdvana.com.pointofsales.model.AvailableGcModel;

public abstract class AvailableGcDialog extends BaseDialog {
    private Item item;
    private AvailableGcAdapter availableGcAdapter;
    private List<AvailableGcModel> list;
    private List<CheckGcResponse.Approve> approveList;
    private RecyclerView listAvailableGc;
    private Context context;
    private Button ok;
    public AvailableGcDialog(@NonNull Context context, List<CheckGcResponse.Approve> approveList) {
        super(context);
        this.approveList = approveList;
        this.context = context;
    }

    public AvailableGcDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AvailableGcDialog(@Nonnull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_available_gc);
        setDialogLayout(R.layout.dialog_available_gc, "AVAIALBLE GC/s");
        ok = (Button) findViewById(R.id.ok);
        listAvailableGc = (RecyclerView) findViewById(R.id.listAvailableGc);
        list = new ArrayList<>();

        for (CheckGcResponse.Approve apr : approveList) {
            list.add(new AvailableGcModel(String.valueOf(apr.getId()), String.valueOf(apr.getGcId()), apr.getSeriesNo(), String.valueOf(apr.getAmount())));
        }

        item = new Item() {
            @Override
            public void remove(int position) {
                list.remove(position);
                availableGcAdapter.notifyDataSetChanged();
            }
        };

        availableGcAdapter = new AvailableGcAdapter(list, item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listAvailableGc.setLayoutManager(linearLayoutManager);
        listAvailableGc.setAdapter(availableGcAdapter);
        availableGcAdapter.notifyDataSetChanged();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed(list);
                dismiss();
            }
        });

    }

    public abstract void proceed(List<AvailableGcModel> list);


    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(700, height);
        }
    }

    public interface Item {
        void remove(int position);
    }

}
