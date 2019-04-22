package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CashNReconcileResponse {

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
        private Object guestInfoId;
        @SerializedName("employee_id")
        @Expose
        private Integer employeeId;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("payment_type_id")
        @Expose
        private Integer paymentTypeId;
        @SerializedName("ot_hours")
        @Expose
        private Integer otHours;
        @SerializedName("ot_amount")
        @Expose
        private Integer otAmount;
        @SerializedName("xPerson_amount")
        @Expose
        private Integer xPersonAmount;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("advance")
        @Expose
        private Integer advance;
        @SerializedName("tendered")
        @Expose
        private Integer tendered;
        @SerializedName("change")
        @Expose
        private Integer change;
        @SerializedName("is_soa")
        @Expose
        private Integer isSoa;
        @SerializedName("soa_count")
        @Expose
        private String soaCount;
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
        private Integer vatExempt;
        @SerializedName("vat_exempt_sales")
        @Expose
        private Integer vatExemptSales;
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

        public Object getGuestInfoId() {
            return guestInfoId;
        }

        public void setGuestInfoId(Object guestInfoId) {
            this.guestInfoId = guestInfoId;
        }

        public Integer getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Integer employeeId) {
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

        public Integer getOtHours() {
            return otHours;
        }

        public void setOtHours(Integer otHours) {
            this.otHours = otHours;
        }

        public Integer getOtAmount() {
            return otAmount;
        }

        public void setOtAmount(Integer otAmount) {
            this.otAmount = otAmount;
        }

        public Integer getXPersonAmount() {
            return xPersonAmount;
        }

        public void setXPersonAmount(Integer xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getAdvance() {
            return advance;
        }

        public void setAdvance(Integer advance) {
            this.advance = advance;
        }

        public Integer getTendered() {
            return tendered;
        }

        public void setTendered(Integer tendered) {
            this.tendered = tendered;
        }

        public Integer getChange() {
            return change;
        }

        public void setChange(Integer change) {
            this.change = change;
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

        public Integer getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(Integer vatExempt) {
            this.vatExempt = vatExempt;
        }

        public Integer getVatExemptSales() {
            return vatExemptSales;
        }

        public void setVatExemptSales(Integer vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
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



    public class Result {

        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("payment")
        @Expose
        private List<Payment> payment = null;
        @SerializedName("discount")
        @Expose
        private List<Discount> discount = null;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public List<Payment> getPayment() {
            return payment;
        }

        public void setPayment(List<Payment> payment) {
            this.payment = payment;
        }

        public List<Discount> getDiscount() {
            return discount;
        }

        public void setDiscount(List<Discount> discount) {
            this.discount = discount;
        }
    }


    public class Payment {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("payment_description")
        @Expose
        private String paymentDescription;
        @SerializedName("is_advance")
        @Expose
        private Integer isAdvance;
        @SerializedName("cash_and_reconcile_id")
        @Expose
        private Integer cashAndReconcileId;
        @SerializedName("credit_card")
        @Expose
        private Object creditCard;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getPaymentDescription() {
            return paymentDescription;
        }

        public void setPaymentDescription(String paymentDescription) {
            this.paymentDescription = paymentDescription;
        }

        public Integer getIsAdvance() {
            return isAdvance;
        }

        public void setIsAdvance(Integer isAdvance) {
            this.isAdvance = isAdvance;
        }

        public Integer getCashAndReconcileId() {
            return cashAndReconcileId;
        }

        public void setCashAndReconcileId(Integer cashAndReconcileId) {
            this.cashAndReconcileId = cashAndReconcileId;
        }

        public Object getCreditCard() {
            return creditCard;
        }

        public void setCreditCard(Object creditCard) {
            this.creditCard = creditCard;
        }

    }

    public class CashAndReco {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("cash_on_hand")
        @Expose
        private Integer cashOnHand;
        @SerializedName("short_over")
        @Expose
        private Integer shortOver;
        @SerializedName("total_sales")
        @Expose
        private Integer totalSales;
        @SerializedName("cash_sales")
        @Expose
        private Integer cashSales;
        @SerializedName("cut_off_id")
        @Expose
        private Integer cutOffId;
        @SerializedName("cash_advance")
        @Expose
        private Integer cashAdvance;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;
        @SerializedName("currency_value")
        @Expose
        private Integer currencyValue;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCashOnHand() {
            return cashOnHand;
        }

        public void setCashOnHand(Integer cashOnHand) {
            this.cashOnHand = cashOnHand;
        }

        public Integer getShortOver() {
            return shortOver;
        }

        public void setShortOver(Integer shortOver) {
            this.shortOver = shortOver;
        }

        public Integer getTotalSales() {
            return totalSales;
        }

        public void setTotalSales(Integer totalSales) {
            this.totalSales = totalSales;
        }

        public Integer getCashSales() {
            return cashSales;
        }

        public void setCashSales(Integer cashSales) {
            this.cashSales = cashSales;
        }

        public Integer getCutOffId() {
            return cutOffId;
        }

        public void setCutOffId(Integer cutOffId) {
            this.cutOffId = cutOffId;
        }

        public Integer getCashAdvance() {
            return cashAdvance;
        }

        public void setCashAdvance(Integer cashAdvance) {
            this.cashAdvance = cashAdvance;
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

    }

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("xPerson_amount")
        @Expose
        private Integer xPersonAmount;
        @SerializedName("ot_amount")
        @Expose
        private Integer otAmount;
        @SerializedName("void_amount")
        @Expose
        private Integer voidAmount;
        @SerializedName("gross_sales")
        @Expose
        private Integer grossSales;
        @SerializedName("net_sales")
        @Expose
        private Integer netSales;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("vat")
        @Expose
        private Double vat;
        @SerializedName("vatable")
        @Expose
        private Double vatable;
        @SerializedName("vat_exempt")
        @Expose
        private Integer vatExempt;
        @SerializedName("vat_exempt_sales")
        @Expose
        private Integer vatExemptSales;
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
        private Integer currencyValue;
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
        private Object endOfDayId;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getXPersonAmount() {
            return xPersonAmount;
        }

        public void setXPersonAmount(Integer xPersonAmount) {
            this.xPersonAmount = xPersonAmount;
        }

        public Integer getOtAmount() {
            return otAmount;
        }

        public void setOtAmount(Integer otAmount) {
            this.otAmount = otAmount;
        }

        public Integer getVoidAmount() {
            return voidAmount;
        }

        public void setVoidAmount(Integer voidAmount) {
            this.voidAmount = voidAmount;
        }

        public Integer getGrossSales() {
            return grossSales;
        }

        public void setGrossSales(Integer grossSales) {
            this.grossSales = grossSales;
        }

        public Integer getNetSales() {
            return netSales;
        }

        public void setNetSales(Integer netSales) {
            this.netSales = netSales;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
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

        public Integer getVatExempt() {
            return vatExempt;
        }

        public void setVatExempt(Integer vatExempt) {
            this.vatExempt = vatExempt;
        }

        public Integer getVatExemptSales() {
            return vatExemptSales;
        }

        public void setVatExemptSales(Integer vatExemptSales) {
            this.vatExemptSales = vatExemptSales;
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

        public Integer getCurrencyValue() {
            return currencyValue;
        }

        public void setCurrencyValue(Integer currencyValue) {
            this.currencyValue = currencyValue;
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

        public Object getEndOfDayId() {
            return endOfDayId;
        }

        public void setEndOfDayId(Object endOfDayId) {
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

    }

    public class Discount {

        @SerializedName("discount_amount")
        @Expose
        private Double discountAmount;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("discount_type_id")
        @Expose
        private Integer discountTypeId;
        @SerializedName("discount_type")
        @Expose
        private String discountType;
        @SerializedName("is_special")
        @Expose
        private Integer isSpecial;

        public Double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getDiscountTypeId() {
            return discountTypeId;
        }

        public void setDiscountTypeId(Integer discountTypeId) {
            this.discountTypeId = discountTypeId;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public Integer getIsSpecial() {
            return isSpecial;
        }

        public void setIsSpecial(Integer isSpecial) {
            this.isSpecial = isSpecial;
        }

    }



}
