package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class FetchRoomPendingRequest extends BaseRequest{
    private Map<String, String> mapValue;
    public FetchRoomPendingRequest(String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_code", branchCode);
        mapValue.put("branch_id", branchId);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
