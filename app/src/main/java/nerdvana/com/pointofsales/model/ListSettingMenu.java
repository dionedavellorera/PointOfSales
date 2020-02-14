package nerdvana.com.pointofsales.model;

public class ListSettingMenu {
    private int id;
    private int drawableIcon;
    private String name;
    private boolean isClicked;
    public ListSettingMenu(int id,int drawableIcon, String name, boolean isClicked) {
        this.id = id;
        this.drawableIcon = drawableIcon;
        this.name = name;
        this.isClicked = isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isClicked() {
        return isClicked;
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
