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
      $("#myTable").tablesorter({sortList: [[2,0]], headers: {0: {sorter:false}, 1: {sorter:false}}});
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

  <a href="<%=request.getContextPath()%>/html/createadvisor">Create new advisor</a>
  
  <h3>Advisors</h3>
  Total number of advisors: ${f:length(advisors)}<br>
  <table id="myTable" class="tablesorter">
    <thead>
      <tr>
        <th>#</th>
	    <th>Picture</th>
	    <th>Full Name</th>
	    <th># Projects</th>
	    <th>Institution</th>
	    <th>Division/Faculty</th>
	    <th>Department</th>
	    <th>Start Date</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${advisors}" var="advisor">
      <tr>
        <td>&nbsp;</td>
        <td><a href="<%=request.getContextPath()%>/html/viewadvisor?id=${advisor.id}"><img src="${advisor.pictureUrl}" width="40px"/></a></td>
        <td><a href="<%=request.getContextPath()%>/html/viewadvisor?id=${advisor.id}">${advisor.fullName}</a></td>        
        <td>${advisor.numProjects}</td>
        <td>${advisor.institution}</td>
        <td>${advisor.department1}</td>
        <td>${advisor.department2}</td>
        <td>${advisor.startDate}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
              
  </div>
</body>
</html>
