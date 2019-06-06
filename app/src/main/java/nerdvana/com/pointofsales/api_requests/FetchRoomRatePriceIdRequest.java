package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class FetchRoomRatePriceIdRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public FetchRoomRatePriceIdRequest(String priceRateRoomId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("price_rate_room_id", priceRateRoomId);


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
