package nerdvana.com.pointofsales.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.epson.epos2.printer.Printer;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.model.SpnModelsItem;

public class PrinterFragment extends Fragment {

    private View view;

    private RadioGroup printerRadioGroup;
    private RadioGroup languageRadioGroup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_printer, container, false);
        Log.d("CCCCC", SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER));
        printerRadioGroup = view.findViewById(R.id.printerRadioGroup);
        languageRadioGroup = view.findViewById(R.id.languageRadioGroup);

        List<SpnModelsItem> printerList = new ArrayList<>();
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_m10), Printer.TM_M10));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_m30), Printer.TM_M30));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_p20), Printer.TM_P20));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_p60), Printer.TM_P60));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_p80), Printer.TM_P80));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t20), Printer.TM_T20));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t70), Printer.TM_T70));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t81), Printer.TM_T81));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t82), Printer.TM_T82));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t83), Printer.TM_T83));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t88), Printer.TM_T88));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t90), Printer.TM_T90));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_u220), Printer.TM_U220));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_u330), Printer.TM_U330));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_l90), Printer.TM_L90));
        printerList.add(new SpnModelsItem(getString(R.string.printerseries_h6000), Printer.TM_H6000));

        for (SpnModelsItem itm : printerList) {



            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setPadding(5, 5, 5, 5);
            radioButton.setText(itm.getmModelName());
            radioButton.setId(itm.getmModelConstant());
            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER))) {

                if (itm.getmModelConstant() == Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER))) {
                    radioButton.setChecked(true);
                }
            }
            printerRadioGroup.addView(radioButton);


        }

        printerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                SharedPreferenceManager.saveString(getContext(), String.valueOf(checkedRadioButton.getId()), ApplicationConstants.SELECTED_PRINTER);
            }
        });

        List<SpnModelsItem> langList = new ArrayList<>();
        langList.add(new SpnModelsItem(getString(R.string.lang_ank), Printer.MODEL_ANK));
        langList.add(new SpnModelsItem(getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        langList.add(new SpnModelsItem(getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        langList.add(new SpnModelsItem(getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        langList.add(new SpnModelsItem(getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        langList.add(new SpnModelsItem(getString(R.string.lang_thai), Printer.MODEL_THAI));
        langList.add(new SpnModelsItem(getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));

        for (SpnModelsItem itm : langList) {


            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setPadding(5, 5, 5, 5);
            radioButton.setText(itm.getmModelName());
            radioButton.setId(itm.getmModelConstant());

            if (!TextUtils.isEmpty(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_LANGUAGE))) {
                if (itm.getmModelConstant() == Integer.valueOf(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_LANGUAGE))) {
                    radioButton.setChecked(true);
                }
            }
            languageRadioGroup.addView(radioButton);
        }

        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                SharedPreferenceManager.saveString(getContext(), String.valueOf(checkedRadioButton.getId()), ApplicationConstants.SELECTED_LANGUAGE);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
