// ResultDAO.java
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    public boolean saveResult(Result result) {
        String sql = "INSERT INTO results (user_id, score, subject, exam_date) VALUES (?,?,?,?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, result.getUserId());
            ps.setInt(2, result.getScore());
            ps.setString(3, result.getSubject());
            ps.setDate(4, Date.valueOf(result.getExamDate()));
            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) result.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Result> getResultsByUserId(int userId) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE user_id = ? ORDER BY exam_date DESC";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Result r = new Result();
                    r.setId(rs.getInt("id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setScore(rs.getInt("score"));
                    r.setSubject(rs.getString("subject"));
                    r.setExamDate(rs.getDate("exam_date").toLocalDate());
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
