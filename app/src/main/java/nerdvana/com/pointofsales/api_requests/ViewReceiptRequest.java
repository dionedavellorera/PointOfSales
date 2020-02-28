package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class ViewReceiptRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public ViewReceiptRequest(String roomId, String receiptNumber) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("receipt_no", receiptNumber);

        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
