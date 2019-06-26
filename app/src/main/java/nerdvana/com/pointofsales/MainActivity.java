package nerdvana.com.pointofsales;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
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


import nerdvana.com.pointofsales.api_requests.BackOutGuestRequest;
import nerdvana.com.pointofsales.api_requests.BackupDatabaseRequest;
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
import nerdvana.com.pointofsales.api_requests.FetchOrderPendingRequest;
import nerdvana.com.pointofsales.api_requests.FetchPaymentRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomAreaRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.FetchTimeRequest;
import nerdvana.com.pointofsales.api_requests.FetchUserRequest;
import nerdvana.com.pointofsales.api_requests.FetchXReadingViaIdRequest;
import nerdvana.com.pointofsales.api_responses.BackOutGuestResponse;
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
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchPaymentResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchTimeResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchVehicleResponse;
import nerdvana.com.pointofsales.api_responses.FetchXReadingViaIdResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.api_responses.ZReadResponse;
import nerdvana.com.pointofsales.background.BackOutAsync;
import nerdvana.com.pointofsales.background.CashNReconcileAsync;
import nerdvana.com.pointofsales.background.ChangeWakeUpCallAsync;
import nerdvana.com.pointofsales.background.CheckInAsync;
import nerdvana.com.pointofsales.background.CheckOutAsync;
import nerdvana.com.pointofsales.background.CountUpTimer;
import nerdvana.com.pointofsales.background.DepositAsync;
import nerdvana.com.pointofsales.background.FetchOrderPendingAsync;
import nerdvana.com.pointofsales.background.FoAsync;
import nerdvana.com.pointofsales.background.FranchiseCheckoutAsync;
import nerdvana.com.pointofsales.background.IntransitAsync;
import nerdvana.com.pointofsales.background.PostVoidAsync;
import nerdvana.com.pointofsales.background.RoomStatusAsync;
import nerdvana.com.pointofsales.background.SafeKeepingAsync;
import nerdvana.com.pointofsales.background.ShortOverAsync;
import nerdvana.com.pointofsales.background.SoaRoomAsync;
import nerdvana.com.pointofsales.background.SoaToAsync;
import nerdvana.com.pointofsales.background.SwitchRoomAsync;
import nerdvana.com.pointofsales.background.VoidAsync;
import nerdvana.com.pointofsales.background.XReadAsync;
import nerdvana.com.pointofsales.background.ZReadAsync;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.dialogs.DialogProgressBar;
import nerdvana.com.pointofsales.dialogs.RoomListViewDialog;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ChangeThemeModel;
import nerdvana.com.pointofsales.model.ChangeWakeUpCallPrintModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.InfoModel;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintJobModel;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PreloginContract, View.OnClickListener {
    private CollectionDialog collectionDialog;
    private String currentText = "";
    public static String roomNumber;
    private Loading loadingInterface;
    private SelectionContract centralInterface;

    private AsyncFinishCallBack asyncFinishCallBack;

    private List<PrintJobModel> myPrintJobs;


    RoomListViewDialog roomListViewDialog;

    RoomTableModel selected;

    private LeftFrameFragment preLoginLeftFrameFragment;
    private RightFrameFragment preLoginRightFrameFragment;
    private nerdvana.com.pointofsales.postlogin.LeftFrameFragment postLoginLeftFrameFragment;
    private nerdvana.com.pointofsales.postlogin.RightFrameFragment postLoginRightFrameFragment;

    private ConstraintLayout mainContainerBg;


    android.support.v7.widget.Toolbar toolbar;


    private TextView timer;
    private Button logout;
    private Button showMap;
    private Button showTakeOuts;
    private TextView user;
    private TextView role;
    private CardView cardRole;

    private UserModel userModel;

    private View separator;
    private View separator2;

    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    private DialogProgressBar dialogProgressBar;
    private Switch toggleTheme;

    private Intent timerIntent;

    private String currentDateTime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainContainerBg = findViewById(R.id.mainContainerBg);
        separator = findViewById(R.id.separator);
        separator2 = findViewById(R.id.separator2);
        toggleTheme = findViewById(R.id.toggleTheme);

        if (!SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.THEME_SELECTED).isEmpty()) {
            if(SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("dark")) {
                toggleTheme.setChecked(true);
            }
        }
        toggleTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferenceManager.saveString(getApplicationContext(), isChecked ? "dark" : "light", ApplicationConstants.THEME_SELECTED);
                changeTheme();
            }
        });

        if (TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))) {
            SharedPreferenceManager.saveString(MainActivity.this, "32", ApplicationConstants.MAX_COLUMN_COUNT);
        }

        if (TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.DEFAULT_CURRENCY_VALUE))) {
            SharedPreferenceManager.saveString(MainActivity.this, "1", ApplicationConstants.DEFAULT_CURRENCY_VALUE);
        }

        myPrintJobs = new ArrayList<>();


//        SharedPreferenceManager.saveString(getApplicationContext(), "vc", ApplicationConstants.BRANCH);

        asyncFinishCallBack = new AsyncFinishCallBack() {
            @Override
            public void doneProcessing() {
                myPrintJobs.remove(0);

                if (myPrintJobs.size() > 0) {
                    runTask(myPrintJobs.get(0).getTaskName(), myPrintJobs.get(0).getAsyncTask());
                }
            }
        };

        saveDenominationPref();
        savePaymentTypePref();
        fetchDiscountSpecialRequest();
        saveBranchInfo();
        dialogProgressBar = new DialogProgressBar(MainActivity.this);
        dialogProgressBar.setCancelable(false);

        loadingInterface = new Loading() {
            @Override
            public void show(boolean willShow) {
//                if (willShow) {
//                    if (dialogProgressBar != null) {
//                        if (!dialogProgressBar.isShowing()) dialogProgressBar.show();
//                    }
//                } else {
//                    if (dialogProgressBar != null) {
//                        if (dialogProgressBar.isShowing()) dialogProgressBar.dismiss();
//                    }
//                }
            }
        };


        toolbar = findViewById(R.id.toolbar);
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

                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getCompanyInfo().getIsRoom()), ApplicationConstants.IS_SYSTEM_ROOM);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getCompanyInfo().getIsTable()), ApplicationConstants.IS_SYSTEM_TABLE);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getInfo().getPermitNo()), ApplicationConstants.BRANCH_PERMIT);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getInfo().getTinNo()), ApplicationConstants.TIN_NUMBER);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getAddress()), ApplicationConstants.BRANCH_ADDRESS);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getInfo().getRemarks()), ApplicationConstants.OR_INFO_DISPLAY);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getInfo().getTax()), ApplicationConstants.TAX_RATE);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getId()), ApplicationConstants.BRANCH_ID);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getBranchCode()), ApplicationConstants.BRANCH_CODE);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getTelephone()), ApplicationConstants.BRANCH_TELEPHONE);
                SharedPreferenceManager.saveString(MainActivity.this, String.valueOf(response.body().getResult().getBranchInfo().getInfo().getSafeKeepingAmount()), ApplicationConstants.SAFEKEEPING_AMOUNT);
                SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(String.valueOf(response.body().getResult().getBranchInfo().getShift())), ApplicationConstants.SHIFT_DETAILS);



                SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(response.body().getResult().getBranchInfo().getShift()), ApplicationConstants.SHIFT_INFO_ARRAY);

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
        role = findViewById(R.id.role);
        cardRole = findViewById(R.id.cardRole);
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
                user.setText(String.format("%s - %s", userModel.getUsername(), userModel.getUserGroup()) + ApplicationConstants.VERSION);
                currentText = String.format("%s", userModel.getUsername());
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

//        try {
//            SPrinter.getPrinter().disconnect();
//        } catch (Epos2Exception e) {
//            e.printStackTrace();
//        }
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
        if (canTransact()) {
            checkSafeKeepingRequest();
        }

        loadPrinter();

        changeTheme();
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
        //regionheader
        String[] address = SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.BRANCH_ADDRESS).split(" ");
        ArrayList<String> addressArray = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < address.length; i++) {
            if ((temp + " " + address[i]).length() < 40) {
                temp += " " +address[i];
                if (i == address.length - 1) {
                    addressArray.add(temp);
                }
            } else {
                addressArray.add(temp + " " + address[i]);
                temp = "";
            }
        }
        switch (printModel.getType()) {
            case "FRANCHISE_OR":
                willExecutGlobalPrint = false;
                addAsync(new FranchiseCheckoutAsync(printModel, MainActivity.this, userModel, asyncFinishCallBack), "franchise_or");
                break;
            case "IN_TRANSIT": //ignore header
                willExecutGlobalPrint = false;
                addAsync(new IntransitAsync(printModel, MainActivity.this, userModel,currentDateTime, asyncFinishCallBack), "intransit");
                break;
            case "POST_VOID": //ignore header
                willExecutGlobalPrint = false;
                addAsync(new PostVoidAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "postvoid");
                break;
            case "CHANGE_WAKE_UP_CALL": //done
                willExecutGlobalPrint = false;
                addAsync(new ChangeWakeUpCallAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "changewakeupcall");
                break;
            case "BACKOUT": //done
                willExecutGlobalPrint = false;
                addAsync(new BackOutAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "backout");
                break;
            case "SHORTOVER"://ignore
                willExecutGlobalPrint = false;
                addAsync(new ShortOverAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "shortover");
                break;
            case "CASHRECONCILE"://ignore
                willExecutGlobalPrint = false;
                addAsync(new CashNReconcileAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "cashreconcile");
                break;
            case "SAFEKEEPING"://ignore
                willExecutGlobalPrint = false;
                addAsync(new SafeKeepingAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "safekeeping");
                break;
            case "ZREAD"://ignore
                willExecutGlobalPrint = false;
                addAsync(new ZReadAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "zread");
                 break;
            case "REXREADING"://ignore
                willExecutGlobalPrint = false;
                addAsync(new XReadAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "xread");
                break;
            case "SWITCH_ROOM"://done
                willExecutGlobalPrint = false;
                addAsync(new SwitchRoomAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "switchroom");
                break;
            case "PRINT_RECEIPT"://done //checkout
                willExecutGlobalPrint = false;
                addAsync(new CheckOutAsync(printModel, MainActivity.this, userModel, asyncFinishCallBack), "checkout");
                break;
            case "DEPOSIT"://done
                willExecutGlobalPrint = false;
                addAsync(new DepositAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "deposit");
                break;
            case "SOA-TO"://done
                willExecutGlobalPrint = false;
                addAsync(new SoaToAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "soato");
                break;
            case "CHECKIN"://done
                willExecutGlobalPrint = false;
                addAsync(new CheckInAsync(printModel, MainActivity.this, userModel, currentDateTime, selected, asyncFinishCallBack), "checkin");
                break;
            case "VOID"://done
                willExecutGlobalPrint = false;
                addAsync(new VoidAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "voiditem");
                break;
            case "SOA-ROOM"://done
                willExecutGlobalPrint = false;
                addAsync(new SoaRoomAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "soaroom");
                break;
            case "FO": //done
                willExecutGlobalPrint = false;
                addAsync(new FoAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack, printModel.getKitchenPath(), printModel.getPrinterPath()), "fo");
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

    private String intransitReceipt(List<String> details) {
        String finalString = "";
        float maxColumn = 40;
        float perColumn = maxColumn / details.size();
        for (int i = 0; i < details.size(); i++) {
            if (details.size() >= perColumn) {
                finalString += details.get(i);
            } else {
                finalString += details.get(i);
                float temp = perColumn - details.get(i).length();
                for (int j = 0; j < temp; j++) {
                    finalString += " ";
                }
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

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.SELECTED_PRINTER))) {
            SPrinter sPrinter = new SPrinter(
                    Integer.valueOf(SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.SELECTED_LANGUAGE)),
                    getApplicationContext()
            );
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
        Toast.makeText(getApplicationContext(), "REQ COM USER", Toast.LENGTH_SHORT).show();
        BusProvider.getInstance().post(new FetchCompanyUserRequest());
    }

    @Subscribe
    public void fetchCompanyUserRequest(FetchCompanyUserResponse fetchCompanyUserResponse) {
        SharedPreferenceManager.saveString(getApplicationContext(),
                GsonHelper.getGson().toJson(fetchCompanyUserResponse.getResult()),
                ApplicationConstants.COMPANY_USER);

        Toast.makeText(getApplicationContext(), "FETCH COMPANY USER SAVED", Toast.LENGTH_SHORT).show();

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
            case 128: //BACKUP

                backupDatabase();

                break;
            case 125: //ROOM LIST VIEW POPUP
                if (roomListViewDialog == null) {
                    roomListViewDialog = new RoomListViewDialog(MainActivity.this) {
                        @Override
                        public void instransitClicked(List<FetchRoomResponse.Result> data) {
                            BusProvider.getInstance().post(new PrintModel("",
                                    "",
                                    "IN_TRANSIT",
                                    GsonHelper.getGson().toJson(data)));
                        }
                    };

                    roomListViewDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            roomListViewDialog = null;
                        }
                    });

                    roomListViewDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            roomListViewDialog = null;
                        }
                    });
                }

                if (!roomListViewDialog.isShowing()) roomListViewDialog.show();
                break;
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

            if (collectionDialog != null) {
                if (!collectionDialog.isShowing()) collectionDialog.show();
            }

            collectionDialog = new CollectionDialog(MainActivity.this, "SAFEKEEPING", false) {
                @Override
                public void printCashRecoData(String cashNRecoData) {

                }
            };


        }
    }

    @Subscribe
    public void updateTime(TimerModel timerModel) {
        currentDateTime = timerModel.getTime();
        timer.setText(timerModel.getTime());

        user.setText(currentText);
        role.setText(String.format("%s SHIFT : %s", userModel.getUserGroup().toUpperCase(), timerModel.getShiftNumber()));

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

//    private void fixDenoPrint(List<CollectionFinalPostModel> myList) {
//
//        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON))) {
//            TypeToken<List<FetchDenominationResponse.Result>> collectionToken = new TypeToken<List<FetchDenominationResponse.Result>>() {};
//            List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON), collectionToken.getType());
//            Double finalAmount = 0.00;
//            for (FetchDenominationResponse.Result cfm : denoDetails) {
//                String valueCount = "0";
//                String valueAmount = "0.00";
//                for (CollectionFinalPostModel c : myList) {
//                    if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
//                        valueCount = c.getAmount();
//                        valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
//                        break;
//                    }
//                }
//
//                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                        String.format("%s  x %s", valueCount, cfm.getAmount()),
//                        valueAmount
//                        ,
//                        40,
//                        2),
//                        Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//                finalAmount += Double.valueOf(valueAmount);
//            }
//
//            addPrinterSpace(1);
//
//            addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
//            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                    "CASH COUNT",
//                    String.valueOf(finalAmount)
//                    ,
//                    40,
//                    2),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//            addTextToPrinter(SPrinter.getPrinter(), twoColumns(
//                    "CASH OUT",
//                    "0.00"
//                    ,
//                    40,
//                    2),
//                    Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//            addPrinterSpace(1);
//            addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
//            addTextToPrinter(SPrinter.getPrinter(), "Printed date" , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//            addTextToPrinter(SPrinter.getPrinter(), currentDateTime , Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//            addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
//
//        }
//
//    }

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



        BusProvider.getInstance().post(new FetchDiscountSpecialRequest());
        if (TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.DISCOUNT_SPECIAL_JSON))) {

        }

    }

    @Subscribe
    public void fetchDiscountSpecialRespone(FetchDiscountSpecialResponse fetchDiscountSpecialResponse) {

        Log.d("TETET", "DIONEY");
        SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(fetchDiscountSpecialResponse.getResult()), ApplicationConstants.DISCOUNT_SPECIAL_JSON);
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



    private boolean canTransact() {
        if (SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("NOT_ALLOW") ||
                SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("") ||
                SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SHIFT_BLOCKER).equalsIgnoreCase("please execute cutoff")) {
            Toast.makeText(MainActivity.this, "Please execute cutoff", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void backupDatabase() {
        if (dialogProgressBar != null) {
            if (!dialogProgressBar.isShowing()) dialogProgressBar.show();
        }

        BackupDatabaseRequest backupDatabaseRequest = new BackupDatabaseRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<ResponseBody> request = iUsers.backupDb(backupDatabaseRequest.getMapValue());
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (dialogProgressBar != null) {
                    if (dialogProgressBar.isShowing()) dialogProgressBar.dismiss();
                }

                Utils.showDialogMessage(MainActivity.this, "Backup completed", "Information");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }


    private static String getEposExceptionText(int state) {
        String return_text = "";
        switch (state) {
            case    Epos2Exception.ERR_PARAM:
                return_text = "ERR_PARAM";
                break;
            case    Epos2Exception.ERR_CONNECT:
                return_text = "ERR_CONNECT";
                break;
            case    Epos2Exception.ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case    Epos2Exception.ERR_MEMORY:
                return_text = "ERR_MEMORY";
                break;
            case    Epos2Exception.ERR_ILLEGAL:
                return_text = "ERR_ILLEGAL";
                break;
            case    Epos2Exception.ERR_PROCESSING:
                return_text = "ERR_PROCESSING";
                break;
            case    Epos2Exception.ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case    Epos2Exception.ERR_IN_USE:
                return_text = "ERR_IN_USE";
                break;
            case    Epos2Exception.ERR_TYPE_INVALID:
                return_text = "ERR_TYPE_INVALID";
                break;
            case    Epos2Exception.ERR_DISCONNECT:
                return_text = "ERR_DISCONNECT";
                break;
            case    Epos2Exception.ERR_ALREADY_OPENED:
                return_text = "ERR_ALREADY_OPENED";
                break;
            case    Epos2Exception.ERR_ALREADY_USED:
                return_text = "ERR_ALREADY_USED";
                break;
            case    Epos2Exception.ERR_BOX_COUNT_OVER:
                return_text = "ERR_BOX_COUNT_OVER";
                break;
            case    Epos2Exception.ERR_BOX_CLIENT_OVER:
                return_text = "ERR_BOX_CLIENT_OVER";
                break;
            case    Epos2Exception.ERR_UNSUPPORTED:
                return_text = "ERR_UNSUPPORTED";
                break;
            case    Epos2Exception.ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }

    public interface AsyncFinishCallBack {
        void doneProcessing();
    }

    private void addAsync(AsyncTask asyncTask, String taskName) {
        if (myPrintJobs.size() < 1) {
            myPrintJobs.add(new PrintJobModel(taskName, asyncTask));
            runTask(taskName, asyncTask);
        } else {
            myPrintJobs.add(new PrintJobModel(taskName, asyncTask));
        }
    }

    private void runTask(String taskName, AsyncTask asyncTask) {
        switch (taskName) {
            case "franchise_or":
                FranchiseCheckoutAsync franchiseCheckoutAsync = (FranchiseCheckoutAsync) asyncTask;
                franchiseCheckoutAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "fo":
                FoAsync foAsync = (FoAsync) asyncTask;
                foAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "checkin":
                CheckInAsync checkInAsync = (CheckInAsync) asyncTask;
                checkInAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "checkout":
                CheckOutAsync checkOutAsync = (CheckOutAsync) asyncTask;
                checkOutAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "soaroom":
                SoaRoomAsync soaRoomAsync = (SoaRoomAsync) asyncTask;
                soaRoomAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "intransit":
                IntransitAsync intransitAsync = (IntransitAsync) asyncTask;
                intransitAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "postvoid":
                PostVoidAsync postVoidAsync = (PostVoidAsync) asyncTask;
                postVoidAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "changewakeupcall":
                ChangeWakeUpCallAsync changeWakeUpCallAsync = (ChangeWakeUpCallAsync) asyncTask;
                changeWakeUpCallAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "backout":
                BackOutAsync backOutAsync = (BackOutAsync) asyncTask;
                backOutAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "shortover":
                ShortOverAsync shortOverAsync = (ShortOverAsync) asyncTask;
                shortOverAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "cashreconcile":
                CashNReconcileAsync cashNReconcileAsync = (CashNReconcileAsync) asyncTask;
                cashNReconcileAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "safekeeping":
                SafeKeepingAsync safeKeepingAsync = (SafeKeepingAsync) asyncTask;
                safeKeepingAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "zread":
                ZReadAsync zReadAsync = (ZReadAsync) asyncTask;
                zReadAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "xread":
                XReadAsync xReadAsync = (XReadAsync) asyncTask;
                xReadAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "switchroom":
                SwitchRoomAsync switchRoomAsync = (SwitchRoomAsync) asyncTask;
                switchRoomAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "deposit":
                DepositAsync depositAsync =(DepositAsync) asyncTask;
                depositAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "soato":
                SoaToAsync soaToAsync = (SoaToAsync) asyncTask;
                soaToAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "voiditem":
                VoidAsync voidAsync = (VoidAsync) asyncTask;
                voidAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
        }
    }

    private void changeTheme() {
        BusProvider.getInstance().post(new ChangeThemeModel(""));
        if (SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.THEME_SELECTED).isEmpty()) {
            lightTheme();
        } else {
            if (SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                lightTheme();
            } else {
                darkTheme();
            }
        }
    }

    private void lightTheme() {
        cardRole.setCardBackgroundColor(getResources().getColor(R.color.lightColorAccent));
        role.setBackgroundColor(getResources().getColor(R.color.lightColorAccent));
        role.setTextColor(getResources().getColor(R.color.lightColorAccentFont));

        mainContainerBg.setBackgroundColor(getResources().getColor(R.color.lightMainBg));
        toolbar.setBackgroundColor(getResources().getColor(R.color.lightHeaderBg));

        separator.setBackgroundColor(getResources().getColor(R.color.lightPrimaryFont));
        separator2.setBackgroundColor(getResources().getColor(R.color.lightPrimaryFont));
        user.setTextColor(getResources().getColor(R.color.darkFont));
        timer.setTextColor(getResources().getColor(R.color.darkFont));
    }

    private void darkTheme() {
        cardRole.setCardBackgroundColor(getResources().getColor(R.color.darkColorAccent));
        role.setBackgroundColor(getResources().getColor(R.color.darkColorAccent));
        role.setTextColor(getResources().getColor(R.color.darkColorAccentFont));

        mainContainerBg.setBackgroundColor(getResources().getColor(R.color.darkMainBg));
        toolbar.setBackgroundColor(getResources().getColor(R.color.darkHeaderBg));

        separator.setBackgroundColor(getResources().getColor(R.color.darkFont));
        separator2.setBackgroundColor(getResources().getColor(R.color.darkFont));
        user.setTextColor(getResources().getColor(R.color.darkFont));
        timer.setTextColor(getResources().getColor(R.color.darkFont));

    }
}

