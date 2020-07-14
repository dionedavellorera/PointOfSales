package nerdvana.com.pointofsales.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.IUsers;
import nerdvana.com.pointofsales.PosClient;
import nerdvana.com.pointofsales.R;
import nerdvana.com.pointofsales.SharedPreferenceManager;
import nerdvana.com.pointofsales.adapters.ProductFilterAdapter;
import nerdvana.com.pointofsales.api_requests.TestConnectionRequest;
import nerdvana.com.pointofsales.api_responses.FetchDepartmentsResponse;
import nerdvana.com.pointofsales.api_responses.TestConnectionResponse;
import nerdvana.com.pointofsales.interfaces.ProductFilterContract;
import nerdvana.com.pointofsales.model.ProductFilterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ProductFilterDialog extends BaseDialog  {

    Call<FetchDepartmentsResponse> request;
    List<String> filtersSelected;
    List<ProductFilterModel> productFilterModelList;
    ProductFilterAdapter productFilterAdapter;
    private ProductFilterContract productFilterContract;
    private RecyclerView rvProductFilter;
    public ProductFilterDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogLayout(R.layout.dialog_product_filter, "PRODUCT FILTER");
        filtersSelected = GsonHelper.getGson().fromJson(SharedPreferenceManager.getString(null, ApplicationConstants.PRODUCT_FILTERS), List.class);
        productFilterModelList = new ArrayList<>();
        if (filtersSelected == null) {
            Log.d("WETWEt", " FILTER IS NULL");
            filtersSelected = new ArrayList<>();
        } else {
            for (String str : filtersSelected){
                Log.d("WETWEt", str);
            }

        }


        rvProductFilter = findViewById(R.id.rvProductFilter);
        productFilterContract = new ProductFilterContract() {
            @Override
            public void filterSelected(ProductFilterModel result) {
                if (filtersSelected.contains(String.valueOf(result.getCore_id()))) {
                    Log.d("WETWEt", "REMOVED");
                    filtersSelected.remove(String.valueOf(result.getCore_id()));
                } else {
                    Log.d("WETWEt", "aDDED");
                    filtersSelected.add(String.valueOf(result.getCore_id()));
                }

                SharedPreferenceManager.saveString(getContext(), GsonHelper.getGson().toJson(filtersSelected), ApplicationConstants.PRODUCT_FILTERS);
            }
        };
        requestProductDepartmentData();
    }

    private void requestProductDepartmentData() {
        TestConnectionRequest testConnectionRequest = new TestConnectionRequest();
        IUsers iUsers = PosClient.mRestAdapter.create(IUsers.class);
        if (request == null) {
            request = iUsers.fetchDepartments(
                    testConnectionRequest.getMapValue());
            request.enqueue(new Callback<FetchDepartmentsResponse>() {
                @Override
                public void onResponse(Call<FetchDepartmentsResponse> call, Response<FetchDepartmentsResponse> response) {
                    request = null;
                    for (FetchDepartmentsResponse.Result r : response.body().getResult()) {
                        boolean isChecked = false;

                        if (filtersSelected.size() > 0) {
                            if (filtersSelected.contains(String.valueOf(r.getCoreId()))) {
                                isChecked = true;
                            }
                        }
                        productFilterModelList.add(new ProductFilterModel(r.getId(), r.getCoreId(), r.getDepartment(), isChecked));

                    }
                    setDepartmentsData(productFilterModelList);
                }

                @Override
                public void onFailure(Call<FetchDepartmentsResponse> call, Throwable t) {
                    request = null;
                }
            });
        }

    }

    private void setDepartmentsData(List<ProductFilterModel> results) {



        productFilterAdapter = new ProductFilterAdapter(results, productFilterContract, getContext());
        rvProductFilter.setAdapter(productFilterAdapter);
        rvProductFilter.setLayoutManager(new GridLayoutManager(getContext(), 3));
        productFilterAdapter.notifyDataSetChanged();

    }

    @Override
    public void dismiss() {
        super.dismiss();
        request = null;
        filtersClosed();
    }

    @Override
    public void cancel() {
        super.cancel();
        request = null;
        filtersClosed();
    }

    public abstract void filtersClosed();


}
