package nerdvana.com.pointofsales;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import nerdvana.com.pointofsales.api_requests.FetchRoomStatusRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;
import nerdvana.com.pointofsales.background.RoomStatusAsync;
import nerdvana.com.pointofsales.entities.CurrentTransactionEntity;
import nerdvana.com.pointofsales.entities.RoomStatusEntity;
import nerdvana.com.pointofsales.interfaces.PreloginContract;
import nerdvana.com.pointofsales.interfaces.SelectionContract;
import nerdvana.com.pointofsales.model.FragmentNotifierModel;
import nerdvana.com.pointofsales.model.RoomTableModel;
import nerdvana.com.pointofsales.model.UserModel;
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
    private TextView user;

    private UserModel userModel;

    private List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        initializeFragments();

        decideViewToShow();

        BusProvider.getInstance().post(new TestRequest("test"));

        requestRoomStatusList();

        Log.d("TAG", "SERIAL: " + Build.SERIAL);
        Log.d("TAG","MODEL: " + Build.MODEL);
        Log.d("TAG","ID: " + Build.ID);
        Log.d("TAG","Manufacture: " + Build.MANUFACTURER);
        Log.d("TAG","brand: " + Build.BRAND);
        Log.d("TAG","type: " + Build.TYPE);
        Log.d("TAG","user: " + Build.USER);
        Log.d("TAG","BASE: " + Build.VERSION_CODES.BASE);
        Log.d("TAG","INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.d("TAG","SDK  " + Build.VERSION.SDK);
        Log.d("TAG","BOARD: " + Build.BOARD);
        Log.d("TAG","BRAND " + Build.BRAND);
        Log.d("TAG","HOST " + Build.HOST);
        Log.d("TAG","FINGERPRINT: "+Build.FINGERPRINT);
        Log.d("TAG","Version Code: " + Build.VERSION.RELEASE);

    }

    private void initializeViews() {
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
        showMap = findViewById(R.id.showMap);
        showMap.setOnClickListener(this);
        user = findViewById(R.id.user);
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
                user.setText(String.format("%s %s", getResources().getString(R.string.welcome_text), userModel.getUsername()));
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
        }
    }

    @SuppressLint("NewApi")
    private void showListMenu(View anchor) {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Logout");
        map.put("icon", R.drawable.ic_launcher_background);
        data.add(map);

        ListPopupWindow popupWindow = new ListPopupWindow(this);

        ListAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.activity_list_item, // You may want to use your own cool layout
                new String[] {"title", "icon"}, // These are just the keys that the data uses
                new int[] {android.R.id.text1, android.R.id.icon}); // The view ids to map the data to


        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (data.get(position).get("title").toString().toLowerCase()) {
                    case "logout":
                        SharedPreferenceManager.clearPreference(getApplicationContext());
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10) {
                RoomTableModel selected = GsonHelper.getGson().fromJson(data.getStringExtra("selected"), RoomTableModel.class);

//                saveSelectedSpace(selected.getName());

                Log.d("TESTTEST", "ACT RESULT");

                CurrentTransactionEntity.deleteAll(CurrentTransactionEntity.class);
                CurrentTransactionEntity currentTransactionEntity = new
                        CurrentTransactionEntity( selected.getName());

                currentTransactionEntity.save();
                BusProvider.getInstance().post(new FragmentNotifierModel(selected.getName()));

                Toast.makeText(this, selected.getName() + " selected", Toast.LENGTH_SHORT).show();
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
    }
}
