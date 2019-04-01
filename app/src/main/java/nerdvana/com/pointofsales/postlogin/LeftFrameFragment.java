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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

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
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.AddPaymentRequest;
import nerdvana.com.pointofsales.api_requests.AddProductToRequest;
import nerdvana.com.pointofsales.api_requests.AddRoomPriceRequest;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.CheckOutRequest;
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
import nerdvana.com.pointofsales.api_requests.FocRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
import nerdvana.com.pointofsales.api_requests.SwitchRoomRequest;
import nerdvana.com.pointofsales.api_requests.WelcomeGuestRequest;
import nerdvana.com.pointofsales.api_responses.AddPaymentResponse;
import nerdvana.com.pointofsales.api_responses.AddProductToResponse;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckOutResponse;
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
import nerdvana.com.pointofsales.api_responses.FocResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.RatePrice;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.api_responses.RoomRateMainViaId;
import nerdvana.com.pointofsales.api_responses.RoomRateSubViaId;
import nerdvana.com.pointofsales.api_responses.SwitchRoomResponse;
import nerdvana.com.pointofsales.api_responses.WelcomeGuestResponse;
//import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.background.RetrieveCartItemsAsync;
import nerdvana.com.pointofsales.background.SaveTransactionAsync;
import nerdvana.com.pointofsales.custom.SwipeToDeleteCallback;
import nerdvana.com.pointofsales.dialogs.CheckInDialog;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.dialogs.ConfirmWithRemarksDialog;
import nerdvana.com.pointofsales.dialogs.DiscountSelectionDialog;
import nerdvana.com.pointofsales.dialogs.ManualDiscountDialog;
import nerdvana.com.pointofsales.dialogs.FocDialog;
import nerdvana.com.pointofsales.dialogs.GuestInfoDialog;
import nerdvana.com.pointofsales.dialogs.OpenPriceDialog;
import nerdvana.com.pointofsales.dialogs.OrderSlipDialog;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.dialogs.RateDialog;
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
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.OrderSlipModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeftFrameFragment extends Fragment implements AsyncContract, CheckoutItemsContract,
         SaveTransactionContract, RetrieveCartItemContract, View.OnClickListener {

    private FetchRoomPendingResponse.Result fetchRoomPendingResult = null;

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




        fetchArOnlineRequest();
        fetchNationalityRequest();
        fetchCreditCardRequest();
        fetchCurrencyExceptDefaultRequest();


        initializeViews(view);
        setProductAdapter();
//        setButtonsAdapter();


        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
            isValid = true;
        }

        TypeToken<List<FetchUserResponse.Result>> token = new TypeToken<List<FetchUserResponse.Result>>() {};
        userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_JSON), token.getType());


        TypeToken<List<FetchRoomAreaResponse.Result>> areaToken = new TypeToken<List<FetchRoomAreaResponse.Result>>() {};
        roomAreaList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.ROOM_AREA_JSON), areaToken.getType());






        defaultView();
        if (!TextUtils.isEmpty(selectedRoomNumber())) {
            //reload data from selected table && set views
//            retrieveCartItems();
            setView(selectedRoomNumber());
//            computeFromDb();
        }

        fetchCarRequest();
        fetchVehicleRequest();
        fetchGuestTypeRequest();
        fetchPaymentTypeRequest();


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
            setView(selectedRoom.getSelectedRoom().getName());
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
                        false
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
                            false
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
        if (itemSelected.isProduct()) {
            popupMenu.show();
        } else {
            roomPopupMenu.show();
        }

    }

    @Subscribe
    public void clickedButton(ButtonsModel clickedItem) {
        switch (clickedItem.getId()) {
            case 118:// SAFEKEEPING
                CollectionDialog safeKeepingDialog = new CollectionDialog(getActivity(), "SAFEKEEPING");
                if (!safeKeepingDialog.isShowing()) safeKeepingDialog.show();
                break;
            case 117: //CUTOFF
                CollectionDialog cutOffDialog = new CollectionDialog(getActivity(), "CUTOFF");
                if (!cutOffDialog.isShowing()) cutOffDialog.show();
                break;
            case 116: //cancel selected room / TO
                defaultView();
                clearCartItems();
                break;
            case 115://DISCOUNT

                //fetchRoomPendingResult

                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {

                    } else {
                        DiscountSelectionDialog discountSelectionDialog = new DiscountSelectionDialog(getContext(), getActivity(), fetchRoomPendingResult );

                        if (!discountSelectionDialog.isShowing()) discountSelectionDialog.show();
                    }


                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }

                break;
            case 114://SWITCH ROOM
                if (selectedRoom != null) {
                    SwitchRoomDialog switchRoomDialog = new SwitchRoomDialog(getActivity(), selectedRoom.getName()) {
                        @Override
                        public void switchRoomConfirm(final String roomRatePriceId, final String qty,
                                                      final String price, final String rateName,
                                                      final String roomId, List<RoomRateMain> roomRateMainList) {
                            ConfirmWithRemarksDialog cfrmDialog = new ConfirmWithRemarksDialog(getActivity()) {
                                @Override
                                public void save(String remarks) {
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

//                                    Log.d("TESDATAT", new SwitchRoomRequest(
//                                            String.valueOf(selectedRoom.getRoomId()),
//                                            roomRatePriceId,
//                                            remarks,
//                                            roomId,
//                                            "658",
//                                            voidModel
//                                    ).toString());

                                    BusProvider.getInstance().post(
                                            new SwitchRoomRequest(
                                                    String.valueOf(selectedRoom.getRoomId()),
                                                    roomRatePriceId,
                                                    remarks,
                                                    roomId,
                                                    "658",
                                                    voidModel
                                            )
                                    );


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
                        Utils.showDialogMessage((MainActivity)getContext(), "Room not yet checked-in", "Information");
                    }
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }


                break;
            case 113://POST VOID
                TransactionsDialog postVoid = new TransactionsDialog(getActivity(), false);

                if (!postVoid.isShowing()) {
                    postVoid.show();
                }
                break;
            case 112://VIEW RECEIPT
                TransactionsDialog transactionsDialog = new TransactionsDialog(getActivity(), true);

                if (!transactionsDialog.isShowing()) {
                    transactionsDialog.show();
                }


                break;
            case 111://GUEST INFO
                GuestInfoDialog guestInfoDialog = new GuestInfoDialog(getActivity());

                if (selectedRoom != null) {
                    if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                            currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA)) {
                        guestInfoDialog.show();
                    } else {
                        Utils.showDialogMessage((MainActivity)getContext(), "No guest info yet", "Information");
                    }
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
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
            case 109: //FOC
                FocDialog focDialog = new FocDialog(getActivity(), postedPaymentsList) {
                    @Override
                    public void focSuccess() {
                        dismiss();
                        Toast.makeText(getContext(), "SUCC FOC", Toast.LENGTH_SHORT).show();
                        focTransaction();
                    }
                };
                if (selectedRoom != null) {
                    if (selectedRoom.getStatus().equalsIgnoreCase("17")) {

                        if (advancePayment != 0 || discountPayment != 0) {
                            Utils.showDialogMessage((MainActivity)getContext(), "Please remove advance payment / discount", "Information");
                        } else {
                            if (!focDialog.isShowing()) focDialog.show();
                        }


                    } else {
                        Utils.showDialogMessage((MainActivity)getContext(), "Please SOA room first", "Information");
                    }
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }
                break;
                //
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
                            Utils.showDialogMessage((MainActivity)getContext(), "Room not yet occupied / SOA", "Information");
                        }
                    }

                } else {

                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }
                break;
            case 107: //CHECK IN - WAITING GUEST (DIRTY / RC)
                if (selectedRoom != null) {
                    if (selectedRoom.getStatus().equalsIgnoreCase("32") ||
                            selectedRoom.getStatus().equalsIgnoreCase("4") ||
                            selectedRoom.getStatus().equalsIgnoreCase("59")) {
                        showCheckInDialog();
                    } else {
                        Utils.showDialogMessage((MainActivity)getContext(), "Room already checked-in", "Information");
                    }
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }

                break;
            case 106: //PRINT SOA
                if (selectedRoom != null) {
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
                                Utils.showDialogMessage((MainActivity)getContext(), "Cannot SOA a room without a room rate, add one first", "Information");
                            }

                        } else {
                            Utils.showDialogMessage((MainActivity)getContext(), "Please SOA room first", "Information");
                        }
                    }
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }


                break;
            case 105: //CHECKOUT
                PaymentDialog checkoutDialog = new PaymentDialog(getActivity(),
                        paymentTypeList,
                        true,
                        postedPaymentsList,
                        totalBalance,
                        currencyList,
                        creditCardList,
                        arOnlineList) {
                    @Override
                    public void paymentSuccess(List<PostedPaymentsModel> postedPaymentLit) {
                        List<PostedPaymentsModel> paymentsToPost = new ArrayList<>();
                        boolean isReadyForCheckOut = false;
                        Double totalPayments = 0.00;
                        for (PostedPaymentsModel ppm : postedPaymentLit) {
                            if (!ppm.isIs_posted()) {
                                paymentsToPost.add(ppm);
                            }

                            totalPayments += Double.valueOf(ppm.getAmount());
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
                                                        selectedRoom.getControlNo());
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
                            if (totalPayments >= totalBalance) {
                                if (paymentsToPost.size() > 0) {
                                    if (selectedRoom.isTakeOut()) {
                                        postCheckoutPayment(paymentsToPost, "", selectedRoom.getControlNo());
                                    } else {
                                        postCheckoutPayment(paymentsToPost, String.valueOf(selectedRoom.getRoomId()), "");
                                    }
                                } else {
                                    Toast.makeText(getContext(), "No payment to post", Toast.LENGTH_SHORT).show();
                                }

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (selectedRoom.isTakeOut()) {
                                            checkoutRoom("",
                                                    selectedRoom.getControlNo());
                                        } else {
                                            checkoutRoom(String.valueOf(selectedRoom.getRoomId()),
                                                    "");
                                        }


                                    }
                                }, 500);
                                dismiss();
                            } else {

                                Utils.showDialogMessage(getActivity(), "Payment is less than balance", "Warning");
                            }
                        }


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

                                Utils.showDialogMessage((MainActivity)getContext(), "Please soa room first", "Information");
                            }

                        } else {

                            Utils.showDialogMessage((MainActivity)getContext(), "No payment type found, please re-open the application", "Information");
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

                                    Utils.showDialogMessage((MainActivity)getContext(), "Cannot checkout a room without a room rate, add one first", "Information");
                                }
                            } else {

                                Utils.showDialogMessage((MainActivity)getContext(), "No payment type found, please re-open the application", "Information");

                            }
                        } else {
                            Utils.showDialogMessage((MainActivity)getContext(), "Please print SOA first", "Information");
                        }
                    }

                } else {

                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }
                break;
            case 103: //ADD RATE TO EXISTING TRANSACTION
                if (selectedRoom != null) {
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

                            Utils.showDialogMessage((MainActivity)getContext(), "Room not yet occupied", "Information");
                        }
                    } else {

                        Utils.showDialogMessage((MainActivity)getContext(), "Adding rate feature is for rooms only", "Information");
                    }

                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
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
                            Utils.showDialogMessage((MainActivity)getContext(), "Please select item/s to order", "Information");
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
                                Utils.showDialogMessage((MainActivity)getContext(), "Please select item/s to order", "Information");
                            } else {
                                confirmWithRemarksDialog.show();
                            }





                        }

                    }


                }
                break;
            case 101: //VOID
                if (selectedRoom != null) {

                    if (cartItemList.size() < 1) {
                        Utils.showDialogMessage((MainActivity)getContext(), "No items to void", "Information");
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
                            public void passwordSuccess(final String employeeId) {


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
                            Utils.showDialogMessage((MainActivity) getContext(), "Please select item/s to void", "Information");
                        }



                    }


                } else {

                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }

                break;
            case 102: //ADVANCE PAYMENT

                if (selectedRoom != null) {
                    PaymentDialog paymentDialog = new PaymentDialog(getActivity(),
                            paymentTypeList,
                            false,
                            postedPaymentsList,
                            totalBalance,
                            currencyList,
                            creditCardList,
                            arOnlineList) {
                        @Override
                        public void paymentSuccess(List<PostedPaymentsModel> postedPaymentLit) {
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

                                BusProvider.getInstance().post(new PrintModel("",
                                        selectedRoom.getName(),
                                        "DEPOSIT",
                                        GsonHelper.getGson().toJson(paymentsToPost),
                                        selectedRoom.getRoomType()));

                            } else {

                                Utils.showDialogMessage((MainActivity)getContext(), "No payment to post", "Information");
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

                                Utils.showDialogMessage((MainActivity)getContext(), "No payment type found, please re-open the paplication", "Information");
                            }
                        } else {
                            Utils.showDialogMessage((MainActivity)getContext(), "Room not yet occupied, cant accept payment", "Information");
                        }
                    }

                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No room selected", "Information");
                }



//                if ((userModel.getSystemType().equals(SystemConstants.SYS_ROOM) ||
//                        userModel.getSystemType().equals(SystemConstants.SYS_TABLE)) &&
//                    TextUtils.isEmpty(selectedRoomNumber())) {
//                    //alert need to select table / room
//                    Toast.makeText(getContext(), getString(R.string.error_no_space_selected), Toast.LENGTH_SHORT).show();
//                } else if(userModel.getSystemType().equals(SystemConstants.SYS_CHECKOUT)) {
//
//                    //show total
//
//                } else {

                    //loop payment details then pass to payment dialog
//                    if (getTableRecord().size() > 0) {
//                        double balance = 0;
//                        for (ProductsModel selectedItem : cartItemList) {
//                            balance += selectedItem.getPrice();
//                        }
//                        PaymentDialog paymentDialog = new PaymentDialog(getActivity(), getTableRecord().get(0).getTransactionId(), balance) {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//
//                            @Override
//                            public void paymentSuccess() {
//                                if (getTableRecord().size() > 0) {
//                                    String tempTransId = getTableRecord().get(0).getTransactionId();
//                                    for (TransactionEntity t : getTableRecord()) {
//                                        if (t.getTransactionStatus() == TransactionConstants.PENDING) {
//                                            t.setTransactionStatus(TransactionConstants.FULLY_PAID);
//                                            t.save();
//                                        }
//                                    }
//                                    for (CartEntity c : getCartRecord(tempTransId)) {
//                                        if (c.getProductStatus() != ProductConstants.PENDING &&
//                                                c.getProductStatus() != ProductConstants.VOID &&
//                                                c.getProductStatus() != ProductConstants.DISABLED)
//                                            c.setProductStatus(ProductConstants.PAID);
//                                        c.save();
//                                    }
//                                }
//
//                                clearCartItems();
//                                defaultView();
//                                CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
//                            }
//
//                            @Override
//                            public void paymentFailed() {
//
//                            }
//                        };
////                        PaymentDialog paymentDialog = new PaymentDialog(getActivity(), getTableRecord().get(0).getTransactionId(), balance);
//                        paymentDialog.show();
////                        Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
////                        paymentIntent.putExtra("transaction_number", getTableRecord().get(0).getTransactionId());
////                        startActivityForResult(paymentIntent, 100);
//                    } else {
//                        Toast.makeText(getContext(), "No transactions made yet.", Toast.LENGTH_SHORT).show();
//                    }
//                }

                break;
        }
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

//                final ProductsModel itemToRestore = cartItemList.get(viewHolder.getAdapterPosition());
//                new DeleteCartItemAsync(cartItemList.get(viewHolder.getAdapterPosition())).execute();

                cartItemList.remove(viewHolder.getAdapterPosition());

                checkoutAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

//                computeFromDb();
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (cartItemList.size() > 0) {
                    if (cartItemList.get(viewHolder.getAdapterPosition()).isPosted()) {
                        return 0;
                    }
                }

//                if (cartItemList.get(viewHolder.getAdapterPosition()).getProductStatus() != ProductConstants.PENDING) {
//                    return 0;
//                }
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

    private void fetchRoomPending(String roomId) {

        BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));


        printReceiptFromCheckout("VCHI-2019-00000001");


        showLoading();
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

    private void fetchOrderPendingViaControlNo(String controlNo) {
        BusProvider.getInstance().post(new FetchOrderPendingViaControlNoRequest(controlNo));
    }

    @Subscribe
    public void fetchRoomPendingResponse(FetchRoomPendingResponse fetchRoomPendingResponse) {
        cartItemList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        Double totalAmount = 0.00;
        discountPayment = 0.00;
        advancePayment = 0.00;
        currentRoomStatus = String.valueOf(fetchRoomPendingResponse.getResult().getStatus());
        if (fetchRoomPendingResponse.getResult().getBooked().size() > 0) {
            for (FetchRoomPendingResponse.Booked r : fetchRoomPendingResponse.getResult().getBooked()) {
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
                                symbolRight
                        ));
                    }
                }

                if (r.getTransaction().getTrans().size() > 0) {

                    totalBalance = fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTotal() +
                            fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getOtAmount() +
                            fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getXPersonAmount();



                    advancePayment = r.getTransaction().getAdvance();
                    discountPayment = r.getTransaction().getDiscount();
                    subTotal.setText(String.valueOf(totalBalance));

                    total.setText(String.valueOf(totalBalance - (advancePayment + discountPayment)));
                    discount.setText(String.valueOf(discountPayment));
//                    tax.setText(String.valueOf(r.getTransaction().getVat()));
                    deposit.setText(String.valueOf(r.getTransaction().getAdvance()));
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
                                    false
                            ));
                        } else {
                            Log.d("COUNTER", String.valueOf(roomRateCounter.size()));
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
                                    false
                            ));
                        }

                        totalAmount += tpost.getTotal();
                    }
                }
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

                                    BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                            welcomeGuestRequest.getRoomRatePriceId(),
                                            remarks));
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
            checkInDialog = new CheckInDialog(getActivity(), selectedRoom,
                    carList, vehicleList,
                    guestTypeList, userList,
                    roomAreaList, nationalityList, fetchRoomPendingResult) {
                @Override
                public void successCheckIn(final WelcomeGuestRequest welcomeGuestRequest) {
                    ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getActivity()) {
                        @Override
                        public void save(String remarks) {
                            BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                    welcomeGuestRequest.getRoomRatePriceId(),
                                    remarks));
                        }
                    };

                    confirmWithRemarksDialog.show();

                }
            };
            if (!checkInDialog.isShowing()) checkInDialog.show();
        } else {
            Utils.showDialogMessage(getActivity(), "Fetch room pending result is null", "Information");
        }

        checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
            }
        });

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

                BusProvider.getInstance().post(new PrintModel("", "ROOM# "+ selectedRoom.getName(), "FO", GsonHelper.getGson().toJson(model)));
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
        fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
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

    private void postAdvancePayment(List<PostedPaymentsModel> ppm, String roomId, String controlNumber) {
        BusProvider.getInstance().post(new AddPaymentRequest(ppm, roomId, "1", controlNumber));

        showLoading();
    }

    private void postCheckoutPayment(List<PostedPaymentsModel> ppm, String roomId, String  controlNumber) {
//        Log.d("CHECKOUTDATA", new AddPaymentRequest(ppm, roomId, "0", controlNumber).toString());
        BusProvider.getInstance().post(new AddPaymentRequest(ppm, roomId, "0", controlNumber));

        showLoading();
    }

    @Subscribe
    public void printSoaResponse(PrintSoaResponse printSoaResponse) {

        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
                BusProvider.getInstance().post(new PrintModel("",
                        selectedRoom.getName(),
                        "SOA-TO",
                        GsonHelper.getGson().toJson(printSoaResponse.getResult())
                ));

                if (printSoaResponse.getResult().getToPostList().size() > 0) {
                    Utils.showDialogMessage((MainActivity)getContext(), "Printing statement of account", "Information");
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No item/s to print", "Information");
                }
            } else {
                BusProvider.getInstance().post(new PrintModel("",
                        selectedRoom.getName(),
                        "SOA-ROOM",
                        GsonHelper.getGson().toJson(printSoaResponse.getResult().getBooked())
                ));
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));

                if (printSoaResponse.getResult().getBooked().size() > 0) {
                    Utils.showDialogMessage((MainActivity)getContext(), "Printing statement of account", "Information");
                } else {
                    Utils.showDialogMessage((MainActivity)getContext(), "No item/s to print", "Information");
                }
            }



        }
    }

    @Subscribe
    public void addPaymentResponse(AddPaymentResponse addPaymentResponse) {
        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
            } else {
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
            }
        }
    }

    private void printSoaRequest(String roomId, String controlNumber) {
        BusProvider.getInstance().post(new PrintSoaRequest(roomId, controlNumber));
    }

    @Subscribe
    public void fetchOrderPendingViaControlNoResponse(FetchOrderPendingViaControlNoResponse fetchOrderPendingViaControlNoResponse) {
//        Toast.makeText(getContext(), "FOP RESP", Toast.LENGTH_SHORT).show();
        currentRoomStatus = String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getIsSoa());
        totalBalance = fetchOrderPendingViaControlNoResponse.getResult().getTotal() - (fetchOrderPendingViaControlNoResponse.getResult().getAdvance() + fetchOrderPendingViaControlNoResponse.getResult().getDiscount());
        cartItemList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        Double totalAmount = 0.00;
        if (fetchOrderPendingViaControlNoResponse.getResult() != null) {
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
                            symbolRight
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
                                false
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
    }

    @Subscribe
    public void addProductToResponse(AddProductToResponse addProductToResponse) {
        if (selectedRoom != null) {
            fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
        }

    }

    private void checkoutRoom(String roomId, String controlNumber) {
        Log.d("CHECKOUTDATA", new CheckOutRequest(roomId, controlNumber, "1").toString());
//        BusProvider.getInstance().post(new CheckOutRequest(roomId, controlNumber, "1"));
    }

    @Subscribe
    public void checkoutResponse(CheckOutResponse checkOutResponse) {
        clearCartItems();
        defaultView();
        Toast.makeText(getContext(), "checkout", Toast.LENGTH_SHORT).show();
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


    private void focTransaction() {
        BusProvider.getInstance().post(new FocRequest());
    }

    @Subscribe
    public void focResponse(FocResponse focResponse) {

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

        }
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
            Utils.showDialogMessage((MainActivity)getContext(), switchRoomResponse.getMesage(), "Warning");
        } else {
            if (switchRoomResponse.getResults() != null) {
                if (switchRoomResponse.getResults().getBooked().size() > 0) {
                    fetchRoomViaIdRequest(String.valueOf(switchRoomResponse.getResults().getBooked().get(0).getRoomId()));
                    Utils.showDialogMessage((MainActivity)getContext(), "Switch room succeeded", "Success");
                }

            }
        }



    }

    private void fetchRoomViaIdRequest(String roomId) {
        BusProvider.getInstance().post(new FetchRoomViaIdRequest(roomId));
    }

    @Subscribe
    public void fetchRoomViaIdResponse(FetchRoomViaIdResponse r) {

        Toast.makeText(getContext(),"FETCH ROOM VIA ID SUCCSS", Toast.LENGTH_SHORT).show();


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
                r.getResult().getTransaction() != null ? String.valueOf(r.getResult().getTransaction().getTransaction().getOtHours()) : ""
        );

        currentRoomStatus = selectedRoom.getStatus();
        setView(selectedRoom.getName());
    }

    private void printReceiptFromCheckout(String controlNumber) {
        Toast.makeText(getContext(), "PRINT", Toast.LENGTH_SHORT).show();
        FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
        request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
            @Override
            public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                BusProvider.getInstance().post(new PrintModel(
                        "", selectedRoom.getName(),
                        "PRINT_RECEIPT", GsonHelper.getGson().toJson(response.body().getResult())));
            }

            @Override
            public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

            }
        });
    }
}
