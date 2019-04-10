package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public class Result {
        @SerializedName("uncollected")
        @Expose
        private Double unCollected;
        @SerializedName("cash_on_hand")
        @Expose
        private Double cashOnHand;
        @SerializedName("payments")
        @Expose
        private Double payments;

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
