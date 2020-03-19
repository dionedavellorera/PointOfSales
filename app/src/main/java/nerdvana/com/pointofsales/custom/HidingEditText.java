package nerdvana.com.pointofsales.custom;

import android.content.Context;
import android.util.AttributeSet;

public class HidingEditText extends androidx.appcompat.widget.AppCompatEditText {


    public HidingEditText(Context context) {
        super(context);
        hideKeyboard();
        init();
    }

    public HidingEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        hideKeyboard();
        init();
    }

    public HidingEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        hideKeyboard();
        init();
    }

    private void init() {
//        this.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        this.setLongClickable(false);
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    private void hideKeyboard() {
        setTextIsSelectable(false);
    }

//    @Override
//    public boolean onCheckIsTextEditor() {
//        return false;
//    }
}
