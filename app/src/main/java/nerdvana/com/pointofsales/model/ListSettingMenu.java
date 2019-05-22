package nerdvana.com.pointofsales.model;

public class ListSettingMenu {
    private int id;
    private int drawableIcon;
    private String name;

    public ListSettingMenu(int id,int drawableIcon, String name) {
        this.id = id;
        this.drawableIcon = drawableIcon;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public String getName() {
        return name;
    }
}
