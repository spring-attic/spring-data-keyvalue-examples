<div class="span-24 prepend-1 last">
 <h3 class="alt"><c:if test="${loggedUser eq name}"><fmt:message key="welcome"/> </c:if>${name}</h3>
</div>
<div class="span-15 prepend-1">
 <c:choose>
  <c:when test="${loggedUser eq name}">
  <div id="updateform" class="box">
	<form method="post" action="!${name}">
	  <c:choose>
	  	<c:when test="${!empty replyTo}">
	  	<fmt:message key="replyto"/> <i>${replyTo}</i>:
	  	</c:when>
	  	<c:otherwise>
	  	<b><i>${name}</i></b>, <fmt:message key="wazza"/>
	  	</c:otherwise>
	  </c:choose>
      <textarea name="content" rows="3" cols="60"><c:if test="${!empty replyTo}">@${replyTo} </c:if></textarea><br />
      <input type="hidden" name="replyTo" value="${replyTo}"/>
      <input type="hidden" name="replyPid" value="${replyPid}"/>
      <input type="submit" value="<fmt:message key="update"/>"/>
	</form>
  </div>
  </c:when>
  <c:otherwise>
  	<c:if test="${loggedIn}">
  	  <%@ include file="/WEB-INF/templates/userFollow.jspf" %>
  	</c:if>
  </c:otherwise>
</c:choose>
</div>
<c:if test="${!loggedIn}">
  <a href="timeline"><fmt:message key="timeline"/></a>
</c:if>


<%@ include file="/WEB-INF/templates/network.jspf" %>
<%@ include file="/WEB-INF/templates/posts.jspf" %>

