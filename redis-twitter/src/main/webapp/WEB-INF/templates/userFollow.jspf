<div class="box">
	<c:choose>
	  <c:when test="${follows}"><a href="!${name}/stopfollowing">Stop following</a></c:when>
	  <c:otherwise><a href="!${name}/follow">Follow</a></c:otherwise>
	</c:choose>
	<c:if test="${!no_mentions}"><br/><a href="!${name}/mentions"><fmt:message key="Mentions"/></a></c:if>
</div>

<div class="box">
    <c:if test="${!empty also_followed}">
  	Also followed by: <c:forEach var="f" items="${also_followed}"><a href="!${f}">${f}</a> </c:forEach>
    </c:if>
</div>