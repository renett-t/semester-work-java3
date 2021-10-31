<%@tag description="Comment Edit Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="commentInstance" type="ru.kpfu.itis.renett.models.Comment" %>
<%@attribute name="id" required="true" type="java.lang.Integer" %>

<div class="comment-edit-wrapper">
    <form action="<c:url value="/article?id=*/newcomment"/>" method="POST">
        <div class="col-3 input-effect">
            <input class="" id="article-body" type="text" name="body" placeholder="Введите текст комментария">
        </div>
        <button class="btn" type="submit"> Отправить </button>
    </form>
</div>
