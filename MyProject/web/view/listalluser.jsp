<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ABC Company - ListUser</title>
        <link rel="stylesheet" href="../css/home.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <style>
        .content {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }

        tbody tr:hover {
            background-color: #f1f1f1;
        }

        tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tbody tr:nth-child(odd) {
            background-color: #ffffff;
        }

        p {
            text-align: center;
            color: #777;
            font-size: 18px;
        }
    </style>

    <body>
        <header>
            <div class="container">
                <nav>
                    <img class="logo" src="https://www.shutterstock.com/image-vector/3d-abc-cubes-multicolored-vector-600nw-2374071877.jpg" alt="ABC">
                    <ul>
                        <li><a href="../view/home.html">Home</a></li>
                        <li><a href="../view/order.html">Đặt hàng</a></li>
                        <li><a href="#">Đơn hàng</a></li>
                        <li><a href="../view/KHSX.jsp">KHSX</a></li>
                        <li><a href="#">Xưởng</a></li>                   
                        <li><a href="#" class="active">Nhân sự</a></li>
                    </ul>
                    <div class="login-container" onclick="showDropdown()" onclick="hideDropdown()"> 
                        <div class="login-icon">
                            <i class="fas fa-sign-in-alt"></i>
                        </div>
                        <div id="myDropdown" class="dropdown-menu">
                            <form action="../logout" method="GET">
                                <button type="submit" class="logout-btn">Đăng xuất</button>
                            </form>
                        </div>
                    </div>
                </nav>
            </div>
        </header>

        <div class="content">
            <!-- Kiểm tra xem users có tồn tại trong session hay không -->
            <c:if test="${not empty sessionScope.users}">
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
                        <!-- Lặp qua danh sách users -->
                        <c:forEach var="user" items="${sessionScope.users}">
                            <tr>
                                <td>${user.uid}</td>
                                <td>${user.username}</td>
                                <td>${user.password}</td>
                                <td>${user.isLocked}</td>

                                <!-- Department -->
                                <td><c:out value="${user.depts.dname}" /></td>

                                <!-- Feature -->
                                <td><c:out value="${user.features.fid}" /></td>
                                <td><c:out value="${user.features.fname}" /></td>

                                <!-- Employee -->
                                <td><c:out value="${user.employees.eid}" /></td>
                                <td><c:out value="${user.employees.ename}" /></td>
                                <td><c:out value="${user.employees.salaryLevel}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- Hiển thị thông báo nếu không có users -->
            <c:if test="${empty sessionScope.users}">
                <p>No users found in session.</p>
            </c:if>
        </div>


        <footer>
            <div class="footer-container">
                <div class="footer-left">
                    <h3>ABC Company</h3>
                    <p>123 Main Street, City Name, Country</p>
                </div>
                <div class="footer-center">
                    <div class="social-icons">
                        <a href="#" class="social-icon"><i class="fab fa-facebook-f"></i></a>
                        <a href="#" class="social-icon"><i class="fab fa-twitter"></i></a>
                        <a href="#" class="social-icon"><i class="fab fa-instagram"></i></a>
                        <a href="#" class="social-icon"><i class="fab fa-linkedin-in"></i></a>
                    </div>
                </div>
                <div class="footer-right">
                    <img src="https://cdn.wccftech.com/wp-content/uploads/2017/03/Google-Maps.jpg" alt="Map" class="map-image">
                </div>
            </div>
        </footer>

        <script src="../js/home.js"></script>
    </body>
</html>
