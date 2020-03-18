package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckSafeKeepingResponse {
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

    public static class Denomination {
        @SerializedName("cash_denomination_id")
        @Expose
        private String cashDenominationId;
        @SerializedName("cash_denomination_value")
        @Expose
        private String cashDenominationValue;
        @SerializedName("amount")
        @Expose
        private String amount;

        public String getCashDenominationId() {
            return cashDenominationId;
        }

        public void setCashDenominationId(String cashDenominationId) {
            this.cashDenominationId = cashDenominationId;
        }

        public String getCashDenominationValue() {
            return cashDenominationValue;
        }

        public void setCashDenominationValue(String cashDenominationValue) {
            this.cashDenominationValue = cashDenominationValue;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public class Liiiist {
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("currency_id")
        @Expose
        private String currencyId;

        @SerializedName("denomination")
        @Expose
        private List<Denomination> denominationList;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public List<Denomination> getDenominationList() {
            return denominationList;
        }

        public void setDenominationList(List<Denomination> denominationList) {
            this.denominationList = denominationList;
        }
    }

    public class Result {
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("list")
        @Expose
        private List<Liiiist> list;
        @SerializedName("change")
        @Expose
        private String change;
        @SerializedName("uncollected")
        @Expose
        private Double unCollected;
        @SerializedName("cash_on_hand")
        @Expose
        private Double cashOnHand;
        @SerializedName("payments")
        @Expose
        private Double payments;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<Liiiist> getList() {
            return list;
        }

        public void setList(List<Liiiist> list) {
            this.list = list;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public void setUnCollected(Double unCollected) {
            this.unCollected = unCollected;
        }

        public Double getUnCollected() {
            return unCollected;
        }

        public void setCollected(Double unCollected) {
            this.unCollected = unCollected;
        }

        public Double getCashOnHand() {
            return cashOnHand;
        }

        public void setCashOnHand(Double cashOnHand) {
            this.cashOnHand = cashOnHand;
        }

        public Double getPayments() {
            return payments;
        }

        public void setPayments(Double payments) {
            this.payments = payments;
        }
    }
}
