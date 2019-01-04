package nerdvana.com.pointofsales.prelogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nerdvana.com.pointofsales.R;

public class LeftFrameFragment extends Fragment{
    private View view;
    public static LeftFrameFragment newInstance() {
        LeftFrameFragment leftFrameFragment = new LeftFrameFragment();
        return leftFrameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prelogin_left_frame_fragment, container, false);
        return view;
    }
}
