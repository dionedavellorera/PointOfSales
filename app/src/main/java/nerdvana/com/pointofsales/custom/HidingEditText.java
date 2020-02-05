package nerdvana.com.pointofsales.custom;

import android.content.Context;
import android.util.AttributeSet;

public class HidingEditText extends androidx.appcompat.widget.AppCompatEditText {


    public HidingEditText(Context context) {
        super(context);
        hideKeyboard();
    }

    public HidingEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        hideKeyboard();
    }

    public HidingEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        hideKeyboard();
    }

    private void hideKeyboard() {
        setTextIsSelectable(true);
    }

//    @Override
//    public boolean onCheckIsTextEditor() {
//        return false;
//    }
}
