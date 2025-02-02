<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 페이지</title>
    <style>
        .container {
            display: flex;
            flex-direction: row; /* 양옆으로 배치 */
            justify-content: space-between; /* 양옆 간격을 균등하게 배치 */
            align-items: flex-start; /* 세로 정렬: 상단에 정렬 */
            gap: 20px; /* 각 콘텐츠 간 간격 */
        }

        .content {
            width: 48%; /* 각 콘텐츠의 너비를 설정 */
            border: 1px dashed #ddd;
            padding: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="content">
        <jsp:include page="showStatistics.jsp" />
    </div>
    <div class="content">
        <jsp:include page="record.jsp" />
    </div>
</div>
</body>
</html>
