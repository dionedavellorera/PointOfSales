package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class VerifyMachineRequest extends BaseRequest{

    private Map<String, String> mapValue;
    public VerifyMachineRequest(String productKey, String androidId,
                                String serial, String model,
                                String manufacturer, String board) {
        mapValue = new HashMap<>();
        mapValue.put("product_key", productKey);
        mapValue.put("android_id", androidId);
        mapValue.put("serial", serial);
        mapValue.put("model", model);
        mapValue.put("manufacturer", manufacturer);
        mapValue.put("board", board);
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
