package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrintSoaResponse {
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
        @SerializedName("dicount_type")
        @Expose
        private Object dicountType;
        @SerializedName("discount_percent")
        @Expose
        private Object discountPercent;
        @SerializedName("discount_details")
        @Expose
        private Object discountDetails;
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
        private Integer voidedBy;
        @SerializedName("is_open_price")
        @Expose
        private Integer isOpenPrice;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Double currencyValue;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("rate")
        @Expose
        private Rate rate;
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

        public Object getDicountType() {
            return dicountType;
        }

        public void setDicountType(Object dicountType) {
            this.dicountType = dicountType;
        }

        public Object getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(Object discountPercent) {
            this.discountPercent = discountPercent;
        }

        public Object getDiscountDetails() {
            return discountDetails;
        }

        public void setDiscountDetails(Object discountDetails) {
            this.discountDetails = discountDetails;
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

        public Integer getVoidedBy() {
            return voidedBy;
        }

        public void setVoidedBy(Integer voidedBy) {
            this.voidedBy = voidedBy;
        }

        public Integer getIsOpenPrice() {
            return isOpenPrice;
        }

        public void setIsOpenPrice(Integer isOpenPrice) {
            this.isOpenPrice = isOpenPrice;
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
        private Object productBarcode;
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

        public Object getProductBarcode() {
            return productBarcode;
        }

        public void setProductBarcode(Object productBarcode) {
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
        private Double xPerson;
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

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Double getXPerson() {
            return xPerson;
        }

        public void setXPerson(Double xPerson) {
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

    }

    public class ToProduct {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("core_id")
        @Expose
        private String coreId;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String productInitial;
        @SerializedName("product_barcode")
        @Expose
        private String productBarcode;
        @SerializedName("image_file")
        @Expose
        private String imageFile;
        @SerializedName("is_promo")
        @Expose
        private String isPromo;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("mark_up")
        @Expose
        private String markUp;

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

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }

        public String getIsPromo() {
            return isPromo;
        }

        public void setIsPromo(String isPromo) {
            this.isPromo = isPromo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getMarkUp() {
            return markUp;
        }

        public void setMarkUp(String markUp) {
            this.markUp = markUp;
        }
    }

    public class SoaToPost {
        @SerializedName("group")
        @Expose
        private List<FetchRoomPendingResponse.PostGroup> postGroupList;
        @SerializedName("alacart")
        @Expose
        private List<FetchRoomPendingResponse.PostAlaCart> postAlaCartList;
        @SerializedName("control_no")
        @Expose
        private String controlNo;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("unit_cost")
        @Expose
        private String unitCode;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("tax_info")
        @Expose
        private String taxInfo;
        @SerializedName("vatable")
        @Expose
        private String vatable;
        @SerializedName("vat")
        @Expose
        private String vat;
        @SerializedName("vat_exempt")
        @Expose
        private String vatExempt;
        @SerializedName("is_posted")
        @Expose
        private String isPosted;
        @SerializedName("is_product_bundle")
        @Expose
        private String isProductBundle;
        @SerializedName("void")
        @Expose
        private String voidd;
        @SerializedName("voided_by")
        @Expose
        private String voidedBy;
        @SerializedName("is_open_price")
        @Expose
        private String isOpenPrice;
        @SerializedName("product")
        @Expose
        private ToProduct toProduct;

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

        public ToProduct getToProduct() {
            return toProduct;
        }

        public void setToProduct(ToProduct toProduct) {
            this.toProduct = toProduct;
        }

        public String getControlNo() {
            return controlNo;
        }

        public void setControlNo(String controlNo) {
            this.controlNo = controlNo;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getUnitCode() {
            return unitCode;
        }

        public void setUnitCode(String unitCode) {
            this.unitCode = unitCode;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTaxInfo() {
            return taxInfo;
        }

        public void setTaxInfo(String taxInfo) {
            this.taxInfo = taxInfo;
        }

        public String getVatable() {
            return vatable;
        }

        public void setVatable(String vatable) {
            this.vatable = vatable;
        }

        public String getVat() {
            return vat;
        }

        public void setVat(String vat) {
            this.vat = vat;
        }

        public String getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(String vatExempt) {
            this.vatExempt = vatExempt;
        }

        public String getIsPosted() {
            return isPosted;
        }

        public void setIsPosted(String isPosted) {
            this.isPosted = isPosted;
        }

        public String getIsProductBundle() {
            return isProductBundle;
        }

        public void setIsProductBundle(String isProductBundle) {
            this.isProductBundle = isProductBundle;
        }

        public String getVoidd() {
            return voidd;
        }

        public void setVoidd(String voidd) {
            this.voidd = voidd;
        }

        public String getVoidedBy() {
            return voidedBy;
        }

        public void setVoidedBy(String voidedBy) {
            this.voidedBy = voidedBy;
        }

        public String getIsOpenPrice() {
            return isOpenPrice;
        }

        public void setIsOpenPrice(String isOpenPrice) {
            this.isOpenPrice = isOpenPrice;
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
        @SerializedName("vat_exempt_sales")
        @Expose
        private Double vatExemptSales;
        @SerializedName("customer")
        @Expose
        private Customer customer;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("control_no")
        @Expose
        private String controlNumber;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("advance")
        @Expose
        private String advance;
        @SerializedName("tendered")
        @Expose
        private String tendered;
        @SerializedName("change")
        @Expose
        private String change;
        @SerializedName("is_soa")
        @Expose
        private String isSoa;
        @SerializedName("soa_count")
        @Expose
        private String soaCount;
        @SerializedName("is_foc")
        @Expose
        private String isFoc;
        @SerializedName("vatable")
        @Expose
        private String vatable;
        @SerializedName("vat")
        @Expose
        private String vat;
        @SerializedName("vat_exempt")
        @Expose
        private String vatExempt;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private String currencyValue;
        @SerializedName("pos_id")
        @Expose
        private String posId;
        @SerializedName("void")
        @Expose
        private String voidd;
        @SerializedName("post")
        @Expose
        private List<SoaToPost> toPostList;

        public Double getVatExemptSales() {
            return vatExemptSales;
        }

        public void setVatExemptSales(Double vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public List<SoaToPost> getToPostList() {
            return toPostList;
        }

        public void setToPostList(List<SoaToPost> toPostList) {
            this.toPostList = toPostList;
        }

        public String getControlNumber() {
            return controlNumber;
        }

        public void setControlNumber(String controlNumber) {
            this.controlNumber = controlNumber;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAdvance() {
            return advance;
        }

        public void setAdvance(String advance) {
            this.advance = advance;
        }

        public String getTendered() {
            return tendered;
        }

        public void setTendered(String tendered) {
            this.tendered = tendered;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getIsSoa() {
            return isSoa;
        }

        public void setIsSoa(String isSoa) {
            this.isSoa = isSoa;
        }

        public String getSoaCount() {
            return soaCount;
        }

        public void setSoaCount(String soaCount) {
            this.soaCount = soaCount;
        }

        public String getIsFoc() {
            return isFoc;
        }

        public void setIsFoc(String isFoc) {
            this.isFoc = isFoc;
        }

        public String getVatable() {
            return vatable;
        }

        public void setVatable(String vatable) {
            this.vatable = vatable;
        }

        public String getVat() {
            return vat;
        }

        public void setVat(String vat) {
            this.vat = vat;
        }

        public String getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(String vatExempt) {
            this.vatExempt = vatExempt;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public String getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(String currencyValue) {
            this.currencyValue = currencyValue;
        }

        public String getPosId() {
            return posId;
        }

        public void setPosId(String posId) {
            this.posId = posId;
        }

        public String getVoidd() {
            return voidd;
        }

        public void setVoidd(String voidd) {
            this.voidd = voidd;
        }

        @SerializedName("booked")
        @Expose
        private List<Booked> booked = null;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("time")
        @Expose
        private Object time;

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

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
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

    public class DiscountReason {
        @SerializedName("discount_reason")
        @Expose
        private String discountReason;

        public String getDiscountReason() {
            return discountReason;
        }

        public void setDiscountReason(String discountReason) {
            this.discountReason = discountReason;
        }
    }

    public class Discounts {
        @SerializedName("discount_type")
        @Expose
        private String discountType;

        @SerializedName("discount_type_id")
        @Expose
        private String discountTypeId;
        @SerializedName("info")
        @Expose
        private Info info;
        @SerializedName("discount_reason")
        @Expose
        private DiscountReason discountReason;

        public DiscountReason getDiscountReason() {
            return discountReason;
        }

        public void setDiscountReason(DiscountReason discountReason) {
            this.discountReason = discountReason;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public void setDiscountTypeId(String discountTypeId) {
            this.discountTypeId = discountTypeId;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public Info getInfo() {
            return info;
        }

        public String getDiscountType() {
            return discountType;
        }

        public String getDiscountTypeId() {
            return discountTypeId;
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

    public class Transaction {
        @SerializedName("customer")
        @Expose
        private CustomerTrans customerTrans;
        @SerializedName("soa_count")
        @Expose
        private String soaCount;
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
        @SerializedName("employee_id")
        @Expose
        private Object employeeId;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("payment_type_id")
        @Expose
        private Object paymentTypeId;
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
        private Integer roomAreaId;
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
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Double currencyValue;
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

        @SerializedName("discounts")
        @Expose
        private List<Discounts> discounts;

        public CustomerTrans getCustomerTrans() {
            return customerTrans;
        }

        public void setCustomerTrans(CustomerTrans customerTrans) {
            this.customerTrans = customerTrans;
        }

        public void setDiscounts(List<Discounts> discounts) {
            this.discounts = discounts;
        }

        public List<Discounts> getDiscounts() {
            return discounts;
        }

        public String getSoaCount() {
            return soaCount;
        }

        public void setSoaCount(String soaCount) {
            this.soaCount = soaCount;
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

        public Object getPaymentTypeId() {
            return paymentTypeId;
        }

        public void setPaymentTypeId(Object paymentTypeId) {
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

    }

    public class Area {
        @SerializedName("room_area")
        @Expose
        private String roomArea;
        @SerializedName("printer_path")
        @Expose
        private String printerPath;

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

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }
    }

    public class Booked {
        @SerializedName("room")
        @Expose
        private Room room;
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
        @SerializedName("outCheck_by")
        @Expose
        private Integer outCheckBy;
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
        @SerializedName("transaction")
        @Expose
        private Transaction transaction;

        public Room getRoom() {
            return room;
        }

        public void setRoom(Room room) {
            this.room = room;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
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

        public Integer getOutCheckBy() {
            return outCheckBy;
        }

        public void setOutCheckBy(Integer outCheckBy) {
            this.outCheckBy = outCheckBy;
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

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

    }


}
