package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewReceiptViaDateResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
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


    public class GuestInfo {

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
        @SerializedName("nationality_id")
        @Expose
        private Integer nationalityId;
        @SerializedName("room_no")
        @Expose
        private Object roomNo;
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
        private Integer steward;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("checkOut_by")
        @Expose
        private Integer checkOutBy;
        @SerializedName("checkIn")
        @Expose
        private String checkIn;
        @SerializedName("wake_up_call")
        @Expose
        private String wakeUpCall;
        @SerializedName("expected_checkOut")
        @Expose
        private String expectedCheckOut;
        @SerializedName("outCheck_by")
        @Expose
        private Integer outCheckBy;
        @SerializedName("checkOut")
        @Expose
        private String checkOut;
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
        @SerializedName("customer")
        @Expose
        private Customer customer;
        @SerializedName("branch_nationality")
        @Expose
        private BranchNationality branchNationality;
        @SerializedName("room")
        @Expose
        private Room room;
        @SerializedName("rate_room")
        @Expose
        private RateRoom rateRoom;
        @SerializedName("cashier_in")
        @Expose
        private CashierIn cashierIn;
        @SerializedName("room_boy_in")
        @Expose
        private RoomBoyIn roomBoyIn;
        @SerializedName("cashier_out")
        @Expose
        private CashierOut_ cashierOut;
        @SerializedName("room_boy_out")
        @Expose
        private Object roomBoyOut;

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

        public Integer getNationalityId() {
            return nationalityId;
        }

        public void setNationalityId(Integer nationalityId) {
            this.nationalityId = nationalityId;
        }

        public Object getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(Object roomNo) {
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

        public Integer getSteward() {
            return steward;
        }

        public void setSteward(Integer steward) {
            this.steward = steward;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getCheckOutBy() {
            return checkOutBy;
        }

        public void setCheckOutBy(Integer checkOutBy) {
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

        public Integer getOutCheckBy() {
            return outCheckBy;
        }

        public void setOutCheckBy(Integer outCheckBy) {
            this.outCheckBy = outCheckBy;
        }

        public String getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(String checkOut) {
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

        public CashierIn getCashierIn() {
            return cashierIn;
        }

        public void setCashierIn(CashierIn cashierIn) {
            this.cashierIn = cashierIn;
        }

        public RoomBoyIn getRoomBoyIn() {
            return roomBoyIn;
        }

        public void setRoomBoyIn(RoomBoyIn roomBoyIn) {
            this.roomBoyIn = roomBoyIn;
        }

        public CashierOut_ getCashierOut() {
            return cashierOut;
        }

        public void setCashierOut(CashierOut_ cashierOut) {
            this.cashierOut = cashierOut;
        }

        public Object getRoomBoyOut() {
            return roomBoyOut;
        }

        public void setRoomBoyOut(Object roomBoyOut) {
            this.roomBoyOut = roomBoyOut;
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
        private List<Post> post = null;

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

        public List<Post> getPost() {
            return post;
        }

        public void setPost(List<Post> post) {
            this.post = post;
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
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
        @SerializedName("cash_and_reconcile_id")
        @Expose
        private Integer cashAndReconcileId;
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

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public Integer getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Integer currencyValue) {
            this.currencyValue = currencyValue;
        }

        public Integer getCashAndReconcileId() {
            return cashAndReconcileId;
        }

        public void setCashAndReconcileId(Integer cashAndReconcileId) {
            this.cashAndReconcileId = cashAndReconcileId;
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
        private String roomRate;
        @SerializedName("category")
        @Expose
        private Object category;
        @SerializedName("sub_category")
        @Expose
        private Object subCategory;
        @SerializedName("department")
        @Expose
        private Object department;
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
        @SerializedName("bundle_price")
        @Expose
        private Double bundlePrice;
        @SerializedName("bundle_total")
        @Expose
        private Double bundleTotal;
        @SerializedName("bundle_discount")
        @Expose
        private Double bundleDiscount;
        @SerializedName("food_budget_discount")
        @Expose
        private Double foodBudgetDiscount;
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
        @SerializedName("vat_exempt_sales")
        @Expose
        private Double vatExemptSales;
        @SerializedName("room_area_id")
        @Expose
        private Integer roomAreaId;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("is_posted")
        @Expose
        private Integer isPosted;
        @SerializedName("is_product_bundle")
        @Expose
        private String isProductBundle;
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
        @SerializedName("is_promo")
        @Expose
        private Integer isPromo;
        @SerializedName("is_bundle")
        @Expose
        private Integer isBundle;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("price_rate_room_id")
        @Expose
        private Integer priceRateRoomId;
        @SerializedName("product_alacart_id")
        @Expose
        private Integer productAlacartId;
        @SerializedName("currency_id")
        @Expose
        private Object currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
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

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public Object getCategory() {
            return category;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public Object getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(Object subCategory) {
            this.subCategory = subCategory;
        }

        public Object getDepartment() {
            return department;
        }

        public void setDepartment(Object department) {
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

        public Double getBundlePrice() {
            return bundlePrice;
        }

        public void setBundlePrice(Double bundlePrice) {
            this.bundlePrice = bundlePrice;
        }

        public Double getBundleTotal() {
            return bundleTotal;
        }

        public void setBundleTotal(Double bundleTotal) {
            this.bundleTotal = bundleTotal;
        }

        public Double getBundleDiscount() {
            return bundleDiscount;
        }

        public void setBundleDiscount(Double bundleDiscount) {
            this.bundleDiscount = bundleDiscount;
        }

        public Double getFoodBudgetDiscount() {
            return foodBudgetDiscount;
        }

        public void setFoodBudgetDiscount(Double foodBudgetDiscount) {
            this.foodBudgetDiscount = foodBudgetDiscount;
        }

        public void setVatExempt(Double vatExempt) {
            this.vatExempt = vatExempt;
        }

        public void setVatExemptSales(Double vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
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

        public Double getVatExemptSales() {
            return vatExemptSales;
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

        public String getIsProductBundle() {
            return isProductBundle;
        }

        public void setIsProductBundle(String isProductBundle) {
            this.isProductBundle = isProductBundle;
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

        public Integer getIsPromo() {
            return isPromo;
        }

        public void setIsPromo(Integer isPromo) {
            this.isPromo = isPromo;
        }

        public Integer getIsBundle() {
            return isBundle;
        }

        public void setIsBundle(Integer isBundle) {
            this.isBundle = isBundle;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getPriceRateRoomId() {
            return priceRateRoomId;
        }

        public void setPriceRateRoomId(Integer priceRateRoomId) {
            this.priceRateRoomId = priceRateRoomId;
        }

        public Integer getProductAlacartId() {
            return productAlacartId;
        }

        public void setProductAlacartId(Integer productAlacartId) {
            this.productAlacartId = productAlacartId;
        }

        public Object getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(Object currencyId) {
            this.currencyId = currencyId;
        }

        public Integer getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Integer currencyValue) {
            this.currencyValue = currencyValue;
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

    public class Product {
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String product_initial;

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getProduct_initial() {
            return product_initial;
        }

        public void setProduct_initial(String product_initial) {
            this.product_initial = product_initial;
        }
    }

    public class Post_ {

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
        private String roomRate;
        @SerializedName("category")
        @Expose
        private Object category;
        @SerializedName("sub_category")
        @Expose
        private Object subCategory;
        @SerializedName("department")
        @Expose
        private Object department;
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
        @SerializedName("bundle_price")
        @Expose
        private Double bundlePrice;
        @SerializedName("bundle_total")
        @Expose
        private Double bundleTotal;
        @SerializedName("bundle_discount")
        @Expose
        private Double bundleDiscount;
        @SerializedName("food_budget_discount")
        @Expose
        private Double foodBudgetDiscount;
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
        @SerializedName("vat_exempt_sales")
        @Expose
        private Double vatExemptSales;
        @SerializedName("room_area_id")
        @Expose
        private Integer roomAreaId;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("is_posted")
        @Expose
        private Integer isPosted;
        @SerializedName("is_product_bundle")
        @Expose
        private String isProductBundle;
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
        @SerializedName("is_promo")
        @Expose
        private Integer isPromo;
        @SerializedName("is_bundle")
        @Expose
        private Integer isBundle;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("price_rate_room_id")
        @Expose
        private Integer priceRateRoomId;
        @SerializedName("product_alacart_id")
        @Expose
        private Integer productAlacartId;
        @SerializedName("currency_id")
        @Expose
        private Object currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("discounts")
        @Expose
        private List<Object> discounts = null;
        @SerializedName("rate")
        @Expose
        private Rate rate;
        @SerializedName("product")
        @Expose
        private Product product;

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

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public Object getCategory() {
            return category;
        }

        public void setCategory(Object category) {
            this.category = category;
        }

        public Object getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(Object subCategory) {
            this.subCategory = subCategory;
        }

        public Object getDepartment() {
            return department;
        }

        public void setDepartment(Object department) {
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

        public Double getBundlePrice() {
            return bundlePrice;
        }

        public void setBundlePrice(Double bundlePrice) {
            this.bundlePrice = bundlePrice;
        }

        public Double getBundleTotal() {
            return bundleTotal;
        }

        public void setBundleTotal(Double bundleTotal) {
            this.bundleTotal = bundleTotal;
        }

        public Double getBundleDiscount() {
            return bundleDiscount;
        }

        public void setBundleDiscount(Double bundleDiscount) {
            this.bundleDiscount = bundleDiscount;
        }

        public Double getFoodBudgetDiscount() {
            return foodBudgetDiscount;
        }

        public void setFoodBudgetDiscount(Double foodBudgetDiscount) {
            this.foodBudgetDiscount = foodBudgetDiscount;
        }

        public void setVatExempt(Double vatExempt) {
            this.vatExempt = vatExempt;
        }

        public void setVatExemptSales(Double vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
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

        public Double getVatExemptSales() {
            return vatExemptSales;
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

        public String getIsProductBundle() {
            return isProductBundle;
        }

        public void setIsProductBundle(String isProductBundle) {
            this.isProductBundle = isProductBundle;
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

        public Integer getIsPromo() {
            return isPromo;
        }

        public void setIsPromo(Integer isPromo) {
            this.isPromo = isPromo;
        }

        public Integer getIsBundle() {
            return isBundle;
        }

        public void setIsBundle(Integer isBundle) {
            this.isBundle = isBundle;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getPriceRateRoomId() {
            return priceRateRoomId;
        }

        public void setPriceRateRoomId(Integer priceRateRoomId) {
            this.priceRateRoomId = priceRateRoomId;
        }

        public Integer getProductAlacartId() {
            return productAlacartId;
        }

        public void setProductAlacartId(Integer productAlacartId) {
            this.productAlacartId = productAlacartId;
        }

        public Object getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(Object currencyId) {
            this.currencyId = currencyId;
        }

        public Integer getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Integer currencyValue) {
            this.currencyValue = currencyValue;
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

        public List<Object> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<Object> discounts) {
            this.discounts = discounts;
        }

        public Rate getRate() {
            return rate;
        }

        public void setRate(Rate rate) {
            this.rate = rate;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

    public class Rate {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("room_rate_id")
        @Expose
        private Integer roomRateId;
        @SerializedName("currency_id")
        @Expose
        private Integer currencyId;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("xPerson")
        @Expose
        private Integer xPerson;
        @SerializedName("perHour")
        @Expose
        private Double perHour;
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
        @SerializedName("branch_room_rate")
        @Expose
        private BranchRoomRate branchRoomRate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRoomRateId() {
            return roomRateId;
        }

        public void setRoomRateId(Integer roomRateId) {
            this.roomRateId = roomRateId;
        }

        public Integer getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(Integer currencyId) {
            this.currencyId = currencyId;
        }

        public Integer getXPerson() {
            return xPerson;
        }

        public void setXPerson(Integer xPerson) {
            this.xPerson = xPerson;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Integer getxPerson() {
            return xPerson;
        }

        public void setxPerson(Integer xPerson) {
            this.xPerson = xPerson;
        }

        public Double getPerHour() {
            return perHour;
        }

        public void setPerHour(Double perHour) {
            this.perHour = perHour;
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

        public BranchRoomRate getBranchRoomRate() {
            return branchRoomRate;
        }

        public void setBranchRoomRate(BranchRoomRate branchRoomRate) {
            this.branchRoomRate = branchRoomRate;
        }

    }
    public class RateRoom {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("room_rate")
        @Expose
        private String roomRate;
        @SerializedName("minHr")
        @Expose
        private Double minHr;
        @SerializedName("maxHr")
        @Expose
        private Double maxHr;
        @SerializedName("gracePeriod")
        @Expose
        private Double gracePeriod;
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

        public Integer getCoreId() {
            return coreId;
        }

        public void setCoreId(Integer coreId) {
            this.coreId = coreId;
        }

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public Double getMinHr() {
            return minHr;
        }

        public void setMinHr(Double minHr) {
            this.minHr = minHr;
        }

        public Double getMaxHr() {
            return maxHr;
        }

        public void setMaxHr(Double maxHr) {
            this.maxHr = maxHr;
        }

        public Double getGracePeriod() {
            return gracePeriod;
        }

        public void setGracePeriod(Double gracePeriod) {
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
        private String lastCheckout;
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

        public String getLastCheckout() {
            return lastCheckout;
        }

        public void setLastCheckout(String lastCheckout) {
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

    public class RoomBoyIn {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
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
        @SerializedName("remarks")
        @Expose
        private Object remarks;
        @SerializedName("dispatched_by")
        @Expose
        private Object dispatchedBy;
        @SerializedName("dispatched_at")
        @Expose
        private Object dispatchedAt;
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

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getDispatchedBy() {
            return dispatchedBy;
        }

        public void setDispatchedBy(Object dispatchedBy) {
            this.dispatchedBy = dispatchedBy;
        }

        public Object getDispatchedAt() {
            return dispatchedAt;
        }

        public void setDispatchedAt(Object dispatchedAt) {
            this.dispatchedAt = dispatchedAt;
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

    public class RoomBoy {
        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("receipt_no")
        @Expose
        private String receiptNo;
        @SerializedName("guest_info_id")
        @Expose
        private Integer guestInfoId;
        @SerializedName("employee_id")
        @Expose
        private Object employeeId;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("payment_type_id")
        @Expose
        private Integer paymentTypeId;
        @SerializedName("ot_hours")
        @Expose
        private Double otHours;
        @SerializedName("ot_amount")
        @Expose
        private Double otAmount;
        @SerializedName("xPerson_amount")
        @Expose
        private Double xPersonAmount;
        @SerializedName("rc")
        @Expose
        private Double rc;
        @SerializedName("fnb")
        @Expose
        private Double fnb;
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
        @SerializedName("is_soa")
        @Expose
        private Integer isSoa;
        @SerializedName("soa_count")
        @Expose
        private String soaCount;
        @SerializedName("soa_date_time")
        @Expose
        private String soaDateTime;
        @SerializedName("is_checkout")
        @Expose
        private Integer isCheckout;
        @SerializedName("check_out_by")
        @Expose
        private Integer checkOutBy;
        @SerializedName("checked_out_at")
        @Expose
        private String checkedOutAt;
        @SerializedName("room_area_id")
        @Expose
        private Integer roomAreaId;
        @SerializedName("is_foc")
        @Expose
        private Integer isFoc;
        @SerializedName("is_cut_off")
        @Expose
        private Integer isCutOff;
        @SerializedName("cash_and_reconcile_id")
        @Expose
        private Integer cashAndReconcileId;
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
        @SerializedName("vat_exempt_sales")
        @Expose
        private Double vatExemptSales;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("person_count")
        @Expose
        private Double personCount;
        @SerializedName("total_item")
        @Expose
        private Double totalItem;
        @SerializedName("total_qty")
        @Expose
        private Double totalQty;
        @SerializedName("avg_sale_per_head")
        @Expose
        private Double avgSalePerHead;
        @SerializedName("shift_id")
        @Expose
        private Integer shiftId;
        @SerializedName("shift_no")
        @Expose
        private Integer shiftNo;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
        @SerializedName("xSkip")
        @Expose
        private Integer xSkip;
        @SerializedName("room_boy")
        @Expose
        private RoomBoy roomBoy;
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
        @SerializedName("special")
        @Expose
        private Integer special;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("cashier")
        @Expose
        private Cashier cashier;
        @SerializedName("cashier_out")
        @Expose
        private CashierOut cashierOut;
        @SerializedName("guest_info")
        @Expose
        private GuestInfo guestInfo;
        @SerializedName("trans")
        @Expose
        private List<Tran> trans = null;
        @SerializedName("payments")
        @Expose
        private List<Payment> payments = null;
        @SerializedName("discounts")
        @Expose
        private List<VRDiscounts> discounts = null;
        @SerializedName("post")
        @Expose
        private List<Post_> post = null;

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

        public String getReceiptNo() {
            return receiptNo;
        }

        public void setReceiptNo(String receiptNo) {
            this.receiptNo = receiptNo;
        }

        public Integer getGuestInfoId() {
            return guestInfoId;
        }

        public void setGuestInfoId(Integer guestInfoId) {
            this.guestInfoId = guestInfoId;
        }

        public Object getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Object employeeId) {
            this.employeeId = employeeId;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public Integer getPaymentTypeId() {
            return paymentTypeId;
        }

        public void setPaymentTypeId(Integer paymentTypeId) {
            this.paymentTypeId = paymentTypeId;
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

        public Double getxPersonAmount() {
            return xPersonAmount;
        }

        public void setxPersonAmount(Double xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
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

        public void setPersonCount(Double personCount) {
            this.personCount = personCount;
        }

        public void setTotalItem(Double totalItem) {
            this.totalItem = totalItem;
        }

        public void setTotalQty(Double totalQty) {
            this.totalQty = totalQty;
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

        public Integer getIsSoa() {
            return isSoa;
        }

        public void setIsSoa(Integer isSoa) {
            this.isSoa = isSoa;
        }

        public String getSoaCount() {
            return soaCount;
        }

        public void setSoaCount(String soaCount) {
            this.soaCount = soaCount;
        }

        public String getSoaDateTime() {
            return soaDateTime;
        }

        public void setSoaDateTime(String soaDateTime) {
            this.soaDateTime = soaDateTime;
        }

        public Integer getIsCheckout() {
            return isCheckout;
        }

        public void setIsCheckout(Integer isCheckout) {
            this.isCheckout = isCheckout;
        }

        public Integer getCheckOutBy() {
            return checkOutBy;
        }

        public void setCheckOutBy(Integer checkOutBy) {
            this.checkOutBy = checkOutBy;
        }

        public String getCheckedOutAt() {
            return checkedOutAt;
        }

        public void setCheckedOutAt(String checkedOutAt) {
            this.checkedOutAt = checkedOutAt;
        }

        public Integer getRoomAreaId() {
            return roomAreaId;
        }

        public void setRoomAreaId(Integer roomAreaId) {
            this.roomAreaId = roomAreaId;
        }

        public Integer getIsFoc() {
            return isFoc;
        }

        public void setIsFoc(Integer isFoc) {
            this.isFoc = isFoc;
        }

        public Integer getIsCutOff() {
            return isCutOff;
        }

        public void setIsCutOff(Integer isCutOff) {
            this.isCutOff = isCutOff;
        }

        public Integer getCashAndReconcileId() {
            return cashAndReconcileId;
        }

        public void setCashAndReconcileId(Integer cashAndReconcileId) {
            this.cashAndReconcileId = cashAndReconcileId;
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

        public Double getVatExemptSales() {
            return vatExemptSales;
        }

        public void setVatExemptSales(Double vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public Double getPersonCount() {
            return personCount;
        }

        public Double getTotalItem() {
            return totalItem;
        }

        public Double getTotalQty() {
            return totalQty;
        }

        public Double getAvgSalePerHead() {
            return avgSalePerHead;
        }

        public void setAvgSalePerHead(Double avgSalePerHead) {
            this.avgSalePerHead = avgSalePerHead;
        }

        public Integer getShiftId() {
            return shiftId;
        }

        public void setShiftId(Integer shiftId) {
            this.shiftId = shiftId;
        }

        public Integer getShiftNo() {
            return shiftNo;
        }

        public void setShiftNo(Integer shiftNo) {
            this.shiftNo = shiftNo;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public Integer getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Integer currencyValue) {
            this.currencyValue = currencyValue;
        }

        public Integer getXSkip() {
            return xSkip;
        }

        public void setXSkip(Integer xSkip) {
            this.xSkip = xSkip;
        }

        public RoomBoy getRoomBoy() {
            return roomBoy;
        }

        public void setRoomBoy(RoomBoy roomBoy) {
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

        public Integer getSpecial() {
            return special;
        }

        public void setSpecial(Integer special) {
            this.special = special;
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

        public Cashier getCashier() {
            return cashier;
        }

        public void setCashier(Cashier cashier) {
            this.cashier = cashier;
        }

        public CashierOut getCashierOut() {
            return cashierOut;
        }

        public void setCashierOut(CashierOut cashierOut) {
            this.cashierOut = cashierOut;
        }

        public GuestInfo getGuestInfo() {
            return guestInfo;
        }

        public void setGuestInfo(GuestInfo guestInfo) {
            this.guestInfo = guestInfo;
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

        public List<VRDiscounts> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<VRDiscounts> discounts) {
            this.discounts = discounts;
        }

        public List<Post_> getPost() {
            return post;
        }

        public void setPost(List<Post_> post) {
            this.post = post;
        }

    }

    public class VRDiscounts {
        @SerializedName("discount_type")
        @Expose
        private String discountType;
        @SerializedName("info")
        @Expose
        private VRInfo vrInfo;

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public VRInfo getVrInfo() {
            return vrInfo;
        }

        public void setVrInfo(VRInfo vrInfo) {
            this.vrInfo = vrInfo;
        }
    }

    public class VRInfo {
        @SerializedName("card_no")
        @Expose
        private String cardNo;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("tin")
        @Expose
        private String tin;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("business_style")
        @Expose
        private String businessStyle;

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getBusinessStyle() {
            return businessStyle;
        }

        public void setBusinessStyle(String businessStyle) {
            this.businessStyle = businessStyle;
        }
    }

    public class BranchRoomRate {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("room_rate")
        @Expose
        private String roomRate;
        @SerializedName("minHr")
        @Expose
        private Double minHr;
        @SerializedName("maxHr")
        @Expose
        private Double maxHr;
        @SerializedName("gracePeriod")
        @Expose
        private Double gracePeriod;
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

        public Integer getCoreId() {
            return coreId;
        }

        public void setCoreId(Integer coreId) {
            this.coreId = coreId;
        }

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }


        public Double getMinHr() {
            return minHr;
        }

        public void setMinHr(Double minHr) {
            this.minHr = minHr;
        }

        public Double getMaxHr() {
            return maxHr;
        }

        public void setMaxHr(Double maxHr) {
            this.maxHr = maxHr;
        }

        public Double getGracePeriod() {
            return gracePeriod;
        }

        public void setGracePeriod(Double gracePeriod) {
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

    public class Cashier {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
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

    public class CashierIn {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
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

    public class CashierOut {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
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

    public class CashierOut_ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("role_id")
        @Expose
        private Integer roleId;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("customer")
        @Expose
        private String customer;
        @SerializedName("tin")
        @Expose
        private Object tin;
        @SerializedName("address")
        @Expose
        private Object address;
        @SerializedName("business_style")
        @Expose
        private Object businessStyle;
        @SerializedName("nationality_id")
        @Expose
        private Integer nationalityId;
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

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public Object getTin() {
            return tin;
        }

        public void setTin(Object tin) {
            this.tin = tin;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getBusinessStyle() {
            return businessStyle;
        }

        public void setBusinessStyle(Object businessStyle) {
            this.businessStyle = businessStyle;
        }

        public Integer getNationalityId() {
            return nationalityId;
        }

        public void setNationalityId(Integer nationalityId) {
            this.nationalityId = nationalityId;
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



    public class BranchNationality {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("nationality")
        @Expose
        private String nationality;
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

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
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
