<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ru.kpfu.itis.renett.models.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Регистрация">
    <div class="registration-form-wrapper">
        <form class="registration-form" action="<c:url value="/signup"/>" method="POST">
            <div>
                <label for="nameInput" class="form-label">Ваше имя</label>
                <input name="firstName" class="input-field" id="nameInput" type="text" value="<c:out default="" value="${session.getAttribute(\"firstName\")}"/>" required>
                <div class="error-field" id="error-nameInput"></div><br>
            </div>
            <div>
                <label for="name2Input" class="form-label">Ваша фамилия</label>
                <input name="secondName" id="name2Input" type="text" value="<c:out default="" value="${session.getAttribute(\"secondName\")}"/>" required>
            </div>
            <div>
                <label for="emailInput" class="form-label">E-mail</label>
                <input name="email" class="input-field" id="emailInput" type="text" value="<c:out default="" value="${session.getAttribute(\"email\")}"/>" required>
                <div class="error-field" id="error-emailInput"></div><br>
            </div>
            <div>
                <label for="loginInput" class="form-label">Логин (никнэйм)</label>
                <div class="input-group">
                    <span class="input-group-text" id="inputGroupPrepend2">@</span>
                    <input name="login" class="input-field" id="loginInput" type="text" value="<c:out default="" value="${session.getAttribute(\"login\")}"/>" required>
                    <div class="error-field" id="error-loginInput"></div><br>
                </div>
            </div>
            <div>
                <label for="passwordInput" class="form-label">Пароль</label>
                <input name="password" class="input-field" id="passwordInput" type="password" required>
                <div class="error-field" id="error-passwordInput"></div><br>
            </div>
            <div>
                <label for="repeatedPasswordInput" class="form-label">Повторите пароль</label>
                <input name="repeatedPassword" class="input-field" id="repeatedPasswordInput" type="password" required>
                <div class="error-field" id="error-repeatedPasswordInput"></div><br>
            </div>
            <div class="col-12">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="agreement" required>
                    <label class="form-check-label" for="agreement">
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
</t:mainLayout>

<script>
    <%@include file = "/WEB-INF/scripts/form-control.js"%>
</script>
