<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách Plan</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 20px;
                background-color: #f4f4f4;
            }
            h2 {
                color: #333;
            }
            table {
                width: 80%;
                border-collapse: collapse;
                margin: 20px 0;
                background-color: #fff;
                border: 1px solid #ddd;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .error {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <h2>Danh sách Plan</h2>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <!-- Hiển thị bảng dữ liệu Plan -->
        <c:if test="${not empty plans}">
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
                    <c:forEach var="result" items="${requestScope.plans}">
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
        </c:if>

        <!-- Hiển thị thông báo nếu không có kế hoạch nào được tìm thấy -->
        <c:if test="${empty plans}">
            <p>Không có kế hoạch nào được tìm thấy cho ngày đã chọn.</p>
        </c:if>
    </body>
</html>
