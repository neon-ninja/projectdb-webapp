<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/common.css" type="text/css"/>  
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <h3>Add HPC facility to project</h3>
  <br>
  
  <form:form method="post" commandName="projectFacility">
  <spring:bind path="projectFacility.projectId">
    <input type="hidden" name="projectId" value="${pid}"/>
  </spring:bind>
  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>HPC Facility</td>
      <td><form:select path="facilityId" items="${pfNotOnProject}"/></td>
    </tr>
  </table>
  <br>
  <input type="submit" align="center" value="Add HPC facility to project">
  </form:form>
      
  </div>
</body>
</html>
