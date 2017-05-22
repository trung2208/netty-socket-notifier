package com.mpcc.springmvc.fb.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

public class DBUtility {

    @Autowired
    HttpSession session;

    private static Connection connection = null;
    public static String ipConnectDB = "";
    public static String user = "";
    public static String pass = "";

    public Connection getConnection() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            return connection;
        } else {
            ipConnectDB = new ResourceBunde().getConfigPath("serverDb");
            user = new ResourceBunde().getConfigPath("accDb");
            pass = new ResourceBunde().getConfigPath("passDb");

            if (connection != null) {
                connection.close();
                connection = null;
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
            String url = "jdbc:mysql://" + ipConnectDB + ":3306/cc_mptelecom?useUnicode=true&characterEncoding=UTF-8";
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println(connection);

            return connection;
        }

    }

    public boolean checkConnectLogin(boolean val) {
        if (val) {
            connection = null;
            return true;
        } else {
            return false;
        }
    }
}
