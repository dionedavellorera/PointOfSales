package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class CheckInRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CheckInRequest(String roomId, String roomRatePriceId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("room_rate_price_id", roomRatePriceId);
        mapValue.put("tax", tax);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
