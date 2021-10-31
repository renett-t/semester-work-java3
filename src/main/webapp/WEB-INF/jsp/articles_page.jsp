<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Все статьи">
    <div class="articles-wrapper ">
        <c:if test="${not empty userArticlesList}">
            <div class="heading-second">Ваши статьи: </div>
            <div class="user-articles-wrapper card-group row row-cols-3 g-4">
                <%--@elvariable id="articleList" type="java.util.List"--%>
                <c:forEach var="article" items="${userArticlesList}">
                    <t:article-card articleInstance="${article}">
                    </t:article-card>
                </c:forEach>
                <div class="col">
                    <div class="card h-100">
                        <a href="<c:url value="/article/new"/>">
                            <img src="<c:url value="/resources/icons/new.png"/>" class="card-img-top" alt="create new article image">
                            <div class="card-body">
                                <h5 class="card-title">Создай свою статью</h5>
                            </div>
                            <div class="card-footer">
                                <div class="text-muted">Поделись опытом, разбором песни, чем-угодно</div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <br> <hr> <br>
        </c:if>
        <div class="heading-second">Все статьи: </div>
        <div class="all-articles-wrapper card-group row row-cols-3 g-4">
            <c:forEach var="article" items="${articlesList}">
                <t:article-card articleInstance="${article}">
                </t:article-card>
            </c:forEach>
        </div>
    </div>
</t:mainLayout>
