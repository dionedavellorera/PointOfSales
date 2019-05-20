package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VerifyMachineResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("mesage")
    @Expose
    private String mesage;
    @SerializedName("company")
    @Expose
    private List<Company> company = null;
    @SerializedName("branch")
    @Expose
    private Branch branch;

    public List<Company> getCompany() {
        return company;
    }

    public void setCompany(List<Company> company) {
        this.company = company;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

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

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public class Result {
        @SerializedName("printer_path")
        @Expose
        private String printer_path;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_key")
        @Expose
        private String productKey;
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
//        @SerializedName("device")
//        @Expose
//        private Device device;


        public String getPrinter_path() {
            return printer_path;
        }

        public void setPrinter_path(String printer_path) {
            this.printer_path = printer_path;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
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

//        public Device getDevice() {
//            return device;
//        }
//
//        public void setDevice(Device device) {
//            this.device = device;
//        }
    }

    public class Device {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("serial")
        @Expose
        private String serial;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("android_id")
        @Expose
        private String androidId;
        @SerializedName("manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("board")
        @Expose
        private String board;
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

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
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

    public class Company {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("corporate_id")
        @Expose
        private Integer corporateId;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("company_code")
        @Expose
        private String companyCode;
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
        @SerializedName("image_file")
        @Expose
        private Object imageFile;
        @SerializedName("icon_file")
        @Expose
        private Object iconFile;
        @SerializedName("logo_file")
        @Expose
        private Object logoFile;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("flag")
        @Expose
        private Integer flag;
        @SerializedName("is_user_increment")
        @Expose
        private Integer isUserIncrement;
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

        public Integer getCorporateId() {
            return corporateId;
        }

        public void setCorporateId(Integer corporateId) {
            this.corporateId = corporateId;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
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

        public Object getImageFile() {
            return imageFile;
        }

        public void setImageFile(Object imageFile) {
            this.imageFile = imageFile;
        }

        public Object getIconFile() {
            return iconFile;
        }

        public void setIconFile(Object iconFile) {
            this.iconFile = iconFile;
        }

        public Object getLogoFile() {
            return logoFile;
        }

        public void setLogoFile(Object logoFile) {
            this.logoFile = logoFile;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Integer getIsUserIncrement() {
            return isUserIncrement;
        }

        public void setIsUserIncrement(Integer isUserIncrement) {
            this.isUserIncrement = isUserIncrement;
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

    public class Branch {

        @SerializedName("id")
        @Expose
        private Integer id;
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
        private List<Shift> shift;

        public List<Shift> getShift() {
            return shift;
        }

        public void setShift(List<Shift> shift) {
            this.shift = shift;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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
        @SerializedName("safe_keeping_amount")
        @Expose
        private Double safe_keeping_amount;

        public Double getSafe_keeping_amount() {
            return safe_keeping_amount;
        }

        public void setSafe_keeping_amount(Double safe_keeping_amount) {
            this.safe_keeping_amount = safe_keeping_amount;
        }

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

    public class Shift {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("branch_id")
        @Expose
        private String branchId;
        @SerializedName("shift_no")
        @Expose
        private String shiftNo;
        @SerializedName("sTime")
        @Expose
        private String sTime;
        @SerializedName("eTime")
        @Expose
        private String eTime;
        @SerializedName("grace_period")
        @Expose
        private String gracePeriod;
        @SerializedName("is_last_shift")
        @Expose
        private String isLastShift;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getShiftNo() {
            return shiftNo;
        }

        public void setShiftNo(String shiftNo) {
            this.shiftNo = shiftNo;
        }

        public String getsTime() {
            return sTime;
        }

        public void setsTime(String sTime) {
            this.sTime = sTime;
        }

        public String geteTime() {
            return eTime;
        }

        public void seteTime(String eTime) {
            this.eTime = eTime;
        }

        public String getGracePeriod() {
            return gracePeriod;
        }

        public void setGracePeriod(String gracePeriod) {
            this.gracePeriod = gracePeriod;
        }

        public String getIsLastShift() {
            return isLastShift;
        }

        public void setIsLastShift(String isLastShift) {
            this.isLastShift = isLastShift;
        }
    }
}
