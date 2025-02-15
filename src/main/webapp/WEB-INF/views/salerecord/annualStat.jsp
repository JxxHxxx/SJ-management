<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  <%-- JSTL fmt 태그 라이브러리 선언 --%>

<!DOCTYPE html>
<h2 style="text-align: center;">${year}년 판매 통계</h2>
<div class="btn-gr">
    <div></div>
    <div>
        <form action="/manage/saleRecord/statMain.do" method="get">
            <input class="searchInput"
                   type="text"
                   id="year"
                   name="year"
                   placeholder="조회할 연도 e.g) 2024"
                   value="${param.year}">
        </form>
    </div>
</div>
<table id="statisticsTable">
    <thead>
    <tr>
        <th>게임 이름</th>
        <th>거래 건수</th>
        <th>총 판매 금액</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${result}">
        <tr>
            <td>${record.gameName}</td>
            <td>${record.transactionCount}건</td>
            <td><fmt:formatNumber value="${record.amountSum}" pattern="#,###"/>원</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
