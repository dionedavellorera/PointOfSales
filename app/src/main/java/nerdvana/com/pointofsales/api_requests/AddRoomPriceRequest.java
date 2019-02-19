package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class AddRoomPriceRequest {
    private Map<String, String> mapValue;

    public AddRoomPriceRequest(String productId, String roomRateId, String quantity) {
        mapValue = new HashMap<>();
        mapValue.put("product_id", productId);
        mapValue.put("room_rate_id", roomRateId);
        mapValue.put("quantity", quantity);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
