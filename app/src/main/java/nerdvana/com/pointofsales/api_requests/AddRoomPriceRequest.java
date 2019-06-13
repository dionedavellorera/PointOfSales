package nerdvana.com.pointofsales.api_requests;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.UpdateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class AddRoomPriceRequest extends BaseRequest {
    private Map<String, String> mapValue;

    public AddRoomPriceRequest(ArrayList<AddRateProductModel> addRateProductList,
                               String roomId,
                               ArrayList<VoidProductModel> voidList,
                               String remarks, String managerId,
                               String postTransId,
                               String freebieId,
                               ArrayList<UpdateProductModel> updateProductModelArrayList) {
        mapValue = new HashMap<>();
        mapValue.put("room_id", roomId);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("void", GsonHelper.getGson().toJson(voidList));
        mapValue.put("emp_id", managerId);
        mapValue.put("remarks", remarks);
        mapValue.put("branch_code", branchCode);
        mapValue.put("post_trans_id", postTransId);
        mapValue.put("freebie_id", freebieId);
        mapValue.put("update", GsonHelper.getGson().toJson(updateProductModelArrayList));

    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "AddRoomPriceRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
