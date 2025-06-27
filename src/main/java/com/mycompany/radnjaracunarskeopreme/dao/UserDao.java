package com.mycompany.radnjaracunarskeopreme.dao;

import com.mycompany.radnjaracunarskeopreme.data.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static UserDao instance;
    private Connection connection;

    private UserDao(Connection connection) {
        this.connection = connection;
    }
    
    public static UserDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new UserDao(connection);
        }
        return instance;
    }

    public User find(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id_user = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setIdUser(rs.getInt("id_user"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setBirthDate(rs.getDate("birth_date"));
            user.setAccountBalance(rs.getBigDecimal("account_balance"));
            return user;
        }
        return null;
    }
    
    public void insert(User user) throws SQLException {
        String query = "INSERT INTO user (name, email, username, birth_date, account_balance) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getUsername());
        stmt.setDate(4, user.getBirthDate());
        stmt.setBigDecimal(5, user.getAccountBalance());
        stmt.executeUpdate();
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE user SET name = ?, email = ?, username = ?, birth_date = ?, account_balance = ? WHERE id_user = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getUsername());
        stmt.setDate(4, user.getBirthDate());
        stmt.setBigDecimal(5, user.getAccountBalance());
        stmt.setInt(6, user.getIdUser());
        stmt.executeUpdate();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM user WHERE id_user = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    
    // Додат метод за враћање листе свих корисника
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setIdUser(rs.getInt("id_user"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setBirthDate(rs.getDate("birth_date"));
            user.setAccountBalance(rs.getBigDecimal("account_balance"));
            users.add(user);
        }
        return users;
    }

    // Додат метод за проналажење корисника по email-у
    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setIdUser(rs.getInt("id_user"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setBirthDate(rs.getDate("birth_date"));
            user.setAccountBalance(rs.getBigDecimal("account_balance"));
            return user;
        }
        return null;
    }
}
