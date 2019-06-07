package nerdvana.com.pointofsales.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchRoomRatePriceIdResponse {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Product {
        @SerializedName("amount")
        @Expose
        private double amount;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("core_id")
        @Expose
        private int coreId;
        @SerializedName("product")
        @Expose
        private String product;
        @SerializedName("product_initial")
        @Expose
        private String productInitial;
        @SerializedName("branch_alacart")
        @Expose
        private List<FetchProductsResponse.BranchAlaCart> branchAlaCart;
        @SerializedName("branch_group")
        @Expose
        private List<FetchProductsResponse.BranchGroup> branchGroup;

        @SerializedName("mark_up")
        @Expose
        private double markUp;

        public double getMarkUp() {
            return markUp;
        }

        public void setMarkUp(double markUp) {
            this.markUp = markUp;
        }


        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCoreId() {
            return coreId;
        }

        public void setCoreId(int coreId) {
            this.coreId = coreId;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getProductInitial() {
            return productInitial;
        }

        public void setProductInitial(String productInitial) {
            this.productInitial = productInitial;
        }

        public List<FetchProductsResponse.BranchAlaCart> getBranchAlaCart() {
            return branchAlaCart;
        }

        public void setBranchAlaCart(List<FetchProductsResponse.BranchAlaCart> branchAlaCart) {
            this.branchAlaCart = branchAlaCart;
        }

        public List<FetchProductsResponse.BranchGroup> getBranchGroup() {
            return branchGroup;
        }

        public void setBranchGroup(List<FetchProductsResponse.BranchGroup> branchGroup) {
            this.branchGroup = branchGroup;
        }
    }

    public class ProductBundle {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("qty")
        @Expose
        private int qty;
        @SerializedName("product")
        @Expose
        private Product product;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

    public class Result {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("room_rate_id")
        @Expose
        private int roomRateId;
        @SerializedName("currency_id")
        @Expose
        private int currencyId;
        @SerializedName("amount")
        @Expose
        private double amount;
        @SerializedName("product_bundle")
        @Expose
        private List<ProductBundle> productBundleList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRoomRateId() {
            return roomRateId;
        }

        public void setRoomRateId(int roomRateId) {
            this.roomRateId = roomRateId;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public List<ProductBundle> getProductBundleList() {
            return productBundleList;
        }

        public void setProductBundleList(List<ProductBundle> productBundleList) {
            this.productBundleList = productBundleList;
        }
    }
}
