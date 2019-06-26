package nerdvana.com.pointofsales.model;

public class ButtonsModel implements Comparable<ButtonsModel>{
    private int id;
    private String name;
    private String imageUrl;
    private int position;

    private boolean isSpecial;
    private  boolean isEnabled = true;
    public ButtonsModel(int id, String name, String imageUrl, int position) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
    }


    public ButtonsModel(int id, String name, String imageUrl, int position, boolean isSpecial) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
        this.isSpecial = isSpecial;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isSpecial() {
        return isSpecial;
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

    @Override
    public int compareTo(ButtonsModel o) {
        if (position > o.getPosition()) {
            return 1;
        } else if (position < o.getPosition()) {
            return -1;
        }
        return 0;
    }

    //100 = SAVE
    //101 = VOID
    //102 = PAY
}
