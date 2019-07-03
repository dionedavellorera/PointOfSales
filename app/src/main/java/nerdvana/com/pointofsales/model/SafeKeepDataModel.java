package nerdvana.com.pointofsales.model;

import android.widget.EditText;

public class SafeKeepDataModel implements Comparable<SafeKeepDataModel>{
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

    @Override
    public int compareTo(SafeKeepDataModel o) {
        if (Double.valueOf(value) < Double.valueOf(o.getValue())) {
            return 1;
        } else if(Double.valueOf(value) > Double.valueOf(o.getValue())) {
            return -1;
        }
        return 0;
    }
}
