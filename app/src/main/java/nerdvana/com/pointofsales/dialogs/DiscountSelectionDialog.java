package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.adapters.CustomSpinnerAdapter;
import nerdvana.com.pointofsales.api_requests.AutoDiscountRequest;
import nerdvana.com.pointofsales.api_requests.FetchDiscountRequest;
import nerdvana.com.pointofsales.api_responses.AutoDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
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
    public DiscountSelectionDialog(@NonNull Context context,
                                   Activity activity,
                                   FetchRoomPendingResponse.Result fetchRoomPendingResult,
                                   String controlNumber,
                                   String roomId) {
        super(context);
        this.activity = activity;
        this.fetchRoomPendingResult = fetchRoomPendingResult;
        this.controlNumber = controlNumber;
        this.roomId = roomId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_selection, "DISCOUNT SELECTION");

        listDiscounts = findViewById(R.id.listDiscounts);


        buttonsModelList = new ArrayList<>();
        String[]images = {"", ""};
        buttonsModelList.add(new ButtonsModel(1000,
                "MANUAL",
                "http://www.twentyonepilots.com/sites/g/files/g2000004896/f/styles/media_gallery_large/public/Sample-image10-highres.jpg?itok=-UQ2667f",
                1));
        buttonsModelList.add(new ButtonsModel(1001,"SELECTION", "",2));
        buttonsModelList.add(new ButtonsModel(1002,"CARD", "",3));

        buttonsAdapter = new ButtonsAdapter(buttonsModelList, this);
        listDiscounts.setLayoutManager(new GridLayoutManager(getContext(),4,  GridLayoutManager.VERTICAL, false));
        listDiscounts.setAdapter(buttonsAdapter);



        requestDiscountSelection();
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
                    if (fetchRoomPendingResult != null) {
                        ManualDiscountDialog manualDiscountDialog = new ManualDiscountDialog(activity, fetchRoomPendingResult, controlNumber, roomId) {
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


//                    ArrayList<String> stringArray = new ArrayList<>();
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
//                    CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getContext(), R.id.spinnerItem,
//                            stringArray);
//                    spinnerDiscountType.setAdapter(customSpinnerAdapter);
//
//                    spinnerDiscountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                            discountType = response.body().getResult().get(position).getDiscountCard();
//                            discountId = String.valueOf(response.body().getResult().get(position).getId());
//                            if (response.body().getResult().get(position).getIsCard() == 1) {
//                                selectionType = "card";
//                                showForm("card");
//                            } else if (response.body().getResult().get(position).getIsEmployee() == 1) {
//                                selectionType = "employee";
//                                showForm("employee");
//                            } else if (response.body().getResult().get(position).getIsSpecial() == 1) {
//                                selectionType = "special";
//                                showForm("special");
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });

                }
            }

            @Override
            public void onFailure(Call<FetchDiscountResponse> call, Throwable t) {

            }
        });
    }
}
