<%@ page contentType="text/html; charset=UTF-8" %>
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


    <link rel="stylesheet" type="text/css"
          href="yourPath/silviomoreto-bootstrap-select-83d5a1b/dist/css/bootstrap-select.css">
    <link href="yourPath/bootstrap.min.css" rel="stylesheet">

    <title>Регистрация</title>
    <style>
        body {
            padding: 30px;
            background-image: url('https://catherineasquithgallery.com/uploads/posts/2021-02/1613405883_169-p-fon-dlya-storis-bezhevii-gradient-188.jpg');
            background-repeat: no-repeat;
            background-position: center;
        }
    </style>


</head>
<script>
    function validate() {
        let name = $('#name');
        let email = $('#email');
        let pass = $('#password');
        if (email.val() == '' || pass.val() == '' || name.val() == '') {
            alert("Не все поля заполнены");
        }
        return false;
    }
</script>

<%--запросы в CityServlet для получения списка городов --%>
<script>
    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/dreamjob/city',
            dataType: 'text'
        }).done(function (data) {
            for (var cities of data) {
                $('#selectpicker option:last').append(`<option>${cities.name}</option>`)
            }
        }).fail(function (err) {
            console.log(err);
        });
    });
</script>

<%--работа с options формой --%>
<script>
    const element = document.querySelector('#select');

    console.log(element.value)

    element.addEventListener("change", function() {
        const element = document.querySelector('#select');
        alert(element.value)
    });
</script>

<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Регистрация
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post">
                    <div class="form-group">
                        <label>Имя пользователя</label>
                        <input type="text" class="form-control" name="name">
                    </div>
                    <div class="form-group">
                        <label>Почта</label>
                        <input type="text" class="form-control" name="email">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="text" class="form-control" name="password">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="validate()">Зарегистрироваться</button>
                    <c:if test="${not empty error}">
                        <div style="color:#ff0000; font-weight: bold; margin: 30px 0;">
                                ${error}
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>
</body>


</html>