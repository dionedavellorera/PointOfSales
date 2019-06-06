package nerdvana.com.pointofsales.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AddRateProductModel {
    private String product_id;
    private String room_rate_price_id;
    private String qty;
    private String tax;
    private String price;
    private int is_price_changed;
    private String product_initial;
    private List<AlaCarte> alacart;
    private List<Group> group;
    public static class AlaCarte {
        private String product_id;
        private String room_rate_price_id;
        private String qty;
        private String tax;
        private String price;
        private int is_price_changed;
        private String product_initial;

        public AlaCarte(String product_id, String room_rate_price_id,
                        String qty, String tax,
                        String price, int is_price_changed,
                        String product_initial) {
            this.product_id = product_id;
            this.room_rate_price_id = room_rate_price_id;
            this.qty = qty;
            this.tax = tax;
            this.price = price;
            this.is_price_changed = is_price_changed;
            this.product_initial = product_initial;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getRoom_rate_price_id() {
            return room_rate_price_id;
        }

        public String getQty() {
            return qty;
        }

        public String getTax() {
            return tax;
        }

        public String getPrice() {
            return price;
        }

        public int getIs_price_changed() {
            return is_price_changed;
        }

        public String getProduct_initial() {
            return product_initial;
        }
    }

    public static class Group {
        private GroupCompo groupCompoList;

        public Group(GroupCompo groupCompoList) {
            this.groupCompoList = groupCompoList;
        }

        public GroupCompo getGroupCompoList() {
            return groupCompoList;
        }
    }

    public static class GroupCompo {

        private int groupId;
        private String groupName;
        private int qty;
        private List<AddRateProductModel> item;

        public GroupCompo(int groupId, String groupName, int qty, List<AddRateProductModel> item) {
            this.groupId = groupId;
            this.groupName = groupName;
            this.qty = qty;
            this.item = item;
        }

        public int getGroupId() {
            return groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public int getQty() {
            return qty;
        }

        public List<AddRateProductModel> getItem() {
            return item;
        }
    }


    public AddRateProductModel(String product_id, String room_rate_price_id,
                               String qty, String tax,
                               String price, int isPriceChanged,
                               String productInitial,
                               List<AlaCarte> alacart,
                               List<Group> group) {
        this.product_id = product_id;
        this.room_rate_price_id = room_rate_price_id;
        this.qty = qty;
        this.tax = tax;
        this.price = price;
        this.is_price_changed = isPriceChanged;
        this.product_initial = productInitial;
        this.alacart = alacart;
        this.group = group;
    }




    public List<AlaCarte> getAlaCarteList() {
        return alacart;
    }

    public List<Group> getGroupList() {
        return group;
    }

    public String getProduct_initial() {
        return product_initial;
    }

    public void setIs_price_changed(int is_price_changed) {
        this.is_price_changed = is_price_changed;
    }

    public int getIs_price_changed() {
        return is_price_changed;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getRoom_rate_price_id() {
        return room_rate_price_id;
    }

    public String getQty() {
        return qty;
    }

    public String getTax() {
        return tax;
    }
}
