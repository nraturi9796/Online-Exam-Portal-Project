// UserDAO.java
import java.sql.*;

public class UserDAO {

    public boolean register(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) user.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email already registered.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String email, String password) {
        String sql = "SELECT id, name, email, password FROM users WHERE email = ? AND password = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
