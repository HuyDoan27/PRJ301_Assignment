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
            width: 70%; /* Chiếm 20% chiều rộng */
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
            grid-template-rows: 100px 250px 300px;
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
            margin-left: 10%;
            background-color: #f2f2f2;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 80%;
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
            width: 80%; /* Chiếm toàn bộ chiều rộng */
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

        #formAccount {
            display: block; /* Đảm bảo form được hiển thị */
            padding: 20px; /* Thêm padding xung quanh nội dung */
            border: 1px solid #ccc; /* Đường viền cho form */
            border-radius: 5px; /* Bo tròn các góc */
            background-color: #f9f9f9; /* Màu nền nhẹ cho form */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Đổ bóng cho form */
            margin-top: 20px; /* Khoảng cách từ phần tử phía trên */
            max-width: 400px; /* Đặt chiều rộng tối đa cho form */
        }

        #formAccount label {
            display: block; /* Hiển thị label dưới dạng khối */
            margin-bottom: 5px; /* Khoảng cách dưới mỗi label */
            font-weight: bold; /* Làm đậm văn bản label */
        }

        #formAccount input[type="text"],
        #formAccount input[type="password"] {
            width: 100%; /* Chiều rộng của input là 100% */
            padding: 10px; /* Padding bên trong input */
            border: 1px solid #ccc; /* Đường viền cho input */
            border-radius: 4px; /* Bo tròn các góc của input */
            margin-bottom: 15px; /* Khoảng cách dưới mỗi input */
            font-size: 16px; /* Kích thước chữ trong input */
        }

        #formAccount button {
            background-color: #28a745; /* Màu nền cho nút */
            color: white; /* Màu chữ cho nút */
            border: none; /* Ẩn đường viền của nút */
            border-radius: 4px; /* Bo tròn các góc của nút */
            padding: 10px 15px; /* Padding cho nút */
            cursor: pointer; /* Con trỏ thay đổi khi hover */
            font-size: 16px; /* Kích thước chữ trong nút */
        }

        #formAccount button:hover {
            background-color: #218838; /* Màu nền cho nút khi hover */
        }

        /* Định dạng tổng quan cho container của form delete account */
        #formDeleteAccount {
            width: 80%;
            max-width: 800px;
            margin-left: 10%;
            padding: 20px;
        }

        /* Định dạng cho tiêu đề của form delete account */
        #formDeleteAccount h3 {
            font-size: 24px;
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        /* Định dạng cho form delete account */
        #formDeleteAccount form {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        /* Định dạng cho các trường input trong form delete account */
        #formDeleteAccount form input[type="text"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        /* Định dạng cho các nhãn (label) trong form delete account */
        #formDeleteAccount form label {
            font-size: 16px;
            color: #333;
            margin-bottom: 5px;
            display: block;
        }

        /* Định dạng cho nút submit trong form delete account */
        #formDeleteAccount form button {
            width: 100%;
            background-color: #e74c3c;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }

        /* Hiệu ứng hover cho nút submit */
        #formDeleteAccount form button:hover {
            background-color: #c0392b;
        }

        /* Định dạng cho các trường input bị thiếu dữ liệu khi required */
        #formDeleteAccount form input:required:invalid {
            border-color: #e74c3c;
        }

        /* Định dạng cho thông báo lỗi (nếu có) */
        #formDeleteAccount form .error-message {
            color: #e74c3c;
            font-size: 14px;
            margin-top: 5px;
        }

        .button-container .btn.update-user {
            background-color: #4a90e2; /* Màu nền xanh lá cây */
            color: white; /* Màu chữ trắng */
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .button-container .btn.update-user:hover {
            background-color: #45a049; /* Màu nền khi hover */
        }

        #updateForm {
            display: none; /* Mặc định ẩn form */
            width: 100%; /* Đặt chiều rộng theo nhu cầu */
            padding: 20px; /* Khoảng cách bên trong form */
            background-color: #e0f0ff; /* Nền xanh dương nhạt */
            border: 1px solid #4a90e2; /* Viền xanh dương đậm */
            border-radius: 8px; /* Bo góc cho form */
            color: #333; /* Màu chữ */
            margin-top: 15px; /* Khoảng cách với phần tử phía trên */
        }

        #updateForm h3 {
            color: #4a90e2; /* Màu tiêu đề xanh dương đậm */
            margin-bottom: 15px;
        }

        #updateForm label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
            color: #4a90e2; /* Màu nhãn xanh dương đậm */
        }

        #updateForm input[type="text"] {
            width: 100%; /* Độ rộng đầy đủ */
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #4a90e2; /* Viền xanh dương đậm */
            border-radius: 5px;
            background-color: #ffffff; /* Nền trắng cho input */
            color: #333; /* Màu chữ */
        }

        #updateForm input[type="text"]:focus {
            border-color: #337ab7; /* Màu viền khi focus */
            outline: none; /* Loại bỏ outline mặc định */
        }

        #submitUpdateBtn {
            margin-top: 15px;
            padding: 10px 20px;
            background-color: #4a90e2; /* Nền nút xanh dương đậm */
            color: #fff; /* Màu chữ trắng */
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        #submitUpdateBtn:hover {
            background-color: #337ab7; /* Màu nền khi hover */
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
                        <li><a href="../plan/insert">KHSX</a></li>
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
                    <button class="btn delete-user" id="deleteAccountBtn">DELETE ACCOUNT</button>
                    <button class="btn update-user" id="updateAccountBtn" onclick="toggleUpdateForm()">UPDATE ACCOUNT</button>
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

                <!-- Form Delete Account (ẩn ban đầu) -->
                <div id="formDeleteAccount" style="display: none;">
                    <form action="../user/delete" method="post">
                        <h3>Delete Account</h3>
                        <label for="userID">User ID:</label>
                        <input type="text" name="userID" placeholder="Enter User ID" required><br>
                        <label for="username">Username:</label>
                        <input type="text" name="username" placeholder="Enter Username" required><br>
                        <button type="submit">Delete Account</button>
                    </form>
                </div>
                <!-- Form Update Account, ban đầu được ẩn -->
                <div class="update-form" id="updateForm" style="display: none;">
                    <form action="../user/update" method="post">
                        <h3>Update Account</h3>
                        <label for="employeeID">Employee ID:</label>
                        <input type="number" id="employeeID" name="employeeID" placeholder="Enter Employee ID" required>

                        <label for="userID">User ID:</label>
                        <input type="number" id="userID" name="userID" placeholder="Enter User ID" required>

                        <button class="btn submit-update" id="submitUpdateBtn" >Find user</button>
                    </form>             
                </div>

                <!-- Form mới, mặc định là ẩn -->
                <div id="newUpdateFormContainer" style="display: none;">
                    <form action="UpdateUserServlet" method="post" id="newUpdateForm">
                        <!-- Ô input cho Username (không chỉnh sửa) -->
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" value="${user != null ? user.username : ''}" readonly>

                        <!-- Ô input cho Password (không chỉnh sửa) -->
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" value="${user != null ? user.password : ''}" readonly>

                        <!-- Ô input cho New Password (người dùng nhập mật khẩu mới) -->
                        <label for="newPassword">New Password:</label>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Enter New Password" required>

                        <!-- Nút Submit -->
                        <button type="submit" class="btn submit-update">Update Password</button>
                    </form>
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

                        // Khi nhấn nút "ADD ACCOUNT"
                        document.getElementById("addAccountBtn").addEventListener("click", function () {
                            // Hiển thị Form tạo tài khoản cho nhân viên
                            document.getElementById("formEmployee").style.display = "block";
                            // Hiển thị Form tạo tài khoản cho người dùng (nếu có)
                            var formAccount = document.getElementById("formAccount");
                            if (formAccount) {
                                formAccount.style.display = "block";
                            }
                            // Ẩn Form xóa tài khoản
                            document.getElementById("formDeleteAccount").style.display = "none";
                            document.getElementById("updateForm").style.display = "none";
                        });

                        // Khi nhấn nút "DELETE ACCOUNT"
                        document.getElementById("deleteAccountBtn").addEventListener("click", function () {
                            // Ẩn Form tạo tài khoản cho nhân viên
                            document.getElementById("formEmployee").style.display = "none";
                            document.getElementById("updateForm").style.display = "none";
                            // Ẩn Form tạo tài khoản cho người dùng (nếu có)
                            var formAccount = document.getElementById("formAccount");
                            if (formAccount) {
                                formAccount.style.display = "none";
                            }
                            // Hiển thị Form xóa tài khoản
                            document.getElementById("formDeleteAccount").style.display = "block";
                        });

                        function toggleUpdateForm() {
                            // Lấy các phần tử form theo ID
                            var formDeleteAccount = document.getElementById("formDeleteAccount");
                            var formAccount = document.getElementById("formAccount");
                            var formEmployee = document.getElementById("formEmployee");
                            var updateForm = document.getElementById("updateForm");

                            // Kiểm tra và ẩn các form nếu chúng đang hiển thị
                            if (formDeleteAccount && formDeleteAccount.style.display !== "none") {
                                formDeleteAccount.style.display = "none";
                            }
                            if (formAccount && formAccount.style.display !== "none") {
                                formAccount.style.display = "none";
                            }
                            if (formEmployee && formEmployee.style.display !== "none") {
                                formEmployee.style.display = "none";
                            }

                            // Hiển thị form updateForm
                            if (updateForm) {
                                updateForm.style.display = "block";
                            }
                        }
                        ;

                        var userExists = ${u != null ? "true" : "false"};
                        window.onload = function () {
                            if (userExists === "true") {
                                // Hiển thị form nếu user tồn tại
                                document.getElementById("newUpdateFormContainer").style.display = "block";
                            } else {
                                // Hiển thị thông báo lỗi nếu user không tồn tại
                                document.getElementById("userNotFoundMessage").style.display = "block";
                            }
                        };
        </script>
    </body>
</html>
