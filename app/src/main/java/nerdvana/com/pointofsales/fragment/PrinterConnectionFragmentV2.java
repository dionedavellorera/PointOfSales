package nerdvana.com.pointofsales.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.sunmi.devicemanager.cons.Cons;
import com.sunmi.devicemanager.device.Device;
import com.sunmi.devicesdk.core.DeviceManager;
import com.sunmi.devicesdk.core.PrinterManager;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PrintoutSelectionIntf;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.AllPrintoutAdapter;
import nerdvana.com.pointofsales.adapters.PrintersArrayAdapter;
import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;
import nerdvana.com.pointofsales.custom.ThreadPoolManager;
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;

public class PrinterConnectionFragmentV2 extends Fragment implements PrintoutSelectionIntf, View.OnClickListener {
    private View view;
    private RecyclerView rvPrinterList;
    SunmiPrinterService mSunmiPrintService;
    private List<SunmiPrinterModel> sunmiPrinterModelList;

    private AllPrintoutAdapter allPrintoutAdapter;

    private PrintersArrayAdapter printersArrayAdapter;
    private Spinner spinnerPrinterAvailable;

    private List<PrintingListModel> printoutList;

    private Button btnAddPrinter;
    private Button btnSelectAll;
    private Button btnRemoveAllAssigned;
    private Button btnDeviceManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_printer_connection_v2, container,false);
        initViews(view);
        loadPrinterList();
        initializeAdapter();
        initializeSpinnerAdapter();
        connectInnerPrinter();
        detectExternalPrinters();
        return view;
    }

    private void loadPrinterList() {
        TypeToken<List<PrintingListModel>> collectionToken = new TypeToken<List<PrintingListModel>>() {};
        List<PrintingListModel> collectionDetails = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(getContext(), ApplicationConstants.PRINTER_PREFS), collectionToken.getType());
        printoutList = collectionDetails;
    }

    private void initializeSpinnerAdapter() {
        printersArrayAdapter = new PrintersArrayAdapter(getContext(), R.layout.list_item_array, sunmiPrinterModelList);
        spinnerPrinterAvailable.setAdapter(printersArrayAdapter);

    }

    private void initializeAdapter() {
        allPrintoutAdapter = new AllPrintoutAdapter(printoutList, this, getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvPrinterList.setLayoutManager(llm);
        rvPrinterList.setAdapter(allPrintoutAdapter);
        allPrintoutAdapter.notifyDataSetChanged();
    }

    private void detectExternalPrinters() {
        ThreadPoolManager.getsInstance().execute(() -> {
            List<Device> deviceList = PrinterManager.getInstance().getPrinterDevice();
            if (deviceList == null || deviceList.isEmpty()) return;
            for (Device device : deviceList) {

                if (device.type == Cons.Type.PRINT && device.connectType == Cons.ConT.INNER) {
                    continue;
                }
                sunmiPrinterModelList.add(new SunmiPrinterModel(SunmiPrinterModel.PRINTER_EXTERNAL, device));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (printersArrayAdapter != null) printersArrayAdapter.notifyDataSetChanged();

                    }
                });
//                printerPresenter.printByDeviceManager(device, finalString1);
            }
        });
    }

    private void initViews(View view) {
        btnDeviceManager = view.findViewById(R.id.btnDeviceManager);
        btnDeviceManager.setOnClickListener(this);
        btnRemoveAllAssigned = view.findViewById(R.id.btnRemoveAllAssigned);
        btnRemoveAllAssigned.setOnClickListener(this);
        btnSelectAll = view.findViewById(R.id.btnSelectAll);
        btnSelectAll.setOnClickListener(this);
        btnAddPrinter = view.findViewById(R.id.btnAddPrinter);
        btnAddPrinter.setOnClickListener(this);
        spinnerPrinterAvailable = view.findViewById(R.id.spinnerPrinterAvailable);
        sunmiPrinterModelList = new ArrayList<>();
        rvPrinterList = view.findViewById(R.id.rvPrinterList);



    }

    private void connectInnerPrinter() {
        try {
            InnerPrinterManager.getInstance().bindService(getContext(),
                    innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {

            mSunmiPrintService = service;
            //ADD BUILT IN PRINTER TO THE LIST
            if (mSunmiPrintService != null) {
                try {
                    if (mSunmiPrintService.updatePrinterState() == 1) {
                        sunmiPrinterModelList.add(new SunmiPrinterModel(SunmiPrinterModel.PRINTER_BUILT_IN, null));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (printersArrayAdapter != null) printersArrayAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                } catch (Exception e) {

                }

            }


        }

        @Override
        protected void onDisconnected() {
            mSunmiPrintService = null;

        }
    };


    @Override
    public void clicked(int position, PrintingListModel printingListModel) {
        if (printoutList.get(position).isEditing()) {
            printoutList.get(position).setEditing(false);
        } else {
            printoutList.get(position).setEditing(true);
        }

        if (allPrintoutAdapter != null) allPrintoutAdapter.notifyDataSetChanged();

    }

    @Override
    public void closeClicked(int position, int innerPosition) {
        printoutList.get(position).getSelectedPrinterList().remove(innerPosition);
        if (allPrintoutAdapter != null) allPrintoutAdapter.notifyDataSetChanged();

        SharedPreferenceManager.saveString(getContext(), GsonHelper.getGson().toJson(printoutList) , ApplicationConstants.PRINTER_PREFS);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeviceManager:
                if (DeviceManager.getInstance() != null) {
                    DeviceManager.getInstance().startSettingActivity();
                }

                break;
            case R.id.btnRemoveAllAssigned:
                btnRemoveAllAssigned.setEnabled(false);
                for (PrintingListModel pom : printoutList) {
                    pom.getSelectedPrinterList().clear();
                }
                SharedPreferenceManager.saveString(getContext(), GsonHelper.getGson().toJson(printoutList) , ApplicationConstants.PRINTER_PREFS);
                if (allPrintoutAdapter != null) allPrintoutAdapter.notifyDataSetChanged();
                btnRemoveAllAssigned.setEnabled(true);
                break;
            case R.id.btnSelectAll:
                if (btnSelectAll.getText().equals("SELECT ALL")) {
                    btnSelectAll.setText("DESELECT ALL");
                    for (PrintingListModel printingListModel : printoutList) {
                        printingListModel.setEditing(true);
                    }
                } else {
                    btnSelectAll.setText("SELECT ALL");
                    for (PrintingListModel printingListModel : printoutList) {
                        printingListModel.setEditing(false);
                    }
                }

                if (allPrintoutAdapter != null) allPrintoutAdapter.notifyDataSetChanged();
                break;
            case R.id.btnAddPrinter:
                btnAddPrinter.setEnabled(false);
                if (spinnerPrinterAvailable.getSelectedItem() != null) {
                    SunmiPrinterModel spnr = (SunmiPrinterModel) spinnerPrinterAvailable.getSelectedItem();
                    if (spnr.getType().equalsIgnoreCase(SunmiPrinterModel.PRINTER_EXTERNAL)) {

                        if (printoutList != null) {
                            if (printoutList.size() > 0) {
                                for (PrintingListModel printingListModel : printoutList) {
                                    if (printingListModel.isEditing()) {
                                        if (printingListModel.getSelectedPrinterList().size() < 1) {
                                            printingListModel.getSelectedPrinterList().add(new PrintingListModel.SelectedPrinterData(spnr.getDevice().getId(), spnr.getDevice().getNickName()));
                                            printingListModel.setEditing(false);
                                        } else {
                                            for (PrintingListModel.SelectedPrinterData selected : printingListModel.getSelectedPrinterList()) {
                                                if (!selected.getId().equalsIgnoreCase(spnr.getDevice().getId())) {
                                                    printingListModel.getSelectedPrinterList().add(new PrintingListModel.SelectedPrinterData(spnr.getDevice().getId(), spnr.getDevice().getNickName()));
                                                    printingListModel.setEditing(false);
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }




                    } else {

                        if (printoutList != null) {
                            if (printoutList.size() > 0) {
                                for (PrintingListModel printingListModel : printoutList) {
                                    if (printingListModel.isEditing()) {
                                        if (printingListModel.getSelectedPrinterList().size() < 1) {
                                            printingListModel.getSelectedPrinterList().add(new PrintingListModel.SelectedPrinterData(SunmiPrinterModel.PRINTER_BUILT_IN, SunmiPrinterModel.PRINTER_BUILT_IN));
                                            printingListModel.setEditing(false);
                                        } else {
                                            for (PrintingListModel.SelectedPrinterData selected : printingListModel.getSelectedPrinterList()) {
                                                if (!selected.getId().equalsIgnoreCase(SunmiPrinterModel.PRINTER_BUILT_IN)) {
                                                    printingListModel.getSelectedPrinterList().add(new PrintingListModel.SelectedPrinterData(SunmiPrinterModel.PRINTER_BUILT_IN, SunmiPrinterModel.PRINTER_BUILT_IN));
                                                    printingListModel.setEditing(false);
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }


                    }

                    SharedPreferenceManager.saveString(getContext(), GsonHelper.getGson().toJson(printoutList) , ApplicationConstants.PRINTER_PREFS);
                    if (allPrintoutAdapter != null) allPrintoutAdapter.notifyDataSetChanged();
                }


                btnAddPrinter.setEnabled(true);
                break;
        }
    }
}
