package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.PosSelectionAdapter;
import nerdvana.com.pointofsales.api_requests.PosMachinesRequest;
import nerdvana.com.pointofsales.api_responses.PosMachinesResponse;
import nerdvana.com.pointofsales.interfaces.PosListContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToMachineSelectionDialog extends BaseDialog {

    private RecyclerView rvPosList;
    private PosListContract posListContract;
    public ToMachineSelectionDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_pos_selections, "Pos Machines");
        initViews();

        setupPosListAdapter();

    }

    private void setupPosListAdapter() {

        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        PosMachinesRequest posMachinesRequest = new PosMachinesRequest();
        Call<PosMachinesResponse> request = iUsers.fetchPosMachines(posMachinesRequest.getMapValue());
        request.enqueue(new Callback<PosMachinesResponse>() {
            @Override
            public void onResponse(Call<PosMachinesResponse> call, Response<PosMachinesResponse> response) {
                posListContract = new PosListContract() {
                    @Override
                    public void clicked(int position) {
                        SharedPreferenceManager.saveString(getContext(), SharedPreferenceManager.getString(getContext(), ApplicationConstants.MACHINE_ID), ApplicationConstants.POS_TO_ID);
                        SharedPreferenceManager.saveString(getContext(), response.body().getResultList().get(position).getId(), ApplicationConstants.MACHINE_ID);
                        dismiss();
                    }
                };

                PosSelectionAdapter posSelectionAdapter = new PosSelectionAdapter(response.body().getResultList(), posListContract);
                rvPosList.setAdapter(posSelectionAdapter);
                rvPosList.setLayoutManager(new LinearLayoutManager(getContext()));
                posSelectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PosMachinesResponse> call, Throwable t) {

            }
        });

    }

    private void initViews() {
        rvPosList = findViewById(R.id.rvPosList);
    }
}
