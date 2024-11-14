<%@page import="Model.Course"%>
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
        function openUpdateStudent(studentId, studentName, semester) {
            document.getElementById('updateStudentId').value = studentId;
            document.getElementById('updateName').value = studentName;
            document.getElementById('updateSemester').value = semester;
            document.getElementById("updateStudent").style.display = "block";
        }

        function closeUpdateStudent() {
            document.getElementById("updateStudent").style.display = "none";
        }
        
        function openAddStudentCourse(studentId, studentName) {
            document.getElementById('studentId').value = studentId;
            document.getElementById('studentName').textContent = studentName;
            document.getElementById("addStudentCourse").style.display = "block";
        }

        function closeAddStudentCourse() {
            document.getElementById("addStudentCourse").style.display = "none";
        }
        function toggleForm() {
            var formContainer = document.getElementById("form-container");
            formContainer.style.display = formContainer.style.display === "none" ? "block" : "none";
        }
    </script>
</head>
<body>

    <h2 style="text-align: center;">Student List</h2>

    <div style="text-align: center; margin-bottom: 20px;">
        <form method="get">
            Họ và tên: <input type="text" name="searchByName">
            <button type="submit">Search</button>
        </form>
    </div>

    <div style="text-align: center; margin-bottom: 20px;">
        <button onclick="toggleForm()">Add Student</button>
    </div>

    <div id="form-container" class="form-container">
        <form method="post" action="student">
            <input type="hidden" name="action" value="addStudent">
            <label for="name">Student Name:</label>
            <input type="text" id="name" name="name" required>
            <br>
            <label for="semester">Semester:</label>
            <input type="text" id="semester" name="semester" required>
            <br><br>
            <input type="submit" value="submit">
        </form>
    </div>

    <table>
        <tr>
            <th>STT</th>
            <th>Name</th>
            <th>Semester</th>
            <th>Action</th>
        </tr>

        <% List<Student> students = (List<Student>) request.getAttribute("students");
           int currentPage = (int) request.getAttribute("currentPage");
           int i = 0;
           if (students != null) {
                for (Student student : students) {
                    %>
                    <tr>
                        <td><%= currentPage*10+1+i %></td>
                        <td><%= student.getStudentName() %></td>
                        <td><%= student.getSemester() %></td>
                        <td>
                            <form method="post" action="student" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="studentId" value="<%= student.getId() %>">
                                <button type="submit" onclick="return confirm('xóa học sinh này?');">Delete</button>
                            </form>
                                <button onclick="openUpdateStudent('<%= student.getId() %>', '<%= student.getStudentName() %>', '<%= student.getSemester() %>')">Update</button>
                                <button onclick="openAddStudentCourse('<%= student.getId() %>', '<%= student.getStudentName() %>')">Add Student Course</button>
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
            <a href="student?page=<%= currentPage - 1 %>">Previous</a>
        <%
            }
        %>
        <span>Page <%= currentPage + 1 %></span>
        <a href="student?page=<%= currentPage + 1 %>">Next</a>
    </div>

    <div id="updateStudent" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeUpdateStudent()">&times;</span>
            <h3>Update Student Information</h3>
            <form method="post" action="student">
                <input type="hidden" name="action" value="Update">
                <input type="hidden" id="updateStudentId" name="studentId">
                <label for="updateName">Student Name:</label>
                <input type="text" id="updateName" name="studentName" required>
                <br>
                <label for="updateSemester">Semester:</label>
                <input type="text" id="updateSemester" name="semester" required>
                <br><br>
                <input type="submit" value="Update">
            </form>
        </div>
    </div>

    <div id="addStudentCourse" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeAddStudentCourse()">&times;</span>
            <h3>Add Course</h3>
            <form method="post" action="student">
                <input type="hidden" name="action" value="AddStudentCourse">
                <input type="hidden" id="studentId" name="studentId">
                <label>Student Name:</label>
                <span id="studentName" name="studentName"></span>
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
                <input type="submit" value="AddStudentCourse">
            </form>
        </div>
    </div>
</body>
</html>