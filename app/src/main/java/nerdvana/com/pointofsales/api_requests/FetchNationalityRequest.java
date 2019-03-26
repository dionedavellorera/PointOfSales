package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchNationalityRequest {
    private Map<String, String> mapValue;

    public FetchNationalityRequest() {
        mapValue = new HashMap<>();

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
