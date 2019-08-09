package nerdvana.com.pointofsales.dialogs;

public class ZXReadModel {

    private String id;

    private String company;
    private String address;
    private String telNumber;
    private String serialNumber;
    private String regTin;
    private String permitNumber;
    private String minNumber;
    private String title;

    private String postingDate;
    private String shiftNumber;

    private String user;
    private String manager;

    private String machineNumber;
    private String grossSales;
    private String netSales;
    private String vatableSales;
    private String vatExemptSales;
    private String twelveVat;
    private String nonVat;
    private String serviceCharge;
    private String cashSales;
    private String cardSales;
    private String depositSales;
    private String online;
    private String depositAdjustment;
    private String voidAmount;

    private String pwdCount;
    private String pwdAmount;

    private String seniorCount;
    private String seniorAmount;
    private String others;

    private String beginningTrans;
    private String endTrans;
    private String oldGrandTotal;
    private String newGrandTotal;

    private String zReadNo;

    public ZXReadModel(String company, String address, String telNumber, String serialNumber, String regTin, String permitNumber, String minNumber, String title, String postingDate, String shiftNumber, String user, String manager, String machineNumber, String grossSales, String netSales, String vatableSales, String vatExemptSales, String twelveVat, String nonVat, String serviceCharge, String cashSales, String cardSales, String depositSales, String online, String depositAdjustment, String voidAmount, String pwdCount, String pwdAmount, String seniorCount, String seniorAmount, String others, String beginningTrans, String endTrans, String oldGrandTotal, String newGrandTotal, String zReadNo, String id) {
        this.company = company;
        this.id = id;
        this.address = address;
        this.telNumber = telNumber;
        this.serialNumber = serialNumber;
        this.regTin = regTin;
        this.permitNumber = permitNumber;
        this.minNumber = minNumber;
        this.title = title;
        this.postingDate = postingDate;
        this.shiftNumber = shiftNumber;
        this.user = user;
        this.manager = manager;
        this.machineNumber = machineNumber;
        this.grossSales = grossSales;
        this.netSales = netSales;
        this.vatableSales = vatableSales;
        this.vatExemptSales = vatExemptSales;
        this.twelveVat = twelveVat;
        this.nonVat = nonVat;
        this.serviceCharge = serviceCharge;
        this.cashSales = cashSales;
        this.cardSales = cardSales;
        this.depositSales = depositSales;
        this.online = online;
        this.depositAdjustment = depositAdjustment;
        this.voidAmount = voidAmount;
        this.pwdCount = pwdCount;
        this.pwdAmount = pwdAmount;
        this.seniorCount = seniorCount;
        this.seniorAmount = seniorAmount;
        this.others = others;
        this.beginningTrans = beginningTrans;
        this.endTrans = endTrans;
        this.oldGrandTotal = oldGrandTotal;
        this.newGrandTotal = newGrandTotal;
        this.zReadNo = zReadNo;
    }

    public String getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public String getShiftNumber() {
        return shiftNumber;
    }

    public String getUser() {
        return user;
    }

    public String getManager() {
        return manager;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public String getGrossSales() {
        return grossSales;
    }

    public String getNetSales() {
        return netSales;
    }

    public String getVatableSales() {
        return vatableSales;
    }

    public String getVatExemptSales() {
        return vatExemptSales;
    }

    public String getTwelveVat() {
        return twelveVat;
    }

    public String getNonVat() {
        return nonVat;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public String getCashSales() {
        return cashSales;
    }

    public String getCardSales() {
        return cardSales;
    }

    public String getDepositSales() {
        return depositSales;
    }

    public String getOnline() {
        return online;
    }

    public String getDepositAdjustment() {
        return depositAdjustment;
    }

    public String getVoidAmount() {
        return voidAmount;
    }

    public String getPwdCount() {
        return pwdCount;
    }

    public String getPwdAmount() {
        return pwdAmount;
    }

    public String getSeniorCount() {
        return seniorCount;
    }

    public String getSeniorAmount() {
        return seniorAmount;
    }

    public String getOthers() {
        return others;
    }

    public String getBeginningTrans() {
        return beginningTrans;
    }

    public String getEndTrans() {
        return endTrans;
    }

    public String getOldGrandTotal() {
        return oldGrandTotal;
    }

    public String getNewGrandTotal() {
        return newGrandTotal;
    }

    public String getzReadNo() {
        return zReadNo;
    }
}
