<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Профиль - Редактирование">
    <form method="POST" class="account-form">
        <div class="row">
            <div class="col">
                <label for="nameInput" class="form-label">Ваше имя</label><br>
                <input name="firstName" class="input-field form-control" id="nameInput" type="text" value="" required>
                <div class="error-field" id="error-nameInput"></div><br>
            </div>
            <div class="col">
                <label for="name2Input" class="form-label">Ваша фамилия</label><br>
                <input name="secondName" class="form-control" id="name2Input" type="text" value="" required>
            </div>
            <div class="col">
                <label for="emailInput" class="form-label">E-mail</label><br>
                <input name="email" class="input-field form-control" id="emailInput" type="text" value="" required>
                <div class="error-field" id="error-emailInput"></div><br>
            </div>
            <div class="col">
                <label for="loginInput" class="form-label">Логин</label><br>
                <input name="login" class="input-field form-control" id="loginInput" type="text" value="" required>
                <div class="error-field" id="error-loginInput"></div><br>
            </div>
            <div class="col">
                <label for="oldPasswordInput" class="form-label">Старый пароль</label><br>
                <input name="oldPassword" class="form-control" id="oldPasswordInput" type="password" required>
            </div>
            <div class="col">
                <label for="passwordInput" class="form-label">Новый пароль</label><br>
                <input name="password" class="input-field form-control" id="passwordInput" type="password" required>
                <div class="error-field" id="error-passwordInput"></div><br>
            </div>
            <div class="message-wrapper">
                <c:if test="${not empty message}">
                    <h6 class="mx-3">${message}</h6>
                </c:if>
            </div>

            <div class="col">
                <button type="submit" class="btn btn-primary" name="submit" value="edit">Сохранить</button>
                <button type="submit" class="btn btn-primary btn-dark" name="submit" value="cancel">Отмена</button>
            </div>
    </form>
    <script src="<c:url value="/scripts/form-control.js"/>" charset="UTF-8">
    </script>
</t:mainLayout>
