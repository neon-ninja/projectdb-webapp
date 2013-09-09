<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <script src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery.tablesorter.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery.watermark.min.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/common.css" type="text/css"/>  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/jquery-ui.css" type="text/css"/>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/tablesorter/blue/style.css" type="text/css"/>
  <script>
    $(function() {
        $("#researcherTable").tablesorter();
        $("#datepicker1").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker2").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker3").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker4").datepicker({ dateFormat: "yy-mm-dd" });

        $("#project_id").watermark("E.g. NeSI project id");
        $("#project_todo").watermark("Things that need to be done on this project");
        $("#project_requirements").watermark("Potential things to add here are required disk space, help with scripting, help with code parallelization, etc");
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <a href="<%=request.getContextPath()%>/html/viewproject?id=${project.id}">Back to view</a><br>
  
  <h3>Edit Project</h3>

  <br>
  <h4>Details</h4>
    
  <form:form method="post" modelAttribute="project" commandName="project">
  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>Name:</td>
      <td>&nbsp;</td>
      <td><form:input path="name" size="120"/></td>
    </tr>
    <tr>
      <td valign="top">Description:</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea path="description" rows="10" cols="104"/></td>
    </tr>
    <tr>
      <td>Host Institution:</td>
      <td>&nbsp;</td>
      <td><form:input path="hostInstitution" size="120"/></td>
    </tr>
    <tr>
      <td>Type:</td>
      <td>&nbsp;</td>
      <td><form:select path="projectTypeId" items="${projectTypes}"/></td>
    </tr>
    <tr>
      <td>Start date:</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker1" path="startDate" size="20"/></td>
    </tr>
    <tr>
      <td>Next review date:</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker2" path="nextReviewDate" size="20"/></td>
    </tr>
    <tr>
      <td>Next follow-up date:</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker3" path="nextFollowUpDate" size="20"/></td>
    </tr>
    <tr>
      <td>End date:</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker4" path="endDate" size="20"/></td>
    </tr>
    <tr>
      <td>Project Code:</td>
      <td>&nbsp;</td>
      <td><form:input id="project_id" path="projectCode" size="20"/></td>
    </tr>
    <tr>
      <td valign="top">Requirements:</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea id="project_requirements" path="requirements" rows="5" cols="104"/></td>
    </tr>
    <tr>
      <td valign="top">Notes:</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea path="notes" rows="5" cols="104"/></td>
    </tr>
    <tr>
      <td valign="top">Todo:</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea id="project_todo" path="todo" rows="5" cols="104"/></td>
    </tr>
  </table>
  
  <br><br>
  <input type="submit" align="center" value="Submit changes">
  <input type="reset" align="center" value="Reset">
  </form:form>
  
  <br>
  <h4>Researchers on project</h4>

  <table id="researcherTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Picture</th>
	    <th>Name</th>
   	    <th>Role on Project</th>
   	    <th>Institution</th>
   	    <th>Institutional role</th>
   	    <th>Notes</th>
   	    <th>Action</th>
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
          <td>
           <a href="<%=request.getContextPath()%>/html/deleteresearcherfromproject?projectId=${project.id}&researcherId=${researcher.id}"> 
              Delete researcher from project</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  
  <a href="<%=request.getContextPath()%>/html/createRPLink?id=${project.id}">Add researcher to project</a><br>

  <br>
  <h4>Advisors on project</h4>

  <table id="advisorTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Picture</th>
	    <th>Name</th>
   	    <th>Role on Project</th>
   	    <th>Notes</th>
   	    <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${advisors}" var="advisor" varStatus="loop">
        <tr>
          <td><a href="<%=request.getContextPath()%>/html/viewadvisor?id=${advisors[loop.index].id}"><img src="${advisors[loop.index].pictureUrl}" width="60px"/></a></td>
          <td><a href="<%=request.getContextPath()%>/html/viewadvisor?id=${advisors[loop.index].id}">${advisors[loop.index].fullName}</a></td>
          <td>${apls[loop.index].advisorRole}</td>
          <td>${apls[loop.index].notes}</td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deleteadvisorfromproject?projectId=${project.id}&advisorId=${advisor.id}"> 
              Delete advisor from project</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>


<!-- 
  <table id="advisorTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Picture</th>
	    <th>Name</th>
   	    <th>Action</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${advisors}" var="advisor">
        <tr>
          <td><img src="${advisor.pictureUrl}" width="60px"/></td>
          <td>${advisor.fullName}</td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deleteadvisorfromproject?projectId=${project.id}&advisorId=${advisor.id}"> 
              Delete advisor from project</a>
          </td>
        </tr>
    </c:forEach>
    </tbody>
  </table>
 -->
  
  <a href="<%=request.getContextPath()%>/html/createAPLink?id=${project.id}">Add advisor to project</a><br>
  
 
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
	    <th>Action</th>
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
          <td>
              <a href="<%=request.getContextPath()%>/html/deleteprojectkpi?id=${projectKpi.id}&projectId=${project.id}"> 
              Delete</a>
          
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  
  <a href="<%=request.getContextPath()%>/html/createprojectkpi?id=${project.id}">Create KPI for project</a><br>
  
  
  <br>
  <h4>Research Output</h4>

  <table id="researchOutputTable" class="tablesorter">
    <thead>
      <tr>
        <th>Date</th>
	    <th>Type</th>
	    <th>Citation</th>
	    <th>Link</th>
   	    <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${researchOutputs}" var="researchOutput">
        <tr>
          <td>${researchOutput.date}</td>
          <td>${researchOutput.type}</td>
          <td>${researchOutput.description}</td>
          <td><a target="new" href="${researchOutput.link}">${researchOutput.link}</a></td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deleteresearchoutput?researchOutputId=${researchOutput.id}&projectId=${project.id}"> 
              Delete</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <a href="<%=request.getContextPath()%>/html/createresearchoutput?id=${project.id}">Add research output</a><br>

<!-- 
  <br>
  <h4>Attachments</h4>

  <table id="attachmentTable" class="tablesorter">
    <thead>
      <tr>
        <th>Date</th>
	    <th>Description</th>
	    <th>Link</th>
	    <th>Attachment</th>
	    <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${attachments}" var="attachment">
        <c:if test="${empty attachment.followUpId}">
        <tr>
          <td>${attachment.date}</td>
          <td>${attachment.description}</td>
          <td><a target="new" href="${attachment.link}">${attachment.link}</a></td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deleteattachment?attachmentId=${attachment.id}&projectId=${project.id}"> 
              Delete</a>
          </td>
        </tr>
        </c:if>
      </c:forEach>
    </tbody>
  </table>


  <a href="<%=request.getContextPath()%>/html/createattachment?id=${project.id}">Add attachment</a><br>

-->

  <br>
  <h4>Reviews</h4>
    
  <table id="reviewTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Advisor</th>
   	    <th>Notes</th>
   	    <th>Attachments</th>
   	    <th>Action</th>
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
              ${attachment.description}<br>
              <a href="<%=request.getContextPath()%>/html/deleteattachment?attachmentId=${attachment.id}&projectId=${project.id}">Delete attachment</a>
              <hr/>
            </c:if>
          </c:forEach>
          <a href="<%=request.getContextPath()%>/html/createattachment?id=${project.id}&reviewId=${review.id}">Add attachment</a>
          </td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deletereview?reviewId=${review.id}&projectId=${project.id}"> 
              Delete</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <a href="<%=request.getContextPath()%>/html/createreview?id=${project.id}">Add review</a><br>

  <br>
  <h4>Follow-ups</h4>

  <table id="followUpTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Advisor</th>
   	    <th>Notes</th>
   	    <th>Attachments</th>
   	    <th>Action</th>
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
              <a href="<%=request.getContextPath()%>/html/deleteattachment?attachmentId=${attachment.id}&projectId=${project.id}">Delete attachment</a>
              <hr/>
            </c:if>
          </c:forEach>
          <a href="<%=request.getContextPath()%>/html/createattachment?id=${project.id}&followUpId=${followUp.id}">Add attachment</a>
          </td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deletefollowup?followUpId=${followUp.id}&projectId=${project.id}"> 
              Delete</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <a href="<%=request.getContextPath()%>/html/createfollowup?id=${project.id}">Add follow-up</a><br>

  <br>
  <h4>Advisor Actions</h4>

  <table id="advisorActionTable" class="tablesorter">
    <thead>
      <tr>
	    <th>Date</th>
	    <th>Advisor</th>
   	    <th>Advisor Action</th>
   	    <th>Attachments</th>
   	    <th>Action</th>
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
              <a href="<%=request.getContextPath()%>/html/deleteattachment?attachmentId=${attachment.id}&projectId=${project.id}">Delete attachment</a>
              <hr/>
            </c:if>
          </c:forEach>
          <a href="<%=request.getContextPath()%>/html/createattachment?id=${project.id}&advisorActionId=${advisorAction.id}">Add attachment</a>
          </td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deleteadvisoraction?advisorActionId=${advisorAction.id}&projectId=${project.id}"> 
              Delete</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <a href="<%=request.getContextPath()%>/html/createadvisoraction?id=${project.id}">Add advisor action</a><br>

  <br>
  <h4>HPC Facilities</h4>

  <table id="facilityTable" class="tablesorter">
    <thead>
      <tr>
   	    <th>HPC Facility</th>
   	    <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${facilitiesOnProject}" var="facility">
        <tr>
          <td>${facility.name}</td>
          <td>
            <a href="<%=request.getContextPath()%>/html/deletefacilityfromproject?facilityId=${facility.id}&projectId=${project.id}"> 
              Delete</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <a href="<%=request.getContextPath()%>/html/createprojectfacility?id=${project.id}">Add HPC facility</a><br>

  </div>
</body>
</html>
