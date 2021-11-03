<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="tag" required="true" type="ru.kpfu.itis.renett.models.Tag"%>
<a class="tag-wrapper" href="<c:url value="/articles?tag=${tag.id}"/>">${tag.title}</a>
