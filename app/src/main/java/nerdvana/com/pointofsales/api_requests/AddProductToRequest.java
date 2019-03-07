package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class AddProductToRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public AddProductToRequest(ArrayList<AddRateProductModel> addRateProductList, String roomId,
                               String roomAreaId, String controlNo,
                               ArrayList<VoidProductModel> voidList) {
        mapValue = new HashMap<>();
        mapValue.put("room_area_id", roomAreaId);
        mapValue.put("control_no", controlNo);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("void", GsonHelper.getGson().toJson(voidList));
        mapValue.put("emp_id", "762");
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "AddProductToRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
