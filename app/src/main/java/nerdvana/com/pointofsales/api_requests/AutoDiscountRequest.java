package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class AutoDiscountRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public AutoDiscountRequest(String discountType, String discountId,
                               String discountedBy, String empId,
                               String cardNumber, String name,
                               String address, String controlNumber,
                               String roomId) {
        mapValue = new HashMap<>();

        mapValue.put("post", "[]");
        mapValue.put("discount_type", discountType);
        mapValue.put("discount_id", discountId);
        mapValue.put("discounted_by", discountedBy);
        mapValue.put("discount_reason_id", "");
        mapValue.put("value", "");
        mapValue.put("remarks", ""); //optional empty string
        mapValue.put("is_percentage", "");
        mapValue.put("emp_id", empId);
        mapValue.put("card_no", cardNumber);
        mapValue.put("name", name);
        mapValue.put("address", address);
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


    @Override
    public String toString() {
        return "AutoDiscountRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
