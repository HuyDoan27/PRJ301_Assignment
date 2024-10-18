<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="your.package.PlanQuery" %>
<%@ page import="your.package.PlanResult" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Plan Data</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
    </style>
</head>
<body>
    <h2>Danh sách Plan</h2>

            <table>
                <thead>
                    <tr>
                        <th>Plan ID</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Tổng số lượng</th>
                        <th>Số lượng lũy kế</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="result" items="${requestScope.plans}">
                        <tr>
                            <td>${result.plid}</td>
                            <td>${result.startDate}</td>
                            <td>${result.endDate}</td>
                            <td>${result.totalQuantity}</td>
                            <td>${result.cumulativeQuantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

    
</body>
</html>
