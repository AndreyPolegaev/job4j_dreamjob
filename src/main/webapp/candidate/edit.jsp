<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
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
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <title>Работа мечты</title>
    <%--    стили для списка options--%>
    <style>
        #select {
            width: 1100px;
            height: 100px;
            line-height: 100px;
            font-size: 15px;
        }
    </style>
</head>

<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "");
    if (id != null) {
        candidate = PsqlStore.instOf().findCandidateById(Integer.valueOf(id));
    }
%>
<%--извлечение данных через сервлет CityServlet из базы данных и заполнение списка option--%>
<script>
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/dreamjob/city',
            dataType: 'json'
        }).done(function (data) {
            let cities = "";
            for (let i = 0; i < data.length; i++) {
                cities += "<option value=" + data[i]['id'] + ">" + data[i]['name'] + "</option>";
            }
            $('#select').html(cities);
        }).fail(function (err) {
            console.log(err);
        });
    });
</script>

<%--запрос сервлета candidates.do с передачей параметром из формы регистрации--%>
<%--id, name, city - кандидата--%>
<%--id: <%=candidate.getId()%> - получение id кандидата через jstl плагин--%>
<%--name: $('.form-control').val(), - получение имени из формы ввода input--%>
<%--city: document.querySelector('#select').value - получение значения из формы выбора города--%>
<script>
    function option() {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/dreamjob/candidates.do',
            data: {
                id: <%=candidate.getId()%>,
                name: $('.form-control').val(),
                city: document.querySelector('#select').value
            },
            dataType: "html"
        }).done(function (data) {
            document.location.href = 'http://localhost:8080/dreamjob/candidates.do';
        }).fail(function (err) {
            alert(err);
        });
    }
</script>
<div class="container pt-3">
    <jsp:include page="/menu.jsp"/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новый кандидат.
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label>Имя кандидата</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>">
                    </div>
                    <button type="button" class="btn btn-primary"
                            onclick="option()">Сохранить
                    </button>
                    <br><br>
                    <%--добавление городов через Ajax--%>
                    <select size="1" multiple name="cityFromEditForm" id="select">
                        <%--                        <option value="0">ноль</option>--%>
                        <%--                        <option value="1" selected>один</option>--%>
                        <%--                        <option value="2">два</option>--%>
                    </select>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>