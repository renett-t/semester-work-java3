<%@tag description="Article Displaying Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="articleInstance" required="true" type="ru.kpfu.itis.renett.models.Article" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="article-wrapper">
    <div class="article-thumbnail">
        <img class="article-thumbnail-img" src="<c:url value="/resources/articles/${articleInstance.thumbnailPath}"/>" alt="article thumbnail">
    </div>
    <div class="article-heading-elements">
        <h3>${articleInstance.title}</h3>
        Опубликовано пользователем ${articleInstance.author.login}, ${articleInstance.publishedAt.toLocaleString()}
    </div>
    <c:if test="${author}">
        <div class="edit-delete-wrapper">
            <img class="icon-img edit-icon" src="<c:url value="/resources/icons/edit.png"/>" alt="edit">
            <img class="icon-img delete-icon" src="<c:url value="/resources/icons/cancel.png"/>" alt="delete">
        </div>
    </c:if>
    <hr>
    <div class="article-body">${articleInstance.body}</div>
    <hr>
    <div class="article-footer-wrapper">
        <img class="icon-img views-icon" src="<c:url value="/resources/icons/eye.png"/>" alt="v">
        <p class="article-views-count">${articleInstance.viewCount}</p>
        <img class="icon-img comment-icon" src="<c:url value="/resources/icons/comment.png"/>" alt="comments count icon">
        <p class="article-comments-count">${articleInstance.commentAmount}</p>
        <img class="icon-img like-icon" src="<c:url value="/resources/icons/like.png"/>" alt="likes count icon">
        <p class="article-comments-count">${articleInstance.likeAmount}</p>
    </div>
    <hr>
    <div class="article-comments-wrapper">
        <c:forEach var="commentInstance" items="${articleInstance.commentList}">
            <t:comment commentInstance="${commentInstance}"></t:comment>
        </c:forEach>
        <c:if test="${authorized}">
            <t:comment-edit id="${articleInstance.id}"></t:comment-edit>
        </c:if>
        <c:if test="${not authorized}">
            <div>
                Войдите, чтобы оставить комментарий: <a class="" href="<c:url value="/signin"/>">Вход</a>
            </div>
        </c:if>
    </div>
</div>
