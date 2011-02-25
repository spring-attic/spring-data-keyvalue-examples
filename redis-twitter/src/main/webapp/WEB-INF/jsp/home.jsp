<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><fmt:message key="profile"/></h2>

<h1>Welcome ${name}</h1>

<form method="post" action="home">
  <table>
    <tr>
      <th>
        <i>${name}</i>, what's on your mind?<br/>
        <form:errors path="type" cssClass="errors"/>
        <br/>
        <textarea name="content" rows="3" columns="70"></textarea><br />
      </th>
    </tr>
    <tr>
      <td>
        <p class="submit"><input type="submit" value="Update"/></p>
      </td>
    </tr>
  </table>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>