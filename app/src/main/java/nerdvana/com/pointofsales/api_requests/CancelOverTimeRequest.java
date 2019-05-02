package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class CancelOverTimeRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CancelOverTimeRequest(String roomId, String controlNumber,
                                 String oldOtHour, String newOtHour,
                                 String empId) {
        mapValue = new HashMap<>();

        mapValue.put("room_id", roomId);
        mapValue.put("control_no", controlNumber);
        mapValue.put("old_ot_hour", oldOtHour);
        mapValue.put("new_ot_hour", newOtHour);

        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);

        mapValue.put("emp_id", empId);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
