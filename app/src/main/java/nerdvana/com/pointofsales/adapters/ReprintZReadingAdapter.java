package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchXReadListResponse;
import nerdvana.com.pointofsales.api_responses.FetchZReadListResponse;
import nerdvana.com.pointofsales.dialogs.ReprintXReadingDialog;
import nerdvana.com.pointofsales.dialogs.ReprintZReadingDialog;

public class ReprintZReadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FetchZReadListResponse.Result> zreadList;
    private ReprintZReadingDialog.Item item;
    public ReprintZReadingAdapter(List<FetchZReadListResponse.Result> zreadList, ReprintZReadingDialog.Item item) {
        this.zreadList = zreadList;
        this.item = item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReprintZReadingAdapter.ZReadListHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_zreading, viewGroup, false));
    }

    static class ZReadListHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView total;
        private RelativeLayout row;
        public ZReadListHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            total = itemView.findViewById(R.id.total);
            row = itemView.findViewById(R.id.row);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ReprintZReadingAdapter.ZReadListHolder){
            ((ReprintZReadingAdapter.ZReadListHolder) holder).date.setText(zreadList.get(i).getGeneratedAt());
            ((ReprintZReadingAdapter.ZReadListHolder) holder).total.setText(String.valueOf(zreadList.get(i).getTotal()));

            ((ReprintZReadingAdapter.ZReadListHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RERERERe", String.valueOf(zreadList.get(i).getId()));
                    item.clicked(String.valueOf(zreadList.get(i).getId()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return zreadList.size();
    }

}
