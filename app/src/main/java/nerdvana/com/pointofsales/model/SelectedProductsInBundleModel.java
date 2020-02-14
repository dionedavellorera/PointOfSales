package nerdvana.com.pointofsales.model;

import java.util.List;

public class SelectedProductsInBundleModel {
    private int groupId;
    private String groupName;
    private int position;
    private Double maxQty;
    private int totalQtySelected;
    private double bundleAmount;
    private List<BundleProductModel> bundleProductModelList;

    public SelectedProductsInBundleModel(int groupId, String groupName,
                                         int position, List<BundleProductModel> bundleProductModelList,
                                         Double maxQty, int totalQtySelected,
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

    public Double getMaxQty() {
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
        private double addPrice;
        public BundleProductModel(String name, String url,
                                  int qty, int productId,
                                  double amount, double addPrice) {
            this.name = name;
            this.url = url;
            this.qty = qty;
            this.productId = productId;
            this.amount = amount;
            this.addPrice = addPrice;
        }

        public double getAddPrice() {
            return addPrice;
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
