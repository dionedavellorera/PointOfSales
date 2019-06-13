package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class CheckPermissionRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CheckPermissionRequest(String username, String password, String actionId) {
        this.mapValue = new HashMap<>();
        mapValue.put("email", username);
        mapValue.put("password", password);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("access_id", actionId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
