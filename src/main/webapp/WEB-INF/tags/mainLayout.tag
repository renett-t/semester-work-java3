<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="title" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes, height=device-height">
        <title>
            MusicSite <c:if test="${not empty title}"> - ${title}</c:if>
        </title>
        <link rel="stylesheet" href="<c:url value = "/css/main.css"/>">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/_header.jsp"/>

        <div class="content-container">
            <jsp:doBody/>
        </div>

        <jsp:include page="/WEB-INF/jsp/_footer.jsp"/>
      </body>
</html>
