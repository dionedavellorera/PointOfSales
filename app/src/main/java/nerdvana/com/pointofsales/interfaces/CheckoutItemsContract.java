package nerdvana.com.pointofsales.interfaces;

import android.view.View;

import nerdvana.com.pointofsales.model.ProductsModel;

public interface CheckoutItemsContract {
    void itemAdded(ProductsModel itemAdded);
    void itemRemoved(ProductsModel item);
    void itemSelected(ProductsModel itemSelected, int position);
    void itemLongClicked(ProductsModel itemSelected, int position, View view);
}
