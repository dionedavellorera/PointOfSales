package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.InstransitFilterAdapter;
import nerdvana.com.pointofsales.interfaces.IntransitFilterContract;
import nerdvana.com.pointofsales.model.IntransitFilterModel;

public abstract class IntransitFilterDialog extends BaseDialog implements IntransitFilterContract {
    private RecyclerView rvIntransitFilter;
    private List<IntransitFilterModel> filterList;
    public IntransitFilterDialog(@NonNull Context context, List<IntransitFilterModel> filterList) {
        super(context);
        this.filterList = filterList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_intransit_filter, "FILTER");
        initViews();
        setIntransitFilterAdapter();
    }

    private void initViews() {
        rvIntransitFilter = findViewById(R.id.rvIntransitFilter);
    }

    private void setIntransitFilterAdapter() {
        InstransitFilterAdapter instransitFilterAdapter = new InstransitFilterAdapter(filterList, this);
        rvIntransitFilter.setAdapter(instransitFilterAdapter);
        rvIntransitFilter.setLayoutManager(new LinearLayoutManager(getContext()));
        instransitFilterAdapter.notifyDataSetChanged();
    }

    @Override
    public void clicked(IntransitFilterModel selectedFilter) {
        filterSelected(selectedFilter.getId());
        dismiss();
    }

    public abstract void filterSelected(int id);
}
