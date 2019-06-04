package nerdvana.com.pointofsales.model;

import java.util.List;

public class SelectedProductsInBundleModel {
    private int groupId;
    private String groupName;
    private int position;
    private int maxQty;
    private int totalQtySelected;
    private double bundleAmount;
    private List<BundleProductModel> bundleProductModelList;

    public SelectedProductsInBundleModel(int groupId, String groupName,
                                         int position, List<BundleProductModel> bundleProductModelList,
                                         int maxQty, int totalQtySelected,
                                         double bundleAmount) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.position = position;
        this.bundleProductModelList = bundleProductModelList;
        this.maxQty = maxQty;
        this.totalQtySelected = totalQtySelected;
        this.bundleAmount = bundleAmount;
    }

    public double getBundleAmount() {
        return bundleAmount;
    }

    public void setTotalQtySelected(int totalQtySelected) {
        this.totalQtySelected = totalQtySelected;
    }

    public int getTotalQtySelected() {
        return totalQtySelected;
    }

    public int getMaxQty() {
        return maxQty;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getPosition() {
        return position;
    }

    public List<BundleProductModel> getBundleProductModelList() {
        return bundleProductModelList;
    }

    public static class BundleProductModel {
        private int productId;
        private String name;
        private String url;
        private int qty;
        private double amount;
        public BundleProductModel(String name, String url, int qty, int productId, double amount) {
            this.name = name;
            this.url = url;
            this.qty = qty;
            this.productId = productId;
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }

        public int getProductId() {
            return productId;
        }

        public int getQty() {
            return qty;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
    }
}
