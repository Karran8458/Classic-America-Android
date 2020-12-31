package com.hvac.classicamerice.ui.ProductSpecs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductSpecsViewModel extends ViewModel {

    private String MainProductName;

    private String ProductName;
    private String ProductImage;
    private String ProductDesc;
    private String ProductDiagram;
    private String ProductSpecs;
    private String ProductURL;
    private String Product_count;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductDesc() {
        return ProductDesc;
    }

    public void setProductDesc(String productDesc) {
        ProductDesc = productDesc;
    }

    public String getProductDiagram() {
        return ProductDiagram;
    }

    public void setProductDiagram(String productDiagram) {
        ProductDiagram = productDiagram;
    }

    public String getProductSpecs() {
        return ProductSpecs;
    }

    public void setProductSpecs(String productSpecs) {
        ProductSpecs = productSpecs;
    }

    public String getProductURL() {
        return ProductURL;
    }

    public void setProductURL(String productURL) {
        ProductURL = productURL;
    }

    public String getProduct_count() {
        return Product_count;
    }

    public void setProduct_count(String product_count) {
        Product_count = product_count;
    }

    public String getMainProductName() {
        return MainProductName;
    }

    public void setMainProductName(String mainProductName) {
        MainProductName = mainProductName;
    }

}