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
                width: 50%;
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

        <form action="../plan/insert" method="POST" class="form-container"> 
            <label for="from">From:</label>
            <input type="date" name="from" id="from" required /> 

            <label for="to">To:</label>
            <input type="date" name="to" id="to" required />

            <label for="did">Workshop:</label>
            <select name="did" id="did" required>
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
                            <td>${p.name}<input type="hidden" name="pid[]" value="${p.id}"/></td>
                            <td><input type="text" name="quantity${p.id}" pattern="\\d*" required/></td>
                            <td><input type="text" name="effort${p.id}" pattern="\\d*" required/></td>
                        </tr>   
                    </c:forEach>
                </c:if>
            </table>

            <input type="submit" value="Save"/>
        </form>

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
