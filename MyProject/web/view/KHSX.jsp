<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            .info {
                max-width: 500px;
                margin: 0 auto;
                padding: 120px ;
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            }

            label {
                font-weight: bold;
                margin: 10px 0 5px;
                color: #333;
            }

            input[type="text"],
            input[type="date"] {
                width: 100%;
                padding: 12px;
                border: 1px solid #ccc;
                border-radius: 6px;
                margin-bottom: 20px;
                font-size: 16px;
                box-sizing: border-box;
                transition: border-color 0.3s;
            }

            input[type="text"]:focus,
            input[type="date"]:focus {
                border-color: #4CAF50;
                outline: none;
            }

            input[type="submit"] {
                width: 100%;
                background-color: #4CAF50;
                color: white;
                padding: 12px;
                font-size: 18px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }

            input[type="submit"]:active {
                background-color: #3e8e41;
            }
            /* Đặt kiểu cho toàn bộ biểu mẫu */
            .form-container {
                width: 90%;
                margin: 20px auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 10px;
                background-color: #f9f9f9;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                font-family: Arial, sans-serif;
                display: flex;
                flex-direction: column;
            }

            /* Kiểu cho nhãn và ô nhập liệu */
            .form-container label {
                font-weight: bold;
                margin-top: 15px;
            }

            .form-container input[type="date"],
            .form-container input[type="text"],
            .form-container select {
                padding: 8px;
                margin-top: 5px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 14px;
                width: 100%;
                box-sizing: border-box;
            }

            /* Kiểu cho nút gửi */
            .form-container input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                font-size: 16px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
                margin-top: 20px;
                width: 150px;
                align-self: center;
            }

            .form-container input[type="submit"]:hover {
                background-color: #45a049;
            }

            /* Kiểu cho bảng sản phẩm */
            .product-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
            }

            .product-table th,
            .product-table td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: left;
            }

            .product-table th {
                background-color: #f2f2f2;
                font-weight: bold;
            }

            .product-table tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .content {
                display: flex;
                justify-content: space-between;
                padding: 20px;
                align-items: flex-start; /* Align panels at the top */
            }

            .left-panel, .right-panel {
                flex: 1;
                max-width: 50%;
                border: 1px solid #ddd;
                border-radius: 15px;
                padding: 20px;
                background-color: #ffffff;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            }

            .right-panel {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .button-container {
                display: flex;
                gap: 10px;
                margin-bottom: 20px;
            }

            .button {
                padding: 10px 15px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 10px;
                cursor: pointer;
                transition: background-color 0.3s ease, box-shadow 0.3s ease;
                font-size: 16px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                width: 200px;
                text-align: center;
            }

            .button:hover {
                background-color: #3e8e41;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
            }

            .date-form {
                width: 80%;
                padding: 15px;
                background-color: #f9f9f9;
                border: 1px solid #ccc;
                border-radius: 10px;
                margin-top: 20px;
                display: none; /* Ẩn đi ban đầu */
                flex-direction: column;
                align-items: center;
            }

            .date-form label {
                margin-right: 10px;
                font-size: 16px;
                color: #333;
            }

            .date-form input[type="date"] {
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 5px;
                font-size: 16px;
                width: 200px;
            }

            .date-form input[type="submit"] {
                padding: 8px 15px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: background-color 0.3s ease, box-shadow 0.3s ease;
                margin-top: 10px;
            }

            .date-form input[type="submit"]:hover {
                background-color: #3e8e41;
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
            }

            .plan-section {
                width: 80%;
                margin-top: 20px;
            }

            #planSection {
                width: 80%;
                border-collapse: collapse;
                margin: 20px 0;
                background-color: #fff;
                border: 1px solid #ddd;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }

            #planSection table, #planSection th, #planSection td {
                border: 1px solid #ddd;
            }

            #planSection th, #planSection td {
                padding: 10px;
                text-align: left;
            }

            #planSection th {
                background-color: #f2f2f2;
            }

            /* CSS cho phần lỗi */
            .error {
                color: red;
                font-weight: bold;
            }

            .close-btn {
                float: right;
                font-size: 18px;
                cursor: pointer;
                color: #ff0000;
                padding: 5px;
                margin-left: 10px;
            }

            .close-btn:hover {
                color: #b30000;
            }
        </style>

    </head>
    <body>
        <header>
            <div class="container">
                <nav>
                    <img class="logo" src="https://www.shutterstock.com/image-vector/3d-abc-cubes-multicolored-vector-600nw-2374071877.jpg" alt="ABC">
                    <ul>
                        <li><a href="../view/home.html">Home</a></li>
                        <li><a href="order.html">Đặt hàng</a></li>
                        <li><a href="#">Đơn hàng</a></li>
                        <li><a href="../plan/insert" class="active">KHSX</a></li>
                        <li><a href="#">Xưởng</a></li>                   
                        <li><a href="../user/list">Nhân sự</a></li>
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

        <c:if test="${not empty message}">
            <p class="success">${message}</p>
        </c:if>

        <div class="content">
            <div class="left-panel">
                <form action="../plan/insert" method="post" class="form-container"> 
                    <label for="from">From:</label>
                    <input type="date" name="from" id="from" /> 

                    <label for="to">To:</label>
                    <input type="date" name="to" id="to" />

                    <label for="did">Workshop:</label>
                    <select name="did" id="did">
                        <c:if test="${not empty requestScope.depts}">
                            <c:forEach items="${requestScope.depts}" var="d">
                                <option value="${d.did}">${d.dname}</option>
                            </c:forEach>
                        </c:if>
                    </select>

                    <table class="product-table">
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Estimated Effort</th>
                        </tr>
                        <c:if test="${not empty requestScope.products}">
                            <c:forEach items="${requestScope.products}" var="p">
                                <tr>
                                    <td>${p.name}<input type="hidden" name="pid" value="${p.id}"/></td>
                                    <td><input type="text" name="quantity${p.id}"/></td>
                                    <td><input type="text" name="effort${p.id}"/></td>
                                </tr>   
                            </c:forEach>
                        </c:if>
                    </table>

                    <input type="submit" value="Save"/>
                </form>
            </div>

            <div class="right-panel">
                <div class="button-container">
                    <div class="button" onclick="showDateForm()">Theo dõi tiến độ kế hoạch</div>
                    <div class="button">Phân công</div>
                </div>

                <!-- Form nhập ngày (được căn chỉnh bên trong right-panel) -->
                <div id="dateForm" class="date-form">
                    <form action="../plan/list" method="get">
                        <label for="inputDate">Chọn ngày:</label>
                        <input type="date" id="inputDate" name="inputDate" required />
                        <input type="submit" value="Xem kế hoạch" />
                    </form>
                </div>

                <!-- Phần hiện thị bảng kế hoạch sau khi lấy dữ liệu -->
                <c:if test="${not empty plans}">
                    <div id="planSection" class="plan-section">
                        <h2>
                            Tiến độ kế hoạch đến ngày : ${inputDate} 
                            <span class="close-btn" onclick="hidePlanSection()">X</span>
                        </h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>Plan ID</th>
                                    <th>Ngày bắt đầu</th>
                                    <th>Ngày kết thúc</th>
                                    <th>Tổng số lượng</th>
                                    <th>Số lượng lũy kế</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="result" items="${plans}">
                                    <tr>
                                        <td>${result.plid}</td>
                                        <td>${result.start_day}</td>
                                        <td>${result.end_day}</td>
                                        <td>${result.totalQuantity}</td>
                                        <td>${result.cumulativeQuantity}</td>
                                        <td>${result.status}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>

            <!-- Hiển thị thông báo nếu không có kế hoạch nào được tìm thấy -->
            <c:if test="${empty plans}">
                <div id="noPlanMessage" style="display: none;">
                    <p>Không có kế hoạch nào được tìm thấy cho ngày đã chọn.</p>
                </div>
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
        <script>
                function hidePlanSection() {
                    document.getElementById("planSection").style.display = "none";
                };
                
                function showDateForm() {
                    document.getElementById("dateForm").style.display = "flex";
                };
        </script>
    </body>
</html>
