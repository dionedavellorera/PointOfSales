package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.ViewReceiptViaDateResponse;

public class ViewReceiptActualModel {
    private String controlNumber;
    private String company;
    private String address;
    private String telNumber;
    private String serialNumber;
    private String regTin;
    private String permitNumber;
    private String minNumber;
    private String roomNumber;

    private String cashier;
    private String roomBoy;
    private String checkInTime;
    private String expectedCheckOut;
    private String receiptNumber;
    private String machineNumber;

    private String vatExempt;
    private String discount;
    private String advanceDepo;

    private String amountDue;
    private String tendered;
    private String change;
    private String vatableSales;
    private String vatExemptsales;
    private String twelveVat;

    private String personCountValue;
    private String itemCountValue;

    private String subTotalValue;

    private String otHours;
    private String otAmount;
    private List<ViewReceiptViaDateResponse.Post_> postList;

    private String customerName;
    private String customerAddress;
    private String customerTin;
    private String customerBusinessStyle;

    public ViewReceiptActualModel(String company, String address,
                                  String telNumber, String serialNumber,
                                  String regTin, String permitNumber,
                                  String minNumber, String roomNumber,
                                  String cashier, String roomBoy,
                                  String checkInTime, String expectedCheckOut,
                                  String receiptNumber, String machineNumber,
                                  String vatExempt, String discount,
                                  String advanceDepo, String amountDue,
                                  String tendered, String change,
                                  String vatableSales, String vatExemptsales,
                                  String twelveVat, String personCountValue,
                                  String itemCountValue, String subTotalValue,
                                  List<ViewReceiptViaDateResponse.Post_> postList,
                                  String otHours, String otAmount,
                                  String customerName, String customerAddress,
                                  String customerTin, String customerBusinessStyle,
                                  String controlNumber) {
        this.controlNumber = controlNumber;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerTin = customerTin;
        this.customerBusinessStyle = customerBusinessStyle;
        this.otHours = otHours;
        this.otAmount = otAmount;
        this.subTotalValue = subTotalValue;
        this.personCountValue = personCountValue;
        this.itemCountValue = itemCountValue;
        this.amountDue = amountDue;
        this.tendered = tendered;
        this.change = change;
        this.vatableSales = vatableSales;
        this.vatExemptsales = vatExemptsales;
        this.twelveVat = twelveVat;
        this.company = company;
        this.address = address;
        this.telNumber = telNumber;
        this.serialNumber = serialNumber;
        this.regTin = regTin;
        this.permitNumber = permitNumber;
        this.minNumber = minNumber;
        this.roomNumber = roomNumber;
        this.cashier = cashier;
        this.roomBoy = roomBoy;
        this.checkInTime = checkInTime;
        this.expectedCheckOut = expectedCheckOut;
        this.receiptNumber = receiptNumber;
        this.machineNumber = machineNumber;
        this.vatExempt = vatExempt;
        this.discount = discount;
        this.advanceDepo = advanceDepo;
        this.postList = postList;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public List<ViewReceiptViaDateResponse.Post_> getPostList() {
        return postList;
    }

    public String getOtHours() {
        return otHours;
    }

    public void setOtHours(String otHours) {
        this.otHours = otHours;
    }

    public String getOtAmount() {
        return otAmount;
    }

    public void setOtAmount(String otAmount) {
        this.otAmount = otAmount;
    }

    public String getSubTotalValue() {
        return subTotalValue;
    }

    public String getPersonCountValue() {
        return personCountValue;
    }

    public String getItemCountValue() {
        return itemCountValue;
    }

    public String getVatExempt() {
        return vatExempt;
    }

    public String getDiscount() {
        return discount;
    }

    public String getAdvanceDepo() {
        return advanceDepo;
    }



    public String getAmountDue() {
        return amountDue;
    }

    public String getTendered() {
        return tendered;
    }

    public String getChange() {
        return change;
    }

    public String getVatableSales() {
        return vatableSales;
    }

    public String getVatExemptsales() {
        return vatExemptsales;
    }

    public String getTwelveVat() {
        return twelveVat;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getRegTin() {
        return regTin;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public String getMinNumber() {
        return minNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCashier() {
        return cashier;
    }

    public String getRoomBoy() {
        return roomBoy;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getExpectedCheckOut() {
        return expectedCheckOut;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerTin() {
        return customerTin;
    }

    public String getCustomerBusinessStyle() {
        return customerBusinessStyle;
    }
}
