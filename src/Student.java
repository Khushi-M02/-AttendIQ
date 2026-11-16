public class Student {

    // Student details
    private int studentId;
    private String name;
    private int age;
    private String department;
    private int semester;

    // Constructor
    public Student(int studentId, String name, int age,
                   String department, int semester) {

        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.department = department;
        this.semester = semester;
    }

    // Get student ID
    public int getStudentId() {
        return studentId;
    }

    // Get student name
    public String getName() {
        return name;
    }

    // Get student age
    public int getAge() {
        return age;
    }

    // Get department
    public String getDepartment() {
        return department;
    }

    // Get semester
    public int getSemester() {
        return semester;
    }

    // Display student details
    public void displayStudent() {

        System.out.println("--------------------------------");
        System.out.println("Student ID : " + studentId);
        System.out.println("Name       : " + name);
        System.out.println("Age        : " + age);
        System.out.println("Department : " + department);
        System.out.println("Semester   : " + semester);
        System.out.println("--------------------------------");
    }
}
