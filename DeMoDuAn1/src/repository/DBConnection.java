
package repository;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    public static final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=DB_502689_pro;user=sa;password=321456";

    public static Connection getDBConect() {
        try {
            Connection con = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
            con = DriverManager.getConnection(connectionUrl);
            return con;
        } catch (Exception e) {
            System.out.println("Error Connect: " + e.toString());
        }
        return null;
    }

    
}
