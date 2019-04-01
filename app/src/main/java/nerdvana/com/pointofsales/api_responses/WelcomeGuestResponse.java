package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WelcomeGuestResponse {

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

    public class Post {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("room_id")
        @Expose
        private Integer roomId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("room_type_id")
        @Expose
        private Integer roomTypeId;
        @SerializedName("room_rate_id")
        @Expose
        private Integer roomRateId;
        @SerializedName("room_rate_price_id")
        @Expose
        private Integer roomRatePriceId;
        @SerializedName("room_no")
        @Expose
        private String roomNo;
        @SerializedName("room_type")
        @Expose
        private String roomType;
        @SerializedName("room_rate")
        @Expose
        private String roomRate;
        @SerializedName("qty")
        @Expose
        private Integer qty;
        @SerializedName("unit_cost")
        @Expose
        private Double unitCost;
        @SerializedName("price")
        @Expose
        private Double price;
        @SerializedName("total")
        @Expose
        private Double total;
        @SerializedName("discount")
        @Expose
        private Double discount;
        @SerializedName("tax_info")
        @Expose
        private String taxInfo;
        @SerializedName("vatable")
        @Expose
        private Double vatable;
        @SerializedName("vat")
        @Expose
        private Double vat;
        @SerializedName("vat_exempt")
        @Expose
        private Double vatExempt;
        @SerializedName("room_area_id")
        @Expose
        private Integer roomAreaId;
        @SerializedName("pos_id")
        @Expose
        private Object posId;
        @SerializedName("xSkip")
        @Expose
        private Integer xSkip;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("void")
        @Expose
        private Integer _void;
        @SerializedName("voided_by")
        @Expose
        private Object voidedBy;
        @SerializedName("bump_by")
        @Expose
        private Object bumpBy;
        @SerializedName("bumped_at")
        @Expose
        private Object bumpedAt;
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

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
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

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Double getUnitCost() {
            return unitCost;
        }

        public void setUnitCost(Double unitCost) {
            this.unitCost = unitCost;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        public String getTaxInfo() {
            return taxInfo;
        }

        public void setTaxInfo(String taxInfo) {
            this.taxInfo = taxInfo;
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

        public Double getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(Double vatExempt) {
            this.vatExempt = vatExempt;
        }

        public Integer getRoomAreaId() {
            return roomAreaId;
        }

        public void setRoomAreaId(Integer roomAreaId) {
            this.roomAreaId = roomAreaId;
        }

        public Object getPosId() {
            return posId;
        }

        public void setPosId(Object posId) {
            this.posId = posId;
        }

        public Integer getXSkip() {
            return xSkip;
        }

        public void setXSkip(Integer xSkip) {
            this.xSkip = xSkip;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public Object getBumpBy() {
            return bumpBy;
        }

        public void setBumpBy(Object bumpBy) {
            this.bumpBy = bumpBy;
        }

        public Object getBumpedAt() {
            return bumpedAt;
        }

        public void setBumpedAt(Object bumpedAt) {
            this.bumpedAt = bumpedAt;
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

        @SerializedName("booked")
        @Expose
        private List<Booked> booked = null;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("room")
        @Expose
        private RoomOuter room;
        @SerializedName("car")
        @Expose
        private List<Car> car = null;
        @SerializedName("vehicle")
        @Expose
        private List<Vehicle> vehicle = null;
        @SerializedName("guestType")
        @Expose
        private List<GuestType> guestType = null;

        public List<Booked> getBooked() {
            return booked;
        }

        public void setBooked(List<Booked> booked) {
            this.booked = booked;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public RoomOuter getRoom() {
            return room;
        }

        public void setRoom(RoomOuter room) {
            this.room = room;
        }

        public List<Car> getCar() {
            return car;
        }

        public void setCar(List<Car> car) {
            this.car = car;
        }

        public List<Vehicle> getVehicle() {
            return vehicle;
        }

        public void setVehicle(List<Vehicle> vehicle) {
            this.vehicle = vehicle;
        }

        public List<GuestType> getGuestType() {
            return guestType;
        }

        public void setGuestType(List<GuestType> guestType) {
            this.guestType = guestType;
        }

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


    public class Booked {

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
        private Integer carId;
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
        private String plateNo;
        @SerializedName("steward")
        @Expose
        private Integer steward;
        @SerializedName("user_id")
        @Expose
        private Object userId;
        @SerializedName("checkOut_by")
        @Expose
        private Object checkOutBy;
        @SerializedName("checkIn")
        @Expose
        private Object checkIn;
        @SerializedName("wake_up_call")
        @Expose
        private Object wakeUpCall;
        @SerializedName("expected_checkOut")
        @Expose
        private Object expectedCheckOut;
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
        private Car car;
        @SerializedName("guest_type")
        @Expose
        private GuestType guestType;
        @SerializedName("transaction")
        @Expose
        private TransactionBooked transaction;
        @SerializedName("room")
        @Expose
        private Room room;
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

        public Integer getCarId() {
            return carId;
        }

        public void setCarId(Integer carId) {
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

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public Integer getSteward() {
            return steward;
        }

        public void setSteward(Integer steward) {
            this.steward = steward;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getCheckOutBy() {
            return checkOutBy;
        }

        public void setCheckOutBy(Object checkOutBy) {
            this.checkOutBy = checkOutBy;
        }

        public Object getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(Object checkIn) {
            this.checkIn = checkIn;
        }

        public Object getWakeUpCall() {
            return wakeUpCall;
        }

        public void setWakeUpCall(Object wakeUpCall) {
            this.wakeUpCall = wakeUpCall;
        }

        public Object getExpectedCheckOut() {
            return expectedCheckOut;
        }

        public void setExpectedCheckOut(Object expectedCheckOut) {
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

        public Car getCar() {
            return car;
        }

        public void setCar(Car car) {
            this.car = car;
        }

        public GuestType getGuestType() {
            return guestType;
        }

        public void setGuestType(GuestType guestType) {
            this.guestType = guestType;
        }

        public TransactionBooked getTransaction() {
            return transaction;
        }

        public void setTransaction(TransactionBooked transaction) {
            this.transaction = transaction;
        }

        public Room getRoom() {
            return room;
        }

        public void setRoom(Room room) {
            this.room = room;
        }

        public RateRoom getRateRoom() {
            return rateRoom;
        }

        public void setRateRoom(RateRoom rateRoom) {
            this.rateRoom = rateRoom;
        }

    }




    public class Car {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("car_make")
        @Expose
        private String carMake;
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

        public String getCarMake() {
            return carMake;
        }

        public void setCarMake(String carMake) {
            this.carMake = carMake;
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

    public class Room {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_no")
        @Expose
        private String roomNo;
        @SerializedName("room_name")
        @Expose
        private String roomName;
        @SerializedName("CRoom_Stat")
        @Expose
        private Integer cRoomStat;
        @SerializedName("room_type_id")
        @Expose
        private Integer roomTypeId;
        @SerializedName("room_area_id")
        @Expose
        private Integer roomAreaId;
        @SerializedName("last_checkout")
        @Expose
        private Object lastCheckout;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
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

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public Integer getCRoomStat() {
            return cRoomStat;
        }

        public void setCRoomStat(Integer cRoomStat) {
            this.cRoomStat = cRoomStat;
        }

        public Integer getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(Integer roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public Integer getRoomAreaId() {
            return roomAreaId;
        }

        public void setRoomAreaId(Integer roomAreaId) {
            this.roomAreaId = roomAreaId;
        }

        public Object getLastCheckout() {
            return lastCheckout;
        }

        public void setLastCheckout(Object lastCheckout) {
            this.lastCheckout = lastCheckout;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
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


    public class RoomRate {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_rate_price_id")
        @Expose
        private Integer roomRatePriceId;
        @SerializedName("room_id")
        @Expose
        private Integer roomId;
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
        @SerializedName("room_rate")
        @Expose
        private RoomRateInner roomRate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRoomRatePriceId() {
            return roomRatePriceId;
        }

        public void setRoomRatePriceId(Integer roomRatePriceId) {
            this.roomRatePriceId = roomRatePriceId;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
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

        public RoomRateInner getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(RoomRateInner roomRate) {
            this.roomRate = roomRate;
        }

    }

    public class RoomRateInner {

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

    public class RoomOuter {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_no")
        @Expose
        private String roomNo;
        @SerializedName("room_name")
        @Expose
        private String roomName;
        @SerializedName("CRoom_Stat")
        @Expose
        private Integer cRoomStat;
        @SerializedName("room_type_id")
        @Expose
        private Integer roomTypeId;
        @SerializedName("room_area_id")
        @Expose
        private Integer roomAreaId;
        @SerializedName("last_checkout")
        @Expose
        private Object lastCheckout;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("room_rate")
        @Expose
        private List<RoomRate> roomRate = null;
        @SerializedName("status")
        @Expose
        private Status status;
        @SerializedName("transaction")
        @Expose
        private Transaction transaction;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public Integer getCRoomStat() {
            return cRoomStat;
        }

        public void setCRoomStat(Integer cRoomStat) {
            this.cRoomStat = cRoomStat;
        }

        public Integer getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(Integer roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public Integer getRoomAreaId() {
            return roomAreaId;
        }

        public void setRoomAreaId(Integer roomAreaId) {
            this.roomAreaId = roomAreaId;
        }

        public Object getLastCheckout() {
            return lastCheckout;
        }

        public void setLastCheckout(Object lastCheckout) {
            this.lastCheckout = lastCheckout;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
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

        public List<RoomRate> getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(List<RoomRate> roomRate) {
            this.roomRate = roomRate;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

    }


    public class Status {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("room_status")
        @Expose
        private String roomStatus;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("is_blink")
        @Expose
        private Integer isBlink;
        @SerializedName("is_timer")
        @Expose
        private Integer isTimer;
        @SerializedName("is_name")
        @Expose
        private Integer isName;
        @SerializedName("is_buddy")
        @Expose
        private Integer isBuddy;
        @SerializedName("is_cancel")
        @Expose
        private Integer isCancel;
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

        public Integer getCoreId() {
            return coreId;
        }

        public void setCoreId(Integer coreId) {
            this.coreId = coreId;
        }

        public String getRoomStatus() {
            return roomStatus;
        }

        public void setRoomStatus(String roomStatus) {
            this.roomStatus = roomStatus;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getIsBlink() {
            return isBlink;
        }

        public void setIsBlink(Integer isBlink) {
            this.isBlink = isBlink;
        }

        public Integer getIsTimer() {
            return isTimer;
        }

        public void setIsTimer(Integer isTimer) {
            this.isTimer = isTimer;
        }

        public Integer getIsName() {
            return isName;
        }

        public void setIsName(Integer isName) {
            this.isName = isName;
        }

        public Integer getIsBuddy() {
            return isBuddy;
        }

        public void setIsBuddy(Integer isBuddy) {
            this.isBuddy = isBuddy;
        }

        public Integer getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(Integer isCancel) {
            this.isCancel = isCancel;
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
        @SerializedName("payment_type_id")
        @Expose
        private Object paymentTypeId;
        @SerializedName("rc")
        @Expose
        private Double rc;
        @SerializedName("fnb")
        @Expose
        private double fnb;
        @SerializedName("oth")
        @Expose
        private Double oth;
        @SerializedName("ot_hours")
        @Expose
        private Double otHours;
        @SerializedName("ot_amount")
        @Expose
        private Double otAmount;
        @SerializedName("xPerson_amount")
        @Expose
        private Double xPersonAmount;
        @SerializedName("discount")
        @Expose
        private Double discount;
        @SerializedName("total")
        @Expose
        private Double total;
        @SerializedName("advance")
        @Expose
        private Double advance;
        @SerializedName("tendered")
        @Expose
        private Double tendered;
        @SerializedName("change")
        @Expose
        private Double change;
        @SerializedName("vatable")
        @Expose
        private Double vatable;
        @SerializedName("vat")
        @Expose
        private Double vat;
        @SerializedName("vat_exempt")
        @Expose
        private Double vatExempt;
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
        @SerializedName("post")
        @Expose
        private Post post;

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

        public Object getPaymentTypeId() {
            return paymentTypeId;
        }

        public void setPaymentTypeId(Object paymentTypeId) {
            this.paymentTypeId = paymentTypeId;
        }

        public Double getRc() {
            return rc;
        }

        public void setRc(Double rc) {
            this.rc = rc;
        }

        public Double getFnb() {
            return fnb;
        }

        public void setFnb(Double fnb) {
            this.fnb = fnb;
        }

        public Double getOth() {
            return oth;
        }

        public void setOth(Double oth) {
            this.oth = oth;
        }

        public Double getOtHours() {
            return otHours;
        }

        public void setOtHours(Double otHours) {
            this.otHours = otHours;
        }

        public Double getOtAmount() {
            return otAmount;
        }

        public void setOtAmount(Double otAmount) {
            this.otAmount = otAmount;
        }

        public Double getXPersonAmount() {
            return xPersonAmount;
        }

        public void setXPersonAmount(Double xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getAdvance() {
            return advance;
        }

        public void setAdvance(Double advance) {
            this.advance = advance;
        }

        public Double getTendered() {
            return tendered;
        }

        public void setTendered(Double tendered) {
            this.tendered = tendered;
        }

        public Double getChange() {
            return change;
        }

        public void setChange(Double change) {
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

        public Double getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(Double vatExempt) {
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

        public Post getPost() {
            return post;
        }

        public void setPost(Post post) {
            this.post = post;
        }

    }

    public class TransactionBooked {

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
        private Integer carId;
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
        private String plateNo;
        @SerializedName("steward")
        @Expose
        private Integer steward;
        @SerializedName("user_id")
        @Expose
        private Object userId;
        @SerializedName("checkOut_by")
        @Expose
        private Object checkOutBy;
        @SerializedName("checkIn")
        @Expose
        private Object checkIn;
        @SerializedName("wake_up_call")
        @Expose
        private Object wakeUpCall;
        @SerializedName("expected_checkOut")
        @Expose
        private Object expectedCheckOut;
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

        public Integer getCarId() {
            return carId;
        }

        public void setCarId(Integer carId) {
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

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public Integer getSteward() {
            return steward;
        }

        public void setSteward(Integer steward) {
            this.steward = steward;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getCheckOutBy() {
            return checkOutBy;
        }

        public void setCheckOutBy(Object checkOutBy) {
            this.checkOutBy = checkOutBy;
        }

        public Object getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(Object checkIn) {
            this.checkIn = checkIn;
        }

        public Object getWakeUpCall() {
            return wakeUpCall;
        }

        public void setWakeUpCall(Object wakeUpCall) {
            this.wakeUpCall = wakeUpCall;
        }

        public Object getExpectedCheckOut() {
            return expectedCheckOut;
        }

        public void setExpectedCheckOut(Object expectedCheckOut) {
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

    }

    public class Vehicle {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("vehicle")
        @Expose
        private String vehicle;
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

        public String getVehicle() {
            return vehicle;
        }

        public void setVehicle(String vehicle) {
            this.vehicle = vehicle;
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
}
