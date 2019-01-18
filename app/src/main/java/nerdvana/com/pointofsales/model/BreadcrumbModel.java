package nerdvana.com.pointofsales.model;

import java.util.List;

public class BreadcrumbModel {
    private String name;
    private Integer index;
    private List<ProductsModel> prodList;
    public BreadcrumbModel(String name, Integer index, List<ProductsModel> prodList) {
        this.name = name;
        this.index = index;
        this.prodList = prodList;
    }

    public List<ProductsModel> getProdList() {
        return prodList;
    }

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }
}
