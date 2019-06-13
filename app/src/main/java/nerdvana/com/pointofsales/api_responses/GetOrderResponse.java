package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderResponse {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("control_no")
        @Expose
        private String controlNo;

        public String getControlNo() {
            return controlNo;
        }

        public void setControlNo(String controlNo) {
            this.controlNo = controlNo;
        }
    }
}
