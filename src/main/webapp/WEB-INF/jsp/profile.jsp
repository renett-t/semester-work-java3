<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:mainLayout title="Профиль"><%--@elvariable id="user" type="ru.kpfu.itis.renett.models.User"--%>
    <div class="centered-content-wrapper">
        <div class="profile">
            <h1>Ваш аккаунт @${user.login}</h1>
            <h3>${user.firstName} ${user.secondName}</h3>
            <h3>E-mail: </h3>
                <p>${user.email}</p>
            <a href="<c:url value="/logout"/>" class="logout-button" id="logout">Выйти</a>
        </div>
        <br>
        <hr>
        <c:if test="${not empty likedArticlesList}">
            <div class="heading-second">Понравившиеся статьи: </div>
            <div class="card-group row row-cols-3 g-4">
            <c:forEach items="${likedArticlesList}" var="likedArticle">
                    <t:article-card articleInstance="${likedArticle}" tagList="${likedArticle.tagList}"></t:article-card>
            </c:forEach>
            </div>
        </c:if>
    </div>
</t:mainLayout>
