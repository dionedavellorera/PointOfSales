package nerdvana.com.pointofsales.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.postlogin.adapter.ButtonsAdapter;

public class RoomRatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RoomRateMain> rateList;
    public RoomRatesAdapter(List<RoomRateMain> rateList) {
        this.rateList = rateList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RatesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_rates, viewGroup, false));
    }



    static class RatesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView amount;
        private LinearLayout rootView;
        public RatesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
//        ((ButtonsAdapter.ButtonsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonsContract.clicked(buttonsModelList.get(i));
//            }
//        });
//        ((ButtonsAdapter.ButtonsViewHolder)holder).name.setText(buttonsModelList.get(i).getName());
        ((RatesViewHolder)holder).name.setText(rateList.get(i).getRatePrice().getRoomRate().getRoomRate());
        ((RatesViewHolder)holder).amount.setText(String.valueOf(rateList.get(i).getRatePrice().getAmount()));
    }


    @Override
    public int getItemCount() {
        return rateList.size();
    }
}
