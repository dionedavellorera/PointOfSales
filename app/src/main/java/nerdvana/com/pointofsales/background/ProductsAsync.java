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
        String[]images = {"https://www.dike.lib.ia.us/images/sample-1.jpg/image"};

        for (int i = 0; i < 20; i++) {
//            productsModelList.add(new ProductsModel("Product Main" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
            if (i == 0) {
                List<ProductsModel> productsList = new ArrayList<>();
                List<ProductsModel> thirdLevelProductList = new ArrayList<>();
                thirdLevelProductList.add(new ProductsModel("Third Sub 1" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                thirdLevelProductList.add(new ProductsModel("Third Sub 2" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                thirdLevelProductList.add(new ProductsModel("Third Sub 3" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                thirdLevelProductList.add(new ProductsModel("Third Sub 4" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));


                productsList.add(new ProductsModel("Sub Category with third level" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false));
                productsList.add(new ProductsModel("Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "shortname" + i, productsList, false));
            } else if (i == 1) {
                List<ProductsModel> productsList = new ArrayList<>();

                List<ProductsModel> thirdLevelProductList = new ArrayList<>();
                thirdLevelProductList.add(new ProductsModel("Third Sub Productiop" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                thirdLevelProductList.add(new ProductsModel("Third Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                thirdLevelProductList.add(new ProductsModel("Third Sub Productiop" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                thirdLevelProductList.add(new ProductsModel("Third Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));


                productsList.add(new ProductsModel("Sub Category with third level" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false));


                productsList.add(new ProductsModel("Sub Sub Productcxz" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                productsList.add(new ProductsModel("Sub Sub Productqwe" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                productsList.add(new ProductsModel("Sub Sub Producthjk" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "shortname" + i, productsList, false));
            } else {
                productsModelList.add(new ProductsModel("Product Main" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
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
