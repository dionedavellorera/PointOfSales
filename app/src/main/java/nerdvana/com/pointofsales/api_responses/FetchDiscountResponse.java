package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchDiscountResponse {

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

    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private Object code;
        @SerializedName("discount_card")
        @Expose
        private String discountCard;
        @SerializedName("sDate")
        @Expose
        private Object sDate;
        @SerializedName("eDate")
        @Expose
        private Object eDate;
        @SerializedName("is_custom_discount")
        @Expose
        private Integer isCustomDiscount;
        @SerializedName("is_card")
        @Expose
        private Integer isCard;
        @SerializedName("is_employee")
        @Expose
        private Integer isEmployee;
        @SerializedName("is_special")
        @Expose
        private Integer isSpecial;
        @SerializedName("extra_hrs")
        @Expose
        private Integer extraHrs;
        @SerializedName("food_budget")
        @Expose
        private Integer foodBudget;
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
        @SerializedName("discount_settings")
        @Expose
        private List<DiscountSetting> discountSettings = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public String getDiscountCard() {
            return discountCard;
        }

        public void setDiscountCard(String discountCard) {
            this.discountCard = discountCard;
        }

        public Object getSDate() {
            return sDate;
        }

        public void setSDate(Object sDate) {
            this.sDate = sDate;
        }

        public Object getEDate() {
            return eDate;
        }

        public void setEDate(Object eDate) {
            this.eDate = eDate;
        }

        public Integer getIsCustomDiscount() {
            return isCustomDiscount;
        }

        public void setIsCustomDiscount(Integer isCustomDiscount) {
            this.isCustomDiscount = isCustomDiscount;
        }

        public Integer getIsCard() {
            return isCard;
        }

        public void setIsCard(Integer isCard) {
            this.isCard = isCard;
        }

        public Integer getIsEmployee() {
            return isEmployee;
        }

        public void setIsEmployee(Integer isEmployee) {
            this.isEmployee = isEmployee;
        }

        public Integer getIsSpecial() {
            return isSpecial;
        }

        public void setIsSpecial(Integer isSpecial) {
            this.isSpecial = isSpecial;
        }

        public Integer getExtraHrs() {
            return extraHrs;
        }

        public void setExtraHrs(Integer extraHrs) {
            this.extraHrs = extraHrs;
        }

        public Integer getFoodBudget() {
            return foodBudget;
        }

        public void setFoodBudget(Integer foodBudget) {
            this.foodBudget = foodBudget;
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

        public List<DiscountSetting> getDiscountSettings() {
            return discountSettings;
        }

        public void setDiscountSettings(List<DiscountSetting> discountSettings) {
            this.discountSettings = discountSettings;
        }

    }

    public class DiscountSetting {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("discount_type_id")
        @Expose
        private Integer discountTypeId;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("department_id")
        @Expose
        private String departmentId;
        @SerializedName("room_type_id")
        @Expose
        private Object roomTypeId;
        @SerializedName("room_rate_id")
        @Expose
        private Object roomRateId;
        @SerializedName("percentage")
        @Expose
        private Integer percentage;
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

        public Integer getDiscountTypeId() {
            return discountTypeId;
        }

        public void setDiscountTypeId(Integer discountTypeId) {
            this.discountTypeId = discountTypeId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
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

        public Integer getPercentage() {
            return percentage;
        }

        public void setPercentage(Integer percentage) {
            this.percentage = percentage;
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


}
