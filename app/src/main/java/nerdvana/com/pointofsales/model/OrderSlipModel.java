package nerdvana.com.pointofsales.model;

import java.util.List;

public class OrderSlipModel {
    private String orderSlipId;
    private String controlNo;
    List<OrderSlipInfo> orderSlipInfoList;



    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public List<OrderSlipInfo> getOrderSlipInfoList() {
        return orderSlipInfoList;
    }

    public void setOrderSlipInfoList(List<OrderSlipInfo> orderSlipInfoList) {
        this.orderSlipInfoList = orderSlipInfoList;
    }

    public String getOrderSlipId() {
        return orderSlipId;
    }

    public void setOrderSlipId(String orderSlipId) {
        this.orderSlipId = orderSlipId;
    }

    public OrderSlipModel(String controlNo, List<OrderSlipInfo> orderSlipInfoList, String orderSlipId) {
        this.controlNo = controlNo;
        this.orderSlipInfoList = orderSlipInfoList;
        this.orderSlipId = orderSlipId;


    }

    public static class OrderSlipInfo {
        private String id;
        private String postOrderTransId;
        private String postTransId;
        List<OrderSlipProduct> orderSlipProductList;

        public OrderSlipInfo(String id, String postOrderTransId, String postTransId, List<OrderSlipProduct> orderSlipProductList) {
            this.id = id;
            this.postOrderTransId = postOrderTransId;
            this.postTransId = postTransId;
            this.orderSlipProductList = orderSlipProductList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPostOrderTransId() {
            return postOrderTransId;
        }

        public void setPostOrderTransId(String postOrderTransId) {
            this.postOrderTransId = postOrderTransId;
        }

        public String getPostTransId() {
            return postTransId;
        }

        public void setPostTransId(String postTransId) {
            this.postTransId = postTransId;
        }

        public List<OrderSlipProduct> getOrderSlipProductList() {
            return orderSlipProductList;
        }

        public void setOrderSlipProductList(List<OrderSlipProduct> orderSlipProductList) {
            this.orderSlipProductList = orderSlipProductList;
        }
    }

    public static class OrderSlipProduct {
        private String orderId;
        private String productId;
        private String productName;
        private String productInitial;
        private String roomTypeId;
        private String roomRateTypeId;
        private String roomRatePriceId;
        private String roomType;
        private String roomRate;
        private String quantity;
        private String unitCost;
        private String price;
        private String total;
        private boolean isVoided;


        public OrderSlipProduct(String orderId, String productId, String productName, String productInitial, String roomTypeId, String roomRateTypeId, String roomRatePriceId, String roomType, String roomRate, String quantity, String unitCost, String price, String total, boolean isVoided) {
            this.orderId = orderId;
            this.productId = productId;
            this.productName = productName;
            this.productInitial = productInitial;
            this.roomTypeId = roomTypeId;
            this.roomRateTypeId = roomRateTypeId;
            this.roomRatePriceId = roomRatePriceId;
            this.roomType = roomType;
            this.roomRate = roomRate;
            this.quantity = quantity;
            this.unitCost = unitCost;
            this.price = price;
            this.total = total;
            this.isVoided = isVoided;
        }



        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductInitial() {
            return productInitial;
        }

        public void setProductInitial(String productInitial) {
            this.productInitial = productInitial;
        }

        public String getRoomTypeId() {
            return roomTypeId;
        }

        public void setRoomTypeId(String roomTypeId) {
            this.roomTypeId = roomTypeId;
        }

        public String getRoomRateTypeId() {
            return roomRateTypeId;
        }

        public void setRoomRateTypeId(String roomRateTypeId) {
            this.roomRateTypeId = roomRateTypeId;
        }

        public String getRoomRatePriceId() {
            return roomRatePriceId;
        }

        public void setRoomRatePriceId(String roomRatePriceId) {
            this.roomRatePriceId = roomRatePriceId;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getRoomRate() {
            return roomRate;
        }

        public void setRoomRate(String roomRate) {
            this.roomRate = roomRate;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnitCost() {
            return unitCost;
        }

        public void setUnitCost(String unitCost) {
            this.unitCost = unitCost;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public boolean isVoided() {
            return isVoided;
        }

        public void setVoided(boolean voided) {
            isVoided = voided;
        }
    }
}
