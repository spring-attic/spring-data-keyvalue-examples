<div class="span-24 prepend-1 last">
  <h2 class="alt"><fmt:message key="timeline"/></h2>
</div>
<div class="span-15 prepend-1">
  <fmt:message key="all.posts"/>
</div>
<br/>

<%@ include file="/WEB-INF/templates/posts.jspf" %>


<div class="span-7 last" id="rightcol">
  <div class="span-7 box">
    <h4><fmt:message key="newusers"/></h4>
    
    <c:forEach var="u" items="${users}">
      <a href="!${u}">${u}</a>
    </c:forEach>
  </div>
</div>