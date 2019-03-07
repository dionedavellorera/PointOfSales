package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchDefaultCurrenyResponse {

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

    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("currency")
        @Expose
        private String currency;
        @SerializedName("symbol_left")
        @Expose
        private String symbolLeft;
        @SerializedName("symbol_right")
        @Expose
        private Object symbolRight;
        @SerializedName("value")
        @Expose
        private Double value;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("is_default")
        @Expose
        private Integer isDefault;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
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

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getSymbolLeft() {
            return symbolLeft;
        }

        public void setSymbolLeft(String symbolLeft) {
            this.symbolLeft = symbolLeft;
        }

        public Object getSymbolRight() {
            return symbolRight;
        }

        public void setSymbolRight(Object symbolRight) {
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
