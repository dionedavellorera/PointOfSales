package nerdvana.com.pointofsales.model;

import android.widget.EditText;

public class SafeKeepDataModel {
    private EditText editText;
    private String value;

    public SafeKeepDataModel(EditText editText, String value) {
        this.editText = editText;
        this.value = value;
    }

    public EditText getEditText() {
        return editText;
    }

    public String getValue() {
        return value;
    }
}
