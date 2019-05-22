package nerdvana.com.pointofsales.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SettingsActivity;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.fragment.PrinterConnectionFragment;
import nerdvana.com.pointofsales.model.ListSettingMenu;
import nerdvana.com.pointofsales.model.OtherPrinterModel;

public class OtherPrinterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OtherPrinterModel> otherPrinterModelList;
    private PrinterConnectionFragment.PrinterConnection printerConnection;
    private Context context;
    public OtherPrinterAdapter(List<OtherPrinterModel> otherPrinterModelList,
                               PrinterConnectionFragment.PrinterConnection printerConnection,
                               Context context) {
        this.otherPrinterModelList = otherPrinterModelList;
        this.printerConnection = printerConnection;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OtherPrinterAdapter.OtherPrinterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_other_printer, viewGroup, false));
    }

    static class OtherPrinterViewHolder extends RecyclerView.ViewHolder {
        private TextView head;
        private TextView sub;
        private TextView activeText;
        private RelativeLayout row;
        public OtherPrinterViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.head);
            sub = itemView.findViewById(R.id.sub);
            activeText = itemView.findViewById(R.id.activeText);
            row = itemView.findViewById(R.id.row);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof OtherPrinterAdapter.OtherPrinterViewHolder){

            ((OtherPrinterAdapter.OtherPrinterViewHolder)holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printerConnection.clicked(i);
                }
            });

            String printerSelected = "";

            if (!SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY).isEmpty()) {
                printerSelected = SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PRINTER_MANUALLY);
            } else {
                if (!SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT).isEmpty()) {
                    printerSelected = SharedPreferenceManager.getString(context, ApplicationConstants.SELECTED_PORT);
                } else {
                    printerSelected = "N/A";
                }

            }

            if (printerSelected.equalsIgnoreCase(otherPrinterModelList.get(i).getHead())) {
                ((OtherPrinterViewHolder)holder).activeText.setVisibility(View.VISIBLE);
            } else {
                ((OtherPrinterViewHolder)holder).activeText.setVisibility(View.GONE);
            }

            ((OtherPrinterViewHolder)holder).head.setText(otherPrinterModelList.get(i).getHead());
            ((OtherPrinterViewHolder)holder).sub.setText(otherPrinterModelList.get(i).getSub());
        }
    }

    @Override
    public int getItemCount() {
        return otherPrinterModelList.size();
    }
}
