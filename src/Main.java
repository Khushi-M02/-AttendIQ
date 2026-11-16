import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentManager sm = new StudentManager();
        AttendanceManager am = new AttendanceManager();

        int choice;

        System.out.println("========================================");
        System.out.println("       ATTENDIQ ATTENDANCE SYSTEM");
        System.out.println("========================================");

        do {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Student Management");
            System.out.println("2. Attendance Management");
            System.out.println("3. Attendance Calculator");
            System.out.println("4. Attendance Reports");
            System.out.println("5. Exit");

            System.out.print("\nEnter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    studentMenu(sm, sc);
                    break;

                case 2:
                    attendanceMenu(am, sc);
                    break;

                case 3:
                    am.calculateAttendance(sc);
                    break;

                case 4:
                    attendanceReportsMenu(am, sc);
                    break;

                case 5:
                    System.out.println("\nThank you for using AttendIQ!");
                    System.out.println("Exiting application...");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-5.");
            }

        } while (choice != 5);

        sc.close();
    }

    // STUDENT MANAGEMENT
    public static void studentMenu(StudentManager sm, Scanner sc) {

        int choice;

        do {
            System.out.println("\n====== STUDENT MANAGEMENT ======");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    sm.addStudent(sc);
                    break;

                case 2:
                    sm.viewStudents();
                    break;

                case 3:
                    sm.searchStudent(sc);
                    break;

                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-4.");
            }

        } while (choice != 4);
    }

    // ATTENDANCE MANAGEMENT
    public static void attendanceMenu(AttendanceManager am, Scanner sc) {

        int choice;

        do {
            System.out.println("\n====== ATTENDANCE MANAGEMENT ======");
            System.out.println("1. Record Attendance");
            System.out.println("2. View Attendance Records");
            System.out.println("3. Back to Main Menu");

            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    am.recordAttendance(sc);
                    break;

                case 2:
                    am.viewAttendance();
                    break;

                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-3.");
            }

        } while (choice != 3);
    }

    // ATTENDANCE REPORTS
    public static void attendanceReportsMenu(
            AttendanceManager am, Scanner sc) {

        int choice;

        do {
            System.out.println("\n====== ATTENDANCE REPORTS ======");
            System.out.println("1. Students Below 75%");
            System.out.println("2. Shortage Prediction");
            System.out.println("3. Weekly Attendance Report");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    am.displayShortageStudents();
                    break;

                case 2:
                    am.calculateRequiredClasses(sc);
                    break;

                case 3:
                    am.weeklyAttendanceReport(sc);
                    break;

                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-4.");
            }

        } while (choice != 4);
    }
}