package nerdvana.com.pointofsales.interfaces;

import java.util.List;

import nerdvana.com.pointofsales.model.ProductsModel;

public interface RetrieveCartItemContract {
    void cartItemRetrieved(List<ProductsModel> cartItemList);
}
