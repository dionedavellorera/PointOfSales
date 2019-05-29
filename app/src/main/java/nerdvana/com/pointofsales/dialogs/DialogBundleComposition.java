package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.ListMenuAdapter;
import nerdvana.com.pointofsales.adapters.ListProductsAdapter;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;

public class DialogBundleComposition extends BaseDialog {
    private List<FetchProductsResponse.BranchGroup> branchGroupList;
    private RecyclerView listMenu;
    private ListMenuAdapter listMenuAdapter;
    private RecyclerView listProducts;
    private Category category;
    public DialogBundleComposition(@NonNull Context context, List<FetchProductsResponse.BranchGroup> branchGroupList) {
        super(context);
        this.branchGroupList = branchGroupList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_bundle_composition, "COMPOSE BUNDLE");
        listMenu = findViewById(R.id.listMenu);
        listProducts = findViewById(R.id.listProducts);
        category = new Category() {
            @Override
            public void clicked(int position) {

                ListProductsAdapter listProductsAdapter = new ListProductsAdapter(branchGroupList.get(position).getBranchLists());
                listProducts.setLayoutManager(new GridLayoutManager(getContext(), 5));
                listProducts.setAdapter(listProductsAdapter);
                listProductsAdapter.notifyDataSetChanged();
            }
        };


        listMenu.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));
        listMenuAdapter = new ListMenuAdapter(branchGroupList, category);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listMenu.setLayoutManager(llm);
        listMenu.setAdapter(listMenuAdapter);
        listMenuAdapter.notifyDataSetChanged();



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

    public interface Category {
        void clicked(int position);
    }
}
