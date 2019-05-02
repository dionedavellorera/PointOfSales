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
        private ImageView skipImage;
        private ImageView voidImage;
        private TextView cutOffImage;
        public OrListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            skipImage = itemView.findViewById(R.id.skipImage);
            voidImage = itemView.findViewById(R.id.voidImage);
            rootView = itemView.findViewById(R.id.rootView);
            cutOffImage = itemView.findViewById(R.id.cutOffImage);
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
            orList.get(0).getXSkip();
            orList.get(0).getVoid();
            ((OrListAdapter.OrListViewHolder) holder).name.setText(orList.get(i).getReceiptNo());

            if (orList.get(i).getXSkip() == 1) {
                ((OrListViewHolder) holder).skipImage.setVisibility(View.VISIBLE);
            } else {
                ((OrListViewHolder) holder).skipImage.setVisibility(View.GONE);
            }

            if (orList.get(i).getVoid() == 1) {
                ((OrListViewHolder) holder).voidImage.setVisibility(View.VISIBLE);
            } else {
                ((OrListViewHolder) holder).voidImage.setVisibility(View.GONE);
            }

            if (orList.get(i).getIsCutOff() != 0) {
                ((OrListViewHolder)holder).cutOffImage.setVisibility(View.VISIBLE);
            } else {
                ((OrListViewHolder)holder).cutOffImage.setVisibility(View.GONE);
            }
        }



    }

    @Override
    public int getItemCount() {
        return orList.size();
    }
}
