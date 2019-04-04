package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.ViewReceiptResponse;
import nerdvana.com.pointofsales.dialogs.TransactionsDialog;

public class OrListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewReceiptResponse.Result> orList;
    private TransactionsDialog.OrList orImpl;
    public OrListAdapter(List<ViewReceiptResponse.Result> orList, TransactionsDialog.OrList orImpl) {
        this.orList = orList;
        this.orImpl = orImpl;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_official_receipts, viewGroup, false));
    }



    static class OrListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout rootView;
        public OrListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {


        if(holder instanceof OrListViewHolder){

            ((OrListViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orImpl.clicked(holder.getAdapterPosition());
                }
            });
            ((OrListAdapter.OrListViewHolder) holder).name.setText(orList.get(i).getReceiptNo());
        }

    }

    @Override
    public int getItemCount() {
        return orList.size();
    }
}
