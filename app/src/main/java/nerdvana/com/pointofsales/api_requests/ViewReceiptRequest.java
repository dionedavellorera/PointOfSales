package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class ViewReceiptRequest {
    private Map<String, String> mapValue;

    public ViewReceiptRequest(String roomId, String receiptNumber) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("receipt_no", receiptNumber);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}