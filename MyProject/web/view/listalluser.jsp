<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/customtags" prefix="mytag" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ABC Company - ListUser</title>
        <link rel="stylesheet" href="../css/home.css">
        <link rel="stylesheet" href="../css/listalluser.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>
    <body>
        <header>
            <div class="container">
                <nav>
                    <img class="logo" src="https://www.shutterstock.com/image-vector/3d-abc-cubes-multicolored-vector-600nw-2374071877.jpg" alt="ABC">
                    <ul>
                        <li><a href="../view/home.html">Home</a></li>
                        <li><a href="../view/order.html">Đặt hàng</a></li>
                        <li><a href="../plan/insert">KHSX</a></li>
                        <li><a href="#" class="active">Nhân sự</a></li>
                    </ul>
                    <div class="login-container" onclick="toggleDropdown()"> 
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

        <div id="realTimeClock">
            <mytag:RealTimeDateTime />
        </div>

        <div class="user-container">
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
                <!-- Hiển thị thông báo -->
                <c:if test="${not empty sessionScope.error}">
                    <script>
                        alert("${sessionScope.error}");
                        <c:set var="sessionScope.error" value="" />
                    </script>
                    <c:remove var="error" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.successMessage}">
                    <div class="alert alert-success">
                        ${sessionScope.successMessage}
                    </div>
                    <c:remove var="successMessage" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.successDelete}">
                    <div class="alert alert-success">
                        ${sessionScope.successDelete}
                    </div>
                    <c:remove var="successDelete" scope="session" />
                </c:if>
            </div>

            <div class="form-container">
                <div class="button-container">
                    <button class="btn add-user" id="addAccountBtn">ADD ACCOUNT</button>
                    <button class="btn delete-user" id="deleteAccountBtn" onclick="window.location.href = 'deleteUser.jsp'">DELETE ACCOUNT</button>
                    <button class="btn update-user" id="updateAccountBtn" onclick="window.location.href = 'updateUser.jsp'">UPDATE ACCOUNT</button>
                </div>

                <!-- Form 1: Add Account For Employee -->
                <div id="formEmployee" style="display: block;">
                    <form action="../user/create" method="post">
                        <h3>Add Account For Employee</h3>
                        <label for="eid">Employee ID:</label>
                        <input type="text" name="eid" placeholder="Enter Employee ID" required><br>
                        <label for="ename">Employee Name:</label>
                        <input type="text" name="ename" placeholder="Enter Employee Name" required><br>
                        <input type="hidden" name="step" value="1">
                        <button type="submit" id="checkEmployeeBtn">OK</button>
                    </form>
                </div>

                <!-- Form 2: Create Account -->
                <c:if test="${not empty sessionScope.employee}">
                    <div id="formAccount" style="${not empty employee ? 'display:block;' : 'display:none;'}">
                        <div id="employeeInfo" style="color: green;">
                            <p>Create Account for Employee: </p>
                            <p>ID: ${employee.eid} | ${employee.ename}</p>
                        </div>
                        <form action="../user/create" method="post">
                            <label for="username">Username:</label>
                            <input type="text" name="username" placeholder="Enter Username" required><br>
                            <label for="password">Password:</label>
                            <input type="password" name="password" placeholder="Enter Password" required><br>
                            <input type="hidden" name="step" value="2">
                            <input type="hidden" name="eid" value="${employee.eid}">
                            <button type="submit" id="createAccountBtn">Create Account</button>
                        </form>

                    </div>
                </c:if>                            
            </div>          
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
        <script>
                        document.getElementById('addAccountBtn').addEventListener('click', function () {
                            // Hiển thị form nhập Employee ID và Name
                            document.getElementById('formEmployee').style.display = 'block';
                            document.getElementById('formAccount').style.display = 'none'; // Ẩn form tạo account nếu có
                        });

                        // Khi nhấn nút "ADD ACCOUNT"
                        document.getElementById("addAccountBtn").addEventListener("click", function () {
                            // Hiển thị Form tạo tài khoản cho nhân viên
                            document.getElementById("formEmployee").style.display = "block";
                            // Hiển thị Form tạo tài khoản cho người dùng (nếu có)
                            var formAccount = document.getElementById("formAccount");
                            if (formAccount) {
                                formAccount.style.display = "block";
                            }
                        });

                        function startRealTimeClock() {
                            function updateClock() {
                                const now = new Date();
                                const options = {
                                    year: 'numeric', month: 'numeric', day: 'numeric',
                                    hour: '2-digit', minute: '2-digit', second: '2-digit'
                                };
                                document.getElementById('realTimeClock').innerText = `Ngày ${now.getDate()} tháng ${now.getMonth() + 1} năm ${now.getFullYear()}, ${now.toLocaleTimeString('vi-VN')}`;
                            }
                            setInterval(updateClock, 1000);
                            updateClock(); // Gọi ngay lần đầu để hiển thị thời gian ngay lập tức
                        }
        </script>
    </body>
</html>
