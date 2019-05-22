package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SettingsActivity;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.dialogs.AvailableGcDialog;
import nerdvana.com.pointofsales.model.AvailableGcModel;
import nerdvana.com.pointofsales.model.ListSettingMenu;

public class SettingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListSettingMenu> settingMenuList;
    private SettingsActivity.Setting setting;
    public SettingListAdapter(List<ListSettingMenu> settingMenuList, SettingsActivity.Setting setting) {
        this.settingMenuList = settingMenuList;
        this.setting = setting;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SettingListAdapter.SettingViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_setting_menu, viewGroup, false));
    }

    static class SettingViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;
        private RelativeLayout row;
        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            row = itemView.findViewById(R.id.row);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof SettingListAdapter.SettingViewHolder){

            ((SettingViewHolder)holder).row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.clicked(i);
                }
            });

            ImageLoader.loadImage(R.mipmap.baseline_add_circle_outline_black_24, ((SettingViewHolder)holder).icon);
            ((SettingViewHolder)holder).name.setText(settingMenuList.get(i).getName());
        }
    }

    @Override
    public int getItemCount() {
        return settingMenuList.size();
    }
}
