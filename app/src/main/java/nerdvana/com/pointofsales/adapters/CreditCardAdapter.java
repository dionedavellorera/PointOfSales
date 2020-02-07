package nerdvana.com.pointofsales.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.api_responses.FetchCreditCardResponse;
import nerdvana.com.pointofsales.interfaces.CreditCardContract;
import nerdvana.com.pointofsales.model.CreditCardListModel;

public class CreditCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CreditCardListModel> cardList;
    private CreditCardContract creditCardContract;
    public CreditCardAdapter(List<CreditCardListModel> cardList, CreditCardContract creditCardContract) {
        this.cardList = cardList;
        this.creditCardContract = creditCardContract;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CreditCardAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_credit_card, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;
        private ImageView ivCheck;
        private CardView rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.rootView);
            ivCheck = itemView.findViewById(R.id.ivCheck);
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof ViewHolder){

            CreditCardListModel model = cardList.get(i);

//            ((AvailableGcAdapter.ApproveListGcViewHolder) holder).remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    item.remove(i);
//                }
//            });
            ((ViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creditCardContract.clicked(model, holder.getAdapterPosition());
                }
            });
            if (model.isIs_selected()) {
                ((ViewHolder) holder).ivCheck.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).ivCheck.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).name.setText(model.getCredit_card());

            ((ViewHolder) holder).image.setBackgroundResource(model.getImage_url());
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

}
