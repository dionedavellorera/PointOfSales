package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchCreditCardRequest {
    private Map<String, String> mapValue;

    public FetchCreditCardRequest() {
        mapValue = new HashMap<>();

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
