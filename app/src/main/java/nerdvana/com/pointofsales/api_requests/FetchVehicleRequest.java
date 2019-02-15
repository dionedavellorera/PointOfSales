package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchVehicleRequest {
    private Map<String, String> mapValue;
    public FetchVehicleRequest() {
        mapValue = new HashMap<>();
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
