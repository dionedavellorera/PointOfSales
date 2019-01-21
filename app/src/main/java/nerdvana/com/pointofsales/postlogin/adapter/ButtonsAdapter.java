package nerdvana.com.pointofsales.postlogin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ButtonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ButtonsModel> buttonsModelList;
    private ButtonsContract buttonsContract;
    public ButtonsAdapter(List<ButtonsModel> buttonsModelList, ButtonsContract buttonsContract) {
        this.buttonsModelList = buttonsModelList;
        this.buttonsContract = buttonsContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ButtonsAdapter.ButtonsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_buttons, viewGroup, false));
    }



    static class ButtonsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView imageUrl;
        private CardView rootView;
        public ButtonsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageUrl = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        ((ButtonsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsContract.clicked(buttonsModelList.get(i));
            }
        });
        ((ButtonsViewHolder)holder).name.setText(buttonsModelList.get(i).getName());
    }


    public void addItems(List<ButtonsModel> buttonsModelList) {
        this.buttonsModelList = buttonsModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return buttonsModelList.size();
    }
}
