<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="articleInstance" required="true" type="ru.kpfu.itis.renett.models.Article" %>

<div class="card-group">
    <div class="card">
        <a href="<c:url value="/article/${articleInstance.id}"/>">
            <img src="" class="card-img-top" alt="there should be thumbnail of article... sorry">
            <div class="card-body">
                <h5 class="card-title">${articleInstance.title}</h5>
            </div>
            <div class="card-footer views-comments-wrapper">
                <img src="<c:url value="/resources/icons/views-count.png"/>">
                <p class="article-views-count">${articleInstance.viewCount}</p>
                <img src="<c:url value="/resources/icons/comment.png"/>">
                <p class="article-comments-count">${articleInstance.commentAmount}</p>
            </div>
        </a>
    </div>
</div>
