package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZReadResponse {
//    @SerializedName("data")
//    @Expose
//    private Data dataList;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

//    public Data getDataList() {
//        return dataList;
//    }

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

//    public class Data {
//        @SerializedName("date")
//        @Expose
//        private String date;
//        @SerializedName("check_out_by")
//        @Expose
//        private String checkOutBy;
//        @SerializedName("shift_no")
//        @Expose
//        private String shiftNo;
//        @SerializedName("username")
//        @Expose
//        private String username;
//        @SerializedName("user_id")
//        @Expose
//        private String userId;
//        @SerializedName("email")
//        @Expose
//        private String email;
//        @SerializedName("name")
//        @Expose
//        private String name;
//
//        public String getDate() {
//            return date;
//        }
//
//        public String getCheckOutBy() {
//            return checkOutBy;
//        }
//
//        public String getShiftNo() {
//            return shiftNo;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public String getUserId() {
//            return userId;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public String getName() {
//            return name;
//        }
//    }
}
