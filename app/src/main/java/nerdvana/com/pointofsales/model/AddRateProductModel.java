package nerdvana.com.pointofsales.model;

import android.support.annotation.NonNull;

public class AddRateProductModel {
    private String product_id;
    private String room_rate_price_id;
    private String qty;
    private String tax;
    private String price;
    private int is_price_changed;
    private String product_initial;
    public AddRateProductModel(String product_id, String room_rate_price_id,
                               String qty, String tax,
                               String price, int isPriceChanged,
                               String productInitial) {
        this.product_id = product_id;
        this.room_rate_price_id = room_rate_price_id;
        this.qty = qty;
        this.tax = tax;
        this.price = price;
        this.is_price_changed = isPriceChanged;
        this.product_initial = productInitial;
    }

    public String getProduct_initial() {
        return product_initial;
    }

    public void setIs_price_changed(int is_price_changed) {
        this.is_price_changed = is_price_changed;
    }

    public int getIs_price_changed() {
        return is_price_changed;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getRoom_rate_price_id() {
        return room_rate_price_id;
    }

    public String getQty() {
        return qty;
    }

    public String getTax() {
        return tax;
    }
}
