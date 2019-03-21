package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class CheckGcRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CheckGcRequest(String gcCode, String qty) {
        mapValue = new HashMap<>();
        mapValue.put("gc_code", gcCode);
        mapValue.put("qty", qty);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

}
