package nerdvana.com.pointofsales.model;

import java.util.List;

import nerdvana.com.pointofsales.api_requests.CollectionFinalPostModel;

public class SpotAuditModel {
    private String shortOver;

    List<CollectionFinalPostModel> collectionFinalPostModels;

    private String cashSales;

    public SpotAuditModel(String shortOver, List<CollectionFinalPostModel> collectionFinalPostModels,
                        String cashSales) {
        this.shortOver = shortOver;
        this.collectionFinalPostModels = collectionFinalPostModels;
        this.cashSales = cashSales;
    }

    public String getCashSales() {
        return cashSales;
    }

    public String getShortOver() {
        return shortOver;
    }

    public List<CollectionFinalPostModel> getCollectionFinalPostModels() {
        return collectionFinalPostModels;
    }
}
