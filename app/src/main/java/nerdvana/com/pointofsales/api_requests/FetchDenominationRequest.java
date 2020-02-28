package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class FetchDenominationRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public FetchDenominationRequest() {
        mapValue = new HashMap<>();

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }
}
