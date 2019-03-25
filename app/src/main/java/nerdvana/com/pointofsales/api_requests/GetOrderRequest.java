package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class GetOrderRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public GetOrderRequest(String customerName, String roomAreaId) {
        mapValue = new HashMap<>();
        mapValue.put("customer", customerName);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("user_id", userId);
        mapValue.put("room_area_id", roomAreaId);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);

        mapValue.put("branch_code", branchCode);
        mapValue.put("branch_id", branchId);

        mapValue.put("tax", tax);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
