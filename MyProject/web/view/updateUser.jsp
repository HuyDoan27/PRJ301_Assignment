<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update User</title>
        <style>
            /* Định dạng chung cho cả hai form */
            form {
                max-width: 400px;
                margin: 20px auto;
                padding: 20px;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                font-family: Arial, sans-serif;
            }

            form h3 {
                margin-bottom: 15px;
                font-size: 1.5em;
                color: #333;
                text-align: center;
            }

            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
                color: #555;
            }

            input[type="text"],
            input[type="number"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }

            input[type="text"]:read-only,
            input[type="number"]:read-only,
            input[type="password"]:read-only {
                background-color: #e9ecef;
                cursor: not-allowed;
            }

            button.btn.submit-update {
                width: 100%;
                padding: 10px;
                font-size: 1em;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            button.btn.submit-update:hover {
                background-color: #45a049;
            }

            /* Thông báo lỗi */
            .notification-message {
                max-width: 400px;
                margin: 10px auto;
                padding: 10px;
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
                border-radius: 4px;
                font-size: 0.9em;
                text-align: center;
                font-family: Arial, sans-serif;
            }

            /* Container cho nút đóng */
            .close-button-container {
                position: absolute;
                top: 10px;
                right: 10px;
            }

            /* Nút đóng (dấu X) */
            .close-button {
                font-size: 24px;
                color: #333;
                text-decoration: none;
                font-weight: bold;
                background-color: transparent;
                border: none;
                cursor: pointer;
            }

            .close-button:hover {
                color: #ff0000; /* Đổi màu khi hover để dễ nhận biết */
            }


        </style>
    </head>
    <body>
        <div class="close-button-container">
            <a href="listalluser.jsp" class="close-button">×</a>
        </div>

        <div class="update-form" id="updateForm">
            <form action="../user/update" method="post">
                <h3>Find Account</h3>
                <label for="employeeID">Employee ID:</label>
                <input type="number" id="employeeID" name="employeeID" placeholder="Enter Employee ID" required>

                <label for="userID">User ID:</label>
                <input type="number" id="userID" name="userID" placeholder="Enter User ID" required>

                <button class="btn submit-update" id="submitUpdateBtn">Find user</button>
            </form>   
        </div>

        <!-- Hiển thị form 2 nếu có user -->
        <c:if test="${sessionScope.user != null}">
            <div id="newUpdateFormContainer">
                <form action="../user/update" method="get" id="newUpdateForm">
                    <h3>Update Password for ${sessionScope.user.username}</h3>

                    <input type="hidden" name="userID" value="${sessionScope.user.uid}" />

                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" value="${sessionScope.user.username}" readonly>

                    <label for="password">Password:</label>
                    <input type="number" id="password" name="password" value="${sessionScope.user.password}" readonly>

                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Enter New Password" required>

                    <button type="submit" class="btn submit-update">Update Password</button>
                </form>
            </div>
        </c:if>

        <c:if test="${not empty sessionScope.updateStatus}">
            <div class="notification-message">
                ${sessionScope.updateStatus}
            </div>
            <c:remove var="updateStatus" scope="session" />
        </c:if>

        <!-- Thông báo lỗi nếu không tìm thấy user -->
        <c:if test="${sessionScope.searchStatus == 'notFound'}">
            <div class="notification-message">
                Không tìm thấy tài khoản. Vui lòng kiểm tra lại thông tin.
            </div>
            <c:remove var="searchStatus" scope="session" />
        </c:if>
    </body>
</html>
