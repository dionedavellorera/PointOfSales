package nerdvana.com.pointofsales;

public class ApiError {

    private String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
