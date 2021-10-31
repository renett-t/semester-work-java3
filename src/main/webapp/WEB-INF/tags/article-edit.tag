<%@tag description="Article Editing Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="articleInstance" type="ru.kpfu.itis.renett.models.Article" %>
<%@attribute name="tagList" required="true" type="java.util.List"%>

<div class="article-edit-wrapper">
    <form action="<c:url value="/article/new"/>" method="POST">
        <input type="file" name="thumbnailImage" id="fileupload">
        <label for="fileupload">Выберите изображение для вашей статьи: </label>
        <br>
        <input type="image" src="<>" alt="put thumbnail" width="100">

        <input class="effect-15" type="text" name="title" placeholder="Введите название Вашей статьи" value="<c:out default="" value="${articleInstance.title}"/>">

        <input class="effect-16" type="text" name="body" placeholder="Контентище" value="<c:out default="" value="${articleInstance.body}"/>">

        <div class="tags-wrapper">
                <input type="checkbox" id="tag" name="tag" value="-1">
                <label for="tag">Не выбрано</label>
                <br>
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" id="tag" name="tag" value="${tag.id}">
                <label for="tag">${tag.title}</label>
                <br>
            </c:forEach>
        </div>
        <button class="btn" type="submit">Создать статью</button>
    </form>
</div>
