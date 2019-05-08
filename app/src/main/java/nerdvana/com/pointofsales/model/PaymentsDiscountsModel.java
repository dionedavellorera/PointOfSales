package nerdvana.com.pointofsales.model;

public class PaymentsDiscountsModel {
    private String name;
    private int viewType;
    public PaymentsDiscountsModel(String name, int viewType) {
        this.name = name;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public String getName() {
        return name;
    }
}
