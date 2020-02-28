package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PosMachinesResponse {

    @SerializedName("result")
    @Expose
    private List<Result> resultList;

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public class Device {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("pos_id")
        @Expose
        private String pos_id;
        @SerializedName("serial")
        @Expose
        private String serial;
        @SerializedName("model")
        @Expose
        private String model;
        @SerializedName("android_id")
        @Expose
        private String android_id;
        @SerializedName("manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("board")
        @Expose
        private String board;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPos_id() {
            return pos_id;
        }

        public void setPos_id(String pos_id) {
            this.pos_id = pos_id;
        }

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getAndroid_id() {
            return android_id;
        }

        public void setAndroid_id(String android_id) {
            this.android_id = android_id;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
        }
    }
    public class Result {
        @SerializedName("product_key")
        @Expose
        private String product_key;
        @SerializedName("permit_nos")
        @Expose
        private String permit_nos;
        @SerializedName("permit_issued_at")
        @Expose
        private String permit_issued_at;
        @SerializedName("permit_valid_at")
        @Expose
        private String permit_valid_at;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("device")
        @Expose
        private Device device;

        public String getProduct_key() {
            return product_key;
        }

        public void setProduct_key(String product_key) {
            this.product_key = product_key;
        }

        public String getPermit_nos() {
            return permit_nos;
        }

        public void setPermit_nos(String permit_nos) {
            this.permit_nos = permit_nos;
        }

        public String getPermit_issued_at() {
            return permit_issued_at;
        }

        public void setPermit_issued_at(String permit_issued_at) {
            this.permit_issued_at = permit_issued_at;
        }

        public String getPermit_valid_at() {
            return permit_valid_at;
        }

        public void setPermit_valid_at(String permit_valid_at) {
            this.permit_valid_at = permit_valid_at;
        }

        public Device getDevice() {
            return device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
