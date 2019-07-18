package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.BundleSummaryAdapter;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;

public abstract class BundleSummaryDialog extends BaseDialog {

    private Button btnConfirm;

    private RecyclerView listSummary;

    private List<SelectedProductsInBundleModel> selectedProductsInBundleModel;
    public BundleSummaryDialog(@NonNull Context context, List<SelectedProductsInBundleModel> selectedProductsInBundleModels) {
        super(context);
        this.selectedProductsInBundleModel = selectedProductsInBundleModels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_bundle_summary, "BUNDLE SUMMARY");

        btnConfirm = findViewById(R.id.confirm);

        listSummary = findViewById(R.id.listSummary);


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        BundleSummaryAdapter bundleSummaryAdapter = new BundleSummaryAdapter(selectedProductsInBundleModel, getContext());
        listSummary.setLayoutManager(llm);
        listSummary.setAdapter(bundleSummaryAdapter);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }

    public abstract void confirm();
}
