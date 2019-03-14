package nerdvana.com.pointofsales.model;

public class UserModel {
    private String username;
    private boolean isLoggedIn;
    private String systemType;
    private String hostName;
    private String branchName;
    private String branchCode;
    private String userId;
    private String userGroup;
    private String userGroupId;
    public UserModel(String username, boolean isLoggedIn,
                     String systemType, String hostName,
                     String branchName, String branchCode,
                     String userId, String userGroup,
                     String userGroupId) {
        this.username = username;
        this.isLoggedIn = isLoggedIn;
        this.systemType = systemType;
        this.hostName = hostName;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.userId = userId;
        this.userGroup = userGroup;
        this.userGroupId = userGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public String getUserGroupId() {
        return userGroupId;
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

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
