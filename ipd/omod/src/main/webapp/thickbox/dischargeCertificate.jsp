<%-- 
    Document   : dischargeCertificate
    Created on : Mar 14, 2015, 4:04:53 PM
    Author     : khairul
--%>

<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<%@ include file="../includes/js_css.jsp" %>


<table width="100%" >
     
     
    <tr><td >Diagnosis<strong> ${obs.valueCodedName.name}</strong></td></tr>
    <tr><td >Diagnosis<strong> ${outCome}</strong></td></tr>
    <tr><td >Investigation<strong> ${inv.valueCode}</strong></td></tr>
     
</table>
<a class="button" href="${pageContext.request.contextPath}/module/ipd/main.htm?tab=1">Back</a>
<br/><br/><br/><br/>
