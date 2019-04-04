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

    private boolean onBind;


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
        return new DepartmentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_dc_department, viewGroup, false), checkBoxItem, discountList);
    }


    static class DepartmentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox header;
        public RecyclerView discountProductsList;
        public ManualDiscountDialog.CheckBoxItem checkBoxItem;
        private List<DiscountListModel> dd;
        public DepartmentsViewHolder(@NonNull View itemView,
                                     final ManualDiscountDialog.CheckBoxItem checkBoxItem,
                                     List<DiscountListModel> discountList) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            discountProductsList = itemView.findViewById(R.id.discountProductsList);
            this.checkBoxItem = checkBoxItem;
            itemView.setOnClickListener(this);
            this.dd = discountList;

//            header.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    checkBoxItem.isChecked(getAdapterPosition(), isChecked);
//                    dd.get(getAdapterPosition()).setChecked(isChecked);
//                }
//            });

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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        onBind = true;
        final DiscountListModel dlm = discountList.get(i);
        ((DepartmentsViewHolder)holder).header.setText(discountList.get(i).getDepartment());

        ((DepartmentsViewHolder)holder).header.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!onBind) {
                    dlm.setChecked(isChecked);

                    for (DiscountListModel.DiscountProduct result : dlm.getDiscountProductList()) {
                        result.setChecked(isChecked);
                    }


                    notifyItemChanged(holder.getAdapterPosition());
                }
//                checkBoxItem.isChecked(holder.getAdapterPosition(), isChecked);
//                notifyItemChanged(holder.getAdapterPosition());
//                dlm.setChecked(isChecked);
            }
        });
        if (discountList.get(i).isChecked()) {
            ((DepartmentsViewHolder)holder).header.setChecked(true);
        } else {
            ((DepartmentsViewHolder)holder).header.setChecked(false);
        }
        DiscountProductsAdapter discountProductsAdapter = new DiscountProductsAdapter(discountList.get(i).getDiscountProductList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(((DepartmentsViewHolder) holder).discountProductsList.getContext());
        ((DepartmentsAdapter.DepartmentsViewHolder)holder).discountProductsList.setLayoutManager(linearLayoutManager);
        ((DepartmentsAdapter.DepartmentsViewHolder)holder).discountProductsList.setAdapter(discountProductsAdapter);

        ((DepartmentsViewHolder) holder).discountProductsList.setRecycledViewPool(viewPool);


        onBind = false;
    }


    @Override
    public int getItemCount() {
        return discountList.size();
    }

}
