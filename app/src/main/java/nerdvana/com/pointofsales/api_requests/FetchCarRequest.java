package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchCarRequest {
    private Map<String, String> mapValue;
    public FetchCarRequest() {
        mapValue = new HashMap<>();
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
