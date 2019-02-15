package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchRoomPendingRequest {
    private Map<String, String> mapValue;
    public FetchRoomPendingRequest(String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
