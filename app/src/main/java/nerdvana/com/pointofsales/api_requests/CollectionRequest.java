package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.PostedPaymentsModel;

public class CollectionRequest extends BaseRequest{
    private Map<String, String> mapValue;

    public CollectionRequest(List<CollectionFinalPostModel> collectionFinalPostModels, String fromPopUp) {
        mapValue = new HashMap<>();
        mapValue.put("post", GsonHelper.getGson().toJson(collectionFinalPostModels));
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("branch_id", branchId);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("from_pop_up", fromPopUp);

        mapValue.put(ApplicationConstants.POS_TO_ID, toId);
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "CollectionRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
