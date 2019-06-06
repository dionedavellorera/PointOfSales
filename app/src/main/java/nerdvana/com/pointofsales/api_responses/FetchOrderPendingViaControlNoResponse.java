package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchOrderPendingViaControlNoResponse {

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

        public String getKitchenPath() {
            return kitchenPath;
        }

        public void setKitchenPath(String kitchenPath) {
            this.kitchenPath = kitchenPath;
        }
    }

    public class CardDetail {
        @SerializedName("credit_card_id")
        @Expose
        private String creditCardId;
        @SerializedName("card_number")
        @Expose
        private String cardNumber;

        public String getCreditCardId() {
            return creditCardId;
        }

        public void setCreditCardId(String creditCardId) {
            this.creditCardId = creditCardId;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
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
        @SerializedName("currency")
        @Expose
        private Currency currency;
        @SerializedName("card_detail")
        @Expose
        private CardDetail cardDetail;

        public CardDetail getCardDetail() {
            return cardDetail;
        }

        public void setCardDetail(CardDetail cardDetail) {
            this.cardDetail = cardDetail;
        }

        public Currency getCurrency() {
            return currency;
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
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


    public class Post {
        @SerializedName("group")
        @Expose
        private List<FetchRoomPendingResponse.PostGroup> postGroupList;
        @SerializedName("alacart")
        @Expose
        private List<FetchRoomPendingResponse.PostAlaCart> postAlaCartList;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("room_id")
        @Expose
        private Object roomId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("room_type_id")
        @Expose
        private Object roomTypeId;
        @SerializedName("room_rate_id")
        @Expose
        private Object roomRateId;
        @SerializedName("room_rate_price_id")
        @Expose
        private Object roomRatePriceId;
        @SerializedName("category_id")
        @Expose
        private Object categoryId;
        @SerializedName("department_id")
        @Expose
        private Object departmentId;
        @SerializedName("room_no")
        @Expose
        private Object roomNo;
        @SerializedName("room_type")
        @Expose
        private Object roomType;
        @SerializedName("room_rate")
        @Expose
        private Object roomRate;
        @SerializedName("category")
        @Expose
        private String category;
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
        private Product product;

        public List<FetchRoomPendingResponse.PostGroup> getPostGroupList() {
            return postGroupList;
        }

        public void setPostGroupList(List<FetchRoomPendingResponse.PostGroup> postGroupList) {
            this.postGroupList = postGroupList;
        }

        public List<FetchRoomPendingResponse.PostAlaCart> getPostAlaCartList() {
            return postAlaCartList;
        }

        public void setPostAlaCartList(List<FetchRoomPendingResponse.PostAlaCart> postAlaCartList) {
            this.postAlaCartList = postAlaCartList;
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

        public Object getRoomId() {
            return roomId;
        }

        public void setRoomId(Object roomId) {
            this.roomId = roomId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Object getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(Object roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public Object getRoomRateId() {
            return roomRateId;
        }

        public void setRoomRateId(Object roomRateId) {
            this.roomRateId = roomRateId;
        }

        public Object getRoomRatePriceId() {
            return roomRatePriceId;
        }

        public void setRoomRatePriceId(Object roomRatePriceId) {
            this.roomRatePriceId = roomRatePriceId;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public Object getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Object departmentId) {
            this.departmentId = departmentId;
        }

        public Object getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(Object roomNo) {
            this.roomNo = roomNo;
        }

        public Object getRoomType() {
            return roomType;
        }

        public void setRoomType(Object roomType) {
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

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
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

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
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

    public class Cashier {
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
        @SerializedName("role_id")
        @Expose
        private String roleId;
        @SerializedName("group_id")
        @Expose
        private String groupId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
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

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }

    public class Info {
        @SerializedName("card_no")
        @Expose
        private String cardNo;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCardNo() {
            return cardNo;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }
    }



    public class Discounts {
        @SerializedName("info")
        @Expose
        private Info info;
        @SerializedName("void_by")
        @Expose
        private String void_by;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("discount_type")
        @Expose
        private String discountType;
        @SerializedName("discount_amount")
        @Expose
        private Double discountAmount;

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public String getVoid_by() {
            return void_by;
        }

        public void setVoid_by(String void_by) {
            this.void_by = void_by;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public Double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Double discountAmount) {
            this.discountAmount = discountAmount;
        }
    }


    public class RoomBoy {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;

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
    }

    public class GuestInfo {
        @SerializedName("room_id")
        @Expose
        private String roomId;
        @SerializedName("room_type")
        @Expose
        private String roomType;
        @SerializedName("room_rate")
        @Expose
        private String roomRate;
        @SerializedName("room_rate_price_id")
        @Expose
        private String roomRatePriceId;
        @SerializedName("car_id")
        @Expose
        private String carId;
        @SerializedName("vehicle_id")
        @Expose
        private String vehicleId;
        @SerializedName("guest_type_id")
        @Expose
        private String guestTypeId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("nationality_id")
        @Expose
        private String nationalityId;
        @SerializedName("room_no")
        @Expose
        private String roomNo;
        @SerializedName("adult")
        @Expose
        private String adult;
        @SerializedName("child")
        @Expose
        private String child;
        @SerializedName("plate_no")
        @Expose
        private String plateNo;
        @SerializedName("steward")
        @Expose
        private String steward;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("checkOut_by")
        @Expose
        private String checkOutBy;
        @SerializedName("checkIn")
        @Expose
        private String checkIn;
        @SerializedName("wake_up_call")
        @Expose
        private String wakeUpCall;
        @SerializedName("expected_checkOut")
        @Expose
        private String expectedCheckout;
        @SerializedName("outCheck_by")
        @Expose
        private String outCheckBy;
        @SerializedName("checkOut")
        @Expose
        private String checkOut;
        @SerializedName("room_boy_in")
        @Expose
        private RoomBoy roomBoy;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getAdult() {
            return adult;
        }

        public void setAdult(String adult) {
            this.adult = adult;
        }

        public String getChild() {
            return child;
        }

        public void setChild(String child) {
            this.child = child;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getSteward() {
            return steward;
        }

        public void setSteward(String steward) {
            this.steward = steward;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCheckOutBy() {
            return checkOutBy;
        }

        public void setCheckOutBy(String checkOutBy) {
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

        public String getExpectedCheckout() {
            return expectedCheckout;
        }

        public void setExpectedCheckout(String expectedCheckout) {
            this.expectedCheckout = expectedCheckout;
        }

        public String getOutCheckBy() {
            return outCheckBy;
        }

        public void setOutCheckBy(String outCheckBy) {
            this.outCheckBy = outCheckBy;
        }

        public String getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(String checkOut) {
            this.checkOut = checkOut;
        }

        public RoomBoy getRoomBoy() {
            return roomBoy;
        }

        public void setRoomBoy(RoomBoy roomBoy) {
            this.roomBoy = roomBoy;
        }

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public String getRoomRatePriceId() {
            return roomRatePriceId;
        }

        public void setRoomRatePriceId(String roomRatePriceId) {
            this.roomRatePriceId = roomRatePriceId;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getGuestTypeId() {
            return guestTypeId;
        }

        public void setGuestTypeId(String guestTypeId) {
            this.guestTypeId = guestTypeId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getNationalityId() {
            return nationalityId;
        }

        public void setNationalityId(String nationalityId) {
            this.nationalityId = nationalityId;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }
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

    public class Result {
        @SerializedName("area")
        @Expose
        private Area area;
        @SerializedName("customer")
        @Expose
        private Customer customer;
        @SerializedName("employee")
        @Expose
        private Employee employee;
        @SerializedName("guest_info")
        @Expose
        private GuestInfo guestInfo;
        @SerializedName("discounts")
        @Expose
        private List<Discounts> discountsList;
        @SerializedName("cashier")
        @Expose
        private Cashier cashier;
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
        private Object guestInfoId;
        @SerializedName("employee_id")
        @Expose
        private Object employeeId;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("payment_type_id")
        @Expose
        private Integer paymentTypeId;
        @SerializedName("rc")
        @Expose
        private Double rc;
        @SerializedName("fnb")
        @Expose
        private Double fnb;
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
        @SerializedName("is_soa")
        @Expose
        private Integer isSoa;
        @SerializedName("is_checkout")
        @Expose
        private Integer isCheckout;
        @SerializedName("room_area_id")
        @Expose
        private Object roomAreaId;
        @SerializedName("is_foc")
        @Expose
        private Integer isFoc;
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
        private List<Post> post = null;
        @SerializedName("payments")
        @Expose
        private List<Payment> payments = null;
        @SerializedName("trans")
        @Expose
        private List<Tran> trans = null;
        @SerializedName("vat_exempt_sales")
        @Expose
        private Double vatExemptSales;

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }

        public Employee getEmployee() {
            return employee;
        }

        public Double getVatExemptSales() {
            return vatExemptSales;
        }

        public void setVatExemptSales(Double vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
        }

        public GuestInfo getGuestInfo() {
            return guestInfo;
        }

        public void setGuestInfo(GuestInfo guestInfo) {
            this.guestInfo = guestInfo;
        }

        public List<Discounts> getDiscountsList() {
            return discountsList;
        }

        public void setDiscountsList(List<Discounts> discountsList) {
            this.discountsList = discountsList;
        }

        public Cashier getCashier() {
            return cashier;
        }

        public void setCashier(Cashier cashier) {
            this.cashier = cashier;
        }

        public Double getxPersonAmount() {
            return xPersonAmount;
        }

        public void setxPersonAmount(Double xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
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

        public List<Tran> getTrans() {
            return trans;
        }

        public void setTrans(List<Tran> trans) {
            this.trans = trans;
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

        public Object getGuestInfoId() {
            return guestInfoId;
        }

        public void setGuestInfoId(Object guestInfoId) {
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

        public Integer getIsSoa() {
            return isSoa;
        }

        public void setIsSoa(Integer isSoa) {
            this.isSoa = isSoa;
        }

        public Integer getIsCheckout() {
            return isCheckout;
        }

        public void setIsCheckout(Integer isCheckout) {
            this.isCheckout = isCheckout;
        }

        public Object getRoomAreaId() {
            return roomAreaId;
        }

        public void setRoomAreaId(Object roomAreaId) {
            this.roomAreaId = roomAreaId;
        }

        public Integer getIsFoc() {
            return isFoc;
        }

        public void setIsFoc(Integer isFoc) {
            this.isFoc = isFoc;
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

        public List<Post> getPost() {
            return post;
        }

        public void setPost(List<Post> post) {
            this.post = post;
        }

        public List<Payment> getPayments() {
            return payments;
        }

        public void setPayments(List<Payment> payments) {
            this.payments = payments;
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





}
