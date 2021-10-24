<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Профиль">
    <div class="centered-content-wrapper">
        <div class="profile">
            <h1>Ваш аккаунт ${user.getLogin()}</h1>
            <h3>${user.getFirstName()} ${user.getSecondName()}</h3>
            <h3>E-mail: </h3>
                <p>${user.getEmail()}</p>
        </div>
        <a href="<c:url value="/logout"/>" class="logout-button" id="logout">Выйти</a>
    </div>
</t:mainLayout>
