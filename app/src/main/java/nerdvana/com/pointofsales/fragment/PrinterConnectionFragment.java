package nerdvana.com.pointofsales.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.DeviceInfo;
import com.epson.epos2.discovery.Discovery;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.OtherPrinterAdapter;
import nerdvana.com.pointofsales.model.OtherPrinterModel;

public class PrinterConnectionFragment extends Fragment {
    private View view;
    private TextView activePrinter;
    private OtherPrinterAdapter otherPrinterAdapter;

    private List<OtherPrinterModel> otherPrinterModelList;
    private RecyclerView listOtherPrinter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_printer_connection, container, false);
        activePrinter = view.findViewById(R.id.activePrinter);
        listOtherPrinter = view.findViewById(R.id.listOtherPrinter);
        otherPrinterModelList = new ArrayList<>();

        PrinterConnection printerConnection = new PrinterConnection() {
            @Override
            public void clicked(int position) {
                SharedPreferenceManager.saveString(getContext(), otherPrinterModelList.get(position).getHead(), ApplicationConstants.SELECTED_PRINTER_MANUALLY);
                activePrinter.setText(otherPrinterModelList.get(position).getHead());
            }
        };

        otherPrinterAdapter = new OtherPrinterAdapter(otherPrinterModelList, printerConnection, getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listOtherPrinter.setLayoutManager(llm);
        listOtherPrinter.setAdapter(otherPrinterAdapter);
        otherPrinterAdapter.notifyDataSetChanged();

        FilterOption mFilterOption = new FilterOption();
        mFilterOption.setDeviceModel(Discovery.MODEL_ALL);
        mFilterOption.setPortType(Discovery.TYPE_ALL);
        mFilterOption.setDeviceType(Discovery.TYPE_ALL);
        mFilterOption.setEpsonFilter(Discovery.FILTER_NONE);
        try {
            Discovery.start(getContext(), mFilterOption, mDiscoveryListener);
        } catch (Epos2Exception e) {

        }

        if (!SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER_MANUALLY).isEmpty()) {
            activePrinter.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PRINTER_MANUALLY));
        } else {
            if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT).isEmpty()) {
                activePrinter.setText("No printer set");
            } else {
                activePrinter.setText(SharedPreferenceManager.getString(getContext(), ApplicationConstants.SELECTED_PORT));
            }
        }
        return view;
    }

    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {
        @Override
        public void onDiscovery(final DeviceInfo deviceInfo) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getContext(), deviceInfo.getTarget(), Toast.LENGTH_SHORT).show();
                    Log.d("dsdsds", deviceInfo.getTarget());

                    otherPrinterModelList.add(new OtherPrinterModel(deviceInfo.getTarget(), deviceInfo.getDeviceName()));

                    if (otherPrinterAdapter != null) otherPrinterAdapter.notifyDataSetChanged();
                }
            });
        }
    };


    @Override
    public void onStop() {
        super.onStop();
        Log.d("BTBTB", "STOP");
        try {
            Discovery.stop();
        } catch (Epos2Exception e) {
            e.printStackTrace();
        }
    }

    public interface PrinterConnection {
        void clicked(int position);
    }
}
