package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
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
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.PaymentsAdapter;
import nerdvana.com.pointofsales.adapters.PostedPaymentsAdapter;
import nerdvana.com.pointofsales.api_requests.CheckGcRequest;
import nerdvana.com.pointofsales.api_requests.PrintSoaRequest;
import nerdvana.com.pointofsales.api_responses.CheckGcResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.entities.PaymentEntity;
import nerdvana.com.pointofsales.model.AvailableGcModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.postlogin.adapter.CheckoutAdapter;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public abstract class PaymentDialog extends BaseDialog  {


    private LinearLayout formCash;
    private LinearLayout formCard;
    private LinearLayout formOnline;
    private LinearLayout formVoucher;
    private LinearLayout formForex;

    private Spinner spinnerForex;
    private TextView forexRate;

    private AvailableGcAdapter availableGcAdapter;
    private PaymentsAdapter paymentsAdapter;
    private List<FetchPaymentResponse.Result> paymentList;
    private List<FetchCreditCardResponse.Result> creditCardList;
    private List<FetchArOnlineResponse.Result> arOnlineList;
    private RecyclerView listPayments;
    private RecyclerView listPostedPayments;
    private Button add;
    private Button pay;
    private EditText amountToPay;
    private TextView displayTotalChange;
    private FetchPaymentResponse.Result paymentMethod = null;
    private PaymentMethod paymentMethodImpl;
    List<PostedPaymentsModel> postedPaymentList = new ArrayList<>();
    PostedPaymentsAdapter postedPaymentsAdapter;
    private boolean isCheckout;
    private Double totalBalance;
    private TextView displayTotalBalance;
    private TextView displayTotalPayment;
    private Spinner spinnerRoomBoy;

    //forex fields
    private String currencyId;
    private String currencyValue;
    private EditText forexAmount;

    //online
    private EditText voucherCode;
    private EditText voucherAmount;
    private Spinner spinnerOnline;
    private String onlineId;


    //gift check
    private EditText voucherNumber;
    private EditText voucherQuantity;
    private Button checkGc;
    private List<AvailableGcModel> gcList;
    private RecyclerView listAvailedGcs;

    //credit card
    private Spinner spinnerCreditCard;
    private EditText cardNumber;
    private EditText cardExpiration;
    private EditText authorization;
    private EditText remarks;
    private EditText cardHoldersName;
    private EditText creditCardAmount;
    private String cardTypeId;
    private Context act;

    private LinearLayout linRoomBoy;

    private String empId = ""; //room boy selected

    private List<FetchCurrencyExceptDefaultResponse.Result> currencyList;
    public PaymentDialog(@NonNull Context context, List<FetchPaymentResponse.Result> paymentList,
                         boolean isCheckout,
                         List<PostedPaymentsModel> postedPaymentList,
                         Double totalBalance,
                         List<FetchCurrencyExceptDefaultResponse.Result> currencyList,
                         List<FetchCreditCardResponse.Result> creditCardList,
                         List<FetchArOnlineResponse.Result> arOnlineList) {
        super(context);
        this.act = context;
        this.paymentList = paymentList;
        paymentMethod = paymentList.get(0);
        this.isCheckout = isCheckout;
        this.postedPaymentList = postedPaymentList;
        this.totalBalance = totalBalance;
        this.currencyList = currencyList;
        this.creditCardList = creditCardList;
        this.arOnlineList = arOnlineList;
//        this.transactionNumber = transactionNumber;
//        this.balance = balance;
    }

    public PaymentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PaymentDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_payment);
        setDialogLayout(R.layout.dialog_payment, "PAYMENTS");
        gcList = new ArrayList<>();
        linRoomBoy = findViewById(R.id.linRoomBoy);
        spinnerForex = findViewById(R.id.spinnerForex);
        formCash = findViewById(R.id.formCash);
        formCard = findViewById(R.id.formCard);
        formOnline = findViewById(R.id.formOnline);
        formVoucher = findViewById(R.id.formGiftCheck);
        formForex = findViewById(R.id.formForex);
        forexAmount = findViewById(R.id.forexAmount);
        spinnerRoomBoy = findViewById(R.id.spinnerRoomBoy);
        forexRate = findViewById(R.id.forexRate);
        displayTotalBalance = findViewById(R.id.totalBalance);
        displayTotalPayment = findViewById(R.id.totalPayment);
        listPayments = findViewById(R.id.listPayments);
        listPostedPayments = findViewById(R.id.listPostedPayments);
        add = findViewById(R.id.add);
        pay = findViewById(R.id.pay);
        displayTotalChange = findViewById(R.id.totalChange);
        amountToPay = (EditText) findViewById(R.id.amount);

        voucherCode = (EditText) findViewById(R.id.voucherCode);
        voucherAmount = (EditText) findViewById(R.id.voucherAmount);
        spinnerOnline = (Spinner) findViewById(R.id.spinnerOnline);

        listAvailedGcs = (RecyclerView) findViewById(R.id.listAvailedGcs);
        voucherNumber = (EditText) findViewById(R.id.voucherNumber);
        voucherQuantity = (EditText) findViewById(R.id.voucherQuantity);
        checkGc = (Button) findViewById(R.id.checkGc);
        checkGc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGc(voucherNumber.getText().toString(), voucherQuantity.getText().toString());
            }
        });


        spinnerCreditCard = (Spinner) findViewById(R.id.spinnerCreditCard);
        cardNumber = (EditText) findViewById(R.id.cardNumber);
        cardHoldersName = (EditText) findViewById(R.id.cardHoldersName);
        creditCardAmount = (EditText) findViewById(R.id.creditCardAmount);
        cardExpiration = (EditText) findViewById(R.id.expiration);
        authorization = (EditText) findViewById(R.id.authorization);
        remarks = (EditText) findViewById(R.id.remarks);

        setForexSpinner();
        setVoucherSpinner();
        setupCreditCardSpinner();

        if (isCheckout) {
            linRoomBoy.setVisibility(View.VISIBLE);
        } else {
            linRoomBoy.setVisibility(GONE);
        }
        setRoomBoySpinner();

        displayTotalBalance.setText(String.valueOf(totalBalance));
        paymentMethodImpl = new PaymentMethod() {
            @Override
            public void clicked(int position) {
                showForm(paymentList.get(position).getCoreId());

                paymentMethod = paymentList.get(position);
            }
        };



        if (isCheckout) {


            pay.setText("CHECKOUT");


        } else {
            pay.setText("PAY");
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentMethod == null) {
                    Toast.makeText(getContext(), "Please select payment method", Toast.LENGTH_SHORT).show();
                } else {
                    if (paymentMethod.getCoreId().equalsIgnoreCase("1")) { //cash
                        if (!TextUtils.isEmpty(amountToPay.getText().toString())) {
                            if (Double.valueOf(amountToPay.getText().toString()) > 0) {
                                postedPaymentList.add(new PostedPaymentsModel(
                                        paymentMethod.getCoreId(),
                                        amountToPay.getText().toString(),
                                        paymentMethod.getPaymentType(),
                                        false,
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                                        new JSONObject(),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT)));
                            } else {
                                Utils.showDialogMessage(act, "Please enter valid amount for cash payment", "Information");
                            }
                        } else {
                            Utils.showDialogMessage(act, "Please enter valid amount for cash payment", "Information");
                        }

                    } else if (paymentMethod.getCoreId().equalsIgnoreCase("2")) { //card
                        boolean isValid = true;
                        String errorMessage = "";
                        if (TextUtils.isEmpty(cardTypeId)) {
                            isValid = false;
                            errorMessage += "Empty card type \n";
                        }

                        if (TextUtils.isEmpty(cardNumber.getText().toString().trim())) {
                            isValid = false;
                            errorMessage += "Empty card number \n";
                        }

                        if (TextUtils.isEmpty(cardHoldersName.getText().toString().trim())) {
                            isValid = false;
                            errorMessage += "Empty card holders name \n";
                        }

                        if (TextUtils.isEmpty(cardExpiration.getText().toString().trim())) {
                            isValid = false;
                            errorMessage += "Empty expiration date \n";
                        }

                        if (TextUtils.isEmpty(authorization.getText().toString().trim())) {
                            isValid = false;
                            errorMessage += "Empty authorization \n";
                        }

                        if (TextUtils.isEmpty(creditCardAmount.getText().toString().trim())) {
                            isValid = false;
                            errorMessage += "Invalid amount \n";
                        } else {
                            if (Double.valueOf(creditCardAmount.getText().toString()) < 1) {
                                isValid = false;
                                errorMessage += "Invalid amount \n";
                            }
                        }



                        if (paymentMethod == null) {
                            isValid = false;
                            errorMessage += "Invalid payment method \n";
                        }

                        if (isValid) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("card_type_id", cardTypeId);
                                jsonObject.put("card_number", cardNumber.getText().toString());
                                jsonObject.put("account_name", cardHoldersName.getText().toString());
                                jsonObject.put("card_expiration", cardExpiration.getText().toString());
                                jsonObject.put("authorization", authorization.getText().toString());
                                jsonObject.put("remarks", remarks.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            postedPaymentList.add(new PostedPaymentsModel(
                                    paymentMethod.getCoreId(),
                                    creditCardAmount.getText().toString(),
                                    paymentMethod.getPaymentType(),
                                    false,
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                                    jsonObject,SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT)));


                        } else {
                            Utils.showDialogMessage(act, errorMessage, "Information");
                        }


                    } else if (paymentMethod.getCoreId().equalsIgnoreCase("3")) { //online

                        boolean isValid = true;
                        String errorMessage = "";

                        if (paymentMethod == null) {
                            isValid = false;
                            errorMessage += "Invalid payment method \n";
                        }

                        if (TextUtils.isEmpty(onlineId.trim())) {
                            isValid = false;
                            errorMessage += "Please select ar type \n" ;
                        }

                        if (TextUtils.isEmpty(voucherCode.getText().toString().trim())) {
                            isValid = false;
                            errorMessage += "Empty voucher code \n";
                        }

                        if (isValid) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("online_payment_id", onlineId);
                                jsonObject.put("voucher_code", voucherCode.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            postedPaymentList.add(new PostedPaymentsModel(
                                    paymentMethod.getCoreId(),
                                    voucherAmount.getText().toString(),
                                    paymentMethod.getPaymentType(),
                                    false,
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                                    jsonObject,
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT)));
                        } else {
                            Utils.showDialogMessage(act, errorMessage, "Information");
                        }

                    } else if (paymentMethod.getCoreId().equalsIgnoreCase("5")) { //voucher
                        if (gcList.size() > 0) {
                            Double amount = 0.00;
                            String gcCode = "";
                            int index = 0;
                            for (AvailableGcModel availableGcModel : gcList) {
                                amount += Double.valueOf(availableGcModel.getAmount());
                                if (index == gcList.size() - 1) {
                                    gcCode += availableGcModel.getId();
                                } else {
                                    gcCode += availableGcModel.getId() + ",";
                                }
                                index++;
                            }

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("gc_code", gcCode);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            postedPaymentList.add(new PostedPaymentsModel(
                                    paymentMethod.getCoreId(),
                                    String.valueOf(amount),
                                    paymentMethod.getPaymentType(),
                                    false,
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                                    jsonObject,
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT)));
                        } else {
                            Utils.showDialogMessage(act, "No gc added", "Information");
                        }
                    } else if (paymentMethod.getCoreId().equalsIgnoreCase("6")) { //forex

                        boolean isValid = true;
                        String errorMessage = "";

                        if (paymentMethod == null) {
                            isValid = false;
                            errorMessage += "Invalid payment method";
                        }

                        if (TextUtils.isEmpty(currencyId.toString().trim()) || TextUtils.isEmpty(currencyId.toString().trim())) {
                            isValid = false;
                            errorMessage += "invalid currency";
                        }

                        if (!TextUtils.isEmpty(forexAmount.getText().toString().trim())) {
                            if (Double.valueOf(forexAmount.getText().toString()) < 1) {
                                isValid = false;
                                errorMessage = "Invalid amount \n";
                            }
                        } else {
                            isValid = false;
                            errorMessage = "Invalid amount \n";
                        }


                        if (isValid) {
                            postedPaymentList.add(new PostedPaymentsModel(
                                    paymentMethod.getCoreId(),
                                    forexAmount.getText().toString(),
                                    paymentMethod.getPaymentType(),
                                    false,
                                    currencyId,
                                    currencyValue,
                                    new JSONObject(),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT)));
                        } else {
                            Utils.showDialogMessage(act, errorMessage, "Information");
                        }


                    }

                    if (postedPaymentsAdapter != null) {
                        postedPaymentsAdapter.notifyDataSetChanged();
                    }
//                    paymentMethod = null;
                }

                computeTotal();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postedPaymentList.size() > 0) {
                    paymentSuccess(postedPaymentList, empId);
                } else {
                    Utils.showDialogMessage(act, "No payment to post", "Warning");
                }

            }
        });

        computeTotal();

        paymentsAdapter = new PaymentsAdapter(paymentList, paymentMethodImpl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listPayments.setLayoutManager(new GridLayoutManager(getContext(), LinearLayoutManager.VERTICAL));
        listPayments.setAdapter(paymentsAdapter);
        paymentsAdapter.notifyDataSetChanged();

        postedPaymentsAdapter = new PostedPaymentsAdapter(postedPaymentList);
        listPostedPayments.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        listPostedPayments.setAdapter(postedPaymentsAdapter);

        AvailableGcDialog.Item item = new AvailableGcDialog.Item() {
            @Override
            public void remove(int position) {
                gcList.remove(position);
                availableGcAdapter.notifyDataSetChanged();
            }
        };

        availableGcAdapter = new AvailableGcAdapter(gcList, item);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listAvailedGcs.setLayoutManager(llm);
        listAvailedGcs.setAdapter(availableGcAdapter);
        availableGcAdapter.notifyDataSetChanged();
        showForm("1");
    }

    public abstract void paymentSuccess(List<PostedPaymentsModel> postedPaymentList, String roomBoy);
    public abstract void paymentFailed();

    public interface PaymentMethod {
        void clicked(int position);
    }

    private void computeTotal() {
        Double totalPayment = 0.00;
        Double totalChange = 0.00;
        for (PostedPaymentsModel ppm : postedPaymentList) {
            totalPayment += Double.valueOf(ppm.getAmount());
        }

        totalChange = totalPayment - totalBalance;
        if (Double.valueOf(totalChange) < 1) {
            displayTotalChange.setText("0");
        } else {
            displayTotalChange.setText(String.valueOf(totalChange));
        }

        displayTotalPayment.setText(String.valueOf(totalPayment));

        if (totalPayment >= totalBalance) {
            pay.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
        Dialog dialog = this;
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void showForm(String coreId) {
        if (coreId.equalsIgnoreCase("1")) { //cash
            formCash.setVisibility(View.VISIBLE);
            formCard.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formForex.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("2")) { //card
            formCash.setVisibility(GONE);
            formCard.setVisibility(View.VISIBLE);
            formVoucher.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formForex.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("3")) { //online
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formOnline.setVisibility(View.VISIBLE);
            formForex.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("5")) { //voucher
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formVoucher.setVisibility(View.VISIBLE);
            formOnline.setVisibility(GONE);
            formForex.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("6")) { //forex
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formForex.setVisibility(View.VISIBLE);
        }
    }

    private void setForexSpinner() {
        List<String> forexArray = new ArrayList<>();
        if (currencyList != null) {
            for (FetchCurrencyExceptDefaultResponse.Result curr : currencyList) {
                forexArray.add(curr.getCurrency());
            }
        }

        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                forexArray);
        spinnerForex.setAdapter(rateSpinnerAdapter);

        spinnerForex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyId = currencyList.get(position).getCountryCode();
                currencyValue = String.valueOf(currencyList.get(position).getValue());

                forexRate.setText(String.valueOf(1 / currencyList.get(position).getValue()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setVoucherSpinner() {
        List<String> onlineArray = new ArrayList<>();
        if (arOnlineList != null) {
            for (FetchArOnlineResponse.Result ar : arOnlineList) {
                onlineArray.add(ar.getArOnline());
            }
        }

        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                onlineArray);
        spinnerOnline.setAdapter(rateSpinnerAdapter);

        spinnerOnline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //fix this when api call ready
                onlineId = String.valueOf(arOnlineList.get(position).getCoreId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupCreditCardSpinner() {
        List<String> ccArray = new ArrayList<>();
        if (creditCardList != null) {
            for (FetchCreditCardResponse.Result cc : creditCardList) {
                ccArray.add(cc.getCreditCard());
            }
        }

        CustomSpinnerAdapter cardSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                ccArray);
        spinnerCreditCard.setAdapter(cardSpinnerAdapter);

        spinnerCreditCard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //fix this when api call ready
                cardTypeId = String.valueOf(creditCardList.get(position).getCoreId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    private void checkGc(String voucherNumber, String qty) {
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<CheckGcResponse> request = iUsers.checkGc(new CheckGcRequest(voucherNumber, qty).getMapValue());
        request.enqueue(new Callback<CheckGcResponse>() {
            @Override
            public void onResponse(Call<CheckGcResponse> call, Response<CheckGcResponse> response) {
                AvailableGcDialog availableGcDialog = new AvailableGcDialog(act, response.body().getResult().getApprove()) {
                    @Override
                    public void proceed(List<AvailableGcModel> list) {
                        for (AvailableGcModel availList : list) {
                            boolean isValid = true;
                            for (AvailableGcModel myList : gcList) {
                                if (myList.getGcId().equalsIgnoreCase(availList.getGcId())) {
                                    isValid = false;
                                }
                            }
                            if (isValid) {
                                gcList.add(availList);
                            }
                        }
                        availableGcAdapter.notifyDataSetChanged();


                    }
                };
                availableGcDialog.show();
            }

            @Override
            public void onFailure(Call<CheckGcResponse> call, Throwable t) {
//                Log.d("RESPORES", "FALSE");
            }
        });

    }


    private void setRoomBoySpinner() {

        TypeToken<List<FetchCompanyUserResponse.Result>> companyUser = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        final List<FetchCompanyUserResponse.Result> userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.COMPANY_USER), companyUser.getType());

        List<String> userArray = new ArrayList<>();
        for (FetchCompanyUserResponse.Result res : userList) {
            userArray.add(res.getName());
        }

        CustomSpinnerAdapter userAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                userArray);
        spinnerRoomBoy.setAdapter(userAdapter);

        spinnerRoomBoy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                empId = String.valueOf(userList.get(position).getUserId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
