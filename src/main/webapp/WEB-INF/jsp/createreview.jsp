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
        $("#attachment_description").watermark("If you add an attachment, describe what it is");
        $("#attachment_link").watermark("If you add an attachment, store it on a publicly available webserver, and add a link here");
    });
  </script>
</head>
<body>

<%@include file="includes/header.jsp" %>

  <div id="body">
  
  <h3>Add project review</h3>
  <br>
  
  <form:form method="post" commandName="review">
  <spring:bind path="review.projectId">
    <input type="hidden" name="projectId" value="${pid}"/>
    <input type="hidden" name="adviserId" value="${adviserId}"/>
  </spring:bind>
  <table border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td>Review date</td>
      <td>&nbsp;</td>
      <td><form:input id="datepicker" path="date" size="20"/></td>
    </tr>
    <tr>
      <td valign="top">Notes</td>
      <td>&nbsp;</td>
      <td><form:textarea path="notes" rows="5" cols="104"/></td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2"><b>Attachment:</b></td>
    </tr>
    <tr>
      <td valign="top">Description</td>
      <td>&nbsp;</td>
      <td><form:textarea id="attachment_description" path="attachmentDescription" rows="5" cols="104"/></td>
    </tr>
    <tr>
      <td valign="top">Link</td>
      <td>&nbsp;</td>
      <td><form:input id="attachment_link" path="attachmentLink" size="100"/></td>
    </tr>
  </table>
  <br>
  <input type="submit" align="center" value="Add review to project">
  </form:form>
      
  </div>
</body>
</html>
