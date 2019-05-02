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
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("room_id", roomId);
        mapValue.put("is_adv", isAdv);
        mapValue.put("control_no", controlNumber);


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
