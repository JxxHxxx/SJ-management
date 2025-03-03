<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                    <table id="gameAccountTable">
                        <thead>
                        <tr>
                            <th>계정 명</th>
                            <th>계정 생성일</th>
                            <th>첫 수익 실현일</th>
                            <th>게임 명</th>
                            <th>서버 명</th>
                            <th>사용 여부</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${gameAccounts}">
                            <tr>
                                <td value="${item.gameCode}">${item.gameAccountName}</td>
                                <td>${item.createDate}</td>
                                <td>${item.firstSaleDate}</td>
                                <td>${item.gameName}</td>
                                <td>${item.serverName}</td>
                                <td>
                                    <c:if test="${item.used == true}">
                                        사용중
                                    </c:if>
                                    <c:if test="${item.used != true}">
                                        미사용
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="content">
                    <p>C2</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
