package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;

public class PrintSoaRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public PrintSoaRequest(String roomId, String controlNumber) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("control_no", controlNumber);

        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);

        mapValue.put("branch_code", branchCode);
        mapValue.put("branch_id", branchId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
