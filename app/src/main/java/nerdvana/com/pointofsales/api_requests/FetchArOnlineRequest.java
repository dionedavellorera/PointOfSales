package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class FetchArOnlineRequest {
    private Map<String, String> mapValue;

    public FetchArOnlineRequest() {
        mapValue = new HashMap<>();

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
