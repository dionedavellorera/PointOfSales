package nerdvana.com.pointofsales.postlogin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.background.ButtonsAsync;
import nerdvana.com.pointofsales.background.CategoryAsync;
import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.background.DepartmentsAsync;
import nerdvana.com.pointofsales.background.ProductsAsync;
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.background.SubCategoryAsync;
import nerdvana.com.pointofsales.custom.GridLayoutManagerOverScroll;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.OverScroll;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.DepartmentsModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.DepartmentsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;

public class RightFrameFragment extends Fragment implements AsyncContract{
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
    private RecyclerView listDepartments;
    private DepartmentsAdapter departmentsAdapter;

    public static RightFrameFragment newInstance() {
        RightFrameFragment rightFrameFragment = new RightFrameFragment();
        return rightFrameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_right_frame, container, false);

        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
            isValid = true;
        }

        ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());

        initializeViews(view);

        showAppropriateView();

        setProductAdapter();

        setRoomsTableAdapter();

        setDepartmentAdapter();

        return view;
    }

    private void initializeViews(View view) {
        listDepartments = view.findViewById(R.id.listDepartments);
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
                        new RoomsTablesAsync(RightFrameFragment.this).execute();
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
        productsAdapter = new ProductsAdapter(new ArrayList<ProductsModel>());

//        GridLayoutManagerOverScroll gridLayoutManager = new GridLayoutManagerOverScroll(getContext(), 5, this);
        listProducts.setLayoutManager(new GridLayoutManager(getContext(), 5));
        listProducts.setAdapter(productsAdapter);


        new ProductsAsync(this).execute();
    }



    @Override
    public void doneLoading(List list, String isFor) {
        switch (isFor) {
            case "products":
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
        roomsTablesAdapter = new RoomsTablesAdapter(new ArrayList<RoomTableModel>());
        listTableRoomSelection.setLayoutManager(new GridLayoutManager(getContext(), 5));
        listTableRoomSelection.setAdapter(roomsTablesAdapter);
//        new RoomsTablesAsync(this).execute();
    }

    private void setDepartmentAdapter() {
        departmentsAdapter = new DepartmentsAdapter(new ArrayList<DepartmentsModel>());
        listDepartments.setLayoutManager(new LinearLayoutManager(getContext()));
        listDepartments.setAdapter(departmentsAdapter);
        new DepartmentsAsync(this).execute();
    }

}
