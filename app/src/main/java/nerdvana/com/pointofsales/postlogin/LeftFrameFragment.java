package nerdvana.com.pointofsales.postlogin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.background.ButtonsAsync;
import nerdvana.com.pointofsales.background.CategoryAsync;
import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.background.ProductsAsync;
import nerdvana.com.pointofsales.background.SubCategoryAsync;
import nerdvana.com.pointofsales.custom.BusProvider;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;

public class LeftFrameFragment extends Fragment implements AsyncContract, CheckoutItemsContract, ButtonsContract {
    private View view;

    private double amountToPay = 0;

    private TextView total;
    private TextView header;
    private TextView noItems;

    private List<ProductsModel> selectedProductsList;

    private RecyclerView listCheckoutItems;
    private RecyclerView listButtons;

    private RecyclerView listCategory;
    private RecyclerView listSubCategory;

    private CategoryAdapter categoryAdapter;
    private CategoryAdapter subCategoryAdapter;

    private UserModel userModel;
    private boolean isValid = false;

    private CheckoutAdapter checkoutAdapter;
    private ButtonsAdapter buttonsAdapter;

    private static SelectionContract selectionContract;

    public static LeftFrameFragment newInstance(SelectionContract selectionContract) {
        LeftFrameFragment.selectionContract = selectionContract;
        LeftFrameFragment leftFrameFragment = new LeftFrameFragment();

        return leftFrameFragment;
    }

    private void initializeViews(View view) {
        total = view.findViewById(R.id.total);

        listCheckoutItems = view.findViewById(R.id.listCheckoutItems);
        listButtons = view.findViewById(R.id.listButtons);

        listCategory = view.findViewById(R.id.listCategory);
        listSubCategory = view.findViewById(R.id.listSubCategory);

        noItems = view.findViewById(R.id.notItems);
        header = view.findViewById(R.id.header);

        selectedProductsList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_left_frame, container, false);
        initializeViews(view);
        setProductAdapter();
        setButtonsAdapter();


        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
            isValid = true;
        }

        defaultView();


//        setListCategory();
//        setListSubCategory();



        return view;
    }

    private void setProductAdapter() {

        checkoutAdapter = new CheckoutAdapter(selectedProductsList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listCheckoutItems.setLayoutManager(linearLayoutManager);
        listCheckoutItems.setAdapter(checkoutAdapter);
//        new CheckoutItemsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        checkoutAdapter.addItems(productsModelList);
    }

    private void setButtonsAdapter() {
        buttonsAdapter = new ButtonsAdapter(new ArrayList<ButtonsModel>(), this);
        listButtons.setLayoutManager(new GridLayoutManager(getContext(),2,  GridLayoutManager.HORIZONTAL, false));
        listButtons.setAdapter(buttonsAdapter);
        new ButtonsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setListCategory() {
        categoryAdapter = new CategoryAdapter(new ArrayList<ButtonsModel>());
        listCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listCategory.setAdapter(categoryAdapter);
//        new CategoryAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    private void setListSubCategory() {
        subCategoryAdapter = new CategoryAdapter(new ArrayList<ButtonsModel>());
        listSubCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listSubCategory.setAdapter(subCategoryAdapter);
//        new SubCategoryAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }




    @Override
    public void doneLoading(List list, String isFor) {

        switch (isFor) {
//            case "checkout":
//                noItems.setVisibility(View.GONE);
////                checkoutAdapter.addItems(list);
//
//                selectedProductsList.add(((ProductsModel)item));
//                checkoutAdapter.notifyDataSetChanged();
//                break;
            case "buttons":
                buttonsAdapter.addItems(list);
                break;
            case "category":
                categoryAdapter.addItems(list);
                break;
            case "subcategory":
                subCategoryAdapter.addItems(list);
                break;
        }
    }

    private void defaultView() {
        if (isValid) { //means userModel is not null
            noItems.setVisibility(View.VISIBLE);
            switch (userModel.getSystemType().toLowerCase()) {
                case "room":
                    header.setText("No room selected");
                    break;
                case "table":
                    header.setText("No table selected");
                    break;
                case "checkout":
                    header.setText("Checkout");
                    break;
            }

        }
    }

    private void setView(String input) {
        if (isValid) { //means userModel is not null
//            noItems.setVisibility(View.VISIBLE);
            switch (userModel.getSystemType().toLowerCase()) {
                case "room":
                    header.setText(String.format("%s %s %s", "Room", input, "selected"));
                    break;
                case "table":
                    header.setText(String.format("%s %s %s", "Table", input, "selected"));
                    break;
                case "checkout":
                    header.setText("Checkout");
                    break;
            }

        }
    }

    @Subscribe
    public void fragmentNotified(FragmentNotifierModel fragmentNotifierModel) {
        setView(fragmentNotifierModel.getNotifier());
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void productsClicked(ProductsModel productsModel) {
        if (noItems.getVisibility() == View.VISIBLE) noItems.setVisibility(View.GONE);
        productsModel.setSelected(false);
        new CheckoutItemsAsync(this, selectedProductsList , productsModel).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void itemAdded(ProductsModel itemAdded) {
//        checkoutAdapter.notifyItemInserted(selectedProductsList.size() - 1);
        checkoutAdapter.notifyDataSetChanged();
        listCheckoutItems.scrollToPosition(checkoutAdapter.getItemCount() - 1);
        computeTotal(itemAdded);
    }

    private void computeTotal(ProductsModel itemAdded) {
        amountToPay += itemAdded.getPrice();
        total.setText(String.valueOf(amountToPay));
    }

    @Override
    public void itemRemoved(ProductsModel item) {

    }

    @Override
    public void itemSelected(ProductsModel itemSelected, int position) {
        itemSelected.setSelected(itemSelected.isSelected() ? false : true);
        checkoutAdapter.notifyItemChanged(position);
    }

    @Override
    public void itemLongClicked(ProductsModel itemSelected, int position, View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_checkout_item, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public void clicked(ButtonsModel buttonsModel) {
        switch (buttonsModel.getId()) {
            case 100: //SAVE TRANSACTION:
//                selectedProductsList;
                Toast.makeText(getContext(), "SAVE TRANS MADE", Toast.LENGTH_SHORT).show();
                break;
            case 101: //VOID
                break;
            case 102: //PAYMENT
                break;
        }
    }
}
