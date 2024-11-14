package Controller;

import DAO.CourseDAO;
import Model.Course;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/course")
public class CourseController extends HttpServlet{
    
    private CourseDAO courseDAO;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
         
        courseDAO = new CourseDAO();
        String name = req.getParameter("searchByName") != null ? req.getParameter("searchByName") : "";
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 0;
        
        List<Course> courses = courseDAO.getCourseByName(name, page);
        
        req.setAttribute("courses", courses);
        RequestDispatcher dispatcher = req.getRequestDispatcher("Course.jsp");
        dispatcher.forward(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        courseDAO = new CourseDAO();
        String message = "";
        
        
        switch (action) {
            case "delete":
                int courseId = Integer.parseInt(req.getParameter("courseId"));
                courseDAO.deleteCourse(courseId);
                message = "xóa môn học thành công";
                resp.sendRedirect("course");
                break;

            case "Update":
                courseId = Integer.parseInt(req.getParameter("courseId"));
                String courseName = req.getParameter("courseName");
                courseDAO.updateCourse(courseId, courseName);
                message = "Update môn học thành công";
                resp.sendRedirect("course");
                break;
            
            case "addCourse":
                courseName = req.getParameter("name");
                courseDAO.addCourse(courseName);
                message = "thêm môn học thành công";
                resp.sendRedirect("course");
                break;
                
            default:
                
                throw new AssertionError();
        }
        
        req.getSession().setAttribute("message", message);
    }
}