<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Plan Campaign Detail List</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
                text-align: left;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #ddd;
            }
            .table-title {
                margin-top: 20px;
                font-weight: bold;
                font-size: 1.2em;
                border-bottom: 1px solid #333;
                padding-bottom: 5px;
            }
            .clear {
                clear: both; /* Để phân tách các phần tử khác nhau */
            }
        </style>
    </head>
    <body>

        <h2>Plan Campaign Detail List</h2>
        <c:set var="startEndDates" value="${sessionScope.startEndDates}" />
        <h3>Date Range: ${startEndDates[0]} to ${startEndDates[1]}</h3>

        <table>
            <thead>
                <tr>
                    <th>CamID</th>
                    <th>Plan ID</th>
                    <th>Product ID</th>
                    <th>Quantity</th>
                    <th>Estimated Effort</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="planCampain" items="${sessionScope.planCampainList}">
                    <tr>
                        <td>
                            <a onclick="sendCamid(${planCampain.camid})" style="text-decoration: none; color: blue;">${planCampain.camid} </a>
                        </td>
                        <td>${planCampain.plan.plid}</td>
                        <td>${planCampain.product.id}</td>
                        <td>${planCampain.quantity}</td>
                        <td>${planCampain.estimatedeffort}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:set var="plid" value="${sessionScope.planCampainList[0].plan.plid}" />


        <form action="../plan/assignwork" method="Post" id="plidForm">
            <input type="hidden" name="plid" value="${plid}">
            <button type="submit">Assign Work for Plan ${plid}</button>
        </form>

        <div id="scheduleSection" class="table-title">Schedule Campaigns</div>
        <table id="scheduleTable">
            <thead>
                <tr>
                    <th>CamID</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Quantity</th>
                    <th>SCID</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="totalQuantity" value="0" />
                <c:forEach var="schedule" items="${scheduleCampainList}">
                    <tr>
                        <td>${schedule.camid}</td>
                        <td>${schedule.date}</td>
                        <td>${schedule.shift}</td>
                        <td>${schedule.quantity}</td>
                        <td>
                            <a onclick="sendScid(${schedule.scid})" style="text-decoration: none; color: blue;">${schedule.scid}
                            </a>
                        </td>
                    </tr>
                    <c:set var="totalQuantity" value="${totalQuantity + schedule.quantity}" />
                </c:forEach>
                <tr>
                    <td colspan="3" style="text-align: right; font-weight: bold;">Total Quantity:</td>
                    <td>${totalQuantity}</td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="3" style="text-align: right; font-weight: bold;">Còn:</td>
                    <td>
                        <c:set var="remainingQuantity" value="0" />
                        <c:set var="found" value="false" />
                        <c:forEach var="planCampain" items="${sessionScope.planCampainList}">
                            <c:if test="${!found}">
                                <c:set var="calculatedQuantity" value="${planCampain.quantity}" />
                                <c:forEach var="schedule" items="${scheduleCampainList}">
                                    <c:if test="${schedule.camid == planCampain.camid}">
                                        <c:set var="calculatedQuantity" value="${calculatedQuantity - totalQuantity}" />
                                        <c:set var="found" value="true" /> 
                                    </c:if>
                                </c:forEach>
                                <c:if test="${found}">
                                    <c:set var="remainingQuantity" value="${calculatedQuantity <= 0 ? 0 : calculatedQuantity}" />
                                </c:if>
                            </c:if>
                        </c:forEach>

                        ${remainingQuantity}
                    </td>
                    <td></td>
                </tr>
            </tbody>
        </table>

        <div class="clear"></div>

        <script>
            function sendCamid(camid) {
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = '../schedule/list';

                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'camid';
                input.value = camid;

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            };

            function sendScid(scid) {
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = '../plan/assignworkworker';

                var input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'scid';
                input.value = scid;

                form.appendChild(input);
                document.body.appendChild(form);
                console.log("Submitting to URL:", form.action); 
                form.submit();
            };

            function showScheduleTable() {
                document.getElementById('scheduleTable').style.display = 'table'; // Hiện bảng
                document.getElementById('scheduleSection').style.display = 'block'; // Hiện tiêu đề bảng
            }
        </script>

    </body>
</html>
