package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;

public class SkListModel {
    private double totalSk;
    private String skCount;
    private List<CollectionFinalPostModel> cfpmList;

    public SkListModel(double totalSk, String skCount) {
        this.totalSk = totalSk;
        this.skCount = skCount;
    }

    public double getTotalSk() {
        return totalSk;
    }

    public void setTotalSk(double totalSk) {
        this.totalSk = totalSk;
    }

    public String getSkCount() {
        return skCount;
    }

    public void setSkCount(String skCount) {
        this.skCount = skCount;
    }

    public List<CollectionFinalPostModel> getCfpmList() {
        return cfpmList;
    }

    public void setCfpmList(List<CollectionFinalPostModel> cfpmList) {
        this.cfpmList = cfpmList;
    }
}
