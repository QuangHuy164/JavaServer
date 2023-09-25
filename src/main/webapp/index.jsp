<!DOCTYPE html>
<html>
<head>
    <title>Student Management System</title>
</head>
<body>
    <h1>Student Management System</h1>
    <form action="StudentServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label>Last Name: <input type="text" name="lastname"></label>
        <label>First Name: <input type="text" name="firstname"></label>
        <input type="submit" value="Add Student">
    </form>
    
    <h2>Student List</h2>
    <table border="1">
        <tr>
            <th>Number</th>
            <th>Last Name</th>
            <th>First Name</th>
            <th>Action</th>
        </tr>
        <c:forEach var="student" items="${students}">
            <tr>
                <td>${student.number}</td>
                <td>${student.lastname}</td>
                <td>${student.firstname}</td>
                <td>
                    <form action="StudentServlet" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="number" value="${student.number}">
                        <input type="text" name="lastname" value="${student.lastname}">
                        <input type="text" name="firstname" value="${student.firstname}">
                        <input type="submit" value="Update">
                    </form>
                    <form action="StudentServlet" method="post">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="number" value="${student.number}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
