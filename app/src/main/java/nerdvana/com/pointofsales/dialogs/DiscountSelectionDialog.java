package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.adapters.VoidDiscountsAdapter;
import nerdvana.com.pointofsales.api_requests.AutoDiscountRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountRequest;
import nerdvana.com.pointofsales.api_requests.VoidDiscountRequest;
import nerdvana.com.pointofsales.api_responses.AutoDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchOrderPendingViaControlNoResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.VoidDiscountResponse;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.interfaces.VoidItemContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ForVoidDiscountModel;
import nerdvana.com.pointofsales.postlogin.LeftFrameFragment;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class DiscountSelectionDialog extends BaseDialog implements ButtonsContract {

    private RecyclerView listDiscounts;
    private Activity activity;
    private FetchRoomPendingResponse.Result fetchRoomPendingResult;

    private String controlNumber;
    private String roomId;

    List<ButtonsModel> buttonsModelList;
    ButtonsAdapter buttonsAdapter;

    private RecyclerView listPostedDiscounts;
    private LeftFrameFragment.Data data;
    VoidDiscountsAdapter voidDiscountsAdapter;
    private List<ForVoidDiscountModel> forVoidDiscountModels;
    private FetchOrderPendingViaControlNoResponse.Result fetchOrderPendingResult;
    public DiscountSelectionDialog(@NonNull Context context,
                                   Activity activity,
                                   FetchRoomPendingResponse.Result fetchRoomPendingResult,
                                   String controlNumber,
                                   String roomId,
                                   List<ForVoidDiscountModel> forVoidDiscountModels,
                                   LeftFrameFragment.Data data,
                                   FetchOrderPendingViaControlNoResponse.Result fetchOrderPendingResult) {
        super(context);
        this.data = data;
        this.activity = activity;
        this.fetchRoomPendingResult = fetchRoomPendingResult;
        this.controlNumber = controlNumber;
        this.roomId = roomId;
        this.forVoidDiscountModels = forVoidDiscountModels;
        this.fetchOrderPendingResult = fetchOrderPendingResult;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_selection, "DISCOUNT SELECTION");

        listDiscounts = findViewById(R.id.listDiscounts);
        listPostedDiscounts = findViewById(R.id.listPostedDiscounts);


        buttonsModelList = new ArrayList<>();
        String[]images = {"", ""};
        buttonsModelList.add(new ButtonsModel(1000,
                "MANUAL",
                "http://www.twentyonepilots.com/sites/g/files/g2000004896/f/styles/media_gallery_large/public/Sample-image10-highres.jpg?itok=-UQ2667f",
                1));
        buttonsModelList.add(new ButtonsModel(1001,"CUSTOM", "",2));
//        buttonsModelList.add(new ButtonsModel(1002,"CARD", "",3));

        buttonsAdapter = new ButtonsAdapter(buttonsModelList, this, getContext());
        listDiscounts.setLayoutManager(new GridLayoutManager(getContext(),4,  GridLayoutManager.VERTICAL, false));
        listDiscounts.setAdapter(buttonsAdapter);



        requestDiscountSelection();

        VoidItemContract voidItemContract = new VoidItemContract() {
            @Override
            public void remove(final String post_id, String name, String amount, final int position) {
                PasswordDialog passwordDialog = new PasswordDialog(activity, "65") {
                    @Override
                    public void passwordSuccess(String employeeId, String employeeName) {
                        VoidDiscountRequest voidDiscountRequest = new VoidDiscountRequest(controlNumber, post_id, employeeId);
                        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                        Call<VoidDiscountResponse> request = iUsers.voidDiscount(voidDiscountRequest.getMapValue());
                        request.enqueue(new Callback<VoidDiscountResponse>() {
                            @Override
                            public void onResponse(Call<VoidDiscountResponse> call, Response<VoidDiscountResponse> response) {
                                data.refresh();
                                forVoidDiscountModels.remove(position);
                                if (voidDiscountsAdapter != null) {
                                    voidDiscountsAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<VoidDiscountResponse> call, Throwable t) {

                            }
                        });


                    }

                    @Override
                    public void passwordFailed() {

                    }
                };

                if (!passwordDialog.isShowing()) passwordDialog.show();


            }
        };

        voidDiscountsAdapter = new VoidDiscountsAdapter(forVoidDiscountModels, voidItemContract);
        listPostedDiscounts.setLayoutManager(new LinearLayoutManager(getContext()));
        listPostedDiscounts.setAdapter(voidDiscountsAdapter);

    }

    @Override
    public void clicked(ButtonsModel buttonsModel) {
        if (buttonsModel.isSpecial()) {
            SelectionDiscountDialog selectionDiscountDialog =
                    new SelectionDiscountDialog(activity, controlNumber, roomId, String.valueOf(buttonsModel.getId())) {
                        @Override
                        public void discountSuccess() {
                            if (!TextUtils.isEmpty(controlNumber)) {
                                fetchPending("to");
                            } else {
                                fetchPending("room");
                            }
                            dismiss();
                        }
                    };
            if (!selectionDiscountDialog.isShowing()) selectionDiscountDialog.show();
        } else {

            switch (buttonsModel.getId()) {
                case 1000:
                    if (!TextUtils.isEmpty(controlNumber)) { //TAKEOUT
                        if (fetchOrderPendingResult != null) {
                            ManualDiscountDialog manualDiscountDialog = new ManualDiscountDialog(activity, null, controlNumber, roomId, fetchOrderPendingResult) {
                                @Override
                                public void discountSuccess() {
                                    if (!TextUtils.isEmpty(controlNumber)) {
                                        fetchPending("to");
                                    } else {
                                        fetchPending("room");
                                    }
                                }
                            };
                            if (!manualDiscountDialog.isShowing()) manualDiscountDialog.show();
                        } else {
                            Utils.showDialogMessage(activity, "EMPTY FETCH ROOM PENDING", "WARNING CONTACT DEVELOPER");
                        }
                    } else {
                        if (fetchRoomPendingResult != null) {
                            ManualDiscountDialog manualDiscountDialog = new ManualDiscountDialog(activity, fetchRoomPendingResult, controlNumber, roomId, null) {
                                @Override
                                public void discountSuccess() {
                                    if (!TextUtils.isEmpty(controlNumber)) {
                                        fetchPending("to");
                                    } else {
                                        fetchPending("room");
                                    }
                                }
                            };
                            if (!manualDiscountDialog.isShowing()) manualDiscountDialog.show();
                        } else {
                            Utils.showDialogMessage(activity, "EMPTY FETCH ROOM PENDING", "WARNING CONTACT DEVELOPER");
                        }
                    }

                    break;
                case 1001:
                    SelectionDiscountDialog selectionDiscountDialog =
                            new SelectionDiscountDialog(activity, controlNumber, roomId, "") {
                                @Override
                                public void discountSuccess() {
                                    if (!TextUtils.isEmpty(controlNumber)) {
                                        fetchPending("to");
                                    } else {
                                        fetchPending("room");
                                    }

                                    dismiss();
                                }
                            };

                    if (!selectionDiscountDialog.isShowing()) selectionDiscountDialog.show();
                    break;
            }
        }

    }

    public abstract void fetchPending(String type);



    private void requestDiscountSelection() {
        FetchDiscountRequest fetchDiscountRequest = new FetchDiscountRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchDiscountResponse> request = iUsers.fetchDiscount(fetchDiscountRequest.getMapValue());
        request.enqueue(new Callback<FetchDiscountResponse>() {
            @Override
            public void onResponse(Call<FetchDiscountResponse> call, final Response<FetchDiscountResponse> response) {
                if (response.body().getResult().size() > 0) {
                    for (FetchDiscountResponse.Result r :response.body().getResult()) {
                        if (r.getIsSpecial() == 1) {
                            buttonsModelList.add(new ButtonsModel(r.getId(),
                                    r.getDiscountCard(),
                                    "http://www.twentyonepilots.com/sites/g/files/g2000004896/f/styles/media_gallery_large/public/Sample-image10-highres.jpg?itok=-UQ2667f",
                                    1,
                                    true));
                        }
                    }
                    buttonsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FetchDiscountResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Dialog dialog = this;
        if (dialog != null) {
            int width = 500;
            int height = 500;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
