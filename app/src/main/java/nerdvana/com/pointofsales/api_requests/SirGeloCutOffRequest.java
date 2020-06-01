package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class SirGeloCutOffRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public SirGeloCutOffRequest(String companyCode, String shiftNo) {
        mapValue = new HashMap<>();

        mapValue.put("company_code", companyCode.toLowerCase());
        mapValue.put("branch_code", branchCode.toLowerCase());
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("shift_no", shiftNo);
        mapValue.put("type", "motel");


        mapValue.put("branch_id", branchId);
        mapValue.put("tax", tax);
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);


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
