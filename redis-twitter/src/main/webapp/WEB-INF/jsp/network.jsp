<%@page import="org.springframework.data.redis.samples.retwis.web.TimeUtils"%>

<div class="network" id="rightcol">
  <c:if test="${!empty in_common}">
  	 <div class="common">
        <h4>You both follow:</h4>
		<c:forEach var="c" items="${in_common}">
		   <ul class="user-list">
		     <a href="!${c}"/>${c}</a>
		   </ul>
		</c:forEach>
     </div>
  </c:if>
  
  <div class="followers">
  	<h4>Follower<c:if test="${fn:length(followers) ne 1}">s</c:if></h4>
	
	<c:forEach var="f" items="${followers}">
	   <ul class="user-list">
	     <a href="!${f}"/>${f}</a>
	   </ul>
	</c:forEach>
	<c:if test="${more_followers}">and more...</c:if>
  </div>
  <div class="following">
  	<h4>Following</h4>
	
	<c:forEach var="f" items="${following}">
	   <ul class="user-list">
	     <a href="!${f}"/>${f}</a>
	   </ul>
	</c:forEach>
	<c:if test="${more_following}">and more...</c:if>
  </div>
</div>
