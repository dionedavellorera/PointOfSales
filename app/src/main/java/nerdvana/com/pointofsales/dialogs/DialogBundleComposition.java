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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.ListMenuAdapter;
import nerdvana.com.pointofsales.adapters.ListProductsAdapter;
import nerdvana.com.pointofsales.adapters.SelectedProductsAdapter;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;

public class DialogBundleComposition extends BaseDialog {
    private boolean onBind = false;
    private List<FetchProductsResponse.BranchGroup> branchGroupList;
    private List<SelectedProductsInBundleModel> selectedProductsInBundleModelList;
    private List<FetchProductsResponse.BranchList> branchLists;
    private RecyclerView listMenu;
    private ListMenuAdapter listMenuAdapter;
    private SelectedProductsAdapter selectedProductsAdapter;
    private RecyclerView listProducts;
    private RecyclerView listSelectedProducts;
    private Category category;
    private Product product;
    private TextView selectionTitle;
    private List<SelectedProductsInBundleModel.BundleProductModel> bundleProductModelList;;
    public DialogBundleComposition(@NonNull Context context, List<FetchProductsResponse.BranchGroup> branchGroupList) {
        super(context);
        this.branchGroupList = branchGroupList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_bundle_composition, "COMPOSE BUNDLE");
        selectedProductsInBundleModelList = new ArrayList<>();
        branchLists = new ArrayList<>();
        listMenu = findViewById(R.id.listMenu);
        listProducts = findViewById(R.id.listProducts);
        selectionTitle = findViewById(R.id.selectionTitle);
        listSelectedProducts = findViewById(R.id.listSelectedProducts);


        bundleProductModelList = new ArrayList<>();



        int index = 0;
        for (FetchProductsResponse.BranchGroup branchGroup : branchGroupList) {
            selectedProductsInBundleModelList.add(new SelectedProductsInBundleModel(branchGroup.getCoreId(),
                    branchGroup.getGroupName(),
                    index,
                    new ArrayList<SelectedProductsInBundleModel.BundleProductModel>()));
            index++;
        }


        product = new Product() {
            @Override
            public void clicked(int position) {
                onBind = true;

                if (selectedProductsAdapter != null) {
                    for (SelectedProductsInBundleModel sib : selectedProductsInBundleModelList) {
                        if (sib.getGroupId() == branchLists.get(position).getProductGroupId()) {

                                if (sib.getBundleProductModelList().size() < 1) {
                                    sib.getBundleProductModelList()
                                            .add(
                                                    new SelectedProductsInBundleModel.BundleProductModel(
                                                            branchLists.get(position).getBranchProduct().getProduct(),
                                                            branchLists.get(position).getBranchProduct().getImageFile(),
                                                            1));
                                } else {
                                    boolean isExisting = false;
                                    for (SelectedProductsInBundleModel.BundleProductModel bpm : sib.getBundleProductModelList()) {
                                        if (bpm.getName().equals(branchLists.get(position).getBranchProduct().getProduct())) {
                                            bpm.setQty(bpm.getQty() + 1);
                                            isExisting = true;
                                        }
                                    }
                                    if (!isExisting){
                                        sib.getBundleProductModelList()
                                                .add(
                                                        new SelectedProductsInBundleModel.BundleProductModel(
                                                                branchLists.get(position).getBranchProduct().getProduct(),
                                                                branchLists.get(position).getBranchProduct().getImageFile(),
                                                                1));
                                    }
                                }



                                if (selectedProductsAdapter != null) {
                                    selectedProductsAdapter.notifyDataSetChanged();
                                }


                        }
                    }
                }
                onBind = false;
            }
        };

        category = new Category() {
            @Override
            public void clicked(int position) {

                bundleProductModelList = selectedProductsInBundleModelList.get(position).getBundleProductModelList();
                selectedProductsAdapter = new SelectedProductsAdapter(bundleProductModelList);
                LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                listSelectedProducts.setLayoutManager(llm2);
                listSelectedProducts.setAdapter(selectedProductsAdapter);
                selectedProductsAdapter.notifyDataSetChanged();


                branchLists = branchGroupList.get(position).getBranchLists();
                selectionTitle.setText(branchGroupList.get(position).getGroupName());
                ListProductsAdapter listProductsAdapter = new ListProductsAdapter(branchGroupList.get(position).getBranchLists(), product);
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


        listSelectedProducts.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));
        selectedProductsAdapter = new SelectedProductsAdapter(bundleProductModelList);
        LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listSelectedProducts.setLayoutManager(llm2);
        listSelectedProducts.setAdapter(selectedProductsAdapter);
        selectedProductsAdapter.notifyDataSetChanged();
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

    public interface Product {
        void clicked(int position);
    }
}
