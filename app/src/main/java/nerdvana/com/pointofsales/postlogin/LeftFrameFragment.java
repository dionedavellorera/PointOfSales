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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
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
import nerdvana.com.pointofsales.Helper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.PrinterUtils;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.RoomsActivity;
import nerdvana.com.pointofsales.SPrinter;
import nerdvana.com.pointofsales.SettingsActivity;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.SocketManager;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.UpdateDataModel;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.AddPaymentRequest;
import nerdvana.com.pointofsales.api_requests.AddProductRemarksRequest;
import nerdvana.com.pointofsales.api_requests.AddProductToRequest;
import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_requests.BackOutGuestRequest;
import nerdvana.com.pointofsales.api_requests.BackupDatabaseRequest;
import nerdvana.com.pointofsales.api_requests.CancelOverTimeRequest;
import nerdvana.com.pointofsales.api_requests.ChangeRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.CheckOutRequest;
import nerdvana.com.pointofsales.api_requests.CheckSafeKeepingRequest;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_requests.FetchArOnlineRequest;
import nerdvana.com.pointofsales.api_requests.FetchBranchInfoRequest;
import nerdvana.com.pointofsales.api_requests.FetchCarRequest;
import nerdvana.com.pointofsales.api_requests.FetchCreditCardRequest;
import nerdvana.com.pointofsales.api_requests.FetchCurrencyExceptDefaultRequest;
import nerdvana.com.pointofsales.api_requests.FetchGuestTypeRequest;
import nerdvana.com.pointofsales.api_requests.FetchNationalityRequest;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingViaControlNoRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FetchZReadViaIdRequest;
import nerdvana.com.pointofsales.api_requests.FocTransactionRequest;
import nerdvana.com.pointofsales.api_requests.GetOrderRequest;
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
import nerdvana.com.pointofsales.api_responses.ChangeRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckOutResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchNationalityResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.FocTransactionResponse;
import nerdvana.com.pointofsales.api_responses.GetOrderResponse;
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
import nerdvana.com.pointofsales.background.RoomsTablesAsync;
import nerdvana.com.pointofsales.background.SaveTransactionAsync;
import nerdvana.com.pointofsales.custom.AlertYesNo;
import nerdvana.com.pointofsales.custom.SwipeToDeleteCallback;
import nerdvana.com.pointofsales.dialogs.AddGuestDialog;
import nerdvana.com.pointofsales.dialogs.CheckInDialog;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.dialogs.ConfirmWithRemarksDialog;
import nerdvana.com.pointofsales.dialogs.DialogBundleComposition;
import nerdvana.com.pointofsales.dialogs.DiscountSelectionDialog;
import nerdvana.com.pointofsales.dialogs.EmployeeSelectionDialog;
import nerdvana.com.pointofsales.dialogs.FreebiesDialog;
import nerdvana.com.pointofsales.dialogs.GuestInfoDialog;
import nerdvana.com.pointofsales.dialogs.InputDialog;
import nerdvana.com.pointofsales.dialogs.OpenPriceDialog;
import nerdvana.com.pointofsales.dialogs.OrderSlipDialog;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.dialogs.RateDialog;
import nerdvana.com.pointofsales.dialogs.RemoveOtDialog;
import nerdvana.com.pointofsales.dialogs.ReprintActualXReadReceiptDialog;
import nerdvana.com.pointofsales.dialogs.ReprintActualZReadReceiptDialog;
import nerdvana.com.pointofsales.dialogs.ReprintSkDialog;
import nerdvana.com.pointofsales.dialogs.ReprintXReadingDialog;
import nerdvana.com.pointofsales.dialogs.ReprintZReadingDialog;
import nerdvana.com.pointofsales.dialogs.SetupPrinterDialog;
import nerdvana.com.pointofsales.dialogs.ShiftCutOffMenuDialog;
import nerdvana.com.pointofsales.dialogs.SwitchRoomDialog;
import nerdvana.com.pointofsales.dialogs.TransactionsDialog;
import nerdvana.com.pointofsales.dialogs.ViewReceiptDialog;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.entities.PaymentEntity;
import nerdvana.com.pointofsales.entities.RoomEntity;
import nerdvana.com.pointofsales.entities.TransactionEntity;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.interfaces.RetrieveCartItemContract;
import nerdvana.com.pointofsales.interfaces.SaveTransactionContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.ChangeThemeModel;
import nerdvana.com.pointofsales.model.ClearSearchData;
import nerdvana.com.pointofsales.model.ForVoidDiscountModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.GlobalServerTime;
import nerdvana.com.pointofsales.model.GuestReceiptInfoModel;
import nerdvana.com.pointofsales.model.InfoModel;
import nerdvana.com.pointofsales.model.ItemScannedModel;
import nerdvana.com.pointofsales.model.LogoutUserAction;
import nerdvana.com.pointofsales.model.OrderSlipModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.RoomWelcomeNotifier;
import nerdvana.com.pointofsales.model.SelectedProductsInBundleModel;
import nerdvana.com.pointofsales.model.SwitchRoomPrintModel;
import nerdvana.com.pointofsales.model.UpdateProductModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

public class LeftFrameFragment extends Fragment implements AsyncContract, CheckoutItemsContract,
         SaveTransactionContract, RetrieveCartItemContract, View.OnClickListener {
    Call<XReadResponse> xreadApiRequest;
    Call<ResponseBody> zreadApiRequest;
    Call<AddRoomPriceResponse> addProductApiRequest;
    Call<AddProductToResponse> addProductToApiRequest;
    Call<FetchOrderPendingViaControlNoResponse> fetchOrderPendingViaControlNoApiRequest;
    Call<FetchRoomPendingResponse> fetchRoomPendingApiRequest;



    DialogBundleComposition dialogBundleComposition;
    //regiondialogs
    ShiftCutOffMenuDialog shiftCutOffMenuDialog;
    PaymentDialog checkoutDialog;
    RateDialog rateDialog;
    PaymentDialog advancePaymentDialog;
    SwitchRoomDialog switchRoomDialog;
    TransactionsDialog postVoid;
    TransactionsDialog transactionsDialog;
    GuestInfoDialog guestInfoDialog;
    DiscountSelectionDialog discountSelectionDialog;
    OrderSlipDialog orderSlipDialog;
    RemoveOtDialog removeOtDialog;
    PasswordDialog passwordDialog;
    ConfirmWithRemarksDialog backoutGuestDialog;
    EmployeeSelectionDialog employeeSelectionDialog;
    CollectionDialog safeKeepingDialog;
    CollectionDialog spotAuditDialog;
    private InputDialog inputDialog;

    //endregion

    private String pShiftNumber = "";

    private String depositInfoData = "0.00";

    private boolean hasExistingRequest = false;


    AddGuestDialog addGuestDialog;
    FreebiesDialog freebiesDialog;
    private String globalServerTimeString = "";
    private String kitchenPath = "";
    private String printerPath = "";

    private AlertDialog blockerDialog;
    private CollectionDialog cutOffDialog;
    private Data data;
    private String employeeId = "";
    private List<ForVoidDiscountModel> forVoidDiscountModels = new ArrayList<>();

    private FetchRoomPendingResponse.Result fetchRoomPendingResult = null;
    private FetchOrderPendingViaControlNoResponse.Result fetchOrderPendingRresult = null;

    private LayoutAnimationController anim;

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

    private TextView depositLabel;
    private TextView subTotalLabel;
    private TextView discountLabel;
    private TextView totalLabel;

    private TextView total;
    private TextView discount;
    private TextView deposit;
    private TextView subTotal;
    private TextView header;
    private CardView cardHeader;
    private RelativeLayout cardHeaderRoot;
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
        depositLabel = view.findViewById(R.id.depositLabel);
        discountLabel = view.findViewById(R.id.discountLabel);
        subTotalLabel = view.findViewById(R.id.subTotalLabel);
        totalLabel = view.findViewById(R.id.totalLabel);




        total = view.findViewById(R.id.totalValue);
        discount = view.findViewById(R.id.discountValue);
        deposit = view.findViewById(R.id.depositValue);
        subTotal = view.findViewById(R.id.subTotalValue);
        checkoutSwipe = view.findViewById(R.id.checkoutSwipe);
        listCheckoutItems = view.findViewById(R.id.listCheckoutItems);
        listButtons = view.findViewById(R.id.listButtons);

        noItems = view.findViewById(R.id.notItems);
        header = view.findViewById(R.id.header);
        cardHeader = view.findViewById(R.id.cardHeader);
        cardHeaderRoot = view.findViewById(R.id.cardHeaderRoot);
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

        anim = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);

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

//        fetchXReadViaIdRequest("5");
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
                        //dione
                        fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                    } else {
                        //dione
                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                    }
                } else {
                    checkoutSwipe.setRefreshing(false);
                }
            }
        });

        detectSystem();


//        printReceiptFromCheckout("VCHI-2019-00000002", "TEST PRINT", "TEST ROOM TYPE");

//        fetchXReadViaIdRequest("5");

        return view;
    }

    private void setProductAdapter() {

        checkoutAdapter = new CheckoutAdapter(cartItemList, this, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        listCheckoutItems.setLayoutManager(linearLayoutManager);
        listCheckoutItems.setLayoutAnimation(anim);
        listCheckoutItems.setAdapter(checkoutAdapter);
        listCheckoutItems.scheduleLayoutAnimation();
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

//        printReceiptFromCheckout("VCHI-2019-00000005", "ttete", "tet");


        //dione start here
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

            checkoutAdapter = new CheckoutAdapter(cartItemList, this, getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//            linearLayoutManager.setReverseLayout(true);
//            linearLayoutManager.setStackFromEnd(true);
            listCheckoutItems.setLayoutManager(linearLayoutManager);
            listCheckoutItems.setLayoutAnimation(anim);
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
        if (fetchRoomPendingApiRequest != null) {
            fetchRoomPendingApiRequest.cancel();
            fetchRoomPendingApiRequest = null;
        }

        if (fetchOrderPendingViaControlNoApiRequest != null) {
            fetchOrderPendingViaControlNoApiRequest.cancel();
            fetchOrderPendingViaControlNoApiRequest = null;
        }

        if (addProductApiRequest != null) {
            addProductApiRequest.cancel();
            addProductApiRequest = null;
        }

        if (addProductToApiRequest != null) {
            addProductToApiRequest.cancel();
            addProductToApiRequest = null;
        }

        if (xreadApiRequest != null) {
            xreadApiRequest.cancel();
            xreadApiRequest = null;
        }

        if (zreadApiRequest != null) {
            zreadApiRequest.cancel();
            zreadApiRequest = null;
        }


        Log.d("HEYNAPAUSEAKO", "HEY NAPAUSE");
//        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fetchRoomPendingApiRequest != null) {
            fetchRoomPendingApiRequest.request();
        }

        if (fetchOrderPendingViaControlNoApiRequest != null) {
            fetchOrderPendingViaControlNoApiRequest.request();
        }
        Log.d("HEYNAPAUSEAKO", "HEY NARESUME");
//        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void updateRoom(UpdateDataModel updateDataModel) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (selectedRoom != null) {
                        if (selectedRoom.getName().equalsIgnoreCase(updateDataModel.getRoomno())) {
                            if (!selectedRoom.getStatus().equalsIgnoreCase(updateDataModel.getStatus())) {
                                clearCartItems();
                                defaultView();
                            }
                        }
                    }
                }
            });
        }



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
    public void productsClicked(final ProductsModel productsModel) {
        switch (Utils.getSystemType(getContext())) {
            case "not_supported":
                Utils.showDialogMessage(getActivity(), "System not supported", "Information");
                break;
            case "franchise":
                if (productsModel.getBranchGroupList().size() > 0) {
                    dialogBundleComposition = new DialogBundleComposition(
                            getActivity(),
                            new ArrayList<FetchProductsResponse.BranchGroup>(productsModel.getBranchGroupList()),
                            productsModel.getPrice(),
                            productsModel.getQty()) {
                        @Override
                        public void bundleCompleted(List<SelectedProductsInBundleModel> selectedProductsInBundleModelList) {

                            ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                            ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
                            ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
                            ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();
                            for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                                groupCompoList = new ArrayList<>();
                                groupCompoProductsList = new ArrayList<>();
                                for (SelectedProductsInBundleModel.BundleProductModel bpm : sipm.getBundleProductModelList()) {
                                    groupCompoProductsList.add(
                                            new AddRateProductModel(
                                                    String.valueOf(bpm.getProductId()),
                                                    "0",
                                                    String.valueOf(bpm.getQty()),
                                                    SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                    String.valueOf(bpm.getAmount()),
                                                    0,
                                                    bpm.getName(),
                                                    new ArrayList<AddRateProductModel.AlaCarte>(),
                                                    new ArrayList<AddRateProductModel.Group>(),
                                                    ""
                                            ));

                                }
                                groupLst.add(new AddRateProductModel.Group(new AddRateProductModel.GroupCompo(sipm.getGroupId(), sipm.getGroupName(), sipm.getTotalQtySelected(),groupCompoProductsList)));
                            }

                            if (productsModel.getBranchAlaCartList().size() > 0) {
                                for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {
                                    DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                    DateTime companyUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getUpdatedAt()));
                                    Double amount = balac.getBranchProduct().getAmount();
                                    if (balac.getBranchProduct().getBranchPrice() != null) {
                                        DateTime branchUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getBranchPrice().getUpdatedAt()));
                                        if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                                            amount = balac.getBranchProduct().getBranchPrice().getAmount();
                                            amount = ((amount * (balac.getBranchProduct().getBranchPrice().getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE));
                                        }
                                    }
                                    alaCartes.add(new AddRateProductModel.AlaCarte(
                                            String.valueOf(balac.getBranchProduct().getCoreId()),
                                            "0",
                                            String.valueOf(balac.getQty()),
                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                            String.valueOf(amount),
                                            0,
                                            balac.getBranchProduct().getProductInitial()

                                    ));
                                }
                            }

                            if (selectedProductsInBundleModelList.size() > 0) {
                                cartItemList.add(new CartItemsModel(
                                        "",
                                        0,
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
                                        "to",
                                        alaCartes,
                                        groupLst,
                                        false,
                                        null,
                                        productsModel.getRemarks()
                                ));
                            }

                            if (checkoutAdapter != null) {
                                checkoutAdapter.notifyDataSetChanged();
                                listCheckoutItems.scheduleLayoutAnimation();
                            }

                        }
                    };
                    dialogBundleComposition.show();
                } else {

                    ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                    if (productsModel.getBranchAlaCartList().size() > 0) {
                        for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {

                            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                            DateTime companyUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getUpdatedAt()));
                            Double amount = balac.getBranchProduct().getAmount();
                            if (balac.getBranchProduct().getBranchPrice() != null) {
                                DateTime branchUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getBranchPrice().getUpdatedAt()));
                                if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                                    amount = balac.getBranchProduct().getBranchPrice().getAmount();
                                    amount = ((amount * (balac.getBranchProduct().getBranchPrice().getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE));
                                }
                            }
                            alaCartes.add(new AddRateProductModel.AlaCarte(
                                    String.valueOf(balac.getBranchProduct().getCoreId()),
                                    "0",
                                    String.valueOf(balac.getQty()),
                                    SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                    String.valueOf(amount),
                                    0,
                                    balac.getBranchProduct().getProductInitial()

                            ));
                        }
                    }
                    cartItemList.add(new CartItemsModel(
                            "",
                            0,
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
                            "to",
                            alaCartes,
                            new ArrayList<AddRateProductModel.Group>(),
                            false,
                            null,
                            productsModel.getRemarks()
                    ));
                }

                if (checkoutAdapter != null) {
                    checkoutAdapter.notifyDataSetChanged();
                    listCheckoutItems.scheduleLayoutAnimation();
                }


                break;
            case "table":
                break;
            case "room":
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        if (productsModel.getBranchGroupList().size() > 0) {
                            dialogBundleComposition = new DialogBundleComposition(
                                    getActivity(),
                                    productsModel.getBranchGroupList(),
                                    productsModel.getPrice(),
                                    productsModel.getQty()) {
                                @Override
                                public void bundleCompleted(List<SelectedProductsInBundleModel> selectedProductsInBundleModelList) {
                                    computeTotal(productsModel);
                                    double addPrice = 0.00;
                                    for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                                        for (SelectedProductsInBundleModel.BundleProductModel bpm : sipm.getBundleProductModelList()) {
                                            addPrice += bpm.getAddPrice() * bpm.getQty();
                                        }
                                    }

                                    ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                                    ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
                                    ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
                                    ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();
                                    for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                                        groupCompoList = new ArrayList<>();
                                        groupCompoProductsList = new ArrayList<>();
                                        for (SelectedProductsInBundleModel.BundleProductModel bpm : sipm.getBundleProductModelList()) {
                                            groupCompoProductsList.add(
                                                    new AddRateProductModel(
                                                            String.valueOf(bpm.getProductId()),
                                                            "0",
                                                            String.valueOf(bpm.getQty()),
                                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                            String.valueOf(bpm.getAmount()),
                                                            0,
                                                            bpm.getName(),
                                                            new ArrayList<AddRateProductModel.AlaCarte>(),
                                                            new ArrayList<AddRateProductModel.Group>(),
                                                            ""
                                                    ));

                                        }
                                        groupLst.add(new AddRateProductModel.Group(new AddRateProductModel.GroupCompo(sipm.getGroupId(), sipm.getGroupName(), sipm.getTotalQtySelected(),groupCompoProductsList)));
                                    }

                                    if (productsModel.getBranchAlaCartList().size() > 0) {
                                        for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {

                                            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                            DateTime companyUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getUpdatedAt()));
                                            Double amount = balac.getBranchProduct().getAmount();
                                            if (balac.getBranchProduct().getBranchPrice() != null) {
                                                DateTime branchUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getBranchPrice().getUpdatedAt()));
                                                if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                                                    amount = balac.getBranchProduct().getBranchPrice().getAmount();
                                                    amount = ((amount * (balac.getBranchProduct().getBranchPrice().getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE));
                                                }
                                            }
                                            alaCartes.add(new AddRateProductModel.AlaCarte(
                                                    String.valueOf(balac.getBranchProduct().getCoreId()),
                                                    "0",
                                                    String.valueOf(balac.getQty()),
                                                    SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                    String.valueOf(amount),
                                                    0,
                                                    balac.getBranchProduct().getProductInitial()

                                            ));
                                        }
                                    }

                                    if (selectedProductsInBundleModelList.size() > 0) {
                                        cartItemList.add(new CartItemsModel(
                                                selectedRoom.getControlNo(),
                                                0,
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
                                                productsModel.getUnitPrice() + addPrice,
                                                false,
                                                "",
                                                false,
                                                "to",
                                                alaCartes,
                                                groupLst,
                                                false,
                                                null,
                                                productsModel.getRemarks()
                                        ));
                                    }

                                    if (checkoutAdapter != null) {
                                        checkoutAdapter.notifyDataSetChanged();
                                    }

                                }
                            };
                            dialogBundleComposition.show();
                        } else {

                            ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                            if (productsModel.getBranchAlaCartList().size() > 0) {
                                for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {

                                    DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                    DateTime companyUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getUpdatedAt()));
                                    Double amount = balac.getBranchProduct().getAmount();
                                    if (balac.getBranchProduct().getBranchPrice() != null) {
                                        DateTime branchUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getBranchPrice().getUpdatedAt()));
                                        if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                                            amount = balac.getBranchProduct().getBranchPrice().getAmount();
                                            amount = ((amount * (balac.getBranchProduct().getBranchPrice().getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE));
                                        }
                                    }
                                    alaCartes.add(new AddRateProductModel.AlaCarte(
                                            String.valueOf(balac.getBranchProduct().getCoreId()),
                                            "0",
                                            String.valueOf(balac.getQty()),
                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                            String.valueOf(amount),
                                            0,
                                            balac.getBranchProduct().getProductInitial()

                                    ));
                                }
                            }

                            computeTotal(productsModel);
                            cartItemList.add(new CartItemsModel(
                                    selectedRoom.getControlNo(),
                                    0,
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
                                    "to",
                                    alaCartes,
                                    new ArrayList<AddRateProductModel.Group>(),
                                    false,
                                    null,
                                    productsModel.getRemarks()
                            ));
                        }

                        if (checkoutAdapter != null) {
                            checkoutAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                                currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA) ||
                                currentRoomStatus.equalsIgnoreCase("32") ||
                                currentRoomStatus.equalsIgnoreCase("4")) {

                            if (productsModel.getBranchGroupList().size() > 0) {
                                dialogBundleComposition = new DialogBundleComposition(
                                        getActivity(),
                                        productsModel.getBranchGroupList(),
                                        productsModel.getPrice(),
                                        productsModel.getQty()) {
                                    @Override
                                    public void bundleCompleted(List<SelectedProductsInBundleModel> selectedProductsInBundleModelList) {
                                        computeTotal(productsModel);
                                        double addPrice = 0.00;
                                        for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                                            for (SelectedProductsInBundleModel.BundleProductModel bpm : sipm.getBundleProductModelList()) {
                                                addPrice += bpm.getAddPrice() * bpm.getQty();
                                            }
                                        }

                                        ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                                        ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
                                        ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
                                        ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();
                                        for (SelectedProductsInBundleModel sipm : selectedProductsInBundleModelList) {
                                            groupCompoList = new ArrayList<>();
                                            groupCompoProductsList = new ArrayList<>();
                                            for (SelectedProductsInBundleModel.BundleProductModel bpm : sipm.getBundleProductModelList()) {
                                                groupCompoProductsList.add(
                                                        new AddRateProductModel(
                                                                String.valueOf(bpm.getProductId()),
                                                                "0",
                                                                String.valueOf(bpm.getQty()),
                                                                SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                                String.valueOf(bpm.getAmount()),
                                                                0,
                                                                bpm.getName(),
                                                                new ArrayList<AddRateProductModel.AlaCarte>(),
                                                                new ArrayList<AddRateProductModel.Group>(),
                                                                ""
                                                        ));

                                            }
                                            groupLst.add(new AddRateProductModel.Group(new AddRateProductModel.GroupCompo(sipm.getGroupId(), sipm.getGroupName(), sipm.getTotalQtySelected(),groupCompoProductsList)));
                                        }

                                        if (productsModel.getBranchAlaCartList().size() > 0) {
                                            for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {

                                                alaCartes.add(new AddRateProductModel.AlaCarte(
                                                        String.valueOf(balac.getBranchProduct().getCoreId()),
                                                        "0",
                                                        String.valueOf(balac.getQty()),
                                                        SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                        String.valueOf(balac.getPrice()),
                                                        0,
                                                        balac.getBranchProduct().getProductInitial()

                                                ));
                                            }
                                        }

                                        if (selectedProductsInBundleModelList.size() > 0) {
                                            cartItemList.add(
                                                    roomRateCounter.size(),
                                                    new CartItemsModel(
                                                            "",
                                                            selectedRoom.getRoomId(),
                                                            productsModel.getProductId(),
                                                            0,
                                                            0,
                                                            0,
                                                            productsModel.getName(),
                                                            true,
                                                            productsModel.getPrice(),
                                                            productsModel.getProductId(),
                                                            productsModel.getQty(),
                                                            false,
                                                            0.00,
                                                            0,
                                                            productsModel.getPrice() + addPrice,
                                                            false,
                                                            "",
                                                            false,
                                                            "room",
                                                            alaCartes,
                                                            groupLst,
                                                            false,
                                                            null,
                                                            productsModel.getRemarks())
                                            );
                                        }

                                        if (checkoutAdapter != null) {
                                            checkoutAdapter.notifyDataSetChanged();
                                        }

                                    }
                                };
                                dialogBundleComposition.show();
                            } else {
                                ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                                if (productsModel.getBranchAlaCartList().size() > 0) {

                                    for (FetchProductsResponse.BranchAlaCart balac : productsModel.getBranchAlaCartList()) {
                                        if (balac.getBranchProduct() != null) {
                                            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                                            DateTime companyUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getUpdatedAt()));
                                            Double amount = balac.getBranchProduct().getAmount();
                                            if (balac.getBranchProduct().getBranchPrice() != null) {
                                                DateTime branchUpdatedAt = new DateTime(df.parseDateTime(balac.getBranchProduct().getBranchPrice().getUpdatedAt()));
                                                if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                                                    amount = balac.getBranchProduct().getBranchPrice().getAmount();
                                                    amount = ((amount * (balac.getBranchProduct().getBranchPrice().getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE));
                                                }
                                            }



                                            alaCartes.add(new AddRateProductModel.AlaCarte(
                                                    String.valueOf(balac.getBranchProduct().getCoreId()),
                                                    "0",
                                                    String.valueOf(balac.getQty()),
                                                    SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                    String.valueOf(amount),
                                                    0,
                                                    balac.getBranchProduct().getProductInitial()

                                            ));
                                        }

                                    }
                                }

                                computeTotal(productsModel);
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
                                        "room",
                                        alaCartes,
                                        new ArrayList<AddRateProductModel.Group>(),
                                        false,
                                        null,
                                        productsModel.getRemarks()
                                ));
                            }
                            if (checkoutAdapter != null) {
                                checkoutAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), "Room not occupied", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Please select a room first", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        if (noItems.getVisibility() == View.VISIBLE) noItems.setVisibility(View.GONE);
    }

    @Override
    public void itemAdded(ProductsModel itemAdded) {
//        retrieveCartItems();
//        listCheckoutItems.scrollToPosition(checkoutAdapter.getItemCount() - 1);
        computeTotal(itemAdded);
    }

    private void subtractTotal(Double amountToSubtract) {


        amountToPay -= amountToSubtract;
        String cleanString = total.getText().toString().replace(",", "");
        Log.d("CLEANSTRING", cleanString);
        Double tmpTotal = Double.valueOf(cleanString);
        Double totalBalance = tmpTotal - Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(amountToSubtract)));
        subTotal.setText(Utils.digitsWithComma(totalBalance));
        total.setText(Utils.digitsWithComma(totalBalance));
    }

    private void computeTotal(ProductsModel itemAdded) {
        amountToPay = itemAdded.getPrice() * itemAdded.getQty();
        String cleanString = total.getText().toString().replace(",", "");
        Log.d("CLEANSTRING", cleanString);
        Double tmpTotal = Double.valueOf(cleanString);
        Double totalBalance = tmpTotal + Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(amountToPay)));
        subTotal.setText(Utils.digitsWithComma(totalBalance));
        total.setText(Utils.digitsWithComma(totalBalance));
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
    public void itemLongClicked(final CartItemsModel itemSelected, final int position, View view, String type) {


        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_checkout_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addRemarks:
                        if (itemSelected.isPosted() && itemSelected.isProduct()) {
                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity(), true, itemSelected.getRemarks()) {
                                @Override
                                public void save(String remarks) {

                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    AddProductRemarksRequest addProductRemarksRequest = new AddProductRemarksRequest(itemSelected.getPostId(), remarks);
                                    Call<ResponseBody> request = iUsers.addRemarksToProduct(addProductRemarksRequest.getMapValue());
                                    request.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (selectedRoom != null) {
                                                if (selectedRoom.isTakeOut()) {
                                                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                                } else {
                                                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                                }


                                                final ArrayList<AddRateProductModel> model = new ArrayList<>();


                                                for (CartItemsModel cim : cartItemList) {
                                                    if (itemSelected.getProductId() == cim.getProductId()) {

                                                        model.add(new AddRateProductModel(
                                                                String.valueOf(cim.getProductId()),
                                                                "0",
                                                                String.valueOf(cim.getQuantity()),
                                                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                                                String.valueOf(cim.getUnitPrice()),
                                                                cim.getIsPriceChanged(),
                                                                cim.getName(),
                                                                cim.getAlaCarteList(),
                                                                cim.getGroupList(),
                                                                remarks
                                                        ));


                                                        break;
                                                    }

                                                }


                                                if (model.size() > 0) {
                                                    BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath, "", kitchenPath, printerPath));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                }
                            };
                            confirmWithRemarksDialog.show();
                        } else {
                            Utils.showDialogMessage(getActivity(), "Item not yet posted / Item selected is room rate", "Information");
                        }

                        break;
                    case R.id.changeQty:
                        final OpenPriceDialog openPriceDialog =
                                new OpenPriceDialog(getActivity(),
                                        itemSelected,
                                        position,
                                        itemSelected.isPosted(),
                                        "qty") {
                                    @Override
                                    public void openPriceChangeSuccess(final int quantity, final Double newPrice, final int position, String type) {

                                        if (type.equalsIgnoreCase("qty")) {
                                            if (itemSelected.isPosted()) {
                                                PasswordDialog pwd = new PasswordDialog(getActivity(),"CONFIRM CHANGE QTY", "") {
                                                    @Override
                                                    public void passwordSuccess(String employeeId, String employeeName) {

                                                        BusProvider.getInstance().post(new PrintModel(
                                                                "",
                                                                selectedRoom.getName(),
                                                                "CHANGE_QTY",
                                                                GsonHelper.getGson().toJson(cartItemList.get(position)),
                                                                quantity));


                                                        if (cartItemList.get(position).isPosted()) {
                                                            cartItemList.get(position).setPosted(false);
                                                            cartItemList.get(position).setUpdated(true);
                                                            cartItemList.get(position).setForVoid(false);
                                                        }
                                                        cartItemList.get(position).setUnitPrice(newPrice);
                                                        if (quantity != 0) {
                                                            cartItemList.get(position).setQuantity(quantity);
                                                        }
                                                        cartItemList.get(position).setIsPriceChanged(1);
                                                        if (checkoutAdapter != null) {
                                                            checkoutAdapter.notifyItemChanged(position);
                                                        }
                                                        doSaveFunction();


                                                        dismiss();
                                                    }

                                                    @Override
                                                    public void passwordFailed() {

                                                    }
                                                };
                                                pwd.show();
                                            } else {
                                                if (cartItemList.get(position).isPosted()) {
                                                    cartItemList.get(position).setPosted(false);
                                                    cartItemList.get(position).setUpdated(true);
                                                    cartItemList.get(position).setForVoid(false);
                                                }
                                                cartItemList.get(position).setUnitPrice(newPrice);
                                                if (quantity != 0) {
                                                    cartItemList.get(position).setQuantity(quantity);
                                                }
                                                cartItemList.get(position).setIsPriceChanged(1);
                                                if (checkoutAdapter != null) {
                                                    checkoutAdapter.notifyItemChanged(position);
                                                }
                                                dismiss();
                                            }


                                        }
                                        dismiss();
                                    }
                                };

                        if (itemSelected.isPosted()) {
                            Utils.showDialogMessage(getActivity(), "Cannot change qty, item already posted", "Information");
                        } else {
                            openPriceDialog.show();
                        }


                        break;
                    case R.id.voidItem:
                        doVoidFunction();
                        break;
                    case R.id.changePrice:
                        final OpenPriceDialog openPriceDialog1 =
                                new OpenPriceDialog(getActivity(),
                                        itemSelected,
                                        position,
                                        itemSelected.isPosted(),
                                        "price") {
                            @Override
                            public void openPriceChangeSuccess(final int quantity, final Double newPrice, final int position, String type) {

                                if (type.equalsIgnoreCase("qty")) {

                                    BusProvider.getInstance().post(new PrintModel(
                                            "",
                                            selectedRoom.getName(),
                                            "CHANGE_QTY",
                                            GsonHelper.getGson().toJson(cartItemList.get(position)),
                                            quantity));


                                } else {

                                    if (cartItemList.get(position).isPosted()) {
                                        PasswordDialog passwordDialog3 = new PasswordDialog(getActivity(),"CONFIRM CHANGE PRICE", "") {
                                            @Override
                                            public void passwordSuccess(String employeeId, String employeeName) {
                                                if (cartItemList.get(position).isPosted()) {
                                                    cartItemList.get(position).setPosted(false);
                                                    cartItemList.get(position).setUpdated(true);
                                                    cartItemList.get(position).setForVoid(false);
                                                }
                                                cartItemList.get(position).setUnitPrice(newPrice);
                                                if (quantity != 0) {
                                                    cartItemList.get(position).setQuantity(quantity);
                                                }
                                                cartItemList.get(position).setIsPriceChanged(1);
                                                if (checkoutAdapter != null) {
                                                    checkoutAdapter.notifyItemChanged(position);
                                                }
                                                dismiss();
                                            }

                                            @Override
                                            public void passwordFailed() {

                                            }
                                        };
                                        passwordDialog3.show();
                                    } else {
                                        if (cartItemList.get(position).isPosted()) {
                                            cartItemList.get(position).setPosted(false);
                                            cartItemList.get(position).setUpdated(true);
                                            cartItemList.get(position).setForVoid(false);
                                        }
                                        cartItemList.get(position).setUnitPrice(newPrice);
                                        if (quantity != 0) {
                                            cartItemList.get(position).setQuantity(quantity);
                                        }
                                        cartItemList.get(position).setIsPriceChanged(1);
                                        if (checkoutAdapter != null) {
                                            checkoutAdapter.notifyItemChanged(position);
                                        }
                                        dismiss();
                                    }


                                }
                                dismiss();
                            }
                        };
                        openPriceDialog1.show();
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
                    case R.id.changeRoomQuantity:
                        final OpenPriceDialog changeRoomPriceDialog =
                                new OpenPriceDialog(
                                        getActivity(),
                                        itemSelected,
                                        position,
                                        itemSelected.isPosted(),
                                        "qty") {
                                    @Override
                                    public void openPriceChangeSuccess(final int quantity, final Double newPrice, final int position, String type) {

                                        if (type.equalsIgnoreCase("qty")) {

                                            PasswordDialog pwd1 = new PasswordDialog(getActivity(),"CONFIRM CHANGE QTY", "") {
                                                @Override
                                                public void passwordSuccess(String employeeId, String employeeName) {

                                                    BusProvider.getInstance().post(new PrintModel(
                                                            "",
                                                            selectedRoom.getName(),
                                                            "CHANGE_QTY",
                                                            GsonHelper.getGson().toJson(cartItemList.get(position)),
                                                            quantity));


                                                    if (cartItemList.get(position).isPosted()) {
                                                        cartItemList.get(position).setPosted(false);
                                                        cartItemList.get(position).setUpdated(true);
                                                        cartItemList.get(position).setForVoid(false);
                                                    }
                                                    cartItemList.get(position).setUnitPrice(newPrice);
                                                    if (quantity != 0) {
                                                        cartItemList.get(position).setQuantity(quantity);
                                                    }
                                                    cartItemList.get(position).setIsPriceChanged(1);
                                                    if (checkoutAdapter != null) {
                                                        checkoutAdapter.notifyItemChanged(position);
                                                    }

                                                    doSaveFunction();


                                                    dismiss();
                                                }

                                                @Override
                                                public void passwordFailed() {

                                                }
                                            };
                                            pwd1.show();


                                        }
                                        dismiss();
                                    }
                                };

                        if (Utils.isPasswordProtected(getActivity(), "60")) {
                            passwordDialog = new PasswordDialog(getActivity(), "UPDATE(PASSWORD PROTECTED)", "") {
                                @Override
                                public void passwordSuccess(String employeeId, String employeeName) {
                                    changeRoomPriceDialog.show();
                                }

                                @Override
                                public void passwordFailed() {

                                }
                            };

                            passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    passwordDialog = null;
                                }
                            });

                            passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    passwordDialog = null;
                                }
                            });

                            passwordDialog.show();
                        } else {
                            changeRoomPriceDialog.show();
                        }

                        break;
                    case R.id.changeRoomPrice:
                        final OpenPriceDialog changeRoomPriceDialog1 =
                                new OpenPriceDialog(
                                        getActivity(),
                                        itemSelected,
                                        position,
                                        itemSelected.isPosted(),
                                        "price") {
                            @Override
                            public void openPriceChangeSuccess(final int quantity, final Double newPrice, final int position, String type) {

                                if (type.equalsIgnoreCase("qty")) {
                                    BusProvider.getInstance().post(new PrintModel(
                                            "",
                                            selectedRoom.getName(),
                                            "CHANGE_QTY",
                                            GsonHelper.getGson().toJson(cartItemList.get(position)),
                                            quantity));
                                } else {

                                    if (cartItemList.get(position).isPosted()) {
                                        cartItemList.get(position).setPosted(false);
                                        cartItemList.get(position).setUpdated(true);
                                        cartItemList.get(position).setForVoid(false);
                                    }
                                    cartItemList.get(position).setUnitPrice(newPrice);
                                    if (quantity != 0) {
                                        cartItemList.get(position).setQuantity(quantity);
                                    }
                                    cartItemList.get(position).setIsPriceChanged(1);
                                    if (checkoutAdapter != null) {
                                        checkoutAdapter.notifyItemChanged(position);
                                    }

                                }
                                dismiss();
                            }
                        };


                        if (Utils.isPasswordProtected(getActivity(), "60")) {
                            if (passwordDialog == null) {
                                passwordDialog = new PasswordDialog(getActivity(), "UPDATE(PASSWORD PROTECTED)", "") {
                                    @Override
                                    public void passwordSuccess(String employeeId, String employeeName) {
                                        changeRoomPriceDialog1.show();
                                    }

                                    @Override
                                    public void passwordFailed() {

                                    }
                                };
                                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        passwordDialog = null;
                                    }
                                });

                                passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        passwordDialog = null;
                                    }
                                });

                                passwordDialog.show();
                            }
                        } else {
                            changeRoomPriceDialog1.show();
                        }

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

            if (type.equalsIgnoreCase("misc")) {
                PopupMenu editGuestMenu = new PopupMenu(getActivity(), view);
                editGuestMenu.getMenuInflater().inflate(R.menu.menu_edit_guest, editGuestMenu.getMenu());
                editGuestMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editGuest:
                                addGuestDialog = new AddGuestDialog(
                                        getActivity(),
                                        fetchRoomPendingResult.getBooked().get(0).getTransaction().getPersonCount(),
                                        String.valueOf(selectedRoom.getRoomId()),
                                        fetchRoomPendingResult.getBooked().get(0).getTransaction().getSpecial(),
                                        true) {
                                    @Override
                                    public void editGuestSucess() {
                                        if (selectedRoom != null) {
                                            Utils.showDialogMessage(getActivity(), "Guest count edit success", "Information");
                                            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                            dismiss();
                                        }
                                    }
                                };

                                addGuestDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        addGuestDialog = null;
                                    }
                                });

                                addGuestDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        addGuestDialog = null;
                                    }
                                });

                                addGuestDialog.show();
                                Toast.makeText(getContext(), "Edit Guest count", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });

                editGuestMenu.show();


            } else {
                if (itemSelected.isProduct()) {
                    popupMenu.show();
                }  else {
                    roomPopupMenu.show();
                }
            }


        }


    }

    private void changeShiftFunction() {
        Toast.makeText(getContext(), "CHANGE SHIFT", Toast.LENGTH_SHORT).show();
    }

    private void doFocFunction(String adminPassword) {


        Log.d("FOCBALANC", String.valueOf((totalBalance - (advancePayment + discountPayment))));
        boolean isValid = true;
        String errorMessage = "";


        if (selectedRoom != null) {

            if ((totalBalance - (advancePayment + discountPayment)) <= 0) {
                if (TextUtils.isEmpty(employeeId)) {
                    if (employeeSelectionDialog == null) {
                        employeeSelectionDialog = new EmployeeSelectionDialog(getActivity(), selectedRoom.getControlNo()) {
                            @Override
                            public void saveEmployeeToTransaction(final String userId, String wholeName) {

                                if (cartItemList.size() > 0) {
                                    if (selectedRoom.isTakeOut()) {


                                        BusProvider.getInstance().post(new FocTransactionRequest(
                                                "",
                                                selectedRoom.getControlNo(),
                                                adminPassword
                                        ));
                                    } else {
                                        BusProvider.getInstance().post(new FocTransactionRequest(
                                                String.valueOf(selectedRoom.getRoomId()),
                                                "",
                                                adminPassword
                                        ));
                                    }
                                } else {
                                    Utils.showDialogMessage(getActivity(), "Please add a product for FOC", "Information");
                                }
                            }
                        };

                        employeeSelectionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                employeeSelectionDialog = null;
                            }
                        });

                        employeeSelectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                employeeSelectionDialog = null;
                            }
                        });

                        employeeSelectionDialog.show();
                    }

                } else {

                    if (cartItemList.size() > 0) {
                        if (selectedRoom.isTakeOut()) {
                            BusProvider.getInstance().post(new FocTransactionRequest(
                                    "",
                                    selectedRoom.getControlNo(),
                                    adminPassword
                            ));
                        } else {
                            BusProvider.getInstance().post(new FocTransactionRequest(
                                    String.valueOf(selectedRoom.getRoomId()),
                                    "",
                                    adminPassword
                            ));
                        }
                    } else {
                        Utils.showDialogMessage(getActivity(), "Please add a product for FOC", "Information");
                    }



                }
            } else {
                Utils.showDialogMessage(getActivity(), "Cannot FOC a transaction with balance.", "Information");
            }


        } else {
            Utils.showDialogMessage(getActivity(), "Please select a room", "Information");
        }





    }

    private boolean isAllowedToTransact() {
        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }


    private boolean canTransact() {
        return true;
//        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("please execute end of day")) {
//            Toast.makeText(getContext(), "Please execute end of day", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("NOT_ALLOW") ||
//                SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("") ||
//                SharedPreferenceManager.getString(getContext(), ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("please execute cutoff")) {
//            Toast.makeText(getContext(), "Please execute cutoff", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
    }


    @Subscribe
    public void clickedButton(ButtonsModel clickedItem) {
        if (Utils.isPasswordProtected(getContext(), String.valueOf(clickedItem.getCore_id()))) {
            if (passwordDialog == null) {
                passwordDialog = new PasswordDialog(getActivity(),clickedItem.getName()+"(PASSWORD PROTECTED)", "") {

                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        Log.d("WEKWEK", "TEST");
                        doSwitchCaseFunction(clickedItem, employeeId);
                    }

                    @Override
                    public void passwordFailed() {

                    }
                };
                passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        passwordDialog = null;
                    }
                });

                passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        passwordDialog = null;
                    }
                });

                passwordDialog.show();
            }
        } else {
            doSwitchCaseFunction(clickedItem, "");
        }

    }

    private void doSwitchCaseFunction(ButtonsModel clickedItem, String adminPassword) {
        if (TextUtils.isEmpty(adminPassword)) {
            adminPassword = SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID);
        }

        Log.d("IDTRACING", adminPassword);
        Log.d("IDTRACING", "UTUT");
        switch (clickedItem.getId()) {
            case 109://REPRINT SK
                ReprintSkDialog reprintSkDialog = new ReprintSkDialog(getActivity());
                reprintSkDialog.show();
                break;
            case 500://REPRINT X READ V2
                ReprintActualXReadReceiptDialog reprintActualXReadReceiptDialog = new ReprintActualXReadReceiptDialog(getActivity());
                reprintActualXReadReceiptDialog.show();
                break;
            case 501://REPRINT ZREAD V2
                ReprintActualZReadReceiptDialog reprintActualZReadReceiptDialog = new ReprintActualZReadReceiptDialog(getActivity());
                reprintActualZReadReceiptDialog.show();
                break;
            case 996://upon completion remove view receipt :: 112
                ViewReceiptDialog viewReceiptDialog = new ViewReceiptDialog(getActivity());
                viewReceiptDialog.show();
                break;
            case 133://SHOW SHIT CUT OFF DIALOG(X READ,Z READ, REPRINTS)
                if (shiftCutOffMenuDialog == null) {
                    shiftCutOffMenuDialog = new ShiftCutOffMenuDialog(getActivity());


                    shiftCutOffMenuDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            shiftCutOffMenuDialog = null;
                        }
                    });

                    shiftCutOffMenuDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            shiftCutOffMenuDialog = null;
                        }
                    });

                    if (!shiftCutOffMenuDialog.isShowing()) {
                        shiftCutOffMenuDialog.show();
                    }

                }
                break;
            case 132: //PRINT SPOT AUDIT


                if (spotAuditDialog == null) {
                    spotAuditDialog = new CollectionDialog(getActivity(), "Spot Audit", false, pShiftNumber, adminPassword, 0) {
                        @Override
                        public void printCashRecoData(String cashNRecoData) {

                        }
                    };

                    spotAuditDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            spotAuditDialog = null;
                        }
                    });

                    spotAuditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            spotAuditDialog = null;
                        }
                    });

                    if (!spotAuditDialog.isShowing()) spotAuditDialog.show();
                }

                break;
            case 131: //ADD GUEST COUNT
                if (selectedRoom != null) {
                    if (addGuestDialog == null) {
                        addGuestDialog = new AddGuestDialog(getActivity(),
                                fetchRoomPendingResult.getBooked().get(0).getTransaction().getPersonCount(),
                                String.valueOf(selectedRoom.getRoomId()),
                                fetchRoomPendingResult.getBooked().get(0).getTransaction().getSpecial(),
                                false) {
                            @Override
                            public void editGuestSucess() {
                                if (selectedRoom != null) {
                                    Utils.showDialogMessage(getActivity(), "Guest count edit success", "Information");
                                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                    dismiss();
                                }
                            }
                        };

                        addGuestDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                addGuestDialog = null;
                            }
                        });

                        addGuestDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                addGuestDialog = null;
                            }
                        });

                        if (!addGuestDialog.isShowing()) addGuestDialog.show();
                    }
                }

                break;
            case 130: //CHECK FOR ROOM FREEBIES / ROOM BUNDLE
                if (fetchRoomPendingResult != null) {
                    if (fetchRoomPendingResult.getBooked().get(0).getTransaction().getFreebiesList().size() > 0) {
                        boolean hasValidData = false;
                        if (freebiesDialog == null) {

                            for (FetchRoomPendingResponse.Freebies freebies : fetchRoomPendingResult.getBooked().get(0).getTransaction().getFreebiesList()) {
                                if (freebies.getFreebyRoomRatePrice() != null) {
                                    hasValidData = true;
                                    break;
                                }
                            }


                            freebiesDialog = new FreebiesDialog(
                                    getActivity(),
                                    fetchRoomPendingResult.getBooked().get(0).getTransaction().getFreebiesList(),
                                    selectedRoom,
                                    1,
                                    kitchenPath,
                                    printerPath) {
                                @Override
                                public void freebySelected() {
                                    if (freebiesDialog != null) {
                                        freebiesDialog.dismiss();
                                    }
                                }
                            };

                            freebiesDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    freebiesDialog = null;
                                }
                            });

                            freebiesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    freebiesDialog = null;
                                }
                            });
                        }

                        if (!freebiesDialog.isShowing()) {
                            if (hasValidData) {
                                freebiesDialog.show();
                            } else {
                                Utils.showDialogMessage(getActivity(), "No freebies , e", "Information");
                            }

                        }
                    } else {
                        Utils.showDialogMessage(getActivity(), "No freebies", "Information");
                    }
                }
                break;
            case 129: //setting for app, new activity
                Intent settingIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingIntent);

                break;
            case 128: //backup
                //check main activity for function
                break;
            case 127://REPRINT Z READ

                ReprintZReadingDialog reprintZReadingDialog = new ReprintZReadingDialog(getActivity());
                reprintZReadingDialog.show();

                break;
            case 126: //FOC
                if (canTransact()) {
                    doFocFunction(adminPassword);
                }
                break;
            case 124: //BACKOUT
                if (canTransact()) {
                    doBackOutGuestFunction(adminPassword);
                }
                break;
            case 123: //REPRINT X/Z READING
                if (passwordDialog == null) {
                    passwordDialog = new PasswordDialog(getActivity(),"CONFIRM X/Z READ DATA ACCESS", "") {
                        @Override
                        public void passwordSuccess(String employeeId, String employeeName) {
                            ReprintXReadingDialog reprintXReadingDialog = new ReprintXReadingDialog(getActivity());
                            reprintXReadingDialog.show();
                        }

                        @Override
                        public void passwordFailed() {

                        }
                    };

                    passwordDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            passwordDialog = null;
                        }
                    });

                    passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            passwordDialog = null;
                        }
                    });
                }

                if (!passwordDialog.isShowing()) {
                    passwordDialog.show();
                }

                break;
            case 9999: //rooms
                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            BusProvider.getInstance().post(new ButtonsModel(999,"ROOMS", "",1, 0));
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    BusProvider.getInstance().post(new ButtonsModel(999,"ROOMS", "",1, 0));
                }
                break;
            case 9988: //take order
                if (hasUnpostedItems()) {
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            BusProvider.getInstance().post(new ButtonsModel(998,"TAKE ORDER", "",2, 0));
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    BusProvider.getInstance().post(new ButtonsModel(998,"TAKE ORDER", "",2, 0));
                }
                break;
            case 122: //CANCEL OVERTIME

                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        String finalAdminPassword = adminPassword;
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {

                                cancelOverTime(finalAdminPassword);
                            }


                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();
                    } else {
                        cancelOverTime(adminPassword);
                    }
                }
                break;
            case 121: //CASH AND RECONCILE
                if (hasUnpostedItems()) {
                    String finalAdminPassword1 = adminPassword;
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            cashNReconcile(finalAdminPassword1, clickedItem.getFromPopUp());
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    cashNReconcile(adminPassword, clickedItem.getFromPopUp());
                }
                break;
            case 120: //Z READ, END OF DAY
                if (hasUnpostedItems()) {
                    String finalAdminPassword2 = adminPassword;
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            zReadRequest(finalAdminPassword2);
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    zReadRequest(adminPassword);
                }
                break;
            case 119: //X READ, SHIFT CUT OFF


                if (hasUnpostedItems()) {
                    String finalAdminPassword3 = adminPassword;
                    AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                        @Override
                        public void yesClicked() {
                            xReadRequest(finalAdminPassword3);
                        }

                        @Override
                        public void noClicked() {

                        }
                    };
                    alertYesNo.show();
                } else {
                    xReadRequest(adminPassword);
                }




                break;
            case 118:// SAFEKEEPING

                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        String finalAdminPassword4 = adminPassword;
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {
                                doSafeKeepFunction(finalAdminPassword4);
                            }

                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();
                    } else {
                        doSafeKeepFunction(adminPassword);
                    }
                }



                break;
            case 116: //cancel selected room / TO
                defaultView();
                clearCartItems();

                detectSystem();
                break;
            case 115://DISCOUNT
                switch (Utils.getSystemType(getContext())) {
                    case "room":
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
                }





                break;
            case 114://SWITCH ROOM
                if (canTransact()) {
                    if (selectedRoom != null) {
                        if (hasUnpostedItems()) {
                            String finalAdminPassword6 = adminPassword;
                            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                                @Override
                                public void yesClicked() {
                                    doSwitchRoomFunction(finalAdminPassword6);
                                }

                                @Override
                                public void noClicked() {

                                }
                            };
                            alertYesNo.show();
                        } else {
                            doSwitchRoomFunction(adminPassword);
                        }


                    } else {
                        Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                    }
                }
                break;
            case 113://POST VOID

                if (canTransact()) {
                    if (hasUnpostedItems()) {
                        String finalAdminPassword5 = adminPassword;
                        AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                            @Override
                            public void yesClicked() {
                                doPostVoidFunction(finalAdminPassword5);
                            }

                            @Override
                            public void noClicked() {

                            }
                        };
                        alertYesNo.show();
                    } else {
                        doPostVoidFunction(adminPassword);
                    }
                }



                break;
            case 112://VIEW RECEIPT
                if (transactionsDialog == null) {
                    transactionsDialog = new TransactionsDialog(getActivity(),
                            true, getActivity(),
                            adminPassword) {
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


                                postVoidPrinter(toListPV.getControlNo(), toListPV.getPost().get(0).getRoomNo(), toListPV.getPost().get(0).getRoomType());
//                                BusProvider.getInstance().post(new PrintModel("",
//                                        toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomNo() : "TAKEOUT",
//                                        "POST_VOID",
//                                        jsonData,
//                                        toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomType() : ""));
                            }


                            defaultView();
                            clearCartItems();
                        }
                    };

                    transactionsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            transactionsDialog = null;
                        }
                    });

                    transactionsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            transactionsDialog = null;
                        }
                    });
                }


                if (!transactionsDialog.isShowing()) {
                    transactionsDialog.show();
                }


                break;
            case 111://GUEST INFO
                if (fetchRoomPendingResult != null) {
                    if (canTransact()) {
                        if (guestInfoDialog == null) {


                            guestInfoDialog = new GuestInfoDialog(getActivity(), fetchRoomPendingResult, getActivity(), globalServerTimeString) {
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

                            guestInfoDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    guestInfoDialog = null;
                                }
                            });

                            guestInfoDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    guestInfoDialog = null;
                                }
                            });
                        }


                        if (selectedRoom != null) {
                            if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                                    currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA)) {
                                if (!TextUtils.isEmpty(globalServerTimeString)) {
                                    guestInfoDialog.show();
                                }
                            } else {
                                Utils.showDialogMessage(getActivity(), "No guest info yet", "Information");
                            }
                        } else {
                            Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                        }
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
                if (orderSlipDialog == null) {
                    orderSlipDialog = new OrderSlipDialog(getActivity(), orderSlipList, roomNumber, kitchenPath, printerPath);

                    orderSlipDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            orderSlipDialog = null;
                        }
                    });

                    orderSlipDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            orderSlipDialog = null;
                        }
                    });
                }

                if (selectedRoom != null) {
                    if (!orderSlipDialog.isShowing()) orderSlipDialog.show();
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

                doSaveFunction();

                break;
            case 101: //VOID

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
                    Log.d("QWEQWE", "NO ROOM SELECTED");
                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                }


                if (canTransact()) {


                }

                break;
            case 102: //ADVANCE PAYMENT

                switch (Utils.getSystemType(getContext())) {
                    case "room":
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
                break;
        }
    }

    private void doSaveFunction() {
        switch (Utils.getSystemType(getContext())) {
            case "not_supported":
                Utils.showDialogMessage(getActivity(), "System not supported", "Information");
                break;
            case "franchise":
                if (selectedRoom == null) {
                    //create new and add
                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                    GetOrderRequest getOrderRequest = new GetOrderRequest("EMPTY", "1", "");
                    Call<GetOrderResponse> request = iUsers.getOrder(getOrderRequest.getMapValue());
                    request.enqueue(new Callback<GetOrderResponse>() {
                        @Override
                        public void onResponse(Call<GetOrderResponse> call, final Response<GetOrderResponse> response) {
                            selectedRoom = new RoomTableModel(response.body().getResult().getControlNo(), true);
                            fetchOrderPendingViaControlNo(selectedRoom.getControlNo());


                            final ArrayList<AddRateProductModel> model = new ArrayList<>();
                            final ArrayList<VoidProductModel> voidModel = new ArrayList<>();
                            final ArrayList<UpdateProductModel> updateModel = new ArrayList<>();
                            for (CartItemsModel cim : cartItemList) {
                                if (!cim.isPosted()) {

                                    if (cim.isUpdated()) {
                                        updateModel.add(new UpdateProductModel(
                                                cim.getPostId(),
                                                cim.getName(),
                                                String.valueOf(cim.getUnitPrice()),
                                                String.valueOf(cim.getQuantity())
                                        ));
                                    } else {
                                        model.add(new AddRateProductModel(
                                                String.valueOf(cim.getProductId()),
                                                "0",
                                                String.valueOf(cim.getQuantity()),
                                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                                String.valueOf(cim.getUnitPrice()),
                                                cim.getIsPriceChanged(),
                                                cim.getName(),
                                                cim.getAlaCarteList(),
                                                cim.getGroupList(),
                                                ""
                                        ));
                                    }

                                }



                                if (cim.isForVoid()) {
                                    voidModel.add(new VoidProductModel(
                                            cim.getPostId(),
                                            cim.getName(),
                                            String.valueOf(cim.getAmount()),
                                            String.valueOf(cim.getQuantity()),
                                            ""
                                    ));
                                }
                            }

                            if (model.size() == 0 && voidModel.size() == 0 && updateModel.size() == 0) {
                                Utils.showDialogMessage(getActivity(), "Please select item/s to save", "Information");
                            } else {
//                                        BusProvider.getInstance().post(
//                                                new AddProductToRequest(
//                                                        model,
//                                                        "",
//                                                        "1",
//                                                        selectedRoom.getControlNo(),
//                                                        voidModel,
//                                                        "",
//                                                        "0",
//                                                        "0",
//                                                        updateModel));

                                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                Call<AddProductToResponse> request = iUsers.addProductTo(new AddProductToRequest(
                                        model,
                                        "",
                                        "1",
                                        selectedRoom.getControlNo(),
                                        voidModel,
                                        "",
                                        "0",
                                        "0",
                                        updateModel).getMapValue());

                                request.enqueue(new Callback<AddProductToResponse>() {
                                    @Override
                                    public void onResponse(Call<AddProductToResponse> call, Response<AddProductToResponse> response) {
                                        detectSystem();
                                    }

                                    @Override
                                    public void onFailure(Call<AddProductToResponse> call, Throwable t) {

                                    }
                                });


                            }

                            SocketManager.reloadPosGraph();


                        }

                        @Override
                        public void onFailure(Call<GetOrderResponse> call, Throwable t) {

                        }
                    });


                } else {
                    //just add
                    final ArrayList<AddRateProductModel> model = new ArrayList<>();
                    final ArrayList<VoidProductModel> voidModel = new ArrayList<>();
                    final ArrayList<UpdateProductModel> updateModel = new ArrayList<>();
                    for (CartItemsModel cim : cartItemList) {
                        if (!cim.isPosted()) {

                            if (cim.isUpdated()) {
                                updateModel.add(new UpdateProductModel(
                                        cim.getPostId(),
                                        cim.getName(),
                                        String.valueOf(cim.getUnitPrice()),
                                        String.valueOf(cim.getQuantity())
                                ));
                            } else {
                                model.add(new AddRateProductModel(
                                        String.valueOf(cim.getProductId()),
                                        "0",
                                        String.valueOf(cim.getQuantity()),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                        String.valueOf(cim.getUnitPrice()),
                                        cim.getIsPriceChanged(),
                                        cim.getName(),
                                        cim.getAlaCarteList(),
                                        cim.getGroupList(),
                                        ""
                                ));
                            }

                        }



                        if (cim.isForVoid()) {
                            voidModel.add(new VoidProductModel(
                                    cim.getPostId(),
                                    cim.getName(),
                                    String.valueOf(cim.getAmount()),
                                    String.valueOf(cim.getQuantity()),
                                    ""
                            ));
                        }
                    }

                    if (model.size() == 0 && voidModel.size() == 0 && updateModel.size() == 0) {
                        Utils.showDialogMessage(getActivity(), "Please select item/s to save", "Information");
                    } else {
//                                BusProvider.getInstance().post(
//                                        new AddProductToRequest(
//                                                model,
//                                                "",
//                                                "1",
//                                                selectedRoom.getControlNo(),
//                                                voidModel,
//                                                "",
//                                                "0",
//                                                "0",
//                                                updateModel));



                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<AddProductToResponse> request = iUsers.addProductTo(new AddProductToRequest(
                                model,
                                "",
                                "1",
                                selectedRoom.getControlNo(),
                                voidModel,
                                "",
                                "0",
                                "0",
                                updateModel).getMapValue());

                        request.enqueue(new Callback<AddProductToResponse>() {
                            @Override
                            public void onResponse(Call<AddProductToResponse> call, Response<AddProductToResponse> response) {
                                detectSystem();
                            }

                            @Override
                            public void onFailure(Call<AddProductToResponse> call, Throwable t) {

                            }
                        });


                    }
                }
                break;
            case "table":
                break;
            case "room":
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        final ArrayList<AddRateProductModel> model = new ArrayList<>();
                        final ArrayList<VoidProductModel> voidModel = new ArrayList<>();
                        final ArrayList<UpdateProductModel> updateModel = new ArrayList<>();
                        for (CartItemsModel cim : cartItemList) {
                            if (!cim.isPosted()) {

                                if (cim.isUpdated()) {
                                    updateModel.add(new UpdateProductModel(
                                            cim.getPostId(),
                                            cim.getName(),
                                            String.valueOf(cim.getUnitPrice()),
                                            String.valueOf(cim.getQuantity())
                                    ));
                                } else {
                                    model.add(new AddRateProductModel(
                                            String.valueOf(cim.getProductId()),
                                            "0",
                                            String.valueOf(cim.getQuantity()),
                                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                            String.valueOf(cim.getUnitPrice()),
                                            cim.getIsPriceChanged(),
                                            cim.getName(),
                                            cim.getAlaCarteList(),
                                            cim.getGroupList(),
                                            ""
                                    ));
                                }

                            }



                            if (cim.isForVoid()) {
                                voidModel.add(new VoidProductModel(
                                        cim.getPostId(),
                                        cim.getName(),
                                        String.valueOf(cim.getAmount()),
                                        String.valueOf(cim.getQuantity()),
                                        ""
                                ));
                            }
                        }

                        if (model.size() > 0) {
                            BusProvider.getInstance().post(new PrintModel("", "TAKEOUT", "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath, "",kitchenPath, printerPath));
                        }



                        if (model.size() == 0 && voidModel.size() == 0 && updateModel.size() == 0) {
                            Utils.showDialogMessage(getActivity(), "Please select item/s to order", "Information");
                        } else {

                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                            if (addProductToApiRequest == null) {
                                AddProductToRequest addProductToRequest = new AddProductToRequest(
                                        model,
                                        String.valueOf(selectedRoom.getRoomId()),
                                        String.valueOf(selectedRoom.getAreaId()),
                                        selectedRoom.getControlNo(),
                                        voidModel,
                                        "",
                                        "0",
                                        "0",
                                        updateModel);
                                addProductToApiRequest = iUsers.addProductTo(addProductToRequest.getMapValue());
                                addProductToApiRequest.enqueue(new Callback<AddProductToResponse>() {
                                    @Override
                                    public void onResponse(Call<AddProductToResponse> call, Response<AddProductToResponse> response) {
                                        addProductToApiRequest = null;

                                        if (response.code() == 200) {
                                            if (response.body() != null) {

                                                if (selectedRoom != null) {
                                                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                                }
                                                endLoading();

                                            } else {
                                                Toast.makeText(getContext(), "Response body of add product to is null", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "There is an error add product to", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<AddProductToResponse> call, Throwable t) {
                                        addProductToApiRequest = null;
                                        Toast.makeText(getContext(), "ADD PRODUCT TO " + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });


                            } else {
                                Toast.makeText(getContext(), "ADD PRODUCT TO REQUEST STILL ON GOING", Toast.LENGTH_LONG).show();
                            }



//                            BusProvider.getInstance().post(
//                                    new AddProductToRequest(
//                                            model,
//                                            String.valueOf(selectedRoom.getRoomId()),
//                                            String.valueOf(selectedRoom.getAreaId()),
//                                            selectedRoom.getControlNo(),
//                                            voidModel,
//                                            "",
//                                            "0",
//                                            "0",
//                                            updateModel));
                            showLoading();
                        }
                    } else {
                        if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                                currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA) ||
                                selectedRoom.getStatus().equalsIgnoreCase("4") ||
                                selectedRoom.getStatus().equalsIgnoreCase("32") ||
                                selectedRoom.getStatus().equalsIgnoreCase("59")) {
                            final ArrayList<AddRateProductModel> model = new ArrayList<>();
                            final ArrayList<UpdateProductModel> updateModel = new ArrayList<>();

                            for (CartItemsModel cim : cartItemList) {
                                if (!cim.isPosted()) {

                                    if (cim.isUpdated()) {
                                        updateModel.add(new UpdateProductModel(
                                                cim.getPostId(),
                                                cim.getName(),
                                                String.valueOf(cim.getUnitPrice()),
                                                String.valueOf(cim.getQuantity())
                                        ));
                                    } else {
                                        model.add(new AddRateProductModel(
                                                String.valueOf(cim.getProductId()),
                                                "0",
                                                String.valueOf(cim.getQuantity()),
                                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                                String.valueOf(cim.getUnitPrice()),
                                                cim.getIsPriceChanged(),
                                                cim.getName(),
                                                cim.getAlaCarteList(),
                                                cim.getGroupList(),
                                                ""
                                        ));
                                    }

                                }
                            }



                            if (model.size() == 0 && updateModel.size() == 0) {
                                Utils.showDialogMessage(getActivity(), "Please select item/s to order", "Information");
                            } else {
                                if (model.size() > 0) {
                                    BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model), kitchenPath, printerPath, "",kitchenPath, printerPath));
                                }


                                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                if (addProductApiRequest == null) {
                                    AddRoomPriceRequest addRoomPriceRequest = new AddRoomPriceRequest(
                                            model,
                                            String.valueOf(selectedRoom.getRoomId()),
                                            new ArrayList<VoidProductModel>(),
                                            "",
                                            "",
                                            "0", "0",
                                            updateModel);

                                    addProductApiRequest = iUsers.sendAddRoomPriceRequest(addRoomPriceRequest.getMapValue());
                                    addProductApiRequest.enqueue(new Callback<AddRoomPriceResponse>() {
                                        @Override
                                        public void onResponse(Call<AddRoomPriceResponse> call, Response<AddRoomPriceResponse> response) {
                                            addProductApiRequest = null;

                                            if (response.code() == 200) {
                                                if (response.body() != null) {

                                                    if (selectedRoom!=null) {
                                                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                                    }
                                                    endLoading();

                                                } else {
                                                    Toast.makeText(getContext(), "Response body of add product is null", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "There is an error add product ", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AddRoomPriceResponse> call, Throwable t) {
                                            addProductApiRequest = null;
                                            Toast.makeText(getContext(), "ADD PRODUCT " + t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });


                                } else {
                                    Toast.makeText(getContext(), "ADD PRODUCT REQ STILL ON GOING" , Toast.LENGTH_LONG).show();
                                }




//                                BusProvider.getInstance().post(new AddRoomPriceRequest(
//                                        model,
//                                        String.valueOf(selectedRoom.getRoomId()),
//                                        new ArrayList<VoidProductModel>(),
//                                        "",
//                                        "",
//                                        "0", "0",
//                                        updateModel));
                                showLoading();
                            }
                        }
                    }
                }
                break;
        }
    }

    private void doBackOutGuestFunction(String adminPassword) {
        if (selectedRoom != null) {
            if (hasUnpostedItems()) {
                AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
                    @Override
                    public void yesClicked() {

                        final ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity(), true) {
                            @Override
                            public void save(final String remarks) {


                                if (!selectedRoom.isTakeOut()) {
                                    BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest(String.valueOf(selectedRoom.getRoomId()), remarks, "", adminPassword);
                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                    request.enqueue(new Callback<BackOutGuestResponse>() {
                                        @Override
                                        public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {


                                            selectedRoom.setDepositInfo(depositInfoData);

                                            SocketManager.reloadPosBackoutRoom(
                                                    selectedRoom.getName(),
                                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME)
                                            );
                                            BusProvider.getInstance().post(new PrintModel("",
                                                    selectedRoom.getName(),
                                                    "BACKOUT",
                                                    GsonHelper.getGson().toJson(selectedRoom),
                                                    selectedRoom.getRoomType(),
                                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                                    remarks,
                                                    kitchenPath, printerPath));
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
                                    BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest("", remarks, selectedRoom.getControlNo(), adminPassword);
                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                    request.enqueue(new Callback<BackOutGuestResponse>() {
                                        @Override
                                        public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {


                                            selectedRoom.setDepositInfo(depositInfoData);

                                            SocketManager.reloadPosBackoutRoom(
                                                    selectedRoom.getName(),
                                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME)
                                            );
                                            BusProvider.getInstance().post(new PrintModel("",
                                                    selectedRoom.getName(),
                                                    "BACKOUT",
                                                    GsonHelper.getGson().toJson(selectedRoom),
                                                    selectedRoom.getRoomType(),
                                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                                    remarks,
                                                    kitchenPath, printerPath));
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
                ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity(), true) {
                    @Override
                    public void save(final String remarks) {

                        Log.d("BACKOUTTRACE", "START");
                        if (selectedRoom != null) {
                            Log.d("BACKOUTTRACE", "START 1");
                            if (!selectedRoom.isTakeOut()) {
                                Log.d("BACKOUTTRACE", "START 1-1");
                                BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest(String.valueOf(selectedRoom.getRoomId()), remarks, "", adminPassword);
                                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                request.enqueue(new Callback<BackOutGuestResponse>() {
                                    @Override
                                    public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {


                                        selectedRoom.setDepositInfo(depositInfoData);

                                        SocketManager.reloadPosBackoutRoom(
                                                selectedRoom.getName(),
                                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME)
                                        );


                                        BusProvider.getInstance().post(new PrintModel("",
                                                selectedRoom.getName(),
                                                "BACKOUT",
                                                GsonHelper.getGson().toJson(selectedRoom),
                                                selectedRoom.getRoomType(),adminPassword,
                                                remarks,
                                                kitchenPath, printerPath));
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
                                Log.d("BACKOUTTRACE", "START 1-2");
                                BackOutGuestRequest backOutGuestRequest = new BackOutGuestRequest("", remarks, selectedRoom.getControlNo(), adminPassword);
                                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                Call<BackOutGuestResponse> request = iUsers.backOutGuest(backOutGuestRequest.getMapValue());
                                request.enqueue(new Callback<BackOutGuestResponse>() {
                                    @Override
                                    public void onResponse(Call<BackOutGuestResponse> call, Response<BackOutGuestResponse> response) {


                                        selectedRoom.setDepositInfo(depositInfoData);

                                        SocketManager.reloadPosBackoutRoom(
                                                selectedRoom.getName(),
                                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME)
                                        );


                                        BusProvider.getInstance().post(new PrintModel("",
                                                selectedRoom.getName(),
                                                "BACKOUT",
                                                GsonHelper.getGson().toJson(selectedRoom),
                                                selectedRoom.getRoomType(),adminPassword,
                                                remarks,
                                                kitchenPath, printerPath));
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


                    }
                };
                if (!confirmWithRemarksDialog.isShowing()) confirmWithRemarksDialog.show();
            }




        } else {
            Utils.showDialogMessage(getActivity(), "No room selected", "Information");
        }

    }

    private void doSafeKeepFunction(String adminPassword) {

        if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {
            if (safeKeepingDialog == null) {
                ApplicationConstants.IS_ACTIVE = "T";
                safeKeepingDialog = new CollectionDialog(getActivity(), "SAFEKEEPING", false, pShiftNumber, adminPassword, 0) {
                    @Override
                    public void printCashRecoData(String cashNRecoData) {

                    }
                };

                safeKeepingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        ApplicationConstants.IS_ACTIVE = "F";
                        safeKeepingDialog = null;
                    }
                });

                safeKeepingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ApplicationConstants.IS_ACTIVE = "F";
                        safeKeepingDialog = null;
                    }
                });
                if (!safeKeepingDialog.isShowing()) safeKeepingDialog.show();
            }
        }


    }

    private void doPostVoidFunction(String adminPassword) {
        if (postVoid == null) {
            postVoid = new TransactionsDialog(getActivity(), false, getActivity(), adminPassword) {
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

                        postVoidPrinter(toListPV.getControlNo(), toListPV.getPost().get(0).getRoomNo(), toListPV.getPost().get(0).getRoomType());

//                        BusProvider.getInstance().post(new PrintModel("",
//                                toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomNo() : "TAKEOUT",
//                                "POST_VOID",
//                                jsonData,
//                                toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomType() : ""));
                    }


                    defaultView();
                    clearCartItems();
                }
            };

            postVoid.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    postVoid = null;
                }
            });

            postVoid.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    postVoid = null;
                }
            });
        }


        if (!postVoid.isShowing()) {
            postVoid.show();
        }
    }

    private void doDiscountFunction() {
        if (selectedRoom.isTakeOut()) {
            if (discountSelectionDialog == null) {
                discountSelectionDialog =
                        new DiscountSelectionDialog(getContext(),
                                getActivity(),
                                fetchRoomPendingResult,
                                selectedRoom.getControlNo(),
                                "",
                                forVoidDiscountModels,
                                data,
                                fetchOrderPendingRresult) {
                            @Override
                            public void fetchPending(String type) {

                                if (selectedRoom != null) {
                                    if (selectedRoom.isTakeOut()) {
                                        fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                    } else {
                                        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                    }
                                }
                            }
                        };

                discountSelectionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        discountSelectionDialog = null;
                    }
                });

                discountSelectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        discountSelectionDialog = null;
                    }
                });
            }


            if (!discountSelectionDialog.isShowing()) discountSelectionDialog.show();
        } else {
            if (discountSelectionDialog == null) {
                discountSelectionDialog =
                        new DiscountSelectionDialog(getContext(),
                                getActivity(),
                                fetchRoomPendingResult,
                                "",
                                String.valueOf(selectedRoom.getRoomId()),
                                forVoidDiscountModels,
                                data,
                                fetchOrderPendingRresult) {
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

                discountSelectionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        discountSelectionDialog = null;
                    }
                });

                discountSelectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        discountSelectionDialog = null;
                    }
                });
            }


            if (!discountSelectionDialog.isShowing()) discountSelectionDialog.show();
        }
    }

    private void doSwitchRoomFunction(String adminPassword) {
        if (switchRoomDialog == null) {
            switchRoomDialog = new SwitchRoomDialog(getActivity(), selectedRoom.getName()) {
                @Override
                public void switchRoomConfirm(final String roomRatePriceId, final String qty,
                                              final String price, final String rateName,
                                              final String roomId, List<RoomRateMain> roomRateMainList) {
                    ConfirmWithRemarksDialog cfrmDialog = new ConfirmWithRemarksDialog(getActivity(), true) {
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
                                            String.valueOf(cim.getQuantity()),
                                            ""
                                    ));
                                }
                            }

                            SwitchRoomRequest switchRoomRequest = new SwitchRoomRequest(
                                    String.valueOf(selectedRoom.getRoomId()),
                                    roomRatePriceId,
                                    remarks,
                                    roomId,
                                    adminPassword,
                                    voidModel
                            );

                            Log.d("SWITCHROOMDATA", switchRoomRequest.getMapValue().toString());
                            //uncomment fuckin switch room
                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                            Call<SwitchRoomResponse> request = iUsers.switchRoom(switchRoomRequest.getMapValue());



                            request.enqueue(new Callback<SwitchRoomResponse>() {
                                @Override
                                public void onResponse(Call<SwitchRoomResponse> call, Response<SwitchRoomResponse> response) {
                                    if (response.body().getStatus() == 0) {
                                        Utils.showDialogMessage(getActivity(), response.body().getMesage(), "Warning");
                                    } else {
                                        if (response.body().getResults() != null) {




                                            if (response.body().getResults().getBooked().size() > 0) {

                                                SocketManager.reloadPosSwitchRoom(
                                                        selectedRoom.getName(),
                                                        response.body().getResults().getBooked().get(0).getRoomNumber(),
                                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME));


                                                SwitchRoomPrintModel switchRoomPrintModel =
                                                        new SwitchRoomPrintModel(
                                                                selectedRoom.getName(),
                                                                selectedRoom.getRoomType(),
                                                                response.body().getResults().getBooked().get(0).getRoomNumber(),
                                                                response.body().getResults().getBooked().get(0).getRoomType(),
                                                                response.body().getResults().getBooked().get(0).getCheckInTime(),
                                                                response.body().getResults().getBooked().get(0).getUser_id());

                                                BusProvider.getInstance().post(
                                                        new PrintModel(
                                                                "",
                                                                response.body().getResults().getBooked().get(0).getRoomNumber(),
                                                                "SWITCH_ROOM" ,
                                                                GsonHelper.getGson().toJson(switchRoomPrintModel),
                                                                kitchenPath,
                                                                printerPath,
                                                                remarks,
                                                                kitchenPath, printerPath));

                                                //dione
                                                Handler hndl2 = new Handler(Looper.getMainLooper());
                                                hndl2.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        fetchRoomViaIdRequest(String.valueOf(response.body().getResults().getBooked().get(0).getRoomId()));
                                                    }
                                                }, 500);

                                                Utils.showDialogMessage(getActivity(), "Switch room succeeded", "Success");
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<SwitchRoomResponse> call, Throwable t) {

                                }
                            });



                        }
                    };

                    if (!cfrmDialog.isShowing()) cfrmDialog.show();
                }
            };

            switchRoomDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    switchRoomDialog = null;
                }
            });

            switchRoomDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    switchRoomDialog = null;
                }
            });
        }


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
                if (rateDialog == null) {
                    rateDialog = new RateDialog(getContext(), selectedRoom.getPrice()) {
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

                    rateDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            rateDialog = null;
                        }
                    });

                    rateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            rateDialog = null;
                        }
                    });
                }

                if (!rateDialog.isShowing()) {
                    rateDialog.show();
                }
            } else {

                Utils.showDialogMessage(getActivity(), "Room not yet occupied", "Information");
            }
        } else {

            Utils.showDialogMessage(getActivity(), "Adding rate feature is for rooms only", "Information");
        }

    }

    private void doAdvancePaymentFunction() {
        if (advancePaymentDialog == null) {
            advancePaymentDialog = new PaymentDialog(getActivity(),
                    paymentTypeList,
                    false,
                    postedPaymentsList,
                    totalBalance,
                    currencyList,
                    creditCardList,
                    arOnlineList,
                    discountPayment,
                    selectedRoom.getControlNo(),
                    guestReceiptInfoModel,
                    selectedRoom.isTakeOut(),
                    true) {
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

            advancePaymentDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    advancePaymentDialog = null;
                }
            });

            advancePaymentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    advancePaymentDialog = null;
                }
            });
        }

        if (selectedRoom.isTakeOut()) {
            if (paymentTypeList.size() > 0) {
                if (!advancePaymentDialog.isShowing()) {
                    advancePaymentDialog.show();
                }

            } else {
                Toast.makeText(getContext(), "No payment type found", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (currentRoomStatus.equalsIgnoreCase("2") ||
                    currentRoomStatus.equalsIgnoreCase("17")) {
                if (paymentTypeList.size() > 0) {

                    if (!advancePaymentDialog.isShowing()) {
                        advancePaymentDialog.show();
                    }


                } else {

                    Utils.showDialogMessage(getActivity(), "No payment type found, please re-open the application", "Information");
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

            boolean hasRemainingRate = true;
            int totalRoomCounter = 0;
            int roomCounterToVoid = 0;
            for (CartItemsModel cim : cartItemList) {
                Log.d("ITEM", cim.getName() + " - " + cim.isProduct());
                if (!cim.isProduct()) totalRoomCounter +=1;
                if (cim.isSelected()) {
                    if (!cim.isProduct()) roomCounterToVoid +=1;
                    model.add(new VoidProductModel(
                            cim.getPostId(),
                            cim.getName(),
                            String.valueOf(cim.getUnitPrice()),
                            String.valueOf(cim.getQuantity()),
                            ""
                    ));
                }
            }

            if (!selectedRoom.isTakeOut()) {
                if (totalRoomCounter == roomCounterToVoid) {
                    hasRemainingRate = false;
                }
            }

            if (hasRemainingRate == false) {
                Utils.showDialogMessage(getActivity(), "Cannot void the remaining room rate", "Information");
            } else {
                if (model.size() > 0) {
                    if (selectedRoom != null) {
                        Log.d("QWEQWE", String.valueOf(selectedRoom.isTakeOut()));
                        if (selectedRoom.isTakeOut()) {
                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {
                                    BusProvider.getInstance().post(new PrintModel("", "TAKEOUT "+ selectedRoom.getName(), "VOID", GsonHelper.getGson().toJson(model)));

                                    BusProvider.getInstance().post(new AddProductToRequest(new ArrayList<AddRateProductModel>(), String.valueOf(selectedRoom.getRoomId()),
                                            String.valueOf(selectedRoom.getAreaId()),
                                            selectedRoom.getControlNo(),
                                            model,
                                            remarks,
                                            "0",
                                            "0",
                                            new ArrayList<UpdateProductModel>()));
                                    showLoading();

                                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                }
                            };
                            confirmWithRemarksDialog.show();
                        } else {
                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {
                                    BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "VOID", GsonHelper.getGson().toJson(model)));


                                    BusProvider.getInstance().post(new AddRoomPriceRequest(new ArrayList<AddRateProductModel>(), String.valueOf(selectedRoom.getRoomId()),
                                            model, remarks,
                                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_ID),
                                            "0", "0",
                                            new ArrayList<UpdateProductModel>()));
                                    showLoading();
                                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                                }
                            };

                            confirmWithRemarksDialog.show();


                        }
                    }
                } else {
                    Utils.showDialogMessage(getActivity(), "Please select item/s to void", "Information");
                }
            }




        }
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Log.d("ITEMTOSUBTRACT", String.valueOf(cartItemList.get(viewHolder.getAdapterPosition()).getAmount()));
                Log.d("ITEMTOSUBTRACT", String.valueOf(cartItemList.get(viewHolder.getAdapterPosition()).getQuantity()));

                subtractTotal(cartItemList.get(viewHolder.getAdapterPosition()).getAmount() * cartItemList.get(viewHolder.getAdapterPosition()).getQuantity());
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

        total.setText(temp < 1 ? "0.00": Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(temp)))));
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

        sendFetchRoomPendingRequest(roomId);


        showLoading();


//        if (hasUnpostedItems()) {
//            AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
//                @Override
//                public void yesClicked() {
//
//
//                    sendFetchRoomPendingRequest(roomId);
//
//
//                    showLoading();
//                }
//
//                @Override
//                public void noClicked() {
//                    checkoutSwipe.setRefreshing(false);
//                }
//            };
//
//            alertYesNo.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    checkoutSwipe.setRefreshing(false);
//                }
//            });
//            alertYesNo.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    checkoutSwipe.setRefreshing(false);
//                }
//            });
//
//            alertYesNo.show();
//        } else {
////            BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));
//            sendFetchRoomPendingRequest(roomId);
//        }
    }

    private void sendFetchRoomPendingRequest(String roomId) {

//        BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        if (fetchRoomPendingApiRequest == null) {
            FetchRoomPendingRequest fetchRoomPendingRequest = new FetchRoomPendingRequest(roomId);
            fetchRoomPendingApiRequest = iUsers.sendFetchRoomPendingRequest(
                    fetchRoomPendingRequest.getMapValue());
            fetchRoomPendingApiRequest.enqueue(new Callback<FetchRoomPendingResponse>() {
                @Override
                public void onResponse(Call<FetchRoomPendingResponse> call, Response<FetchRoomPendingResponse> response) {
                    fetchRoomPendingApiRequest = null;

                    if (response.code() == 200) {
                        if (response.body() != null) {


                            BusProvider.getInstance().post(new ClearSearchData("d"));
                            forVoidDiscountModels = new ArrayList<>();
                            guestReceiptInfoModel = null;
                            checkoutSwipe.setRefreshing(false);

                            cartItemList = new ArrayList<>();
                            orderSlipList = new ArrayList<>();
                            postedPaymentsList = new ArrayList<>();
                            Double totalAmount = 0.00;
                            discountPayment = 0.00;
                            advancePayment = 0.00;
                            currentRoomStatus = String.valueOf(response.body().getResult().getStatus());
//        Utils.showDialogMessage(getActivity(), currentRoomStatus, "SIR PAKITANDAAN(DONT IGNORE)");
                            if (response.body().getResult().getBooked().size() > 0) {

                                kitchenPath = response.body().getResult().getBooked().get(0).getRoom().getArea().getKitchenPath();
                                printerPath = response.body().getResult().getBooked().get(0).getRoom().getArea().getPrinterPath();
                                for (FetchRoomPendingResponse.Booked r : response.body().getResult().getBooked()) {
//                                    if (!TextUtils.isEmpty(r.getRoomNo())) {
//                                        SocketManager.reloadTagboardCancelledSoa(r.getRoomNo(), SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID));
//                                    } else {
//                                        if (r.getRoom() != null) {
//                                            SocketManager.reloadTagboardCancelledSoa(r.getRoom().getRoomNo(), SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID));
//                                        }
//                                    }

                                    if (r.getTransaction() != null) {
                                        if (r.getTransaction().getEmployee_id() != null && !TextUtils.isEmpty(r.getTransaction().getEmployee_id())) {
                                            employeeId = r.getTransaction().getEmployee_id();
                                        }
                                        if (r.getTransaction().getCustomerTrans() != null) {
                                            if (r.getTransaction().getCustomerTrans() != null) {
                                                guestReceiptInfoModel = new GuestReceiptInfoModel(r.getTransaction().getCustomerTrans().getCustomer(),
                                                        r.getTransaction().getCustomerTrans().getAddress() != null ? r.getTransaction().getCustomerTrans().getAddress() : "",
                                                        r.getTransaction().getCustomerTrans().getTin() != null ? r.getTransaction().getCustomerTrans().getTin() : "");
                                            }
                                        }
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
                                                    String.valueOf(pym.getId()),
                                                    ""
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
                                        totalBalance = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((response.body().getResult().getBooked().get(0).getTransaction().getTotal() +
                                                Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(response.body().getResult().getBooked().get(0).getTransaction().getOtAmount()))) +
                                                Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(response.body().getResult().getBooked().get(0).getTransaction().getXPersonAmount()))))
//                            - (fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTendered());
                                                - (Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(response.body().getResult().getBooked().get(0).getTransaction().getTendered())))
                                                + Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(response.body().getResult().getBooked().get(0).getTransaction().getVatExempt())))))));

                                        advancePayment = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getAdvance())));
                                        depositInfoData = String.valueOf(advancePayment);
                                        discountPayment = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getDiscount())));
                                        subTotal.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(totalBalance)))));


                                        total.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((totalBalance - (advancePayment + discountPayment)) < 0 ? 0 : (totalBalance - (advancePayment + discountPayment)))))));
                                        if (discountPayment > totalBalance) {
                                            discount.setText(Utils.digitsWithComma(totalBalance));
                                        } else {
                                            discount.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(discountPayment)))));
                                        }
                                        if (r.getCheckIn() != null && !TextUtils.isEmpty(globalServerTimeString)) {

                                            if (selectedRoom.getRoomType() != null) {
                                                setView(selectedRoom.getName(), selectedRoom.getRoomType());
                                            } else {
                                                setView(selectedRoom.getName(), "TO");
                                            }



                                            header.setText(String.format("%s(%s)", header.getText().toString(),
                                                    Utils.durationOfStay(globalServerTimeString, r.getCheckIn().toString())));
                                        }
                                        deposit.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getAdvance())))));
                                        depositInfoData = String.valueOf(r.getTransaction().getAdvance());
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
                                            if (tpost.getTransactionPostFreebies() != null) {
                                                //whatis this
                                            }
                                            if (tpost.getRoomRateId() != null) {
                                                roomRateCounter.add(1);
                                                cartItemList.add(0, new CartItemsModel(
                                                        tpost.getControlNo(),
                                                        tpost.getRoomId() == null ? 0 : tpost.getRoomId(),
                                                        tpost.getProductId(),
                                                        tpost.getRoomTypeId() == null ? 0 : tpost.getRoomTypeId(),
                                                        tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
                                                        tpost.getRoomRatePriceId() == null ? 0 : tpost.getRoomRatePriceId(),
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
                                                        "room",
                                                        new ArrayList<AddRateProductModel.AlaCarte>(),
                                                        new ArrayList<AddRateProductModel.Group>(),
                                                        false,
                                                        tpost.getTransactionPostFreebies(),
                                                        TextUtils.isEmpty(tpost.getRemarks()) ? "" : tpost.getRemarks()
                                                ));
                                            } else {

                                                ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                                                ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
                                                ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
                                                ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();

                                                if (tpost.getPostGroupList() != null) {
                                                    for (FetchRoomPendingResponse.PostGroup sipm : tpost.getPostGroupList()) {
                                                        groupCompoList = new ArrayList<>();
                                                        groupCompoProductsList = new ArrayList<>();
//                                    if (sipm.getPostGroupInfo() != null) {
                                                        for (FetchRoomPendingResponse.PostGroupItem bpm : sipm.getPostGroupItems()) {
                                                            groupCompoProductsList.add(
                                                                    new AddRateProductModel(
                                                                            "",
                                                                            "0",
                                                                            String.valueOf(bpm.getQty()),
                                                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                                            "",
                                                                            0,
                                                                            bpm.getPostGroupItemProduct().getProduct(),
                                                                            new ArrayList<AddRateProductModel.AlaCarte>(),
                                                                            new ArrayList<AddRateProductModel.Group>(),
                                                                            ""
                                                                    ));

                                                        }
                                                        groupLst.add(
                                                                new AddRateProductModel.Group(
                                                                        new AddRateProductModel.GroupCompo(
                                                                                0,
                                                                                sipm.getPostGroupInfo() == null ? "PB" : sipm.getPostGroupInfo().getGroupName(),
                                                                                0,
                                                                                groupCompoProductsList)));
//                                    }
                                                    }
                                                }


                                                for (FetchRoomPendingResponse.PostAlaCart balac : tpost.getPostAlaCartList()) {


                                                    alaCartes.add(new AddRateProductModel.AlaCarte(
                                                            "",
                                                            "0",
                                                            String.valueOf(balac.getQty()),
                                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                            "0.00",
                                                            0,
                                                            balac.getPostAlaCartProduct().getProduct()
                                                    ));
                                                }


                                                cartItemList.add(roomRateCounter.size(), new CartItemsModel(
                                                        tpost.getControlNo(),
                                                        tpost.getRoomId() == null ? 0 : tpost.getRoomId(),
                                                        tpost.getProductId(),
                                                        tpost.getRoomTypeId() == null ? 0 :tpost.getRoomTypeId(),
                                                        tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
                                                        tpost.getRoomRatePriceId() == null ? 0 : tpost.getRoomRatePriceId(),
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
                                                        "room",
                                                        alaCartes,
                                                        groupLst,
                                                        false,
                                                        tpost.getTransactionPostFreebies(),
                                                        TextUtils.isEmpty(tpost.getRemarks()) ? "" : tpost.getRemarks()
                                                ));
                                            }

                                            totalAmount += tpost.getTotal();
                                        }
                                    }
                                    //endregion
                                    //region ot
                                    if (r.getTransaction().getOtHours() > 0) {
                                        cartItemList.add(new CartItemsModel(
                                                r.getTransaction().getControlNo(),
                                                0,
                                                0,
                                                0,
                                                0,
                                                0,
                                                "OT HOURS",
                                                true,
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
                                                "ot",
                                                new ArrayList<AddRateProductModel.AlaCarte>(),
                                                new ArrayList<AddRateProductModel.Group>(),
                                                false,
                                                null,
                                                ""
                                        ));
                                    }

                                    if (Integer.valueOf(r.getTransaction().getPersonCount()) > 2) {
                                        cartItemList.add(new CartItemsModel(
                                                r.getTransaction().getControlNo(),
                                                0,
                                                0,
                                                0,
                                                0,
                                                0,
                                                "EXTRA PERSON",
                                                true,
                                                r.getTransaction().getXPersonAmount(),
                                                0,
                                                Integer.valueOf(r.getTransaction().getPersonCount()) - 2,
                                                true,
                                                0.00,
                                                0,
                                                r.getTransaction().getXPersonAmount(),
                                                false,
                                                "",
                                                false,
                                                "misc",
                                                new ArrayList<AddRateProductModel.AlaCarte>(),
                                                new ArrayList<AddRateProductModel.Group>(),
                                                false,
                                                null,
                                                ""
                                        ));
                                    }

                                    //endregion

                                }
                            } else {
                                subTotal.setText("0.00");
                                discount.setText("0.00");
                                deposit.setText("0.00");
                                total.setText("0.00");
                                totalBalance = 0;
                            }


                            checkoutAdapter = new CheckoutAdapter(cartItemList, LeftFrameFragment.this, getContext());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            listCheckoutItems.setLayoutManager(linearLayoutManager);
                            listCheckoutItems.setLayoutAnimation(anim);
                            listCheckoutItems.setAdapter(checkoutAdapter);
                            checkoutAdapter.notifyDataSetChanged();


                            if (response.body().getResult() != null) {
                                fetchRoomPendingResult = response.body().getResult();
                                switch (response.body().getResult().getStatus()) {
                                    case 1://clean
                                        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
                                            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                                                showGuestInfoDialog(String.valueOf(response.body().getResult().getStatus()));
                                            } else {
                                                showCheckInNotAllowed();
                                            }
                                        } else {
                                            showCheckInNotAllowed();
                                        }

                                        break;
                                    case 3: //dirty

                                        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
                                            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                                                showGuestInfoDialog(
                                                        String.valueOf(response.body().getResult().getStatus()));
                                            } else {
                                                showCheckInNotAllowed();
                                            }
                                        } else {
                                            showCheckInNotAllowed();
                                        }


                                        break;
                                    case 19:
                                        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
                                            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                                                showGuestInfoDialog(String.valueOf(response.body().getResult().getStatus()));
                                            } else {
                                                showCheckInNotAllowed();
                                            }
                                        } else {
                                            showCheckInNotAllowed();
                                        }
                                        break;// ongoing nego
                                    case 20: //onnego show check in form
                                        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
                                            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                                                showGuestInfoDialog(String.valueOf(response.body().getResult().getStatus()));
                                            } else {
                                                showCheckInNotAllowed();
                                            }
                                        } else {
                                            showCheckInNotAllowed();
                                        }

                                        break;
                                    case 32:
//                    showCheckInDialog(fetchRoomPendingResponse.getResult());
                                        break;
                                    case 4:
//                    showCheckInDialog(fetchRoomPendingResponse.getResult());
                                        break;
                                    case 59: //check in guest
                                        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
                                            if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
                                                showCheckInDialog();
                                            } else {
                                                showCheckInNotAllowed();
                                            }
                                        } else {
                                            showCheckInNotAllowed();
                                        }
                                        break;

                                    case 2: //already checked in, can now order
//                    Toast.makeText(getContext(), "Please order", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
//                    showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
                                        break;
                                }
                            } else {
                                Toast.makeText(getContext(), "Call fetch room pending", Toast.LENGTH_SHORT).show();
                            }
                            endLoading();
                        } else {

                            Toast.makeText(getContext(), "Response body of fetch room pending is null", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Toast.makeText(getContext(), "There is an error in fetch room pending", Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<FetchRoomPendingResponse> call, Throwable t) {
                    fetchRoomPendingApiRequest = null;
                    Toast.makeText(getContext(), "FETCH ROOM PENDING " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
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

        sendFetchOrderPendingRequest(controlNo);

//        if (Utils.getSystemType(getContext()).equalsIgnoreCase("franchise")) {
////            BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(controlNo));
//            sendFetchOrderPendingRequest(controlNo);
//        } else {
//            if (hasUnpostedItems()) {
//                AlertYesNo alertYesNo = new AlertYesNo(getActivity(), ApplicationConstants.DISCARD_STRING) {
//                    @Override
//                    public void yesClicked() {
//                        sendFetchOrderPendingRequest(controlNo);
//
//                    }
//
//                    @Override
//                    public void noClicked() {
//                        checkoutSwipe.setRefreshing(false);
//                    }
//                };
//
//                alertYesNo.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        checkoutSwipe.setRefreshing(false);
//                    }
//                });
//                alertYesNo.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        checkoutSwipe.setRefreshing(false);
//                    }
//                });
//                alertYesNo.show();
//            } else {
////                BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(controlNo));
//                sendFetchOrderPendingRequest(controlNo);
//            }
//        }




    }

    private void sendFetchOrderPendingRequest(String controlNo) {
//        BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(controlNo));
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNo);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        if (fetchOrderPendingViaControlNoApiRequest == null) {
            fetchOrderPendingViaControlNoApiRequest = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
            fetchOrderPendingViaControlNoApiRequest.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
                @Override
                public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                    fetchOrderPendingViaControlNoApiRequest = null;

                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getResult() != null) {
                                fetchOrderPendingRresult = response.body().getResult();
                                fetchOrderPendingViaControlNumberFunction(response.body());
                            }
                        } else {

                            Toast.makeText(getContext(), "Response body of order pending is null", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "There is an error in fetch order pending", Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {
                    fetchOrderPendingViaControlNoApiRequest = null;
                    Toast.makeText(getContext(), "FETCH ORDER PENDING " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }


    }

    private void doCheckoutFunction() {

        switch (Utils.getSystemType(getContext())) {
            case "not_supported":
                Utils.showDialogMessage(getActivity(), "System not supported", "Information");
                break;
            case "franchise":
                if (selectedRoom == null) {
                    Utils.showDialogMessage(getActivity(), "No transaction", "Information");
                    return;
                } else {
                    if (cartItemList.size() > 0) {
                        final ArrayList<AddRateProductModel> model = new ArrayList<>();
                        final ArrayList<VoidProductModel> voidModel = new ArrayList<>();
                        final ArrayList<UpdateProductModel> updateModel = new ArrayList<>();
                        for (CartItemsModel cim : cartItemList) {
                            if (!cim.isPosted()) {

                                if (cim.isUpdated()) {
                                    updateModel.add(new UpdateProductModel(
                                            cim.getPostId(),
                                            cim.getName(),
                                            String.valueOf(cim.getUnitPrice()),
                                            String.valueOf(cim.getQuantity())
                                    ));
                                } else {
                                    model.add(new AddRateProductModel(
                                            String.valueOf(cim.getProductId()),
                                            "0",
                                            String.valueOf(cim.getQuantity()),
                                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                            String.valueOf(cim.getUnitPrice()),
                                            cim.getIsPriceChanged(),
                                            cim.getName(),
                                            cim.getAlaCarteList(),
                                            cim.getGroupList(),
                                            ""
                                    ));
                                }
                            }
                            if (cim.isForVoid()) {
                                voidModel.add(new VoidProductModel(
                                        cim.getPostId(),
                                        cim.getName(),
                                        String.valueOf(cim.getAmount()),
                                        String.valueOf(cim.getQuantity()),
                                        ""
                                ));
                            }
                        }

                        AddProductToRequest addProductToRequest = new AddProductToRequest(
                                model,
                                "",
                                "",
                                selectedRoom.getControlNo(),
                                voidModel,
                                "",
                                "0",
                                "0",
                                updateModel);

                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<AddProductToResponse> request = iUsers.addProductTo(addProductToRequest.getMapValue());
                        request.enqueue(new Callback<AddProductToResponse>() {
                            @Override
                            public void onResponse(Call<AddProductToResponse> call, Response<AddProductToResponse> response) {

                                FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(response.body().getResult().getControlNo());
                                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                Call<FetchOrderPendingViaControlNoResponse> re = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
                                re.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
                                    @Override
                                    public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                                        fetchOrderPendingViaControlNumberFunction(response.body());
                                        if (checkoutDialog == null) {
                                            checkoutDialog = new PaymentDialog(getActivity(),
                                                    paymentTypeList,
                                                    true,
                                                    postedPaymentsList,
                                                    totalBalance,
                                                    currencyList,
                                                    creditCardList,
                                                    arOnlineList,
                                                    discountPayment,
                                                    selectedRoom.getControlNo(),
                                                    guestReceiptInfoModel,
                                                    selectedRoom.isTakeOut(),
                                                    false){

                                                @Override
                                                public void removePaymentSuccess() {
                                                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                                }

                                                @Override
                                                public void paymentSuccess(List<PostedPaymentsModel> postedPaymentList, String roomBoy) {
                                                    final List<PostedPaymentsModel> paymentsToPost = new ArrayList<>();
                                                    boolean isReadyForCheckOut = false;
                                                    Double totalPayments = 0.00;
                                                    for (PostedPaymentsModel ppm : postedPaymentList) {
                                                        if (!ppm.isIs_posted()) {
                                                            paymentsToPost.add(ppm);
                                                        }
                                                        totalPayments += Double.valueOf(ppm.getAmount()) / Double.valueOf(ppm.getCurrency_value());
                                                    }

                                                    if (totalPayments >= Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((totalBalance - (advancePayment + discountPayment)))))) {

                                                        PrintSoaRequest printSoaRequest = new PrintSoaRequest("", selectedRoom.getControlNo());
                                                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                                        Call<PrintSoaResponse> request = iUsers.printSoa(printSoaRequest.getMapValue());
                                                        request.enqueue(new Callback<PrintSoaResponse>() {
                                                            @Override
                                                            public void onResponse(Call<PrintSoaResponse> call, Response<PrintSoaResponse> response) {
                                                                if (paymentsToPost.size() > 0) {
                                                                    postCheckoutPayment(paymentsToPost, "", selectedRoom.getControlNo(), "");
                                                                } else {

                                                                    checkoutRoom("",
                                                                            selectedRoom.getControlNo(),
                                                                            "");
                                                                    Toast.makeText(getContext(), "No payment to post, will proceed to checkout", Toast.LENGTH_SHORT).show();
                                                                }
                                                                dismiss();
                                                            }

                                                            @Override
                                                            public void onFailure(Call<PrintSoaResponse> call, Throwable t) {

                                                            }
                                                        });


                                                    } else {

                                                        Utils.showDialogMessage(getActivity(), "Payment is less than balance", "Warning");
                                                    }
                                                }

                                                @Override
                                                public void paymentFailed() {

                                                }
                                            };

                                            checkoutDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialog) {
                                                    checkoutDialog = null;
                                                }
                                            });

                                            checkoutDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    checkoutDialog = null;
                                                }
                                            });
                                        }

                                        if (!checkoutDialog.isShowing()) {
                                            checkoutDialog.show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<AddProductToResponse> call, Throwable t) {

                            }
                        });
                    } else {
                        Utils.showDialogMessage(getActivity(), "No product selected", "Information");
                    }
                }


                break;
            case "table":
                break;
            case "room":
                if (selectedRoom != null) {
                    if (checkoutDialog == null) {
                        checkoutDialog = new PaymentDialog(getActivity(),
                                paymentTypeList,
                                true,
                                postedPaymentsList,
                                totalBalance,
                                currencyList,
                                creditCardList,
                                arOnlineList,
                                discountPayment,
                                selectedRoom.getControlNo(),
                                guestReceiptInfoModel,
                                selectedRoom.isTakeOut(),
                                false) {
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
                                    if (selectedRoom != null) {
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
                                    }


                                } else {

                                    if (totalPayments >= Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((totalBalance - (advancePayment + discountPayment)))))) {
                                        if (paymentsToPost.size() > 0) {
                                            if (selectedRoom != null) {

                                                if (selectedRoom.isTakeOut()) {
                                                    postCheckoutPayment(paymentsToPost, "", selectedRoom.getControlNo(), "");
                                                } else {
                                                    postCheckoutPayment(paymentsToPost, String.valueOf(selectedRoom.getRoomId()), "", roomboy);
                                                }
                                            }

                                        } else {
                                            if (selectedRoom != null) {
                                                if (selectedRoom.isTakeOut()) {
                                                    checkoutRoom("",
                                                            selectedRoom.getControlNo(),
                                                            "");
                                                } else {
                                                    checkoutRoom(String.valueOf(selectedRoom.getRoomId()),
                                                            "",
                                                            roomboy);
                                                }
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

                        checkoutDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                checkoutDialog = null;
                            }
                        });

                        checkoutDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                checkoutDialog = null;
                            }
                        });
                    }

                    if (selectedRoom != null) {
                        if (selectedRoom.isTakeOut()) {
                            if (paymentTypeList.size() > 0) {
                                if (currentRoomStatus.equalsIgnoreCase("1")) {
                                    if (!checkoutDialog.isShowing()) {
                                        checkoutDialog.show();
                                    }

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
                                        if (!checkoutDialog.isShowing()) {
                                            checkoutDialog.show();
                                        }

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
                } else {
                    Utils.showDialogMessage(getActivity(), "No room selected", "Information");
                }
                break;
        }



    }

    @Subscribe
    public void fetchRoomPendingResponse(FetchRoomPendingResponse fetchRoomPendingResponse) {
//        BusProvider.getInstance().post(new ClearSearchData("d"));
//        forVoidDiscountModels = new ArrayList<>();
//        guestReceiptInfoModel = null;
//        checkoutSwipe.setRefreshing(false);
//
//        cartItemList = new ArrayList<>();
//        orderSlipList = new ArrayList<>();
//        postedPaymentsList = new ArrayList<>();
//        Double totalAmount = 0.00;
//        discountPayment = 0.00;
//        advancePayment = 0.00;
//        currentRoomStatus = String.valueOf(fetchRoomPendingResponse.getResult().getStatus());
////        Utils.showDialogMessage(getActivity(), currentRoomStatus, "SIR PAKITANDAAN(DONT IGNORE)");
//        if (fetchRoomPendingResponse.getResult().getBooked().size() > 0) {
//
//            kitchenPath = fetchRoomPendingResponse.getResult().getBooked().get(0).getRoom().getArea().getKitchenPath();
//            printerPath = fetchRoomPendingResponse.getResult().getBooked().get(0).getRoom().getArea().getPrinterPath();
//            for (FetchRoomPendingResponse.Booked r : fetchRoomPendingResponse.getResult().getBooked()) {
//                if (r.getTransaction() != null) {
//                    if (r.getTransaction().getEmployee_id() != null && !TextUtils.isEmpty(r.getTransaction().getEmployee_id())) {
//                        employeeId = r.getTransaction().getEmployee_id();
//                    }
//                    if (r.getTransaction().getCustomerTrans() != null) {
//                        if (r.getTransaction().getCustomerTrans() != null) {
//                            guestReceiptInfoModel = new GuestReceiptInfoModel(r.getTransaction().getCustomerTrans().getCustomer(),
//                                    r.getTransaction().getCustomerTrans().getAddress() != null ? r.getTransaction().getCustomerTrans().getAddress() : "",
//                                    r.getTransaction().getCustomerTrans().getTin() != null ? r.getTransaction().getCustomerTrans().getTin() : "");
//                        }
//                    }
//                }
//                //regionpayments
//                overTimeValue =  r.getTransaction().getOtHours();
//                if (r.getTransaction().getPayments().size() > 0) {
//                    for(FetchRoomPendingResponse.Payment pym : r.getTransaction().getPayments()) {
//
//                        String paymentDescription = "";
//                        if (pym.getPaymentTypeId() == 5) {
//                            if (pym.getOuterAr() != null) {
//                                paymentDescription = pym.getPaymentDescription() + " - " + pym.getOuterAr().getInnerAr().getArOnline() + "(" + pym.getOuterAr().getVoucherCode() +")";
//                            } else {
//                                paymentDescription = pym.getPaymentDescription();
//                            }
//                        } else {
//                            paymentDescription = pym.getPaymentDescription();
//                        }
//
//
//                        String symbolLeft = "";
//                        String symbolRight = "";
//
//                        if (pym.getCurrency() != null) {
//                            if (pym.getCurrency().getSymbolLeft() != null) {
//                                symbolLeft = pym.getCurrency().getSymbolLeft();
//                            }
//
//
//                            if (pym.getCurrency().getSymbolRight() != null) {
//                                symbolRight = pym.getCurrency().getSymbolRight();
//                            }
//                        }
//
//                        postedPaymentsList.add(new PostedPaymentsModel(
//                                String.valueOf(pym.getPaymentTypeId()),
//                                String.valueOf(pym.getAmount()),
//                                paymentDescription,
//                                true,
//                                String.valueOf(pym.getCurrencyId()),
//                                String.valueOf(pym.getCurrencyValue()),
//                                new JSONObject(),
//                                symbolLeft,
//                                symbolRight,
//                                pym.getIsAdvance() == 1 ? true : false,
//                                "",
//                                String.valueOf(pym.getId()),
//                                ""
//                        ));
//                    }
//                }
//                //endregion
//                //region order list
//                if (r.getTransaction().getTrans().size() > 0) {
//
//                    forVoidDiscountModels = new ArrayList<>();
//                    for (FetchRoomPendingResponse.DiscountsOuter dos  : r.getTransaction().getDiscountsOuter()) {
//                        if (dos.getVoid_by() == null) {
//                            forVoidDiscountModels.add(new ForVoidDiscountModel(dos.getId(), dos.getDiscountType(), dos.getDiscountAmount()));
//                        }
//
//                    }
//
//                    selectedRoom.setControlNo(r.getTransaction().getControlNo());
//                    totalBalance = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTotal() +
//                            Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getOtAmount()))) +
//                            Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getXPersonAmount()))))
////                            - (fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTendered());
//                            - (Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTendered())))
//                            + Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getVatExempt())))))));
//
//                    advancePayment = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getAdvance())));
//                    depositInfoData = String.valueOf(advancePayment);
//                    discountPayment = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getDiscount())));
//                    subTotal.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(totalBalance)))));
//
//
//                    total.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((totalBalance - (advancePayment + discountPayment)) < 0 ? 0 : (totalBalance - (advancePayment + discountPayment)))))));
//                    if (discountPayment > totalBalance) {
//                        discount.setText(Utils.digitsWithComma(totalBalance));
//                    } else {
//                        discount.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(discountPayment)))));
//                    }
//                    if (r.getCheckIn() != null && !TextUtils.isEmpty(globalServerTimeString)) {
//
//                        if (selectedRoom.getRoomType() != null) {
//                            setView(selectedRoom.getName(), selectedRoom.getRoomType());
//                        } else {
//                            setView(selectedRoom.getName(), "TO");
//                        }
//
//
//
//                        header.setText(String.format("%s(%s)", header.getText().toString(),
//                                Utils.durationOfStay(globalServerTimeString, r.getCheckIn().toString())));
//                    }
//                    deposit.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(r.getTransaction().getAdvance())))));
//                    depositInfoData = String.valueOf(r.getTransaction().getAdvance());
//                    for (FetchRoomPendingResponse.Tran transPost : r.getTransaction().getTrans()) {
//                        List<OrderSlipModel.OrderSlipInfo> osiList = new ArrayList<>();
//                        for (FetchRoomPendingResponse.Order osi : transPost.getOrder()) {
//                            List<OrderSlipModel.OrderSlipProduct> osp = new ArrayList<>();
//                            for (FetchRoomPendingResponse.PostTrans prod : osi.getPost()) {
//                                if (prod.getProductId() == 0) { //room
//                                    osp.add(
//                                            new OrderSlipModel.OrderSlipProduct(String.valueOf(prod.getId()),
//                                                    "",
//                                                    "",
//                                                    "",
//                                                    String.valueOf(prod.getRoomTypeId()),
//                                                    "",
//                                                    String.valueOf(prod.getRoomRatePriceId()),
//                                                    prod.getRoomType(),
//                                                    prod.getRoomRate().toString(),
//                                                    String.valueOf(prod.getQty()),
//                                                    String.valueOf(prod.getPrice()),
//                                                    String.valueOf(prod.getPrice()),
//                                                    String.valueOf(prod.getTotal()),
//                                                    prod.getVoid() == 0 ? false : true));
//                                } else { //product
//                                    osp.add(
//                                            new OrderSlipModel.OrderSlipProduct(String.valueOf(prod.getId()),
//                                                    String.valueOf(prod.getProduct().getId()),
//                                                    prod.getProduct().getProduct(),
//                                                    prod.getProduct().getProductInitial(),
//                                                    "",
//                                                    "",
//                                                    "",
//                                                    "",
//                                                    "",
//                                                    String.valueOf(prod.getQty()),
//                                                    String.valueOf(prod.getUnitCost()),
//                                                    String.valueOf(prod.getPrice()),
//                                                    String.valueOf(prod.getTotal()),
//                                                    prod.getVoid() == 0 ? false : true));
//                                }
//
//                            }
//                            OrderSlipModel.OrderSlipInfo slipInfoList =
//                                    new OrderSlipModel.OrderSlipInfo(String.valueOf(osi.getId()),
//                                            String.valueOf(osi.getPostOrderId()),
//                                            String.valueOf(osi.getPostTransId()),
//                                            osp);
//                            osiList.add(slipInfoList);
//                        }
//                        OrderSlipModel orderSlipModel = new OrderSlipModel(transPost.getControlNo(), osiList, String.valueOf(transPost.getId()));
//                        orderSlipList.add(orderSlipModel);
//
//
//                    }
//                }
//                //endregion
//                //region orders
//                roomRateCounter = new ArrayList<>();
//
//
//                for (FetchRoomPendingResponse.Post tpost : r.getTransaction().getPost()) {
//                    if (tpost.getVoid() == 0) {
//                        if (tpost.getTransactionPostFreebies() != null) {
//                            //whatis this
//                        }
//                        if (tpost.getRoomRateId() != null) {
//                            roomRateCounter.add(1);
//                            cartItemList.add(0, new CartItemsModel(
//                                    tpost.getControlNo(),
//                                    tpost.getRoomId() == null ? 0 : tpost.getRoomId(),
//                                    tpost.getProductId(),
//                                    tpost.getRoomTypeId() == null ? 0 : tpost.getRoomTypeId(),
//                                    tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
//                                    tpost.getRoomRatePriceId() == null ? 0 : tpost.getRoomRatePriceId(),
//                                    tpost.getRoomRateId() == null ? tpost.getProduct().getProductInitial().toUpperCase() : tpost.getRoomRate().toUpperCase(),
//                                    tpost.getProductId() == 0 ? false : true,
//                                    tpost.getTotal(),
//                                    tpost.getId(),
//                                    tpost.getQty(),
//                                    true,
//                                    0.00,
//                                    0,
//                                    tpost.getPrice(),
//                                    false,
//                                    String.valueOf(tpost.getId()),
//                                    false,
//                                    "room",
//                                    new ArrayList<AddRateProductModel.AlaCarte>(),
//                                    new ArrayList<AddRateProductModel.Group>(),
//                                    false,
//                                    tpost.getTransactionPostFreebies(),
//                                    TextUtils.isEmpty(tpost.getRemarks()) ? "" : tpost.getRemarks()
//                            ));
//                        } else {
//
//                            ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
//                            ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
//                            ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
//                            ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();
//
//                            if (tpost.getPostGroupList() != null) {
//                                for (FetchRoomPendingResponse.PostGroup sipm : tpost.getPostGroupList()) {
//                                    groupCompoList = new ArrayList<>();
//                                    groupCompoProductsList = new ArrayList<>();
////                                    if (sipm.getPostGroupInfo() != null) {
//                                        for (FetchRoomPendingResponse.PostGroupItem bpm : sipm.getPostGroupItems()) {
//                                            groupCompoProductsList.add(
//                                                    new AddRateProductModel(
//                                                            "",
//                                                            "0",
//                                                            String.valueOf(bpm.getQty()),
//                                                            SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
//                                                            "",
//                                                            0,
//                                                            bpm.getPostGroupItemProduct().getProduct(),
//                                                            new ArrayList<AddRateProductModel.AlaCarte>(),
//                                                            new ArrayList<AddRateProductModel.Group>(),
//                                                            ""
//                                                    ));
//
//                                        }
//                                        groupLst.add(
//                                                new AddRateProductModel.Group(
//                                                        new AddRateProductModel.GroupCompo(
//                                                                0,
//                                                                sipm.getPostGroupInfo() == null ? "PB" : sipm.getPostGroupInfo().getGroupName(),
//                                                                0,
//                                                                groupCompoProductsList)));
////                                    }
//                                }
//                            }
//
//
//                            for (FetchRoomPendingResponse.PostAlaCart balac : tpost.getPostAlaCartList()) {
//
//
//                                alaCartes.add(new AddRateProductModel.AlaCarte(
//                                        "",
//                                        "0",
//                                        String.valueOf(balac.getQty()),
//                                        SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
//                                        "0.00",
//                                        0,
//                                        balac.getPostAlaCartProduct().getProduct()
//                                ));
//                            }
//
//
//                            cartItemList.add(roomRateCounter.size(), new CartItemsModel(
//                                    tpost.getControlNo(),
//                                    tpost.getRoomId() == null ? 0 : tpost.getRoomId(),
//                                    tpost.getProductId(),
//                                    tpost.getRoomTypeId() == null ? 0 :tpost.getRoomTypeId(),
//                                    tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
//                                    tpost.getRoomRatePriceId() == null ? 0 : tpost.getRoomRatePriceId(),
//                                    tpost.getRoomRateId() == null ? tpost.getProduct().getProductInitial().toUpperCase() : tpost.getRoomRate().toUpperCase(),
//                                    tpost.getProductId() == 0 ? false : true,
//                                    tpost.getTotal(),
//                                    tpost.getId(),
//                                    tpost.getQty(),
//                                    true,
//                                    0.00,
//                                    0,
//                                    tpost.getPrice(),
//                                    false,
//                                    String.valueOf(tpost.getId()),
//                                    false,
//                                    "room",
//                                    alaCartes,
//                                    groupLst,
//                                    false,
//                                    tpost.getTransactionPostFreebies(),
//                                    TextUtils.isEmpty(tpost.getRemarks()) ? "" : tpost.getRemarks()
//                            ));
//                        }
//
//                        totalAmount += tpost.getTotal();
//                    }
//                }
//                //endregion
//                //region ot
//                if (r.getTransaction().getOtHours() > 0) {
//                    cartItemList.add(new CartItemsModel(
//                            r.getTransaction().getControlNo(),
//                            0,
//                            0,
//                            0,
//                            0,
//                            0,
//                            "OT HOURS",
//                            true,
//                            r.getTransaction().getOtAmount(),
//                            0,
//                            r.getTransaction().getOtHours(),
//                            true,
//                            0.00,
//                            0,
//                            Double.valueOf(r.getTransaction().getOtAmount()),
//                            false,
//                            "",
//                            false,
//                            "ot",
//                            new ArrayList<AddRateProductModel.AlaCarte>(),
//                            new ArrayList<AddRateProductModel.Group>(),
//                            false,
//                            null,
//                            ""
//                    ));
//                }
//
//                if (Integer.valueOf(r.getTransaction().getPersonCount()) > 2) {
//                    cartItemList.add(new CartItemsModel(
//                            r.getTransaction().getControlNo(),
//                            0,
//                            0,
//                            0,
//                            0,
//                            0,
//                            "EXTRA PERSON",
//                            true,
//                            r.getTransaction().getXPersonAmount(),
//                            0,
//                            Integer.valueOf(r.getTransaction().getPersonCount()) - 2,
//                            true,
//                            0.00,
//                            0,
//                            r.getTransaction().getXPersonAmount(),
//                            false,
//                            "",
//                            false,
//                            "misc",
//                            new ArrayList<AddRateProductModel.AlaCarte>(),
//                            new ArrayList<AddRateProductModel.Group>(),
//                            false,
//                            null,
//                            ""
//                    ));
//                }
//
//                //endregion
//
//            }
//        } else {
//            subTotal.setText("0.00");
//            discount.setText("0.00");
//            deposit.setText("0.00");
//            total.setText("0.00");
//            totalBalance = 0;
//        }
//
//
//        checkoutAdapter = new CheckoutAdapter(this.cartItemList, this, getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        listCheckoutItems.setLayoutManager(linearLayoutManager);
//        listCheckoutItems.setLayoutAnimation(anim);
//        listCheckoutItems.setAdapter(checkoutAdapter);
//        checkoutAdapter.notifyDataSetChanged();
//
//
//        if (fetchRoomPendingResponse.getResult() != null) {
//            fetchRoomPendingResult = fetchRoomPendingResponse.getResult();
//            switch (fetchRoomPendingResponse.getResult().getStatus()) {
//                case 1://clean
//                    if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
//                        if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
//                            showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
//                        } else {
//                            showCheckInNotAllowed();
//                        }
//                    } else {
//                        showCheckInNotAllowed();
//                    }
//
//                    break;
//                case 3: //dirty
//
//                    if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
//                        if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
//                            showGuestInfoDialog(
//                                    String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
//                        } else {
//                            showCheckInNotAllowed();
//                        }
//                    } else {
//                        showCheckInNotAllowed();
//                    }
//
//
//                    break;
//                case 19:
//                    if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
//                        if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
//                            showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
//                        } else {
//                            showCheckInNotAllowed();
//                        }
//                    } else {
//                        showCheckInNotAllowed();
//                    }
//                    break;// ongoing nego
//                case 20: //onnego show check in form
//                    if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
//                        if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
//                            showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
//                        } else {
//                            showCheckInNotAllowed();
//                        }
//                    } else {
//                        showCheckInNotAllowed();
//                    }
//
//                    break;
//                case 32:
////                    showCheckInDialog(fetchRoomPendingResponse.getResult());
//                    break;
//                case 4:
////                    showCheckInDialog(fetchRoomPendingResponse.getResult());
//                    break;
//                case 59: //check in guest
//                    if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).isEmpty()) {
//                        if(SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_CHECK_IN).equalsIgnoreCase("y")) {
//                            showCheckInDialog();
//                        } else {
//                            showCheckInNotAllowed();
//                        }
//                    } else {
//                        showCheckInNotAllowed();
//                    }
//                    break;
//
//                case 2: //already checked in, can now order
////                    Toast.makeText(getContext(), "Please order", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
////                    showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
//                    break;
//            }
//        } else {
//            Toast.makeText(getContext(), "Call fetch room pending", Toast.LENGTH_SHORT).show();
//        }
//        endLoading();
    }

    private void showCheckInNotAllowed() {
        sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
        Utils.showDialogMessage(getActivity(), "Not allowed to check in, please check settings", "Information");
    }

    private void sendOffGoingNegoRequest(String roomId) {

        if (selectedRoom != null) {

            if (selectedRoom.getStatus().equalsIgnoreCase("59")) {
                SocketManager.reloadPos(
                        selectedRoom.getName(),
                        String.valueOf(selectedRoom.getRoomId()),
                        "59",
                        "1",
                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                        "end");
            } else {
                SocketManager.reloadPos(
                        selectedRoom.getName(),
                        String.valueOf(selectedRoom.getRoomId()),
                        "1",
                        "1",
                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                        "cancel");
            }

        } else {
            Log.d("EMIT", "EMPTY ROOM SELECTED");
        }

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

                if (selectedRoom != null) {
                    if (!TextUtils.isEmpty(selectedRoom.getName())) {
                        SocketManager.reloadPos(
                                selectedRoom.getName(),
                                String.valueOf(selectedRoom.getRoomId()),
                                "2",
                                "2",
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                "end");
                    }

                }




                if (!status.equalsIgnoreCase("19") &&
                        !status.equalsIgnoreCase("3") &&
                        !status.equalsIgnoreCase("20")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {


//                                    Log.d("CHECKNREQU", new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
//                                            welcomeGuestRequest.getRoomRatePriceId(),
//                                            remarks).toString());
//                                    BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
//                                            welcomeGuestRequest.getRoomRatePriceId(),
//                                            remarks));
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
        if (!checkInDialog.isShowing()) {

            SocketManager.reloadPos(
                    selectedRoom.getName(),
                    String.valueOf(selectedRoom.getRoomId()),
                    "20",
                    "20",
                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                    "start");



            checkInDialog.show();
        }


        checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (selectedRoom != null) {
                    sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
                }
            }
        });

    }

    private void showCheckInDialog() {
        if (fetchRoomPendingResult != null) {
            if (fetchRoomPendingResult.getBooked().size() > 0) {


                TypeToken<List<FetchUserResponse.Result>> token = new TypeToken<List<FetchUserResponse.Result>>() {};
                userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_JSON), token.getType());


                checkInDialog = new CheckInDialog(getActivity(), selectedRoom,
                        carList, vehicleList,
                        guestTypeList, userList,
                        roomAreaList, nationalityList, fetchRoomPendingResult) {
                    @Override
                    public void successCheckIn(final WelcomeGuestRequest welcomeGuestRequest) {
                        BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                welcomeGuestRequest.getRoomRatePriceId(),
                                ""));

                        if (selectedRoom != null){
                            SocketManager.reloadPos(
                                    selectedRoom.getName(),
                                    String.valueOf(selectedRoom.getRoomId()),
                                    "2",
                                    "2",
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                    "end");
                        }




                    }
                };
                if (!checkInDialog.isShowing()) checkInDialog.show();

                checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (selectedRoom != null) {
                            sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
                        }

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
        model.add(
                new AddRateProductModel(
                        productId, roomRatePriceId,
                        quantity, tax, amount,
                        0, roomRateDesc,
                        new ArrayList<AddRateProductModel.AlaCarte>(),
                        new ArrayList<AddRateProductModel.Group>(),
                        ""));
        ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
            @Override
            public void save(String remarks) {

                if (selectedRoom != null) {

                    BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model),kitchenPath, printerPath, remarks,kitchenPath, printerPath));
                    BusProvider.getInstance().post(new AddRoomPriceRequest(
                            model,
                            roomId, new ArrayList<VoidProductModel>(),
                            remarks, "","0", "0",
                            new ArrayList<UpdateProductModel>()));
                }
            }
        };

        if (!confirmWithRemarksDialog.isShowing()) confirmWithRemarksDialog.show();

    }

    @Subscribe
    public void addProductResponse(AddRoomPriceResponse addRoomPriceResponse) {
//        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));

        if (selectedRoom!=null) {
            //DIONE RETURN HERE
            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
//            BusProvider.getInstance().post(new FetchRoomPendingRequest(String.valueOf(selectedRoom.getRoomId())));
        }

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
                                selectedRoom.getRoomType(),
                                kitchenPath, printerPath));


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



    private void postCheckoutPayment(List<PostedPaymentsModel> ppm, String roomId, String  controlNumber, final String roomBoy) {
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
//                BusProvider.getInstance().post(new PrintModel("",
//                        "takeout",
//                        "SOA-TO",
//                        GsonHelper.getGson().toJson(response.body().getResult())
//                ));


                printSoaFromCentralizedCallTo(printSoaResponse.getResult().getControlNumber());

                if (printSoaResponse.getResult().getToPostList().size() > 0) {
                    Utils.showDialogMessage(getActivity(), "Printing statement of account", "Information");
                } else {
                    Utils.showDialogMessage(getActivity(), "No item/s to print", "Information");
                }


            } else {


//                BusProvider.getInstance().post(new PrintModel("",
//                        selectedRoom.getName(),
//                        "SOA-ROOM",
//                        GsonHelper.getGson().toJson(printSoaResponse.getResult().getBooked())
//                ));
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));

                printSoaFromCentralizedCallRoom(printSoaResponse.getResult().getBooked().get(0).getTransaction().getControlNo());
                if (printSoaResponse.getResult().getBooked().size() > 0) {
                    Utils.showDialogMessage(getActivity(), "Printing statement of account", "Information");
                } else {
                    Utils.showDialogMessage(getActivity(), "No item/s to print", "Information");
                }


            }

            hasExistingRequest = false;
        }


    }

    private void printSoaFromCentralizedCallTo(String controlNo) {
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNo);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {


                BusProvider.getInstance().post(new PrintModel("",
                        "takeout",
                        "SOA-TO",
                        GsonHelper.getGson().toJson(response.body().getResult()),
                        kitchenPath,
                        printerPath
                ));
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });
    }

    private void printSoaFromCentralizedCallRoom(String controlNo) {

        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNo);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {

                if (selectedRoom!=null) {

                    BusProvider.getInstance().post(new PrintModel("",
                            selectedRoom.getName(),
                            "SOA-ROOM",
                            GsonHelper.getGson().toJson(response.body().getResult()),
                            kitchenPath,
                            printerPath
                    ));
                }
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });


    }

    private void printSoaRequest(String roomId, String controlNumber) {
        if (!hasExistingRequest) {
            hasExistingRequest = true;
            BusProvider.getInstance().post(new PrintSoaRequest(roomId, controlNumber));
        }

    }

    private void fetchOrderPendingViaControlNumberFunction(FetchOrderPendingViaControlNoResponse fetchOrderPendingViaControlNoResponse) {
        BusProvider.getInstance().post(new ClearSearchData("d"));
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
        totalBalance = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal() -
                ( fetchOrderPendingViaControlNoResponse.getResult().getTendered()  +
                        fetchOrderPendingViaControlNoResponse.getResult().getVatExempt()))));

        advancePayment = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getAdvance())));
        discountPayment = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getDiscount())));
        depositInfoData = String.valueOf(advancePayment);

        cartItemList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        Double totalAmount = 0.00;
        if (fetchOrderPendingViaControlNoResponse.getResult() != null) {

            if (fetchOrderPendingViaControlNoResponse.getResult().getArea() != null) {
                kitchenPath = fetchOrderPendingViaControlNoResponse.getResult().getArea().getKitchenPath();
                printerPath = fetchOrderPendingViaControlNoResponse.getResult().getArea().getPrinterPath();
            }



            if (fetchOrderPendingViaControlNoResponse.getResult().getEmployeeId() != null) {

                employeeId = fetchOrderPendingViaControlNoResponse.getResult().getEmployeeId().toString();

            }


            for (FetchOrderPendingViaControlNoResponse.Discounts dos : fetchOrderPendingViaControlNoResponse.getResult().getDiscountsList()) {
                if (dos.getVoid_by() == null) {
                    forVoidDiscountModels.add(new ForVoidDiscountModel(dos.getId(), dos.getDiscountType(), String.valueOf(dos.getDiscountAmount())));
                }

            }

            if (fetchOrderPendingViaControlNoResponse.getResult().getCustomer() != null) {
                guestReceiptInfoModel = new GuestReceiptInfoModel(
                        fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getCustomer(),
                        fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getAddress() != null ? fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getAddress() : "",
                        fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getTin() != null ? fetchOrderPendingViaControlNoResponse.getResult().getCustomer().getAddress() : "");
            }

            if (fetchOrderPendingViaControlNoResponse.getResult().getDiscount() > Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal())))) {
                discount.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal()))))))));
            } else {
                discount.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getDiscount())))));
            }

            subTotal.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal())))));
            deposit.setText(Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getAdvance())))));
            depositInfoData = String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getAdvance());
            total.setText(String.valueOf(
                    (fetchOrderPendingViaControlNoResponse.getResult().getTotal() -(fetchOrderPendingViaControlNoResponse.getResult().getAdvance() + fetchOrderPendingViaControlNoResponse.getResult().getDiscount())) < 0 ?
                            "0.00" :
                            Utils.digitsWithComma(Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getTotal() -(fetchOrderPendingViaControlNoResponse.getResult().getAdvance() + fetchOrderPendingViaControlNoResponse.getResult().getDiscount())))))
            ));
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
                            String.valueOf(pym.getId()),
                            ""
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


            //ADD ALACARTE GROUP HERE

            if (fetchOrderPendingViaControlNoResponse.getResult().getPost().size() > 0) {
                for (FetchOrderPendingViaControlNoResponse.Post tpost : fetchOrderPendingViaControlNoResponse.getResult().getPost()) {

                    if (tpost.getVoid() == 0) {
                        ArrayList<AddRateProductModel.AlaCarte> alaCartes = new ArrayList<>();
                        ArrayList<AddRateProductModel.Group> groupLst = new ArrayList<>();
                        ArrayList<AddRateProductModel.GroupCompo> groupCompoList = new ArrayList<>();
                        ArrayList<AddRateProductModel> groupCompoProductsList = new ArrayList<>();

                        if (tpost.getPostGroupList() != null) {
                            for (FetchRoomPendingResponse.PostGroup sipm : tpost.getPostGroupList()) {
                                groupCompoList = new ArrayList<>();
                                groupCompoProductsList = new ArrayList<>();
                                if (sipm.getPostGroupInfo() != null) {
                                    for (FetchRoomPendingResponse.PostGroupItem bpm : sipm.getPostGroupItems()) {
                                        groupCompoProductsList.add(
                                                new AddRateProductModel(
                                                        "",
                                                        "0",
                                                        String.valueOf(bpm.getQty()),
                                                        SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                                        "",
                                                        0,
                                                        bpm.getPostGroupItemProduct().getProduct(),
                                                        new ArrayList<AddRateProductModel.AlaCarte>(),
                                                        new ArrayList<AddRateProductModel.Group>(),
                                                        ""
                                                ));

                                    }
                                    groupLst.add(
                                            new AddRateProductModel.Group(
                                                    new AddRateProductModel.GroupCompo(
                                                            0,
                                                            sipm.getPostGroupInfo().getGroupName(),
                                                            0,
                                                            groupCompoProductsList)));
                                }
                            }
                        }


                        for (FetchRoomPendingResponse.PostAlaCart balac : tpost.getPostAlaCartList()) {
                            alaCartes.add(new AddRateProductModel.AlaCarte(
                                    "",
                                    "0",
                                    String.valueOf(balac.getQty()),
                                    SharedPreferenceManager.getString(getContext(),ApplicationConstants.TAX_RATE),
                                    "0.00",
                                    0,
                                    balac.getPostAlaCartProduct().getProduct()
                            ));
                        }


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
                                "to",
                                alaCartes,
                                groupLst,
                                false,
                                null,
                                TextUtils.isEmpty(tpost.getRemarks()) ? "" : tpost.getRemarks()
                        ));
                        totalAmount += tpost.getTotal();
                    }


                }

            }


            checkoutAdapter = new CheckoutAdapter(this.cartItemList, this, getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//            linearLayoutManager.setReverseLayout(true);
//            linearLayoutManager.setStackFromEnd(true);
            listCheckoutItems.setLayoutManager(linearLayoutManager);
            listCheckoutItems.setLayoutAnimation(anim);
            listCheckoutItems.setAdapter(checkoutAdapter);
            checkoutAdapter.notifyDataSetChanged();
        }

        endLoading();
    }

    @Subscribe
    public void fetchOrderPendingViaControlNoResponse(FetchOrderPendingViaControlNoResponse fetchOrderPendingViaControlNoResponse) {
//        this.fetchOrderPendingRresult = fetchOrderPendingViaControlNoResponse.getResult();
//        fetchOrderPendingViaControlNumberFunction(fetchOrderPendingViaControlNoResponse);
    }

    @Subscribe
    public void addProductToResponse(AddProductToResponse addProductToResponse) {
        if (selectedRoom != null) {
            fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
//            BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(selectedRoom.getControlNo()));
        }
        endLoading();

    }

    private void checkoutRoom(String roomId, String controlNumber, String roomBoyId) {

//        BusProvider.getInstance().post(new CheckOutRequest(roomId, controlNumber, roomBoyId));

        CheckOutRequest checkOutRequest = new CheckOutRequest(roomId, controlNumber, roomBoyId);

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ResponseBody> request = iUsers.checkOut(checkOutRequest.getMapValue());
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String rawResponse = response.body().string();
                    JSONObject responseObject = new JSONObject(rawResponse);

                    if (responseObject.getInt("status") == 0) {
                        Utils.showDialogMessage(getActivity(), responseObject.getString("message"), "Information");
                    } else {

                        if (selectedRoom != null) {

                            SocketManager.reloadPos(
                                    selectedRoom.getName(),
                                    String.valueOf(selectedRoom.getRoomId()),
                                    "3",
                                    "3",
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                    "end");


                            ChangeRoomStatusRequest cr =
                                    new ChangeRoomStatusRequest("3",
                                            String.valueOf(selectedRoom.getRoomId()),
                                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_ID),
                                            "");

                            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                            Call<ChangeRoomStatusResponse> request = iUsers.changeRoomStatus(cr.getMapValue());
                            request.enqueue(new Callback<ChangeRoomStatusResponse>() {
                                @Override
                                public void onResponse(Call<ChangeRoomStatusResponse> call, Response<ChangeRoomStatusResponse> response) {

                                    if (selectedRoom != null) {
                                        SocketManager.reloadPos(
                                                selectedRoom.getName(),
                                                String.valueOf(selectedRoom.getRoomId()),
                                                "3",
                                                "3",
                                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                                "end");
                                    }



                                }

                                @Override
                                public void onFailure(Call<ChangeRoomStatusResponse> call, Throwable t) {

                                }
                            });
                        }





                        if (selectedRoom!= null) {

                            printReceiptFromCheckout(selectedRoom.getControlNo(),
                                    selectedRoom.getName(),
                                    selectedRoom.getRoomType());
                            Toast.makeText(getContext(), "CHECK OUT SUCCESS", Toast.LENGTH_SHORT).show();
                        }


                        BusProvider.getInstance().post(new CheckSafeKeepingRequest());

                    }

                    endLoading();




                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });







    }

    @Subscribe
    public void checkoutResponse(CheckOutResponse checkOutResponse) {



        if (checkOutResponse.getStatus() == 0) {
            Utils.showDialogMessage(getActivity(), checkOutResponse.getMessage(), "Information");
        } else {

            TypeToken<List<String>> roomToken = new TypeToken<List<String>>() {};
            List<String> wul =
                    GsonHelper
                            .getGson()
                            .fromJson(
                                    SharedPreferenceManager.getString(null, "room_no_list"),
                                    roomToken.getType());

            if (wul != null) {
                if (wul.size() > 0) {
                    int index = 0;
                    for (String str : wul) {

                        if (selectedRoom!=null) {
                            if (str.equalsIgnoreCase(selectedRoom.getName())) {
                                break;
                            }
                            index += 1;

                        }

                    }
                    wul.remove(index);
                }
                SharedPreferenceManager.saveString(
                        getContext(),
                        GsonHelper.getGson().toJson(wul),
                        "room_no_list");
            }


            if (selectedRoom != null) {
                ChangeRoomStatusRequest cr =
                        new ChangeRoomStatusRequest("3",
                                String.valueOf(selectedRoom.getRoomId()),
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_ID),
                                "");

                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<ChangeRoomStatusResponse> request = iUsers.changeRoomStatus(cr.getMapValue());
                request.enqueue(new Callback<ChangeRoomStatusResponse>() {
                    @Override
                    public void onResponse(Call<ChangeRoomStatusResponse> call, Response<ChangeRoomStatusResponse> response) {

                        if (selectedRoom != null) {
                            SocketManager.reloadPos(
                                    selectedRoom.getName(),
                                    String.valueOf(selectedRoom.getRoomId()),
                                    "3",
                                    "3",
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                    "end");
                        }



                    }

                    @Override
                    public void onFailure(Call<ChangeRoomStatusResponse> call, Throwable t) {

                    }
                });
            }





            if (selectedRoom!=null) {

                printReceiptFromCheckout(selectedRoom.getControlNo(),
                        selectedRoom.getName(),
                        selectedRoom.getRoomType());
                Toast.makeText(getContext(), "CHECK OUT SUCCESS", Toast.LENGTH_SHORT).show();
            }





//            defaultView();
//            clearCartItems();


//            if (selectedRoom != null) {
//                if (selectedRoom.isTakeOut()) {
//                    fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
//                } else {
//                    fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
//                }
//            }

            BusProvider.getInstance().post(new CheckSafeKeepingRequest());

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

            SharedPreferenceManager.saveString(getContext(),
                    GsonHelper.getGson().toJson(fetchCreditCardResponse.getResult()),
                    ApplicationConstants.CREDIT_CARD);

            TypeToken<List<FetchCreditCardResponse.Result>> creditCardToken = new TypeToken<List<FetchCreditCardResponse.Result>>() {};
            creditCardList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.CREDIT_CARD), creditCardToken.getType());
        }
    }






    @Subscribe
    public void checkinResponse(CheckInResponse checkInResponse) {

        Log.d("MYAPIREQ", "CHECK IN REQUEST");

        if (selectedRoom != null) {
            BusProvider.getInstance().post(new PrintModel("", selectedRoom.getName(), "CHECKIN", GsonHelper.getGson().toJson(checkInResponse.getResult().getBooked())));
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
            } else {
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
            }

            selectedRoom.setControlNo(checkInResponse.getResult().getBooked().get(0).getTransaction().getControlNo());





        }

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
        Call<FetchRoomResponse> request = iUsers.sendRoomListRequest(fetchRoomRequest.getMapValue());
        request.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, Response<FetchRoomResponse> response) {
                if (response.body().getResult().size() > 0) {
                    boolean hasWelcome = false;
                    for (FetchRoomResponse.Result r : response.body().getResult()) {
                        List<RoomEntity> insertedRoom = RoomEntity
                                .findWithQuery(RoomEntity.class,
                                        "SELECT * FROM Room_Entity WHERE roomnumber = ?", String.valueOf(r.getRoomName()));

                        if (insertedRoom.size() == 0) {
                            RoomEntity roomEntity = new RoomEntity(r.getRoomName(), r.getType().getRoomType(),
                                    String.valueOf(r.getStatus().getCoreId()), r.getStatus().getRoomStatus(),
                                    r.getTransaction() != null ? r.getTransaction().getWakeUpCall() : "", 0,
                                    r.getTransaction() != null ? r.getTransaction().getTransaction().getControlNo() : "");
                            roomEntity.save();
                        } else {
                            RoomEntity room = insertedRoom.get(0);
                            boolean hasChanged = false;
                            if (r.getStatus().getCoreId() == 17 || r.getStatus().getCoreId() == 2) {
                                room.setWake_up_call(r.getTransaction() != null ? r.getTransaction().getWakeUpCall() : "");
                                room.setControl_number(r.getTransaction() != null ? r.getTransaction().getTransaction().getControlNo() : "");
                                hasChanged = true;
                            }

                            if (!String.valueOf(r.getStatus().getCoreId()).equalsIgnoreCase(room.getRoom_status())) {
                                room.setRoom_status(String.valueOf(r.getStatus().getCoreId()));
                                room.setRoom_status_description(r.getStatus().getRoomStatus());
                                room.setWake_up_call("");
                                room.setIs_done(0);
                                hasChanged = true;
                            }

                            if (r.getStatus().getCoreId() == 59) {
                                hasWelcome = true;
                            }

                            if (hasChanged) {
                                room.save();
                            }
                        }
                    }
                    BusProvider.getInstance().post(new RoomWelcomeNotifier(hasWelcome));
                }
            }

            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });


    }


    private void loadPrinter() {
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT))) {
            SPrinter printer = new SPrinter(
                    Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_LANGUAGE)),
                    getContext());

            try {


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


                    if (selectedRoom!=null) {

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
                                        "SWITCH_ROOM" ,GsonHelper.getGson().toJson(switchRoomPrintModel),
                                        kitchenPath, printerPath));

                        fetchRoomViaIdRequest(String.valueOf(switchRoomResponse.getResults().getBooked().get(0).getRoomId()));
                        Utils.showDialogMessage(getActivity(), "Switch room succeeded", "Success");
                    }
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

            if (rrm.getRatePrice() != null) {
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
                checkInTime,
                0
        );

        currentRoomStatus = selectedRoom.getStatus();
        if (selectedRoom.getRoomType() != null) {
            setView(selectedRoom.getName(), selectedRoom.getRoomType());
        } else {
            setView(selectedRoom.getName(), "TO");
        }

    }

    private void postVoidPrinter(String controlNumber, final String roomName, final String roomType) {
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {

                if (Utils.getSystemType(getContext()).equalsIgnoreCase("franchise")) {

//                    BusProvider.getInstance().post(new PrintModel(
//                            "", "",
//                            "FRANCHISE_OR",
//                            GsonHelper.getGson().toJson(response.body().getResult()),
//                            ""));
                }
                else {

                    //comment this later on after tesint
//                    BusProvider.getInstance().post(new PrintModel(
//                            "", roomName,
//                            "PRINT_RECEIPT",
//                            GsonHelper.getGson().toJson(response.body().getResult()),
//                            roomType));

                    BusProvider.getInstance().post(new PrintModel(
                            "", TextUtils.isEmpty(roomName) ? "takeout" : roomName,
                            "POST_VOID",
                            GsonHelper.getGson().toJson(response.body().getResult()),
                            roomType, kitchenPath, printerPath));


//                    if (selectedRoom != null) {
//
//                        if (selectedRoom.isTakeOut()) {
//
//
//                        }
//                        else {
//
//                            BusProvider.getInstance().post(new PrintModel(
//                                    "", roomName,
//                                    "POST_VOID",
//                                    GsonHelper.getGson().toJson(response.body().getResult()),
//                                    roomType));
//                        }
//                    } else {
//                        Utils.showDialogMessage(getActivity(), "Empty selected room on printing, please reprint", "Information");;
//                    }
                }



                clearCartItems();
                defaultView();
                detectSystem();
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });
    }

    private void printReceiptFromCheckout(String controlNumber, final String roomName, final String roomType) {
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {

                if (Utils.getSystemType(getContext()).equalsIgnoreCase("franchise")) {

                    BusProvider.getInstance().post(new PrintModel(
                            "", "",
                            "FRANCHISE_OR",
                            GsonHelper.getGson().toJson(response.body().getResult()),
                            "",kitchenPath, printerPath));
                }
                else {




                    //comment this later on after tesint
//                    BusProvider.getInstance().post(new PrintModel(
//                            "", roomName,
//                            "PRINT_RECEIPT",
//                            GsonHelper.getGson().toJson(response.body().getResult()),
//                            roomType));

                    if (selectedRoom != null) {

                        if (selectedRoom.isTakeOut()) {

                            BusProvider.getInstance().post(new PrintModel(
                                    "", "takeout",
                                    "PRINT_RECEIPT",
                                    GsonHelper.getGson().toJson(response.body().getResult()),
                                    roomType, kitchenPath, printerPath));


                        }
                        else {

                            BusProvider.getInstance().post(new PrintModel(
                                    "", roomName,
                                    "PRINT_RECEIPT",
                                    GsonHelper.getGson().toJson(response.body().getResult()),
                                    roomType, kitchenPath, printerPath));
                        }
                    } else {
                        Utils.showDialogMessage(getActivity(), "Empty selected room on printing, please reprint", "Information");;
                    }
                }



                clearCartItems();
                defaultView();
                detectSystem();
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

    private void cashNReconcile(String adminPassword, String fromPopUp) {
        int fromPop = 0;
        if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {

            if (cutOffDialog == null) {
                if (fromPopUp.equalsIgnoreCase("1")) {
                    fromPop = 1;
                }
                ApplicationConstants.IS_ACTIVE = "T";

                cutOffDialog = new CollectionDialog(getActivity(), "Cash and Reconcile", true, pShiftNumber, adminPassword, fromPop) {
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



                            if (cutOffDialog != null) {
                                cutOffDialog.dismiss();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                            if (cutOffDialog != null) {
                                cutOffDialog.dismiss();
                            }
                        }
                    }
                };


                cutOffDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ApplicationConstants.IS_ACTIVE = "F";
                        cutOffDialog = null;
                    }
                });

                cutOffDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        ApplicationConstants.IS_ACTIVE = "F";
                        cutOffDialog = null;
                    }
                });

                if (!cutOffDialog.isShowing()) cutOffDialog.show();
            }




        }

//        if (cutOffDialog != null) {
//
//        } else {
//
//
//            if (!cutOffDialog.isShowing()) cutOffDialog.show();
//        }



    }

    private void zReadRequest(String adminPassword) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("End of day confirmation");
        builder.setMessage("after this action you cannot add any more transaction for this day, continue?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doZReadFunction(adminPassword);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        if (!alert.isShowing()) {
            alert.show();
        }

    }

    private void doZReadFunction(String employeeId) {
        ZReadRequest zReadRequest = new ZReadRequest(employeeId);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        //return z read here
        if (zreadApiRequest == null) {
            zreadApiRequest = iUsers.zReading(zReadRequest.getMapValue());
            zreadApiRequest.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    zreadApiRequest = null;
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
                    zreadApiRequest = null;
                }
            });
        }

    }

    private void xReadRequest(String adminPassword) {

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        XReadRequest collectionRequest = new XReadRequest(new ArrayList<CollectionFinalPostModel>(), adminPassword);
        if (xreadApiRequest == null) {
            xreadApiRequest = iUsers.xReading(collectionRequest.getMapValue());

            xreadApiRequest.enqueue(new Callback<XReadResponse>() {
                @Override
                public void onResponse(Call<XReadResponse> call, Response<XReadResponse> response) {
                    xreadApiRequest = null;
                    if (response.body().getStatus() == 0) {
                        Utils.showDialogMessage(getActivity(), response.body().getMessage(), "Information");
                    } else {
                        Utils.showDialogMessage(getActivity(), "X READ SUCCESS", "Information");
                        BusProvider.getInstance().post(new LogoutUserAction("xread"));
                    }
                }

                @Override
                public void onFailure(Call<XReadResponse> call, Throwable t) {
                    xreadApiRequest = null;
                }
            });
        }



    }

    private void cancelOverTime(String adminPassword) {

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
                        if (removeOtDialog == null) {

                            removeOtDialog = new RemoveOtDialog(getContext(), String.valueOf(overTimeValue), getActivity()) {
                                @Override
                                public void removeOtSuccess(final String oldOtValue, final String remainingOt) {
                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    if (selectedRoom!=null) {

                                        CancelOverTimeRequest cancelOverTimeRequest = new CancelOverTimeRequest(String.valueOf(selectedRoom.getRoomId()), selectedRoom.getControlNo(), oldOtValue, remainingOt, adminPassword);
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
                                }
                            };


                            removeOtDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    removeOtDialog = null;
                                }
                            });

                            removeOtDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    removeOtDialog = null;
                                }
                            });
                        }

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

                    SocketManager.reloadPos(
                            selectedRoom.getName(),
                            String.valueOf(selectedRoom.getRoomId()),
                            "17",
                            "17",
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                            "end");


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
        Log.d("REQ", "REQXREAD");
    }

    @Subscribe
    public void fetchXReadingViaIdResponse(FetchXReadingViaIdResponse fetchXReadingViaIdResponse) {
        Log.d("REQ", "REQ X READ RESPONSE");
        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_XREADING).equalsIgnoreCase("y")) {
            BusProvider.getInstance().post(new PrintModel("", "X READING", "REXREADING", GsonHelper.getGson().toJson(fetchXReadingViaIdResponse.getResult())));
        } else {
            BusProvider.getInstance().post(new PrintModel("X READ SUCCESS", "xread", "ACK_SLIP", GsonHelper.getGson().toJson(fetchXReadingViaIdResponse.getResult())));
        }

        BusProvider.getInstance().post(new PrintModel("", "SHORT/OVER", "SHORTOVER", GsonHelper.getGson().toJson(fetchXReadingViaIdResponse.getResult())));
    }

    private void fetchZReadViaIdRequest(String zReadId) {
        FetchZReadViaIdRequest fetchZReadViaIdRequest = new FetchZReadViaIdRequest(zReadId);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ZReadResponse> request = iUsers.fetchZReadViaId(fetchZReadViaIdRequest.getMapValue());
        request.enqueue(new Callback<ZReadResponse>() {
            @Override
            public void onResponse(Call<ZReadResponse> call, Response<ZReadResponse> response) {

                if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.IS_ALLOWED_FOR_ZREADING).equalsIgnoreCase("y")) {
                    BusProvider.getInstance().post(new PrintModel("", "Z READING", "ZREAD", GsonHelper.getGson().toJson(response.body().getResult())));
                } else {
                    BusProvider.getInstance().post(new PrintModel("Z READ SUCCESS", "zread", "ACK_SLIP", GsonHelper.getGson().toJson(response.body().getResult())));
                }

                if (shiftCutOffMenuDialog != null) {
                    shiftCutOffMenuDialog.dismiss();
                }



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
            //return dione here
            Log.d("FOCRESPONSe"," GO REQUEST");
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);

            FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(selectedRoom.getControlNo());

            Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
            request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
                @Override
                public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                    if (selectedRoom != null) {


                        SocketManager.reloadPos(
                                selectedRoom.getName(),
                                String.valueOf(selectedRoom.getRoomId()),
                                "3",
                                "3",
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.USERNAME),
                                "end");


                        if (selectedRoom.isTakeOut()) {
                            BusProvider.getInstance().post(new PrintModel(
                                    "", "takeout",
                                    "PRINT_FOC",
                                    GsonHelper.getGson().toJson(response.body().getResult()),
                                    "", kitchenPath, printerPath));
                        } else {
                            BusProvider.getInstance().post(new PrintModel(
                                    "", selectedRoom.getName(),
                                    "PRINT_FOC",
                                    GsonHelper.getGson().toJson(response.body().getResult()),
                                    selectedRoom.getRoomType(), kitchenPath, printerPath));
                        }


                    }



                    defaultView();
                    clearCartItems();
                    Utils.showDialogMessage(getActivity(), "FOC SUCCESS", "Information");


                }

                @Override
                public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

                }
            });


        } else {
            Utils.showDialogMessage(getActivity(), focResponse.getMessage(), "Information");
        }

    }


    @Subscribe
    public void infoModel(InfoModel infoModel) {
//        Toast.makeText(getContext(), infoModel.getInformation(), Toast.LENGTH_SHORT).show();
        SharedPreferenceManager.saveString(getContext(), infoModel.getInformation(), ApplicationConstants.SHIFT_BLOCKER);
        //dione add shift number
        pShiftNumber = !TextUtils.isEmpty(infoModel.getShiftNumber()) ? infoModel.getShiftNumber() : "";
        if (ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("F")) {
            if (infoModel.getInformation().equalsIgnoreCase("please execute end of day")) {
                Toast.makeText(getContext(), infoModel.getInformation(), Toast.LENGTH_SHORT).show();

                if (blockerDialog == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {
                                        BusProvider.getInstance().post(new ButtonsModel(121,"XREAD", "",19, 0, "1"));
                                    }
                                    blockerDialog = null;
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    blockerDialog = null;
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("You need to execute cutoff before you can transact, Continue?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener);

                    blockerDialog = builder.create();
                    builder.setCancelable(false);
                    builder.show();

                }
            } else if(infoModel.getInformation().equalsIgnoreCase("Generate end of day")) {//

                if (blockerDialog == null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    BusProvider.getInstance().post(new ButtonsModel(120,"ZREAD", "",21, 0));

//                                    zReadRequest(SharedPreferenceManager);
                                    blockerDialog = null;
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    blockerDialog = null;
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("This will generate end of day, continue?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener);

                    blockerDialog = builder.create();
                    builder.setCancelable(false);
                    builder.show();
                }




            }
        }



        if (!canTransact()) {


//            else {




//                if (cutOffDialog != null) {
//                    if (!cutOffDialog.isShowing()) {
//                        if (blockerDialog != null) {
//                            if (!blockerDialog.isShowing()) {
//                                blockerDialog.show();
//                            }
//                        } else {
//                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    switch (which){
//                                        case DialogInterface.BUTTON_POSITIVE:
//                                            if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {
//                                                BusProvider.getInstance().post(new ButtonsModel(121,"XREAD", "",19));
//                                            }
//                                            break;
//                                        case DialogInterface.BUTTON_NEGATIVE:
//                                            break;
//                                    }
//                                }
//                            };
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage("You need to execute cutoff before you can transact, Continue?")
//                                    .setPositiveButton("Yes", dialogClickListener)
//                                    .setNegativeButton("No", dialogClickListener);
//                            blockerDialog = builder.create();
//                            builder.show();
//                        }
//                    }
//                } else {
//                    if (blockerDialog != null) {
//                        if (!blockerDialog.isShowing()) {
//                            blockerDialog.show();
//                        }
//                    } else {
//                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                switch (which){
//                                    case DialogInterface.BUTTON_POSITIVE:
//                                        if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {
//                                            BusProvider.getInstance().post(new ButtonsModel(121,"XREAD", "",19));
//                                        }
//
//                                        break;
//
//                                    case DialogInterface.BUTTON_NEGATIVE:
//                                        break;
//                                }
//                            }
//                        };
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setMessage("You need to execute cutoff before you can transact, Continue?")
//                                .setPositiveButton("Yes", dialogClickListener)
//                                .setNegativeButton("No", dialogClickListener);
//                        blockerDialog = builder.create();
//                        builder.show();
//                    }
//                }
//            }


        }
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

        if (checkoutAdapter != null) {
            checkoutAdapter.notifyDataSetChanged();
        }
    }

    private void lightTheme() {

        listCheckoutItems.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        header.setBackgroundColor(getResources().getColor(R.color.lightTextHeader));
        cardHeader.setCardBackgroundColor(getResources().getColor(R.color.lightListBg));
        cardHeaderRoot.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        subTotal.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        deposit.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        discount.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        depositLabel.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        discountLabel.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        subTotalLabel.setBackgroundColor(getResources().getColor(R.color.lightListBg));
        totalLabel.setBackgroundColor(getResources().getColor(R.color.lightTextHeader));
        total.setBackgroundColor(getResources().getColor(R.color.lightTextHeader));



        header.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        subTotal.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        discount.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        deposit.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        total.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        depositLabel.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        discountLabel.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        subTotalLabel.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
        totalLabel.setTextColor(getResources().getColor(R.color.lightPrimaryFont));
    }

    private void darkTheme() {
        listCheckoutItems.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        header.setBackgroundColor(getResources().getColor(R.color.darkTextHeader));
        cardHeader.setCardBackgroundColor(getResources().getColor(R.color.darkListBg));
        cardHeaderRoot.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        subTotal.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        deposit.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        discount.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        depositLabel.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        discountLabel.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        subTotalLabel.setBackgroundColor(getResources().getColor(R.color.darkListBg));
        totalLabel.setBackgroundColor(getResources().getColor(R.color.darkTextHeader));
        total.setBackgroundColor(getResources().getColor(R.color.darkTextHeader));

        header.setTextColor(getResources().getColor(R.color.darkFont));
        total.setTextColor(getResources().getColor(R.color.darkFont));
        subTotal.setTextColor(getResources().getColor(R.color.darkFont));
        discount.setTextColor(getResources().getColor(R.color.darkFont));
        deposit.setTextColor(getResources().getColor(R.color.darkFont));
        depositLabel.setTextColor(getResources().getColor(R.color.darkFont));
        discountLabel.setTextColor(getResources().getColor(R.color.darkFont));
        subTotalLabel.setTextColor(getResources().getColor(R.color.darkFont));
        totalLabel.setTextColor(getResources().getColor(R.color.darkFont));
    }

    @Subscribe
    public void changeTheme(ChangeThemeModel changeThemeModel) {
        changeTheme();
    }

    private void detectSystem() {
        FetchBranchInfoRequest fetchBranchInfoRequest = new FetchBranchInfoRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchBranchInfoResponse> request = iUsers.fetchBranchInfo(fetchBranchInfoRequest.getMapValue());

        request.enqueue(new Callback<FetchBranchInfoResponse>() {
            @Override
            public void onResponse(Call<FetchBranchInfoResponse> call, Response<FetchBranchInfoResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult().getCompanyInfo().getIsRoom().equalsIgnoreCase("0") &&
                            response.body().getResult().getCompanyInfo().getIsTable().equalsIgnoreCase("0")) {
                        //franchise
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        FetchOrderPendingRequest fetchOrderPendingRequest = new FetchOrderPendingRequest();
                        Call<FetchOrderPendingResponse> request = iUsers.fetchOrderPending(fetchOrderPendingRequest.getMapValue());
                        request.enqueue(new Callback<FetchOrderPendingResponse>() {
                            @Override
                            public void onResponse(Call<FetchOrderPendingResponse> call, Response<FetchOrderPendingResponse> response) {
                                boolean hasPendingCleanTransaction = false;
                                if (response.body().getResult().size() > 0) {
                                    for (FetchOrderPendingResponse.Result r : response.body().getResult()) {
                                        if (r.getTotal() < 1) {
                                            selectedRoom = new RoomTableModel(r.getControlNo(), true);
                                            fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                            hasPendingCleanTransaction = true;
                                            break;
                                        }
                                    }

                                    if (!hasPendingCleanTransaction) {
                                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                        GetOrderRequest getOrderRequest = new GetOrderRequest("EMPTY", "1", "");
                                        Call<GetOrderResponse> request = iUsers.getOrder(getOrderRequest.getMapValue());
                                        request.enqueue(new Callback<GetOrderResponse>() {
                                            @Override
                                            public void onResponse(Call<GetOrderResponse> call, final Response<GetOrderResponse> response) {
                                                selectedRoom = new RoomTableModel(response.body().getResult().getControlNo(), true);
                                                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                            }

                                            @Override
                                            public void onFailure(Call<GetOrderResponse> call, Throwable t) {

                                            }
                                        });
                                    }
                                } else {
                                    IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                                    GetOrderRequest getOrderRequest = new GetOrderRequest("EMPTY", "1", "");
                                    Call<GetOrderResponse> request = iUsers.getOrder(getOrderRequest.getMapValue());
                                    request.enqueue(new Callback<GetOrderResponse>() {
                                        @Override
                                        public void onResponse(Call<GetOrderResponse> call, final Response<GetOrderResponse> response) {
                                            selectedRoom = new RoomTableModel(response.body().getResult().getControlNo(), true);
                                            fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                                        }

                                        @Override
                                        public void onFailure(Call<GetOrderResponse> call, Throwable t) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<FetchOrderPendingResponse> call, Throwable t) {

                            }
                        });

                    } else if (response.body().getResult().getCompanyInfo().getIsTable().equalsIgnoreCase("1")) {
                        //table
                    } else if (response.body().getResult().getCompanyInfo().getIsRoom().equalsIgnoreCase("1")) {
                        //room
                    } else {
                        //not supported
                    }
                }

            }

            @Override
            public void onFailure(Call<FetchBranchInfoResponse> call, Throwable t) {

            }
        });
    }

    @Subscribe
    public void globalServerTimeListener(GlobalServerTime globalServerTime) {
        globalServerTimeString = globalServerTime.getServerTime();
    }




}
