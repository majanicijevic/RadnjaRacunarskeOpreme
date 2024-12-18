package com.mycompany.radnjaracunarskeopreme.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.radnjaracunarskeopreme.exception.RadnjaRacunarskeOpremeException;

public class ResourcesManager {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/radnja_racunarske_opreme?user=root&password=&useSSL=false&serverTimezone=UTC");
        return con;
    }

    public static void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }

    public static void closeConnection(Connection con) throws RadnjaRacunarskeOpremeException {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RadnjaRacunarskeOpremeException("Failed to close database connection.", ex);
            }
        }
    }

    public static void rollbackTransactions(Connection con) throws RadnjaRacunarskeOpremeException {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RadnjaRacunarskeOpremeException("Failed to rollback database transactions.", ex);
            }
        }
    }
}

