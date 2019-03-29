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
        return new DepartmentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dc_department, viewGroup, false), checkBoxItem);
    }


    static class DepartmentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox header;
        public RecyclerView discountProductsList;
        public ManualDiscountDialog.CheckBoxItem checkBoxItem;
        public DepartmentsViewHolder(@NonNull View itemView, final ManualDiscountDialog.CheckBoxItem checkBoxItem) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            discountProductsList = itemView.findViewById(R.id.discountProductsList);
            this.checkBoxItem = checkBoxItem;


            itemView.setOnClickListener(this);

            header.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxItem.isChecked(getAdapterPosition(), isChecked);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.header:
                    break;
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        ((DepartmentsViewHolder)holder).header.setText(discountList.get(i).getDepartment());

//        DiscountProductsAdapter discountProductsAdapter = new DiscountProductsAdapter(discountList.get(i).getDiscountProductList());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setInitialPrefetchItemCount(discountList.get(i).getDiscountProductList().size());
//        ((DepartmentsAdapter.DepartmentsViewHolder)holder).discountProductsList.setLayoutManager(linearLayoutManager);
//        ((DepartmentsAdapter.DepartmentsViewHolder)holder).discountProductsList.setAdapter(discountProductsAdapter);
//
//        ((DepartmentsViewHolder) holder).discountProductsList.setRecycledViewPool(viewPool);

//        discountProductsAdapter.setHasStableIds(true);

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
