package com.mycompany.radnjaracunarskeopreme.data;

import java.math.BigDecimal;

public class SearchSettings {
    private int idSearchSettings;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private String productType;

    // Getters and Setters
    public int getIdSearchSettings() { return idSearchSettings; }
    public void setIdSearchSettings(int idSearchSettings) { this.idSearchSettings = idSearchSettings; }

    public BigDecimal getPriceMin() { return priceMin; }
    public void setPriceMin(BigDecimal priceMin) { this.priceMin = priceMin; }

    public BigDecimal getPriceMax() { return priceMax; }
    public void setPriceMax(BigDecimal priceMax) { this.priceMax = priceMax; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
}
