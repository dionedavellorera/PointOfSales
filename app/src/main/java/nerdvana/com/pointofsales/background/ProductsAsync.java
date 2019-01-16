package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ProductsAsync extends AsyncTask<ProductsModel, Void, List<ProductsModel>> {
    private AsyncContract asyncContract;
    public ProductsAsync(AsyncContract asyncContract) {
        this.asyncContract = asyncContract;
    }

    @Override
    protected List<ProductsModel> doInBackground(ProductsModel... productsModels) {
        List<ProductsModel> productsModelList = new ArrayList<>();
        String[]images = {"https://sc01.alicdn.com/kf/HTB1rcAOFDJYBeNjy1zeq6yhzVXaA/Find-A-Very-Small-Size-Sample-3G.jpg"};

        for (int i = 0; i < 20; i++) {

            if (i == 0) {
                List<ProductsModel> productsList = new ArrayList<>();
                productsList.add(new ProductsModel("Sub Productiop" + i, 100.00, 12.00, true, images, true, "prod1", new ArrayList<ProductsModel>()));
                productsList.add(new ProductsModel("Sub Productrty" + i, 100.00, 12.00, true, images, true, "prod1", new ArrayList<ProductsModel>()));
                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "prod1", productsList));
            } else if (i == 1) {
                List<ProductsModel> productsList = new ArrayList<>();
                productsList.add(new ProductsModel("Sub Sub Productcxz" + i, 100.00, 12.00, true, images, true, "prod1", new ArrayList<ProductsModel>()));
                productsList.add(new ProductsModel("Sub Sub Productqwe" + i, 100.00, 12.00, true, images, true, "prod1", new ArrayList<ProductsModel>()));
                productsList.add(new ProductsModel("Sub Sub Producthjk" + i, 100.00, 12.00, true, images, true, "prod1", new ArrayList<ProductsModel>()));
                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "prod1", productsList));
            } else {
                productsModelList.add(new ProductsModel("Product Main" + i, 100.00, 12.00, true, images, true, "prod1", new ArrayList<ProductsModel>()));
            }


        }
        return productsModelList;
    }

    @Override
    protected void onPostExecute(List<ProductsModel> productsModels) {
        this.asyncContract.doneLoading(productsModels, "products");
        super.onPostExecute(productsModels);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
