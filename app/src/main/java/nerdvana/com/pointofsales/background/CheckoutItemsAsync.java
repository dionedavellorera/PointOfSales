package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.interfaces.CheckoutItemsContract;
import nerdvana.com.pointofsales.model.ProductsModel;

public class CheckoutItemsAsync extends AsyncTask<ProductsModel, Void, ProductsModel> {
    private CheckoutItemsContract checkoutItemsContract;
    private ProductsModel selectedProduct;
    private List<ProductsModel> productList;
    public CheckoutItemsAsync(CheckoutItemsContract checkoutItemsContract, List<ProductsModel> productsList ,ProductsModel selectedProduct) {
        this.checkoutItemsContract = checkoutItemsContract;
        this.selectedProduct = selectedProduct;
        this.productList = productsList;
    }

    @Override
    protected ProductsModel doInBackground(ProductsModel... productsModels) {

        productList.add(selectedProduct);
        return selectedProduct;
    }

    @Override
    protected void onPostExecute(ProductsModel productsModel) {
        this.checkoutItemsContract.itemAdded(productsModel);
        super.onPostExecute(productsModel);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
