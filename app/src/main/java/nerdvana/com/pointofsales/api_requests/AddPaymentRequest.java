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

    public AddPaymentRequest(List<PostedPaymentsModel> addRateProductList,
                             String roomId, String isAdv,
                             String controlNumber) {
        mapValue = new HashMap<>();
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("room_id", roomId);
        mapValue.put("is_adv", isAdv);
        mapValue.put("control_no", controlNumber);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
