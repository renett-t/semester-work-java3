<%@tag description="Article Editing Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="articleInstance" type="ru.kpfu.itis.renett.models.Article" %>
<%@attribute name="tagList" required="true" type="java.util.List"%>

<div class="article-edit-wrapper">
    <form method="POST" enctype="multipart/form-data">
        <label for="thumbnailImage"> Выберите изображение для вашей статьи: </label>
        <input type="file" name="thumbnailImage" id="thumbnailImage" accept=".jpg, .jpeg, .png">
        <div class="message-wrapper">
            <c:if test="${not empty message}">
                <h6 class="mx-3">${message}</h6>
            </c:if>
        </div>
        <br> <br>
        <label for="article-title"> Название статьи: </label>
        <br>
        <input class="" id="article-title" type="text" name="title" placeholder="Введите название Вашей статьи" value="<c:out default="" value="${articleInstance.title}"/>" required>
        <br> <br>
        <label for="article-body"> Основное содержимое статьи: </label>
        <textarea class="" id="article-body" name="article-body" placeholder="Основное содержимое"><c:out default="" value="${articleInstance.body}"/></textarea>
        <br> <br>
        <p> Выберите тэги: </p>
        <div class="tags-wrapper">
                <input type="checkbox" id="tag-1" name="tag" value="-1">
                <label for="tag-1">Не выбрано</label>
                <br>
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" id="tag${tag.id}" name="tag" value="${tag.id}">
                <label for="tag${tag.id}">${tag.title}</label>
                <br>
            </c:forEach>
        </div>
        <c:if test="${empty articleInstance}">
            <button class="btn btn-primary" type="submit" name="submit" value="create">Создать статью</button>
        </c:if>
        <c:if test="${not empty articleInstance}">
            <button class="btn btn-primary" type="submit" name="submit" value="edit">Отредактировать статью</button>
        </c:if>
    </form>
<%--    https://ckeditor.com/docs/ckeditor5/latest/builds/guides/predefined-builds/quick-start.html#next-steps--%>
    <script src="https://cdn.ckeditor.com/ckeditor5/31.0.0/classic/ckeditor.js"></script>
    <script>
        ClassicEditor
            .create( document.querySelector( '#article-body' ) )
            .catch( error => {
                console.error( error );
            });
    </script>
</div>
