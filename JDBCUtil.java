// JDBCUtil.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/online_exam?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "your_db_user";
    private static final String DB_PASS = "your_db_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8+ driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
