package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class SwitchRoomRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public SwitchRoomRequest(String roomId, String roomRatePriceId,
                             String remarks, String roomTransferId, String empId,
                             ArrayList<VoidProductModel> voidModel) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("room_rate_price_id", roomRatePriceId);
        mapValue.put("remarks", remarks);
        mapValue.put("room_transfer_id", roomTransferId);
        mapValue.put("emp_id", empId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("void", GsonHelper.getGson().toJson(voidModel));
        mapValue.put("tax", tax);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("user_id", userId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "SwitchRoomRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
