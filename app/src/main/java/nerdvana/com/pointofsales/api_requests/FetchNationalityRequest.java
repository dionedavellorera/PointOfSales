package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;

public class FetchNationalityRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public FetchNationalityRequest() {
        mapValue = new HashMap<>();
        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
