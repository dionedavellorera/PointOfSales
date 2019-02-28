package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class FetchVehicleRequest extends BaseRequest {
    private Map<String, String> mapValue;
    public FetchVehicleRequest() {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
