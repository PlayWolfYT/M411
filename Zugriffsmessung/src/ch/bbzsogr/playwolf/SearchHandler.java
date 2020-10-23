/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbzsogr.playwolf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ArraylistSearch;
import model.HashMapSearch;
import model.IndexedSQLSearch;
import model.SQLSearch;
import model.Search;
import model.SearchResult;
import model.SimpleSearchResult;

/**
 *
 * @author PlayWolfYT
 */
public class SearchHandler {
    
    private static Connection connection;
    private static final String MYSQL_HOST = "localhost";
    private static final int MYSQL_PORT = 3306;
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "";
    private static final String MYSQL_DB = "m411_zugriffsmessung";
    
    
    public SearchHandler() {
        try {
            connect();
        } catch (SQLException ex) {
            Logger.getLogger(SearchHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private void connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + MYSQL_DB, MYSQL_USER, MYSQL_PASS);

            if(connection != null) {
                System.out.println("Successfully connected.");
            } else {
                System.out.println("Error when connecting.");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public SearchResult search(String surname, String name) {
        Object[][] tableData;
        
        Search currentSearch;
        SimpleSearchResult result;
        
        long totalProcessingTime = 0;
        
        currentSearch = new SQLSearch();
        result = currentSearch.search(surname, name);
        totalProcessingTime += result.getTime();
        tableData = result.getData();
        
        currentSearch = new IndexedSQLSearch();
        result = currentSearch.search(surname, name);
        totalProcessingTime += result.getTime();
        
        currentSearch = new ArraylistSearch();
        result = currentSearch.search(surname, name);
        totalProcessingTime += result.getTime();
        
        currentSearch = new HashMapSearch();
        result = currentSearch.search(surname, name);
        totalProcessingTime += result.getTime();
        
        return new SearchResult(totalProcessingTime, tableData);
    }
    
    private static Object[][] stripData(Object[][] data) {
        return Arrays.stream(data).filter(arr -> arr.length > 0 && arr[0] != null).toArray(s -> new Object[s][6]);
    }
    
}
