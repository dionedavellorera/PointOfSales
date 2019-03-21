package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchVehicleRequest extends BaseRequest {
    private Map<String, String> mapValue;
    public FetchVehicleRequest() {
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
