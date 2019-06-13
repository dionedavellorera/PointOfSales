package nerdvana.com.pointofsales.model;

public class UpdateProductModel {

    private String post_id;
    private String name;
    private String price;
    private String quantity;

    public UpdateProductModel(String post_id, String name, String price, String quantity) {
        this.post_id = post_id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
