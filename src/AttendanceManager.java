import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class AttendanceManager {

    private ArrayList<Attendance> attendanceRecords = new ArrayList<>();

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd-MM-uuuu");

    // Helper: Read a positive integer
    private int readPositiveInt(Scanner sc, String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                int value = Integer.parseInt(input);

                if (value > 0) {
                    return value;
                }

                System.out.println("Enter a positive number.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter numbers only.");
            }
        }
    }

    // Helper: Read non-empty text
    private String readText(Scanner sc, String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    // Helper: Read valid date
    private String readDate(Scanner sc) {

        while (true) {
            System.out.print("Enter Date (DD-MM-YYYY): ");
            String date = sc.nextLine().trim();

            try {
                LocalDate.parse(date, formatter);
                return date;

            } catch (DateTimeParseException e) {
                System.out.println(
                    "Invalid date! Please use DD-MM-YYYY."
                );
            }
        }
    }

    // Helper: Read attendance status
    private boolean readAttendanceStatus(Scanner sc) {

        while (true) {
            System.out.print("Is the student present? (yes/no): ");

            String status = sc.nextLine().trim();

            if (status.equalsIgnoreCase("yes")) {
                return true;
            }

            if (status.equalsIgnoreCase("no")) {
                return false;
            }

            System.out.println("Enter only yes or no.");
        }
    }

    // Helper: Check duplicate attendance
    private boolean isDuplicate(int studentId, String department,
                                int semester, String subject, String date) {

        for (Attendance record : attendanceRecords) {

            if (record.getStudentId() == studentId &&
                record.getDepartment().equalsIgnoreCase(department) &&
                record.getSemester() == semester &&
                record.getSubject().equalsIgnoreCase(subject) &&
                record.getDate().equals(date)) {

                return true;
            }
        }

        return false;
    }

    // 1. Record Attendance
    public void recordAttendance(Scanner sc) {

        System.out.println("\n===== RECORD ATTENDANCE =====");

        int studentId = readPositiveInt(
                sc, "Enter Student ID / Roll No: ");

        String department = readText(sc, "Enter Department: ");

        int semester = readPositiveInt(sc, "Enter Semester: ");

        String subject = readText(sc, "Enter Subject: ");

        String date = readDate(sc);

        if (isDuplicate(studentId, department, semester, subject, date)) {
            System.out.println(
                "Duplicate attendance! This record already exists."
            );
            return;
        }

        boolean present = readAttendanceStatus(sc);

        Attendance record = new Attendance(
                studentId, department, semester,
                subject, date, present
        );

        attendanceRecords.add(record);

        System.out.println("Attendance recorded successfully!");
    }

    // 2. View All Attendance Records
    public void viewAttendance() {

        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records available.");
            return;
        }

        System.out.println("\n================ ATTENDANCE RECORDS ================");

        System.out.printf(
            "%-10s %-15s %-10s %-18s %-15s %-10s%n",
            "Roll No", "Department", "Semester",
            "Subject", "Date", "Status"
        );

        System.out.println(
            "----------------------------------------------------------------------------"
        );

        for (Attendance record : attendanceRecords) {

            System.out.printf(
                "%-10d %-15s %-10d %-18s %-15s %-10s%n",
                record.getStudentId(),
                record.getDepartment(),
                record.getSemester(),
                record.getSubject(),
                record.getDate(),
                record.isPresent() ? "Present" : "Absent"
            );
        }

        System.out.println(
            "----------------------------------------------------------------------------"
        );
    }

    // 3. Calculate Attendance Percentage
    public void calculateAttendance(Scanner sc) {

        int studentId = readPositiveInt(sc, "Enter Student ID: ");

        String department = readText(sc, "Enter Department: ");

        int semester = readPositiveInt(sc, "Enter Semester: ");

        String subject = readText(sc, "Enter Subject: ");

        int totalClasses = 0;
        int attendedClasses = 0;

        for (Attendance record : attendanceRecords) {

            if (record.getStudentId() == studentId &&
                record.getDepartment().equalsIgnoreCase(department) &&
                record.getSemester() == semester &&
                record.getSubject().equalsIgnoreCase(subject)) {

                totalClasses++;

                if (record.isPresent()) {
                    attendedClasses++;
                }
            }
        }

        if (totalClasses == 0) {
            System.out.println("No attendance records found.");
            return;
        }

        double percentage =
                attendedClasses * 100.0 / totalClasses;

        System.out.println("\n===== ATTENDANCE SUMMARY =====");

        System.out.println("Student ID       : " + studentId);
        System.out.println("Department       : " + department);
        System.out.println("Semester         : " + semester);
        System.out.println("Subject          : " + subject);
        System.out.println("Total Classes    : " + totalClasses);
        System.out.println("Classes Attended : " + attendedClasses);

        System.out.printf("Attendance       : %.2f%%%n", percentage);

        if (percentage < 75) {
            System.out.println("Status: Attendance Shortage!");
        } else {
            System.out.println("Status: Attendance Satisfactory.");
        }
    }

    // 4. Display Students Below 75%
    public void displayShortageStudents() {

        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records available.");
            return;
        }

        boolean shortageFound = false;

        System.out.println("\n===== ATTENDANCE SHORTAGE REPORT =====");

        for (int i = 0; i < attendanceRecords.size(); i++) {

            Attendance current = attendanceRecords.get(i);

            int studentId = current.getStudentId();
            String department = current.getDepartment();
            int semester = current.getSemester();
            String subject = current.getSubject();

            boolean alreadyChecked = false;

            for (int j = 0; j < i; j++) {

                Attendance previous = attendanceRecords.get(j);

                if (previous.getStudentId() == studentId &&
                    previous.getDepartment().equalsIgnoreCase(department) &&
                    previous.getSemester() == semester &&
                    previous.getSubject().equalsIgnoreCase(subject)) {

                    alreadyChecked = true;
                    break;
                }
            }

            if (alreadyChecked) {
                continue;
            }

            int total = 0;
            int attended = 0;

            for (Attendance record : attendanceRecords) {

                if (record.getStudentId() == studentId &&
                    record.getDepartment().equalsIgnoreCase(department) &&
                    record.getSemester() == semester &&
                    record.getSubject().equalsIgnoreCase(subject)) {

                    total++;

                    if (record.isPresent()) {
                        attended++;
                    }
                }
            }

            double percentage = attended * 100.0 / total;

            if (percentage < 75) {

                shortageFound = true;

                System.out.println("--------------------------------");

                System.out.println("Student ID : " + studentId);
                System.out.println("Department : " + department);
                System.out.println("Semester   : " + semester);
                System.out.println("Subject    : " + subject);

                System.out.printf("Attendance : %.2f%%%n", percentage);

                System.out.println("Status     : SHORTAGE");
            }
        }

        if (!shortageFound) {
            System.out.println("No students have attendance below 75%.");
        }
    }

    // 5. Calculate Consecutive Classes Required
    public void calculateRequiredClasses(Scanner sc) {

        int studentId = readPositiveInt(sc, "Enter Student ID: ");

        String department = readText(sc, "Enter Department: ");

        int semester = readPositiveInt(sc, "Enter Semester: ");

        String subject = readText(sc, "Enter Subject: ");

        int totalClasses = 0;
        int attendedClasses = 0;

        for (Attendance record : attendanceRecords) {

            if (record.getStudentId() == studentId &&
                record.getDepartment().equalsIgnoreCase(department) &&
                record.getSemester() == semester &&
                record.getSubject().equalsIgnoreCase(subject)) {

                totalClasses++;

                if (record.isPresent()) {
                    attendedClasses++;
                }
            }
        }

        if (totalClasses == 0) {
            System.out.println("No attendance records found.");
            return;
        }

        double percentage =
                attendedClasses * 100.0 / totalClasses;

        if (percentage >= 75) {
            System.out.println(
                "Student already has 75% attendance or above."
            );
            return;
        }

        int requiredClasses = 0;

        while (((attendedClasses + requiredClasses) * 100.0)
                / (totalClasses + requiredClasses) < 75) {

            requiredClasses++;
        }

        double futurePercentage =
                (attendedClasses + requiredClasses) * 100.0
                / (totalClasses + requiredClasses);

        System.out.println("\n===== ATTENDANCE RECOVERY REPORT =====");

        System.out.println("Student ID       : " + studentId);
        System.out.println("Department       : " + department);
        System.out.println("Semester         : " + semester);
        System.out.println("Subject          : " + subject);

        System.out.println("Current Classes  : " + totalClasses);
        System.out.println("Classes Attended : " + attendedClasses);

        System.out.println(
            "Required Consecutive Classes: " + requiredClasses
        );

        System.out.printf(
            "Future Attendance: %.2f%%%n", futurePercentage
        );
    }

    // 6. Department-wise Attendance Report
    public void departmentWiseReport(Scanner sc) {

        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records available.");
            return;
        }

        String department = readText(sc, "Enter Department: ");

        int total = 0;
        int present = 0;
        int absent = 0;

        for (Attendance record : attendanceRecords) {

            if (record.getDepartment().equalsIgnoreCase(department)) {

                total++;

                if (record.isPresent()) {
                    present++;
                } else {
                    absent++;
                }
            }
        }

        if (total == 0) {
            System.out.println("No records found for this department.");
            return;
        }

        double percentage = present * 100.0 / total;

        System.out.println("\n===== DEPARTMENT ATTENDANCE REPORT =====");

        System.out.println("Department       : " + department);
        System.out.println("Total Records    : " + total);
        System.out.println("Present          : " + present);
        System.out.println("Absent           : " + absent);

        System.out.printf("Attendance       : %.2f%%%n", percentage);
    }

    // 7. Date-wise Attendance Report
    public void dateWiseReport(Scanner sc) {

        String date = readDate(sc);

        int total = 0;
        int present = 0;
        int absent = 0;

        for (Attendance record : attendanceRecords) {

            if (record.getDate().equals(date)) {

                total++;

                if (record.isPresent()) {
                    present++;
                } else {
                    absent++;
                }
            }
        }

        if (total == 0) {
            System.out.println("No attendance records found for this date.");
            return;
        }

        double percentage = present * 100.0 / total;

        System.out.println("\n===== DATE-WISE ATTENDANCE REPORT =====");

        System.out.println("Date          : " + date);
        System.out.println("Total Records : " + total);
        System.out.println("Present       : " + present);
        System.out.println("Absent        : " + absent);

        System.out.printf("Attendance    : %.2f%%%n", percentage);
    }

    // 8. Student-wise Attendance Percentage Table
    public void percentageTable() {

        if (attendanceRecords.isEmpty()) {
            System.out.println("No attendance records available.");
            return;
        }

        System.out.println("\n===== STUDENT ATTENDANCE PERCENTAGE TABLE =====");

        System.out.printf(
            "%-10s %-15s %-10s %-18s %-10s%n",
            "Roll No", "Department", "Semester",
            "Subject", "Percentage"
        );

        System.out.println(
            "----------------------------------------------------------------"
        );

        for (int i = 0; i < attendanceRecords.size(); i++) {

            Attendance current = attendanceRecords.get(i);

            int studentId = current.getStudentId();
            String department = current.getDepartment();
            int semester = current.getSemester();
            String subject = current.getSubject();

            boolean checked = false;

            for (int j = 0; j < i; j++) {

                Attendance previous = attendanceRecords.get(j);

                if (previous.getStudentId() == studentId &&
                    previous.getDepartment().equalsIgnoreCase(department) &&
                    previous.getSemester() == semester &&
                    previous.getSubject().equalsIgnoreCase(subject)) {

                    checked = true;
                    break;
                }
            }

            if (checked) {
                continue;
            }

            int total = 0;
            int attended = 0;

            for (Attendance record : attendanceRecords) {

                if (record.getStudentId() == studentId &&
                    record.getDepartment().equalsIgnoreCase(department) &&
                    record.getSemester() == semester &&
                    record.getSubject().equalsIgnoreCase(subject)) {

                    total++;

                    if (record.isPresent()) {
                        attended++;
                    }
                }
            }

            double percentage = attended * 100.0 / total;

            System.out.printf(
                "%-10d %-15s %-10d %-18s %-10.2f%%%n",
                studentId, department, semester, subject, percentage
            );
        }
    }

    // 9. Weekly Attendance Report
    public void weeklyAttendanceReport(Scanner sc) {

        String startDate = readDate(sc);

        LocalDate start;

        try {
            start = LocalDate.parse(startDate, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date.");
            return;
        }

        LocalDate end = start.plusDays(6);

        int total = 0;
        int present = 0;
        int absent = 0;

        System.out.println("\n===== WEEKLY ATTENDANCE REPORT =====");

        System.out.println("From: " + start.format(formatter));
        System.out.println("To  : " + end.format(formatter));

        System.out.println("----------------------------------------------");

        System.out.printf(
            "%-15s %-10s %-10s %-12s%n",
            "Date", "Present", "Absent", "Percentage"
        );

        for (int day = 0; day < 7; day++) {

            LocalDate currentDate = start.plusDays(day);

            int dailyPresent = 0;
            int dailyAbsent = 0;

            for (Attendance record : attendanceRecords) {

                LocalDate recordDate =
                        LocalDate.parse(record.getDate(), formatter);

                if (recordDate.equals(currentDate)) {

                    if (record.isPresent()) {
                        dailyPresent++;
                    } else {
                        dailyAbsent++;
                    }
                }
            }

            int dailyTotal = dailyPresent + dailyAbsent;

            double percentage = dailyTotal == 0
                    ? 0
                    : dailyPresent * 100.0 / dailyTotal;

            System.out.printf(
                "%-15s %-10d %-10d %-12.2f%%%n",
                currentDate.format(formatter),
                dailyPresent,
                dailyAbsent,
                percentage
            );

            total += dailyTotal;
            present += dailyPresent;
            absent += dailyAbsent;
        }

        System.out.println("----------------------------------------------");

        double weeklyPercentage = total == 0
                ? 0
                : present * 100.0 / total;

        System.out.println("Total Records : " + total);
        System.out.println("Present       : " + present);
        System.out.println("Absent        : " + absent);

        System.out.printf(
            "Weekly Attendance: %.2f%%%n", weeklyPercentage
        );
    }
}