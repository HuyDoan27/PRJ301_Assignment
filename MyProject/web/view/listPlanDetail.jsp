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
            <a href="../plan/insert" class="close-button">×</a>
        </div>

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
                    <th>Quantity (Đã phân công)</th>
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
                    <c:if test="${empty camid1}">
                        <c:set var="camid1" value="${schedule.camid}" scope="page" />
                    </c:if>
                </c:forEach>
                    <tr>
                        <td colspan="3" style="text-align: right; font-weight: bold;">Total Quantity:</td>
                        <td>${totalQuantity}</td>
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
            }
            ;

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
            }
            ;

            function showScheduleTable() {
                document.getElementById('scheduleTable').style.display = 'table';
                document.getElementById('scheduleSection').style.display = 'block';
            }
        </script>

    </body>
</html>
