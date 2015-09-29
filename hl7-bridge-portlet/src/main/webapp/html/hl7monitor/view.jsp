<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:defineObjects />

<p>This is the <b>HL7 Monitor</b> portlet.</p>
<span>Received messages:</span><br />
<c:forEach var="entry" items="${counter}">
 <p>
  <strong>Msg:</strong> <c:out value="${entry.key}"/><br />
  <strong>Count:</strong> <c:out value="${entry.value}"/>
 </p>
</c:forEach>

<p><small>Reload page to refresh</small></p>
