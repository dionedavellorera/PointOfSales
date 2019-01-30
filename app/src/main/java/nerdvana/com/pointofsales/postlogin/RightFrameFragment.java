package nerdvana.com.pointofsales.postlogin;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.background.ProductsAsync;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.custom.BusProvider;
import nerdvana.com.pointofsales.custom.DrawableClickListener;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.ProductsContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.BreadcrumbModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.postlogin.adapter.DepartmentsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;

public class RightFrameFragment extends Fragment implements AsyncContract, SelectionContract, ProductsContract{
    private View view;
    private boolean hasCollapsed = false;
    private boolean isValid = false;
    private RecyclerView listProducts;
    private ProductsAdapter productsAdapter;
    private static UserModel userModel;
    private TextView bottomSheetHeader;
    private View bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView listTableRoomSelection;
    private ConstraintLayout rightFrameConstraint;
    private RoomsTablesAdapter roomsTablesAdapter;
//    private RecyclerView listDepartments;
    private DepartmentsAdapter departmentsAdapter;
    private List<ProductsModel> productsList;
    private List<ProductsModel> originalProductsList;
    private List<BreadcrumbModel> categoryClickedArray;

    private TextView breadcrumb;
    private String breadcrumbString = "";
    public static RightFrameFragment newInstance() {

        RightFrameFragment rightFrameFragment = new RightFrameFragment();
        return rightFrameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_right_frame, container, false);

        initializeArrays();

        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);

        if (userModel != null) {
            isValid = true;
        }


        initializeViews(view);

        showAppropriateView();

        setProductAdapter();

        setRoomsTableAdapter();

        return view;
    }

    private void initializeArrays() {
        productsList = new ArrayList<>();
        originalProductsList = new ArrayList<>();
        categoryClickedArray = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeViews(View view) {
        breadcrumb = view.findViewById(R.id.breadcrumb);
        listProducts = view.findViewById(R.id.listProducts);
        listTableRoomSelection = view.findViewById(R.id.listTableRoomSelection);
        listTableRoomSelection.setNestedScrollingEnabled(false);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetHeader = view.findViewById(R.id.bottomSheetHeader);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        rightFrameConstraint = view.findViewById(R.id.rightFrameConstraint);
        breadcrumb.setText(getResources().getString(R.string.home_text));

        breadcrumb.setOnTouchListener(new DrawableClickListener.LeftDrawableClickListener(breadcrumb) {
            @Override
            public boolean onDrawableClick() {
                breadcrumb.setText(getResources().getString(R.string.home_text));
                breadcrumbString = "";
                categoryClickedArray.clear();
                productsList.clear();

                for (ProductsModel productsModel : originalProductsList) {
                    productsList.add(
                            new ProductsModel(
                                    productsModel.getName(), productsModel.getPrice(),
                                    productsModel.getVat(), productsModel.isAvailable(),
                                    productsModel.getImageUrls(), productsModel.isVattable(),
                                    productsModel.getShortName(), productsModel.getProductsList(),
                                    productsModel.isSelected(), productsModel.isSerialNumberRequired(),
                                    productsModel.getLowStackCount(), productsModel.getProductStatus(),
                                    productsModel.getProductId()));
                }
                productsAdapter.notifyDataSetChanged();

                return true;
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_DRAGGING: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        new RoomsTablesAsync(RightFrameFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        bottomSheetHeader.setText(getResources().getString(R.string.pulldown_text));
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        showAppropriateView();
                        hasCollapsed = true;
                        bottomSheet.scrollTo(0, 0);
                        break;
                    }
                    case BottomSheetBehavior.STATE_HIDDEN: {
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        bottomSheetHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    pullUpBottomSheet();
                } else {
                    pullDownBottomSheet();
                }
            }
        });
    }

    private void showAppropriateView() {
        //is valid determines userModel is not null
        //decide what view to show depending on system type
        if (isValid) {
            switch (userModel.getSystemType().toLowerCase()) {
                case "room":
                    setView(getResources().getString(R.string.room_text));
                    break;
                case "table":
                    setView(getResources().getString(R.string.table_text));
                    break;
                case "checkout":
//                    setView("Not Available");
                    listTableRoomSelection.setAdapter(null);
                    bottomSheetBehavior.setHideable(true);
                    bottomSheetBehavior.setPeekHeight(0);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)rightFrameConstraint.getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    rightFrameConstraint.setLayoutParams(params);
                    break;
            }
        }
    }

    private void setView(String title) {
        bottomSheetHeader.setText(String.format("%s %s", getResources().getString(R.string.pullup_text), title));
    }

    private void setProductAdapter() {
        //set products adapter with 5 columns (grid layout)
        productsAdapter = new ProductsAdapter(productsList, this);
        listProducts.setLayoutManager(new GridLayoutManager(getContext(), 5));
        listProducts.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();

        //asynchronously add products to the list
        new ProductsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    @Override
    public void doneLoading(List list, String isFor) {
        //notifier that an async job is done
        switch (isFor) {
            case "products":
                productsList = list;
                originalProductsList =  new ArrayList<>(list);
                productsAdapter.addItems(list);
                break;
            case "roomstables":
                roomsTablesAdapter.addItems(list);
                break;
            case "departments":
                departmentsAdapter.addItems(list);
                break;
        }
    }

    private void setRoomsTableAdapter() {
        roomsTablesAdapter = new RoomsTablesAdapter(new ArrayList<RoomTableModel>(), this);
        listTableRoomSelection.setLayoutManager(new GridLayoutManager(getContext(), 5));
        listTableRoomSelection.setAdapter(roomsTablesAdapter);
    }

    @Override
    public void listClicked(RoomTableModel selectedItem) {
        Toast.makeText(getContext(), "SAVED", Toast.LENGTH_SHORT).show();
        saveSelectedSpace(selectedItem.getName());
//        BusProvider.getInstance().post(new FragmentNotifierModel(selectedItem.getName()));
    }

//    @Override
//    public void listClicked(String input) {
//        //notifies leftfragment that an area is clicked and updates the ui
//        //closes the bottom sheet immediately after selecting
//        saveSelectedSpace(input);
//        BusProvider.getInstance().post(new FragmentNotifierModel(input));
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//    }

    private String selectedRoomNumber() {
        List<CurrentTransactionEntity> currentTransaction  = CurrentTransactionEntity.listAll(CurrentTransactionEntity.class);
        return currentTransaction.size() > 0 ? currentTransaction.get(0).getRoomNumber(): "";
    }

    @Override
    public void productClicked(int position) {

//        Log.d("PRODUCTIDSHIT", String.valueOf(productsList.get(position).getProductId()));
//        Log.d("PRODUCTIDSHIT", SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_ROOM_TABLE));
//        Log.d("PRODUCTIDSHIT", userModel.getSystemType());

        //checks if system is hotel / dine in and verifies if there is a selected area to put order
        if (TextUtils.isEmpty(selectedRoomNumber()) &&
                (userModel.getSystemType().equals("room") || userModel.getSystemType().equals("table"))) {
            Toast.makeText(getContext(), getResources().getString(R.string.error_no_space_selected), Toast.LENGTH_SHORT).show();
            pullUpBottomSheet();
        } else {
            //checkout/proceed to order
            //conditions are for breadcrumb purposes, dynamic product listing (unlimited categories)
            if (categoryClickedArray.size() > 0) {
                ProductsModel tempProduct = productsList.get(position);
                if (tempProduct.getProductsList().size() != 0) {
                    categoryClickedArray.add(new BreadcrumbModel(tempProduct.getName(), position, new ArrayList<ProductsModel>(productsList)));

                    if (categoryClickedArray.size() < 1) {
                        breadcrumbString += String.format("%s %s", "", categoryClickedArray.get(categoryClickedArray.size() - 1).getName());
                        breadcrumb.setText(breadcrumbString);
                    } else {
                        breadcrumbString += String.format(" %s %s", "»", categoryClickedArray.get(categoryClickedArray.size() - 1).getName());
                        breadcrumb.setText(breadcrumbString);
                    }
                    repopulateList(tempProduct.getProductsList());
                    productsAdapter.notifyDataSetChanged();
                } else {
                    BusProvider.getInstance().post(productsList.get(position));
                    Toast.makeText(getContext(), productsList.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (productsList.get(position).getProductsList().size() != 0) {
                    if (categoryClickedArray.size() < 1) {
                        breadcrumbString += String.format("%s %s", "", productsList.get(position).getName());
                        breadcrumb.setText(breadcrumbString);
                    } else {
                        breadcrumbString += String.format(" %s %s", "»", productsList.get(position).getName());
                        breadcrumb.setText(breadcrumbString);
                    }

                    categoryClickedArray.add(new BreadcrumbModel(productsList.get(position).getName(), position, productsList));
                    repopulateList(productsList.get(position).getProductsList());
                    productsAdapter.notifyDataSetChanged();
                } else {
                    BusProvider.getInstance().post(productsList.get(position));
                    Toast.makeText(getContext(), productsList.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    private void repopulateList(List<ProductsModel> tempProduct) {
//        List<ProductsModel> tempProduct2 = tempProduct;
        productsList.clear();
        for (ProductsModel productsModel : tempProduct) {
            productsList.add(
                    new ProductsModel(
                            productsModel.getName(), productsModel.getPrice(),
                            productsModel.getVat(), productsModel.isAvailable(),
                            productsModel.getImageUrls(), productsModel.isVattable(),
                            productsModel.getShortName(), productsModel.getProductsList(),
                            productsModel.isSelected(), productsModel.isSerialNumberRequired(),
                            productsModel.getLowStackCount(), productsModel.getProductStatus(),
                            productsModel.getProductId()));
        }
    }

    private void pullUpBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void pullDownBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void saveSelectedSpace(String selectedSpace) {
        CurrentTransactionEntity currentTransaction = new CurrentTransactionEntity(selectedSpace);
        currentTransaction.save();
//        SharedPreferenceManager.saveString(getContext(), selectedSpace, ApplicationConstants.SELECTED_ROOM_TABLE);
    }


}
