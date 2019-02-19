package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.SqlQueries;
import nerdvana.com.pointofsales.entities.CartEntity;
import nerdvana.com.pointofsales.interfaces.RetrieveCartItemContract;
import nerdvana.com.pointofsales.model.ProductsModel;

public class RetrieveCartItemsAsync extends AsyncTask<Void, Void, List<ProductsModel>> {
    private List<ProductsModel> cartList;
    private String transactionId;
    private RetrieveCartItemContract retrieveCartItemContract;
    public RetrieveCartItemsAsync(String transactionId, RetrieveCartItemContract retrieveCartItemContract) {
        this.transactionId = transactionId;
        this.cartList = new ArrayList<>();
        this.retrieveCartItemContract = retrieveCartItemContract;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<ProductsModel> doInBackground(Void... voids) {

//        String name, double price,
//        double vat, boolean isAvailable,
//        String[] imageUrls, boolean isVattable,
//        String shortName, List<ProductsModel> productsList,
//        boolean isSelected, boolean isSerialNumberRequired,
//        int lowStackCount, int productStatus

//        for (CartEntity cartRecord : getCartRecord(transactionId)) {
//            cartList.add(new ProductsModel(
//                    cartRecord.getName(),
//                    cartRecord.getPrice(),
//                    cartRecord.getVat(),
//                    cartRecord.isAvailable(),
//                    new String[]{""},
//                    cartRecord.isVattable(),
//                    cartRecord.getShortName(),
//                    new ArrayList<ProductsModel>(),
//                    false,
//                    cartRecord.isSerialNumberRequired(),
//                    cartRecord.getLowStackCount(),
//                    cartRecord.getProductStatus(),
//                    cartRecord.getProductId()
//            ));
//        }

        return cartList;
    }

    @Override
    protected void onPostExecute(List<ProductsModel> productsModels) {
        super.onPostExecute(productsModels);
        this.retrieveCartItemContract.cartItemRetrieved(productsModels);
    }

    private List<CartEntity> getCartRecord(String transactionId) {
        return CartEntity.
                findWithQuery(
                        CartEntity.class,
                        SqlQueries.GET_CART_ITEMS,
                        transactionId
                );
    }
}
