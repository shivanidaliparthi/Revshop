package com.revshop.util;



import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "SYSTEM",
                "Jaya0919"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
