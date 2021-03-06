package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class ViewReceiptViaDateRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public ViewReceiptViaDateRequest(String roomId, String receiptNumber,
                                     String sDate, String eDate) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("receipt_no", receiptNumber);

        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
//        mapValue.put("pos_id", "1");
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);

        mapValue.put("sDate", sDate);
        mapValue.put("eDate", eDate);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
