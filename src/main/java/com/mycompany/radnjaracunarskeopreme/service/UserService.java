package com.mycompany.radnjaracunarskeopreme.service;

import com.mycompany.radnjaracunarskeopreme.dao.UserDao;
import com.mycompany.radnjaracunarskeopreme.data.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService(UserDao userDAO) {
        this.userDao = userDAO;
    }
    
    public static UserService getInstance(UserDao userDao) {
        if (instance == null) {
            instance = new UserService(userDao);
        }
        return instance;
    }

    public User createUser(User user) throws SQLException {
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email je već zauzet.");
        }
        userDao.insert(user);
        return user;
    }

    public User getUserById(int id) throws SQLException {
        return userDao.find(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDao.findAll();
    }

    public void updateUser(User user) throws SQLException {
        userDao.update(user);
    }

    public void deleteUser(int id) throws SQLException {
        userDao.delete(id);
    }
}