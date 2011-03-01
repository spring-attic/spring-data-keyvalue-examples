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
	<div class="box">
	   <c:choose>
	     <c:when test="${follows}"><a href="!${name}/stopfollowing">Stop following</a></c:when>
	     <c:otherwise><a href="!${name}/follow">Follow</a></c:otherwise>
	   </c:choose>
	   <br/><a href="!${name}/mentions"><fmt:message key="Mentions"/></a>
	</div>
	<div class="box">
	  <c:if test="${!empty also_followed}">
	  	Also followed by: <c:forEach var="f" items="also_follwed"><a href="!/${f}">${f}</a> </c:forEach>
	  </c:if>
	</div>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jsp/posts.jsp" %>

<%@ include file="/WEB-INF/jsp/network.jsp" %>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>