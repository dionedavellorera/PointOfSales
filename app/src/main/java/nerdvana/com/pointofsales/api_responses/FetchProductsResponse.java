package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchProductsResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class BranchProduct {
        @SerializedName("branch_price")
        @Expose
        private BranchPrice branchPrice;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("core_id")
        @Expose
        private int coreId;
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
        private int qty;
        @SerializedName("tax_rate")
        @Expose
        private double taxRate;
        @SerializedName("min")
        @Expose
        private int min;
        @SerializedName("image_file")
        @Expose
        private String imageFile;
        @SerializedName("is_available")
        @Expose
        private int isAvailable;
        @SerializedName("is_fixed_asset")
        @Expose
        private int isFixedAsset;
        @SerializedName("is_raw")
        @Expose
        private int isRaw;
        @SerializedName("is_promo")
        @Expose
        private int isPromo;
        @SerializedName("flag")
        @Expose
        private int flag;
        @SerializedName("price_id")
        @Expose
        private int price_id;
        @SerializedName("amount")
        @Expose
        private double amount;
        @SerializedName("mark_up")
        @Expose
        private double markUp;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deletedAt")
        @Expose
        private String deletedAt;

        public BranchPrice getBranchPrice() {
            return branchPrice;
        }

        public void setBranchPrice(BranchPrice branchPrice) {
            this.branchPrice = branchPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCoreId() {
            return coreId;
        }

        public void setCoreId(int coreId) {
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

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public double getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(double taxRate) {
            this.taxRate = taxRate;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public String getImageFile() {
            return imageFile;
        }

        public void setImageFile(String imageFile) {
            this.imageFile = imageFile;
        }

        public int getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(int isAvailable) {
            this.isAvailable = isAvailable;
        }

        public int getIsFixedAsset() {
            return isFixedAsset;
        }

        public void setIsFixedAsset(int isFixedAsset) {
            this.isFixedAsset = isFixedAsset;
        }

        public int getIsRaw() {
            return isRaw;
        }

        public void setIsRaw(int isRaw) {
            this.isRaw = isRaw;
        }

        public int getIsPromo() {
            return isPromo;
        }

        public void setIsPromo(int isPromo) {
            this.isPromo = isPromo;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getPrice_id() {
            return price_id;
        }

        public void setPrice_id(int price_id) {
            this.price_id = price_id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getMarkUp() {
            return markUp;
        }

        public void setMarkUp(double markUp) {
            this.markUp = markUp;
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

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }
    }


    public class BranchAlaCart {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("product_id")
        @Expose
        private int productId;
        @SerializedName("product_alacart_id")
        @Expose
        private int productAlacarId;
        @SerializedName("currency_id")
        @Expose
        private int currencyId;
        @SerializedName("qty")
        @Expose
        private int qty;
        @SerializedName("price")
        @Expose
        private double price;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deletedAt")
        @Expose
        private String deletedAt;
        @SerializedName("branch_product")
        @Expose
        private BranchProduct branchProduct;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getProductAlacarId() {
            return productAlacarId;
        }

        public void setProductAlacarId(int productAlacarId) {
            this.productAlacarId = productAlacarId;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }

        public BranchProduct getBranchProduct() {
            return branchProduct;
        }

        public void setBranchProduct(BranchProduct branchProduct) {
            this.branchProduct = branchProduct;
        }
    }

    public class BranchList {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("product_group_id")
        @Expose
        private int productGroupId;
        @SerializedName("currency_id")
        @Expose
        private int currencyId;
        @SerializedName("price")
        @Expose
        private double Price;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deletedAt")
        @Expose
        private String deletedAt;
        @SerializedName("branch_product")
        @Expose
        private BranchProduct branchProduct;

        public BranchProduct getBranchProduct() {
            return branchProduct;
        }

        public void setBranchProduct(BranchProduct branchProduct) {
            this.branchProduct = branchProduct;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductGroupId() {
            return productGroupId;
        }

        public void setProductGroupId(int productGroupId) {
            this.productGroupId = productGroupId;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
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

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }
    }

    public class BranchGroup {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("core_id")
        @Expose
        private int coreId;
        @SerializedName("product_id")
        @Expose
        private int productId;
        @SerializedName("group_name")
        @Expose
        private String groupName;
        @SerializedName("currency_id")
        @Expose
        private int currencyId;
        @SerializedName("qty")
        @Expose
        private int qty;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deletedAt")
        @Expose
        private String deletedAt;
        @SerializedName("branch_list")
        @Expose
        private List<BranchList> branchLists;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCoreId() {
            return coreId;
        }

        public void setCoreId(int coreId) {
            this.coreId = coreId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
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

        public String getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(String deletedAt) {
            this.deletedAt = deletedAt;
        }

        public List<BranchList> getBranchLists() {
            return branchLists;
        }

        public void setBranchLists(List<BranchList> branchLists) {
            this.branchLists = branchLists;
        }
    }

    public class Result {
        @SerializedName("branch_group")
        @Expose
        private List<BranchGroup> branchGroupList;
        @SerializedName("branch_alacart")
        @Expose
        private List<BranchAlaCart> branchAlaCartList;
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
        @SerializedName("branch_price")
        @Expose
        private BranchPrice branchPrice;
        @SerializedName("branch_categories")
        @Expose
        private List<BranchCategory> branchCategories;
        @SerializedName("branch_departments")
        @Expose
        private List<BranchDepartment> branchDepartments;

        public List<BranchGroup> getBranchGroupList() {
            return branchGroupList;
        }

        public void setBranchGroupList(List<BranchGroup> branchGroupList) {
            this.branchGroupList = branchGroupList;
        }

        public List<BranchAlaCart> getBranchAlaCartList() {
            return branchAlaCartList;
        }

        public void setBranchAlaCartList(List<BranchAlaCart> branchAlaCartList) {
            this.branchAlaCartList = branchAlaCartList;
        }

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

        public BranchPrice getBranchPrice() {
            return branchPrice;
        }

        public void setBranchPrice(BranchPrice branchPrice) {
            this.branchPrice = branchPrice;
        }

        public List<BranchCategory> getBranchCategories() {
            return branchCategories;
        }

        public void setBranchCategories(List<BranchCategory> branchCategories) {
            this.branchCategories = branchCategories;
        }

        public List<BranchDepartment> getBranchDepartments() {
            return branchDepartments;
        }

        public void setBranchDepartments(List<BranchDepartment> branchDepartments) {
            this.branchDepartments = branchDepartments;
        }

    }

    public class BranchCategory {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("category_id")
        @Expose
        private Integer categoryId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
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
        @SerializedName("branch_category")
        @Expose
        private BranchCategoryInner branchCategory;

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

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
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

        public BranchCategoryInner getBranchCategory() {
            return branchCategory;
        }

        public void setBranchCategory(BranchCategoryInner branchCategory) {
            this.branchCategory = branchCategory;
        }

    }

    public class BranchCategoryInner {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("category_id")
        @Expose
        private Object categoryId;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
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

    public class BranchDepartment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("department_id")
        @Expose
        private Integer departmentId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
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
        @SerializedName("branch_department")
        @Expose
        private BranchDepartmentInner branchDepartment;

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

        public Integer getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
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

        public BranchDepartmentInner getBranchDepartment() {
            return branchDepartment;
        }

        public void setBranchDepartment(BranchDepartmentInner branchDepartment) {
            this.branchDepartment = branchDepartment;
        }

    }

    public class BranchDepartmentInner {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("core_id")
        @Expose
        private Integer coreId;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
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

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
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

    public class BranchPrice {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("currency_id")
        @Expose
        private Integer currencyId;
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

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
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
