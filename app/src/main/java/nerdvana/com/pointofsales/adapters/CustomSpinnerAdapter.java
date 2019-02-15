package nerdvana.com.pointofsales.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private List<String> roomSpinnerModelList;
    private Context context;
    public CustomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context  = context;
        this.roomSpinnerModelList = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        TextView name = (TextView) view.findViewById(R.id.spinnerItem);
        name.setText(roomSpinnerModelList.get(position));
        return view;
    }
}
