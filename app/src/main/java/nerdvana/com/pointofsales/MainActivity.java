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
import android.os.Environment;
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
import android.support.v7.widget.TooltipCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.epson.epos2.printer.ReceiveListener;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
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
import nerdvana.com.pointofsales.background.ChangeQtyAsync;
import nerdvana.com.pointofsales.background.ChangeWakeUpCallAsync;
import nerdvana.com.pointofsales.background.CheckInAsync;
import nerdvana.com.pointofsales.background.CheckOutAsync;
import nerdvana.com.pointofsales.background.CountUpTimer;
import nerdvana.com.pointofsales.background.CreateReceiptAsync;
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
import nerdvana.com.pointofsales.background.SpotAuditAsync;
import nerdvana.com.pointofsales.background.SwitchRoomAsync;
import nerdvana.com.pointofsales.background.VoidAsync;
import nerdvana.com.pointofsales.background.XReadAsync;
import nerdvana.com.pointofsales.background.ZReadAsync;
import nerdvana.com.pointofsales.dialogs.CollectionDialog;
import nerdvana.com.pointofsales.dialogs.DialogProgressBar;
import nerdvana.com.pointofsales.dialogs.DialogWakeUpCall;
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
import nerdvana.com.pointofsales.model.MachineChangeRefresh;
import nerdvana.com.pointofsales.model.OpenWakeUpCallDialog;
import nerdvana.com.pointofsales.model.PaymentPrintModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.PrintJobModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.ProgressBarModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.SeniorReceiptCheckoutModel;
import nerdvana.com.pointofsales.model.ServerConnectionModel;
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

import static nerdvana.com.pointofsales.PrinterUtils.addPrinterSpace;
import static nerdvana.com.pointofsales.PrinterUtils.addTextToPrinter;
import static nerdvana.com.pointofsales.PrinterUtils.returnWithTwoDecimal;
import static nerdvana.com.pointofsales.PrinterUtils.twoColumnsRightGreaterTr;

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

    private DialogWakeUpCall dialogWakeUpCall;


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

    private TextView onlineTextIndicator;
    private ImageView onlineImageIndicator;

    private String branchAndCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        branchAndCode = SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.BRANCH) + "_"  +
                SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CODE) + "_";



        onlineImageIndicator = findViewById(R.id.onlineImageIndicator);
        onlineTextIndicator = findViewById(R.id.onlineTextIndicator);


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
                ApplicationConstants.IS_THEME_CHANGED = "T";
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

        role.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //ROOM REFRESH NOT SPAM
                //ROOM SEARCH APPLIES EVEN AFTER REFRESH
                //SEARCH VIEW SWITCHED TO HIDINGEDITTEXT TO HIDE KEYBOARD
                TooltipCompat.setTooltipText(v, "v1.2.5");
                return false;
            }
        });
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

        BusProvider.getInstance().post(new FetchRoomStatusRequest());


//        List<RoomStatusEntity> list = RoomStatusEntity.listAll(RoomStatusEntity.class);
//        if (list.size() < 1) {
//            BusProvider.getInstance().post(new FetchRoomStatusRequest());
//        }
    }

    @Subscribe
    public void onReceiveRoomStatusList(FetchRoomStatusResponse fetchRoomStatusResponse) {
//        new RoomStatusAsync(fetchRoomStatusResponse.getResult()).execute();

        SharedPreferenceManager.saveString(MainActivity.this, GsonHelper.getGson().toJson(fetchRoomStatusResponse.getResult()), ApplicationConstants.ROOM_STATUS_JSON);

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

        new SocketManager(this);
//        if (canTransact()) {
//            checkSafeKeepingRequest();
//        }

        loadPrinter();

        changeTheme();
        changeMachine();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!hasPermission()) {
//                String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                requestPermissions(permissions, 100);
//            } else {
//                Toast.makeText(MainActivity.this, "Allowed", Toast.LENGTH_LONG).show();
//            }
//
//        }

    }

    private boolean hasPermission() {
        boolean isPermitted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                isPermitted = true;
            }
        }
        return isPermitted;
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
            case "SPOT_AUDIT_PRINT":
                //willExecutGlobalPrint = false;
                //addAsync(new SpotAuditAsync(printModel, MainActivity.this, userModel, asyncFinishCallBack, currentDateTime), "spot_audit");
                break;
            case "CHANGE_QTY":
                //willExecutGlobalPrint = false;
                //addAsync(new ChangeQtyAsync(printModel, MainActivity.this, userModel, asyncFinishCallBack), "change_qty");
                break;
            case "FRANCHISE_OR":
                willExecutGlobalPrint = false;
                addAsync(new FranchiseCheckoutAsync(printModel, MainActivity.this, userModel, asyncFinishCallBack), "franchise_or");
                break;
            case "IN_TRANSIT": //ignore header
                //willExecutGlobalPrint = false;
                //addAsync(new IntransitAsync(printModel, MainActivity.this, userModel,currentDateTime, asyncFinishCallBack), "intransit");
                break;
            case "POST_VOID": //ignore header
                willExecutGlobalPrint = false;
                savePostVoidToLocal(printModel, MainActivity.this, userModel, currentDateTime);
                addAsync(new PostVoidAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "postvoid");
                break;
            case "CHANGE_WAKE_UP_CALL": //done
                //willExecutGlobalPrint = false;
                //addAsync(new ChangeWakeUpCallAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "changewakeupcall");
                break;
            case "BACKOUT": //done
                //willExecutGlobalPrint = false;
                //addAsync(new BackOutAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "backout");
                break;
            case "SHORTOVER"://ignore
                //willExecutGlobalPrint = false;
                //addAsync(new ShortOverAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "shortover");
                break;
            case "CASHRECONCILE"://ignore
                //willExecutGlobalPrint = false;
                saveCashierReconcileToLocal(printModel, userModel, currentDateTime);
                //addAsync(new CashNReconcileAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "cashreconcile");
                break;
            case "SAFEKEEPING"://ignore
                //willExecutGlobalPrint = false;
                saveSafeKeepToLocal(printModel, userModel, currentDateTime);
                //addAsync(new SafeKeepingAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "safekeeping");
                break;//REPRINTZREAD
            case "REPRINTZREAD"://ignore
                willExecutGlobalPrint = false;
                saveZReadToLocal(printModel, userModel, currentDateTime);
                addAsync(new ZReadAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "zread");
                break;
            case "ZREAD"://ignore
                willExecutGlobalPrint = false;
                saveZReadToLocal(printModel, userModel, currentDateTime);
                addAsync(new ZReadAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "zread");
                 break;
            case "REPRINTXREADING"://ignore

                Log.d("REPRINT", "PLEASE");

                willExecutGlobalPrint = false;
                addAsync(new XReadAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "xread");
                break;
            case "REXREADING"://ignore
                willExecutGlobalPrint = false;
                saveXReadToLocal(printModel, userModel, currentDateTime);
                addAsync(new XReadAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "xread");
                break;
            case "SWITCH_ROOM"://done
                //willExecutGlobalPrint = false;
                //addAsync(new SwitchRoomAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "switchroom");
                break;
            case "PRINT_RECEIPT"://done //checkout
                willExecutGlobalPrint = false;
                saveDataToLocal(printModel, userModel, currentDateTime);
                addAsync(new CheckOutAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "checkout");
                break;
            case "REPRINT_RECEIPT"://done //checkout
                willExecutGlobalPrint = false;
                saveDataToLocal(printModel, userModel, currentDateTime);
                addAsync(new CheckOutAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "reprint_checkout");
                break;
            case "DEPOSIT"://done
                //willExecutGlobalPrint = false;
                //addAsync(new DepositAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "deposit");
                break;
            case "SOA-TO"://done
                willExecutGlobalPrint = false;
                addAsync(new SoaToAsync(printModel, MainActivity.this, userModel, currentDateTime, asyncFinishCallBack), "soato");
                break;
            case "CHECKIN"://done
                //willExecutGlobalPrint = false;
                //addAsync(new CheckInAsync(printModel, MainActivity.this, userModel, currentDateTime, selected, asyncFinishCallBack), "checkin");
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

    private void savePostVoidToLocal(PrintModel printModel, MainActivity mainActivity, UserModel userModel, String currentDateTime) {

        String finalString = "";

        String receiptNo = "NA";


        finalString += receiptString("NERDVANA CORP.", "", MainActivity.this, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", MainActivity.this, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", MainActivity.this, true);
        finalString += receiptString("TEL NO: 8671-9782", "", MainActivity.this, true);
        finalString += receiptString("SERIAL NO: ********", "", MainActivity.this, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", MainActivity.this, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", MainActivity.this, true);

        finalString += receiptString("V O I D", "", MainActivity.this, true);

        FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class)
                ;


        finalString += receiptString("", "", MainActivity.this, true);

        if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
            finalString += receiptString("ROOM #" + printModel.getRoomNumber(), "", MainActivity.this, true);
        } else {
            finalString += receiptString("TAKEOUT", "", MainActivity.this, true);
        }


        if (toList1 != null) {

            //region create receipt data
            finalString += receiptString("CASHIER", userModel.getUsername(), MainActivity.this, false);
            finalString += receiptString("ROOM BOY", String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA"), MainActivity.this, false);
            finalString += receiptString("CHECK IN", convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA"), MainActivity.this, false);
            finalString += receiptString("CHECK OUT", convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA"), MainActivity.this, false);

            if (toList1.getReceiptNo() == null) {
                receiptNo = "NA";
            } else {
                receiptNo = toList1.getReceiptNo().toString();
            }


            finalString += receiptString("OR NO", toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(), MainActivity.this, false);
            finalString += receiptString("TERMINAL NO", SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MACHINE_ID), MainActivity.this, false);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);
            finalString += receiptString("QTY   DESCRIPTION         AMOUNT", "", MainActivity.this, true);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);
            for (FetchOrderPendingViaControlNoResponse.Post soaTrans : toList1.getPost()) {
                if (soaTrans.getVoid() == 0) {
                    String qty = "";
                    String qtyFiller = "";

                    for (int i = 0; i < soaTrans.getQty(); i++) {
                        qtyFiller += " ";
                    }

                    qty += soaTrans.getQty();
                    if (String.valueOf(soaTrans.getQty()).length() < 4) {
                        for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                            qty += " ";
                            qtyFiller += " ";
                        }
                    } else {
                        qtyFiller = "    ";
                    }
                    String item = "";

                    if (soaTrans.getProductId() == 0) {
                        item =soaTrans.getRoomRate().toString();
                    } else {
                        item =soaTrans.getProduct().getProductInitial();
                    }

                    finalString += receiptString(qty + " " + item, returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty())), MainActivity.this, false);


                    if (soaTrans.getFreebie() != null) {
                        if (soaTrans.getFreebie().getPostAlaCart().size() > 0) {
                            for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getFreebie().getPostAlaCart()) {

                                finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", MainActivity.this, false);

                            }
                        }

                        if (soaTrans.getFreebie().getPostGroup().size() > 0) {
                            for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getFreebie().getPostGroup()) {
                                for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                    finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", MainActivity.this, false);

                                }
                            }
                        }


                    }



                    if (soaTrans.getPostAlaCartList().size() > 0) {
                        for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getPostAlaCartList()) {

                            finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", MainActivity.this, false);

                        }
                    }

                    if (soaTrans.getPostGroupList().size() > 0) {
                        for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getPostGroupList()) {
                            for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", MainActivity.this, false);

                            }

                        }
                    }

                    if (soaTrans.getDiscounts().size() > 0) {
                        for (FetchOrderPendingViaControlNoResponse.PostObjectDiscount d : soaTrans.getDiscounts()) {
                            if (TextUtils.isEmpty(d.getDeleted_at())) {
                                String itemDiscount = "";
                                if (d.getDiscountPercentage().equalsIgnoreCase("0")) {
                                    itemDiscount = "LESS ";
                                } else {
                                    itemDiscount = "LESS "+d.getDiscountPercentage() + "%";
                                }

                                finalString += receiptString(qtyFiller+ " "+itemDiscount,"-" + returnWithTwoDecimal(String.valueOf(d.getDiscountAmount())) ,MainActivity.this, false);

                            }
                        }
                    }
                }
            }




            if (toList1.getOtHours() > 0) {

                finalString += receiptString(String.valueOf(toList1.getOtHours()) + " " + "OT HOURS",
                        returnWithTwoDecimal(String.valueOf(toList1.getOtAmount())), MainActivity.this, false);

            }

            if (Integer.valueOf(toList1.getPersonCount()) > 2) {


                finalString += receiptString(String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                        returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount())), MainActivity.this, false);

            }

            finalString += receiptString("", "", MainActivity.this, true);



            finalString += receiptString("NO OF PERSON/S", returnWithTwoDecimal(String.valueOf(toList1.getPersonCount())), MainActivity.this, false);

            finalString += receiptString("NO OF ITEMS", returnWithTwoDecimal(String.valueOf(toList1.getTotalQty())), MainActivity.this, false);

            finalString += receiptString("", "", MainActivity.this, true);


            finalString += receiptString("LESS", "", MainActivity.this, false);




            finalString += receiptString("   VAT EXEMPT", toList1.getVatExempt() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getVatExempt()))) : returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())), MainActivity.this, false);

            finalString += receiptString("   DISCOUNT", toList1.getDiscount() > 0 ? String.format("-%s",returnWithTwoDecimal(String.valueOf(toList1.getDiscount()))) : returnWithTwoDecimal(String.valueOf(toList1.getDiscount())), MainActivity.this, false);

//            finalString += receiptString("   ADVANCED DEPOSIT", toList1.getAdvance() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getAdvance()))) : returnWithTwoDecimal(String.valueOf(toList1.getAdvance())), MainActivity.this, false);

            finalString += receiptString("", "", MainActivity.this, true);


//            bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount()


            finalString += receiptString("SUB TOTAL", returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))), MainActivity.this, false);



            finalString += receiptString("AMOUNT DUE", returnWithTwoDecimal(String.valueOf(
                    (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                            - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))), MainActivity.this, false);


            finalString += receiptString("TENDERED", returnWithTwoDecimal(String.valueOf(toList1.getTendered())), MainActivity.this, false);


            finalString += receiptString("CHANGE", returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))), MainActivity.this, false);


            finalString += receiptString("", "", MainActivity.this, true);

            List<Integer> tmpArr = new ArrayList<>();
            String pymType = "";
            List<String> ccardArray = new ArrayList<>();
            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (!tmpArr.contains(pym.getPaymentTypeId())) {
                    tmpArr.add(pym.getPaymentTypeId());
                    pymType = pym.getPaymentDescription();
                }

                if (pym.getPaymentTypeId() == 2) {
                    if (pym.getCardDetail() != null) {
                        if (!pym.getCardDetail().getCardNumber().trim().isEmpty()) {
                            int starCount = 0;
                            String finalData = "";
                            if (pym.getCardDetail().getCardNumber().length() < 3) {
                                finalData += pym.getCardDetail().getCardNumber();
                            } else {
                                starCount = pym.getCardDetail().getCardNumber().length() - 3;
                                finalData += new String(new char[starCount]).replace("\0", "*");
                                finalData += pym.getCardDetail().getCardNumber().substring(starCount);
                            }

                            if (pym.getCardDetail().getCreditCardId().equalsIgnoreCase("1")) {

                                finalString += receiptString("MASTER", "", MainActivity.this, false);

                            } else {
                                finalString += receiptString("VISA", "", MainActivity.this, false);

                            }

                            finalString += receiptString(pym.getPaymentDescription(), finalData, MainActivity.this, false);

                        }
                    }
                }
            }


//            finalString += receiptString("PAYMENT TYPE", tmpArr.size() > 1 ? "MULTIPLE" : pymType, MainActivity.this, false);



            finalString += receiptString("", "", MainActivity.this, true);



            finalString += receiptString("VATABLE SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatable())), MainActivity.this, false);

            finalString += receiptString("VAT-EXEMPT SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())), MainActivity.this, false);

            finalString += receiptString("12% VAT", returnWithTwoDecimal(String.valueOf(toList1.getVat())), MainActivity.this, false);


            finalString += receiptString("", "", MainActivity.this, true);


            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (pym.getIsAdvance() == 1) {

                    finalString += receiptString(pym.getPaymentDescription(), returnWithTwoDecimal(String.valueOf(pym.getAmount())), MainActivity.this, false);

                }
            }

            boolean hasSpecial = false;
            List<SeniorReceiptCheckoutModel> seniorReceiptList = new ArrayList<>();
            if (toList1.getDiscountsList().size() > 0) {
                finalString += receiptString("DISCOUNT LIST", "", MainActivity.this, true);

                for (FetchOrderPendingViaControlNoResponse.Discounts d : toList1.getDiscountsList()) {


                    if (TextUtils.isEmpty(d.getVoid_by())) {
                        if (d.getId().equalsIgnoreCase("0")) { //MANUAL

                        } else {

                            if (d.getInfo() != null) {

//                                if (d.getDiscountTypes().getIsSpecial() == 1) {
//                                    hasSpecial = true;
//                                    seniorReceiptList.add(
//                                            new SeniorReceiptCheckoutModel(
//                                                    d.getInfo().getName() == null ? "" : d.getInfo().getName(),
//                                                    d.getInfo().getCardNo() == null ? "" : d.getInfo().getCardNo(),
//                                                    d.getInfo().getAddress() == null ? "" : d.getInfo().getAddress(),
//                                                    d.getInfo().getTin() == null ? "" : d.getInfo().getTin(),
//                                                    d.getInfo().getBusinessStyle() == null ? "" : d.getInfo().getBusinessStyle()
//                                            )
//                                    );
//                                }

                                if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                    finalString += receiptString(d.getDiscountType(), "NA", MainActivity.this, false);


                                } else {

                                    if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                        finalString += receiptString(d.getDiscountType(), "NA", MainActivity.this, false);


                                    } else {
                                        if (d.getInfo().getCardNo() != null) {

                                            finalString += receiptString(d.getDiscountType(), d.getInfo().getCardNo().toUpperCase(), MainActivity.this, false);


                                        }

                                        if (d.getInfo().getName() != null) {

                                            finalString += receiptString("NAME", d.getInfo().getName().toUpperCase(), MainActivity.this, false);

                                        }
                                    }


                                }

                            }
                        }
                    }
                }

            }




            if (toList1.getCustomer() != null) {
                if (!toList1.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList1.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {

                    finalString += receiptString("", "", MainActivity.this, true);

                    finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", MainActivity.this, true);


                    finalString += receiptString("NAME:"+toList1.getCustomer().getCustomer(), "", MainActivity.this, true);

                    if (toList1.getCustomer().getAddress() != null) {

                        finalString += receiptString("ADDRESS:"+toList1.getCustomer().getAddress(), "", MainActivity.this, true);

                    } else {
                        finalString += receiptString("ADDRESS:________________________", "", MainActivity.this, true);

                    }

                    if (toList1.getCustomer().getTin() != null) {
                        finalString += receiptString("TIN#:"+toList1.getCustomer().getTin(), "", MainActivity.this, true);

                    } else {

                        finalString += receiptString("TIN#:___________________________", "", MainActivity.this, true);

                    }

                    if (toList1.getCustomer().getBusinessStyle() != null) {
                        finalString += receiptString("BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(), "", MainActivity.this, true);


                        finalString += receiptString(toList1.getCustomer().getBusinessStyle(), "", MainActivity.this, true);

                    } else {
                        finalString += receiptString("BUSINESS STYLE:_________________", "", MainActivity.this, true);

                    }



                    finalString += receiptString("", "", MainActivity.this, true);


                } else {
                    finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", MainActivity.this, true);
                    finalString += receiptString("NAME:___________________________", "", MainActivity.this, true);
                    finalString += receiptString("ADDRESS:________________________", "", MainActivity.this, true);
                    finalString += receiptString("TIN#:___________________________", "", MainActivity.this, true);
                    finalString += receiptString("BUSINESS STYLE:_________________", "", MainActivity.this, true);

                }
            } else {
                finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", MainActivity.this, true);
                finalString += receiptString("NAME:___________________________", "", MainActivity.this, true);
                finalString += receiptString("ADDRESS:________________________", "", MainActivity.this, true);
                finalString += receiptString("TIN#:___________________________", "", MainActivity.this, true);
                finalString += receiptString("BUSINESS STYLE:_________________", "", MainActivity.this, true);

            }

            finalString += receiptString("", "", MainActivity.this, true);


            finalString += receiptString("", "", MainActivity.this, true);

            finalString += receiptString("Thank you come again", "", MainActivity.this, true);

            finalString += receiptString("----- SYSTEM PROVIDER DETAILS -----", "", MainActivity.this, true);
            finalString += receiptString("POS Provider : NERDVANA CORP.", "", MainActivity.this, true);
            finalString += receiptString("Address : 1 CANLEY ROAD BRGY", "", MainActivity.this, true);
            finalString += receiptString("BAGONG ILOG PASIG CITY", "", MainActivity.this, true);
            finalString += receiptString("VAT REG TIN: 009-772-500-000", "", MainActivity.this, true);
            finalString += receiptString("ACCRED NO:**********************", "", MainActivity.this, true);
            finalString += receiptString("Date Issued : " + toList1.getCreatedAt(), "", MainActivity.this, true);
            finalString += receiptString("Valid Until : " + PrinterUtils.yearPlusFive(toList1.getCreatedAt()), "", MainActivity.this, true);

            finalString += receiptString("", "", MainActivity.this, true);

            finalString += receiptString("THIS RECEIPT SHALL BE VALID FOR", "", MainActivity.this, true);

            finalString += receiptString("FIVE(5) YEARS FROM THE DATE OF", "", MainActivity.this, true);

            finalString += receiptString("THE PERMIT TO USE", "", MainActivity.this, true);

            finalString += receiptString("", "", MainActivity.this, true);



            //endregion



            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
            String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");

            try {
                File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, branchAndCode +"PV_"+receiptNo +".txt");
                FileWriter writer = null;
                writer = new FileWriter(gpxfile);

                writer.append(finalString);
//                writer.append("test data");

                writer.flush();

                writer.close();

            } catch (IOException e) {
                Log.d("ERRORMESSAGE", e.getMessage());
//                asyncFinishCallBack.doneProcessing();
            }




        } else {
            Log.d("DATANUL"," DATAI SNULL");
        }







    }

    private void saveZReadToLocal(PrintModel printModel, UserModel userModel, String currentDateTime) {

        String finalString = "";


        finalString += receiptString("NERDVANA CORP.", "", MainActivity.this, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", MainActivity.this, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", MainActivity.this, true);
        finalString += receiptString("TEL NO: 8671-9782", "", MainActivity.this, true);
        finalString += receiptString("SERIAL NO: ********", "", MainActivity.this, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", MainActivity.this, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", MainActivity.this, true);

        finalString += receiptString("X READING", "", MainActivity.this, true);




        ZReadResponse.Result zReadResponse = GsonHelper.getGson().fromJson(printModel.getData(), ZReadResponse.Result.class);

        if (zReadResponse != null) {

            finalString += receiptString("POSTING DATE: ", zReadResponse.getData().getGeneratedAt(), MainActivity.this, false);
            finalString += receiptString("USER : ", userModel.getUsername(), MainActivity.this, false);
            finalString += receiptString("MANAGER : ", zReadResponse.getData().getDutyManager().getName(), MainActivity.this, false);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);
            finalString += receiptString("DESCRIPTION                VALUE", "", MainActivity.this, true);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);



            finalString += receiptString("TERMINAL NO", String.valueOf(zReadResponse.getData().getPosId()), MainActivity.this, false);
            finalString += receiptString("", "", MainActivity.this, false);
            finalString += receiptString("GROSS SALES", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getGrossSales())), MainActivity.this, false);
            finalString += receiptString("NET SALES", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getNetSales())), MainActivity.this, false);
            finalString += receiptString("", "", MainActivity.this, false);
            finalString += receiptString("VATABLES SALES", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatable())), MainActivity.this, false);
            finalString += receiptString("VAT EXEMPT SALES", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExemptSales())), MainActivity.this, false);
            finalString += receiptString("VAT EXEMPT", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt())), MainActivity.this, false);
            finalString += receiptString("12% VAT", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVat())), MainActivity.this, false);
//            finalString += receiptString("NON VAT", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVatExempt())), MainActivity.this, false);
//            finalString += receiptString("SERVICE CHARGE", "0.00", MainActivity.this, false);
            finalString += receiptString("", "", MainActivity.this, false);

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

                            finalString += receiptString(payment.getPaymentType(), "0.00", MainActivity.this, false);

                        } else {

                            finalString += receiptString(payment.getPaymentType().toUpperCase()+ " SALES", String.valueOf(value), MainActivity.this, false);


                            if (payment.getPaymentType().equalsIgnoreCase("card")) {

                                finalString += receiptString("DEPOSIT SALES", String.valueOf(totalAdvancePayment), MainActivity.this, false);

                            }
                        }

                    } else {

                        if (value > 0) {
                            if (isAdvance.equalsIgnoreCase("1")) {

                                paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                            } else {


                                finalString += receiptString(payment.getPaymentType(), String.valueOf(value), MainActivity.this, false);

                            }
                        }
                    }
                }
            }


            finalString += receiptString("", "", MainActivity.this, false);

            double depositAdjustment = 0.00;
            for (ZReadResponse.CutOff cutOff : zReadResponse.getData().getCutOff()) {
                depositAdjustment += Double.valueOf(cutOff.getCashAndReco().get(0).getAdjustmentDeposit());
            }


//            finalString += receiptString("DEPO. ADJUSTMENT", String.valueOf(depositAdjustment), MainActivity.this, false);
            finalString += receiptString("VOID", returnWithTwoDecimal(String.valueOf(zReadResponse.getData().getVoidAmount())), MainActivity.this, false);

            addPrinterSpace(1);
            if (zReadResponse.getDiscount().size() > 0) {

                finalString += receiptString("DISCOUNT LIST", "", MainActivity.this, false);


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

                            finalString += receiptString(d.getDiscountCard(), returnWithTwoDecimal(String.valueOf(amount)), MainActivity.this, false);
                            finalString += receiptString(d.getDiscountCard()+"(COUNT)", String.valueOf(count), MainActivity.this, false);

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

                finalString += receiptString("OTHERS(AMOUNT)", String.valueOf(otherDiscAmount), MainActivity.this, false);
                finalString += receiptString("OTHERS(COUNT)", String.valueOf(otherDiscCount), MainActivity.this, false);

            } else {

                finalString += receiptString("SENIOR CITIZEN", "0.00", MainActivity.this, false);
                finalString += receiptString("SENIOR CITIZEN(COUNT)", "0", MainActivity.this, false);

                finalString += receiptString("PWD", "0.00", MainActivity.this, false);
                finalString += receiptString("PWD(COUNT)", "0", MainActivity.this, false);

                finalString += receiptString("OTHERS", "0.00", MainActivity.this, false);
                finalString += receiptString("OTHERS(COUNT)", "0", MainActivity.this, false);

            }

            finalString += receiptString("", "", MainActivity.this, false);

            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]), "", MainActivity.this, true);


            finalString += receiptString("BEG. OR NO", zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(0) : "NA", MainActivity.this, false);
            finalString += receiptString("ENDING OR NO", zReadResponse.getControlNo().size() > 0 ? zReadResponse.getControlNo().get(zReadResponse.getControlNo().size() - 1) : "NA", MainActivity.this, false);

            finalString += receiptString("BEG. BALANCE", returnWithTwoDecimal(String.valueOf(zReadResponse.getOldGrandTotal())), MainActivity.this, false);
            finalString += receiptString("ENDING BALANCE", returnWithTwoDecimal(String.valueOf(zReadResponse.getNewGrandTotal())), MainActivity.this, false);

            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);


            finalString += receiptString("", "", MainActivity.this, true);


            finalString += receiptString("Z KEEPING COUNTER", returnWithTwoDecimal(String.valueOf(zReadResponse.getCount())), MainActivity.this, false);

            finalString += receiptString("------ END OF REPORT ------", "", MainActivity.this, true);
            finalString += receiptString("PRINTED DATE", "", MainActivity.this, true);
            finalString += receiptString(currentDateTime, "", MainActivity.this, true);
            finalString += receiptString("PRINTED BY", userModel.getUsername(), MainActivity.this, false);


        } else {

        }






        DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
        String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");
        String fileName = dtf.parseDateTime(currentDateTime).toString("yyyy_MM_dd_HH_mm_ss");

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
            if (!root.exists()) {
                root.mkdirs();
            }


            File gpxfile = new File(root, branchAndCode +"ZREAD_"+fileName +".txt");
            FileWriter writer = null;
            writer = new FileWriter(gpxfile);

            writer.append(finalString);
//                writer.append("test data");

            writer.flush();

            writer.close();

        } catch (IOException e) {
            Log.d("ERRORMESSAGE", e.getMessage());
//                asyncFinishCallBack.doneProcessing();
        }


    }

    private void saveXReadToLocal(PrintModel printModel, UserModel userModel, String currentDateTime) {

        String finalString = "";


        finalString += receiptString("NERDVANA CORP.", "", MainActivity.this, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", MainActivity.this, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", MainActivity.this, true);
        finalString += receiptString("TEL NO: 8671-9782", "", MainActivity.this, true);
        finalString += receiptString("SERIAL NO: ********", "", MainActivity.this, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", MainActivity.this, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", MainActivity.this, true);

        finalString += receiptString("X READING", "", MainActivity.this, true);

            try {


                JSONObject jsonObject = new JSONObject(printModel.getData());


                JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                JSONArray dataCashAndRecoJsonObject = jsonObject.getJSONObject("data").getJSONArray("cash_and_reco");
                JSONObject cashierDataObject = jsonObject.getJSONObject("data").getJSONObject("cashier");
                JSONObject dutyManager = jsonObject.getJSONObject("data").getJSONObject("duty_manager");
                if (dataJsonObject != null) {

                    finalString += receiptString("POSTING DATE: ", dataJsonObject.getString("cut_off_date"), MainActivity.this, false);
                    finalString += receiptString("SHIFT : ", (dataJsonObject.getString("shift_no") != null ? dataJsonObject.getString("shift_no") : " NA"), MainActivity.this, false);
                    finalString += receiptString("USER : ", userModel.getUsername(), MainActivity.this, false);
                    finalString += receiptString("MANAGER : ", dutyManager.getString("name"), MainActivity.this, false);
                    finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);
                    finalString += receiptString("DESCRIPTION                VALUE", "", MainActivity.this, true);
                    finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);

                    finalString += receiptString("TERMINAL NO: ", dataJsonObject.getString("pos_id"), MainActivity.this, false);
                    finalString += receiptString("", "", MainActivity.this, false);
                    finalString += receiptString("Gross Sales", returnWithTwoDecimal(dataJsonObject.getString("gross_sales")), MainActivity.this, false);
                    finalString += receiptString("Net Sales", returnWithTwoDecimal(dataJsonObject.getString("net_sales")), MainActivity.this, false);
                    finalString += receiptString("", "", MainActivity.this, false);
                    finalString += receiptString("VATable Sales", returnWithTwoDecimal(dataJsonObject.getString("vatable")), MainActivity.this, false);
                    finalString += receiptString("VAT EXEMPT SALES", returnWithTwoDecimal(dataJsonObject.getString("vat_exempt_sales")), MainActivity.this, false);
                    finalString += receiptString("VAT EXEMPT", returnWithTwoDecimal(dataJsonObject.getString("vat_exempt")), MainActivity.this, false);
                    finalString += receiptString("12% VAT", returnWithTwoDecimal(dataJsonObject.getString("vat")), MainActivity.this, false);
//                    finalString += receiptString("SERVICE CHARGE", "0.00", MainActivity.this, false);


                }


                JSONArray paymentJsonArray = jsonObject.getJSONArray("payment");

                finalString += receiptString("", "", MainActivity.this, false);



                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON))) {

                    TypeToken<List<FetchPaymentResponse.Result>> paymentTypeToken = new TypeToken<List<FetchPaymentResponse.Result>>() {
                    };
                    List<FetchPaymentResponse.Result> paymentTypeList = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.PAYMENT_TYPE_JSON), paymentTypeToken.getType());

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

                            if (isAdvance.equalsIgnoreCase("1")) {

                                paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));


                                finalString += receiptString(payment.getPaymentType(), "0.00", MainActivity.this, false);

                            } else {


                                finalString += receiptString(payment.getPaymentType() + " Sales", String.valueOf(value), MainActivity.this, false);

                                if (payment.getPaymentType().equalsIgnoreCase("card")) {

                                    finalString += receiptString("DEPOSIT SALES", String.valueOf(totalAdvancePayment), MainActivity.this, false);

                                }
                            }

                        } else {

                            if (value > 0) {
                                if (isAdvance.equalsIgnoreCase("1")) {

                                    paymentPrintModels.add(new PaymentPrintModel(payment.getPaymentType() + "(adv)", String.valueOf(value)));
                                } else {

                                    finalString += receiptString(payment.getPaymentType(), String.valueOf(value), MainActivity.this, false);

                                }
                            }
                        }
                    }

                }

                finalString += receiptString("", "", MainActivity.this, false);
                JSONObject cashRecoObj = dataCashAndRecoJsonObject.getJSONObject(0);
//                finalString += receiptString("DEPOSIT ADJ.", String.format("(%s)", String.valueOf(Double.valueOf(cashRecoObj.getString("adjustment_deposit")))), MainActivity.this, false);
                finalString += receiptString("VOID", returnWithTwoDecimal(String.valueOf(dataJsonObject.get("void_amount"))), MainActivity.this, false);
                JSONArray discountJsonArray = jsonObject.getJSONArray("discount");
                finalString += receiptString("", "", MainActivity.this, false);
                if (discountJsonArray.length() > 0) {
                    finalString += receiptString("DISCOUNT LIST", "", MainActivity.this, false);
                    TypeToken<List<FetchDiscountSpecialResponse.Result>> discToken = new TypeToken<List<FetchDiscountSpecialResponse.Result>>() {};
                    List<FetchDiscountSpecialResponse.Result> discountDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.DISCOUNT_SPECIAL_JSON), discToken.getType());
                    double otherDiscAmount = 0.00;
                    if (discountDetails != null) {
                        for (FetchDiscountSpecialResponse.Result d : discountDetails) {
                            Integer count = 0;
                            Double amount = 0.00;
                            if (discountJsonArray.length() > 0) {
                                for (int i = 0; i < discountJsonArray.length(); i++) {
                                    JSONObject temp = discountJsonArray.getJSONObject(i);
                                    if (temp.getString("is_special").equalsIgnoreCase("1") || temp.getString("is_special").equalsIgnoreCase("1.0")) {
                                        if (temp.getString("discount_type_id").equalsIgnoreCase(String.valueOf(d.getId()))) {
                                            amount = Double.valueOf(temp.getString("discount_amount"));
                                            count = Integer.valueOf(temp.getString("count"));
                                        }
                                    } else {
                                        otherDiscAmount += Double.valueOf(temp.getString("discount_amount"));
                                    }
                                }
                            }
                            finalString += receiptString(d.getDiscountCard(), String.valueOf(amount), MainActivity.this, false);
                            finalString += receiptString(d.getDiscountCard() + "(COUNT)", String.valueOf(count), MainActivity.this, false);




                        }
                    }
                    int otherDiscCount = 0;
//                double otherDiscAmount = 0.00;
                    finalString += receiptString("OTHERS", String.valueOf(otherDiscAmount), MainActivity.this, false);
                } else {

                    finalString += receiptString("SENIOR CITIZEN", "0.00", MainActivity.this, false);
                    finalString += receiptString("SENIOR CITIZEN(COUNT)", "0", MainActivity.this, false);

                    finalString += receiptString("PWD", "0.00", MainActivity.this, false);
                    finalString += receiptString("PWD(COUNT)", "0", MainActivity.this, false);

                    finalString += receiptString("OTHERS", "0.00", MainActivity.this, false);


                }

                finalString += receiptString("", "", MainActivity.this, false);

                finalString += receiptString("------ END OF REPORT ------", "", MainActivity.this, true);
                finalString += receiptString("PRINTED DATE", "", MainActivity.this, true);
                finalString += receiptString(currentDateTime, "", MainActivity.this, true);
                finalString += receiptString("PRINTED BY:", userModel.getUsername(), MainActivity.this, true);



            } catch (JSONException e) {
                Log.d("ERROR", e.getMessage());
            }


        DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
        String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");
        String fileName = dtf.parseDateTime(currentDateTime).toString("yyyy_MM_dd_HH_mm_ss");

        try {
            File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, branchAndCode +"XREAD_"+fileName +".txt");
            FileWriter writer = null;
            writer = new FileWriter(gpxfile);

            writer.append(finalString);
//                writer.append("test data");

            writer.flush();

            writer.close();

        } catch (IOException e) {
            Log.d("ERRORMESSAGE", e.getMessage());
//                asyncFinishCallBack.doneProcessing();
        }
    }

    private void saveCashierReconcileToLocal(PrintModel printModel, UserModel userModel, String currentDateTime) {

        String finalString = "";

        finalString += receiptString("NERDVANA CORP.", "", MainActivity.this, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", MainActivity.this, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", MainActivity.this, true);
        finalString += receiptString("TEL NO: 8671-9782", "", MainActivity.this, true);
        finalString += receiptString("SERIAL NO: ********", "", MainActivity.this, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", MainActivity.this, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", MainActivity.this, true);

        finalString += receiptString("CASHIER RECONCILE", "", MainActivity.this, true);



        TypeToken<List<CollectionFinalPostModel>> collectionToken = new TypeToken<List<CollectionFinalPostModel>>() {};
        List<CollectionFinalPostModel> collectionDetails = GsonHelper.getGson().fromJson(printModel.getData(), collectionToken.getType());
        finalString += receiptString("BILLS", "", MainActivity.this, true);
        finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]), "", MainActivity.this, true);

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON))) {
            TypeToken<List<FetchDenominationResponse.Result>> collectionToken2 = new TypeToken<List<FetchDenominationResponse.Result>>() {};
            List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON), collectionToken2.getType());
            Double finalAmount = 0.00;
            for (FetchDenominationResponse.Result cfm : denoDetails) {
                String valueCount = "0";
                String valueAmount = "0.00";
                for (CollectionFinalPostModel c : collectionDetails) {
                    if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                        valueCount = c.getAmount();
                        valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                        break;
                    }
                }
                finalString += receiptString(String.format("%s  x %s", valueCount, cfm.getAmount()), valueAmount, MainActivity.this, false);
                finalAmount += Double.valueOf(valueAmount);
            }
            finalString += receiptString("", "", MainActivity.this, false);
            finalString += receiptString("CASH COUNT", String.valueOf(finalAmount), MainActivity.this, false);
            finalString += receiptString("CASH OUT", String.valueOf(0.00), MainActivity.this, false);
            finalString += receiptString("", "", MainActivity.this, false);
            finalString += receiptString("------------", "", MainActivity.this, true);
            finalString += receiptString("PRINTED DATE", "", MainActivity.this, true);
            finalString += receiptString(currentDateTime, "", MainActivity.this, true);
            finalString += receiptString("PRINTED BY:", userModel.getUsername(), MainActivity.this, true);



            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
            String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");
            String fileName = dtf.parseDateTime(currentDateTime).toString("yyyy_MM_dd_HH_mm_ss");

            try {
                File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, branchAndCode +"CR_"+fileName +".txt");
                FileWriter writer = null;
                writer = new FileWriter(gpxfile);

                writer.append(finalString);
//                writer.append("test data");

                writer.flush();

                writer.close();

            } catch (IOException e) {
                Log.d("ERRORMESSAGE", e.getMessage());
//                asyncFinishCallBack.doneProcessing();
            }


        }




    }

    private void saveSafeKeepToLocal(PrintModel printModel, UserModel userModel, String currentDateTime) {

        String finalString = "";

        finalString += receiptString("NERDVANA CORP.", "", MainActivity.this, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", MainActivity.this, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", MainActivity.this, true);
        finalString += receiptString("TEL NO: 8671-9782", "", MainActivity.this, true);
        finalString += receiptString("SERIAL NO: ********", "", MainActivity.this, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", MainActivity.this, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", MainActivity.this, true);

        finalString += receiptString("SAFEKEEPING", "", MainActivity.this, true);



        TypeToken<List<CollectionFinalPostModel>> collectionToken = new TypeToken<List<CollectionFinalPostModel>>() {};
        List<CollectionFinalPostModel> collectionDetails = GsonHelper.getGson().fromJson(printModel.getData(), collectionToken.getType());
        finalString += receiptString("BILLS", "", MainActivity.this, true);
        finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]), "", MainActivity.this, true);

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON))) {
            TypeToken<List<FetchDenominationResponse.Result>> collectionToken2 = new TypeToken<List<FetchDenominationResponse.Result>>() {};
            List<FetchDenominationResponse.Result> denoDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.CASH_DENO_JSON), collectionToken2.getType());
            Double finalAmount = 0.00;
            for (FetchDenominationResponse.Result cfm : denoDetails) {
                String valueCount = "0";
                String valueAmount = "0.00";
                for (CollectionFinalPostModel c : collectionDetails) {
                    if (c.getCash_denomination_id().equalsIgnoreCase(String.valueOf(cfm.getCoreId()))) {
                        valueCount = c.getAmount();
                        valueAmount = String.valueOf(Double.valueOf(c.getAmount()) * Double.valueOf(c.getCash_valued()));
                        break;
                    }
                }
                finalString += receiptString(String.format("%s  x %s", valueCount, cfm.getAmount()), valueAmount, MainActivity.this, false);
                finalAmount += Double.valueOf(valueAmount);
            }
            finalString += receiptString("", "", MainActivity.this, false);
            finalString += receiptString("CASH COUNT", String.valueOf(finalAmount), MainActivity.this, false);
            finalString += receiptString("CASH OUT", String.valueOf(0.00), MainActivity.this, false);
            finalString += receiptString("", "", MainActivity.this, false);
            finalString += receiptString("------------", "", MainActivity.this, true);
            finalString += receiptString("PRINTED DATE", "", MainActivity.this, true);
            finalString += receiptString(currentDateTime, "", MainActivity.this, true);
            finalString += receiptString("PRINTED BY:", userModel.getUsername(), MainActivity.this, true);



            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
            String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");
            String fileName = dtf.parseDateTime(currentDateTime).toString("yyyy_MM_dd_HH_mm_ss");

            try {
                File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, branchAndCode +"SK_"+fileName +".txt");
                FileWriter writer = null;
                writer = new FileWriter(gpxfile);

                writer.append(finalString);
//                writer.append("test data");

                writer.flush();

                writer.close();

            } catch (IOException e) {
                Log.d("ERRORMESSAGE", e.getMessage());
//                asyncFinishCallBack.doneProcessing();
            }


        }




    }

    private void saveDataToLocal(PrintModel printModel, UserModel userModel, String currentDateTime) {

        String finalString = "";
        String receiptNo = "NA";
        finalString += receiptString("NERDVANA CORP.", "", MainActivity.this, true);
        finalString += receiptString("1 CANLEY ROAD BRGY BAGONG", "", MainActivity.this, true);
        finalString += receiptString("ILOG PASIG CITY 1600", "", MainActivity.this, true);
        finalString += receiptString("TEL NO: 8671-9782", "", MainActivity.this, true);
        finalString += receiptString("SERIAL NO: ********", "", MainActivity.this, true);
        finalString += receiptString("VAT REG TIN NO: 009-772-500-000", "", MainActivity.this, true);
        finalString += receiptString("PERMIT NO: ********-***-******", "", MainActivity.this, true);



        finalString += receiptString("OFFICIAL RECEIPT", "", MainActivity.this, true);
        finalString += receiptString("", "", MainActivity.this, true);

        if (!printModel.getRoomNumber().equalsIgnoreCase("takeout")) {
            finalString += receiptString("ROOM #" + printModel.getRoomNumber(), "", MainActivity.this, true);
        } else {
            finalString += receiptString("TAKEOUT", "", MainActivity.this, true);
        }






        FetchOrderPendingViaControlNoResponse.Result toList1 = GsonHelper.getGson().fromJson(printModel.getData(), FetchOrderPendingViaControlNoResponse.Result.class)
                ;
        if (toList1 != null) {

            //region create receipt data
            finalString += receiptString("CASHIER", userModel.getUsername(), MainActivity.this, false);
            finalString += receiptString("ROOM BOY", String.valueOf(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getRoomBoy().getName() : "NA"), MainActivity.this, false);
            finalString += receiptString("CHECK IN", convertDateToReadableDate(toList1.getGuestInfo() != null ?toList1.getGuestInfo().getCheckIn() : "NA"), MainActivity.this, false);
            finalString += receiptString("CHECK OUT", convertDateToReadableDate(toList1.getGuestInfo() != null ? toList1.getGuestInfo().getCheckOut() : "NA"), MainActivity.this, false);

            if (toList1.getReceiptNo() == null) {
                receiptNo = "NA";
            } else {
                receiptNo = toList1.getReceiptNo().toString();
            }


            finalString += receiptString("OR NO", toList1.getReceiptNo() == null ? "NOT YET CHECKOUT" : toList1.getReceiptNo().toString(), MainActivity.this, false);
            finalString += receiptString("TERMINAL NO", SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MACHINE_ID), MainActivity.this, false);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);
            finalString += receiptString("QTY   DESCRIPTION         AMOUNT", "", MainActivity.this, true);
            finalString += receiptString(new String(new char[Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.MAX_COLUMN_COUNT))]).replace("\0", "-"), "", MainActivity.this, true);
            for (FetchOrderPendingViaControlNoResponse.Post soaTrans : toList1.getPost()) {
                if (soaTrans.getVoid() == 0) {
                    String qty = "";
                    String qtyFiller = "";

                    for (int i = 0; i < soaTrans.getQty(); i++) {
                        qtyFiller += " ";
                    }


                    qty += soaTrans.getQty();
                    if (String.valueOf(soaTrans.getQty()).length() < 4) {
                        for (int i = 0; i < 4 - String.valueOf(soaTrans.getQty()).length(); i++) {
                            qty += " ";
                            qtyFiller += " ";
                        }
                    } else {
                        qtyFiller = "    ";
                    }
                    String item = "";

                    if (soaTrans.getProductId() == 0) {
                        item =soaTrans.getRoomRate().toString();
                    } else {
                        item =soaTrans.getProduct().getProductInitial();
                    }

                    finalString += receiptString(qty + " " + item, returnWithTwoDecimal(String.valueOf(soaTrans.getPrice() * soaTrans.getQty())), MainActivity.this, false);


                    if (soaTrans.getFreebie() != null) {
                        if (soaTrans.getFreebie().getPostAlaCart().size() > 0) {
                            for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getFreebie().getPostAlaCart()) {

                                finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", MainActivity.this, false);

                            }
                        }

                        if (soaTrans.getFreebie().getPostGroup().size() > 0) {
                            for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getFreebie().getPostGroup()) {
                                for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                    finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", MainActivity.this, false);

                                }
                            }
                        }


                    }



                    if (soaTrans.getPostAlaCartList().size() > 0) {
                        for (FetchRoomPendingResponse.PostAlaCart palac : soaTrans.getPostAlaCartList()) {

                            finalString += receiptString("   "+palac.getQty()+ " "+palac.getPostAlaCartProduct().getProductInitial(), "", MainActivity.this, false);

                        }
                    }

                    if (soaTrans.getPostGroupList().size() > 0) {
                        for (FetchRoomPendingResponse.PostGroup postGroup : soaTrans.getPostGroupList()) {
                            for (FetchRoomPendingResponse.PostGroupItem pgi : postGroup.getPostGroupItems()) {

                                finalString += receiptString("   "+pgi.getQty()+ " "+ pgi.getPostGroupItemProduct().getProductInitial(), "", MainActivity.this, false);

                            }

                        }
                    }

                    if (soaTrans.getDiscounts().size() > 0) {
                        for (FetchOrderPendingViaControlNoResponse.PostObjectDiscount d : soaTrans.getDiscounts()) {
                            if (TextUtils.isEmpty(d.getDeleted_at())) {
                                String itemDiscount = "";
                                if (d.getDiscountPercentage().equalsIgnoreCase("0")) {
                                    itemDiscount = "LESS ";
                                } else {
                                    itemDiscount = "LESS "+d.getDiscountPercentage() + "%";
                                }

                                finalString += receiptString(qtyFiller+ " "+itemDiscount,"-" + returnWithTwoDecimal(String.valueOf(d.getDiscountAmount())) ,MainActivity.this, false);

                            }
                        }
                    }

                }
            }




            if (toList1.getOtHours() > 0) {

                finalString += receiptString(String.valueOf(toList1.getOtHours()) + " " + "OT HOURS",
                        returnWithTwoDecimal(String.valueOf(toList1.getOtAmount())), MainActivity.this, false);

            }

            if (Integer.valueOf(toList1.getPersonCount()) > 2) {


                finalString += receiptString(String.valueOf(Integer.valueOf(toList1.getPersonCount()) - 2) + " " + "EXTRA PERSON",
                        returnWithTwoDecimal(String.valueOf(toList1.getxPersonAmount())), MainActivity.this, false);

            }

            finalString += receiptString("", "", MainActivity.this, true);



            finalString += receiptString("NO OF PERSON/S", returnWithTwoDecimal(String.valueOf(toList1.getPersonCount())), MainActivity.this, false);

            finalString += receiptString("NO OF FOOD ITEMS", returnWithTwoDecimal(String.valueOf(toList1.getTotalQty())), MainActivity.this, false);

            finalString += receiptString("", "", MainActivity.this, true);


            finalString += receiptString("LESS", "", MainActivity.this, false);


            finalString += receiptString("   VAT EXEMPT", toList1.getVatExempt() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getVatExempt()))) : returnWithTwoDecimal(String.valueOf(toList1.getVatExempt())), MainActivity.this, false);

            finalString += receiptString("   DISCOUNT", toList1.getDiscount() > 0 ? String.format("-%s",returnWithTwoDecimal(String.valueOf(toList1.getDiscount()))) : returnWithTwoDecimal(String.valueOf(toList1.getDiscount())), MainActivity.this, false);

//            finalString += receiptString("   ADVANCED DEPOSIT", toList1.getAdvance() > 0 ? String.format("-%s", returnWithTwoDecimal(String.valueOf(toList1.getAdvance()))) : returnWithTwoDecimal(String.valueOf(toList1.getAdvance())), MainActivity.this, false);



            finalString += receiptString("", "", MainActivity.this, true);


//            bookedList.get(0).getTransaction().getTotal() + bookedList.get(0).getTransaction().getOtAmount() + bookedList.get(0).getTransaction().getXPersonAmount()


            finalString += receiptString("SUB TOTAL", returnWithTwoDecimal(String.valueOf((toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount()))), MainActivity.this, false);



            finalString += receiptString("AMOUNT DUE", returnWithTwoDecimal(String.valueOf(
                    (toList1.getTotal() + toList1.getOtAmount() + toList1.getxPersonAmount())
                            - (toList1.getAdvance() + toList1.getDiscount() + toList1.getVatExempt()))), MainActivity.this, false);


            finalString += receiptString("TENDERED", returnWithTwoDecimal(String.valueOf(toList1.getTendered())), MainActivity.this, false);


            finalString += receiptString("CHANGE", returnWithTwoDecimal(String.valueOf((toList1.getChange() < 0 ? toList1.getChange() * -1 : toList1.getChange()))), MainActivity.this, false);


            finalString += receiptString("", "", MainActivity.this, true);

            List<Integer> tmpArr = new ArrayList<>();
            String pymType = "";
            List<String> ccardArray = new ArrayList<>();
            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (!tmpArr.contains(pym.getPaymentTypeId())) {
                    tmpArr.add(pym.getPaymentTypeId());
                    pymType = pym.getPaymentDescription();
                }

                if (pym.getPaymentTypeId() == 2) {
                    if (pym.getCardDetail() != null) {
                        if (!pym.getCardDetail().getCardNumber().trim().isEmpty()) {
                            int starCount = 0;
                            String finalData = "";
                            if (pym.getCardDetail().getCardNumber().length() < 3) {
                                finalData += pym.getCardDetail().getCardNumber();
                            } else {
                                starCount = pym.getCardDetail().getCardNumber().length() - 3;
                                finalData += new String(new char[starCount]).replace("\0", "*");
                                finalData += pym.getCardDetail().getCardNumber().substring(starCount);
                            }

                            if (pym.getCardDetail().getCreditCardId().equalsIgnoreCase("1")) {

                                finalString += receiptString("MASTER", "", MainActivity.this, false);

                            } else {
                                finalString += receiptString("VISA", "", MainActivity.this, false);

                            }

                            finalString += receiptString(pym.getPaymentDescription(), finalData, MainActivity.this, false);

                        }
                    }
                }
            }


//            finalString += receiptString("PAYMENT TYPE", tmpArr.size() > 1 ? "MULTIPLE" : pymType, MainActivity.this, false);



            finalString += receiptString("", "", MainActivity.this, true);



            finalString += receiptString("VATABLE SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatable())), MainActivity.this, false);

            finalString += receiptString("VAT-EXEMPT SALES", returnWithTwoDecimal(String.valueOf(toList1.getVatExemptSales())), MainActivity.this, false);

            finalString += receiptString("12% VAT", returnWithTwoDecimal(String.valueOf(toList1.getVat())), MainActivity.this, false);


            finalString += receiptString("", "", MainActivity.this, true);


            for (FetchOrderPendingViaControlNoResponse.Payment pym : toList1.getPayments()) {
                if (pym.getIsAdvance() == 1) {

                    finalString += receiptString(pym.getPaymentDescription(), returnWithTwoDecimal(String.valueOf(pym.getAmount())), MainActivity.this, false);

                }
            }

            boolean hasSpecial = false;
            List<SeniorReceiptCheckoutModel> seniorReceiptList = new ArrayList<>();
            if (toList1.getDiscountsList().size() > 0) {
                finalString += receiptString("DISCOUNT LIST", "", MainActivity.this, true);

                for (FetchOrderPendingViaControlNoResponse.Discounts d : toList1.getDiscountsList()) {


                    if (TextUtils.isEmpty(d.getVoid_by())) {
                        if (d.getId().equalsIgnoreCase("0")) { //MANUAL

                        } else {

                            if (d.getInfo() != null) {

//                                if (d.getDiscountTypes().getIsSpecial() == 1) {
//                                    hasSpecial = true;
//                                    seniorReceiptList.add(
//                                            new SeniorReceiptCheckoutModel(
//                                                    d.getInfo().getName() == null ? "" : d.getInfo().getName(),
//                                                    d.getInfo().getCardNo() == null ? "" : d.getInfo().getCardNo(),
//                                                    d.getInfo().getAddress() == null ? "" : d.getInfo().getAddress(),
//                                                    d.getInfo().getTin() == null ? "" : d.getInfo().getTin(),
//                                                    d.getInfo().getBusinessStyle() == null ? "" : d.getInfo().getBusinessStyle()
//                                            )
//                                    );
//                                }

                                if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                    finalString += receiptString(d.getDiscountType(), "NA", MainActivity.this, false);


                                } else {

                                    if (d.getInfo().getCardNo() == null && d.getInfo().getName() == null) {

                                        finalString += receiptString(d.getDiscountType(), "NA", MainActivity.this, false);


                                    } else {
                                        if (d.getInfo().getCardNo() != null) {

                                            finalString += receiptString(d.getDiscountType(), d.getInfo().getCardNo().toUpperCase(), MainActivity.this, false);


                                        }

                                        if (d.getInfo().getName() != null) {

                                            finalString += receiptString("NAME", d.getInfo().getName().toUpperCase(), MainActivity.this, false);

                                        }
                                    }


                                }

                            }
                        }
                    }
                }

            }


            finalString += receiptString("", "", MainActivity.this, true);

            finalString += receiptString("THIS IS NOT AN OFFICIAL RECEIPT", "", MainActivity.this, true);

            finalString += receiptString("", "", MainActivity.this, true);


            if (toList1.getCustomer() != null) {
                if (!toList1.getCustomer().getCustomer().equalsIgnoreCase("EMPTY") && !toList1.getCustomer().getCustomer().equalsIgnoreCase("To be filled")) {

                    finalString += receiptString("", "", MainActivity.this, true);

                    finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", MainActivity.this, true);


                    finalString += receiptString("NAME:"+toList1.getCustomer().getCustomer(), "", MainActivity.this, true);

                    if (toList1.getCustomer().getAddress() != null) {

                        finalString += receiptString("ADDRESS:"+toList1.getCustomer().getAddress(), "", MainActivity.this, true);

                    } else {
                        finalString += receiptString("ADDRESS:________________________", "", MainActivity.this, true);

                    }

                    if (toList1.getCustomer().getTin() != null) {
                        finalString += receiptString("TIN#:"+toList1.getCustomer().getTin(), "", MainActivity.this, true);

                    } else {

                        finalString += receiptString("TIN#:___________________________", "", MainActivity.this, true);

                    }

                    if (toList1.getCustomer().getBusinessStyle() != null) {
                        finalString += receiptString("BUSINESS STYLE:"+ toList1.getCustomer().getBusinessStyle(), "", MainActivity.this, true);


                        finalString += receiptString(toList1.getCustomer().getBusinessStyle(), "", MainActivity.this, true);

                    } else {
                        finalString += receiptString("BUSINESS STYLE:_________________", "", MainActivity.this, true);

                    }



                    finalString += receiptString("", "", MainActivity.this, true);


                } else {
                    finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", MainActivity.this, true);
                    finalString += receiptString("NAME:___________________________", "", MainActivity.this, true);
                    finalString += receiptString("ADDRESS:________________________", "", MainActivity.this, true);
                    finalString += receiptString("TIN#:___________________________", "", MainActivity.this, true);
                    finalString += receiptString("BUSINESS STYLE:_________________", "", MainActivity.this, true);

                }
            } else {
                finalString += receiptString("THIS RECEIPT IS ISSUED TO", "", MainActivity.this, true);
                finalString += receiptString("NAME:___________________________", "", MainActivity.this, true);
                finalString += receiptString("ADDRESS:________________________", "", MainActivity.this, true);
                finalString += receiptString("TIN#:___________________________", "", MainActivity.this, true);
                finalString += receiptString("BUSINESS STYLE:_________________", "", MainActivity.this, true);

            }

            finalString += receiptString("", "", MainActivity.this, true);


            finalString += receiptString("", "", MainActivity.this, true);

            finalString += receiptString("Thank you come again", "", MainActivity.this, true);

            finalString += receiptString("----- SYSTEM PROVIDER DETAILS -----", "", MainActivity.this, true);
            finalString += receiptString("POS Provider : NERDVANA CORP.", "", MainActivity.this, true);
            finalString += receiptString("Address : 1 CANLEY ROAD BRGY", "", MainActivity.this, true);
            finalString += receiptString("BAGONG ILOG PASIG CITY", "", MainActivity.this, true);
            finalString += receiptString("VAT REG TIN: 009-772-500-000", "", MainActivity.this, true);
            finalString += receiptString("ACCRED NO:**********************", "", MainActivity.this, true);
            finalString += receiptString("Date Issued : " + toList1.getCreatedAt(), "", MainActivity.this, true);
            finalString += receiptString("Valid Until : " + PrinterUtils.yearPlusFive(toList1.getCreatedAt()), "", MainActivity.this, true);

            finalString += receiptString("", "", MainActivity.this, true);

            finalString += receiptString("THIS RECEIPT SHALL BE VALID FOR", "", MainActivity.this, true);

            finalString += receiptString("FIVE(5) YEARS FROM THE DATE OF", "", MainActivity.this, true);

            finalString += receiptString("THE PERMIT TO USE", "", MainActivity.this, true);

            finalString += receiptString("", "", MainActivity.this, true);



            //endregion



            DateTimeFormatter dtf = DateTimeFormat.forPattern("EEEE, MMMM d, yyyy hh:mm:ss a");
            String folderName = dtf.parseDateTime(currentDateTime).toString("yyyy-MM-dd");

            try {
                File root = new File(Environment.getExternalStorageDirectory(), "POS/"+folderName);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, branchAndCode +"OR_"+receiptNo +".txt");
                FileWriter writer = null;
                writer = new FileWriter(gpxfile);

                writer.append(finalString);
//                writer.append("test data");

                writer.flush();

                writer.close();

            } catch (IOException e) {
                Log.d("ERRORMESSAGE", e.getMessage());
//                asyncFinishCallBack.doneProcessing();
            }




        } else {
            Log.d("DATANUL"," DATAI SNULL");
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

        Log.d("ZCXZXC", progressBarModel.getMessage());

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
                ApplicationConstants.IS_THEME_CHANGED = "T";
                SharedPreferenceManager
                        .saveString(
                                MainActivity.this,
                                null,
                                "room_no_list");

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
        if (safeKeepAmount != 0) {
            if (checkSafeKeepingResponse.getResult().getUnCollected() >= safeKeepAmount) {


                if (!ApplicationConstants.IS_ACTIVE.equalsIgnoreCase("T")) {
                    if (collectionDialog == null) {
                        ApplicationConstants.IS_ACTIVE = "T";
                        collectionDialog = new CollectionDialog(MainActivity.this, "SAFEKEEPING", false) {
                            @Override
                            public void printCashRecoData(String cashNRecoData) {

                            }
                        };

                        collectionDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                collectionDialog = null;
                                ApplicationConstants.IS_ACTIVE = "F";
                            }
                        });

                        collectionDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                collectionDialog = null;
                                ApplicationConstants.IS_ACTIVE = "F";
                            }
                        });

                        if (!collectionDialog.isShowing()) collectionDialog.show();
                    }
                }
            }
        }
    }

    @Subscribe
    public void updateTime(TimerModel timerModel) {

        currentDateTime = timerModel.getTime();
        timer.setText(timerModel.getTime());

        user.setText(currentText);
//        role.setText(String.format("%s SHIFT : %s", userModel.getUserGroup().toUpperCase(), timerModel.getShiftNumber()));
        role.setText(String.format("%s SHIFT : %s", "CASHIER", timerModel.getShiftNumber()));
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
            case "create_receipt":
                CreateReceiptAsync createReceiptAsync = (CreateReceiptAsync) asyncTask;
                createReceiptAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "spot_audit":
                SpotAuditAsync spotAuditAsync = (SpotAuditAsync) asyncTask;
                spotAuditAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
            case "change_qty":
                ChangeQtyAsync changeQtyAsync = (ChangeQtyAsync) asyncTask;
                changeQtyAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
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
            case "reprint_checkout":
                CheckOutAsync reprintAsync = (CheckOutAsync) asyncTask;
                reprintAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
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

    private void changeMachine() {
        if (ApplicationConstants.IS_MACHINE_CHANGED.equalsIgnoreCase("T")) {
            BusProvider.getInstance().post(new MachineChangeRefresh(""));
            ApplicationConstants.IS_MACHINE_CHANGED = "F";
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


        if (ApplicationConstants.IS_THEME_CHANGED.equalsIgnoreCase("T")) {

        }

    }

    private void lightTheme() {
        ApplicationConstants.IS_THEME_CHANGED = "F";
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
        ApplicationConstants.IS_THEME_CHANGED = "T";
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

    @Subscribe
    public void openWakeUpCallDialog(OpenWakeUpCallDialog openWakeUpCallDialog) {

        if (dialogWakeUpCall == null) {
            dialogWakeUpCall = new DialogWakeUpCall(MainActivity.this, openWakeUpCallDialog.getWakeUpCallModels());

            dialogWakeUpCall.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialogWakeUpCall = null;
                }
            });

            dialogWakeUpCall.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialogWakeUpCall = null;
                }
            });

            if (!dialogWakeUpCall.isShowing()) {
                dialogWakeUpCall.show();
            }

        }
    }

    private String receiptString(String partOne, String partTwo, Context context, boolean isCenter) {
        String finalString = "";
        int filler = 0;
        int maxColumnDivideTwo = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) / 2);

        if (isCenter) {
            if (partOne.length() > Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT))) {
                finalString = partOne.substring(0, Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)));
            } else {
                int custFillter = (Integer.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.MAX_COLUMN_COUNT)) - partOne.length()) / 2;
                finalString = repeat(" ", custFillter) + partOne + repeat(" ", custFillter);
            }

        } else {

            if (partOne.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partOne.length());
            }
            if (partTwo.length() < maxColumnDivideTwo) {
                filler += (maxColumnDivideTwo - partTwo.length());
            }
            finalString = (partOne.length() >= maxColumnDivideTwo ? partOne.substring(0, maxColumnDivideTwo) : partOne)
                    + repeat(" ", filler)
                    + (partTwo.length() >= maxColumnDivideTwo ? partTwo.substring(0, maxColumnDivideTwo) : partTwo);

        }



        return finalString + "\n";
    }

    private static String repeat(String str, int i){
        return new String(new char[i]).replace("\0", str);
    }

    @Subscribe
    public void listenToServerConnection(ServerConnectionModel serverConnectionModel) {

        Log.d("MYDATARES", String.valueOf(serverConnectionModel.isCanConnect()));

        if (serverConnectionModel.isCanConnect()) {
            onlineTextIndicator.setText("ONLINE");
            onlineImageIndicator.setBackground(getResources().getDrawable(R.drawable.circle_online));
        } else {
            onlineTextIndicator.setText("OFFLINE");
            onlineImageIndicator.setBackground(getResources().getDrawable(R.drawable.circle_offline));
        }
    }
}

