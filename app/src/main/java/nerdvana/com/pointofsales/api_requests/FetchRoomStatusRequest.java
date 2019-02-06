package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchRoomStatusRequest {
    private Map<String, String> mapValue;
    public FetchRoomStatusRequest() {
        mapValue = new HashMap<>();
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
