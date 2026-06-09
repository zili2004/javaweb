package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBCon {
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    public Connection dbCon() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/sams?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8&allowPublicKeyRetrieval=true";
            String username = "root";
            
            // Your real password
            String password = "123456"; 

            InputStream is = DBCon.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (is != null) {
                Properties prop = new Properties();
                prop.load(is);
                driver = prop.getProperty("driver");
                url = prop.getProperty("url");
                username = prop.getProperty("username");
                password = prop.getProperty("password");
                System.out.println("[INFO] Read db.properties successfully!");
            } else {
                System.out.println("[WARN] Cannot find db.properties, using hardcoded config!");
            }

            System.out.println("[INFO] Connecting to DB: " + url);
            
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            
            System.out.println("[INFO] DB Connection SUCCESS!");

        } catch (Exception e) {
            System.err.println("[ERROR] DB Connection FAILED:");
            e.printStackTrace();
            throw new RuntimeException("DB Connection Error: " + e.getMessage());
        }
        return con;
    }

    public int query(String sql) {
        int result = 0;
        try {
            con = dbCon();
            st = con.createStatement();
            result = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public ResultSet find(String sql) {
        try {
            con = dbCon();
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            close();
            e.printStackTrace();
        }
        return rs;
    }

    public void close() {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}