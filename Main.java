// Main.java
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static ResultDAO resultDAO = new ResultDAO();
    private static ExamService examService = new ExamService(scanner);

    public static void main(String[] args) {
        System.out.println("=== Welcome to Online Exam Portal ===");
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    loginAndTakeExam();
                    break;
                case "3":
                    viewPreviousResults();
                    break;
                case "4":
                    System.out.println("Exiting... Bye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n1. Register");
        System.out.println("2. Login & Take Exam");
        System.out.println("3. View Previous Results");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static void register() {
        System.out.println("\n-- Register --");
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        boolean ok = userDAO.register(user);
        if (ok) {
            System.out.println("Registration successful. Your user id: " + user.getId());
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void loginAndTakeExam() {
        System.out.println("\n-- Login --");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = userDAO.login(email, password);
        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }
        System.out.println("Welcome, " + user.getName() + "!");
        examService.takeExam(user);
    }

    private static void viewPreviousResults() {
        System.out.println("\n-- View Results --");
        System.out.print("Enter your registered email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        User user = userDAO.login(email, password);
        if (user == null) {
            System.out.println("Invalid credentials.");
            return;
        }

        List<Result> results = resultDAO.getResultsByUserId(user.getId());
        if (results.isEmpty()) {
            System.out.println("No previous results found.");
            return;
        }

        System.out.println("\nPrevious Results for " + user.getName() + ":");
        System.out.printf("%-5s %-10s %-8s %-12s\n", "ID", "Subject", "Score", "Exam Date");
        for (Result r : results) {
            System.out.printf("%-5d %-10s %-8d %-12s\n", r.getId(), r.getSubject(), r.getScore(), r.getExamDate().toString());
        }
    }
}
