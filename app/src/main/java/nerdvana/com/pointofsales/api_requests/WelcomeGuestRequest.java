package nerdvana.com.pointofsales.api_requests;

import java.util.HashMap;
import java.util.Map;

public class WelcomeGuestRequest extends BaseRequest {
    private String roomId;
    private String roomTypeId;
    private String roomRateId;
    private String roomRatePriceId;
    private String carId;
    private String vehicleId;
    private String guestTypeId;
    private String customer;
    private String plateumber;
    private String steward;
    private String checkIn;
    private String checkOut;
    private String posId;
    private String adult;
    private String child;
    private String tax;
    private String roomAreaId;

    private Map<String, String> mapValue;

    public WelcomeGuestRequest(String roomId, String roomTypeId,
                               String roomRateId, String roomRatePriceId,
                               String carId, String vehicleId,
                               String guestTypeId, String customer,
                               String plateumber, String steward,
                               String checkIn, String checkOut,
                               String adult, String child,
                               String tax, String roomAreaId,
                               String nationalityId) {

        mapValue = new HashMap<>();
        mapValue.put("nationality_id", nationalityId);
        mapValue.put("room_id", roomId);
        mapValue.put("room_type_id", roomTypeId);
        mapValue.put("room_rate_id", roomRateId);
        mapValue.put("room_rate_price_id", roomRatePriceId);
        mapValue.put("car_id", carId);
        mapValue.put("vehicle_id", vehicleId);
        mapValue.put("guest_type_id", guestTypeId);
        mapValue.put("customer", customer);
        mapValue.put("plate_no", plateumber);
        mapValue.put("steward", steward);
        mapValue.put("checkIn", checkIn);
        mapValue.put("checkOut", checkOut);
        mapValue.put("user_id", userId);
        mapValue.put("adult", adult);
        mapValue.put("child", child);
        mapValue.put("tax", tax);
        mapValue.put("room_area_id", roomAreaId);
        mapValue.put("remarks", "");
        mapValue.put("platform", "1");

        mapValue.put("pos_id", machineNumber);
        mapValue.put("currency_id", currencyId);
        mapValue.put("currency_value", currencyValue);
        mapValue.put("branch_code", branchCode);
        mapValue.put("branch_id", branchId);


        this.roomId = roomId;
        this.roomTypeId = roomTypeId;
        this.roomRateId = roomRateId;
        this.roomRatePriceId = roomRatePriceId;
        this.carId = carId;
        this.vehicleId = vehicleId;
        this.guestTypeId = guestTypeId;
        this.customer = customer;
        this.plateumber = plateumber;
        this.steward = steward;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.adult = adult;
        this.child = child;
        this.tax = tax;
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public String getRoomRateId() {
        return roomRateId;
    }

    public String getRoomRatePriceId() {
        return roomRatePriceId;
    }

    public String getCarId() {
        return carId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getGuestTypeId() {
        return guestTypeId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPlateumber() {
        return plateumber;
    }

    public String getSteward() {
        return steward;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getUserId() {
        return userId;
    }

    public String getPosId() {
        return posId;
    }

    public String getAdult() {
        return adult;
    }

    public String getChild() {
        return child;
    }

    public String getTax() {
        return tax;
    }

    @Override
    public String toString() {
        return "WelcomeGuestRequest{" +
                "roomId='" + roomId + '\'' +
                ", roomTypeId='" + roomTypeId + '\'' +
                ", roomRateId='" + roomRateId + '\'' +
                ", roomRatePriceId='" + roomRatePriceId + '\'' +
                ", carId='" + carId + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", guestTypeId='" + guestTypeId + '\'' +
                ", customer='" + customer + '\'' +
                ", plateumber='" + plateumber + '\'' +
                ", steward='" + steward + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", userId='" + userId + '\'' +
                ", posId='" + posId + '\'' +
                ", adult='" + adult + '\'' +
                ", child='" + child + '\'' +
                ", tax='" + tax + '\'' +
                '}';
    }
}
