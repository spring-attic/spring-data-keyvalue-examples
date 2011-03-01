<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<h2><fmt:message key="timeline"/></h2>
Posts from all users.
<br/>

<%@ include file="/WEB-INF/jsp/posts.jsp" %>

<div class="span-7 last" id="rightcol">
  <div class="span-7 box">
    <h4>New users</h4>
    
    <c:forEach var="u" items="${users}">
      <a href="!${u}">${u}</a>
    </c:forEach>
  </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>