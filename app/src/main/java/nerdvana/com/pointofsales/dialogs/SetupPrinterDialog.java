package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.DeviceInfo;
import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;
import com.epson.epos2.printer.Printer;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.model.SpnModelsItem;

public abstract class SetupPrinterDialog extends BaseDialog {

    private Spinner printerSelection;
    private Spinner languageSelection;
    private TextView spinnerPort;
    private Context context;
    private Button connect;
    private Button searchPorts;

    private String selectedPrinter = "";
    private String selectedLanguage = "";

    public SetupPrinterDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public SetupPrinterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SetupPrinterDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setOnDismissListener(@Nullable DialogInterface.OnDismissListener listener) {
        super.setOnDismissListener(listener);
        try {
            Discovery.stop();
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_setup_printer);
        setDialogLayout(R.layout.dialog_setup_printer, "PRINTER SETUP");

        printerSelection = findViewById(R.id.spinnerPrinter);
        languageSelection = findViewById(R.id.spinnerLanguage);
        searchPorts = findViewById(R.id.searchPrinters);
        spinnerPort = findViewById(R.id.spinnerPort);
        connect = findViewById(R.id.connect);
        setPrinterSelection();
        setLanguageSelection();

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT))) {
                    SharedPreferenceManager.saveString(getContext(), selectedPrinter, ApplicationConstants.SELECTED_PRINTER);
                    SharedPreferenceManager.saveString(getContext(), selectedLanguage, ApplicationConstants.SELECTED_LANGUAGE);
                    printerConnected();
                    dismiss();
                }
            }
        });

        searchPorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterOption mFilterOption = new FilterOption();
                mFilterOption.setDeviceModel(Discovery.MODEL_ALL);
                mFilterOption.setPortType(Discovery.TYPE_ALL);
                mFilterOption.setDeviceType(Discovery.TYPE_ALL);
                mFilterOption.setEpsonFilter(Discovery.FILTER_NONE);
                try {
                    Discovery.start(getContext(), mFilterOption, mDiscoveryListener);
                } catch (Epos2Exception e) {

//                    Toast.makeText(getContext(), getEposExceptionText(((Epos2Exception) e).getErrorStatus()),Toast.LENGTH_SHORT).show();
//
//
//                    Toast.makeText(getContext(), "ERR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT))) {
            spinnerPort.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT));
        }

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
    }

    private void setPrinterSelection() {
        List<SpnModelsItem> printerList = new ArrayList<>();
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_m10), Printer.TM_M10));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_m30), Printer.TM_M30));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_p20), Printer.TM_P20));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_p60), Printer.TM_P60));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_p80), Printer.TM_P80));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t20), Printer.TM_T20));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t70), Printer.TM_T70));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t81), Printer.TM_T81));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t82), Printer.TM_T82));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t83), Printer.TM_T83));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t88), Printer.TM_T88));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t90), Printer.TM_T90));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_u220), Printer.TM_U220));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_u330), Printer.TM_U330));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_l90), Printer.TM_L90));
        printerList.add(new SpnModelsItem(context.getString(R.string.printerseries_h6000), Printer.TM_H6000));

        final ArrayAdapter<SpnModelsItem> seriesAdapter = new ArrayAdapter<SpnModelsItem>(getContext(), android.R.layout.simple_spinner_item, printerList);
        printerSelection.setAdapter(seriesAdapter);

        printerSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPrinter = String.valueOf(seriesAdapter.getItem(position).getmModelConstant());
                Log.d("TETETE", selectedPrinter);
                Log.d("TETETE", seriesAdapter.getItem(position).getmModelName());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER).isEmpty()) {
            for (int i = 0;i < printerList.size(); i++) {
                if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER).equalsIgnoreCase(printerList.get(i).getmModelName())) {
                    printerSelection.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setLanguageSelection() {
        List<SpnModelsItem> langList = new ArrayList<>();
        langList.add(new SpnModelsItem(context.getString(R.string.lang_ank), Printer.MODEL_ANK));
        langList.add(new SpnModelsItem(context.getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        langList.add(new SpnModelsItem(context.getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        langList.add(new SpnModelsItem(context.getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        langList.add(new SpnModelsItem(context.getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        langList.add(new SpnModelsItem(context.getString(R.string.lang_thai), Printer.MODEL_THAI));
        langList.add(new SpnModelsItem(context.getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
        final ArrayAdapter<SpnModelsItem> langAdapter = new ArrayAdapter<SpnModelsItem>(getContext(), android.R.layout.simple_spinner_item, langList);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageSelection.setAdapter(langAdapter);
        languageSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = String.valueOf(langAdapter.getItem(position).getmModelConstant());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_LANGUAGE).isEmpty()) {
            for (int i = 0;i < langList.size(); i++) {
                if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_LANGUAGE).equalsIgnoreCase(langList.get(i).getmModelName())) {
                    languageSelection.setSelection(i);
                    break;
                }
            }
        }
    }


    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {
        @Override
        public void onDiscovery(final DeviceInfo deviceInfo) {
            ((MainActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.d("GGGGGGG", deviceInfo.getTarget());

                    spinnerPort.setText(deviceInfo.getTarget());
//                    SharedPreferenceManager.saveString(getContext(), deviceInfo.getTarget(), ApplicationConstants.SELECTED_PORT);
                }
            });

        }
    };

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

    public abstract void printerConnected();

}
