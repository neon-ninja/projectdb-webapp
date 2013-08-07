<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="redirectPath" value="${pageContext.request.contextPath}/html/editproject?id=${id}"/>

<% 
  response.sendRedirect((String)pageContext.getAttribute("redirectPath")); 
%>