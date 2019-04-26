package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class SaveGuestInfoRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public SaveGuestInfoRequest(String emp, String guestName,
                                String guestAddress, String guestTin) {
        mapValue = new HashMap<>();

        mapValue.put("user_id", emp);
        mapValue.put("name", guestName);
        mapValue.put("address", guestAddress);
        mapValue.put("tin", guestTin);

        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
