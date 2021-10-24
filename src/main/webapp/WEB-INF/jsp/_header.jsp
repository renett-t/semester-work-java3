<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <nav class="navbar navbar-light" style="background-color: #e3f2fd;">
        <a class="navbar-brand" href="#">
            <img src="/assets/brand/bootstrap-solid.svg" width="30" height="30" class="d-inline-block align-top" alt="">
            Bootstrap
        </a>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Features</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Pricing</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Dropdown link
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                </li>
            </ul>
        </div>


    </nav>

<%--    <div class="header-left">--%>
<%--        <h1 class="logo">Waterfall</h1>--%>
<%--    </div>--%>
<%--    <div class="header-right">--%>
<%--        <div class="header-menu">--%>
<%--            <a href="<c:url value="/feed"/>" class="menu-btn underline-on-hover">Статьи</a>--%>
<%--            &lt;%&ndash;@elvariable id="auth" type="java.lang.Boolean"&ndash;%&gt;--%>
<%--            <c:if test="${auth eq false or auth eq null}">--%>
<%--                <a href="<c:url value="/login"/>" class="menu-btn underline-on-hover">Войти</a>--%>
<%--            </c:if>--%>
<%--            <c:if test="${auth eq true}">--%>
<%--                &lt;%&ndash;<a href="<c:url value="/userFeed"/>" class="menu-btn underline-on-hover">Ваша лента</a>&ndash;%&gt;--%>
<%--                <a href="<c:url value="/profile"/>" class="menu-btn underline-on-hover">Профиль</a>--%>
<%--                <a href="<c:url value="/edit"/>" class="menu-btn underline-on-hover">Создать статью</a>--%>
<%--                <a href="<c:url value="/saved"/>" class="menu-btn underline-on-hover">Сохранённое</a>--%>
<%--                <a href="<c:url value="/userArticles"/>" class="menu-btn underline-on-hover">Ваши статьи</a>--%>
<%--            </c:if>--%>
<%--        </div>--%>
<%--        <div class="header-dropdown">--%>
<%--            <div class="dropdown">--%>
<%--                <a data-toggle="dropdown">--%>
<%--                    <img class="menu-img" src="<c:url value="/res/menu.png"/>" alt="menu">--%>
<%--                </a>--%>
<%--                <div class="dropdown-menu">--%>
<%--                    <a href="<c:url value="/feed"/>" class="dropdown-item">Статьи</a>--%>
<%--                    &lt;%&ndash;@elvariable id="auth" type="java.lang.Boolean"&ndash;%&gt;--%>
<%--                    <c:if test="${auth eq false or auth eq null}">--%>
<%--                        <a href="<c:url value="/login"/>" class="dropdown-item">Войти</a>--%>
<%--                    </c:if>--%>
<%--                    <c:if test="${auth eq true}">--%>
<%--                        &lt;%&ndash;<a href="<c:url value="/userFeed"/>" class="dropdown-item">Ваша лента</a>&ndash;%&gt;--%>
<%--                        <a href="<c:url value="/profile"/>" class="dropdown-item">Профиль</a>--%>
<%--                        <a href="<c:url value="/edit"/>" class="dropdown-item">Создать статью</a>--%>
<%--                        <a href="<c:url value="/saved"/>" class="dropdown-item">Сохранённое</a>--%>
<%--                        <a href="<c:url value="/userArticles"/>" class="dropdown-item">Ваши статьи</a>--%>
<%--                    </c:if>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>

</header>

