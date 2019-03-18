package nerdvana.com.pointofsales.model;

public class SpnModelsItem {
    private String mModelName = "";
    private int mModelConstant = 0;

    public SpnModelsItem(String mModelName, int mModelConstant) {
        this.mModelName = mModelName;
        this.mModelConstant = mModelConstant;
    }

    public String getmModelName() {
        return mModelName;
    }

    public int getmModelConstant() {
        return mModelConstant;
    }

    @Override
    public String toString() {
        return mModelName;
    }
}
