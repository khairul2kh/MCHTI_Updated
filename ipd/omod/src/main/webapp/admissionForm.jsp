 <%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of IPD module.
 *
 *  IPD module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  IPD module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with IPD module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%>

<%@ include file="/WEB-INF/template/include.jsp"%>
<openmrs:require privilege="Manage IPD" otherwise="/login.htm" redirect="index.htm" />
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="includes/js_css.jsp"%> 

<script>

jQuery(document).ready(function() {
var wardId= ${admission.admissionWard.id};
FIRSTBEDSTRENGTH.getFirstBedStrength(wardId);
});

function validate(){
var admittedward=document.forms["admissionForm"]["admittedWard"].value;
var treatingdoctor=document.forms["admissionForm"]["treatingDoctor"].value;
var bednumber=document.forms["admissionForm"]["bedNumber"].value;

  if (admittedward==null || admittedward=="")
  {
  alert("Please select admitted Ward");
  return false;
  }
  
  if (treatingdoctor==null || treatingdoctor=="")
  {
  alert("Please select treating Doctor");
  return false;
  }
  if (bednumber==null || bednumber=="")
  {
  alert("Please enter bed Number");
  return false;
  }
  if (bednumber!=null)
  {
	var checkMaxBed=parseInt(document.forms["BedStrength"]["bedMax"].value);
	if(isNaN(bednumber)){
	  alert("Please enter bed number in correct format");
	  return false;
	  }
	if(bednumber > checkMaxBed){
	  alert("Please enter correct bed number");
	  return false;
	  }
  }

}

</script>



<br><br><br>
<input type="hidden" id="pageId" value="admissionPage"/>

<form method="post" id="admissionForm" class="kha-aero" onsubmit="return validate()">
<h1>Admission Form</h1>
<input type="hidden" id="id" name="id" value="${admission.id }"/>
<c:if test ="${not empty message }">
<div class="error">
<ul>
    <li>${message}</li>   
</ul>
</div>
</c:if>
<div >
<table width="100%">
	<tr>
		<td width="300px;"  ><spring:message code="ipd.patient.patientName"/>:&nbsp;<b>${admission.patientName }</b></td>
		<td ><spring:message code="ipd.patient.patientId"/>:&nbsp;<b>${admission.patientIdentifier}</b></td>
	</tr>
	<tr>
		<td ><spring:message code="ipd.patient.age"/>:&nbsp;<b>${admission.age }</b></td>
		<td><spring:message code="ipd.patient.gender"/>:&nbsp;<b>${admission.gender }</b></td>
	</tr>
	<%-- ghanshyam 27-02-2013 Feedback #966[Billing]Add Paid Bill/Add Free Bill for Bangladesh module(remove category from registration,OPD,IPD,Inventory) --%>
	<tr>
		<%--
		<td><spring:message code="ipd.patient.category"/>:&nbsp;<b>${patCategory }</b> </td>
		--%>
		<td>${relationType }:&nbsp;${relationName }</td>
		<td><spring:message code="ipd.patient.address"/>: ${address } &nbsp;${upazila } &nbsp;${district } </td>
	</tr>
        <tr>
	<td colspan="10">
	<hr>
	</td>
	</tr>
	<!--
	<tr>
	   <td><spring:message code="ipd.patient.address"/>: ${address } &nbsp;${upazila } &nbsp;${district } </td>
	</tr> -->
	<%-- ghanshyam 27-02-2013 Support #965[IPD]change Tehsil TO Upazila,reomve monthly income field,remove IST Time for Bangladesh module --%>
	<!-- <tr>
		<td><spring:message code="ipd.patient.monthlyIncome"/><em>*</em></td>
		<td><input type="text" id="monthlyIncome" name="monthlyIncome"  /></td>
	</tr> --> 
	<tr>
		<td><spring:message code="ipd.patient.treatingDoctor"/><em>*</em></td>
		<td>
			<select  id="treatingDoctor" name="treatingDoctor" >
			  <option value=""></option>
				<c:if test="${not empty listDoctor }">
			  			<c:forEach items="${listDoctor}" var="doctor" >
			          			<option title="${doctor.givenName}"   value="${doctor.id}"  
			          					<c:if test="${doctor.id ==  admission.opdLog.user.id}">
			          				    	selected
			          				    </c:if>
			          			>${doctor.givenName}</option>
			       		</c:forEach>
		       		</c:if>
			</select>
		</td>
		
	</tr>
	<tr>
		<td ><spring:message code="ipd.patient.admittedWard"/><em>*</em></td>
		<td >
		<select  id="admittedWard" name="admittedWard" onchange="BEDSTRENGTH.getBedStrength(this);">
			  <option value=""></option>
				<c:if test="${not empty listIpd }">
			  			<c:forEach items="${listIpd}" var="ipd" >
			          			<option title="${ipd.answerConcept.name}"   value="${ipd.answerConcept.id}"  
			          					<c:if test="${admission.admissionWard.id ==  ipd.answerConcept.id}">
			          				    	selected
			          				    </c:if>
			          			>${ipd.answerConcept.name}</option>
			       		</c:forEach>
		       		</c:if>
			</select>
		</td>
		<td colspan="5">Click To Select Bed Number
		</td>
	</tr>
	
	<tr>
		<td><spring:message code="ipd.patient.bedNumber"/><em>*</em></td>
		<td ><input class="icon icon-bed" type="text" id="bedNumber" name="bedNumber" readonly="readonly" />
		
		</td>
		
		<td>
		<div id="divBedStrength"></div>
		</td>
	</tr>
	<!-- ghanshyam 11-july-2013 feedback # 1724 Introducing bed availability -->
	<tr>
		<td><spring:message code="ipd.patient.comments"/></td>
		<!-- <td><input type="textarea" id="comments" name="comments" /></td> -->
		<td><textarea  id="comments" name="comments" placeholder="Comments"
                                          ></textarea>
	</tr>
	<tr> <!-- MARTA -->
		<td><spring:message code="ipd.patient.dateTime"/>: </td>
		<td>
		<openmrs:formatDate date="${dateAdmission}" type="long" /></td>  
	</tr>
</table>
</div>
<br/>
<input type="submit"  value="Submit" class="button no-print">
<%-- ghanshyam 27-sept-2012 Support #387 [ALL] Small changes in all modules(note:these lines of code written for cancel button) --%>
<!-- <input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Cancel" onclick="tb_cancel();"> -->
 <a class="button no-print" href="${pageContext.request.contextPath}/module/ipd/main.htm">Back</a>

</form>