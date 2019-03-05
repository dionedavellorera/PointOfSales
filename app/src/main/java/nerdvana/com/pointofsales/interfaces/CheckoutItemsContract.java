package nerdvana.com.pointofsales.interfaces;

import android.view.View;

import nerdvana.com.pointofsales.model.CartItemsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public interface CheckoutItemsContract {
    void itemAdded(ProductsModel itemAdded);
    void itemRemoved(ProductsModel item);
    void itemSelected(CartItemsModel itemSelected, int position);
    void itemLongClicked(CartItemsModel itemSelected, int position, View view);
}
