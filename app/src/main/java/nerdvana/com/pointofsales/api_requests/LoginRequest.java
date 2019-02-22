package nerdvana.com.pointofsales.api_requests;

import android.util.ArrayMap;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public LoginRequest(String username, String password) {
        this.mapValue = new HashMap<>();
        mapValue.put("email", username);
        mapValue.put("password", password);
        mapValue.put("pos_id", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
