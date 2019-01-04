package nerdvana.com.pointofsales.postlogin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.model.ButtonsModel;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ButtonsModel> buttonsModelList;
    public CategoryAdapter(List<ButtonsModel> buttonsModelList) {
        this.buttonsModelList = buttonsModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ButtonsAdapter.ButtonsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_category, viewGroup, false));
    }



    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private Button button;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

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
