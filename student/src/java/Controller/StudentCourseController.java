package Controller;

import DAO.CourseDAO;
import DAO.StudentCourseDAO;
import DTO.StudentCourseRequest;
import Model.Course;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/studentCourse")
public class StudentCourseController extends HttpServlet{
    
    private CourseDAO courseDAO;
    private StudentCourseDAO studentCourseDAO;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
         
        studentCourseDAO = new StudentCourseDAO();
        courseDAO = new CourseDAO();
        String studentName = req.getParameter("searchByStudentName") != null ? req.getParameter("searchByStudentName") : "";
        String courseName = req.getParameter("searchByCourseName") != null ? req.getParameter("searchByCourseName") : "";
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 0;
        
        List<StudentCourseRequest> studentCourses = studentCourseDAO.getStudentCourseByName(page, studentName, courseName);
        List<Course> courses = courseDAO.getCourse();
        
        req.setAttribute("courses", courses);
        req.setAttribute("studentCourses", studentCourses);
        req.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = req.getRequestDispatcher("StudentCourse.jsp");
        dispatcher.forward(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        String message = "";
        studentCourseDAO = new StudentCourseDAO();
        
        if ("delete".equals(action)) {
            int studentCourseId = Integer.parseInt(req.getParameter("studentCourseId"));
            studentCourseDAO.deleteStudentCourse(studentCourseId);
            message = "xóa khóa học của sinh viên thành công";
            resp.sendRedirect("studentCourse");
        }
        if ("updateStudentCourse".equals(action)){
            int studentCourseId = Integer.parseInt(req.getParameter("studentCourseId"));
            int courseId = Integer.parseInt(req.getParameter("courseName"));
            
            System.out.println(courseId);
            studentCourseDAO.updateStudentCourse(studentCourseId, courseId);
            message = "sửa khóa học của sinh viên thành công";
            resp.sendRedirect("studentCourse");
        }
        
        req.getSession().setAttribute("message", message);
    }
}