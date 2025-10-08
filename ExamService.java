// ExamService.java
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExamService {
    private QuestionDAO questionDAO = new QuestionDAO();
    private ResultDAO resultDAO = new ResultDAO();
    private Scanner scanner;

    public ExamService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void takeExam(User user) {
        List<Question> questions = questionDAO.getAllQuestions();
        if (questions.isEmpty()) {
            System.out.println("No questions available. Contact admin.");
            return;
        }

        System.out.println("Enter subject for this exam (e.g., Java): ");
        String subject = scanner.nextLine().trim();

        int score = 0;
        int qNo = 1;
        for (Question q : questions) {
            System.out.println("\nQ" + qNo + ": " + q.getQuestionText());
            System.out.println("A. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());
            System.out.print("Your answer (A/B/C/D): ");
            String ans = scanner.nextLine().trim().toUpperCase();
            if (ans.length() > 0 && ans.charAt(0) == q.getCorrectOption()) {
                score++;
            }
            qNo++;
        }

        System.out.println("\nExam finished. Score: " + score + " out of " + questions.size());

        Result result = new Result(user.getId(), score, subject, LocalDate.now());
        boolean saved = resultDAO.saveResult(result);
        if (saved) {
            System.out.println("Result saved successfully.");
        } else {
            System.out.println("Failed to save result.");
        }
    }
}
