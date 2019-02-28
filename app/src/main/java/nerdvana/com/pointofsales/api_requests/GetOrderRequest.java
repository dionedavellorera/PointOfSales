package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class GetOrderRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public GetOrderRequest(String customerName) {
        mapValue = new HashMap<>();
        mapValue.put("customer", customerName);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("user_id", customerName);

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
