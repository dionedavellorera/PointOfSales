package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class FocTransactionRequest extends BaseRequest {

    private Map<String, String> mapValue;

    public FocTransactionRequest(String roomId, String controlNumber, String empId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("room_id", roomId);
        mapValue.put("control_no", controlNumber);
        mapValue.put("emp_id", empId);
        mapValue.put("branch_code", branchCode);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


}
