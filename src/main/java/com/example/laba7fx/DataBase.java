package com.example.laba7fx;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class DataBase {
    public static String DBname;
    public static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private final String url;


    public DataBase(String DBname) {
        this.DBname = DBname;
        url = "jdbc:mysql://localhost:3309/" + DBname;
        con = null;
    }

    public static String getDBname() {
        return DBname;
    }

    public boolean Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "quotes");
            properties.setProperty("password", "12345");
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            con = getConnection(url, properties);
            this.executeQuery("use " + DBname + ";");

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean executeQuery(String query) throws SQLException {
        Statement st = con.createStatement();
        return st.execute(query);
    }

    public boolean insert(String query) throws SQLException {
        executeQuery(query);
        return true;
    }

    public boolean delete(String query) throws SQLException {
        executeQuery(query);
        return true;
    }

    public ObservableList search(String text, String searchСolumn) throws SQLException {
        String query = "SELECT * FROM `m_quotes` WHERE "+searchСolumn+" LIKE ?;";
        PreparedStatement st = this.con.prepareStatement(query);
        st.setString(1, "%" + text + "%");


        ObservableList<Quote> tempSearchData = FXCollections.observableArrayList();
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            tempSearchData.add(new Quote(rs.getString("quote"), rs.getString("author"), rs.getString("series"), rs.getLong("quoteId")));
        }
        return tempSearchData;
    }

}
