package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.api_requests.BaseRequest;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class DiscountRequest extends BaseRequest {

    private Map<String, String> mapValue;


    public DiscountRequest(String post, String remarks,
                           String isPercentage, String value,
                           String empId, String discountReasonId,
                           String controlNumber, String roomId) {
        mapValue = new HashMap<>();

        mapValue.put("post", post);
        mapValue.put("discount_type", "MANUAL DISCOUNT");
        mapValue.put("discount_id", "0");
        mapValue.put("discounted_by", "");
        mapValue.put("discount_reason_id", discountReasonId);
        mapValue.put("value", value);
        mapValue.put("remarks", remarks); //optional empty string
        mapValue.put("is_percentage", isPercentage);
        mapValue.put("emp_id", empId);
        mapValue.put("card_no", "");
        mapValue.put("name", "");
        mapValue.put("address", "");
        mapValue.put("control_no", controlNumber);
        mapValue.put("room_id", roomId);

        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


//    public DiscountRequest(String post, String remarks,
//                           String isPercentage, String value,
//                           String discountTypeId, String discountReasonId) {
//        mapValue = new HashMap<>();
//        mapValue.put("post", post);
//        mapValue.put("remarks", remarks); //required
//        mapValue.put("is_percentage", isPercentage);
//        mapValue.put("value", value);
//        mapValue.put("discount_id", "0");
//        mapValue.put("discount_type", "MANUAL");
//        mapValue.put("discount_reason_id", discountReasonId);
//
//        mapValue.put("user_id", userId);
//        mapValue.put("pos_id", machineNumber);
//        mapValue.put("branch_id", branchId);
//        mapValue.put("currency_id", currencyId);
//        mapValue.put("currency_value", currencyValue);
//        mapValue.put("branch_code", branchCode);
//        mapValue.put("tax", tax);
//
//
//    }
//
//    public Map<String, String> getMapValue() {
//        return mapValue;
//    }


    @Override
    public String toString() {
        return "DiscountRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
