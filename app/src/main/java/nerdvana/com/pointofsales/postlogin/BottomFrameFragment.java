package nerdvana.com.pointofsales.postlogin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.background.ButtonsAsync;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;

public class BottomFrameFragment extends Fragment implements ButtonsContract, AsyncContract {
    private View view;
    private RecyclerView listButtons;
    private ButtonsAdapter buttonsAdapter;
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
    }

    private void setButtonsAdapter() {
        buttonsAdapter = new ButtonsAdapter(new ArrayList<ButtonsModel>(), this);
        listButtons.setLayoutManager(new GridLayoutManager(getContext(),2,  GridLayoutManager.HORIZONTAL, false));
        listButtons.setAdapter(buttonsAdapter);
        new ButtonsAsync(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

}
