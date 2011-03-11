<h2 ><fmt:message key="welcome"/></h2>

<div class="span-11 box">
  <h2>Sign In</h2>
  
  <form action="signIn" method="post">
     <c:if test="${errorpass}">
	    <div class="error">
 		    <fmt:message key="error.pass"/>
		</div>
  	</c:if>
  
    <table>
      <tr>
        <td>username</td>
       <td><input  name="name" /></td>
      </tr>
      <tr>
        <td>password</td>
        <td><input type="password" name="pass" /></td>
      </tr>
    </table>
    <input type="submit" value="Sign In" />

  </form>
</div>  

<div class="span-11 box last">
  <h2>Sign Up</h2>

   <c:if test="${errorduplicateuser}">
	   <div class="error">
    	   <fmt:message key="error.duplicateuser"/>
       </div>
   </c:if>

   <c:if test="${errormatch}">
      <div class="error">
	     <fmt:message key="error.match"/>
	  </div>
   </c:if>
	
  <form action="signUp" method="post">
    <table>
      <tr>
        <td>username</td>
        <td><input  name="name" /></td>
      </tr>
      <tr>
        <td>password</td>
        <td><input  type="password" name="pass" /></td>
      </tr>
      <tr>
        <td>password again</td>
        <td><input  type="password" name="pass2" /></td>
      </tr>
    </table>
    <input type="submit" value="Sign Up">
  </form>
</div>

<p>&nbsp;</p>
<p>&nbsp;</p>