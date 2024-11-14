<%@page import="Model.Course"%>
<%@page import="DTO.StudentCourseRequest"%>
<%@page import="java.util.List"%>
<%@page import="Model.Student"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
    <style>
        table {
            width: 50%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .pagination {
            text-align: center;
            margin-top: 20px;
        }
        .form-container {
            display: none;
            text-align: center;
            margin-top: 20px;
        }
        .modal {
            display: none; 
            position: fixed; 
            z-index: 1; 
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0); 
            background-color: rgba(0,0,0,0.4); 
            padding-top: 60px;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 40%;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
    <script>
        function toggleForm() {
            var formContainer = document.getElementById("form-container");
            formContainer.style.display = formContainer.style.display === "none" ? "block" : "none";
        }
        
        function openUpdateStudentCourse(studentCourseId, studentName) {
            document.getElementById('updateStudentCourseId').value = studentCourseId;
            document.getElementById('updateName').textContent = studentName;
            document.getElementById("updateStudentCourse").style.display = "block";
        }

        function closeUpdateStudentCourse() {
            document.getElementById("updateStudentCourse").style.display = "none";
        }
    </script>
</head>
<body>

    <% 
     String message = (String) session.getAttribute("message");
     if (message != null) {
         session.removeAttribute("message");
        %>
            <script>
                alert("<%= message %>")
            </script>
        <%
        }
    %>
    <h2 style="text-align: center;">Student Course List</h2>

    <div style="text-align: center; margin-bottom: 20px;">
        <form method="get">
            Họ và tên: <input type="text" name="searchByStudentName">
            Khóa học: <input type="text" name="searchByCourseName">
            <button type="submit">Search</button>
        </form>
    </div>

    <table>
        <tr>
            <th>STT</th>
            <th>Student Name</th>
            <th>Course Name</th>
            <th>Semester</th>
            <th>Action</th>
        </tr>

        <% List<StudentCourseRequest> studentCourses = (List<StudentCourseRequest>) request.getAttribute("studentCourses");
           int currentPage = (int) request.getAttribute("currentPage");
           int i = 0;
           if (studentCourses != null) {
                for (StudentCourseRequest studentCourse : studentCourses) {
                    %>
                    <tr>
                        <td><%= currentPage*10+1+i %></td>
                        <td><%= studentCourse.getStudentName()%></td>
                        <td><%= studentCourse.getCourseName()%></td>
                        <td><%= studentCourse.getSemester() %></td>
                        <td>
                            <form method="post" action="studentCourse" style="display:inline;">
                                <input type="hidden" name="studentCourseId" value="<%= studentCourse.getId() %>">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" onclick="return confirm('xóa khóa học của sinh viên này');">Delete</button>
                            </form>
                                <button onclick="openUpdateStudentCourse('<%= studentCourse.getId() %>', '<%= studentCourse.getStudentName()%>')">Update</button>
                                
                        </td>
                    </tr>
                    <%
                        i++;
                }
            }
        %>
    </table>
    
    <div class="pagination">
        <% 
            if (currentPage > 0) {
        %>
            <a href="studentCourse?page=<%= currentPage - 1 %>">Previous</a>
        <%
            }
        %>
        <span>Page <%= currentPage + 1 %></span>
        <a href="studentCourse?page=<%= currentPage + 1 %>">Next</a>
    </div>

    <div id="updateStudentCourse" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeUpdateStudentCourse()">&times;</span>
            <h3>Update Student Course</h3>
            <form method="post" action="studentCourse">
                <input type="hidden" name="action" value="updateStudentCourse">
                <input type="hidden" id="updateStudentCourseId" name="studentCourseId">
                <label>Student Name:</label>
                <span id="updateName" name="updateName"></span>
                <br><br>
                <label>Courses: </label>
                <select name = "courseName"> 
                    
                    <% List<Course> courses = (List<Course>) request.getAttribute("courses"); 
                        for (Course course : courses) {
                        %>
                        <option name="courseId" value="<%= course.getId()%>"><%= course.getCourseName()%> </option>
                        <%
                        }
                    %>
                </select>
                <label> 
                <br><br>
                <input type="submit" value="Update Student Course">
            </form>
        </div>
    </div>
</body>
</html>