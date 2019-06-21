package nerdvana.com.pointofsales.postlogin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class CheckoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CartItemsModel> cartItemsList;
    private CheckoutItemsContract checkoutItemsContract;
    private Context context;
    public CheckoutAdapter(List<CartItemsModel> cartItemsList, CheckoutItemsContract checkoutItemsContract, Context context) {
        this.cartItemsList = cartItemsList;
        this.checkoutItemsContract = checkoutItemsContract;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CheckoutAdapter.ProductsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_checkout_items, viewGroup, false));
    }


    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView quantity;
        private TextView price;
        private TextView totalPrice;
        private ImageView iconStatus;
        private ConstraintLayout rootView;
        private LinearLayout rootView1;
        private LinearLayout linearBundle;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            iconStatus = itemView.findViewById(R.id.iconStatus);
            rootView = itemView.findViewById(R.id.rootView);
            rootView1 = itemView.findViewById(R.id.rootView1);
            linearBundle = itemView.findViewById(R.id.linearBundle);
        }

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        final CartItemsModel cartItem = cartItemsList.get(holder.getAdapterPosition());

        if(holder instanceof CheckoutAdapter.ProductsViewHolder){
            if (!cartItem.isPosted()) {
                ((ProductsViewHolder) holder).iconStatus.setVisibility(View.GONE);
            } else {
                ((ProductsViewHolder) holder).iconStatus.setVisibility(View.VISIBLE);
            }

            ((ProductsViewHolder)holder).rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    checkoutItemsContract.itemLongClicked(cartItem, holder.getAdapterPosition(), v);
                    return false;
                }
            });


            if (cartItem.getAlaCarteList().size() > 0 ||
                    cartItem.getGroupList().size() > 0 ||
                    cartItem.getTransactionPostFreebies() != null) {
                ((ProductsViewHolder)holder).linearBundle.removeAllViews();
            }

            if (cartItem.getTransactionPostFreebies() != null) {

                if (cartItem.getTransactionPostFreebies().getTransactionPostFreebyCoreProduct() != null) {
                    TextView tv = new TextView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tv.setPadding(30,0,0,0);
                    tv.setLayoutParams(params);
                    tv.setText(String.format("(%s) %s", cartItem.getTransactionPostFreebies().getQty(), cartItem.getTransactionPostFreebies().getTransactionPostFreebyCoreProduct().getProductInitial()));
                    if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            tv.setTextColor(Color.WHITE);
                        }
                    }
                    ((ProductsViewHolder)holder).linearBundle.addView(tv);
                }


                if (cartItem.getTransactionPostFreebies().getTransactionPostAlaCart().size() > 0) {
                    for (FetchRoomPendingResponse.PostAlaCart alaCarte : cartItem.getTransactionPostFreebies().getTransactionPostAlaCart()) {
                        TextView tv = new TextView(context);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tv.setPadding(30,0,0,0);
                        tv.setLayoutParams(params);
                        tv.setText(String.format("--(%s) %s", alaCarte.getQty(), alaCarte.getPostAlaCartProduct().getProductInitial()));
                        if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                                tv.setTextColor(Color.BLACK);
                            } else {
                                tv.setTextColor(Color.WHITE);
                            }
                        }
                        ((ProductsViewHolder)holder).linearBundle.addView(tv);
                    }
                }



                if (cartItem.getTransactionPostFreebies().getTransactionPostGroup().size() > 0) {
                    for (FetchRoomPendingResponse.PostGroup grp : cartItem.getTransactionPostFreebies().getTransactionPostGroup()) {
                        for (FetchRoomPendingResponse.PostGroupItem arpm : grp.getPostGroupItems()) {
                            TextView tv = new TextView(context);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            tv.setPadding(30,0,0,0);
                            tv.setLayoutParams(params);
                            tv.setText(String.format("--(%s) %s", arpm.getQty(), arpm.getPostGroupItemProduct().getProductInitial()));
                            if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) {
                                tv.setTextColor(Color.BLACK);
                            } else {
                                if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                                    tv.setTextColor(Color.BLACK);
                                } else {
                                    tv.setTextColor(Color.WHITE);
                                }
                            }
                            ((ProductsViewHolder)holder).linearBundle.addView(tv);
                        }
                    }
                }

            }

            if (cartItem.getAlaCarteList().size() > 0) {
                for (AddRateProductModel.AlaCarte alaCarte : cartItem.getAlaCarteList()) {
                    TextView tv = new TextView(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tv.setPadding(30,0,0,0);
                    tv.setLayoutParams(params);
                    tv.setText(String.format("(%s) %s", alaCarte.getQty(), alaCarte.getProduct_initial()));
                    if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) {
                        tv.setTextColor(Color.BLACK);
                    } else {
                        if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            tv.setTextColor(Color.WHITE);
                        }
                    }
                    ((ProductsViewHolder)holder).linearBundle.addView(tv);
                }
            }
            if (cartItem.getGroupList().size() > 0) {
                for (AddRateProductModel.Group grp : cartItem.getGroupList()) {
                    for (AddRateProductModel arpm : grp.getGroupCompoList().getItem()) {
                        TextView tv = new TextView(context);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tv.setPadding(30,0,0,0);
                        tv.setLayoutParams(params);
                        tv.setText(String.format("(%s) %s", arpm.getQty(), arpm.getProduct_initial()));
                        if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) {
                            tv.setTextColor(Color.BLACK);
                        } else {
                            if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                                tv.setTextColor(Color.BLACK);
                            } else {
                                tv.setTextColor(Color.WHITE);
                            }
                        }
                        ((ProductsViewHolder)holder).linearBundle.addView(tv);
                    }
                }
            }

            if (cartItem.getAlaCarteList().size() < 1 &&
                    cartItem.getGroupList().size() < 1 &&
                cartItem.getTransactionPostFreebies() == null) {
                ((ProductsViewHolder)holder).linearBundle.removeAllViews();
            }


            ((ProductsViewHolder)holder).name.setText(cartItem.getName());
            ((ProductsViewHolder)holder).quantity.setText(String.valueOf(cartItem.getQuantity())); //oki
            ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice()));

            if (cartItem.getType().equalsIgnoreCase("ot")) {
                ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice() / cartItem.getQuantity()));
                ((ProductsViewHolder)holder).totalPrice.setText(String.valueOf(cartItem.getUnitPrice()));

            } else {

                ((ProductsViewHolder)holder).rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkoutItemsContract.itemSelected(cartItem, holder.getAdapterPosition());
                        notifyItemChanged(i);
                    }
                });


                ((ProductsViewHolder)holder).price.setText(String.valueOf(cartItem.getUnitPrice()));
                ((ProductsViewHolder)holder).totalPrice.setText(String.valueOf(cartItem.getUnitPrice() * cartItem.getQuantity()));
            }






//            if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) { //show light theme
//                ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                ((ProductsViewHolder)holder).name.setTextColor(Color.BLACK);
//                ((ProductsViewHolder)holder).price.setTextColor(Color.BLACK);
//                ((ProductsViewHolder)holder).quantity.setTextColor(Color.BLACK);
//                ((ProductsViewHolder)holder).totalPrice.setTextColor(Color.BLACK);
//            } else {
//                if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
//                    ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
//                    ((ProductsViewHolder)holder).name.setTextColor(Color.BLACK);
//                    ((ProductsViewHolder)holder).price.setTextColor(Color.BLACK);
//                    ((ProductsViewHolder)holder).quantity.setTextColor(Color.BLACK);
//                    ((ProductsViewHolder)holder).totalPrice.setTextColor(Color.BLACK);
//                } else {
//                    ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
//                    ((ProductsViewHolder)holder).name.setTextColor(Color.WHITE);
//                    ((ProductsViewHolder)holder).price.setTextColor(Color.WHITE);
//                    ((ProductsViewHolder)holder).quantity.setTextColor(Color.WHITE);
//                    ((ProductsViewHolder)holder).totalPrice.setTextColor(Color.WHITE);
//                }
//            }

            if (cartItem.isSelected()) {
                if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) { //show light theme
                    ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                } else {
                    if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                        ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                        ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                        ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                        ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                        ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                        ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                        ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.lightColorAccent));
                    } else {
                        ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                        ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                        ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                        ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                        ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                        ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                        ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.darkColorAccent));
                    }
                }
            } else {
                if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).isEmpty()) { //show light theme
                    ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                    ((ProductsViewHolder)holder).name.setTextColor(Color.BLACK);
                    ((ProductsViewHolder)holder).price.setTextColor(Color.BLACK);
                    ((ProductsViewHolder)holder).quantity.setTextColor(Color.BLACK);
                    ((ProductsViewHolder)holder).totalPrice.setTextColor(Color.BLACK);
                } else {
                    if (SharedPreferenceManager.getString(context, ApplicationConstants.THEME_SELECTED).equalsIgnoreCase("light")) {
                        ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.lightListBg));
                        ((ProductsViewHolder)holder).name.setTextColor(Color.BLACK);
                        ((ProductsViewHolder)holder).price.setTextColor(Color.BLACK);
                        ((ProductsViewHolder)holder).quantity.setTextColor(Color.BLACK);
                        ((ProductsViewHolder)holder).totalPrice.setTextColor(Color.BLACK);
                    } else {
                        ((ProductsViewHolder)holder).name.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).iconStatus.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).rootView1.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).rootView.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).price.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).quantity.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).totalPrice.setBackgroundColor(context.getResources().getColor(R.color.darkListBg));
                        ((ProductsViewHolder)holder).name.setTextColor(Color.WHITE);
                        ((ProductsViewHolder)holder).price.setTextColor(Color.WHITE);
                        ((ProductsViewHolder)holder).quantity.setTextColor(Color.WHITE);
                        ((ProductsViewHolder)holder).totalPrice.setTextColor(Color.WHITE);
                    }
                }
            }



        }
    }


    public void addItems(List<CartItemsModel> cartItemsList) {
        this.cartItemsList = cartItemsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }


}
