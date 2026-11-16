import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();

    // 1. Add Student
    public void addStudent(Scanner sc) {

        System.out.print("Enter Student ID / Roll No: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Semester: ");
        int semester = sc.nextInt();
        sc.nextLine();

        // Check duplicate student
        for (Student s : students) {

            if (s.getStudentId() == id &&
                s.getDepartment().equalsIgnoreCase(department) &&
                s.getSemester() == semester) {

                System.out.println(
                    "Student already exists in this department and semester!"
                );
                return;
            }
        }

        Student student = new Student(
            id, name, age, department, semester
        );

        students.add(student);

        System.out.println("Student added successfully!");
    }

    // 2. View All Students in Table Format
    public void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\n======================= STUDENT DETAILS =======================");

        System.out.printf(
            "%-12s %-20s %-8s %-15s %-10s%n",
            "Roll No", "Name", "Age", "Department", "Semester"
        );

        System.out.println("----------------------------------------------------------------");

        for (Student s : students) {

            System.out.printf(
                "%-12d %-20s %-8d %-15s %-10d%n",
                s.getStudentId(),
                s.getName(),
                s.getAge(),
                s.getDepartment(),
                s.getSemester()
            );
        }

        System.out.println("----------------------------------------------------------------");
    }

    // 3. Search Student
    public void searchStudent(Scanner sc) {

        System.out.print("Enter Student ID / Roll No: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Semester: ");
        int semester = sc.nextInt();
        sc.nextLine();

        boolean found = false;

        for (Student s : students) {

            if (s.getStudentId() == id &&
                s.getDepartment().equalsIgnoreCase(department) &&
                s.getSemester() == semester) {

                System.out.println("\n================ STUDENT FOUND ================");

                System.out.printf(
                    "%-12s %-20s %-8s %-15s %-10s%n",
                    "Roll No", "Name", "Age", "Department", "Semester"
                );

                System.out.println("----------------------------------------------------------------");

                System.out.printf(
                    "%-12d %-20s %-8d %-15s %-10d%n",
                    s.getStudentId(),
                    s.getName(),
                    s.getAge(),
                    s.getDepartment(),
                    s.getSemester()
                );

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }
}
