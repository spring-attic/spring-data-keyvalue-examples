<div class="span-15" id="maincol">
<%@ include file="/WEB-INF/templates/posts.jspf" %>
</div>
<c:if test="${!loggedIn}">
 <div class="span-7 last" id="rightcol">
  <a href="signIn">Sign In</a>
  </div>
</c:if>
<c:if test="${!loggedIn}">
 <div class="span-7" id="rightcol">
  <a href="timeline">Timeline</a>
  </div>
</c:if>