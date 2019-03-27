package nerdvana.com.pointofsales.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.RoomRateMain;
import nerdvana.com.pointofsales.dialogs.ManualDiscountDialog;
import nerdvana.com.pointofsales.model.DiscountListModel;

public class DepartmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DiscountListModel> discountList;

    private Context context;
    private ManualDiscountDialog.CheckBoxItem checkBoxItem;

    private RecyclerView.RecycledViewPool viewPool;

    public DepartmentsAdapter(List<DiscountListModel> discountList, Context context, ManualDiscountDialog.CheckBoxItem checkBoxItem) {
        this.discountList = discountList;
        this.context = context;
        this.checkBoxItem = checkBoxItem;

        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DepartmentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dc_department, viewGroup, false));
    }



    static class DepartmentsViewHolder extends RecyclerView.ViewHolder {
        public CheckBox header;
        public RecyclerView discountProductsList;
        public DepartmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            discountProductsList = itemView.findViewById(R.id.discountProductsList);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        ((DepartmentsViewHolder)holder).header.setText(discountList.get(holder.getAdapterPosition()).getDepartment());

        ((DepartmentsViewHolder)holder).header.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxItem.isChecked(holder.getAdapterPosition(), isChecked);
            }
        });

        DiscountProductsAdapter discountProductsAdapter = new DiscountProductsAdapter(discountList.get(holder.getAdapterPosition()).getDiscountProductList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        ((DepartmentsAdapter.DepartmentsViewHolder)holder).discountProductsList.setLayoutManager(linearLayoutManager);
        ((DepartmentsAdapter.DepartmentsViewHolder)holder).discountProductsList.setAdapter(discountProductsAdapter);

        ((DepartmentsViewHolder) holder).discountProductsList.setRecycledViewPool(viewPool);

//        ((RoomRatesAdapter.DiscProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roomRateImpl.clicked(rateList.get(i));
//            }
//        });
//        ((RoomRatesAdapter.DiscProductsViewHolder)holder).name.setText(rateList.get(i).getRatePrice().getRoomRate().getRoomRate());
//        ((RoomRatesAdapter.DiscProductsViewHolder)holder).amount.setText(String.valueOf(rateList.get(i).getRatePrice().getAmount()));

    }


    @Override
    public int getItemCount() {
        return discountList.size();
    }

}
