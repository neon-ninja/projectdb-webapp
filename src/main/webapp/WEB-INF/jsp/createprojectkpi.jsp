<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <meta charset="utf-8">
  <script src="<%=request.getContextPath()%>/js/jquery-1.8.3.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
  <script src="<%=request.getContextPath()%>/js/jquery.watermark.min.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/common.css" type="text/css"/>  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/style/jquery-ui.css" type="text/css"/>
  <script>
    $(function() {
        $("#datepicker").datepicker({ dateFormat: "yy-mm-dd" });

        // form field watermarks
        $("#value").watermark("!!! base 10 scaling factor from now on !!!");
        $("#notes").watermark("Describe the KPI, e.g. in case of NeSI-9: What factor (memory, CPU, level of concurrency) was scaled");
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <h3>Add project KPI</h3>
  <br>
  
  <form:form method="post" commandName="projectkpi">
  
  <spring:bind path="projectkpi.projectId">
    <input type="hidden" name="projectId" value="${projectId}"/>
  </spring:bind>

 
  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>Advisor</td>
      <td>&nbsp;</td>
      <td><form:select path="advisorId" items="${advisors}"/></td>
    </tr>
    <tr>
      <td>Date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker" path="date" size="20"/></td>
    </tr>
    <tr>
      <td>KPI</td>
      <td>&nbsp;</td>
      <td><form:select path="kpiId" items="${kpis}"/></td>
    </tr>
    <tr>
      <td>Value</td>
      <td>&nbsp;</td>
      <td><form:input id="value" path="value" size="80"/></td>
    </tr>
    <tr>
      <td valign="top">Notes</td>
      <td>&nbsp;</td>
      <td><form:textarea path="notes" rows="5" cols="104"/></td>
    </tr>
  </table>
  <br>
  <input type="submit" align="center" value="Create KPI">
  </form:form>
      
  </div>
</body>
</html>
