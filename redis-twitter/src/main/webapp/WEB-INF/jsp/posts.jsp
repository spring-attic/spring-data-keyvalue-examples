<%@page import="org.springframework.data.redis.samples.retwis.web.WebUtils"%>

<div id="posts">
   <c:choose>
    	<c:when test="${empty posts}">
	       <i>No posts.</i>
	   </c:when>
	   <c:otherwise>
		   <c:forEach var="p" items="${posts}">
		   <div class="post">
		      <a href="!${p.name}"><b>${p.name}</b></a> ${p.content}
		      <c:set var="post_time" value="${p.time}"/>
		      
		      <div class="date">
		        <!-- link to post -->
		      	<a href="!${p.name}/status/${p.pid}"><%= WebUtils.timeInWords(Long.valueOf((String)pageContext.getAttribute("post_time"))) %></a>
			    <!-- reply connection -->
			    <c:if test="${!empty p.replyPid}">
			       <a href="!${p.replyName}}/status/${p.replyPid}">in reply to ${p.replyName}</a>
			    </c:if>
			    Reply | Repost
		      </div>
		   </div>
		   </c:forEach>
	   </c:otherwise>
   </c:choose>
</div>