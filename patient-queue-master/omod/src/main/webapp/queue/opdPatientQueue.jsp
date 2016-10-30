 <%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Patient-queue module.
 *
 *  Patient-queue module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Patient-queue module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Patient-queue module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="View Patient Queue" otherwise="/login.htm" redirect="index.htm" />


<table cellpadding="5" cellspacing="0" width="100%" id="queueList">
<tr align="center" >
	<th>#</th>
	<th><spring:message code="patientqueue.queue.patientId"/></th>
	<th><spring:message code="patientqueue.queue.patientName"/></th>
	<th>Age</th>
	<th>Gender</th>
	<th><spring:message code="patientqueue.queue.referralType"/></th>
	<th><spring:message code="patientqueue.queue.status"/></th>
</tr>
<c:choose>
<c:when test="${not empty patientQueues}">
<c:forEach items="${patientQueues}" var="queue" varStatus="varStatus">
	<tr  align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } ' onclick="QUEUE.selectPatientInQueue('${queue.id}');">
		<td><c:out value="${varStatus.count }"/></td>	
		<td>${queue.patientIdentifier}</td>
		<td>${queue.patientName}</td>
		<td>${queue.age }</td>
		<td>${queue.sex}</td>
		<td>${queue.referralConceptName}</td>
		<td>${queue.status}</td>
	</tr>
</c:forEach>

</c:when>
<c:otherwise>
<tr align="center" >
	<td colspan="6">No patient found</td>
</tr>
</c:otherwise>
</c:choose>
</table>
