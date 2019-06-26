package nerdvana.com.pointofsales.postlogin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.custom.ImageLoader;
import nerdvana.com.pointofsales.interfaces.ButtonsContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ButtonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ButtonsModel> buttonsModelList;
    private ButtonsContract buttonsContract;
    private List<String> shortcutString;
    private Context context;
    public ButtonsAdapter(List<ButtonsModel> buttonsModelList, ButtonsContract buttonsContract, Context context) {
        this.buttonsModelList = buttonsModelList;
        this.buttonsContract = buttonsContract;
        this.context = context;
        this.shortcutString = new ArrayList<>();
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
        private RelativeLayout relView;
        public ButtonsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageUrl = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.rootView);
            relView = itemView.findViewById(R.id.relView);
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

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) { //show light theme
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                ((ButtonsViewHolder)holder).relView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bottom_button_light));
            } else {
                ((ButtonsViewHolder)holder).relView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_button_light));
            }

            ((ButtonsViewHolder)holder).name.setTextColor(context.getResources().getColorStateList(R.color.light_text_color));
        } else {
            if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ((ButtonsViewHolder)holder).relView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bottom_button_light));
                } else {
                    ((ButtonsViewHolder)holder).relView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_button_light));
                }
                ((ButtonsViewHolder)holder).name.setTextColor(context.getResources().getColorStateList(R.color.light_text_color));
            } else {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    ((ButtonsViewHolder)holder).relView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bottom_button_dark));
                } else {
                    ((ButtonsViewHolder)holder).relView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_button_dark));
                }
                ((ButtonsViewHolder)holder).name.setTextColor(context.getResources().getColorStateList(R.color.dark_text_color));
            }
        }


        String firstString = buttonsModelList.get(i).getName().substring(0, 1);
        String remainingString = buttonsModelList.get(i).getName().substring(1);
        String finalString = String.format("<b><u>%s</b></u>%s", firstString, remainingString);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((ButtonsViewHolder)holder).name.setText(Html.fromHtml(finalString, Html.FROM_HTML_MODE_LEGACY));
        } else {
            ((ButtonsViewHolder)holder).name.setText(Html.fromHtml(finalString));
        }


        if (!TextUtils.isEmpty(buttonsModelList.get(i).getImageUrl())) {
            ((ButtonsViewHolder)holder).imageUrl.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(
                    buttonsModelList.get(i).getImageUrl(),
                    ((ButtonsViewHolder)holder).imageUrl
            );
        }

        if (buttonsModelList.get(i).isEnabled()) {
            ((ButtonsViewHolder)holder).rootView.setEnabled(true);
        } else {
            ((ButtonsViewHolder)holder).rootView.setEnabled(false);
        }
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
