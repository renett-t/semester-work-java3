<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="articleInstance" required="true" type="ru.kpfu.itis.renett.models.Article"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
            <div class="col">
                <div class="card h-100">
                    <a href="<c:url value="/article?id=${articleInstance.id}"/>">
                        <img src="<c:url value="/resources/articles/${articleInstance.thumbnailPath}"/>" class="card-img-top" alt="there should be thumbnail of article... sorry">
                        <div class="card-body">
                            <h5 class="card-title">${articleInstance.title}</h5>
                            <c:forEach var="tagInstance" items="${articleInstance.tagList}">
                                <t:tags tag="${tagInstance}"></t:tags>
                            </c:forEach>
                            <c:if test="${articleInstance.tagList.size() == 0}">
                                <br>
                            </c:if>
                            <br>
                            <div class="card-footer">
                                <div class="views-comments-wrapper text-muted">
                                    <img class="icon-img views-icon" src="<c:url value="/resources/icons/eye.png"/>" alt="v">
                                    <p class="article-views-count">${articleInstance.viewAmount}</p>
                                    <img class="icon-img comment-icon" src="<c:url value="/resources/icons/comment.png"/>" alt="comments count icon">
                                    <p class="article-comments-count">${articleInstance.commentAmount}</p>
                                    <img class="icon-img like-icon" src="<c:url value="/resources/icons/like.png"/>" alt="likes count icon">
                                    <p class="article-comments-count">${articleInstance.likeAmount}</p>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
