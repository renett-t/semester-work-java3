<%@page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="ru.kpfu.itis.renett.models.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Регистрация">
    <div class="registration-form-wrapper">
        <form class="row g-3 registration-form" action="<c:url value="/signup"/>" method="POST">
            <div class="col-md-4">
                <label for="validationDefault01" class="form-label">Ваше имя</label>
                <input name="firstName" type="text" value="<c:out default="" value="${session.getAttribute(\"firstName\")}"/>" class="form-control" id="validationDefault01" required>
            </div>
            <div class="col-md-4">
                <label for="validationDefault02" class="form-label">Ваша фамилия</label>
                <input name="secondName" type="text" value="<c:out default="" value="${session.getAttribute(\"secondName\")}"/>" class="form-control" id="validationDefault02" required>
            </div>
            <div class="col-md-4">
                <label for="validationDefault02" class="form-label">E-mail</label>
                <input name="email" type="text" value="<c:out default="" value="${session.getAttribute(\"email\")}"/>" class="form-control" id="validationDefault03" required>
            </div>
            <div class="col-md-4">
                <label for="validationDefaultUsername" class="form-label">Логин (никнэйм)</label>
                <div class="input-group">
                    <span class="input-group-text" id="inputGroupPrepend2">@</span>
                    <input name="login" type="text" value="<c:out default="" value="${session.getAttribute(\"login\")}"/>" class="form-control" id="validationDefaultUsername"  aria-describedby="inputGroupPrepend2" required>
                </div>
            </div>
            <div class="col-md-4">
                <label for="validationDefault02" class="form-label">Пароль</label>
                <input name="password" type="password" class="form-control" id="validationDefault05" required>
            </div>
            <div class="col-md-4">
                <label for="validationDefault02" class="form-label">Повторите пароль</label>
                <input name="repeatedPassword" type="password" class="form-control" id="validationDefault06" required>
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
                <button class="btn btn-primary" type="submit">Sign up!</button>
            </div>
        </form>
    </div>
</t:mainLayout>
