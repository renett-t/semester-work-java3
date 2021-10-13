<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="page_title" value="Sign up page"/>
<%@include file="_header.jsp"%>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<%--    <link href="<c:url value="/css/table_styles.css" />" rel="stylesheet">--%>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Kaisei+HarunoUmi&family=Source+Code+Pro:wght@300&display=swap');

        body {
            margin: 0 auto;
            font-family: 'Source Code Pro', monospace;
            font-size: 22px;
        }

        .registered-table-block-wrapper {
            margin: 20px;
        }

        .registered-heading-wrapper {
            font-family: 'Kaisei HarunoUmi', serif;
            font-size: 26px;
            margin: 20px;
        }

    </style>
</head>
<body>
        <div class="registered-table-block-wrapper">
            <div class="registered-heading-wrapper">
                Already registered
            </div>
            <div class="registered-table-wrapper">
                <table class="table table-striped table-hover">
                    <tr>
                        <th>Name</th>
                        <th>E-mail</th>
                        <th>Login</th>
                    </tr>
                    <c:forEach items="${usersFromDB}" var="user">
                        <tr>
                            <td>${user.firstName} ${user.secondName}</td>
                            <td>${user.email}</td>
                            <td>${user.login}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

<%@include file="_footer.jsp"%>
