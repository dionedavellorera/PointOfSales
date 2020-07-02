package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import nerdvana.com.pointofsales.PrintoutSelectionIntf;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.PrintingListModel;
import nerdvana.com.pointofsales.model.SunmiPrinterModel;
import nerdvana.com.pointofsales.postlogin.adapter.ProductsAdapter;

public class AllPrintoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PrintingListModel> printingListModelList;
    private PrintoutSelectionIntf printoutSelectionIntf;
    private Context context;
    public AllPrintoutAdapter(List<PrintingListModel> printingListModelList,
                              PrintoutSelectionIntf printoutSelectionIntf,
                              Context context) {
        this.printingListModelList = printingListModelList;
        this.printoutSelectionIntf = printoutSelectionIntf;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AllPrintoutAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_sunmi_printers, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private LinearLayout row;
        private ChipGroup chipGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            row = itemView.findViewById(R.id.row);
            chipGroup = itemView.findViewById(R.id.chipGroup);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof AllPrintoutAdapter.ViewHolder){
            PrintingListModel model = printingListModelList.get(holder.getAdapterPosition());
            ((AllPrintoutAdapter.ViewHolder) holder).name.setText(model.getNameToShow().toUpperCase());


            ((AllPrintoutAdapter.ViewHolder) holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    printoutSelectionIntf.clicked(holder.getAdapterPosition(), model);
                }
            });

            if (model.isEditing()) {
                ((ViewHolder) holder).row.setBackgroundColor(context.getResources().getColor(R.color.colorLtGrey));

            } else {
                ((AllPrintoutAdapter.ViewHolder) holder).row.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            }

            ((AllPrintoutAdapter.ViewHolder) holder).chipGroup.removeAllViews();
            if (model.getSelectedPrinterList().size() > 0) {
                int innerInc = 0;
                for (PrintingListModel.SelectedPrinterData str : model.getSelectedPrinterList()) {
//                    Chip chip = new Chip(context);
//                    chip.setText(str.toUpperCase());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(3, 3, 3, 3);
//                    chip.setLayoutParams(params);
                    View vg = LayoutInflater.from(context).inflate(R.layout.single_chip_layout, ((AllPrintoutAdapter.ViewHolder) holder).row, false);
                    Chip chip = (Chip) vg;
                    chip.setText(str.getPrinterName().toUpperCase());
                    chip.setLayoutParams(params);
                    int finalInnerInc = innerInc;
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            printoutSelectionIntf.closeClicked(holder.getAdapterPosition(), finalInnerInc);
                        }
                    });
                    ((ViewHolder) holder).chipGroup.addView(chip);
                    innerInc++;
                }
            }


        }
    }

    @Override
    public int getItemCount() {
        return printingListModelList.size();
    }
}
