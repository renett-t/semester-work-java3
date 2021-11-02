<%@tag description="Article Editing Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="articleInstance" type="ru.kpfu.itis.renett.models.Article" %>
<%@attribute name="tagList" required="true" type="java.util.List"%>

<div class="article-edit-wrapper">
    <form action="<c:url value="/article/new"/>" method="POST" enctype="multipart/form-data">
        <label for="fileupload"> Выберите изображение для вашей статьи: </label>
        <input type="file" name="thumbnailImage" id="fileupload" accept=".jpg, .jpeg, .png">
        <br> <br>
        <label for="article-title"> Название статьи: </label>
        <input class="" id="article-title" type="text" name="title" placeholder="Введите название Вашей статьи" value="<c:out default="" value="${articleInstance.title}"/>">
        <br> <br>
        <label for="article-body"> Основное содержимое статьи: </label>
        <input class="" id="article-body" type="text" name="body" placeholder="Основное содержимое" value="<c:out default="" value="${articleInstance.body}"/>">
        <br> <br>
        <p> Выберите тэги: </p>
        <div class="tags-wrapper">
                <input type="checkbox" id="tag-1" name="tag" value="-1">
                <label for="tag-1">Не выбрано</label>
                <br>
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" id="tag" name="tag" value="${tag.id}">
                <label for="tag">${tag.title}</label>
                <br>
            </c:forEach>
        </div>
        <button class="btn btn-primary" type="submit">Создать статью</button>
    </form>
</div>
