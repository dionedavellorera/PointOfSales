package nerdvana.com.pointofsales;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.adapters.SettingListAdapter;
import nerdvana.com.pointofsales.fragment.MachineSetupFragment;
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
    private MachineSetupFragment machineSetupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        printerFragment = new PrinterFragment();
        printerConnectionFragment = new PrinterConnectionFragment();
        themeFragment = new ThemeFragment();
        receiptSetupFragment = new ReceiptSetupFragment();
        machineSetupFragment = new MachineSetupFragment();

        listMainItems = findViewById(R.id.listMainItems);

        setTitle("Settings");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        settingMenuList = new ArrayList<>();
        settingMenuList.add(new ListSettingMenu(0, R.mipmap.baseline_print_black_24, "Printer Model", true));
        settingMenuList.add(new ListSettingMenu(1, R.mipmap.baseline_print_black_24, "Printer Connection", false));
        settingMenuList.add(new ListSettingMenu(2, R.mipmap.baseline_print_black_24, "Theme", false));
        settingMenuList.add(new ListSettingMenu(3, R.mipmap.baseline_print_black_24, "Receipt Setup", false));
        settingMenuList.add(new ListSettingMenu(4, R.mipmap.baseline_print_black_24, "Machine Setup", false));


        Setting setting = new Setting() {
            @Override
            public void clicked(int position) {
                switch (settingMenuList.get(position).getId()) {
                    case 0: //PRINTER DEVICE
                        openFragment(printerFragment, settingMenuList.get(position));
//                        settingMenuList.get(position).setClicked(true);
                        break;
                    case 1: //PRINTER CONNECTION
                        openFragment(printerConnectionFragment, settingMenuList.get(position));
                        break;
                    case 2: //THEME
                        openFragment(themeFragment, settingMenuList.get(position));
                        break;
                    case 3: //RECEIPT SETUP
                        openFragment(receiptSetupFragment, settingMenuList.get(position));
                        break;
                    case 4: //MACHINE SETUP
                        openFragment(machineSetupFragment, settingMenuList.get(position));
                        break;
                }
            }
        };


        settingListAdapter = new SettingListAdapter(settingMenuList, setting);
        LinearLayoutManager llm = new LinearLayoutManager(SettingsActivity.this);
        listMainItems.setLayoutManager(llm);
        listMainItems.setAdapter(settingListAdapter);
        settingListAdapter.notifyDataSetChanged();


        openFragment(printerFragment, settingMenuList.get(0));
    }

    private void refreshSelected(int id) {
        for (ListSettingMenu sml : settingMenuList) {
            if (id == sml.getId()) {
                sml.setClicked(true);
            } else {
                sml.setClicked(false);
            }
        }

        if (settingListAdapter != null) settingListAdapter.notifyDataSetChanged();
    }

    public interface Setting {
        void clicked(int position);
    }

    private void openFragment(Fragment fragment, ListSettingMenu listSettingMenu) {
        if (!fragment.isVisible()) {
            refreshSelected(listSettingMenu.getId());
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
