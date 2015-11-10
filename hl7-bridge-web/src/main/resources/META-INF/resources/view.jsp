<%@ include file="/init.jsp" %>

<span>Received messages:</span><br />
<c:forEach var="entry" items="${counter}">
 <p>
  <strong>Msg:</strong> <c:out value="${entry.key}"/><br />
  <strong>Count:</strong> <c:out value="${entry.value}"/>
 </p>
</c:forEach>

<p><small>Reload page to refresh</small></p>