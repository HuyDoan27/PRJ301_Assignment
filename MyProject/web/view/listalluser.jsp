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

        .user-container {
            display: flex; /* Sử dụng Flexbox */
            justify-content: space-between; /* Căn chỉnh không gian giữa các phần tử */
        }

        .content {
            width: 70%; /* Chiếm 80% chiều rộng */
        }

        .button-container {
            width: 30%; /* Chiếm 20% chiều rộng */
            display: flex; /* Sử dụng Flexbox để căn chỉnh các nút */
            flex-direction: row; /* Sắp xếp các nút theo chiều dọc */
            margin: auto;
            margin-top: 20px; /* Khoảng cách giữa bảng và nút */
        }

        .btn {
            padding: 10px 20px; /* Padding cho nút */
            margin-left: 15px; /* Khoảng cách giữa hai nút */
            border: none; /* Xóa đường viền */
            border-radius: 5px; /* Bo góc */
            cursor: pointer; /* Con trỏ khi hover */
            font-size: 16px; /* Kích thước chữ */
        }

        .add-user {
            background-color: #4CAF50; /* Màu xanh lá */
            color: white; /* Màu chữ */
        }

        .delete-user {
            background-color: #f44336; /* Màu đỏ */
            color: white; /* Màu chữ */
        }

        .btn:hover {
            opacity: 0.8; /* Hiệu ứng khi hover */
        }

        .form-container {
            width: 30%;
            display: grid;
            grid-template-columns: auto;
            grid-template-rows: 100px 250px auto;
        }

        /* Label và input */
        form label {
            margin-bottom: 5px;
            font-weight: bold;
        }

        form input {
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
        }

        form button {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }

        form button:hover {
            background-color: #45a049;
        }

        /* Ẩn form ban đầu */
        #formEmployee, #formAccount {
            display: none; /* Ẩn form ban đầu */
            padding: 10px;
            margin-left: 15%;
            background-color: #f2f2f2;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 100%;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2); /* Đổ bóng nhẹ để tạo sự rõ ràng */
            clear: both;
        }

        /* Form cho employee */
        #formEmployee {
            display: none; /* Ẩn form ban đầu */
            margin-top: 10px; /* Khoảng cách từ các phần tử trên */
            padding: 15px; /* Giảm padding để tối ưu không gian */
            background-color: #f9f9f9; /* Màu nền sáng cho form */
            border: 1px solid #ccc; /* Viền xám cho form */
            border-radius: 8px; /* Bo tròn các góc */
            width: 100%; /* Chiếm toàn bộ chiều rộng */
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* Đổ bóng nhẹ cho form */
            max-height: 250px; /* Giới hạn chiều cao của form */
            overflow: auto; /* Thêm cuộn nếu nội dung vượt quá chiều cao */
        }

        /* Tiêu đề cho form */
        #formEmployee h3 {
            font-size: 18px; /* Giảm kích thước chữ tiêu đề */
            color: #333; /* Màu chữ đen cho tiêu đề */
            margin-bottom: 10px; /* Khoảng cách dưới tiêu đề */
            font-weight: bold; /* Chữ đậm */
            text-transform: uppercase; /* Viết hoa chữ */
            letter-spacing: 1px; /* Khoảng cách giữa các chữ cái */
            border-bottom: 2px solid #007BFF; /* Đường gạch dưới màu xanh */
            padding-bottom: 5px; /* Khoảng cách dưới đường gạch */
        }

        /* Nhãn trong form */
        #formEmployee label {
            font-weight: 600; /* Chữ đậm cho nhãn */
            margin-bottom: 5px; /* Khoảng cách dưới nhãn */
            display: block; /* Hiển thị theo dạng khối */
            color: #555; /* Màu xám cho nhãn */
            text-transform: uppercase; /* Viết hoa chữ */
            font-size: 12px; /* Kích thước chữ nhỏ hơn */
            letter-spacing: 0.5px; /* Khoảng cách giữa các chữ cái */
        }

        /* Input trong form */
        #formEmployee input[type="text"] {
            width: 100%; /* Chiếm toàn bộ chiều rộng */
            padding: 10px; /* Giảm padding */
            margin-bottom: 10px; /* Khoảng cách dưới input */
            border: 1px solid #ccc; /* Viền xám cho input */
            border-radius: 6px; /* Bo tròn các góc */
            box-sizing: border-box; /* Bao gồm padding và border trong chiều rộng */
            font-size: 14px; /* Kích thước chữ trong input nhỏ hơn */
            background-color: #fff; /* Màu nền trắng cho input */
            color: #333; /* Màu chữ đen cho input */
            transition: border-color 0.3s ease, box-shadow 0.3s ease; /* Hiệu ứng chuyển tiếp */
        }

        /* Hiệu ứng khi focus vào input */
        #formEmployee input[type="text"]:focus {
            border-color: #007BFF; /* Đổi màu viền khi focus */
            outline: none; /* Ẩn outline mặc định */
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Hiệu ứng bóng nhẹ */
        }

        /* Nút OK */
        #formEmployee button#checkEmployeeBtn {
            background-color: #28a745; /* Màu nền xanh lá cho nút */
            color: white; /* Màu chữ trắng */
            padding: 10px; /* Giảm padding cho nút */
            border: none; /* Ẩn viền */
            border-radius: 6px; /* Bo tròn các góc */
            cursor: pointer; /* Con trỏ chuột thành hình bàn tay */
            font-size: 14px; /* Kích thước chữ trong nút nhỏ hơn */
            font-weight: 600; /* Chữ đậm */
            text-transform: uppercase; /* Viết hoa chữ */
            width: 100%; /* Chiếm toàn bộ chiều rộng */
            transition: background-color 0.3s ease; /* Hiệu ứng chuyển tiếp */
        }

        /* Hiệu ứng hover cho nút */
        #formEmployee button#checkEmployeeBtn:hover {
            background-color: #218838; /* Đổi màu khi hover */
        }

        /* Thông báo lỗi */
        #employeeError {
            margin-top: 5px; /* Khoảng cách trên thông báo lỗi */
            font-weight: bold; /* Chữ đậm cho thông báo lỗi */
            text-align: center; /* Căn giữa thông báo lỗi */
            display: none; /* Ẩn thông báo lỗi ban đầu */
            font-size: 12px; /* Kích thước chữ cho thông báo lỗi nhỏ hơn */
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
            </div>
            <div class="form-container">
                <div class="button-container">
                    <button class="btn add-user" id="addAccountBtn">ADD ACCOUNT</button>
                    <button class="btn delete-user">DELETE ACCOUNT</button>
                </div>

                <!-- Form 1: Add Account For Employee -->
                <div id="formEmployee" style="display: none;">
                    <h3>Add Account For Employee</h3>
                    <label for="eid">Employee ID:</label>
                    <input type="text" id="eid" placeholder="Enter Employee ID" required><br>
                    <label for="ename">Employee Name:</label>
                    <input type="text" id="ename" placeholder="Enter Employee Name" required><br>
                    <button id="checkEmployeeBtn">OK</button>
                    <p id="employeeError" style="color: red; display: none;">Not found this employee</p>
                </div>

                <!-- Form 2: Create Account -->
                <div id="formAccount" style="display: none;">
                    <h3>Create Account</h3>
                    <label for="username">Username:</label>
                    <input type="text" id="username" placeholder="Enter Username" required><br>
                    <label for="password">Password:</label>
                    <input type="password" id="password" placeholder="Enter Password" required><br>
                    <button id="createAccountBtn">Create Account</button>
                </div>
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

                        document.getElementById('checkEmployeeBtn').addEventListener('click', function () {
                            var eid = document.getElementById('eid').value;
                            var ename = document.getElementById('ename').value;

                            // Giả lập kiểm tra nhân viên có tồn tại hay không bằng hàm checkEmployee(eid, ename)
                            if (checkEmployee(eid, ename)) {
                                // Nhân viên tồn tại, chuyển sang form tạo account
                                document.getElementById('formEmployee').style.display = 'none';
                                document.getElementById('formAccount').style.display = 'block';
                                document.getElementById('employeeError').style.display = 'none'; // Ẩn thông báo lỗi nếu có
                            } else {
                                // Nhân viên không tồn tại, hiển thị thông báo lỗi
                                document.getElementById('employeeError').style.display = 'block';
                            }
                        });

// Hàm giả lập kiểm tra nhân viên trong cơ sở dữ liệu
                        function checkEmployee(eid, ename) {
                            // Đây là hàm giả lập, thay thế bằng gọi API hoặc AJAX để kiểm tra trong database
                            // Giả sử có nhân viên với eid là "123" và ename là "John"
                            if (eid === "123" && ename.toLowerCase() === "john") {
                                return true; // Nhân viên tồn tại
                            }
                            return false; // Nhân viên không tồn tại
                        }

                        document.getElementById('createAccountBtn').addEventListener('click', function () {
                            var username = document.getElementById('username').value;
                            var password = document.getElementById('password').value;

                            // Kiểm tra dữ liệu và tạo tài khoản
                            if (username && password) {
                                // Giả lập tạo tài khoản
                                alert('Account created for username: ' + username);
                                // Reset lại form sau khi tạo tài khoản thành công
                                document.getElementById('formEmployee').style.display = 'none';
                                document.getElementById('formAccount').style.display = 'none';
                                document.getElementById('eid').value = '';
                                document.getElementById('ename').value = '';
                                document.getElementById('username').value = '';
                                document.getElementById('password').value = '';
                            } else {
                                alert('Please fill in all required fields');
                            }
                        });

        </script>
    </body>
</html>
