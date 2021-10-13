<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="ru.kpfu.itis.renett.models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="page_title" value="Sign up page"/>
<%@include file="_header.jsp"%>


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="<c:url value="/css/signup_styles.css"/>">--%>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Source+Code+Pro:wght@300&display=swap');

    body {
        margin: 0 auto;
        font-family: 'Source Code Pro', monospace;
        font-size: 22px;
    }

    .authorization-form {
        display: flex;
        flex-direction: column;
        margin: 20px;
    }

    header {
        background-color: #F2EDEB;
        position: sticky;
        top: 0px;
        left: 0px;
        height: 10%;
        width: 100%;
        z-index: 1;
    }
    footer {
        background-color: #F2EDEB;
        width: 100%;
        height: 10%;
        font-size: 18px;
        text-align: center;
    }

    .message_div h6 {
        color: red;
    }
</style>
</head>
<body>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>

<header>
    <div class="header">
        some header
    </div>
</header>

<main>
    <div class="registration-form">
        <form class="row g-3 authorization-form" action="http://localhost:8080/another/signup" method="POST">
            <div class="col-md-4">
                <label for="validationDefaultUsername" class="form-label">Login</label>
                <div class="input-group">
                    <span class="input-group-text" id="inputGroupPrepend2">@</span>
                    <input name="login" type="text" value="<c:out default="" value="${session.getAttribute(\"login\")}"/>" class="form-control" id="validationDefaultUsername"  aria-describedby="inputGroupPrepend2" required>
                </div>
            </div>
            <div class="col-md-4">
                <label for="validationDefault01" class="form-label">Password</label>
                <input name="password" type="password" class="form-control" id="validationDefault01" required>
            </div>

            <div class="col-12">
                <button class="btn btn-primary" type="submit">Sign in!</button>
            </div>
        </form>
    </div>

    <div class="message_div">
        <c:if test="${not empty message}">
            <h6 class="mx-3">${message}</h6>
        </c:if>
    </div>

</main>

<footer>
    <div class="footer">
        some footer
    </div>
</footer>

<%@include file="_footer.jsp"%>
