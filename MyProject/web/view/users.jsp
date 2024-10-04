<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1px">
            <thead>
                <tr>
                    <th>Username id</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>isLocked</th>
                    <th>Department name</th>
                    <th>Feature id</th>
                    <th>Feature name</th>
                    <th>Employee id</th>
                    <th>Employee name</th>
                    <th>Salary Level</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${sessionScope.users}">
                    <tr>
                        <td>${user.uid}</td>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>${user.isLocked}</td>
                        <td>
                            <c:forEach var="dept" items="${user.depts}">
                                ${dept.dname}<br/>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="feature" items="${user.features}">
                                ${feature.fid}<br/>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="feature" items="${user.features}">
                                ${feature.fname}<br/>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="employee" items="${user.employees}">
                                ${employee.eid}<br/>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="employee" items="${user.employees}">
                                ${employee.ename}<br/>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="employee" items="${user.employees}">
                                ${employee.salaryLevel}<br/>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
