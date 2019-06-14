package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.ListMenuAdapter;
import nerdvana.com.pointofsales.adapters.ListProductsAdapter;
import nerdvana.com.pointofsales.adapters.SelectedProductsAdapter;
import nerdvana.com.pointofsales.api_requests.TestSend;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DialogBundleComposition extends BaseDialog {
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
    private Context act;
    private FloatingActionButton nextButton;
    private FloatingActionButton backButton;
    private int pageIndicator = 0;
    private List<SelectedProductsInBundleModel.BundleProductModel> bundleProductModelList;;
    private double bundleAmount;

    private Minus minus;
    private int qtySelected = 1;
    public DialogBundleComposition(@NonNull Context context,
                                   List<FetchProductsResponse.BranchGroup> branchGroupList,
                                   double bundleAmount,
                                   int qtySelected) {
        super(context);
        this.act = context;
        this.branchGroupList = branchGroupList;
        this.bundleAmount = bundleAmount;
        this.qtySelected = qtySelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_bundle_composition, "COMPOSE BUNDLE");
        selectedProductsInBundleModelList = new ArrayList<>();
        branchLists = new ArrayList<>();
        listMenu = findViewById(R.id.listMenu);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButtonNavigation);
        listProducts = findViewById(R.id.listProducts);
        selectionTitle = findViewById(R.id.selectionTitle);
        listSelectedProducts = findViewById(R.id.listSelectedProducts);

        minus = new Minus() {
            @Override
            public void clicked(int position) {
                if ((bundleProductModelList.get(position).getQty() - 1) == 0) {
                    bundleProductModelList.remove(position);
                    selectedProductsAdapter.notifyDataSetChanged();
                }
            }
        };

        bundleProductModelList = new ArrayList<>();
        int index = 0;
        for (FetchProductsResponse.BranchGroup branchGroup : branchGroupList) {
            selectedProductsInBundleModelList.add(new SelectedProductsInBundleModel(
                    branchGroup.getCoreId(),
                    branchGroup.getGroupName(),
                    index,
                    new ArrayList<SelectedProductsInBundleModel.BundleProductModel>(),
                    branchGroup.getQty() * qtySelected,
                    0,
                    bundleAmount));
            index++;
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToNext();
            }
        });


        product = new Product() {
            @Override
            public void clicked(int position) {
                onBind = true;
                if (selectedProductsAdapter != null) {

                    for (SelectedProductsInBundleModel sib : selectedProductsInBundleModelList) {
                        boolean shouldStop = false;
                        if (sib.getGroupId() == branchLists.get(position).getProductGroupId()) {

                                if (sib.getBundleProductModelList().size() < 1) {
                                    if (sib.getTotalQtySelected() < sib.getMaxQty()) {
                                        sib.setTotalQtySelected(sib.getTotalQtySelected() + 1);
                                        sib.getBundleProductModelList()
                                                .add(new SelectedProductsInBundleModel.BundleProductModel(
                                                                branchLists.get(position).getBranchProduct().getProduct(),
                                                                branchLists.get(position).getBranchProduct().getImageFile(),
                                                                1,
                                                                branchLists.get(position).getBranchProduct().getCoreId(),
                                                                branchLists.get(position).getBranchProduct().getAmount()));

                                        selectionTitle.setText(String.format("%s(%s)", sib.getGroupName(), String.valueOf(sib.getMaxQty() - sib.getTotalQtySelected())));
                                        if (sib.getTotalQtySelected() == sib.getMaxQty()) proceedToNext();
                                    } else {
                                        Utils.showDialogMessage(act, "Bundle group max qty exceeded, cannot add", "Information" );
                                        shouldStop = true;
                                        break;
                                    }

                                } else {
                                    boolean isExisting = false;
                                    for (SelectedProductsInBundleModel.BundleProductModel bpm : sib.getBundleProductModelList()) {
                                        if (bpm.getName().equals(branchLists.get(position).getBranchProduct().getProduct())) {
                                            if (sib.getTotalQtySelected() < sib.getMaxQty()) {
                                                bpm.setQty(bpm.getQty() + 1);
                                                sib.setTotalQtySelected(sib.getTotalQtySelected() + 1);
                                                selectionTitle.setText(String.format("%s(%s)", sib.getGroupName(), String.valueOf(sib.getMaxQty() - sib.getTotalQtySelected())));
                                                if (sib.getTotalQtySelected() == sib.getMaxQty()) proceedToNext();
                                                isExisting = true;
                                            }
//                                            else if(sib.getTotalQtySelected() == sib.getMaxQty()) {
//                                                bpm.setQty(bpm.getQty() + 1);
//                                                sib.setTotalQtySelected(sib.getTotalQtySelected() + 1);
//                                                selectionTitle.setText(String.format("%s(%s)", sib.getGroupName(), String.valueOf(sib.getMaxQty() - sib.getTotalQtySelected())));
//                                                if (sib.getTotalQtySelected() == sib.getMaxQty()) proceedToNext();
//                                                isExisting = true;
//                                            }
                                            else {
                                                isExisting = true;
                                                Utils.showDialogMessage(act, "Bundle group max qty exceeded, cannot add", "Information" );
                                                shouldStop = true;
                                                break;
                                            }

                                        }
                                    }
                                    if (!isExisting){
                                        if (sib.getTotalQtySelected() < sib.getMaxQty()) {
                                            sib.setTotalQtySelected(sib.getTotalQtySelected() + 1);
                                            sib.getBundleProductModelList()
                                                    .add(
                                                            new SelectedProductsInBundleModel.BundleProductModel(
                                                                    branchLists.get(position).getBranchProduct().getProduct(),
                                                                    branchLists.get(position).getBranchProduct().getImageFile(),
                                                                    1,
                                                                    branchLists.get(position).getBranchProduct().getCoreId(),
                                                                    branchLists.get(position).getBranchProduct().getAmount()));

                                            selectionTitle.setText(String.format("%s(%s)", sib.getGroupName(), String.valueOf(sib.getMaxQty() - sib.getTotalQtySelected())));

                                            if (sib.getTotalQtySelected() == sib.getMaxQty()) proceedToNext();

                                        } else {
                                            Utils.showDialogMessage(act, "Bundle group max qty exceeded, cannot add", "Information" );
                                            shouldStop = true;
                                            break;
                                        }

                                    }
                                }

                                if (selectedProductsAdapter != null) {
                                    selectedProductsAdapter.notifyDataSetChanged();
                                }

                            break;
                        }
                    }
                }
                onBind = false;
            }
        };

        category = new Category() {
            @Override
            public void clicked(int position) {
                pageIndicator = position;
                bundleProductModelList = selectedProductsInBundleModelList.get(position).getBundleProductModelList();
                selectedProductsAdapter = new SelectedProductsAdapter(bundleProductModelList, minus);
                LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                listSelectedProducts.setLayoutManager(llm2);
                listSelectedProducts.setAdapter(selectedProductsAdapter);
                selectedProductsAdapter.notifyDataSetChanged();


                branchLists = branchGroupList.get(position).getBranchLists();

                selectionTitle.setText(String.format("%s(%s)", branchGroupList.get(position).getGroupName(), String.valueOf(selectedProductsInBundleModelList.get(position).getMaxQty() - selectedProductsInBundleModelList.get(position).getTotalQtySelected())));
                ListProductsAdapter listProductsAdapter = new ListProductsAdapter(
                        branchGroupList.get(position).getBranchLists(), product);
                listProducts.setLayoutManager(new GridLayoutManager(getContext(),
                        5));
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
        selectedProductsAdapter = new SelectedProductsAdapter(bundleProductModelList, minus);
        LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listSelectedProducts.setLayoutManager(llm2);
        listSelectedProducts.setAdapter(selectedProductsAdapter);
        selectedProductsAdapter.notifyDataSetChanged();

        previousPage();
    }

    private void previousPage() {

        if (pageIndicator > 0) {
            pageIndicator -= 1;

        } else {
//            Utils.showDialogMessage(act, "Reached the start", "Information");
        }

        if (selectedProductsInBundleModelList.size() > 0) {
            bundleProductModelList = selectedProductsInBundleModelList.get(pageIndicator).getBundleProductModelList();
            selectedProductsAdapter = new SelectedProductsAdapter(bundleProductModelList, minus);
            LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            listSelectedProducts.setLayoutManager(llm2);
            listSelectedProducts.setAdapter(selectedProductsAdapter);
            selectedProductsAdapter.notifyDataSetChanged();

            branchLists = branchGroupList.get(pageIndicator).getBranchLists();
            selectionTitle.setText(String.format("%s(%s)", branchGroupList.get(pageIndicator).getGroupName(), String.valueOf(selectedProductsInBundleModelList.get(pageIndicator).getMaxQty() - selectedProductsInBundleModelList.get(pageIndicator).getTotalQtySelected())));
            ListProductsAdapter listProductsAdapter = new ListProductsAdapter(branchGroupList.get(pageIndicator).getBranchLists(), product);
            listProducts.setLayoutManager(new GridLayoutManager(getContext(), 5));
            listProducts.setAdapter(listProductsAdapter);
            listProductsAdapter.notifyDataSetChanged();
        }

    }

    private void proceedToNext() {

        if (pageIndicator < selectedProductsInBundleModelList.size() - 1) {
            pageIndicator += 1;
            bundleProductModelList = selectedProductsInBundleModelList.get(pageIndicator).getBundleProductModelList();
            selectedProductsAdapter = new SelectedProductsAdapter(bundleProductModelList, minus);
            LinearLayoutManager llm2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            listSelectedProducts.setLayoutManager(llm2);
            listSelectedProducts.setAdapter(selectedProductsAdapter);
            selectedProductsAdapter.notifyDataSetChanged();


            branchLists = branchGroupList.get(pageIndicator).getBranchLists();
            selectionTitle.setText(String.format("%s(%s)", branchGroupList.get(pageIndicator).getGroupName(), String.valueOf(branchGroupList.get(pageIndicator).getQty())));
            ListProductsAdapter listProductsAdapter = new ListProductsAdapter(branchGroupList.get(pageIndicator).getBranchLists(), product);
            listProducts.setLayoutManager(new GridLayoutManager(getContext(), 5));
            listProducts.setAdapter(listProductsAdapter);
            listProductsAdapter.notifyDataSetChanged();
        } else {
            boolean hasCompletedData = true;
            for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                if (sipm.getMaxQty() != sipm.getTotalQtySelected()) {
                    hasCompletedData = false;
                    Utils.showDialogMessage(act, "Please complete " + sipm.getGroupName(), "Information");
                    break;
                }
            }
            if (hasCompletedData) {
//                Utils.showDialogMessage(act, "Please show summary", "Information");
                bundleCompleted(selectedProductsInBundleModelList);
                dismiss();

            }
        }



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

    public interface Minus {
        void clicked(int position);
    }

    public abstract void bundleCompleted(List<SelectedProductsInBundleModel> sipm);
}
