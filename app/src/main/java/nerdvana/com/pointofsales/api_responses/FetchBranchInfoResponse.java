package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchBranchInfoResponse {

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

    public class Info {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("branch_id")
        @Expose
        private Integer branchId;
        @SerializedName("owner_name")
        @Expose
        private String ownerName;
        @SerializedName("tin_no")
        @Expose
        private String tinNo;
        @SerializedName("tax")
        @Expose
        private Double tax;
        @SerializedName("permit_no")
        @Expose
        private String permitNo;
        @SerializedName("accreditation_no")
        @Expose
        private String accreditationNo;
        @SerializedName("safe_keeping_amount")
        @Expose
        private Integer safeKeepingAmount;
        @SerializedName("remarks")
        @Expose
        private String remarks;
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

        public Integer getBranchId() {
            return branchId;
        }

        public void setBranchId(Integer branchId) {
            this.branchId = branchId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getTinNo() {
            return tinNo;
        }

        public void setTinNo(String tinNo) {
            this.tinNo = tinNo;
        }

        public Double getTax() {
            return tax;
        }

        public void setTax(Double tax) {
            this.tax = tax;
        }

        public String getPermitNo() {
            return permitNo;
        }

        public void setPermitNo(String permitNo) {
            this.permitNo = permitNo;
        }

        public String getAccreditationNo() {
            return accreditationNo;
        }

        public void setAccreditationNo(String accreditationNo) {
            this.accreditationNo = accreditationNo;
        }

        public Integer getSafeKeepingAmount() {
            return safeKeepingAmount;
        }

        public void setSafeKeepingAmount(Integer safeKeepingAmount) {
            this.safeKeepingAmount = safeKeepingAmount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("branch")
        @Expose
        private String branch;
        @SerializedName("branch_code")
        @Expose
        private String branchCode;
        @SerializedName("database")
        @Expose
        private String database;
        @SerializedName("host")
        @Expose
        private String host;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("fax")
        @Expose
        private Object fax;
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
        @SerializedName("info")
        @Expose
        private Info info;
        @SerializedName("shift")
        @Expose
        private List<Shift> shift = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public Object getFax() {
            return fax;
        }

        public void setFax(Object fax) {
            this.fax = fax;
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

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public List<Shift> getShift() {
            return shift;
        }

        public void setShift(List<Shift> shift) {
            this.shift = shift;
        }

    }

    public class Shift {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("branch_id")
        @Expose
        private Integer branchId;
        @SerializedName("shift_no")
        @Expose
        private Integer shiftNo;
        @SerializedName("sTime")
        @Expose
        private String sTime;
        @SerializedName("eTime")
        @Expose
        private String eTime;
        @SerializedName("grace_period")
        @Expose
        private Integer gracePeriod;
        @SerializedName("is_last_shift")
        @Expose
        private Integer isLastShift;
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

        public Integer getBranchId() {
            return branchId;
        }

        public void setBranchId(Integer branchId) {
            this.branchId = branchId;
        }

        public Integer getShiftNo() {
            return shiftNo;
        }

        public void setShiftNo(Integer shiftNo) {
            this.shiftNo = shiftNo;
        }

        public String getSTime() {
            return sTime;
        }

        public void setSTime(String sTime) {
            this.sTime = sTime;
        }

        public String getETime() {
            return eTime;
        }

        public void setETime(String eTime) {
            this.eTime = eTime;
        }

        public Integer getGracePeriod() {
            return gracePeriod;
        }

        public void setGracePeriod(Integer gracePeriod) {
            this.gracePeriod = gracePeriod;
        }

        public Integer getIsLastShift() {
            return isLastShift;
        }

        public void setIsLastShift(Integer isLastShift) {
            this.isLastShift = isLastShift;
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
