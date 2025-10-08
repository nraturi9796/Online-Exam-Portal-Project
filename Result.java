// Result.java
import java.time.LocalDate;

public class Result {
    private int id;
    private int userId;
    private int score;
    private String subject;
    private LocalDate examDate;

    public Result() {}

    public Result(int userId, int score, String subject, LocalDate examDate) {
        this.userId = userId;
        this.score = score;
        this.subject = subject;
        this.examDate = examDate;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
}
