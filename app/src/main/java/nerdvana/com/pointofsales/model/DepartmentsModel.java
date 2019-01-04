package nerdvana.com.pointofsales.model;

public class DepartmentsModel {
    private String name;
    private String imageUrl;

    public DepartmentsModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
