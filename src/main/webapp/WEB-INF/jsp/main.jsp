<%@page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Welcome!">
    <div class="message-wrapper">
        <c:if test="${not empty message}">
            <h6 class="mx-3">${message}</h6>
        </c:if>
    </div>
</t:mainLayout>
