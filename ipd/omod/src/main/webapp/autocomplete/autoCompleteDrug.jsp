<%-- 
    Document   : autoCompleteDrug
    Created on : Apr 6, 2015, 2:15:19 PM
    Author     : khairul
--%>

<%@ include file="/WEB-INF/template/include.jsp" %>
<?xml version="1.0"?>
<items>
<c:choose>
<c:when test="${not empty drugs}">
<c:forEach items="${instructions}" var="drg" varStatus="loop">
  <item>
    
    <value>${drg}</value>
  </item>
</c:forEach>  
</c:when>
</c:choose>

</items>