package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class VoidPaymentRequest extends BaseRequest{

    private Map<String, String> mapValue;

    public VoidPaymentRequest(String controlNumber, String paymentId, String empId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("control_no", controlNumber);
        mapValue.put("payment_id", paymentId);
        mapValue.put("emp_id", empId);



    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


}
