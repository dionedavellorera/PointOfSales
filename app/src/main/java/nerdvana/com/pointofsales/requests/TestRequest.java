package nerdvana.com.pointofsales.requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.api_requests.BaseRequest;

public class TestRequest extends BaseRequest {
    private Map<String, String> mapValue;
    public TestRequest(String userId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("machine_number", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
