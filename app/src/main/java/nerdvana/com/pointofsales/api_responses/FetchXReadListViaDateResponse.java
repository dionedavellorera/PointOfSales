package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchXReadListViaDateResponse {

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
        private Double currencyValue;
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

        public void setCurrencyValue(Double currencyValue) {
            this.currencyValue = currencyValue;
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

    public class SafeKeeping {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
        @SerializedName("cash_and_reconcile_id")
        @Expose
        private Integer cashAndReconcileId;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
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
        @SerializedName("denomination")
        @Expose
        private List<Denomination> denomination = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
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

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
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

        public List<Denomination> getDenomination() {
            return denomination;
        }

        public void setDenomination(List<Denomination> denomination) {
            this.denomination = denomination;
        }

    }

    public class Transaction {

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
        private Integer roomBoy;
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



        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
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

        public Double getPersonCount() {
            return personCount;
        }

        public void setPersonCount(Double personCount) {
            this.personCount = personCount;
        }

        public Double getTotalItem() {
            return totalItem;
        }

        public void setTotalItem(Double totalItem) {
            this.totalItem = totalItem;
        }

        public Double getTotalQty() {
            return totalQty;
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

        public Integer getRoomBoy() {
            return roomBoy;
        }

        public void setRoomBoy(Integer roomBoy) {
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

    }





    public static class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("total")
        @Expose
        private Double total;
        @SerializedName("xPerson_amount")
        @Expose
        private Double xPersonAmount;
        @SerializedName("ot_amount")
        @Expose
        private Double otAmount;
        @SerializedName("void_amount")
        @Expose
        private Double voidAmount;
        @SerializedName("foc_sales")
        @Expose
        private Double focSales;
        @SerializedName("gross_sales")
        @Expose
        private Double grossSales;
        @SerializedName("net_sales")
        @Expose
        private Double netSales;
        @SerializedName("discount")
        @Expose
        private Double discount;
        @SerializedName("vat")
        @Expose
        private Double vat;
        @SerializedName("vatable")
        @Expose
        private Double vatable;
        @SerializedName("vat_exempt")
        @Expose
        private Double vatExempt;
        @SerializedName("vat_exempt_sales")
        @Expose
        private Double vatExemptSales;
        @SerializedName("cash_and_reconcile_id_list")
        @Expose
        private String cashAndReconcileIdList;
        @SerializedName("control_id_list")
        @Expose
        private String controlIdList;
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
        private Double currencyValue;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("emp_id")
        @Expose
        private Integer empId;
        @SerializedName("cut_off_date")
        @Expose
        private String cutOffDate;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("end_of_day_id")
        @Expose
        private Integer endOfDayId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("cash_and_reco")
        @Expose
        private List<CashAndReco> cashAndReco = null;
        @SerializedName("transactions")
        @Expose
        private List<Transaction> transactions = null;
        @SerializedName("cashier")
        @Expose
        private Cashier cashier;
        @SerializedName("duty_manager")
        @Expose
        private DutyManager dutyManager;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Double getxPersonAmount() {
            return xPersonAmount;
        }

        public void setxPersonAmount(Double xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
        }

        public Double getOtAmount() {
            return otAmount;
        }

        public void setOtAmount(Double otAmount) {
            this.otAmount = otAmount;
        }

        public Double getVoidAmount() {
            return voidAmount;
        }

        public void setVoidAmount(Double voidAmount) {
            this.voidAmount = voidAmount;
        }

        public Double getFocSales() {
            return focSales;
        }

        public void setFocSales(Double focSales) {
            this.focSales = focSales;
        }

        public Double getGrossSales() {
            return grossSales;
        }

        public void setGrossSales(Double grossSales) {
            this.grossSales = grossSales;
        }

        public Double getNetSales() {
            return netSales;
        }

        public void setNetSales(Double netSales) {
            this.netSales = netSales;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        public Double getVatExemptSales() {
            return vatExemptSales;
        }

        public void setVatExemptSales(Double vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
        }

        public Double getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Double currencyValue) {
            this.currencyValue = currencyValue;
        }

        public Double getVat() {
            return vat;
        }

        public void setVat(Double vat) {
            this.vat = vat;
        }

        public Double getVatable() {
            return vatable;
        }

        public void setVatable(Double vatable) {
            this.vatable = vatable;
        }

        public Double getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(Double vatExempt) {
            this.vatExempt = vatExempt;
        }



        public String getCashAndReconcileIdList() {
            return cashAndReconcileIdList;
        }

        public void setCashAndReconcileIdList(String cashAndReconcileIdList) {
            this.cashAndReconcileIdList = cashAndReconcileIdList;
        }

        public String getControlIdList() {
            return controlIdList;
        }

        public void setControlIdList(String controlIdList) {
            this.controlIdList = controlIdList;
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

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getEmpId() {
            return empId;
        }

        public void setEmpId(Integer empId) {
            this.empId = empId;
        }

        public String getCutOffDate() {
            return cutOffDate;
        }

        public void setCutOffDate(String cutOffDate) {
            this.cutOffDate = cutOffDate;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getEndOfDayId() {
            return endOfDayId;
        }

        public void setEndOfDayId(Integer endOfDayId) {
            this.endOfDayId = endOfDayId;
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

        public List<CashAndReco> getCashAndReco() {
            return cashAndReco;
        }

        public void setCashAndReco(List<CashAndReco> cashAndReco) {
            this.cashAndReco = cashAndReco;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        public Cashier getCashier() {
            return cashier;
        }

        public void setCashier(Cashier cashier) {
            this.cashier = cashier;
        }

        public DutyManager getDutyManager() {
            return dutyManager;
        }

        public void setDutyManager(DutyManager dutyManager) {
            this.dutyManager = dutyManager;
        }

    }

    public class CashAndReco {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cash_on_hand")
        @Expose
        private Double cashOnHand;
        @SerializedName("short_over")
        @Expose
        private Double shortOver;
        @SerializedName("total_sales")
        @Expose
        private Double totalSales;
        @SerializedName("cash_sales")
        @Expose
        private Double cashSales;
        @SerializedName("cut_off_id")
        @Expose
        private Integer cutOffId;
        @SerializedName("cash_advance")
        @Expose
        private Double cashAdvance;
        @SerializedName("adjustment_deposit")
        @Expose
        private String adjustmentDeposit;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Double currencyValue;
        @SerializedName("pos_id")
        @Expose
        private Integer posId;
        @SerializedName("emp_id")
        @Expose
        private Integer empId;
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
        @SerializedName("safe_keepings")
        @Expose
        private List<SafeKeeping> safeKeepings = null;
        @SerializedName("payments")
        @Expose
        private List<Payment> payments = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getCashOnHand() {
            return cashOnHand;
        }

        public void setCashOnHand(Double cashOnHand) {
            this.cashOnHand = cashOnHand;
        }

        public Double getShortOver() {
            return shortOver;
        }

        public void setShortOver(Double shortOver) {
            this.shortOver = shortOver;
        }

        public Double getTotalSales() {
            return totalSales;
        }

        public void setTotalSales(Double totalSales) {
            this.totalSales = totalSales;
        }

        public Double getCashSales() {
            return cashSales;
        }

        public void setCashSales(Double cashSales) {
            this.cashSales = cashSales;
        }

        public void setCurrencyValue(Double currencyValue) {
            this.currencyValue = currencyValue;
        }

        public Integer getCutOffId() {
            return cutOffId;
        }

        public void setCutOffId(Integer cutOffId) {
            this.cutOffId = cutOffId;
        }

        public Double getCashAdvance() {
            return cashAdvance;
        }

        public void setCashAdvance(Double cashAdvance) {
            this.cashAdvance = cashAdvance;
        }

        public String getAdjustmentDeposit() {
            return adjustmentDeposit;
        }

        public void setAdjustmentDeposit(String adjustmentDeposit) {
            this.adjustmentDeposit = adjustmentDeposit;
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

        public Integer getPosId() {
            return posId;
        }

        public void setPosId(Integer posId) {
            this.posId = posId;
        }

        public Integer getEmpId() {
            return empId;
        }

        public void setEmpId(Integer empId) {
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

        public List<SafeKeeping> getSafeKeepings() {
            return safeKeepings;
        }

        public void setSafeKeepings(List<SafeKeeping> safeKeepings) {
            this.safeKeepings = safeKeepings;
        }

        public List<Payment> getPayments() {
            return payments;
        }

        public void setPayments(List<Payment> payments) {
            this.payments = payments;
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

    public class Denomination {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cash_denomination_id")
        @Expose
        private Integer cashDenominationId;
        @SerializedName("cash_denomination_value")
        @Expose
        private Integer cashDenominationValue;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
        @SerializedName("safe_keeping_id")
        @Expose
        private Integer safeKeepingId;
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

        public Integer getCashDenominationId() {
            return cashDenominationId;
        }

        public void setCashDenominationId(Integer cashDenominationId) {
            this.cashDenominationId = cashDenominationId;
        }

        public Integer getCashDenominationValue() {
            return cashDenominationValue;
        }

        public void setCashDenominationValue(Integer cashDenominationValue) {
            this.cashDenominationValue = cashDenominationValue;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
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

        public Integer getSafeKeepingId() {
            return safeKeepingId;
        }

        public void setSafeKeepingId(Integer safeKeepingId) {
            this.safeKeepingId = safeKeepingId;
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

    public class DutyManager {

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


}
