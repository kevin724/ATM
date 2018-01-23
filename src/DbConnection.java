import java.sql.*;
public class DbConnection {
    private final String URL = "jdbc:mysql://localhost:3306/ATM?useSSL=false";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String USERNAME = "root";
    private final String PASSWORD = "Anyounghasaeyo970724";
    private Connection conn;

    public DbConnection() {
        try
        {
            Class.forName(DRIVER);
            this.conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public Connection getConn() {
        return this.conn;
    }
}
