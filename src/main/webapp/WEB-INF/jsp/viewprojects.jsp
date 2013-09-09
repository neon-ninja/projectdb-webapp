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
      $("#myTable").tablesorter({sortList: [[6,0],[5,0]], headers: {0: {sorter:false}}});      
      var table = $("#myTable");
      table.bind("sortEnd",function() { 
          var i = 1;
          table.find("tr:gt(0)").each(function(){
              $(this).find("td:eq(0)").text(i);
              i++;
          });
      }); 
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <a href="<%=request.getContextPath()%>/html/createproject">Create new project</a><br>
  <!--
  <a href="http://cluster.ceres.auckland.ac.nz/project_management/ResearcherQuestionnaire.txt">Researcher Questionnaire</a>
  -->
   
  <h3>Projects</h3>
  Total number of projects: ${f:length(projects)}<br>
  <table id="myTable" class="tablesorter">
    <thead>
      <tr>
        <th>#</th>
	    <th>Name</th>
	    <th>Code</th>
<!--
   	    <th><img src="<%=request.getContextPath()%>/pics/attachment.png"/></th>
   	    <th><img src="<%=request.getContextPath()%>/pics/kpi.jpg"/></th>
   	    <th><img src="<%=request.getContextPath()%>/pics/output.jpg"/></th>
-->
   	    <th>Start</th>
   	    <th>Next review</th>
   	    <th>Next follow-up</th>
   	    <th>End</th>
	    <th>Host Institution</th>
        <th>Type</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${projects}" var="project">
      <tr>
        <td>&nbsp;</td>
        <td><a href="<%=request.getContextPath()%>/html/viewproject?id=${project.id}">${project.name}</a></td>
        <td>${project.projectCode}</td>
<!--
        <td>
          <c:if test="${empty project.projectId}">
            y   
          </c:if>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
-->
        <td>${project.startDate}</td>
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
        <td>${project.endDate}</td>
        <td>${project.hostInstitution}</td>
        <td>${project.projectType}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
              
  </div>
</body>
</html>
