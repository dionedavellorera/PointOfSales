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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.eposprint.Print;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import nerdvana.com.pointofsales.api_requests.FetchArOnlineRequest;
import nerdvana.com.pointofsales.api_requests.FetchCreditCardRequest;
import nerdvana.com.pointofsales.api_requests.FetchCurrencyExceptDefaultRequest;
import nerdvana.com.pointofsales.api_requests.FetchDefaultCurrencyRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomAreaRequest;
import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_requests.FetchUserRequest;
import nerdvana.com.pointofsales.api_responses.CheckInResponse;
import nerdvana.com.pointofsales.api_responses.FetchArOnlineResponse;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.api_responses.FetchCurrencyExceptDefaultResponse;
import nerdvana.com.pointofsales.api_responses.FetchDefaultCurrenyResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomAreaResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.api_responses.FetchUserResponse;
import nerdvana.com.pointofsales.api_responses.PrintSoaResponse;
import nerdvana.com.pointofsales.background.RoomStatusAsync;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.PrintModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
import nerdvana.com.pointofsales.model.VoidProductModel;
import nerdvana.com.pointofsales.postlogin.BottomFrameFragment;
import nerdvana.com.pointofsales.prelogin.LeftFrameFragment;
import nerdvana.com.pointofsales.prelogin.RightFrameFragment;
import nerdvana.com.pointofsales.requests.TestRequest;

public class MainActivity extends AppCompatActivity implements PreloginContract, View.OnClickListener {

    public static String roomNumber;

    private SelectionContract centralInterface;

    private LeftFrameFragment preLoginLeftFrameFragment;
    private RightFrameFragment preLoginRightFrameFragment;
    private nerdvana.com.pointofsales.postlogin.LeftFrameFragment postLoginLeftFrameFragment;
    private nerdvana.com.pointofsales.postlogin.RightFrameFragment postLoginRightFrameFragment;

    private Button logout;
    private Button showMap;
    private Button showTakeOuts;
    private TextView user;

    private UserModel userModel;

    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initializeViews();
        initializeFragments();

        decideViewToShow();
        fetchRoomAreaRequest();
        fetchUserListRequest();
        BusProvider.getInstance().post(new TestRequest("test"));

        requestRoomStatusList();
        fetchDefaultCurrencyRequest();
//
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

    private void initializeViews() {
        logout = findViewById(R.id.logout);
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

        postLoginLeftFrameFragment = nerdvana.com.pointofsales.postlogin.LeftFrameFragment.newInstance(centralInterface);
        postLoginRightFrameFragment = nerdvana.com.pointofsales.postlogin.RightFrameFragment.newInstance();
    }

    private void decideViewToShow() {
        userModel = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(this, ApplicationConstants.userSettings), UserModel.class);

        openFragment(R.id.bottomFrame, new BottomFrameFragment());

        if (userModel != null) {
            if (userModel.isLoggedIn()) { //post login
                logout.setVisibility(View.VISIBLE);
                user.setVisibility(View.VISIBLE);
                user.setText(String.format("%s %s - %s", getResources().getString(R.string.welcome_text), userModel.getUsername(), userModel.getUserGroup()));
                openFragment(R.id.leftFrame, postLoginLeftFrameFragment);

                openFragment(R.id.rightFrame, postLoginRightFrameFragment);
            } else { //pre login
                logout.setVisibility(View.GONE);
                user.setVisibility(View.GONE);
                openFragment(R.id.leftFrame, preLoginLeftFrameFragment);
                openFragment(R.id.rightFrame, preLoginRightFrameFragment);
            }
        } else { //pre login
            logout.setVisibility(View.GONE);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10) {
                RoomTableModel selected = GsonHelper.getGson().fromJson(data.getStringExtra("selected"), RoomTableModel.class);

//                CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
//                CurrentTransactionEntity currentTransactionEntity = new
//                CurrentTransactionEntity( selected.getName(), selected.getAmountSelected());
//
//                currentTransactionEntity.save();


                BusProvider.getInstance().post(new FragmentNotifierModel(selected));

                Toast.makeText(this, selected.getName() + " selected", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 20) {
                RoomTableModel selected = GsonHelper.getGson().fromJson(data.getStringExtra("selected"), RoomTableModel.class);
                Toast.makeText(getApplicationContext(), "ORDER SELECTED", Toast.LENGTH_SHORT).show();
                BusProvider.getInstance().post(new FragmentNotifierModel(selected));
            }
        } else {
            Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show();
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
        //regionheader
        addTextToPrinter(SPrinter.getPrinter(), "PANORAMA ENTERPRISE INC", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
        addTextToPrinter(SPrinter.getPrinter(), "ESCARPMENT ROAD BARANGAY BAGONG", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "ILOG PASIG CITY", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "671-9782", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "SERIAL NO: PC001LTR", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1,1);
        addTextToPrinter(SPrinter.getPrinter(), "VAT REG TIN NO: " + SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.TIN_NUMBER), Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
        addTextToPrinter(SPrinter.getPrinter(), "PERMIT NO: FP082015-43A-0046941-00000", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1 ,1 );
        addTextToPrinter(SPrinter.getPrinter(), "MIN NO: 15080516005415409", Printer.FALSE, Printer.FALSE, Printer.ALIGN_CENTER, 2,1 ,1 );
        addTextToPrinter(SPrinter.getPrinter(), printModel.getRoomNumber(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 2,2,2);
        //endregion

        switch (printModel.getType()) {
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
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
//
                addTextToPrinter(SPrinter.getPrinter(), "SOA-TAKE OUT SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description                     Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                qty+ " "+item,
                                String.valueOf(soaTrans.getTotal())
                                ,
                                45,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }
                }
//
                addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   VAT EXEMPT",
                        String.valueOf(toList.getVatExempt()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   DISCOUNT",
                        String.valueOf(toList.getDiscount()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   ADVANCED DEPOSIT",
                        String.valueOf(toList.getAdvance()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "AMOUNT DUE",
                        String.valueOf(toList.getTotal()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
//
                addPrinterSpace(1);
//
                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        toList.getControlNumber(),
                        toList.getSoaCount(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
//
                addPrinterSpace(1);
//
                addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);


                break;


            case "CHECKIN":

                TypeToken<List<CheckInResponse.Booked>> checkInToken = new TypeToken<List<CheckInResponse.Booked>>() {};
                List<CheckInResponse.Booked> checkinDetails = GsonHelper.getGson().fromJson(printModel.getData(), checkInToken.getType());
                addTextToPrinter(SPrinter.getPrinter(), "CHECK IN RECEIPT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);


                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "Room Type",
                        checkinDetails.get(0).getRoomType(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "RATE DESC.",
                        checkinDetails.get(0).getRoomRate(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "STARTING RATE",
                        checkinDetails.get(0).getRateRoom().getRoomRate(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "DATE / TIME",
                        checkinDetails.get(0).getCreatedAt(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "VEHICLY TYPE",
                        "VEHICLE TYPE",
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "CAR MAKE",
                        checkinDetails.get(0).getCar().getCarMake(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "PLATE NUMBER",
                        checkinDetails.get(0).getPlateNo(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                break;
            case "VOID":

                TypeToken<List<VoidProductModel>> voidToken = new TypeToken<List<VoidProductModel>>() {};
                List<VoidProductModel> voidList = GsonHelper.getGson().fromJson(printModel.getData(), voidToken.getType());

                addTextToPrinter(SPrinter.getPrinter(), "VOID SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description                     Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(qty+ vpm.getName(), vpm.getPrice(), 45, 2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                }

                addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(voidTotalAmount), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);

                break;
            case "SOA-ROOM":
                TypeToken<List<PrintSoaResponse.Booked>> bookedToken = new TypeToken<List<PrintSoaResponse.Booked>>() {};
                List<PrintSoaResponse.Booked> bookedList = GsonHelper.getGson().fromJson(printModel.getData(), bookedToken.getType());

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "CASHIER",
                        String.valueOf(bookedList.get(0).getUserId()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), bookedList.get(0).getCreatedAt(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1, 1, 1);
                addPrinterSpace(1);
                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "MACHINE NO",
                        SharedPreferenceManager.getString(getApplicationContext(), ApplicationConstants.MACHINE_ID),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "CHECKED-IN",
                        bookedList.get(0).getCheckIn(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "CHECKED-OUT",
                        "EK EK",
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "DURATION",
                        "TO DO",
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


//                Double soaTotal = 0.00;
                addTextToPrinter(SPrinter.getPrinter(), "SOA SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description                     Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
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
                        addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                                qty+ " "+item,
                                String.valueOf(soaTrans.getPrice())
                                ,
                                45,
                                2),
                                Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                    }
                }

                addTextToPrinter(SPrinter.getPrinter(), "LESS", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   VAT EXEMPT",
                        String.valueOf(bookedList.get(0).getTransaction().getVatExempt()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   DISCOUNT",
                        String.valueOf(bookedList.get(0).getTransaction().getDiscount()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "   ADVANCED DEPOSIT",
                        String.valueOf(bookedList.get(0).getTransaction().getAdvance()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        "AMOUNT DUE",
                        String.valueOf(bookedList.get(0).getTransaction().getTotal()),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);


                addPrinterSpace(1);

                addTextToPrinter(SPrinter.getPrinter(), twoColumns(
                        bookedList.get(0).getTransaction().getControlNo(),
                        bookedList.get(0).getTransaction().getSoaCount(),
                        45,
                        2)
                        ,Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

                addPrinterSpace(1);

                addTextToPrinter(SPrinter.getPrinter(), "STATEMENT OF ACCOUNT", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);


                break;
            case "FO":
                Double totalAmount = 0.00;
                addTextToPrinter(SPrinter.getPrinter(), "FO ORDER SLIP", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 2, 1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "QTY   Description                     Amount", Printer.TRUE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "------------------------------------------------", Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);

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
                    addTextToPrinter(SPrinter.getPrinter(), twoColumns(qty+ r.getProduct_initial(), r.getPrice(), 45, 2), Printer.FALSE, Printer.FALSE, Printer.ALIGN_LEFT, 1,1,1);
                }

                addTextToPrinter(SPrinter.getPrinter(), "TOTAL: " + String.valueOf(totalAmount), Printer.TRUE, Printer.FALSE, Printer.ALIGN_RIGHT, 1,1,1);

                addTextToPrinter(SPrinter.getPrinter(), "------------", Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1,1,1);
                addTextToPrinter(SPrinter.getPrinter(), "Printed by: " + userModel.getUsername(), Printer.TRUE, Printer.FALSE, Printer.ALIGN_CENTER, 1, 1, 1);
                break;
        }
        try {

            SPrinter.getPrinter().addCut(Printer.CUT_FEED);
            SPrinter.getPrinter().sendData(Printer.PARAM_DEFAULT);

            SPrinter.getPrinter().clearCommandBuffer();

        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    private String twoColumns(String partOne, String partTwo, int maxTextCountPerLine, int columns) {
        String finalString = "";
        float countPerColumn = maxTextCountPerLine / columns;

        double column1Length =  Math.ceil(partOne.length() / (countPerColumn + 8));
        double column2Length =  Math.ceil(partTwo.length() / countPerColumn);
        for (int i = 0; i < column1Length; i++) {
            if (i == column1Length - 1) {
                String tmpLast = partOne.substring(
                        i * (int)countPerColumn);
                finalString += partOne.substring(
                        i * (int)countPerColumn);
                for (int j = 0; j < (countPerColumn -tmpLast.length())+ 8; j++) {
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

    private void addTextToPrinter(Printer printer, String text,
                                  int isBold, int isUnderlined,
                                  int alignment, int feedLine,
                                  int textSizeWidth, int textSizeHeight) {
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

    private void loadPrinter() {
        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_PORT))) {
            SPrinter printer = new SPrinter(
                    Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_PRINTER)),
                    Integer.valueOf(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_LANGUAGE)),
                    getApplicationContext());

            try {
                if (SPrinter.getPrinter() != null) {
                    SPrinter.getPrinter().connect(SharedPreferenceManager.getString(MainActivity.this, ApplicationConstants.SELECTED_PORT), Printer.PARAM_DEFAULT);
                    SPrinter.getPrinter().beginTransaction();
                } else {
                    Toast.makeText(MainActivity.this, "No Printer", Toast.LENGTH_SHORT).show();
                }

            } catch (Epos2Exception e) {
                e.printStackTrace();
            }

//            try {
//
//            } catch (Epos2Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private void addPrinterSpace(int count) {
        try {
            SPrinter.getPrinter().addFeedLine(count);
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

}
