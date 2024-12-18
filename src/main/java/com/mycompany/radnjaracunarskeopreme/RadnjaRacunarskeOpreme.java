package com.mycompany.radnjaracunarskeopreme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RadnjaRacunarskeOpreme {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/radnja_racunarske_opreme";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Konekcija uspešno uspostavljena!");

            // Dodavanje korisnika
            String insertUserQuery = "INSERT INTO user (name, email, username, birth_date, account_balance) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertUserQuery)) {
                pstmt.setString(1, "Petar Petrović"); // name
                pstmt.setString(2, "petar.petrovic@gmail.com"); // email
                pstmt.setString(3, "petar123"); // username
                pstmt.setDate(4, java.sql.Date.valueOf("1990-05-20")); // birth_date
                pstmt.setBigDecimal(5, new java.math.BigDecimal("1000.50")); // account_balance

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Dodato " + rowsAffected + " korisnik(a) u tabelu `user`.");
            }

            // Dodavanje proizvoda
            String insertProductQuery = "INSERT INTO product (name, price, type, stock) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertProductQuery)) {
                pstmt.setString(1, "Laptop HP"); // name
                pstmt.setBigDecimal(2, new java.math.BigDecimal("799.99")); // price
                pstmt.setString(3, "Laptop"); // type
                pstmt.setInt(4, 10); // stock

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Dodato " + rowsAffected + " proizvoda u tabelu `product`.");
            }
            
            
            // Dodavanje kupovine
            String insertPurchaseQuery = "INSERT INTO purchase (fk_user, fk_product) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertPurchaseQuery)) {
                pstmt.setInt(1, 1); // fk_user (ID korisnika iz tabele `user`)
                pstmt.setInt(2, 1); // fk_product (ID proizvoda iz tabele `product`)

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Dodato " + rowsAffected + " kupovina u tabelu `purchase`.");
            }
            
            // Dodavanje search_settings podataka
            String insertSearchSettingsQuery = "INSERT INTO search_settings (price_min, price_max, product_type) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSearchSettingsQuery)) {
                pstmt.setBigDecimal(1, new java.math.BigDecimal("500.00")); // price_min
                pstmt.setBigDecimal(2, new java.math.BigDecimal("1500.00")); // price_max
                pstmt.setString(3, "Laptop"); // product_type

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Dodato " + rowsAffected + " postavki pretrage u tabelu `search_settings`.");
            }
            
            // Dodavanje search podataka
            String insertSearchQuery = "INSERT INTO search (fk_user, fk_search_settings, keyword) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSearchQuery)) {
                pstmt.setInt(1, 1); // fk_user (ID korisnika iz tabele `user`)
                pstmt.setInt(2, 1); // fk_search_settings (ID iz tabele `search_settings`)
                pstmt.setString(3, "HP Laptop"); // keyword

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Dodato " + rowsAffected + " pretraga u tabelu `search`.");
            }

        } catch (SQLException e) {
            System.out.println("Greška prilikom povezivanja ili umetanja podataka:");
            e.printStackTrace();
        }
    }
}

