package nerdvana.com.pointofsales.custom;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public abstract class AlertYesNo extends AlertDialog.Builder {

    private String message;

    public AlertYesNo(@NonNull Context context, String message) {
        super(context);
        setMessage(message);

        setPositiveButton("YES", clickListener);
        setNegativeButton("NO", clickListener);
    }

    DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    yesClicked();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    noClicked();
                    break;
            }
        }
    };


    public abstract void yesClicked();
    public abstract void noClicked();


}
