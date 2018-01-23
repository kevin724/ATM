import java.util.List;
import java.util.ArrayList;
import java.sql.*;
public class AccountManager {
    private List<Account> accounts;
    private Connection conn;

    public AccountManager(Connection conn) {
        this.accounts = new ArrayList<Account>();
        this.conn = conn;
        this.accounts = this.getAccounts();
    }

    List<Account> getAccounts() {
        //read from database
        try
        {
            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM user";

            // create the java statement
            Statement st = this.conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                double balance = rs.getDouble("balance");
                Account account = new Account(id, password, balance);
                this.accounts.add(account);
            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return this.accounts;
    }

//    List<Account> getAccounts() {
//        //read from database
//        try
//        {
//            // create our mysql database connection
//            String myDriver = "com.mysql.jdbc.Driver";
//            String myUrl = "jdbc:mysql://localhost:3306/ATM?useSSL=false";
//            Class.forName(myDriver);
//            Connection conn = DriverManager.getConnection(myUrl, "root", "Anyounghasaeyo970724");
//
//            // our SQL SELECT query.
//            // if you only need a few columns, specify them by name instead of using "*"
//            String query = "SELECT * FROM user";
//
//            // create the java statement
//            Statement st = conn.createStatement();
//
//            // execute the query, and get a java resultset
//            ResultSet rs = st.executeQuery(query);
//
//            // iterate through the java resultset
//            while (rs.next())
//            {
//                int id = rs.getInt("id");
//                String password = rs.getString("password");
//                double balance = rs.getDouble("balance");
//                Account account = new Account(id, password, balance);
//                this.accounts.add(account);
//            }
//            st.close();
//        }
//        catch (Exception e)
//        {
//            System.err.println("Got an exception! ");
//            System.err.println(e.getMessage());
//        }
//        return this.accounts;
//    }

    public Account getAccount(int id, String password) {
        for (Account account: this.accounts) {
            if (account.isMatching(id, password)) {
                return account;
            }
        }
        return null;
    }

    public void updateBalance(int id, double newBal) {
        try
        {
            // create the java mysql update preparedstatement
            String query = "update user set balance = ? where id = ?";
            PreparedStatement preparedStmt = this.conn.prepareStatement(query);
            preparedStmt.setDouble   (1, newBal);
            preparedStmt.setInt(2, id);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void addAccount(String password) {
        try {
            String query = " insert into user (password, balance)"
                    + " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString (1, password);
            preparedStmt.setDouble(2, 0);

            // execute the preparedstatement
            preparedStmt.execute();

            ResultSet rs = preparedStmt.getGeneratedKeys();
            if(rs.next())
            {
                int lastId = rs.getInt(1);
                System.out.println(lastId);
                this.accounts.add(new Account(lastId, password, 0));
            }
        }
        catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
