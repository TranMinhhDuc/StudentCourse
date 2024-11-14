package DAO;

import Model.Course;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO extends DAO {

    public CourseDAO() {
        super();
    }

    public List<Course> getCourse(){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Course";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("courseName"));
                courses.add(course);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }


    public List<Course> getCourseByName(String name, int page) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Course WHERE courseName "
                + "LIKE ? "
                + "ORDER BY courseName ASC "
                + "LIMIT 10 OFFSET ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, page * 10);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("courseName"));
                courses.add(course);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean addCourse(String courseName) {
        String query = "INSERT INTO course (courseName) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, courseName);
            int rowInsert = ps.executeUpdate();
            return rowInsert > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCourse(int courseId, String courseName) {
        String query = "UPDATE Course SET courseName = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, courseName);
            ps.setInt(2, courseId);
            int rowUpdate = ps.executeUpdate();
            return rowUpdate > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourse(int id) {
        String query = "DELETE FROM Course WHERE id = ?";
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