<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ru.kpfu.itis.renett.models.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Регистрация">
    <div class="registration-form-wrapper">
        <form class="registration-form" action="<c:url value="/signup"/>" method="POST">
            <div>
                <label for="nameInput" class="form-label">Ваше имя</label>
                <input name="firstName input-field" id="nameInput" type="text" value="<c:out default="" value="${session.getAttribute(\"firstName\")}"/>" class="form-control" required>
            </div>
            <div>
                <label for="name2Input" class="form-label">Ваша фамилия</label>
                <input name="secondName" id="name2Input" type="text" value="<c:out default="" value="${session.getAttribute(\"secondName\")}"/>" class="form-control" required>
            </div>
            <div>
                <label for="emailInput" class="form-label">E-mail</label>
                <input name="email input-field" id="emailInput" type="text" value="<c:out default="" value="${session.getAttribute(\"email\")}"/>" class="form-control" required>
            </div>
            <div>
                <label for="loginInput" class="form-label">Логин (никнэйм)</label>
                <div class="input-group">
                    <span class="input-group-text" id="inputGroupPrepend2">@</span>
                    <input name="login input-field" id="loginInput" type="text" value="<c:out default="" value="${session.getAttribute(\"login\")}"/>" class="form-control" aria-describedby="inputGroupPrepend2" required>
                </div>
            </div>
            <div>
                <label for="passwordInput" class="form-label">Пароль</label>
                <input name="password input-field" id="passwordInput" type="password" class="form-control" required>
            </div>
            <div>
                <label for="repeatedPasswordInput" class="form-label">Повторите пароль</label>
                <input name="repeatedPassword input-field" id="repeatedPasswordInput" type="password" class="form-control" required>
            </div>
            <div class="col-12">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="invalidCheck2" required>
                    <label class="form-check-label" for="invalidCheck2">
                        Согласен с политикой конфиденциальности и условиями обработки персональных данных
                    </label>
                </div>
            </div>

            <div class="message-wrapper">
                <c:if test="${not empty message}">
                    <h6 class="mx-3">${message}</h6>
                </c:if>
            </div>

            <div class="col-12">
                <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
            </div>
        </form>
        <div>
            Уже зарегистрированы? <a class="" href="<c:url value="/signin"/>">Вход</a>
        </div>
    </div>

    <script>
        <%@include file = "/WEB-INF/scripts/form-control.js"%>
    </script>
</t:mainLayout>
