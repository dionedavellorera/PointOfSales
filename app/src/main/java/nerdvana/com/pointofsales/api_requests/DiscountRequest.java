package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.api_requests.BaseRequest;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class DiscountRequest extends BaseRequest {

    private Map<String, String> mapValue;

    public DiscountRequest(String post, String remarks, String isPercentage, String value, String discountTypeId) {
        mapValue = new HashMap<>();
        mapValue.put("post", post);
        mapValue.put("remarks", remarks);
        mapValue.put("is_percentage", isPercentage);
        mapValue.put("value", value);
        mapValue.put("discount_type_id", discountTypeId);

        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("branch_code", branchCode);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


}
