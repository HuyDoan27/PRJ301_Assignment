<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Worker and Attendent Management</title>
        <style>
            /* Reset một số kiểu mặc định */
            body {
                margin: 0;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8f9fa; /* Màu nền nhẹ nhàng */
                color: #495057; /* Màu chữ */
            }

            .container {
                max-width: 900px; /* Tăng chiều rộng tối đa */
                margin: 20px auto;
                padding: 30px;
                background: #ffffff; /* Màu nền trắng */
                border-radius: 12px; /* Bo góc */
                box-shadow: 0 6px 30px rgba(0, 0, 0, 0.15); /* Đổ bóng */
            }

            /* Style cho tiêu đề */
            h1 {
                text-align: center;
                color: #007bff; /* Màu xanh */
                font-size: 2.5em; /* Tăng kích thước chữ */
                margin-bottom: 30px; /* Khoảng cách dưới tiêu đề */
            }

            /* Style cho form */
            form {
                margin-bottom: 40px; /* Tăng khoảng cách dưới form */
            }

            label {
                display: block;
                margin: 12px 0 6px;
                font-weight: bold; /* Chữ đậm */
                color: #343a40; /* Màu chữ tối */
            }

            input[type="text"],
            input[type="number"],
            select {
                width: calc(100% - 24px); /* Chiều rộng đầy đủ trừ padding */
                padding: 12px;
                margin: 5px 0 15px;
                border: 1px solid #ced4da; /* Viền nhạt */
                border-radius: 8px; /* Bo góc */
                font-size: 16px; /* Kích thước chữ */
                transition: border-color 0.3s; /* Hiệu ứng chuyển tiếp */
            }

            input[type="text"]:focus,
            input[type="number"]:focus,
            select:focus {
                border-color: #007bff; /* Đổi màu viền khi focus */
                outline: none; /* Ẩn viền ngoài */
            }

            /* Style cho nút submit */
            button {
                display: inline-block;
                padding: 12px 20px;
                color: #ffffff; /* Chữ trắng */
                background-color: #007bff; /* Màu nền nút */
                border: none; /* Ẩn viền */
                border-radius: 8px; /* Bo góc */
                cursor: pointer; /* Con trỏ chuột */
                font-size: 16px; /* Kích thước chữ */
                transition: background-color 0.3s; /* Hiệu ứng chuyển tiếp */
                margin-top: 10px; /* Khoảng cách trên nút */
            }

            button:hover {
                background-color: #0056b3; /* Màu khi hover */
            }

            /* Style cho bảng */
            table {
                width: 100%; /* Chiều rộng đầy đủ */
                border-collapse: collapse; /* Gộp viền */
                margin-top: 20px; /* Khoảng cách trên bảng */
                font-size: 16px; /* Kích thước chữ */
            }

            th, td {
                padding: 15px; /* Khoảng cách trong ô */
                text-align: left; /* Căn trái */
                border: 1px solid #dee2e6; /* Viền bảng */
            }

            th {
                background-color: #007bff; /* Màu nền tiêu đề */
                color: white; /* Chữ trắng */
            }

            tr:nth-child(even) {
                background-color: #f2f2f2; /* Màu nền cho dòng chẵn */
            }

            tr:hover {
                background-color: #e9ecef; /* Màu nền khi hover */
            }

            /* Style cho thông báo lỗi */
            .error-message {
                color: #dc3545; /* Màu đỏ cho lỗi */
                font-weight: bold; /* Chữ đậm */
                margin-top: 10px; /* Khoảng cách trên thông báo lỗi */
            }

            .success-message {
                color: #28a745; /* Màu xanh cho thành công */
                font-weight: bold; /* Chữ đậm */
                margin-top: 10px; /* Khoảng cách trên thông báo thành công */
            }

            /* Cân bằng chiều cao các ô */
            input[type="text"],
            input[type="number"],
            select {
                box-sizing: border-box; /* Đảm bảo padding không làm tăng chiều rộng */
            }
            
            .close-button-container {
                position: absolute;
                top: 10px;
                right: 10px;
            }

        </style>
    </head>
    <body>
        <div class="close-button-container">
            <a href="listPlanDetail.jsp" class="close-button">×</a>
        </div>
        
        <h1>Manage Worker Schedule</h1>
        <form action="../plan/assignworkworker" method="get">
            <input type="hidden" name="scid" value="${param.scid}">
            <table border="1" id="workerScheduleTable">
                <thead>
                    <tr>
                        <th>Employee</th>
                        <th>Quantity</th>
                        <th>Actual Quantity</th>
                        <th>Alpha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="workerSchedule" items="${workerScheduleList}">
                        <tr>
                            <td>
                                <select name="eid" onchange="updateEmployeeName(this, 'ename_${workerSchedule.wsid}')">
                                    <c:forEach var="employee" items="${employees}">
                                        <option value="${employee.eid}" <c:if test="${employee.eid == workerSchedule.eid}">selected</c:if>>
                                            ${employee.eid} (${employee.ename})
                                        </option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="ename_${workerSchedule.wsid}" value="${workerSchedule.ename}">
                            </td>
                            <td>
                                <input type="number" id="quantity_${workerSchedule.wsid}" name="quantity_${workerSchedule.wsid}" 
                                       value="${workerSchedule.quantity}" 
                                       oninput="updateAnphal('quantity_${workerSchedule.wsid}', 'actualQuantity_${workerSchedule.wsid}', 'anphal_${workerSchedule.wsid}')" readonly>
                            </td>
                            <td>
                                <input type="number" id="actualQuantity_${workerSchedule.wsid}" name="actualQuantity" 
                                       value="${workerSchedule.attendentQuantity}" 
                                       oninput="updateAnphal('quantity_${workerSchedule.wsid}', 'actualQuantity_${workerSchedule.wsid}', 'anphal_${workerSchedule.wsid}')">
                            </td>
                            <td>
                                <input type="text" id="anphal_${workerSchedule.wsid}" name ="alpha"
                                       value="${(workerSchedule.quantity != 0) ? (workerSchedule.attendentQuantity / workerSchedule.quantity) : 0}" 
                                       readonly>
                            </td>
                    <input type="hidden" name="wsid" value="${workerSchedule.wsid}">
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <button type="button" onclick="addRow()">Add Row</button>
            <button type="button" onclick="deleteRow()">Delete Row</button>
            <button type="submit">Submit Changes</button>
        </form>

        <script>
            function updateAnphal(quantityInputId, actualQuantityInputId, anphalDisplayId) {
                const quantityInput = document.getElementById(quantityInputId);
                const actualQuantityInput = document.getElementById(actualQuantityInputId);
                const anphalDisplay = document.getElementById(anphalDisplayId);

                // Chuyển đổi giá trị input thành số và kiểm tra giá trị
                const quantity = parseFloat(quantityInput.value) || 0;
                const actualQuantity = parseFloat(actualQuantityInput.value) || 0;

                // Tính anphal
                const anphal = (quantity > 0) ? (actualQuantity / quantity) : 0;
                anphalDisplay.value = anphal.toFixed(2); // Định dạng số
            }

            function updateEmployeeName(selectElement, nameDisplayId) {
                const selectedOption = selectElement.options[selectElement.selectedIndex];
                document.getElementById(nameDisplayId).value = selectedOption.text;
            }

        </script>
    </body>
</html>
