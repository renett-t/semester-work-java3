<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="commentInstance" required="true" type="ru.kpfu.itis.renett.models.Comment" %>

<div class="comment-wrapper">
    <div class="comment-heading-wrapper">
        <img class="comment-profile-icon" src="<c:url value="/resources/icons/profile.png"/>" alt="profile pic">
        <div>${commentInstance.author.login}</div>
        <div>${commentInstance.publishedAt.toLocaleString()}</div>
    </div>
    <div class="comment-body-wrapper">
        ${commentInstance.body}
    </div>
    <div class="comment-footer-wrapper">
        <button class="reply-button" id="reply-button">Ответить</button>
    </div>
    <div class="child-comments-wrapper">
        <c:forEach var="child" items="${commentInstance.childComments}">
            <t:comment commentInstance="${child}"/>
        </c:forEach>
    </div>
</div>
