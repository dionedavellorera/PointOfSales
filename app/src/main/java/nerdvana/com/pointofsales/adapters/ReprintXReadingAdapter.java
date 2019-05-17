package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.dialogs.ReprintXReadingDialog;

public class ReprintXReadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchXReadListResponse.Result> xreadList;
    private ReprintXReadingDialog.Item item;
    public ReprintXReadingAdapter(List<FetchXReadListResponse.Result> xreadList, ReprintXReadingDialog.Item item) {
        this.xreadList = xreadList;
        this.item = item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReprintXReadingAdapter.XReadListHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_xreading, viewGroup, false));
    }

    static class XReadListHolder extends RecyclerView.ViewHolder {
        private TextView shift;
        private TextView date;
        private TextView total;
        private RelativeLayout row;
        public XReadListHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            shift = itemView.findViewById(R.id.shiftLabel);
            total = itemView.findViewById(R.id.total);
            row = itemView.findViewById(R.id.row);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ReprintXReadingAdapter.XReadListHolder){
            ((XReadListHolder) holder).date.setText(xreadList.get(i).getCutOffDate());
            ((XReadListHolder) holder).shift.setText("SHIFT# " + xreadList.get(i).getShiftNo());
            ((XReadListHolder) holder).total.setText(xreadList.get(i).getTotal() + "(" + xreadList.get(i).getTransactions().size() +")");

            ((XReadListHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.clicked(xreadList.get(i).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return xreadList.size();
    }
}
