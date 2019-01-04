package nerdvana.com.pointofsales.postlogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.background.ButtonsAsync;
import nerdvana.com.pointofsales.background.CategoryAsync;
import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.background.ProductsAsync;
import nerdvana.com.pointofsales.background.SubCategoryAsync;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;

public class LeftFrameFragment extends Fragment implements AsyncContract {
    private View view;
    private RecyclerView listCheckoutItems;
    private RecyclerView listButtons;

    private RecyclerView listCategory;
    private RecyclerView listSubCategory;

    private CategoryAdapter categoryAdapter;
    private CategoryAdapter subCategoryAdapter;



    private CheckoutAdapter checkoutAdapter;
    private ButtonsAdapter buttonsAdapter;
    public static LeftFrameFragment newInstance() {
        LeftFrameFragment leftFrameFragment = new LeftFrameFragment();

        return leftFrameFragment;
    }

    private void initializeViews(View view) {
        listCheckoutItems = view.findViewById(R.id.listCheckoutItems);
        listButtons = view.findViewById(R.id.listButtons);

        listCategory = view.findViewById(R.id.listCategory);
        listSubCategory = view.findViewById(R.id.listSubCategory);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_left_frame, container, false);
        initializeViews(view);
        setProductAdapter();
        setButtonsAdapter();

//        setListCategory();
//        setListSubCategory();


        return view;
    }

    private void setProductAdapter() {
        checkoutAdapter = new CheckoutAdapter(new ArrayList<ProductsModel>());
        listCheckoutItems.setLayoutManager(new LinearLayoutManager(getContext()));
        listCheckoutItems.setAdapter(checkoutAdapter);
        new CheckoutItemsAsync(this).execute();
//        checkoutAdapter.addItems(productsModelList);
    }



    private void setButtonsAdapter() {
        buttonsAdapter = new ButtonsAdapter(new ArrayList<ButtonsModel>());
        listButtons.setLayoutManager(new GridLayoutManager(getContext(),2,  GridLayoutManager.HORIZONTAL, false));
        listButtons.setAdapter(buttonsAdapter);
        new ButtonsAsync(this).execute();
    }

    private void setListCategory() {
        categoryAdapter = new CategoryAdapter(new ArrayList<ButtonsModel>());
        listCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listCategory.setAdapter(categoryAdapter);
        new CategoryAsync(this).execute();

    }

    private void setListSubCategory() {
        subCategoryAdapter = new CategoryAdapter(new ArrayList<ButtonsModel>());
        listSubCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        listSubCategory.setAdapter(subCategoryAdapter);
        new SubCategoryAsync(this).execute();
    }




    @Override
    public void doneLoading(List list, String isFor) {
        switch (isFor) {
            case "checkout":
                checkoutAdapter.addItems(list);
                break;
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
}
