package com.mycompany.radnjaracunarskeopreme.dao;

import com.mycompany.radnjaracunarskeopreme.data.*;
import java.sql.*;

public class SearchSettingsDao {
    private static SearchSettingsDao instance;
    private Connection connection;

    private SearchSettingsDao(Connection connection) {
        this.connection = connection;
    }
    
    public static SearchSettingsDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new SearchSettingsDao(connection);
        }
        return instance;
    }
    
    public SearchSettings find(int id) throws SQLException {
        String query = "SELECT * FROM search_settings WHERE id_search_settings = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            SearchSettings settings = new SearchSettings();
            settings.setIdSearchSettings(rs.getInt("id_search_settings"));
            settings.setPriceMin(rs.getBigDecimal("price_min"));
            settings.setPriceMax(rs.getBigDecimal("price_max"));
            settings.setProductType(rs.getString("product_type"));
            return settings;
        }
        return null;
    }

    public void insert(SearchSettings settings) throws SQLException {
        String query = "INSERT INTO search_settings (price_min, price_max, product_type) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setBigDecimal(1, settings.getPriceMin());
        stmt.setBigDecimal(2, settings.getPriceMax());
        stmt.setString(3, settings.getProductType());
        stmt.executeUpdate();
    }
    
    public void update(SearchSettings settings) throws SQLException {
        String query = "UPDATE search_settings SET price_min = ?, price_max = ?, product_type = ? WHERE id_search_settings = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setBigDecimal(1, settings.getPriceMin());
        stmt.setBigDecimal(2, settings.getPriceMax());
        stmt.setString(3, settings.getProductType());
        stmt.setInt(4, settings.getIdSearchSettings());
        stmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM search_settings WHERE id_search_settings = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }   
}

