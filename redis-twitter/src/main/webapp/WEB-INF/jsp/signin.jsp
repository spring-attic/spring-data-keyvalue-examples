<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><fmt:message key="Retwis"/></h2>

<div class="span-11 box">
  <h2>Login</h2>
  
  <form action="signin" method="post">
    <table>
      <tr>
        <td>username</td>
       <td><input name="username" /></td>
      </tr>
      <tr>
        <td>password</td>
        <td><input type="password" name="password" /></td>
      </tr>
    </table>
    <input type="submit" value="Sign in" />

  </form>
</div>  

<div class="span-11 last box">
  <h2>Sign up</h2>
  
  <form action="signup" method="post">
    <table>
      <tr>
        <td>username</td>
        <td><input name="username" /></td>
      </tr>
      <tr>
        <td>password</td>
        <td><input type="password" name="password" /></td>
      </tr>
      <tr>
        <td>password again</td>
        <td><input type="password" name="password_confirmation" /></td>
      </tr>
    </table>
    <input type="submit" value="Sign up">
  </form>
</div>

<p>&nbsp;</p>
<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>