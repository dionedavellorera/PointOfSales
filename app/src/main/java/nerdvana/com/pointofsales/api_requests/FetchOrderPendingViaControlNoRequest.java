package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;

public class FetchOrderPendingViaControlNoRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public FetchOrderPendingViaControlNoRequest(String controlNo) {
        mapValue = new HashMap<>();
        mapValue.put("control_no", controlNo);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}