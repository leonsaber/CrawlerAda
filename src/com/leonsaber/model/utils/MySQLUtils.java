package com.leonsaber.model.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLUtils {

    private static final String URL = "jdbc:mysql://leonsaberdb.ccb34yvn70hs.us-west-2.rds.amazonaws.com/ada_rs_db";
    private static final String USERNAME = "leonsaber";
    private static final String PASSWORD = "8eightwing";
    public void createProduct(String url,
                              String productName,
                              int productID,
                              double productPrice,
                              String productStatus,
                              int qty) {

        String sql = "INSERT INTO ada_products (product_name, product_id, product_price, product_qty, product_status, product_url) VALUES('%s', '%s', '%s', '%s', '%s', '%s')";

        String formatSQL = String.format(sql, productName, productID, productPrice, qty, productStatus, url);

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(formatSQL);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
