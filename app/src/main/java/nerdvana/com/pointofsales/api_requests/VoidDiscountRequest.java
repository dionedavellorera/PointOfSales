package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class VoidDiscountRequest extends BaseRequest {

    private Map<String, String> mapValue;

    public VoidDiscountRequest(String controlNumber, String discountId, String empId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("control_no", controlNumber);
        mapValue.put("discount_id", discountId);
        mapValue.put("emp_id", empId);



    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
