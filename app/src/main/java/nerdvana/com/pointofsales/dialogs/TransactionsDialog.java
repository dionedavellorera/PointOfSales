package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.OrListAdapter;
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingViaControlNoRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomRequest;
import nerdvana.com.pointofsales.api_requests.PostVoidRequest;
import nerdvana.com.pointofsales.api_requests.ViewReceiptRequest;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.PostVoidResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class TransactionsDialog extends BaseDialog implements CheckoutItemsContract {
    private Boolean isViewing;
    private String roomId = "";

    private RecyclerView listTransaction;
    List<String> roomNumbers = new ArrayList<>();
    private RecyclerView listTransactionDetails;
    private Button reprintOr;
    private Button postVoid;

    private TextView header;
    private TextView subTotal;
    private TextView total;
    private TextView discount;
    private TextView deposit;


    private OrList orList;
    String receiptNo = "";
    private String controlNumber = "";
    private List<ViewReceiptResponse.Result> resultList;
    private List<CartItemsModel> cartItemList;
    private ViewReceiptResponse.Result selectedOr;
    private Activity act;

    private CheckBox isTo;
    private SearchView receiptNumber;
    private Spinner roomSpinner;
    private Button search;

    private ViewReceiptResponse.Result selectedOrToPrintOrVoid;

    public TransactionsDialog(@NonNull Context context, Boolean isViewing, Activity activity) {
        super(context);
        this.isViewing = isViewing;
        this.act = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_transactions, "TRANSACTIONS");

        search = findViewById(R.id.buttonSearch);
        isTo = findViewById(R.id.checkBoxIsTo);
        receiptNumber = findViewById(R.id.searchView);
        roomSpinner = findViewById(R.id.roomSpinner);

        setRoomSpinner();


        subTotal = findViewById(R.id.subTotalValue);
        total = findViewById(R.id.totalValue);
        discount = findViewById(R.id.discountValue);
        deposit = findViewById(R.id.depositValue);
        header = findViewById(R.id.header);

        listTransaction = findViewById(R.id.listTransactions);
        cartItemList = new ArrayList<>();
        listTransactionDetails = findViewById(R.id.listTransactionDetails);
        resultList = new ArrayList<>();
        orList = new OrList() {
            @Override
            public void clicked(int position) {
                selectedOr = resultList.get(position);

                setOrDetails(selectedOr);
                receiptNo = resultList.get(position).getReceiptNo();
                controlNumber = resultList.get(position).getControlNo();
                if (selectedOr.getGuestInfo() != null) {
                    header.setText("RECEIPT# " +receiptNo + " - ROOM " + selectedOr.getGuestInfo().getRoomNo());
                } else {
                    header.setText("RECEIPT# " +receiptNo + " - TAKE OUT" );
                }

            }
        };

        reprintOr = findViewById(R.id.reprintOr);
        postVoid = findViewById(R.id.postVoid);

        if (isViewing) {
            reprintOr.setVisibility(View.VISIBLE);
            postVoid.setVisibility(View.GONE);

        } else {
//            showAllReceipt();
            reprintOr.setVisibility(View.GONE);
            postVoid.setVisibility(View.VISIBLE);
        }

        reprintOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchOrderPendingViaControlNoRequest fetchOrderPendingViaControlNoRequest = new FetchOrderPendingViaControlNoRequest(selectedOr.getControlNo());
                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<FetchOrderPendingViaControlNoResponse> request = iUsers.fetchOrderPendingViaControlNo(fetchOrderPendingViaControlNoRequest.getMapValue());
                request.enqueue(new Callback<FetchOrderPendingViaControlNoResponse>() {
                    @Override
                    public void onResponse(Call<FetchOrderPendingViaControlNoResponse> call, Response<FetchOrderPendingViaControlNoResponse> response) {
                        String roomNo = "";
                        String roomType = "";
                        if (selectedOr.getGuestInfo() != null) {
                            roomNo = selectedOr.getGuestInfo().getRoomNo();
                            roomType = selectedOr.getGuestInfo().getRoomType();
                        } else {
                            roomNo = "TAKEOUT";
                            roomType = "NA";
                        }


                        BusProvider.getInstance().post(new PrintModel(
                                "", roomNo,
                                "PRINT_RECEIPT",
                                GsonHelper.getGson().toJson(response.body().getResult()),
                                roomType));

                    }

                    @Override
                    public void onFailure(Call<FetchOrderPendingViaControlNoResponse> call, Throwable t) {

                    }
                });
            }
        });

        postVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(controlNumber)) {
                    Toast.makeText(getContext(), "Please select transaction to void", Toast.LENGTH_SHORT).show();
                } else {
                    PasswordDialog passwordDialog = new PasswordDialog(act, "65") {
                        @Override
                        public void passwordSuccess(String employeeId, String employeeName) {
                            postVoidRequest(controlNumber, employeeId);
                        }

                        @Override
                        public void passwordFailed() {

                        }
                    };
                    if (!passwordDialog.isShowing()) passwordDialog.show();
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTo.isChecked()) {
                    showAllReceipt("", receiptNumber.getQuery().toString());
                } else {
                    showAllReceipt(roomId, receiptNumber.getQuery().toString());
                }
            }
        });

        if (Utils.getSystemType(getContext()).equalsIgnoreCase("franchise")) {
            isTo.setChecked(true);
            isTo.setEnabled(false);
            roomSpinner.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    private void showAllReceipt(String roomId, String receiptNumber) {
        ViewReceiptRequest viewReceiptRequest = new ViewReceiptRequest(roomId, receiptNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ViewReceiptResponse> request = iUsers.viewReceipt(viewReceiptRequest.getMapValue());
        request.enqueue(new Callback<ViewReceiptResponse>() {
            @Override
            public void onResponse(Call<ViewReceiptResponse> call, Response<ViewReceiptResponse> response) {
                resultList = response.body().getResult();
                OrListAdapter ospa = new OrListAdapter(resultList, orList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                listTransaction.setLayoutManager(linearLayoutManager);
                listTransaction.setAdapter(ospa);
                ospa.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ViewReceiptResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void itemAdded(ProductsModel itemAdded) {

    }

    @Override
    public void itemRemoved(ProductsModel item) {

    }

    @Override
    public void itemSelected(CartItemsModel itemSelected, int position) {

    }

    @Override
    public void itemLongClicked(CartItemsModel itemSelected, int position, View view) {

    }

    public interface OrList {
        void clicked(int position);
    }

    private void setOrDetails(ViewReceiptResponse.Result selectedOr) {
        if (selectedOr != null) {

            postVoidPrint(GsonHelper.getGson().toJson(selectedOrToPrintOrVoid));


            selectedOrToPrintOrVoid = selectedOr;
            cartItemList = new ArrayList<>();
            List<Integer> roomRateCounter = new ArrayList<>();
            for (ViewReceiptResponse.Post_ tpost : selectedOr.getPost()) {
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
                                tpost.getRoomRateId() == null ? tpost.getProduct().getProductInitial().toUpperCase() : tpost.getRoomRate().toString().toUpperCase(),
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
                                "",
                                new ArrayList<AddRateProductModel.AlaCarte>(),
                                new ArrayList<AddRateProductModel.Group>(),
                                false,
                                null
                        ));
                    } else {
                        cartItemList.add(roomRateCounter.size(), new CartItemsModel(
                                tpost.getControlNo(),
                                tpost.getRoomId() != null ? tpost.getRoomId() : 0,
                                tpost.getProductId(),
                                tpost.getRoomTypeId() != null ? tpost.getRoomTypeId() : 0,
                                tpost.getRoomRateId() == null ? 0 : Integer.parseInt(String.format("%.0f", Double.valueOf(tpost.getRoomRateId().toString()))) ,
                                tpost.getRoomRatePriceId() != null ? tpost.getRoomRatePriceId() : 0,
                                tpost.getRoomRateId() == null ? tpost.getProduct().getProductInitial().toUpperCase() : tpost.getRoomRate().toString().toUpperCase(),
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
                                "",
                                new ArrayList<AddRateProductModel.AlaCarte>(),
                                new ArrayList<AddRateProductModel.Group>(),
                                false,
                                null
                        ));
                    }
                }
            }

            CheckoutAdapter checkoutAdapter = new CheckoutAdapter(this.cartItemList, this, getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            listTransactionDetails.setLayoutManager(linearLayoutManager);
            listTransactionDetails.setAdapter(checkoutAdapter);
            checkoutAdapter.notifyDataSetChanged();

            Double totalBalance = selectedOr.getTotal() +
                    selectedOr.getOtAmount() +
                    selectedOr.getXPersonAmount();

            Double advancePayment = selectedOr.getAdvance();
            Double discountPayment = selectedOr.getDiscount();
            subTotal.setText(Utils.returnWithTwoDecimal(String.valueOf(totalBalance)));
            total.setText(Utils.returnWithTwoDecimal(String.valueOf(totalBalance - (advancePayment + discountPayment))));
            discount.setText(Utils.returnWithTwoDecimal(String.valueOf(selectedOr.getDiscount())));
            deposit.setText(Utils.returnWithTwoDecimal(String.valueOf(selectedOr.getAdvance())));
        }
    }

    private void postVoidRequest(String controlNumber, String empId) {
        PostVoidRequest postVoidRequest = new PostVoidRequest(empId, controlNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<PostVoidResponse> request = iUsers.voidReceipt(postVoidRequest.getMapValue());
        request.enqueue(new Callback<PostVoidResponse>() {
            @Override
            public void onResponse(Call<PostVoidResponse> call, Response<PostVoidResponse> response) {

                if (response.body().getStatus() == 0) {
                    Utils.showDialogMessage(act, response.body().getMessage(), "Information");
                } else {

                    dismiss();
                    postVoidPrint(GsonHelper.getGson().toJson(selectedOrToPrintOrVoid));

                    postVoidSuccess();
                    dismiss();
                }



            }

            @Override
            public void onFailure(Call<PostVoidResponse> call, Throwable t) {

            }
        });
    }

    public abstract void postVoidSuccess();

    private void setRoomSpinner() {
        FetchRoomRequest fetchRoomRequest = new FetchRoomRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        roomNumbers = new ArrayList<>();
        Call<FetchRoomResponse> roomlistRequest = iUsers.sendRoomListRequest(
                fetchRoomRequest.getMapValue());
        roomlistRequest.enqueue(new Callback<FetchRoomResponse>() {
            @Override
            public void onResponse(Call<FetchRoomResponse> call, final Response<FetchRoomResponse> response) {
                for (FetchRoomResponse.Result frr : response.body().getResult()) {
                    roomNumbers.add(frr.getRoomNo());
                }

                CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                        roomNumbers);
                roomSpinner.setAdapter(rateSpinnerAdapter);

                roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        roomId = String.valueOf(response.body().getResult().get(position).getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onFailure(Call<FetchRoomResponse> call, Throwable t) {

            }
        });

    }

    public abstract void postVoidPrint(String jsonData);

}
