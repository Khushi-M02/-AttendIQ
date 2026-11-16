public class Attendance {

    private int studentId;
    private String department;
    private int semester;
    private String subject;
    private String date;
    private boolean present;

    // Constructor
    public Attendance(int studentId, String department, int semester,
                      String subject, String date, boolean present) {

        this.studentId = studentId;
        this.department = department;
        this.semester = semester;
        this.subject = subject;
        this.date = date;
        this.present = present;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getDepartment() {
        return department;
    }

    public int getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }
}
