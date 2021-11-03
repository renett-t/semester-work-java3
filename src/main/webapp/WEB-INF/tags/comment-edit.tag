<%@tag description="Comment Edit Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="commentInstance" type="ru.kpfu.itis.renett.models.Comment" %>
<%@attribute name="id" required="true" type="java.lang.Integer" %>

<div class="comment-edit-wrapper">
    <form action="<c:url value="/newcomment?id=${id}"/>" method="POST">
        <input class="" id="comment-body" type="text" name="commentBody" placeholder="Введите текст комментария">
        <br>
        <button class="btn" type="submit"> Отправить комментарий </button>
    </form>
</div>
