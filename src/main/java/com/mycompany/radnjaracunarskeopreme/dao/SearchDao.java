package com.mycompany.radnjaracunarskeopreme.dao;

import com.mycompany.radnjaracunarskeopreme.data.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchDao {
    private static SearchDao instance;
    private Connection connection;

    private SearchDao(Connection connection) {
        this.connection = connection;
    }
    
    public static SearchDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new SearchDao(connection);
        }
        return instance;
    }
    
    public Search find(int id) throws SQLException {
        String query = "SELECT * FROM search WHERE id_search = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Search search = new Search();
            search.setIdSearch(rs.getInt("id_search"));
            search.setFkUser(rs.getInt("fk_user"));
            search.setFkSearchSettings(rs.getInt("fk_search_settings"));
            search.setKeyword(rs.getString("keyword"));
            search.setSearchDate(rs.getTimestamp("search_date"));
            return search;
        }
        return null;
    }
    
    public void insert(Search search) throws SQLException {
        String query = "INSERT INTO search (fk_user, fk_search_settings, keyword, search_date) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, search.getFkUser());
        stmt.setInt(2, search.getFkSearchSettings());
        stmt.setString(3, search.getKeyword());
        stmt.setTimestamp(4, search.getSearchDate());
        stmt.executeUpdate();
    }

    public void update(Search search) throws SQLException {
        String query = "UPDATE search SET fk_user = ?, fk_search_settings = ?, keyword = ?, search_date = ? WHERE id_search = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, search.getFkUser());
        stmt.setInt(2, search.getFkSearchSettings());
        stmt.setString(3, search.getKeyword());
        stmt.setTimestamp(4, search.getSearchDate());
        stmt.setInt(5, search.getIdSearch());
        stmt.executeUpdate();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM search WHERE id_search = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    
    // Додат метод за враћање листе свих претрага
    public List<Search> findAll() throws SQLException {
        List<Search> searches = new ArrayList<>();
        String query = "SELECT * FROM search";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Search search = new Search();
            search.setIdSearch(rs.getInt("id_search"));
            search.setFkUser(rs.getInt("fk_user"));
            search.setFkSearchSettings(rs.getInt("fk_search_settings"));
            search.setKeyword(rs.getString("keyword"));
            search.setSearchDate(rs.getTimestamp("search_date"));
            searches.add(search);
        }
        return searches;
    }
}

