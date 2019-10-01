package nerdvana.com.pointofsales.model;

public class ServerConnectionModel {
    private boolean canConnect;

    public ServerConnectionModel(boolean canConnect) {
        this.canConnect = canConnect;
    }

    public boolean isCanConnect() {
        return canConnect;
    }
}
