package com.mycompany.radnjaracunarskeopreme.dao;

import com.mycompany.radnjaracunarskeopreme.data.*;
import java.sql.*;

public class PurchaseDao {
    private static PurchaseDao instance;
    private Connection connection;

    private PurchaseDao(Connection connection) {
        this.connection = connection;
    }
    
    public static PurchaseDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new PurchaseDao(connection);
        }
        return instance;
    }
    
    public Purchase find(int id) throws SQLException {
        String query = "SELECT * FROM purchase WHERE id_purchase = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Purchase purchase = new Purchase();
            purchase.setIdPurchase(rs.getInt("id_purchase"));
            purchase.setFkUser(rs.getInt("fk_user"));
            purchase.setFkProduct(rs.getInt("fk_product"));
            purchase.setPurchaseDate(rs.getTimestamp("purchase_date"));
            return purchase;
        }
        return null;
    }
    
    public void insert(Purchase purchase) throws SQLException {
        String query = "INSERT INTO purchase (fk_user, fk_product, purchase_date) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, purchase.getFkUser());
        stmt.setInt(2, purchase.getFkProduct());
        stmt.setTimestamp(3, purchase.getPurchaseDate());
        stmt.executeUpdate();
    }
    
    public void update(Purchase purchase) throws SQLException {
        String query = "UPDATE purchase SET fk_user = ?, fk_product = ?, purchase_date = ? WHERE id_purchase = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, purchase.getFkUser());
        stmt.setInt(2, purchase.getFkProduct());
        stmt.setTimestamp(3, purchase.getPurchaseDate());
        stmt.setInt(4, purchase.getIdPurchase());
        stmt.executeUpdate();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM purchase WHERE id_purchase = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
