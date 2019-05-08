package nerdvana.com.pointofsales;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.eposprint.Print;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import nerdvana.com.pointofsales.api_requests.CheckSafeKeepingRequest;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.api_requests.FetchArOnlineRequest;
import nerdvana.com.pointofsales.api_requests.FetchBranchInfoRequest;
import nerdvana.com.pointofsales.api_requests.FetchCompanyUserRequest;
import nerdvana.com.pointofsales.api_requests.FetchCreditCardRequest;
import nerdvana.com.pointofsales.api_requests.FetchCurrencyExceptDefaultRequest;
import nerdvana.com.pointofsales.api_requests.FetchDefaultCurrencyRequest;
import nerdvana.com.pointofsales.api_requests.FetchDenominationRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountSpecialRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomAreaRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.FetchTimeRequest;
import nerdvana.com.pointofsales.api_requests.FetchUserRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_responses.CashNReconcileResponse;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.CheckSafeKeepingResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchDefaultCurrenyResponse;
import nerdvana.com.pointofsales.api_responses.FetchDenominationResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountReasonResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountSpecialResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchTimeResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
import nerdvana.com.pointofsales.background.CheckOutAsync;
import nerdvana.com.pointofsales.background.CountUpTimer;
import nerdvana.com.pointofsales.background.RoomStatusAsync;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.dialogs.DialogProgressBar;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ChangeWakeUpCallPrintModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProgressBarModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.SwitchRoomPrintModel;
import nerdvana.com.pointofsales.model.TimerModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;
import nerdvana.com.pointofsales.postlogin.BottomFrameFragment;
import nerdvana.com.pointofsales.prelogin.LeftFrameFragment;
import nerdvana.com.pointofsales.prelogin.RightFrameFragment;
import nerdvana.com.pointofsales.requests.TestRequest;
import nerdvana.com.pointofsales.service.TimerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PreloginContract, View.OnClickListener {
    private String currentText = "";
    public static String roomNumber;
    private Loading loadingInterface;
    private SelectionContract centralInterface;

    RoomTableModel selected;

    private LeftFrameFragment preLoginLeftFrameFragment;
    private RightFrameFragment preLoginRightFrameFragment;
    private nerdvana.com.pointofsales.postlogin.LeftFrameFragment postLoginLeftFrameFragment;
    private nerdvana.com.pointofsales.postlogin.RightFrameFragment postLoginRightFrameFragment;


    private TextView timer;
    private Button logout;
    private Button showMap;
    private Button showTakeOuts;
    private TextView user;

    private UserModel userModel;

    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    private DialogProgressBar dialogProgressBar;


    private Intent timerIntent;

    private String currentDateTime = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        saveDenominationPref();
        savePaymentTypePref();
        fetchDiscountSpecialRequest();
        saveBranchInfo();
        dialogProgressBar = new DialogProgressBar(MainActivity.this);
        dialogProgressBar.setCancelable(false);

        loadingInterface = new Loading() {
            @Override
            public void show(boolean willShow) {
                if (willShow) {
                    if (dialogProgressBar != null) {
                        if (!dialogProgressBar.isShowing()) dialogProgressBar.show();
                    }
                } else {
                    if (dialogProgressBar != null) {
                        if (dialogProgressBar.isShowing()) dialogProgressBar.dismiss();
                    }
                }
            }
        };


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initializeViews();
        initializeFragments();

        decideViewToShow();
        fetchRoomAreaRequest();
        fetchUserListRequest();
        fetchCompanyUserRequest();

        requestRoomStatusList();
        fetchDefaultCurrencyRequest();
        BusProvider.getInstance().post(new TestRequest("test"));

//        dialogProgressBar.show();
//        Log.d("TAG", "SERIAL: " + Build.SERIAL);
//        Log.d("TAG","MODEL: " + Build.MODEL);
//        Log.d("TAG","ID: " + Build.ID);
//        Log.d("TAG","Manufacture: " + Build.MANUFACTURER);
//        Log.d("TAG","brand: " + Build.BRAND);
//        Log.d("TAG","type: " + Build.TYPE);
//        Log.d("TAG","user: " + Build.USER);
//        Log.d("TAG","BASE: " + Build.VERSION_CODES.BASE);
//        Log.d("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
//        Log.d("TAG","SDK  " + Build.VERSION.SDK);
//        Log.d("TAG","BOARD: " + Build.BOARD);
//        Log.d("TAG","BRAND " + Build.BRAND);
//        Log.d("TAG","HOST " + Build.HOST);
//        Log.d("TAG","FINGERPRINT: "+Build.FINGERPRINT);
//        Log.d("TAG","Version Code: " + Build.VERSION.RELEASE);
//        Log.d("MYCONNECTION", String.valueOf(Utils.checkConnection(this)));

    }

    private void saveBranchInfo() {

        FetchBranchInfoRequest fetchBranchInfoRequest = new FetchBranchInfoRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchBranchInfoResponse> request = iUsers.fetchBranchInfo(fetchBranchInfoRequest.getMapValue());

        request.enqueue(new Callback<FetchBranchInfoResponse>() {
            @Override
            public void onResponse(Call<FetchBranchInfoResponse> call, Response<FetchBranchInfoResponse> response) {


                SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(response.body().getResult().getShift()), ApplicationConstants.SHIFT_INFO_ARRAY);

                fetchTimeRequest();

            }

            @Override
            public void onFailure(Call<FetchBranchInfoResponse> call, Throwable t) {

            }
        });


    }


    private void initializeViews() {
        logout = findViewById(R.id.logout);
        timer = findViewById(R.id.timer);
        logout.setOnClickListener(this);
        showMap = findViewById(R.id.showMap);
        showMap.setOnClickListener(this);
        user = findViewById(R.id.user);
        showTakeOuts = findViewById(R.id.showTakeOuts);
        showTakeOuts.setOnClickListener(this);
    }

    private void initializeFragments() {

        preLoginLeftFrameFragment = LeftFrameFragment.newInstance();
        preLoginRightFrameFragment = RightFrameFragment.newInstance(this);

        postLoginLeftFrameFragment = nerdvana.com.pointofsales.postlogin.LeftFrameFragment.newInstance(centralInterface, loadingInterface);
        postLoginRightFrameFragment = nerdvana.com.pointofsales.postlogin.RightFrameFragment.newInstance();
    }

    private void decideViewToShow() {
        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.userSettings), UserModel.class);

        openFragment(R.id.bottomFrame, new BottomFrameFragment());

        if (userModel != null) {
            if (userModel.isLoggedIn()) { //post login
//                logout.setVisibility(View.VISIBLE);
                user.setVisibility(View.VISIBLE);
                user.setText(String.format("%s %s - %s", getResources().getString(R.string.welcome_text), userModel.getUsername(), userModel.getUserGroup()) + ApplicationConstants.VERSION);
                currentText = String.format("%s %s - %s", getResources().getString(R.string.welcome_text), userModel.getUsername(), userModel.getUserGroup());
                openFragment(R.id.leftFrame, postLoginLeftFrameFragment);

                openFragment(R.id.rightFrame, postLoginRightFrameFragment);
            } else { //pre login
//                logout.setVisibility(View.GONE);
                user.setVisibility(View.GONE);
                openFragment(R.id.leftFrame, preLoginLeftFrameFragment);
                openFragment(R.id.rightFrame, preLoginRightFrameFragment);
            }
        } else { //pre login
//            logout.setVisibility(View.GONE);
            user.setVisibility(View.GONE);
            openFragment(R.id.leftFrame, preLoginLeftFrameFragment);
            openFragment(R.id.rightFrame, preLoginRightFrameFragment);
        }
    }


    private void openFragment(int container, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void loginSuccess() {
        decideViewToShow();
        showMessage(getResources().getString(R.string.login_success_message));

    }

    @Override
    public void loginFailed() {
        showMessage(getResources().getString(R.string.login_error_message));
    }

    private void showMessage(String message) {
        Helper.hideKeyboard(this, logout);
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.coordinator),
                message, Snackbar.LENGTH_LONG);
        mySnackbar.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                showListMenu(logout);
//                SharedPreferenceManager.clearPreference(this);
//                decideViewToShow();
                break;
            case R.id.showMap:
                Intent roomSelectionIntent = new Intent(this, RoomsActivity.class);
                startActivityForResult(roomSelectionIntent, 10);
                break;
            case R.id.showTakeOuts:
                Intent takeOutIntent = new Intent(this, TakeOutActivity.class);
                startActivityForResult(takeOutIntent, 20);
                break;
        }
    }

    @SuppressLint("NewApi")
    private void showListMenu(View anchor) {
        data = new ArrayList<HashMap<String, Object>>();

        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Logout");
        map.put("icon", R.drawable.ic_launcher_background);

        data.add(map);

        ListPopupWindow popupWindow = new ListPopupWindow(this);

        ListAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_1, // You may want to use your own cool layout
                new String[] {"title", "icon"}, // These are just the keys that the data uses
                new int[] {android.R.id.text1, android.R.id.icon}); // The view ids to map the data to


        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (data.get(position).get("title").toString().toLowerCase()) {
                    case "logout":
                        userModel.setLoggedIn(false);

                        SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(userModel), ApplicationConstants.userSettings);


//                        SharedPreferenceManager.clearPreference(getApplicationContext());
                        CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);

//                        openFragment(R.id.leftFrame, preLoginLeftFrameFragment);
//                        openFragment(R.id.rightFrame, preLoginRightFrameFragment);

                        finish();
                        startActivity(new Intent(MainActivity.this, SetupActivity.class));
                        break;
                }
            }
        });

        popupWindow.setAnchorView(anchor);
        popupWindow.setAdapter(adapter);
//        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.lblstyle));//----
        popupWindow.setWidth(400); // note: don't use pixels, use a dimen resource
//        popupWindow.setOnItemClickListener(myListener); // the callback for when a list item is selected
        popupWindow.show();


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }

        try {
            SPrinter.getPrinter().disconnect();
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
        if (timerIntent != null) {
            stopService(timerIntent);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10) {
                selected = GsonHelper.getGson().fromJson(data.getStringExtra("selected"), RoomTableModel.class);

//                CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
//                CurrentTransactionEntity currentTransactionEntity = new
//                CurrentTransactionEntity( selected.getName(), selected.getAmountSelected());
//
//                currentTransactionEntity.save();


                BusProvider.getInstance().post(new FragmentNotifierModel(selected));

//                Toast.makeText(this, selected.getName() + " selected", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 20) {
                RoomTableModel selected = GsonHelper.getGson().fromJson(data.getStringExtra("selected"), RoomTableModel.class);
//                Toast.makeText(getApplicationContext(), "ORDER SELECTED", Toast.LENGTH_SHORT).show();
                BusProvider.getInstance().post(new FragmentNotifierModel(selected));
            }
        } else {
//            Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show();
        }
    }

//    private void saveSelectedSpace(String selectedSpace) {
//        SharedPreferenceManager.saveString(MainActivity.this, selectedSpace, ApplicationConstants.SELECTED_ROOM_TABLE);
//    }

    private void requestRoomStatusList() {
        List<RoomStatusEntity> list = RoomStatusEntity.listAll(RoomStatusEntity.class);
        if (list.size() < 1) {
            BusProvider.getInstance().post(new FetchRoomStatusRequest());
        }
    }

    @Subscribe
    public void onReceiveRoomStatusList(FetchRoomStatusResponse fetchRoomStatusResponse) {
        new RoomStatusAsync(fetchRoomStatusResponse.getResult()).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        checkSafeKeepingRequest();
        loadPrinter();
    }

    @Subscribe
    public void apiError(ApiError apiError) {
        Toast.makeText(MainActivity.this, apiError.message(), Toast.LENGTH_SHORT).show();
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }

    private void fetchRoomAreaRequest() {
        BusProvider.getInstance().post(new FetchRoomAreaRequest());
    }

    @Subscribe
    public void onReceiveFetchRoomAreaResponse(FetchRoomAreaResponse fetchRoomAreaResponse) {
        if (fetchRoomAreaResponse.getResult().size() > 0) {
            SharedPreferenceManager.saveString(getApplicationContext(),
                    GsonHelper.getGson().toJson(fetchRoomAreaResponse.getResult()),
                    ApplicationConstants.ROOM_AREA_JSON);
        }
    }

    private void fetchUserListRequest() {
        BusProvider.getInstance().post(new FetchUserRequest());
    }

    @Subscribe
    public void fetchUserResponse(FetchUserResponse fetchUserResponse) {
        if (fetchUserResponse.getResult().size() > 0) {
            SharedPreferenceManager.saveString(getApplicationContext(),
                    GsonHelper.getGson().toJson(fetchUserResponse.getResult()),
                    ApplicationConstants.USER_JSON);
        }
    }

    private void fetchDefaultCurrencyRequest() {
        BusProvider.getInstance().post(new FetchDefaultCurrencyRequest());
    }

    @Subscribe
    public void fetchDefaultCurrencyRequest(FetchDefaultCurrenyResponse fetchDefaultCurrenyResponse) {
        SharedPreferenceManager.saveString(getApplicationContext(), String.valueOf(fetchDefaultCurrenyResponse.getResult().getValue()) ,ApplicationConstants.DEFAULT_CURRENCY_VALUE);
        SharedPreferenceManager.saveString(getApplicationContext(), String.valueOf(fetchDefaultCurrenyResponse.getResult().getId()) ,ApplicationConstants.DEFAULT_CURRENCY_ID);
        SharedPreferenceManager.saveString(getApplicationContext(), fetchDefaultCurrenyResponse.getResult().getSymbolLeft() == null ? "" : fetchDefaultCurrenyResponse.getResult().getSymbolLeft() ,ApplicationConstants.DEFAULT_SYMBOL_LEFT);
        SharedPreferenceManager.saveString(getApplicationContext(), fetchDefaultCurrenyResponse.getResult().getSymbolRight() == null ? "" : fetchDefaultCurrenyResponse.getResult().getSymbolRight().toString() ,ApplicationConstants.DEFAULT_SYMBOL_RIGHT);
        SharedPreferenceManager.saveString(getApplicationContext(), fetchDefaultCurrenyResponse.getResult().getCountryCode() ,ApplicationConstants.COUNTRY_CODE);
    }


    //isBold || isUnderlined = Printer.FALSE, Printer.TRUE
    //alignment =  Printer.ALIGN_LEFT, Printer.ALIGN_CENTER, Printer.ALIGN_RIGHT
    @Subscribe
    public void print(PrintModel printModel) {
        boolean willExecutGlobalPrint = true;
        try {
            SPrinter.getPrinter().beginTransaction();
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
        //regionheader
        addTextToPrinter(SPrinter.getPrinter(), "*", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "PANORAMA ENTERPRISE INC", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "ESCARPMENT ROAD BARANGAY BAGONG", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "ILOG PASIG CITY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "671-9782", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "SERIAL NO: PC001LTR", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1,1);
        addTextToPrinter(SPrinter.getPrinter(), "VAT REG TIN NO: " + SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.TIN_NUMBER), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "PERMIT NO: FP082015-43A-0046941-00000", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );
        addTextToPrinter(SPrinter.getPrinter(), "MIN NO: 15080516005415409", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1 ,1 );
        if (!printModel.getType().equalsIgnoreCase("rexreading")
                && !printModel.getType().equalsIgnoreCase("safekeeping")
                && !printModel.getType().equalsIgnoreCase("shortover")
                && !printModel.getType().equalsIgnoreCase("zread")
                && !printModel.getType().equalsIgnoreCase("backout")) {
            addTextToPrinter(SPrinter.getPrinter(), printModel.getRoomNumber().equalsIgnoreCase("takeout") ? printModel.getRoomNumber() : "ROOM #" + printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,2,2);
        }

        //endregion

        switch (printModel.getType()) {
            case "POST_VOID":

                ViewReceiptResponse.Result toListPV = GsonHelper.getGson().fromJson(printModel.getData(), ViewReceiptResponse.Result.class)
                        ;

                if (toListPV != null) {
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CASHIER IN",
                            toListPV.getCashier() != null ? String.valueOf(toListPV.getCashier().getName()) : "NA "
                            ,
                            40,
                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CASHIER OUT",
                            toListPV.getCashierOut() != null ? String.valueOf(toListPV.getCashierOut().getName()) : "NA "
                            ,
                            40,
                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "ROOM BOY IN",
                            String.valueOf(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomBoyIn().getName() : "NA")
                            ,
                            40,
                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "ROOM BOY OUT",
                            String.valueOf(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getRoomBoyOut().getName() : "NA")
                            ,
                            40,
                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CHECK IN",
                            convertDateToReadableDate(toListPV.getGuestInfo() != null ?toListPV.getGuestInfo().getCheckIn() : "NA")
                            ,
                            40,
                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CHECK OUT",
                            convertDateToReadableDate(toListPV.getGuestInfo() != null ? toListPV.getGuestInfo().getCheckOut() : "NA")
                            ,
                            40,
                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "RECEIPT NO",
                            toListPV.getReceiptNo() == null ? "NOT YET CHECKOUT" : toListPV.getReceiptNo().toString(),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "MACHINE NO",
                            SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_ID),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "QTY   Description             Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    for (ViewReceiptResponse.Post_ soaTrans : toListPV.getPost()) {

                        String qty = "";

                        qty += soaTrans.getQty();
                        if (String.valueOf(soaTrans.getQty()).length() < 4) {
                            for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                                qty += " ";
                            }
                        }
                        String item = "";

                        if (soaTrans.getProductId() == 0) {
                            item =soaTrans.getRoomRate().toString();
                        } else {
                            item =soaTrans.getProduct().getProductInitial();
                        }


                        if (soaTrans.getVoid() == 0) {
                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    qty+ " "+item,
                                    returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty()))
                                    ,
                                    40,
                                    2),
                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                    }

                    if (toListPV.getOtHours() > 0) {
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                String.valueOf(toListPV.getOtHours()) + " " + "OT HOURS",
                                returnWithTwoDecimal(String.valueOf(toListPV.getOtAmount()))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }




//
                    addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "   VAT EXEMPT",
                            returnWithTwoDecimal(String.valueOf(toListPV.getVatExempt())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "   DISCOUNT",
                            returnWithTwoDecimal(String.valueOf(toListPV.getDiscount())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "   ADVANCED DEPOSIT",
                            returnWithTwoDecimal(String.valueOf(toListPV.getAdvance())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "SUB TOTAL",
                            returnWithTwoDecimal(String.valueOf(toListPV.getTotal())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "AMOUNT DUE",
                            returnWithTwoDecimal(String.valueOf((toListPV.getTotal() + toListPV.getOtAmount() + toListPV.getxPersonAmount()) - (toListPV.getAdvance() + toListPV.getDiscount() + toListPV.getVatExempt()))),
                            40,
                            2)
                            ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "TENDERED",
                            returnWithTwoDecimal(String.valueOf(toListPV.getTendered())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CHANGE",
                            returnWithTwoDecimal(String.valueOf((toListPV.getChange() * -1))),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "VATable sales",
                            returnWithTwoDecimal(String.valueOf(toListPV.getVatable())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "VAT-EXEMPT SALES",
                            returnWithTwoDecimal(String.valueOf(toListPV.getVatExemptSales())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "12% VAT",
                            returnWithTwoDecimal(String.valueOf(toListPV.getVat())),
                            40,
                            2)
                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);
//                    addTextToPrinter(SPrinter.getPrinter(), "ADVANCE DEPOSIT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    for (ViewReceiptResponse.Payment pym : toListPV.getPayments()) {
                        if (pym.getIsAdvance() == 1) {
                            addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                    pym.getPaymentDescription(),
                                    returnWithTwoDecimal(String.valueOf(pym.getAmount())),
                                    40,
                                    2)
                                    ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        }
                    }

                    addPrinterSpace(1);

                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "VOID", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,2,2);
                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);

                    addFooterToPrinter();

//                    try {
//                        SPrinter.getPrinter().addCut(Printer.CUT_FEED);
//                    } catch (Epos2Exception e) {
//                        e.printStackTrace();
//                    }

//                    addTextToPrinter(SPrinter.getPrinter(), "PAYMENT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "ROOM NO",
//                            String.valueOf(printModel.getRoomNumber())
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "ROOM TYPE",
//                            printModel.getRoomType()
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "CASHIER",
//                            toList1.getCashier() != null ? String.valueOf(toList1.getCashier().getName()) : "NA"
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "ROOM BOY",
//                            String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA")
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "CHECK IN",
//                            convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckIn() : "NA" )
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
//                            "CHECK OUT",
//                            convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA")
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//                    List<Integer> tempArray = new ArrayList<>();
//                    String paymentType = "";
//                    for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
//                        if (!tempArray.contains(pym.getPaymentTypeId())) {
//                            tempArray.add(pym.getPaymentTypeId());
//                            paymentType = pym.getPaymentDescription();
//                        }
//                    }
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "PAYMENT TYPE",
//                            tempArray.size() > 1 ? "MULTIPLE" : paymentType
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addPrinterSpace(1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "AMOUNT DUE",
//                            returnWithTwoDecimal(String.valueOf(toList1.getTotal() - (toList1.getAdvance() + toList1.getDiscount()))),
//                            40,
//                            2), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "TENDERED",
//                            returnWithTwoDecimal(String.valueOf(toList1.getTendered()))
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                            "CHANGE",
//                            returnWithTwoDecimal(String.valueOf((toList1.getChange() * -1)))
//                            ,
//                            40,
//                            2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                } else {
                    Log.d("DATANUL"," DATAI SNULL");
                }



                break;
            case "CHANGE_WAKE_UP_CALL":

                ChangeWakeUpCallPrintModel changeWakeUpCallPrintModel = GsonHelper.getGson().fromJson(printModel.getData(), ChangeWakeUpCallPrintModel.class);

                addTextToPrinter(SPrinter.getPrinter(), "CHANGE WAKE UP CALL SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "NEW WAKE UP CALL",
                        changeWakeUpCallPrintModel.getNewWakeUpCall()
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


                break;
            case "BACKOUT":

                RoomTableModel selectedRoom = GsonHelper.getGson().fromJson(printModel.getData(), RoomTableModel.class);

                addTextToPrinter(SPrinter.getPrinter(), "BACK OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

                if (selectedRoom.getRoomId() != 0) {
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "ROOM NO",
                            selectedRoom.getName() != null ? selectedRoom.getName() : "NA"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CHECK IN TIME",
                            selectedRoom.getCheckInTime() != null ? selectedRoom.getCheckInTime() : "NA"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "EXPECTED CHECKOUT",
                            selectedRoom.getExpectedCheckout() != null ? selectedRoom.getExpectedCheckout() : "NA"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "BACKOUT TIME",
                            currentDateTime
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "REMARKS",
                            printModel.getRemarks()
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                } else {

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CONTROL NO",
                            selectedRoom.getControlNo()
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "BACKOUT TIME",
                            currentDateTime
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "REMARKS",
                            printModel.getRemarks()
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                }



                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Approved by: " + printModel.getDutyManager(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);



                break;
            case "SHORTOVER":
                FetchXReadingViaIdResponse.Result shorover = GsonHelper.getGson().fromJson(printModel.getData(), FetchXReadingViaIdResponse.Result.class);

                addTextToPrinter(SPrinter.getPrinter(), "SHORT OVER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

                addPrinterSpace(1);

//                Log.d("SHORTOVERDATA", String.valueOf(shorover.getShortOver()));

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "SHORT / OVER",
                        String.valueOf(shorover.getShortOver())
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                break;
            case "CASHRECONCILE":
                addTextToPrinter(SPrinter.getPrinter(), "CASHIER RECONCILE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                TypeToken<List<CollectionFinalPostModel>> cashrecotoken = new TypeToken<List<CollectionFinalPostModel>>() {};
                List<CollectionFinalPostModel> cashrecodetails = GsonHelper.getGson().fromJson(printModel.getData(), cashrecotoken.getType());
                addTextToPrinter(SPrinter.getPrinter(), "BILLS", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                fixDenoPrint(cashrecodetails);
                break;
            case "SAFEKEEPING":
                addTextToPrinter(SPrinter.getPrinter(), "SAFEKEEPING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                TypeToken<List<CollectionFinalPostModel>> collectionToken = new TypeToken<List<CollectionFinalPostModel>>() {};
                List<CollectionFinalPostModel> collectionDetails = GsonHelper.getGson().fromJson(printModel.getData(), collectionToken.getType());
                addTextToPrinter(SPrinter.getPrinter(), "BILLS", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                fixDenoPrint(collectionDetails);
                break;
            case "ZREAD":
                ZReadResponse.Result zReadResponse = GsonHelper.getGson().fromJson(printModel.getData(), ZReadResponse.Result.class);

                if (zReadResponse != null) {

                    addTextToPrinter(SPrinter.getPrinter(), "Z READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "POSTING DATE: " + zReadResponse.getData().getGeneratedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "USER : " + zReadResponse.getData().getCashier().getName(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "MANAGER : " + zReadResponse.getData().getDutyManager().getName(), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "DESCRIPTION                      VALUE", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "MACHINE NO.",
                            String.valueOf(zReadResponse.getData().getPosId())
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addPrinterSpace(1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "Gross Sales",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getGrossSales()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "Net Sales",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getNetSales()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "VATable SALES",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatable()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "VAT EXEMPT SALES",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExemptSales()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "12% VAT",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVat()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "NON VAT",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "SERVICE CHARGE",
                            "0.00"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



//                    zReadResponse.getResult().getPayment()

//                    JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");

                    addPrinterSpace(1);


                    if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                        TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                        };
                        List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());

                        List<PaymentPrintModel> paymentPrintModels = new ArrayList<>();

                        Double totalAdvancePayment = 0.00;
                        for (ZReadResponse.Payment pym : zReadResponse.getPayment()) {
                            if (pym.getIsAdvance() == 1) {
                                totalAdvancePayment += pym.getAmount();
                            }

                        }


                        for (FetchPaymentResponse.Result payment : paymentTypeList) {

                            Double value = 0.00;
                            String isAdvance = "0";

                            for (ZReadResponse.Payment pym : zReadResponse.getPayment()) {
                                if (pym.getPaymentDescription().equalsIgnoreCase(payment.getPaymentType())) {
                                    value = Double.valueOf(pym.getAmount());
                                    isAdvance = String.valueOf(pym.getIsAdvance());
                                    break;
                                }
                            }
                            if (payment.getPaymentType().equalsIgnoreCase("cash") || payment.getPaymentType().equalsIgnoreCase("card")) {

                                if (isAdvance.equalsIgnoreCase("1")) {
                                    paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            payment.getPaymentType(),
                                            "0.00"
                                            ,
                                            40,
                                            2),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {
                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            payment.getPaymentType() + " Sales",
                                            String.valueOf(value)
                                            ,
                                            40,
                                            2),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                                    if (payment.getPaymentType().equalsIgnoreCase("card")) {
                                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                                "Deposit Sales",
                                                String.valueOf(totalAdvancePayment)
                                                ,
                                                40,
                                                2),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }
                                }

                            } else {

                                if (value > 0) {
                                    if (isAdvance.equalsIgnoreCase("1")) {

                                        paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                    } else {
                                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                                payment.getPaymentType(),
                                                String.valueOf(value)
                                                ,
                                                40,
                                                2),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }
                                }
                            }
                        }
                    }

                    addPrinterSpace(1);
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "CASH OUT",
                            "0.00"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "REFUND",
                            "0.00"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "Void",
                            returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVoidAmount()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);
                    if (zReadResponse.getDiscount().size() > 0) {
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "DISCOUNT LIST",
                                ""
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        TypeToken<List<FetchDiscountSpecialResponse.Result>> discToken = new TypeToken<List<FetchDiscountSpecialResponse.Result>>() {};
                        List<FetchDiscountSpecialResponse.Result> discountDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.DISCOUNT_SPECIAL_JSON), discToken.getType());

                        if (discountDetails != null) {
                            for (FetchDiscountSpecialResponse.Result d : discountDetails) {
                                Integer count = 0;
                                Double amount = 0.00;
                                for (ZReadResponse.Discount disc : zReadResponse.getDiscount()) {
                                    if (disc.getIsSpecial() == 1) {
                                        if (d.getId() == disc.getDiscountTypeId()) {
                                            amount = disc.getDiscountAmount();
                                            count = disc.getCount();
                                            break;
                                        }
                                    }
                                }

                                if (d.getIsSpecial() == 1) {
                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            d.getDiscountCard(),
                                            returnWithTwoDecimal(String.valueOf(amount))
                                            ,
                                            40,
                                            2),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            d.getDiscountCard() + "(COUNT)",
                                            String.valueOf(count)
                                            ,
                                            40,
                                            2),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }

                            }
                        }



                        int otherDiscCount = 0;
                        double otherDiscAmount = 0.00;


                        for (ZReadResponse.Discount disc : zReadResponse.getDiscount()) {
                            if (disc.getIsSpecial() == 0) {
                                otherDiscAmount+= disc.getDiscountAmount();
                                otherDiscCount+= disc.getCount();
                            }
                        }

                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "OTHERS(AMOUNT)",
                                String.valueOf(otherDiscAmount)
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "OTHERS(COUNT)",
                                String.valueOf(otherDiscCount)
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);





//                        if (otherDiscCount > 0) {
//                            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                                    "OTHERS",
//                                    String.valueOf(otherDiscAmount)
//                                    ,
//                                    40,
//                                    2),
//                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                                    "OTHERS" + "(COUNT)",
//                                    String.valueOf(otherDiscCount)
//                                    ,
//                                    40,
//                                    2),
//                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//                        }

                    } else {

                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "SENIOR CITIZEN",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "SENIOR CITIZEN" + "(COUNT)",
                                "0"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "PWD",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "PWD" + "(COUNT)",
                                "0"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "OTHERS",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                "OTHERS" + "(COUNT)",
                                "0"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }

                    addPrinterSpace(1);

                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "BEGINNING TRANS",
                            zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : "NA"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "ENDING TRANS",
                            zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : "NA"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "NEW GRAND TOTAL",
                            String.valueOf(zReadResponse.getNewGrandTotal())
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            "OLD GRAND TOTAL",
                            String.valueOf(zReadResponse.getOldGrandTotal())
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                    addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);


                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
                            "Z-READ NO",
                            String.valueOf(zReadResponse.getCount())
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "------ END OF REPORT ------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);





                } else {
                    Toast.makeText(getApplicationContext(), "ZREAD IS NULL", Toast.LENGTH_SHORT).show();
                }

                 break;

            case "REXREADING":
                try {
                    JSONObject jsonObject = new JSONObject(printModel.getData());
                    JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                    JSONObject cashierDataObject = jsonObject.getJSONObject("data").getJSONObject("cashier");
                    JSONObject dutyManager = jsonObject.getJSONObject("data").getJSONObject("duty_manager");
                    if (dataJsonObject != null) {
//                        Log.d("TESXXXXTDATA", jsonObject.toString());
                        addTextToPrinter(SPrinter.getPrinter(), "X READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "POSTING DATE: " + dataJsonObject.getString("cut_off_date"), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "SHIFT : " + (dataJsonObject.getString("shift_no") != null ? dataJsonObject.getString("shift_no") : " NA"), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "USER : " + cashierDataObject.getString("name"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "MANAGER : " + dutyManager.getString("name"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), "DESCRIPTION                      VALUE", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "MACHINE NO.",
                                dataJsonObject.getString("pos_id")
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addPrinterSpace(1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "Gross Sales",
                                returnWithTwoDecimal(dataJsonObject.getString("gross_sales"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "Net Sales",
                                returnWithTwoDecimal(dataJsonObject.getString("net_sales"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addPrinterSpace(1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "VATable Sales",
                                returnWithTwoDecimal(dataJsonObject.getString("vatable"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "VAT EXEMPT SALES",
                                returnWithTwoDecimal(dataJsonObject.getString("vat_exempt_sales"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "12% VAT",
                                returnWithTwoDecimal(dataJsonObject.getString("vat"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "NON VAT",
                                returnWithTwoDecimal(dataJsonObject.getString("vat_exempt"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "SERVICE CHARGE",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    }


                    JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");

                    addPrinterSpace(1);


                    if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                        TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                        };
                        List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());
//                        Log.d("TESZZ", String.valueOf(paymentTypeList.size()));


                        Double totalAdvancePayment = 0.00;
                        for (int i = 0; i < paymentJsonArray.length(); i++) {
                            JSONObject temp = paymentJsonArray.getJSONObject(i);
                            if (temp.getString("is_advance").equalsIgnoreCase("1") || temp.getString("is_advance").equalsIgnoreCase("1.0")) {
                                totalAdvancePayment += Double.valueOf(temp.getString("amount"));
                            }
                        }




                        List<PaymentPrintModel> paymentPrintModels = new ArrayList<>();
                        for (FetchPaymentResponse.Result payment : paymentTypeList) {

                            Double value = 0.00;
                            String isAdvance = "0";
                            for (int i = 0; i < paymentJsonArray.length(); i++) {
                                JSONObject temp = paymentJsonArray.getJSONObject(i);
                                if (temp.getString("payment_description").equalsIgnoreCase(payment.getPaymentType())) {
                                    value = Double.valueOf(temp.getString("amount"));
                                    isAdvance = temp.getString("is_advance");
                                    break;
                                }
                            }




                            if (payment.getPaymentType().equalsIgnoreCase("cash") || payment.getPaymentType().equalsIgnoreCase("card")) {

//                                Log.d("TEKTEK", payment.getPaymentType() + " - " + String.valueOf(isAdvance));
                                if (isAdvance.equalsIgnoreCase("1")) {

                                    paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));


                                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                            payment.getPaymentType(),
                                            "0.00"
                                            ,
                                            40,
                                            2),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {


                                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                            payment.getPaymentType() + " Sales",
                                            String.valueOf(value)
                                            ,
                                            40,
                                            2),
                                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                                    if (payment.getPaymentType().equalsIgnoreCase("card")) {
                                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                                "DEPOSIT SALES",
                                                String.valueOf(totalAdvancePayment)
                                                ,
                                                40,
                                                2),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }
                                }

                            } else {

                                if (value > 0) {
                                    if (isAdvance.equalsIgnoreCase("1")) {

                                        paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                    } else {
                                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                                payment.getPaymentType(),
                                                String.valueOf(value)
                                                ,
                                                40,
                                                2),
                                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                    }
                                }
                            }
                        }

                    }

                    addPrinterSpace(1);
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                            "CASH OUT",
                            "0.00"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                            "REFUND",
                            "0.00"
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                            "Void",
                            returnWithTwoDecimal(String.valueOf(dataJsonObject.get("void_amount")))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);




                    JSONArray discountJsonArray = jsonObject.getJSONArray("discount");
                    addPrinterSpace(1);
                    if (discountJsonArray.length() > 0) {
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "DISCOUNT LIST",
                                ""
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);



                        TypeToken<List<FetchDiscountSpecialResponse.Result>> discToken = new TypeToken<List<FetchDiscountSpecialResponse.Result>>() {};
                        List<FetchDiscountSpecialResponse.Result> discountDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.DISCOUNT_SPECIAL_JSON), discToken.getType());

                        if (discountDetails != null) {
                            for (FetchDiscountSpecialResponse.Result d : discountDetails) {
                                Integer count = 0;
                                Double amount = 0.00;

                                if (discountJsonArray.length() > 0) {
                                    for (int i = 0; i < discountJsonArray.length(); i++) {
                                        JSONObject temp = discountJsonArray.getJSONObject(i);
                                        if (temp.getString("is_special").equalsIgnoreCase("1") || temp.getString("is_special").equalsIgnoreCase("1.0")) {
                                            amount = Double.valueOf(temp.getString("discount_amount"));
                                            count = Integer.valueOf(temp.getString("count"));
                                            break;
                                        }
                                    }
                                }


                                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                        d.getDiscountCard(),
                                        String.valueOf(amount)
                                        ,
                                        40,
                                        2),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                        d.getDiscountCard() + "(COUNT)",
                                        String.valueOf(count)
                                        ,
                                        40,
                                        2),
                                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                            }
                        }



                        int otherDiscCount = 0;
                        double otherDiscAmount = 0.00;

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "OTHERS",
                                String.valueOf(otherDiscAmount)
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "OTHERS" + "(COUNT)",
                                String.valueOf(otherDiscCount)
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    } else {

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "SENIOR CITIZEN",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "SENIOR CITIZEN" + "(COUNT)",
                                "0"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "PWD",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "PWD" + "(COUNT)",
                                "0"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "OTHERS",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "OTHERS" + "(COUNT)",
                                "0"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    }

                    addPrinterSpace(1);

                    addTextToPrinter(SPrinter.getPrinter(), "------ END OF REPORT ------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);





                    //short over
                    SPrinter.getPrinter().addCut(Printer.CUT_FEED);

                    addTextToPrinter(SPrinter.getPrinter(), "SHORT OVER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                    addPrinterSpace(1);
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                            "SHORT / OVER",
                            String.valueOf(jsonObject.getString("short_over"))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    addPrinterSpace(1);
                    addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                    addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                    addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);



                } catch (JSONException e) {
                    Log.d("ERROR", e.getMessage());
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }


                //separate short over




                break;


            case "XREADING": //USE REXREADING
                /* DEPRECATED
                try {
                    JSONObject jsonObject = new JSONObject(printModel.getData());
                    JSONObject dataJsonObject = jsonObject.getJSONObject("nameValuePairs").getJSONObject("data").getJSONObject("nameValuePairs");
                    JSONObject cashierDataObject = jsonObject.getJSONObject("nameValuePairs").getJSONObject("data").getJSONObject("nameValuePairs").getJSONObject("cashier");
                    JSONObject dutyManager = jsonObject.getJSONObject("nameValuePairs").getJSONObject("data").getJSONObject("nameValuePairs").getJSONObject("duty_manager");
                    if (dataJsonObject != null) {
                        Log.d("TESXXXXTDATA", jsonObject.toString());
                        addTextToPrinter(SPrinter.getPrinter(), "X READING", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "POSTING DATE: " + convertDateToReadableDate(dataJsonObject.getString("cut_off_date")), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "SHIFT : " + dataJsonObject.getString("shift_no"), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "USER : " + cashierDataObject.getString("name"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "MANAGER : " + dutyManager.getString("name"), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                        addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), "DESCRIPTION                      VALUE", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "TERMINAL NO.",
                                dataJsonObject.getString("pos_id")
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                        addPrinterSpace(1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "GROSS SALES",
                                returnWithTwoDecimal(dataJsonObject.getString("gross_sales"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "NET SALES",
                                returnWithTwoDecimal(dataJsonObject.getString("net_sales"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addPrinterSpace(1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "VATable SALES",
                                returnWithTwoDecimal(dataJsonObject.getString("vatable"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "VAT EXEMPT SALES",
                                returnWithTwoDecimal(dataJsonObject.getString("vat_exempt_sales"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "12% VAT",
                                returnWithTwoDecimal(dataJsonObject.getString("vat"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "NON VAT",
                                returnWithTwoDecimal(dataJsonObject.getString("vat_exempt"))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                "SERVICE CHARGE",
                                "0.00"
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    }
                    JSONArray paymentJsonArray = jsonObject.getJSONObject("nameValuePairs").getJSONArray("payment");
                    if (paymentJsonArray.length() > 0) {
                        Log.d("TESTDATA", paymentJsonArray.toString());
//                        for (int i = 0; i < paymentJsonArray.length(); i++) {
//                            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                                    paymentJsonArray.getJSONArray(1).getString(),
//                                    "0.00"
//                                    ,
//                                    40,
//                                    2),
//                                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                        }
                    }

                    JSONArray discountJsonArray = jsonObject.getJSONObject("nameValuePairs").getJSONArray("discount");
                    if (discountJsonArray.length() > 0) {

                    }
                } catch (JSONException e) {
                    Log.d("ERRORxXXx", e.getMessage());
                }
                */
                break;
            case "SWITCH_ROOM":

                SwitchRoomPrintModel switchRoomPrintModel = GsonHelper.getGson().fromJson(printModel.getData(), SwitchRoomPrintModel.class);

                addTextToPrinter(SPrinter.getPrinter(), "SWITCH ROOM SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), "FROM : "+switchRoomPrintModel.getFromRoomNumber() +"(" + switchRoomPrintModel.getFromRoomType() + ")", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(SPrinter.getPrinter(), "SWITCHED TO : " +switchRoomPrintModel.getToRoomNumber() + "(" + switchRoomPrintModel.getToRoomType() + ")", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(SPrinter.getPrinter(), "CASHIER : " + getUserInfo(switchRoomPrintModel.getUserId()), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(SPrinter.getPrinter(), "CHECK IN TIME : " + convertDateToReadableDate(switchRoomPrintModel.getCheckInTime()), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addPrinterSpace(1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                break;
            case "PRINT_RECEIPT":
                willExecutGlobalPrint = false;
                new CheckOutAsync(printModel, MainActivity.this).execute();

                break;
            case "DEPOSIT":
                TypeToken<List<PostedPaymentsModel>> depositToken = new TypeToken<List<PostedPaymentsModel>>() {};
                List<PostedPaymentsModel> depositDetails = GsonHelper.getGson().fromJson(printModel.getData(), depositToken.getType());


                addTextToPrinter(SPrinter.getPrinter(), "DEPOSIT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);


                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "MACHINE NO",
                        SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_ID),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "ROOM TYPE",
                        printModel.getRoomType(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "TYPE                        Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "---------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                Double total = 0.00;
                for (PostedPaymentsModel ppm : depositDetails) {
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                            ppm.getPayment_description(),
                            ppm.getAmount(),
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    total += Double.valueOf(ppm.getAmount());
                }


                addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(total), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);


                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);




                break;
            case "SOA-TO":
                PrintSoaResponse.Result toList = GsonHelper.getGson().fromJson(printModel.getData(), PrintSoaResponse.Result.class)
                        ;
//
//
                addTextToPrinter(SPrinter.getPrinter(), toList.getCreatedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "MACHINE NO",
                        SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_ID),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//
                addTextToPrinter(SPrinter.getPrinter(), "SOA-TAKE OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description             Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                for (PrintSoaResponse.SoaToPost soaTrans : toList.getToPostList()) {

                    String qty = "";

                    qty += soaTrans.getQty();
                    if (String.valueOf(soaTrans.getQty()).length() < 4) {
                        for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                            qty += " ";
                        }
                    }
                    String item = "";
                    item =soaTrans.getToProduct().getProductInitial();

                    if (soaTrans.getVoidd().equalsIgnoreCase("0")) {
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                qty+ " "+item,
                                String.valueOf(Double.valueOf(soaTrans.getPrice()) * Integer.valueOf(soaTrans.getQty()))
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }
                }
//
                addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   VAT EXEMPT",
                        returnWithTwoDecimal(String.valueOf(toList.getVatExempt())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   DISCOUNT",
                        returnWithTwoDecimal(String.valueOf(toList.getDiscount())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   ADVANCED DEPOSIT",
                        returnWithTwoDecimal(String.valueOf(toList.getAdvance())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "SUB TOTAL",
                        returnWithTwoDecimal(String.valueOf(toList.getTotal())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "AMOUNT DUE",
                        returnWithTwoDecimal(String.valueOf((Double.valueOf(toList.getTotal())) - (Double.valueOf(toList.getDiscount()) + Double.valueOf(toList.getAdvance()) + Double.valueOf(toList.getVatExempt())))),
                        40,
                        2)
                        ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);
//
                addPrinterSpace(1);
//
                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "SOA NO:",
                        toList.getSoaCount(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
                addPrinterSpace(1);

                if (toList.getCustomer() != null) {
                    if (!toList.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {
                        addTextToPrinter(SPrinter.getPrinter(), "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), toList.getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), toList.getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), toList.getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                    }
                }



                addPrinterSpace(1);
//                addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);



                break;


            case "CHECKIN":

                TypeToken<List<CheckInResponse.Booked>> checkInToken = new TypeToken<List<CheckInResponse.Booked>>() {};
                List<CheckInResponse.Booked> checkinDetails = GsonHelper.getGson().fromJson(printModel.getData(), checkInToken.getType());
                addTextToPrinter(SPrinter.getPrinter(), "CHECK IN SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "ROOM TYPE",
                        checkinDetails.get(0).getRoomType().toUpperCase(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreater(
                        "RATE DESC.",
                        checkinDetails.get(0).getRoomRate().toUpperCase(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "STARTING RATE",
                        roomRatePrice(String.valueOf(checkinDetails.get(0).getRoomRatePriceId())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "DATE / TIME",
                        convertDateToReadableDate(checkinDetails.get(0).getCreatedAt() != null ? checkinDetails.get(0).getCreatedAt() : "NA"),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "VEHICLE TYPE",
                        fetchVehicleFromId(String.valueOf(checkinDetails.get(0).getVehicleId() != null ? checkinDetails.get(0).getVehicleId() : "NA")).toUpperCase(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "CAR MAKE",
                        checkinDetails.get(0).getCar().getCarMake() != null ? checkinDetails.get(0).getCar().getCarMake().toUpperCase() : "NA",
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "PLATE NUMBER",
                        checkinDetails.get(0).getPlateNo() != null ? checkinDetails.get(0).getPlateNo().toUpperCase() : "NA",
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(SPrinter.getPrinter(),
                        "ROOM BOY:  " + getUserInfo(String.valueOf(checkinDetails.get(0).getUserId())),Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "REMARKS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "PENDING TO DO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                break;
            case "VOID":

                TypeToken<List<VoidProductModel>> voidToken = new TypeToken<List<VoidProductModel>>() {};
                List<VoidProductModel> voidList = GsonHelper.getGson().fromJson(printModel.getData(), voidToken.getType());

                addTextToPrinter(SPrinter.getPrinter(), "VOID SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description              Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                Double voidTotalAmount = 0.00;
                for (VoidProductModel vpm : voidList) {

                    String qty = "";

                    qty += vpm.getQuantity();
                    if (vpm.getQuantity().length() < 4) {
                        for (int i = 0; i < 4 - vpm.getQuantity().length(); i++) {
                            qty += " ";

                        }
                    }

                    voidTotalAmount += Double.valueOf(vpm.getPrice());
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(qty+ vpm.getName(), vpm.getPrice(), 40, 2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                }

                addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(voidTotalAmount), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                break;
            case "SOA-ROOM":
                TypeToken<List<PrintSoaResponse.Booked>> bookedToken = new TypeToken<List<PrintSoaResponse.Booked>>() {};
                List<PrintSoaResponse.Booked> bookedList = GsonHelper.getGson().fromJson(printModel.getData(), bookedToken.getType());

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                        "CASHIER",
                        getUserInfo(String.valueOf(bookedList.get(0).getUserId())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                        "MACHINE NO",
                        SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_ID),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                        "CHECKED-IN",
                        bookedList.get(0).getCheckIn(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                        "EXP. CHECK-OUT",
                        bookedList.get(0).getExpectedCheckOut(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterLr(
                        "DURATION",
                        getDuration(bookedList.get(0).getCheckIn()),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                Double soaTotal = 0.00;
                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description             Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                for (PrintSoaResponse.Post soaTrans : bookedList.get(0).getTransaction().getPost()) {

                    String qty = "";

                    qty += soaTrans.getQty();
                    if (String.valueOf(soaTrans.getQty()).length() < 4) {
                        for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                            qty += " ";
                        }
                    }
                    String item = "";
                    if (soaTrans.getProduct() != null) {
                        item =soaTrans.getProduct().getProductInitial();
                    } else {
                        item = soaTrans.getRoomRate().toString();
                    }

                    if (soaTrans.getVoid() == 0) {
                        addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                qty+ " "+item,
                                String.valueOf(soaTrans.getPrice() * soaTrans.getQty())
                                ,
                                40,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }
                }

                if (bookedList.get(0).getTransaction().getOtHours() > 0) {
                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                            String.valueOf(bookedList.get(0).getTransaction().getOtHours()) + " " + "OT HOURS",
                            returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getOtAmount()))
                            ,
                            40,
                            2),
                            Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }




                addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "   VAT EXEMPT",
                        returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getVatExempt())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "   DISCOUNT",
                        returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getDiscount())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "   ADVANCED DEPOSIT",
                        returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getAdvance())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "SUB TOTAL",
                        returnWithTwoDecimal(String.valueOf(bookedList.get(0).getTransaction().getTotal())),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "AMOUNT DUE",
                        returnWithTwoDecimal(String.valueOf((
                                bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount())
                                - (bookedList.get(0).getTransaction().getDiscount() + bookedList.get(0).getTransaction().getAdvance() + bookedList.get(0).getTransaction().getVatExempt()))),
                        40,
                        2)
                        ,Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,2,1);

                if (bookedList.get(0).getTransaction().getDiscounts().size() > 0) {

                    addPrinterSpace(2);

                    addTextToPrinter(SPrinter.getPrinter(), "DISCOUNT LIST", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                    for (PrintSoaResponse.Discounts d : bookedList.get(0).getTransaction().getDiscounts()) {
//                        addTextToPrinter(SPrinter.getPrinter(), d.getDiscountType(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        if (d.getDiscountTypeId().equalsIgnoreCase("0")) { //MANUAL
                            addTextToPrinter(SPrinter.getPrinter(), "    " + d.getDiscountReason().getDiscountReason(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                        } else {
                            if (d.getInfo() != null) {
                                if (d.getInfo().getCardNo().isEmpty()) {

                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            d.getDiscountType(),
                                            d.getInfo().getName().toUpperCase(),
                                            40,
                                            2)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                                    addTextToPrinter(SPrinter.getPrinter(), "    " +d.getInfo().getName().toUpperCase(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                } else {

                                    addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                                            d.getDiscountType(),
                                            d.getInfo().getCardNo().toUpperCase(),
                                            40,
                                            2)
                                            ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                                    addTextToPrinter(SPrinter.getPrinter(), "    " +d.getInfo().getCardNo().toUpperCase(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                                }
                            }
                        }

                    }

                }


                addPrinterSpace(1);

                if (bookedList.get(0).getCustomer() != null) {
                    if (!bookedList.get(0).getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !bookedList.get(0).getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {
                        addTextToPrinter(SPrinter.getPrinter(), "THIS RECEIPT IS ISSUED TO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getCustomer(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getAddress(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                        addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCustomer().getTin(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                    }
                }



                addPrinterSpace(1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumnsRightGreaterTr(
                        "SOA NO:",
                        bookedList.get(0).getTransaction().getSoaCount(),
                        40,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addPrinterSpace(1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);


//                addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);


                break;
            case "FO":
                Double totalAmount = 0.00;
                addTextToPrinter(SPrinter.getPrinter(), "FO ORDER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description               Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "----------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                TypeToken<List<AddRateProductModel>> token = new TypeToken<List<AddRateProductModel>>() {};
                List<AddRateProductModel> aprm = GsonHelper.getGson().fromJson(printModel.getData(), token.getType());
                for (AddRateProductModel r : aprm) {
                    String qty = "";

                    qty += r.getQty();
                    if (r.getQty().length() < 4) {
                        for (int i = 0; i < 4 - r.getQty().length(); i++) {
                            qty += " ";
                        }
                    }

                    totalAmount += Double.valueOf(r.getPrice());
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(qty+ r.getProduct_initial(), r.getPrice(), 40, 2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }

                addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(totalAmount), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "REMARKS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), "PENDING TO DO", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                break;
        }


        try {

            if (willExecutGlobalPrint) {
                SPrinter.getPrinter().addCut(Printer.CUT_FEED);

                if (SPrinter.getPrinter().getStatus().getConnection() == 1) {
                    SPrinter.getPrinter().sendData(Printer.PARAM_DEFAULT);
                    SPrinter.getPrinter().clearCommandBuffer();
                }
            }



//            SPrinter.getPrinter().endTransaction();
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    private String twoColumns(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
        String finalString = "";
        float countPerColumn = maxTextCountPerLine / columns;

        double column1Length =  Math.ceil(partOne.length() / (countPerColumn + 4));
        double column2Length =  Math.ceil(partTwo.length() / countPerColumn);
        for (int i = 0; i < column1Length; i++) {
            if (i == column1Length - 1) {
                String tmpLast = partOne.substring(
                        i * (int)countPerColumn);
                finalString += partOne.substring(
                        i * (int)countPerColumn);
                for (int j = 0; j < (countPerColumn -tmpLast.length())+ 4; j++) {
                    finalString += " ";
                }
            } else {
                finalString += partOne.substring(
                        i * (int)countPerColumn,
                        (i+1) * (int)countPerColumn) + "\n";
            }
        }

        for (int i = 0; i < column2Length; i++) {
            if (i == column2Length - 1) {
                String tmpLast = partTwo.substring(
                        i * (int)countPerColumn);

                for (int j = 0; j < (countPerColumn - tmpLast.length()) - 7; j++) {
                    finalString += " ";
                }

                finalString += tmpLast;

            } else {
                finalString += partTwo.substring(
                        i * (int)countPerColumn,
                        (i+1) * (int)countPerColumn) + "\n";
            }
        }
        return finalString;
    }

    private String twoColumnsRightGreater(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
        String finalString = "";
//        float countPerColumn = maxTextCountPerLine / columns;
        float column1 = 11;
        float column2 = 28;
//        if (partTwo.length() >= 22) {
//            partTwo = partTwo.substring(0, 22);
//        }
        double column1Length =  Math.ceil(partOne.length() / (column1));
        double column2Length =  Math.ceil(partTwo.length() / column2);
        for (int i = 0; i < column1Length; i++) {
            if (i == column1Length - 1) {
                String tmpLast = partOne.substring(
                        i * (int)column1);
                finalString += partOne.substring(
                        i * (int)column1);
                for (int j = 0; j < (column1 -tmpLast.length()); j++) {
                    finalString += " ";
                }
            } else {
                finalString += partOne.substring(
                        i * (int)column1,
                        (i+1) * (int)column1) + "\n";
            }
        }

        for (int i = 0; i < column2Length; i++) {
            if (i == column2Length - 1) {
                String tmpLast = partTwo.substring(
                        i * (int)column2);

                for (int j = 0; j < (column2 - tmpLast.length()); j++) {
                    finalString += " ";
                }

                finalString += tmpLast;

            } else {
                finalString += partTwo.substring(
                        i * (int)column2,
                        (i+1) * (int)column2) + "\n";
            }
        }
        return finalString;
    }

    private String twoColumnsRightGreaterTr(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
        String finalString = "";
        float column1 = 20;
        float column2 = 20;
        if (partOne.length() >= 20) {
            finalString += partOne.substring(0, 20);
        } else {
            finalString += partOne;

            for (int i = 0; i < column1 - partOne.length(); i++) {
                finalString += " ";
            }
        }

        if (partTwo.length() >= 20) {
            finalString += partTwo.substring(0, 20);
        } else {

            for (int i = 0; i < column2 - partTwo.length(); i++) {
                finalString += " ";
            }

            finalString += partTwo;
        }


        return finalString;
    }

    private String twoColumnsRightGreaterLr(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
        String finalString = "";
        float column1 = 14;
        float column2 = 26;
        if (partOne.length() >= column1) {
            finalString += partOne.substring(0, 14);
        } else {
            finalString += partOne;

            for (int i = 0; i < column1 - partOne.length(); i++) {
                finalString += " ";
            }
        }

        if (partTwo.length() >= column2) {
            finalString += partTwo.substring(0, 26);
        } else {

            for (int i = 0; i < column2 - partTwo.length(); i++) {
                finalString += " ";
            }

            finalString += partTwo;
        }


        return finalString;
    }



    private void addFooterToPrinter() {
        if (SPrinter.getPrinter() != null) {
            addTextToPrinter(SPrinter.getPrinter(), "THiS IS NOT AN OFFICIAL RECEIPT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Thank you come again", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "----- SYSTEM PROVIDER DETAILS -----", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Provider : Nerdvana", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Address : test address", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "TIN: 432540435435", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "ACCRE No. : 1231231231231231231234324234", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Date issued : 01/01/2001", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Valid until : 01/01/2090", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "PTU No. : FPU 42434242424242423", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
            addTextToPrinter(SPrinter.getPrinter(), "THIS INVOICE RECEIPT SHALL BE VALID FOR FIVE(5) YEARS FROM THE DATE OF THE PERMIT TO USE", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addPrinterSpace(1);
        }



    }


    private void addTextToPrinter(Printer printer, String text,
                                  int isBold, int isUnderlined,
                                  int alignment, int feedLine,
                                  int textSizeWidth, int textSizeHeight) {

        if (printer != null) {
            StringBuilder textData = new StringBuilder();
            try {
                printer.addTextSize(textSizeWidth, textSizeHeight);
                printer.addTextAlign(alignment);
                printer.addTextStyle(Printer.PARAM_DEFAULT, isUnderlined, isBold, Printer.PARAM_DEFAULT);
                printer.addTextSmooth(Printer.TRUE);
                printer.addText(textData.toString());
                textData.append(text);
                printer.addText(textData.toString());
                textData.delete(0, textData.length());
                printer.addFeedLine(feedLine);
            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPrinter() {
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_PORT))) {
            new SPrinter(
                    Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_LANGUAGE)),
                    getApplicationContext());

            try {
                if (SPrinter.getPrinter() != null) {
                    SPrinter.getPrinter().connect(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_PORT), Printer.PARAM_DEFAULT);
//                    SPrinter.getPrinter().beginTransaction();
                } else {
                    Toast.makeText(MainActivity.this, "No Printer", Toast.LENGTH_SHORT).show();
                }

            } catch (Epos2Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void addPrinterSpace(int count) {
        try {
            SPrinter.getPrinter().addFeedLine(count);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    public interface Loading {
        void show(boolean willShow);
    }

    @Subscribe
    public void onReceiveLoading(ProgressBarModel progressBarModel) {
        if (!progressBarModel.isWillStart()) {
            if (dialogProgressBar != null) {
                if (dialogProgressBar.isShowing()) dialogProgressBar.dismiss();
            }
        }

        Utils.showDialogMessage(MainActivity.this, progressBarModel.getMessage(), "ERROR");
    }


    private void fetchCompanyUserRequest() {
        BusProvider.getInstance().post(new FetchCompanyUserRequest());
    }

    @Subscribe
    public void fetchCompanyUserRequest(FetchCompanyUserResponse fetchCompanyUserResponse) {
        if (fetchCompanyUserResponse.getResult().size() > 0) {
            SharedPreferenceManager.saveString(getApplicationContext(),
                    GsonHelper.getGson().toJson(fetchCompanyUserResponse.getResult()),
                    ApplicationConstants.COMPANY_USER);
        }

//        Toast.makeText(getApplicationContext(), "FETCH COMPANY USER SAVED", Toast.LENGTH_SHORT).show();

    }

    private String fetchVehicleFromId(String vehicleId) {
        TypeToken<List<FetchVehicleResponse.Result>> vehicleToken = new TypeToken<List<FetchVehicleResponse.Result>>() {};
        List<FetchVehicleResponse.Result> vehicleList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.VEHICLE_JSON), vehicleToken.getType());
        String result = "N/A";

        for (FetchVehicleResponse.Result res : vehicleList) {
            if (String.valueOf(res.getId()).equals(vehicleId)) {
                result = res.getVehicle();
                break;
            }
        }

        return result;
    }

    private String roomRatePrice(String roomRatePriceId) {
        String rateDisplay = "";

        if (selected != null) {
            for (int i = 0; i < selected.getPrice().size(); i++) {
                if (roomRatePriceId.equalsIgnoreCase(String.valueOf(selected.getPrice().get(i).getRoomRatePriceId()))) {
                    rateDisplay = String.valueOf(selected.getPrice().get(i).getRatePrice().getAmount());
                    break;
                }
            }
        }

        return rateDisplay;
    }

    private String convertDateToReadableDate(String createdAt) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String res = "";
        try {
            DateTime jodatime = dtf.parseDateTime(createdAt);
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM d h:m a");


            res = dtfOut.print(jodatime);
        } catch (Exception e) {
            res  = "NA";
        }


        return res.toUpperCase();

    }

    private String getUserInfo(String userId) {

        TypeToken<List<FetchCompanyUserResponse.Result>> companyUser = new TypeToken<List<FetchCompanyUserResponse.Result>>() {};
        List<FetchCompanyUserResponse.Result> vehicleList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.COMPANY_USER), companyUser.getType());
        String result = "N/A";

        for (FetchCompanyUserResponse.Result res : vehicleList) {
            if (String.valueOf(res.getUserId()).equals(userId)) {
                result = res.getName();
                break;
            }
        }

        return result;
    }

    private String returnWithTwoDecimal(String amount) {
        String finalValue = "";
        if (amount.contains(".")) {
            String[] tempArray = amount.split("\\.");
            if (tempArray[1].length() > 2) {
                finalValue = tempArray[0] + "." + tempArray[1].substring(0,2);
            } else {
                finalValue = tempArray[0] + "." + tempArray[1];
            }
        } else {
            finalValue = amount;
        }

        return finalValue;

    }

    public static String formatSeconds(long timeInSeconds)
    {
        long hours = timeInSeconds / 3600;
        long secondsLeft = timeInSeconds - hours * 3600;
        long minutes = secondsLeft / 60;
        long seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds ;

        return formattedTime;
    }

    private String  getDuration(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime today = new DateTime();
        DateTime yesterday = formatter.parseDateTime(dateTime);
        Duration duration = new Duration(yesterday, today);
        return formatSeconds(duration.getStandardSeconds());
    }

    @Subscribe
    public void clickedButton(ButtonsModel clickedItem) {
        switch (clickedItem.getId()) {
            case 999: //rooms
                Intent roomSelectionIntent = new Intent(this, RoomsActivity.class);
                startActivityForResult(roomSelectionIntent, 10);
                break;
            case 998: //take order
                Intent takeOutIntent = new Intent(this, TakeOutActivity.class);
                startActivityForResult(takeOutIntent, 20);
                break;
            case 997: //logout
                if (timerIntent != null) {
                    stopService(timerIntent);
                }
                userModel.setLoggedIn(false);
                SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(userModel), ApplicationConstants.userSettings);
                CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
                finish();
                startActivity(new Intent(MainActivity.this, SetupActivity.class));
                break;
        }
    }

    private void checkSafeKeepingRequest() {
        BusProvider.getInstance().post(new CheckSafeKeepingRequest());
    }

    @Subscribe
    public void checkSafeKeeping(CheckSafeKeepingResponse checkSafeKeepingResponse) {
        Double safeKeepAmount = Double.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SAFEKEEPING_AMOUNT));
        if (checkSafeKeepingResponse.getResult().getUnCollected() >= safeKeepAmount) {
            CollectionDialog collectionDialog = new CollectionDialog(MainActivity.this, "SAFEKEEPING", false) {
                @Override
                public void printCashRecoData(String cashNRecoData) {

                }
            };
            if (!collectionDialog.isShowing()) collectionDialog.show();
        }
    }

    @Subscribe
    public void updateTime(TimerModel timerModel) {

        currentDateTime = timerModel.getTime();

        timer.setText(timerModel.getTime());

        user.setText(currentText + " SHIFT : " + timerModel.getShiftNumber());




    }

    private void fetchTimeRequest() {
        BusProvider.getInstance().post(new FetchTimeRequest());
    }

    @Subscribe
    public void fetchServerTime(FetchTimeResponse fetchTimeResponse) {
        if (fetchTimeResponse != null) {
            if (fetchTimeResponse.getTime() != null) {
                timerIntent = new Intent(this, TimerService.class);
                timerIntent.putExtra("start_time", fetchTimeResponse.getTime());
                timerIntent.putExtra("shift_info_array", SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SHIFT_INFO_ARRAY));
                startService(timerIntent);
            }
        }

    }



    private void saveDenominationPref() {

        if (TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON))) {
            FetchDenominationRequest fetchDenominationRequest = new FetchDenominationRequest();
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
            Call<FetchDenominationResponse> request = iUsers.fetchDenomination(fetchDenominationRequest.getMapValue());
            request.enqueue(new Callback<FetchDenominationResponse>() {
                @Override
                public void onResponse(Call<FetchDenominationResponse> call, Response<FetchDenominationResponse> response) {
                    SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(response.body().getResult()), ApplicationConstants.CASH_DENO_JSON);
                }

                @Override
                public void onFailure(Call<FetchDenominationResponse> call, Throwable t) {

                }
            });
        }
    }

    private void fixDenoPrint(List<CollectionFinalPostModel> myList) {

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON))) {
            TypeToken<List<FetchDenominationResponse.Result>> collectionToken = new TypeToken<List<FetchDenominationResponse.Result>>() {};
            List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON), collectionToken.getType());
            Double finalAmount = 0.00;
            for (FetchDenominationResponse.Result cfm : denoDetails) {
                String valueCount = "0";
                String valueAmount = "0.00";
                for (CollectionFinalPostModel c : myList) {
                    if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                        valueCount = c.getAmount();
                        valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                        break;
                    }
                }

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        String.format("%s  x %s", valueCount, cfm.getAmount()),
                        valueAmount
                        ,
                        40,
                        2),
                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                finalAmount += Double.valueOf(valueAmount);
            }

            addPrinterSpace(1);

            addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                    "CASH COUNT",
                    String.valueOf(finalAmount)
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                    "CASH OUT",
                    "0.00"
                    ,
                    40,
                    2),
                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

            addPrinterSpace(1);
            addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
            addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
            addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

        }

    }

    private void savePaymentTypePref() {

        if (TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON))) {
            FetchPaymentRequest fetchPaymentRequest = new FetchPaymentRequest();
            IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
            Call<FetchPaymentResponse> request = iUsers.sendFetchPaymentRequest(fetchPaymentRequest.getMapValue());
            request.enqueue(new Callback<FetchPaymentResponse>() {
                @Override
                public void onResponse(Call<FetchPaymentResponse> call, Response<FetchPaymentResponse> response) {
                    SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(response.body().getResult()), ApplicationConstants.PAYMENT_TYPE_JSON);
                }

                @Override
                public void onFailure(Call<FetchPaymentResponse> call, Throwable t) {

                }
            });
        }
    }

    private void fetchDiscountSpecialRequest() {
        if (TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.DISCOUNT_SPECIAL_JSON))) {
            BusProvider.getInstance().post(new FetchDiscountSpecialRequest());
        }

    }

    @Subscribe
    public void fetchDiscountSpecialRespone(FetchDiscountReasonResponse fetchDiscountReasonResponse) {
        SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(fetchDiscountReasonResponse.getResult()), ApplicationConstants.DISCOUNT_SPECIAL_JSON);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.isShiftPressed()) {
            Toast.makeText(getApplicationContext(), "KEY PRESSED", Toast.LENGTH_SHORT).show();
        }
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_J:
//                Toast.makeText(getApplicationContext(), "J IS PRESSED", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onKeyUp(keyCode, event);
//
//        }
        return super.onKeyUp(keyCode, event);
    }
}

