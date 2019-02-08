package nerdvana.com.pointofsales;

public class SqlQueries {
    public static final String GET_PENDING_TABLE_ORDER = "SELECT * FROM Transaction_Entity WHERE room_table_number = ? AND transaction_status = 2001";
    public static final String GET_CART_ITEMS = "SELECT * FROM Cart_Entity WHERE _trans_id = ? AND product_status != 1004";
}
