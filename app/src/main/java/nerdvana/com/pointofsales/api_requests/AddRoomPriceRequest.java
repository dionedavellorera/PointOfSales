package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;

public class AddRoomPriceRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public AddRoomPriceRequest(ArrayList<AddRateProductModel> addRateProductList, String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
