package com.mycompany.radnjaracunarskeopreme.service;

import com.mycompany.radnjaracunarskeopreme.dao.ProductDao;
import com.mycompany.radnjaracunarskeopreme.dao.PurchaseDao;
import com.mycompany.radnjaracunarskeopreme.dao.UserDao;
import com.mycompany.radnjaracunarskeopreme.data.Product;
import com.mycompany.radnjaracunarskeopreme.data.Purchase;
import com.mycompany.radnjaracunarskeopreme.data.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PurchaseService {
    private PurchaseDao purchaseDAO;
    private ProductDao productDAO;
    private UserDao userDAO;
    private Connection connection;

    public PurchaseService(PurchaseDao purchaseDAO, ProductDao productDAO, UserDao userDAO, Connection connection) {
        this.purchaseDAO = purchaseDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.connection = connection;
    }

    public void makePurchase(int userId, int productId) throws SQLException {
        try {
            connection.setAutoCommit(false); // Start transaction

            // Validate product availability
            Product product = productDAO.find(productId);
            if (product == null) {
                throw new IllegalArgumentException("Proizvod ne postoji.");
            }
            if (product.getStock() <= 0) {
                throw new IllegalArgumentException("Proizvod nije dostupan na zalihama.");
            }

            // Validate user balance
            User user = userDAO.find(userId);
            if (user == null) {
                throw new IllegalArgumentException("Korisnik ne postoji.");
            }
            if (user.getAccountBalance().compareTo(product.getPrice()) < 0) {
                throw new IllegalArgumentException("Nedovoljno sredstava na racunu korisnika.");
            }

            // Reduce product stock
            product.setStock(product.getStock() - 1);
            productDAO.update(product);

            // Deduct balance from user's account
            user.setAccountBalance(user.getAccountBalance().subtract(product.getPrice()));
            userDAO.update(user);

            // Insert purchase record
            Purchase purchase = new Purchase();
            purchase.setFkUser(userId);
            purchase.setFkProduct(productId);
            purchaseDAO.insert(purchase);

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            connection.rollback(); // Rollback transaction
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
