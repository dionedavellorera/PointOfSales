package nerdvana.com.pointofsales.api_requests;

public class CollectionFinalPostModel {
    private String cash_denomination_id;
    private String amount;
    private String cash_valued;
    private String currency_id;
    private String currency_value;
    private String pos_id;
    private String user_id;
    private String emp_id;

    public CollectionFinalPostModel(String cash_denomination_id, String amount, String cash_valued, String currency_id, String currency_value, String pos_id, String user_id, String emp_id) {
        this.cash_denomination_id = cash_denomination_id;
        this.amount = amount;
        this.cash_valued = cash_valued;
        this.currency_id = currency_id;
        this.currency_value = currency_value;
        this.pos_id = pos_id;
        this.user_id = user_id;
        this.emp_id = emp_id;
    }

    public String getCash_denomination_id() {
        return cash_denomination_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getCash_valued() {
        return cash_valued;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public String getCurrency_value() {
        return currency_value;
    }

    public String getPos_id() {
        return pos_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    @Override
    public String toString() {
        return "CollectionFinalPostModel{" +
                "cash_denomination_id='" + cash_denomination_id + '\'' +
                ", amount='" + amount + '\'' +
                ", cash_valued='" + cash_valued + '\'' +
                ", currency_id='" + currency_id + '\'' +
                ", currency_value='" + currency_value + '\'' +
                ", pos_id='" + pos_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", emp_id='" + emp_id + '\'' +
                '}';
    }
}
