<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="articleInstance" required="true" type="ru.kpfu.itis.renett.models.Article" %>
            <div class="col">
                <div class="card h-100">
                    <a href="<c:url value="/article?id=${articleInstance.id}"/>">
                        <img src="<c:url value="/resources/guitar-background.jpg"/>" class="card-img-top" alt="there should be thumbnail of article... sorry">
                        <div class="card-body">
                            <h5 class="card-title">${articleInstance.title}</h5>
                        </div>
                        <div class="card-footer">
                            <div class="views-comments-wrapper text-muted">
                                <img class="icon-img views-icon" src="<c:url value="/resources/icons/eye.png"/>" alt="v">
                                <p class="article-views-count">${articleInstance.viewCount}</p>
                                <img class="icon-img comment-icon" src="<c:url value="/resources/icons/comment.png"/>" alt="comments count icon">
                                <p class="article-comments-count">${articleInstance.commentAmount}</p>
                                <img class="icon-img like-icon" src="<c:url value="/resources/icons/like.png"/>" alt="likes count icon">
                                <p class="article-comments-count">${articleInstance.likeAmount}</p>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
