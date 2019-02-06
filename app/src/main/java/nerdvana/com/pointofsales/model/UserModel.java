package nerdvana.com.pointofsales.model;

public class UserModel {
    private String username;
    private boolean isLoggedIn;
    private String systemType;
    private String hostName;
    private String branchName;
    private String branchCode;
    public UserModel(String username, boolean isLoggedIn,
                     String systemType, String hostName,
                     String branchName, String branchCode) {
        this.username = username;
        this.isLoggedIn = isLoggedIn;
        this.systemType = systemType;
        this.hostName = hostName;
        this.branchName = branchName;
        this.branchCode = branchCode;
    }

    public String getHostName() {
        return hostName;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getBranchCode() {
        return branchCode;
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
