<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="redirectPath" value="http://${proxy}${pageContext.request.contextPath}/html/${pathAndQuerystring}"/>

<% 
  response.sendRedirect((String)pageContext.getAttribute("redirectPath")); 
%>
