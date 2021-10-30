<%@tag description="Article Displaying Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="articleInstance" required="true" type="ru.kpfu.itis.renett.models.Article" %>
    <div class="article-wrapper">
        <div class="article-heading-elements">
            <div class="heading-second">${articleInstance.title}</div>
            Опубликовано пользователем ${articleInstance.author.login}, ${articleInstance.publishedAt.toLocaleString()}
            <img class="icon-img views-icon" src="<c:url value="/resources/icons/eye.png"/>" alt="v">
            <p class="article-views-count">${articleInstance.viewCount}</p>
            <img class="icon-img comment-icon" src="<c:url value="/resources/icons/comment.png"/>" alt="comments count icon">
            <p class="article-comments-count">${articleInstance.commentAmount}</p>
            <img class="icon-img like-icon" src="<c:url value="/resources/icons/like.png"/>" alt="likes count icon">
            <p class="article-comments-count">${articleInstance.likeAmount}</p>
        </div>
        <hr>
        <div class="article-body">${articleInstance.body}</div>
        <div class="article-comments-wrapper">
            Здесь будут комментарии
        </div>
    </div>
