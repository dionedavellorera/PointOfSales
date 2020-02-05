package nerdvana.com.pointofsales.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.dialogs.RateDialog;

public class RoomRatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RoomRateMain> rateList;
    private RateDialog.RoomRateImpl roomRateImpl;

    public RoomRatesAdapter(List<RoomRateMain> rateList, RateDialog.RoomRateImpl roomRateImpl) {
        this.rateList = rateList;
        this.roomRateImpl = roomRateImpl;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RatesViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_rates, viewGroup, false));
    }



    static class RatesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView amount;
        private RelativeLayout rootView;
        public RatesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        ((RatesViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomRateImpl.clicked(rateList.get(i));
            }
        });
        ((RatesViewHolder)holder).name.setText(rateList.get(i).getRatePrice().getRoomRate().getRoomRate());
        ((RatesViewHolder)holder).amount.setText(String.valueOf(rateList.get(i).getRatePrice().getAmount()));

    }


    @Override
    public int getItemCount() {
        return rateList.size();
    }
}
