<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><fmt:message key="Mentions"/> ${name}</h2>

<c:choose>
  <c:when test="${loggedUser eq name}">
	<fmt:message key="itsyou"/>
 </c:when>
<c:otherwise>
    <c:set var="no_mentions" scope="page" value="true"/>
    <%@ include file="/WEB-INF/jsp/userFollow.jsp" %>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jsp/posts.jsp" %>

<%@ include file="/WEB-INF/jsp/network.jsp" %>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>