package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class AddRoomPriceRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public AddRoomPriceRequest(String productId, String roomRateId, String quantity, String tax, String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("product_id", productId);
        mapValue.put("room_id", roomId);
        mapValue.put("room_rate_price_id", roomRateId);
        mapValue.put("qty", quantity);
        mapValue.put("tax", tax);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


}
