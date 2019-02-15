package nerdvana.com.pointofsales.postlogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.RoomConstants;
import nerdvana.com.pointofsales.SetupActivity;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.SystemConstants;
import nerdvana.com.pointofsales.TransactionConstants;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.CheckInRequest;
import nerdvana.com.pointofsales.api_requests.FetchCarRequest;
import nerdvana.com.pointofsales.api_requests.FetchGuestTypeRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchVehicleRequest;
import nerdvana.com.pointofsales.api_requests.OffGoingNegoRequest;
import nerdvana.com.pointofsales.api_responses.FetchCarResponse;
import nerdvana.com.pointofsales.api_responses.FetchGuestTypeResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.api_responses.WelcomeGuestResponse;
import nerdvana.com.pointofsales.background.CheckoutItemsAsync;
import nerdvana.com.pointofsales.background.DeleteCartItemAsync;
import nerdvana.com.pointofsales.background.RetrieveCartItemsAsync;
import nerdvana.com.pointofsales.background.SaveTransactionAsync;
import nerdvana.com.pointofsales.custom.SwipeToDeleteCallback;
import nerdvana.com.pointofsales.dialogs.CheckInDialog;
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
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CategoryAdapter;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import okhttp3.ResponseBody;

public class LeftFrameFragment extends Fragment implements AsyncContract, CheckoutItemsContract,
         SaveTransactionContract, RetrieveCartItemContract, View.OnClickListener {
    private View view;

    private double amountToPay = 0;

    private CheckInDialog checkInDialog;

    private List<RoomRateMain> roomRateMainList;

    private TextView total;
    private TextView discount;
    private TextView tax;
    private TextView subTotal;
    private TextView header;
    private TextView noItems;

    private List<ProductsModel> selectedProductsList;

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

    private List<FetchCarResponse.Result> carList;
    private List<FetchVehicleResponse.Result> vehicleList;
    private List<FetchGuestTypeResponse.Result> guestTypeList;
    private RoomTableModel selectedRoom;
    public static LeftFrameFragment newInstance(SelectionContract selectionContract) {
        LeftFrameFragment.selectionContract = selectionContract;
        LeftFrameFragment leftFrameFragment = new LeftFrameFragment();
        return leftFrameFragment;
    }

    private void initializeViews(View view) {
        total = view.findViewById(R.id.totalValue);
        discount = view.findViewById(R.id.discountValue);
        tax = view.findViewById(R.id.taxValue);
        subTotal = view.findViewById(R.id.subTotalValue);

        listCheckoutItems = view.findViewById(R.id.listCheckoutItems);
        listButtons = view.findViewById(R.id.listButtons);

        noItems = view.findViewById(R.id.notItems);
        header = view.findViewById(R.id.header);
        header.setOnClickListener(this);


        rootView = view.findViewById(R.id.rootView);

        selectedProductsList = new ArrayList<>();
        carList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        guestTypeList = new ArrayList<>();
        roomRateMainList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_left_frame, container, false);
        initializeViews(view);
        setProductAdapter();
//        setButtonsAdapter();


        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.userSettings), UserModel.class);
        if (userModel != null) {
            isValid = true;
        }



        defaultView();
        if (!TextUtils.isEmpty(selectedRoomNumber())) {
            //reload data from selected table && set views
            retrieveCartItems();
            setView(selectedRoomNumber());
            computeFromDb();
        }

        fetchCarRequest();
        fetchVehicleRequest();
        fetchGuestTypeRequest();

        return view;
    }

    private void setProductAdapter() {

        checkoutAdapter = new CheckoutAdapter(selectedProductsList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
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
//                selectedProductsList.add(((ProductsModel)item));
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
        this.selectedRoom = selectedRoom.getSelectedRoom();
        this.roomRateMainList = selectedRoom.getSelectedRoom().getPrice();

        retrieveCartItems();

        computeFromDb();

        setView(selectedRoom.getSelectedRoom().getName());

        fetchRoomPending(String.valueOf(selectedRoom.getSelectedRoom().getRoomId()));

    }

    private void retrieveCartItems() {
        if (getTableRecord().size() > 0) {
            new RetrieveCartItemsAsync(
                    getTableRecord().get(0).getTransactionId(),
                    this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            selectedProductsList.clear();
            selectedProductsList = new ArrayList<>();

            checkoutAdapter = new CheckoutAdapter(selectedProductsList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
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
        Log.d("TESTTEST", "ATTACH");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
        Log.d("TESTTEST", "DETACH");
    }

    @Subscribe
    public void productsClicked(ProductsModel productsModel) {
        if (noItems.getVisibility() == View.VISIBLE) noItems.setVisibility(View.GONE);
        productsModel.setSelected(false);
        new CheckoutItemsAsync(this,
                selectedProductsList ,
                productsModel,
                getContext(),
                getTableRecord().size() < 1 ? "" : getTableRecord().get(0).getTransactionId(),
                5,
                selectedRoomNumber())
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void itemAdded(ProductsModel itemAdded) {
        retrieveCartItems();
        listCheckoutItems.scrollToPosition(checkoutAdapter.getItemCount() - 1);
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
    public void itemSelected(ProductsModel itemSelected, int position) {
        itemSelected.setSelected(itemSelected.isSelected() ? false : true);
        checkoutAdapter.notifyItemChanged(position);
    }

    @Override
    public void itemLongClicked(ProductsModel itemSelected, int position, View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_checkout_item, popupMenu.getMenu());
        popupMenu.show();
    }

    @Subscribe
    public void clickedButton(ButtonsModel clickedItem) {
        switch (clickedItem.getId()) {
            case 100: //SAVE TRANSACTION:
//                fetchRoomPending(String.valueOf(selectedRoom.getRoomId()));
                if (selectedRoom != null) {
                    switch (selectedRoom.getStatus()) {
                        case RoomConstants.CLEAN:
                            //pass this price array to dialog selectedRoom.getPrice().get(0).getRatePrice()



                            Toast.makeText(getContext(), "PLEASE CHECK IN GUEST", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

//                saveTransaction();
//                Toast.makeText(getContext(), "SAVE TRANS MADE", Toast.LENGTH_SHORT).show();
                break;
            case 101: //VOID
                final PasswordDialog passwordDialog = new PasswordDialog(getActivity()) {
                    @Override
                    public void passwordSuccess() {
                        ArrayList<Long> selectedIds = new ArrayList<>();
                        for (ProductsModel p : selectedProductsList) {
                            if (p.isSelected()) {
                                selectedIds.add(p.getProductId());
                            }
                        }


                        String tempTransId = getTableRecord().get(0).getTransactionId();

                        for (CartEntity c : getCartRecord(tempTransId)) {
                            if (selectedIds.contains(c.getProductId())) {
                                c.setProductStatus(ProductConstants.VOID);
                                c.save();
                            }
                        }

                        retrieveCartItems();
                        computeFromDb();

                    }

                    @Override
                    public void passwordFailed() {

                    }
                };
                if(!passwordDialog.isShowing()) passwordDialog.show();
                break;
            case 102: //PAYMENT
                if ((userModel.getSystemType().equals(SystemConstants.SYS_ROOM) ||
                        userModel.getSystemType().equals(SystemConstants.SYS_TABLE)) &&
                    TextUtils.isEmpty(selectedRoomNumber())) {
                    //alert need to select table / room
                    Toast.makeText(getContext(), getString(R.string.error_no_space_selected), Toast.LENGTH_SHORT).show();
                } else if(userModel.getSystemType().equals(SystemConstants.SYS_CHECKOUT)) {

                    //show total

                } else {
                    if (getTableRecord().size() > 0) {
                        double balance = 0;
                        for (ProductsModel selectedItem : selectedProductsList) {
                            balance += selectedItem.getPrice();
                        }
                        PaymentDialog paymentDialog = new PaymentDialog(getActivity(), getTableRecord().get(0).getTransactionId(), balance) {
                            @Override
                            public void paymentSuccess() {
                                if (getTableRecord().size() > 0) {
                                    String tempTransId = getTableRecord().get(0).getTransactionId();
                                    for (TransactionEntity t : getTableRecord()) {
                                        if (t.getTransactionStatus() == TransactionConstants.PENDING) {
                                            t.setTransactionStatus(TransactionConstants.FULLY_PAID);
                                            t.save();
                                        }
                                    }
                                    for (CartEntity c : getCartRecord(tempTransId)) {
                                        if (c.getProductStatus() != ProductConstants.PENDING &&
                                                c.getProductStatus() != ProductConstants.VOID &&
                                                c.getProductStatus() != ProductConstants.DISABLED)
                                            c.setProductStatus(ProductConstants.PAID);
                                        c.save();
                                    }
                                }

                                clearCartItems();
                                defaultView();
                                CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
                            }

                            @Override
                            public void paymentFailed() {

                            }
                        };
//                        PaymentDialog paymentDialog = new PaymentDialog(getActivity(), getTableRecord().get(0).getTransactionId(), balance);
                        paymentDialog.show();
//                        Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
//                        paymentIntent.putExtra("transaction_number", getTableRecord().get(0).getTransactionId());
//                        startActivityForResult(paymentIntent, 100);
                    } else {
                        Toast.makeText(getContext(), "No transactions made yet.", Toast.LENGTH_SHORT).show();
                    }


                }

                break;
        }
    }


    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

//                final ProductsModel itemToRestore = selectedProductsList.get(viewHolder.getAdapterPosition());
                new DeleteCartItemAsync(selectedProductsList.get(viewHolder.getAdapterPosition())).execute();

                selectedProductsList.remove(viewHolder.getAdapterPosition());

                checkoutAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                computeFromDb();
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (selectedProductsList.get(viewHolder.getAdapterPosition()).getProductStatus() != ProductConstants.PENDING) {
                    return 0;
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
        selectedProductsList.clear();
        checkoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void cartItemRetrieved(List<ProductsModel> cartItemList) {

        selectedProductsList.clear();
        selectedProductsList = cartItemList.size() > 0 ? cartItemList : new ArrayList<ProductsModel>();

        checkoutAdapter = new CheckoutAdapter(selectedProductsList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listCheckoutItems.setLayoutManager(linearLayoutManager);
        listCheckoutItems.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();

        computeFromDb();


    }

    private void computeFromDb() {
        double temp = 0;
        for (ProductsModel p : selectedProductsList) {

            temp += p.getPrice();

        }

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
                RateDialog rateDialog = new RateDialog(getContext(), roomRateMainList) {
                    @Override
                    public void rateChangeSuccess(double amountSelected) {
                        List<CurrentTransactionEntity> currentTransaction  = CurrentTransactionEntity.listAll(CurrentTransactionEntity.class);
                        for (CurrentTransactionEntity c : currentTransaction) {
                            c.setAmount(amountSelected);
                            c.save();
                        }
                        computeFromDb();
                    }
                };
                if (roomRateMainList.size() > 0) {
                    rateDialog.show();
                } else {
                    Toast.makeText(getContext(), "No room rate list found", Toast.LENGTH_SHORT).show();
                }

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
        Log.d("TEKTEK", "WEEE");
    }

    private void fetchRoomPending(String roomId) {
        BusProvider.getInstance().post(new FetchRoomPendingRequest(roomId));
    }

    @Subscribe
    public void fetchRoomPendingResponse(FetchRoomPendingResponse fetchRoomPendingResponse) {
        if (fetchRoomPendingResponse.getResult() != null) {
            switch (fetchRoomPendingResponse.getResult().getStatus()) {
                case 20: //onnego show check in form
                    checkInDialog = new CheckInDialog(getActivity(), selectedRoom, carList, vehicleList, guestTypeList);
                    checkInDialog.show();

                    checkInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            sendOffGoingNegoRequest(String.valueOf(selectedRoom.getRoomId()), "1");
                        }
                    });
                    Window window = checkInDialog.getWindow();
                    window.setLayout((Utils.getDeviceWidth(getContext()) / 2), ViewGroup.LayoutParams.WRAP_CONTENT);
                    break;
                case 59: //check in guest
                    if (checkInDialog != null && checkInDialog.isShowing()) {
                        checkInDialog.dismiss();
                    }

                    final AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
                    al.setMessage("Confirm check in guest?");
                    al.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BusProvider.getInstance().post(new CheckInRequest("1", String.valueOf(selectedRoom.getRoomId())));
                            dialog.cancel();
                        }
                    });

                    al.show();

                    break;

                case 2: //already checked in, can now order
                    Toast.makeText(getContext(), "Please order", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(getContext(), "Call fetch room pending", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOffGoingNegoRequest(String roomId, String userId ) {
        BusProvider.getInstance().post(new OffGoingNegoRequest(roomId, userId));
    }
}
