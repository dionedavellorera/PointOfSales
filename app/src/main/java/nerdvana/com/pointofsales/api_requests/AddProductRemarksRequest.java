package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class AddProductRemarksRequest extends BaseRequest {

    private Map<String, String> mapValue;

    public AddProductRemarksRequest(String post_trans_id, String remarks) {
        mapValue = new HashMap<>();
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("post_trans_id", post_trans_id);
        mapValue.put("remarks", remarks);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }



}
