<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <meta charset="utf-8">
  <script src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/common.css" type="text/css"/>  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/jquery-ui.css" type="text/css"/>
  <script>
    $(function() {
        $("#datepicker1").datepicker({ dateFormat: "yy-mm-dd" });
        $("#datepicker2").datepicker({ dateFormat: "yy-mm-dd" });
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">

  <a href="<%=request.getContextPath()%>/html/viewresearcher?id=${researcher.id}">Back to view</a><br>
  
  <h3>Edit Researcher</h3>

  <br>
  
  <form:form method="post" commandName="researcher">
  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>Full Name</td>
      <td>&nbsp;</td>
      <td><form:input path="fullName" size="120"/></td>
    </tr>
    <tr>
      <td>Picture URL</td>
      <td>&nbsp;</td>
      <td><form:input path="pictureUrl" size="120"/></td>
    </tr>
    <tr>
      <td valign="top">Email</td>
      <td valign="top">&nbsp;</td>
      <td><form:input path="email" size="120"/></td>
    </tr>
    <tr>
      <td valign="top">Phone</td>
      <td valign="top">&nbsp;</td>
      <td><form:input path="phone" size="120"/></td>
    </tr>
    <tr>
      <td>Institution</td>
      <td>&nbsp;</td>
      <td><form:input path="institution" size="120"/></td>
    </tr>
    <tr>
      <td>Division/Faculty</td>
      <td>&nbsp;</td>
      <td><form:input path="division" size="120"/></td>
    </tr>
    <tr>
      <td>Department</td>
      <td>&nbsp;</td>
      <td><form:input path="department" size="120"/></td>
    </tr>
    <tr>
      <td>Institutional role</td>
      <td>&nbsp;</td>
      <td><form:select path="institutionalRoleId" items="${institutionalRoles}"/></td>
    </tr>
    <tr>
      <td>Start date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker1" path="startDate" size="20"/></td>
    </tr>
    <tr>
      <td>End date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker2" path="endDate" size="20"/></td>
    </tr>
    <tr>
      <td valign="top">Notes</td>
      <td valign="top">&nbsp;</td>
      <td><form:textarea path="notes" rows="5" cols="104"/></td>
    </tr>
  </table>
  <br>
  <input type="submit" align="center" value="Save">
  <input type="reset" align="center" value="Reset">
  </form:form>
      
  </div>
</body>
</html>
