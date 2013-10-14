<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <script src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery.tablesorter.min.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/common.css" type="text/css"/>  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/jquery-ui.css" type="text/css"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/tablesorter/blue/style.css" type="text/css"/>
  <script>
    $(document).ready(function() {
      $("#myTable").tablesorter({sortList: [[2,0]]});
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <a href="<%=request.getContextPath()%>/html/editadviser?id=${adviser.id}">Edit</a> | 
  <a href="<%=request.getContextPath()%>/html/deleteadviser?id=${adviser.id}">Delete</a>

  <h3>${adviser.fullName}</h3>

  <br><img src="${adviser.pictureUrl}" width="80px"/><br><br>    

  <table border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td valign="top">Email:</td>
      <td>${adviser.email}</td>
    </tr>
    <tr>
      <td valign="top">Phone:</td>
      <td>${adviser.phone}</td>
    </tr>
    <tr>
      <td>Institution:</td>
      <td>${adviser.institution}</td>
    </tr>
    <tr>
      <td>Division/Faculty:</td>
      <td>${adviser.division}</td>
    </tr>
    <tr>
      <td>Department:</td>
      <td>${adviser.department}</td>
    </tr>
    <tr>
      <td>Start date:</td>
      <td>${adviser.startDate}</td>
    </tr>
    <tr>
      <td>End date:</td>
      <td>${adviser.endDate}</td>
    </tr>
    <tr>
      <td valign="top">Notes:</td>
      <td>${adviser.notes}</td>
    </tr>
  </table>
      
  <br><br>   
  <b>Projects:</b><br>
  Total number of projects: ${f:length(projects)}<br>
  <table id="myTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Name</th>
	    <th>Host Institution</th>
	    <th>Next Review</th>
	    <th>Next Follow-up</th>
	    <th>Type</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${projects}" var="project">
      <tr>
        <td><a href="<%=request.getContextPath()%>/html/viewproject?id=${project.id}">${project.name}</a>
        <c:if test="${not empty project.todo}">
          <font color="red">(todo)</font>
        </c:if>
        </td>
        <td>${project.hostInstitution}</td>
        <c:choose>
          <c:when test="${f:contains(project.nextReviewDate, 'due')}">
            <td><font color="red">${project.nextReviewDate}</font></td>   
          </c:when>
          <c:otherwise>
            <td>${project.nextReviewDate}</td>
          </c:otherwise>
        </c:choose>
        <c:choose>
          <c:when test="${f:contains(project.nextFollowUpDate, 'due')}">
            <td><font color="red">${project.nextFollowUpDate}</font></td>   
          </c:when>
          <c:otherwise>
            <td>${project.nextFollowUpDate}</td>
          </c:otherwise>
        </c:choose>
        <td>${project.projectTypeName}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  </div>
</body>
</html>
