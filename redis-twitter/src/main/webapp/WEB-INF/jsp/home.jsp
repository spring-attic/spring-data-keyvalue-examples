<h2><c:if test="${loggedUser eq name}"><fmt:message key="welcome"/> </c:if>${name}</h2>

<div class="span-16" id="maincol">
 <c:choose>
  <c:when test="${loggedUser eq name}">
  <div id="updateform" class="box">
	<form method="post" action="!${name}">
	  <c:choose>
	  	<c:when test="${!empty replyTo}">
	  	Reply to <i>${replyTo}</i>:
	  	</c:when>
	  	<c:otherwise>
	  	<i>${name}</i>, what's happening?
	  	</c:otherwise>
	  </c:choose>
      <textarea name="content" rows="3" columns="70"><c:if test="${!empty replyTo}">@${replyTo} </c:if></textarea><br />
      <input type="hidden" name="replyTo" value="${replyTo}"/>
      <input type="hidden" name="replyPid" value="${replyPid}"/>
      <input type="submit" value="Update"/>
	</form>
  </div>
  </c:when>
  <c:otherwise>
  	<c:if test="${loggedIn}">
  	  <%@ include file="/WEB-INF/templates/userFollow.jspf" %>
  	</c:if>
  </c:otherwise>
</c:choose>

<c:if test="${!loggedIn}">
  <a href="timeline">Timeline</a>
</c:if>
</div>

<%@ include file="/WEB-INF/templates/network.jspf" %>
<%@ include file="/WEB-INF/templates/posts.jspf" %>

