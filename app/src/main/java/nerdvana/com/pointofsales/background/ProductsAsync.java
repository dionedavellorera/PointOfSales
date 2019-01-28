package nerdvana.com.pointofsales.background;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nerdvana.com.pointofsales.ProductConstants;
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

                List<ProductsModel> fourthProductList = new ArrayList<>();
                fourthProductList.add(new ProductsModel("Fourth Sub 1" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 1));
                fourthProductList.add(new ProductsModel("Fourth Sub 2" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 2));
                fourthProductList.add(new ProductsModel("Fourth Sub 3" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 3));
                fourthProductList.add(new ProductsModel("Fourth Sub 4" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 4));


                List<ProductsModel> thirdLevelProductList = new ArrayList<>();
                thirdLevelProductList.add(new ProductsModel("With Fourth" + i, 100.00, 12.00, true, images, true, "shortname" + i, fourthProductList, false, false, 0, ProductConstants.PENDING, (long) 5));
                thirdLevelProductList.add(new ProductsModel("Third Sub 2" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 6));
                thirdLevelProductList.add(new ProductsModel("Third Sub 3" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 7));
                thirdLevelProductList.add(new ProductsModel("Third Sub 4" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 8));





                productsList.add(new ProductsModel("Sub Category with third level" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false, false, 0, ProductConstants.PENDING, (long) 9));
                productsList.add(new ProductsModel("Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 10));
                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false, false, 0, ProductConstants.PENDING, (long) 11));
            } else if (i == 1) {
                List<ProductsModel> productsList = new ArrayList<>();

                List<ProductsModel> thirdLevelProductList = new ArrayList<>();
                thirdLevelProductList.add(new ProductsModel("Third Sub Productiop" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 12));
                thirdLevelProductList.add(new ProductsModel("Third Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 13));
                thirdLevelProductList.add(new ProductsModel("Third Sub Productiop" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 14));
                thirdLevelProductList.add(new ProductsModel("Third Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 15));


                productsList.add(new ProductsModel("Sub Category with third level" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 16));


                productsList.add(new ProductsModel("Sub Sub Productcxz" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 17));
                productsList.add(new ProductsModel("Sub Sub Productqwe" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 18));
                productsList.add(new ProductsModel("Sub Sub Producthjk" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 19));
                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false, false, 0, ProductConstants.PENDING, (long) 20));
            } else {
                productsModelList.add(new ProductsModel("Product Main" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) i));
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
