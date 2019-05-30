package nerdvana.com.pointofsales.model;

import java.util.List;

public class SelectedProductsInBundleModel {
    private int groupId;
    private String groupName;
    private int position;
    private List<BundleProductModel> bundleProductModelList;

    public SelectedProductsInBundleModel(int groupId, String groupName, int position, List<BundleProductModel> bundleProductModelList) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.position = position;
        this.bundleProductModelList = bundleProductModelList;
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
        private String name;
        private String url;
        private int qty;
        public BundleProductModel(String name, String url, int qty) {
            this.name = name;
            this.url = url;
            this.qty = qty;
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
