package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_responses.FetchRoomPendingResponse;

public class DiscountListModel {
    private String department;
    private List<DiscountProduct> discountProductList;
    private List<FetchRoomPendingResponse.Discount> discountList;
    private boolean isChecked;

    public static class DiscountProduct {
        private String postId;
        private String controlNo;
        private String price;
        private String total;
        private String discount;
        private String name;
        private boolean isChecked;
        public DiscountProduct(String postId, String controlNo,
                               String price, String total,
                               String discount, String name,
                               boolean isChecked) {
            this.postId = postId;
            this.controlNo = controlNo;
            this.price = price;
            this.total = total;
            this.discount = discount;
            this.name = name;
            this.isChecked = isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPostId() {
            return postId;
        }

        public String getControlNo() {
            return controlNo;
        }

        public String getPrice() {
            return price;
        }

        public String getTotal() {
            return total;
        }

        public String getDiscount() {
            return discount;
        }
    }

    public List<DiscountProduct> getDiscountProductList() {
        return discountProductList;
    }

    public void setDiscountProductList(List<DiscountProduct> discountProductList) {
        this.discountProductList = discountProductList;
    }

    public List<FetchRoomPendingResponse.Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<FetchRoomPendingResponse.Discount> discountList) {
        this.discountList = discountList;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public DiscountListModel(String department, List<DiscountProduct> discountProductList,
                             List<FetchRoomPendingResponse.Discount> discountList, boolean isChecked) {
        this.department = department;
        this.discountProductList = discountProductList;
        this.discountList = discountList;
        this.isChecked = true;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
