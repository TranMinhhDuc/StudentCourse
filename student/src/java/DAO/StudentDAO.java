package DAO;

import Model.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DAO {

    public static int totalPages;

    public StudentDAO() {
        super();
    }

    public List<Student> getStudent(int pageNumber) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student LIMIT 10 OFFSET ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, pageNumber* 10);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("studentName"));
                student.setSemester(rs.getInt("semester"));
                students.add(student);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentByName(String name, int page) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student WHERE studentName "
                + "LIKE ? "
                + "ORDER BY SUBSTRING_INDEX(studentName, ' ', -1) ASC "
                + "LIMIT 10 OFFSET ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, page * 10);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("studentName"));
                student.setSemester(rs.getInt("semester"));
                students.add(student);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean addStudent(String studentName, int semester) {
        String query = "INSERT INTO student (semester, studentName) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, semester);
            ps.setString(2, studentName);
            int rowInserted = ps.executeUpdate();

            return rowInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(String studentName, int semester, int studentId) {
        String query = "UPDATE student SET studentName = ?, semester = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, studentName);
            ps.setInt(2, semester);
            ps.setInt(3, studentId);

            int rowUpdated = ps.executeUpdate();
            return rowUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStudent(int id, String name) {
        String query = "UPDATE student SET studentName = ? Where id = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setInt(2, id);
                int rowUpdated = ps.executeUpdate();
                return rowUpdated > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public boolean updateStudent( int id, int semester) {
        String query = "UPDATE student SET semester = ? Where id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, semester);
            ps.setInt(2, id);
            int rowUpdated = ps.executeUpdate();
            return rowUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM Student WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);

            int rowDelete = ps.executeUpdate();
            return rowDelete > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}