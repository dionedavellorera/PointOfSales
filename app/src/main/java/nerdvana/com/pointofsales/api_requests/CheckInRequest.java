package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class CheckInRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CheckInRequest(String roomId, String roomRatePriceId, String remarks) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("user_id", userId);
        mapValue.put("room_rate_price_id", roomRatePriceId);
        mapValue.put("tax", tax);
        mapValue.put("remarks", remarks);
        mapValue.put("machine_number", machineNumber);

        mapValue.put("pos_id", machineNumber);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("branch_code", branchCode);
        mapValue.put("branch_id", branchId);
        mapValue.put("post_trans_id", "0");
        mapValue.put("platform", "1");
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


    @Override
    public String toString() {
        return "CheckInRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
