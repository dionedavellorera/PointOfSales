package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;

public class AddProductToRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public AddProductToRequest(ArrayList<AddRateProductModel> addRateProductList, String roomId,
                               String roomAreaId, String controlNo) {
        mapValue = new HashMap<>();
        mapValue.put("room_area_id", roomAreaId);
        mapValue.put("control_no", controlNo);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
