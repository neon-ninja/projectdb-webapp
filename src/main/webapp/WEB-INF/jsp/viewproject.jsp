<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="c-rt" uri="http://java.sun.com/jstl/core_rt" %>
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
      $("#researcherTable").tablesorter();
      $("#advisorTable").tablesorter();
      $("#kpiTable").tablesorter();
      $("#researchOutputTable").tablesorter();
      $("#attachmentTable").tablesorter();
      $("#reviewTable").tablesorter();
      $("#followUpTable").tablesorter();
      $("#advisorActionTable").tablesorter();
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">

  <a href="<%=request.getContextPath()%>/html/editproject?id=${project.id}">Edit</a> | 
  <a href="<%=request.getContextPath()%>/html/deleteproject?id=${project.id}">Delete</a>
  
  <h3>Project: ${project.name}</h3>

  <br>
  <h4>Project Details</h4>
  
  <table border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td><nobr>Name:</nobr></td>
      <td>${project.name}</td>
    </tr>
    <tr>
      <td valign="top"><nobr>Description:</nobr></td>
      <td>${project.description}</td>
    </tr>
    <tr>
      <td><nobr>Host Institution:</nobr></td>
      <td>${project.hostInstitution}</td>
    </tr>
    <tr>
      <td valign="top"><nobr>HPC Facilities:</nobr></td>
      <td>
        <c:forEach items="${facilities}" var="facility">
          ${facility.name}<br>
        </c:forEach>
      </td>
    </tr>
    <tr>
      <td><nobr>Type:</nobr></td>
      <td>${project.projectType}</td>
    </tr>
    <tr>
      <td><nobr>Start:</nobr></td>
      <td>${project.startDate}</td>
    </tr>
    <tr>
      <td><nobr>Next review:</nobr></td>
      <td>${project.nextReviewDate}</td>
    </tr>
    <tr>
      <td><nobr>Next follow-up:</nobr></td>
      <td>${project.nextFollowUpDate}</td>
    </tr>
    <tr>
      <td><nobr>End:</nobr></td>
      <td>${project.endDate}</td>
    </tr>
    <tr>
      <td><nobr>ID (NeSI ID):</nobr></td>
      <td>${project.projectId}</td>
    </tr>
    <tr>
      <td valign="top">Requirements:</td>
      <td valign="top">${project.requirements}</td>
    </tr>
    <tr>
      <td valign="top"><nobr>Notes:</nobr></td>
      <td>${project.notes}</td>
    </tr>
    <tr>
      <td valign="top"><nobr>Todo:</nobr></td>
      <td>${project.todo}</td>
    </tr>
  </table>

  <br>
  <h4>Researchers on project</h4>

  <table id="researcherTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Picture</th>
	    <th>Name</th>
   	    <th>Role</th>
   	    <th>Institution</th>
   	    <th>Institutional role</th>
   	    <th>Notes</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${researchers}" var="researcher" varStatus="loop">
        <tr>
          <td><a href="<%=request.getContextPath()%>/html/viewresearcher?id=${researchers[loop.index].id}"><img src="${researchers[loop.index].pictureUrl}" width="60px"/></a></td>
          <td><a href="<%=request.getContextPath()%>/html/viewresearcher?id=${researchers[loop.index].id}">${researchers[loop.index].fullName}</a></td>
          <td>${rpls[loop.index].researcherRole}</td>
          <td>${researchers[loop.index].institution}</td>
          <td>${researchers[loop.index].institutionalRole}</td>
          <td>${rpls[loop.index].notes}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  
  <br>
  <h4>Advisors on project</h4>

  <table id="advisorTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Picture</th>
	    <th>Name</th>
   	    <th>Role</th>
   	    <th>Notes</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${advisors}" var="advisor" varStatus="loop">
        <tr>
          <td><a href="<%=request.getContextPath()%>/html/viewadvisor?id=${advisors[loop.index].id}"><img src="${advisors[loop.index].pictureUrl}" width="60px"/></a></td>
          <td><a href="<%=request.getContextPath()%>/html/viewadvisor?id=${advisors[loop.index].id}">${advisors[loop.index].fullName}</a></td>
          <td>${apls[loop.index].advisorRole}</td>
          <td>${apls[loop.index].notes}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <br>
  <h4>KPIs</h4>
  
  <table id="kpiTable" class="tablesorter">
    <thead>
      <tr>
        <th>Date</th>
	    <th>KPI</th>
	    <th>Reported By</th>
	    <th>Value</th>
	    <th>Notes</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${projectKpis}" var="projectKpi">
        <tr>
          <td>${projectKpi.date}</td>
          <td>${projectKpi.kpiType}-${projectKpi.kpiId}: ${projectKpi.kpiTitle}</td>
          <td>${projectKpi.advisor}</td>
          <td>${projectKpi.value}</td>
          <td>${projectKpi.notes}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <br>
  <h4>Research Output</h4>

  <table id="researchOutputTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Type</th>
	    <th>Citation</th>
	    <th>Link</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${researchOutputs}" var="researchOutput">
        <tr>
          <td>${researchOutput.date}</td>
          <td>${researchOutput.type}</td>
          <td>${researchOutput.description}</td>
          <td><a target="new" href="${researchOutput.link}">${researchOutput.link}</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

<!-- 
  <br>
  <h4>Attachments</h4>

  <table id="attachmentTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Description</th>
	    <th>Link</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${attachments}" var="attachment">
        <c:if test="${empty attachment.followUpId}">
          <tr>
            <td>${attachment.date}</td>
            <td>${attachment.description}</td>
            <td><a target="new" href="${attachment.link}">${attachment.link}</a></td>
          </tr>
        </c:if>
      </c:forEach>
    </tbody>
  </table>
-->
 
  <br>
  <h4>Reviews</h4>

  <table id="reviewTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Reported By</th>
   	    <th>Notes</th>
   	    <th>Attachments</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${reviews}" var="review">
        <tr>
          <td>${review.date}</td>
          <td>${review.advisor}</td>
          <td>${review.notes}</td>
          <td>
          <c:forEach items="${attachments}" var="attachment">
            <c:if test="${attachment.reviewId eq review.id}">
              <b>Link</b>:<br>
              <a href="${attachment.link}">${attachment.link}</a><br>
              <b>Description</b>:<br>
              ${attachment.description}
              <hr/>
            </c:if>
          </c:forEach>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <br>
  <h4>Follow-ups</h4>

  <table id="followUpTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Reported By</th>
   	    <th>Notes</th>
   	    <th>Attachments</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${followUps}" var="followUp">
        <tr>
          <td>${followUp.date}</td>
          <td>${followUp.advisor}</td>
          <td>${followUp.notes}</td>
          <td>
          <c:forEach items="${attachments}" var="attachment">
            <c:if test="${attachment.followUpId eq followUp.id}">
              <b>Link</b>:<br>
              <a href="${attachment.link}">${attachment.link}</a><br>
              <b>Description</b>:<br>
              ${attachment.description}<br>
              <hr/>
            </c:if>
          </c:forEach>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <br>
  <h4>Advisor Actions</h4>

  <table id="advisorActionTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Advisor</th>
   	    <th>Action</th>
   	    <th>Attachments</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${advisorActions}" var="advisorAction">
        <tr>
          <td>${advisorAction.date}</td>
          <td>${advisorAction.advisor}</td>
          <td>${advisorAction.action}</td>
          <td>
          <c:forEach items="${attachments}" var="attachment">
            <c:if test="${attachment.advisorActionId eq advisorAction.id}">
              <b>Link</b>:<br>
              <a href="${attachment.link}">${attachment.link}</a><br>
              <b>Description</b>:<br>
              ${attachment.description}<br>
              <hr/>              
            </c:if>
          </c:forEach>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  </div>
</body>
</html>
