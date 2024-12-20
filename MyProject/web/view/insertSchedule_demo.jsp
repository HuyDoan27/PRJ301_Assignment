<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
        <h2>Phân công PlanID ${planId}</h2>

        <form action="../plan/assignwork" method="get">
            <input type="hidden" name="planId" value="${planId}" />
            <!-- Bảng hiển thị thông tin nhập liệu theo ngày và sản phẩm -->
            <table class="schedule-table">
                <!-- Duyệt qua các ngày theo nhóm 3 ngày -->
                <c:forEach var="date" items="${dateRange}" varStatus="status">
                    <!-- Khi bắt đầu nhóm mới hoặc là nhóm đầu tiên, tạo phần <thead> -->
                    <c:if test="${status.index % 3 == 0}">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <!-- Tạo tiêu đề cho 3 ngày tiếp theo -->
                                <c:forEach var="d" begin="${status.index}" end="${Math.min(status.index + 2, dateRange.size() - 1)}">
                                    <th colspan="3">${dateRange[d]}</th>
                                    </c:forEach>
                            </tr>
                            <tr>
                                <th></th> 
                                    <c:forEach var="d" begin="${status.index}" end="${Math.min(status.index + 2, dateRange.size() - 1)}">
                                    <th>K1</th><th>K2</th><th>K3</th>
                                    </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Duyệt qua danh sách sản phẩm để tạo các ô nhập liệu -->
                            <c:forEach var="product" items="${productList}">
                                <tr>
                                    <td>${product.name}</td>
                                    <!-- Lặp qua nhóm 3 ngày, tạo các ô nhập liệu -->
                                    <c:forEach var="d" begin="${status.index}" end="${Math.min(status.index + 2, dateRange.size() - 1)}">
                                        <c:set var="keyK1" value="${product.camid}_${dateRange[d]}_K1_${product.id}" />
                                        <c:set var="keyK2" value="${product.camid}_${dateRange[d]}_K2_${product.id}" />
                                        <c:set var="keyK3" value="${product.camid}_${dateRange[d]}_K3_${product.id}" />

                                        <td>
                                            <input type="number" 
                                                   name="quantity[${product.camid}][${dateRange[d]}][K1][${product.id}]" 
                                                   id="input-${product.camid}-${dateRange[d]}-K1-${product.id}"
                                                   value="${preAssignedQuantities[keyK1] != null ? preAssignedQuantities[keyK1] : ''}" />
                                        </td>
                                        <td>
                                            <input type="number" 
                                                   name="quantity[${product.camid}][${dateRange[d]}][K2][${product.id}]" 
                                                   id="input-${product.camid}-${dateRange[d]}-K2-${product.id}"
                                                   value="${preAssignedQuantities[keyK2] != null ? preAssignedQuantities[keyK2] : ''}" />
                                        </td>
                                        <td>
                                            <input type="number" 
                                                   name="quantity[${product.camid}][${dateRange[d]}][K3][${product.id}]" 
                                                   id="input-${product.camid}-${dateRange[d]}-K3-${product.id}"
                                                   value="${preAssignedQuantities[keyK3] != null ? preAssignedQuantities[keyK3] : ''}" />
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </c:if>
                </c:forEach>
            </table>
            <button type="submit">Lưu lịch</button>
        </form>

        <script>
            document.querySelector('form').addEventListener('submit', function (e) {
                const inputs = document.querySelectorAll('input[type="number"]');

                // Loại bỏ các input không có giá trị để tránh gửi lên server
                inputs.forEach(input => {
                    if (input.value.trim() === "") {
                        input.disabled = true; // Đặt thành disabled để không gửi đi
                    }
                });

                console.log("Form is being submitted.");
            });
        </script>
    </body>
</html>
