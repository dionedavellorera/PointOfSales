package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchRoomStatusRequest extends BaseRequest {
    private Map<String, String> mapValue;
    public FetchRoomStatusRequest() {
        mapValue = new HashMap<>();
        mapValue.put("machine_number", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}