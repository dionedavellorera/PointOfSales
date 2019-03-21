package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchRoomStatusRequest extends BaseRequest {
    private Map<String, String> mapValue;
    public FetchRoomStatusRequest() {
        mapValue = new HashMap<>();
        mapValue.put("machine_number", machineNumber);
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
