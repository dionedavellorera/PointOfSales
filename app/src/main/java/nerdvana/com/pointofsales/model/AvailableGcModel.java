package nerdvana.com.pointofsales.model;

public class AvailableGcModel {
    private String id;
    private String gcId;
    private String seriesNumber;
    private String amount;

    public AvailableGcModel(String id, String gcId, String seriesNumber, String amount) {
        this.id = id;
        this.gcId = gcId;
        this.seriesNumber = seriesNumber;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getGcId() {
        return gcId;
    }

    public String getSeriesNumber() {
        return seriesNumber;
    }

    public String getAmount() {
        return amount;
    }
}
