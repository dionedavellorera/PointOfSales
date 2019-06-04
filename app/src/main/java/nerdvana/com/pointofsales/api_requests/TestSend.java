package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class TestSend extends BaseRequest {

    private Map<String, String> mapValue;

    public TestSend(String testData) {
        mapValue = new HashMap<>();
        mapValue.put("TESTDATA", testData);


    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }


}
