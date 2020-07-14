package nerdvana.com.pointofsales.background;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
            boolean shouldDisplay = false;
            if (r.getImageFile() != null) {
                images = new String[]{SharedPreferenceManager.getString(null, ApplicationConstants.HOST) + "/uploads/company/product/" + r.getImageFile()};

            }
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
            String branchDepartmentId = "";
            if (r.getBranchDepartments().size() > 0) {
                if (r.getBranchDepartments().get(0).getBranchDepartment() != null) {
//                    if (r.getBranchDepartments().get(0).getDepartmentId() == 3 ||
//                            r.getBranchDepartments().get(0).getDepartmentId() == 4 ||
//                            r.getBranchDepartments().get(0).getDepartmentId() == 5 ||
//                            r.getBranchDepartments().get(0).getDepartmentId() == 6) {
//
//                    }
                    shouldDisplay = true;
                    branchDepartment = r.getBranchDepartments().get(0).getBranchDepartment().getDepartment();
                    branchDepartmentId = String.valueOf(r.getBranchDepartments().get(0).getBranchDepartment().getCoreId());
                }
            }

            if (shouldDisplay) {

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
                        r.getBranchGroupList(),
                        "",
                        r.getProductBarcode() != null ? r.getProductBarcode().toString() : "",
                        branchDepartmentId));


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
