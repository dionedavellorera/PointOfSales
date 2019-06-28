package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class ChangeRoomStatusRequest extends BaseRequest {

    private Map<String, String> mapValue;

    public ChangeRoomStatusRequest(String newValue,
                                 String roomId, String empId,
                                 String remarks) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);

        mapValue.put("room_status_id", newValue);
        mapValue.put("room_id", roomId);
        mapValue.put("emp_id", empId);
        mapValue.put("remarks", remarks);
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
