package com.mycompany.radnjaracunarskeopreme.data;

import java.sql.Timestamp;

public class Search {
    private int idSearch;
    private int fkUser;
    private int fkSearchSettings;
    private String keyword;
    private Timestamp searchDate;

    // Getters and Setters
    public int getIdSearch() { return idSearch; }
    public void setIdSearch(int idSearch) { this.idSearch = idSearch; }

    public int getFkUser() { return fkUser; }
    public void setFkUser(int fkUser) { this.fkUser = fkUser; }

    public int getFkSearchSettings() { return fkSearchSettings; }
    public void setFkSearchSettings(int fkSearchSettings) { this.fkSearchSettings = fkSearchSettings; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public Timestamp getSearchDate() { return searchDate; }
    public void setSearchDate(Timestamp searchDate) { this.searchDate = searchDate; }
}