package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchXReadListResponse {

    @SerializedName("result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Transactions {
        @SerializedName("id")
        @Expose
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Result {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("shift_no")
        @Expose
        private String shiftNo;
        @SerializedName("cut_off_date")
        @Expose
        private String cutOffDate;
        @SerializedName("transactions")
        @Expose
        private List<Transactions> transactions;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getShiftNo() {
            return shiftNo;
        }

        public void setShiftNo(String shiftNo) {
            this.shiftNo = shiftNo;
        }

        public String getCutOffDate() {
            return cutOffDate;
        }

        public void setCutOffDate(String cutOffDate) {
            this.cutOffDate = cutOffDate;
        }

        public List<Transactions> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transactions> transactions) {
            this.transactions = transactions;
        }
    }

}
