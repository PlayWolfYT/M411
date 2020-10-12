package ch.bbzsogr.playwolf.hashmap;

import java.sql.*;
import java.util.ArrayList;

public class DBtoList {
    
    protected static final String DB_HOST = "localhost";
    protected static final int DB_PORT = 3306;
    protected static final String DB_DATABASE = "energiebev";
    protected static final String DB_USER = "root";
    protected static final String DB_PASS = "";

    private Connection connection;

    public DBtoList() {
        initConnection();
    }

    /**
     * Initializes the connection to the database.
     */
    private void initConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection == null || connection.isClosed()) {
                final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_DATABASE;
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    /**
     * Loads the data from the database into an ArrayList
     * @return An ArrayList with the data or null.
     */
    public ArrayList<EnergieData> loadData() {
        try {
            String query = "SELECT * FROM enebev ORDER BY jahr DESC";
            
            ArrayList<EnergieData> data = new ArrayList<EnergieData>();

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                final int year = rs.getInt("jahr");
                final int energy_type = rs.getInt("energieform");
                final double amount = rs.getDouble("betrag");
                final int pop_ch = rs.getInt("bevch");
                final int pop_foreign = rs.getInt("bevaus");

                data.add(new EnergieData(year, energy_type, amount, pop_ch, pop_foreign));
            }

            return data;
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
