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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.DepartmentsAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.api_requests.DiscountRequest;
import nerdvana.com.pointofsales.api_responses.DiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchNationalityResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.model.DiscountListModel;
import nerdvana.com.pointofsales.model.DiscountModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManualDiscountDialog extends BaseDialog {
    List<DiscountListModel> discountList;
    private DepartmentsAdapter departmentsAdapter;
    private RecyclerView listPosts;
    private FetchRoomPendingResponse.Result fetchRoomPendingData;
    private CheckBoxItem checkBoxItem;
    private FloatingActionButton fabSave;
    private RadioGroup discountOptionGroup;
    private EditText inputReason;
    private EditText inputAmount;

    private ArrayList<DiscountModel> discountModelList;
    public ManualDiscountDialog(@NonNull Context context, FetchRoomPendingResponse.Result fetchRoomPendingData) {
        super(context);
        this.fetchRoomPendingData = fetchRoomPendingData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_discount, "MANUAL DISCOUNTING");
        fabSave = findViewById(R.id.fabSave);
        inputReason = findViewById(R.id.inputReason);
        inputAmount = findViewById(R.id.inputAmount);
        discountModelList = new ArrayList<>();
        discountOptionGroup = findViewById(R.id.discountOptionGroup);
        discountList = new ArrayList<>();

        checkBoxItem = new CheckBoxItem() {
            @Override
            public void isChecked(int position, boolean isChecked) {

                for (DiscountListModel.DiscountProduct result : discountList.get(position).getDiscountProductList()) {
                    result.setChecked(isChecked);
                }

                departmentsAdapter.notifyDataSetChanged();
            }
        };

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discountModelList = new ArrayList<>();

                String isPercentage = "1";
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
                DiscountRequest discountRequest =
                        new DiscountRequest(
                                GsonHelper.getGson().toJson(discountModelList),
                                inputReason.getText().toString(),
                                isPercentage,
                                inputAmount.getText().toString(),
                                "1");

                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<DiscountResponse> request = iUsers.sendDiscount(discountRequest.getMapValue());
                request.enqueue(new Callback<DiscountResponse>() {
                    @Override
                    public void onResponse(Call<DiscountResponse> call, Response<DiscountResponse> response) {
                    }

                    @Override
                    public void onFailure(Call<DiscountResponse> call, Throwable t) {

                    }
                });



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
                                myDiscs
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
                                myDiscs
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



}
