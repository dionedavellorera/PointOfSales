package nerdvana.com.pointofsales.model;

public class ButtonsModel {
    private int id;
    private String name;
    private String imageUrl;
    private int position;
    public ButtonsModel(int id, String name, String imageUrl, int position) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    //100 = SAVE
    //101 = VOID
    //102 = PAY
}
