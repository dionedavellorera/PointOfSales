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

    public class Employee {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;

        public String getId() {
            return id;
        }

        public String getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }

    public class Result {

        @SerializedName("booked")
        @Expose
        private List<Booked> booked = null;
        @SerializedName("status")
        @Expose
        private Integer status;

//        @SerializedName("room")
//        @Expose
//        private Room room;


//        @SerializedName("vehicle")
//        @Expose
//        private List<Vehicle> vehicle = null;
//        @SerializedName("guestType")
//        @Expose
//        private List<GuestType_> guestType = null;

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

//        public Room getRoom() {
//            return room;
//        }
//
//        public void setRoom(Room room) {
//            this.room = room;
//        }


//        public List<Vehicle> getVehicle() {
//            return vehicle;
//        }
//
//        public void setVehicle(List<Vehicle> vehicle) {
//            this.vehicle = vehicle;
//        }

//        public List<GuestType_> getGuestType() {
//            return guestType;
//        }
//
//        public void setGuestType(List<GuestType_> guestType) {
//            this.guestType = guestType;
//        }

    }

    public class Area {
        @SerializedName("room_area")
        @Expose
        private String roomArea;
        @SerializedName("printer_path")
        @Expose
        private String printerPath;
        @SerializedName("kitchen_path")
        @Expose
        private String kitchenPath;

        public String getKitchenPath() {
            return kitchenPath;
        }

        public void setKitchenPath(String kitchenPath) {
            this.kitchenPath = kitchenPath;
        }

        public String getRoomArea() {
            return roomArea;
        }

        public void setRoomArea(String roomArea) {
            this.roomArea = roomArea;
        }

        public String getPrinterPath() {
            return printerPath;
        }

        public void setPrinterPath(String printerPath) {
            this.printerPath = printerPath;
        }
    }

    public class Room {
        @SerializedName("area")
        @Expose
        private Area area;
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
//        @SerializedName("room_rate")
//        @Expose
//        private List<Room> roomRate = null;
        @SerializedName("status")
        @Expose
        private Status status;
//        @SerializedName("transaction")
//        @Expose
//        private Transaction transaction;


        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public Integer getcRoomStat() {
            return cRoomStat;
        }

        public void setcRoomStat(Integer cRoomStat) {
            this.cRoomStat = cRoomStat;
        }

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

//        public List<RoomRateSub> getRoomRate() {
//            return roomRate;
//        }
//
//        public void setRoomRate(List<RoomRateSub> roomRate) {
//            this.roomRate = roomRate;
//        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

//        public Transaction getTransaction() {
//            return transaction;
//        }
//
//        public void setTransaction(Transaction transaction) {
//            this.transaction = transaction;
//        }

    }

    public class DiscountsOuter {
        @SerializedName("void_by")
        @Expose
        private String void_by;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("discount_type_id")
        @Expose
        private String discountTypeId;
        @SerializedName("discount_type")
        @Expose
        private String discountType;
        @SerializedName("discount_amount")
        @Expose
        private String discountAmount;
        @SerializedName("rc_discount")
        @Expose
        private String rcDiscount;
        @SerializedName("food_discount")
        @Expose
        private String foodDiscount;
        @SerializedName("extra_hours")
        @Expose
        private String extraHours;
        @SerializedName("control_no")
        @Expose
        private String controlNumber;

        public String getVoid_by() {
            return void_by;
        }

        public void setVoid_by(String void_by) {
            this.void_by = void_by;
        }

        public String getControlNumber() {
            return controlNumber;
        }

        public void setControlNumber(String controlNumber) {
            this.controlNumber = controlNumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiscountTypeId() {
            return discountTypeId;
        }

        public void setDiscountTypeId(String discountTypeId) {
            this.discountTypeId = discountTypeId;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getRcDiscount() {
            return rcDiscount;
        }

        public void setRcDiscount(String rcDiscount) {
            this.rcDiscount = rcDiscount;
        }

        public String getFoodDiscount() {
            return foodDiscount;
        }

        public void setFoodDiscount(String foodDiscount) {
            this.foodDiscount = foodDiscount;
        }

        public String getExtraHours() {
            return extraHours;
        }

        public void setExtraHours(String extraHours) {
            this.extraHours = extraHours;
        }
    }

    public class CustomerTrans {
        @SerializedName("customer")
        @Expose
        private String customer;
        @SerializedName("tin")
        @Expose
        private String tin;
        @SerializedName("address")
        @Expose
        private String address;

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getTin() {
            return tin;
        }

        public void setTin(String tin) {
            this.tin = tin;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public class FreebyRoomRatePrice {
        @SerializedName("room_rate")
        @Expose
        private FreebyRoomRate freebyRoomRate;
        @SerializedName("room_rate_id")
        @Expose
        private int roomRateId;
        @SerializedName("amount")
        @Expose
        private double amount;
        @SerializedName("flag")
        @Expose
        private int flag;

        public FreebyRoomRate getFreebyRoomRate() {
            return freebyRoomRate;
        }

        public void setFreebyRoomRate(FreebyRoomRate freebyRoomRate) {
            this.freebyRoomRate = freebyRoomRate;
        }

        public int getRoomRateId() {
            return roomRateId;
        }

        public void setRoomRateId(int roomRateId) {
            this.roomRateId = roomRateId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }

    public class FreebyRoomRate {
        @SerializedName("room_rate")
        @Expose
        private String roomRate;

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }
    }

    public class TransactionPostFreebyCore {
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("sub_category")
        @Expose
        private String subCategory;
        @SerializedName("department")
        @Expose
        private String department;
//        @SerializedName("product")
//        @Expose
//        private TransactionPostFreebyCoreProduct transactionPostFreebyCoreProduct;

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

//        public TransactionPostFreebyCoreProduct getTransactionPostFreebyCoreProduct() {
//            return transactionPostFreebyCoreProduct;
//        }
//
//        public void setTransactionPostFreebyCoreProduct(TransactionPostFreebyCoreProduct transactionPostFreebyCoreProduct) {
//            this.transactionPostFreebyCoreProduct = transactionPostFreebyCoreProduct;
//        }
    }

    public class TransactionPostFreebyCoreProduct {
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String productInitial;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getProductInitial() {
            return productInitial;
        }

        public void setProductInitial(String productInitial) {
            this.productInitial = productInitial;
        }
    }

    public class TransactionPostFreebies {
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("product")
        @Expose
        private TransactionPostFreebyCoreProduct transactionPostFreebyCoreProduct;
        @SerializedName("alacart")
        @Expose
        private List<PostAlaCart> transactionPostAlaCart;
        @SerializedName("group")
        private List<PostGroup> transactionPostGroup;

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public TransactionPostFreebyCoreProduct getTransactionPostFreebyCoreProduct() {
            return transactionPostFreebyCoreProduct;
        }

        public void setTransactionPostFreebyCoreProduct(TransactionPostFreebyCoreProduct transactionPostFreebyCoreProduct) {
            this.transactionPostFreebyCoreProduct = transactionPostFreebyCoreProduct;
        }

        public List<PostAlaCart> getTransactionPostAlaCart() {
            return transactionPostAlaCart;
        }

        public void setTransactionPostAlaCart(List<PostAlaCart> transactionPostAlaCart) {
            this.transactionPostAlaCart = transactionPostAlaCart;
        }

        public List<PostGroup> getTransactionPostGroup() {
            return transactionPostGroup;
        }

        public void setTransactionPostGroup(List<PostGroup> transactionPostGroup) {
            this.transactionPostGroup = transactionPostGroup;
        }
    }

    public class Freebies {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("room_rate_price")
        @Expose
        private FreebyRoomRatePrice freebyRoomRatePrice;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("price_rate_room_id")
        @Expose
        private int priceRateRoomId;
        @SerializedName("post_trans_id")
        @Expose
        private int postTransId;
        @SerializedName("qty")
        @Expose
        private int qty;
        @SerializedName("flag")
        @Expose
        private int flag;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public FreebyRoomRatePrice getFreebyRoomRatePrice() {
            return freebyRoomRatePrice;
        }

        public void setFreebyRoomRatePrice(FreebyRoomRatePrice freebyRoomRatePrice) {
            this.freebyRoomRatePrice = freebyRoomRatePrice;
        }

        public String getControlNo() {
            return controlNo;
        }

        public void setControlNo(String controlNo) {
            this.controlNo = controlNo;
        }

        public int getPriceRateRoomId() {
            return priceRateRoomId;
        }

        public void setPriceRateRoomId(int priceRateRoomId) {
            this.priceRateRoomId = priceRateRoomId;
        }

        public int getPostTransId() {
            return postTransId;
        }

        public void setPostTransId(int postTransId) {
            this.postTransId = postTransId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }

    public class RoomTransaction {
        @SerializedName("freebies")
        @Expose
        private List<Freebies> freebiesList;
        @SerializedName("customer")
        @Expose
        private CustomerTrans customerTrans;
        @SerializedName("employee_id")
        @Expose
        private String employee_id;
        @SerializedName("employee")
        @Expose
        private Employee employee;
        @SerializedName("discounts")
        @Expose
        private List<DiscountsOuter> discountsOuter;
        @SerializedName("payments")
        @Expose
        private List<Payment> payments = null;
        @SerializedName("is_soa")
        @Expose
        private Integer isSoa;
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
        private List<Post> post;
        @SerializedName("trans")
        @Expose
        private List<Tran> trans = null;

        public List<Freebies> getFreebiesList() {
            return freebiesList;
        }

        public void setFreebiesList(List<Freebies> freebiesList) {
            this.freebiesList = freebiesList;
        }

        public CustomerTrans getCustomerTrans() {
            return customerTrans;
        }

        public void setCustomerTrans(CustomerTrans customerTrans) {
            this.customerTrans = customerTrans;
        }

        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }

        public Employee getEmployee() {
            return employee;
        }

        public List<DiscountsOuter> getDiscountsOuter() {
            return discountsOuter;
        }

        public void setDiscountsOuter(List<DiscountsOuter> discountsOuter) {
            this.discountsOuter = discountsOuter;
        }

        public List<Tran> getTrans() {
            return trans;
        }

        public void setTrans(List<Tran> trans) {
            this.trans = trans;
        }

        public List<Payment> getPayments() {
            return payments;
        }

        public void setPayments(List<Payment> payments) {
            this.payments = payments;
        }

        public Integer getIsSoa() {
            return isSoa;
        }

        public void setIsSoa(Integer isSoa) {
            this.isSoa = isSoa;
        }

        public List<Post> getPost() {
            return post;
        }

        public void setPost(List<Post> post) {
            this.post = post;
        }

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

    }

    public class Customer {
        @SerializedName("customer")
        @Expose
        private String customer;
        @SerializedName("tin")
        @Expose
        private String tin;
        @SerializedName("address")
        @Expose
        private String address;

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getTin() {
            return tin;
        }

        public void setTin(String tin) {
            this.tin = tin;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }



    public class Booked {
        @SerializedName("customer")
        @Expose
        private Customer customer;
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
//        @SerializedName("status")
//        @Expose
//        private String status;



        @SerializedName("car")
        @Expose
        private Car car;
        @SerializedName("guest_type")
        @Expose
        private GuestType guestType;
        @SerializedName("transaction")
        @Expose
        private RoomTransaction transaction;
        @SerializedName("room")
        @Expose
        private Room room;
        @SerializedName("rate_room")
        @Expose
        private RoomRateInner rateRoom;
        @SerializedName("branch_nationality")
        @Expose
        private BranchNationality branchNationality;



//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }


        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public BranchNationality getBranchNationality() {
            return branchNationality;
        }

        public void setBranchNationality(BranchNationality branchNationality) {
            this.branchNationality = branchNationality;
        }

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

        public RoomTransaction getTransaction() {
            return transaction;
        }

        public void setTransaction(RoomTransaction transaction) {
            this.transaction = transaction;
        }

        public Room getRoom() {
            return room;
        }

        public void setRoom(Room room) {
            this.room = room;
        }

        public RoomRateInner getRateRoom() {
            return rateRoom;
        }

        public void setRateRoom(RoomRateInner rateRoom) {
            this.rateRoom = rateRoom;
        }

    }

    public class GuestType {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("guest_type")
        @Expose
        private String guestType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGuestType() {
            return guestType;
        }

        public void setGuestType(String guestType) {
            this.guestType = guestType;
        }
    }

    public class BranchNationality {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("core_id")
        @Expose
        private String coreId;
        @SerializedName("nationality")
        @Expose
        private String nationality;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoreId() {
            return coreId;
        }

        public void setCoreId(String coreId) {
            this.coreId = coreId;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }
    }


    public class BPostProduct {
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String productInitial;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getProductInitial() {
            return productInitial;
        }

        public void setProductInitial(String productInitial) {
            this.productInitial = productInitial;
        }
    }

    public class PostAlaCart {
        @SerializedName("qty")
        @Expose
        private int qty;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("product")
        @Expose
        private BPostProduct postAlaCartProduct;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public BPostProduct getPostAlaCartProduct() {
            return postAlaCartProduct;
        }

        public void setPostAlaCartProduct(BPostProduct postAlaCartProduct) {
            this.postAlaCartProduct = postAlaCartProduct;
        }
    }

    public class PostGroupInfo {
        @SerializedName("group_name")
        @Expose
        private String groupName;
        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }


    }

    public class PostGroupItem {
        @SerializedName("qty")
        @Expose
        private int qty;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("product")
        @Expose
        private BPostProduct postGroupItemProduct;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public BPostProduct getPostGroupItemProduct() {
            return postGroupItemProduct;
        }

        public void setPostGroupItemProduct(BPostProduct postGroupItemProduct) {
            this.postGroupItemProduct = postGroupItemProduct;
        }
    }

    public class PostGroup {
        @SerializedName("group_info")
        @Expose
        private PostGroupInfo postGroupInfo;

        public PostGroupInfo getPostGroupInfo() {
            return postGroupInfo;
        }

        public void setPostGroupInfo(PostGroupInfo postGroupInfo) {
            this.postGroupInfo = postGroupInfo;
        }

        @SerializedName("item")
        @Expose
        private List<PostGroupItem> postGroupItems;

        public List<PostGroupItem> getPostGroupItems() {
            return postGroupItems;
        }

        public void setPostGroupItems(List<PostGroupItem> postGroupItems) {
            this.postGroupItems = postGroupItems;
        }


    }

    public class Post {
        @SerializedName("freebie")
        @Expose
        private TransactionPostFreebies transactionPostFreebies;
        @SerializedName("group")
        @Expose
        private List<PostGroup> postGroupList;
        @SerializedName("alacart")
        @Expose
        private List<PostAlaCart> postAlaCartList;
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
        private Object roomRateId;
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
        @SerializedName("product")
        @Expose
        private Product product;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("discounts")
        @Expose
        private List<Discount> discounts;

        public TransactionPostFreebies getTransactionPostFreebies() {
            return transactionPostFreebies;
        }

        public void setTransactionPostFreebies(TransactionPostFreebies transactionPostFreebies) {
            this.transactionPostFreebies = transactionPostFreebies;
        }

        public List<PostGroup> getPostGroupList() {
            return postGroupList;
        }

        public void setPostGroupList(List<PostGroup> postGroupList) {
            this.postGroupList = postGroupList;
        }

        public List<PostAlaCart> getPostAlaCartList() {
            return postAlaCartList;
        }

        public void setPostAlaCartList(List<PostAlaCart> postAlaCartList) {
            this.postAlaCartList = postAlaCartList;
        }

        public void setRoomRateId(Object roomRateId) {
            this.roomRateId = roomRateId;
        }

        public Integer getxSkip() {
            return xSkip;
        }

        public void setxSkip(Integer xSkip) {
            this.xSkip = xSkip;
        }

        public Integer get_void() {
            return _void;
        }

        public void set_void(Integer _void) {
            this._void = _void;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public List<Discount> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<Discount> discounts) {
            this.discounts = discounts;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

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

        public Object getRoomRateId() {
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

    public class Discount {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("discount_posting_id")
        @Expose
        private String discountPostingId;
        @SerializedName("post_trans_id")
        @Expose
        private String postTransId;
        @SerializedName("discount_id")
        @Expose
        private String discountId;
        @SerializedName("discount_percentage")
        @Expose
        private String discountPercentage;
        @SerializedName("discount_amount")
        @Expose
        private String discountAmount;
        @SerializedName("emp_id")
        @Expose
        private String empId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiscountPostingId() {
            return discountPostingId;
        }

        public void setDiscountPostingId(String discountPostingId) {
            this.discountPostingId = discountPostingId;
        }

        public String getPostTransId() {
            return postTransId;
        }

        public void setPostTransId(String postTransId) {
            this.postTransId = postTransId;
        }

        public String getDiscountId() {
            return discountId;
        }

        public void setDiscountId(String discountId) {
            this.discountId = discountId;
        }

        public String getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(String discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }
    }

    public class Product {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String productInitial;
        @SerializedName("product_barcode")
        @Expose
        private String productBarcode;
        @SerializedName("unit_id")
        @Expose
        private Integer unitId;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("procedure")
        @Expose
        private Object procedure;
        @SerializedName("product_tags")
        @Expose
        private String productTags;
        @SerializedName("qty")
        @Expose
        private Integer qty;
        @SerializedName("min")
        @Expose
        private Integer min;
        @SerializedName("image_file")
        @Expose
        private String imageFile;
        @SerializedName("is_subtract")
        @Expose
        private Integer isSubtract;
        @SerializedName("is_available")
        @Expose
        private Integer isAvailable;
        @SerializedName("is_fixed_asset")
        @Expose
        private Integer isFixedAsset;
        @SerializedName("is_raw")
        @Expose
        private Integer isRaw;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("created_by")
        @Expose
        private Object createdBy;
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

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getProductInitial() {
            return productInitial;
        }

        public void setProductInitial(String productInitial) {
            this.productInitial = productInitial;
        }

        public String getProductBarcode() {
            return productBarcode;
        }

        public void setProductBarcode(String productBarcode) {
            this.productBarcode = productBarcode;
        }

        public Integer getUnitId() {
            return unitId;
        }

        public void setUnitId(Integer unitId) {
            this.unitId = unitId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getProcedure() {
            return procedure;
        }

        public void setProcedure(Object procedure) {
            this.procedure = procedure;
        }

        public String getProductTags() {
            return productTags;
        }

        public void setProductTags(String productTags) {
            this.productTags = productTags;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getIsSubtract() {
            return isSubtract;
        }

        public void setIsSubtract(Integer isSubtract) {
            this.isSubtract = isSubtract;
        }

        public Integer getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Integer isAvailable) {
            this.isAvailable = isAvailable;
        }

        public Integer getIsFixedAsset() {
            return isFixedAsset;
        }

        public void setIsFixedAsset(Integer isFixedAsset) {
            this.isFixedAsset = isFixedAsset;
        }

        public Integer getIsRaw() {
            return isRaw;
        }

        public void setIsRaw(Integer isRaw) {
            this.isRaw = isRaw;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
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

    public class InnerAr {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("ar_online")
        @Expose
        private String arOnline;

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

        public String getArOnline() {
            return arOnline;
        }

        public void setArOnline(String arOnline) {
            this.arOnline = arOnline;
        }
    }

    public class OuterAr {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("payment_id")
        @Expose
        private Integer paymentId;
        @SerializedName("ar_online_id")
        @Expose
        private Integer arOnlineId;
        @SerializedName("voucher_code")
        @Expose
        private String voucherCode;
        @SerializedName("ar")
        @Expose
        private InnerAr innerAr;

        public InnerAr getInnerAr() {
            return innerAr;
        }

        public void setInnerAr(InnerAr innerAr) {
            this.innerAr = innerAr;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getArOnlineId() {
            return arOnlineId;
        }

        public void setArOnlineId(Integer arOnlineId) {
            this.arOnlineId = arOnlineId;
        }

        public String getVoucherCode() {
            return voucherCode;
        }

        public void setVoucherCode(String voucherCode) {
            this.voucherCode = voucherCode;
        }
    }

    public class Car {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("car_make")
        @Expose
        private String carMake;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCarMake() {
            return carMake;
        }

        public void setCarMake(String carMake) {
            this.carMake = carMake;
        }
    }

    public class Currency {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("symbol_left")
        @Expose
        private String symbolLeft;
        @SerializedName("symbol_right")
        @Expose
        private String symbolRight;
        @SerializedName("value")
        @Expose
        private Double value;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("is_default")
        @Expose
        private Integer isDefault;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getSymbolLeft() {
            return symbolLeft;
        }

        public void setSymbolLeft(String symbolLeft) {
            this.symbolLeft = symbolLeft;
        }

        public String getSymbolRight() {
            return symbolRight;
        }

        public void setSymbolRight(String symbolRight) {
            this.symbolRight = symbolRight;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Integer getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(Integer isDefault) {
            this.isDefault = isDefault;
        }
    }

    public class Payment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("payment_type_id")
        @Expose
        private Integer paymentTypeId;
        @SerializedName("payment_description")
        @Expose
        private String paymentDescription;
        @SerializedName("collected_by")
        @Expose
        private Integer collectedBy;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("is_advance")
        @Expose
        private Integer isAdvance;
        @SerializedName("void_by")
        @Expose
        private Object voidBy;
        @SerializedName("void_at")
        @Expose
        private Object voidAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Double currencyValue;
        @SerializedName("ar")
        @Expose
        private OuterAr outerAr;
        @SerializedName("currency")
        @Expose
        private Currency currency;

        public Currency getCurrency() {
            return currency;
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
        }

        public OuterAr getOuterAr() {
            return outerAr;
        }

        public void setOuterAr(OuterAr outerAr) {
            this.outerAr = outerAr;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public Double getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Double currencyValue) {
            this.currencyValue = currencyValue;
        }

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

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Integer getPaymentTypeId() {
            return paymentTypeId;
        }

        public void setPaymentTypeId(Integer paymentTypeId) {
            this.paymentTypeId = paymentTypeId;
        }

        public String getPaymentDescription() {
            return paymentDescription;
        }

        public void setPaymentDescription(String paymentDescription) {
            this.paymentDescription = paymentDescription;
        }

        public Integer getCollectedBy() {
            return collectedBy;
        }

        public void setCollectedBy(Integer collectedBy) {
            this.collectedBy = collectedBy;
        }

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getIsAdvance() {
            return isAdvance;
        }

        public void setIsAdvance(Integer isAdvance) {
            this.isAdvance = isAdvance;
        }

        public Object getVoidBy() {
            return voidBy;
        }

        public void setVoidBy(Object voidBy) {
            this.voidBy = voidBy;
        }

        public Object getVoidAt() {
            return voidAt;
        }

        public void setVoidAt(Object voidAt) {
            this.voidAt = voidAt;
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

    public class Tran {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
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
        @SerializedName("order")
        @Expose
        private List<Order> order = null;

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

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }

    }

    public class Order {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("post_order_id")
        @Expose
        private Integer postOrderId;
        @SerializedName("post_trans_id")
        @Expose
        private Integer postTransId;
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
        private List<PostTrans> post = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPostOrderId() {
            return postOrderId;
        }

        public void setPostOrderId(Integer postOrderId) {
            this.postOrderId = postOrderId;
        }

        public Integer getPostTransId() {
            return postTransId;
        }

        public void setPostTransId(Integer postTransId) {
            this.postTransId = postTransId;
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

        public List<PostTrans> getPost() {
            return post;
        }

        public void setPost(List<PostTrans> post) {
            this.post = post;
        }

    }


    public class PostTrans {

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
        private Object roomRateId;
        @SerializedName("room_rate_price_id")
        @Expose
        private Integer roomRatePriceId;
        @SerializedName("category_id")
        @Expose
        private Object categoryId;
        @SerializedName("sub_category_id")
        @Expose
        private Object subCategoryId;
        @SerializedName("department_id")
        @Expose
        private Object departmentId;
        @SerializedName("room_no")
        @Expose
        private String roomNo;
        @SerializedName("room_type")
        @Expose
        private String roomType;
        @SerializedName("room_rate")
        @Expose
        private Object roomRate;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("sub_category")
        @Expose
        private Object subCategory;
        @SerializedName("department")
        @Expose
        private String department;
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
        private Integer posId;
        @SerializedName("is_posted")
        @Expose
        private Integer isPosted;
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
        @SerializedName("is_open_price")
        @Expose
        private Integer isOpenPrice;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("product")
        @Expose
        private PostProduct product;

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

        public Object getRoomRateId() {
            return roomRateId;
        }

        public void setRoomRateId(Object roomRateId) {
            this.roomRateId = roomRateId;
        }

        public Integer getRoomRatePriceId() {
            return roomRatePriceId;
        }

        public void setRoomRatePriceId(Integer roomRatePriceId) {
            this.roomRatePriceId = roomRatePriceId;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public Object getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(Object subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public Object getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Object departmentId) {
            this.departmentId = departmentId;
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

        public Object getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(Object roomRate) {
            this.roomRate = roomRate;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Object getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(Object subCategory) {
            this.subCategory = subCategory;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
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

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getIsPosted() {
            return isPosted;
        }

        public void setIsPosted(Integer isPosted) {
            this.isPosted = isPosted;
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

        public Integer getIsOpenPrice() {
            return isOpenPrice;
        }

        public void setIsOpenPrice(Integer isOpenPrice) {
            this.isOpenPrice = isOpenPrice;
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

        public PostProduct getProduct() {
            return product;
        }

        public void setProduct(PostProduct product) {
            this.product = product;
        }

    }

    public class PostProduct {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String productInitial;
        @SerializedName("product_barcode")
        @Expose
        private String productBarcode;
        @SerializedName("product_tags")
        @Expose
        private String productTags;
        @SerializedName("qty")
        @Expose
        private Integer qty;
        @SerializedName("min")
        @Expose
        private Integer min;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("is_available")
        @Expose
        private Integer isAvailable;
        @SerializedName("is_fixed_asset")
        @Expose
        private Integer isFixedAsset;
        @SerializedName("is_raw")
        @Expose
        private Integer isRaw;
        @SerializedName("is_promo")
        @Expose
        private Integer isPromo;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("price_id")
        @Expose
        private Integer priceId;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("mark_up")
        @Expose
        private Double markUp;
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

        public Integer getCoreId() {
            return coreId;
        }

        public void setCoreId(Integer coreId) {
            this.coreId = coreId;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getProductInitial() {
            return productInitial;
        }

        public void setProductInitial(String productInitial) {
            this.productInitial = productInitial;
        }

        public String getProductBarcode() {
            return productBarcode;
        }

        public void setProductBarcode(String productBarcode) {
            this.productBarcode = productBarcode;
        }

        public String getProductTags() {
            return productTags;
        }

        public void setProductTags(String productTags) {
            this.productTags = productTags;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Integer isAvailable) {
            this.isAvailable = isAvailable;
        }

        public Integer getIsFixedAsset() {
            return isFixedAsset;
        }

        public void setIsFixedAsset(Integer isFixedAsset) {
            this.isFixedAsset = isFixedAsset;
        }

        public Integer getIsRaw() {
            return isRaw;
        }

        public void setIsRaw(Integer isRaw) {
            this.isRaw = isRaw;
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

        public Integer getPriceId() {
            return priceId;
        }

        public void setPriceId(Integer priceId) {
            this.priceId = priceId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Double getMarkUp() {
            return markUp;
        }

        public void setMarkUp(Double markUp) {
            this.markUp = markUp;
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


}
