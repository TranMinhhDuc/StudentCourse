package Controller;

import DAO.CourseDAO;
import DAO.StudentCourseDAO;
import DAO.StudentDAO;
import Model.Course;
import Model.Student;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/student")
public class StudentController extends HttpServlet{
    
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private StudentCourseDAO studentCourseDAO;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
         
        courseDAO = new CourseDAO();
        studentDAO = new StudentDAO();
        
        String name = req.getParameter("searchByName") != null ? req.getParameter("searchByName") : "";
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 0;
        
        List<Student> students = studentDAO.getStudentByName(name, page);
        List<Course> courses = courseDAO.getCourse();
        
        req.setAttribute("students", students);
        req.setAttribute("courses", courses);
        req.setAttribute("currentPage", page);
        RequestDispatcher dispatcher = req.getRequestDispatcher("Students.jsp");
        dispatcher.forward(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        studentDAO = new StudentDAO();
        String message="";

        switch (action) {
            case "delete":
                int studentId = Integer.parseInt(req.getParameter("studentId"));
                System.out.println(studentId);
                studentDAO.deleteStudent(studentId);
                System.out.println("delete success");
                
                message = "xóa sinh viên thành công";
            
                resp.sendRedirect("student");
                break;
            case "Update":
                String studentName = req.getParameter("studentName");
                studentId = Integer.parseInt(req.getParameter("studentId"));
                int semester = Integer.parseInt(req.getParameter("semester"));
                studentDAO.updateStudent(studentName, semester, studentId);
                
                message = "Update sinh viên thành công";
                
                resp.sendRedirect("student");
                break;
            case "AddStudentCourse":
                studentId = Integer.parseInt(req.getParameter("studentId"));
                int courseId = Integer.parseInt(req.getParameter("courseName"));
                studentCourseDAO = new StudentCourseDAO();
                studentCourseDAO.addCourseForStudent(studentId, courseId);
                
                message = "thêm khóa học cho sinh viên thành công";

                resp.sendRedirect("student");
                break;
            case "addStudent":
                studentName = req.getParameter("name");
                semester = Integer.parseInt(req.getParameter("semester"));
                studentDAO.addStudent(studentName, semester);
                message = "thêm sinh viên thành công";

                resp.sendRedirect("student");
                break;
            default:
                throw new AssertionError();
        }
        
        req.getSession().setAttribute("message", message);
    }
}