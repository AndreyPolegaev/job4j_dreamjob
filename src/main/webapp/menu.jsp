<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>menu</title>
    <style>
        body {
            padding: 30px;
            background-image: url('https://phonoteka.org/uploads/posts/2021-04/1618959233_33-phonoteka_org-p-stilnii-gradient-fon-35.jpg');
            background-repeat: no-repeat;
            background-position: center;
        }
    </style>
</head>
<body>
<div class="row">
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
        </li>
        <c:if test="${user != null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        </c:if>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/index.do">На главную</a>
        </li>
    </ul>
</div>
</body>
</html>
