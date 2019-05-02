package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class UpdateCheckInTimeRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public UpdateCheckInTimeRequest(String roomId, String checkInTime) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("room_id", roomId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("check_in_time", checkInTime);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
