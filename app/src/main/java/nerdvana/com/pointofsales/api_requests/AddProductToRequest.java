package nerdvana.com.pointofsales.api_requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nerdvana.com.pointofsales.GsonHelper;
import nerdvana.com.pointofsales.model.AddRateProductModel;
import nerdvana.com.pointofsales.model.UpdateProductModel;
import nerdvana.com.pointofsales.model.VoidProductModel;

public class AddProductToRequest extends BaseRequest {
    private Map<String, String> mapValue;
    private ArrayList<UpdateProductModel> updateProductModels;
    public AddProductToRequest(ArrayList<AddRateProductModel> addRateProductList, String roomId,
                               String roomAreaId, String controlNo,
                               ArrayList<VoidProductModel> voidList,
                               String remarks,
                               String postTransId,
                               String freebieId,
                               ArrayList<UpdateProductModel> updateProductModelArrayList) {
        mapValue = new HashMap<>();
        mapValue.put("room_area_id", roomAreaId);
        mapValue.put("control_no", controlNo);
        mapValue.put("user_id", userId);
        mapValue.put("pos_id", machineNumber);
        mapValue.put("post", GsonHelper.getGson().toJson(addRateProductList));
        mapValue.put("void", GsonHelper.getGson().toJson(voidList));
        mapValue.put("emp_id", "762");
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("remarks", remarks);
        mapValue.put("branch_code", branchCode);
        mapValue.put("tax", tax);
        mapValue.put("post_trans_id", postTransId);
        mapValue.put("freebie_id", freebieId);
        mapValue.put("update", GsonHelper.getGson().toJson(updateProductModelArrayList));
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @Override
    public String toString() {
        return "AddProductToRequest{" +
                "mapValue=" + mapValue +
                '}';
    }
}
