package com.mycompany.radnjaracunarskeopreme.data;

import java.sql.Timestamp;

public class Purchase {
    private int idPurchase;
    private int fkUser;
    private int fkProduct;
    private Timestamp purchaseDate;

    // Getters and Setters
    public int getIdPurchase() { return idPurchase; }
    public void setIdPurchase(int idPurchase) { this.idPurchase = idPurchase; }

    public int getFkUser() { return fkUser; }
    public void setFkUser(int fkUser) { this.fkUser = fkUser; }

    public int getFkProduct() { return fkProduct; }
    public void setFkProduct(int fkProduct) { this.fkProduct = fkProduct; }

    public Timestamp getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Timestamp purchaseDate) { this.purchaseDate = purchaseDate; }
}