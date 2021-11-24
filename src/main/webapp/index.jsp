<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <title>Работа мечты</title>
    <style>
        body {
            padding: 30px;
            background-image: url('https://catherineasquithgallery.com/uploads/posts/2021-02/1613405883_169-p-fon-dlya-storis-bezhevii-gradient-188.jpg');
            background-repeat: no-repeat;
            background-position: center;
        }
    </style>
</head>
<body>
<div class="container">
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
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
            </li>
        </ul>
    </div>


    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <b>Сегодняшние Вакансии</b>
            </div>
            <div class="card-body">
                <table class="table">
                    <tbody>
                    <c:forEach items="${currentPost}" var="post">
                        <tr>
                            <td>
                                <a href='<c:url value="/post/edit.jsp?id=${post.id}"/>'>
                                    <i class="fa fa-edit mr-3"></i>
                                </a>
                                <c:out value="${post.name}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <b>Сегодняшние Кандидаты</b>
            </div>
            <div class="card-body">
                <table class="table">
                    <c:forEach items="${currentCandidates}" var="candidate">
                        <tr>
                            <td>
                                <a href='<c:url value="/candidate/edit.jsp?id=${candidate.id}"/>'>
                                    <i class="fa fa-edit mr-3"></i>
                                </a>
                                    <c:out value="${candidate.name}"/>
                            <td>
                                <img src="<c:url value='/download?name=${candidate.id}'/>" width="130px"
                                     height="130px"/>
                            </td>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>