package com.graminvikreta_supplier.Model;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {

    @SerializedName("productId")
    public String productId;


    @SerializedName("productName")
    public String productName;

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
}
