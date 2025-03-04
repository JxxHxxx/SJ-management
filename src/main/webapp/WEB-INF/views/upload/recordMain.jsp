<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/record.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/left.css">
    <script src="https://kit.fontawesome.com/7f69257d22.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="mainContainer">
    <div>
        <jsp:include page="../layout/left.jsp"/>
    </div>
    <div>
        <div class="contentContainer">
            <jsp:include page="record.jsp"/>
        </div>
    </div>
</div>
</body>
</html>
