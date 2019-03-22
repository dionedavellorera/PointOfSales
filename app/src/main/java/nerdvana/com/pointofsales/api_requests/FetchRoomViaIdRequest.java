package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class FetchRoomViaIdRequest {
    private Map<String, String> mapValue;

    public FetchRoomViaIdRequest(String roomId) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

}
