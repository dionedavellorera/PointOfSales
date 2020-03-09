package nerdvana.com.pointofsales.postlogin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.IUsers;
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
import nerdvana.com.pointofsales.model.MachineChangeRefresh;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomFrameFragment extends Fragment implements ButtonsContract, AsyncContract {
    private View view;
    private RecyclerView listButtons;
    private ButtonsAdapter buttonsAdapter;
    private ConstraintLayout mainContainer;
    LayoutAnimationController anim;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.postlogin_bottom_frame, container, false);
        anim = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);
        initializeViews();

//        setButtonsAdapter();

        return view;
    }

    private void initializeViews() {
        progressBar = view.findViewById(R.id.progressBar);
//        Drawable draw= getContext().getResources().getDrawable(R.drawable.customprogressbar);
//        progressBar.setProgressDrawable(draw);

        listButtons = view.findViewById(R.id.listButtons);
        mainContainer = view.findViewById(R.id.mainContainer);
    }

    private void setButtonsAdapter() {
        progressBar.setVisibility(View.VISIBLE);
        buttonsAdapter = new ButtonsAdapter(new ArrayList<ButtonsModel>(), this, getContext());
        listButtons.setLayoutManager(new GridLayoutManager(getContext(),2,  GridLayoutManager.HORIZONTAL, false));
        listButtons.setAdapter(buttonsAdapter);
        listButtons.setLayoutAnimation(anim);
        FetchBranchInfoRequest fetchBranchInfoRequest = new FetchBranchInfoRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        Call<FetchBranchInfoResponse> request = iUsers.fetchBranchInfo(fetchBranchInfoRequest.getMapValue());
        request.enqueue(new Callback<FetchBranchInfoResponse>() {
            @Override
            public void onResponse(Call<FetchBranchInfoResponse> call, Response<FetchBranchInfoResponse> response) {
                if (response.body() != null) {
                    SharedPreferenceManager.saveString(getContext(), String.valueOf(response.body().getResult().getCompanyInfo().getIsRoom()), ApplicationConstants.IS_SYSTEM_ROOM);
                    SharedPreferenceManager.saveString(getContext(), String.valueOf(response.body().getResult().getCompanyInfo().getIsTable()), ApplicationConstants.IS_SYSTEM_TABLE);
                    new ButtonsAsync(BottomFrameFragment.this, getContext()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

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
//        Log.d("WEKWEK", "ISDONEBOTTOM");
        switch (isFor) {
            case "buttons":
                progressBar.setVisibility(View.GONE);
                buttonsAdapter.addItems(list);
                buttonsAdapter.notifyDataSetChanged();
                listButtons.scheduleLayoutAnimation();
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

    @Subscribe
    public void machineChangeReqeust(MachineChangeRefresh machineChangeRefresh) {
        setButtonsAdapter();
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
