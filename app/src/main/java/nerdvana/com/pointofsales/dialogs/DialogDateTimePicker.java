package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import nerdvana.com.pointofsales.R;

public abstract class DialogDateTimePicker extends BaseDialog
{
    private String type = "";
    public DialogDateTimePicker(@NonNull Context context, String type) {
        super(context);
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_time_picker);

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("TIMEVAL", String.format("%d-%d-%d %d%d:00", datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute()))
//            }
//        });
    }

    public abstract void newTimeSet();
}
