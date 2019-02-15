package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchRoomPendingResponse {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class GuestType {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("guest_type")
        @Expose
        private String guestType;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getGuestType() {
            return guestType;
        }

        public void setGuestType(String guestType) {
            this.guestType = guestType;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }
    public class RateRoom {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_rate")
        @Expose
        private String roomRate;
        @SerializedName("minHr")
        @Expose
        private Integer minHr;
        @SerializedName("maxHr")
        @Expose
        private Integer maxHr;
        @SerializedName("gracePeriod")
        @Expose
        private Integer gracePeriod;
        @SerializedName("is_promo")
        @Expose
        private Integer isPromo;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public Integer getMinHr() {
            return minHr;
        }

        public void setMinHr(Integer minHr) {
            this.minHr = minHr;
        }

        public Integer getMaxHr() {
            return maxHr;
        }

        public void setMaxHr(Integer maxHr) {
            this.maxHr = maxHr;
        }

        public Integer getGracePeriod() {
            return gracePeriod;
        }

        public void setGracePeriod(Integer gracePeriod) {
            this.gracePeriod = gracePeriod;
        }

        public Integer getIsPromo() {
            return isPromo;
        }

        public void setIsPromo(Integer isPromo) {
            this.isPromo = isPromo;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }

    public class Result {

        @SerializedName("room")
        @Expose
        private List<Room> room = null;
        @SerializedName("status")
        @Expose
        private Integer status;

        public List<Room> getRoom() {
            return room;
        }

        public void setRoom(List<Room> room) {
            this.room = room;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }

    public class Room {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_id")
        @Expose
        private Integer roomId;
        @SerializedName("room_type_id")
        @Expose
        private Integer roomTypeId;
        @SerializedName("room_rate_id")
        @Expose
        private Integer roomRateId;
        @SerializedName("room_rate_price_id")
        @Expose
        private Integer roomRatePriceId;
        @SerializedName("car_id")
        @Expose
        private Object carId;
        @SerializedName("vehicle_id")
        @Expose
        private Integer vehicleId;
        @SerializedName("guest_type_id")
        @Expose
        private Integer guestTypeId;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("room_no")
        @Expose
        private String roomNo;
        @SerializedName("room_type")
        @Expose
        private String roomType;
        @SerializedName("room_rate")
        @Expose
        private String roomRate;
        @SerializedName("adult")
        @Expose
        private Integer adult;
        @SerializedName("child")
        @Expose
        private Integer child;
        @SerializedName("plate_no")
        @Expose
        private Object plateNo;
        @SerializedName("steward")
        @Expose
        private Object steward;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("checkOut_by")
        @Expose
        private Object checkOutBy;
        @SerializedName("checkIn")
        @Expose
        private String checkIn;
        @SerializedName("wake_up_call")
        @Expose
        private String wakeUpCall;
        @SerializedName("expected_checkOut")
        @Expose
        private String expectedCheckOut;
        @SerializedName("checkOut")
        @Expose
        private Object checkOut;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("car")
        @Expose
        private Object car;
        @SerializedName("guest_type")
        @Expose
        private GuestType guestType;
        @SerializedName("transaction")
        @Expose
        private Transaction transaction;
        @SerializedName("room")
        @Expose
        private PendingRoomInnter room;
        @SerializedName("rate_room")
        @Expose
        private RateRoom rateRoom;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        public Integer getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(Integer roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public Integer getRoomRateId() {
            return roomRateId;
        }

        public void setRoomRateId(Integer roomRateId) {
            this.roomRateId = roomRateId;
        }

        public Integer getRoomRatePriceId() {
            return roomRatePriceId;
        }

        public void setRoomRatePriceId(Integer roomRatePriceId) {
            this.roomRatePriceId = roomRatePriceId;
        }

        public Object getCarId() {
            return carId;
        }

        public void setCarId(Object carId) {
            this.carId = carId;
        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public Integer getGuestTypeId() {
            return guestTypeId;
        }

        public void setGuestTypeId(Integer guestTypeId) {
            this.guestTypeId = guestTypeId;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public Integer getAdult() {
            return adult;
        }

        public void setAdult(Integer adult) {
            this.adult = adult;
        }

        public Integer getChild() {
            return child;
        }

        public void setChild(Integer child) {
            this.child = child;
        }

        public Object getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(Object plateNo) {
            this.plateNo = plateNo;
        }

        public Object getSteward() {
            return steward;
        }

        public void setSteward(Object steward) {
            this.steward = steward;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getCheckOutBy() {
            return checkOutBy;
        }

        public void setCheckOutBy(Object checkOutBy) {
            this.checkOutBy = checkOutBy;
        }

        public String getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(String checkIn) {
            this.checkIn = checkIn;
        }

        public String getWakeUpCall() {
            return wakeUpCall;
        }

        public void setWakeUpCall(String wakeUpCall) {
            this.wakeUpCall = wakeUpCall;
        }

        public String getExpectedCheckOut() {
            return expectedCheckOut;
        }

        public void setExpectedCheckOut(String expectedCheckOut) {
            this.expectedCheckOut = expectedCheckOut;
        }

        public Object getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(Object checkOut) {
            this.checkOut = checkOut;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public Object getCar() {
            return car;
        }

        public void setCar(Object car) {
            this.car = car;
        }

        public GuestType getGuestType() {
            return guestType;
        }

        public void setGuestType(GuestType guestType) {
            this.guestType = guestType;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

        public PendingRoomInnter getRoom() {
            return room;
        }

        public void setRoom(PendingRoomInnter room) {
            this.room = room;
        }

        public RateRoom getRateRoom() {
            return rateRoom;
        }

        public void setRateRoom(RateRoom rateRoom) {
            this.rateRoom = rateRoom;
        }

    }

    public class Transaction {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("receipt_no")
        @Expose
        private Object receiptNo;
        @SerializedName("guest_info_id")
        @Expose
        private Integer guestInfoId;
        @SerializedName("rc")
        @Expose
        private Integer rc;
        @SerializedName("fnb")
        @Expose
        private Integer fnb;
        @SerializedName("oth")
        @Expose
        private Integer oth;
        @SerializedName("ot_hours")
        @Expose
        private Integer otHours;
        @SerializedName("ot_amount")
        @Expose
        private Integer otAmount;
        @SerializedName("xPerson_amount")
        @Expose
        private Integer xPersonAmount;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("advance")
        @Expose
        private Integer advance;
        @SerializedName("tendered")
        @Expose
        private Integer tendered;
        @SerializedName("change")
        @Expose
        private Integer change;
        @SerializedName("vatable")
        @Expose
        private Double vatable;
        @SerializedName("vat")
        @Expose
        private Double vat;
        @SerializedName("vat_exempt")
        @Expose
        private Integer vatExempt;
        @SerializedName("xSkip")
        @Expose
        private Integer xSkip;
        @SerializedName("room_boy")
        @Expose
        private Object roomBoy;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("void")
        @Expose
        private Integer _void;
        @SerializedName("voided_by")
        @Expose
        private Object voidedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getControlNo() {
            return controlNo;
        }

        public void setControlNo(String controlNo) {
            this.controlNo = controlNo;
        }

        public Object getReceiptNo() {
            return receiptNo;
        }

        public void setReceiptNo(Object receiptNo) {
            this.receiptNo = receiptNo;
        }

        public Integer getGuestInfoId() {
            return guestInfoId;
        }

        public void setGuestInfoId(Integer guestInfoId) {
            this.guestInfoId = guestInfoId;
        }

        public Integer getRc() {
            return rc;
        }

        public void setRc(Integer rc) {
            this.rc = rc;
        }

        public Integer getFnb() {
            return fnb;
        }

        public void setFnb(Integer fnb) {
            this.fnb = fnb;
        }

        public Integer getOth() {
            return oth;
        }

        public void setOth(Integer oth) {
            this.oth = oth;
        }

        public Integer getOtHours() {
            return otHours;
        }

        public void setOtHours(Integer otHours) {
            this.otHours = otHours;
        }

        public Integer getOtAmount() {
            return otAmount;
        }

        public void setOtAmount(Integer otAmount) {
            this.otAmount = otAmount;
        }

        public Integer getXPersonAmount() {
            return xPersonAmount;
        }

        public void setXPersonAmount(Integer xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getAdvance() {
            return advance;
        }

        public void setAdvance(Integer advance) {
            this.advance = advance;
        }

        public Integer getTendered() {
            return tendered;
        }

        public void setTendered(Integer tendered) {
            this.tendered = tendered;
        }

        public Integer getChange() {
            return change;
        }

        public void setChange(Integer change) {
            this.change = change;
        }

        public Double getVatable() {
            return vatable;
        }

        public void setVatable(Double vatable) {
            this.vatable = vatable;
        }

        public Double getVat() {
            return vat;
        }

        public void setVat(Double vat) {
            this.vat = vat;
        }

        public Integer getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(Integer vatExempt) {
            this.vatExempt = vatExempt;
        }

        public Integer getXSkip() {
            return xSkip;
        }

        public void setXSkip(Integer xSkip) {
            this.xSkip = xSkip;
        }

        public Object getRoomBoy() {
            return roomBoy;
        }

        public void setRoomBoy(Object roomBoy) {
            this.roomBoy = roomBoy;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getVoid() {
            return _void;
        }

        public void setVoid(Integer _void) {
            this._void = _void;
        }

        public Object getVoidedBy() {
            return voidedBy;
        }

        public void setVoidedBy(Object voidedBy) {
            this.voidedBy = voidedBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

    }
}
