package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;
import retrofit2.http.FieldMap;

public class FetchXReadingViaIdRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public FetchXReadingViaIdRequest(String xReadId) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
//        mapValue.put("pos_id", "1");
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("xread_id", xReadId);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
