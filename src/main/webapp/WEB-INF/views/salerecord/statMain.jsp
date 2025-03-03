<%@ page import="com.jx.management.salerecord.transfer.AccountPerAmountResponse" %>
<%@ page import="java.util.List" %>
<%@ page import="com.jx.management.salerecord.transfer.AccountPerMonthlyAmount" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/left.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>
    <div class="mainContainer">
        <div>
            <jsp:include page="../layout/left.jsp"/>
        </div>
        <div>
            <div class="contentContainer">
                <div class="content">
                    <jsp:include page="monthlyStat.jsp"/>
                </div>
                <div class="content">
                    <jsp:include page="annualStat.jsp"/>
                </div>
            </div>
            <div class="contentContainer">
                <div class="content">
                    <h2>계정 당 수익</h2>
                    <canvas id="salesChart"></canvas>
                    <%
                        List<AccountPerAmountResponse> accountPerAmounts = (List<AccountPerAmountResponse>) request.getAttribute("accountPerAmounts");
                    %>

                    <script>
                        var ctx = document.getElementById('salesChart').getContext('2d');

                        // colors 배열 (게임별 색상 지정)
                        var colors = [
                            { border: "rgba(75, 192, 192, 1)", background: "rgba(75, 192, 192, 0.2)" },
                            { border: "rgba(255, 99, 132, 1)", background: "rgba(255, 99, 132, 0.2)" },
                            { border: "rgba(54, 162, 235, 1)", background: "rgba(54, 162, 235, 0.2)" },
                            { border: "rgba(255, 159, 64, 1)", background: "rgba(255, 159, 64, 0.2)" },
                            { border: "rgba(153, 102, 255, 1)", background: "rgba(153, 102, 255, 0.2)" },
                            { border: "rgba(201, 203, 207, 1)", background: "rgba(201, 203, 207, 0.2)" },
                            { border: "rgba(255, 99, 255, 1)", background: "rgba(255, 99, 255, 0.2)" },
                            { border: "rgba(100, 192, 255, 1)", background: "rgba(100, 192, 255, 0.2)" },
                            { border: "rgba(255, 200, 132, 1)", background: "rgba(255, 200, 132, 0.2)" },
                            { border: "rgba(102, 255, 153, 1)", background: "rgba(102, 255, 153, 0.2)" }
                        ];

                        var datasets = [];
                        <%
                            int index = 0;
                            for (AccountPerAmountResponse gameData : accountPerAmounts) {
                                String gameName = gameData.getGameName();
                                List<Integer> perAmount = gameData.getAccountPerMonthlyAmounts()
                                    .stream().map(AccountPerMonthlyAmount::getAccountPerAmount).toList();
                        %>

                        datasets.push({
                            label: "<%= gameName %>",
                            data: <%= perAmount.toString() %>, // Java List<Integer> -> JavaScript 배열 변환
                            borderColor: colors[<%= index %> % colors.length].border,
                            backgroundColor: colors[<%= index %> % colors.length].background,
                            borderWidth: 2,
                            tension: 0.3,
                            pointRadius: 5,
                            pointBackgroundColor: "white",
                            pointBorderColor: colors[<%= index %> % colors.length].border
                        });

                        <%
                                index++;
                            }
                        %>

                        const salesChart = new Chart(ctx, {
                            type: 'line',
                            data: {
                                labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                                datasets: datasets
                            },
                            options: {
                                responsive: true,
                                plugins: {
                                    legend: { position: 'top' }
                                }
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
