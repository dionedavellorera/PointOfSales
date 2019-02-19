package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchPaymentRequest {
    private Map<String, String> mapValue;

    public FetchPaymentRequest() {
        mapValue = new HashMap<>();
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
