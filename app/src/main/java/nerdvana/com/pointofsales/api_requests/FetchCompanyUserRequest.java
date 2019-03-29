package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchCompanyUserRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public FetchCompanyUserRequest() {
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
