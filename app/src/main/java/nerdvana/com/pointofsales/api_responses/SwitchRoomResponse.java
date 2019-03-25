package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SwitchRoomResponse {

    @SerializedName("result")
    @Expose
    private Result results;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String mesage;



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

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public class Result {

        @SerializedName("booked")
        @Expose
        private List<Booked> booked = null;

        public List<Booked> getBooked() {
            return booked;
        }

        public void setBooked(List<Booked> booked) {
            this.booked = booked;
        }
    }




    public class Booked {

        @SerializedName("room_id")
        @Expose
        private Integer roomId;

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }
    }

}
