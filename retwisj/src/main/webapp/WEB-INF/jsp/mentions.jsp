<div class="span-24 prepend-1 last">
  <h2 class="alt"><fmt:message key="Mentions"/> ${name}</h2>
</div>

<div class="span-15 prepend-1">
	<div class="box">
	<c:choose>
	  <c:when test="${loggedUser eq name}">
		<fmt:message key="itsyou"/>
	 </c:when>
	 <c:otherwise>
	    <c:set var="no_mentions" scope="page" value="true"/>
	    <%@ include file="/WEB-INF/templates/userFollow.jspf" %>
	 </c:otherwise>
	</c:choose>
	</div>
</div>
<%@ include file="/WEB-INF/templates/network.jspf" %>
<%@ include file="/WEB-INF/templates/posts.jspf" %>
