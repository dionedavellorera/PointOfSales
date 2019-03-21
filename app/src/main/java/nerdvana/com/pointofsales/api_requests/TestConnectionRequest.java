package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class TestConnectionRequest extends BaseRequest{
    private Map<String, String> mapValue;
    public TestConnectionRequest() {
        mapValue = new HashMap<>();
        mapValue.put("machine_number", machineNumber);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
