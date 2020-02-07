package nerdvana.com.pointofsales.model;

public class PaymentTypeModel {

    private String core_id;
    private String payment_type;
    private boolean is_selected;
    private String image;
    private boolean is_two_form;

    public PaymentTypeModel(String core_id, String payment_type,
                            boolean is_selected, String image,
                            boolean is_two_form) {
        this.is_two_form = is_two_form;
        this.core_id = core_id;
        this.payment_type = payment_type;
        this.is_selected = is_selected;
        this.image = image;
    }

    public boolean isIs_two_form() {
        return is_two_form;
    }

    public void setIs_two_form(boolean is_two_form) {
        this.is_two_form = is_two_form;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCore_id() {
        return core_id;
    }

    public void setCore_id(String core_id) {
        this.core_id = core_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }




}
