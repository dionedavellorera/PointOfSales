package nerdvana.com.pointofsales.model;

public class VoidProductModel {

    private String post_id;
    private String name;
    private String price;
    private String quantity;
    private String remarks;

    public VoidProductModel(String postId, String name,
                            String price, String quantity,
                            String remarks) {
        this.post_id = postId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPost_id() {
        return post_id;
    }
}
