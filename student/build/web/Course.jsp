<%@page import="Model.Course"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Course List</title>
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
        
        function openUpdateCourse(courseId, courseName) {
            document.getElementById("updateCourseId").value = courseId;
            document.getElementById("updateCourseName").value =courseName;
            document.getElementById("updateCourse").style.display = "block";
        }
        
        function closeUpdateCourse(){
            document.getElementById("updateCourse").style.display = "none";
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
    <h2 style="text-align: center;">Course List</h2>

    <div style="text-align: center; margin-bottom: 20px;">
        <form method="get">
            Tên Môn Học: <input type="text" name="searchByName">
            <button type="submit">Search</button>
        </form>
    </div>

    <div style="text-align: center; margin-bottom: 20px;">
        <button onclick="toggleForm()">Add Course</button>
    </div>

    <div id="form-container" class="form-container">
        <form method="post" action="course">
            <input type="hidden" name="action" value="addCourse">
            <label for="name">Course Name:</label>
            <input type="text" id="name" name="name" required>
            <br><br>
            <input type="submit" value="submit">
        </form>
    </div>

    <table>
        <tr>
            <th>STT</th>
            <th>Name</th>
            <th>Action</th>
        </tr>

        <% List<Course> courses = (List<Course>) request.getAttribute("courses");
           int i = 0;
           if (courses != null) {
                for (Course course : courses) {
                i ++;
                    %>
                    <tr>
                        <td><%= i %></td>
                        <td><%= course.getCourseName()%></td>
                        <td>
                            <form method="post" action="course" style="display:inline;">
                                <input type="hidden" name="courseId" value="<%= course.getId() %>">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" onclick="return confirm('xóa khóa học này');">Delete</button>
                            </form>
                                <button onclick="openUpdateCourse('<%= course.getId() %>', '<%= course.getCourseName() %>')">Update</button>
                        </td>
                    </tr>
                    <%
                }
            }
        %>
    </table>

    <div id="updateCourse" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeUpdateCourse()">&times;</span>
            <h3>Update Course Information</h3>
            <form method="post" action="course">
                <input type="hidden" name="action" value="Update">
                <input type="hidden" id="updateCourseId" name="courseId">
                <label for="updateName">Student Name:</label>
                <input type="text" id="updateCourseName" name="courseName" required>
                <br><br>
                <input type="submit" value="Update">
            </form>
        </div>
    </div>
</body>
</html>