package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;

public class FetchRoomAreaRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public FetchRoomAreaRequest() {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
