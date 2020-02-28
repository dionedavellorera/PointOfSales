package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class FetchGuestTypeRequest extends BaseRequest {
    private Map<String, String> mapValue;
    public FetchGuestTypeRequest() {
        mapValue = new HashMap<>();
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_code", branchCode);
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
