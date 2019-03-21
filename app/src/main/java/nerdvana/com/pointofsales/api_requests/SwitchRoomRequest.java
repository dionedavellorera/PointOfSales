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

    public SwitchRoomRequest(ArrayList<AddRateProductModel> addRateProductList,
                             String roomId,
                             ArrayList<VoidProductModel> voidList,
                             String remarks,
                             String controlNumber) {
        mapValue = new HashMap<>();
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("room_id", roomId);
        mapValue.put("control_no", controlNumber);
        mapValue.put("branch_code", branchCode);


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
