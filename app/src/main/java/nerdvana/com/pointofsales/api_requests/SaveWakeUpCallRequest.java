package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class SaveWakeUpCallRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public SaveWakeUpCallRequest(String controlNumber) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("emp_id", "");
        mapValue.put("pos_id", machineNumber);
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
        mapValue.put("control_no", controlNumber);

        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

}
