package nerdvana.com.pointofsales.interfaces;

import nerdvana.com.pointofsales.api_responses.FetchDepartmentsResponse;
import nerdvana.com.pointofsales.model.ProductFilterModel;

public interface ProductFilterContract {
    void filterSelected(ProductFilterModel result);
}
