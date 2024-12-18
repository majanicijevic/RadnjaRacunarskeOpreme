package com.mycompany.radnjaracunarskeopreme.service;

import com.mycompany.radnjaracunarskeopreme.dao.SearchDao;
import com.mycompany.radnjaracunarskeopreme.data.Search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class SearchService {
    private SearchDao searchDao;

    public SearchService(SearchDao searchDAO) {
        this.searchDao = searchDAO;
    }

    public Search createSearch(Search search) throws SQLException {
        if (search.getKeyword() == null || search.getKeyword().trim().isEmpty()) {
            throw new IllegalArgumentException("Kljucna rec ne sme biti prazna.");
        }
        searchDao.insert(search);
        return search;
    }

    public Search getSearchById(int id) throws SQLException {
        return searchDao.find(id);
    }

    public List<Search> getAllSearches() throws SQLException {
        return searchDao.findAll();
    }

    public void updateSearch(Search search) throws SQLException {
        if (search.getKeyword() == null || search.getKeyword().trim().isEmpty()) {
            throw new IllegalArgumentException("Kljucna rec ne sme biti prazna.");
        }
        searchDao.update(search);
    }

    public void deleteSearch(int id) throws SQLException {
        searchDao.delete(id);
    }
}
