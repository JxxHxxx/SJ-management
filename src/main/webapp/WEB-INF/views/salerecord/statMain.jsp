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
                    <jsp:include page="monthlyStat.jsp"/>
                </div>
                <div class="content">
                    <jsp:include page="annualStat.jsp"/>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
