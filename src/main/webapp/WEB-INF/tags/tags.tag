<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="tag" required="true" type="ru.kpfu.itis.renett.models.Tag"%>
<form action="<c:url value="/articles?tag=${tag.id}"/>" method="get">
    <button class="btn tag-wrapper btn-light" type="submit">${tag.title}</button>
</form>
