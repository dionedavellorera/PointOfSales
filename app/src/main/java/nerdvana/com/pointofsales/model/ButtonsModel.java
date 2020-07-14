package nerdvana.com.pointofsales.model;

public class ButtonsModel implements Comparable<ButtonsModel>{
    private int id;
    private String name;
    private String imageUrl;
    private int position;

    private boolean isSpecial;
    private  boolean isEnabled = true;
    private int core_id;

    private boolean hasWelcome;

    private String fromPopUp = "";
    public ButtonsModel(int id, String name, String imageUrl, int position, int core_id) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
        this.core_id = core_id;
    }


    public ButtonsModel(int id, String name, String imageUrl, int position, boolean isSpecial) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
        this.isSpecial = isSpecial;
    }


    public ButtonsModel(int id, String name, String imageUrl, int position, int core_id, String fromPopUp) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.position = position;
        this.core_id = core_id;
        this.fromPopUp = fromPopUp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    public String getFromPopUp() {
        return fromPopUp;
    }

    public void setFromPopUp(String fromPopUp) {
        this.fromPopUp = fromPopUp;
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

    public boolean isHasWelcome() {
        return hasWelcome;
    }

    public void setHasWelcome(boolean hasWelcome) {
        this.hasWelcome = hasWelcome;
    }

    public int getCore_id() {
        return core_id;
    }

    public void setCore_id(int core_id) {
        this.core_id = core_id;
    }

    //100 = SAVE
    //101 = VOID
    //102 = PAY
}
