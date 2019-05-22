package nerdvana.com.pointofsales.postlogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SettingsActivity;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.AddPaymentRequest;
import nerdvana.com.pointofsales.api_requests.AddProductToRequest;
import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_requests.BackOutGuestRequest;
import nerdvana.com.pointofsales.api_requests.BackupDatabaseRequest;
import nerdvana.com.pointofsales.api_requests.CancelOverTimeRequest;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.CheckOutRequest;
import nerdvana.com.pointofsales.api_requests.CheckShiftRequest;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_requests.FetchArOnlineRequest;
import nerdvana.com.pointofsales.api_requests.FetchCarRequest;
import nerdvana.com.pointofsales.api_requests.FetchCreditCardRequest;
import nerdvana.com.pointofsales.api_requests.FetchCurrencyExceptDefaultRequest;
import nerdvana.com.pointofsales.api_requests.FetchGuestTypeRequest;
import nerdvana.com.pointofsales.api_requests.FetchNationalityRequest;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingViaControlNoRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchZReadViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FocTransactionRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
import nerdvana.com.pointofsales.api_requests.SwitchRoomRequest;
import nerdvana.com.pointofsales.api_requests.WelcomeGuestRequest;
import nerdvana.com.pointofsales.api_requests.XReadRequest;
import nerdvana.com.pointofsales.api_requests.ZReadRequest;
import nerdvana.com.pointofsales.api_responses.AddPaymentResponse;
import nerdvana.com.pointofsales.api_responses.AddProductToResponse;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.BackOutGuestResponse;
import nerdvana.com.pointofsales.api_responses.CancelOverTimeResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckOutResponse;
import nerdvana.com.pointofsales.api_responses.CheckShiftResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchNationalityResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FocTransactionResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.RatePrice;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.api_responses.RoomRateMainViaId;
import nerdvana.com.pointofsales.api_responses.RoomRateSubViaId;
import nerdvana.com.pointofsales.api_responses.SwitchRoomResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.api_responses.WelcomeGuestResponse;
//import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.api_responses.XReadResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
import nerdvana.com.pointofsales.background.RetrieveCartItemsAsync;
import nerdvana.com.pointofsales.background.SaveTransactionAsync;
import nerdvana.com.pointofsales.custom.AlertYesNo;
import nerdvana.com.pointofsales.custom.SwipeToDeleteCallback;
import nerdvana.com.pointofsales.dialogs.CheckInDialog;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.dialogs.ConfirmWithRemarksDialog;
import nerdvana.com.pointofsales.dialogs.DiscountSelectionDialog;
import nerdvana.com.pointofsales.dialogs.FocDialog;
import nerdvana.com.pointofsales.dialogs.GuestInfoDialog;
import nerdvana.com.pointofsales.dialogs.OpenPriceDialog;
import nerdvana.com.pointofsales.dialogs.OrderSlipDialog;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.dialogs.RateDialog;
import nerdvana.com.pointofsales.dialogs.RemoveOtDialog;
import nerdvana.com.pointofsales.dialogs.ReprintXReadingDialog;
import nerdvana.com.pointofsales.dialogs.ReprintZReadingDialog;
import nerdvana.com.pointofsales.dialogs.SetupPrinterDialog;
import nerdvana.com.pointofsales.dialogs.SwitchRoomDialog;
import nerdvana.com.pointofsales.dialogs.TransactionsDialog;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.entities.PaymentEntity;
import nerdvana.com.pointofsales.entities.TransactionEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.interfaces.RetrieveCartItemContract;
import nerdvana.com.pointofsales.interfaces.SaveTransactionContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.ForVoidDiscountModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.GuestReceiptInfoModel;
import nerdvana.com.pointofsales.model.InfoModel;
import nerdvana.com.pointofsales.model.OrderSlipModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.SwitchRoomPrintModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeftFrameFragment extends Fragment implements AsyncContract, CheckoutItemsContract,
         SaveTransactionContract, RetrieveCartItemContract, View.OnClickListener {
    private AlertDialog blockerDialog;
    private CollectionDialog cutOffDialog;
    private Data data;
    private String employeeId = "";
    private List<ForVoidDiscountModel> forVoidDiscountModels = new ArrayList<>();

    private FetchRoomPendingResponse.Result fetchRoomPendingResult = null;


    GuestReceiptInfoModel guestReceiptInfoModel;

    boolean isAllowedToDiscard = true;

    private Integer overTimeValue = 0;

    private View view;
    private String currentRoomStatus = "";
    private int roomRateCount = 0;
    private double amountToPay = 0;

    private Double discountPayment = 0.00;
    private Double advancePayment = 0.00;

    List<Integer> roomRateCounter;
    private CheckInDialog checkInDialog;

//    private List<RoomRateMain> roomRateMainList;

    private TextView total;
    private TextView discount;
    private TextView deposit;
    private TextView subTotal;
    private TextView header;
    private TextView noItems;


    private List<FetchCurrencyExceptDefaultResponse.Result> currencyList;
    private List<FetchCreditCardResponse.Result> creditCardList;
    private List<FetchArOnlineResponse.Result> arOnlineList;
    private List<FetchNationalityResponse.Result> nationalityList;
    private List<CartItemsModel> cartItemList;
    private List<FetchPaymentResponse.Result> paymentTypeList;
    private List<PostedPaymentsModel> postedPaymentsList;
    private List<OrderSlipModel> orderSlipList;

    private RecyclerView listCheckoutItems;
    private RecyclerView listButtons;
    private SwipeRefreshLayout checkoutSwipe;

    private CategoryAdapter categoryAdapter;
    private CategoryAdapter subCategoryAdapter;

    private ConstraintLayout rootView;

    private UserModel userModel;
    private boolean isValid = false;

    private CheckoutAdapter checkoutAdapter;
    private ButtonsAdapter buttonsAdapter;

    private static SelectionContract selectionContract;

    private List<FetchRoomAreaResponse.Result> roomAreaList;
    private List<FetchUserResponse.Result> userList;
    private List<FetchCarResponse.Result> carList;
    private List<FetchVehicleResponse.Result> vehicleList;
    private List<FetchGuestTypeResponse.Result> guestTypeList;
    private RoomTableModel selectedRoom;

    private double totalBalance = 0;


    private List<RoomRateMain> roomRateMainListFromSwitch;
    private static MainActivity.Loading loadingInterface;
    public static LeftFrameFragment newInstance(SelectionContract selectionContract, MainActivity.Loading loadingInterface) {
        LeftFrameFragment.selectionContract = selectionContract;
        LeftFrameFragment leftFrameFragment = new LeftFrameFragment();
        LeftFrameFragment.loadingInterface = loadingInterface;
        return leftFrameFragment;
    }

    private void initializeViews(View view) {
        roomRateCounter = new ArrayList<>();
        total = view.findViewById(R.id.totalValue);
        discount = view.findViewById(R.id.discountValue);
        deposit = view.findViewById(R.id.depositValue);
        subTotal = view.findViewById(R.id.subTotalValue);
        checkoutSwipe = view.findViewById(R.id.checkoutSwipe);
        listCheckoutItems = view.findViewById(R.id.listCheckoutItems);
        listButtons = view.findViewById(R.id.listButtons);

        noItems = view.findViewById(R.id.notItems);
        header = view.findViewById(R.id.header);
        header.setOnClickListener(this);

        roomAreaList = new ArrayList<>();
        rootView = view.findViewById(R.id.rootView);
        userList = new ArrayList<>();
        cartItemList = new ArrayList<>();
        carList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        guestTypeList = new ArrayList<>();
//        roomRateMainList = new ArrayList<>();
        cartItemList = new ArrayList<>();
        paymentTypeList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        roomRateMainListFromSwitch = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_left_frame, container, false);

        data = new Data() {
            @Override
            public void refresh() {
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                    } else {
                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                    }
                }
            }
        };
        forVoidDiscountModels = new ArrayList<>();
        fetchArOnlineRequest();
        fetchNationalityRequest();
        fetchCreditCardRequest();
        fetchCurrencyExceptDefaultRequest();


        initializeViews(view);
        setProductAdapter();
//        setButtonsAdapter();


//        printReceiptFromCheckout("VCHI-2019-00000020",
//                "TEST",
//                "STANDARD");


//        printReceiptFromCheckout("VCHI-2019-00000078", "XXX", "DDD");


        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
            isValid = true;
        }

        TypeToken<List<FetchUserResponse.Result>> token = new TypeToken<List<FetchUserResponse.Result>>() {};
        userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_JSON), token.getType());


        TypeToken<List<FetchRoomAreaResponse.Result>> areaToken = new TypeToken<List<FetchRoomAreaResponse.Result>>() {};
        roomAreaList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.ROOM_AREA_JSON), areaToken.getType());






        defaultView();
//        if (!TextUtils.isEmpty(selectedRoomNumber())) {
//            //reload data from selected table && set views
////            retrieveCartItems();
//            setView(selectedRoomNumber(), sele);
////            computeFromDb();
//        }

//        fetchXReadViaIdRequest("6");
//        fetchZReadViaIdRequest("1");

        fetchCarRequest();
        fetchVehicleRequest();
        fetchGuestTypeRequest();
        fetchPaymentTypeRequest();


        checkoutSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (selectedRoom != null) {

                    if (selectedRoom.isTakeOut()) {
                        fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                    } else {
                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                    }
                } else {
                    checkoutSwipe.setRefreshing(false);
                }
            }
        });
        return view;
    }

    private void setProductAdapter() {

        checkoutAdapter = new CheckoutAdapter(cartItemList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        listCheckoutItems.setLayoutManager(linearLayoutManager);
        listCheckoutItems.setAdapter(checkoutAdapter);
        enableSwipeToDeleteAndUndo();

//        new CheckoutItemsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        checkoutAdapter.addItems(productsModelList);
    }

//    private void setButtonsAdapter() {
//        buttonsAdapter = new ButtonsAdapter(new ArrayList<ButtonsModel>(), this);
//        listButtons.setLayoutManager(new GridLayoutManager(getContext(),2,  GridLayoutManager.HORIZONTAL, false));
//        listButtons.setAdapter(buttonsAdapter);
//        new ButtonsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//    }




    @Override
    public void doneLoading(List list, String isFor) {

        switch (isFor) {
//            case "checkout":
//                noItems.setVisibility(View.GONE);
////                checkoutAdapter.addItems(list);
//
//                cartItemList.add(((ProductsModel)item));
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

        employeeId = "";

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

            total.setText("0.00");
            discount.setText("0.00");
            deposit.setText("0.00");
//            tax.setText("0.00");
            subTotal.setText("0.00");
            selectedRoom = null;

        }
    }

    private void setView(String input, String roomType) {

        if (isValid) { //means userModel is not null
//            noItems.setVisibility(View.VISIBLE);
            switch (userModel.getSystemType().toLowerCase()) {
                case "room":
                    header.setText(String.format("%s %s (%s)", "Room", input, roomType));
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

    //branchcode-year-autoincrement 8 digit

    @Subscribe
    public void notify(FragmentNotifierModel selectedRoom) {
        if (selectedRoom.getSelectedRoom().isTakeOut()) {
            //takeout logic
//            selectedRoom.getSelectedRoom().getControlNo()
            header.setText(String.format("%s", selectedRoom.getSelectedRoom().getControlNo()));
//            setView(selectedRoom.getSelectedRoom().getControlNo());
            fetchOrderPendingViaControlNo(selectedRoom.getSelectedRoom().getControlNo());
        } else {
            //room logic
            currentRoomStatus = selectedRoom.getSelectedRoom().getStatus();
            setView(selectedRoom.getSelectedRoom().getName(), selectedRoom.getSelectedRoom().getRoomType());
            fetchRoomPending(String.valueOf(selectedRoom.getSelectedRoom().getRoomId()));
        }

        this.selectedRoom = selectedRoom.getSelectedRoom();

    }

    private void retrieveCartItems() {
        if (getTableRecord().size() > 0) {
            new RetrieveCartItemsAsync(
                    getTableRecord().get(0).getTransactionId(),
                    this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            cartItemList.clear();
            cartItemList = new ArrayList<>();

            checkoutAdapter = new CheckoutAdapter(cartItemList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//            linearLayoutManager.setReverseLayout(true);
//            linearLayoutManager.setStackFromEnd(true);
            listCheckoutItems.setLayoutManager(linearLayoutManager);
            listCheckoutItems.setAdapter(checkoutAdapter);

            total.setText("0.00");
        }
    }

    private List<TransactionEntity> getTableRecord() {
        return TransactionEntity.
                findWithQuery(
                        TransactionEntity.class,
                        SqlQueries.GET_PENDING_TABLE_ORDER,
                        selectedRoomNumber());
    }

    private List<CartEntity> getCartRecord(String transactionId) {
        return CartEntity.
                findWithQuery(
                        CartEntity.class,
                        SqlQueries.GET_CART_ITEMS,
                        transactionId
                );
    }

    @Override
    public void onPause() {
        super.onPause();

//        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        BusProvider.getInstance().register(this);
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

    @Subscribe
    public void productsClicked(ProductsModel productsModel) {
        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                cartItemList.add(new CartItemsModel(
                        "",
                        selectedRoom.getRoomId(),
                        productsModel.getProductId(),
                        0,
                        0,
                        0,
                        productsModel.getShortName(),
                        true,
                        productsModel.getPrice(),
                        productsModel.getProductId(),
                        productsModel.getQty(),
                        false,
                        productsModel.getMarkUp(),
                        productsModel.getIsPriceChanged(),
                        productsModel.getUnitPrice(),
                        false,
                        "",
                        false,
                        "to"
                ));
                checkoutAdapter.notifyDataSetChanged();
//                listCheckoutItems.scrollToPosition(checkoutAdapter.getItemCount() - 1);
            } else {

                if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                        currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA) ||
                        currentRoomStatus.equalsIgnoreCase("32") ||
                        currentRoomStatus.equalsIgnoreCase("4")) {

                    cartItemList.add(roomRateCounter.size(), new CartItemsModel(
                            "",
                            selectedRoom.getRoomId(),
                            productsModel.getProductId(),
                            0,
                            0,
                            0,
                            productsModel.getShortName(),
                            true,
                            productsModel.getPrice(),
                            productsModel.getProductId(),
                            productsModel.getQty(),
                            false,
                            productsModel.getMarkUp(),
                            productsModel.getIsPriceChanged(),
                            productsModel.getUnitPrice(),
                            false,
                            "",
                            false,
                            "room"
                    ));

                    checkoutAdapter.notifyDataSetChanged();
//                    listCheckoutItems.scrollToPosition(checkoutAdapter.getItemCount() - 1);

                } else {
                    Toast.makeText(getContext(), "Room not occupied", Toast.LENGTH_SHORT).show();
                }
//                                BusProvider.getInstance().post(new AddRoomPriceRequest(model, String.valueOf(selectedRoom.getRoomId())));
            }
        } else {
            Toast.makeText(getContext(), "Please select a room first", Toast.LENGTH_SHORT).show();
        }
        if (noItems.getVisibility() == View.VISIBLE) noItems.setVisibility(View.GONE);




//        new CheckoutItemsAsync(
//                "",
//                productsModel,
//                ).execute();
//        new CheckoutItemsAsync(this,
//                cartItemList,
//                productsModel,
//                getContext(),
//                getTableRecord().size() < 1 ? "" : getTableRecord().get(0).getTransactionId(),
//                5,
//                selectedRoomNumber())
//                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void itemAdded(ProductsModel itemAdded) {
//        retrieveCartItems();
//        listCheckoutItems.scrollToPosition(checkoutAdapter.getItemCount() - 1);
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
    public void itemSelected(CartItemsModel itemSelected, int position) {
        itemSelected.setSelected(itemSelected.isSelected() ? false : true);
        checkoutAdapter.notifyItemChanged(position);
    }

    @Override
    public void itemLongClicked(final CartItemsModel itemSelected, final int position, View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_checkout_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.voidItem:
                        doVoidFunction();
                        break;
                    case R.id.changePrice:
                        final OpenPriceDialog openPriceDialog = new OpenPriceDialog(getActivity(), itemSelected, position) {
                            @Override
                            public void openPriceChangeSuccess(int quantity, Double newPrice, int position) {

                                if (cartItemList.get(position).isPosted()) {
                                    cartItemList.get(position).setPosted(false);
                                    cartItemList.get(position).setForVoid(true);
                                }
                                if (newPrice != 0) {
                                    cartItemList.get(position).setUnitPrice(newPrice);
                                }

                                if (quantity != 0) {
                                    cartItemList.get(position).setQuantity(quantity);
                                }

                                cartItemList.get(position).setIsPriceChanged(1);
                                if (checkoutAdapter != null) {
                                    checkoutAdapter.notifyItemChanged(position);
                                }

                                dismiss();
                            }
                        };

                        openPriceDialog.show();
//                        if (!itemSelected.isPosted()) {
//                            if (!openPriceDialog.isShowing()) {
//                                openPriceDialog.show();
//                                Window window = openPriceDialog.getWindow();
//                                window.setLayout((Utils.getDeviceWidth(getContext()) / 2), ViewGroup.LayoutParams.WRAP_CONTENT);
//                            }
//                        } else {
//                            Toast.makeText(getContext(), "Cannot change price, already posted", Toast.LENGTH_SHORT).show();
//                        }
                        break;
                }
                return true;
            }
        });

        PopupMenu roomPopupMenu = new PopupMenu(getActivity(), view);
        roomPopupMenu.getMenuInflater().inflate(R.menu.menu_room_update, roomPopupMenu.getMenu());
        roomPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.changeRoomPrice:

                    break;
                }
                return true;
            }
        });

        if (itemSelected.getType().equalsIgnoreCase("ot")) {

            PopupMenu cancelOtMenu = new PopupMenu(getActivity(), view);
            cancelOtMenu.getMenuInflater().inflate(R.menu.menu_cancel_ot, cancelOtMenu.getMenu());
            cancelOtMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.cancelOt:
                            Toast.makeText(getContext(), "cnacel ot please", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                    }
            });

            cancelOtMenu.show();
        } else {
            if (itemSelected.isProduct()) {
                popupMenu.show();
            }  else {
                roomPopupMenu.show();
            }
        }


    }

    private void changeShiftFunction() {
        Toast.makeText(getContext(), "CHANGE SHIFT", Toast.LENGTH_SHORT).show();
    }

    private void doFocFunction() {

        if (selectedRoom != null) {
            if (!employeeId.isEmpty()) {
                PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        if (selectedRoom.isTakeOut()) {
                            BusProvider.getInstance().post(new FocTransactionRequest(
                                    "",
                                    selectedRoom.getControlNo(),
                                    employeeId
                            ));
                        } else {
                            BusProvider.getInstance().post(new FocTransactionRequest(
                                    String.valueOf(selectedRoom.getRoomId()),
                                    "",
                                    employeeId
                            ));
                        }

                    }

                    @Override
                    public void passwordFailed() {

                    }
                };

                if (!passwordDialog.isShowing()) passwordDialog.show();

            } else {
                Utils.showDialogMessage(getActivity(), "Not an employee", "Information");
            }
        } else {
            Utils.showDialogMessage(getActivity(), "No room selected", "Information");
        }
    }

    private boolean isAllowedToTransact() {
        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }


    private boolean canTransact() {

        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("please execute end of day")) {
            Toast.makeText(getContext(), "Please execute end of day", Toast.LENGTH_SHORT).show();
            return false;
        } else if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("NOT_ALLOW") ||
                SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("") ||
                SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("please execute cutoff")) {
            Toast.makeText(getContext(), "Please execute cutoff", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Subscribe
    public void clickedButton(ButtonsModel clickedItem) {


        switch (clickedItem.getId()) {
            case 129: //setting for app, new activity

                Intent settingIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingIntent);

                break;
            case 128: //backup
                //check main activity for function
                break;
            case 127://REPRINT Z READ
                PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        ReprintZReadingDialog reprintZReadingDialog = new ReprintZReadingDialog(getActivity());
                        reprintZReadingDialog.show();
                    }

                    @Override
                    public void passwordFailed() {

                    }
                };
                passwordDialog.show();

                break;
            case 126: //FOC
                if (canTransact()) doFocFunction();
                break;
            case 124: //BACKOUT
                if (canTransact()) doBackOutGuestFunction();
                break;
            case 123: //REPRINT X/Z READING
                PasswordDialog passwordDialogs = new PasswordDialog(getActivity()) {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        ReprintXReadingDialog reprintXReadingDialog = new ReprintXReadingDialog(getActivity());
                        reprintXReadingDialog.show();
                    }

                    @Override
                    public void passwordFailed() {

                    }
                };
                passwordDialogs.show();

                break;
            case 9999: //rooms
                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            BusProvider.getInstance().post(new ButtonsModel(999,"ROOMS", "",1));
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    BusProvider.getInstance().post(new ButtonsModel(999,"ROOMS", "",1));
                }
                break;
            case 9988: //take order
                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            BusProvider.getInstance().post(new ButtonsModel(998,"TAKE ORDER", "",2));
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    BusProvider.getInstance().post(new ButtonsModel(998,"TAKE ORDER", "",2));
                }
                break;
            case 122: //CANCEL OVERTIME

                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {

                                cancelOverTime();
                            }


                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();
                    } else {
                        cancelOverTime();
                    }
                }
                break;
            case 121: //CASH AND RECONCILE
                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            cashNReconcile();
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    cashNReconcile();
                }
                break;
            case 120: //Z READ, END OF DAY
                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            zReadRequest();
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    zReadRequest();
                }
                break;
            case 119: //X READ, SHIFT CUT OFF


                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            xReadRequest();
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    xReadRequest();
                }




                break;
            case 118:// SAFEKEEPING

                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {
                                doSafeKeepFunction();
                            }

                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();
                    } else {
                        doSafeKeepFunction();
                    }
                }



                break;
            case 116: //cancel selected room / TO
                defaultView();
                clearCartItems();
                break;
            case 115://DISCOUNT
                if (canTransact()) {
                    if (selectedRoom != null) {
                        if (hasUnpostedItems()) {
                            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                                @Override
                                public void yesClicked() {
                                    doDiscountFunction();
                                }

                                @Override
                                public void noClicked() {

                                }
                            };
                            alertYesNo.show();
                        } else {
                            doDiscountFunction();
                        }


                    } else {
                        Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                    }
                }


                break;
            case 114://SWITCH ROOM
                if (canTransact()) {
                    if (selectedRoom != null) {
                        if (hasUnpostedItems()) {
                            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                                @Override
                                public void yesClicked() {
                                    doSwitchRoomFunction();
                                }

                                @Override
                                public void noClicked() {

                                }
                            };
                            alertYesNo.show();
                        } else {
                            doSwitchRoomFunction();
                        }


                    } else {
                        Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                    }
                }
                break;
            case 113://POST VOID

                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {
                                doPostVoidFunction();
                            }

                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();
                    } else {
                        doPostVoidFunction();
                    }
                }



                break;
            case 112://VIEW RECEIPT
                TransactionsDialog transactionsDialog = new TransactionsDialog(getActivity(), true, getActivity()) {
                    @Override
                    public void postVoidSuccess() {
                        defaultView();
                        clearCartItems();
                    }

                    @Override
                    public void postVoidPrint(String jsonData) {
                        ViewReceiptResponse.Result toListPV = GsonHelper.getGson().fromJson(jsonData, ViewReceiptResponse.Result.class)
                                ;

                        if (toListPV != null) {
                            BusProvider.getInstance().post(new PrintModel("",
                                    toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomNo() : "TAKEOUT",
                                    "POST_VOID",
                                    jsonData,
                                    toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomType() : ""));
                        }


                        defaultView();
                        clearCartItems();
                    }
                };

                if (!transactionsDialog.isShowing()) {
                    transactionsDialog.show();
                }


                break;
            case 111://GUEST INFO
                if (canTransact()) {
                    GuestInfoDialog guestInfoDialog = new GuestInfoDialog(getActivity(), fetchRoomPendingResult, getActivity()) {
                        @Override
                        public void refresh() {
                            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                        }

                        @Override
                        public void refresh(String jsonString) {
                            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                            BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "CHANGE_WAKE_UP_CALL", jsonString));

                        }
                    };

                    if (selectedRoom != null) {
                        if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                                currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA)) {
                            guestInfoDialog.show();
                        } else {
                            Utils.showDialogMessage(getActivity(), "No guest info yet", "Information");
                        }
                    } else {
                        Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                    }
                }

                break;
            case 110:// SETUP PRINTER
                SetupPrinterDialog setupPrinterDialog = new SetupPrinterDialog(getActivity()) {
                    @Override
                    public void printerConnected() {
                        loadPrinter();
                    }
                };
                if (!setupPrinterDialog.isShowing()) {
                    setupPrinterDialog.show();
                }
                break;

//            case 109: //FOC not being used
//                FocDialog focDialog = new FocDialog(getActivity(), postedPaymentsList) {
//                    @Override
//                    public void focSuccess() {
//                        dismiss();
//                        Toast.makeText(getContext(), "SUCC FOC", Toast.LENGTH_SHORT).show();
//                        focTransaction();
//                    }
//                };
//                if (selectedRoom != null) {
//                    if (selectedRoom.getStatus().equalsIgnoreCase("17")) {
//
//                        if (advancePayment != 0 || discountPayment != 0) {
//                            Utils.showDialogMessage(getActivity(), "Please remove advance payment / discount", "Information");
//                        } else {
//                            if (!focDialog.isShowing()) focDialog.show();
//                        }
//
//
//                    } else {
//                        Utils.showDialogMessage(getActivity(), "Please SOA room first", "Information");
//                    }
//                } else {
//                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
//                }
//                break;
//                //
            case 108: //show order slip form
                String roomNumber = "";
                if (selectedRoom != null) {
                    if (!selectedRoom.isTakeOut()) {
                        roomNumber = selectedRoom.getName();
                    }
                }
                OrderSlipDialog orderSlipDialog = new OrderSlipDialog(getActivity(), orderSlipList, roomNumber);
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        if (!orderSlipDialog.isShowing()) orderSlipDialog.show();
                    } else {
                        if (selectedRoom.getStatus().equalsIgnoreCase("2") || selectedRoom.getStatus().equalsIgnoreCase("17")) {
                            if (!orderSlipDialog.isShowing()) orderSlipDialog.show();
                        } else {
                            Utils.showDialogMessage(getActivity(), "Room not yet occupied / SOA", "Information");
                        }
                    }

                } else {

                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                }
                break;
            case 107: //CHECK IN - WAITING GUEST (DIRTY / RC)
                if (selectedRoom != null) {
                    if (selectedRoom.getStatus().equalsIgnoreCase("32") ||
                            selectedRoom.getStatus().equalsIgnoreCase("4") ||
                            selectedRoom.getStatus().equalsIgnoreCase("59")) {
                        showCheckInDialog();
                    } else {
                        Utils.showDialogMessage(getActivity(), "Room already checked-in", "Information");
                    }
                } else {
                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                }

                break;
            case 106: //PRINT SOA
                if (canTransact()) {
                    if (selectedRoom != null) {
                        if (hasUnpostedItems()) {
                            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                                @Override
                                public void yesClicked() {
                                    doSoaFunction();
                                }

                                @Override
                                public void noClicked() {

                                }
                            };
                            alertYesNo.show();
                        } else {
                            doSoaFunction();
                        }
                    } else {
                        Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                    }
                }



                break;
            case 105: //CHECKOUT
                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {
                                doCheckoutFunction();
                            }

                            @Override
                            public void noClicked() {

                            }
                        };

                        alertYesNo.show();
                    } else {
                        doCheckoutFunction();
                    }
                }



                break;
            case 103: //ADD RATE TO EXISTING TRANSACTION
                if (selectedRoom != null) {
                    if (hasUnpostedItems()) {
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {
                                doAddRateFunction();
                            }

                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();

                    } else {
                        doAddRateFunction();
                    }
                } else {
                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                }

                break;
            case 100: //SAVE TRANSACTION:
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
//                                BusProvider.getInstance().post(new AddProductToRequest(model, String.valueOf(selectedRoom.getRoomId())));
                        final ArrayList<AddRateProductModel> model = new ArrayList<>();
                        final ArrayList<VoidProductModel> voidModel = new ArrayList<>();
//                        model.add(new AddRateProductModel(productId, roomRatePriceId, quantity, tax));
                        for (CartItemsModel cim : cartItemList) {
                            if (!cim.isPosted() && cim.isProduct()) {
                                model.add(new AddRateProductModel(
                                        String.valueOf(cim.getProductId()),
                                        "0",
                                        String.valueOf(cim.getQuantity()),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                        String.valueOf(cim.getUnitPrice()),
                                        cim.getIsPriceChanged(),
                                        cim.getName()
                                ));
                            }

                            if (cim.isForVoid()) {
                                voidModel.add(new VoidProductModel(
                                        cim.getPostId(),
                                        cim.getName(),
                                        String.valueOf(cim.getAmount()),
                                        String.valueOf(cim.getQuantity())
                                ));
                            }
                        }
                        ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                            @Override
                            public void save(String remarks) {
                                BusProvider.getInstance().post(new PrintModel("", "TAKEOUT", "FO", GsonHelper.getGson().toJson(model)));
                                BusProvider.getInstance().post(new AddProductToRequest(
                                        model, String.valueOf(selectedRoom.getRoomId()),
                                        String.valueOf(selectedRoom.getAreaId()),
                                        selectedRoom.getControlNo(),
                                        voidModel, remarks));
                                showLoading();
                            }
                        };

                        if (model.size() == 0 && voidModel.size() == 0) {
                            Utils.showDialogMessage(getActivity(), "Please select item/s to order", "Information");
                        } else {
                            confirmWithRemarksDialog.show();
                        }
                    } else {
                        if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                                currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA) ||
                                selectedRoom.getStatus().equalsIgnoreCase("4") ||
                                selectedRoom.getStatus().equalsIgnoreCase("32") ||
                                selectedRoom.getStatus().equalsIgnoreCase("59")) {
                            final ArrayList<AddRateProductModel> model = new ArrayList<>();
                            for (CartItemsModel cim : cartItemList) {
                                if (!cim.isPosted() && cim.isProduct()) {
                                    model.add(new AddRateProductModel(
                                            String.valueOf(cim.getProductId()),
                                            "0",
                                            String.valueOf(cim.getQuantity()),
                                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                            String.valueOf(cim.getUnitPrice()),
                                            cim.getIsPriceChanged(),
                                            cim.getName()
                                    ));
                                }
                            }


                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {
                                    BusProvider.getInstance().post(new PrintModel("", "ROOM# "+ selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model)));

                                    BusProvider.getInstance().post(new AddRoomPriceRequest(
                                            model,
                                            String.valueOf(selectedRoom.getRoomId()),
                                            new ArrayList<VoidProductModel>(),
                                            remarks,
                                            ""));
                                    showLoading();
                                }
                            };

                            if (model.size() == 0) {
                                Utils.showDialogMessage(getActivity(), "Please select item/s to order", "Information");
                            } else {
                                confirmWithRemarksDialog.show();
                            }

                        }

                    }


                }
                break;
            case 101: //VOID
                if (canTransact()) {
                    if (selectedRoom != null) {
                        if (hasUnpostedItems()) {
                            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                                @Override
                                public void yesClicked() {
                                    doVoidFunction();
                                }

                                @Override
                                public void noClicked() {

                                }
                            };

                            alertYesNo.show();
                        } else {
                            doVoidFunction();
                        }



                    } else {

                        Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                    }

                }

                break;
            case 102: //ADVANCE PAYMENT

                if (selectedRoom != null) {

                    if (hasUnpostedItems()) {
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {

                            @Override
                            public void yesClicked() {

                                doAdvancePaymentFunction();

                            }

                            @Override
                            public void noClicked() {

                            }
                        };

                        alertYesNo.show();

                    } else {

                        doAdvancePaymentFunction();
                    }



                } else {
                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                }

                break;
        }
    }

    private void doBackOutGuestFunction() {

        if (selectedRoom != null) {
            if (hasUnpostedItems()) {
                AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                    @Override
                    public void yesClicked() {

                        final ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                            @Override
                            public void save(final String remarks) {

                                PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                                    @Override
                                    public void passwordSuccess(String employeeId, final String employeeName) {

                                        if (!selectedRoom.isTakeOut()) {
                                            BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest(String.valueOf(selectedRoom.getRoomId()), remarks, "", employeeId);
                                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                            Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                            request.enqueue(new Callback<BackOutGuestResponse>() {
                                                @Override
                                                public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {
                                                    BusProvider.getInstance().post(new PrintModel("",
                                                            selectedRoom.getName(),
                                                            "BACKOUT",
                                                            GsonHelper.getGson().toJson(selectedRoom),
                                                            selectedRoom.getRoomType(),
                                                            employeeName,
                                                            remarks));
                                                    defaultView();
                                                    clearCartItems();
                                                    endLoading();
                                                }

                                                @Override
                                                public void onFailure(Call<BackOutGuestResponse> call, Throwable t) {
                                                    endLoading();
                                                }
                                            });
                                        } else {
                                            BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest("", remarks, selectedRoom.getControlNo(), employeeId);
                                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                            Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                            request.enqueue(new Callback<BackOutGuestResponse>() {
                                                @Override
                                                public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {
                                                    BusProvider.getInstance().post(new PrintModel("",
                                                            selectedRoom.getName(),
                                                            "BACKOUT",
                                                            GsonHelper.getGson().toJson(selectedRoom),
                                                            selectedRoom.getRoomType(),employeeName,
                                                            remarks));
                                                    defaultView();
                                                    clearCartItems();
                                                    endLoading();
                                                }

                                                @Override
                                                public void onFailure(Call<BackOutGuestResponse> call, Throwable t) {
                                                    endLoading();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void passwordFailed() {

                                    }
                                };
                                if (!passwordDialog.isShowing()) passwordDialog.show();


                            }
                        };
                        if (!confirmWithRemarksDialog.isShowing()) confirmWithRemarksDialog.show();
                        showLoading();
                    }

                    @Override
                    public void noClicked() {

                    }
                };
                alertYesNo.show();
            } else {
                ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                    @Override
                    public void save(final String remarks) {
                        PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                            @Override
                            public void passwordSuccess(String employeeId, final String employeeName) {

                                if (!selectedRoom.isTakeOut()) {
                                    BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest(String.valueOf(selectedRoom.getRoomId()), remarks, "", employeeId);
                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                    request.enqueue(new Callback<BackOutGuestResponse>() {
                                        @Override
                                        public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {
                                            BusProvider.getInstance().post(new PrintModel("",
                                                    selectedRoom.getName(),
                                                    "BACKOUT",
                                                    GsonHelper.getGson().toJson(selectedRoom),
                                                    selectedRoom.getRoomType(),employeeName,
                                                    remarks));
                                            defaultView();
                                            clearCartItems();
                                            endLoading();
                                        }

                                        @Override
                                        public void onFailure(Call<BackOutGuestResponse> call, Throwable t) {
                                            endLoading();
                                        }
                                    });
                                } else {
                                    BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest("", remarks, selectedRoom.getControlNo(), employeeId);
                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                    request.enqueue(new Callback<BackOutGuestResponse>() {
                                        @Override
                                        public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {
                                            BusProvider.getInstance().post(new PrintModel("",
                                                    selectedRoom.getName(),
                                                    "BACKOUT",
                                                    GsonHelper.getGson().toJson(selectedRoom),
                                                    selectedRoom.getRoomType(),employeeName,
                                                    remarks));
                                            defaultView();
                                            clearCartItems();
                                            endLoading();
                                        }

                                        @Override
                                        public void onFailure(Call<BackOutGuestResponse> call, Throwable t) {
                                            endLoading();
                                        }
                                    });

//                                    Utils.showDialogMessage(getActivity(), "Transaction for take out only", "Information");
                                }


                            }

                            @Override
                            public void passwordFailed() {

                            }
                        };
                        if (!passwordDialog.isShowing()) passwordDialog.show();
                    }
                };
                if (!confirmWithRemarksDialog.isShowing()) confirmWithRemarksDialog.show();
            }




        } else {
            Utils.showDialogMessage(getActivity(), "No room selected", "Information");
        }





    }

    private void doSafeKeepFunction() {
        CollectionDialog safeKeepingDialog = new CollectionDialog(getActivity(), "SAFEKEEPING", false) {
            @Override
            public void printCashRecoData(String cashNRecoData) {

            }
        };
        if (!safeKeepingDialog.isShowing()) safeKeepingDialog.show();
    }

    private void doPostVoidFunction() {
        TransactionsDialog postVoid = new TransactionsDialog(getActivity(), false, getActivity()) {
            @Override
            public void postVoidSuccess() {
                defaultView();
                clearCartItems();
            }

            @Override
            public void postVoidPrint(String jsonData) {
                ViewReceiptResponse.Result toListPV = GsonHelper.getGson().fromJson(jsonData, ViewReceiptResponse.Result.class)
                        ;

                if (toListPV != null) {
                    BusProvider.getInstance().post(new PrintModel("",
                            toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomNo() : "TAKEOUT",
                            "POST_VOID",
                            jsonData,
                            toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomType() : ""));
                }


                defaultView();
                clearCartItems();
            }
        };

        if (!postVoid.isShowing()) {
            postVoid.show();
        }
    }

    private void doDiscountFunction() {
        if (selectedRoom.isTakeOut()) {
            DiscountSelectionDialog discountSelectionDialog =
                    new DiscountSelectionDialog(getContext(),
                            getActivity(),
                            fetchRoomPendingResult,
                            selectedRoom.getControlNo(),
                            "",
                            forVoidDiscountModels,
                            data) {
                        @Override
                        public void fetchPending(String type) {

                            if (selectedRoom != null) {
                                if (selectedRoom.isTakeOut()) {
                                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                } else {
                                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                }
                            }
//                            if (type.equalsIgnoreCase("to")) {
//
//                            } else {
//
//                            }
                        }
                    };

            if (!discountSelectionDialog.isShowing()) discountSelectionDialog.show();
        } else {

            DiscountSelectionDialog discountSelectionDialog =
                    new DiscountSelectionDialog(getContext(),
                            getActivity(),
                            fetchRoomPendingResult,
                            "",
                            String.valueOf(selectedRoom.getRoomId()),
                            forVoidDiscountModels,
                            data) {
                        @Override
                        public void fetchPending(String type) {

                            if (selectedRoom != null) {
                                if (selectedRoom.isTakeOut()) {
                                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                } else {
                                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                }
                            }


//                            if (type.equalsIgnoreCase("to")) {
//                                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
//                            } else {
//                                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
//                            }

                        }
                    };

            if (!discountSelectionDialog.isShowing()) discountSelectionDialog.show();
        }
    }

    private void doSwitchRoomFunction() {
        SwitchRoomDialog switchRoomDialog = new SwitchRoomDialog(getActivity(), selectedRoom.getName()) {
            @Override
            public void switchRoomConfirm(final String roomRatePriceId, final String qty,
                                          final String price, final String rateName,
                                          final String roomId, List<RoomRateMain> roomRateMainList) {
                ConfirmWithRemarksDialog cfrmDialog = new ConfirmWithRemarksDialog(getActivity()) {
                    @Override
                    public void save(final String remarks) {
//                                final ArrayList<AddRateProductModel> model = new ArrayList<>();
                        final ArrayList<VoidProductModel> voidModel = new ArrayList<>();

                        for (CartItemsModel cim : cartItemList) {
                            if (!cim.isProduct()) {
                                voidModel.add(new VoidProductModel(
                                        cim.getPostId(),
                                        cim.getName(),
                                        String.valueOf(cim.getAmount()),
                                        String.valueOf(cim.getQuantity())
                                ));
                            }
                        }

                        final PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {

                            @Override
                            public void passwordSuccess(String employeeId, String employeeName) {

                                BusProvider.getInstance().post(
                                        new SwitchRoomRequest(
                                                String.valueOf(selectedRoom.getRoomId()),
                                                roomRatePriceId,
                                                remarks,
                                                roomId,
                                                employeeId,
                                                voidModel
                                        )
                                );


                            }

                            @Override
                            public void passwordFailed() {

                            }
                        };

                        passwordDialog.show();






                    }
                };

                if (!cfrmDialog.isShowing()) cfrmDialog.show();
            }
        };

        if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA)) {
            if (!switchRoomDialog.isShowing()) {
                switchRoomDialog.show();
            }
        } else {
            Utils.showDialogMessage(getActivity(), "Room not yet checked-in", "Information");
        }
    }

    private void doAddRateFunction() {
        if (!selectedRoom.isTakeOut()) {
            if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                    currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA)) {
                final RateDialog rateDialog = new RateDialog(getContext(), selectedRoom.getPrice()) {
                    @Override
                    public void rateChangeSuccess(RoomRateMain selectedRate, String qty) {
                        addRateRequest("0",
                                String.valueOf(selectedRate.getRoomRatePriceId()),
                                qty,
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                String.valueOf(selectedRoom.getRoomId()),
                                String.valueOf(selectedRate.getRatePrice().getAmount()),
                                selectedRate.getRatePrice().getRoomRate().getRoomRate());
                        this.dismiss();
                    }
                };
                rateDialog.show();
            } else {

                Utils.showDialogMessage(getActivity(), "Room not yet occupied", "Information");
            }
        } else {

            Utils.showDialogMessage(getActivity(), "Adding rate feature is for rooms only", "Information");
        }

    }

    private void doAdvancePaymentFunction() {
        PaymentDialog paymentDialog = new PaymentDialog(getActivity(),
                paymentTypeList,
                false,
                postedPaymentsList,
                totalBalance,
                currencyList,
                creditCardList,
                arOnlineList,
                discountPayment,
                selectedRoom.getControlNo(),
                guestReceiptInfoModel) {
            @Override
            public void removePaymentSuccess() {
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                    } else {
                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                    }
                }
            }

            @Override
            public void paymentSuccess(List<PostedPaymentsModel> postedPaymentLit, String roomBoy) {
                List<PostedPaymentsModel> paymentsToPost = new ArrayList<>();
                for (PostedPaymentsModel ppm : postedPaymentLit) {
                    if (!ppm.isIs_posted()) {
                        paymentsToPost.add(ppm);
                    }
                }

                if (paymentsToPost.size() > 0) {
                    if (selectedRoom.isTakeOut()) {
                        postAdvancePayment(paymentsToPost, "", selectedRoom.getControlNo());
                    } else {
                        postAdvancePayment(paymentsToPost, String.valueOf(selectedRoom.getRoomId()), "");
                    }


                } else {
                    Utils.showDialogMessage(getActivity(), "No payment to post", "Information");
                }

                dismiss();
            }

            @Override
            public void paymentFailed() {

            }
        };


        if (selectedRoom.isTakeOut()) {
            if (paymentTypeList.size() > 0) {
                paymentDialog.show();
            } else {
                Toast.makeText(getContext(), "No payment type found", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (currentRoomStatus.equalsIgnoreCase("2") ||
                    currentRoomStatus.equalsIgnoreCase("17")) {
                if (paymentTypeList.size() > 0) {
                    paymentDialog.show();
                } else {

                    Utils.showDialogMessage(getActivity(), "No payment type found, please re-open the paplication", "Information");
                }
            } else {
                Utils.showDialogMessage(getActivity(), "Room not yet occupied, cant accept payment", "Information");
            }
        }

    }

    private void doVoidFunction() {
        if (cartItemList.size() < 1) {
            Utils.showDialogMessage(getActivity(), "No items to void", "Information");
        } else {

            final ArrayList<VoidProductModel> model = new ArrayList<>();

            for (CartItemsModel cim : cartItemList) {
                if (cim.isSelected()) {
                    model.add(new VoidProductModel(
                            cim.getPostId(),
                            cim.getName(),
                            String.valueOf(cim.getAmount()),
                            String.valueOf(cim.getQuantity())
                    ));
                }
            }

            final PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                @Override
                public void passwordSuccess(final String employeeId, String employeeName) {


                    if (selectedRoom != null) {

                        if (selectedRoom.isTakeOut()) {
                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {
                                    BusProvider.getInstance().post(new PrintModel("", "TAKEOUT "+ selectedRoom.getName(), "VOID", GsonHelper.getGson().toJson(model)));
                                    BusProvider.getInstance().post(new AddProductToRequest(new ArrayList<AddRateProductModel>(), String.valueOf(selectedRoom.getRoomId()),
                                            String.valueOf(selectedRoom.getAreaId()),
                                            selectedRoom.getControlNo(),
                                            model,
                                            remarks));

                                    showLoading();
                                }
                            };
                            confirmWithRemarksDialog.show();

                        } else {

                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {
                                    BusProvider.getInstance().post(new PrintModel("", "ROOM# "+ selectedRoom.getName(), "VOID", GsonHelper.getGson().toJson(model)));
                                    BusProvider.getInstance().post(new AddRoomPriceRequest(new ArrayList<AddRateProductModel>(), String.valueOf(selectedRoom.getRoomId()),
                                            model, remarks,
                                            employeeId));

                                    showLoading();
                                }
                            };
                            confirmWithRemarksDialog.show();

                        }
                    }
                }

                @Override
                public void passwordFailed() {

                }
            };

            if (model.size() > 0) {
                if(!passwordDialog.isShowing()) passwordDialog.show();
            } else {
                Utils.showDialogMessage(getActivity(), "Please select item/s to void", "Information");
            }



        }
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                cartItemList.remove(viewHolder.getAdapterPosition());

                checkoutAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (cartItemList.size() > 0) {
                    if (cartItemList.get(viewHolder.getAdapterPosition()).isPosted()) {
                        return 0;
                    }
                }

                return super.getMovementFlags(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(listCheckoutItems);
    }


    private void saveTransaction() {
        new SaveTransactionAsync(getTableRecord(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;
        CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
    }

    @Override
    public void finishedSaving() {
        clearCartItems();

        defaultView();

        CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
    }

    private void clearCartItems() {
        header.setText("No room selected");
        cartItemList.clear();
        checkoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void cartItemRetrieved(List<ProductsModel> cartItemList) {

        this.cartItemList.clear();
//        this.cartItemList = cartItemList.size() > 0 ? cartItemList : new ArrayList<ProductsModel>();

//        checkoutAdapter = new CheckoutAdapter(this.cartItemList, this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        listCheckoutItems.setLayoutManager(linearLayoutManager);
//        listCheckoutItems.setAdapter(checkoutAdapter);
//        checkoutAdapter.notifyDataSetChanged();

//        computeFromDb();


    }

    private void computeFromDb() {
        double temp = 0;
//        for (ProductsModel p : cartItemList) {
//
//            temp += p.getPrice();
//
//        }

        List<CurrentTransactionEntity> currentTransaction  = CurrentTransactionEntity.listAll(CurrentTransactionEntity.class);

        temp += currentTransaction.get(0).getAmount();

        total.setText(temp < 1 ? "0.00": String.valueOf(temp));
    }


    private String selectedRoomNumber() {
        List<CurrentTransactionEntity> currentTransaction  = CurrentTransactionEntity.listAll(CurrentTransactionEntity.class);
        return currentTransaction.size() > 0 ? currentTransaction.get(0).getRoomNumber(): "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getContext(), "OK GO", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "OK DONT GO", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void savePayment() {
        PaymentEntity payment = new PaymentEntity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header:
//                RateDialog rateDialog = new RateDialog(getContext(), roomRateMainList) {
//                    @Override
//                    public void rateChangeSuccess(double amountSelected) {
//                        List<CurrentTransactionEntity> currentTransaction  = CurrentTransactionEntity.listAll(CurrentTransactionEntity.class);
//                        for (CurrentTransactionEntity c : currentTransaction) {
//                            c.setAmount(amountSelected);
//                            c.save();
//                        }
//                        computeFromDb();
//                    }
//                };
//                if (roomRateMainList.size() > 0) {
////                    rateDialog.show();
//                } else {
//                    Toast.makeText(getContext(), "No room rate list found", Toast.LENGTH_SHORT).show();
//                }

                break;
        }
    }

    private void fetchCarRequest() {
        BusProvider.getInstance().post(new FetchCarRequest());
    }

    private void fetchVehicleRequest() {
        BusProvider.getInstance().post(new FetchVehicleRequest());
    }

    private void fetchGuestTypeRequest() {
        BusProvider.getInstance().post(new FetchGuestTypeRequest());
    }

    @Subscribe
    public void fetchCarResponse(FetchCarResponse fetchCarResponse) {
        carList = fetchCarResponse.getResult();
    }

    @Subscribe
    public void fetchVehicleResponse(FetchVehicleResponse fetchVehicleResponse) {
        vehicleList = fetchVehicleResponse.getResult();

        SharedPreferenceManager.saveString(getContext(), GsonHelper.getGson().toJson(fetchVehicleResponse.getResult()), ApplicationConstants.VEHICLE_JSON);
    }

    @Subscribe
    public void fetchGuestTypeResponse(FetchGuestTypeResponse fetchGuestTypeResponse) {
        guestTypeList = fetchGuestTypeResponse.getResult();
    }

    @Subscribe
    public void guestWelcomeResponse(WelcomeGuestResponse welcomeGuestResponse) {
        //print checkin receipt
        BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "CHECKIN", GsonHelper.getGson().toJson(welcomeGuestResponse.getResult().getBooked())));

        if (selectedRoom != null) {
            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
//            BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId())));
        }

    }

    private void fetchRoomPending(final String roomId) {

        if (hasUnpostedItems()) {
            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                @Override
                public void yesClicked() {
                    BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));

                    showLoading();
                }

                @Override
                public void noClicked() {

                }
            };
            alertYesNo.show();
        } else {
            BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));
        }
    }

    private void showLoading() {
        if (LeftFrameFragment.loadingInterface != null) {
            LeftFrameFragment.loadingInterface.show(true);
        }
    }

    private void endLoading() {
        if (LeftFrameFragment.loadingInterface != null) {
            LeftFrameFragment.loadingInterface.show(false);
        }
    }

    private void fetchOrderPendingViaControlNo(final String controlNo) {

        if (hasUnpostedItems()) {
            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                @Override
                public void yesClicked() {
                    BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(controlNo));
                }

                @Override
                public void noClicked() {

                }
            };
            alertYesNo.show();
        } else {
            BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(controlNo));
        }


    }

    private void doCheckoutFunction() {
        PaymentDialog checkoutDialog = new PaymentDialog(getActivity(),
                paymentTypeList,
                true,
                postedPaymentsList,
                totalBalance,
                currencyList,
                creditCardList,
                arOnlineList,
                discountPayment,
                selectedRoom.getControlNo(),
                guestReceiptInfoModel) {
            @Override
            public void removePaymentSuccess() {
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                    } else {
                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                    }
                }
            }

            @Override
            public void paymentSuccess(final List<PostedPaymentsModel> postedPaymentLit, final String roomboy) {


                //regionpayment checkout chain
                List<PostedPaymentsModel> paymentsToPost = new ArrayList<>();
                boolean isReadyForCheckOut = false;
                Double totalPayments = 0.00;
                for (PostedPaymentsModel ppm : postedPaymentLit) {
                    if (!ppm.isIs_posted()) {
                        paymentsToPost.add(ppm);
                    }

                    totalPayments += Double.valueOf(ppm.getAmount()) / Double.valueOf(ppm.getCurrency_value());
                }

                if (cartItemList.size() == 0) {
                    //no order and prompt to cancel order, disregard all payments
                    if (selectedRoom.isTakeOut()) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        checkoutRoom("",
                                                selectedRoom.getControlNo(),
                                                "");
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dismiss();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("You have no orders, this will cancel your transaction. are you sure?")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    } else {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        dismiss();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dismiss();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("You have no orders / room rate, cannot proceed to checkout")
                                .setPositiveButton("Ok", dialogClickListener).show();

                    }

                } else {
                    if (totalPayments >= (totalBalance - (advancePayment + discountPayment))) {
                        if (paymentsToPost.size() > 0) {
                            if (selectedRoom.isTakeOut()) {
                                postCheckoutPayment(paymentsToPost, "", selectedRoom.getControlNo(), "");
                            } else {
                                postCheckoutPayment(paymentsToPost, String.valueOf(selectedRoom.getRoomId()), "", roomboy);
                            }
                        } else {

                            if (selectedRoom.isTakeOut()) {
                                checkoutRoom("",
                                        selectedRoom.getControlNo(),
                                        "");
                            } else {
                                checkoutRoom(String.valueOf(selectedRoom.getRoomId()),
                                        "",
                                        roomboy);
                            }


                            Toast.makeText(getContext(), "No payment to post, will proceed to checkout", Toast.LENGTH_SHORT).show();
                        }

                        dismiss();
                    } else {

                        Utils.showDialogMessage(getActivity(), "Payment is less than balance", "Warning");
                    }
                }
                //endregion


            }

            @Override
            public void paymentFailed() {

            }
        };
        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                if (paymentTypeList.size() > 0) {
                    if (currentRoomStatus.equalsIgnoreCase("1")) {
                        checkoutDialog.show();
                    } else {

                        Utils.showDialogMessage(getActivity(), "Please soa room first", "Information");
                    }

                } else {

                    Utils.showDialogMessage(getActivity(), "No payment type found, please re-open the application", "Information");
                }
            } else {
                if (currentRoomStatus.equalsIgnoreCase("17")) {
                    if (paymentTypeList.size() > 0) {

                        boolean isValid = false;

                        for (CartItemsModel cim : cartItemList) {
                            if (!cim.isProduct()) {
                                isValid = true;
                            }
                        }

                        if (isValid) {
                            checkoutDialog.show();
                        } else {

                            Utils.showDialogMessage(getActivity(), "Cannot checkout a room without a room rate, add one first", "Information");
                        }
                    } else {

                        Utils.showDialogMessage(getActivity(), "No payment type found, please re-open the application", "Information");

                    }
                } else {
                    Utils.showDialogMessage(getActivity(), "Please print SOA first", "Information");
                }
            }

        } else {

            Utils.showDialogMessage(getActivity(), "No room selected", "Information");
        }

    }

    @Subscribe
    public void fetchRoomPendingResponse(FetchRoomPendingResponse fetchRoomPendingResponse) {
        forVoidDiscountModels = new ArrayList<>();
        guestReceiptInfoModel = null;
        checkoutSwipe.setRefreshing(false);

        cartItemList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        Double totalAmount = 0.00;
        discountPayment = 0.00;
        advancePayment = 0.00;
        currentRoomStatus = String.valueOf(fetchRoomPendingResponse.getResult().getStatus());
        if (fetchRoomPendingResponse.getResult().getBooked().size() > 0) {

            for (FetchRoomPendingResponse.Booked r : fetchRoomPendingResponse.getResult().getBooked()) {

                if (r.getTransaction() != null) {
                    if (r.getTransaction().getEmployee_id() != null && !TextUtils.isEmpty(r.getTransaction().getEmployee_id())) {
                        employeeId = r.getTransaction().getEmployee_id();
                    }

                }

                if (r.getCustomer() != null) {
                    guestReceiptInfoModel = new GuestReceiptInfoModel(r.getCustomer().getCustomer(), r.getCustomer().getAddress(), r.getCustomer().getTin());
                }


                //regionpayments
                overTimeValue =  r.getTransaction().getOtHours();
                if (r.getTransaction().getPayments().size() > 0) {
                    for(FetchRoomPendingResponse.Payment pym : r.getTransaction().getPayments()) {

                        String paymentDescription = "";
                        if (pym.getPaymentTypeId() == 5) {
                            if (pym.getOuterAr() != null) {
                                paymentDescription = pym.getPaymentDescription() + " - " + pym.getOuterAr().getInnerAr().getArOnline() + "(" + pym.getOuterAr().getVoucherCode() +")";
                            } else {
                                paymentDescription = pym.getPaymentDescription();
                            }
                        } else {
                            paymentDescription = pym.getPaymentDescription();
                        }


                        String symbolLeft = "";
                        String symbolRight = "";

                        if (pym.getCurrency() != null) {
                            if (pym.getCurrency().getSymbolLeft() != null) {
                                symbolLeft = pym.getCurrency().getSymbolLeft();
                            }


                            if (pym.getCurrency().getSymbolRight() != null) {
                                symbolRight = pym.getCurrency().getSymbolRight();
                            }
                        }

                        postedPaymentsList.add(new PostedPaymentsModel(
                                String.valueOf(pym.getPaymentTypeId()),
                                String.valueOf(pym.getAmount()),
                                paymentDescription,
                                true,
                                String.valueOf(pym.getCurrencyId()),
                                String.valueOf(pym.getCurrencyValue()),
                                new JSONObject(),
                                symbolLeft,
                                symbolRight,
                                pym.getIsAdvance() == 1 ? true : false,
                                "",
                                String.valueOf(pym.getId())
                        ));
                    }
                }
                //endregion
                //region order list
                if (r.getTransaction().getTrans().size() > 0) {

                    forVoidDiscountModels = new ArrayList<>();
                    for (FetchRoomPendingResponse.DiscountsOuter dos  : r.getTransaction().getDiscountsOuter()) {
                        if (dos.getVoid_by() == null) {
                            forVoidDiscountModels.add(new ForVoidDiscountModel(dos.getId(), dos.getDiscountType(), dos.getDiscountAmount()));
                        }

                    }

                    selectedRoom.setControlNo(r.getTransaction().getControlNo());
                    totalBalance = (fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTotal() +
                            fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getOtAmount() +
                            fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getXPersonAmount())
//                            - (fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTendered());
                            - (fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTendered() + fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getVatExempt());

                    advancePayment = r.getTransaction().getAdvance();
                    discountPayment = r.getTransaction().getDiscount();
                    subTotal.setText(Utils.returnWithTwoDecimal(String.valueOf(totalBalance)));
                    total.setText(Utils.returnWithTwoDecimal(String.valueOf(totalBalance - (advancePayment + discountPayment))));
                    discount.setText(Utils.returnWithTwoDecimal(String.valueOf(discountPayment)));
                    deposit.setText(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getAdvance())));
                    for (FetchRoomPendingResponse.Tran transPost : r.getTransaction().getTrans()) {
                        List<OrderSlipModel.OrderSlipInfo> osiList = new ArrayList<>();
                        for (FetchRoomPendingResponse.Order osi : transPost.getOrder()) {
                            List<OrderSlipModel.OrderSlipProduct> osp = new ArrayList<>();
                            for (FetchRoomPendingResponse.PostTrans prod : osi.getPost()) {
                                if (prod.getProductId() == 0) { //room
                                    osp.add(
                                            new OrderSlipModel.OrderSlipProduct(String.valueOf(prod.getId()),
                                                    "",
                                                    "",
                                                    "",
                                                    String.valueOf(prod.getRoomTypeId()),
                                                    "",
                                                    String.valueOf(prod.getRoomRatePriceId()),
                                                    prod.getRoomType(),
                                                    prod.getRoomRate().toString(),
                                                    String.valueOf(prod.getQty()),
                                                    String.valueOf(prod.getPrice()),
                                                    String.valueOf(prod.getPrice()),
                                                    String.valueOf(prod.getTotal()),
                                                    prod.getVoid() == 0 ? false : true));
                                } else { //product
                                    osp.add(
                                            new OrderSlipModel.OrderSlipProduct(String.valueOf(prod.getId()),
                                                    String.valueOf(prod.getProduct().getId()),
                                                    prod.getProduct().getProduct(),
                                                    prod.getProduct().getProductInitial(),
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    String.valueOf(prod.getQty()),
                                                    String.valueOf(prod.getUnitCost()),
                                                    String.valueOf(prod.getPrice()),
                                                    String.valueOf(prod.getTotal()),
                                                    prod.getVoid() == 0 ? false : true));
                                }

                            }
                            OrderSlipModel.OrderSlipInfo slipInfoList =
                                    new OrderSlipModel.OrderSlipInfo(String.valueOf(osi.getId()),
                                            String.valueOf(osi.getPostOrderId()),
                                            String.valueOf(osi.getPostTransId()),
                                            osp);
                            osiList.add(slipInfoList);
                        }
                        OrderSlipModel orderSlipModel = new OrderSlipModel(transPost.getControlNo(), osiList, String.valueOf(transPost.getId()));
                        orderSlipList.add(orderSlipModel);


                    }
                }
                //endregion
                //region orders
                roomRateCounter = new ArrayList<>();


                for (FetchRoomPendingResponse.Post tpost : r.getTransaction().getPost()) {
                    if (tpost.getVoid() == 0) {
                        if (tpost.getRoomRateId() != null) {
                            roomRateCounter.add(1);
                            cartItemList.add(0, new CartItemsModel(
                                    tpost.getControlNo(),
                                    tpost.getRoomId(),
                                    tpost.getProductId(),
                                    tpost.getRoomTypeId(),
                                    tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
                                    tpost.getRoomRatePriceId(),
                                    tpost.getRoomRateId() == null ? tpost.getProduct().getProductInitial().toUpperCase() : tpost.getRoomRate().toUpperCase(),
                                    tpost.getProductId() == 0 ? false : true,
                                    tpost.getTotal(),
                                    tpost.getId(),
                                    tpost.getQty(),
                                    true,
                                    0.00,
                                    0,
                                    tpost.getPrice(),
                                    false,
                                    String.valueOf(tpost.getId()),
                                    false,
                                    "room"
                            ));
                        } else {
//                            Log.d("COUNTER", String.valueOf(roomRateCounter.size()));
                            cartItemList.add(roomRateCounter.size(), new CartItemsModel(
                                    tpost.getControlNo(),
                                    tpost.getRoomId(),
                                    tpost.getProductId(),
                                    tpost.getRoomTypeId(),
                                    tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
                                    tpost.getRoomRatePriceId(),
                                    tpost.getRoomRateId() == null ? tpost.getProduct().getProductInitial().toUpperCase() : tpost.getRoomRate().toUpperCase(),
                                    tpost.getProductId() == 0 ? false : true,
                                    tpost.getTotal(),
                                    tpost.getId(),
                                    tpost.getQty(),
                                    true,
                                    0.00,
                                    0,
                                    tpost.getPrice(),
                                    false,
                                    String.valueOf(tpost.getId()),
                                    false,
                                    "room"
                            ));
                        }

                        totalAmount += tpost.getTotal();
                    }
                }
                //endregion
                //region ot
//                r.getTransaction().getOtHours()
                if (r.getTransaction().getOtHours() > 0) {
                    cartItemList.add(new CartItemsModel(
                            r.getTransaction().getControlNo(),
                            0,
                            0,
                            0,
                            0,
                            0,
                            "OT HOURS",
                            false,
                            r.getTransaction().getOtAmount(),
                            0,
                            r.getTransaction().getOtHours(),
                            true,
                            0.00,
                            0,
                            Double.valueOf(r.getTransaction().getOtAmount()),
                            false,
                            "",
                            false,
                            "ot"
                    ));
                }

                //endregion

            }
        } else {
            totalBalance = 0;
        }


        checkoutAdapter = new CheckoutAdapter(this.cartItemList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        listCheckoutItems.setLayoutManager(linearLayoutManager);
        listCheckoutItems.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();


        if (fetchRoomPendingResponse.getResult() != null) {
            fetchRoomPendingResult = fetchRoomPendingResponse.getResult();
            switch (fetchRoomPendingResponse.getResult().getStatus()) {
                case 3: //dirty
                    showGuestInfoDialog(
                            String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
                    break;
                case 19:
                    showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
                    break;// ongoing nego
                case 20: //onnego show check in form
                    showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
                    break;
                case 32:
//                    showCheckInDialog(fetchRoomPendingResponse.getResult());
                    break;
                case 4:
//                    showCheckInDialog(fetchRoomPendingResponse.getResult());
                    break;
                case 59: //check in guest
                    showCheckInDialog();

                    break;

                case 2: //already checked in, can now order
                    Toast.makeText(getContext(), "Please order", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(getContext(), "Call fetch room pending", Toast.LENGTH_SHORT).show();
        }
        endLoading();
    }

    private void sendOffGoingNegoRequest(String roomId) {
        BusProvider.getInstance().post(new OffGoingNegoRequest(roomId));
        defaultView();
        clearCartItems();

    }

    private void showGuestInfoDialog(final String status) {
        fetchRoomPendingResult = null;
        checkInDialog = new CheckInDialog(getActivity(), selectedRoom, carList,
                vehicleList, guestTypeList,
                userList, roomAreaList,
                nationalityList,
                fetchRoomPendingResult) {
            @Override
            public void successCheckIn(final WelcomeGuestRequest welcomeGuestRequest) {
                BusProvider.getInstance().post(welcomeGuestRequest);
                if (!status.equalsIgnoreCase("19") &&
                        !status.equalsIgnoreCase("3") &&
                        !status.equalsIgnoreCase("20")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {

//                                    Log.d("CHECKINDATA", new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
//                                            welcomeGuestRequest.getRoomRatePriceId(),
//                                            remarks).toString());
//                                    Log.d("CHECKNREQU", new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
//                                            welcomeGuestRequest.getRoomRatePriceId(),
//                                            remarks).toString());
                                    BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                            welcomeGuestRequest.getRoomRatePriceId(),
                                            remarks));
//                                      BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
//                                            welcomeGuestRequest.getRoomRatePriceId(),
//                                            remarks));
                                    dismiss();
                                }
                            };
                            if (!confirmWithRemarksDialog.isShowing()) confirmWithRemarksDialog.show();


                        }
                    }, 500);


                }



            dismiss();
            }
        };
        if (!checkInDialog.isShowing()) checkInDialog.show();


        checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
            }
        });

    }

    private void showCheckInDialog() {
        if (fetchRoomPendingResult != null) {
            if (fetchRoomPendingResult.getBooked().size() > 0) {
                checkInDialog = new CheckInDialog(getActivity(), selectedRoom,
                        carList, vehicleList,
                        guestTypeList, userList,
                        roomAreaList, nationalityList, fetchRoomPendingResult) {
                    @Override
                    public void successCheckIn(final WelcomeGuestRequest welcomeGuestRequest) {


                        Log.d("CHECKNREQU", new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                welcomeGuestRequest.getRoomRatePriceId(),
                                "").toString());


                        BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                welcomeGuestRequest.getRoomRatePriceId(),
                                ""));


//                        ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
//                            @Override
//                            public void save(String remarks) {
//                                BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
//                                        welcomeGuestRequest.getRoomRatePriceId(),
//                                        remarks));
//                            }
//                        };
//
//                        confirmWithRemarksDialog.show();

                    }
                };
                if (!checkInDialog.isShowing()) checkInDialog.show();

                checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
                    }
                });
            } else {
                Utils.showDialogMessage(getActivity(), "No guest info data", "Information");
            }

        } else {
            Utils.showDialogMessage(getActivity(), "Fetch room pending result is null", "Information");
        }



    }

    private void addRateRequest(String productId, String roomRatePriceId,
                                String quantity, String tax,
                                final String roomId, String amount,
                                String roomRateDesc) {
        final ArrayList<AddRateProductModel> model = new ArrayList<>();
        model.add(new AddRateProductModel(productId, roomRatePriceId, quantity, tax, amount, 0, roomRateDesc));
        ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
            @Override
            public void save(String remarks) {

                BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model)));
                BusProvider.getInstance().post(new AddRoomPriceRequest(
                        model,
                        roomId, new ArrayList<VoidProductModel>(),
                        remarks, ""));
            }
        };

        if (!confirmWithRemarksDialog.isShowing()) confirmWithRemarksDialog.show();

    }

    @Subscribe
    public void addProductResponse(AddRoomPriceResponse addRoomPriceResponse) {
//        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));

        BusProvider.getInstance().post(new FetchRoomPendingRequest(String.valueOf(selectedRoom.getRoomId())));

        showLoading();

    }

    private void fetchPaymentTypeRequest() {
        BusProvider.getInstance().post(new FetchPaymentRequest());
    }

    @Subscribe
    public void onReceiveFetchPaymentTypeResponse(FetchPaymentResponse fetchPaymentResponse) {
        for (FetchPaymentResponse.Result r : fetchPaymentResponse.getResult()) {
            if (!r.getCoreId().equalsIgnoreCase("4")) {
                FetchPaymentResponse.Result fpr = r;
                paymentTypeList.add(fpr);
            }

        }
//        paymentTypeList = fetchPaymentResponse.getResult();
    }

    private void postAdvancePayment(final List<PostedPaymentsModel> ppm, String roomId, String controlNumber) {
//        BusProvider.getInstance().post(new AddPaymentRequest(ppm, roomId, "1", controlNumber));
        AddPaymentRequest addPaymentRequest = new AddPaymentRequest(ppm, roomId, "1", controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<AddPaymentResponse> request = iUsers.sendAddPayment(addPaymentRequest.getMapValue());

        request.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {
                if (selectedRoom != null) {
                    if (response.body().getStatus() == 0) {
                        Utils.showDialogMessage(getActivity(), response.body().getMessage(), "Information");
                        endLoading();
                    } else {

                        BusProvider.getInstance().post(new PrintModel("",
                                selectedRoom.getName(),
                                "DEPOSIT",
                                GsonHelper.getGson().toJson(ppm),
                                selectedRoom.getRoomType()));


                        if (selectedRoom.isTakeOut()) {
                            fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                        } else {
                            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPaymentResponse> call, Throwable t) {
                endLoading();
            }
        });

        showLoading();
    }

//    @Subscribe
//    public void addPaymentResponse(AddPaymentResponse addPaymentResponse) {
//        if (selectedRoom != null) {
//            if (addPaymentResponse.getStatus() == 0) {
//                Utils.showDialogMessage(getActivity(), addPaymentResponse.getMessage(), "Information");
//            } else {
//                if (selectedRoom.isTakeOut()) {
//                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
//                } else {
//                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
//                }
//            }
//
//        }
//    }



    private void postCheckoutPayment(List<PostedPaymentsModel> ppm, String roomId, String  controlNumber, final String roomBoy) {
//        Log.d("CHECKOUTDATA", new AddPaymentRequest(ppm, roomId, "0", controlNumber).toString());
//        BusProvider.getInstance().post(new AddPaymentRequest(ppm, roomId, "0", controlNumber));
        AddPaymentRequest addPaymentRequest = new AddPaymentRequest(ppm, roomId, "0", controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<AddPaymentResponse> request = iUsers.sendAddPayment(addPaymentRequest.getMapValue());
        request.enqueue(new Callback<AddPaymentResponse>() {
            @Override
            public void onResponse(Call<AddPaymentResponse> call, Response<AddPaymentResponse> response) {
                if (selectedRoom != null) {
                    if (response.body().getStatus() == 0) {
                        Utils.showDialogMessage(getActivity(), response.body().getMessage(), "Information");
                        endLoading();
                    } else {

                        if (selectedRoom.isTakeOut()) {
                            checkoutRoom("",
                                    selectedRoom.getControlNo(),
                                    "");
                        } else {
                            checkoutRoom(String.valueOf(selectedRoom.getRoomId()),
                                    "",
                                    roomBoy);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPaymentResponse> call, Throwable t) {
                endLoading();
            }
        });
        showLoading();
    }

    @Subscribe
    public void printSoaResponse(PrintSoaResponse printSoaResponse) {

        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                BusProvider.getInstance().post(new PrintModel("",
                        "takeout",
                        "SOA-TO",
                        GsonHelper.getGson().toJson(printSoaResponse.getResult())
                ));

                if (printSoaResponse.getResult().getToPostList().size() > 0) {
                    Utils.showDialogMessage(getActivity(), "Printing statement of account", "Information");
                } else {
                    Utils.showDialogMessage(getActivity(), "No item/s to print", "Information");
                }
            } else {

                BusProvider.getInstance().post(new PrintModel("",
                        selectedRoom.getName(),
                        "SOA-ROOM",
                        GsonHelper.getGson().toJson(printSoaResponse.getResult().getBooked())
                ));
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));

                if (printSoaResponse.getResult().getBooked().size() > 0) {
                    Utils.showDialogMessage(getActivity(), "Printing statement of account", "Information");
                } else {
                    Utils.showDialogMessage(getActivity(), "No item/s to print", "Information");
                }
            }



        }
    }



    private void printSoaRequest(String roomId, String controlNumber) {
        BusProvider.getInstance().post(new PrintSoaRequest(roomId, controlNumber));
    }

    @Subscribe
    public void fetchOrderPendingViaControlNoResponse(FetchOrderPendingViaControlNoResponse fetchOrderPendingViaControlNoResponse) {
//        Toast.makeText(getContext(), "FOP RESP", Toast.LENGTH_SHORT).show();
        forVoidDiscountModels = new ArrayList<>();
        guestReceiptInfoModel = null;
        checkoutSwipe.setRefreshing(false);
        currentRoomStatus = String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getIsSoa());
        String headerText = fetchOrderPendingViaControlNoResponse.getResult().getControlNo();
        if (fetchOrderPendingViaControlNoResponse.getResult().getEmployee() != null) {
            headerText += " " + fetchOrderPendingViaControlNoResponse.getResult().getEmployee().getName() + "\n";
        }

        header.setText(headerText);

        //to fix
//        totalBalance = fetchOrderPendingViaControlNoResponse.getResult().getTotal() - (fetchOrderPendingViaControlNoResponse.getResult().getAdvance() + fetchOrderPendingViaControlNoResponse.getResult().getDiscount());
        /*fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTotal() +
                fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getOtAmount() +
                fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getXPersonAmount()*/
        totalBalance = fetchOrderPendingViaControlNoResponse.getResult().getTotal() - ( fetchOrderPendingViaControlNoResponse.getResult().getTendered()  + fetchOrderPendingViaControlNoResponse.getResult().getVatExempt());

        advancePayment = fetchOrderPendingViaControlNoResponse.getResult().getAdvance();
        discountPayment = fetchOrderPendingViaControlNoResponse.getResult().getDiscount();


        cartItemList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        Double totalAmount = 0.00;
        if (fetchOrderPendingViaControlNoResponse.getResult() != null) {
            if (fetchOrderPendingViaControlNoResponse.getResult().getEmployeeId() != null) {

                employeeId = fetchOrderPendingViaControlNoResponse.getResult().getEmployeeId().toString();

            }


            for (FetchOrderPendingViaControlNoResponse.Discounts dos : fetchOrderPendingViaControlNoResponse.getResult().getDiscountsList()) {
                if (dos.getVoid_by() == null) {
                    forVoidDiscountModels.add(new ForVoidDiscountModel(dos.getId(), dos.getDiscountType(), String.valueOf(dos.getDiscountAmount())));
                }

            }

            if (fetchOrderPendingViaControlNoResponse.getResult().getCustomer() != null) {
                guestReceiptInfoModel = new GuestReceiptInfoModel(fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getCustomer(), fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getAddress(), fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getTin());
            }

            discount.setText(String.valueOf(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getDiscount())));
            subTotal.setText(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal()));
            deposit.setText(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getAdvance()));
            total.setText(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal() -(fetchOrderPendingViaControlNoResponse.getResult().getAdvance() + fetchOrderPendingViaControlNoResponse.getResult().getDiscount())));
            if (fetchOrderPendingViaControlNoResponse.getResult().getPayments().size() > 0) {
                for (FetchOrderPendingViaControlNoResponse.Payment pym : fetchOrderPendingViaControlNoResponse.getResult().getPayments()) {

                    String symbolLeft = "";
                    String symbolRight = "";

                    if (pym.getCurrency() != null) {
                        if (pym.getCurrency().getSymbolLeft() != null) {
                            symbolLeft = pym.getCurrency().getSymbolLeft();
                        }


                        if (pym.getCurrency().getSymbolRight() != null) {
                            symbolRight = pym.getCurrency().getSymbolRight();
                        }
                    }


                    postedPaymentsList.add(new PostedPaymentsModel(
                            String.valueOf(pym.getPaymentTypeId()),
                            String.valueOf(pym.getAmount()),
                            pym.getPaymentDescription(),
                            true,
                            String.valueOf(pym.getCurrencyId()),
                            String.valueOf(pym.getCurrencyValue()),
                            new JSONObject(),
                            symbolLeft,
                            symbolRight,
                            pym.getIsAdvance() == 1 ? true : false,
                            "",
                            String.valueOf(pym.getId())
                    ));
                }
            }
            if (fetchOrderPendingViaControlNoResponse.getResult().getTrans().size() > 0) {
                for (FetchOrderPendingViaControlNoResponse.Tran transPost : fetchOrderPendingViaControlNoResponse.getResult().getTrans()) {
                    List<OrderSlipModel.OrderSlipInfo> osiList = new ArrayList<>();
                    for (FetchOrderPendingViaControlNoResponse.Order osi : transPost.getOrder()) {
                        List<OrderSlipModel.OrderSlipProduct> osp = new ArrayList<>();
                        for (FetchOrderPendingViaControlNoResponse.Post prod : osi.getPost()) {
                            if (prod.getProductId() == 0) { //room
                                osp.add(
                                        new OrderSlipModel.OrderSlipProduct(String.valueOf(prod.getId()),
                                                "",
                                                "",
                                                "",
                                                String.valueOf(prod.getRoomTypeId()),
                                                "",
                                                String.valueOf(prod.getRoomRatePriceId()),
                                                prod.getRoomType().toString(),
                                                prod.getRoomRate().toString(),
                                                String.valueOf(prod.getQty()),
                                                "",
                                                String.valueOf(prod.getPrice()),
                                                String.valueOf(prod.getTotal()),
                                                prod.getVoid() == 0 ? false : true));
                            } else { //product
                                osp.add(
                                        new OrderSlipModel.OrderSlipProduct(String.valueOf(prod.getId()),
                                                String.valueOf(prod.getProduct().getId()),
                                                prod.getProduct().getProduct(),
                                                prod.getProduct().getProductInitial(),
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                String.valueOf(prod.getQty()),
                                                String.valueOf(prod.getUnitCost()),
                                                String.valueOf(prod.getPrice()),
                                                String.valueOf(prod.getTotal()),
                                                prod.getVoid() == 0 ? false : true));
                            }
                        }
                        OrderSlipModel.OrderSlipInfo slipInfoList =
                                new OrderSlipModel.OrderSlipInfo(String.valueOf(osi.getId()),
                                        String.valueOf(osi.getPostOrderId()),
                                        String.valueOf(osi.getPostTransId()),
                                        osp);
                        osiList.add(slipInfoList);
                    }
                    OrderSlipModel orderSlipModel = new OrderSlipModel(transPost.getControlNo(), osiList, String.valueOf(transPost.getId()));
                    orderSlipList.add(orderSlipModel);


                }
            }


            if (fetchOrderPendingViaControlNoResponse.getResult().getPost().size() > 0) {
                for (FetchOrderPendingViaControlNoResponse.Post tpost : fetchOrderPendingViaControlNoResponse.getResult().getPost()) {

                    if (tpost.getVoid() == 0) {
                        cartItemList.add(new CartItemsModel(
                                tpost.getControlNo(),
                                0,
                                tpost.getProductId(),
                                0,
                                0 ,
                                0,
                                tpost.getProduct().getProductInitial().toUpperCase(),
                                tpost.getProductId() == 0 ? false : true,
                                tpost.getTotal(),
                                tpost.getId(),
                                tpost.getQty(),
                                true,
                                0.00,
                                0,
                                tpost.getPrice(),
                                false,
                                String.valueOf(tpost.getId()),
                                false,
                                "to"
                        ));
                        totalAmount += tpost.getTotal();
                    }


                }

            }


            checkoutAdapter = new CheckoutAdapter(this.cartItemList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//            linearLayoutManager.setReverseLayout(true);
//            linearLayoutManager.setStackFromEnd(true);
            listCheckoutItems.setLayoutManager(linearLayoutManager);
            listCheckoutItems.setAdapter(checkoutAdapter);
            checkoutAdapter.notifyDataSetChanged();
        }

        endLoading();
    }

    @Subscribe
    public void addProductToResponse(AddProductToResponse addProductToResponse) {
        if (selectedRoom != null) {
            BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(selectedRoom.getControlNo()));
        }
        endLoading();

    }

    private void checkoutRoom(String roomId, String controlNumber, String roomBoyId) {
        BusProvider.getInstance().post(new CheckOutRequest(roomId, controlNumber, roomBoyId));
    }

    @Subscribe
    public void checkoutResponse(CheckOutResponse checkOutResponse) {

        if (checkOutResponse.getStatus() == 0) {
            Utils.showDialogMessage(getActivity(), checkOutResponse.getMessage(), "Information");
        } else {


            printReceiptFromCheckout(selectedRoom.getControlNo(),
                    selectedRoom.getName(),
                    selectedRoom.getRoomType());
            Toast.makeText(getContext(), "CHECK OUT SUCCESS", Toast.LENGTH_SHORT).show();

            if (selectedRoom != null) {
                if (selectedRoom.isTakeOut()) {
                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                } else {
                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                }
            }



//            Utils.showDialogMessage(getActivity(), "CHECK OUT SUCCESS", "Information");
//            clearCartItems();
//            defaultView();

//            if (checkOutResponse.getResult().getData().getBooked() != null) {
//                BusProvider.getInstance().post(new ButtonsModel(999, "ROOMS", "", 1));
//            } else {
//                BusProvider.getInstance().post(new ButtonsModel(998, "TAKE ORDER", "", 2));
//            }

        }

        endLoading();
    }



    private void fetchCurrencyExceptDefaultRequest() {
        BusProvider.getInstance().post(new FetchCurrencyExceptDefaultRequest());
    }

    @Subscribe
    public void fetchCurrencyExceptDefaultResponse(FetchCurrencyExceptDefaultResponse fetchCurrencyExceptDefaultResponse) {
        if (fetchCurrencyExceptDefaultResponse.getResult().size() > 0) {
            SharedPreferenceManager.saveString(getContext(),
                    GsonHelper.getGson().toJson(fetchCurrencyExceptDefaultResponse.getResult()),
                    ApplicationConstants.CURRENCY_JSON);

            TypeToken<List<FetchCurrencyExceptDefaultResponse.Result>> currencyToken = new TypeToken<List<FetchCurrencyExceptDefaultResponse.Result>>() {};
            currencyList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.CURRENCY_JSON), currencyToken.getType());
        }
    }

    private void fetchNationalityRequest() {
        BusProvider.getInstance().post(new FetchNationalityRequest());
    }

    @Subscribe
    public void fetchNationalityResponse(FetchNationalityResponse fetchNationalityResponse) {
        if (fetchNationalityResponse.getResult().size() > 0) {
            SharedPreferenceManager.saveString(getContext(),
                    GsonHelper.getGson().toJson(fetchNationalityResponse.getResult()),
                    ApplicationConstants.NATIONALITY_JSON);
            TypeToken<List<FetchNationalityResponse.Result>> nationalityToken = new TypeToken<List<FetchNationalityResponse.Result>>() {};
            nationalityList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.NATIONALITY_JSON), nationalityToken.getType());
        }
    }

    private void fetchArOnlineRequest() {
        BusProvider.getInstance().post(new FetchArOnlineRequest());
    }

    @Subscribe
    public void fetchArOnlineResponse(FetchArOnlineResponse fetchArOnlineResponse) {
        if (fetchArOnlineResponse.getResult().size() > 0) {
            SharedPreferenceManager.saveString(getContext(),
                    GsonHelper.getGson().toJson(fetchArOnlineResponse.getResult()),
                    ApplicationConstants.AR_ONLINE);
            TypeToken<List<FetchArOnlineResponse.Result>> artoken = new TypeToken<List<FetchArOnlineResponse.Result>>() {};
            arOnlineList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.AR_ONLINE), artoken.getType());
        }
    }

    private void fetchCreditCardRequest() {
        BusProvider.getInstance().post(new FetchCreditCardRequest());
    }

    @Subscribe
    public void fetchCCardRespose(FetchCreditCardResponse fetchCreditCardResponse) {
        if (fetchCreditCardResponse.getResult().size() > 0) {
            Log.d("CCARD", "YYY");
            SharedPreferenceManager.saveString(getContext(),
                    GsonHelper.getGson().toJson(fetchCreditCardResponse.getResult()),
                    ApplicationConstants.CREDIT_CARD);

            TypeToken<List<FetchCreditCardResponse.Result>> creditCardToken = new TypeToken<List<FetchCreditCardResponse.Result>>() {};
            creditCardList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.CREDIT_CARD), creditCardToken.getType());
        }
    }






    @Subscribe
    public void checkinResponse(CheckInResponse checkInResponse) {
        BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "CHECKIN", GsonHelper.getGson().toJson(checkInResponse.getResult().getBooked())));
        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
            } else {
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
            }

            selectedRoom.setControlNo(checkInResponse.getResult().getBooked().get(0).getTransaction().getControlNo());

        }
    }


    private void loadPrinter() {
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT))) {
            SPrinter printer = new SPrinter(
                    Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_LANGUAGE)),
                    getContext());

            try {

                Log.d("GFGFGFG", SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT));
                SPrinter.getPrinter().connect(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT), Printer.PARAM_DEFAULT);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void switchRoomResponse(SwitchRoomResponse switchRoomResponse) {

        if (switchRoomResponse.getStatus() == 0) {
            Utils.showDialogMessage(getActivity(), switchRoomResponse.getMesage(), "Warning");
        } else {
            if (switchRoomResponse.getResults() != null) {
                if (switchRoomResponse.getResults().getBooked().size() > 0) {


                    SwitchRoomPrintModel switchRoomPrintModel =
                            new SwitchRoomPrintModel(
                                    selectedRoom.getName(),
                                    selectedRoom.getRoomType(),
                                    switchRoomResponse.getResults().getBooked().get(0).getRoomNumber(),
                                    switchRoomResponse.getResults().getBooked().get(0).getRoomType(),
                                    switchRoomResponse.getResults().getBooked().get(0).getCheckInTime(),
                                    switchRoomResponse.getResults().getBooked().get(0).getUser_id());

                    BusProvider.getInstance().post(
                            new PrintModel("", switchRoomResponse.getResults().getBooked().get(0).getRoomNumber(),
                                    "SWITCH_ROOM" ,GsonHelper.getGson().toJson(switchRoomPrintModel)));

                    fetchRoomViaIdRequest(String.valueOf(switchRoomResponse.getResults().getBooked().get(0).getRoomId()));
                    Utils.showDialogMessage(getActivity(), "Switch room succeeded", "Success");
                }

            }
        }
    }

    private void fetchRoomViaIdRequest(String roomId) {
        BusProvider.getInstance().post(new FetchRoomViaIdRequest(roomId));
    }

    @Subscribe
    public void fetchRoomViaIdResponse(FetchRoomViaIdResponse r) {

        List<RoomRateMainViaId> roomRateMainList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();



        if (r.getResult().getRoomRate().size() > 0) {
            for (RoomRateSubViaId rateSub : r.getResult().getRoomRate()) {
                if (!tempList.contains(rateSub.getRoomRatePriceId())) {
                    if (rateSub.getRatePrice() != null) {
                        roomRateMainList.add(
                                new RoomRateMainViaId(
                                        rateSub.getId(), rateSub.getRoomRatePriceId(),
                                        r.getResult().getRoomTypeId(),rateSub.getCreatedBy(),
                                        rateSub.getCreatedAt(), rateSub.getUpdatedAt(),
                                        rateSub.getDeletedAt(), rateSub.getRatePrice())
                        );
                        tempList.add(rateSub.getRoomRatePriceId());
                    }

                }
            }
        }


        if (r.getResult().getType() != null) {

            if (r.getResult().getType().getParent() != null) {
                if (r.getResult().getType().getParent().getRoomRate().size() > 0) {
                    for (RoomRateMainViaId p : r.getResult().getType().getParent().getRoomRate()) {
                        if (!tempList.contains(p.getRoomRatePriceId())) {
                            roomRateMainList.add(p);
                            tempList.add(p.getRoomRatePriceId());
                        }
                    }
                }

            }

            if (r.getResult().getType().getRoomRate().size() > 0) {
                for (RoomRateMainViaId rateList : r.getResult().getType().getRoomRate()) {
                    if (!tempList.contains(rateList.getRoomRatePriceId())) {
                        roomRateMainList.add(rateList);
                        tempList.add(rateList.getRoomRatePriceId());
                    }
                }
            }

        }

        Double amountSelected = 0.00;
        String checkoutExpected = "";
        if (String.valueOf(r.getResult().getStatus().getCoreId()).equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                String.valueOf(r.getResult().getStatus().getCoreId()).equalsIgnoreCase(RoomConstants.SOA)) {

            if (r.getResult().getTransaction() != null) {
                if (r.getResult().getTransaction().getExpectedCheckOut() != null) {
                    DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                    DateTime jodatime = dtf.parseDateTime(r.getResult().getTransaction().getExpectedCheckOut());
                    DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");

                    amountSelected = r.getResult().getTransaction().getTransaction().getTotal();
                    checkoutExpected = dtfOut.print(jodatime);
                } else {
                    checkoutExpected = "";
                }
            } else {
                checkoutExpected = "";
            }



        } else {
            amountSelected = 0.00;
            checkoutExpected = "--";
        }
//        Integer id, Integer roomRatePriceId,
//                Integer roomTypeId, Integer createdBy,
//                String createdAt, String updatedAt,
//                Object deletedAt, RatePrice ratePrice
        List<RoomRateMain> mYList=  new ArrayList<>();
        for (RoomRateMainViaId rrm : roomRateMainList) {
            RatePrice ratePrice = new RatePrice(
                    rrm.getRatePrice().getId(),
                    rrm.getRatePrice().getRoomRateId(),
                    rrm.getRatePrice().getCurrencyId(),
                    rrm.getRatePrice().getAmount(),
                    rrm.getRatePrice().getXPerson(),
                    rrm.getRatePrice().getPerHour(),
                    rrm.getRatePrice().getFlag(),
                    rrm.getRatePrice().getCreatedBy(),
                    rrm.getRatePrice().getCreatedAt(),
                    rrm.getRatePrice().getUpdatedAt(),
                    "",
                    rrm.getRatePrice().getRoomRate()
            );
            mYList.add(new RoomRateMain(
                    rrm.getId(),
                    rrm.getRoomRatePriceId(),
                    rrm.getRoomTypeId(),
                    rrm.getCreatedBy(),
                    rrm.getCreatedAt(),
                    rrm.getUpdatedAt(),
                    rrm.getDeletedAt(),
                    ratePrice

            ));

        }

        String checkInTime = "NA";
        if (r.getResult().getTransaction() != null) {
            if (r.getResult().getTransaction().getCheckIn() != null) {
                checkInTime = r.getResult().getTransaction().getCheckIn();
            }
        }

        selectedRoom = new RoomTableModel (
                r.getResult().getId(),
                r.getResult().getRoomTypeId(),
                r.getResult().getType().getRoomType(),
                0, //r.getType().getParent() == null ? 0 : r.getType().getParent().getId(),
                "test parent", //r.getType().getParent() == null ? "NONE" : r.getType().getParent().getRoomType(),
                r.getResult().getRoomAreaId(),
                r.getResult().getArea().getRoomArea(),
                r.getResult().getStatus().getRoomStatus(),
                r.getResult().getRoomNo(),
                mYList,
                true,
                "https://imageog.flaticon.com/icons/png/512/51/51882.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF",
                String.valueOf(r.getResult().getCRoomStat()),
                r.getResult().getStatus().getColor(),
                amountSelected,
                false,
                r.getResult().getTransaction() != null ? r.getResult().getTransaction().getTransaction().getControlNo() : "",
                0,
                r.getResult().getStatus().getIsBlink() == 1 ? true : false,
                r.getResult().getStatus().getIsTimer() == 1 ? true : false,
                checkoutExpected,
                r.getResult().getTransaction() != null ? String.valueOf(r.getResult().getTransaction().getTransaction().getOtHours()) : "",
                checkInTime
        );

        currentRoomStatus = selectedRoom.getStatus();
        if (selectedRoom.getRoomType() != null) {
            setView(selectedRoom.getName(), selectedRoom.getRoomType());
        } else {
            setView(selectedRoom.getName(), "TO");
        }

    }

    private void printReceiptFromCheckout(String controlNumber, final String roomName, final String roomType) {
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                BusProvider.getInstance().post(new PrintModel(
                        "", roomName,
                        "PRINT_RECEIPT",
                        GsonHelper.getGson().toJson(response.body().getResult()),
                        roomType));

                clearCartItems();
                defaultView();
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });
    }

    private boolean hasUnpostedItems() {
        boolean hasUnposted = false;
        if (cartItemList.size() > 0) {
            for (CartItemsModel cim : cartItemList) {
                if (!cim.isPosted()) {
                    hasUnposted = true;
                    break;
                }
            }
        }
        return hasUnposted;
    }

    private void cashNReconcile() {

        if (cutOffDialog != null) {
            if (!cutOffDialog.isShowing()) cutOffDialog.show();
        } else {
            cutOffDialog = new CollectionDialog(getActivity(), "CASH AND RECONCILE", true) {
                @Override
                public void printCashRecoData(String cashNRecoData) {
                    try {
                        JSONObject jsonObject = new JSONObject(cashNRecoData);
                        JSONObject dataJsonObject = jsonObject.getJSONObject("nameValuePairs").getJSONObject("data").getJSONObject("nameValuePairs");
                        JSONObject cashierDataObject = jsonObject.getJSONObject("nameValuePairs").getJSONObject("data").getJSONObject("nameValuePairs").getJSONObject("cashier");

                        JSONObject dutyManager = jsonObject.getJSONObject("nameValuePairs").getJSONObject("data").getJSONObject("nameValuePairs").getJSONObject("duty_manager");
                        if (dataJsonObject != null) {
                            fetchXReadViaIdRequest(dataJsonObject.getString("id"));

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            if (!cutOffDialog.isShowing()) cutOffDialog.show();
        }



    }

    private void zReadRequest() {

        PasswordDialog passwordDialog = new PasswordDialog(getActivity(), "END OF DAY PROCESS \n") {
            @Override
            public void passwordSuccess(String employeeId, String employeeName) {
                ZReadRequest zReadRequest = new ZReadRequest(employeeId);
                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<ResponseBody> request = iUsers.zReading(zReadRequest.getMapValue());
                request.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result = response.body().string();
                            JSONObject zReadObject = new JSONObject(result);
                            if (zReadObject.getString("status").equalsIgnoreCase("0")) {
                                Utils.showDialogMessage(getActivity(), zReadObject.getString("message"), "Information");
                            } else {
                                JSONObject zReadResultObject = zReadObject.getJSONObject("result");
                                JSONObject ZReadResultDataObject = zReadResultObject.getJSONObject("data");
                                fetchZReadViaIdRequest(ZReadResultDataObject.getString("id"));
                                Utils.showDialogMessage(getActivity(), "ZREAD SUCCESS", "Information");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        BackupDatabaseRequest backupDatabaseRequest = new BackupDatabaseRequest();
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<ResponseBody> request = iUsers.backupDb(backupDatabaseRequest.getMapValue());
                        request.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
//                request.enqueue(new Callback<ZReadResponse>() {
//                    @Override
//                    public void onResponse(Call<ZReadResponse> call, Response<ZReadResponse> response) {
//
//                        fetchZReadViaIdRequest(String.valueOf(response.body().getResult().getData().getId()));
//                        Utils.showDialogMessage(getActivity(), response.body().getMessage(), "Information");
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ZReadResponse> call, Throwable t) {
//
//                    }
//                });
            }

            @Override
            public void passwordFailed() {

            }
        };

        if (!passwordDialog.isShowing()) passwordDialog.show();



    }

    private void xReadRequest() {

        PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
            @Override
            public void passwordSuccess(String employeeId, String employeeName) {
                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                XReadRequest collectionRequest = new XReadRequest(new ArrayList<CollectionFinalPostModel>(), employeeId);
                Call<XReadResponse> request = iUsers.xReading(collectionRequest.getMapValue());

                request.enqueue(new Callback<XReadResponse>() {
                    @Override
                    public void onResponse(Call<XReadResponse> call, Response<XReadResponse> response) {
                        if (response.body().getStatus() == 0) {
                            Utils.showDialogMessage(getActivity(), response.body().getMessage(), "Information");
                        } else {
                            Utils.showDialogMessage(getActivity(), "X READ SUCCESS", "Information");
                        }
                    }

                    @Override
                    public void onFailure(Call<XReadResponse> call, Throwable t) {

                    }
                });

            }

            @Override
            public void passwordFailed() {

            }
        };

        if (!passwordDialog.isShowing()) passwordDialog.show();


//        CheckXReadRequest xReadRequest = new CheckXReadRequest();
//        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
//        Call<CheckXReadResponse> request = iUsers.checkXREad(xReadRequest.getMapValue());
//        request.enqueue(new Callback<CheckXReadResponse>() {
//            @Override
//            public void onResponse(Call<CheckXReadResponse> call, Response<CheckXReadResponse> response) {
//                if (response.body().getStatus() == 1) {
//
//
//
//
//                    //open safekeep, upon success of safekeep call cash and reconcile
////                    CollectionDialog cutOffDialog = new CollectionDialog(getActivity(), "CUTOFF", true);
////                    if (!cutOffDialog.isShowing()) cutOffDialog.show();
//                } else {
//                    Utils.showDialogMessage((MainActivity)getContext(), response.body().getMessage(), "Information");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CheckXReadResponse> call, Throwable t) {
//
//            }
//        });
    }

    private void cancelOverTime() {

        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                Utils.showDialogMessage(getActivity(), "Cannot Cancel, not room", "Information");
            } else {

                if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                        currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA)) {
                    boolean hasOt = false;
                    for (CartItemsModel cim : cartItemList) {
                        if (cim.getType().equalsIgnoreCase("ot")) {
                            hasOt = true;
                            break;
                        }
                    }

                    if (hasOt) {

                        RemoveOtDialog removeOtDialog = new RemoveOtDialog(getContext(), String.valueOf(overTimeValue), getActivity()) {
                            @Override
                            public void removeOtSuccess(final String oldOtValue, final String remainingOt) {

                                PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                                    @Override
                                    public void passwordSuccess(String employeeId, String employeeName) {
                                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                        CancelOverTimeRequest cancelOverTimeRequest = new CancelOverTimeRequest(String.valueOf(selectedRoom.getRoomId()), selectedRoom.getControlNo(), oldOtValue, remainingOt, employeeId);
                                        Call<CancelOverTimeResponse> request = iUsers.cancelOverTime(cancelOverTimeRequest.getMapValue());
                                        request.enqueue(new Callback<CancelOverTimeResponse>() {
                                            @Override
                                            public void onResponse(Call<CancelOverTimeResponse> call, Response<CancelOverTimeResponse> response) {
                                                if (response.body().getStatus() == 0) {
                                                    Utils.showDialogMessage(getActivity(), response.body().getMessage(), "Information");
                                                } else {
                                                    Utils.showDialogMessage(getActivity(), "Cancel OT Success", "Information");
                                                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<CancelOverTimeResponse> call, Throwable t) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void passwordFailed() {

                                    }
                                };

                                if (!passwordDialog.isShowing()) passwordDialog.show();

                            }
                        };
                        if (!removeOtDialog.isShowing()) removeOtDialog.show();


                    } else {
                        Utils.showDialogMessage(getActivity(), "Room dont have OT", "Information");
                    }
                } else {
                    Utils.showDialogMessage(getActivity(), "Room not occupied", "Information");
                }

            }
        }

    }

    private void doSoaFunction() {
        if (selectedRoom.isTakeOut()) {
            printSoaRequest("", selectedRoom.getControlNo());
        } else {
            if (currentRoomStatus.equalsIgnoreCase("2") || currentRoomStatus.equalsIgnoreCase("17")) {
                boolean isValid = false;

                for (CartItemsModel cim : cartItemList) {
                    if (!cim.isProduct()) {
                        isValid = true;
                    }
                }

                if (isValid) {
                    printSoaRequest(String.valueOf(selectedRoom.getRoomId()), "");
                } else {
                    Utils.showDialogMessage(getActivity(), "Cannot SOA a room without a room rate, add one first", "Information");
                }

            } else {
                Utils.showDialogMessage(getActivity(), "Please SOA room first", "Information");
            }
        }
    }

    private void fetchXReadViaIdRequest(String xReadingId) {
        BusProvider.getInstance().post(new FetchXReadingViaIdRequest(xReadingId));
    }

    @Subscribe
    public void fetchXReadingViaIdResponse(FetchXReadingViaIdResponse fetchXReadingViaIdResponse) {
        Log.d("WATAFAK", "TEST");
        BusProvider.getInstance().post(new PrintModel("", "X READING", "REXREADING", GsonHelper.getGson().toJson(fetchXReadingViaIdResponse.getResult())));
        BusProvider.getInstance().post(new PrintModel("", "SHORT/OVER", "SHORTOVER", GsonHelper.getGson().toJson(fetchXReadingViaIdResponse.getResult())));
    }

    private void fetchZReadViaIdRequest(String zReadId) {
        FetchZReadViaIdRequest fetchZReadViaIdRequest = new FetchZReadViaIdRequest(zReadId);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ZReadResponse> request = iUsers.fetchZReadViaId(fetchZReadViaIdRequest.getMapValue());
        request.enqueue(new Callback<ZReadResponse>() {
            @Override
            public void onResponse(Call<ZReadResponse> call, Response<ZReadResponse> response) {

                BusProvider.getInstance().post(new PrintModel("", "ZREAD", "ZREAD", GsonHelper.getGson().toJson(response.body().getResult())));


//                BusProvider.getInstance().post(new PrintModel("", "ZREAD", "ZREAD", GsonHelper.getGson().toJson(response.body().getResult())));


            }

            @Override
            public void onFailure(Call<ZReadResponse> call, Throwable t) {

            }
        });

    }

    public interface Data {
        void refresh();
    }

    @Subscribe
    public void focResponse(FocTransactionResponse focResponse) {
        if (focResponse.getStatus() == 1) {
            defaultView();
            clearCartItems();
        }
        Utils.showDialogMessage(getActivity(), focResponse.getMessage(), "Information");
    }


    @Subscribe
    public void infoModel(InfoModel infoModel) {
        Toast.makeText(getContext(), infoModel.getInformation(), Toast.LENGTH_SHORT).show();
        SharedPreferenceManager.saveString(getContext(), infoModel.getInformation(), ApplicationConstants.SHIFT_BLOCKER);

        if (!canTransact()) {

            if (infoModel.getInformation().equalsIgnoreCase("please execute end of day")) {
                Toast.makeText(getContext(), infoModel.getInformation(), Toast.LENGTH_SHORT).show();
            } else {
                if (cutOffDialog != null) {
                    if (!cutOffDialog.isShowing()) {
                        if (blockerDialog != null) {
                            if (!blockerDialog.isShowing()) {
                                blockerDialog.show();
                            }
                        } else {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            BusProvider.getInstance().post(new ButtonsModel(121,"XREAD", "",19));
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("You need to execute cutoff before you can transact, Continue?")
                                    .setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener);
                            blockerDialog = builder.create();
                            builder.show();
                        }
                    }
                } else {
                    if (blockerDialog != null) {
                        if (!blockerDialog.isShowing()) {
                            blockerDialog.show();
                        }
                    } else {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        BusProvider.getInstance().post(new ButtonsModel(121,"XREAD", "",19));
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("You need to execute cutoff before you can transact, Continue?")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener);
                        blockerDialog = builder.create();
                        builder.show();
                    }
                }
            }


        }
    }



}
