<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<%@page import="org.springframework.data.redis.samples.retwis.web.TimeUtils"%>

<h2><fmt:message key="profile"/></h2>

<h1>Welcome ${name}</h1>

<form:form commandName="post" method="post" action="!${name}">
  <table>
    <tr>
      <th>
        <i>${name}</i>, what's on your mind?<br/>
        <br/>
        <form:textarea path="content" rows="3" columns="70"></form:textarea><br />
      </th>
    </tr>
    <tr>
      <td>
        <p class="submit"><input type="submit" value="Update"/></p>
      </td>
    </tr>
  </table>
</form:form>

Home
<div id="posts">
   <c:forEach var="p" items="${posts}">
   <div class="post">
      ${p.content}
      <c:set var="post_time" scope="page" value="${p.time}"/>
      
      <div class="date">
      	<%= TimeUtils.inWords(Long.valueOf((String)pageContext.getAttribute("post_time"))) %>
      </div>
   </div>
   </c:forEach>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>