package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.ProductConstants;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.api_responses.FetchProductsResponse;
import nerdvana.com.pointofsales.interfaces.AsyncContract;
import nerdvana.com.pointofsales.model.ButtonsModel;
import nerdvana.com.pointofsales.model.ProductsModel;

public class ProductsAsync extends AsyncTask<ProductsModel, Void, List<ProductsModel>> {
    private AsyncContract asyncContract;
    private FetchProductsResponse fetchProductsResponse;
    private Context context;
    public ProductsAsync(AsyncContract asyncContract, FetchProductsResponse fetchProductsResponse,
                         Context context) {
        this.asyncContract = asyncContract;
        this.fetchProductsResponse = fetchProductsResponse;
        this.context = context;
    }

    @Override
    protected List<ProductsModel> doInBackground(ProductsModel... productsModels) {


        List<ProductsModel> productsModelList = new ArrayList<>();


        for (FetchProductsResponse.Result r : fetchProductsResponse.getResult()) {
            String[]images = {};
            if (r.getImageFile() != null) {
                images = new String[]{"http://192.168.1.90/pos/uploads/company/product/" + r.getImageFile()};
            }
//            else {
//                images = new String[]{"http://autopilot.vcourt.biz/acctmanagementsys/images/vc_logov2.png"};
//            }
            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime companyUpdatedAt = new DateTime(df.parseDateTime(r.getUpdatedAt()));
            Double amount = r.getAmount();
            if (r.getBranchPrice() != null) {
                DateTime branchUpdatedAt = new DateTime(df.parseDateTime(r.getBranchPrice().getUpdatedAt()));
                if (branchUpdatedAt.isAfter(companyUpdatedAt)) {
                    amount = r.getBranchPrice().getAmount();
                }
            }

            amount = ((amount * (r.getMarkUp() + 1))) * Double.valueOf(SharedPreferenceManager.getString(context, ApplicationConstants.DEFAULT_CURRENCY_VALUE));
            String branchDepartment = "";
            if (r.getBranchDepartments().size() > 0) {
                if (r.getBranchDepartments().get(0).getBranchDepartment() != null) {
                    branchDepartment = r.getBranchDepartments().get(0).getBranchDepartment().getDepartment();
                }
            }

            productsModelList.add(new ProductsModel(
                    r.getProduct(),
                    amount,
                    0.00,
                    r.getIsAvailable() == 1 ? true : false,
                    images,
                    true,
                    r.getProductInitial(),
                    new ArrayList<ProductsModel>(),
                    false,
                    false,
                    0,
                    ProductConstants.PENDING,
                    r.getCoreId(),
                    r.getMarkUp(),
                    0,
                    branchDepartment,
                    amount,
                    r.getBranchAlaCartList(),
                    r.getBranchGroupList()));
        }

//        for (int i = 0; i < 20; i++) {
////            productsModelList.add(new ProductsModel("Product Main" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false));
//            if (i == 0) {
//                List<ProductsModel> productsList = new ArrayList<>();
//
//                List<ProductsModel> fourthProductList = new ArrayList<>();
//                fourthProductList.add(new ProductsModel("Fourth Sub 1" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 1));
//                fourthProductList.add(new ProductsModel("Fourth Sub 2" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 2));
//                fourthProductList.add(new ProductsModel("Fourth Sub 3" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 3));
//                fourthProductList.add(new ProductsModel("Fourth Sub 4" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 4));
//
//
//                List<ProductsModel> thirdLevelProductList = new ArrayList<>();
//                thirdLevelProductList.add(new ProductsModel("With Fourth" + i, 100.00, 12.00, true, images, true, "shortname" + i, fourthProductList, false, false, 0, ProductConstants.PENDING, (long) 5));
//                thirdLevelProductList.add(new ProductsModel("Third Sub 2" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 6));
//                thirdLevelProductList.add(new ProductsModel("Third Sub 3" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 7));
//                thirdLevelProductList.add(new ProductsModel("Third Sub 4" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 8));
//
//
//
//
//
//                productsList.add(new ProductsModel("Sub Category with third level" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false, false, 0, ProductConstants.PENDING, (long) 9));
//                productsList.add(new ProductsModel("Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 10));
//                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false, false, 0, ProductConstants.PENDING, (long) 11));
//            } else if (i == 1) {
//                List<ProductsModel> productsList = new ArrayList<>();
//
//                List<ProductsModel> thirdLevelProductList = new ArrayList<>();
//                thirdLevelProductList.add(new ProductsModel("Third Sub Productiop" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 12));
//                thirdLevelProductList.add(new ProductsModel("Third Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 13));
//                thirdLevelProductList.add(new ProductsModel("Third Sub Productiop" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 14));
//                thirdLevelProductList.add(new ProductsModel("Third Sub Productrty" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 15));
//
//
//                productsList.add(new ProductsModel("Sub Category with third level" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 16));
//
//
//                productsList.add(new ProductsModel("Sub Sub Productcxz" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 17));
//                productsList.add(new ProductsModel("Sub Sub Productqwe" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 18));
//                productsList.add(new ProductsModel("Sub Sub Producthjk" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) 19));
//                productsModelList.add(new ProductsModel("Category" + i, 100.00, 12.00, true, images, true, "shortname" + i, thirdLevelProductList, false, false, 0, ProductConstants.PENDING, (long) 20));
//            } else {
//                productsModelList.add(new ProductsModel("Product Main" + i, 100.00, 12.00, true, images, true, "shortname" + i, new ArrayList<ProductsModel>(), false, false, 0, ProductConstants.PENDING, (long) i));
//            }
//
//
//        }
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
