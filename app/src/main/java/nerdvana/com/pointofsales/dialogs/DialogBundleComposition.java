package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.ListMenuAdapter;
import nerdvana.com.pointofsales.adapters.ListProductsAdapter;
import nerdvana.com.pointofsales.adapters.SelectedProductsAdapter;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;

public abstract class DialogBundleComposition extends BaseDialog {
    private boolean onBind = false;
    private DialogBundleComposition d;
    private List<FetchProductsResponse.BranchGroup> branchGroupList = new ArrayList<>();
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

//    private List<FetchProductsResponse.BranchGroup> myCopy = new ArrayList<>();
    public DialogBundleComposition(@NonNull Context context,
                                   List<FetchProductsResponse.BranchGroup> bgl,
                                   double bundleAmount,
                                   int qtySelected) {
        super(context);
        this.act = context;
        this.d = this;
        this.branchGroupList = new ArrayList<>();
        for (FetchProductsResponse.BranchGroup bg : bgl) {
            FetchProductsResponse.BranchGroup branchGroup =
                    new FetchProductsResponse.BranchGroup(
                            0,
                            bg.getId(),
                            bg.getCoreId(),
                            bg.getProductId(),
                            bg.getGroupName(),
                            bg.getCurrencyId(),
                            bg.getQty(),
                            bg.getCreatedBy(),
                            bg.getCreatedAt(),
                            "",
                            "");
            List<FetchProductsResponse.BranchList> branchList = new ArrayList<>();
            for (FetchProductsResponse.BranchList bls : bg.getBranchLists()) {
                FetchProductsResponse.BranchList tmpBrclist =
                        new FetchProductsResponse.BranchList(
                                bls.getId(),
                                bls.getProductGroupId(),
                                bls.getCurrencyId(),
                                bls.getPrice(),
                                bls.getCreatedBy(),
                                bls.getCreatedAt(),
                                bls.getUpdatedAt(),
                                bls.getDeletedAt(),
                                bls.getBranchProduct()
                        );
                branchList.add(tmpBrclist);
            }
            branchGroup.setBranchLists(branchList);
            branchGroupList.add(branchGroup);
        }
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
                    int qty = 0;
                    for (SelectedProductsInBundleModel.BundleProductModel bpm : bundleProductModelList) {
                        qty += bpm.getQty();
                    }
                    selectedProductsInBundleModelList
                            .get(pageIndicator)
                            .setTotalQtySelected((qty - 1));
                    branchGroupList
                            .get(pageIndicator)
                            .setSelectedQtyInBranch(0);
                    bundleProductModelList.remove(position);
                    selectedProductsAdapter.notifyDataSetChanged();
                    if (listMenuAdapter != null) {
                        listMenuAdapter.notifyDataSetChanged();
                    }
                    selectionTitle.setText(String.format("%s(%s)",
                            branchGroupList.get(pageIndicator).getGroupName(),
                            String.valueOf(
                                    selectedProductsInBundleModelList.get(pageIndicator).getMaxQty()
                                            - (qty - 1)
                            )));
                } else {

                    selectedProductsInBundleModelList
                            .get(pageIndicator)
                            .setTotalQtySelected(
                                    selectedProductsInBundleModelList
                                            .get(pageIndicator)
                                            .getTotalQtySelected() - 1

                            );
                    branchGroupList
                            .get(pageIndicator)
                            .setSelectedQtyInBranch(
                                    selectedProductsInBundleModelList
                                            .get(pageIndicator)
                                            .getTotalQtySelected() - 1

                            );
                    bundleProductModelList
                            .get(position)
                            .setQty(
                                    bundleProductModelList.get(position).getQty() - 1
                            );
                    selectedProductsAdapter.notifyDataSetChanged();
                    if (listMenuAdapter != null) {
                        listMenuAdapter.notifyDataSetChanged();
                    }


                    selectionTitle.setText(String.format("%s(%s)",
                            branchGroupList.get(pageIndicator).getGroupName(),
                            String.valueOf(selectedProductsInBundleModelList.get(pageIndicator).getMaxQty()
                                    - selectedProductsInBundleModelList.get(pageIndicator).getTotalQtySelected())));
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
                                        branchGroupList.get(pageIndicator).setSelectedQtyInBranch(sib.getTotalQtySelected());
                                        sib.getBundleProductModelList()
                                                .add(new SelectedProductsInBundleModel.BundleProductModel(
                                                                branchLists.get(position).getBranchProduct().getProduct(),
                                                                branchLists.get(position).getBranchProduct().getImageFile(),
                                                                1,
                                                                branchLists.get(position).getBranchProduct().getCoreId(),
                                                                branchLists.get(position).getBranchProduct().getAmount(),
                                                                branchLists.get(position).getPrice()));

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
                                                branchGroupList.get(pageIndicator).setSelectedQtyInBranch(sib.getTotalQtySelected());
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
                                            branchGroupList.get(pageIndicator).setSelectedQtyInBranch(sib.getTotalQtySelected());
                                            sib.getBundleProductModelList()
                                                    .add(
                                                            new SelectedProductsInBundleModel.BundleProductModel(
                                                                    branchLists.get(position).getBranchProduct().getProduct(),
                                                                    branchLists.get(position).getBranchProduct().getImageFile(),
                                                                    1,
                                                                    branchLists.get(position).getBranchProduct().getCoreId(),
                                                                    branchLists.get(position).getBranchProduct().getAmount(),
                                                                    branchLists.get(position).getPrice()));

                                            selectionTitle.setText(String.format("%s(%s)", sib.getGroupName(), String.valueOf(sib.getMaxQty() - sib.getTotalQtySelected())));


                                            if (sib.getTotalQtySelected() == sib.getMaxQty()) {
                                                proceedToNext();
                                            }

                                        } else {
                                            Utils.showDialogMessage(act, "Bundle group max qty exceeded, cannot add", "Information" );
                                            shouldStop = true;
                                            break;
                                        }
                                    }
                                }



                            if (listMenuAdapter != null) {
                                listMenuAdapter.notifyDataSetChanged();
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
        listMenuAdapter = new ListMenuAdapter(branchGroupList, category, getContext());

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
//        pageIndicator = 0;
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
                final BundleSummaryDialog bundleSummaryDialog = new BundleSummaryDialog(act, selectedProductsInBundleModelList) {
                    @Override
                    public void confirm() {
                        bundleCompleted(selectedProductsInBundleModelList);
                        dismiss();
                        d.dismiss();
                    }
                };
                bundleSummaryDialog.show();
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
