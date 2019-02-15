package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class CheckInRequest {
    private Map<String, String> mapValue;

    public CheckInRequest(String userId, String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("user_id", userId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
