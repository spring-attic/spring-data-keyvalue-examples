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
<%@ include file="/WEB-INF/templates/userFollow.jspf" %>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/templates/posts.jspf" %>
<%@ include file="/WEB-INF/templates/network.jspf" %>
