package DAO;

import DTO.StudentCourseRequest;
import Model.StudentCourse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseDAO extends DAO {

    private StudentCourse studentCourse;

    public StudentCourseDAO() {
        super();
    }

    public List<StudentCourseRequest> getStudentCourse(int pageNumber) {

        List<StudentCourseRequest> listRequests = new ArrayList<>();
        String totalPages = "";
        studentCourse = new StudentCourse();

        String query = "SELECT sc.id, s.id AS studentId, s.studentName, c.id AS courseId, c.courseName, s.semester " +
                "FROM StudentCourse sc " +
                "JOIN Student s ON s.id = sc.studentId " +
                "JOIN Course c ON c.id = sc.courseId " +
                "ORDER BY sc.id  ASC "+
                "LIMIT 10 OFFSET ?;";

        String totalPageQuery = "SELECT CEIL(COUNT(*) / 10.0) AS totalPages FROM StudentCourse;";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, pageNumber * 10);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentCourseRequest request = new StudentCourseRequest();
                request.setId(rs.getInt("id"));
                request.setStudentId(rs.getInt("studentId"));
                request.setCourseId(rs.getInt("courseId"));
                request.setStudentName(rs.getString("studentName"));
                request.setCourseName(rs.getString("courseName"));
                request.setSemester(rs.getInt("semester"));
                listRequests.add(request);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRequests;
    }

    public List<StudentCourseRequest> getStudentCourseByName(int pageNumber, String studentName, String courseName) {

        List<StudentCourseRequest> listRequests = new ArrayList<>();

        String query = "SELECT sc.id, s.id AS studentId, s.studentName, c.id AS courseId, c.courseName, s.semester " +
                        "FROM StudentCourse sc " +
                        "JOIN Student s ON s.id = sc.studentId " +
                        "JOIN Course c ON c.id = sc.courseId " +
                        "WHERE s.studentName LIKE ? " +
                        "AND c.courseName LIKE ? " +
                        "ORDER BY SUBSTRING_INDEX(s.studentName, ' ', -1) ASC " +
                        "LIMIT 10 OFFSET ?;";

        String totalPageQuery = "SELECT CEIL(COUNT(*) / 10.0) AS totalPages FROM student "
                + "WHERE studentName LIKE ?;";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement tp = con.prepareStatement(totalPageQuery);

            ps.setString(1, "%" + studentName + "%");
            ps.setString(2, "%" + courseName + "%");
            ps.setInt(3, pageNumber * 10);

            tp.setString(1, "%" + studentName + "%");

            ResultSet rs = ps.executeQuery();
            ResultSet rtp = tp.executeQuery();

            while (rs.next()) {
                StudentCourseRequest request = new StudentCourseRequest();
                request.setId(rs.getInt("id"));
                request.setStudentId(rs.getInt("studentId"));
                request.setCourseId(rs.getInt("courseId"));
                request.setStudentName(rs.getString("studentName"));
                request.setCourseName(rs.getString("courseName"));
                request.setSemester(rs.getInt("semester"));
                listRequests.add(request);
            }

            rs.close();
            rtp.close();
            ps.close();
            tp.close();
        } catch (Exception e) {
        }

        return listRequests;
    }

    public boolean addCourseForStudent(int studentId, int courseId) {
        String query = "INSERT INTO studentcourse (studentId, courseId) VALUES (?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            int rowInserted = ps.executeUpdate();

            return rowInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudentCourse (int id) {
        String query = "DELETE FROM studentcourse WHERE id = ?;";


        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean updateStudentCourse(int studentCourseId, int courseId) {
        String query = "UPDATE studentcourse SET courseId = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);
            ps.setInt(2, studentCourseId);
            int rowUpdate = ps.executeUpdate();
            return rowUpdate > 0;
        } catch (Exception e) {
            return false;
        }
        
    }

}