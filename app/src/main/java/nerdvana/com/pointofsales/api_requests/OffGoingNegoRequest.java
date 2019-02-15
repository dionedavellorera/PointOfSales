package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class OffGoingNegoRequest {
    private Map<String, String> mapValue;
    public OffGoingNegoRequest(String roomId, String userId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("user_id", userId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
