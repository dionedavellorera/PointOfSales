package nerdvana.com.pointofsales.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.Utils;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.interfaces.CalcuContract;
import nerdvana.com.pointofsales.interfaces.ProductsContract;
import nerdvana.com.pointofsales.model.CalcuModel;
import nerdvana.com.pointofsales.model.ProductsModel;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;

public class CalculatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CalcuModel> buttonList;
    private CalcuContract calcuContract;
    private Context context;
    public CalculatorAdapter(List<CalcuModel> buttonList, CalcuContract calcuContract, Context context) {
        this.buttonList = new ArrayList<>(buttonList);
        this.context = context;
        this.calcuContract = calcuContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_calcu, viewGroup, false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView btnNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnNumber = itemView.findViewById(R.id.btnNumber);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        CalcuModel model = buttonList.get(i);
        ((ViewHolder)holder).btnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getValue().equalsIgnoreCase("x")) {
                    calcuContract.subtract();
                } else if (model.getValue().equalsIgnoreCase("c")) {
                    calcuContract.clear();
                } else {
                    calcuContract.clicked(model.getValue());
                }

            }
        });
        ((ViewHolder)holder).btnNumber.setText(model.getDisplay());

    }



    @Override
    public int getItemCount() {
        return buttonList.size();
    }


}
