package nerdvana.com.pointofsales.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchDepartmentsResponse;
import nerdvana.com.pointofsales.interfaces.CalcuContract;
import nerdvana.com.pointofsales.interfaces.ProductFilterContract;
import nerdvana.com.pointofsales.model.CalcuModel;
import nerdvana.com.pointofsales.model.ProductFilterModel;

public class ProductFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProductFilterModel> list;
    private ProductFilterContract productFilterContract;
    private Context context;
    public ProductFilterAdapter(List<ProductFilterModel> list, ProductFilterContract productFilterContract, Context context) {
        this.list = new ArrayList<>(list);
        this.context = context;
        this.productFilterContract = productFilterContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductFilterAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_product_filter, viewGroup, false));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        ProductFilterModel model = list.get(i);
//        ((CalculatorAdapter.ViewHolder)holder).btnNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (model.getValue().equalsIgnoreCase("x")) {
//                    calcuContract.subtract();
//                } else if (model.getValue().equalsIgnoreCase("c")) {
//                    calcuContract.clear();
//                } else {
//                    calcuContract.clicked(model.getValue());
//                }
//
//            }
//        });

        ((ViewHolder)holder).name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!compoundButton.isPressed()) {
                    return;
                }
                if (!b) {
                    model.setChecked(true);
                    productFilterContract.filterSelected(model);
                } else {
                    model.setChecked(false);
                    productFilterContract.filterSelected(model);
                }
            }
        });
        if (model.isChecked()) {
            ((ViewHolder)holder).name.setChecked(true);
        } else {
            ((ViewHolder)holder).name.setChecked(false);
        }
        ((ViewHolder)holder).name.setText(model.getDepartment().toUpperCase());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


}
