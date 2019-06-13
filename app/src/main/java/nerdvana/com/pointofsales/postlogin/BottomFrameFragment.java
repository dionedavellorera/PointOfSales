package nerdvana.com.pointofsales.postlogin;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_requests.FetchBranchInfoRequest;
import nerdvana.com.pointofsales.api_responses.FetchBranchInfoResponse;
import nerdvana.com.pointofsales.background.ButtonsAsync;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ChangeThemeModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomFrameFragment extends Fragment implements ButtonsContract, AsyncContract {
    private View view;
    private RecyclerView listButtons;
    private ButtonsAdapter buttonsAdapter;
    private ConstraintLayout mainContainer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_bottom_frame, container, false);

        initializeViews();

        setButtonsAdapter();

        return view;
    }

    private void initializeViews() {
        listButtons = view.findViewById(R.id.listButtons);
        mainContainer = view.findViewById(R.id.mainContainer);
    }

    private void setButtonsAdapter() {
        buttonsAdapter = new ButtonsAdapter(new ArrayList<ButtonsModel>(), this, getContext());
        listButtons.setLayoutManager(new GridLayoutManager(getContext(),2,  GridLayoutManager.HORIZONTAL, false));
        listButtons.setAdapter(buttonsAdapter);

        FetchBranchInfoRequest fetchBranchInfoRequest = new FetchBranchInfoRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchBranchInfoResponse> request = iUsers.fetchBranchInfo(fetchBranchInfoRequest.getMapValue());
        request.enqueue(new Callback<FetchBranchInfoResponse>() {
            @Override
            public void onResponse(Call<FetchBranchInfoResponse> call, Response<FetchBranchInfoResponse> response) {

                SharedPreferenceManager.saveString(getContext(), String.valueOf(response.body().getResult().getCompanyInfo().getIsRoom()), ApplicationConstants.IS_SYSTEM_ROOM);
                SharedPreferenceManager.saveString(getContext(), String.valueOf(response.body().getResult().getCompanyInfo().getIsTable()), ApplicationConstants.IS_SYSTEM_TABLE);


                new ButtonsAsync(BottomFrameFragment.this, getContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onFailure(Call<FetchBranchInfoResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void clicked(ButtonsModel buttonsModel) {
        BusProvider.getInstance().post(buttonsModel);
    }

    @Override
    public void doneLoading(List list, String isFor) {
        switch (isFor) {
            case "buttons":
                buttonsAdapter.addItems(list);
                break;
        }
    }


    private void changeTheme() {
        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.THEME_SELECTED).isEmpty()) {
            lightTheme();
        } else {
            if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                lightTheme();
            } else {
                darkTheme();
            }
        }
        setButtonsAdapter();
    }

    private void lightTheme() {
        mainContainer.setBackgroundColor(getResources().getColor(R.color.lightPrimary));
    }

    private void darkTheme() {
        mainContainer.setBackgroundColor(getResources().getColor(R.color.darkListBg));
    }

    @Subscribe
    public void changeTheme(ChangeThemeModel changeThemeModel) {
        changeTheme();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
    }
}
