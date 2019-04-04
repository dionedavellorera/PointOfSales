package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.OrListAdapter;
import nerdvana.com.pointofsales.adapters.OrderSlipProductsAdapter;
import nerdvana.com.pointofsales.api_requests.PostVoidRequest;
import nerdvana.com.pointofsales.api_requests.ViewReceiptRequest;
import nerdvana.com.pointofsales.api_responses.PostVoidResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class TransactionsDialog extends BaseDialog implements CheckoutItemsContract {
    private Boolean isViewing;

    private RecyclerView listTransaction;
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
    private List<ViewReceiptResponse.Result> resultList;
    private List<CartItemsModel> cartItemList;
    private ViewReceiptResponse.Result selectedOr;
    private Activity act;

    public TransactionsDialog(@NonNull Context context, Boolean isViewing, Activity activity) {
        super(context);
        this.isViewing = isViewing;
        this.act = activity;
    }

    public TransactionsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TransactionsDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_transactions, "TRANSACTIONS");

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
            showAllReceipt();
            reprintOr.setVisibility(View.GONE);
            postVoid.setVisibility(View.VISIBLE);
        }

        reprintOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postVoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(receiptNo)) {
                    Toast.makeText(getContext(), "Please select transaction to void", Toast.LENGTH_SHORT).show();
                } else {
                    PasswordDialog passwordDialog = new PasswordDialog(act) {
                        @Override
                        public void passwordSuccess(String employeeId) {
                            postVoidRequest(receiptNo, employeeId);
                        }

                        @Override
                        public void passwordFailed() {

                        }
                    };
                    if (!passwordDialog.isShowing()) passwordDialog.show();
                }
            }
        });
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

    private void showAllReceipt() {
        ViewReceiptRequest viewReceiptRequest = new ViewReceiptRequest();
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
                                false
                        ));
                    } else {
                        cartItemList.add(roomRateCounter.size(), new CartItemsModel(
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
                                false
                        ));
                    }
                }
            }

            CheckoutAdapter checkoutAdapter = new CheckoutAdapter(this.cartItemList, this);
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

    private void postVoidRequest(String receiptNumber, String empId) {
        PostVoidRequest postVoidRequest = new PostVoidRequest(empId, receiptNumber);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<PostVoidResponse> request = iUsers.voidReceipt(postVoidRequest.getMapValue());
        request.enqueue(new Callback<PostVoidResponse>() {
            @Override
            public void onResponse(Call<PostVoidResponse> call, Response<PostVoidResponse> response) {
                postVoidSuccess();
                dismiss();

            }

            @Override
            public void onFailure(Call<PostVoidResponse> call, Throwable t) {

            }
        });
    }

    public abstract void postVoidSuccess();
}
