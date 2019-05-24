package nerdvana.com.pointofsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.SettingListAdapter;
import nerdvana.com.pointofsales.fragment.PrinterConnectionFragment;
import nerdvana.com.pointofsales.fragment.PrinterFragment;
import nerdvana.com.pointofsales.fragment.ReceiptSetupFragment;
import nerdvana.com.pointofsales.fragment.ThemeFragment;
import nerdvana.com.pointofsales.model.ListSettingMenu;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView listMainItems;

    private SettingListAdapter settingListAdapter;
    private List<ListSettingMenu> settingMenuList;

    private PrinterFragment printerFragment;
    private PrinterConnectionFragment printerConnectionFragment;
    private ThemeFragment themeFragment;
    private ReceiptSetupFragment receiptSetupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        printerFragment = new PrinterFragment();
        printerConnectionFragment = new PrinterConnectionFragment();
        themeFragment = new ThemeFragment();
        receiptSetupFragment = new ReceiptSetupFragment();

        listMainItems = findViewById(R.id.listMainItems);

        setTitle("SETTINGS");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        settingMenuList = new ArrayList<>();
        settingMenuList.add(new ListSettingMenu(0, R.mipmap.baseline_print_black_24, "Printer Model"));
        settingMenuList.add(new ListSettingMenu(1, R.mipmap.baseline_print_black_24, "Printer Connection"));
        settingMenuList.add(new ListSettingMenu(2, R.mipmap.baseline_print_black_24, "Theme"));
        settingMenuList.add(new ListSettingMenu(3, R.mipmap.baseline_print_black_24, "Receipt Setup"));


        Setting setting = new Setting() {
            @Override
            public void clicked(int position) {
                switch (settingMenuList.get(position).getId()) {
                    case 0: //PRINTER DEVICE
                        openFragment(printerFragment);
                        break;
                    case 1: //PRINTER CONNECTION
                        openFragment(printerConnectionFragment);
                        break;
                    case 2: //THEME
                        openFragment(themeFragment);
                        break;
                    case 3: //RECEIPT SETUP
                        openFragment(receiptSetupFragment);
                        break;
                }
            }
        };


        settingListAdapter = new SettingListAdapter(settingMenuList, setting);
        LinearLayoutManager llm = new LinearLayoutManager(SettingsActivity.this);
        listMainItems.setLayoutManager(llm);
        listMainItems.setAdapter(settingListAdapter);
        settingListAdapter.notifyDataSetChanged();


        openFragment(printerFragment);
    }

    public interface Setting {
        void clicked(int position);
    }

    private void openFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.settingFrame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
