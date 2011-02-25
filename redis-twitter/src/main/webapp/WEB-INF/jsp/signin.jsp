<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><fmt:message key="welcome"/></h2>

<div class="span-11 box">
  <h2>Sign In</h2>
  
  <form action="signIn" method="post">
    <table>
      <tr>
        <td>username</td>
       <td><input name="name" /></td>
      </tr>
      <tr>
        <td>password</td>
        <td><input type="pass" name="pass" /></td>
      </tr>
    </table>
    <input type="submit" value="Sign In" />

  </form>
</div>  

<div class="span-11 last box">
  <h2>Sign Up</h2>
  
  <form action="signUp" method="post">
    <table>
      <tr>
        <td>username</td>
        <td><input name="name" /></td>
      </tr>
      <tr>
        <td>password</td>
        <td><input type="password" name="pass" /></td>
      </tr>
      <tr>
        <td>password again</td>
        <td><input type="password" name="pass2" /></td>
      </tr>
    </table>
    <input type="submit" value="Sign Up">
  </form>
</div>

<p>&nbsp;</p>
<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>