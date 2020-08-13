package com.graminvikreta_supplier.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllList {



    @SerializedName("productResponse")
    private List<ProductResponse> productResponseList;


    public List<ProductResponse> getProductResponseList() {
        return productResponseList;
    }

    public void setProductResponseList(List<ProductResponse> productResponseList) {
        this.productResponseList = productResponseList;
    }
}
