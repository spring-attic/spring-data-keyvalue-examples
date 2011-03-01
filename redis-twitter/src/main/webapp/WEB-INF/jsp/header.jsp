<!--
	Retwis :: a Spring Data Redis Framework demonstration
-->

<%@page import="org.springframework.data.redis.samples.retwis.RetwisSecurity"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="<spring:url value="/static/styles/petclinic.css" htmlEscape="true" />" type="text/css"/>
  <title>Retwis :: a Spring Data Redis Framework demonstration</title>	
</head>

<body>

  <div class="span-12 last right-align">
    <% 
    	pageContext.setAttribute("loggedIn", RetwisSecurity.isSignedIn(), PageContext.PAGE_SCOPE);
    	pageContext.setAttribute("loggedUser", RetwisSecurity.getName(), PageContext.PAGE_SCOPE); 
    %>
    <c:if test="${loggedIn}">
	    <br /><br />
	    <a href="!${loggedUser}">home</a> |
        <a href="!${loggedUser}/mentions">@${loggedUser}</a> | 
        <a href="timeline">timeline</a> | 
        <a href="logout">logout</a>
    </c:if>
  </div>
  <hr />

  <div id="main">
