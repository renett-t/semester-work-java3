<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="commentInstance" required="true" type="ru.kpfu.itis.renett.models.Comment" %>

<div class="comment-wrapper">
    <div class="comment-heading-wrapper">
        <img class="profile-icon" src="<c:url value="/resources/icons/profile.png"/>" alt="profile pic">
        <p>${commentInstance.author.login}</p>
        <p>${commentInstance.publishedAt.toLocaleString()}</p>
    </div>
    <div class="comment-body-wrapper">
        ${commentInstance.body}
    </div>
    <div class="comment-footer-wrapper">
        <form class="row action="<c:url value="/article?id=*/newcomment"/>" method="POST">
        <button class="btn" type="submit"> Ответить </button>
        </form>
    </div>
    <div class="child-comments-wrapper">
        <c:forEach var="child" items="${commentInstance.childComments}">
            <t:comment commentInstance="${child}"/>
        </c:forEach>
    </div>
</div>
