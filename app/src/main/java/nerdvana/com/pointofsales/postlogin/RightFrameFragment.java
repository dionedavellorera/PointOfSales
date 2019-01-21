package nerdvana.com.pointofsales.postlogin;

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
    private List<ProductsModel> beforeChangeProductList;
    private List<BreadcrumbModel> categoryClickedArray;

    private TextView breadcrumb;
    private String breadcrumbString = "";
    public static RightFrameFragment newInstance(SelectionContract selectionContract) {

        RightFrameFragment rightFrameFragment = new RightFrameFragment();
        return rightFrameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_right_frame, container, false);
        productsList = new ArrayList<>();
        originalProductsList = new ArrayList<>();
        categoryClickedArray = new ArrayList<>();
        beforeChangeProductList = new ArrayList<>();
        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
            isValid = true;
        }

        ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());

        initializeViews(view);

        showAppropriateView();

        setProductAdapter();

        setRoomsTableAdapter();

//        setDepartmentAdapter();

        return view;
    }

    private void initializeViews(View view) {
//        listDepartments = view.findViewById(R.id.listDepartments);
        breadcrumb = view.findViewById(R.id.breadcrumb);
        listProducts = view.findViewById(R.id.listProducts);
        listTableRoomSelection = view.findViewById(R.id.listTableRoomSelection);
        listTableRoomSelection.setNestedScrollingEnabled(false);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetHeader = view.findViewById(R.id.bottomSheetHeader);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        rightFrameConstraint = view.findViewById(R.id.rightFrameConstraint);
        breadcrumb.setText("Home");

        breadcrumb.setOnTouchListener(new DrawableClickListener.LeftDrawableClickListener(breadcrumb) {
            @Override
            public boolean onDrawableClick() {
                breadcrumb.setText("Home");
                breadcrumbString = "";
                categoryClickedArray.clear();
                productsList.clear();
//                productsList = new ArrayList<>(originalProductsList);

                for (ProductsModel productsModel : originalProductsList) {
                    productsList.add(new ProductsModel(productsModel.getName(), productsModel.getPrice(), productsModel.getVat(), productsModel.isAvailable(), productsModel.getImageUrls(), productsModel.isVattable(), productsModel.getShortName(), productsModel.getProductsList()));
                }
                productsAdapter.notifyDataSetChanged();

//                if (categoryClickedArray.size() > 0) {
//
//
////                    if (categoryClickedArray.size() < 1) {
////                        breadcrumbString += String.format("%s %s", "", categoryClickedArray.get(categoryClickedArray.size() - 1).getName());
////                        breadcrumb.setText(breadcrumbString);
////                    } else {
////                        breadcrumbString += String.format(" %s %s", ">>", categoryClickedArray.get(categoryClickedArray.size() - 1).getName());
////                        breadcrumb.setText(breadcrumbString);
////                    }
//                    Toast.makeText(getContext(), "should repopulate list", Toast.LENGTH_SHORT).show();
////                    beforeChangeProductList = new ArrayList<>(productsList);
//                    Log.d("HHH", String.valueOf(categoryClickedArray.get(categoryClickedArray.size() - 1).getProdList().size()));
//                    repopulateList(categoryClickedArray.get(categoryClickedArray.size() - 1).getProdList());
////                    List<ProductsModel> tempProduct2 = categoryClickedArray.get(categoryClickedArray.size() - 1).getProdList().getProductsList();
////                    productsList.clear();
////                    productsList = tempProduct2;
//                    productsAdapter.notifyDataSetChanged();
//
//                    categoryClickedArray.remove(categoryClickedArray.size() - 1);
//                } else {
//                    Log.d("TEKTEK", String.valueOf(originalProductsList.size()));
////                    repopulateList(originalProductsList);
////                    List<ProductsModel> tempProduct2 = originalProductsList;
////                    beforeChangeProductList = new ArrayList<>(productsList);
//                    productsList.clear();
//                    productsList = originalProductsList;
//                    productsAdapter.notifyDataSetChanged();
//                }

                return true;
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_DRAGGING: {
//                        Log.d("BOTTOMSHEET", "DRAGGING");
                        break;
                    }
                    case BottomSheetBehavior.STATE_SETTLING: {
//                        Log.d("BOTTOMSHEET", "SETTLING");
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        Log.d("BOTTOMSHEET", "EXPANDED");
//                        setRoomsTableAdapter();
                        new RoomsTablesAsync(RightFrameFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        bottomSheetHeader.setText("Slide down to close");

                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        Log.d("BOTTOMSHEET", "COLLAPSED");
                        showAppropriateView();
                        hasCollapsed = true;
                        break;
                    }
                    case BottomSheetBehavior.STATE_HIDDEN: {
//                        Log.d("BOTTOMSHEET", "HIDDEN");
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
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


    }

    private void showAppropriateView() {
        if (isValid) {
            switch (userModel.getSystemType().toLowerCase()) {
                case "room":
                    setView("room");
                    break;
                case "table":
                    setView("table");
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
        bottomSheetHeader.setText(String.format("%s %s", "Swipe up to view", title));
    }

    private void setProductAdapter() {
        productsAdapter = new ProductsAdapter(productsList, this);

//        GridLayoutManagerOverScroll gridLayoutManager = new GridLayoutManagerOverScroll(getContext(), 5, this);
        listProducts.setLayoutManager(new GridLayoutManager(getContext(), 5));
        listProducts.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();

        new ProductsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    @Override
    public void doneLoading(List list, String isFor) {

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

    private void setDepartmentAdapter() {
//        departmentsAdapter = new DepartmentsAdapter(new ArrayList<DepartmentsModel>());
//        listDepartments.setLayoutManager(new LinearLayoutManager(getContext()));
//        listDepartments.setAdapter(departmentsAdapter);
//        new DepartmentsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @Override
    public void listClicked(String input) {

        BusProvider.getInstance().post(new FragmentNotifierModel(input));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void productClicked(int position) {
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
                beforeChangeProductList = new ArrayList<>(productsList);
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
                beforeChangeProductList = new ArrayList<>(productsList);
                repopulateList(productsList.get(position).getProductsList());
//                List<ProductsModel> tempProduct = productsList.get(position).getProductsList();
//                productsList.clear();
//                productsList = tempProduct;
                productsAdapter.notifyDataSetChanged();
            } else {
                BusProvider.getInstance().post(productsList.get(position));
                Toast.makeText(getContext(), productsList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void repopulateList(List<ProductsModel> tempProduct) {
//        List<ProductsModel> tempProduct2 = tempProduct;
        productsList.clear();
        for (ProductsModel productsModel : tempProduct) {
            productsList.add(new ProductsModel(productsModel.getName(), productsModel.getPrice(), productsModel.getVat(), productsModel.isAvailable(), productsModel.getImageUrls(), productsModel.isVattable(), productsModel.getShortName(), productsModel.getProductsList()));
        }
    }


}
