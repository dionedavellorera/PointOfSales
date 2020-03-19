package nerdvana.com.pointofsales.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.reflect.TypeToken;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.CreditCardAdapter;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.PaymentsAdapter;
import nerdvana.com.pointofsales.adapters.PostedPaymentsAdapter;
import nerdvana.com.pointofsales.api_requests.CheckGcRequest;
import nerdvana.com.pointofsales.api_requests.SaveGuestInfoRequest;
import nerdvana.com.pointofsales.api_requests.TakasListRequest;
import nerdvana.com.pointofsales.api_requests.VoidPaymentRequest;
import nerdvana.com.pointofsales.api_responses.CheckGcResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.SaveGuestInfoResponse;
import nerdvana.com.pointofsales.api_responses.TakasListResponse;
import nerdvana.com.pointofsales.api_responses.VoidPaymentResponse;
import nerdvana.com.pointofsales.custom.SwipeToDeleteCallback;
import nerdvana.com.pointofsales.interfaces.CreditCardContract;
import nerdvana.com.pointofsales.interfaces.VoidItemContract;
import nerdvana.com.pointofsales.model.AvailableGcModel;
import nerdvana.com.pointofsales.model.CreditCardListModel;
import nerdvana.com.pointofsales.model.GuestReceiptInfoModel;
import nerdvana.com.pointofsales.model.PaymentTypeModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class PaymentDialog extends BaseDialog  {

    Double advancePayment = 0.00;
    Double normalPayment = 0.00;
    Double totalPayment = 0.00;
    Double amountDue = 0.00;
    Double totalChange = 0.00;

    private boolean isEmployee = false;

    private TextView totalDeposit;
    private TextView totalAmountDue;

    private LinearLayout formGuestInfo;
    private TextView guestName;
    private TextView guestAddress;
    private TextView guestTin;
    private RelativeLayout relGuestInfo;

    private EditText guestNameInput;
    private EditText guestBusinessStyle;
    private EditText guestAddressInput;
    private EditText guestTinInput;

    private EditText takasRemarks;
    private EditText takasAmount;

    String guestInfoEmployeeId;
    String guestInfoEmployeeName;

    private LinearLayout formCash;
    private LinearLayout formCard;
    private LinearLayout formCard2;
    private LinearLayout formOnline;
    private LinearLayout formOnline2;
    private LinearLayout formVoucher;
    private LinearLayout formVoucher2;
    private LinearLayout formForex;
    private LinearLayout formTakas;
    private LinearLayout formTakas2;

    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;

    private Spinner spinnerForex;
    private TextView forexRate;

    private TextInputLayout tilGuestName;
    private TextInputLayout tilGuestAddress;
    private TextInputLayout tilTin;

    private CreditCardAdapter creditCardAdapter;
    private AvailableGcAdapter availableGcAdapter;
    private PaymentsAdapter paymentsAdapter;
    private List<FetchPaymentResponse.Result> paymentList;
    private List<FetchCreditCardResponse.Result> creditCardList;
    private List<FetchArOnlineResponse.Result> arOnlineList;
    private RecyclerView listPayments;
    private RecyclerView listPostedPayments;
    private Button add;

    private Button addCash;
    private Button addCard;
    private Button addOnline;
    private Button addGc;
    private Button addForex;
    private Button addGuest;
    private Button addTakas;

    private Button pay;
    private EditText amountToPay;
    private TextView displayTotalChange;
    private PaymentTypeModel paymentMethod = null;
    private PaymentMethod paymentMethodImpl;
    List<PostedPaymentsModel> postedPaymentList = new ArrayList<>();
    PostedPaymentsAdapter postedPaymentsAdapter;
    private boolean isCheckout;
    private Double totalBalance;
    private TextView displayTotalBalance;
    private TextView displayTotalPayment;
    private SearchableSpinner spinnerRoomBoy;

    private CheckBox checkEmployee;
    private Spinner spinnerEmplyeeSelection;
    //forex fields
    private String currencyId;
    private String currencyValue;
    private EditText forexAmount;

    //online
    private EditText voucherCode;
    private EditText voucherAmount;
    private SearchableSpinner spinnerOnline;
    private SearchableSpinner spinnerOnlineTakas;
    private String onlineId;
    private String takasId;

    FetchPaymentResponse.Result f;

    private RelativeLayout rootView;

    //gift check
    private EditText voucherNumber;
    private EditText voucherQuantity;
    private Button checkGc;
    private List<AvailableGcModel> gcList;
    private RecyclerView listAvailedGcs;

    //credit card
    private RecyclerView rvCreditCard;
    private EditText cardNumber;
    private EditText cardExpiration;
    private EditText authorization;
    private EditText remarks;
    private EditText cardHoldersName;
    private EditText creditCardAmount;
    private String cardTypeId;
    private Activity act;
    private List<PaymentTypeModel> paymentTypeList = new ArrayList<>();

    private LinearLayout linRoomBoy;

    private String empId = ""; //room boy selected


    private Double discountPayment = 0.00;

    private VoidItemContract voidItemContract;
    private String controlNumber = "";
    private GuestReceiptInfoModel guestReceiptInfoModel;
    private List<FetchCurrencyExceptDefaultResponse.Result> currencyList;
    private boolean isTakeOut;
    private boolean isDeposit;
    public PaymentDialog(@NonNull Activity context,
                         List<FetchPaymentResponse.Result> paymentList,
                         boolean isCheckout,
                         final List<PostedPaymentsModel> postedPaymentList,
                         Double totalBalance,
                         List<FetchCurrencyExceptDefaultResponse.Result> currencyList,
                         List<FetchCreditCardResponse.Result> creditCardList,
                         List<FetchArOnlineResponse.Result> arOnlineList,
                         Double discountPayment,
                         final String controlNumber,
                         GuestReceiptInfoModel guestReceiptInfoModel,
                         boolean isTakeOut,
                         boolean isDeposit) {
        super(context);
        this.isDeposit = isDeposit;
        this.isTakeOut = isTakeOut;
        this.act = context;
        this.guestReceiptInfoModel = guestReceiptInfoModel;
        this.controlNumber = controlNumber;
        this.paymentList = new ArrayList<>(paymentList);

        for (FetchPaymentResponse.Result res : paymentList) {
            if (res.getCoreId().equalsIgnoreCase("1")) {
                paymentTypeList.add(new PaymentTypeModel(res.getCoreId(), res.getPaymentType(),
                        true, res.getImage(),
                        false));
            }  else if (res.getCoreId().equalsIgnoreCase("999")) {
                paymentTypeList.add(new PaymentTypeModel(res.getCoreId(), res.getPaymentType(),
                        false, res.getImage(),
                        false));
            }  else if (res.getCoreId().equalsIgnoreCase("6")) {
                paymentTypeList.add(new PaymentTypeModel(res.getCoreId(), res.getPaymentType(),
                        false, res.getImage(),
                        false));
            }  else {
                paymentTypeList.add(new PaymentTypeModel(res.getCoreId(), res.getPaymentType(),
                        false, res.getImage(),
                        true));
            }

        }

        if (paymentTypeList.size() > 0) {
            paymentMethod = paymentTypeList.get(0);
        }

        this.isCheckout = isCheckout;
        this.postedPaymentList = postedPaymentList;
        this.totalBalance = totalBalance;
        this.currencyList = currencyList;
        this.creditCardList = creditCardList;


        this.arOnlineList = arOnlineList;
        this.discountPayment = discountPayment;
        this.voidItemContract = new VoidItemContract() {
            @Override
            public void remove(final String post_id, String name, String amount, final int position) {

                PasswordDialog passwordDialog = new PasswordDialog(act,"Confirm Void Item", "") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {

                        VoidPaymentRequest voidPaymentRequest = new VoidPaymentRequest(controlNumber, post_id, employeeId);
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<VoidPaymentResponse> request = iUsers.voidPayment(voidPaymentRequest.getMapValue());
                        request.enqueue(new Callback<VoidPaymentResponse>() {
                            @Override
                            public void onResponse(Call<VoidPaymentResponse> call, Response<VoidPaymentResponse> response) {
                                postedPaymentList.remove(position);
                                computeTotal();

                                removePaymentSuccess();
                            }

                            @Override
                            public void onFailure(Call<VoidPaymentResponse> call, Throwable t) {

                            }
                        });





                    }

                    @Override
                    public void passwordFailed() {

                    }
                };

                if (!passwordDialog.isShowing()) {
                    passwordDialog.show();
                }


            }
        };
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
        setDialogLayout(R.layout.dialog_payment, "PAYMENTS");
        tilGuestName = findViewById(R.id.tilGuestName);
        tilGuestAddress = findViewById(R.id.tilGuestAddress);
        tilTin = findViewById(R.id.tilTin);

        rl1 = findViewById(R.id.rl1);

        takasRemarks = findViewById(R.id.takasRemarks);
        takasAmount = findViewById(R.id.takasAmount);

        rl2 = findViewById(R.id.rl2);
        rl3 = findViewById(R.id.rl3);
        rootView = findViewById(R.id.rootView);
        rootView.requestFocus();
        relGuestInfo = findViewById(R.id.relGuestInfo);
        guestName = findViewById(R.id.guestName);
        guestAddress = findViewById(R.id.guestAddress);
        guestTin = findViewById(R.id.guestTin);
        gcList = new ArrayList<>();
        totalDeposit = findViewById(R.id.totalDeposit);
        totalAmountDue = findViewById(R.id.totalAmountDue);
        linRoomBoy = findViewById(R.id.linRoomBoy);
        spinnerForex = findViewById(R.id.spinnerForex);
        formGuestInfo = findViewById(R.id.formGuestInfo);
        guestNameInput = findViewById(R.id.guestNameInput);
        guestBusinessStyle = findViewById(R.id.guestBusinessStyle);
        checkEmployee = findViewById(R.id.checkEmployee);
        spinnerEmplyeeSelection = findViewById(R.id.spinnerEmplyeeSelection);
        guestAddressInput = findViewById(R.id.guestAddressinput);
        guestTinInput = findViewById(R.id.guestTinInput);
        formCash = findViewById(R.id.formCash);
        formCard = findViewById(R.id.formCard);
        formCard2 = findViewById(R.id.formCard2);
        formTakas2 = findViewById(R.id.formTakas2);
        formTakas = findViewById(R.id.formTakas);
        formOnline = findViewById(R.id.formOnline);
        formOnline2 = findViewById(R.id.formOnline2);
        formVoucher = findViewById(R.id.formGiftCheck);
        formVoucher2 = findViewById(R.id.formGiftCheck2);
        formForex = findViewById(R.id.formForex);
        forexAmount = findViewById(R.id.forexAmount);
        spinnerRoomBoy = findViewById(R.id.spinnerRoomBoy);
        forexRate = findViewById(R.id.forexRate);
        displayTotalBalance = findViewById(R.id.totalBalance);
        displayTotalPayment = findViewById(R.id.totalPayment);
        listPayments = findViewById(R.id.listPayments);
        listPostedPayments = findViewById(R.id.listPostedPayments);
        add = findViewById(R.id.add);

        addCash = findViewById(R.id.addCash);
        addCard = findViewById(R.id.addCard);
        addOnline = findViewById(R.id.addOnline);
        addGc = findViewById(R.id.addGc);
        addForex = findViewById(R.id.addForex);
        addGuest = findViewById(R.id.addGuest);
        addTakas = findViewById(R.id.addTakas);

        pay = findViewById(R.id.pay);
        displayTotalChange = findViewById(R.id.totalChange);
        amountToPay = (EditText) findViewById(R.id.amount);

        voucherCode = (EditText) findViewById(R.id.voucherCode);
        voucherAmount = (EditText) findViewById(R.id.voucherAmount);
        spinnerOnline = (SearchableSpinner) findViewById(R.id.spinnerOnline);
        spinnerOnlineTakas = (SearchableSpinner) findViewById(R.id.spinnerOnlineTakas);

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


        rvCreditCard = (RecyclerView) findViewById(R.id.rvCreditCard);
        cardNumber = (EditText) findViewById(R.id.cardNumber);
        cardHoldersName = (EditText) findViewById(R.id.cardHoldersName);
        creditCardAmount = (EditText) findViewById(R.id.creditCardAmount);
        cardExpiration = (EditText) findViewById(R.id.expiration);
        ImageView ivSetExpiration = (ImageView) findViewById(R.id.ivSetExpiration);
        ivSetExpiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(), new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        cardExpiration.setText(String.format("%s/%s", String.valueOf((selectedMonth + 1 < 10 ? "0" + (selectedMonth + 1) : (selectedMonth + 1))), String.valueOf(selectedYear)));
                    }
                }, 2020, 01);

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(1990)
                        .setActivatedYear(2020)
                        .setMaxYear(2040)
                        .setTitle("CARD EXPIRATION")
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int year) {

                            }
                        })
                        .build().show();
            }
        });
        authorization = (EditText) findViewById(R.id.authorization);
        remarks = (EditText) findViewById(R.id.remarks);

        setForexSpinner();
        setVoucherSpinner();
        setupCreditCardSpinner();
        setupGuestName();
        setupTakasSpinner();
        if (isCheckout) {
            linRoomBoy.setVisibility(View.VISIBLE);
        } else {
            linRoomBoy.setVisibility(GONE);
        }
        setRoomBoySpinner();
        setGuestInfoSelection();

        displayTotalBalance.setText(Utils.returnWithTwoDecimal(String.valueOf(totalBalance)));
        paymentMethodImpl = new PaymentMethod() {
            @Override
            public void clicked(int position) {

                showForm(paymentTypeList.get(position).getCore_id(), paymentTypeList.get(position));
                paymentMethod = paymentTypeList.get(position);
            }
        };



        if (isCheckout) {

            pay.setText("CHECKOUT");

        } else {

            pay.setText("POSTING");

        }

        paymentTypeList.add(new PaymentTypeModel("999", "GUEST", false, "", false));



        checkEmployee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isEmployee = true;
                    tilGuestName.setVisibility(GONE);
                    tilGuestAddress.setVisibility(GONE);
                    tilTin.setVisibility(GONE);
                    guestAddressInput.setVisibility(View.GONE);
                    guestNameInput.setVisibility(View.GONE);
                    guestTinInput.setVisibility(View.GONE);
                    spinnerEmplyeeSelection.setVisibility(View.VISIBLE);
                } else {
                    isEmployee = false;
                    tilGuestName.setVisibility(VISIBLE);
                    tilGuestAddress.setVisibility(VISIBLE);
                    tilTin.setVisibility(VISIBLE);
                    guestAddressInput.setVisibility(View.VISIBLE);
                    guestNameInput.setVisibility(View.VISIBLE);
                    guestTinInput.setVisibility(View.VISIBLE);
                    spinnerEmplyeeSelection.setVisibility(View.GONE);
                }
            }
        });


        addCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(amountToPay.getText().toString())) {
                    if (Double.valueOf(amountToPay.getText().toString()) > 0) {
                        postedPaymentList.add(new PostedPaymentsModel(
                                paymentMethod.getCore_id(),
                                amountToPay.getText().toString(),
                                paymentMethod.getPayment_type(),
                                false,
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                                new JSONObject(),
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                                SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
                                isCheckout ? false : true,
                                "cash", "", ""));



                        if (postedPaymentsAdapter != null) {
                            postedPaymentsAdapter.notifyDataSetChanged();
                        }
                        computeTotal();
                    } else {
                        Utils.showDialogMessage(act, "Please enter valid amount for cash payment", "Information");
                    }
                } else {
                    Utils.showDialogMessage(act, "Please enter valid amount for cash payment", "Information");
                }


            }
        });
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            paymentMethod.getCore_id(),
                            creditCardAmount.getText().toString(),
                            paymentMethod.getPayment_type(),
                            false,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                            jsonObject,SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
                            isCheckout ? false : true,
                            "card", "",""));


                } else {
                    Utils.showDialogMessage(act, errorMessage, "Information");
                }

                if (postedPaymentsAdapter != null) {
                    postedPaymentsAdapter.notifyDataSetChanged();
                }
                computeTotal();
            }
        });

        addTakas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String errorMessage = "";

                if (paymentMethod == null) {
                    isValid = false;
                    errorMessage += "Invalid payment method \n";
                }

                if (TextUtils.isEmpty(takasId.trim())) {
                    isValid = false;
                    errorMessage += "Please select takas type \n" ;
                }

                if (TextUtils.isEmpty(takasRemarks.getText().toString().trim())) {
                    isValid = false;
                    errorMessage += "Empty takas remarks \n";
                }

                if (isValid) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("takas_id", takasId);
                        jsonObject.put("remarks", takasRemarks.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    postedPaymentList.add(new PostedPaymentsModel(
                            paymentMethod.getCore_id(),
                            takasAmount.getText().toString(),
                            paymentMethod.getPayment_type(),
                            false,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                            jsonObject,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
                            isCheckout ? false : true,
                            "online", "",
                            takasRemarks.getText().toString()));
                } else {
                    Utils.showDialogMessage(act, errorMessage, "Information");
                }

                if (postedPaymentsAdapter != null) {
                    postedPaymentsAdapter.notifyDataSetChanged();
                }
                computeTotal();
            }
        });

        addOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            paymentMethod.getCore_id(),
                            voucherAmount.getText().toString(),
                            paymentMethod.getPayment_type(),
                            false,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                            jsonObject,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
                            isCheckout ? false : true,
                            "online", "", ""));
                } else {
                    Utils.showDialogMessage(act, errorMessage, "Information");
                }

                if (postedPaymentsAdapter != null) {
                    postedPaymentsAdapter.notifyDataSetChanged();
                }
                computeTotal();
            }
        });
        addGc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            paymentMethod.getCore_id(),
                            String.valueOf(amount),
                            paymentMethod.getPayment_type(),
                            false,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
                            jsonObject,
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
                            isCheckout ? false : true,
                            "voucher","", ""));
                } else {
                    Utils.showDialogMessage(act, "No gc added", "Information");
                }

                if (postedPaymentsAdapter != null) {
                    postedPaymentsAdapter.notifyDataSetChanged();
                }
                computeTotal();
            }
        });
        addForex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            paymentMethod.getCore_id(),
                            forexAmount.getText().toString(),
                            paymentMethod.getPayment_type(),
                            false,
                            currencyId,
                            currencyValue,
                            new JSONObject(),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
                            SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
                            isCheckout ? false : true,
                            "forex","", ""));
                } else {
                    Utils.showDialogMessage(act, errorMessage, "Information");
                }

                if (postedPaymentsAdapter != null) {
                    postedPaymentsAdapter.notifyDataSetChanged();
                }
                computeTotal();
            }
        });
        addGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String errorMessage = "";

                if (paymentMethod == null) {
                    isValid = false;
                    errorMessage += "Invalid payment method";
                }

                if (!isEmployee) {
                    if (TextUtils.isEmpty(guestNameInput.getText().toString().trim())) {
                        isValid = false;
                        errorMessage += "Invalid name";
                    }
                }

                if (isValid) {
                    submitGuestInfoData(isEmployee ? guestInfoEmployeeId : "",
                            !isEmployee ?guestNameInput.getText().toString() : guestInfoEmployeeName,
                            !isEmployee ? guestAddressInput.getText().toString() : "",
                            !isEmployee ? guestTinInput.getText().toString() : "",
                            controlNumber,
                            !isEmployee ? guestBusinessStyle.getText().toString() : "");
                } else {
                    Utils.showDialogMessage(act, errorMessage, "Information");
                }

                if (postedPaymentsAdapter != null) {
                    postedPaymentsAdapter.notifyDataSetChanged();
                }
                computeTotal();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (paymentMethod == null) {
//                    Toast.makeText(getContext(), "Please select payment method", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (paymentMethod.getCore_id().equalsIgnoreCase("1")) { //cash
//                        if (!TextUtils.isEmpty(amountToPay.getText().toString())) {
//                            if (Double.valueOf(amountToPay.getText().toString()) >= 0) {
//                                postedPaymentList.add(new PostedPaymentsModel(
//                                        paymentMethod.getCore_id(),
//                                        amountToPay.getText().toString(),
//                                        paymentMethod.getPayment_type(),
//                                        false,
//                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
//                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
//                                        new JSONObject(),
//                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
//                                        SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
//                                        isCheckout ? false : true,
//                                        "cash", ""));
//                            } else {
//                                Utils.showDialogMessage(act, "Please enter valid amount for cash payment", "Information");
//                            }
//                        } else {
//                            Utils.showDialogMessage(act, "Please enter valid amount for cash payment", "Information");
//                        }
//
//                    } else if (paymentMethod.getCore_id().equalsIgnoreCase("2")) { //card
//                        boolean isValid = true;
//                        String errorMessage = "";
//                        if (TextUtils.isEmpty(cardTypeId)) {
//                            isValid = false;
//                            errorMessage += "Empty card type \n";
//                        }
//
//                        if (TextUtils.isEmpty(cardNumber.getText().toString().trim())) {
//                            isValid = false;
//                            errorMessage += "Empty card number \n";
//                        }
//
//                        if (TextUtils.isEmpty(cardHoldersName.getText().toString().trim())) {
//                            isValid = false;
//                            errorMessage += "Empty card holders name \n";
//                        }
//
//                        if (TextUtils.isEmpty(cardExpiration.getText().toString().trim())) {
//                            isValid = false;
//                            errorMessage += "Empty expiration date \n";
//                        }
//
//                        if (TextUtils.isEmpty(authorization.getText().toString().trim())) {
//                            isValid = false;
//                            errorMessage += "Empty authorization \n";
//                        }
//
//                        if (TextUtils.isEmpty(creditCardAmount.getText().toString().trim())) {
//                            isValid = false;
//                            errorMessage += "Invalid amount \n";
//                        } else {
//                            if (Double.valueOf(creditCardAmount.getText().toString()) < 1) {
//                                isValid = false;
//                                errorMessage += "Invalid amount \n";
//                            }
//                        }
//
//
//
//                        if (paymentMethod == null) {
//                            isValid = false;
//                            errorMessage += "Invalid payment method \n";
//                        }
//
//                        if (isValid) {
//                            JSONObject jsonObject = new JSONObject();
//                            try {
//                                jsonObject.put("card_type_id", cardTypeId);
//                                jsonObject.put("card_number", cardNumber.getText().toString());
//                                jsonObject.put("account_name", cardHoldersName.getText().toString());
//                                jsonObject.put("card_expiration", cardExpiration.getText().toString());
//                                jsonObject.put("authorization", authorization.getText().toString());
//                                jsonObject.put("remarks", remarks.getText().toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            postedPaymentList.add(new PostedPaymentsModel(
//                                    paymentMethod.getCore_id(),
//                                    creditCardAmount.getText().toString(),
//                                    paymentMethod.getPayment_type(),
//                                    false,
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
//                                    jsonObject,SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
//                                    isCheckout ? false : true,
//                                    "card", ""));
//
//
//                        } else {
//                            Utils.showDialogMessage(act, errorMessage, "Information");
//                        }
//
//
//                    } else if (paymentMethod.getCore_id().equalsIgnoreCase("3")) { //online
//
//                        boolean isValid = true;
//                        String errorMessage = "";
//
//                        if (paymentMethod == null) {
//                            isValid = false;
//                            errorMessage += "Invalid payment method \n";
//                        }
//
//                        if (TextUtils.isEmpty(onlineId.trim())) {
//                            isValid = false;
//                            errorMessage += "Please select ar type \n" ;
//                        }
//
//                        if (TextUtils.isEmpty(voucherCode.getText().toString().trim())) {
//                            isValid = false;
//                            errorMessage += "Empty voucher code \n";
//                        }
//
//                        if (isValid) {
//                            JSONObject jsonObject = new JSONObject();
//                            try {
//                                jsonObject.put("online_payment_id", onlineId);
//                                jsonObject.put("voucher_code", voucherCode.getText().toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            postedPaymentList.add(new PostedPaymentsModel(
//                                    paymentMethod.getCore_id(),
//                                    voucherAmount.getText().toString(),
//                                    paymentMethod.getPayment_type(),
//                                    false,
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
//                                    jsonObject,
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
//                                    isCheckout ? false : true,
//                                    "online", ""));
//                        } else {
//                            Utils.showDialogMessage(act, errorMessage, "Information");
//                        }
//
//                    } else if (paymentMethod.getCore_id().equalsIgnoreCase("5")) { //voucher
//                        if (gcList.size() > 0) {
//                            Double amount = 0.00;
//                            String gcCode = "";
//                            int index = 0;
//                            for (AvailableGcModel availableGcModel : gcList) {
//                                amount += Double.valueOf(availableGcModel.getAmount());
//                                if (index == gcList.size() - 1) {
//                                    gcCode += availableGcModel.getId();
//                                } else {
//                                    gcCode += availableGcModel.getId() + ",";
//                                }
//                                index++;
//                            }
//
//                            JSONObject jsonObject = new JSONObject();
//                            try {
//                                jsonObject.put("gc_code", gcCode);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                            postedPaymentList.add(new PostedPaymentsModel(
//                                    paymentMethod.getCore_id(),
//                                    String.valueOf(amount),
//                                    paymentMethod.getPayment_type(),
//                                    false,
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.COUNTRY_CODE),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_CURRENCY_VALUE),
//                                    jsonObject,
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
//                                    isCheckout ? false : true,
//                                    "voucher",""));
//                        } else {
//                            Utils.showDialogMessage(act, "No gc added", "Information");
//                        }
//                    } else if (paymentMethod.getCore_id().equalsIgnoreCase("6")) { //forex
//
//                        boolean isValid = true;
//                        String errorMessage = "";
//
//                        if (paymentMethod == null) {
//                            isValid = false;
//                            errorMessage += "Invalid payment method";
//                        }
//
//                        if (TextUtils.isEmpty(currencyId.toString().trim()) || TextUtils.isEmpty(currencyId.toString().trim())) {
//                            isValid = false;
//                            errorMessage += "invalid currency";
//                        }
//
//                        if (!TextUtils.isEmpty(forexAmount.getText().toString().trim())) {
//                            if (Double.valueOf(forexAmount.getText().toString()) < 1) {
//                                isValid = false;
//                                errorMessage = "Invalid amount \n";
//                            }
//                        } else {
//                            isValid = false;
//                            errorMessage = "Invalid amount \n";
//                        }
//
//
//                        if (isValid) {
//                            postedPaymentList.add(new PostedPaymentsModel(
//                                    paymentMethod.getCore_id(),
//                                    forexAmount.getText().toString(),
//                                    paymentMethod.getPayment_type(),
//                                    false,
//                                    currencyId,
//                                    currencyValue,
//                                    new JSONObject(),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_LEFT),
//                                    SharedPreferenceManager.getString(getContext(), ApplicationConstants.DEFAULT_SYMBOL_RIGHT),
//                                    isCheckout ? false : true,
//                                    "forex",""));
//                        } else {
//                            Utils.showDialogMessage(act, errorMessage, "Information");
//                        }
//
//
//                    } else if (paymentMethod.getCore_id().equalsIgnoreCase("999")) { //add guest
//                        boolean isValid = true;
//                        String errorMessage = "";
//
//                        if (paymentMethod == null) {
//                            isValid = false;
//                            errorMessage += "Invalid payment method";
//                        }
//
//                        if (!isEmployee) {
//                            if (TextUtils.isEmpty(guestNameInput.getText().toString().trim())) {
//                                isValid = false;
//                                errorMessage += "Invalid name";
//                            }
//                        }
//
//                        if (isValid) {
//                            submitGuestInfoData(isEmployee ? guestInfoEmployeeId : "",
//                                    !isEmployee ?guestNameInput.getText().toString() : guestInfoEmployeeName,
//                                    !isEmployee ? guestAddressInput.getText().toString() : "",
//                                    !isEmployee ? guestTinInput.getText().toString() : "",
//                                    controlNumber,
//                                    !isEmployee ? guestBusinessStyle.getText().toString() : "");
//                        } else {
//                            Utils.showDialogMessage(act, errorMessage, "Information");
//                        }
//                    }
//
//                    if (postedPaymentsAdapter != null) {
//                        postedPaymentsAdapter.notifyDataSetChanged();
//                    }
////                    paymentMethod = null;
//                }
//
//                computeTotal();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postedPaymentList.size() > 0) {
                    if (isDeposit) {
                        paymentSuccess(postedPaymentList, "");
                    } else {
                        if (!isTakeOut) {
                            if (TextUtils.isEmpty(empId)) {
                                Toast.makeText(getContext(), "Steward is empty", Toast.LENGTH_LONG).show();
                            } else {
                                paymentSuccess(postedPaymentList, empId);
                            }

                        } else {
                            paymentSuccess(postedPaymentList, empId);
                        }
                    }


                } else {

                    if (totalPayment >= amountDue) {
                        paymentSuccess(postedPaymentList, empId);
//                        pay.setBackgroundColor(Color.GREEN);
                    }
//                    else {
//                        pay.setBackgroundColor(Color.RED);
//                    }



                    Utils.showDialogMessage(act, "No payment to post", "Warning");
                }
            }
        });

        computeTotal();

        paymentsAdapter = new PaymentsAdapter(paymentTypeList, paymentMethodImpl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listPayments.setLayoutManager(new GridLayoutManager(getContext(), LinearLayoutManager.VERTICAL));
        listPayments.setAdapter(paymentsAdapter);
        paymentsAdapter.notifyDataSetChanged();

        postedPaymentsAdapter = new PostedPaymentsAdapter(postedPaymentList, voidItemContract);
        listPostedPayments.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
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
        showForm("1", paymentTypeList.get(0));

        setPostedPaymentSwipe();


        if (Utils.getSystemType(getContext()).equalsIgnoreCase("franchise")) {
            linRoomBoy.setVisibility(GONE);
        }
    }

    private void setupGuestName() {

        if (guestReceiptInfoModel != null) {

            if (!guestReceiptInfoModel.getName().equalsIgnoreCase("to be filled") && !guestReceiptInfoModel.getName().equalsIgnoreCase("empty")) {
                guestName.setText("NAME : " +guestReceiptInfoModel.getName());
                guestAddress.setText("ADDRESS : " +guestReceiptInfoModel.getAddress());
                guestTin.setText("TIN : " +guestReceiptInfoModel.getTin());

                guestNameInput.setText(guestReceiptInfoModel.getName());
                guestAddressInput.setText(guestReceiptInfoModel.getAddress());
                guestTinInput.setText(guestReceiptInfoModel.getTin());
            }
        }
    }

    public abstract void removePaymentSuccess();
    public abstract void paymentSuccess(List<PostedPaymentsModel> postedPaymentList, String roomBoy);
    public abstract void paymentFailed();

    public interface PaymentMethod {
        void clicked(int position);
    }

    private void computeTotal() {
        advancePayment = 0.00;
        normalPayment = 0.00;
        totalPayment = 0.00;
        amountDue = 0.00;
        totalChange = 0.00;
        for (PostedPaymentsModel ppm : postedPaymentList) {
            Log.d("PAYMNt",String.valueOf(Double.valueOf(ppm.getAmount()) / Double.valueOf(ppm.getCurrency_value())));

            if (ppm.isAdvance()) {
                advancePayment += Double.valueOf(ppm.getAmount()) / Double.valueOf(ppm.getCurrency_value());
            } else {
                normalPayment += Double.valueOf(ppm.getAmount()) / Double.valueOf(ppm.getCurrency_value());
            }
        }
        totalPayment = normalPayment;

        Double deductibles = Double.valueOf(String.format("%.2f", advancePayment + discountPayment + normalPayment));

        amountDue = Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((totalBalance - (deductibles))))) <= 0 ? 0.00 : Double.valueOf(Utils.returnWithTwoDecimal(String.valueOf((totalBalance - (deductibles)))));
        totalAmountDue.setText(Utils.returnWithTwoDecimal(String.valueOf(amountDue)));

        amountToPay.setText(Utils.returnWithTwoDecimal(String.valueOf(amountDue)));
        takasAmount.setText(Utils.returnWithTwoDecimal(String.valueOf(amountDue)));
        creditCardAmount.setText(Utils.returnWithTwoDecimal(String.valueOf(amountDue)));
//        amountToPay.requestFocus();
        totalChange = (deductibles) - totalBalance;

        displayTotalChange.setText(String.valueOf(totalChange));
//        totalChange = (totalPayment + advancePayment) - (totalBalance - discountPayment);
        if (Double.valueOf(totalChange) < 1) {
            displayTotalChange.setText("0.00");
        } else {
            displayTotalChange.setText(Utils.returnWithTwoDecimal(String.valueOf(totalChange)));
        }

        displayTotalPayment.setText(Utils.returnWithTwoDecimal(String.valueOf(normalPayment)));


        if (totalPayment >= amountDue) {
            pay.setBackgroundColor(Color.GREEN);
        } else {
            pay.setBackgroundColor(Color.RED);
        }

        totalDeposit.setText(Utils.returnWithTwoDecimal(String.valueOf(advancePayment)));
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

    private void enableCheckedForm(String core_id) {
        for (PaymentTypeModel ptm : paymentTypeList) {
            if (ptm.getCore_id().equalsIgnoreCase(core_id)) {
                ptm.setIs_selected(true);
            } else {
                ptm.setIs_selected(false);
            }

            if (paymentsAdapter != null) {
                paymentsAdapter.notifyDataSetChanged();
            }

        }
    }
    private void showForm(String coreId, PaymentTypeModel ptm) {
        if (ptm.isIs_two_form()) {
            LinearLayout.LayoutParams rl1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.3f);
            LinearLayout.LayoutParams rl2Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.4f);
            rl1.setLayoutParams(rl1Params);
            rl2.setLayoutParams(rl2Params);
        } else {
            LinearLayout.LayoutParams rl1Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.6f);
            LinearLayout.LayoutParams rl2Params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
            rl1.setLayoutParams(rl1Params);
            rl2.setLayoutParams(rl2Params);
        }
        enableCheckedForm(coreId);
        if (coreId.equalsIgnoreCase("1")) { //cash
            formCash.setVisibility(View.VISIBLE);
            formCard.setVisibility(GONE);
            formCard2.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formVoucher2.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formOnline2.setVisibility(GONE);
            formTakas.setVisibility(GONE);
            formTakas2.setVisibility(GONE);
            formForex.setVisibility(GONE);
            formGuestInfo.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("2")) { //card
            formCash.setVisibility(GONE);
            formCard.setVisibility(View.VISIBLE);
            formCard2.setVisibility(View.VISIBLE);
            formVoucher.setVisibility(GONE);
            formVoucher2.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formOnline2.setVisibility(GONE);
            formTakas.setVisibility(GONE);
            formTakas2.setVisibility(GONE);
            formForex.setVisibility(GONE);
            formGuestInfo.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("3")) { //online
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formCard2.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formVoucher2.setVisibility(GONE);
            formOnline.setVisibility(View.VISIBLE);
            formOnline2.setVisibility(View.VISIBLE);
            formTakas.setVisibility(GONE);
            formTakas2.setVisibility(GONE);
            formForex.setVisibility(GONE);
            formGuestInfo.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("5")) { //voucher
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formCard2.setVisibility(GONE);
            formVoucher.setVisibility(View.VISIBLE);
            formVoucher2.setVisibility(VISIBLE);
            formOnline.setVisibility(GONE);
            formOnline2.setVisibility(GONE);
            formTakas.setVisibility(GONE);
            formTakas2.setVisibility(GONE);
            formForex.setVisibility(GONE);
            formGuestInfo.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("6")) { //forex
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formCard2.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formVoucher2.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formOnline2.setVisibility(GONE);
            formTakas.setVisibility(GONE);
            formTakas2.setVisibility(GONE);
            formForex.setVisibility(View.VISIBLE);
            formGuestInfo.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("8")) { //forex
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formCard2.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formVoucher2.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formOnline2.setVisibility(GONE);
            formTakas.setVisibility(VISIBLE);
            formTakas2.setVisibility(VISIBLE);
            formForex.setVisibility(GONE);
            formGuestInfo.setVisibility(GONE);
        } else if (coreId.equalsIgnoreCase("999")) {
            formGuestInfo.setVisibility(View.VISIBLE);
            formCash.setVisibility(View.GONE);
            formCard.setVisibility(GONE);
            formCard2.setVisibility(GONE);
            formVoucher.setVisibility(GONE);
            formVoucher2.setVisibility(GONE);
            formOnline.setVisibility(GONE);
            formOnline2.setVisibility(GONE);
            formTakas.setVisibility(GONE);
            formTakas2.setVisibility(GONE);
            formForex.setVisibility(View.GONE);
        }
    }

    private void setForexSpinner() {
        final List<String> forexArray = new ArrayList<>();
        if (currencyList != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (FetchCurrencyExceptDefaultResponse.Result curr : currencyList) {
                        forexArray.add(curr.getCurrency());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                            forexArray);
                    spinnerForex.setAdapter(rateSpinnerAdapter);

                    spinnerForex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            currencyId = currencyList.get(position).getCountryCode();
                            currencyValue = String.valueOf(currencyList.get(position).getValue());

                            forexRate.setText("1 " + currencyList.get(position).getCountryCode() + " is equivalent to " + Utils.returnWithTwoDecimal(String.valueOf(1 / currencyList.get(position).getValue())));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }.execute();

        }


    }

    private void setVoucherSpinner() {
        final List<String> onlineArray = new ArrayList<>();
        if (arOnlineList != null) {


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (FetchArOnlineResponse.Result ar : arOnlineList) {
                        onlineArray.add(ar.getArOnline());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
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
            }.execute();


        }


    }

    private void setupTakasSpinner() {
        //spinnerOnlineTakas

        final List<String> takasArray = new ArrayList<>();

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        TakasListRequest takasListRequest = new TakasListRequest();
        Call<TakasListResponse> request = iUsers.fetchTakasList(takasListRequest.getMapValue());
        request.enqueue(new Callback<TakasListResponse>() {
            @Override
            public void onResponse(Call<TakasListResponse> call, Response<TakasListResponse> response) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        for (TakasListResponse.Result ar : response.body().getResult()) {
                            takasArray.add(ar.getTakas());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        CustomSpinnerAdapter rateSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                                takasArray);
                        spinnerOnlineTakas.setAdapter(rateSpinnerAdapter);

                        spinnerOnlineTakas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //fix this when api call ready
                                takasId = String.valueOf(response.body().getResult().get(position).getId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }.execute();
            }

            @Override
            public void onFailure(Call<TakasListResponse> call, Throwable t) {

            }
        });







    }

    private void setupCreditCardSpinner() {
        final List<CreditCardListModel> ccArray = new ArrayList<>();
        if (creditCardList != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (FetchCreditCardResponse.Result cc : creditCardList) {
                        if (cc.getCoreId() == 1) {
                            ccArray.add(new CreditCardListModel(cc.getCoreId(), cc.getCreditCard(), false, R.drawable.mastercard));
                        } else if (cc.getCoreId()== 2) {
                            ccArray.add(new CreditCardListModel(cc.getCoreId(), cc.getCreditCard(), false, R.drawable.mastercard));
                        }


                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);



                    CreditCardContract creditCardContract = new CreditCardContract() {
                        @Override
                        public void clicked(CreditCardListModel creditCardListModel, int position) {
                            for (CreditCardListModel ccm : ccArray) {

                                if (ccm.getCore_id() == ccArray.get(position).getCore_id()) {
                                    ccm.setIs_selected(true);
                                    cardTypeId = String.valueOf(creditCardList.get(position).getCoreId());
                                } else {
                                    ccm.setIs_selected(false);
                                }
                                if (creditCardAdapter != null) {
                                    creditCardAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    };
                    creditCardAdapter = new CreditCardAdapter(ccArray, creditCardContract);
                    rvCreditCard.setAdapter(creditCardAdapter);
                    rvCreditCard.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    creditCardAdapter.notifyDataSetChanged();

                }
            }.execute();


        }


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
                                if (myList.getId().equalsIgnoreCase(availList.getId())) {
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


    private void setGuestInfoSelection() {

        TypeToken<List<FetchCompanyUserResponse.Result>> companyUser = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        final List<FetchCompanyUserResponse.Result> userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.COMPANY_USER), companyUser.getType());

        final List<String> userArray = new ArrayList<>();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (FetchCompanyUserResponse.Result res : userList) {
                    userArray.add(res.getName());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                CustomSpinnerAdapter userAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                        userArray);
                spinnerEmplyeeSelection.setAdapter(userAdapter);

                spinnerEmplyeeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        guestInfoEmployeeId = String.valueOf(userList.get(position).getUsername());
                        guestInfoEmployeeName = String.valueOf(userList.get(position).getName());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }.execute();




    }

    private void setRoomBoySpinner() {

        TypeToken<List<FetchUserResponse.Result>> companyUser = new TypeToken<List<FetchUserResponse.Result>>() {};
        final List<FetchUserResponse.Result> userList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.USER_JSON), companyUser.getType());

        final List<String> userArray = new ArrayList<>();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (FetchUserResponse.Result res : userList) {
                    userArray.add(res.getName());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
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
        }.execute();




    }

    private void setPostedPaymentSwipe() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                postedPaymentList.remove(viewHolder.getAdapterPosition());
                computeTotal();
                postedPaymentsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (postedPaymentList.size() > 0) {
                    if (postedPaymentList.get(viewHolder.getAdapterPosition()).isIs_posted()) {
                        return 0;
                    }
                }

                return super.getMovementFlags(recyclerView, viewHolder);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(listPostedPayments);
    }

    private void submitGuestInfoData(String userId, final String gn,
                                     final String ga, final String gt,
                                     String controlNumber, String businessStyle) {

        SaveGuestInfoRequest saveGuestInfoRequest = new SaveGuestInfoRequest(userId, gn,
                ga, gt,
                controlNumber, businessStyle);
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<SaveGuestInfoResponse> request = iUsers.saveGuestInfo(saveGuestInfoRequest.getMapValue());

        request.enqueue(new Callback<SaveGuestInfoResponse>() {
            @Override
            public void onResponse(Call<SaveGuestInfoResponse> call, Response<SaveGuestInfoResponse> response) {
                removePaymentSuccess();
                guestName.setText("NAME : " + gn);
                guestAddress.setText("ADDRESS : " +ga);
                guestTin.setText("TIN : " + gt);
                Utils.showDialogMessage(act, "Guest info saved", "Information");
            }

            @Override
            public void onFailure(Call<SaveGuestInfoResponse> call, Throwable t) {

            }
        });
    }

}
