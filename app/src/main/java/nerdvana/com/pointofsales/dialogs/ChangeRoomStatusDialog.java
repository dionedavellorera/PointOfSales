package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.ChangeRoomStatusAdapter;
import nerdvana.com.pointofsales.api_requests.ChangeRoomStatusRequest;
import nerdvana.com.pointofsales.api_responses.FetchRoomStatusResponse;

public abstract class ChangeRoomStatusDialog extends BaseDialog {
    private NewRoomStatus newRoomStatus;
    private RecyclerView listNewStatus;
    private List<FetchRoomStatusResponse.Result> resultList;
    private String roomId;
    public ChangeRoomStatusDialog(@NonNull Context context,
                                  List<FetchRoomStatusResponse.Result> resultList,
                                  String roomId) {
        super(context);
        this.resultList = resultList;
        this.roomId = roomId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_change_room_status, "CHANGE ROOM STATUS");


        newRoomStatus = new NewRoomStatus() {
            @Override
            public void clicked(final int newRoomStatusId, String newRoomStatusDescription) {
                ConfirmWithRemarksDialog confirmWithRemarksDialog = new ConfirmWithRemarksDialog(getContext()) {
                    @Override
                    public void save(final String remarks) {
                        PasswordDialog passwordDialog = new PasswordDialog(getContext(),"CONFIRM CHANGE ROOM STATUS", "115") {
                            @Override
                            public void passwordSuccess(String employeeId, String employeeName) {

                                ChangeRoomStatusRequest cr =
                                        new ChangeRoomStatusRequest(String.valueOf(newRoomStatusId),
                                                roomId,
                                                employeeId,
                                                remarks);
                                changeStatus(cr);

                                dismiss();
                            }

                            @Override
                            public void passwordFailed() {

                            }
                        };
                        passwordDialog.show();
                    }
                };
                confirmWithRemarksDialog.show();
            }
        };


        listNewStatus = findViewById(R.id.listNewStatus);


        ChangeRoomStatusAdapter changeRoomStatusAdapter = new ChangeRoomStatusAdapter(resultList, newRoomStatus);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listNewStatus.setLayoutManager(llm);
        listNewStatus.setAdapter(changeRoomStatusAdapter);
        changeRoomStatusAdapter.notifyDataSetChanged();


    }


    public interface NewRoomStatus {
        void clicked(int newRoomStatusId, String newRoomStatusDescription);
    }

    public abstract void changeStatus(ChangeRoomStatusRequest changeRoomStatusRequest);

}
