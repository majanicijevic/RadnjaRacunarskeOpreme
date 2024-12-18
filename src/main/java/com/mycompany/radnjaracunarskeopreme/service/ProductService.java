package com.mycompany.radnjaracunarskeopreme.service;

import com.mycompany.radnjaracunarskeopreme.dao.ProductDao;
import com.mycompany.radnjaracunarskeopreme.data.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDAO) {
        this.productDao = productDAO;
    }

    public Product createProduct(Product product) throws SQLException {
        if (product.getPrice().compareTo(new java.math.BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException("Cena proizvoda mora biti veća od nule.");
        }
        productDao.insert(product);
        return product;
    }

    public Product getProductById(int id) throws SQLException {
        return productDao.find(id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDao.findAll();
    }

    public void updateProduct(Product product) throws SQLException {
        if (product.getPrice().compareTo(new java.math.BigDecimal("0")) <= 0) {
            throw new IllegalArgumentException("Cena proizvoda mora biti veća od nule.");
        }
        productDao.update(product);
    }

    public void deleteProduct(int id) throws SQLException {
        productDao.delete(id);
    }
}
