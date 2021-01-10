/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.bbzsogr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.HashMap;


/**
 *
 * @author PlayWolfYT
 */
public class DBToList {
    
    private Connection connection;
    
    private static final String MYSQL_HOST = "localhost";
    private static final int MYSQL_PORT = 3306;
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASS = "";
    private static final String MYSQL_DB = "energiebev";
    
    public DBToList() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + MYSQL_DB, MYSQL_USER, MYSQL_PASS);

            if(connection != null) {
                System.out.println("Successfully connected.");
            } else {
                System.out.println("Error when connecting.");
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBToList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadData() {
        try {
            HashMap<Integer, Integer> data = new HashMap<>();
            
            String query = "SELECT jahr, SUM(betrag) as 'Betrag Total', (SUM(bevch) + SUM(bevaus)) as 'Bev Total' FROM enebev GROUP BY jahr";
            
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery(query);
            
            while(r.next()) {
                                
                System.out.format("%-20s%-20s\n", new Object[]{"Jahr", "Betrag Pro Kopf"});

                
                
                BigDecimal totalVal = new BigDecimal(r.getFloat("Betrag Total"));
                totalVal.multiply(new BigDecimal(10^6));
                
                System.out.println(totalVal.toPlainString());
                
                
                BigDecimal perHead = new BigDecimal(totalVal.toPlainString()); 
                perHead.divide(new BigDecimal(r.getFloat("Bev Total")), RoundingMode.HALF_UP);
                
                Object[] out = new Object[r.getMetaData().getColumnCount()];
                out[0] = r.getFloat("jahr");
                out[1] = perHead.toPlainString();
                
                System.out.format("%-20.0f%-20.0s\n\n", out);
                
            } 
            
        } catch (SQLException ex) {
            Logger.getLogger(DBToList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
