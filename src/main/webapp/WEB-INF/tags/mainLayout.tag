<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="title" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width" />
        <title>
            MusicSite <c:if test="${not empty title}" > - ${title}</c:if>
        </title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">

        <style>
            <%@include file = "/WEB-INF/css/main.css"%>
        </style>
    </head>

    <body>
        <jsp:include page="/WEB-INF/jsp/_header.jsp"/>

        <div class="content-container">
            <jsp:doBody/>
        </div>

        <jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
    </body>
</html>

<%-- READ THIS TO UNDERSTAND TAGS CREATING TEQ--%>
<%--https://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier--%>
