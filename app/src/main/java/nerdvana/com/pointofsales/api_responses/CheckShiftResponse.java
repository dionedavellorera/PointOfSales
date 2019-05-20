package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckShiftResponse {

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

    public class Result {
        @SerializedName("date")
        @Expose
        private String lastTransDate;
        @SerializedName("earlyEnd")
        @Expose
        private String earlyEnd;
        @SerializedName("sTime")
        @Expose
        private String sTime;
        @SerializedName("eTime")
        @Expose
        private String eTime;
        @SerializedName("endShift")
        @Expose
        private String endShift;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("shift_no")
        @Expose
        private Integer shiftNo;


        public String getLastTransDate() {
            return lastTransDate;
        }

        public void setLastTransDate(String lastTransDate) {
            this.lastTransDate = lastTransDate;
        }

        public void setsTime(String sTime) {
            this.sTime = sTime;
        }

        public void seteTime(String eTime) {
            this.eTime = eTime;
        }

        public String getEarlyEnd() {
            return earlyEnd;
        }

        public void setEarlyEnd(String earlyEnd) {
            this.earlyEnd = earlyEnd;
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

        public String getEndShift() {
            return endShift;
        }

        public void setEndShift(String endShift) {
            this.endShift = endShift;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getShiftNo() {
            return shiftNo;
        }

        public void setShiftNo(Integer shiftNo) {
            this.shiftNo = shiftNo;
        }

    }


}
