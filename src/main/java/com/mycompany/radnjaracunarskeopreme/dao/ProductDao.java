package com.mycompany.radnjaracunarskeopreme.dao;

import com.mycompany.radnjaracunarskeopreme.data.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    public Product find(int id) throws SQLException {
        String query = "SELECT * FROM product WHERE id_product = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Product product = new Product();
            product.setIdProduct(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setType(rs.getString("type"));
            product.setStock(rs.getInt("stock"));
            return product;
        }
        return null;
    }
    
    public void insert(Product product) throws SQLException {
        String query = "INSERT INTO product (name, price, type, stock) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, product.getName());
        stmt.setBigDecimal(2, product.getPrice());
        stmt.setString(3, product.getType());
        stmt.setInt(4, product.getStock());
        stmt.executeUpdate();
    }

    public void update(Product product) throws SQLException {
        String query = "UPDATE product SET name = ?, price = ?, type = ?, stock = ? WHERE id_product = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, product.getName());
        stmt.setBigDecimal(2, product.getPrice());
        stmt.setString(3, product.getType());
        stmt.setInt(4, product.getStock());
        stmt.setInt(5, product.getIdProduct());
        stmt.executeUpdate();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM product WHERE id_product = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    
    // Додат метод за враћање листе свих производа
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setIdProduct(rs.getInt("id_product"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setType(rs.getString("type"));
            product.setStock(rs.getInt("stock"));
            products.add(product);
        }
        return products;
    }
}