package nerdvana.com.pointofsales.requests;

import java.util.HashMap;
import java.util.Map;

public class TestRequest {
    private Map<String, String> mapValue;
    public TestRequest(String userId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
