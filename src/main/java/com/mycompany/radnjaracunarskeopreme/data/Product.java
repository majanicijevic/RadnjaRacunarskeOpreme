package com.mycompany.radnjaracunarskeopreme.data;

import java.math.BigDecimal;

public class Product {
    private int idProduct;
    private String name;
    private BigDecimal price;
    private String type;
    private int stock;

    // Getters and Setters
    public int getIdProduct() { return idProduct; }
    public void setIdProduct(int idProduct) { this.idProduct = idProduct; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}