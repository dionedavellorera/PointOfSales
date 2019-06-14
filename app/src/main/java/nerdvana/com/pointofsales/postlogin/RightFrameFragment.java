package nerdvana.com.pointofsales.postlogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApiError;
import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.FetchProductsRequest;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.background.ProductsAsync;
import nerdvana.com.pointofsales.custom.DrawableClickListener;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.ProductsContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.BreadcrumbModel;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ChangeThemeModel;
import nerdvana.com.pointofsales.model.ClearSearchData;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.postlogin.adapter.DepartmentsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.RoomsTablesAdapter;

public class RightFrameFragment extends Fragment implements AsyncContract, SelectionContract, ProductsContract{
    private View view;
    private TextView labelQty;
    private RoomTableModel selectedRoom;
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
    private Spinner qtySpinner;

    private SwipeRefreshLayout refreshProducts;
    private TextView breadcrumb;
    private String breadcrumbString = "";

    private String qtySelected = "1";

    private EditText search;
    private CardView cardSearch;
    private RelativeLayout cardSearchRelContainer;
    private ImageView srchImage;

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


        sendFetchProductsRequest();


        refreshProducts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendFetchProductsRequest();
            }
        });

        setQuantitySpinner();


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (productsList.size() > 0) {
                    productsAdapter.getFilter().filter(s);
                }
            }
        });
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if (productsAdapter != null) {
//                    if (productsList.size() > 0) {
//                        productsAdapter.getFilter().filter(s);
//                    }
//
//                }
//                return false;
//            }
//        });
        return view;
    }

    private void initializeArrays() {
        productsList = new ArrayList<>();
        originalProductsList = new ArrayList<>();
        categoryClickedArray = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeViews(View view) {
        search = view.findViewById(R.id.search);
        cardSearch = view.findViewById(R.id.cardSearch);
        cardSearchRelContainer = view.findViewById(R.id.cardSearchRelContainer);
        srchImage = view.findViewById(R.id.srchImage);
//        EditText searchEditText = (EditText) search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchEditText.setTextColor(getResources().getColor(R.color.colorWhite));
//        searchEditText.setHintTextColor(getResources().getColor(R.color.colorWhite));
//
//        ImageView searchIcon = search.findViewById(android.support.v7.appcompat.R.drawable.search_icon);
//        searchIcon.setColorFilter(Color.WHITE);


        qtySpinner = view.findViewById(R.id.qtySpinner);
        breadcrumb = view.findViewById(R.id.breadcrumb);
        listProducts = view.findViewById(R.id.listProducts);
        listTableRoomSelection = view.findViewById(R.id.listTableRoomSelection);
        labelQty = view.findViewById(R.id.labelQty);
        listTableRoomSelection.setNestedScrollingEnabled(false);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetHeader = view.findViewById(R.id.bottomSheetHeader);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        rightFrameConstraint = view.findViewById(R.id.rightFrameConstraint);
        refreshProducts = view.findViewById(R.id.refreshProducts);
        breadcrumb.setText(getResources().getString(R.string.home_text));

        qtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                qtySelected = qtySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                                    productsModel.getProductId(),
                                    productsModel.getMarkUp(),
                                    productsModel.getIsPriceChanged(),
                                    productsModel.getDepartment(),
                                    productsModel.getUnitPrice(),
                                    productsModel.getBranchAlaCartList(),
                                    productsModel.getBranchGroupList()));
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
//                        new RoomsTablesAsync(RightFrameFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
        productsAdapter = new ProductsAdapter(productsList, this, getContext());
        listProducts.setLayoutManager(new GridLayoutManager(getContext(), 4));
        listProducts.setAdapter(productsAdapter);
        productsAdapter.notifyDataSetChanged();


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
        roomsTablesAdapter = new RoomsTablesAdapter(
                new ArrayList<RoomTableModel>(),
                this, getContext(),
                Utils.getSystemType(getContext()));
        listTableRoomSelection.setLayoutManager(new GridLayoutManager(getContext(), 5));
        listTableRoomSelection.setAdapter(roomsTablesAdapter);
    }

    @Override
    public void listClicked(RoomTableModel selectedItem) {
//        Toast.makeText(getContext(), "SAVED", Toast.LENGTH_SHORT).show();
        selectedRoom = selectedItem;
//        saveSelectedSpace(selectedItem.getName(), selectedItem.getAmountSelected());
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
    public void productClicked(ProductsModel productsModel) {
        productsModel.setQty(Integer.valueOf(qtySelected));
        if (categoryClickedArray.size() > 0) {
            if (productsModel.getProductsList().size() != 0) {
                repopulateList(productsModel.getProductsList());
                productsAdapter.notifyDataSetChanged();

            } else {
                BusProvider.getInstance().post(productsModel);
            }
        } else {

            if (productsModel.getProductsList().size() != 0) {
                repopulateList(productsModel.getProductsList());
                productsAdapter.notifyDataSetChanged();
            } else {
                BusProvider.getInstance().post(productsModel);
            }
        }
        qtySpinner.setSelection(0, true);
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
                            productsModel.getProductId(),
                            productsModel.getMarkUp(),
                            productsModel.getIsPriceChanged(),
                            productsModel.getDepartment(),
                            productsModel.getUnitPrice(),
                            productsModel.getBranchAlaCartList(),
                            productsModel.getBranchGroupList()));
        }
    }

    private void pullUpBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void pullDownBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void saveSelectedSpace(String selectedSpace, double amountSelected) {
        CurrentTransactionEntity currentTransaction = new CurrentTransactionEntity(selectedSpace, amountSelected);
        currentTransaction.save();
//        SharedPreferenceManager.saveString(getContext(), selectedSpace, ApplicationConstants.SELECTED_ROOM_TABLE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
    }

    private void sendFetchProductsRequest() {
        BusProvider.getInstance().post(new FetchProductsRequest());
    }

    @Subscribe
    public void onReceiveFetchProductsResponse(FetchProductsResponse fetchProductsResponse) {
        refreshProducts.setRefreshing(false);
//        search.setQuery("", false);
        search.setText("");
        new ProductsAsync(this, fetchProductsResponse, getContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setQuantitySpinner() {
        ArrayList<String> stringArray = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            stringArray.add(String.valueOf(i));
        }

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                stringArray);
        qtySpinner.setAdapter(customSpinnerAdapter);
    }


    @Subscribe
    public void clickedButton(ButtonsModel clickedItem) {
        switch (clickedItem.getId()) {

        }
    }


    @Subscribe
    public void apiErrorReceived(ApiError apiError) {
        refreshProducts.setRefreshing(false);
    }



    private void changeTheme() {
        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.THEME_SELECTED).isEmpty()) {
            lightTheme();
        } else {
            if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                lightTheme();
            } else {
                darkTheme();
            }
        }

        setProductAdapter();
    }

    private void lightTheme() {
        cardSearch.setCardBackgroundColor(getResources().getColor(R.color.lightListBg));
        cardSearchRelContainer.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        srchImage.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        search.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        qtySpinner.setBackgroundColor(getResources().getColor(R.color.lightMainBg));
        labelQty.setBackgroundColor(getResources().getColor(R.color.lightMainBg));

        labelQty.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        search.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        search.setHintTextColor(getResources().getColor(R.color.lightPrimaryFont));
        breadcrumb.setTextColor(getResources().getColor(R.color.lightPrimaryFont));

        for (Drawable drawable : breadcrumb.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(breadcrumb.getContext(), R.color.lightPrimaryFont), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void darkTheme() {
        cardSearch.setCardBackgroundColor(getResources().getColor(R.color.darkListBg));
        cardSearchRelContainer.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        srchImage.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        search.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        qtySpinner.setBackgroundColor(getResources().getColor(R.color.darkMainBg));
        labelQty.setBackgroundColor(getResources().getColor(R.color.darkMainBg));

        labelQty.setTextColor(getResources().getColor(R.color.darkFont));
        search.setTextColor(getResources().getColor(R.color.darkFont));
        search.setHintTextColor(getResources().getColor(R.color.darkFont));
        breadcrumb.setTextColor(getResources().getColor(R.color.darkFont));


        for (Drawable drawable : breadcrumb.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(breadcrumb.getContext(), R.color.darkFont), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @Subscribe
    public void changeTheme(ChangeThemeModel changeThemeModel) {
        changeTheme();
    }

    @Subscribe
    public void clearText(ClearSearchData clearSearchData) {
        search.setText("");
    }

}
