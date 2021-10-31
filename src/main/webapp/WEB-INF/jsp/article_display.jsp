<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="articleInstance" type="ru.kpfu.itis.renett.models.Article"--%>
<t:mainLayout title="${articleInstance}">
    <c:if test="${not empty message}">
        <h6>${message}</h6>
    </c:if>
    <c:if test="${empty message}">
        <t:article articleInstance="${articleInstance}"></t:article>
        <c:if test="${authorized}">
            <div class="comment-add-wrapper row">
                <label class="col-4">
                    <input type="text" name="comment-body" class="comment-edit-area">
                </label>
                <label class="col-4">
                    <input type="submit" class="btn">
                </label>
            </div>
        </c:if>
    </c:if>
</t:mainLayout>
