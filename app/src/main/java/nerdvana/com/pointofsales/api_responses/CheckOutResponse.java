package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckOutResponse {

    public class Result {
        @SerializedName("data")
        @Expose
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public class Data {
        @SerializedName("guest_info_id")
        @Expose
        private String guestInfoId;
        @SerializedName("booked")
        @Expose
        private List<Booked> booked = null;

        public List<Booked> getBooked() {
            return booked;
        }

        public void setBooked(List<Booked> booked) {
            this.booked = booked;
        }

        public String getGuestInfoId() {
            return guestInfoId;
        }

        public void setGuestInfoId(String guestInfoId) {
            this.guestInfoId = guestInfoId;
        }
    }

    public class Booked {
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
}
