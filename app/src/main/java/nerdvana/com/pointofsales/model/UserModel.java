package nerdvana.com.pointofsales.model;

public class UserModel {
    private String username;
    private boolean isLoggedIn;
    private String systemType;
    public UserModel(String username, boolean isLoggedIn, String systemType) {
        this.username = username;
        this.isLoggedIn = isLoggedIn;
        this.systemType = systemType;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public String getSystemType() {
        return systemType;
    }
}
