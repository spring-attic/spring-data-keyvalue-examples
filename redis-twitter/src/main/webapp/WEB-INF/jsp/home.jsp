<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><c:if test="${loggedUser eq name}"><fmt:message key="welcome"/> </c:if>${name}</h2>

<c:choose>
  <c:when test="${loggedUser eq name}">
<form:form commandName="post" method="post" action="!${name}">
  <table>
    <tr>
      <th>
        <i>${name}</i>, what's on your mind? (Why not say hi to <a href="!costinl">@costinl</a> ?)<br/>
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
 </c:when>
<c:otherwise>
<%@ include file="/WEB-INF/jsp/userFollow.jsp" %>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jsp/posts.jsp" %>

<%@ include file="/WEB-INF/jsp/network.jsp" %>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>