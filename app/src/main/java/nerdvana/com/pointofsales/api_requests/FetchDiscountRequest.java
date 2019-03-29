package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class FetchDiscountRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public FetchDiscountRequest() {
        mapValue = new HashMap<>();


        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("branch_code", branchCode);

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
