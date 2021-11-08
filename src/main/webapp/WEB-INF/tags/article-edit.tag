<%@tag description="Article Editing Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="articleInstance" type="ru.kpfu.itis.renett.models.Article" %>
<%@attribute name="tagList" required="true" type="java.util.List"%>

<div class="article-edit-wrapper">
    <form method="POST" enctype="multipart/form-data">
        <br>
        <c:if test="${not empty articleInstance}">
            <h3>Текущее изображение статьи:</h3>
            <img class="article-thumbnail-img" src="<c:url value="/resources/articles/guitar-background.jpg"/>" alt="article thumbnail">
            <br>
        </c:if>
        <label for="thumbnailImage"> Выберите изображение для вашей статьи: </label>
        <input type="file" name="thumbnailImage" id="thumbnailImage" accept=".jpg, .jpeg, .png">
        <div class="message-wrapper">
            <c:if test="${not empty message}">
                <h6>${message}</h6>
            </c:if>
        </div>
        <br> <br>
        <label for="article-title"> Название статьи: </label><br>
        <input class="" id="article-title" type="text" name="title" placeholder="Введите название Вашей статьи" value="<c:out default="" value="${articleInstance.title}"/>" required>
        <br> <br>
        <label for="article-body"> Основное содержимое статьи: </label><br>
        <textarea id="article-body" name="articleBody" placeholder="Основное содержимое" required><c:out default="" value="${articleInstance.body}"/></textarea>
        <br> <br>
        <p> Выберите тэги: </p>
        <div class="tags-wrapper" data-taglist="<c:out default="" value="${articleInstance.tagList}"/>">
                <input type="checkbox" id="tag-1" name="tag" value="-1">
                <label for="tag-1">Не выбрано</label>
                <br>
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" id="tag${tag.id}" name="tag" value="${tag.id}">
                <label for="tag${tag.id}">${tag.title}</label>
                <br>
            </c:forEach>
        </div>
        <div class="centered-content-wrapper">
                <button id="submit" class="btn btn-success" type="submit" name="submit"> Сохранить </button>
        </div>
    </form>
    <script src="https://cdn.ckeditor.com/ckeditor5/31.0.0/classic/ckeditor.js"></script>
    <script>
        ClassicEditor
            .create( document.querySelector( '#article-body' ) )
            .then( newEditor => {
                editor = newEditor;
            })
            .catch( error => {
                console.error( error );
            });
    </script>
    <script src="<c:url value="/scripts/edit-article.js"/>" charset="UTF-8">
    </script>
</div>
