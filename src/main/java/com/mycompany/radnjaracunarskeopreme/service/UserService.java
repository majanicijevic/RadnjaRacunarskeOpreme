package com.mycompany.radnjaracunarskeopreme.service;

import com.mycompany.radnjaracunarskeopreme.dao.UserDao;
import com.mycompany.radnjaracunarskeopreme.data.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDAO) {
        this.userDao = userDAO;
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