<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="ru.kpfu.itis.renett.models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Вход">
        <div class="registration-form-wrapper">
            <form class="authorization-form" method="POST">
                <h1 class="h3 mb-3 fw-normal">Войти</h1>

                <div class="form-floating">
                    <input type="text" class="form-control" name="login" value="<c:out default="" value="${session.getAttribute(\"login\")}"/>" id="floatingInput" placeholder="name@example.com" required>
                    <label for="floatingInput">Ваш логин</label>
                </div>
                <div class="form-floating">
                    <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password" required>
                    <label for="floatingPassword">Пароль</label>
                </div>
                <br>
                <div class="message-wrapper">
                    <c:if test="${not empty message}">
                        <h6>${message}</h6>
                    </c:if>
                </div>

                <button class="btn btn-lg btn-primary" type="submit">Войти</button>
            </form>
            <br>
            <div>
                Ещё нет аккаунта? <a class="" href="<c:url value="/signup"/>">Регистрация</a>
            </div>
        </div>
</t:mainLayout>

