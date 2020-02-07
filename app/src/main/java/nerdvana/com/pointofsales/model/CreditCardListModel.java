package nerdvana.com.pointofsales.model;

public class CreditCardListModel {
    private int core_id;
    private String credit_card;
    private boolean is_selected;
    private int image_url;
    public CreditCardListModel(int core_id, String credit_card,
                               boolean is_selected, int image_url) {
        this.core_id = core_id;
        this.credit_card = credit_card;
        this.is_selected = is_selected;
        this.image_url = image_url;
    }

    public int getCore_id() {
        return core_id;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setCore_id(int core_id) {
        this.core_id = core_id;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }
}
