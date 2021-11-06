<%@tag description="Comment Edit Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="commentInstance" type="ru.kpfu.itis.renett.models.Comment"%>
<%@attribute name="id" required="true" type="java.lang.Integer" %>

<div class="comment-edit-wrapper">
    <form action="<c:url value="/newComment?id=${id}"/>" method="POST" id="comment-form">
        <textarea class="" id="comment-body" form="comment-form" name="commentBody" placeholder="Введите текст комментария"></textarea>
        <input type="hidden" name="articleId" value="${id}">
        <br>
        <button class="btn" type="submit" name="submit" value="create">Отправить комментарий</button>
    </form>
</div>
