package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckGcResponse {


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


    public class Approve {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("gc_id")
        @Expose
        private Integer gcId;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("series_no")
        @Expose
        private String seriesNo;
        @SerializedName("is_used")
        @Expose
        private Integer isUsed;
        @SerializedName("branch_id")
        @Expose
        private Object branchId;
        @SerializedName("branch_code")
        @Expose
        private Object branchCode;
        @SerializedName("emp_id")
        @Expose
        private Object empId;
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

        public Integer getGcId() {
            return gcId;
        }

        public void setGcId(Integer gcId) {
            this.gcId = gcId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getSeriesNo() {
            return seriesNo;
        }

        public void setSeriesNo(String seriesNo) {
            this.seriesNo = seriesNo;
        }

        public Integer getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(Integer isUsed) {
            this.isUsed = isUsed;
        }

        public Object getBranchId() {
            return branchId;
        }

        public void setBranchId(Object branchId) {
            this.branchId = branchId;
        }

        public Object getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(Object branchCode) {
            this.branchCode = branchCode;
        }

        public Object getEmpId() {
            return empId;
        }

        public void setEmpId(Object empId) {
            this.empId = empId;
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


        @Override
        public String toString() {
            return seriesNo;
        }
    }

    public class Decline {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("gc_id")
        @Expose
        private Integer gcId;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("series_no")
        @Expose
        private String seriesNo;
        @SerializedName("is_used")
        @Expose
        private Integer isUsed;
        @SerializedName("branch_id")
        @Expose
        private Object branchId;
        @SerializedName("branch_code")
        @Expose
        private Object branchCode;
        @SerializedName("emp_id")
        @Expose
        private Object empId;
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

        public Integer getGcId() {
            return gcId;
        }

        public void setGcId(Integer gcId) {
            this.gcId = gcId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getSeriesNo() {
            return seriesNo;
        }

        public void setSeriesNo(String seriesNo) {
            this.seriesNo = seriesNo;
        }

        public Integer getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(Integer isUsed) {
            this.isUsed = isUsed;
        }

        public Object getBranchId() {
            return branchId;
        }

        public void setBranchId(Object branchId) {
            this.branchId = branchId;
        }

        public Object getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(Object branchCode) {
            this.branchCode = branchCode;
        }

        public Object getEmpId() {
            return empId;
        }

        public void setEmpId(Object empId) {
            this.empId = empId;
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

        @SerializedName("approve")
        @Expose
        private List<Approve> approve = null;
        @SerializedName("decline")
        @Expose
        private List<Decline> decline = null;
        @SerializedName("total")
        @Expose
        private Integer total;

        public List<Approve> getApprove() {
            return approve;
        }

        public void setApprove(List<Approve> approve) {
            this.approve = approve;
        }

        public List<Decline> getDecline() {
            return decline;
        }

        public void setDecline(List<Decline> decline) {
            this.decline = decline;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }


}
