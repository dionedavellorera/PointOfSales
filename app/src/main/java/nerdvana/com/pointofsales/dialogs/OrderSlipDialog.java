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
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.OrderSlipAdapter;
import nerdvana.com.pointofsales.adapters.OrderSlipProductsAdapter;
import nerdvana.com.pointofsales.adapters.RoomRatesAdapter;
import nerdvana.com.pointofsales.model.OrderSlipModel;

public class OrderSlipDialog extends Dialog {
    private List<OrderSlipModel> orderList;
    private RecyclerView ordersList;
    private OrderSlipAdapter orderSlipAdapter;
    private RecyclerView orderSlipProductsList;
    public OrderSlipDialog(@NonNull Context context, List<OrderSlipModel> orderList) {
        super(context);
        this.orderList = orderList;
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
        setContentView(R.layout.dialog_order_slip);
        ordersList = findViewById(R.id.listOrders);
        orderSlipProductsList = findViewById(R.id.orderSlipProductsList);

        OrderSlip orderSlip = new OrderSlip() {
            @Override
            public void clicked(List<OrderSlipModel.OrderSlipInfo> orderSlipInfo) {
                List<OrderSlipModel.OrderSlipProduct> tempOsp = new ArrayList<>();
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
