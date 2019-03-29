package nerdvana.com.pointofsales.model;

public class DiscountModel {
    private String post_id;
    private String name;

    public DiscountModel(String post_id, String name) {
        this.post_id = post_id;
        this.name = name;
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

}
