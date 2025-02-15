<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  <%-- JSTL fmt 태그 라이브러리 선언 --%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>판매 통계</title>
    <style>
        table {
            width: 100%;
            margin: 10px auto;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .searchInput {
            height: 25px;
        }

    </style>
</head>
<body>
<h2 style="text-align: center;">최근 ${mys}개월 간 판매 통계</h2>
<div class="btn-gr">
    <div></div>
    <div>
        <form action="/manage/saleRecord/main.do" method="get">
            <input class="searchInput"
                   type="text"
                   id="mys"
                   name="mys"
                   placeholder="조회할 기간: e.g) 6"
                   value="${param.mys}">
        </form>
    </div>
</div>

<table id="statisticsTable">
    <thead>
    <tr>
        <th>월</th>
        <th>총 판매 금액</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${monthlySaleRecordStat}">
        <tr>
            <td>${item.key}</td>
            <td><fmt:formatNumber value="${item.value}" pattern="#,###"/>원</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
