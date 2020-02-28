package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class EditGuestCountRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public EditGuestCountRequest(String oldValue, String newValue,
                                 String roomId, String empId,
                                 String remarks) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);

        mapValue.put("old_value", oldValue);
        mapValue.put("person", newValue);
        mapValue.put("room_id", roomId);
        mapValue.put("emp_id", empId);
        mapValue.put("remarks", remarks);
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "AddPaymentRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
