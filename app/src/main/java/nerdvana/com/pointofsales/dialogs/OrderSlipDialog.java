package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.OrderSlipAdapter;
import nerdvana.com.pointofsales.adapters.OrderSlipProductsAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.OrderSlipModel;
import nerdvana.com.pointofsales.model.PrintModel;

public class OrderSlipDialog extends BaseDialog {
    private List<OrderSlipModel> orderList;
    private RecyclerView ordersList;
    private OrderSlipAdapter orderSlipAdapter;
    private RecyclerView orderSlipProductsList;
    private Button reprintFoodOrderSlip;
    List<OrderSlipModel.OrderSlipProduct> tempOsp;
    private String roomNumber;
    private String kitchenPath;
    private String printerPath;
    public OrderSlipDialog(@NonNull Context context, List<OrderSlipModel> orderList,
                           String roomNumber, String kitchenPath,
                           String printerPath) {
        super(context);
        this.orderList = orderList;
        this.roomNumber = roomNumber;
        this.kitchenPath = kitchenPath;
        this.printerPath = printerPath;
    }

    public OrderSlipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OrderSlipDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.dialog_order_slip);
        setDialogLayout(R.layout.dialog_order_slip, "ORDER SLIP");
        tempOsp = new ArrayList<>();
        ordersList = findViewById(R.id.listOrders);
        orderSlipProductsList = findViewById(R.id.orderSlipProductsList);
        reprintFoodOrderSlip = findViewById(R.id.reprintFoodOrderSlip);
        OrderSlip orderSlip = new OrderSlip() {
            @Override
            public void clicked(List<OrderSlipModel.OrderSlipInfo> orderSlipInfo) {
                tempOsp = new ArrayList<>();
                for (OrderSlipModel.OrderSlipInfo osi : orderSlipInfo) {
                    tempOsp.addAll(osi.getOrderSlipProductList());
                }

                OrderSlipProductsAdapter ospa = new OrderSlipProductsAdapter(tempOsp);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                orderSlipProductsList.setLayoutManager(linearLayoutManager);
                orderSlipProductsList.setAdapter(ospa);
                ospa.notifyDataSetChanged();


//                for (OrderSlipModel.OrderSlipInfo osi : orderSlipInfo) {
//                    for (OrderSlipModel.OrderSlipProduct osp : osi.getOrderSlipProductList()) {
//                        if (TextUtils.isEmpty(osp.getProductName())) {
//                            Log.d("TESTCLASS", osp.getRoomRate());
//                        } else {
//                            Log.d("TESTCLASS", osp.getProductName());
//                        }
//
//                    }
//                }
            }
        };

        orderSlipAdapter = new OrderSlipAdapter(orderList, orderSlip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        ordersList.setLayoutManager(linearLayoutManager);
        ordersList.setAdapter(orderSlipAdapter);
        orderSlipAdapter.notifyDataSetChanged();


        reprintFoodOrderSlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AddRateProductModel> reprintList = new ArrayList<>();
                for (OrderSlipModel.OrderSlipProduct osp : tempOsp) {
                    reprintList.add(new AddRateProductModel(
                        osp.getProductId(),
                            osp.getRoomRatePriceId(),
                            osp.getQuantity(),
                            "",
                            osp.getPrice(),
                            0,
                            osp.getProductInitial(),
                            new ArrayList<AddRateProductModel.AlaCarte>(),
                            new ArrayList<AddRateProductModel.Group>()
                    ));
                }

                if (reprintList.size() > 0) {
                    BusProvider.getInstance().post(
                            new PrintModel("",
                                    !TextUtils.isEmpty(roomNumber) ? roomNumber : "",
                                    "FO", GsonHelper.getGson().toJson(reprintList),
                                    kitchenPath,
                                    printerPath));
                }
            }
        });
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

    public interface OrderSlip {
        void clicked(List<OrderSlipModel.OrderSlipInfo> orderSlipInfo);
    }

}
