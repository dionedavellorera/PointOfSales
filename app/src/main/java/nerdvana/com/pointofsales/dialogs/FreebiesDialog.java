package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.adapters.AvailableGcAdapter;
import nerdvana.com.pointofsales.adapters.FreebiesListAdapter;
import nerdvana.com.pointofsales.api_requests.FetchRoomRatePriceIdRequest;
import nerdvana.com.pointofsales.api_responses.AddRoomPriceResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.api_responses.FetchRoomRatePriceIdResponse;
import nerdvana.com.pointofsales.model.RoomTableModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class FreebiesDialog extends BaseDialog {
    private List<FetchRoomPendingResponse.Freebies> freebiesList;
    private FreebiesListAdapter freebiesListAdapter;
    private RecyclerView listFreebies;
    private RoomBundleSelectionDialog roomBundleSelectionDialog;
    private RoomTableModel selectedRoom;
    private String postTransId;
    private Freeby freeby;
    private int qtySelected = 1;
    public FreebiesDialog(@NonNull Context context,
                          List<FetchRoomPendingResponse.Freebies> freebiesList,
                          RoomTableModel selectedRoom,
                          int qtySelected) {
        super(context);
        this.freebiesList = freebiesList;
        this.selectedRoom = selectedRoom;

        this.qtySelected = qtySelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_freebies, "FREEBIES");
        listFreebies = findViewById(R.id.listFreebies);



        freeby = new Freeby() {
            @Override
            public void clicked(final int position) {
                FetchRoomRatePriceIdRequest fetchRoomRatePriceIdRequest = new FetchRoomRatePriceIdRequest(String.valueOf(freebiesList.get(position).getPriceRateRoomId()));
                IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
                Call<FetchRoomRatePriceIdResponse> request = iUsers.fetchRoomRatePriceId(
                        fetchRoomRatePriceIdRequest.getMapValue());
                request.enqueue(new Callback<FetchRoomRatePriceIdResponse>() {
                    @Override
                    public void onResponse(Call<FetchRoomRatePriceIdResponse> call, Response<FetchRoomRatePriceIdResponse> response) {
                        if (response.body().getResult() != null) {

                            roomBundleSelectionDialog = new RoomBundleSelectionDialog(
                                    getContext(),
                                    response.body().getResult(),
                                    selectedRoom,
                                    String.valueOf(freebiesList.get(position).getPostTransId()),
                                    String.valueOf(freebiesList.get(position).getId()),
                                    freeby,
                                    qtySelected) {
                                @Override
                                public void completed() {
                                    roomBundleSelectionDialog.dismiss();
                                    freebySelected();
                                }
                            };
                            roomBundleSelectionDialog.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<FetchRoomRatePriceIdResponse> call, Throwable t) {
                    }
                });
            }
        };

        freebiesListAdapter = new FreebiesListAdapter(freebiesList, freeby);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listFreebies.setLayoutManager(llm);
        listFreebies.setAdapter(freebiesListAdapter);
        freebiesListAdapter.notifyDataSetChanged();
    }

    public abstract void freebySelected();

    public interface Freeby {
        void clicked(int position);
    }
}
