package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class CheckOutRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public CheckOutRequest(String roomId, String controlNumber, String roomBoyId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("room_id", roomId);
        mapValue.put("control_no", controlNumber);
        mapValue.put("emp_id", roomBoyId);
        mapValue.put("branch_code", branchCode);
     }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "CheckOutRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
