<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>ABC Company - KHSX</title>
        <link rel="stylesheet" href="../css/home.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
            }

            h2 {
                color: #333;
                text-align: center;
            }

            form {
                max-width: 500px; /* Giới hạn chiều rộng của form */
                margin: 0 auto; /* Đặt form vào giữa trang */
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block; /* Đặt label thành khối để tạo khoảng cách */
                margin: 10px 0 5px; /* Tạo khoảng cách trên và dưới */
            }

            input[type="text"],
            input[type="datetime-local"] {
                width: 100%; /* Chiều rộng 100% */
                padding: 10px; /* Thêm padding cho input */
                border: 1px solid #ccc; /* Đường viền cho input */
                border-radius: 4px; /* Bo góc cho input */
                margin-bottom: 15px; /* Khoảng cách dưới mỗi input */
                box-sizing: border-box; /* Đảm bảo padding không làm tăng kích thước */
            }

            input[type="submit"] {
                background-color: #4CAF50; /* Màu nền của nút */
                color: white; /* Màu chữ trên nút */
                padding: 10px 15px; /* Padding cho nút */
                border: none; /* Không có đường viền */
                border-radius: 4px; /* Bo góc cho nút */
                cursor: pointer; /* Con trỏ thay đổi khi hover */
                font-size: 16px; /* Kích thước chữ */
            }

            input[type="submit"]:hover {
                background-color: #45a049; /* Màu nền khi hover */
            }
        </style>

    </head>
    <body>
        <header>
            <div class="container">
                <nav>
                    <img class="logo" src="https://www.shutterstock.com/image-vector/3d-abc-cubes-multicolored-vector-600nw-2374071877.jpg" alt="ABC">
                    <ul>
                        <li><a href="home.html">Home</a></li>
                        <li><a href="order.html">Đặt hàng</a></li>
                        <li><a href="#">Đơn hàng</a></li>
                        <li><a href="#" class="active">KHSX</a></li>
                        <li><a href="#">Xưởng</a></li>                   
                        <li><a href="../user/list">Nhân sự</a></li>
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

        <main>
            <h2>Nhập Thông Tin Kế Hoạch Sản Xuất</h2>

            <%-- Hiển thị thông báo nếu có --%>
            <% if (request.getAttribute("message") != null) { %>
            <div class="alert success"><%= request.getAttribute("message") %></div>
            <% } %>

            <% if (request.getAttribute("error") != null) { %>
            <div class="alert error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="../plan/insert" method="post">

                <label for="start">Thời Gian Bắt Đầu (start):</label>
                <input type="datetime-local" id="start" name="start" required>

                <label for="end">Thời Gian Kết Thúc (end):</label>
                <input type="datetime-local" id="end" name="end" required>

                <label for="did">Mã Phòng Ban (did):</label>
                <input type="text" id="did" name="did" required>

                <input type="submit" value="Lưu">
            </form>
        </main>

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
