package nerdvana.com.pointofsales.interfaces;

import nerdvana.com.pointofsales.model.ProductsModel;

public interface CheckoutItemsContract {
    void itemAdded(ProductsModel itemAdded);
    void itemRemoved(ProductsModel item);
}
