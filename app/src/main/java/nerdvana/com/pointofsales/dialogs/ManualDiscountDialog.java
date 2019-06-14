package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.DepartmentsAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.api_requests.DiscountRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountReasonRequest;
import nerdvana.com.pointofsales.api_responses.DiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchCompanyUserResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountReasonResponse;
import nerdvana.com.pointofsales.api_responses.FetchNationalityResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.model.DiscountListModel;
import nerdvana.com.pointofsales.model.DiscountModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ManualDiscountDialog extends BaseDialog {
    String isPercentage = "";
    List<DiscountListModel> discountList;
    private DepartmentsAdapter departmentsAdapter;
    private RecyclerView listPosts;
    private FetchRoomPendingResponse.Result fetchRoomPendingData;
    private CheckBoxItem checkBoxItem;
    private FloatingActionButton fabSave;
    private RadioGroup discountOptionGroup;
    private EditText inputReason;
    private EditText inputAmount;
    private Spinner spinnerDiscountReason;
    private String discountReasonId = "";
    private Context context;
    private String controlNumber = "";
    private String roomId = "";
    private FetchOrderPendingViaControlNoResponse.Result fetchOrderPendingData;
    private ArrayList<DiscountModel> discountModelList;
    public ManualDiscountDialog(@NonNull Context context,
                                FetchRoomPendingResponse.Result fetchRoomPendingData,
                                String controlNumber,
                                String roomId,
                                FetchOrderPendingViaControlNoResponse.Result fetchOrderPendingData) {
        super(context);
        this.context = context;
        this.fetchRoomPendingData = fetchRoomPendingData;
        this.controlNumber = controlNumber;
        this.roomId = roomId;
        this.fetchOrderPendingData = fetchOrderPendingData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_discount, "MANUAL DISCOUNTING");
        fabSave = findViewById(R.id.fabSave);
        inputReason = findViewById(R.id.inputReason);
        inputAmount = findViewById(R.id.inputAmount);
        spinnerDiscountReason = findViewById(R.id.spinnerDiscountReason);
        discountModelList = new ArrayList<>();
        discountOptionGroup = findViewById(R.id.discountOptionGroup);
        discountList = new ArrayList<>();
        fetchDiscountReasonRequest();
        checkBoxItem = new CheckBoxItem() {
            @Override
            public void isChecked(int position, boolean isChecked) {

                for (DiscountListModel.DiscountProduct result : discountList.get(position).getDiscountProductList()) {
                    result.setChecked(isChecked);
                }

                departmentsAdapter.notifyItemChanged(position);
            }
        };

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountModelList = new ArrayList<>();

                isPercentage = "1";
                if (discountOptionGroup.getCheckedRadioButtonId() == R.id.radioAmount) {
                    isPercentage = "0";
                }


                for (DiscountListModel dlm : discountList) {
                    for (DiscountListModel.DiscountProduct dp : dlm.getDiscountProductList()) {


                        if (dp.isChecked()) {
                            DiscountModel discountModel = new DiscountModel(dp.getPostId(), dp.getName());
                            discountModelList.add(discountModel);

                        }

                    }
                }

                PasswordDialog passwordDialog = new PasswordDialog(context, "63") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        dismiss();
                        DiscountRequest discountRequest =
                                new DiscountRequest(
                                        GsonHelper.getGson().toJson(discountModelList),
                                        inputReason.getText().toString(),
                                        isPercentage,
                                        inputAmount.getText().toString(),
                                        employeeId,
                                        discountReasonId,
                                        controlNumber,
                                        roomId);


                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<DiscountResponse> request = iUsers.sendDiscount(discountRequest.getMapValue());
                        request.enqueue(new Callback<DiscountResponse>() {
                            @Override
                            public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
                                if (response.body().getStatus() == 0) {
                                    Utils.showDialogMessage(context, response.body().getMessage(), "Information");
                                } else {
                                    Utils.showDialogMessage(context, response.body().getMessage(), "Information");
                                    discountSuccess();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DiscountResponse> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void passwordFailed() {

                    }
                };
                if (!passwordDialog.isShowing()) passwordDialog.show();





            }
        });


        listPosts = findViewById(R.id.listPosts);
        fixDepartmentData();

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

    private void fixDepartmentData() {
        if (fetchRoomPendingData == null) { //TAKEOUT
            for (FetchOrderPendingViaControlNoResponse.Post result : fetchOrderPendingData.getPost()) {
                if (result.getVoid() == 0) {
                    if (result.getProductId() != 0) { //ROOM RATE


                        //DEPARTMENT == getDepartment()
                        boolean isExisting = false;
                        DiscountListModel temp = null;

                        for (DiscountListModel dcm : discountList) {
                            String tmp = "OTHERS";
                            if (result.getDepartment() != null) {
                                tmp = result.getDepartment().toUpperCase();
                            }
                            if (dcm.getDepartment().equalsIgnoreCase(
                                    tmp)) {
                                isExisting = true;
                                temp = dcm;
                                break;
                            }
                        }

                        if (!isExisting) {

                            ArrayList<DiscountListModel.DiscountProduct> myProd= new ArrayList<>();

                            DiscountListModel.DiscountProduct discProduct =
                                    new DiscountListModel.DiscountProduct(
                                            String.valueOf(result.getId()),
                                            result.getControlNo(),
                                            String.valueOf(result.getPrice()),
                                            String.valueOf(result.getTotal()),
                                            String.valueOf(result.getDiscount()),
                                            result.getProduct().getProduct(),
                                            true);

                            myProd.add(discProduct);
                            discountList.add(new DiscountListModel(
                                    result.getDepartment() == null ? "OTHERS" : result.getDepartment(),
                                    myProd,
                                    new ArrayList<FetchRoomPendingResponse.Discount>(),
                                    true
                            ));
                        }
                    }

                }
            }
        } else {
            for (FetchRoomPendingResponse.Post result : fetchRoomPendingData.getBooked().get(0).getTransaction().getPost()) {
                if (result.getVoid() == 0) {
                    if (result.getProductId() == 0) { //ROOM RATE
                        //DEPARTMENT == STATIC ROOM RATE
                        boolean isExisting = false;
                        DiscountListModel temp = null;

                        for (DiscountListModel dcm : discountList) {
                            if (dcm.getDepartment().equalsIgnoreCase("ROOM RATE")) {
                                isExisting = true;
                                temp = dcm;
                                break;
                            }
                        }
                        if (!isExisting) {
                            ArrayList<DiscountListModel.DiscountProduct> myProd= new ArrayList<>();
                            DiscountListModel.DiscountProduct discProduct =
                                    new DiscountListModel.DiscountProduct(
                                            String.valueOf(result.getId()),
                                            result.getControlNo(),
                                            String.valueOf(result.getPrice()),
                                            String.valueOf(result.getTotal()),
                                            String.valueOf(result.getDiscount()),
                                            result.getRoomRate().toUpperCase(),
                                            true);
                            myProd.add(discProduct);
                            ArrayList<FetchRoomPendingResponse.Discount> myDiscs = new ArrayList<FetchRoomPendingResponse.Discount>();
                            for (FetchRoomPendingResponse.Discount dc : result.getDiscounts()) {
                                myDiscs.add(dc);
                            }
                            discountList.add(new DiscountListModel(
                                    "ROOM RATE",
                                    myProd,
                                    myDiscs,
                                    true
                            ));
                        } else {
                            for (FetchRoomPendingResponse.Discount dc : result.getDiscounts()) {
                                temp.getDiscountList().add(dc);
                            }
                            DiscountListModel.DiscountProduct discProduct =
                                    new DiscountListModel.DiscountProduct(
                                            String.valueOf(result.getId()),
                                            result.getControlNo(),
                                            String.valueOf(result.getPrice()),
                                            String.valueOf(result.getTotal()),
                                            String.valueOf(result.getDiscount()),
                                            result.getRoomRate().toUpperCase(),
                                            true);

                            temp.getDiscountProductList().add(discProduct);
                        }
                    } else { // PRODUCT
                        //DEPARTMENT == getDepartment()
                        boolean isExisting = false;
                        DiscountListModel temp = null;

                        for (DiscountListModel dcm : discountList) {
                            String tmp = "OTHERS";
                            if (result.getDepartment() != null) {
                                tmp = result.getDepartment().toUpperCase();
                            }
                            if (dcm.getDepartment().equalsIgnoreCase(
                                    tmp)) {
                                isExisting = true;
                                temp = dcm;
                                break;
                            }
                        }

                        if (!isExisting) {

                            ArrayList<DiscountListModel.DiscountProduct> myProd= new ArrayList<>();

                            DiscountListModel.DiscountProduct discProduct =
                                    new DiscountListModel.DiscountProduct(
                                            String.valueOf(result.getId()),
                                            result.getControlNo(),
                                            String.valueOf(result.getPrice()),
                                            String.valueOf(result.getTotal()),
                                            String.valueOf(result.getDiscount()),
                                            result.getProduct().getProduct(),
                                            true);

                            myProd.add(discProduct);
                            ArrayList<FetchRoomPendingResponse.Discount> myDiscs = new ArrayList<FetchRoomPendingResponse.Discount>();
                            for (FetchRoomPendingResponse.Discount dc : result.getDiscounts()) {
                                myDiscs.add(dc);
                            }
                            discountList.add(new DiscountListModel(
                                    result.getDepartment() == null ? "OTHERS" : result.getDepartment(),
                                    myProd,
                                    myDiscs,
                                    true
                            ));
                        } else {
                            for (FetchRoomPendingResponse.Discount dc : result.getDiscounts()) {
                                temp.getDiscountList().add(dc);
                            }
                            DiscountListModel.DiscountProduct discProduct =
                                    new DiscountListModel.DiscountProduct(
                                            String.valueOf(result.getId()),
                                            result.getControlNo(),
                                            String.valueOf(result.getPrice()),
                                            String.valueOf(result.getTotal()),
                                            String.valueOf(result.getDiscount()),
                                            result.getProduct().getProduct(),
                                            true);

                            temp.getDiscountProductList().add(discProduct);
                        }
                    }
                }
            }
        }



        setDepartmentList(discountList);

    }

    private void setDepartmentList(List<DiscountListModel> discountListModels) {
        departmentsAdapter = new DepartmentsAdapter(discountListModels, getContext(), checkBoxItem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        listPosts.setLayoutManager(linearLayoutManager);
        listPosts.setAdapter(departmentsAdapter);
    }

    public interface CheckBoxItem {
        void isChecked(int position, boolean isChecked);
    }


    private void fetchDiscountReasonRequest() {
        FetchDiscountReasonRequest fetchDiscountReasonRequest = new FetchDiscountReasonRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDiscountReasonResponse> request = iUsers.fetchDiscountReason(fetchDiscountReasonRequest.getMapValue());
        request.enqueue(new Callback<FetchDiscountReasonResponse>() {
            @Override
            public void onResponse(Call<FetchDiscountReasonResponse> call, final Response<FetchDiscountReasonResponse> response) {

                ArrayList<String> stringArray = new ArrayList<>();

                for (FetchDiscountReasonResponse.Result r :response.body().getResult()) {
                    stringArray.add(r.getDiscountReason());
                }
                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
                        stringArray);
                spinnerDiscountReason.setAdapter(customSpinnerAdapter);

                spinnerDiscountReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        discountReasonId = String.valueOf(response.body().getResult().get(position).getCoreId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onFailure(Call<FetchDiscountReasonResponse> call, Throwable t) {

            }
        });
    }

    public abstract void discountSuccess();

}
