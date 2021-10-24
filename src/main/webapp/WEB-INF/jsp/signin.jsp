<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="ru.kpfu.itis.renett.models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Вход">
    <div class="registration-form-wrapper">
        <form class="row g-3 authorization-form" method="POST">
            <div class="col-md-4">
                <label for="validationDefaultUsername" class="form-label">Логин</label>
                <div class="input-group">
                    <span class="input-group-text" id="inputGroupPrepend2">@</span>
                    <input name="login" type="text" value="<c:out default="" value="${session.getAttribute(\"login\")}"/>" class="form-control" id="validationDefaultUsername"  aria-describedby="inputGroupPrepend2" required>
                </div>
            </div>
            <div class="col-md-4">
                <label for="validationDefault01" class="form-label">Пароль</label>
                <input name="password" type="password" class="form-control" id="validationDefault01" required>
            </div>

            <div class="message-wrapper">
                <c:if test="${not empty message}">
                    <h6 class="mx-3">${message}</h6>
                </c:if>
            </div>

            <div class="col-12">
                <button class="btn btn-primary" type="submit">Sign in!</button>
            </div>

            <div>
                Нет аккаунта?
                <a class="to-registration underline-on-hover" href="<c:url value="/signup"/>">
                    Регистрация
                </a>
            </div>
        </form>
    </div>
</t:mainLayout>

