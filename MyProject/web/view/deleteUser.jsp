<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Xóa Tài Khoản Người Dùng</title>
        <style>
            /* Tổng thể */
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
                font-family: 'Helvetica Neue', Arial, sans-serif;
            }

            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background: #f3f4f6;
                color: #333;
                padding: 20px;
            }

            .container {
                width: 100%;
                max-width: 400px;
                background-color: #fff;
                border-radius: 10px;
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
                padding: 20px;
            }

            .header {
                font-size: 1.5em;
                font-weight: 600;
                color: #007bff;
                text-align: center;
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin: 15px 0 5px;
                font-weight: 500;
                color: #333;
            }

            input[type="text"],
            input[type="number"] {
                width: 100%;
                padding: 12px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 1em;
            }

            input[readonly] {
                background-color: #f9f9f9;
                color: #555;
            }

            .message {
                color: #e74c3c;
                margin: 15px 0;
                font-size: 0.95em;
                text-align: center;
            }

            .buttons {
                display: flex;
                justify-content: space-between;
                gap: 10px;
                margin-top: 20px;
            }

            .buttons button {
                flex: 1;
                padding: 12px;
                font-size: 1em;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 600;
                transition: background-color 0.3s;
            }

            .submit-button {
                background-color: #007bff;
                color: #fff;
            }

            .submit-button:hover {
                background-color: #0056b3;
            }

            .cancel-button {
                background-color: #6c757d;
                color: #fff;
            }

            .cancel-button:hover {
                background-color: #5a6268;
            }

            /* Khoảng cách giữa các form */
            #confirmDeleteForm {
                border-top: 1px solid #ddd;
                padding-top: 20px;
                margin-top: 20px;
            }

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
        <script>
            function cancelDelete() {
                document.getElementById("confirmDeleteForm").style.display = "none";
            }
        </script>
    </head>
    <body>
        <div class="close-button-container">
            <a href="listalluser.jsp" class="close-button">×</a>
        </div>

        <div class="container">
            <div class="header">Xóa Tài Khoản</div>

            <!-- Thông báo khi không tìm thấy user -->
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>

            <c:if test="${not empty sessionScope.errorMessage}">
                <p class="message" style="color: red;">${sessionScope.errorMessage}</p>
                <c:remove var="errorMessage" scope="session"/>
            </c:if>   

            <!-- Form nhập thông tin xóa tài khoản luôn hiển thị -->
            <div id="formDeleteAccount">
                <form action="../user/delete" method="post">
                    <label for="userID">User ID:</label>
                    <input type="number" name="userID" placeholder="Nhập ID người dùng" required>
                    <button type="submit" class="submit-button">Tìm kiếm</button>
                </form>
            </div>

            <!-- Form xác nhận xóa, chỉ hiển thị khi tìm thấy user -->
            <c:if test="${not empty user}">
                <div id="confirmDeleteForm">
                    <p style="font-weight: bold; margin-bottom: 20px;">
                        Bạn có chắc chắn muốn xóa tài khoản này?
                    </p>
                    <form action="../user/delete" method="get">
                        <label for="userID">User ID:</label>
                        <input type="text" name="userID" value="${user.uid}" readonly>

                        <label for="username">Tên người dùng:</label>
                        <input type="text" name="username" value="${user.username}" readonly>

                        <label for="password">Mật khẩu:</label>
                        <input type="text" name="password" value="${user.password}" readonly>

                        <div class="buttons">
                            <button type="submit" class="submit-button">Có</button>
                            <button type="button" class="cancel-button" onclick="cancelDelete()">Không</button>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </body>
</html>
