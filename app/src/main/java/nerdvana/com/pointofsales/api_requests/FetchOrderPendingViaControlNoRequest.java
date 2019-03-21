package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;

public class FetchOrderPendingViaControlNoRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public FetchOrderPendingViaControlNoRequest(String controlNo) {
        mapValue = new HashMap<>();
        mapValue.put("control_no", controlNo);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_code", branchCode);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
