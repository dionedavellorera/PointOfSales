package nerdvana.com.pointofsales.dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.api_requests.AutoDiscountRequest;
import nerdvana.com.pointofsales.api_responses.AutoDiscountResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import retrofit2.Call;

public abstract class DiscountSelectionDialog extends BaseDialog implements ButtonsContract {

    private RecyclerView listDiscounts;
    private Activity activity;
    private FetchRoomPendingResponse.Result fetchRoomPendingResult;

    private String controlNumber;
    private String roomId;
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


        List<ButtonsModel> buttonsModelList = new ArrayList<>();
        String[]images = {"", ""};
        buttonsModelList.add(new ButtonsModel(100,
                "MANUAL",
                "http://www.twentyonepilots.com/sites/g/files/g2000004896/f/styles/media_gallery_large/public/Sample-image10-highres.jpg?itok=-UQ2667f",
                1));
        buttonsModelList.add(new ButtonsModel(101,"SELECTION", "",2));
        buttonsModelList.add(new ButtonsModel(102,"CARD", "",3));

        ButtonsAdapter buttonsAdapter = new ButtonsAdapter(buttonsModelList, this);
        listDiscounts.setLayoutManager(new GridLayoutManager(getContext(),4,  GridLayoutManager.VERTICAL, false));
        listDiscounts.setAdapter(buttonsAdapter);



    }

    @Override
    public void clicked(ButtonsModel buttonsModel) {
        switch (buttonsModel.getId()) {
            case 100:
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
            case 101:
                SelectionDiscountDialog selectionDiscountDialog =
                        new SelectionDiscountDialog(activity, controlNumber, roomId) {
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

    public abstract void fetchPending(String type);


}
