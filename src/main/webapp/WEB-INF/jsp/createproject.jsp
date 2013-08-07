<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <meta charset="utf-8">
  <script src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery.data.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery.watermark.min.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/common.css" type="text/css"/>  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/jquery-ui.css" type="text/css"/>
  <script>
    $(function() {
        $("#datepicker1").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker2").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker3").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker4").datepicker({ dateFormat: "yy-mm-dd" });
        
        // form field watermarks
        $("#project_name").watermark("Name of the project");
        $("#project_description").watermark("Description of the scientific background and goals of the project. Please also add a paragraph how the use of HPC facilities are beneficial to the project");
        $("#project_requirements").watermark("Potential things to add here are required disk space, help with scripting, help with code parallelization, etc");
        $("#project_notes").watermark("Other notes on the project");
        $("#apLink_notes").watermark("Notes on the advisor on this particular project");
        $("#rpLink_notes").watermark("Notes on the researcher on this particular project");
        $("#project_id").watermark("E.g. NeSI project id");
        $("#project_hostInstitution").watermark("Institution that administers the research project. If in doubt, this is the institution where the PI is working.")
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <h3>Create Project</h3>
  
  <form:form method="post" commandName="createProject">

  <h3>Project details</h3>

  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>Name</td>
      <td>&nbsp;</td>
      <td><form:input id="project_name" path="project.name" size="120"/></td>
    </tr>
    <tr>
      <td valign="top">Description</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea id="project_description" path="project.description" rows="10" cols="104"/></td>
    </tr>
    <tr>
      <td>Host Institution</td>
      <td>&nbsp;</td>
      <td><form:input id="project_hostInstitution" path="project.hostInstitution" size="120"/></td>
    </tr>
    <tr>
      <td>Type</td>
      <td>&nbsp;</td>
      <td><form:select path="project.projectTypeId" items="${projectTypes}"/></td>
    </tr>
    <tr>
      <td>Start date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker1" path="project.startDate" size="20"/></td>
    </tr>
    <tr>
      <td>Next review date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker2" path="project.nextReviewDate" size="20"/></td>
    </tr>
    <tr>
      <td>Next follow-up date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker3" path="project.nextFollowUpDate" size="20"/></td>
    </tr>
    <tr>
      <td>End date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker4" path="project.endDate" size="20"/></td>
    </tr>
    <tr>
      <td>ID</td>
      <td>&nbsp;</td>
      <td><form:input id="project_id"  path="project.projectId" size="20"/></td>
    </tr>
    <tr>
      <td valign="top">Requirements</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea id="project_requirements" path="project.requirements" rows="5" cols="104"/></td>
    </tr>
    <tr>
      <td valign="top">Notes</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea id="project_notes" path="project.notes" rows="5" cols="104"/></td>
    </tr>
  </table>
  
  <br><h4>HPC facility</h4>
  
  <table>
    <tr>
      <td>HPC Facility</td>
      <td><form:select path="hpcFacility.facilityId" items="${hpcFacilities}"/></td>
    </tr>
  </table>
  
  <br><h4>Researcher</h4>
  
  <table>
    <tr>
      <td>Researcher</td>
      <td>&nbsp;</td>
      <td><form:select path="rpLink.researcherId" items="${researchers}"/></td>
    </tr>
    <tr>
      <td>Researcher role</td>
      <td>&nbsp;</td>
      <td><form:select path="rpLink.researcherRoleId" items="${researcherRoles}"/></td>
    </tr>
    <tr>
      <td valign="top">Notes</td>
      <td>&nbsp;</td>
      <td><form:textarea id="rpLink_notes" path="rpLink.notes" rows="3" cols="104"/></td>
    </tr>
  </table>

  <br><h4>Advisor</h4>
  
  <table>
    <tr>
      <td>Advisor</td>
      <td>&nbsp;</td>
      <td><form:select path="apLink.advisorId" items="${advisors}"/></td>
    </tr>
    <tr>
      <td>Advisor role</td>
      <td>&nbsp;</td>
      <td><form:select path="apLink.advisorRoleId" items="${advisorRoles}"/></td>
    </tr>
    <tr>
      <td valign="top">Notes</td>
      <td>&nbsp;</td>
      <td><form:textarea id="apLink_notes" path="apLink.notes" rows="3" cols="104"/></td>
    </tr>
  </table>
  
  <br><br>
  <input type="submit" align="center" value="Create project">
  </form:form>
      
  </div>
</body>
</html>
