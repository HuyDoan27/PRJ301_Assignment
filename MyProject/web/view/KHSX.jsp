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
                width: 100%;
                margin-top: 20px;
            }

            #planSection {
                width: 100%;
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

            /* Update Form Section Styling */
            #updateFormSection {
                display: none;
                margin-top: 20px;
                padding: 20px;
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                width: 100%;
                font-family: Arial, sans-serif;
            }

            #updateFormSection h3 {
                font-size: 1.5em;
                margin-bottom: 15px;
                color: #333;
            }

            #updateFormSection label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #555;
            }

            #updateFormSection input[type="text"],
            #updateFormSection input[type="date"],
            #updateFormSection select {
                width: 100%;
                padding: 8px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1em;
                color: #333;
            }

            #updateFormSection input[readonly] {
                background-color: #eee;
                color: #777;
                cursor: not-allowed;
            }

            #updateFormSection button[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                margin-right: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
            }

            #updateFormSection button[type="submit"]:hover {
                background-color: #45a049;
            }

            #updateFormSection button[type="button"] {
                background-color: #f44336;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
            }

            #updateFormSection button[type="button"]:hover {
                background-color: #e53935;
            }

            /* Ensure the form displays in the right position */
            .right-panel {
                position: relative;
            }

            .plan-section + #updateFormSection {
                display: block; /* To ensure it displays right below the plan-section */
                margin-top: 20px;
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
                        <li><a href="../view/order.html">Đặt hàng</a></li>
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

        <c:if test="${not empty successMessage}">
            <script>
                // Chỉ hiển thị alert nếu chưa hiển thị trong phiên làm việc hiện tại
                if (!sessionStorage.getItem("alertShown")) {
                    alert("${successMessage}");
                    sessionStorage.setItem("alertShown", "true");
                }
            </script>
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
                    <div class="button" onclick="showPlanIdForm()">Phân công</div>
                </div>

                <!-- Form nhập PlanID -->
                <div id="planIdForm" class="date-form" style="display: none;">
                    <form action="../planCampain/list" method="post" onsubmit="return validatePlanId()">
                        <label for="planIdInput">Nhập PlanID:</label>
                        <input type="number" id="planIdInput" name="plid" pattern="\d*" required />
                        <input type="submit" value="Gửi" />
                    </form>
                </div>

                <!-- Form nhập ngày (được căn chỉnh bên trong right-panel) -->
                <div id="dateForm" class="date-form">
                    <form action="../plan/list" method="post">
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
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="result" items="${plans}">
                                    <tr>
                                        <td>${result.plid}</td>
                                        <td>${result.start_day}</td>
                                        <td>${result.end_day}</td>
                                        <td>
                                            <c:forEach var="product" items="${result.totalProductQuantities}">
                                                ${product.name}: ${product.quantity}<br/>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="cumulativeProduct" items="${result.cumulativeProductQuantities}">
                                                ${cumulativeProduct.name}: ${cumulativeProduct.quantity}<br/>
                                            </c:forEach>
                                        </td>
                                        <td>${result.status}</td>
                                        <td>
                                            <button onclick="showUpdateForm('${result.plid}')">Update</button>
                                            <button onclick="deletePlan(${result.plid})">Delete</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

                <div id="updateFormSection" style="display: none;">
                    <h3>Cập nhật Kế hoạch</h3>
                    <form action="../plan/update" method="POST">
                        <input type="text" id="plid" name="plid" readonly/>

                        <label for="startDate">Ngày bắt đầu:</label>
                        <input type="date" id="startDate" name="startDate" placeholder="Enter new start date" required/><br/>

                        <label for="endDate">Ngày kết thúc:</label>
                        <input type="date" id="endDate" name="endDate" placeholder="Enter new end date" required/><br/>

                        <label for="did">Department:</label>
                        <select name="did" id="did">
                            <c:if test="${not empty requestScope.depts}">
                                <c:forEach items="${requestScope.depts}" var="d">
                                    <option value="${d.did}">${d.dname}</option>
                                </c:forEach>
                            </c:if>
                        </select>

                        <h4>Product Quantities</h4>
                        <table boder="1">
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th>Effort</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.products}" var="p">
                                    <tr>
                                        <td>${p.name}<input type="hidden" name="productIds" value="${p.id}"/></td>
                                        <td><input type="text" name="quantity${p.id}" placeholder="Enter quantity"/></td>
                                        <td><input type="text" name="effort${p.id}" placeholder="Enter effort"/></td>
                                    </tr>   
                                </c:forEach>
                            </tbody>
                        </table>

                        <button type="submit">Submit</button>
                        <button type="button" onclick="hideUpdateForm()">Cancel</button>
                    </form>
                </div>
            </div>

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
                            }
                            ;

                            function showDateForm() {
                                document.getElementById("dateForm").style.display = "flex";
                            }
                            ;

                            function showPlanIdForm() {
                                document.getElementById("planIdForm").style.display = "flex";
                            }

                            function validatePlanId() {
                                const planIdInput = document.getElementById("planIdInput").value;
                                const isValid = /^\d+$/.test(planIdInput); // Kiểm tra xem có phải số không

                                if (!isValid) {
                                    alert("Vui lòng nhập PlanID hợp lệ (chỉ chấp nhận số).");
                                }
                                return isValid;
                            }

                            function showUpdateForm(plid) {
                                // Đặt giá trị plid vào form
                                document.getElementById("plid").value = plid;

                                // Duyệt qua tất cả các sản phẩm và chỉ hiển thị sản phẩm có plid tương ứng
                                const productEntries = document.querySelectorAll(".product-entry");
                                productEntries.forEach(entry => {
                                    // Nếu sản phẩm có data-plid khớp với plid hiện tại, thì hiển thị
                                    if (entry.getAttribute("data-plid") == plid) {
                                        entry.style.display = "block";
                                    } else {
                                        entry.style.display = "none";
                                    }
                                });

                                // Hiển thị form
                                document.getElementById("updateFormSection").style.display = "block";
                            }

                            function hideUpdateForm() {
                                document.getElementById("updateFormSection").style.display = "none";
                            }
        </script>
    </body>
</html>
