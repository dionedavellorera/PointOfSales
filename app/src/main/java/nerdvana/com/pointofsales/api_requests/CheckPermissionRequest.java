package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class CheckPermissionRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CheckPermissionRequest(String username, String password, String actionId) {
        this.mapValue = new HashMap<>();
        mapValue.put("email", username);
        mapValue.put("password", password);
        mapValue.put("pos_id", machineNumber);
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
        mapValue.put("access_id", actionId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
