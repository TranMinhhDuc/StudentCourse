package Model;

public class Student {
    private int id;
    private String studentName;
    private int semester;

    public Student(int id, String studentName, int semester) {
        this.id = id;
        this.studentName = studentName;
        this.semester = semester;
    }

    public Student() {}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}