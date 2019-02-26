package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class AddPaymentRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public AddPaymentRequest(List<PostedPaymentsModel> addRateProductList, String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("room_id", roomId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
