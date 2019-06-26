package nerdvana.com.pointofsales.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.BusProvider;
import nerdvana.com.pointofsales.MainActivity;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.model.ChangeThemeModel;

public abstract class BaseDialog extends Dialog {

    protected ImageView backButton;
    protected TextView headerText;
    private RelativeLayout toptoptop;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @NonNull DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);

//        changeTheme();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


    }

    protected void setDialogLayout(int layout, String header) {
        setContentView(layout);
        backButton = findViewById(R.id.backButton);
        headerText = findViewById(R.id.displayHeader);
        toptoptop = findViewById(R.id.toptoptop);
        headerText.setText(header);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

//        changeTheme();


    }



//    private void changeTheme() {
//        BusProvider.getInstance().post(new ChangeThemeModel(""));
//        if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.THEME_SELECTED).isEmpty()) {
//            lightTheme();
//        } else {
//            if (SharedPreferenceManager.getString(getContext(), ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
//                lightTheme();
//            } else {
//                darkTheme();
//            }
//        }
//    }
//
//    private void lightTheme() {
//        toptoptop.setBackgroundColor(getContext().getResources().getColor(R.color.lightHeaderBg));
//        headerText.setTextColor(getContext().getResources().getColor(R.color.darkFont));
//        backButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.darkFont), android.graphics.PorterDuff.Mode.MULTIPLY);
//
//    }
//
//    private void darkTheme() {
//        toptoptop.setBackgroundColor(getContext().getResources().getColor(R.color.darkMainBg));
//        headerText.setTextColor(getContext().getResources().getColor(R.color.darkFont));
//        backButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.darkFont), android.graphics.PorterDuff.Mode.MULTIPLY);
//    }
}
