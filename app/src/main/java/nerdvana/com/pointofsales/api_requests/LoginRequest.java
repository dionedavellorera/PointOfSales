package nerdvana.com.pointofsales.api_requests;

import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class LoginRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public LoginRequest(String username, String password, String accessId) {
        this.mapValue = new HashMap<>();
        mapValue.put("email", username);
        mapValue.put("password", password);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("access_id", accessId);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
