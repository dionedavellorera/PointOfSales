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
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
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
import nerdvana.com.pointofsales.api_requests.FetchDefaultCurrencyRequest;
import nerdvana.com.pointofsales.api_requests.FetchGuestTypeRequest;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingViaControlNoRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.FocRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
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
import nerdvana.com.pointofsales.api_responses.FetchDefaultCurrenyResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FocResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.api_responses.WelcomeGuestResponse;
//import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.background.RetrieveCartItemsAsync;
import nerdvana.com.pointofsales.background.SaveTransactionAsync;
import nerdvana.com.pointofsales.custom.SwipeToDeleteCallback;
import nerdvana.com.pointofsales.dialogs.CheckInDialog;
import nerdvana.com.pointofsales.dialogs.ConfirmCheckInDialog;
import nerdvana.com.pointofsales.dialogs.FocDialog;
import nerdvana.com.pointofsales.dialogs.OpenPriceDialog;
import nerdvana.com.pointofsales.dialogs.OrderSlipDialog;
import nerdvana.com.pointofsales.dialogs.PasswordDialog;
import nerdvana.com.pointofsales.dialogs.PaymentDialog;
import nerdvana.com.pointofsales.dialogs.RateDialog;
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
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;

public class LeftFrameFragment extends Fragment implements AsyncContract, CheckoutItemsContract,
         SaveTransactionContract, RetrieveCartItemContract, View.OnClickListener {
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
    private TextView tax;
    private TextView subTotal;
    private TextView header;
    private TextView noItems;


    private List<FetchCurrencyExceptDefaultResponse.Result> currencyList;
    private List<FetchCreditCardResponse.Result> creditCardList;
    private List<FetchArOnlineResponse.Result> arOnlineList;
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

    public static LeftFrameFragment newInstance(SelectionContract selectionContract) {
        LeftFrameFragment.selectionContract = selectionContract;
        LeftFrameFragment leftFrameFragment = new LeftFrameFragment();
        return leftFrameFragment;
    }

    private void initializeViews(View view) {
        roomRateCounter = new ArrayList<>();
        total = view.findViewById(R.id.totalValue);
        discount = view.findViewById(R.id.discountValue);
        tax = view.findViewById(R.id.taxValue);
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_left_frame, container, false);


        fetchArOnlineRequest();
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
            tax.setText("0.00");
            subTotal.setText("0.00");


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
        Log.d("LOGIC", String.valueOf(selectedRoom.getSelectedRoom().isTakeOut()));
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
            case 110:// NOT YET AVAILALBLE
                Toast.makeText(getContext(), "FEATURE NOT YET AVAILABLE", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Please remove advance payment / discount", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!focDialog.isShowing()) focDialog.show();
                        }


                    } else {
                        Toast.makeText(getContext(), "Room not soa", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No room selected", Toast.LENGTH_SHORT).show();
                }
                break;
                //
            case 108: //show order slip form

                OrderSlipDialog orderSlipDialog = new OrderSlipDialog(getActivity(), orderSlipList);
                if (selectedRoom != null) {
                    if (selectedRoom.getStatus().equalsIgnoreCase("2") || selectedRoom.getStatus().equalsIgnoreCase("17")) {
                        if (!orderSlipDialog.isShowing()) orderSlipDialog.show();
                    } else {
                        Toast.makeText(getContext(), "Room not occupied / soa", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "No room selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case 107: //CHECK IN - WAITING GUEST (DIRTY / RC)
                if (selectedRoom.getStatus().equalsIgnoreCase("32") ||
                        selectedRoom.getStatus().equalsIgnoreCase("4") ||
                        selectedRoom.getStatus().equalsIgnoreCase("59")) {
                    showCheckInDialog();
                } else {
                    Toast.makeText(getContext(), "Room already checked in", Toast.LENGTH_SHORT).show();
                }
                break;
            case 106: //PRINT SOA
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        printSoaRequest("", selectedRoom.getControlNo());
                    } else {
                        if (currentRoomStatus.equalsIgnoreCase("2") || currentRoomStatus.equalsIgnoreCase("17")) {
                            printSoaRequest(String.valueOf(selectedRoom.getRoomId()), "");
                        } else {
                            Toast.makeText(getContext(), "Room not occupied, cannot soa", Toast.LENGTH_SHORT).show();
                        }
                    }
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
                                            Log.d("CHECKOUT_LOG", "TAKEOUT_OFF");
                                            checkoutRoom("",
                                                    selectedRoom.getControlNo());
                                        } else {
                                            Log.d("CHECKOUT_LOG", "ROOM_OFF");
                                            checkoutRoom(String.valueOf(selectedRoom.getRoomId()),
                                                    "");
                                        }


                                    }
                                }, 500);
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "Payment is less than balance", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getContext(), "Please print soa before checking out", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), "No payment type found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (currentRoomStatus.equalsIgnoreCase("17")) {
                            if (paymentTypeList.size() > 0) {
                                checkoutDialog.show();
                            } else {
                                Toast.makeText(getContext(), "No payment type found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Please print soa before checking out", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getContext(), "No room selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case 103: //ADD RATE TO EXISTING TRANSACTION
                if (selectedRoom != null) {
                    if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED)) {
                        final RateDialog rateDialog = new RateDialog(getContext(), selectedRoom.getPrice()) {
                            @Override
                            public void rateChangeSuccess(RoomRateMain selectedRate, String qty) {
                                addRateRequest("0",
                                        String.valueOf(selectedRate.getRoomRatePriceId()),
                                        qty,
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                        String.valueOf(selectedRoom.getRoomId()),
                                        String.valueOf(selectedRate.getRatePrice().getAmount()));
                                this.dismiss();
                            }
                        };

                        rateDialog.show();

                        Toast.makeText(getContext(), "ADD RATE DIALOG",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ROOM NOT OCCUPIED", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "PLEASE SELECT A ROOM", Toast.LENGTH_SHORT).show();
                }

                break;
            case 100: //SAVE TRANSACTION:
                if (selectedRoom != null) {
                    if (selectedRoom.isTakeOut()) {
                        Log.d("ISTAKEOUT", "!");
//                                BusProvider.getInstance().post(new AddProductToRequest(model, String.valueOf(selectedRoom.getRoomId())));
                        ArrayList<AddRateProductModel> model = new ArrayList<>();
                        ArrayList<VoidProductModel> voidModel = new ArrayList<>();
//                        model.add(new AddRateProductModel(productId, roomRatePriceId, quantity, tax));
                        for (CartItemsModel cim : cartItemList) {
                            if (!cim.isPosted() && cim.isProduct()) {
                                model.add(new AddRateProductModel(
                                        String.valueOf(cim.getProductId()),
                                        "0",
                                        String.valueOf(cim.getQuantity()),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                        String.valueOf(cim.getUnitPrice()),
                                        cim.getIsPriceChanged()
                                ));
                            }

                            if (cim.isForVoid()) {
                                voidModel.add(new VoidProductModel(
                                        cim.getPostId()
                                ));
                            }
                        }

                        BusProvider.getInstance().post(new AddProductToRequest(model, String.valueOf(selectedRoom.getRoomId()),
                                String.valueOf(selectedRoom.getAreaId()),
                                selectedRoom.getControlNo(),
                                voidModel));
                    } else {
                        Log.d("ISTAKEOUT", "@");
                        if (currentRoomStatus.equalsIgnoreCase(RoomConstants.OCCUPIED) ||
                                currentRoomStatus.equalsIgnoreCase(RoomConstants.SOA) ||
                                selectedRoom.getStatus().equalsIgnoreCase("4") ||
                                selectedRoom.getStatus().equalsIgnoreCase("59")) {
                            ArrayList<AddRateProductModel> model = new ArrayList<>();
//                        model.add(new AddRateProductModel(productId, roomRatePriceId, quantity, tax));
                            for (CartItemsModel cim : cartItemList) {
                                if (!cim.isPosted() && cim.isProduct()) {
                                    model.add(new AddRateProductModel(
                                            String.valueOf(cim.getProductId()),
                                            "0",
                                            String.valueOf(cim.getQuantity()),
                                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.TAX_RATE),
                                            String.valueOf(cim.getUnitPrice()),
                                            cim.getIsPriceChanged()
                                    ));
                                }
                            }

                            BusProvider.getInstance().post(new AddRoomPriceRequest(model, String.valueOf(selectedRoom.getRoomId())));


//                            if (model.size() > 0) {
//
//                            } else {
//                                Toast.makeText(getContext(), "No product for posting", Toast.LENGTH_SHORT).show();
//                            }
                        }

                    }


                }
//                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));


//                saveTransaction();
//                Toast.makeText(getContext(), "SAVE TRANS MADE", Toast.LENGTH_SHORT).show();
                break;
            case 101: //VOID
                final PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                    @Override
                    public void passwordSuccess() {
                        if (cartItemList.size() < 1) {
                            Toast.makeText(getContext(), "No items to void", Toast.LENGTH_SHORT).show();
                        } else {

                            ArrayList<VoidProductModel> model = new ArrayList<>();

                            for (CartItemsModel cim : cartItemList) {
                                if (cim.isSelected()) {
                                    model.add(new VoidProductModel(
                                        cim.getPostId()
                                    ));
                                }
                            }
//
                            BusProvider.getInstance().post(new AddProductToRequest(new ArrayList<AddRateProductModel>(), String.valueOf(selectedRoom.getRoomId()),
                                    String.valueOf(selectedRoom.getAreaId()),
                                    selectedRoom.getControlNo(),
                                    model));
                        }

                    }

                    @Override
                    public void passwordFailed() {

                    }
                };
                if(!passwordDialog.isShowing()) passwordDialog.show();
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

                            } else {
                                Toast.makeText(getContext(), "No payment to post", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getContext(), "No payment type found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Room not yet occupied, cant accept payment", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getContext(), "No room selected", Toast.LENGTH_SHORT).show();
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
    }

    @Subscribe
    public void fetchGuestTypeResponse(FetchGuestTypeResponse fetchGuestTypeResponse) {
        guestTypeList = fetchGuestTypeResponse.getResult();
    }

    @Subscribe
    public void guestWelcomeResponse(WelcomeGuestResponse welcomeGuestResponse) {

        if (selectedRoom != null) {

//            fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
//            BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId())));
        }

    }

    private void fetchRoomPending(String roomId) {

        BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));

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

            totalBalance = fetchRoomPendingResponse.getResult().getBooked().get(0).getTransaction().getTotal();
            for (FetchRoomPendingResponse.Booked r : fetchRoomPendingResponse.getResult().getBooked()) {
                if (r.getTransaction().getPayments().size() > 0) {
                    for(FetchRoomPendingResponse.Payment pym : r.getTransaction().getPayments()) {
                        postedPaymentsList.add(new PostedPaymentsModel(
                                String.valueOf(pym.getPaymentTypeId()),
                                String.valueOf(pym.getAmount()),
                                pym.getPaymentDescription(),
                                true,
                                String.valueOf(pym.getCurrencyId()),
                                String.valueOf(pym.getCurrencyValue()),
                                new JSONObject()
                        ));
                    }
                }

                if (r.getTransaction().getTrans().size() > 0) {

                    advancePayment = r.getTransaction().getAdvance();
                    discountPayment = r.getTransaction().getDiscount();

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

        total.setText(String.valueOf(totalAmount));
        checkoutAdapter = new CheckoutAdapter(this.cartItemList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        listCheckoutItems.setLayoutManager(linearLayoutManager);
        listCheckoutItems.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();


        if (fetchRoomPendingResponse.getResult() != null) {
            switch (fetchRoomPendingResponse.getResult().getStatus()) {
                case 3: //dirty
                    showGuestInfoDialog(String.valueOf(fetchRoomPendingResponse.getResult().getStatus()));
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
    }

    private void sendOffGoingNegoRequest(String roomId) {
        BusProvider.getInstance().post(new OffGoingNegoRequest(roomId));
    }

    private void showGuestInfoDialog(final String status) {
//        checkInDialog = new CheckInDialog(getActivity(), selectedRoom, carList, vehicleList, guestTypeList);
        checkInDialog = new CheckInDialog(getActivity(), selectedRoom, carList, vehicleList, guestTypeList, userList, roomAreaList) {
            @Override
            public void successCheckIn(final WelcomeGuestRequest welcomeGuestRequest) {
                BusProvider.getInstance().post(welcomeGuestRequest);

                if (!status.equalsIgnoreCase("19") && !status.equalsIgnoreCase("3")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()),
                                    welcomeGuestRequest.getRoomRatePriceId()));
                        }
                    }, 500);
                }


            }
        };
        checkInDialog.show();


        checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
            }
        });
//        Window window = checkInDialog.getWindow();
//        window.setLayout((Utils.getDeviceWidth(getContext()) / 2), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void showCheckInDialog() {
//        checkInDialog = new CheckInDialog(getActivity(), selectedRoom, carList, vehicleList, guestTypeList);
        checkInDialog = new CheckInDialog(getActivity(), selectedRoom, carList, vehicleList, guestTypeList, userList, roomAreaList) {
            @Override
            public void successCheckIn(WelcomeGuestRequest welcomeGuestRequest) {
                BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId()), welcomeGuestRequest.getRoomRatePriceId()));
            }
        };
        checkInDialog.show();

        checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()));
            }
        });
//        Window window = checkInDialog.getWindow();
//        window.setLayout((Utils.getDeviceWidth(getContext()) / 2), ViewGroup.LayoutParams.WRAP_CONTENT);




//        if (checkInDialog != null && checkInDialog.isShowing()) {
//            checkInDialog.dismiss();
//        }
//
//        final AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
//        al.setMessage("Confirm check in guest?");
//        al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                BusProvider.getInstance().post(new CheckInRequest(String.valueOf(selectedRoom.getRoomId())));
//                dialog.cancel();
//                BusProvider.getInstance().post(new ProductsModel());
//            }
//        });
//
//        al.show();
    }

    @Subscribe
    public void onReceiveWelcomeGuestResponse(WelcomeGuestResponse welcomeGuestResponse) {
//        CartEntity myCart = new CartEntity();
    }

    private void addRateRequest(String productId, String roomRatePriceId,
                                String quantity, String tax,
                                String roomId, String amount) {
        ArrayList<AddRateProductModel> model = new ArrayList<>();
        model.add(new AddRateProductModel(productId, roomRatePriceId, quantity, tax, amount, 0));
        BusProvider.getInstance().post(new AddRoomPriceRequest(model, roomId));
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
    }

    private void postCheckoutPayment(List<PostedPaymentsModel> ppm, String roomId, String  controlNumber) {

//        Log.d("CHECKOUT_DATA", new AddPaymentRequest(ppm, roomId, "0", controlNumber).toString());
        BusProvider.getInstance().post(new AddPaymentRequest(ppm, roomId, "0", controlNumber));
    }

    @Subscribe
    public void printSoaResponse(PrintSoaResponse printSoaResponse) {
        Toast.makeText(getContext(), "SOA PRINTING", Toast.LENGTH_SHORT).show();
        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
            } else {
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
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
        Toast.makeText(getContext(), "FOP RESP", Toast.LENGTH_SHORT).show();
        currentRoomStatus = String.valueOf(fetchOrderPendingViaControlNoResponse.getResult().getIsSoa());
        totalBalance = fetchOrderPendingViaControlNoResponse.getResult().getTotal();
        cartItemList = new ArrayList<>();
        postedPaymentsList = new ArrayList<>();
        orderSlipList = new ArrayList<>();
        Double totalAmount = 0.00;
        if (fetchOrderPendingViaControlNoResponse.getResult() != null) {


            if (fetchOrderPendingViaControlNoResponse.getResult().getPayments().size() > 0) {
                for (FetchOrderPendingViaControlNoResponse.Payment pym : fetchOrderPendingViaControlNoResponse.getResult().getPayments()) {
                    postedPaymentsList.add(new PostedPaymentsModel(
                            String.valueOf(pym.getPaymentTypeId()),
                            String.valueOf(pym.getAmount()),
                            pym.getPaymentDescription(),
                            true,
                            String.valueOf(pym.getCurrencyId()),
                            String.valueOf(pym.getCurrencyValue()),
                            new JSONObject()
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

            total.setText(String.valueOf(totalAmount));
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
        BusProvider.getInstance().post(new CheckOutRequest(roomId, controlNumber, "1"));
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

    private void fetchArOnlineRequest() {
        BusProvider.getInstance().post(new FetchArOnlineRequest());
    }

    @Subscribe
    public void fetchArOnlineResponse(FetchArOnlineResponse fetchArOnlineResponse) {
        if (fetchArOnlineResponse.getResult().size() > 0) {
            Log.d("CCARD", "AR ONLINE");
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
        if (selectedRoom != null) {
            if (selectedRoom.isTakeOut()) {
                fetchOrderPendingViaControlNo(selectedRoom.getControlNo());
            } else {
                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
            }

        }
    }

}
