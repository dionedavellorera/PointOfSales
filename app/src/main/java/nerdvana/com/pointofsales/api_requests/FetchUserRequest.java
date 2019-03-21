package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class FetchUserRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public FetchUserRequest() {
        mapValue = new HashMap<>();

        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);

        mapValue.put("branch_code", branchCode);
        mapValue.put("branch_id", branchId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
