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
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.ProductsContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
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

    private List<Integer> categoryClickedArray;

    public static RightFrameFragment newInstance(SelectionContract selectionContract) {

        RightFrameFragment rightFrameFragment = new RightFrameFragment();
        return rightFrameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_right_frame, container, false);
        productsList = new ArrayList<>();
        categoryClickedArray = new ArrayList<>();
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
        listProducts = view.findViewById(R.id.listProducts);
        listTableRoomSelection = view.findViewById(R.id.listTableRoomSelection);
        listTableRoomSelection.setNestedScrollingEnabled(false);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetHeader = view.findViewById(R.id.bottomSheetHeader);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        rightFrameConstraint = view.findViewById(R.id.rightFrameConstraint);



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


        new ProductsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    @Override
    public void doneLoading(List list, String isFor) {

        switch (isFor) {
            case "products":
                productsList = list;
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
            List<ProductsModel> tempProduct = selectedArray(categoryClickedArray.get(0)).getProductsList();
            Log.d("PEKPEK", String.valueOf(tempProduct.size()));
            if (tempProduct.size() != 0) {
                List<ProductsModel> tempProduct2 = tempProduct.get(0).getProductsList();
                productsList.clear();
                productsList.addAll(tempProduct2);
                productsAdapter.notifyDataSetChanged();
            } else {
                BusProvider.getInstance().post(productsList.get(position));
                Toast.makeText(getContext(), productsList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

        } else {
            if (productsList.get(position).getProductsList().size() != 0) {
                categoryClickedArray.add(position);
                List<ProductsModel> tempProduct = productsList.get(position).getProductsList();
                productsList.clear();
                productsList.addAll(tempProduct);
                productsAdapter.notifyDataSetChanged();
            } else {
                BusProvider.getInstance().post(productsList.get(position));
                Toast.makeText(getContext(), productsList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private ProductsModel selectedArray(int position) {
        ProductsModel productsModel = null;
        for (Integer arr : categoryClickedArray) {
            productsModel = productsList.get(position);
        }
        return productsModel;
    }


}
