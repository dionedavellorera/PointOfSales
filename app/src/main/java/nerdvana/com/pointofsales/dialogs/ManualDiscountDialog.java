package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.DepartmentsAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.model.DiscountListModel;

public class ManualDiscountDialog extends BaseDialog {
    List<DiscountListModel> discountList;
    private DepartmentsAdapter departmentsAdapter;
    private RecyclerView listPosts;
    private FetchRoomPendingResponse.Result fetchRoomPendingData;
    private CheckBoxItem checkBoxItem;

    public ManualDiscountDialog(@NonNull Context context, FetchRoomPendingResponse.Result fetchRoomPendingData) {
        super(context);
        this.fetchRoomPendingData = fetchRoomPendingData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_discount, "DISCOUNT");
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
                                    false);
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
                                    false);

                    temp.getDiscountProductList().add(discProduct);
                }
            } else { // PRODUCT
                //DEPARTMENT == getDepartment()
                boolean isExisting = false;
                DiscountListModel temp = null;

                for (DiscountListModel dcm : discountList) {
                    if (dcm.getDepartment().equalsIgnoreCase(result.getDepartment().toUpperCase())) {
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
                                    false);

                    myProd.add(discProduct);


                    ArrayList<FetchRoomPendingResponse.Discount> myDiscs = new ArrayList<FetchRoomPendingResponse.Discount>();

                    for (FetchRoomPendingResponse.Discount dc : result.getDiscounts()) {
                        myDiscs.add(dc);
                    }
                    discountList.add(new DiscountListModel(
                            result.getDepartment(),
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
                                    false);

                    temp.getDiscountProductList().add(discProduct);
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
