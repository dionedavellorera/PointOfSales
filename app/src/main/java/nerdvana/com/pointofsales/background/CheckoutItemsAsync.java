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
//        String name, double price,
//        double vat, boolean isAvailable,
//        String[] imageUrls, boolean isVattable,
//        String shortName, List<ProductsModel> productsList

        productList.add(new ProductsModel(
                selectedProduct.getName(), selectedProduct.getPrice(),
                selectedProduct.getVat(), selectedProduct.isAvailable(),
                selectedProduct.getImageUrls(), selectedProduct.isVattable(),
                selectedProduct.getShortName(), selectedProduct.getProductsList(),
                selectedProduct.isSelected(), selectedProduct.isSerialNumberRequired(),
                selectedProduct.getLowStackCount(), selectedProduct.getProductStatus()
        ));
        return selectedProduct;
    }

    @Override
    protected void onPostExecute(ProductsModel productsModel) {

        super.onPostExecute(productsModel);

        this.checkoutItemsContract.itemAdded(productsModel);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
